package framework.geom;

/**
 * A simple superclass representing a 2D shape. The class itself has
 * no utility, but it is used to relate all 2D shapes.
 *
 * @author Jake Zhao
 */
public abstract class Shape {

	public Vector2D pos;

	public abstract double getArea();

	/**
	 * Checks if two rectangles are intersecting.
	 * @param a A rectangle object.
	 * @param b A different rectangle object.
	 * @return True if a and b intersect, false otherwise.
	 */
	public static boolean intersects(Rectangle a, Rectangle b) {
		if (a.pos2.x > b.pos.x || b.pos2.x > a.pos.x) return true;
		if (a.pos2.y > b.pos.y || b.pos2.y > a.pos.y) return true;
		return false;
	}

	/**
	 * Checks if two circles are intersecting.
	 * @param a A circle object.
	 * @param b A different circle object.
	 * @return True if a and b intersect, false otherwise.
	 */
	public static boolean intersects(Circle a, Circle b) {
		// Distance and radius are left squared here to prevent using the Math.sqrt() operation,
		// which is fairly expensive.
		double distSq = Math.pow(Math.abs(b.pos.x-a.pos.x), 2) + Math.pow(Math.abs(b.pos.y-a.pos.y), 2);
		double radiusSq = Math.pow(a.radius + b.radius, 2);
		return radiusSq < distSq;
	}

	/**
	 * Returns the geometric center of the object.
	 * @return The center of the object, as an ordered pair.
	 */
	public abstract Vector2D getCenter();

}
