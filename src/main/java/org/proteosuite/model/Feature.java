/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

/**
 *
 * @author SPerkins
 */
public class Feature extends MzIntensityPair {
    private int charge;    
    public Feature(double mzValue, double intensity, int charge) {
        super(mzValue, intensity);        
        this.charge = charge;
    }
    
    public Feature(MzIntensityPair mzIntensity) {
        this(mzIntensity.getMz(), mzIntensity.getIntensity(), 0);
    }
    
    public int getCharge() {
        return this.charge;
    }
}
