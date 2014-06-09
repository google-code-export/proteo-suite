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
public class MzIntensityPair extends PrimitiveDoublePair implements Comparable {
    
    public MzIntensityPair() {
        this(0.0, 0.0);
    }
    
    public MzIntensityPair(double mz, double intensity) {
        this.setMz(mz);
        this.setIntensity(intensity);
    }
    
    public void setMz(double mz) {
        super.setFirstValue(mz);
    }
    
    public double getMz() {
        return super.getFirstValue();
    }
    
    public void setIntensity(double intensity) {
        super.setSecondValue(intensity);
    }
    
    public double getIntensity() {
        return super.getSecondValue();
    }

    @Override
    public String toString() {
        return "M/Z=" + this.getMz() + "|Intensity=" + this.getIntensity();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.getMz()) ^ (Double.doubleToLongBits(this.getMz()) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.getIntensity()) ^ (Double.doubleToLongBits(this.getIntensity()) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final MzIntensityPair that = (MzIntensityPair) obj;
        if (Double.doubleToLongBits(this.getMz()) != Double.doubleToLongBits(that.getMz())) {
            return false;
        }
        
        if (Double.doubleToLongBits(this.getIntensity()) != Double.doubleToLongBits(that.getIntensity())) {
            return false;
        }
        
        return true;
    }  

    @Override
    public int compareTo(Object o) {
        MzIntensityPair that = (MzIntensityPair)o;
        if (this.getMz() > that.getMz()) {
            return 1;
        } else if (this.getMz() < that.getMz()) {
            return -1;
        } else {
            if (this.getIntensity() > that.getIntensity()) {
                return 1;
            } else if (this.getIntensity() < that.getIntensity()) {
                return -1;
            }
        }
        
        return 0;
    }
    
}
