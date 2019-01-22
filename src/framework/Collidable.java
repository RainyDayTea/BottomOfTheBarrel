package framework;

import framework.geom.Rectangle;
import map.Room;

/**
 * Interface for classes that want to take advantage of the collision system.
 * Technically this can be a collection of methods in RenderedObject, but this is slightly more
 * "open-minded" to expandability.
 *
 * @author Jake Zhao
 */
public interface Collidable {

	void onIntersect(Room room, RenderedObject other);

	void onIntersectBoundary(Room room, Rectangle bounds);

	boolean exceedsBoundary(Rectangle bounds);
}
