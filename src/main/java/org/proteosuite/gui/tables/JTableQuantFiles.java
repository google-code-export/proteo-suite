package org.proteosuite.gui.tables;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class JTableQuantFiles extends JTableDefault {

	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");
		setModel(model);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
