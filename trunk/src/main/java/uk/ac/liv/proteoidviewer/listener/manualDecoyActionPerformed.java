package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class manualDecoyActionPerformed implements ActionListener {

	private final JTextField manualDecoyPrefixValue;
	private final JTextField manualDecoyRatioValue;

	public manualDecoyActionPerformed(JTextField manualDecoyPrefixValue, JTextField manualDecoyRatioValue) {
		this.manualDecoyPrefixValue = manualDecoyPrefixValue;
		this.manualDecoyRatioValue = manualDecoyRatioValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JCheckBox manualDecoy = (JCheckBox) e.getSource();
		if (manualDecoy.isSelected()) {
			manualDecoyPrefixValue.setEnabled(true);
			manualDecoyRatioValue.setEnabled(true);
		} else {
			manualDecoyPrefixValue.setEnabled(false);
			manualDecoyRatioValue.setEnabled(false);
		}
	}
}