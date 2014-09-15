package org.proteosuite.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.proteosuite.actions.ProteoSuiteAction;

/**
 *
 * @author SPerkins
 */
public class BackgroundTaskManager {
    private ExecutorService genericService = null;
    private ExecutorService searchGUIService = null;
    private ExecutorService openMSService = null;
    private final Set<BackgroundTask> tasks = new LinkedHashSet<>();
    private final int OPTIMUM_THREADS = computeOptimumThreads();
    private static BackgroundTaskManager INSTANCE;
    private ProteoSuiteAction<Object, BackgroundTaskSubject> tasksRefreshAction = null;

    private BackgroundTaskManager() {
        this.reset();
    }

    public static BackgroundTaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BackgroundTaskManager();
        }

        return INSTANCE;
    }
    
    public void setTasksRefreshAction(ProteoSuiteAction<Object, BackgroundTaskSubject> action) {
        this.tasksRefreshAction = action;
    }
    
    public Set<BackgroundTask> getTasks() {
        return this.tasks;
    }

    public void submit(BackgroundTask task) {
        if (tasksRefreshAction != null) {            
            task.setRefreshAction(tasksRefreshAction);            
        }
        
        this.tasks.add(task);        
        
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
                task.queueForExecution(genericService);
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
    }

    private static int computeOptimumThreads() {
        System.out.println("Optimum threads: " + (Runtime.getRuntime().availableProcessors() - 1));
        return Runtime.getRuntime().availableProcessors() - 1;
    }
}