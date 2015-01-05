/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite.gui.listener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.gui.tasks.OutputPane;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;

/**
 *
 * @author SPerkins
 */
public class TasksTableRowListener implements ListSelectionListener {
    private TasksTable table;
    public TasksTableRowListener(TasksTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {        
        int rowSelected = table.getSelectedRow();
        if (rowSelected == -1) {
            return;
        }
        
        String taskID = (String) table.getModel().getValueAt(rowSelected, 0);
        BackgroundTask task = BackgroundTaskManager.getInstance().getTaskOfID(taskID);
        OutputPane.getInstance().showLog(task);       
    }    
}