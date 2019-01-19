package game;

import framework.*;
import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import player.Player;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

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
	private ArrayList<RenderedObject> world;
	private QuadTree hitboxes;

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;

	public GameAreaPanel(PlayerKeyListener keyListener, PlayerMouseListener mouseListener) {
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		this.requestFocusInWindow();

		Vector2D playerMaxSpeed = new Vector2D(10, 10);
		// Initialize the player
		player = new Player(0, 0, 100, 100, playerMaxSpeed, keyListener, mouseListener);


		// Initialize the environment and add the player to it
		this.world = new ArrayList<>();
		this.hitboxes = new QuadTree(-GameFrame.WIDTH, -GameFrame.HEIGHT, GameFrame.WIDTH, GameFrame.HEIGHT);
		world.add(player);
		hitboxes.insert(player.getRenderBox());

		// Test: Spawn a whole bunch of objects with random motion
		for (int i = 0; i < 300; i++) {
			double randomX = Math.random()*GameFrame.WIDTH - GameFrame.WIDTH/2;
			double randomY = Math.random()*GameFrame.HEIGHT - GameFrame.HEIGHT/2;
			MovableObject rObj = new MovableObject(randomX, randomY, 50, 50, new Vector2D(), true);
			rObj.setSpeed(new Vector2D(Math.random() * 4 - 2, Math.random() * 4 - 2));
			world.add(rObj);
			hitboxes.insert(rObj.getRenderBox());
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		//setDoubleBuffered(true);

		// Update time variables
		deltaTime = System.nanoTime() / 1000000 - startedTime - currTime;
		currTime = System.nanoTime() / 1000000 - startedTime;

		// Update hitboxes
		hitboxes.clear();
		for (RenderedObject obj : world) {
			hitboxes.insert(obj.getRenderBox());
		}
		hitboxes.insert(player.getRenderBox());

		/* ----- Render and update objects ---- */
		g.setColor(Color.BLUE);

		for (RenderedObject obj : world) {
			// Draw all visible objects
			if (obj.isVisible()) obj.draw(g, player.getPosition());
			// Update the player according to position.
			if (obj instanceof Player) {
				((Player) obj).updateMovement(deltaTime / GameAreaPanel.STEP_DELAY);
			}
			// Attempt to move all physics objects
			if (obj instanceof MovableObject) {
				((MovableObject) obj).move(deltaTime / GameAreaPanel.STEP_DELAY);
			}
		}

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
			hitboxes.draw(g, player.getPosition());
		}
	}
}
