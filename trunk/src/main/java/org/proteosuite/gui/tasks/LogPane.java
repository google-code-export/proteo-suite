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
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
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
        this.textPane.setContentType("text/html");
        this.textPane.setEditable(false);
        this.setViewportView(textPane);
    }

    public void append(String appendedString) {
        try {
            HTMLDocument doc = (HTMLDocument) textPane.getDocument();
            HTMLEditorKit editorKit = (HTMLEditorKit) textPane.getEditorKit();
            editorKit.insertHTML(doc, doc.getLength(), appendedString, 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleLog(final Log log) {
        LogProcessor errorOutput = new LogProcessor(log.getStandardError(), true);
        LogProcessor output = new LogProcessor(log.getStandardOutput(), false);

        BackgroundTaskManager.getInstance().submit(errorOutput);
        BackgroundTaskManager.getInstance().submit(output);        
    }

    private class LogProcessor extends BackgroundTask {

        private final BufferedReader reader;
        private final boolean errorOutput;

        public LogProcessor(final BufferedReader reader, final boolean errorOutput) {
            super(new BackgroundTaskSubject() {
                @Override
                public String getSubjectName() {
                    return "External Program Feed";
                }
            }, "Print Output");

            this.reader = reader;
            this.errorOutput = errorOutput;

            this.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
                @Override
                public Void act(BackgroundTaskSubject argument) {
                    try {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (errorOutput) {
                                line = "<font color=\"red\">" + line + "</font>";
                            }
                            
                            if (printAlso) {
                                System.out.println(line);
                            }
                            
                            LogPane.this.append(line + "\n\n");
                            
                        }
                        
                        reader.close();                       
                    } catch (IOException ex) {
                        Logger.getLogger(LogPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    return null;
                }
            });
            
            this.setInvisibility(true);
        }
    }
}
