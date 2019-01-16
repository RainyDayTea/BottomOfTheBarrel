package player;

import framework.Statistics;
import framework.geom.Circle;
import framework.Character;
import framework.geom.Vector2D;

import java.awt.image.BufferedImage;

public class Player extends Character {
    private BufferedImage[] dodgeRoll;
    private BufferedImage[] standing;
    private BufferedImage[] running;
    private Circle hitbox;
    private int speed;

    public Player(int difficulty){
        if (difficulty == 1) {
            this.setStats(new Statistics(6,1,0));
        }
        this.hitbox = new Circle(getPosition());
        this.speed = 0;
    }
}
