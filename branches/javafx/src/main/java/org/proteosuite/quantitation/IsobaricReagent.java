package org.proteosuite.quantitation;

/**
 *
 * @author SPerkins
 */
public class IsobaricReagent {
    protected String id;
    protected String name;
    protected double reporterMz;
    protected double tagMz;
    protected double[] correctionFactors;
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getReporterMz() {
        return reporterMz;
    }
    
    public double getTagMz() {
        return tagMz;
    }
    
    public double[] getCorrectionFactors() {
        return correctionFactors;
    }    
}
