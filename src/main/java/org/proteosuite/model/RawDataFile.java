package org.proteosuite.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author SPerkins
 */
public abstract class RawDataFile {
    protected Set<Spectrum> ms1Spectra = new TreeSet<>();
    protected Set<FragmentSpectrum> ms2Spectra = new TreeSet<>();
    protected File file;
    protected boolean[] peakPicking = {false, false};
    protected boolean peakPickingChecked;
    protected int spectraCount;
    protected boolean[] msLevelPresence = {false, false};
    protected boolean spectraCountChecked;
    protected boolean guiInteraction = true;
    private Map<String, String> assayConditions = new HashMap<>();
    private IdentDataFile identFile = null;
    private String identStatus = "<None>";  
    
    public RawDataFile(File file) {
        this(file, true);        
    }
    
    public RawDataFile(File file, boolean guiInteraction) {
        this.guiInteraction = guiInteraction;
        this.file = file;
        initiateLoading();
    }
    
    public Map<String, String> getConditions() {
        return assayConditions;
    }
    
    public String getIdentStatus() {
        return identStatus;
    }
    
    public void setIdentStatus(String identStatus) {
        this.identStatus = identStatus;
    }
    
    public void resetAssay() {
        assayConditions.clear();
    }
    
    public void setAssays(String[] assays) {
        for (String assay : assays) {
            if (!assayConditions.containsKey(assay)) {
                assayConditions.put(assay, "");
            }
        }        
    }
    
    public void setIdentificationDataFile(IdentDataFile identFile) {
        this.identFile = identFile;        
    }
    
    public IdentDataFile getIdentificationDataFile() {
        return identFile;
    }
    
    public File getFile() {
        return file;
    }
    
    public int getFileSizeInMegaBytes() {
        long bytes = file.length();
        double megabytes = (double)bytes / (1024.0 * 1024.0);
        return (int)megabytes;
    }
    
    public boolean[] getMSLevelPresence() {
        if (!peakPickingChecked) {
            this.getPeakPicking();
        }
        
        return msLevelPresence;
    }
    
    public abstract boolean isLoaded();
    public abstract String getFormat();
    public abstract int getSpectraCount();    
    public abstract boolean[] getPeakPicking();    
    
    
    public String getFileName() {
        return file.getName();
    }
    
    public String getAbsoluteFileName() {
        return file.getAbsolutePath();
    }
    
    protected abstract void initiateLoading();
}
