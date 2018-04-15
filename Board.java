package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JFrame {

	BoardPanel drawImage = new BoardPanel(); //drawImage is an instance of the class BoardPanel

	Board() {
		add(this.drawImage); //draw the image on the frame
		setSize(565, 580); //set the image size to 565 by 580
	}

	public BoardPanel getBoardPanel() {
		return this.drawImage;
	}

	class BoardPanel extends JPanel {

		int[][] SQUARE_COORD = { { 512, 505 }, { 451, 505 }, { 406, 505 }, { 361, 505 }, { 316, 505 }, { 271, 505 },
				{ 226, 505 }, { 181, 505 }, { 136, 505 }, { 91, 505 }, { 30, 505 }, // bottom Row


				{ 30, 444 }, { 30, 399 }, { 30, 354 }, { 30, 309 }, { 30, 264 }, { 30, 219 }, { 30, 174 }, { 30, 129 },
				{ 30, 84 }, { 30, 23 }, // left Column

				{ 91, 23 }, { 136, 23 }, { 181, 23 }, { 226, 23 }, { 271, 23 }, { 316, 23 }, { 361, 23 }, { 406, 23 },
				{ 451, 23 }, { 512, 23 }, // top Row

				{ 512, 84 }, { 512, 129 }, { 512, 174 }, { 512, 219 }, { 512, 264 }, { 512, 309 }, { 512, 354 },
				{ 512, 399 }, { 512, 444 }// right Column
		};

		Image background = null;
		int x_coord;
		int y_coord;
		int i, j, k, w, p;

		BoardPanel() {
			MediaTracker mediaTracker = new MediaTracker(this);

			try {
				background = ImageIO.read(Board.class.getResource("board2.png"));

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			mediaTracker.addImage(background, 0);
			try {
				mediaTracker.waitForAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image Boardimage = background.getScaledInstance(550, 541, java.awt.Image.SCALE_SMOOTH);
			g.drawImage(Boardimage, 0, 0, null);

			Graphics2D black = (Graphics2D) g;			
			Graphics2D red = (Graphics2D) g;
			Graphics2D blue = (Graphics2D) g; // Colored token instead

			blue.setColor(Color.blue);
			if ( i == 40 ){i = 0;}
			this.x_coord = SQUARE_COORD[i][0];
			this.y_coord = SQUARE_COORD[i][1];
			g.fillOval(this.x_coord+13, this.y_coord-13, 12, 12);

			black.setColor(Color.green);	
			if ( k == 40 ){k = 0;}
			this.x_coord = SQUARE_COORD[k][0];
			this.y_coord = SQUARE_COORD[k][1];
			g.fillOval(this.x_coord+13, this.y_coord+13, 12, 12);


			red.setColor(Color.RED);
			if ( j == 40 ){j = 0;}
			this.x_coord = SQUARE_COORD[j][0];
			this.y_coord = SQUARE_COORD[j][1];
			g.fillOval(this.x_coord -13, this.y_coord+13, 12, 12);


			red.setColor(Color.MAGENTA);
			if ( p == 40 ){p = 0;}
			this.x_coord = SQUARE_COORD[p][0];
			this.y_coord = SQUARE_COORD[p][1];
			g.fillOval(this.x_coord -13, this.y_coord-13, 12, 12);		
		}
	}
}
