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
	public RenderedObject a;
	public RenderedObject b;
	public double penetration;
	public Vector2D normal;
}
