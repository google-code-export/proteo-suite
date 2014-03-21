package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.proteosuite.utils.OpenURL;

public class OpenUrlListener implements ActionListener {
	private final String url;

	public OpenUrlListener(String url) {
		this.url = url;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		OpenURL.open(url);
	}
}
