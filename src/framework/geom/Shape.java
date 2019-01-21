package framework.geom;

import org.w3c.dom.css.Rect;

/**
 * A superclass representing a 2D shape. The class is used to relate all 2D shapes.
 *
 * @author Jake Zhao
 */
public abstract class Shape {

	public Vector2D pos;

	/**
	 * Calculates area.
	 * @return The area of the object.
	 */
	public abstract double getArea();

	/**
	 * A utility method to check if two shapes are intersecting.
	 * @param sa A shape object.
	 * @param sb A different shape object.
	 * @return True if a and b intersect, false otherwise.
	 */
	public static boolean intersect(Shape sa, Shape sb) {

		// In the case that both objects are rectangles
		if (sa instanceof Rectangle && sb instanceof Rectangle) {
			Rectangle a = (Rectangle) sa;
			Rectangle b = (Rectangle) sb;
			if (a.pos2.x < b.pos.x || a.pos.x > b.pos2.x) return false;
			if (a.pos2.y < b.pos.y || a.pos.y > b.pos2.y) return false;
			return true;
		}
		// In the case that both objects are circles
		else if (sa instanceof Circle && sb instanceof Circle) {
			Circle a = (Circle) sa;
			Circle b = (Circle) sb;
			// Distance and radius are left squared here to prevent using the Math.sqrt() operation,
			// which is fairly expensive.
			double distSq = Math.pow(b.pos.x-a.pos.x, 2) + Math.pow(b.pos.y-a.pos.y, 2);
			double radiusSq = Math.pow(a.radius + b.radius, 2);
			return radiusSq > distSq;
		}

		Rectangle a = null;
		Circle b = null;
		if (sa instanceof Circle && sb instanceof Rectangle) {
			a = (Rectangle) sb;
			b = (Circle) sa;
		} else if (sa instanceof Rectangle && sb instanceof Circle) {
			a = (Rectangle) sa;
			b = (Circle) sb;
		}
		// In the case that one object is a Rectangle, the other is a Circle
		Vector2D aCenter = a.getCenter();
		Vector2D aSize = a.size().scale(0.5);
		Vector2D diff = new Vector2D(Math.abs(aCenter.x - b.pos.x), Math.abs(aCenter.y - b.pos.y));
		if (diff.x < aSize.x + b.radius && diff.y < aSize.y + b.radius) return true;
		return false;
	}

	/**
	 * Returns the geometric center of the object.
	 * @return The center of the object, as an ordered pair.
	 */
	public abstract Vector2D getCenter();

	/**
	 * Returns the bounding box of this shape.
	 * @return A rectangle, the shape's bounding box.
	 */
	public abstract Rectangle getBoundingBox();

}
