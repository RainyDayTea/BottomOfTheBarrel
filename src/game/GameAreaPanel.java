package game;

import framework.*;
import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import player.Player;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameAreaPanel extends JPanel {

	// The time waited between calls of paintComponent(), in milliseconds.
	public static final int STEP_DELAY = 16;
	// If true, draws debug info on screen.
	public static final boolean SHOW_DEBUG = true;
	// The elapsed time between calls of paintComponent(), in milliseconds.
	private long deltaTime = 0;
	// The old time used to calculate deltaTime.
	private long currTime = 0;
	// The UNIX timestamp at which the game started.
	private final long startedTime = System.nanoTime() / 1000000;

	private Player player;
	private Room room;

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;

	public GameAreaPanel(PlayerKeyListener keyListener, PlayerMouseListener mouseListener) {
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		this.requestFocusInWindow();

		// Initialize the player
		player = new Player(0, 0, 50, 50, 10, keyListener, mouseListener);


		// Initialize the environment and add the player to it
		Rectangle roomBounds = new Rectangle(-400, -400, 400, 400);
		this.room = new Room(this, roomBounds);
		room.place(player);

		// Test: Spawn a whole bunch of objects with random motion
		for (int i = 0; i < 50; i++) {
			// Generate a random position inside the room
			int xPos = (int) (Math.random() * roomBounds.pos2.x - roomBounds.pos2.x/2);
			int yPos = (int) (Math.random() * roomBounds.pos2.y - roomBounds.pos2.y/2);
			double xSpeed = Math.random() - 0.5;
			double ySpeed = Math.random() - 0.5;
			MovableObject obj = new MovableObject(0, 0, 25, 25, 10, true);
			obj.setSpeed(new Vector2D(xSpeed, ySpeed));
			// Give the objects a circular hitbox
			double rng = Math.random();
			if (rng > 0.5) {
				obj.setHitbox(new Circle(0, 0, 12.5));
			}
			room.place(obj);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		//setDoubleBuffered(true);

		// Update time variables
		deltaTime = System.nanoTime() / 1000000 - startedTime - currTime;
		currTime = System.nanoTime() / 1000000 - startedTime;

		/* ----- Update the room ---- */
		room.update(g, deltaTime / (double) STEP_DELAY);

		/* ----- Print debug info on top of everything ---- */
		if (SHOW_DEBUG) {
			Color oldColor = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawString("Frame time: " + deltaTime + "ms", 0, 12);
			g.drawString("Game time: " + currTime + "ms", 0, 24);
			g.drawString("Pressed keys: " + player.getKeyListener().getPressedKeys(), 0, 36);
			g.drawString("x: " + player.getPosition().x, 700, 400);
			g.drawString("y: " + player.getPosition().y, 700, 412);
			g.setColor(oldColor);
			g.setColor(Color.ORANGE);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
