package org.proteosuite.gui.tables;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Andrew Collins
 */
public class JTableMzId extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Index");
		model.addColumn("Protein");
		model.addColumn("Peptide");
		model.addColumn("Rank");
		model.addColumn("Score");
		model.addColumn("Spectrum ID");
		setModel(model);
	}
}
