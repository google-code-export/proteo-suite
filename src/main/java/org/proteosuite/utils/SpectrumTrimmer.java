/**
 * --------------------------------------------------------------------------
 * SpectrumTrimmer.java
 * --------------------------------------------------------------------------
 * Description:       Utility to trim zero intensity pairs.
 * Developer:         SPerkins
 * Created:           26 November 2013
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

import java.util.Arrays;

/**
 * Class for trimming spectra of zero intensity pairs.
 * @author SPerkins
 */
public class SpectrumTrimmer {
    private Number[] mzValues;
    private Number[] intensityValues;
    private int trimmedSize;
    private Number[] trimmedMzValues;
    private Number[] trimmedIntensityValues;
    private boolean trimmed = false;
    
    /**
     * Constructor for SpectrumTrimmer.
     * @param mzValues M/Z value data.
     * @param intensities Intensity value data.
     */
    public SpectrumTrimmer(final Number[] mzValues, final Number[] intensities) {
        this.mzValues = mzValues;
        this.intensityValues = intensities;
        trimmedMzValues = new Number[mzValues.length];
        trimmedIntensityValues = new Number[intensities.length];
    }
    
    /**
     * Gets the trimmed M/Z value data.
     * @return Trimmed M/Z value data.
     */
    public Number[] getMzValues() {
        if (!trimmed) {
            trimZeros();
        }
        
        return trimmedMzValues;
    }
    
    /**
     * Gets the trimmed intensity value data.
     * @return Trimmed intensity value data.
     */
    public Number[] getIntensityValues() {
        if (!trimmed) {
            trimZeros();
        }
        
        return trimmedIntensityValues;
    }
    
    /**
     * Performs the trimming operation.
     */
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
