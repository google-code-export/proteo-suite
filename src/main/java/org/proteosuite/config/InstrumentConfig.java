/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.proteosuite.utils.NumericalUtils;

/**
 *
 * @author SPerkins
 */
public class InstrumentConfig {
    private static Map<String, InstrumentConfig> configs = new HashMap<>();
    private String instrument_name;
    private double ms1tol = 10;
    private double ms2tol = 0.5;
    private String ms1tolunit = "ppm";
    private String ms2tolunit = "Da";
    
    private boolean successfulRead = true;
    private InstrumentConfig(String filePath) {
        readConfig(filePath);
    }
    
    public static InstrumentConfig getConfig(String filePath) {
        if (configs.containsKey(filePath)) {
            return configs.get(filePath);
        }
        
        InstrumentConfig config = new InstrumentConfig(filePath);
        if (config.isReadSuccessfully()) {
            configs.put(filePath, config);
        }
        
        return config;
    }
    
    public boolean isReadSuccessfully() {
        return successfulRead;
    }
    
    private void readConfig(String filePath) {
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] split = line.split("=");
                    readKeyValue(split[0], split[1]);
                }
            }
            
            reader.close();
        }
        catch (IOException ex) {
            Logger.getLogger(InstrumentConfig.class.getName()).log(Level.SEVERE, null, ex);
            successfulRead = false;
        }        
    }
    
    private void readKeyValue(String key, String value) {
        switch (key) {
            case "INSTRUMENT_NAME":
                instrument_name = value;
                break;
            case "MS1_TOL":
                if (NumericalUtils.isDouble(value)) {
                    ms1tol = Double.parseDouble(value);
                }
            
                break;
            case "MS2_TOL":
                if (NumericalUtils.isDouble(value)) {
                    ms2tol = Double.parseDouble(value);
                }
            
                break;
            case "MS1_TOL_UNIT":
                ms1tolunit = value;
                break;
            case "MS2_TOL_UNIT":
                ms2tolunit = value;
                break;                    
        }
    }
}
