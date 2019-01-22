package framework;

import framework.geom.*;
import game.GameFrame;
import map.Room;

import java.awt.*;

public abstract class Character extends MovableObject {

    private String name;
    private boolean invulnerable;
    private Statistics stats;

    public Character(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed, Statistics stats) {
        super(parent, x, y, sizeX, sizeY, maxSpeed, false);
        this.setInvulnerable(false);
        this.setName(this.hashCode() + "");
        this.setStats(stats);
        this.setHasFriction(true);
        Circle hitbox = new Circle(x, y + 10, Math.min(sizeX, sizeY)/2.0);
        super.setHitbox(hitbox);
    }

    /**
     * Overriden to draw a health bar.
     * @param g The JPanel's graphics object.
     * @param offset
     */
    @Override
    public void draw(Graphics g, Vector2D offset) {
        if (this.stats != null ) {
            Vector2D healthBarPos = new Vector2D(this.getRenderBox().pos.x, this.getRenderBox().pos.y - 12);
            Color oldColor = g.getColor();
            healthBarPos.add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
            double percentHealthFull = 0;
            if (this.getStats().getMaxHP() != 0) {
                percentHealthFull = Math.max(this.stats.getCurrHP(), 0) / (double) this.getStats().getMaxHP();
            }
            int healthBarWidth = (int) this.getRenderBox().size().x;
            // Draw red bar
            g.setColor(Color.RED);
            g.fillRect((int) healthBarPos.x, (int) healthBarPos.y, healthBarWidth, 5);
            // Draw green bar
            g.setColor(Color.GREEN);
            g.fillRect((int) healthBarPos.x, (int) healthBarPos.y, (int) Math.ceil(healthBarWidth * percentHealthFull), 5);
            g.setColor(oldColor);
        }
        super.draw(g, offset);
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
