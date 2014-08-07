package org.proteosuite.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
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
    private final Set<Object> processingResults = new LinkedHashSet<>();
    private final BackgroundTaskSubject taskSubject;
    private String taskStatus = "Pending...";

    public BackgroundTask(BackgroundTaskSubject taskSubject, String taskName) {
        this.taskSubject = taskSubject;
        this.taskName = taskName;
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

    public final void queueForExecution(ExecutorService service) {
        for (ProteoSuiteAction action : BackgroundTask.this.synchronousProcessingActions) {
            processingResults.add(action.act(taskSubject));
        }

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (CountDownLatch condition : BackgroundTask.this.processingConditions) {
                    condition.await();
                }
                
                taskStatus = "In Progress...";
                for (ProteoSuiteAction action : BackgroundTask.this.asynchronousProcessingActions) {
                    processingResults.add(action.act(taskSubject));
                }

                return null;
            }

            @Override
            protected void done() {
                taskStatus = "Complete";
                for (ProteoSuiteAction action : BackgroundTask.this.completionActions) {
                    action.act(taskSubject);
                }
            }
        };

        service.submit(worker);
    }

    public final <T> T getResultOfClass(Class clazz) {
        for (Object object : this.processingResults) {
            if (object.getClass().equals(clazz)) {
                return (T) object;
            }
        }

        return null;
    }
}
