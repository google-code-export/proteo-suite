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

	private static AnalyseData instance = null;

	private AnalyseData() {
		// Should really calculate most efficient number of threads to use.
		genericExecutor = Executors.newFixedThreadPool((int)Math.floor((double)MAX_THREADS / 2.0) - 1);
		searchGUIExecutor = Executors.newSingleThreadExecutor();
                openMSExecutor = Executors.newFixedThreadPool((int)Math.floor((double)MAX_THREADS / 2.0));
	}

	public static AnalyseData getInstance() {
		if (instance == null) {
			instance = new AnalyseData();
		}

		return instance;
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
                openMSExecutor = Executors.newFixedThreadPool((int)Math.floor((double)MAX_THREADS / 2.0));
		searchGUIExecutor.shutdownNow();
		searchGUIExecutor = Executors.newSingleThreadExecutor();
                genericExecutor.shutdownNow();
                genericExecutor = Executors.newFixedThreadPool((int)Math.floor((double)MAX_THREADS / 2.0) - 1);		
		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().reset();
	}

	private static int computeOptimumThreads() {
		return Runtime.getRuntime().availableProcessors() - 1;
	}
}
