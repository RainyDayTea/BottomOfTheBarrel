package framework;
public class Rectangle {

	Vector2D pos;
	Vector2D size;

	public Rectangle() {
		this.pos = new Vector2D();
		this.size = new Vector2D(1, 1);
	}

	public Rectangle(Vector2D pos, Vector2D size) {
		this.pos = pos;
		this.size = size;
	}

	public double getArea() {
		return this.size.x * this.size.y;
	}

	public double getPerimeter() {
		return this.size.x * 2 + this.size.y * 2;
	}

}
