package map;

import entity.Player;
import framework.RenderedObject;

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
}
