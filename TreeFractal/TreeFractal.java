import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
public class TreeFractal extends JPanel{
	private final int W = 1200, H = 800;
	private Dimension windowDim = new Dimension(W,H);
	private BufferedImage canvas;
	private Graphics g;
	private int originX = (int)(W*.45), originY = (int)(H*.8);
	private final int MAX_IT = 14;
	
	private Color[] rainbow = new Color[]{new Color(255,0,0),new Color(255,127,0),new Color(255,255,0),new Color(0,255,0),new Color(0,0,255),new Color(75,0,130),new Color(148,0,211)};

	public TreeFractal(){
		panel();
		repaint();
	}

	public void tree(double x, double y,double xDir, double yDir, double l,double dL,double thetaMin, double thetaMax, double part, double iterations){
		if(iterations==0){
			return;
		}
		double thetaCurr = thetaMin;
		double deltaTheta = (thetaMax-thetaMin)/(part+1);
		for(int p = 0; p < part; p++){
			thetaCurr+=deltaTheta;
			double xP = x + xDir*l;
			double yP = y - yDir*l;
			g.setColor(ColorGradient.getGrad(rainbow,iterations,0,MAX_IT));
			g.drawLine((int)x,(int)y,(int)xP,(int)yP);
			double newDirX = xDir*Math.cos(thetaCurr) - yDir*Math.sin(thetaCurr);
			double newDirY = xDir*Math.sin(thetaCurr) + yDir*Math.cos(thetaCurr);
			tree(xP,yP,newDirX,newDirY,l*dL,dL,thetaMin,thetaMax,part,iterations-1);
		}
	}

	public void panel(){
		JFrame frame = new JFrame("Cast Panel");

		JPanel back = new JPanel();
		back.setPreferredSize(windowDim);
		frame.add(back);

		this.setPreferredSize(new Dimension((int)(W*0.9),H));

		JSlider js = new JSlider(JSlider.VERTICAL,1, MAX_IT, 1);
		js.addChangeListener(e -> {
			JSlider source = (JSlider)e.getSource();
			newCanvas();
			double xDir = 0;
			double yDir = 1;
			double l = 70, dL = 0.9;
			double thetaMin = -(Math.PI/3);
			double thetaMax = (Math.PI/3);
			double part = 2;
			double iterations = (int) source.getValue();
			tree(originX,originY,xDir,yDir,l,dL,thetaMin,thetaMax,part,iterations);
			repaint();
		});
		js.setPreferredSize(new Dimension(20,(int)(windowDim.height*0.9)));
		back.add(js);

		back.add(this);
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
			g = canvas.getGraphics();
			g.setColor(Color.BLACK);
		}catch(Exception e){}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(canvas,0,0,null);
	}

	public static void main(String[] args){
		new TreeFractal();
	}
}