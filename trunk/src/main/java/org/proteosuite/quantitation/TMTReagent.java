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
public class TMTReagent extends IsobaricReagent {
    public static final int SIX_PLEX = 0;
    public static final int EIGHT_PLEX = 1;
    public static final int TEN_PLEX = 2;        
    private TMTReagent(String reagentId, String reagentName, double reagentMz, double[] reagentCorrectionFactors) {
        this.id = reagentId;
        this.name = reagentName;
        this.mz = reagentMz;
        this.correctionFactors = reagentCorrectionFactors;
    }
    
    @Override
    public double getMassDelta() {
        return 229.162932;
    }
    
    public static TMTReagent getReagent(int plex, String simpleName) {
        if (plex == SIX_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT6plex-126 reporter fragment", 126.1277261, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127":
                    return new TMTReagent("127", "TMT6plex-127 reporter fragment", 127.1247610, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128":
                    return new TMTReagent("128", "TMT6plex-128 reporter fragment", 128.1344357, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129":
                    return new TMTReagent("129", "TMT6plex-129 reporter fragment", 129.1314706, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130":
                    return new TMTReagent("130", "TMT6plex-130 reporter fragment", 130.1411453, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT6plex-131 reporter fragment", 131.1381802, new double[] {0.0, 0.0, 0.0, 0.0});
            }
        } else if (plex == EIGHT_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT8plex-126 reporter fragment", 126.1277261, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127a":
                    return new TMTReagent("127a", "TMT8plex-127a reporter fragment", 127.1247610, new double[] {0.0, 0.0, 0.0, 0.0});
                 case "127b":
                    return new TMTReagent("127b", "TMT8plex-127b reporter fragment", 127.1310809, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128":
                    return new TMTReagent("128", "TMT8plex-128 reporter fragment", 128.1344357, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129a":
                    return new TMTReagent("129a", "TMT8plex-129a reporter fragment", 129.1314706, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "129b":
                    return new TMTReagent("129b", "TMT8plex-129b reporter fragment", 129.1377905, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130":
                    return new TMTReagent("130", "TMT8plex-130 reporter fragment", 130.1411453, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT8plex-131 reporter fragment", 131.1381802, new double[] {0.0, 0.0, 0.0, 0.0});                
            }
        } else if (plex == TEN_PLEX) {
            switch (simpleName) {
                case "126":
                    return new TMTReagent("126", "TMT10plex-126 reporter fragment", 126.1277261, new double[] {0.0, 0.0, 0.0, 0.0});
                case "127a":
                    return new TMTReagent("127a", "TMT10plex-127a reporter fragment", 127.1247610, new double[] {0.0, 0.0, 0.0, 0.0});
                 case "127b":
                    return new TMTReagent("127b", "TMT10plex-127b reporter fragment", 127.1310809, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128a":
                    return new TMTReagent("128a", "TMT10plex-128a reporter fragment", 128.1281158, new double[] {0.0, 0.0, 0.0, 0.0});
                case "128b":
                    return new TMTReagent("128b", "TMT10plex-128b reporter fragment", 128.1344357, new double[] {0.0, 0.0, 0.0, 0.0});
                case "129a":
                    return new TMTReagent("129a", "TMT10plex-129a reporter fragment", 129.1314706, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "129b":
                    return new TMTReagent("129b", "TMT10plex-129b reporter fragment", 129.1377905, new double[] {0.0, 0.0, 0.0, 0.0}); 
                case "130a":
                    return new TMTReagent("130a", "TMT10plex-130a reporter fragment", 130.1348254, new double[] {0.0, 0.0, 0.0, 0.0});
                case "130b":
                    return new TMTReagent("130b", "TMT10plex-130b reporter fragment", 130.1411453, new double[] {0.0, 0.0, 0.0, 0.0});
                case "131":
                    return new TMTReagent("131", "TMT10plex-131 reporter fragment", 131.1381802, new double[] {0.0, 0.0, 0.0, 0.0});                                   
            }
        }       
        
        return null;
    }
}
