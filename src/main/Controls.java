package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import utility.Sound;
import utility.Sound.*;

public class Controls implements ActionListener {

	JFrame back = new JFrame();
	JPanel pnl = new JPanel();
	JButton returnn = new JButton("BACK");

	ImageIcon titlelbl = new ImageIcon("title.png");
	Image img1 = titlelbl.getImage();
	Image newimg1 = img1.getScaledInstance(1600, 800, java.awt.Image.SCALE_SMOOTH);
	ImageIcon titlpic1 = new ImageIcon(newimg1);
	JLabel title = new JLabel(titlpic1);

	JLabel lbl2 = new JLabel("<html><center><font color='black' size='+12'>BASIC INSTRUCTIONS</font><br><br>"
			+ "<font color='black' size='+10'>W, A, S, D</font> to move<br>"
			+ "<font color='black' size='+10'>Q</font> to heal<br>"
			+ "<font color='black' size='+10'>Z</font> and <font color='black' size='+10'>X</font> to attack<br>"
			+ "<font color='black' size='+10'>V</font> to block</center></html>");

	Sound musicPlayer = new Sound();

	/*
	 * Controls constructor.
	 * pre: -
	 * post: Creates a new Controls object and initializes the user interface.
	 */
	public Controls() {
		super();
		back.setSize(1600, 800);// setting the size of the window
		// back.setDefaultCloseOperation(EXIT_ON_CLOSE);

		returnn.setBounds(675, 500, 150, 50);
		// title.setBounds(0,0,1550,800);
		lbl2.setBounds(570, 20, 350, 600);
		returnn.addActionListener(this);

		pnl.setLayout(null);
		// pnl.add(title);
		pnl.add(returnn);
		pnl.add(lbl2);
		back.add(pnl);
		back.setVisible(true);
	}

	/*
	 * The entry point of the program.
	 * pre: String[] args
	 * post: Creates a new instance of Controls.
	 */
	public static void main(String[] args) {
		Controls gui = new Controls(); // Creates Screen
	}

	/*
	 * Handles the action events.
	 * pre: ActionEvent event
	 * post: Performs the appropriate action based on the event source.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == returnn) { // Checks If Start is clicked
			back.dispose();
			new MainClass(); // Moves To Game Screen
		}
	}
}
