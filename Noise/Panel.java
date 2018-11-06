import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;
public class Panel extends JPanel{

	private int screenWidth = 800, screenHeight = 800;
	private Dimension screenDim = new Dimension(screenWidth,screenHeight);
	private BufferedImage canvas;
	private Graphics g;

	public Panel(){
		panel();
		Noise n = new Noise(screenWidth,screenHeight,30,30);
		double min = 0, max = 0;
		for(int r = 0; r < screenWidth; r++){
			for(int c = 0; c < screenHeight; c++){
				double d = (n.noise(r,c));
				min = Math.min(min,d);
				max = Math.max(max,d);
			}
		}
		for(int r = 0; r < screenWidth; r++){
			for(int c = 0; c < screenHeight; c++){
				double d = (n.noise(r,c));
				double e = (d-min)/(max-min);
				g.setColor(new Color(0,(int)(255*e),(int)(255*e)));
				g.fillRect(r,c,1,1);
			}
		}
		repaint();
		System.out.println("max: " + max + " min: " + min);
	}


	public void panel(){
		JFrame frame = new JFrame("Panel Noise");
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
		new Panel();
	}
}