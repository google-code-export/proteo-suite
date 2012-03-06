/*
 * --------------------------------------------------------------------------
 * Sample.java
 * --------------------------------------------------------------------------
 * Description:       Sample files included in the analysis
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
public class Sample {
    
   private String Id;
   private String Name;
   private String Version;   
   private int Spectrums;

    public Sample() {
        this("", "", "", 0);
    }

    public Sample(String Id, String Name, String Version, int Spectrums) {
        this.Id = Id;
        this.Name = Name;
        this.Version = Version;        
        this.Spectrums = Spectrums;
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
    
    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getVersion() {
        return Version;
    }

    public int getSpectrums() {
        return Spectrums;
    }

    public void setSpectrums(int Spectrums) {
        this.Spectrums = Spectrums;
    }

    @Override
    public String toString() {
        return Id;
    }
}
