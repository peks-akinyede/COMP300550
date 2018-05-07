package tonyInterfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * Panel to print names dynamically  on the board
 */
public class BoardNames extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int FRAME_WIDTH = 700; // must be even
	private static final int FRAME_HEIGHT = 700;
	private JTextArea labels = new JTextArea();
	private int[][][] coordinates = BoardPanel.squareCoords;
	private WorldBuilder wb = null;

	BoardNames(WorldBuilder worldbuilder) {
		wb = worldbuilder;
		setLayout(null);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		/*
		 * iterator which goes through each Property and the board coordinates
		 * and prints the Property name to its corresponding board position
		 */
		for (int s = 0; s < WorldBuilder.NUM_SQUARES; s++) {
			// loop to make sure property name is not printed on the board
			// locations with images
			if (!(s == 0 || s == 2 || s == 7 || s == 10 || s == 17 || s == 20 || s == 22 || s == 30 || s == 36)) {
				NamedLocation currSquare = wb.getSquare(s);
				String locationName = currSquare.getIdentifier().toUpperCase();

				labels = new JTextArea(locationName);

				if (s < 10) {
					labels.setBounds(coordinates[s][0][0] - 15, coordinates[s][0][1] - 20, 38, 40);

				} else if (s < 14) {
					labels.setBounds(coordinates[s][0][0] + 10, coordinates[s][0][1], 60, 30);
				} else if (s < 19) {
					labels.setBounds(coordinates[s][0][0] + 10, coordinates[s][0][1] - 15, 60, 30);
				} else if (s < 20) {
					labels.setBounds(coordinates[s][0][0] + 10, coordinates[s][0][1] - 20, 60, 30);
				} else if (s < 30) {
					labels.setBounds(coordinates[s][0][0], coordinates[s][0][1] + 5, 38, 40);

				} else {
					labels.setBounds(coordinates[s][0][0] - 15, coordinates[s][0][1], 60, 30);

				}

				labels.setEditable(false);
				labels.setOpaque(false);
				labels.setForeground(Color.BLACK);

				labels.setWrapStyleWord(true);
				labels.setLineWrap(true);
				labels.setFont(new Font("Arial", Font.BOLD, 8));
				// add labels one by one to the panel
				add(labels);
			}
		}

	}



}
