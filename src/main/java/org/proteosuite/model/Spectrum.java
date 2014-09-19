
package org.proteosuite.model;

import java.util.LinkedList;

/**
 *
 * @author SPerkins
 */
public class Spectrum extends LinkedList<MzIntensityPair> {    
    private MzIntensityPair basePeak = new MzIntensityPair(0.0, 0.0);
    private double retentionTimeInSeconds = 0.0;
    private int spectrumIndex = -1;
    private String spectrumID = null;
    public int getSpectraCount() {
        return super.size();
    }

    public void setBasePeak(MzIntensityPair basePeak) {
        this.basePeak = basePeak;
    }
    
    public void setRetentionTimeInMinutes(double minutes) {
        this.retentionTimeInSeconds = minutes * 60;
    }
    
    public void setRetentionTimeInSeconds(double seconds) {
        this.retentionTimeInSeconds = seconds;
    }
    
    public double getRetentionTimeInSeconds() {
        return retentionTimeInSeconds;
    }

    public MzIntensityPair getBasePeak() {
        return basePeak;
    }
    
    public void setSpectrumIndex(int spectrumIndex) {
        this.spectrumIndex = spectrumIndex;
    }
    
    public int getSpectrumIndex() {
        return spectrumIndex;
    }
    
    public void setSpectrumID(String spectrumID) {
        this.spectrumID = spectrumID;
    }
    
    public String getSpectrumID() {
        return this.spectrumID;
    }
}
