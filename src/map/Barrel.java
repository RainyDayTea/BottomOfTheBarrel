package map;

import entity.Projectile;
import framework.MovableObject;
import framework.RenderedObject;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;

public class Barrel extends MovableObject {

	public Barrel(Room parent, double x, double y, int maxSpeed) {
		super(parent, x, y, 26, 30, maxSpeed, true);
		this.setTexture("Barrel");
		this.setHasFriction(true);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
		g.drawImage(texture, (int) drawPos.x, (int) drawPos.y, texture.getWidth(), texture.getHeight(), null);
		super.draw(g, offset);
	}

	@Override
	public void onIntersect(Room room, RenderedObject other) {
		if (other instanceof Projectile) {
			room.remove(this);
		}
	}
}
