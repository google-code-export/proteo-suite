package org.proteosuite.gui.tables;

import javax.swing.JTable;

public abstract class JTableDefault extends JTable {
	private static final long serialVersionUID = 1L;

	public JTableDefault()
	{
		reset();
	}
	
	public abstract void reset();
}
