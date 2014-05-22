package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class manualDecoyActionPerformed implements ActionListener {

	private final JCheckBox manualDecoy;
	private final JTextField manualDecoyPrefixValue;
	private final JTextField manualDecoyRatioValue;

	public manualDecoyActionPerformed(JCheckBox manualDecoy,
			JTextField manualDecoyPrefixValue, JTextField manualDecoyRatioValue) {
		this.manualDecoy = manualDecoy;
		this.manualDecoyPrefixValue = manualDecoyPrefixValue;
		this.manualDecoyRatioValue = manualDecoyRatioValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (manualDecoy.isSelected()) {
			manualDecoyPrefixValue.setEnabled(true);
			manualDecoyRatioValue.setEnabled(true);
		} else {
			manualDecoyPrefixValue.setEnabled(false);
			manualDecoyRatioValue.setEnabled(false);
		}
	}
}