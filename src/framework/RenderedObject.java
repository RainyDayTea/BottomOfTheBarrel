package framework;

import framework.geom.Circle;
import framework.geom.Rectangle;
import framework.geom.Shape;
import framework.geom.Vector2D;
import game.GameAreaPanel;
import game.GameFrame;
import map.Room;

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
public class RenderedObject implements Renderable, Collidable {
	private Room parent;
	// The texture of the object. It's recommended to set this as a pointer
	// instead of storing the BufferedImage directly.
	protected BufferedImage texture;
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
	private long lastCollision;

	/**
	 * Constructs a RenderedObject centered at (x, y) with the dimensions of the texture.
	 * @param parent
	 * @param y The y-coordinate of the center of the object.
	 * @param visible Sets if the object is visible by default.
	 * @param x The x-coordinate of the center of the object.
	 */
	public RenderedObject(Room parent, double y, boolean visible, boolean collidable, double x) {
		Vector2D p0 = new Vector2D(x - texture.getWidth()/2.0, y - texture.getHeight()/2.0);
		Vector2D p1 = new Vector2D(p0.x + texture.getWidth(), p0.y + texture.getHeight());
		this.parent = parent;
		this.renderBox = new Rectangle(p0, p1);
		this.visible = visible;
		this.collidable = collidable;
		this.hitbox = new Rectangle(p0, p1);
		this.lastCollision = System.currentTimeMillis();
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
	public RenderedObject(Room parent, double x, double y, int sizeX, int sizeY, boolean visible, boolean collidable) {
		this.parent = parent;
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
	public Vector2D getPosition() {
		if (hitbox != null) {
			return hitbox.getCenter();
		} else if (renderBox != null) {
			return renderBox.getCenter();
		} else {
			return new Vector2D();
		}
	}

	/**
	 * Moves the hitbox's center to (x, y). The render box is drawn in the same relative position
	 * as the hitbox.
	 * @param x The new x-coordinate.
	 * @param y The new y-coordinate.
	 */
	public void setPosition(double x, double y) {

		Vector2D sizeRenderBox = renderBox.size();
		Vector2D sizeHitbox = null;
		Vector2D hitboxCenter = new Vector2D(hitbox.getCenter());
		Vector2D relativePos1 = new Vector2D(hitboxCenter).sub(hitbox.pos);
		Vector2D relativePosRbox1 = new Vector2D(hitboxCenter).sub(renderBox.pos);


		if (hitbox instanceof Rectangle) {
			sizeHitbox = ((Rectangle) hitbox).size();
			hitbox.pos.set(x, y).sub(relativePos1);
			((Rectangle) hitbox).pos2.set(hitbox.pos).add(sizeHitbox);
		} else if (hitbox instanceof Circle) {
			hitbox.pos.set(x, y).add(relativePos1);
		}

		renderBox.pos.set(x, y).sub(relativePosRbox1);
		renderBox.pos2.set(renderBox.pos).add(sizeRenderBox);

	}

	/**
	 * This method is called to draw the object.
	 * @param g The JPanel's graphics object.
	 */
	public void draw(Graphics g, Vector2D offset) {
		// Prevents drawing if the object is invisible.
		if (!this.isVisible()) return;

		if (this.texture == null || GameAreaPanel.SHOW_DEBUG) {
			Color oldColor = g.getColor();
			// Gets the screen position from world position
			Vector2D drawPos;
			Vector2D sizeRenderBox = renderBox.size();
			g.setColor(Color.BLUE);
			drawPos = new Vector2D(this.renderBox.pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
			g.drawRect((int) drawPos.x, (int) drawPos.y, (int) sizeRenderBox.x, (int) sizeRenderBox.y);
			g.setColor(Color.CYAN);
			if (this.hitbox instanceof Rectangle) {
				drawPos = new Vector2D(this.hitbox.pos).add(-offset.x + GameFrame.WIDTH / 2, -offset.y + GameFrame.HEIGHT / 2);
				Vector2D sizeHitbox = ((Rectangle) this.hitbox).size();
				g.drawRect((int) drawPos.x, (int) drawPos.y, (int) sizeHitbox.x, (int) sizeHitbox.y);
			} else if (this.hitbox instanceof Circle) {
				int radius = (int) Math.round(((Circle) this.hitbox).radius);
				drawPos = new Vector2D(this.hitbox.pos).add(-offset.x + GameFrame.WIDTH / 2 - radius, -offset.y + GameFrame.HEIGHT / 2 - radius);
				g.drawOval((int) drawPos.x, (int) drawPos.y, radius * 2, radius * 2);
			}
			g.setColor(oldColor);
		}
	}

	/**
	 * Checks if the current object exceeds a rectangular region.
	 * @param rect The specified rectangular region.
	 * @return True if any of the object exceeds the boundary, false otherwise.
	 */
	@Override
	public boolean exceedsBoundary(Rectangle rect) {
		// Objects without a hitbox can exceed boundary.
		if (this.hitbox == null) return false;
		// Only check if it is a movable objects. Static objects can exceed boundaries.
		if (this instanceof MovableObject) {
			Rectangle bounds = new Rectangle(rect.pos.x, rect.pos.y, rect.pos2.x, rect.pos2.y);
			MovableObject movObj = (MovableObject) this;
			Vector2D objSize = this.hitbox.getBoundingBox().size();
			bounds.pos.add(objSize.x/2, objSize.y/2);
			bounds.pos2.sub(objSize.x/2, objSize.y/2);
			Vector2D pos = new Vector2D(movObj.getPosition());
			// West wall
			if (pos.x < bounds.pos.x) {
				return true;
			}
			// East wall
			if (pos.x > bounds.pos2.x) {
				return true;
			}
			// North wall
			if (pos.y < bounds.pos.y) {
				return true;
			}
			// South wall
			if (pos.y > bounds.pos2.y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method called upon intersecting another object.
	 * @param other Another specified object.
	 */
	@Override
	public void onIntersect(Room room, RenderedObject other) {
		if (this == other) return;
		if (this.hitbox instanceof Circle && other.hitbox instanceof Circle) {
			Manifold mf = new Manifold();
			mf.a = this;
			mf.b = other;
			if (mf.a != null && mf.b != null) {
				// Don't worry if the objects are not collidable, this is handled in the method
				if (Collisions.circVsCirc(mf)) {
					Collisions.resolve(mf.a, mf.b, mf.normal);
				}
			}
		}
	}

	/**
	 * Method called when exceeding a boundary.
	 * @param rect The specified rectangular region.
	 */
	@Override
	public void onIntersectBoundary(Room room, Rectangle rect) {
		if (this instanceof MovableObject) {
			Rectangle bounds = new Rectangle(rect.pos.x, rect.pos.y, rect.pos2.x, rect.pos2.y);
			MovableObject movObj = (MovableObject) this;
			Vector2D objSize = this.hitbox.getBoundingBox().size();
			bounds.pos.add(objSize.x/2, objSize.y/2);
			bounds.pos2.sub(objSize.x/2, objSize.y/2);
			Vector2D pos = movObj.getPosition();
			Vector2D speed = new Vector2D(movObj.getSpeed());
			// West wall
			pos.x = Math.max(bounds.pos.x, pos.x);
			if (pos.x <= bounds.pos.x && speed.x < 0) {
				speed.add(-2 * speed.x, 0);
			}
			// East wall
			pos.x = Math.min(bounds.pos2.x, pos.x);
			if (pos.x >= bounds.pos2.x && speed.x > 0) {
				speed.add(-2 * speed.x, 0);
			}
			// North wall
			pos.y = Math.max(bounds.pos.y, pos.y);
			if (pos.y <= bounds.pos.y && speed.y < 0) {
				speed.add(0, -2 * speed.y);
			}
			// South wall
			pos.y = Math.min(bounds.pos2.y, pos.y);
			if (pos.y >= bounds.pos2.y && speed.y > 0) {
				speed.add(0, -2 * speed.y);
			}
			movObj.setSpeed(speed);
			movObj.setPosition(pos.x, pos.y);
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
	 * Sets the current object's texture to one in ImageLoader.
	 * @param key The key of the texture in ImageLoader's HashMap.
	 */
	public void setTexture(String key) {
		this.texture = ImageLoader.get(key);
	}

	/**
	 * Sets the object's texture directly from a BufferedImage.
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
