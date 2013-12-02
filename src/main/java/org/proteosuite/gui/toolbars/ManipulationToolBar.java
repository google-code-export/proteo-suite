package org.proteosuite.gui.toolbars;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ManipulationToolBar extends JToolBar {

	public ManipulationToolBar()
	{
		setFloatable(false);
		
		JButton jbCut = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/cut.gif")));
		jbCut.setToolTipText("Cut (Ctrl + X)");
		jbCut.setEnabled(false);
		jbCut.setFocusable(false);
		jbCut.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCut.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(jbCut);

		JButton jbCopy = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/copy.gif")));
		jbCopy.setEnabled(false);
		jbCopy.setFocusable(false);
		jbCopy.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(jbCopy);

		JButton jbPaste = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/paste.gif")));
		jbPaste.setEnabled(false);
		jbPaste.setFocusable(false);
		jbPaste.setHorizontalTextPosition(SwingConstants.CENTER);
		jbPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(jbPaste);
	}
}
