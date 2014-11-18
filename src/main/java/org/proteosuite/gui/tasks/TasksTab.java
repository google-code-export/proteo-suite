package org.proteosuite.gui.tasks;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTaskManager;

/**
 *
 * @author SPerkins
 */
public class TasksTab extends JPanel {

    private static final long serialVersionUID = 1L;
    private static TasksTab instance = null;
    private static final AnalyseData data = AnalyseData.getInstance();
    private final TasksTable tasksTable = new TasksTable();
    private final OutputPane logPane = OutputPane.getInstance();
    

    private TasksTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(tasksTable));
        logPane.setMaximumSize(new Dimension(2000, 300));
        add(logPane);
    }

    public static TasksTab getInstance() {
        if (instance == null) {
            instance = new TasksTab();
        }

        return instance;
    }

    public synchronized void refreshData() {

        tasksTable.clear();
        BackgroundTaskManager.getInstance().getTasks().stream().filter((task) -> (!task.isInvisible())).forEach((task) -> {
            tasksTable.addTaskRow(task.getID(), task.getSubject().getSubjectName(), task.getName(),
                    task.getStatus());
        });
        
        this.repaint();
    }
}
