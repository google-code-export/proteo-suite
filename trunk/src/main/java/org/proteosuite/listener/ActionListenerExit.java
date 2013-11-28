package org.proteosuite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

/**
 * 
 * @author Andrew Collins
 */
public class ActionListenerExit implements ActionListener, WindowListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// ... Exiting from proteosuite ...//
		int iExit = JOptionPane.showConfirmDialog(null, "Do you want to exit?",
				"Exiting from ProteoSuite", JOptionPane.YES_NO_OPTION);
		if (iExit == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		actionPerformed(null);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
