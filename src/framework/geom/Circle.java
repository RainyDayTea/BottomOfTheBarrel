package framework.geom;

/**
 * A class representing a circle in 2D.
 *
 * @author Jake Zhao
 */
public class Circle extends Shape {

	public double radius;

	public Circle(Vector2D pos) {
		this.pos = pos;
		radius = 10;
	}

	public Circle(Vector2D pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}

	public Circle(double x, double y, double radius) {
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}

	public Vector2D getCenter() { return new Vector2D(pos); }

	public double getArea() {
		return radius * radius * Math.PI;
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(pos.x - radius, pos.y - radius, pos.x + radius, pos.y + radius);
	}

}
