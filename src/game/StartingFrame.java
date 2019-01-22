package game;

import framework.PlayerKeyListener;
import framework.PlayerMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class StartingFrame extends JFrame {
    JFrame thisFrame;
    boolean gameState = false;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    static GameAreaPanel gamePanel;

    StartingFrame() {
        super("Bottom of the Barrel");

        this.thisFrame = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setUndecorated(true);  //Set to true to remove title bar
        PlayerKeyListener keyListener = new PlayerKeyListener();
        PlayerMouseListener mouseListener = new PlayerMouseListener();
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        Thread t = new Thread(new Runnable() {
            public void run() {
                step();
            }
        }); //start the gameLoop

        gamePanel = new GameAreaPanel(keyListener, mouseListener);
        this.add(gamePanel);
        t.start();
        this.setVisible(true);
    }

    // The method to be called every frame
    public void step() {

        while (true) {
            this.repaint();
            try {
                Thread.sleep(GameAreaPanel.STEP_DELAY);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
