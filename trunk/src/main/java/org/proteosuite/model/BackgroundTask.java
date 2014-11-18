package org.proteosuite.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.proteosuite.actions.EmptyAction;
import org.proteosuite.actions.ProteoSuiteAction;

/**
 *
 * @author SPerkins
 */
public class BackgroundTask {

    private final String taskName;
    private final Set<CountDownLatch> processingConditions = new HashSet<>();
    private final Set<ProteoSuiteAction<BackgroundTaskSubject, Object>> synchronousProcessingActions = new LinkedHashSet<>();
    private final Set<ProteoSuiteAction<BackgroundTaskSubject, Object>> asynchronousProcessingActions = new LinkedHashSet<>();
    private final Set<ProteoSuiteAction<BackgroundTaskSubject, Object>> completionActions = new LinkedHashSet<>();
    private ProteoSuiteAction<Object, BackgroundTaskSubject> refreshAction;
    private final Set<Object> processingResults = new LinkedHashSet<>();
    private final BackgroundTaskSubject taskSubject;
    private final CountDownLatch taskLatch = new CountDownLatch(1);
    private final BlockingQueue<String> errorQueue = new SynchronousQueue<>();
    private final BlockingQueue<String> outputQueue = new SynchronousQueue<>();
    private TaskStreams streams = null;
    private String id = null;
    private boolean invisibility = false;
    private boolean slave = false;
    private String taskStatus = "Pending...";

    public BackgroundTask(BackgroundTaskSubject taskSubject, String taskName) {
        this.taskSubject = taskSubject;
        this.taskName = taskName;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

    public void setSlaveStatus(boolean slaveStatus) {
        this.slave = slaveStatus;
    }

    public boolean isSlave() {
        return this.slave;
    }

    public void setInvisibility(boolean invisibility) {
        this.invisibility = invisibility;
    }

    public boolean isInvisible() {
        return this.invisibility;
    }

    public final String getName() {
        return this.taskName;
    }

    public final String getStatus() {
        return this.taskStatus;
    }

    public final BackgroundTaskSubject getSubject() {
        return this.taskSubject;
    }

    public final void addProcessingCondition(CountDownLatch condition) {
        if (condition != null) {
            processingConditions.add(condition);
        }
    }

    public final void addSynchronousProcessingAction(ProteoSuiteAction action) {
        this.synchronousProcessingActions.add(action);
    }

    public final void addAsynchronousProcessingAction(ProteoSuiteAction action) {
        this.asynchronousProcessingActions.add(action);
    }

    public final void addCompletionAction(ProteoSuiteAction action) {
        this.completionActions.add(action);
    }

    public final void setRefreshAction(ProteoSuiteAction<Object, BackgroundTaskSubject> action) {
        this.refreshAction = action;
    }

    public CountDownLatch getTaskLatch() {
        return this.taskLatch;
    }

    public final void queueForExecution(ExecutorService service) {
        if (refreshAction == null) {
            refreshAction = EmptyAction.emptyAction();
        }

        if (!invisibility) {
            refreshAction.act(taskSubject);
        }

        BackgroundTask.this.synchronousProcessingActions.stream().forEach(action -> {
            processingResults.add(action.act(taskSubject));
        });

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                for (CountDownLatch condition : BackgroundTask.this.processingConditions) {
                    condition.await();
                }

                taskStatus = "In Progress...";
                if (!invisibility) {
                    refreshAction.act(taskSubject);
                }

                BackgroundTask.this.asynchronousProcessingActions.stream().forEach(action -> {
                    processingResults.add(action.act(taskSubject));
                });

                return "OK";
            }

            @Override
            protected void done() {
                taskStatus = "Complete";
                if (!invisibility) {
                    refreshAction.act(taskSubject);
                }

                BackgroundTask.this.completionActions.stream().forEach(action -> {
                    action.act(taskSubject);
                });

                taskLatch.countDown();
            }
        };

        service.submit(worker);
    }

    public void setStreams(TaskStreams streams) {
        this.streams = streams;
        readStreams();

    }

    public final <T> T getResultOfClass(Class clazz) {
        for (Object object : this.processingResults) {
            if (object != null && object.getClass().equals(clazz)) {
                return (T) object;
            }
        }

        return null;
    }

    private void readStreams() {
        if (streams != null && streams.getOutputStream() != null) {
            BackgroundTaskManager.getInstance().submit(new StreamReader(streams.getOutputStream(), false, true));
        }

        if (streams != null && streams.getErrorStream() != null) {
            BackgroundTaskManager.getInstance().submit(new StreamReader(streams.getErrorStream(), true, true));
        }
    }
    
    public BlockingQueue getOutputQueue() {
        return this.outputQueue;
    }
    
    public BlockingQueue getErrorQueue() {
        return this.errorQueue;
    }

    private class StreamReader extends BackgroundTask {

        private boolean printAlso;
        private boolean errorOutput;
        private InputStream stream;

        public StreamReader(InputStream streamIn, boolean errorOutputIn, boolean printAlsoIn) {
            super(() -> "External Program Feed", "Print Output");

            this.stream = streamIn;
            this.errorOutput = errorOutputIn;
            this.printAlso = printAlsoIn;
            super.setSlaveStatus(true);

            super.addAsynchronousProcessingAction((ProteoSuiteAction<Object, BackgroundTaskSubject>) (BackgroundTaskSubject argument) -> {
                try {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (printAlso) {
                                System.out.println(line);
                            }
                            
                            if (errorOutput) {
                                line = "<font color=\"red\">" + line + "</font>";
                                errorQueue.add(line);
                                taskStatus = "Error: Click For Information";
                                refreshAction.act(argument);
                                
                            } else {
                                outputQueue.add(line);
                            }
                        }
                        
                        if (errorOutput) {
                            errorQueue.add("<END>");
                        } else {
                            outputQueue.add("<END>");
                        }
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(BackgroundTask.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return null;
            });
        }
    }
}