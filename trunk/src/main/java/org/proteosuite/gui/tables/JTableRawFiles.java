package org.proteosuite.gui.tables;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JTableRawFiles extends JTableDefault {
	
	public void reset()
	{
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");
		setModel(model);
		
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		if (colIndex < 4) {
			return false;
		} else {
			return true;
		}
	}
}
