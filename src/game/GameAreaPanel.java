package game;

import framework.*;
import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import entity.Player;
import map.Room;

import javax.swing.*;
import java.awt.*;

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
	// The entity object.
	private Player player;
	// The current room.
	private Room room;

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;

	public GameAreaPanel(PlayerKeyListener keyListener, PlayerMouseListener mouseListener) {
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		this.requestFocusInWindow();

		/* ----- Load all images ----- */
		ImageLoader.loadAll();

		/* ----- Initialize the player ----- */
		player = new Player(0, 0, 50, 50, 10, keyListener, mouseListener);


		// Initialize the environment and add the entity to it
		Rectangle roomBounds = new Rectangle(-448, -448, 448, 448);
		this.room = new Room(this, roomBounds);
		room.place(player, false);

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

	public long getDeltaTime() { return deltaTime; }
}
