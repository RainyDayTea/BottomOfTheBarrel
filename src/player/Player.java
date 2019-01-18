package player;

import framework.Statistics;
import framework.geom.Circle;
import framework.Character;
import framework.geom.Shape;
import framework.geom.Vector2D;

import java.awt.image.BufferedImage;

public class Player extends Character {
	private BufferedImage[] dodgeRoll;
	private BufferedImage[] standing;
	private BufferedImage[] running;
	private Circle hitbox;
	private int speed;
	private String weapon;

	public Player(double x, double y, int sizeX, int sizeY, Vector2D maxSpeed) {
		super(x, y, sizeX, sizeY, maxSpeed);
	}

	public boolean rolling(int characterState){
		boolean invulnerable = false;
		if (characterState == 2){
			invulnerable = true;
		}
		return invulnerable;
	}
	public boolean Invulnerable(int characterState){
		if (rolling(characterState)){
			this.setInvulnerable(isInvulnerable());
		}
		return isInvulnerable();
	}
}
