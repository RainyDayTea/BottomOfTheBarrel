package framework.geom;

/**
 * A class representing a circle in 2D.
 *
 * @author Jake Zhao
 */
public class Circle extends Shape {

	double radius;

	public Circle(Vector2D pos) {
		this.pos = pos;
		radius = 10;
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
