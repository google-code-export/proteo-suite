package org.proteosuite.model;

import java.io.File;

/**
 *
 * @author SPerkins
 */
public abstract class IdentDataFile {
    protected File file;
    private String loadingStatus = "Done";
    protected boolean computedPSMStats = false;
    protected int psmCountPassingThreshold = 0;
    private int psmCountNotPassingThrehsold = 0;
    private int peptideCountPassingThreshold = 0;
    public IdentDataFile(File file) {
        this.file = file;
        initiateLoading();
    }
    
    public String getLoadingStatus() {
        return loadingStatus;
    }
    
    public String getAbsoluteFileName() {
        return file.getAbsolutePath();
    }
    
    public String getFileName() {
        return file.getName();
    }
    
    public abstract String getFormat();
    
    public abstract boolean isLoaded();
    protected abstract void initiateLoading();
    protected abstract void computePSMStats();
    public abstract int getPSMCountPassingThreshold();
    public abstract int getPSMCountNotPassingThreshold();
    public abstract int getPeptideCountPassingThreshold();
    public abstract String getThresholdingUsed();
}
