package framework;

import framework.geom.Vector2D;
import framework.geom.Rectangle;

import java.awt.image.BufferedImage;

public abstract class RenderedObject {

	private BufferedImage texture;
	private Rectangle renderBox;
	private boolean isVisible;

	public RenderedObject(){

	}

	public RenderedObject(BufferedImage texture, Rectangle renderBox, boolean isVisible) {
		this.renderBox = renderBox;
		this.texture = texture;
		this.isVisible = isVisible;
	}

	public Rectangle getRenderBox() {
		return renderBox;
	}

	public void setRenderBox(Rectangle renderBox) {
		this.renderBox = renderBox;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
		renderBox = new Rectangle(renderBox.pos, texture.getWidth(), texture.getHeight());
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}
