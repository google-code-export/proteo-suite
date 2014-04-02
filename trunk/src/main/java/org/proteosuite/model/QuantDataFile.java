package org.proteosuite.model;

import java.io.File;

/**
 *
 * @author SPerkins
 */
public abstract class QuantDataFile {
    protected File file;
    
    public QuantDataFile(File file) {
        this.file = file;
        initiateLoading();
    }
    
    public String getFileName() {
        return file.getName();
    }
    
    public File getFile() {
        return file;
    }
    
    public String getAbsoluteFileName() {
        return file.getAbsolutePath();
    }
    
    public abstract boolean isLoaded();
    public abstract void initiateLoading();
}
