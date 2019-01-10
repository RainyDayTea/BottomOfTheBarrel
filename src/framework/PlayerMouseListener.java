package framework;

import framework.geom.Vector2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerMouseListener implements MouseListener {

	private boolean lmbDown;
	private boolean rmbDown;
	private Vector2D position;

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) lmbDown = true;
		if (e.getButton() == 2) rmbDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) lmbDown = false;
		if (e.getButton() == 2) rmbDown = false;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
