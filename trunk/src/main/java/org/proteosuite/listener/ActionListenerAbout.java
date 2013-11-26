package org.proteosuite.listener;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.proteosuite.gui.About;

public class ActionListenerAbout implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		About about = new About();
		final JFrame jfAbout = new JFrame("About ProteoSuite");
		jfAbout.setResizable(false);
		jfAbout.setSize(400, 300);
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				jfAbout.dispose();
			}
		};
		jfAbout.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		jfAbout.getRootPane().getActionMap().put("ESCAPE", escapeAction);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = dim.width / 2;
		int y1 = dim.height / 2;
		int x2 = jfAbout.getSize().width / 2;
		int y2 = jfAbout.getSize().height / 2;
		jfAbout.setLocation(x1 - x2, y1 - y2);
		Image iconApp = new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage();
		jfAbout.setIconImage(iconApp);
		jfAbout.setAlwaysOnTop(true);

		jfAbout.add(about);
		jfAbout.pack();
		jfAbout.setVisible(true);
	}

}
