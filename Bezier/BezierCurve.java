import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;

public class BezierCurve{

	public static void draw(Point2D p1, Point2D p2, Point2D p3, Graphics g){
		for(double t = 0; t <= 1; t+=0.001){
			double x = (1-t)*((1-t)*p1.getX()+t*p2.getX())+t*((1-t)*p2.getX()+t*p3.getX());
			double y = (1-t)*((1-t)*p1.getY()+t*p2.getY())+t*((1-t)*p2.getY()+t*p3.getY());
			g.fillOval((int)x,(int)y,10,10);
		}
	}

	public static void draw(Point2D p1, Point2D p2, Turtle turtle){
		for(double t = 0; t <= 1; t+=0.01){
			double x = (1-t)*p1.getX()+t*p2.getX();
			double y = (1-t)*p1.getY()+t*p2.getY();
			turtle.g.fillOval((int)x,(int)y,turtle.brush,turtle.brush);
			turtle.p.repaint();
			try{Thread.sleep(turtle.delay);}catch(Exception e){}
		}
	}




}