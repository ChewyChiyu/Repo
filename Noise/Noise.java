public class Noise{

	double[][] vX, vY;
	double w,h;


	public Noise(double w, double h, int dH, int dW){
		vX = new double[dH+1][dW+1];
		vY = new double[dH+1][dW+1];
		this.w = w;
		this.h = h;
		
		for(int i = 0; i < vX.length; i++){
			for(int j = 0; j < vX[0].length; j++){
				vX[i][j] = ((Math.random()>.5)?-1:1)*(int)(Math.random()*255)+255;
				vY[i][j] = ((Math.random()>.5)?-1:1)*(int)(Math.random()*255)+255;
			}
		}
	}


	public double noise(double x, double y){
		int iX = (int)((x*(vX[0].length-1))/w);
		int iY = (int)((y*(vX.length-1))/h);
		double fx = (x-iX*(w/(vX[0].length-1)))/(w/(vX[0].length-1));
		double fy = (y-iY*(h/(vX.length-1)))/(h/(vX.length-1));
		

		double g00X = vX[iY][iX],  g00Y = vY[iY][iX];
		double g10X = vX[iY][iX+1],  g10Y = vY[iY][iX+1];
		double g01X = vX[iY+1][iX],  g01Y = vY[iY+1][iX];
		double g11X = vX[iY+1][iX+1], g11Y = vY[iY+1][iX+1];

		double t = fade(fx);
		double r = g00X*fx+g00Y*fy;
		double s = g10X*(fx-1)+g10Y*fy;
		double g0 = r + t * (s-r);

		r = g01X*fx+g01Y*(fy-1);
		s = g11X*(fx-1)+g11Y*(fy-1);
		double g1 = r + t * (s-r);

		t = fade(fy);

		double g = g0+t*(g1-g0);
		return g/(w*h);
	}


	public double fade(double d){
		return (d*d*d*(d*(d*6-15)+10));
	}

}