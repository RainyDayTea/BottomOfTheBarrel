package framework.geom;

/**
 * A class representing a rectangle in 2D. Note that this shape cannot be rotated.
 *
 * @author Jake Zhao
 */
public class Rectangle extends Shape {

	public Vector2D pos2;

	/**
	 * Constructs a rectangle using two points.
	 * @param pos The top-left corner of the rectangle.
	 * @param pos2 The bottom-right corner of the rectangle.
	 */
	public Rectangle(Vector2D pos, Vector2D pos2) {
		this.pos = pos;
		this.pos2 = pos2;
	}

	/**
	 * Constructs a rectangle centered at one point and with given dimensions.
	 * @param pos The top-left corner of the rectangle.
	 * @param sizeX The horizontal length.
	 * @param sizeY The vertical length.
	 */
	public Rectangle(Vector2D pos, double sizeX, double sizeY) {
		if (sizeX < 0 || sizeY < 0) {
			throw new NumberFormatException("Rectangle cannot have negative dimensions");
		}
		this.pos = pos.sub(sizeX/2, sizeY/2);
		this.pos2 = new Vector2D(pos.x + sizeX/2, pos.y + sizeY/2);
	}

	/**
	 * Constructs a rectangle using two points.
	 * @param x0 The x-coordinate of the first point.
	 * @param y0 The y-coordinate of the first point.
	 * @param x1 The x-coordinate of the second point.
	 * @param y1 The y-coordinate of the second point.
	 */
	public Rectangle(double x0, double y0, double x1, double y1) {
		this.pos = new Vector2D(x0, y0);
		this.pos2 = new Vector2D(x1, y1);
	}

	/**
	 * Calculates the area of the rectangle.
	 * @return The area of the rectangle.
	 */
	public double getArea() {
		Vector2D size = this.size();
		return size.x * size.y;
	}

	/**
	 * Calculates the perimeter of the rectangle.
	 * @return The perimeter of the rectangle.
	 */
	public double getPerimeter() {
		Vector2D size = this.size();
		return size.x * 2 + size.y * 2;
	}

	/**
	 * Returns the center point of the rectangle.
	 * @return The center point, in the form of a vector.
	 */
	@Override
	public Vector2D getCenter() {
		return new Vector2D((pos.x + pos2.x) / 2, (pos.y + pos2.y) / 2);
	}

	/**
	 * Returns the dimensions of the rectangle.
	 * @return A vector containing the X and Y dimensions.
	 */
	public Vector2D size() {
		double sizeX = Math.abs(this.pos2.x - this.pos.x);
		double sizeY = Math.abs(this.pos2.y - this.pos.y);
		return new Vector2D(sizeX, sizeY);
	}

	/**
	 * Returns the bounding box of this rectangle.
	 * @return A copy of this rectangle, since this is a trivial task.
	 */
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(this.pos.x, this.pos.y, this.pos2.x, this.pos2.y);
	}

}
