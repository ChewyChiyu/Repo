import java.awt.Color;
public class ColorGradient{


	public static final Color[] rainbow = new Color[]{new Color(255,0,0),new Color(255,127,0),new Color(255,255,0),new Color(0,255,0),new Color(0,0,255),new Color(75,0,130),new Color(148,0,211)};


	public static Color getGrad(Color[] colors, double x, double xMin, double xMax)
	{
		
		xMax-=xMin;
		x-=xMin;
		xMin=0;
		

		



		if(colors.length==0) return null; 
		Color a,b;
		int aIndex = 0;
		if(colors.length==1)
		{ 
		colors = new Color[]{Color.BLACK,colors[0]};
		a = colors[0];
		b = colors[1]; 
		}
		else
		{		
		aIndex = (int)((x/(xMax-xMin))*(colors.length-1));
		a = colors[aIndex];
		if(aIndex!=colors.length-1) b = colors[aIndex+1];
		else b = colors[aIndex];
		}
		double dX = (xMax-xMin)/(colors.length-1);
		int red = (int)(((double)(b.getRed()-a.getRed())/(double)(dX))*(x-(dX*aIndex))+a.getRed());
		int green = (int)(((double)(b.getGreen()-a.getGreen())/(double)(dX))*(x-(dX*aIndex))+a.getGreen());
		int blue = (int)(((double)(b.getBlue()-a.getBlue())/(double)(dX))*(x-(dX*aIndex))+a.getBlue());
		return new Color(red,green,blue);
	}	


}