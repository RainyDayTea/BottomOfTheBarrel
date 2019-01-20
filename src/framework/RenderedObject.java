package framework;

import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Shape;
import framework.geom.Vector2D;
import game.GameFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The superclass for all renderable objects. All objects that
 * want to be rendered in the game must be a descendant of this class.
 *
 * A RenderedObject consists of a texture and a render box. The render box
 * is a rectangular shape that the texture will attempt to fit into when
 * the object is drawn.
 *
 *
 *
 * @author Jake Zhao
 */
public class RenderedObject implements Renderable {
	// The texture of the object. It's recommended to set this as a pointer
	// instead of storing the BufferedImage directly.
	private static BufferedImage texture;
	// The rectangle which the texture is drawn in.
	private Rectangle renderBox;
	// Toggles if the texture is drawn.
	private boolean visible;
	// The rotation of the texture, in radians.
	// Specifically, this is the angle measured from standard position.
	// When the texture is drawn, the rotation will occur about the center of the render box.
	private double rotation;

	private boolean collidable;
	private Shape hitbox;

	/**
	 * Constructs a RenderedObject centered at (x, y) with the dimensions of the texture.
	 * @param x The x-coordinate of the center of the object.
	 * @param y The y-coordinate of the center of the object.
	 * @param visible Sets if the object is visible by default.
	 */
	public RenderedObject(double x, double y, boolean visible, boolean collidable) {
		Vector2D p0 = new Vector2D(x - texture.getWidth()/2.0, y - texture.getHeight()/2.0);
		Vector2D p1 = new Vector2D(p0.x + texture.getWidth(), p0.y + texture.getHeight());
		this.renderBox = new Rectangle(p0, p1);
		this.visible = visible;
		this.collidable = collidable;
		this.hitbox = new Rectangle(p0, p1);
	}

	/**
	 * Constructs a RenderedObject centered at (x, y) with dimensions (sizeX, sizeY).
	 * @param x The x-coordinate of the center of the object.
	 * @param y The y-coordinate of the center of the object.
	 * @param sizeX The width of the object.
	 * @param sizeY The height of the object.
	 * @param visible Sets if the object is visible by default.
	 * @param collidable Sets if the object is collidable by default.
	 */
	public RenderedObject(double x, double y, int sizeX, int sizeY, boolean visible, boolean collidable) {
		Vector2D p0 = new Vector2D(x - sizeX/2.0, y - sizeY/2.0);
		Vector2D p1 = new Vector2D(x + sizeX/2.0, y + sizeY/2.0);
		this.renderBox = new Rectangle(p0, p1);
		this.visible = visible;
		this.collidable = collidable;
		this.hitbox = new Rectangle(p0, p1);
	}
	/**
	 * Gets the object's position.
	 * @return A 2D vector containing the object's coordinates.
	 */
	public Vector2D getPosition() { return hitbox.getCenter(); }

	/**
	 * Moves the hitbox's center to (x, y). The render box is drawn in the same relative position
	 * as the hitbox.
	 * @param x The new x-coordinate.
	 * @param y The new y-coordinate.
	 */
	public void setPosition(double x, double y) {

		Vector2D hitboxCenter = new Vector2D(hitbox.getCenter());
		Vector2D relativePos1 = new Vector2D(hitboxCenter).sub(hitbox.pos);
		Vector2D relativePos2 = new Vector2D(hitboxCenter).sub(((Rectangle) hitbox).pos2);
		Vector2D relativePosRbox1 = new Vector2D(hitboxCenter).sub(renderBox.pos);
		Vector2D relativePosRbox2 = new Vector2D(hitboxCenter).sub(renderBox.pos2);
		hitbox.pos.set(x, y);
		hitbox.pos.add(relativePos1);
		System.out.println(hitboxCenter.x + ", " + hitboxCenter.y);

		if (hitbox instanceof Rectangle) {
			Rectangle temp = (Rectangle) hitbox;
			Vector2D relativePos2 = new Vector2D(hitboxCenter).sub(temp.pos2);
			temp.pos2.set(x, y);
			temp.pos2.add(relativePos2);
		}


		renderBox.pos.set(x, y);
		renderBox.pos.add(relativePosRbox1);

		renderBox.pos2.set(x, y);
		renderBox.pos2.add(relativePosRbox2);

	}

	/**
	 * This method is called to draw the object.
	 * @param g The JPanel's graphics object.
	 */
	public void draw(Graphics g, Vector2D offset) {
		// Prevents drawing if the object is invisible.
		if (!this.isVisible()) return;

		// Gets the screen position from the world position
		Vector2D drawPos;
		Vector2D sizeRenderBox = renderBox.size();
		if (this.hitbox instanceof Rectangle) {
			drawPos = new Vector2D(this.hitbox.pos).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
			Vector2D sizeHitbox = ((Rectangle) this.hitbox).size();
			//g.drawRect((int) drawPos.x, (int) drawPos.y, (int) sizeHitbox.x, (int) sizeHitbox.y);
		} else if (this.hitbox instanceof Circle) {
			int radius = (int) Math.round(((Circle) this.hitbox).radius);
			drawPos = new Vector2D(this.hitbox.pos).add(-offset.x + GameFrame.WIDTH/2 - radius, -offset.y + GameFrame.HEIGHT/2 - radius);
			g.drawOval((int) drawPos.x, (int) drawPos.y, radius*2, radius*2);
		}
		drawPos = new Vector2D(this.renderBox.pos).add(-offset.x + GameFrame.WIDTH/2, -offset.y + GameFrame.HEIGHT/2);
		g.drawRect((int) drawPos.x, (int) drawPos.y, (int) sizeRenderBox.x, (int) sizeRenderBox.y);
		//g.setColor(oldColor);
	}

	/**
	 * Gets the object's render box.
	 * @return The render box.
	 */
	public Rectangle getRenderBox() {
		return renderBox;
	}

	/**
	 * Sets the object's render box.
	 * @param renderBox The new render box, an instance of Rectangle.
	 */
	public void setRenderBox(Rectangle renderBox) {
		this.renderBox = renderBox;
	}

	/**
	 * Gets the object's texture.
	 * @return The texture.
	 */
	public BufferedImage getTexture() {
		return texture;
	}

	/**
	 * Sets the object's texture.
	 * @param texture The new texture.
	 */
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	/**
	 * Returns the object's visibility.
	 * @return true if the object is visible, false otherwise.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the object's visibility.
	 * @param visible Set to true if the object is visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Gets the object's rotation.
	 * @return The angle to be rotated, in radians from standard position.
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Sets the object's rotation.
	 * @param rotation The new angle, in radians.
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	/**
	 * Checks if the object is collidable.
	 * @return True if the object is collidable, false otherwise.
	 */
	public boolean isCollidable() {
		return collidable;
	}

	/**
	 * Sets the value of collidable.
	 * @param collidable The new value.
	 */
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	/**
	 * Gets the object's hitbox.
	 * @return The object's hitbox.
	 */
	public Shape getHitbox() {
		return hitbox;
	}

	/**
	 * Sets the object's hitbox.
	 * @param hitbox The new hitbox.
	 */
	public void setHitbox(Shape hitbox) {
		this.hitbox = hitbox;
	}
}
