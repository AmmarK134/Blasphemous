/*
 * This class provides utility methods for playing and stopping audio clips.
 */
package utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    /*
     * Plays the specified music file.
     * pre The musicFilePath must be a valid file path.
     * post The music file is played.
     */
    public void playMusic(String musicFilePath) {
        try {
            // Load the audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/" + musicFilePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Start playing the music
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /*
     * Stops the currently playing music.
     * pre: The music must be currently playing.
     * post: The music is stopped.
     */
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
