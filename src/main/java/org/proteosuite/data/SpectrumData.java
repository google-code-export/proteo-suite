/*
 * --------------------------------------------------------------------------
 * SpectrumData.java
 * --------------------------------------------------------------------------
 * Description:       Spectrum data for each scan
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * Notes:             
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.data;

/**
 * @author faviel
 */
public class SpectrumData {
    private String Id;
    private String Name;
    private String MSLevel;
    private double RT;

    public SpectrumData() {
        this.Id = "";
        this.Name = "";
        this.MSLevel = "";
        this.RT = 0.0;
    }    
    public SpectrumData(String Id, String Name, String MSLevel, double RT ) {
        this.Id = Id;
        this.Name = Name;
        this.MSLevel = MSLevel;
    }
    public String getId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getMSLevel() {
        return MSLevel;
    }
    public void setMSLevel(String MSLevel) {
        this.MSLevel = MSLevel;
    }
    public double getRT() {
        return RT;
    }
    public void setRT(double RT) {
        this.RT = RT;
    }    
}
