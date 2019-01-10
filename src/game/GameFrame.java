package game;

/**
 * This template can be used as reference or a starting point
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
// Project framework import
import framework.*;

class GameFrame extends JFrame {

	//class variable (non-static)
	static double x, y;
	static GameAreaPanel gamePanel;
	// The time waited between calls of step(), in milliseconds.
	public static final int STEP_DELAY = 16;


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
			this.x = (Math.random()*this.getWidth());  //update coords
			this.y = (Math.random()*this.getHeight());
			try {
				Thread.sleep(STEP_DELAY);
			} catch (Exception exc) {}  //delay
			this.repaint();
		}
	}

	/** --------- INNER CLASSES ------------- **/

	// Inner class for the the game area - This is where all the drawing of the screen occurs
	private class GameAreaPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); //required
			setBackground(Color.BLACK);
			setDoubleBuffered(true);
			g.setColor(Color.BLUE); //There are many graphics commands that Java can use
			g.fillRect((int)x, (int)y, 50, 50); //notice the x,y variables that we control from our animate method

		}
	}

	/*  ============ MAIN METHOD ==============  */
	public static void main(String[] args) {
		GameFrame game = new GameFrame();
	}

}
