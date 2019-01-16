package game;

/**
 * This template can be used as reference or a starting point
 * for your final summative project
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

	static GameAreaPanel gamePanel;

	GameFrame() {

		super("My Game");
		// Set the frame to full screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		// this.setUndecorated(true);  //Set to true to remove title bar
		//frame.setResizable(false);



		//Set up the game panel (where we put our graphics)
		gamePanel = new GameAreaPanel();
		this.add(new GameAreaPanel());

		PlayerKeyListener keyListener = new PlayerKeyListener();
		this.addKeyListener(keyListener);

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
