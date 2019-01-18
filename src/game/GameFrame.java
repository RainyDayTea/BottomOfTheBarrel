package game;

/**
 * This template can be used as reference or a starting point
 * for your final summative project
 *
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
// Project framework import
import framework.*;

class GameFrame extends JFrame {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	static GameAreaPanel gamePanel;

	GameFrame() {

		super("BOTTOM OF THE BARREL");
		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		// this.setUndecorated(true);  //Set to true to remove title bar

		//Set up the game panel (where we put our graphics)
		PlayerKeyListener keyListener = new PlayerKeyListener();
		PlayerMouseListener mouseListener = new PlayerMouseListener();
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		gamePanel = new GameAreaPanel(keyListener, mouseListener);
		this.add(gamePanel);

		this.requestFocusInWindow(); //make sure the frame has focus

		this.setVisible(true);

		//Start the game loop in a separate thread
		Thread t = new Thread(new Runnable() { public void run() { step(); }}); //start the gameLoop
		t.start();

	} //End of Constructor

	// The method to be called every frame
	public void step() {

		while (true) {
			this.repaint();
			try {
				Thread.sleep(GameAreaPanel.STEP_DELAY);
			} catch (Exception exc) {}  //delay
		}
	}

	/*  ============ MAIN METHOD ==============  */
	public static void main(String[] args) {
		GameFrame game = new GameFrame();
	}

}
