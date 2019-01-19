package player;

import framework.PlayerKeyListener;
import framework.PlayerMouseListener;
import framework.Statistics;
import framework.geom.Circle;
import framework.Character;
import framework.geom.Shape;
import framework.geom.Vector2D;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Player extends Character {

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;

	private BufferedImage[] dodgeRoll;
	private BufferedImage[] standing;
	private BufferedImage[] running;
	private Circle hitbox;
	private int speed;
	private String weapon;

	public Player(double x, double y, int sizeX, int sizeY, Vector2D maxSpeed, PlayerKeyListener kl, PlayerMouseListener ml) {
		super(x, y, sizeX, sizeY, maxSpeed);
		keyListener = kl;
		mouseListener = ml;
	}

	/**
	 * Performs an update on the player's movement.
	 * @param scalar Scalar used to adjust magnitude of movement according to framerate.
	 */
	public void updateMovement(double scalar) {
		// Detects key presses for player movement
		HashSet<String> pressedKeys = keyListener.getPressedKeys();
		Vector2D playerSpeed = new Vector2D();
		if (pressedKeys.contains("W")) {
			playerSpeed.add(0, -5);
		}
		if (pressedKeys.contains("S")) {
			playerSpeed.add(0, 5);
		}
		if (pressedKeys.contains("A")) {
			playerSpeed.add(-5, 0);
		}
		if (pressedKeys.contains("D")) {
			playerSpeed.add(5, 0);
		}
		this.setSpeed(playerSpeed);
	}

	public PlayerKeyListener getKeyListener() {
		return keyListener;
	}

	public void setKeyListener(PlayerKeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public PlayerMouseListener getMouseListener() {
		return mouseListener;
	}

	public void setMouseListener(PlayerMouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
}
