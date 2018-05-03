package tonyInterfaces;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BoardNames extends JPanel{

	private static final long serialVersionUID = 1L;

//	private static final float[][] CORNER_FROM = { {610, 630}, {5, 600}, {40,5}, {630, 40},};
//	private static final float[][] CORNER_TO = {{60, 630}, {5, 70}, {600,5}, {630, 600}};
	private static final int FRAME_WIDTH = 700;    // must be even
	private static final int FRAME_HEIGHT = 700;
	double rotationSide1 = 0;
	double rotationSide2 = Math.PI/2;
	double rotationSide3 = Math.PI;
	double rotationSide4 = 3* Math.PI/2;
	JLabel labels[] = new JLabel[WorldBuilder.NUM_SQUARES];
	int[][][] coordinates = BoardPanel.squareCoords; 
	WorldBuilder wb = null;

	
	BoardNames(WorldBuilder worldbuilder) {
		wb = worldbuilder;
		setLayout(null);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
//		int sideLength = worldbuilder.NUM_SQUARES/4;
		
//		for(int s=0;s<WorldBuilder.NUM_SQUARES;s++){
//			double rotationFactor;
//			if(s<10){
//				rotationFactor = rotationSide1;
//			}else if(s<20){
//				rotationFactor = rotationSide2;
//				
//			}else if(s<30){
//				rotationFactor = rotationSide3;
//			}else{
//				rotationFactor = rotationSide4;
//			}
//		
//			NamedLocation currSquare = worldbuilder.getSquare(s);
//			String locationName = currSquare.getIdentifier();
//			labels[s] = new JTextArea(locationName);
//			labels[s].setBackground(null);
//			labels[s].setBounds(coordinates[s][0][0],coordinates[s][0][1],30,100);
//			labels[s].setFont(new Font("Times New Roman", Font.BOLD, 10));
//			add(labels[s]);
//
//		}
				
	}
	
	
	
	@Override
    public void paintComponent(Graphics g) {
//		double rotationFactor = 0.0;

		for(int s=10;s<WorldBuilder.NUM_SQUARES;s++){
			
			NamedLocation currSquare = wb.getSquare(s);
			String locationName = currSquare.getIdentifier();
			labels[s] = new JLabel(locationName);
			labels[s].setBounds(coordinates[s][0][0],coordinates[s][0][1],30,100);
			labels[s].setFont(new Font("Times New Roman", Font.BOLD, 12));
//	        Graphics2D g2d = (Graphics2D) g;
//	        int w2 = getWidth() / 2;
//	        int h2 = getHeight() / 2;
//	        g2d.rotate(-Math.PI/2, w2, h2);
//	        super.paintComponent(g);
//			add(labels[s]);

			
			if(s<10){
		        Graphics2D g2d = (Graphics2D) g;
		        int w2 = getWidth() / 2;
		        int h2 = getHeight() / 2;
		        g2d.rotate(Math.PI/2, w2, h2);
		        super.paintComponent(g);
		        
			}else if(s<20){
		        Graphics2D g2d = (Graphics2D) g;
		        int w2 = getWidth() / 2;
		        int h2 = getHeight() / 2;
		        g2d.rotate(Math.PI/2, w2, h2);
		        super.paintComponent(g);
				add(labels[s]);


				
			}else if(s<30){
		        Graphics2D g2d = (Graphics2D) g;
		        int w2 = getWidth() / 2;
		        int h2 = getHeight() / 2;
		        g2d.rotate(Math.PI, w2, h2);
		        super.paintComponent(g);
				add(labels[s]);


				
			}else{
		        Graphics2D g2d = (Graphics2D) g;
		        int w2 = getWidth() / 2;
		        int h2 = getHeight() / 2;
		        g2d.rotate(-Math.PI/2, w2, h2);
		        super.paintComponent(g);
				add(labels[s]);


			}
			add(labels[s]);



		}
		
        
    }
	
	
}
