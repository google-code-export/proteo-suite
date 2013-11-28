package org.proteosuite.gui.tables;

import javax.swing.table.DefaultTableModel;

public class JTableFeatureQuant extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model11 = new DefaultTableModel();
		model11.addColumn("Feature");
		model11.addColumn("Label 1");
		model11.addColumn("Label 2");
		model11.addColumn("Label 3");
		model11.addColumn("Label 4");
		setModel(model11);
	}
}
