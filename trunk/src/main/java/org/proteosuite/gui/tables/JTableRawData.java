package org.proteosuite.gui.tables;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JTableRawData extends JTableDefault {
	
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");
		setModel(model);
		
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

}
