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
public abstract class RenderedObject {
	// The texture of the object. It's recommended to set this as a pointer
	// instead of storing the BufferedImage directly.
	private BufferedImage texture;
	// The rectangle which the texture draws in.
	private Rectangle renderBox;
	// Toggles if the texture is drawn.
	private boolean isVisible;
	// The rotation of the texture, in radians.
	// Specifically, this is the angle measured from standard position.
	// When the texture is drawn, the rotation will occur about the center of the render box.
	private double rotation;

	/**
	 * Constructs a RenderedObject with a given texture and a render box set to
	 * the dimensions of the image.
	 * @param texture The inputted texture.
	 * @param isVisible Sets if the object is visible by default.
	 */
	public RenderedObject(BufferedImage texture, boolean isVisible) {
		this.texture = texture;
		this.renderBox = new Rectangle(renderBox.pos, texture.getWidth(), texture.getHeight());
		this.isVisible = isVisible;
	}

	/**
	 * Constructs a RenderedObject with a given texture and render box.
	 * @param texture The inputted texture.
	 * @param renderBox The inputted render box.
	 * @param isVisible Sets if the object is visible by default.
	 */
	public RenderedObject(BufferedImage texture, Rectangle renderBox, boolean isVisible) {
		this.texture = texture;
		this.renderBox = renderBox;
		this.isVisible = isVisible;
	}

	/**
	 *
	 * @param g
	 */
	public void draw(Graphics g) {
		if (texture == null) {
			Vector2D size = renderBox.size();
			g.drawRect((int) renderBox.pos.x, (int) renderBox.pos.y, (int) size.x, (int) size.y);
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
}
