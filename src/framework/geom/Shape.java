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

}
