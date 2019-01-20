package framework;

import framework.geom.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Character extends MovableObject {

    private String name;
    private Shape hitbox;
    private boolean invulnerable;
    private Statistics stats;
    public static final BufferedImage defaultTexture = null;

    public Character(double x, double y, int sizeX, int sizeY, int maxSpeed) {
        super(x, y, sizeX, sizeY, maxSpeed, true);
        this.setInvulnerable(false);
        this.setName(this.hashCode() + "");
        this.setStats(stats);
        this.setHitbox(this.getRenderBox());
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Shape getHitbox() { return hitbox; }

    public void setHitbox(Shape hitbox) { this.hitbox = hitbox; }

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
