package org.proteosuite.data;

/**
 *
 * @author faviel
 */
public class Template1 {
    int mzIndex;
    String scanID;
    int scanIndex;
    float quantIntensities;
    int template2Index;

    public Template1() {
        this.mzIndex = 0;
        this.scanID = "";
        this.scanIndex = 0;
        this.quantIntensities = 0.0f;
        this.template2Index = 0;
    }    
    public int getMzIndex()
    {
        return mzIndex;
    }
    public String getScanID()
    {
        return scanID;
    }
    public int getScanIndex()
    {
        return scanIndex;
    }    
    public float getQuantIntensities()
    {
        return quantIntensities;
    }    
    public int getTemplate2Index()
    {
        return template2Index;
    }
    public void setTemplate(int mzIndex, String scanID, int scanIndex, float quantInt, int template2Index)
    {
        this.mzIndex = mzIndex;
        this.scanID = scanID;
        this.scanIndex = scanIndex;
        this.quantIntensities = quantInt;
        this.template2Index = template2Index;
    }
}
