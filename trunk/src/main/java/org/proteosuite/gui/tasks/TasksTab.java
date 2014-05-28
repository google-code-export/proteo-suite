package org.proteosuite.gui.tasks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
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
    private static final AnalyseData data = AnalyseData.getInstance();
    private TasksTable tasksTable = new TasksTable();
    private final JPanel logArea = new JPanel();
    private Map<BufferedReader, JTextArea> logMap = new LinkedHashMap<>();

    private TasksTab() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(tasksTable), BorderLayout.CENTER);
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

        List<BufferedReader> readers = data.getLogReaders();

        for (final BufferedReader reader : readers) {
            if (!logMap.containsKey(reader)) {
                final JTextArea textArea = new JTextArea();
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                textArea.append(line);
                                if (!line.isEmpty()) {
                                    textArea.append("\n");
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(TasksTab.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        return null;
                    }
                };

                worker.execute();
            }
        }

        logArea.removeAll();
        logArea.setLayout(new GridLayout(1, logMap.size()));
        for (Map.Entry<BufferedReader, JTextArea> entry : logMap.entrySet()) {
            logArea.add(entry.getValue());
        }

        logArea.revalidate();
        this.repaint();
    }
}
