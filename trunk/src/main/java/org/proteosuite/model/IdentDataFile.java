/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.io.File;

/**
 *
 * @author SPerkins
 */
public abstract class IdentDataFile {
    protected File file;
    private String loadingStatus = "Loading...";
    public IdentDataFile(File file) {
        this.file = file;
        initiateLoading();
    }
    
    public String getLoadingStatus() {
        return loadingStatus;
    }
    
    protected abstract void initiateLoading();
}
