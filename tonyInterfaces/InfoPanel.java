package tonyInterfaces;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int TEXT_AREA_HEIGHT = 20;
	private static final int CHARACTER_WIDTH = 38;
	private static final int FONT_SIZE = 14;

	JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, CHARACTER_WIDTH);
//	JTextArea textArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(textArea);
	DefaultCaret caret = (DefaultCaret)textArea.getCaret();
	
	InfoPanel () {
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void displayString (String text) {
		textArea.setText(textArea.getText()+"\n"+text);
	}

}
