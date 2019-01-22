package map;

import framework.RenderedObject;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;

public class Ground extends RenderedObject {

	public Ground(Room parent, double x, double y, int sizeX, int sizeY) {
		super(parent, x, y, sizeX, sizeY, true, false);
		this.setCollidable(false);
		this.setTexture("Ground");
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D correctedPos = new Vector2D(this.getRenderBox().getCenter()).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
		Vector2D size = this.getRenderBox().size();

		for (int i = 0; i < size.y; i+= texture.getHeight()) {
			for (int j = 0; j < size.x; j+= texture.getWidth()) {
				g.drawImage(texture, (int) correctedPos.x + j, (int) correctedPos.y + i, texture.getWidth(), texture.getHeight(), null);
			}
		}
	}

}
