import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
public class Panel extends JPanel{

	private int screenWidth = 800, screenHeight = 800;
	private Dimension screenDim = new Dimension(screenWidth,screenHeight);
	private BufferedImage canvas;
	private Graphics g;

	private Point[] points = new Point[3];


	public Panel(){
		panel();
		mouse();
		g.setColor(Color.BLACK);

		 // Turtle turtle = new Turtle(new Point(screenWidth/2,screenHeight/2),g,this);
		 // turtle.brush = 8;
		 // turtle.delay = 1;
		 // turtle.squareSpiral(Math.PI/30,100,400);

	}	
	public void mouse(){
		addMouseListener(new MouseAdapter() {
              @Override
              public void mouseReleased(MouseEvent e) {
				clearBrush(); 
          	  }
          	  public void mousePressed(MouseEvent e) {
				//clearBrush(); 
				cycle(e.getPoint());
            	BezierCurve.draw(points[0],points[1],points[2],g);
            	repaint();
          	  }
        });
		addMouseMotionListener(new MouseMotionAdapter() { 
        	public void mouseDragged(MouseEvent e){
            	cycle(e.getPoint());
            	BezierCurve.draw(points[0],points[1],points[2],g);
            	repaint();
        	}
    	});
	}

	public void cycle(Point p){
		if(points[0]==null){
			points[2] = clonePoint(p);
			points[1] = clonePoint(points[2]);
			points[0] = clonePoint(points[1]); 
		}else{
			points[0] = clonePoint(points[1]);
			points[1] = clonePoint(points[2]);
			points[2] = clonePoint(p); 
		}
	}

	public void clearBrush(){
		points = new Point[3];
	}

	public Point clonePoint(Point p){
		return new Point(p.x,p.y);
	}

	public void panel(){
		JFrame frame = new JFrame("Panel Curve");
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