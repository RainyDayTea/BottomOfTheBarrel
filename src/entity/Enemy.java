package entity;

import framework.Character;
import framework.Statistics;
import map.Room;

public class Enemy extends Character {

	public Enemy(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed, Statistics stats) {
		super(parent, x, y, sizeX, sizeY, maxSpeed, stats);
	}
}
