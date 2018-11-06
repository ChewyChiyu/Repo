import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
public class InterSim extends JPanel{


	private int screenWidth = 800, screenHeight = 800;
	private Dimension screenDim = new Dimension(screenWidth,screenHeight);
	private BufferedImage canvas;
	private Graphics g;

	public InterSim(){
		panel();
		update();
	}

	public void update(){
	


		double[] vals = new double[]{-1,0,1};
		double v1X = vals[(int)(Math.random()*vals.length)], v1Y = vals[(int)(Math.random()*vals.length)];
		double v2X = vals[(int)(Math.random()*vals.length)], v2Y = vals[(int)(Math.random()*vals.length)];
		double v3X = vals[(int)(Math.random()*vals.length)], v3Y = vals[(int)(Math.random()*vals.length)];
		double v4X = vals[(int)(Math.random()*vals.length)], v4Y = vals[(int)(Math.random()*vals.length)];

		double v5X = vals[(int)(Math.random()*vals.length)], v5Y = vals[(int)(Math.random()*vals.length)];
		double v6X = vals[(int)(Math.random()*vals.length)], v6Y = vals[(int)(Math.random()*vals.length)];
		double v7X = vals[(int)(Math.random()*vals.length)], v7Y = vals[(int)(Math.random()*vals.length)];
		double v8X = vals[(int)(Math.random()*vals.length)], v8Y = vals[(int)(Math.random()*vals.length)];

		double min = 0, max = 0;

		for(int c = 0; c < screenWidth; c++){
			for(int r = 0; r < screenHeight; r++){
				double x = c, y = r;

			


				if(x<screenWidth/2){
					double d1X = (x)/screenWidth/2, d1Y = (y)/screenHeight;
					double d3X = (x)/screenWidth/2, d2Y = (screenHeight-y)/screenHeight;
					double d2X = (screenWidth/2-x)/screenWidth/2, d3Y = y/screenHeight;
					double d4X = (screenWidth/2-x)/screenHeight/2, d4Y = (screenHeight-y)/screenHeight;

					

					double g1 = d1X*v1X+d1Y*v1Y;
					double g2 = d2X*v2X+d2Y*v2Y;
					double g3 = d3X*v3X+d3Y*v3Y;
					double g4 = d4X*v4X+d4Y*v4Y;

					double g0 = g1 + fade(x) * (g2 - g1);
					double g01 = g3 + fade(x) * (g4 - g3);

					//double gradient = bi_Inter(0,0,screenWidth/2,screenHeight,g1,g2,g3,g4,x,y);
					
					double n = g0 + fade(y) * (g01-g0); 

					max = Math.max(n,max);
					min = Math.min(n,min);
				}else{
					double d1X = (screenWidth/2-x)/screenWidth/2, d1Y = (y)/screenHeight;
					double d3X = (screenWidth/2-x)/screenWidth/2, d2Y = (screenHeight-y)/screenHeight;
					double d2X = (screenWidth-x)/screenWidth/2, d3Y = y/screenHeight;
					double d4X = (screenWidth-x)/screenHeight/2, d4Y = (screenHeight-y)/screenHeight;
					

					double g1 = d1X*v5X+d1Y*v5Y;
					double g2 = d2X*v6X+d2Y*v6Y;
					double g3 = d3X*v7X+d3Y*v7Y;
					double g4 = d4X*v8X+d4Y*v8Y;

					double g0 = g1 + fade(screenWidth/2-x) * (g2 - g1);
					double g01 = g3 + fade(screenWidth/2-x) * (g4 - g3);

					//double gradient = bi_Inter(0,0,screenWidth/2,screenHeight,g1,g2,g3,g4,x,y);
					
					double n = g0 + fade(y) * (g01-g0); 
					
					max = Math.max(n,max);
					min = Math.min(n,min);
				}
				

				
			}
		}
		System.out.println("max: " + max + " min: " + min);
		for(int c = 0; c < screenWidth; c++){
			for(int r = 0; r < screenHeight; r++){
				double x = c, y = r;

				if(x<screenWidth/2){
					double d1X = (x)/screenWidth/2, d1Y = (y)/screenHeight;
					double d3X = (x)/screenWidth/2, d2Y = (screenHeight-y)/screenHeight;
					double d2X = (screenWidth/2-x)/screenWidth/2, d3Y = y/screenHeight;
					double d4X = (screenWidth/2-x)/screenHeight/2, d4Y = (screenHeight-y)/screenHeight;


					double g1 = d1X*v1X+d1Y*v1Y;
					double g2 = d2X*v2X+d2Y*v2Y;
					double g3 = d3X*v3X+d3Y*v3Y;
					double g4 = d4X*v4X+d4Y*v4Y;
					//double gradient = bi_Inter(0,0,screenWidth/2,screenHeight,g1,g2,g3,g4,x,y);
					
					double g0 = g1 + fade(x) * (g2 - g1);
					double g01 = g3 + fade(x) * (g4 - g3);

					//double gradient = bi_Inter(0,0,screenWidth/2,screenHeight,g1,g2,g3,g4,x,y);
					
					double n = g0 + fade(y) * (g01-g0); 


					double d = (1/(max-min))*(n-min);
					g.setColor(new Color((int)(255*d),(int)(255*d),(int)(255*d)));
					g.fillRect(c,r,1,1);
				}else{
					double d1X = (screenWidth/2-x)/screenWidth/2, d1Y = (y)/screenHeight;
					double d3X = (screenWidth/2-x)/screenWidth/2, d2Y = (screenHeight-y)/screenHeight;
					double d2X = (screenWidth-x)/screenWidth/2, d3Y = y/screenHeight;
					double d4X = (screenWidth-x)/screenHeight/2, d4Y = (screenHeight-y)/screenHeight;


					double g1 = d1X*v5X+d1Y*v5Y;
					double g2 = d2X*v6X+d2Y*v6Y;
					double g3 = d3X*v7X+d3Y*v7Y;
					double g4 = d4X*v8X+d4Y*v8Y;
					//double gradient = bi_Inter(screenWidth/2,0,screenWidth,screenHeight,g1,g2,g3,g4,x,y);
					
					double g0 = g1 + fade(screenWidth/2-x) * (g2 - g1);
					double g01 = g3 + fade(screenWidth/2-x) * (g4 - g3);

					//double gradient = bi_Inter(0,0,screenWidth/2,screenHeight,g1,g2,g3,g4,x,y);
					
					double n = g0 + fade(y) * (g01-g0); 

					double d = (1/(max-min))*(n-min);
					g.setColor(new Color((int)(255*d),(int)(255*d),(int)(255*d)));
					g.fillRect(c,r,1,1);

				}

				
			}
		}
		repaint();

	}

	public double fade(double d){
		return d * d * d * (d * (d*6-15)+10);
		
	}

	public double bi_Inter(double x1, double y1, double x2, double y2, double a1, double a2, double a3, double a4, double x, double y){
		double r1 = ((x2-x)/(x2-x1))*a3+((x-x1)/(x2-x1))*a4;
		double r2 = ((x2-x)/(x2-x1))*a1+((x-x1)/(x2-x1))*a2;
		double r3 = ((y2-y)/(y2-y1))*r1+((y-y1)/(y2-y1))*r2;
		return r3;

	}
	

	public void panel(){
		JFrame frame = new JFrame("InterSim");
		frame.add(this);
		frame.setPreferredSize(screenDim);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newCanvas();
		repaint();
	}

	public void newCanvas(){
		try{ 
			canvas = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB); 
			g = canvas.getGraphics();
		}catch(Exception e){}
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//draw canvas
		g.drawImage(canvas,0,0,null);
	}

	public static void main(String[] args){
		new InterSim();
	}
}