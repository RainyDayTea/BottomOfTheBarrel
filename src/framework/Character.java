package framework;

import framework.geom.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Character extends RenderedObject implements Collidable {

    private String name;
    private Shape hitbox;
    private Vector2D speed = new Vector2D();
    private boolean invulnerable;
    private int vectorDirection;
    private Statistics stats = new Statistics(0,0,0);
    public static final BufferedImage defaultTexture = null;

    public Character(String name, Vector2D position, Vector2D speed, Statistics stats, Shape hitbox) {
        super(defaultTexture, new Rectangle(position, 100, 100), true);
        this.setInvulnerable(false);
        this.setName(name);
        this.setSpeed(speed);
        this.setVectorDirection(vectorDirection);
        this.setStats(stats);
        this.setHitbox(hitbox);
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Shape getHitbox() { return hitbox; }

    public void setHitbox(Shape hitbox) { this.hitbox = hitbox; }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        return this.invulnerable;
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
