package character;

import static utility.Constants.Directions.*;
import static utility.Constants.PlayerConstants.GetSpriteAmount;
import static utility.Constants.SkeleConstants.GetSkeleAmount;
import static utility.Constants.SkeleConstants.*;
import static utility.Constants.PlayerConstants.*;
import static utility.Constants.BossConstants.*;
import static utility.LoadSave.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import levels.Dead;
import main.Game;
import utility.LoadSave;
import utility.Sound;

public class Player extends Entity {
	
	private BufferedImage[][] ani;  // Animation frames for the player
	private int aniTick, aniIndex, aniSpeed = 25;  // Animation variables for the player
	private int playerAction = IDLE;  // Current action of the player character
	private boolean moving = false;  // Flag indicating if the player is moving
	private boolean attacking1 = false;  // Flag indicating if the player is performing attack 1
	private boolean attacking2 = false;  // Flag indicating if the player is performing attack 2
	private boolean die = false;  // Flag indicating if the player is dead
	private boolean block = false;  // Flag indicating if the player is blocking
	private boolean heal = false;  // Flag indicating if the player is healing
	private boolean hit = false;  // Flag indicating if the player is hit
	private boolean left, up, right, down;  // Flags indicating movement direction
	private float playerSpeed = 2.0f;  // Speed of the player character
	private float[] skeleX = { 1000, 700 };  // X positions of skeleton enemies
	private float[] skeleF = { 500, 550 };  // Y positions of skeleton enemies
	private float[] skeleSpeed = { -0.5f, -0.5f };  // Speeds of skeleton enemies
	private int healthMax = 250;  // Maximum health of the player character
	private static int healthP = 1250;  // Current health of the player character
	private int playerFP = 125;  // Player's FP (focus points)

	int estus = 3;  // Number of estus flasks available to the player
	private int damageCooldown = 180;  // Cooldown period in ticks for taking damage
	private int damageTimer = 0;  // Timer to track the cooldown period after taking damage
	private int[] skeletonHealth = { 3, 3 };  // Health of skeleton enemies
	private boolean[] isSkeletonDead = { false, false };  // Flags indicating if skeleton enemies are dead
	private int[] skeletonHits = { 0, 0 };  // Number of hits received by skeleton enemies

	// Skeleton variables
	BufferedImage[][] skele;  // Animation frames for skeleton enemies
	private int[] tick = { 0, 0 };  // Ticks for animation frames of skeleton enemies
	private int[] index = { 0, 0 };  // Current index of animation frame for skeleton enemies
	private int speed = 25;  // Animation speed for skeleton enemies
	private int[] skeleAction = { WALKS, WALKS };  // Current action of skeleton enemies

	// Boss variables
	private BufferedImage[][] boss;  // Animation frames for the boss
	private int bossTick, bossIndex, bossSpeed = 30;  // Animation variables for the boss
	private int bossAction = WALK_B;  // Current action of the boss
	private boolean stage = false;  // Flag indicating if the boss fight is in progress
	private float bossX = 1200, bossY = 470;  // Boss position
	private float BSpeed = -0.3f;  // Speed of the boss
	public int bossHealth = 1400;  // Health of the boss
	private boolean bossDead = false;  // Flag indicating if the boss is dead


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        // TODO Auto-generated constructor stub
    }

    public void update() {
        // Perform other updates for non-death states
        updatePos();
        for (int i = 0; i < 2; i++) {
            skelePos(i);
        }
        updateAnimationTick();
        setAnimation();
        updateHitbox();
        damageTaken();
        damageGiven();
        if(stage == true) {
        	bossPos();
        	bossAttack();
        	bossDamage();
        }
    }

    /*
     * Updates the state of the player.
     * Purpose: Performs necessary updates for the player character in each game loop iteration.
     * Pre:The player object is initialized and valid.
     * Post: None (void method).
     */
    private void bossPos() {
		// TODO Auto-generated method stub
    	bossX += BSpeed;        	
    	if (bossX < x) {
            BSpeed = 0.3f;
        } else if (bossX >= x) {
        	BSpeed = -0.3f;
        }
    	if(bossDead == true) {
    		BSpeed = 0;
    		bossX = -300;
    	}
        
	}

    /*
     * Renders the game entities and UI elements on the screen.
     * Purpose: Renders the player, skeletons, boss, health bars, and other game elements on the screen.
     * Pre: The Graphics object (g) is valid and initialized.
     * Post: None (void method).
     */
    public void render(Graphics g) {	
        // Render player animation
        g.drawImage(ani[playerAction][aniIndex], (int) x, (int) y, 240, 180, null);

        // Render player health bar
        g.fillRect(92, 30, healthMax, 10);
        g.setColor(new Color(145, 14, 14));
        g.fillRect(93, 31, healthP - 1, 9);
        
        // Render player FP (focus points) bar
        g.drawRect(92, 41, playerFP, 10);
        g.setColor(new Color(73, 68, 248));
        g.fillRect(93, 42, playerFP - 1, 9);
        
        // Render estus flask count
        g.setColor(Color.WHITE);
        String hea = String.valueOf(estus);
        g.drawString(hea, 1415, 230);

        // Render skeletons
        for (int i = 0; i < 2; i++) {
            // Flip skeleton image if it's to the right of the player
            if (skeleX[i] > x) {
                skele[skeleAction[i]][index[i]] = flipImage(skele[skeleAction[i]][index[i]]);
            }
            g.drawImage(skele[skeleAction[i]][index[i]], (int) skeleX[i], (int)skeleF[i], 300, 270, null);
        }
        
        // Render boss and boss-related elements
        if(stage == true) {
            // Flip boss image if it's to the right of the player
            if (bossX > x) {
                boss[bossAction][bossIndex] = flipImage(boss[bossAction][bossIndex]);
            }
            g.drawImage(boss[bossAction][bossIndex],(int)bossX,470,175,258, null);

            // Render boss health bar
            g.setColor(new Color(102, 51, 0));
            g.fillRect(79,739,1401,11);
            g.setColor(new Color(145, 14, 14));
            g.fillRect(80,740,bossHealth,10);

            // Render boss name
            Font font = new Font("Times New Roman", Font.BOLD, 18);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("PAPAY, THE TRANCE OMEN", 80, 725);
            
            // Render boss slain message if the boss is dead
            if(bossDead == true) {
                g.setColor(Color.ORANGE);
                Font font1 = new Font("Times New Roman", Font.BOLD, 120);
                g.setFont(font1);
                g.drawString("NIGHTMARE SLAIN", 200, 400);
            }
        }
    }

    /*
     * Flips the given image horizontally.
     * Pre: buffered image
     *  Post: Retrun buffered Flipped image
     */
    private BufferedImage flipImage(BufferedImage image) {
        // Create a new BufferedImage with the same dimensions as the original image
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        // Create an AffineTransform to perform the image flipping
        AffineTransform transform = new AffineTransform();
        transform.translate(image.getWidth(), 0); // Translate the image to the rightmost point
        transform.scale(-1, 1); // Scale the image horizontally by -1 to flip it

        // Apply the transformation to the original image using AffineTransformOp
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(image, flippedImage); // Return the flipped image
    }

    /*
     * Updates the animation ticks for the player, skeletons, and boss.
     * This method is responsible for advancing the animation frames and resetting them when necessary.
     */
    private void updateAnimationTick() {
        aniTick++; // Increment the animation tick counter

        // Check if the player's animation tick has reached the maximum value
        if (aniTick >= aniSpeed) {
            aniTick = 0; // Reset the animation tick
            aniIndex++; // Move to the next animation frame

            // Check if the animation frame has exceeded the maximum number of frames for the current action
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0; // Reset the animation frame index
                attacking1 = false; // Reset the attacking1 flag
                attacking2 = false; // Reset the attacking2 flag
                die = false; // Reset the die flag
            }
        }

        // Update the animation ticks for the skeletons
        for (int i = 0; i < 2; i++) {
            tick[i]++; // Increment the tick counter for the current skeleton

            // Check if the tick counter has reached the maximum value
            if (tick[i] >= speed) {
                tick[i] = 0; // Reset the tick counter
                index[i]++; // Move to the next frame for the current skeleton

                // Check if the frame index has exceeded the maximum number of frames for the current skeleton action
                if (index[i] >= GetSkeleAmount(skeleAction[i])) {
                    index[i] = 0; // Reset the frame index
                }
            }
        }

        bossTick++; // Increment the boss animation tick

        // Check if the boss animation tick has reached the maximum value
        if (bossTick >= bossSpeed) {
            bossTick = 0; // Reset the boss animation tick
            bossIndex++; // Move to the next boss animation frame

            // Check if the boss animation frame has exceeded the maximum number of frames for the current action
            if (bossIndex >= GetBossAmount(bossAction)) {
                bossIndex = 0; // Reset the boss animation frame index
            }
        }
    }

    /*
     * Sets the animation based on the player's actions.
     * This method determines the appropriate animation action for the player based on their current state.
     */
    private void setAnimation() {
        int startAni = playerAction; // Store the initial player action

        // Determine the player action based on their current state
        if (moving) {
            playerAction = RUNNING; // Player is moving, set the animation to running
        } else {
            playerAction = IDLE; // Player is not moving, set the animation to

        }
        if (attacking1) {
            playerAction = ATTACK_1;

        } else if (attacking2) {
            playerAction = ATTACK_2;
        } else if (block) {
            playerAction = BLOCK;
        } else if (die) {
            playerAction = DIE;
            aniSpeed = 60;
        }
        if (hit) {
            playerAction = HIT;
        }

        if (startAni != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
    }


    /*
     * Purpose: Handles damage given to skeletons based on player attacks.
     * Pre:The 'attacking1' or 'attacking2' flag must be set to true.
     * Post:
     * - Skeletons receive damage if they are not dead and are within the hitbox range.
     * - If a skeleton receives 3 or more hits, it is marked as dead and its speed is set to 0.
     */
    private void damageGiven() {
        if (attacking1 || attacking2) {
            for (int i = 0; i < 2; i++) {
                if (!isSkeletonDead[i] && skeleX[i] > hitbox.x && skeleX[i] < (hitbox.x + 60)) {
                    skeletonHits[i]++;
                    if (skeletonHits[i] >= 3) { // Check if skeleton has received 5 hits
                        isSkeletonDead[i] = true;
                        skeleSpeed[i] = 0;
                    }
                }
            }
        }
    }

    /*
     * Purpose: Handles damage given to the boss based on player attacks. 
     * Pre:
     * - The 'attacking1' or 'attacking2' flag must be set to true.
     * Post:
     * - The boss health is reduced by 150 if the player is within the boss hitbox range.
     * - If the boss health reaches 0 or below, the 'bossDead' flag is set to true.
     * - The damage cooldown timer is set to the specified value.
     */
    private void bossDamage() {
        if (attacking1 || attacking2) {
            if (damageTimer > 0) {
                damageTimer--;
                return; // Exit the method if still within the cooldown period
            }
            if (x > (bossX - 50) && (bossX + 50) > x) {
                bossHealth -= 150;
                if (bossHealth <= 0) {
                    bossDead = true;
                }
                damageTimer = damageCooldown; // Set the cooldown timer
            }
        }
    }

    /*
     * Purpose: Handles damage taken by the player from skeletons.
     * Pre:
     * - The damage cooldown timer must be greater than 0.
     * Post:
     * - The player's health is reduced by 10 if a skeleton is within the player's hitbox range.
     * - If the player's health reaches 0 or below, the 'setDie' method is called to handle game over condition.
     * - The damage cooldown timer is set to the specified value.
     */
    private void damageTaken() {
        if (damageTimer > 0) {
            damageTimer--;
            return; // Exit the method if still within the cooldown period
        }

        for (int i = 0; i < 2; i++) {
            if (skeleX[i] > (hitbox.x-30) && skeleX[i] < (hitbox.x + 60)) {
                skeleAction[i] = ATTACK_1S;
                skeleSpeed[i] = 0;
                healthP -= 10;
                if (healthP <= 0) {
                    setDie(true);
                    // Player is defeated, handle game over condition here
                }

                damageTimer = damageCooldown; // Set the cooldown timer
            } else {
                skeleAction[i] = WALKS; // Reset skeleton action if it's not attacking
            }
        }
    }

    /*
     * Purpose: Handles boss attacks on the player.
     * Pre:
     * - The damage cooldown timer must be greater than 0 
	 * Post:
	 * - The player's health is reduced by 20 if the boss is within the player's hitbox range.
	 * - If the player's health reaches 0 or below, the 'setDie' method is called to handle game over condition.
	 * - The damage cooldown timer is set to the specified value.
	 */
	private void bossAttack() {
	    if (damageTimer > 0) {
	        damageTimer--;
	        return; // Exit the method if still within the cooldown period
	    }
	
	    if (bossX > hitbox.x && bossX < (hitbox.x + 120)) {
	        bossAction = ATTACK1_B;
	        BSpeed = 0;
	        healthP -= 20;
	        if (healthP <= 0) {
	            setDie(true);
	        }
	
	        damageTimer = damageCooldown; // Set the cooldown timer
	    } else {
	        bossAction = WALK_B; // Reset boss action if it's not attacking
	    }
	}
	
	/*
	 * Purpose: Updates the position of skeletons.
	 * 
	 * Pre:
	 * - The index 'i2' must be a valid index for the 'skeleX', 'skeleSpeed', and 'skeleAction' arrays.
	 * 
	 * Post:
	 * - The position of the skeleton at the specified index is updated based on its speed.
	 */
	private void skelePos(int i2) {
	    skeleX[i2] += skeleSpeed[i2];
	}
	
	/*
	 * Purpose: Updates the player's position and handles movement logic.
	 * Pre: n/a.
	 * Post:
	 * - The player's position is updated based on input and movement speed.
	 * - If the player reaches the edges of the screen, its position is adjusted to create a looping effect.
	 * - If the player is moving, the 'moving' flag is set to true.
	 */
	private void updatePos() {
	    moving = false;
	    for (int i = 0; i < 2; i++) {
	        if (skeleX[i] < x) {
	            skeleSpeed[i] = 0.5f;
	        } else if (skeleX[i] >= x) {
	            skeleSpeed[i] = -0.5f;
	        }
	        if (skeletonHits[i] >= 3) {
	            skeleSpeed[i] = 0;
	            skeleAction[i] = DIE_S;
	            if (index[i] == 3) {
	                skeleX[i] = -300;
	            }
	        }
	    }
	    if (left && !right) {
	        x -= playerSpeed;
	        moving = true;
	        if (x <= -60) {
	            x += 2.0f;
	        } else if (x >= 1600) {
	            x = -20;
	        }
	    } else if (right && !left) {
	        x += playerSpeed;
	        moving = true;
	    }
	
	    if (up && !down) {
	        y -= playerSpeed;
	        moving = true;
	        if (y <= 450) {
	            y += 2.0f;
	        }
	    } else if (down && !up) {
	        y += playerSpeed;
	        moving = true;
	        if (y >= 620) {
	            y -= 2.0f;
	        }
	    }
	}
	
	/*
	 * Purpose: Loads the animations for the player, skeletons, and boss from sprite atlases.
	 * Pre:The sprite atlases for the player, skeletons, and boss must be available.
	 * Post:
	 * - The animation frames are extracted from the sprite atlases and stored
	 *   in the respective BufferedImage arrays ('ani', 'skele', and 'boss').
	 */
	private void loadAnimations() {
	    BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
	
	    ani = new BufferedImage[11][7];
	    for (int j = 0; j < ani.length; j++) {
	        for (int i = 0; i < ani[j].length; i++) {
	            ani[j][i] = img.getSubimage(i * 132, j * 86, 132, 86);
	        }
	    }
	
	    BufferedImage img1 = LoadSave.GetSpriteAtlas(LoadSave.SKELE_ATLAS);
	
	    skele = new BufferedImage[6][8];
	    for (int j = 0; j < 6; j++) {
	        for (int i = 0; i < 8; i++) {
	            skele[j][i] = img1.getSubimage(i * 150, j * 108, 150, 112);
	        }
	    }
	   
	    BufferedImage img2 = LoadSave.GetSpriteAtlas(LoadSave.BOSS);
	
	    boss = new BufferedImage[9][13];
	    for (int j = 0; j < boss.length; j++) {
	        for (int i = 0; i < boss[j].length; i++) {
	            boss[j][i] = img2.getSubimage(i * 128, j * 131, 110, 112);
	        }
	    }
	}
	
	/*
	 * Purpose: Resets the directional boolean flags.
	 * Pre:n/a
	 * Post:
	 * - The 'left', 'right', 'up', and 'down' flags are all set to false.
	 */
	public void resetDirBoolean() {
	    left = false;
	    right = false;
	    up = false;
	    down = false;
	}

    public void setAttack1(boolean attacking1) {
        this.attacking1 = attacking1;
    }

    public void setAttack2(boolean attacking2) {
        this.attacking2 = attacking2;
    }

    public void setDie(boolean die) {
        this.die = die;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public float[] getSkeleX() {
        return skeleX;
    }

    public float[] getSkeleF() {
        return skeleF;
    }

    public float[] getSkeleSpeed() {
        return skeleSpeed;
    }

    public void setHealthP(int healthP) {
        this.healthP = healthP;
    }

    public int getHealthP() {
        return healthP;
    }

    public int getPlayerFP() {
        return playerFP;
    }

    public void setPlayerFP(int playerFP) {
        this.playerFP = playerFP;
    }

    public boolean isHeal() {
        return heal;
    }

    /**
     * Purpose: Performs a healing action on the player.
     * Pre:none
     * Post:none
     */
    public void setHeal() {
        if (estus > 0) {
            if (healthP < healthMax) {
                healthP += 50;

                if (healthP > healthMax) {
                    healthP = healthMax; // Limit the health to the maximum value
                }
            }
            estus--;
        }
    }
    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

	public int getX() {
		// TODO Auto-generated method stub
		return (int) x;
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		boolean gg = false;
		if(healthP<=0) {
			gg = true;
		}
		return gg;
	}

	public void setSkeleX(int i) {
		skeleX[i] = -500;
		stage = true;

	}

	public boolean getbossDead() {
		// TODO Auto-generated method stub
		return bossDead;
	}
}
