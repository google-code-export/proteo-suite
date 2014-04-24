/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.quantitation;

/**
 *
 * @author SPerkins
 */
public abstract class IsobaricReagent {
    protected String id;
    protected String name;
    protected double mz;
    protected double[] correctionFactors;
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMz() {
        return mz;
    }
    
    public double[] getCorrectionFactors() {
        return correctionFactors;
    }
    
    public abstract double getMassDelta();
}
