package framework;

import framework.geom.Shape;
import framework.geom.Vector2D;

/**
 * Essentially a compound data type containing collision information.
 * Code is based off a guide on 2D physics by Randy Gaul, slightly edited
 * and adapted to Java.
 *
 * @author Randy Gaul, Jake Zhao
 */
public class Manifold {
	public Shape a;
	public Shape b;
	public double penetration;
	public Vector2D aNormal;
	public Vector2D bNormal;
}
