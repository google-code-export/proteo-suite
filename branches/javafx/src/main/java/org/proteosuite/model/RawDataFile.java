package org.proteosuite.model;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author SPerkins
 */
public abstract class RawDataFile implements Iterable<Spectrum> {
    protected Map<String, Spectrum> cachedSpectra = null;    
    protected boolean cachingComplete = false;
    protected File file;
    protected boolean[] peakPicking = {false, false};
    protected boolean peakPickingChecked;
    protected int spectraCount;
    protected boolean[] msLevelPresence = {false, false};
    protected boolean spectraCountChecked;    
    private final Map<String, String> assayConditions = new HashMap<>();
    private IdentDataFile identFile = null;
    private String identStatus = "<None>";      
   
    
    @Override
    public abstract Iterator<Spectrum> iterator();
    
    public Spectrum getSpectrumByID(String id) {
        if (cachedSpectra.size() > 0) {
            return cachedSpectra.get(id);
        } else {
            for (Spectrum spectrum : this) {
                if (spectrum.getSpectrumID().equals(id)) {
                    return spectrum;
                }
            }
        }
        
        return null;
    }
    
    public RawDataFile(File file) {        
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
