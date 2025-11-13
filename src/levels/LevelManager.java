package levels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import character.Player;
import main.Game;
import utility.LoadSave;
import utility.Sound;

public class LevelManager {

	private Game game;
	private BufferedImage levelSprite, covenant, slot, estus, ashen, levelSprite1;
	private int currentLevelWidth;
	Sound musicPlayer = new Sound();
	
	/*
	 * Constructs a LevelManager object.
	 * 
	 * pre: Game object must be provided
	 * post: Initializes the LevelManager with default values and loads necessary resources.
	 */
	public LevelManager(Game game) {
		this.game = game;
		levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL1_ATLAS); 
		levelSprite1 = LoadSave.GetSpriteAtlas(LoadSave.LEVEL2_ATLAS); 
		covenant = LoadSave.GetSpriteAtlas(LoadSave.COVE); 
		slot = LoadSave.GetSpriteAtlas(LoadSave.SLOT);
		estus = LoadSave.GetSpriteAtlas(LoadSave.ESTUS);
		ashen = LoadSave.GetSpriteAtlas(LoadSave.ASHEN);
		currentLevelWidth = 1600;
	}
	
	/*
	 * Draws the level elements on the screen.
	 * 
	 * pre: Graphics object must be provided
	 * post: Renders the level elements on the screen.
	 */
	public void draw(Graphics g) {
		g.drawImage(levelSprite, 0, 0, 1600, 800, null);
		g.drawImage(covenant, 5, 5, 80, 80, null);
		g.drawImage(slot, 1270, 20, 250, 240, null);
		g.drawImage(estus, 1335, 135, 110, 110, null);
		g.drawImage(ashen, 1430, 195, 60, 60, null);
	}
	
	/*
	 * Updates the level and player progression.
	 * 
	 * pre: None
	 * post: Checks if the player has reached the right side of the current area and loads the next area if necessary.
	 *       Updates the level sprite, current level width, and player properties accordingly.
	 */
	public void update() {
		Player player = game.getPlayer();
		int playerX = player.getX(); // Get the player's X position
	
		if (playerX > currentLevelWidth) {
			// Player has reached the right side of the current area
			// Load the next area here
			levelSprite = levelSprite1; // Set the next area's sprite
			currentLevelWidth += 1600; // Update the width of the current area
			musicPlayer.playMusic("BossTheme.wav");
			for (int i = 0; i < 2; i++) {
				player.setSkeleX(i);
			}
			if (player.getbossDead() || player.bossHealth <= 0) {
				musicPlayer.stopMusic();
			}
	
			// Load any other necessary resources for the next area
		}
	}
}
