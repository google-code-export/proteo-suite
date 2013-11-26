/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils;

import java.util.Arrays;

/**
 *
 * @author SPerkins
 */
public class SpectrumTrimmer {   
    private Number[] mzValues;
    private Number[] intensityValues;
    private int trimmedSize;
    private Number[] trimmedMzValues;
    private Number[] trimmedIntensityValues;
    private boolean trimmed = false;
    public SpectrumTrimmer(final Number[] mzValues, final Number[] intensities) {
        this.mzValues = mzValues;
        this.intensityValues = intensities;
        trimmedMzValues = new Number[mzValues.length];
        trimmedIntensityValues = new Number[intensities.length];
    }
    
    public Number[] getMzValues() {
        if (!trimmed) {
            trimZeros();
        }
        
        return trimmedMzValues;
    }
    
    public Number[] getIntensityValues() {
        if (!trimmed) {
            trimZeros();
        }
        
        return trimmedIntensityValues;
    }
    
    private void trimZeros() {
        trimmedSize = 0;
        for (int i = 0; i < mzValues.length; i++) {
            if (((Double)intensityValues[i] > 0.0d)) {
                trimmedMzValues[trimmedSize] = mzValues[i];
                trimmedIntensityValues[trimmedSize] = intensityValues[i];
                trimmedSize++;
            }
        }
        
        trimmedMzValues = Arrays.copyOf(trimmedMzValues, trimmedSize);
        trimmedIntensityValues = Arrays.copyOf(trimmedIntensityValues, trimmedSize);
        mzValues = null;
        intensityValues = null;
        trimmed = true;
    }  
}
