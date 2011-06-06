/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

/**
 *
 * @author faviel
 */
public class SpectrumData {
    private String specId;
    private String specName;
    private String specMSLevel;
    private double specRT;

    public SpectrumData() {
        this.specId = "";
        this.specName = "";
        this.specMSLevel = "";
        this.specRT = 0.0;
    }    
    public SpectrumData(String specId, String specName, String specMSLevel, double specRT ) {
        this.specId = specId;
        this.specName = specName;
        this.specMSLevel = specMSLevel;
    }
    public String getSpecId() {
        return specId;
    }
    public void setSpecId(String specId) {
        this.specId = specId;
    }
    public String getSpecName() {
        return specName;
    }
    public void setSpecName(String specName) {
        this.specName = specName;
    }
    public String getSpecMSLevel() {
        return specMSLevel;
    }
    public void setSpecMSLevel(String specMSLevel) {
        this.specMSLevel = specMSLevel;
    }
    public double getSpecRT() {
        return specRT;
    }
    public void setSpecRT(double specRT) {
        this.specRT = specRT;
    }    
}
