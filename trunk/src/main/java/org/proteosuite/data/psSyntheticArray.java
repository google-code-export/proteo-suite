/*
 * --------------------------------------------------------------------------
 * psSyntheticArray.java
 * --------------------------------------------------------------------------
 * Description:       Generate synthetic array
 * Developer:         fgonzalez
 * Created:           10 July 2012
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.data;

/**
 *
 * @author fgonzalez
 */
public class psSyntheticArray {
    String[] aScanIndex;
    float[][] aQuantValues;
    float[] aMzValues;
    public psSyntheticArray(int iMaxScans, int iMaxMzValues){
        aScanIndex = new String[iMaxScans];
        aQuantValues = new float[iMaxScans][iMaxMzValues];
        aMzValues = new float[iMaxMzValues];
    }
    public void setScanIndex(String sScanIndex, int iPosition){
        aScanIndex[iPosition] = sScanIndex;
    }
    public String getScanIndex(int iPosition){
        return aScanIndex[iPosition];
    }
    public void setQuantValues(float fValue, int iPos1, int iPos2){
        aQuantValues[iPos1][iPos2] = fValue;
    }
    public float getQuantValues(int iPos1, int iPos2){
        return aQuantValues[iPos1][iPos2];
    }
    public void setMzValues(float fMzValue, int iPos2){
        aMzValues[iPos2] = fMzValue;
    }
    public float getMzValues(int iPos2){
        return aMzValues[iPos2];
    }
}
