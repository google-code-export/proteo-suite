package org.proteosuite.listener;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.ProjectParamsView;

public class ActionListenerOptions implements ActionListener {
	private final ProteoSuiteView proteoSuiteView;
	
	public ActionListenerOptions(ProteoSuiteView proteoSuiteView) {
		this.proteoSuiteView = proteoSuiteView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectParamsView panelParams = new ProjectParamsView();
		final JFrame jfWinParams = new JFrame("Options");
		jfWinParams.setResizable(false);
		jfWinParams.setSize(580, 370);
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		
		final Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				jfWinParams.dispose();
				boolean isOK = proteoSuiteView.getWorkspace();
				if (isOK) {
					proteoSuiteView.updateTitle();
				}
			}
		};
		jfWinParams.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				escapeAction.actionPerformed(null);
			}
		});

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

		jfWinParams.add(panelParams);
		jfWinParams.pack();
		jfWinParams.setVisible(true);
	}
}
