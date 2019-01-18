package framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class PlayerKeyListener implements KeyListener {

	private HashSet<String> pressedKeys;

	public PlayerKeyListener() {
		pressedKeys = new HashSet<>();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		String currentKey = KeyEvent.getKeyText(e.getKeyCode());
		pressedKeys.add(currentKey);
	}

	public void keyReleased(KeyEvent e) {
		String currentKey = KeyEvent.getKeyText(e.getKeyCode());
		pressedKeys.remove(currentKey);
	}

	public HashSet<String> getPressedKeys() {
		return pressedKeys;
	}
}
