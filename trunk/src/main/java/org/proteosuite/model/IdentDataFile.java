package org.proteosuite.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SPerkins
 */
public abstract class IdentDataFile implements BackgroundTaskSubject {
    protected RawDataFile parent;
    protected File file;    
    protected boolean computedPSMStats = false;
    protected int psmCountPassingThreshold = -1;
    protected int psmCountNotPassingThrehsold = -1;
    protected int peptideCountPassingThreshold = -1;
    protected String thresholdingUsed = "";
    protected Map<String, String> thresholdables = new HashMap<>(); 
    private String thresholdStatus = "Needed";
     
    private boolean mayNeedCleaning = false;
    public IdentDataFile(File file, RawDataFile parent) {
        this.file = file;
        this.parent = parent;        
        initiateLoading();
    } 
    
    public String getAbsoluteFileName() {
        return file.getAbsolutePath();
    }
    
    public void setThresholdStatus(String status) {
        this.thresholdStatus = status;
    }
    
    public String getThresholdStatus() {
        return mayNeedCleaning ? thresholdStatus : "Complete";
    }
    
    public String getFileName() {
        return file.getName();
    }

	public File getFile() {
		return file;
	}
	
    public RawDataFile getParent() {
        return parent;
    }
    
    public void setCleanable(boolean cleanable) {
        this.mayNeedCleaning = cleanable;
    } 
    
    public boolean isCleanable() {
        return this.mayNeedCleaning;
    }
    
    public abstract String getFormat();
    
    public abstract boolean isLoaded();
    protected abstract void initiateLoading();
    public abstract void computePSMStats();
    public abstract int getPSMCountPassingThreshold();
    public abstract int getPSMCountNotPassingThreshold();
    public abstract int getPeptideCountPassingThreshold();
    public abstract String getThresholdingUsed();
    public abstract Map<String, String> getThresholdables();
}
