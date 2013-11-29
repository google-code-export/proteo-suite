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

import org.proteosuite.WorkSpace;
import org.proteosuite.gui.MzMLCompressView;

public class ActionListenerMzMLCompress implements ActionListener {
	private static final WorkSpace WORKSPACE = WorkSpace.getInstance();

	@Override
	public void actionPerformed(ActionEvent evt) {
		// ... Load MzML2MGF GUI ...//
		final JFrame jfWinParams = new JFrame("Compress mzML files");
		MzMLCompressView winParams = new MzMLCompressView(jfWinParams,
				WORKSPACE.getWorkSpace());
		jfWinParams.setResizable(false);
		jfWinParams.setSize(500, 450);
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
				KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				jfWinParams.dispose();
			}
		};
		jfWinParams.getRootPane()
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		jfWinParams.getRootPane().getActionMap()
				.put("ESCAPE", escapeAction);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = dim.width / 2;
		int y1 = dim.height / 2;
		int x2 = jfWinParams.getSize().width / 2;
		int y2 = jfWinParams.getSize().height / 2;
		jfWinParams.setLocation(x1 - x2, y1 - y2);
		Image iconApp = new ImageIcon(getClass().getClassLoader()
				.getResource("images/icon.gif")).getImage();
		jfWinParams.setIconImage(iconApp);
		jfWinParams.setAlwaysOnTop(true);

		jfWinParams.add(winParams);
		jfWinParams.pack();
		jfWinParams.setVisible(true);
	}

}
