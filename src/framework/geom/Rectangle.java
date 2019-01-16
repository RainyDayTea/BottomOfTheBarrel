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
	 * Constructs a rectangle using one point and given dimensions.
	 * @param pos The top-left corner of the rectangle.
	 * @param sizeX The horizontal length.
	 * @param sizeY The vertical length.
	 */
	public Rectangle(Vector2D pos, double sizeX, double sizeY) {
		if (sizeX < 0 || sizeY < 0) {
			throw new NumberFormatException("Rectangle cannot have negative dimensions");
		}
		this.pos = pos;
		this.pos2 = new Vector2D(pos.x + sizeX, pos.y + sizeY);
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

}
