package org.proteosuite.model;

/**
 *
 * @author SPerkins
 */
public class FragmentSpectrum extends Spectrum {
    private MzIntensityPair precursorMzIntensity;
    
    public FragmentSpectrum(double precursorMz, double precursorIntensity) {
        this(new MzIntensityPair(precursorMz, precursorIntensity));
    }
    
    public FragmentSpectrum(MzIntensityPair mzIntensityPair) {
        this.precursorMzIntensity = mzIntensityPair;
    }
}
