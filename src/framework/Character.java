package framework;

import framework.geom.*;

public abstract class Character extends MovableObject {

    private String name;
    private boolean invulnerable;
    private Statistics stats;

    public Character(double x, double y, int sizeX, int sizeY, int maxSpeed) {
        super(x, y, sizeX, sizeY, maxSpeed, false);
        this.setInvulnerable(false);
        this.setName(this.hashCode() + "");
        this.setStats(stats);
        Circle hitbox = new Circle(x, y + 10, Math.min(sizeX, sizeY)/2.0);
        super.setHitbox(hitbox);
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

    public boolean setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        return this.invulnerable;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

}
