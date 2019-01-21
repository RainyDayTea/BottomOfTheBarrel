package framework;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Makes animating sprites much easier (in theory) and in a more organized manner.
 *
 * @author Jake Zhao
 */
public class ImageAnimator {
	private BufferedImage[][] sprites;

	private int row;
	private int col;

	/**
	 * Constructs a new animator with a given sprite sheet and sprite dimensions.
	 * @param spriteSheet The sprite sheet image.
	 * @param spriteWidth The width of a sprite.
	 * @param spriteHeight The height of a sprite.
	 */
	public ImageAnimator(BufferedImage spriteSheet, int rows, int cols, int spriteWidth, int spriteHeight) {
		this.row = 0;
		this.col = 0;
		this.sprites = new BufferedImage[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[i][j] = spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
			}
		}
 	}

	/**
	 * Sets the position of the animator.
	 * @param row The row of the animator.
	 * @param col The column of the animator.
	 * @return
	 */
	public void setPos(int row, int col) {
		if (row >= 0 && row < sprites.length && col >= 0 && col < sprites[0].length) {
			this.row = row;
			this.col = col;
		}
	}

	public BufferedImage getImage() {
		return sprites[row][col];
	}

	/**
	 * Returns the image at the current location and moves to the next image to the right, wrapping around if
	 * the last image is reached.
	 * @return The image at the current location.
	 */
	public BufferedImage nextImage() {
		col = (col + 1) % sprites[0].length;
		return sprites[row][col];
	}

	public int getRow() { return row; }

	public int getCol() { return col; }
}
