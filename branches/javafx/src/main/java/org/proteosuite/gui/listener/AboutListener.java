package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.proteosuite.gui.About;

/**
 * 
 * @author Andrew Collins
 */
public class AboutListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new About();
	}

}
