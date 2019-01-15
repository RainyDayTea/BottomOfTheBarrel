package player;

import framework.geom.Circle;
import framework.Character;
import framework.geom.Vector2D;

import java.util.Vector;

public class Player extends Character {
    private int[] dodgeRoll;
    private Circle hitbox = new Circle(getPosition());
    public Player(){

    }


}
