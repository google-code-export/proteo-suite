
package org.proteosuite.config;

/**
 *
 * @author SPerkins
 */
public class Config {
    private static Config INSTANCE = null;
    private GlobalConfig global = null;
    //private Set<InstrumentConfig> instrumentConfigs = new HashSet<>();
    private Config() {
        global = GlobalConfig.getInstance();
//        if (global.isReadCorrectly()) {
//            for (String instrumentName : global.getInstrumentNames()) {
//                InstrumentConfig instrumentConfig = InstrumentConfig.getConfig(instrumentName + ".pc");
//                instrumentConfigs.add(instrumentConfig);
//            }            
//        }
    }   
    
    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        
        return INSTANCE;
    }
    
    public GlobalConfig getGlobalConfig() {
        return global;
    }
}
