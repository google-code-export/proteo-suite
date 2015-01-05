package org.proteosuite.model;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.proteosuite.actions.ProteoSuiteAction;

/**
 *
 * @author SPerkins
 */
public class BackgroundTaskManager {

    private ExecutorService genericService = null;
    private ExecutorService searchGUIService = null;
    private ExecutorService openMSService = null;
    private boolean openMSThreadsExclusive = true;
    private ExecutorService trivialService = null;
    private final Queue<BackgroundTask> tasks = new ConcurrentLinkedQueue<>();
    private final int OPTIMUM_THREADS = computeOptimumThreads();
    private static BackgroundTaskManager INSTANCE;
    private ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject> tasksRefreshAction = null;
    private int idCounter = 0;

    private BackgroundTaskManager() {
        this.reset();
    }

    public void freeMoreThreadsForGenericExecution() {
        openMSThreadsExclusive = false;
    }
    
    public Set<BackgroundTask> getTasksOfType(String type) {
        return tasks.stream().filter(p -> p.getName().toUpperCase().equals(type.toUpperCase())).collect(Collectors.toSet());
    }
    
    public BackgroundTask getTaskOfID(String id) {
        return tasks.stream().filter(p -> p.getID().equals(id)).findFirst().get();
    }

    public static BackgroundTaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BackgroundTaskManager();
        }

        return INSTANCE;
    }

    public void setTasksRefreshAction(ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject> action) {
        this.tasksRefreshAction = action;
    }

    public Queue<BackgroundTask> getTasks() {
        return this.tasks;
    }

    public void submit(BackgroundTask task) {
        task.setID("t" + (idCounter++));
        if (tasksRefreshAction != null) {
            task.setRefreshAction(tasksRefreshAction);
        }

        this.tasks.add(task);

        if (task.isSlave()) {
            task.queueForExecution(trivialService);
            return;
        }

        switch (task.getName().toUpperCase()) {
            case "CREATE IDENTIFICATIONS":
            case "RUN GENOME ANNOTATION":                
                task.queueForExecution(searchGUIService);                
                break;
            case "FINDING FEATURES":
            case "ALIGNING FEATURES":
            case "LINKING FEATURES":
                task.queueForExecution(openMSService);
                break;
            default:
                if (!openMSThreadsExclusive) {
                    task.queueForExecution(openMSService);
                } else {
                    task.queueForExecution(genericService);
                }

                break;
        }
    }

    public final void reset() {
        if (genericService != null) {
            genericService.shutdownNow();
        }

        if (searchGUIService != null) {
            searchGUIService.shutdownNow();
        }
        
        if (openMSService != null) {
            openMSService.shutdownNow();
        }

        if (trivialService != null) {
            trivialService.shutdownNow();
        }

        int threadsForGenericExecutor = (int) Math.floor((double) OPTIMUM_THREADS / 2.0) - 1;
        System.out.println("Constraining generic executor to use: " + threadsForGenericExecutor + " threads.");
        if (threadsForGenericExecutor < 1) {
            System.out.println("Can't use zero threads! Setting to one.");
            threadsForGenericExecutor = 1;
        }

        genericService = Executors.newFixedThreadPool(threadsForGenericExecutor);

        searchGUIService = Executors.newSingleThreadExecutor();
        int threadsForOpenMSExecutor = (int) Math.floor((double) OPTIMUM_THREADS / 2.0);
        System.out.println("Constraining openMS executor to use: " + threadsForOpenMSExecutor + " threads.");
        if (threadsForOpenMSExecutor < 1) {
            System.out.println("Can't use zero threads! Setting to one.");
            threadsForOpenMSExecutor = 1;
        }

        openMSService = Executors.newFixedThreadPool(threadsForOpenMSExecutor);

        trivialService = Executors.newCachedThreadPool();
    }

    private static int computeOptimumThreads() {
        return Runtime.getRuntime().availableProcessors();
    }
}
