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

public class Instructions implements ActionListener {

	JFrame back = new JFrame();
	JPanel pnl = new JPanel();
	JButton returnn = new JButton("BACK");

	ImageIcon titlelbl = new ImageIcon("d3.jpg");
	Image img1 = titlelbl.getImage();
	Image newimg1 = img1.getScaledInstance(1600, 800, java.awt.Image.SCALE_SMOOTH);
	ImageIcon titlpic1 = new ImageIcon(newimg1);
	JLabel title = new JLabel(titlpic1);

	JLabel lbl1 = new JLabel("<html><center><font color='white' size='+7'>BLASPHEMOUS BLADE BRIEF INSTRUCTIONS</font><br><br>"
			+ "<font color='white' size='5'>1. Explore the treacherous world and uncover its secrets.<br>"
			+ "<font color='white' size='5'>2. Engage in challenging battles with powerful enemies.<br>"
			+ "<font color='white' size='5'>3. Learn from your defeats and strategize to overcome obstacles.<br>"
			+ "<font color='white' size='5'>4. Use weapons, magic, and tactics to defeat your foes.<br>"
			+ "<font color='white' size='5'>5. Manage your resources wisely, including health and stamina.<br>"
			+ "<font color='white' size='5'>6. Discover hidden pathways, shortcuts, and hidden treasures.<br>"
			+ "<font color='white' size='5'>7. Interact with NPCs to learn more about the game's lore.<br>"
			+ "<font color='white' size='5'>8. Face epic boss battles and test your skills to the limit.<br>"
			+ "<font color='white' size='5'>9. Embrace the difficulty and perseverance required to succeed.<br>"
			+ "<font color='white' size='5'>10. Prepare to die, learn, and triumph in the world of true terror!</font></center></html>");
	Sound musicPlayer = new Sound();

	/*
	 * Instructions constructor.
	 * pre: -
	 * post: Creates a new Instructions object and initializes the user interface.
	 */
	public Instructions() {
		super();
		back.setSize(1600, 800); // setting the size of the window
		// back.setDefaultCloseOperation(EXIT_ON_CLOSE);

		returnn.setBounds(685, 550, 150, 50);
		lbl1.setBounds(490, 20, 550, 550);
		title.setBounds(0, 0, 1550, 800);
		returnn.addActionListener(this);

		pnl.setLayout(null);
		pnl.setOpaque(false); // Make the panel transparent
		pnl.add(returnn);
		pnl.add(lbl1);
		pnl.add(title);
		back.add(pnl);
		back.setVisible(true);
	}

	/*
	 * The entry point of the program.
	 * pre: String[] args
	 * post: Creates a new instance of Instructions.
	 */
	public static void main(String[] args) {
		Instructions gui = new Instructions(); // Creates Screen
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
