package framework;

import framework.geom.*;

/**
 * A static class containing methods for handling collisions.
 * Some code borrowed from a guide on writing a 2D physics engine, by Randy Gaul.
 *
 * @author Randy Gaul, Jake Zhao
 */
public class Collisions {
	// The percentage moved along the normal for positional correction.
	private static final float CORR_PERCENT = 0.2f;
	// Allowance for objects to collide without positional correction activating.
	private static final float SLOP = 0.01f;

	public static void applyForces(Manifold manifold) {
		Vector2D correction = new Vector2D(manifold.normal).scale(Math.max(manifold.penetration - SLOP, 0.0) / (2 * CORR_PERCENT));
		Vector2D vecA = manifold.a.getPosition().sub(correction);
		Vector2D vecB = manifold.b.getPosition().add(correction);

		if (manifold.a instanceof MovableObject && manifold.a.isCollidable()) {
			MovableObject aMovable = (MovableObject) manifold.a;
			aMovable.setPosition(vecA.x, vecA.y);
		}
		if (manifold.a instanceof MovableObject && manifold.b.isCollidable()) {
			MovableObject bMovable = (MovableObject) manifold.b;
			bMovable.setPosition(vecB.x, vecB.y);
		}
 	}

	/**
	 * Checks if a collision occurs between two circles and computes the normal and other collision info.
	 * @param manifold A compound data type passed in. If a collision occurs, this object is modified.
	 * @return True if a collision occurs, false otherwise.
	 */
	public static boolean circVsCirc(Manifold manifold) {
		Circle a = (Circle) manifold.a.getHitbox();
		Circle b = (Circle) manifold.b.getHitbox();

		// Draw vector from a to b
		Vector2D vec = new Vector2D(b.pos).sub(a.pos);
		// Compute distance squared
		double combinedRadius = a.radius + b.radius;
		combinedRadius *= combinedRadius;
		// If the distance vector is greater than the combined radii of the circles,
		// there is no collision.
		if (vec.getMagnitudeSq() > combinedRadius) {
			return false;
		}
		// If the circles have actually collided, compute the manifold
		double dist = vec.getMagnitude();
		if (dist != 0) {
			combinedRadius = a.radius + b.radius;
			manifold.penetration = combinedRadius - dist;
			manifold.normal = new Vector2D(vec).scale(0.5/combinedRadius);
		} else {
			manifold.penetration = a.radius;
			manifold.normal = new Vector2D(1, 0);
		}
		applyForces(manifold);

		return true;
	}

	/**
	 * Resolves a collision between two objects and a normal
	 * @param a The first object.
	 * @param b The second object.
	 * @param normal The normal along which the collision occurs.
	 */
	public static void resolve(RenderedObject a, RenderedObject b, Vector2D normal) {
		Vector2D vec = new Vector2D(b.getPosition()).sub(a.getPosition());
		double velAlongNormal = new Vector2D(normal).dotProduct(vec);

		//if (velAlongNormal >= 0) {
			velAlongNormal *= -0.2;
			Vector2D impulse = new Vector2D(normal).scale(velAlongNormal);
			// Ignore non-collidable objects
			if (a instanceof MovableObject && a.isCollidable()) {
				((MovableObject) a).setSpeed(((MovableObject) a).getSpeed().sub(new Vector2D(impulse)));
			}
			if (b instanceof MovableObject && b.isCollidable()) {
				((MovableObject) b).setSpeed(((MovableObject) b).getSpeed().sub(new Vector2D(impulse)));
			}
		//}
	}
}
