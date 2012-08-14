package org.proteosuite.data;

/**
 *
 * @author faviel
 */
public class Template1 {
    int mzIndex;
    String scanID;
    float quantIntensities;
    int template2Index;

    public Template1() {
        this.mzIndex = 0;
        this.scanID = "";
        this.quantIntensities = 0.0f;
        this.template2Index = 0;
    }    
    public float getMzIndex()
    {
        return mzIndex;
    }
    public String getScanIndex()
    {
        return scanID;
    }
    public float getQuantIntensities()
    {
        return quantIntensities;
    }    
    public int getTemplate2Index()
    {
        return template2Index;
    }
    public void setTemplate(int mzIndex, String scanIndex, float quantInt, int template2Index)
    {
        this.mzIndex = mzIndex;
        this.scanID = scanIndex;
        this.quantIntensities = quantInt;
        this.template2Index = template2Index;
    }
}
