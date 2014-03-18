/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.quantitation;

/**
 *
 * @author SPerkins
 */
public class ITRAQReagent {
    private final String id;
    private final String name;
    private final double mz;
    private final double[] correctionFactors;
    private ITRAQReagent(String reagentId, String reagentName, double reagentMz, double[] reagentCorrectionFactors) {
        this.id = reagentId;
        this.name = reagentName;
        this.mz = reagentMz;
        this.correctionFactors = reagentCorrectionFactors;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMz() {
        return mz;
    }
    
    public double[] getCorrectionFactors() {
        return correctionFactors;
    }
    

    public static ITRAQReagent getReagent(boolean fourPlex, String simpleName) {
        if (fourPlex) {
            switch (simpleName) {
                case "114":
                    return new ITRAQReagent("114", "iTRAQ4plex-114 reporter fragment", 114.11123, new double[] {0.0, 1.0, 5.9, 0.2});
                case "115":
                    return new ITRAQReagent("115", "iTRAQ4plex-115 reporter fragment", 115.10826, new double[] {0.0, 2.0, 5.6, 0.1});
                case "116":
                    return new ITRAQReagent("116", "iTRAQ4plex-116 reporter fragment", 116.11162, new double[] {0.0, 3.0, 4.5, 0.1});
                case "117":
                    return new ITRAQReagent("117", "iTRAQ4plex-117 reporter fragment", 117.11497, new double[] {0.1, 4.0, 3.5, 0.0});                    
            }
        }
        
        return null;
    }
}
