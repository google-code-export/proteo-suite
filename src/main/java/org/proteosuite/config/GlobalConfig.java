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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SPerkins
 */
public class GlobalConfig {
    private static GlobalConfig INSTANCE = null;
    private String remembered_directory = null;
    private boolean instrument_specific_config = false;
    private Set<String> instruments = new HashSet<>();
    private boolean successfulRead = true;
    private GlobalConfig() {
        readConfig();        
    }
    
    public static GlobalConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalConfig();
        }
        
        return INSTANCE;
    }
    
    private void readConfig() {
        try {
            File globalConfigFile = new File(Config.getConfigRoot() + File.separator + "global.txt");
            try (BufferedReader globalReader = new BufferedReader(new InputStreamReader(new FileInputStream(globalConfigFile)))) {
                String line =  null;
                while ((line = globalReader.readLine()) != null) {
                    if (line.contains("=")) {
                        String[] split = line.split("=");
                        readKeyValue(split[0], split[1]);
                    }
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
            successfulRead = false;
        }
    }
    
    private void readKeyValue(String key, String value) {
        switch (key) {
            case "REMEMBERED_DIRECTORY":
                remembered_directory = value;
                break;
            case "INSTRUMENT_CONFIG":
                instrument_specific_config = value.equals("YES");
                break;
            case "INSTRUMENTS":
                String[] instrumentStrings = value.split(";");
                instruments.addAll(Arrays.asList(instrumentStrings));                
                break;                        
        }
    }
    
    public String getRememberedDirectory() {
        return remembered_directory;
    }
    
    public boolean isReadCorrectly() {
        return successfulRead;
    }
    
    public Set<String> getInstrumentNames() {
        return instruments;
    }
    
    public boolean allowsInstrumentConfig() {
        return instrument_specific_config;
    }
}
