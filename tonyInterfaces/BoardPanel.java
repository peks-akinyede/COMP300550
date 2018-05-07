package tonyInterfaces;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.*;

class BoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 700;    // must be even
	private static final int FRAME_HEIGHT = 700;
	private static final int TOKEN_RADIUS = 8;   // must be even
	private static final Color[] PLAYER_COLORS = {Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.WHITE};
	public static final String[] TOKEN_NAME = {"red","blue","yellow","green","magenta","white"};
	private static final float[] PLAYER_OFFSET = {0, 0.01f, 0.02f, 0.03f, 0.04f, 0.05f};
	private static final float[][] CORNER_FROM = { {650, 630}, {10, 620}, {40,5}, {630, 40},};
	private static final float[][] CORNER_TO = {{60, 630}, {0, 70}, {600,5}, {630, 600}};
	private static final int[] JAIL_COORDS = {60,700};
	
	private Players players;	
	private BufferedImage boardImage;
	static int[][][] squareCoords = new int [WorldBuilder.NUM_SQUARES][Players.MAX_NUM_PLAYERS][2];
	private int[][] jailCoords = new int [Players.MAX_NUM_PLAYERS][2];
	
	BoardPanel (Players players) {
		this.players = players;
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		setBackground(Color.WHITE);
		try {
			boardImage = ImageIO.read(this.getClass().getResource("board.png"));
		} catch (IOException ex) {
			System.out.println("Could not find the image file " + ex.toString());
		}
		int sideLength = WorldBuilder.NUM_SQUARES/4;
 		for (int s=0; s<WorldBuilder.NUM_SQUARES; s++) {
			for (int p=0; p<Players.MAX_NUM_PLAYERS; p++) {
		        	int side = (int) s / sideLength;
		        	float offset = (float) (s % sideLength) / sideLength + PLAYER_OFFSET[p];
		        	squareCoords[s][p][0] = Math.round(CORNER_FROM[side][0] + offset * (CORNER_TO[side][0] - CORNER_FROM[side][0]));
		        	squareCoords[s][p][1] = Math.round(CORNER_FROM[side][1] + offset * (CORNER_TO[side][1] - CORNER_FROM[side][1]));
			}
		}
 		for (int p=0; p<Players.MAX_NUM_PLAYERS; p++) {
 			jailCoords[p][0] = JAIL_COORDS[0];
 			jailCoords[p][1] = JAIL_COORDS[1]-4*p;
 		}
		return;
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(boardImage, 0, 0,this.getWidth(),this.getHeight(),this);
        for (int p=0; p<players.numPlayers(); p++) {
 	        int[] coords;
       		if (players.get(p).isInJail()) {
       			coords = jailCoords[p];
       		} else {
       			coords = squareCoords[players.get(p).getPosition()][p];
       		}
	        g2.setColor(Color.BLACK);
            Ellipse2D.Double outline = new Ellipse2D.Double(coords[0],coords[1],2*TOKEN_RADIUS,2*TOKEN_RADIUS);
            g2.fill(outline);
            Ellipse2D.Double ellipse = new Ellipse2D.Double(coords[0]+1,coords[1]+1,2*TOKEN_RADIUS-2,2*TOKEN_RADIUS-2);
            int tokenId = players.get(p).getTokenId();
            g2.setColor(PLAYER_COLORS[tokenId]);
            g2.fill(ellipse);
        }
		return;
    }
    
    public void refresh () {
		revalidate();
		repaint();
		return;
    }
    
    public String getTokenName (int tokenId) {
    	return TOKEN_NAME[tokenId];
    }
    
}
