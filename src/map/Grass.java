package map;

import framework.RenderedObject;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;

public class Grass extends RenderedObject {

	public Grass(double x, double y, int sizeX, int sizeY) {
		super(x, y, sizeX, sizeY, true, false);
		double rng = Math.random();
		if (rng > 0.5) {
			this.setTexture("Grass1");
		} else {
			this.setTexture("Grass2");
		}
		this.setCollidable(false);
	}

	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
		g.drawImage(texture, (int) drawPos.x, (int) drawPos.y, texture.getWidth(), texture.getHeight(), null);
	}
}
