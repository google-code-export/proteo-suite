/*
 * --------------------------------------------------------------------------
 * psTemplateQuant.java
 * --------------------------------------------------------------------------
 * Description:       Template for Label Free Quantitation
 * Developer:         Faviel Gonzalez
 * Created:           10 July 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.data;

public class psTemplateQuant {
    int mzIndex;
    String scanID;
    int scanIndex;
    float quantIntensities;
    int template2Index;

    public psTemplateQuant() {
        this.mzIndex = 0;
        this.scanID = "";
        this.scanIndex = 0;
        this.quantIntensities = 0.0f;
        this.template2Index = 0;
    }    
    public int getMzIndex(){
        return mzIndex;
    }
    public String getScanID(){
        return scanID;
    }
    public int getScanIndex(){
        return scanIndex;
    }    
    public float getQuantIntensities(){
        return quantIntensities;
    }    
    public int getTemplate2Index(){
        return template2Index;
    }
    public void setTemplate(int mzIndex, String scanID, int scanIndex, float quantInt, int template2Index){
        this.mzIndex = mzIndex;
        this.scanID = scanID;
        this.scanIndex = scanIndex;
        this.quantIntensities = quantInt;
        this.template2Index = template2Index;
    }
}
