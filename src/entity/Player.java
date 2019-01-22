package entity;

import framework.*;
import framework.Character;
import framework.geom.Vector2D;
import game.GameAreaPanel;
import game.GameFrame;
import map.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Player extends Character {

	private static double ANIM_THRESHOLD = 30.0;

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;
	private ImageAnimator animator;
	private Vector2D lastAnimLocation;

	private BufferedImage[] dodgeRoll;
	private BufferedImage[] standing;
	private BufferedImage[] running;
	private int speed;
	private String weapon;
	private long lastShot;

	public Player(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed, PlayerKeyListener kl, PlayerMouseListener ml) {
		super(parent, x, y, sizeX, sizeY, maxSpeed);
		keyListener = kl;
		mouseListener = ml;
		this.setHasFriction(true);
		this.setTexture("Player");
		this.animator = new ImageAnimator(texture, 4, 4, 64, 64);
		this.lastAnimLocation = this.getHitbox().getCenter();
		this.lastShot = System.currentTimeMillis();
	}

	/**
	 * Performs an update on the entity's movement.
	 * @param scalar Scalar used to adjust magnitude of movement according to framerate.
	 */
	public void updateMovement(double scalar) {
		// Detects key presses for entity movement
		HashSet<String> pressedKeys = keyListener.getPressedKeys();
		Vector2D playerAccel = new Vector2D();
		// Walk north
		if (pressedKeys.contains("W")) {
			playerAccel.add(0, -1);
			animator.setPos(3, animator.getCol());
		}
		// Walk south
		if (pressedKeys.contains("S")) {
			playerAccel.add(0, 1);
			animator.setPos(0, animator.getCol());
		}
		// Walk east
		if (pressedKeys.contains("A")) {
			playerAccel.add(-1, 0);
			animator.setPos(2, animator.getCol());
		}
		// Walk west
		if (pressedKeys.contains("D")) {
			playerAccel.add(1, 0);
			animator.setPos(1, animator.getCol());
		}
		this.setAccel(playerAccel);

		if (mouseListener.isLmbDown()) {

		}
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D screenPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);

		double dist = new Vector2D(this.getRenderBox().getCenter()).sub(lastAnimLocation).getMagnitude();
		// If entity wandered too far from the last position, animate it by one frame/step
		if (dist > ANIM_THRESHOLD) {
			animator.nextImage();
			this.lastAnimLocation = this.getRenderBox().getCenter();
		}
		// If speed is too low, revert to standing position
		if (this.getSpeed().getMagnitude() < 1) {
			animator.setPos(animator.getRow(), 0);
		}
		g.drawImage(animator.getImage(), (int) screenPos.x - 5, (int) screenPos.y, null);

		if (GameAreaPanel.SHOW_DEBUG) {
			super.draw(g, offset);
		}
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

	public long getLastShot() {
		return lastShot;
	}

	public void setLastShot(long lastShot) {
		this.lastShot = lastShot;
	}
}
