package org.proteosuite.gui.tables;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JTableIdentFiles extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		if (colIndex < 4) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");
		model.addColumn("Raw File");
		setModel(model);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
