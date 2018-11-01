import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.imageio.ImageIO;

public class CastPanel extends JPanel{

	private int windowHeight = 800, windowWidth = 800;

	private Dimension windowDim = new Dimension(windowWidth,windowHeight);
	private BufferedImage canvas;
	private Graphics gCanvas;

	private boolean wPress = false, aPress = false, sPress = false, dPress = false;

	private int sleepTime = 10;

	private boolean isRun = true;

	private BufferedImage gabenImg;

	private int[][] map = new int[][]{{1,1,1,1,1,1,1,1,1,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,0,0,2,0,2,0,0,0,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,0,0,2,0,2,0,0,0,1},
									  {1,0,0,0,0,0,0,0,0,1},
									  {1,1,1,1,1,1,1,1,1,1}};
	private int mapWidth = 10, mapHeight = 10;
	private double x = 5, y = 5, xDir = 1, yDir = 0, xPlane = 0, yPlane = .66;
	private double mSpeed = 0.05, rSpeed = 0.02;
	public CastPanel(){
		load();
		panel();
		keys();
		run();
	}

	public void run(){
		while(isRun){
			try{
				//manange keys
				if(aPress){
					double oXDir = xDir;
					xDir=xDir*Math.cos(-rSpeed) - yDir*Math.sin(-rSpeed);
					yDir=oXDir*Math.sin(-rSpeed) + yDir*Math.cos(-rSpeed);
					double iXPlane = xPlane;
					xPlane=xPlane*Math.cos(-rSpeed) - yPlane*Math.sin(-rSpeed);
					yPlane=iXPlane*Math.sin(-rSpeed) + yPlane*Math.cos(-rSpeed);
				}
				if(dPress){
					double oXDir = xDir;
					xDir=xDir*Math.cos(rSpeed) - yDir*Math.sin(rSpeed);
					yDir=oXDir*Math.sin(rSpeed) + yDir*Math.cos(rSpeed);
					double iXPlane = xPlane;
					xPlane=xPlane*Math.cos(rSpeed) - yPlane*Math.sin(rSpeed);
					yPlane=iXPlane*Math.sin(rSpeed) + yPlane*Math.cos(rSpeed);
				}
				if(wPress){
					if(map[(int)(x + xDir * mSpeed)][(int)y] == 0) {
						x+=xDir*mSpeed;
					}
					if(map[(int)x][(int)(y + yDir * mSpeed)] == 0){
						y+=yDir*mSpeed;
					}

				}
				if(sPress){
					if(map[(int)(x - xDir * mSpeed)][(int)y] == 0) {
						x-=xDir*mSpeed;
					}
					if(map[(int)x][(int)(y - yDir * mSpeed)] == 0){
						y-=yDir*mSpeed;
					}

				}

				gCanvas.setColor(Color.WHITE);
				gCanvas.fillRect(0,0,windowWidth,windowHeight);

				//raycast algorthim
				for(int col = 0; col < windowWidth; col++){
					double camX = 2 * col/(double)windowWidth + 1;
					double rayDirX = xDir + xPlane * camX;
					double rayDirY = yDir + yPlane * camX;
					double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
		   			double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
					double sideDistX,sideDistY;
					int mapX = (int) x, mapY = (int) y;
					int stepX, stepY, side = 0;
					int hit = 0;
					if(rayDirX < 0){
						stepX = -1;
						sideDistX = (x - mapX) * deltaDistX;
					}else{
						stepX = 1;
		    			sideDistX = (mapX + 1.0 - x) * deltaDistX;
					}
					if(rayDirY < 0){
						stepY = -1;
						sideDistY = (y - mapY) * deltaDistY;
					}else{
						stepY = 1;
		    			sideDistY = (mapY + 1.0 - y) * deltaDistY;
					}
					while(hit==0) {
		    		
		    			if(sideDistX < sideDistY){
		    				sideDistX += deltaDistX;
		    				mapX += stepX;
		    				side = 0;
		        		}else{
		        			sideDistY += deltaDistY;
		        			mapY += stepY;
		        			side = 1;
		        		}
		    			int currMap = map[mapX][mapY];
		    			if(currMap > 0){
		    				hit = currMap;
		    			} 
		    		}
		    		double normal = (side==0)?(Math.abs((mapX - x + (1 - stepX) / 2) / rayDirX)):( Math.abs((mapY - y + (1 - stepY) / 2) / rayDirY));
					int lHeight = 0;
					if(normal > 0){
						lHeight = Math.abs((int)(windowHeight / normal));
					}else{
						lHeight = windowHeight;
					}
					int dStart = -lHeight/2+ windowHeight/2;
		    		if(dStart < 0){
		    			dStart = 0;
		    		}
		    		int dEnd = lHeight/2 + windowHeight/2;
		  		    if(dEnd >= windowHeight){
		  		  		dEnd = windowHeight - 1;
		  		  	} 
		  		  	if(hit==1)
		  		  		gCanvas.setColor(ColorGradient.getGrad(new Color[]{new Color(100,100,100),Color.BLACK},normal,0,mapWidth));
					if(hit==2)
						gCanvas.setColor(ColorGradient.getGrad(new Color[]{new Color(173,216,230),Color.BLUE},normal,0,mapWidth));
					gCanvas.drawLine(col,dStart,col,dEnd);

					if(hit==2){
						if(side==1) {
		    				double wX = (x + ((mapY - y + (1 - stepY) / 2) / rayDirY) * rayDirX);
		    				gCanvas.drawImage(subImg((int)((gabenImg.getWidth()/2)*(wX-(int)wX))),col,dStart,1,(dEnd-dStart),null);
		    			}else {
		    				double wY = (y + ((mapX - x + (1 - stepX) / 2) / rayDirX) * rayDirY);
		    				gCanvas.drawImage(subImg((int)((gabenImg.getWidth()/2)*(wY-(int)wY))),col,dStart,1,(dEnd-dStart),null);
		    			}
					
					}

				}


				repaint();


				Thread.sleep(sleepTime);
			}catch(Exception e){ e.printStackTrace();}
		}
	}

	public void keys(){
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "W");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "released W");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "released D");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "S");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "released S");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "A");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "released A");

		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				 wPress = true;
			}

		});
		
	    getActionMap().put("released W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				 wPress = false;
			}

		});
		
		
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				  sPress = true;
			}

		});
		
		getActionMap().put("released S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				 sPress = false;
			}

		});
		
		
		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				  aPress = true;
			}

		});
		
		getActionMap().put("released A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				 aPress = false;
			}

		});
		
		
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				 dPress = true;
			}

		});
		
		getActionMap().put("released D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e){
				dPress = false;
			}

		});
	}

	public void panel(){
		JFrame frame = new JFrame("Cast Panel");
		frame.add(this);
		frame.setPreferredSize(windowDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
		repaint();
	}
	public void newCanvas(){
		try{ 
			canvas = new BufferedImage(windowDim.width, windowDim.height, BufferedImage.TYPE_INT_ARGB);
			gCanvas = canvas.getGraphics();
		}catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}

	public void load(){
		try{
			gabenImg = ImageIO.read(getClass().getResource("gaben.png"));
		}catch(Exception e){e.printStackTrace();}
	}
	public BufferedImage subImg(int x){
		try{
			return gabenImg.getSubimage(x,0,x,gabenImg.getHeight());
		}catch(Exception e){}
		return null;
	}
	public static void main(String[] args){
		new CastPanel();
	}
}