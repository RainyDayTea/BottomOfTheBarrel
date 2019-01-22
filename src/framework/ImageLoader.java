package framework;

import entity.Player;
import map.Ground;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class for loading images. This is used as a central location for all the code to load images,
 * rather than having them be spread out over every file.
 *
 * @author Jake Zhao
 */
public class ImageLoader {

	private static HashMap<String, BufferedImage> map = new HashMap<>();

	/**
	 * Loads all of the textures.
	 */
	public static void loadAll() {
		try {
			// Load textures into the hash map
			map.put("Player", ImageIO.read(new File("img/cowboy_spritesheet.png")));
			map.put("Ground", ImageIO.read(new File("img/ground.png")));
			map.put("Grass1", ImageIO.read(new File("img/grass1.png")));
			map.put("Grass2", ImageIO.read(new File("img/grass2.png")));
		} catch(IOException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Retrieves a texture from the HashMap.
	 * @param key The key to query.
	 * @return A BufferedImage containing the texture, or null if not found/
	 */
	public static BufferedImage get(String key) {
		return map.get(key);
	}
}
