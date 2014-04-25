package org.proteosuite.quantitation;

/**
 *
 * @author SPerkins
 */
public class ITRAQReagent extends IsobaricReagent {    
    private ITRAQReagent(String reagentId, String reagentName, double tagMz, double reporterMz, double[] reagentCorrectionFactors) {
        this.id = reagentId;
        this.name = reagentName;
        this.reporterMz = reporterMz;
        this.tagMz = tagMz;
        this.correctionFactors = reagentCorrectionFactors;
    } 

    public static ITRAQReagent getReagent(boolean fourPlex, String simpleName) {
        if (fourPlex) {
            switch (simpleName) {
                case "114":
                    return new ITRAQReagent("114", "iTRAQ4plex-114 reporter fragment", 144.105918, 114.11068, new double[] {0.0, 1.0, 5.9, 0.2});
                case "115":
                    return new ITRAQReagent("115", "iTRAQ4plex-115 reporter fragment", 144.099599, 115.114034, new double[] {0.0, 2.0, 5.6, 0.1});
                case "116":
                    return new ITRAQReagent("116", "iTRAQ4plex-116 reporter fragment", 144.102063, 116.111069, new double[] {0.0, 3.0, 4.5, 0.1});
                case "117":
                    return new ITRAQReagent("117", "iTRAQ4plex-117 reporter fragment", 144.102063, 117.114424, new double[] {0.1, 4.0, 3.5, 0.0});                    
            }
        } else {
            switch (simpleName) {
                case "113":
                    return new ITRAQReagent("113", "iTRAQ8plex-113 reporter fragment", 304.205360, 113.107325, new double[] {0.0, 0.0, 6.89, 0.24});
                case "114":
                    return new ITRAQReagent("114", "iTRAQ8plex-114 reporter fragment", 304.205360, 114.11068, new double[] {0.0, 0.94, 5.9, 0.16});
                case "115":
                    return new ITRAQReagent("115", "iTRAQ8plex-115 reporter fragment", 304.199040, 115.107715, new double[] {0.0, 1.88, 4.9, 0.1});
                case "116":
                    return new ITRAQReagent("116", "iTRAQ8plex-116 reporter fragment", 304.205360, 116.111069, new double[] {0.0, 2.82, 3.9, 0.07});
                case "117":
                    return new ITRAQReagent("117", "iTRAQ8plex-117 reporter fragment", 304.205360, 117.114424, new double[] {0.06, 3.77, 2.88, 0.0});
                case "118":
                    return new ITRAQReagent("118", "iTRAQ8plex-118 reporter fragment", 304.199040, 118.111459, new double[] {0.09, 4.71, 1.91, 0.0});
                case "119":
                    return new ITRAQReagent("119", "iTRAQ8plex-119 reporter fragment", 304.199040, 119.114814, new double[] {0.14, 5.66, 0.87, 0.0});
                case "121":
                    return new ITRAQReagent("121", "iTRAQ8plex-121 reporter fragment", 304.199040, 121.121524, new double[] {0.27, 7.44, 0.18, 0.0});
            }
        }
        
        return null;
    }
}