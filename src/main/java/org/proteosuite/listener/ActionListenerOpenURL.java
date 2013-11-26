package org.proteosuite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.proteosuite.utils.OpenURL;

public class ActionListenerOpenURL implements ActionListener {
	private final String url;
	
	public ActionListenerOpenURL (String url)
	{
		this.url = url;
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		new OpenURL(url);
	}

}
