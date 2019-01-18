package framework;

import framework.geom.Rectangle;
import framework.geom.Vector2D;

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
public class RenderedObject {
	// The texture of the object. It's recommended to set this as a pointer
	// instead of storing the BufferedImage directly.
	private static BufferedImage texture;
	// The central position of the object.
	private Vector2D position;
	// The rectangle which the texture is drawn in.
	private Rectangle renderBox;
	// Toggles if the texture is drawn.
	private boolean isVisible;
	// The rotation of the texture, in radians.
	// Specifically, this is the angle measured from standard position.
	// When the texture is drawn, the rotation will occur about the center of the render box.
	private double rotation;

	/**
	 * Constructs a RenderedObject centered at (x, y).
	 * @param x The x-coordinate of the center of the object.
	 * @param y The y-coordinate of the center of the object.
	 * @param isVisible Sets if the object is visible by default.
	 */
	public RenderedObject(double x, double y, boolean isVisible) {
		Vector2D p0 = new Vector2D(x - texture.getWidth()/2, y - texture.getHeight()/2);
		Vector2D p1 = new Vector2D(p0.x + texture.getWidth(), p0.y + texture.getHeight());
		this.renderBox = new Rectangle(p0, p1);
		this.position = new Vector2D(x, y);
		this.isVisible = isVisible;
	}

	public RenderedObject(double x, double y, int sizeX, int sizeY, boolean isVisible) {
		Vector2D p0 = new Vector2D(x - sizeX/2.0, y - sizeY/2.0);
		Vector2D p1 = new Vector2D(p0.x + sizeX/2.0, p0.y + sizeY/2.0);
		this.renderBox = new Rectangle(p0, p1);
		this.position = new Vector2D(x, y);
		this.isVisible = isVisible;
	}

	/**
	 * This method is called somewhere in paintComponent() to draw the object.
	 * @param g The JPanel's graphics object.
	 */
	public void draw(Graphics g) {
		// Prevents drawing if the object is invisible.
		if (!this.isVisible) return;

		if (texture == null) {
			Vector2D size = renderBox.size();
			g.drawRect((int) Math.round(renderBox.pos.x), (int) Math.round(renderBox.pos.y), (int) Math.round(size.x), (int) Math.round(size.y));
		} else {

		}
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
		return isVisible;
	}

	/**
	 * Sets the object's visibility.
	 * @param visible Set to true if the object is visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		isVisible = visible;
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
	 * Gets the object's position.
	 * @return A 2D vector containing the object's coordinates.
	 */
	public Vector2D getPosition() { return position; }

	/**
	 * Sets the object's position.
	 * @param x The new x-coordinate.
	 * @param y The new y-coordinate.
	 */
	public void setPosition(double x, double y) {
		Vector2D size = new Vector2D(renderBox.size());
		this.renderBox.pos = new Vector2D(x - size.x/2, y - size.y/2);
		this.renderBox.pos2 = new Vector2D(x + size.x/2, y + size.y/2);
	}

}
