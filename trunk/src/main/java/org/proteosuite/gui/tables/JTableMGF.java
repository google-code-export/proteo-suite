package org.proteosuite.gui.tables;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Andrew Collins
 */
public class JTableMGF extends JTableDefault {

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model6 = new DefaultTableModel();
		model6.addColumn("Index");
		model6.addColumn("Scan Title");
		model6.addColumn("Peptide Mass");
		model6.addColumn("Charge");
		model6.addColumn("Reference Line");
		setModel(model6);
	}
}
