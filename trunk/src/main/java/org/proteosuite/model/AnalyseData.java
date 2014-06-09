package org.proteosuite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;

/**
 * 
 * @author SPerkins
 */
public class AnalyseData {
	private ExecutorService searchGUIExecutor;
	private ExecutorService genericExecutor;
        private ExecutorService openMSExecutor;
	private final InspectModel inspectModel = new InspectModel();
	private final TasksModel tasksModel = new TasksModel();
	private final List<RawDataFile> rawDataFiles = new ArrayList<>();
	private String multiplexing = "";
	private boolean supportGenomeAnnotation = false;
	public static int MAX_THREADS = computeOptimumThreads();
        public List<Log> logs = new ArrayList<>();

	private static AnalyseData instance = null;

	private AnalyseData() {
		// Should really calculate most efficient number of threads to use.
                int threadsForGenericExecutor = (int)Math.floor((double)MAX_THREADS / 2.0) - 1;
                System.out.println("Constraining generic executor to use: " + threadsForGenericExecutor + " threads.");
                if (threadsForGenericExecutor < 1) {
                     System.out.println("Can't use zero threads! Setting to one.");
                     threadsForGenericExecutor = 1;
                }                
                
		genericExecutor = Executors.newFixedThreadPool(threadsForGenericExecutor);
                
		searchGUIExecutor = Executors.newSingleThreadExecutor();
                
                int threadsForOpenMSExecutor = (int)Math.floor((double)MAX_THREADS / 2.0);
                System.out.println("Constraining openMS executor to use: " + threadsForOpenMSExecutor + " threads.");
                if (threadsForOpenMSExecutor < 1) {
                    System.out.println("Can't use zero threads! Setting to one.");
                    threadsForOpenMSExecutor = 1;
                }
                
                openMSExecutor = Executors.newFixedThreadPool(threadsForOpenMSExecutor);
	}

	public static AnalyseData getInstance() {
		if (instance == null) {
			instance = new AnalyseData();
		}

		return instance;
	}
        
        public List<Log> getLogs() {
            return logs;
        }

	public ExecutorService getGenericExecutor() {
		return genericExecutor;
	}

	public ExecutorService getSearchGUIExecutor() {
		return searchGUIExecutor;
	}
        
        public ExecutorService getOpenMSExecutor() {
            return openMSExecutor;
        }

	public InspectModel getInspectModel() {
		return inspectModel;
	}

	public TasksModel getTasksModel() {
		return tasksModel;
	}

	public void addRawDataFile(RawDataFile rawDataFile) {
		synchronized (this) {
			rawDataFiles.add(rawDataFile);
			
		}
	}

	public RawDataFile getRawDataFile(int fileIndex) {
		synchronized (this) {
			return rawDataFiles.get(fileIndex);
		}
	}

	public void deleteRawDataFile(int fileIndex) {
		synchronized (this) {
			rawDataFiles.remove(fileIndex);
		}
	}

	public int getRawDataCount() {
		synchronized (this) {
			return rawDataFiles.size();
		}
	}

	public void setMultiplexing(String multiplexing) {
		this.multiplexing = multiplexing;
	}
        
        public void setGenomeAnnotationMode(boolean genomeAnnotationMode) {
            this.supportGenomeAnnotation = genomeAnnotationMode;
        }
        
        public boolean getGenomeAnnotationMode() {
            return this.supportGenomeAnnotation;
        }

	public String getMultiplexing() {
		return multiplexing;
	}

	public void clear() {
		rawDataFiles.clear();
		multiplexing = "";
		tasksModel.clear();
		inspectModel.clear();
		supportGenomeAnnotation = false;
                openMSExecutor.shutdownNow();
                
		searchGUIExecutor.shutdownNow();
		
                genericExecutor.shutdownNow();
                
                
                int threadsForGenericExecutor = (int)Math.floor((double)MAX_THREADS / 2.0) - 1;
                System.out.println("Constraining generic executor to use: " + threadsForGenericExecutor + " threads.");
                if (threadsForGenericExecutor < 1) {
                     System.out.println("Can't use zero threads! Setting to one.");
                     threadsForGenericExecutor = 1;
                }                
                
		genericExecutor = Executors.newFixedThreadPool(threadsForGenericExecutor);
                
		searchGUIExecutor = Executors.newSingleThreadExecutor();
                
                int threadsForOpenMSExecutor = (int)Math.floor((double)MAX_THREADS / 2.0);
                System.out.println("Constraining openMS executor to use: " + threadsForOpenMSExecutor + " threads.");
                if (threadsForOpenMSExecutor < 1) {
                    System.out.println("Can't use zero threads! Setting to one.");
                    threadsForOpenMSExecutor = 1;
                }
                
                openMSExecutor = Executors.newFixedThreadPool(threadsForOpenMSExecutor);
                
		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().reset();
	}

	private static int computeOptimumThreads() {
            System.out.println("Optimum threads: " + (Runtime.getRuntime().availableProcessors() - 1));
		return Runtime.getRuntime().availableProcessors() - 1;
	}
}
