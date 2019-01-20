package framework;

import framework.geom.*;

/**
 * WIP
 */
public class Collisions {
	public boolean circVsCirc(Manifold manifold) {
		Circle a = (Circle) manifold.a;
		Circle b = (Circle) manifold.b;

		// Draw vector from a to b
		Vector2D vec = new Vector2D(b.getCenter()).sub(a.getCenter());
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
		} else {

		}
		return false;
	}
}
