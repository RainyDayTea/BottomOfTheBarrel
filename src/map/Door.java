package map;

import entity.Player;
import framework.RenderedObject;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Door extends RenderedObject {

	public Door(double x, double y, int sizeX, int sizeY) {
		super(x, y, sizeX, sizeY, true, true);
	}

	@Override
	public void onIntersect(Room room, RenderedObject other) {
		if (this == other) return;
		if (other instanceof Player) {
			// Check which direction the door is in
			ArrayList<RenderedObject> returnedObjects = new ArrayList<>();
			if (this == room.getDoors()[0]) {
				returnedObjects.addAll(room.onExit("north"));
				room.getParent().onExitRoom("north", returnedObjects);
			} else if (this == room.getDoors()[1]) {
				returnedObjects = room.onExit("east");
				room.getParent().onExitRoom("east", returnedObjects);
			} else if (this == room.getDoors()[2]) {
				returnedObjects = room.onExit("south");
				room.getParent().onExitRoom("south", returnedObjects);
			} else if (this == room.getDoors()[3]) {
				returnedObjects = room.onExit("west");
				room.getParent().onExitRoom("west", returnedObjects);
			}
			if (returnedObjects == null) throw new NullPointerException("Door.onIntersect(): Returned objects do not exist.");
		}
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
		Vector2D size = this.getRenderBox().size();
		Color oldColor = g.getColor();
		g.setColor(new Color(0x2f0f00));
		g.fillRect((int) drawPos.x, (int) drawPos.y, (int) size.x, (int) size.y);
		g.setColor(oldColor);
	}
}
