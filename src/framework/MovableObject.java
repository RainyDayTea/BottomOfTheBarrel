package framework;

import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;

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
	private int maxSpeed;
	// The object's acceleration. This is added to the object's speed every frame.
	private Vector2D accel;

	/**
	 * Constructs a movable object. The hitbox is automatically set to the render box.
	 * @param x The x-coordinate of the object.
	 * @param y The y-coordinate of the object.
	 * @param sizeX The width of the object.
	 * @param sizeY The height of the object.
	 * @param maxSpeed The max speed of the object.
	 * @param collidable Sets if the object is initially collidable.
	 */
	public MovableObject(double x, double y, int sizeX, int sizeY, int maxSpeed, boolean collidable) {
		super(x, y, sizeX, sizeY, true, collidable);
		this.speed = new Vector2D();
		this.accel = new Vector2D();
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Performs a single physics calculation. It translates the object's position vector by its speed vector.
	 * To retain consistent(-ish) movement for different framerates, a time scale can be passed in
	 * to adjust the magnitude of all the calculations.
	 * @param scalar The time scale.
	 */
	public void move(double scalar) {
		Vector2D newSpd = new Vector2D(this.speed).add(this.accel.x * scalar, this.accel.y * scalar);
		double clampedMagnitude = Math.sqrt(Math.min(this.maxSpeed*this.maxSpeed, newSpd.getMagnitudeSq()));
		this.setSpeed(newSpd.getUnitVector().scale(clampedMagnitude));
		Vector2D newPos = new Vector2D(this.getPosition()).add(this.speed.x * scalar, this.speed.y * scalar);
		this.setPosition(newPos.x, newPos.y);
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
		this.speed = speed;
	}

	/**
	 * Gets the object's current max speed.
	 * @return The object's max speed.
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the object's max speed.
	 * @param maxSpeed The new max speed.
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
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
		this.accel = accel;
	}
}
