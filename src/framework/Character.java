package framework;

import framework.geom.*;

public abstract class Character {

    private String name;
    private Vector2D position = new Vector2D();
    private Vector2D speed = new Vector2D();
    private boolean invulnerable;
    private int vectorDirection;
    private Statistics stats = new Statistics(0,0,0);

    public Character() {
        this.setInvulnerable(false);
        this.setName(name);
        this.setPosition(position);
        this.setSpeed(speed);
        this.setVectorDirection(vectorDirection);
        this.setStats(stats);
    }
    public void setName(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public int getVectorDirection() {
        return vectorDirection;
    }

    public void setVectorDirection(int vectorDirection) {
        this.vectorDirection = vectorDirection;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }
}
