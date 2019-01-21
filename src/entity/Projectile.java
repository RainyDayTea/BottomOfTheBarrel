package entity;

import framework.MovableObject;
import framework.Statistics;
import framework.geom.Rectangle;
import framework.geom.Vector2D;
import map.Room;

/**
 *
 */
public class Projectile extends MovableObject {
	// The character that the projectile originated from.
	Character caster;
	// The stats of the projectile
	Statistics stats;

	public Projectile(double x, double y, int radius, Character caster, Statistics stats, Vector2D speed) {
		super(x, y, radius, radius, (int) speed.getMagnitude(), true);
		this.caster = caster;
		this.stats = stats;
		this.setSpeed(speed);
	}

	@Override
	public void onIntersectBoundary(Room room, Rectangle rect) {
		room.remove(this);
	}
}
