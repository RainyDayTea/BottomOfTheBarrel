package entity;

import framework.Character;
import framework.ImageAnimator;
import framework.Statistics;
import framework.geom.Vector2D;
import game.GameFrame;
import map.Room;

import java.awt.*;
import java.util.HashSet;

public class Enemy extends Character {

	private long lastMove;
	private long lastAttack;
	private long attackDelay;
	private long moveDelay;

	public Enemy(Room parent, double x, double y, int sizeX, int sizeY, int maxSpeed, Statistics stats) {
		super(parent, x, y, sizeX, sizeY, maxSpeed, stats);
		this.lastAttack = System.currentTimeMillis();
		this.lastMove = System.currentTimeMillis();
		this.attackDelay = (long) 300 + (long) (Math.random() * 300);
		this.moveDelay = (long) 1000 + (long) (Math.random() * 2000);
		this.setTexture("Enemy");
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D drawPos = new Vector2D(this.getRenderBox().pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
		g.drawImage(texture, (int) drawPos.x, (int) drawPos.y, 64, 64,null);
		super.draw(g, offset);
	}

	public long getLastMove() {
		return lastMove;
	}

	public void setLastMove(long lastMove) {
		this.lastMove = lastMove;
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public long getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(long attackDelay) {
		this.attackDelay = attackDelay;
	}

	public long getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(long moveDelay) {
		this.moveDelay = moveDelay;
	}
}
