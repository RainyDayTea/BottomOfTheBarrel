package framework;

import framework.geom.Vector2D;
import java.awt.Graphics;

/**
 * A simple interface for any classes that want to be drawn.
 * This is used directly in classes like QuadTree to allow the quadtree's boundaries to show up on screen.
 * Otherwise, conventional renderable objects should inherit from RenderedObject.
 */
public interface Renderable {

	public void draw(Graphics g, Vector2D offset);

}
