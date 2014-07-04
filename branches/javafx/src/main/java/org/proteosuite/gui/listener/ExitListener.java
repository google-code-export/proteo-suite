package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * 
 * @author Andrew Collins
 */
public class ExitListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// Exiting from proteosuite
		int iExit = JOptionPane.showConfirmDialog(null, "Do you want to exit?",
				"Exiting from ProteoSuite", JOptionPane.YES_NO_OPTION);
		if (iExit == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
