package game;

import framework.*;
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
	private HashMap<Shape, RenderedObject> hitboxes;
	private ArrayList<RenderedObject> objects;

	public Room(GameAreaPanel panel, Rectangle bounds) {
		this.bounds = bounds;
		this.quadtree = new QuadTree(bounds.pos.x, bounds.pos.y, bounds.pos2.x, bounds.pos2.y);
		this.hitboxes = new HashMap<>();
		this.objects = new ArrayList<>();
	}

	public void place(RenderedObject object, boolean registerCollisions) {
		if (!objects.contains(object) && isWithinBounds(object)) {
			objects.add(object);
			if (object.getHitbox() != null && registerCollisions) {
				quadtree.insert(object.getHitbox());
				hitboxes.put(object.getHitbox(), object);
			}
		}
	}

	public void remove(RenderedObject object) {
		if (objects.contains(object)) {
			objects.remove(object);
			if (object.getHitbox() != null) {
				quadtree.remove(object.getHitbox());
				hitboxes.remove(object.getHitbox());
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
			}
		}
		// Clears and re-updates the quadtree
		quadtree.clear();
		for (RenderedObject obj : objects) {
			quadtree.insert(obj.getHitbox());
		}

		/* ----- Collision detection ----- */
		for (RenderedObject obj : objects) {
			ArrayList<Shape> retrievedObjects = new ArrayList<>();
			Shape curr = obj.getHitbox();
			quadtree.retrieve(retrievedObjects, curr);

			// Constrain to bounds of the room
			if (obj instanceof MovableObject) {
				MovableObject movObj = (MovableObject) obj;
				Vector2D objSize = obj.getHitbox().getBoundingBox().size();
				Rectangle bounds = this.bounds.getBoundingBox();
				bounds.pos.add(objSize.x/2, objSize.y/2);
				bounds.pos2.sub(objSize.x/2, objSize.y/2);
				Vector2D pos = movObj.getPosition();
				Vector2D speed = new Vector2D(movObj.getSpeed());
				// West wall
				pos.x = Math.max(bounds.pos.x, pos.x);
				if (pos.x <= bounds.pos.x && speed.x < 0) {
					speed.add(-2 * speed.x, 0);
				}
				// East wall
				pos.x = Math.min(bounds.pos2.x, pos.x);
				if (pos.x >= bounds.pos2.x && speed.x > 0) {
					speed.add(-2 * speed.x, 0);
				}
				// North wall
				pos.y = Math.max(bounds.pos.y, pos.y);
				if (pos.y <= bounds.pos.y && speed.y < 0) {
					speed.add(0, -2 * speed.y);
				}
				// South wall
				pos.y = Math.min(bounds.pos2.y, pos.y);
				if (pos.y >= bounds.pos2.y && speed.y > 0) {
					speed.add(0, -2 * speed.y);
				}
				movObj.setSpeed(speed);
				movObj.setPosition(pos.x, pos.y);
			}

			for (Shape other : retrievedObjects) {
				if (curr == other) continue;
				if (curr instanceof Circle && other instanceof Circle) {
					Manifold mf = new Manifold();
					mf.a = hitboxes.get(curr);
					mf.b = hitboxes.get(other);
					if (mf.a != null && mf.b != null) {
						if (Collisions.circVsCirc(mf)) {
							Collisions.resolve(mf.a, mf.b, mf.normal);
						}
					}
				}
			}
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
					if (shape == obj.getHitbox()) continue;
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
