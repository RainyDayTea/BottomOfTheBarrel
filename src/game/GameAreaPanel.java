package game;

import javax.swing.*;
import java.awt.*;

public class GameAreaPanel extends JPanel {

	// The time waited between calls of step(), in milliseconds.
	public static final int STEP_DELAY = 16;
	public static final boolean SHOW_FRAMETIME = true;
	private long deltaTime = 0;
	private long currTime = 0;
	private int x, y;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		x = (int) (Math.random() * this.getWidth());
		y = (int) (Math.random() * this.getHeight());
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int)y, 50, 50);
		if (SHOW_FRAMETIME) {
			Color oldColor = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawString("Frame time: ", 0, 0);
		}
	}
}
