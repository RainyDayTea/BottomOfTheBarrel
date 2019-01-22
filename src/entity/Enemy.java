package entity;

import framework.Character;
import map.Room;

public class Enemy extends Character {

	public Enemy(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed) {
		super(parent, x, y, sizeX, sizeY, maxSpeed);
	}
}
