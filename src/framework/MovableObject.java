package framework;

import framework.geom.Vector2D;

import java.awt.image.BufferedImage;

/**
 * An object capable of movement. Two fields, speed and accel are added to
 * represent speed and acceleration.
 *
 * @author Jake Zhao
 */
public class MovableObject extends RenderedObject {

	// The object's speed. This is added to the object's position every frame.
	private Vector2D speed;
	// The object's maximum speed. Setting the x or y value less than 0 disables this.
	private Vector2D maxSpeed;
	// The object's acceleration. This is added to the object's speed every frame.
	private Vector2D accel;

	/**
	 * Constructs a movable object with no initial speed and acceleration.
	 * @param x The x-coordinate of the object.
	 * @param y The y-coordinate of the object.
	 * @param isVisible If the object is initially visible.
	 */
	public MovableObject(double x, double y, Vector2D maxSpeed, boolean isVisible) {
		super(x, y, isVisible);
		this.speed = new Vector2D();
		this.accel = new Vector2D();
		if (maxSpeed != null) maxSpeed = new Vector2D(maxSpeed);
		else maxSpeed = new Vector2D(-1, -1);
	}

	public MovableObject(double x, double y, int sizeX, int sizeY, Vector2D maxSpeed, boolean isVisible) {
		super(x, y, sizeX, sizeY, isVisible);
		this.speed = new Vector2D();
		this.accel = new Vector2D();
		if (maxSpeed != null) maxSpeed = new Vector2D(maxSpeed);
		else maxSpeed = new Vector2D(-1, -1);
	}

	/**
	 * Constructs a movable object with an initial speed and acceleration.
	 * @param x The x-coordinate of the object.
	 * @param y The y-coordinate of the object.
	 * @param isVisible If the object is initially visible.
	 * @param speed The object's initial speed.
	 * @param accel The object's initial acceleration.
	 */
	public MovableObject(int x, int y, boolean isVisible, Vector2D speed, Vector2D accel) {
		super(x, y, isVisible);
		this.speed = speed;
		this.accel = accel;
	}

	/**
	 * Performs a single physics calculation. It translates the object's position vector by its speed vector.
	 * To retain consistent(-ish) movement for different framerates, a time scale can be passed in
	 * to adjust the magnitude of all the calculations.
	 * @param scalar The time scale.
	 */
	public void move(double scalar) {
		Vector2D newPos = this.getPosition().add(this.speed.x * scalar, this.speed.y * scalar);
		this.setPosition(newPos.x, newPos.y);
	}

	/**
	 * Translates the render box by a coordinate (x, y).
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public void translate(double x, double y) {
		this.getRenderBox().pos.x += x;
		this.getRenderBox().pos.y += y;
		this.getRenderBox().pos2.x += x;
		this.getRenderBox().pos2.y += y;
	}

	/**
	 * Gets the object's current speed.
	 * @return The object's speed as a vector.
	 */
	public Vector2D getSpeed() {
		return speed;
	}

	/**
	 * Sets the object's speed.
	 * @param speed The new speed.
	 */
	public void setSpeed(Vector2D speed) {
		this.speed.x = speed.x;
		this.speed.y = speed.y;
	}

	/**
	 * Gets the object's current max speed.
	 * @return The object's max speed as a vector.
	 */
	public Vector2D getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the object's MaxSpeed.
	 * @param maxSpeed The new MaxSpeed.
	 */
	public void setMaxSpeed(Vector2D maxSpeed) {
		this.maxSpeed.x = maxSpeed.x;
		this.maxSpeed.y = maxSpeed.y;
	}

	/**
	 * Gets the object's current acceleration.
	 * @return The object's acceleration as a vector.
	 */
	public Vector2D getAccel() {
		return accel;
	}

	/**
	 * Sets the object's acceleration.
	 * @param accel The new acceleration.
	 */
	public void setAccel(Vector2D accel) {
		this.accel.x = accel.x;
		this.accel.y = accel.y;
	}
}
