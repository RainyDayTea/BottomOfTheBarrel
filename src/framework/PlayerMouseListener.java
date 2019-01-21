package framework;

import framework.geom.Vector2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PlayerMouseListener implements MouseListener, MouseMotionListener {

	private boolean lmbDown;
	private boolean rmbDown;
	private Vector2D position;

	public PlayerMouseListener() {
		lmbDown = false;
		rmbDown = false;
		position = new Vector2D();
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) lmbDown = true;
		if (e.getButton() == 2) rmbDown = true;
		this.position = new Vector2D(e.getX(), e.getY() - 25);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) lmbDown = false;
		if (e.getButton() == 2) rmbDown = false;
		this.position = new Vector2D(e.getX(), e.getY() - 25);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.position = new Vector2D(e.getX(), e.getY() - 25);
	}

	public Vector2D getPosition() {
		return new Vector2D(position.x, position.y);
	}
}
