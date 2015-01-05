/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.tasks;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;

/**
 *
 * @author SPerkins
 */
public class OutputPane extends JPanel {

    private final Map<BackgroundTask, JTabbedPane> taskToPane = new HashMap<>();
    private static OutputPane INSTANCE = null;

    public static OutputPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OutputPane();
        }

        return INSTANCE;
    }

    private OutputPane() {
        setLayout(new BorderLayout());
    }

    public void showLog(BackgroundTask task) {
        if (task.getOutputQueue() == null && task.getErrorQueue() == null) {
            this.removeAll();
            this.revalidate();
            this.repaint();
            return;
        }

        JTabbedPane pane;
        JTextPane outputTextPane;
        JTextPane errorTextPane;
        if (taskToPane.containsKey(task)) {
            pane = taskToPane.get(task);
        } else {
            pane = new JTabbedPane();
            outputTextPane = new JTextPane();
            outputTextPane.setContentType("text/html");
            outputTextPane.setEditable(false);
            errorTextPane = new JTextPane();
            errorTextPane.setContentType("text/html");
            errorTextPane.setEditable(false);
            JScrollPane outputScrollPane = new JScrollPane(outputTextPane);
            outputScrollPane.setAutoscrolls(true);
            outputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            JScrollPane errorScrollPane = new JScrollPane(errorTextPane);
            errorScrollPane.setAutoscrolls(true);
            errorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            errorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            pane.addTab("Output", null, outputScrollPane,
                    "Read the output from the chosen task.");
            pane.addTab("Errors", null, errorScrollPane, "Read the errors from the chosen task.");
            taskToPane.put(task, pane);

            readQueue(task.getErrorQueue(), errorTextPane, true);
            readQueue(task.getOutputQueue(), outputTextPane, false);
        }

        this.removeAll();
        this.add(pane);
        this.revalidate();
        this.repaint();
    }

    private void readQueue(BlockingQueue<String> queue, JTextPane pane, boolean exceptionQueue) {
        if (queue == null) {
            return;
        }

        long timeOut = exceptionQueue ? 60 : 25;
        BackgroundTask task = new BackgroundTask(() -> "External Program Feed", "Print Output");
        task.setSlaveStatus(true);
        task.setInvisibility(true);
        task.addAsynchronousProcessingAction((ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>) (ProteoSuiteActionSubject argument) -> {
            try {
                String text;
                while ((text = queue.poll(timeOut, TimeUnit.MINUTES)) != null) {
                    if (text.toUpperCase().equals("<END>")) {
                        break;
                    }

                    append(text, pane);
                }
            }
            catch (InterruptedException ex) {
                append("Exception in queue reading. Please report this to ProteoSuite developers.", pane);
                Logger.getLogger(OutputPane.class.getName()).log(Level.SEVERE, null, ex);
            }

            return ProteoSuiteActionResult.emptyResult();
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void append(String appendedString, JTextPane pane) {
        try {
            HTMLDocument doc = (HTMLDocument) pane.getDocument();
            HTMLEditorKit editorKit = (HTMLEditorKit) pane.getEditorKit();
            editorKit.insertHTML(doc, doc.getLength(), appendedString, 0, 0, null);
            pane.revalidate();
            pane.repaint();
            pane.getParent().revalidate();
            pane.getParent().repaint();
        }
        catch (BadLocationException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
