/**
 * --------------------------------------------------------------------------
 * ProteoSuite.java
 * --------------------------------------------------------------------------
 * Description: ProteoSuite Graphical Unit Interface
 * Developer: fgonzalez
 * Created: 08 February 2011 
 * Notes: Read our documentation file under our Google SVN repository SVN:
 * http://code.google.com/p/proteo-suite/ Project Website:
 * http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.proteosuite.WorkSpace;

/**
 * This class corresponds to the main form in ProteoSuite. The form includes the
 * visualisation of data and other associated tools.
 * 
 * @author fgonzalez
 */
public class ProteoSuite extends JFrame {
	private static final long serialVersionUID = 1L;
	// Project settings
	public static final String PROTEOSUITE_VERSION = "1.0.0 ALPHA";

	public static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
	//public static final String MZQUANT_VERSION = "1.0.0";
	public static final String PSI_MS_VERSION = "3.37.0";
	public static final String PSI_MOD_VERSION = "1.2";

	private static final WorkSpace WORKSPACE = WorkSpace.getInstance();

	public ProteoSuite() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ISO 3166 country code - GB
		Locale.setDefault(new Locale("en", "GB"));

		// Setting Window Height and Width
		setMinimumSize(new Dimension(1024, 768));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setJMenuBar(new MenuBar());

		add(new TabbedMainPanel(), BorderLayout.CENTER);
		add(new StatusPanel(), BorderLayout.PAGE_END);

		updateTitle();

		// Setting project icons
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage());

		// Configuring exit events
		pack();
	}

	/**
	 * Update window title main project
	 */
	public void updateTitle() {
		setTitle("ProteoSuite " + PROTEOSUITE_VERSION + " - <Project: "
				+ WORKSPACE.getWorkSpace());
	}
}
