import java.awt.Point;
import java.awt.Graphics;
import javax.swing.*;

public class Turtle{

	private Point loc;
	private double xDir = 0, yDir = 1;
	public Graphics g;
	public JPanel p;
	public int brush = 10;
	public int delay = 3;

	public Turtle(Point p, Graphics g, JPanel panel){
		loc = p;
		this.g = g;
		this.p = panel;
	}

	public void rotate(double theta){
		double oldXDir = xDir;
		xDir = oldXDir*Math.cos(theta)-yDir*Math.sin(theta);
		yDir = oldXDir*Math.sin(theta)+yDir*Math.cos(theta);
	}

	public void forward(double d){
		Point next = new Point(loc.x+(int)(xDir*d),loc.y+(int)(yDir*d));
		BezierCurve.draw(loc,next,this);
		loc = next;
	}


	public void squareSpiral(double dTheta, double length, double maxLength){
		int maxCycle = (int)((Math.PI*2)/dTheta);
		for(int cycle = 0; cycle < maxCycle; cycle++){
			g.setColor(ColorGradient.getGrad(ColorGradient.rainbow,cycle,0,maxCycle));
			square(length);
			rotate(dTheta);
			length = (((maxLength-length)/(maxCycle))*(cycle))+length;
		}
	}

	public void square(double length){
		for(int size = 0; size < 4; size++){
			forward(length);
			rotate(Math.PI/2);
		}
	}

}