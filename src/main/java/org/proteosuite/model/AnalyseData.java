/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author SPerkins
 */
public class AnalyseData {
    private ExecutorService msgfExecutor;
    private ExecutorService executor;
    private InspectModel inspectModel = new InspectModel();
    private TasksModel tasksModel = new TasksModel();
    private List<RawDataFile> rawDataFiles = new ArrayList<RawDataFile>();    
    private String multiplexing = "";
    private boolean supportGenomeAnnotation = false;
    
    private static AnalyseData instance = null;
    
    private AnalyseData() {
        // Should really calculate most efficient number of threads to use.
        executor = Executors.newFixedThreadPool(4);
        msgfExecutor = Executors.newSingleThreadExecutor();
    }
    
    public static AnalyseData getInstance() {
        if (instance == null) {
            instance = new AnalyseData();
        }
        
        return instance;
    }
    
    public ExecutorService getExecutor() {
        return executor;
    }
    
    public ExecutorService getMSGFPlusExecutor() {
        return msgfExecutor;
    }
    
    public InspectModel getInspectModel() {
        return inspectModel;
    }
    
    public TasksModel getTasksModel() {
        return tasksModel;
    }
    
    public void addRawDataFile(RawDataFile rawDataFile) {
        synchronized(this) {            
            rawDataFiles.add(rawDataFile);
        }
    }
    
    public RawDataFile getRawDataFile(int fileIndex) {
        synchronized(this) {            
            return rawDataFiles.get(fileIndex);
        }
    }
    
    public void deleteRawDataFile(int fileIndex) {
        synchronized(this) {
            rawDataFiles.remove(fileIndex);
        }
    }
    
    public int getRawDataCount() {
        synchronized(this) {
            return rawDataFiles.size();
        }
    }
    
    public void setMultiplexing(String multiplexing) {
        this.multiplexing = multiplexing;
    }
    
    public String getMultiplexing() {
        return multiplexing;
    }
    
    public void clear() {
        rawDataFiles.clear();
        multiplexing = "";
    }
}
