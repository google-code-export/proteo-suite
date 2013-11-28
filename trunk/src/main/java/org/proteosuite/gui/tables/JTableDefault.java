package org.proteosuite.gui.tables;

import javax.swing.JTable;

public abstract class JTableDefault extends JTable {


	public JTableDefault()
	{
		reset();
	}
	
	public abstract void reset();
}
