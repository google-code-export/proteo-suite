package org.proteosuite.gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.proteosuite.ProteoSuiteView;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public StatusPanel()
	{
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(800, 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel versionLabel = new JLabel("ProteoSuite " + ProteoSuiteView.PROTEOSUITE_VERSION);
		versionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(versionLabel);
	}
}
