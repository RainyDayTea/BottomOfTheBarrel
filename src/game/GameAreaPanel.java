package game;

import framework.RenderedObject;
import framework.geom.Vector2D;
import player.Player;
import javax.swing.*;
import java.awt.*;

public class GameAreaPanel extends JPanel {

	// The time waited between calls of paintComponent(), in milliseconds.
	public static final int STEP_DELAY = 16;
	// If true, shows debug info for frame time.
	public static final boolean SHOW_FRAMETIME = true;
	// The elapsed time between calls of paintComponent(), in milliseconds.
	private long deltaTime = 0;
	// The old time used to calculate deltaTime.
	private long currTime = 0;
	// The UNIX time at which the game started.
	private static final long startedTime = System.currentTimeMillis();

	private RenderedObject player;
	private Vector2D camera;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		g.setColor(Color.BLUE);

		deltaTime = System.currentTimeMillis() - startedTime - currTime;
		currTime = System.currentTimeMillis() - startedTime;
		if (SHOW_FRAMETIME) {
			Color oldColor = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawString("Frame time: " + deltaTime + "ms", 0, 12);
			g.drawString("Global time: " + currTime + "ms", 0, 24);
			g.setColor(oldColor);
		}
	}
}
