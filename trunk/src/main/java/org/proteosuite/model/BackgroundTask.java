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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.proteosuite.Launcher;
import org.proteosuite.actions.Actions;
import org.proteosuite.actions.ProteoSuiteAction;

/**
 *
 * @author SPerkins
 */
public class BackgroundTask<T extends ProteoSuiteActionSubject> {

    private final String taskName;
    private final Set<CountDownLatch> processingConditions = new HashSet<>();
    private final Set<ProteoSuiteAction<ProteoSuiteActionResult, T>> synchronousProcessingActions = new LinkedHashSet<>();
    private final Set<ProteoSuiteAction<ProteoSuiteActionResult, T>> asynchronousProcessingActions = new LinkedHashSet<>();
    private final Set<ProteoSuiteAction<ProteoSuiteActionResult, T>> completionActions = new LinkedHashSet<>();
    private ProteoSuiteAction<ProteoSuiteActionResult, T> refreshAction;
    private final Set<ProteoSuiteActionResult> processingResults = new LinkedHashSet<>();
    private final T taskSubject;
    private final CountDownLatch taskLatch = new CountDownLatch(1);
    private BlockingQueue<String> errorQueue = null;
    private BlockingQueue<String> outputQueue = null;
    private TaskStreams streams = null;
    private String id = null;
    private boolean invisibility = false;
    private boolean slave = false;
    private String taskStatus = "Pending...";
    private boolean inErrorState = false;

    public BackgroundTask(T taskSubject, String taskName) {
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
        if (inErrorState) {
            return "Error: Click For Information";
        }

        return this.taskStatus;
    }

    public final ProteoSuiteActionSubject getSubject() {
        return this.taskSubject;
    }

    public final void addProcessingCondition(CountDownLatch condition) {
        if (condition != null) {
            processingConditions.add(condition);
        }
    }

    public final void addSynchronousProcessingAction(ProteoSuiteAction<ProteoSuiteActionResult, T> action) {
        this.synchronousProcessingActions.add(action);
    }

    public final void addAsynchronousProcessingAction(ProteoSuiteAction<ProteoSuiteActionResult, T> action) {
        this.asynchronousProcessingActions.add(action);
    }

    public final void addCompletionAction(ProteoSuiteAction<ProteoSuiteActionResult, T> action) {
        this.completionActions.add(action);
    }

    public final void setRefreshAction(ProteoSuiteAction<ProteoSuiteActionResult, T> action) {
        this.refreshAction = action;
    }

    public CountDownLatch getTaskLatch() {
        return this.taskLatch;
    }  

    public final void queueForExecution(ExecutorService service) {
        if (refreshAction == null) {
            refreshAction = Actions.emptyAction(taskSubject);
        }

        if (!invisibility) {            
            refreshAction.act(taskSubject);
        }

        BackgroundTask.this.synchronousProcessingActions.stream().forEach((action) -> {
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
                try {
                    String taskResult = get();
                    if (!taskResult.equals("OK")) {
                        inErrorState = true;
                    }
                }
                catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(BackgroundTask.class.getName()).log(Level.SEVERE, null, ex);
                    inErrorState = true;
                }

                taskStatus = "Complete";
                if (!invisibility) {
                    refreshAction.act(taskSubject);
                }

                BackgroundTask.this.completionActions.stream().forEach(action -> {
                    action.act(taskSubject);
                });
                
                processingResults.stream().filter((result) -> (result.hasException())).forEach((result) -> {
                    Launcher.handleException(result.getException());
                });

                taskLatch.countDown();
            }
        };

        try {
            service.submit(worker);
        }
        catch (RejectedExecutionException ex) {
            System.out.println("Failed to schedule task for execution.");
            inErrorState = true;
        }
    }

    public void setStreams(TaskStreams streams) {
        this.streams = streams;
        errorQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();
        readStreams();

    }

    public final <S> S getResultOfClass(Class<S> clazz) {
        for (ProteoSuiteActionResult result : this.processingResults) {
            Object resultValue = result.getResultObject();
            if (resultValue != null && resultValue.getClass().equals(clazz)) {
                return (S) resultValue;
            }
        }

        return (S)null;
    }

    private void readStreams() {
        if (streams != null && streams.getOutputStream() != null) {
            BackgroundTaskManager.getInstance().submit(new StreamReader(streams.getOutputStream(), false, true));
        }

//        try {
//            Thread.sleep(5000);
//        }
//        catch (InterruptedException ex) {
//            Logger.getLogger(BackgroundTask.class.getName()).log(Level.SEVERE, null, ex);
//        }

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

    private class StreamReader extends BackgroundTask<ProteoSuiteActionSubject> {

        private boolean printAlso;
        private boolean errorOutput;
        private InputStream stream;

        public StreamReader(InputStream streamIn, boolean errorOutputIn, boolean printAlsoIn) {
            super(() -> "External Program Feed", "Print Output");

            this.stream = streamIn;
            this.errorOutput = errorOutputIn;
            this.printAlso = printAlsoIn;
            super.setSlaveStatus(true);
            super.setInvisibility(true);

            super.addAsynchronousProcessingAction((ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>) (ProteoSuiteActionSubject ignored) -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (this.printAlso) {
                            System.out.println(line);
                        }

                        if (this.errorOutput) {
                            line = "<font color=\"red\">" + line + "</font>";
                            errorQueue.add(line);
                            inErrorState = true;
                            refreshAction.act(taskSubject);
                        } else {
                            if (line.toUpperCase().contains("EXCEPTION")) {
                                inErrorState = true;
                            }

                            outputQueue.add(line);
                        }
                    }

                    if (this.errorOutput) {
                        errorQueue.add("<END>");
                    } else {
                        outputQueue.add("<END>");
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(BackgroundTask.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return ProteoSuiteActionResult.emptyResult();
            });
        }
    }
}
