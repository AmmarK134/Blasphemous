package utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LoadSave {
	
	// File names for various sprite atlases
	public static final String PLAYER_ATLAS = "cac.png";
	public static final String SKELE_ATLAS = "skeleton.png";
	public static final String LEVEL1_ATLAS = "area.gif";
	public static final String LEVEL2_ATLAS = "area1.png";
	public static final String COVE = "covenant.png";
	public static final String SLOT = "slots.png";
	public static final String ESTUS = "flask1.png";
	public static final String ASHEN = "flask2.png";
	public static final String DEAD = "dedScreen.png";
	public static final String BOSS = "boss1.png";
	public static final String DEADSCREEN = "dead.jpg";
	
	/*
	 * Loads the sprite atlas image from the specified file.
	 * pre fileName The name of the file containing the sprite atlas.
	 * post The loaded sprite atlas as a BufferedImage.
	 */
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
}
