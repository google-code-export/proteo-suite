/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;
import java.math.BigInteger;
/**
 *
 * @author faviel
 */
public class Sample {
    
   private String samId;
   private String samName;
   private String samVersion;   
   private String samAccession;
   private BigInteger samCVs;
   private int samSpectrums;

    public Sample() {
        this("", "", "", "", BigInteger.valueOf(0), 0);
    }

    public Sample(String samId, String samName, String samVersion,  String samAccession, BigInteger samCVs, int samSpectrums) {
        this.samId = samId;
        this.samName = samName;
        this.samVersion = samVersion;        
        this.samAccession = samAccession;
        this.samCVs = samCVs;
        this.samSpectrums = samSpectrums;
    }

    public String getSamId() {
        return samId;
    }

    public void setSamId(String samId) {
        this.samId = samId;
    }

    public String getSamName() {
        return samName;
    }

    public void setSamName(String samName) {
        this.samName = samName;
    }
    
    public void setSamVersion(String samVersion) {
        this.samVersion = samVersion;
    }

    public String getSamVersion() {
        return samVersion;
    }

    public String getSamAccession() {
        return samAccession;
    }

    public void setSamAccession(String sam_accesion) {
        this.samAccession = sam_accesion;
    }

    public BigInteger getSamCVs() {
        return samCVs;
    }

    public void setSamCVs(BigInteger samCVs) {
        this.samCVs = samCVs;
    }

    public int getSamSpectrums() {
        return samSpectrums;
    }

    public void setSamSpectrums(int samSpectrums) {
        this.samSpectrums = samSpectrums;
    }

    @Override
    public String toString() {
        return samId;
    }
}
