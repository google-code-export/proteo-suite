package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.proteosuite.gui.listener.ConfirmButtonListener;
import org.proteosuite.gui.listener.PreviousButtonListener;

/**
 * 
 * @author SPerkins
 */
public class LabelFreeStep extends JPanel {
	private static final long serialVersionUID = 1L;

	public LabelFreeStep() {
		setLayout(new BorderLayout());

		JLabel stepTitle = new JLabel(
				"Confirm your quantitation type as label-free:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));

		JTextArea mainText = new JTextArea(
				"Your identifications will be automatically created and/or loaded in the background.\n"
						+ "Additionally your raw data files are (or have) been loaded for inspection in the Inspect tab. This tab will"
						+ " also become populated with quantitation data after you click confirm below.\n\n"
						+ "Identifications created in Proteosuite are provided by MSGF+.\n"
						+ "Label free quantitation is provided by openMS.");
		mainText.setEditable(false);
		mainText.setLineWrap(true);
		mainText.setFocusable(false);
		mainText.setBorder(BorderFactory.createEmptyBorder());

		// Dirty Hack alert!
		// For some reason setBackground will not accept the colour object
		// getBackground returns
		mainText.setBackground(new Color(getBackground().getRGB()));

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ConfirmButtonListener(this));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
                JButton previousButton = new JButton("Previous");
                previousButton.addActionListener(new PreviousButtonListener(this));
                buttonsPanel.add(previousButton);
		buttonsPanel
				.add(new JLabel(
						"CLICK CONFIRM TO CONFIRM QUANTITATION TYPE AS LABEL-FREE AND RUN ANALYSIS"));
		buttonsPanel.add(confirmButton);

		add(stepTitle, BorderLayout.PAGE_START);
		add(mainText, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	public void refreshFromData() {

	}
}
