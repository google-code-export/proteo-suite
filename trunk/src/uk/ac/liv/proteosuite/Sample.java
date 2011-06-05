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
   private String sam_name;
   private String sam_version;
   private String sam_id;
   private String sam_accession;
   private BigInteger sam_cvs;
   private int sam_spectrums;

    public Sample() {
        this("", "", "", "", BigInteger.valueOf(0), 0);
    }

    public Sample(String sam_name, String sam_version, String sam_id, String sam_accesion, BigInteger sam_cvs, int sam_spectrums) {
        this.sam_name = sam_name;
        this.sam_version = sam_version;
        this.sam_id = sam_id;
        this.sam_accession = sam_accesion;
        this.sam_cvs = sam_cvs;
        this.sam_spectrums = sam_spectrums;
    }

    public void setSam_accession(String sam_accesion) {
        this.sam_accession = sam_accesion;
    }

    public void setSam_cvs(BigInteger sam_cvs) {
        this.sam_cvs = sam_cvs;
    }

    public void setSam_id(String sam_id) {
        this.sam_id = sam_id;
    }

    public void setSam_spectrums(int sam_spectrums) {
        this.sam_spectrums = sam_spectrums;
    }

    public String getSam_accession() {
        return sam_accession;
    }

    public BigInteger getSam_cvs() {
        return sam_cvs;
    }

    public String getSam_id() {
        return sam_id;
    }

    public int getSam_spectrums() {
        return sam_spectrums;
    }

    public void setSam_name(String sam_name) {
        this.sam_name = sam_name;
    }

    public void setSam_version(String sam_version) {
        this.sam_version = sam_version;
    }

    public String getSam_name() {
        return sam_name;
    }

    public String getSam_version() {
        return sam_version;
    }
    @Override
    public String toString() {
        return sam_id;
    }
}
