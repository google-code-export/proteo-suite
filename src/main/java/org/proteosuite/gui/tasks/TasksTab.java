package org.proteosuite.gui.tasks;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
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
    private final OutputPane outputPane = OutputPane.getInstance();
    private static Dimension TASKS_TABLE_MINIMUM_SIZE = new Dimension(2000, 300);
    private boolean errorShown = false;
    

    private TasksTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);
        tasksScrollPane.setMinimumSize(TASKS_TABLE_MINIMUM_SIZE);        
        add(tasksScrollPane);        
        add(outputPane);
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
        
        boolean errorExists = BackgroundTaskManager.getInstance().getTasks().stream().anyMatch(p -> p.getStatus().toUpperCase().contains("ERROR"));
        
        if (errorExists && !errorShown) {
            JOptionPane
                        .showConfirmDialog(
                                this,
                                "One of your tasks has encountered an error!\n"
                                + "To see details of an error, please click the task that is reporting the error.\n"
                                        + "The task will display its output, and any error messages.\n"
                                        + "Please click the 'Errors' tab to see serious errors/exceptions.\n"
                                + "The error may be fatal to your analysis."
                                        + "You may want to close ProteoSuite, clean up any output from the erroneous analysis, and restart your analysis.\n"
                                + "You may report errors to the ProteoSuite team and we can advise on a remedy.",
                                "Error In Task", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
            errorShown = true;
        }
        
        this.repaint();
    }
    
   
}
