package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.gui.listener.TasksTableRowListener;

/**
 *
 * @author SPerkins
 */
public class TasksTable extends JTable {
	private static final long serialVersionUID = 1L;
	private final DefaultTableModel model;
    public TasksTable() {
        model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("Task ID");
        model.addColumn("Name");
        model.addColumn("Phase");
        model.addColumn("Status");               
        
        setModel(model);    
        getColumnModel().removeColumn(getColumnModel().getColumn(0));
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(new TasksTableRowListener(this));
    }
    
    public void addTaskRow(String taskID, String file, String phase, String status) {
        model.addRow(new Object[]{taskID, file, phase, status});
    }
    
    public void clear() {
        model.setRowCount(0);
    }
}
