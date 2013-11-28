package org.proteosuite.gui;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class TabbedProperties extends JTabbedPane {

	public TabbedProperties()
	{
	}

	public TabbedProperties(JTable jtMzML) {
		setBackground(new Color(255, 255, 255));
	}
}
