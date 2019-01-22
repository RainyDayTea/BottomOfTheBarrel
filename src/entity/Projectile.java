package entity;

import framework.Character;
import framework.MovableObject;
import framework.RenderedObject;
import framework.Statistics;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import game.GameFrame;
import map.Room;

import java.awt.*;

/**
 *
 */
public class Projectile extends MovableObject {
	// The character that the projectile originated from.
	boolean friendly;
	// The stats of the projectile
	Statistics stats;

	public Projectile(Room parent, double x, double y, int radius, Vector2D speed, Statistics stats, boolean friendly) {
		super(parent, x, y, radius, radius, 20, false);
		this.friendly = friendly;
		this.stats = stats;
		this.setSpeed(speed);
		this.setTexture("Bullet");
	}

	@Override
	public void onIntersectBoundary(Room room, Rectangle rect) {
		double rng = Math.random();
		if (rng < 0.33) {
			room.remove(this);
		}
	}

	@Override
	public void onIntersect(Room room, RenderedObject other) {
		//System.out.println(other);
		if (this == other) return;
		// If the other object matches projectile type (Friendly), disregard collision
		if (other instanceof Character) {
			if (this.friendly && other instanceof Player) return;
			if ((!this.friendly) && other instanceof Enemy) return;
			Character character = (Character) other;

			// Don't take damage if character is invulnerable
			if (!character.isInvulnerable()) {
				character.getStats().takeDamage(this.stats.getDamage());
			}
		}
		room.remove(this);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
		g.drawImage(texture, (int) drawPos.x, (int) drawPos.y, texture.getWidth(), texture.getHeight(), null);
	}
}
