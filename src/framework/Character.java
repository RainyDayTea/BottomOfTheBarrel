package framework;

import framework.geom.*;
import map.Room;

public abstract class Character extends MovableObject {

    private String name;
    private boolean invulnerable;
    private Statistics stats;

    public Character(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed) {
        super(parent, x, y, sizeX, sizeY, maxSpeed, false);
        this.setInvulnerable(false);
        this.setName(this.hashCode() + "");
        this.setStats(stats);
        this.setHasFriction(true);
        Circle hitbox = new Circle(x, y + 10, Math.min(sizeX, sizeY)/2.0);
        super.setHitbox(hitbox);
    }

    public void die() {

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

}
