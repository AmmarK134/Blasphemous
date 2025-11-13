package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import utility.LoadSave;
import utility.Sound;

public class Dead {
    private Image deadImage;
    Sound musicPlayer = new Sound();

    public Dead() {
        // Load the image for the dead screen
        deadImage = LoadSave.GetSpriteAtlas("dead.jpg");
    }

    public void render(Graphics g) {
//    	musicPlayer.playMusic("death.wav");
//    	musicPlayer.playMusic("end.wav");
        // Set the background color
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1600, 800);

        // Draw the dead image at the center of the screen
        g.setColor(Color.WHITE);
        g.drawImage(deadImage, 0, 0,1600,800, null);
    }
}
