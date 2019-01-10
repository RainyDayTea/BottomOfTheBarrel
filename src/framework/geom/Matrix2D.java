package framework.geom;
public class Matrix2D {
	
	double m00, m01, m10, m11;
	
	public Matrix2D() {
		m00 = 0;
		m01 = 1;
		m10 = 1;
		m11 = 0;
	}
	
	public Matrix2D(double m00, double m01, double m10, double m11) {
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
	}


	
	
}
