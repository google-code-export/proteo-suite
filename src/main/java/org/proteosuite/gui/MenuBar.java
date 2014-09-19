package org.proteosuite.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.proteosuite.gui.listener.AboutListener;
import org.proteosuite.gui.listener.ExitListener;
import org.proteosuite.gui.listener.MemoryChangeListener;
import org.proteosuite.gui.listener.OpenListener;
import org.proteosuite.gui.listener.OpenUrlListener;
import org.proteosuite.utils.SystemUtils;

/**
 * 
 * @author Andrew Collins
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public MenuBar() {
		add(getFileMenu());
		add(getOptionsMenu());
		add(getCustomHelpMenu());
	}

	private JMenu getOptionsMenu() {
		
		JMenu memory = getMemoryMenu();

		JMenu options = new JMenu("Options");
		options.add(memory);

		return options;
	}

	private JMenu getMemoryMenu() {
		SystemUtils sys = new SystemUtils();
		
		JMenu memory = new JMenu("Memory");
		
		JMenuItem currentMemory = new JMenuItem("Current " + (sys.getMaxMemory() / 1024) + " GB");
		currentMemory.setEnabled(false);
		
		memory.add(currentMemory);
		memory.add(new JSeparator());
		
		for (short i = 1; i <= 128; i = (short) (i *2))
		{
			JMenuItem item = new JMenuItem("Set " + i + " GB");
			item.addActionListener(new MemoryChangeListener((byte) i));
			memory.add(item);
		}
		
		return memory;
	}

	private JMenu getFileMenu() {
		JMenuItem openFiles = new JMenuItem("Import Files or Folders...");
		openFiles.addActionListener(new OpenListener());               

		JMenuItem jmExit = new JMenuItem("Exit");
		jmExit.addActionListener(new ExitListener());
		jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				InputEvent.ALT_MASK));

		JMenu jmFile = new JMenu("File");
		jmFile.add(openFiles);                
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

		JMenuItem jmAbout = new JMenuItem("About ProteoSuite");
		jmAbout.addActionListener(new AboutListener());
		jmHelp.add(jmAbout);

		return jmHelp;
	}
}
