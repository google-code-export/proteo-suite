/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SPerkins
 */
public class InspectModel {
    private List<RawDataFile> rawData = new ArrayList<RawDataFile>();
    private List<IdentDataFile> identData = new ArrayList<IdentDataFile>();
    private List<QuantDataFile> quantData = new ArrayList<QuantDataFile>();
    
    public synchronized void addRawDataFile(RawDataFile rawDataFile) {
        rawData.add(rawDataFile);
    }
    
    public synchronized List<RawDataFile> getRawData() {
        return rawData;
    }
    
    public synchronized void addIdentDataFile(IdentDataFile identDataFile) {
        identData.add(identDataFile);
    }
    
    public synchronized List<IdentDataFile> getIdentData() {
        return identData;
    }
    
    public synchronized void addQuantDataFile(QuantDataFile quantDataFile) {
        quantData.add(quantDataFile);
    }
}