/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.io.File;
import java.util.HashMap;

/**
 *
 * @author SPerkins
 */
public abstract class RawDataFile {
    protected File file;
    protected boolean[] peakPicking = {false, false};
    protected boolean peakPickingChecked;
    protected int spectraCount;
    protected boolean spectraCountChecked;
    private HashMap<String, String> assayConditions = new HashMap<String, String>();
    private IdentDataFile identFile = null;
    public RawDataFile(File file) {
        this.file = file;        
        initiateLoading();
    }
    
    public HashMap<String, String> getConditions() {
        return assayConditions;
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
    
    public int getFileSize() {
        long bytes = file.length();
        double megabytes = (double)bytes / (1024.0 * 1024.0);
        return (int)megabytes;
    }
    
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
