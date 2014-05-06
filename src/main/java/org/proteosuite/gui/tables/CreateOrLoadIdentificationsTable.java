package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CreateOrLoadIdentificationsTable extends JTable {

    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;

    public CreateOrLoadIdentificationsTable() {
        model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        model.addColumn("File Name");
        model.addColumn("Identifications Status");

        setModel(model);
    }

    public void addRawFileRow(RawDataFile dataFile) {
        model.addRow(new Object[]{dataFile.getFileName(), dataFile.getIdentStatus()});
    }

    public void clear() {
        model.setRowCount(0);
    }
}
