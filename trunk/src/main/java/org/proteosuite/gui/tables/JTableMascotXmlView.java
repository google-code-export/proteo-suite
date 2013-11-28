package org.proteosuite.gui.tables;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Andrew Collins
 */
public class JTableMascotXmlView extends JTableDefault {

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Index");
		model.addColumn("Protein");
		model.addColumn("Peptide");
		model.addColumn("Composition");
		model.addColumn("Exp Mz");
		model.addColumn("Exp Mr");
		model.addColumn("Charge");
		model.addColumn("Score");
		model.addColumn("Scan");
		model.addColumn("Scan ID");
		model.addColumn("RT (sec)");
		setModel(model);
	}
}
