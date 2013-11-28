package org.proteosuite.gui.tables;

import javax.swing.table.DefaultTableModel;

public class JTablePeptideQuant extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Peptide");
		model.addColumn("Label 1");
		model.addColumn("Label 2");
		model.addColumn("Label 3");
		model.addColumn("Label 4");
		setModel(model);
	}
}
