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
    private String spec_type_data;

    public SpectrumData() {
        this.spec_name = "";
        this.spec_type_data = "";
    }
    
    public SpectrumData(String spec_name, String spec_type_data) {
        this.spec_name = spec_name;
        this.spec_type_data = spec_type_data;
    }

    public String getSpec_name() {
        return spec_name;
    }
    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }
    public String getSpec_type_data() {
        return spec_type_data;
    }
    public void setSpec_type_data(String spec_type_data) {
        this.spec_type_data = spec_type_data;
    }
}
