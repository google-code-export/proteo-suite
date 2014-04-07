package org.proteosuite.gui.tasks;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.Task;
import org.proteosuite.model.TasksModel;

/**
 * 
 * @author SPerkins
 */
public class TasksTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private static TasksTab instance = null;
	private TasksTable tasksTable = new TasksTable();

	private TasksTab() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		add(new JScrollPane(tasksTable), BorderLayout.CENTER);
	}

	public static TasksTab getInstance() {
		if (instance == null)
			instance = new TasksTab();

		return instance;
	}

	public synchronized void refreshFromTasksModel() {
		TasksModel model = AnalyseData.getInstance().getTasksModel();
		tasksTable.clear();
		for (Task task : model) {
			tasksTable.addTaskRow(task.getFile(), task.getPhase(),
					task.getStatus());
		}
	}
}
