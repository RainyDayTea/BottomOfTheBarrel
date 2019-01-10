package framework;

import framework.geom.Vector2D;

public class Enemy extends Article{

    private int health;
    private int damage;
    private Vector2D position;

    public Enemy(int health){
        this.health = health;
    }
    public void hitCalc(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

}
