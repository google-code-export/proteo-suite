package org.proteosuite.gui.tasks;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import org.proteosuite.gui.tables.TasksTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.Log;
import org.proteosuite.model.Task;
import org.proteosuite.model.TasksModel;

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
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        logArea.setMaximumSize(new Dimension(500, 300));
        add(logArea, BorderLayout.PAGE_END);
    }

    public static TasksTab getInstance() {
        if (instance == null) {
            instance = new TasksTab();
        }

        return instance;
    }

    public synchronized void refreshFromTasksModel() {
        TasksModel model = data.getTasksModel();
        tasksTable.clear();
        for (Task task : model) {
            tasksTable.addTaskRow(task.getFile(), task.getPhase(),
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
