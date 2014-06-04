/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.proteosuite.model.Log;

/**
 *
 * @author SPerkins
 */
public class LogPane extends JScrollPane {   
    private boolean printAlso = false;
    private final JTextPane textPane;

    public LogPane(boolean printAlso) {        
        this.printAlso = printAlso;
        this.textPane = new JTextPane();    
        this.setViewportView(textPane);
    }

    public void append(String appendedString) {
        try {
            Document doc = textPane.getDocument();
            doc.insertString(doc.getLength(), appendedString, null);
            textPane.revalidate();
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }

    public void handleLog(final Log log) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    BufferedReader outputReader = log.getStandardOutput();
                    BufferedReader errorReader = log.getStandardError();
                    String outputLine = null;
                    String errorLine = null;
                    while ((outputLine = outputReader.readLine()) != null || (errorLine = errorReader.readLine()) != null) {
                        if (outputLine != null) {
                            LogPane.this.append(outputLine + "\n");
                            if (printAlso) {
                                System.out.println(outputLine);
                            }
                        }

                        if (errorLine != null) {
                            LogPane.this.append("<font color=\"red\">" +errorLine + "</font>\n");
                            if (printAlso) {
                                System.out.println(errorLine);
                            }
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
