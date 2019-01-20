package game;

import framework.MovableObject;
import framework.QuadTree;
import framework.RenderedObject;
import framework.geom.*;
import player.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
	private GameAreaPanel gamePanel;
	private Rectangle bounds;
	private QuadTree quadtree;
	private HashMap<Shape, MovableObject> hitboxes;
	private ArrayList<RenderedObject> objects;

	public Room(GameAreaPanel panel, Rectangle bounds) {
		this.bounds = bounds;
		this.quadtree = new QuadTree(bounds.pos.x, bounds.pos.y, bounds.pos2.x, bounds.pos2.y);
		this.hitboxes = new HashMap<>();
		this.objects = new ArrayList<>();
	}

	public void place(RenderedObject object) {
		if (!objects.contains(object) && isWithinBounds(object)) {
			objects.add(object);
			if (object instanceof MovableObject) {
				MovableObject castedObject = (MovableObject) object;
				if (castedObject.getHitbox() != null) {
					quadtree.insert(castedObject.getHitbox());
					hitboxes.put(castedObject.getHitbox(), castedObject);
				}
			}
		}
	}

	public void remove(RenderedObject object) {
		if (objects.contains(object)) {
			objects.remove(object);
			if (object instanceof MovableObject) {
				MovableObject castedObject = (MovableObject) object;
				if (castedObject.getHitbox() != null) {
					quadtree.remove(castedObject.getHitbox());
					hitboxes.put(castedObject.getHitbox(), castedObject);
				}
			}
		}
	}

	// Checks if the current shape is within bounds of the room.
	private boolean isWithinBounds(RenderedObject object) {
		Rectangle objectBox = object.getRenderBox();
		if (Shape.intersect(objectBox, bounds)) {
			return true;
		}
		return false;
	}

	/**
	 * Performs one update operation on all the objects in the room. This usually means updating movement.
	 * @param timescale The scalar to scale all the movements by, to compensate for fluctuating framerates.
	 */
	public void update(Graphics g, double timescale) {
		Player player = null;
		/* ----- Movement & physics part is done here ----- */
		for (RenderedObject obj : objects) {
			// Update the player according to position.
			if (obj instanceof Player) {
				((Player) obj).updateMovement(timescale);
				player = (Player) obj;
			}
			// Attempt to move all physics objects
			if (obj instanceof MovableObject) {
				((MovableObject) obj).move(timescale);
				MovableObject mov = (MovableObject) obj;
			}
		}
		// Clears and re-updates the quadtree
		quadtree.clear();
		for (RenderedObject obj : objects) {
			quadtree.insert(obj.getHitbox());
		}

		/* ----- Debug rendering ----- */
		if (GameAreaPanel.SHOW_DEBUG) {
			quadtree.draw(g, player.getPosition());
			// TEST: Draw lines between objects that intersect
			Color oldColor = g.getColor();
			g.setColor(Color.RED);
			Vector2D offset = new Vector2D(player.getPosition());

			for (RenderedObject obj : objects) {
				ArrayList<Shape> intersects = new ArrayList<>();
				intersects = quadtree.retrieve(intersects, obj.getHitbox());
				for (Shape shape : intersects) {
					if (Shape.intersect(obj.getHitbox(), shape)) {
						Vector2D center1 = obj.getHitbox().getCenter().add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
						Vector2D center2 = shape.getCenter().add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
						g.drawLine((int) center1.x, (int) center1.y, (int) center2.x, (int) center2.y);
					}
				}
			}
			g.setColor(oldColor);
		}

		/* ----- Main rendering is done here ----- */
		for (int i = 0; i < objects.size(); i++) {
			RenderedObject obj = objects.get(i);
			if (obj.isVisible()) {
				obj.draw(g, player.getPosition());
			}
		}
	}

	/**
	 * Gets the room's bounds
	 * @return The room's bounds, as a rectangle.
	 */
	public Rectangle getBounds() {
		return bounds;
	}
}
