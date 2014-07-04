package org.proteosuite.model;

/**
 *
 * @author SPerkins
 */
public class FragmentSpectrum extends Spectrum {
    private Feature precursorMzChargeIntensity;
    
    public FragmentSpectrum(double precursorMz, double precursorIntensity, int charge) {
        this(new Feature(precursorMz, precursorIntensity, charge));
    }
    
    public FragmentSpectrum(Feature mzIntensityCharge) {
        this.precursorMzChargeIntensity = mzIntensityCharge;
    }
    public FragmentSpectrum(MzIntensityPair mzIntensityPair) {
        this.precursorMzChargeIntensity = new Feature(mzIntensityPair);
    }
    
    public Feature getPrecursor() {
        return precursorMzChargeIntensity;
    }
}
