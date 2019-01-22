package game;

/**
 * Class for the JFrame that contains the game. Based off of Mr. Mangat's template.
 * This class contains the main method.
 *
 * @author Mr. Mangat, Jake Zhao
 **/

//Graphics &GUI imports


import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

// Project framework import

import framework.*;

public class GameFrame extends JFrame {
    JFrame thisFrame;
    boolean gameState = false;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    static GameAreaPanel gamePanel;

    GameFrame() {

        super("Bottom of the Barrel");

        this.thisFrame = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setUndecorated(true);  //Set to true to remove title bar

        //Set up the game panel (where we put our graphics)
        PlayerKeyListener keyListener = new PlayerKeyListener();
        PlayerMouseListener mouseListener = new PlayerMouseListener();
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);


        //Create a Panel for stuff
        JPanel decPanel = new DecoratedPanel();
        decPanel.setBorder(new EmptyBorder(0, 1000, 50, 50));

        //Create main panels to put stuff in
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //Create a JButton for the centerPanel

        ImageIcon sb = new ImageIcon("img/startbutton.png");
        JButton startButton = new JButton(sb);

        startButton.setBackground(new Color(0, 0, 0, 0));
        startButton.setRolloverIcon(new ImageIcon("img/startbuttonpressed.png"));
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusPainted(false);
        startButton.addActionListener(new StartButtonListener());
        if (startButton.getModel().isPressed()) {
            gameState = true;
        }
        this.requestFocusInWindow(); //make sure the frame has focus

        //Create a JButton for the centerPanel
        JLabel startLabel = new JLabel("<HTML><H1><font color='black'>Bottom of The Barrel</H1></HTML>");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 0, 0, 0));
        bottomPanel.add(startButton);
        // Create titlescreen
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(startLabel, BorderLayout.CENTER);
        decPanel.add(mainPanel);
        //add the main panel to the frame
        this.add(decPanel);
        //Start the game loop in a separate thread
        this.setVisible(true);
    } //End of Constructor

    //INNER CLASS - Over ride Paint Component for JPANEL
    public class DecoratedPanel extends JPanel {

        DecoratedPanel() {
            this.setBackground(new Color(0, 0, 0, 0));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image pic = new ImageIcon("img/titlescreen1.png").getImage();
            g.drawImage(pic, 0, 0, null);
        }
    }

    //This is an inner class that is used to detect a button press
    class StartButtonListener implements ActionListener {  //this is the required class definition
        public void actionPerformed(ActionEvent event) {
            thisFrame.dispose();
            new StartingFrame(); //create a new FunkyFrame (another file that extends JFrame)
        }

    }
    /*  ============ MAIN METHOD ==============  */
    public static void main(String[] args) {
        new GameFrame();
    }

}
