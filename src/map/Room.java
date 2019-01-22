package map;

import entity.Projectile;
import framework.*;
import framework.geom.*;
import game.GameAreaPanel;
import game.GameFrame;
import entity.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A container class for all of the game objects in a local environment. Contains a list
 * of objects and methods to manipulate them.
 *
 * @author Jake Zhao
 */
public class Room {
	// The parent object of this
	private Dungeon parent;
	// The boundaries of the room
	private Rectangle bounds;
	// The room's quadtree
	private QuadTree quadtree;
	// A "lookup table" for looking up which object is associated with which hitbox (used for the quadtree)
	private HashMap<Shape, RenderedObject> hitboxes;
	// A list of all objects in the room
	private ArrayList<RenderedObject> objects;
	// A list of objects to be removed in the next update
	private ArrayList<RenderedObject> removedObjects;
	private Door[] doors;

	public Room(Dungeon parent, Rectangle bounds) {
		// Init all necessary components
		this.bounds = bounds;
		this.quadtree = new QuadTree(bounds.pos.x, bounds.pos.y, bounds.pos2.x, bounds.pos2.y);
		this.hitboxes = new HashMap<>();
		this.objects = new ArrayList<>();
		this.removedObjects = new ArrayList<>();
		this.doors = new Door[4];
		this.parent = parent;

		Vector2D size = bounds.size();
		// Initialize the floor
		Ground ground = new Ground(this, bounds.pos.x, bounds.pos.y, (int) size.x, (int) size.y);
		this.place(ground, false);

		// Generate grass
		for (int i = 0; i < 100; i++) {
			Vector2D roomSize = bounds.size();
			roomSize.sub(60, 60);
			double randX = Math.random() * roomSize.x - roomSize.x/2;
			double randY = Math.random() * roomSize.y - roomSize.y/2;
			Grass grass = new Grass(this, randX, randY, 10, 10);
			this.place(grass, false);
		}

		// Generate barrels
		int numBarrels = (int) (Math.random() * 150);
		for (int i = 0; i < numBarrels; i++) {
			Vector2D roomSize = bounds.size();
			roomSize.sub(60, 60);
			double randX = Math.random() * roomSize.x - roomSize.x/2;
			double randY = Math.random() * roomSize.y - roomSize.y/2;
			Barrel barrel = new Barrel(this, randX, randY, 4);
			this.place(barrel, true);
			double xSpeed = Math.random() * 3 - 1.5;
			double ySpeed = Math.random() * 3 - 1.5;
			barrel.setSpeed(new Vector2D(xSpeed, ySpeed));
		}

		// Initialize doors
		Door northDoor = new Door(this, bounds.getCenter().x, bounds.pos.y, 100, 20);
		doors[0] = northDoor;
		Door eastDoor = new Door(this, bounds.pos2.x, bounds.getCenter().y, 20, 100);
		doors[1] = eastDoor;
		Door southDoor = new Door(this, bounds.getCenter().x, bounds.pos2.y, 100, 20);
		doors[2] = southDoor;
		Door westDoor = new Door(this, bounds.pos.x, bounds.getCenter().y, 20, 100);
		doors[3] = westDoor;
		for (int i = 0; i < 4; i++) {
			this.place(doors[i], true);
		}

		// Test: Spawn a whole bunch of objects with random motion
		for (int i = 0; i < 0; i++) {
			// Generate a random position inside the room
			int xPos = (int) (Math.random() * bounds.pos2.x - bounds.pos2.x/2);
			int yPos = (int) (Math.random() * bounds.pos2.y - bounds.pos2.y/2);
			double xSpeed = Math.random() * 3 - 1.5;
			double ySpeed = Math.random() * 3 - 1.5;
			Projectile obj = new Projectile(this, xPos, yPos, 25, new Vector2D(xSpeed, ySpeed), null, true);
			obj.setSpeed(new Vector2D(xSpeed, ySpeed));
			// Give the objects a circular hitbox
			obj.setHitbox(new Circle(xPos, yPos, 8));
			this.place(obj, true);
		}
	}

	/**
	 * Places a specified object in the room.
	 * @param object The specified object.
	 * @param registerCollisions If false, the object won't be registered in hitboxes, meaning it will ignore collisions and
	 *                           intersections altogether.
	 */
	public void place(RenderedObject object, boolean registerCollisions) {
		// The if statement is a temporary test
		if (!objects.contains(object) ) {
			objects.add(object);
			if (object.getHitbox() != null && registerCollisions) {
				quadtree.insert(object.getHitbox());
				hitboxes.put(object.getHitbox(), object);
			}
		} else {
			System.err.println("Duplicate detected");
		}
	}

	/**
	 * Marks an object for removal.
	 * @param object The object to be removed.
	 */
	public void remove(RenderedObject object) {
		if (objects.contains(object) && !(removedObjects.contains(object))) {
			removedObjects.add(object);
		}
	}

	/**
	 * Physically deletes the object from all data structures.
	 * @param object The object to be deleted.
	 */
	private void delete(RenderedObject object) {
		if (removedObjects.contains(object)) {
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
	 * @param timescale The scalar to scale all the movements by, used to compensate for fluctuating framerates.
	 */
	public void update(Graphics g, double timescale) {
		Vector2D playerPos = new Vector2D();

		/* ----- Delete removed items from the last update ----- */
		for (int i = 0; i < removedObjects.size(); i++) {
			RenderedObject obj = removedObjects.get(i);
			this.delete(obj);
		}
		removedObjects.clear();

		/* ----- Movement & physics part is done here ----- */
		for (int i = 0; i < objects.size(); i++) {
			RenderedObject obj = objects.get(i);
			// Update the entity according to position.
			if (obj instanceof Player) {
				Player player = (Player) obj;
				player.updateMovement(timescale);
				playerPos = obj.getPosition();

				// Player logic in here
				if (player.getMouseListener().isLmbDown()) {
					if (System.currentTimeMillis() - player.getLastShot() > 300) {
						player.setLastShot(System.currentTimeMillis());
						Vector2D projSpeed = new Vector2D(player.getMouseListener().getPosition()).sub(GameFrame.WIDTH/2, GameFrame.HEIGHT/2);
						projSpeed.getUnitVector().scale(15);
						Projectile playerProjectile = new Projectile(this, (int) playerPos.x, (int) playerPos.y, 16, projSpeed, null, true);
						this.place(playerProjectile, true);
					}
				}
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
		for (int i = 0; i < objects.size(); i++) {
			RenderedObject obj = objects.get(i);
			ArrayList<Shape> retrievedObjects = new ArrayList<>();
			quadtree.retrieve(retrievedObjects, obj.getHitbox());

			if (obj.exceedsBoundary(bounds)) {
				obj.onIntersectBoundary(this, bounds);
			}

			for (int j = 0; j < retrievedObjects.size(); j++) {
				Shape other = retrievedObjects.get(j);
				if (other == obj.getHitbox()) continue;
				// Continue only if both objects are registered in the hitboxes map
				if (hitboxes.get(other) != null && hitboxes.get(obj.getHitbox()) != null) {
					// Continue only if the objects intersect each other
					if (Shape.intersect(obj.getHitbox(), other)) {
						RenderedObject otherObj = hitboxes.get(other);
						obj.onIntersect(this, otherObj);
						// NOTE: Produces duplicate calls, but should be fine :)
						// delete in case not fine though
						otherObj.onIntersect(this, obj);
					}
				}
			}
		}

		/* ----- Debug rendering ----- */
		if (GameAreaPanel.SHOW_DEBUG) {
			quadtree.draw(g, playerPos);
			// TEST: Draw lines between objects that intersect
			Color oldColor = g.getColor();
			g.setColor(Color.RED);
			Vector2D offset = new Vector2D(playerPos);

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
				obj.draw(g, playerPos);
			}
		}
	}

	public void onEnter(Player player) {
		this.place(player, true);
	}

	/**
	 * Method called when the room is exited.
	 * @param direction A string indicating compass direction (north, east, south, west).
	 * @return An ArrayList of RenderedObjects that should be transferred between rooms.
	 */
	public ArrayList<RenderedObject> onExit(String direction) {
		// By default, objects are saved in the world. This array is used for transferring them
		ArrayList<RenderedObject> savedObjects = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++) {
			RenderedObject obj = objects.get(i);
			if (obj instanceof Player) {
				savedObjects.add(obj);
				if (direction.equals("north") && doors[0] != null) {
					Vector2D doorPos = doors[2].getPosition();
					obj.setPosition(doorPos.x, doorPos.y - 60);
				} else if (direction.equals("east") && doors[1] != null) {
					Vector2D doorPos = doors[3].getPosition();
					obj.setPosition(doorPos.x + 60, doorPos.y);
				} else if (direction.equals("south")) {
					Vector2D doorPos = doors[0].getPosition();
					obj.setPosition(doorPos.x, doorPos.y + 60);
				} else if (direction.equals("west")) {
					Vector2D doorPos = doors[1].getPosition();
					obj.setPosition(doorPos.x - 60, doorPos.y);
				} else {
					System.err.println("Door/direction could not be found.");
				}
			}
			if (obj instanceof Projectile) {
				this.remove(obj);
			}
		}
		return savedObjects;
	}

	/**
	 * Gets the room's doors.
	 * @return The room's doors.
	 */
	public Door[] getDoors() {
		return doors;
	}

	/**
	 * Gets the room's bounds.
	 * @return The room's bounds, as a rectangle.
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Gets the dungeon the room is contained in.
	 * @return The parent dungeon.
	 */
	public Dungeon getParent() {
		return parent;
	}

}
