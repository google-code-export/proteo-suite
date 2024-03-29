package org.proteosuite.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public StatusPanel()
	{
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(800, 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel versionLabel = new JLabel("ProteoSuite " + ProteoSuite.PROTEOSUITE_VERSION);
		versionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		versionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(versionLabel);
	}
}
