package org.proteosuite.model;

import java.util.ArrayList;
import java.util.List;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;

/**
 *
 * @author SPerkins
 */
public class AnalyseData {

    private final InspectModel inspectModel = new InspectModel();
    
    private final List<RawDataFile> rawDataFiles = new ArrayList<>();
    private String multiplexing = "";
    private boolean supportGenomeAnnotation = false;
    public List<Log> logs = new ArrayList<>();
    private static AnalyseData instance = null;

    private AnalyseData() {
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

    public InspectModel getInspectModel() {
        return inspectModel;
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
        inspectModel.clear();
        supportGenomeAnnotation = false;

        BackgroundTaskManager.getInstance().reset();

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().reset();
    }
}
