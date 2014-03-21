package org.proteosuite.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.proteosuite.gui.listener.AboutListener;
import org.proteosuite.gui.listener.CheckUpdateListener;
import org.proteosuite.gui.listener.ExitListener;
import org.proteosuite.gui.listener.OpenListener;
import org.proteosuite.gui.listener.OpenUrlListener;

/**
 * 
 * @author Andrew Collins
 */
public class MenuBar extends JMenuBar {

	public MenuBar() {
		add(getFileMenu());
		add(getCustomHelpMenu());
	}

	private JMenu getFileMenu() {
		JMenuItem openFile = new JMenuItem("Open File");
		openFile.addActionListener(new OpenListener());
		
		JMenuItem jmExit = new JMenuItem("Exit");
		jmExit.addActionListener(new ExitListener());
		jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				InputEvent.ALT_MASK));

		JMenu jmFile = new JMenu("File");
		jmFile.add(openFile);
		jmFile.add(new JPopupMenu.Separator());
		jmFile.add(jmExit);

		return jmFile;
	}

	private JMenu getCustomHelpMenu() {
		JMenu jmHelp = new JMenu("Help");

		JMenuItem jmHelpContent = new JMenuItem("ProteoSuite Help",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/help.gif")));
		jmHelpContent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		jmHelpContent.setToolTipText("Find how to use ProteoSuite");
		jmHelpContent.addActionListener(new OpenUrlListener(
				"http://www.proteosuite.org/?q=tutorials"));

		jmHelp.add(jmHelpContent);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmContactUs = new JMenuItem("Contact us");
		jmContactUs
				.setToolTipText("Click here for contacting our group and collaborators");
		jmContactUs.addActionListener(new OpenUrlListener(
				"http://www.proteosuite.org/?q=contact"));

		jmHelp.add(jmContactUs);

		JMenuItem jmCheckUpdates = new JMenuItem("Check for updates");
		jmCheckUpdates.addActionListener(new CheckUpdateListener());
		jmHelp.add(jmCheckUpdates);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmAbout = new JMenuItem("About ProteoSuite");
		jmAbout.addActionListener(new AboutListener());
		jmHelp.add(jmAbout);

		return jmHelp;
	}
}
