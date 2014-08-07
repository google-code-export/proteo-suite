package org.proteosuite.gui.tasks;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.Log;

/**
 *
 * @author SPerkins
 */
public class TasksTab extends JPanel {

    private static final long serialVersionUID = 1L;
    private static TasksTab instance = null;
    private static final AnalyseData data = AnalyseData.getInstance();
    private final TasksTable tasksTable = new TasksTable();
    private final JPanel logArea = new JPanel();
    private Map<Log, LogPane> logMap = new LinkedHashMap<>();

    private TasksTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(tasksTable));
        logArea.setMaximumSize(new Dimension(2000, 300));
        add(logArea);
    }

    public static TasksTab getInstance() {
        if (instance == null) {
            instance = new TasksTab();
        }

        return instance;
    }

    public synchronized void refreshData() {
        
        tasksTable.clear();
        for (BackgroundTask task : BackgroundTaskManager.getInstance().getTasks()) {
            tasksTable.addTaskRow(task.getSubject().getSubjectName(), task.getName(),
                    task.getStatus());
        }

        List<Log> logs = data.getLogs();

        for (final Log log : logs) {
            if (!logMap.containsKey(log)) {
                LogPane thisLogPane = new LogPane(true);                
                thisLogPane.handleLog(log);                
                logMap.put(log, thisLogPane);
            }
        }

        logArea.removeAll();
        logArea.setLayout(new GridLayout(1, logMap.size()));
        for (Map.Entry<Log, LogPane> entry : logMap.entrySet()) {
            logArea.add(entry.getValue());
        }

        logArea.revalidate();
        this.repaint();
    }
}
