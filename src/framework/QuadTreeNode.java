package framework;

import framework.geom.Vector2D;
import java.util.ArrayList;

/**
 * Represents a node of the quadtree.
 *
 * WIP - Finished once hitboxes are finalized.
 *
 * @author Jake Zhao
 */
public class QuadTreeNode {

	private QuadTreeNode q1;
	private QuadTreeNode q2;
	private QuadTreeNode q3;
	private QuadTreeNode q4;
	private Vector2D size;
	private ArrayList<Vector2D> objects;

	public static final int SPLIT_THRESHOLD = 5;

	public QuadTreeNode getQ1() {
		return q1;
	}

	public QuadTreeNode getQ2() {
		return q2;
	}

	public QuadTreeNode getQ3() {
		return q3;
	}

	public QuadTreeNode getQ4() {
		return q4;
	}

	public ArrayList<Vector2D> getObjects() {
		return objects;
	}

	public void split() {}

	public void merge() {}

	public void add() {}

	public void remove() {}

	public void clear() {}

	public boolean isLeafNode() {
		if (q1 == null && q2 == null && q3 == null && q4 == null) return true;
		return false;
	}

}
