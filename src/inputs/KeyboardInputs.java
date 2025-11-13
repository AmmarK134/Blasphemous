package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;
import main.GamePanel;
import utility.Sound;

public class KeyboardInputs implements KeyListener {

    Sound musicPlayer = new Sound();
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}


	/*
     * Method invoked when a key is typed.
     * pre: KeyEvent e
     * post: Perform the necessary actions based on the typed key.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * Method invoked when a key is released.
     * pre: KeyEvent e
     * post: Update the game state based on the released key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_V:
                gamePanel.getGame().getPlayer().setBlock(false);
                break;
        }
    }

    /*
     * Method invoked when a key is pressed.
     * pre: KeyEvent e
     * post: Update the game state based on the pressed key.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_Z:
                gamePanel.getGame().getPlayer().setAttack1(true);
                musicPlayer.playMusic("slash.wav");
                break;
            case KeyEvent.VK_X:
                gamePanel.getGame().getPlayer().setAttack2(true);
                musicPlayer.playMusic("slash.wav");
                break;
            case KeyEvent.VK_V:
                gamePanel.getGame().getPlayer().setBlock(true);
                break;
            case KeyEvent.VK_Q:
                gamePanel.getGame().getPlayer().setHeal();
                musicPlayer.playMusic("potion.wav");
                break;
        }
    }
}
