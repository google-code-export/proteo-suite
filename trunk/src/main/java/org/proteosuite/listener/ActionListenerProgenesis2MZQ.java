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

import org.proteosuite.gui.Prog2MZQView;

public class ActionListenerProgenesis2MZQ implements ActionListener {

	// TODO:
	private String sWorkspace;

	@Override
	public void actionPerformed(ActionEvent e) {
		// ... Load Progenesis2MZQ GUI ...//
				final JFrame jfWinParams = new JFrame(
						"Convert Progenesis result files to mzQuantML");
				Prog2MZQView winParams = new Prog2MZQView(jfWinParams, sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 180);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
						0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader().getResource(
						"images/icon.gif")).getImage();
				jfWinParams.setIconImage(iconApp);
				jfWinParams.setAlwaysOnTop(true);

				jfWinParams.add(winParams);
				jfWinParams.pack();
				jfWinParams.setVisible(true);
	}

}
