package framework;

import framework.geom.Shape;
import java.util.HashSet;

/**
 * An interface for any object that can collide with other objects.
 *
 */
public interface Collidable {
	Shape hitbox = null;
	boolean isStatic = false;
	boolean collisionEnabled = false;
	HashSet<Shape> ignoreList = new HashSet<>();

}
