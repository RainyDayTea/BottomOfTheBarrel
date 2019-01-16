package player;

import framework.Statistics;
import framework.geom.Circle;
import framework.Character;

import java.awt.image.BufferedImage;

public class Player extends Character {
    private BufferedImage[] dodgeRoll;
    private BufferedImage[] standing;
    private BufferedImage[] running;
    private Circle hitbox;
    private int speed;
    private String weapon;


    public Player(int difficulty){
        if (difficulty == 1) {
            this.setStats(new Statistics(6,1,0));
        }
        else if (difficulty == 2){
            this.setStats(new Statistics(4,1,0));
        }
        else if (difficulty == 3){
            this.setStats(new Statistics(2,1,0));
        }
        this.hitbox = new Circle(getPosition());
        this.speed = 0;
    }
    public boolean Rolling(int characterState){
        boolean invulnerable = false;
         if (characterState == 2){
            invulnerable = true;
        }
        return invulnerable;
    }
    public boolean Invulnerable(int characterState){
        if (Rolling(characterState) == true){
            this.setInvulnerable(isInvulnerable());
        }
        return isInvulnerable();
    }
}
