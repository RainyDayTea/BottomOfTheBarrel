package game;

import javax.swing.*;
import java.awt.*;

public class GameAreaPanel extends JPanel {

	// The time waited between calls of step(), in milliseconds.
	public static final int STEP_DELAY = 16;
	public static final boolean SHOW_DEBUG = true;
	private int deltaTimeMillis = 0;
	private int x, y;

	public void paintComponent(Graphics g) {
		super.paintComponent(g); //required
		x = (int) (Math.random() * this.getWidth());
		y = (int) (Math.random() * this.getHeight());
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		g.setColor(Color.BLUE); //There are many graphics commands that Java can use
		g.fillRect((int)x, (int)y, 50, 50); //notice the x,y variables that we control from our animate method
	}
}
