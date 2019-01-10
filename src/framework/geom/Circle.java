package framework.geom;
public class Circle {

	Vector2D pos;
	double radius;

	public Circle() {
		this.pos = new Vector2D();
		this.radius = 1;
	}

	public Circle(Vector2D pos) {
		this.pos = pos;
		radius = 1;
	}

	public Circle(Vector2D pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}

	public double getArea() {
		return radius * radius * Math.PI;
	}

	public double getCir() {
		return 2 * Math.PI * radius;
	}

}
