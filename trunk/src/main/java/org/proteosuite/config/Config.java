
package org.proteosuite.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author SPerkins
 */
public class Config {
    private static Config INSTANCE = null;
    private GlobalConfig global = null;
    private Set<InstrumentConfig> instrumentConfigs = new HashSet<>();
    private Config() {
        global = GlobalConfig.getInstance();
        if (global.isReadCorrectly()) {
            for (String instrumentName : global.getInstrumentNames()) {
                InstrumentConfig instrumentConfig = InstrumentConfig.getConfig(getConfigRoot() + File.separator + instrumentName + ".txt");
                instrumentConfigs.add(instrumentConfig);
            }            
        }
    }
    
    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        
        return INSTANCE;
    } 
    
    public static String getConfigRoot() {
        return "e:";
    }
}
