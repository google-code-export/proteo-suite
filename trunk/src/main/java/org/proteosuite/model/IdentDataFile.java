package org.proteosuite.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SPerkins
 */
public abstract class IdentDataFile {
    protected RawDataFile parent;
    protected File file;    
    protected boolean computedPSMStats = false;
    protected int psmCountPassingThreshold = -1;
    protected int psmCountNotPassingThrehsold = -1;
    protected int peptideCountPassingThreshold = -1;
    protected String thresholdingUsed = "";
    protected Map<String, String> thresholdables = new HashMap<String, String>(); 
    public IdentDataFile(File file, RawDataFile parent) {
        this.file = file;
        this.parent = parent;
        initiateLoading();
    } 
    
    public String getAbsoluteFileName() {
        return file.getAbsolutePath();
    }
    
    public String getFileName() {
        return file.getName();
    }   
    
    public RawDataFile getParent() {
        return parent;
    }
    
    public abstract String getFormat();
    
    public abstract boolean isLoaded();
    protected abstract void initiateLoading();
    protected abstract void computePSMStats();
    public abstract int getPSMCountPassingThreshold();
    public abstract int getPSMCountNotPassingThreshold();
    public abstract int getPeptideCountPassingThreshold();
    public abstract String getThresholdingUsed();
    public abstract Map<String, String> getThresholdables();
}
