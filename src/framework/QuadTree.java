package framework;

import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Shape;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;
import java.util.ArrayList;

/**
 * A tree data structure which splits into a max of four branches at each node. This is used to reduce the
 * computation required for collision detection (which is normally O(n^2)). Each node stores a
 * rectangular region as well as a list of all objects inside it. Objects are then inserted into the tree.
 * If too many objects lie within the same region, the region is recursively subdivided into four quadrants
 * until this condition becomes false.
 *
 * It should be noted that any objects that do not intersect the quadtree at any level are not affected
 * by collisions.
 *
 * The original code was found on a website and written by Steven Lambert. It has been edited slightly to
 * fit the needs of this project.
 *
 * @author Steven Lambert, edited by Jake Zhao
 */
public class QuadTree implements Renderable {

	// The max amount of objects that are stored in a node before it splits.
	private static final int MAX_OBJ = 5;
	// The max depth allowed in the tree. Used to limit subdividing.
	private static final int MAX_DEPTH = 6;
	// The level of this node in the tree. The root node is at level 0, with subnodes being
	// the parent node's level + 1.
	private int level;
	// An array of child nodes. The array length is always 4.
	private QuadTree[] nodes;
	// The rectangular region which the current node covers.
	private Rectangle region;
	// The list of objects in this region.
	private ArrayList<Shape> objects;

	/**
	 * Constructs a quadtree with a given area.
	 * @param x0 The x-coordinate of the top-left point.
	 * @param y0 The y-coordinate of the top-left point.
	 * @param x1 The x-coordinate of the bottom-right point.
	 * @param y1 The y-coordinate of the bottom-right point.
	 */
	public QuadTree(double x0, double y0, double x1, double y1) {
		this.level = 0;
		this.region = new Rectangle(x0, y0, x1, y1);
		this.objects = new ArrayList<>();
		this.nodes = new QuadTree[4];
	}

	// Construts a quadtree with a specific level value. For internal use only.
	private QuadTree(int level, double x0, double y0, double x1, double y1) {
		this.level = level;
		this.region = new Rectangle(x0, y0, x1, y1);
		this.objects = new ArrayList<>();
		this.nodes = new QuadTree[4];
	}

	/**
	 * Draws various debug info for the quadtree. A node's region is outlined and objects stored in
	 * the node are visualized.
	 * @param g The graphics object to draw to.
	 * @param offset The offset vector, used for pannable cameras.
	 */
	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(region.pos).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
		Vector2D size = region.size();
		Vector2D center = new Vector2D(drawPos).add(size.x/2, size.y/2);
		g.drawRect((int) Math.round(drawPos.x), (int) Math.round(drawPos.y), (int) Math.round(size.x), (int) Math.round(size.y));
		// Recursively draw the subnodes
		if (nodes[0] != null) {
			for (QuadTree node : nodes) {
				node.draw(g, offset);
			}
		}

		// Draw lines between the objects and the node
		for (Shape obj : objects) {
			Vector2D objDrawPos = new Vector2D(obj.getCenter()).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
			g.drawLine((int) Math.round(objDrawPos.x), (int) Math.round(objDrawPos.y), (int) Math.round(center.x), (int) Math.round(center.y));
		}
	}

	/**
	 * Splits the current node into four subnodes.
	 */
	public void split() {
		Vector2D center = region.getCenter();

		nodes[0] = new QuadTree(level+1, center.x, region.pos.y, region.pos2.x, center.y);
		nodes[1] = new QuadTree(level+1, region.pos.x, region.pos.y, center.x, center.y);
		nodes[2] = new QuadTree(level+1, region.pos.x, center.y, center.x, region.pos2.y);
		nodes[3] = new QuadTree(level+1, center.x, center.y, region.pos2.x, region.pos2.y);
	}

	/**
	 * Clears the entire tree.
	 */
	public void clear() {
		objects.clear();
		for (int i = 0; i < 4; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	/**
	 * Returns the index of the quadrant a shape belongs to. If the shape is partially in
	 * two or more quadrants, it is considered to be in none of them but the parent node.
	 * @param obj The shape to be checked.
	 * @return An integer from 0 to 3 denoting the quadrant it falls in, or -1 if it fits in the parent node,
	 * or -2 if it is out of bounds.
	 */
	private int getIndex(Shape obj) {
		int index = -1;

		Vector2D center = region.getCenter();
		if (obj instanceof Rectangle) {
			Rectangle rect = (Rectangle) obj;
			if (rect.pos2.x < region.pos.x || rect.pos2.y < region.pos.y) return -2;
			if (rect.pos.x > region.pos2.x || rect.pos.y > region.pos2.y) return -2;

			// Rectangle completely lies in Q1 or Q2
			boolean fitsQ12 = rect.pos.y < center.y && rect.pos2.y < center.y;
			// Rectangle completely lies in Q3 or Q4
			boolean fitsQ34 = rect.pos.y > center.y && rect.pos2.y > center.y;

			if (rect.pos.x < center.x && rect.pos2.x < center.x) {
				if (fitsQ12) index = 1;
				else if (fitsQ34) index = 2;
			} else if (rect.pos.x > center.x && rect.pos2.x > center.x) {
				if (fitsQ12) index = 0;
				else if (fitsQ34) index = 3;
			}
		} else if (obj instanceof Circle) {
			Circle cir = (Circle) obj;
			if (cir.pos.x + cir.radius < region.pos.x || cir.pos.x - cir.radius > region.pos2.x) return -2;
			if (cir.pos.y + cir.radius < region.pos.y || cir.pos.y - cir.radius > region.pos2.y) return -2;

			boolean fitsQ12 = cir.pos.y + cir.radius < center.y;
			boolean fitsQ34 = cir.pos.y - cir.radius > center.y;
			if (cir.pos.x + cir.radius < center.x) {
				if (fitsQ12) index = 1;
				else if (fitsQ34) index = 2;
			} else if (cir.pos.x - cir.radius > center.x) {
				if (fitsQ12) index = 0;
				else if (fitsQ34) index = 3;
			}
		}
		return index;
	}

	/**
	 * Inserts a given object into the tree.
	 * @param newObj The inputted object.
	 */
	public void insert(Shape newObj) {
		// If the current node is not a leaf node
		int index = getIndex(newObj);
		if (index == -2) return;
		if (nodes[0] != null) {
			if (index >= 0) {
				nodes[index].insert(newObj);
				return;
			}
		}

		objects.add(newObj);

		if (objects.size() > MAX_OBJ && this.level < MAX_DEPTH) {
			if (nodes[0] == null) {
				split();
			}

			int i = 0;
			while (i < objects.size()) {
				index = getIndex(objects.get(i));
				if (index >= 0) {
					nodes[index].insert(objects.remove(i));
				} else {
					i++;
				}
			}
		}
	}

	public void remove(Shape newObj) {
		if (objects.contains(newObj)) {
			objects.remove(newObj);
			return;
		}
		int index = getIndex(newObj);
		if (index == -2) return;
		if (nodes[0] != null) {
			if (index >= 0) {
				nodes[index].remove(newObj);
				return;
			}
		}
	}

	/**
	 * Given an input object, fetches all other objects that could feasibly collide with it.
	 * @param returnObjs An empty ArrayList.
	 * @param obj The inputted object.
	 * @return The inputted ArrayList, filled with objects.
	 */
	public ArrayList<Shape> retrieve(ArrayList<Shape> returnObjs, Shape obj) {
		int index = getIndex(obj);
		if (index >= 0 && nodes[0] != null) {
			nodes[index].retrieve(returnObjs, obj);
		}
		if (index > -2) returnObjs.addAll(objects);

		return returnObjs;
	}

	public QuadTree[] getNodes() { return nodes; }
	public Rectangle getRegion() { return region; }
	public ArrayList<Shape> getObjects() { return objects; }

}
