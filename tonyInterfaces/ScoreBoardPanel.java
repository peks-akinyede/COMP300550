package tonyInterfaces;

import javax.swing.*;
import java.awt.*;

public class ScoreBoardPanel extends JPanel {

private static final long serialVersionUID = 1L;
private static final int TEXT_AREA_HEIGHT = 20;
private static final int CHARACTER_WIDTH = 38;
private static final int FONT_SIZE = 14;

JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);


ScoreBoardPanel() {
textArea.setEditable(false);
textArea.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
textArea.setForeground(Color.WHITE);
textArea.setBackground(Color.BLACK);
textArea.setLineWrap(true);
textArea.setWrapStyleWord(true);

}

public void updateScoreBoard() {
textArea.setText("\nHELLO WORD");
}

}
