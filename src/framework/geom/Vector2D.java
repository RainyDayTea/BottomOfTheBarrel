package framework.geom;

public class Vector2D {
	
	double x;
	double y;
	
	public Vector2D() {
		x = 0;
		y = 0;
	}
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2D other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void sub(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}

	public void set(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public double dotProduct(Vector2D other) {
		return this.x * other.x + this.y * other.y;
	}
	
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
}
