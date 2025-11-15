package main;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import utility.Sound;
import utility.Sound.*;

public class MainClass implements ActionListener {
	
	// ImageIcon for the title image
	ImageIcon titlelbl = new ImageIcon("title.png");
	

	
	// Loading and scaling the title image
	Image img1 = titlelbl.getImage();   
	Image newimg1 = img1.getScaledInstance(1600, 800, java.awt.Image.SCALE_SMOOTH);
	ImageIcon titlpic1 = new ImageIcon(newimg1);
	
	// JLabel to display the title image
	JLabel title = new JLabel(titlpic1);
	
	JFrame back = new JFrame(); // Main frame for the application
	JPanel pnl = new JPanel(); // Panel to hold components
	JButton lbl = new JButton("START GAME"); // Button for starting the game
	JButton lbl1 = new JButton("CONTROLS"); // Button for displaying controls
	JButton lbl2 = new JButton("INSTRUCTIONS"); // Button for displaying instructions
	
	Sound musicPlayer = new Sound(); // Sound player utility
	
	public MainClass()	 {
		super();
		back.setSize(1600, 800); // Setting the size of the window

		// Setting the positions and sizes of the buttons and the title image
		lbl.setBounds(675, 500, 150, 50);
		lbl1.setBounds(675, 560, 150, 50);
		lbl2.setBounds(675, 620, 150, 50);
		title.setBounds(0, 0, 1550, 800);
		
		lbl.addActionListener(this); // Adding an ActionListener to the "START GAME" button
		lbl1.addActionListener(this); // Adding an ActionListener to the "CONTROLS" button
		lbl2.addActionListener(this); // Adding an ActionListener to the "INSTRUCTIONS" button
		
		musicPlayer.playMusic("mainTheme.wav"); // Playing the main theme music
		
		pnl.setLayout(null); // Setting the layout of the panel to null
		pnl.add(title); // Adding the title image to the panel
		title.add(lbl); // Adding the "START GAME" button to the title image
		title.add(lbl1); // Adding the "CONTROLS" button to the title image
		title.add(lbl2); // Adding the "INSTRUCTIONS" button to the title image
		
		back.add(pnl); // Adding the panel to the main frame
		back.setVisible(true); // Setting the visibility of the frame to true
		back.setFocusable(true); // Setting the frame to be focusable
	}
	
	public static void main(String[] args) {
		MainClass gui = new MainClass(); // Creates an instance of MainClass to start the application
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == lbl){ // Checks if the "START GAME" button is clicked
			back.dispose(); // Closes the main frame
			musicPlayer.stopMusic(); // Stops the music
			new Game(); // Moves to the game screen
		}
		
		if(event.getSource() == lbl1){ // Checks if the "CONTROLS" button is clicked
			back.dispose(); // Closes the main frame
			new Controls(); // Moves to the controls screen
		}
		
		if(event.getSource() == lbl2){ // Checks if the "INSTRUCTIONS" button is clicked
			back.dispose(); // Closes the main frame
			new Instructions(); // Moves to the instructions screen
		}
	}
}
