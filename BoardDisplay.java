package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class BoardDisplay extends JPanel {

	public static void InformationPanel(JPanel info_panel) {
		info_panel.setBounds(0, 540, 550, 500); // set the X and Y coordinates
		// of the Information panel
		info_panel.setBackground(Color.black); // set the background color to
		//black
		info_panel.setBorder(BorderFactory.createTitledBorder("INFORMATION PANEL"));
		
		JButton p1 = new JButton("HELP");
		p1.setBackground(Color.red);
		p1.setForeground(Color.black);
		p1.setBounds(20, 30, 10, 100);
		info_panel.add(p1);

		JButton p2 = new JButton("BOOST");
		p2.setBackground(Color.red);
		p2.setForeground(Color.black);
		p2.setBounds(20, 70, 100, 100);
		info_panel.add(p2) ;
	}

	public static void UpdateLabel(JTextField txtfield, JLabel print) {

		String input = txtfield.getText() + print.getText();
		print.setText("<html>" + "<br>" + input + "<br>" + "</html>");
	}

	public static void CommandPanel(JPanel command_panel, Board board) {
		command_panel.setBounds(551, 0, 450, 450); // The X and Y coordinates of
		// the Command panel
		final JLabel print = new JLabel(); // create new JLabel object
		// JButton allows the user to either use the return key or click the
		// mouse to enter the command
		JButton enterButton = new JButton("Enter");
		enterButton.setBounds(20, 20, 50, 50);
		JButton move = new JButton("move"); // create JButton object with Label
		// "move"
		move.setBounds(20, 20, 50, 50);
		command_panel.setBackground(Color.black);
		command_panel.setBorder(BorderFactory.createTitledBorder("COMMAND PANEL"));

		final JTextField txtfield = new JTextField("----ENTER THE COMMAND HERE---", 26); // Text
		// Field
		// size
		// is
		// set
		// to
		// 20
		command_panel.add(move);
		command_panel.add(enterButton);
		command_panel.add(txtfield);
		command_panel.add(print);

		txtfield.addActionListener(new ActionListener() {// Register an instance
			// of the event
			// handler class as
			// a listener to
			// txtfield
			public void actionPerformed(ActionEvent e) // called just after the
			// user performs an
			// action
			{

				UpdateLabel(txtfield, print); // calling the UpdateLabel
				// function
			}
		});

		enterButton.addActionListener(new ActionListener() {// Register an
			// instance of the
			// event handler
			// class as a
			// listener to
			// enterButton
			public void actionPerformed(ActionEvent e) {
				UpdateLabel(txtfield, print);
			}
		});
	}

	public static void PlayerPanel(JPanel player_panel, final Board board) {
		player_panel.setBounds(551, 450, 450, 100);
		player_panel.setBackground(Color.black);
		player_panel.setBorder(BorderFactory.createTitledBorder("Player Panel"));

		JButton p1 = new JButton("ROLL");
		p1.setBackground(Color.cyan);
		p1.setForeground(Color.black);
		p1.setBounds(70, 30, 80, 50);
		player_panel.add(p1);

		JButton p2 = new JButton("BUY");
		p2.setBackground(Color.cyan);		
		p2.setForeground(Color.black);
		p2.setBounds(170, 30, 80, 50);
		player_panel.add(p2);

		JButton p3 = new JButton("MORT");
		p3.setBackground(Color.cyan);
		p3.setForeground(Color.black);
		p3.setBounds(270, 30, 80, 50);
		player_panel.add(p3);

		
		p1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TO_ BE_IMPLEMENTED
			}
		});
		

		p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TO_ BE_IMPLEMENTED
			}
		});

		p3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TO_ BE_IMPLEMENTED
			}
		});

	}

	public static void DisplayBank(JPanel cards) {
		cards.setBounds(551, 540, 450, 450); // The X and Y coordinates of the
		// bank
		cards.setBackground(Color.black);
		cards.setBorder(BorderFactory.createTitledBorder("Banker"));
	}

	public static void main(String[] args) throws IOException {

		Board frame = new Board(); // instance variable of Board class
		frame.setVisible(true); // set the image visible
		JPanel board_panel = new JPanel();
		JPanel info_panel = new JPanel();
		JPanel command_panel = new JPanel();
		JPanel player_panel = new JPanel();
		player_panel.setLayout(null);
		JPanel cards = new JPanel();

		frame.setSize(1010, 1010); // size of the Monopoly Board frame
		frame.add(info_panel);
		frame.add(board_panel);
		frame.add(command_panel);
		frame.add(player_panel);
		frame.add(cards);

		// function calls
		InformationPanel(info_panel);
		DisplayBank(board_panel);
		CommandPanel(command_panel, frame);
		PlayerPanel(player_panel, frame);
		DisplayBank(cards);

		frame.setLayout(null);
		frame.setTitle("Monopoly"); // set the frame title to Monopoly
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.EXIT_ON_CLOSE
		// is used to
		// close the
		// frame
		frame.setVisible(true);
	}
}
