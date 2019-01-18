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
	// If true, shows debug info for frame time.
	public static final boolean SHOW_FRAMETIME = true;
	// The elapsed time between calls of paintComponent(), in milliseconds.
	private long deltaTime = 0;
	// The old time used to calculate deltaTime.
	private long currTime = 0;

	private final long startedTime = System.nanoTime() / 1000000;

	private Player player;
	private ArrayList<RenderedObject> world;
	private Vector2D camera;

	private PlayerKeyListener keyListener;
	private PlayerMouseListener mouseListener;

	public GameAreaPanel(PlayerKeyListener keyListener, PlayerMouseListener mouseListener) {
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		this.requestFocusInWindow();

		// Calculate the center of the screen and place the player there
		Vector2D centerOfScreen = new Vector2D(GameFrame.WIDTH / 2, GameFrame.HEIGHT / 2);
		Vector2D p0 = new Vector2D(centerOfScreen.x - 50, centerOfScreen.y - 50);
		Vector2D p1 = new Vector2D(centerOfScreen.x + 50, centerOfScreen.y + 50);

		Vector2D playerMaxSpeed = new Vector2D(10, 10);

		player = new Player(centerOfScreen.x, centerOfScreen.y, 50, 50, playerMaxSpeed);

		// Initialize the environment and add the player to it
		this.world = new ArrayList<>();
		world.add(player);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		//setDoubleBuffered(true);

		// Update time variables
		deltaTime = System.nanoTime() / 1000000 - startedTime - currTime;
		currTime = System.nanoTime() / 1000000 - startedTime;

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
		player.setSpeed(playerSpeed);

		/* ----- Render objects here ---- */
		g.setColor(Color.BLUE);
		for (RenderedObject obj : world) {
			// Draw all visible objects
			if (obj.isVisible()) obj.draw(g);
			// Attempt to move all physics objects
			if (obj instanceof MovableObject) {
				((MovableObject) obj).move(deltaTime / GameAreaPanel.STEP_DELAY);
			}
		}

		/* ----- Print debug info on top of everything ---- */
		if (SHOW_FRAMETIME) {
			Color oldColor = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawString("Frame time: " + deltaTime + "ms", 0, 12);
			g.drawString("Game time: " + currTime + "ms", 0, 24);
			String buffer = "";
			for (String str : pressedKeys) {
				buffer += str + " ";
			}
			g.drawString("Pressed keys: " + pressedKeys, 0, 36);
			g.setColor(oldColor);
		}
	}
}
