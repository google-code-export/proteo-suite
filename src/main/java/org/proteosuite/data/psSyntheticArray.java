/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.data;

/**
 *
 * @author FG
 */
public class psSyntheticArray {
    String[] aScanIndex;
    float[][] aQuantValues;
    float[] aMzValues;
    public psSyntheticArray(int iMaxScans, int iMaxMzValues)
    {
        aScanIndex = new String[iMaxScans];
        aQuantValues = new float[iMaxScans][iMaxMzValues];
        aMzValues = new float[iMaxMzValues];
    }
    public void setScanIndex(String sScanIndex, int iPosition)
    {
        aScanIndex[iPosition] = sScanIndex;
    }
    public String getScanIndex(int iPosition)
    {
        return aScanIndex[iPosition];
    }
    public void setQuantValues(float fValue, int iPos1, int iPos2)
    {
        aQuantValues[iPos1][iPos2] = fValue;
    }
    public float getQuantValues(int iPos1, int iPos2)
    {
        return aQuantValues[iPos1][iPos2];
    }
    public void setMzValues(float fMzValue, int iPos2)
    {
        aMzValues[iPos2] = fMzValue;
    }
    public float getMzValues(int iPos2)
    {
        return aMzValues[iPos2];
    }
}
