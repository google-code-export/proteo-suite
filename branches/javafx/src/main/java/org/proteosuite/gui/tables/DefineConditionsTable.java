package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author SPerkins
 */
public class DefineConditionsTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;

	public DefineConditionsTable() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 0;
			}
		};

		model.addColumn("Condition");
		model.addColumn("File Name");
		model.addColumn("Multiplex Assay");
		setModel(model);
	}

	public void addConditionRow(String condition, String fileName, String assay) {
		model.addRow(new Object[] { condition, fileName, assay });
	}

	public void clear() {
		model.setRowCount(0);
	}

	public boolean isConditionsValid() {
		for (int i = 0; i < getRowCount(); i++) {
			String condition = (String) model.getValueAt(i, 0);
			if (condition.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
