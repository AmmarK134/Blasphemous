package character;

import static utility.Constants.SkeleConstants.WALKS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected float x, y; // The x and y coordinates of the entity
	protected int width, height; // The width and height of the entity
	protected Rectangle hitbox; // The hitbox of the entity
	BufferedImage[][] skele; // Skeletal animation frames
	private int tick, index, speed = 25; // Animation variables
	private int skeleAction = WALKS; // Current skeletal animation action

	/*
	 * Entity constructor.
	 * pre: float x, float y, int width, int height
	 * post: N/A
	 */
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		initHitbox(); // Initialize the hitbox
	}

	/*
	 * Draws the hitbox of the entity.
	 * pre: Graphics g
	 * post: N/A
	 */
	protected void drawHitbox(Graphics g) {
		// Draw the hitbox as a green rectangle
		g.setColor(Color.GREEN);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

	/*
	 * Initializes the hitbox of the entity.
	 * pre: N/A
	 * post: N/A
	 */
	private void initHitbox() {
		hitbox = new Rectangle((int) x, (int) y, width, height);
	}

	/*
	 * Updates the position of the hitbox to match the entity's current position.
	 * pre: N/A
	 * post: N/A
	 */
	protected void updateHitbox() {
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	/*
	 * Retrieves the hitbox of the entity.
	 * pre: N/A
	 * post: returns the hitbox of the entity
	 */
	public Rectangle getHitbox() {
		return hitbox;
	}
}
