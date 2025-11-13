package main;

import java.awt.Graphics;

import character.Player;
import levels.Dead;
import levels.LevelManager;
import utility.Sound;

public class Game implements Runnable {

	Sound musicPlayer = new Sound();
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;
	private LevelManager levelManager;
	public static boolean gameOver = false; // New variable to track game over state
    private Dead deadScreen; // New instance of the Dead screen
	
	public final static int TILES_SIZE = 132;

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200,500, 240, 180);
//		player.loadlvlData(levelManager.getCurrentLevel().getLevelData());

        deadScreen = new Dead();
		
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
		checkGameOver();
	}

	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		 if (gameOver == true) {
	            deadScreen.render(g); // Render the Dead screen if game over
	        }
		
	}
	//@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate =1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0; 
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0; //for updates
		double deltaF = 0; //for frames/render

		while (true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime)/timePerUpdate;
			deltaF += (currentTime - previousTime)/timePerFrame;
			previousTime = currentTime;
			if(deltaU>=1) {
				update();
				updates++;
				deltaU--;
			}
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
//			

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames+ " | UPS: "+updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void windowFocusLost() {
		player.resetDirBoolean();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	 public boolean checkGameOver() {
		 if (player.isDead()) {
	        gameOver = true;
	     }
		return gameOver;
	}
}
