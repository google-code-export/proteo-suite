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
    private String spec_name;

    public SpectrumData() {
        this.spec_name = "";
    }
    
    public SpectrumData(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }
    
}
