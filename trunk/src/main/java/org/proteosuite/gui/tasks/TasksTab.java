/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.tasks;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.Task;
import org.proteosuite.model.TasksModel;

/**
 *
 * @author SPerkins
 */
public class TasksTab extends JPanel {
    private static TasksTab instance = null;
    private BorderLayout layout = new BorderLayout();
    private TasksTable tasksTable;    
    
    private TasksTab() {
        setLayout(layout);
        
        tasksTable = new TasksTable();
        add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        
    }
    
    public static TasksTab getInstance() {
        if (instance == null) {
            instance = new TasksTab();
        }
        
        return instance;
    }
    
    public synchronized void refreshFromTasksModel() {
        TasksModel model = AnalyseData.getInstance().getTasksModel();
        tasksTable.clear();
        for (Task task : model) {
            tasksTable.addTaskRow(task.getFile(), task.getPhase(), task.getStatus());
        }
    }
}
