package org.proteosuite.gui.tables;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Andrew collins
 */
public class JTableMzML extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Index");
		model.addColumn("ID");
		model.addColumn("MS");
		model.addColumn("Base peak m/z");
		model.addColumn("Base peak int");
		model.addColumn("RT (sec)");
		model.addColumn("Precurs m/z");
		setModel(model);

		// jtMzML.setModel(new DefaultTableModel(new String[][] {
		// }, new String[] { "", "Index", "ID", "MS", "Base peak m/z",
		// "Base peak int", "RT (sec)", "Precurs m/z" }));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
