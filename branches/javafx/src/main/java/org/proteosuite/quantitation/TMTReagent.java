package org.proteosuite.quantitation;

/**
 *
 * @author SPerkins
 */
public class TMTReagent extends IsobaricReagent {
    public static final int TWO_PLEX = 0;
    public static final int SIX_PLEX = 1;
    public static final int EIGHT_PLEX = 2;
    public static final int TEN_PLEX = 3;        
    private TMTReagent(String reagentId, String reagentName, double tagMz, double reagentMz, double[] reagentCorrectionFactors) {
        this.id = reagentId;
        this.name = reagentName;
        this.reporterMz = reagentMz;
        this.tagMz = tagMz;
        this.correctionFactors = reagentCorrectionFactors;
    }   
    
    public static TMTReagent getReagent(int plex, String simpleName) {
        if (plex == TWO_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT2plex-126 reporter fragment", 225.155833, 126.127725, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127":
                    return new TMTReagent("127", "TMT2plex-127 reporter fragment", 225.155833, 127.131079, new double[] {0.0, 0.0, 0.0, 0.0});
            }            
        } else if (plex == SIX_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT6plex-126 reporter fragment", 229.162932, 126.127725, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127":
                    return new TMTReagent("127", "TMT6plex-127 reporter fragment", 229.162932, 127.124760, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128":
                    return new TMTReagent("128", "TMT6plex-128 reporter fragment", 229.162932, 128.134433, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129":
                    return new TMTReagent("129", "TMT6plex-129 reporter fragment", 229.162932, 129.131468, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130":
                    return new TMTReagent("130", "TMT6plex-130 reporter fragment", 229.162932, 130.141141, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT6plex-131 reporter fragment", 229.162932, 131.138176, new double[] {0.0, 0.0, 0.0, 0.0});
            }
        } else if (plex == EIGHT_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT8plex-126 reporter fragment", 229.162932, 126.127725, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127N":
                    return new TMTReagent("127N", "TMT8plex-127N reporter fragment", 229.162932, 127.124760, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127C":
                    return new TMTReagent("127C", "TMT8plex-127C reporter fragment", 229.162932, 127.131079, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128":
                    return new TMTReagent("128", "TMT8plex-128 reporter fragment", 229.162932, 128.134433, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129N":
                    return new TMTReagent("129N", "TMT8plex-129N reporter fragment", 229.162932, 129.131468, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "129C":
                    return new TMTReagent("129C", "TMT8plex-129C reporter fragment", 229.162932, 129.137787, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130":
                    return new TMTReagent("130", "TMT8plex-130 reporter fragment", 229.162932, 130.141141, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT8plex-131 reporter fragment", 229.162932, 131.138176, new double[] {0.0, 0.0, 0.0, 0.0});                
            }
        } else if (plex == TEN_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT10plex-126 reporter fragment", 229.162932, 126.127725, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127N":
                    return new TMTReagent("127N", "TMT10plex-127N reporter fragment", 229.162932, 127.124760, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127C":
                    return new TMTReagent("127C", "TMT10plex-127C reporter fragment", 229.162932, 127.131079, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128N":
                    return new TMTReagent("128N", "TMT10plex-128N reporter fragment", 229.162932, 128.128114, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128C":
                    return new TMTReagent("128C", "TMT10plex-128C reporter fragment", 229.162932, 128.134433, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129N":
                    return new TMTReagent("129N", "TMT10plex-129N reporter fragment", 229.162932, 129.131468, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "129C":
                    return new TMTReagent("129C", "TMT10plex-129C reporter fragment", 229.162932, 129.137787, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130N":
                    return new TMTReagent("130N", "TMT10plex-130N reporter fragment", 229.162932, 130.134822, new double[] {0.0, 0.0, 0.0, 0.0});
                case "130C":
                    return new TMTReagent("130C", "TMT10plex-130C reporter fragment", 229.162932, 130.141141, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT10plex-131 reporter fragment", 229.162932, 131.138176, new double[] {0.0, 0.0, 0.0, 0.0});                                   
            }
        }       
        
        return null;
    }
}
