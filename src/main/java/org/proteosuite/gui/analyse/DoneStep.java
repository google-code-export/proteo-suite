package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author SPerkins
 */
public class DoneStep extends JPanel {
	private static final long serialVersionUID = 1L;

	public DoneStep() {
		setLayout(new BorderLayout());

		JLabel stepTitle = new JLabel("You're all done!");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));

		JTextArea mainText = new JTextArea(
				"Your computations including any identifications and quantitations needed are running in the background\n"
						+ "Visit the Tasks tab to see the progress of your analysis.\n"
						+ "Visit the Inspect tab to see interrogate your raw files and result from this analysis as they become available.\n\n"
						+ "But for now have a cup of tea and wait for this whole thing to blow over :)");
		mainText.setEditable(false);
		mainText.setLineWrap(true);
		mainText.setFocusable(false);
		mainText.setBorder(BorderFactory.createEmptyBorder());

		// Dirty Hack alert!
		// For some reason setBackground will not accept the colour object getBackground returns 
		mainText.setBackground(new Color(getBackground().getRGB()));

		add(stepTitle, BorderLayout.PAGE_START);
		add(mainText, BorderLayout.CENTER);
	}
}
