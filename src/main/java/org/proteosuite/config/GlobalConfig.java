/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.proteosuite.utils.PrimitiveUtils;

/**
 *
 * @author SPerkins
 */
public class GlobalConfig {

    private static GlobalConfig INSTANCE = null;
    private String remembered_directory = null;
    private boolean instrument_specific_config = false;
    private String searchDatabaseFile = "";
    private double searchPrecursorMsTolerance = 5;
    private String searchPrecursorMsToleranceUnit = "ppm";
    private double searchFragmentMsMsTolerance = 0.5;
    private char searchFragmentTypesABC = 'b';
    private char searchFragmentTypesXYZ = 'y';
    private String searchEnzyme = "Trypsin";
    private int searchMaxMissedCleavages = 1;
    private int searchPrecursorChargeRangeLower = 2;
    private int searchPrecursorChargeRangeUpper = 4;
    private String searchPeptideLevelThresholding = "0.01 (1%)";
    private String searchProteinLevelThresholding = "0.01 (1%)";
    private List<String> searchFixedModifications = Collections.singletonList("carbamidomethyl c");
    private List<String> searchVariableModifications = Collections.singletonList("oxidation of m");
    
    private double featureFinderMassTraceMzTolerance = 0.03;
    private int featureFinderMassTraceMinSpectra = 5;
    private int featureFinderMassTraceMaxMissing = 2;
    private int featureFinderMassTraceSlopeBound = 1;
    private int featureFinderIsotopicPatternChargeLow = 2;
    private int featureFinderIsotopicPatternChargeHigh = 4;
    private double featureFinderIsotopicPatternMzTolerance = 0.03;
    private double featureFinderSeedMinScore = 0.1;
    private double featureFinderFeatureMinScore = 0.3;
    private double featureFinderFeatureMinIsotopeFit = 0.1;
    private double featureFinderFeatureMinTraceScore = 0.1;
    private double featureFinderFeatureMaxRtSpan = 3;
    private int identAlignerMinRuns = 2;
    private double identAlignerMaxRtShift = 200;
    private boolean featureLinkerUseIdents = true;
    private int featureLinkerDistanceRtMaxDifference = 60;
    private double featureLinkerDistanceMzMaxDifference = 0.02;
//    private Set<String> instruments = new HashSet<>();
    
    private boolean isDirty = false;

    private GlobalConfig() {
        if (!readConfig()) {
            writeFile();
        }
    }

    public static GlobalConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalConfig();
        }

        return INSTANCE;
    }

    private boolean readConfig() {
        try {
            File globalConfigFile = new File("global.pc");
            if (!globalConfigFile.exists()) {
                return false;
            }
            
            System.out.println("Looking for ProteoSuite global config file in folder: " + globalConfigFile.getAbsolutePath());
            try (BufferedReader globalReader = new BufferedReader(new InputStreamReader(new FileInputStream(globalConfigFile)))) {
                String line = null;
                while ((line = globalReader.readLine()) != null) {
                    if (line.contains("=")) {
                        String[] split = line.split("=");
                        if (split.length == 2) {
                            readKeyValue(split[0], split[1]);
                        }                        
                    }
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);            
            return false;
        }
        
        return true;
    }

    private void readKeyValue(String key, String value) {
        switch (key.toUpperCase()) {
            case "REMEMBERED_DIRECTORY":
                remembered_directory = value;
                break;
            case "INSTRUMENT_CONFIG":
                instrument_specific_config = value.equals("YES");
                break;
            case "FEATURE_FINDER_MASS_TRACE_MZ_TOLERANCE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderMassTraceMzTolerance = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_MASS_TRACE_MZ_TOLERANCE option from config. Using default: " + featureFinderMassTraceMzTolerance);
                    isDirty = true;
                }

                break;                
            case "FEATURE_FINDER_MASS_TRACE_MIN_SPECTRA":
                if (PrimitiveUtils.isInteger(value)) {
                    featureFinderMassTraceMinSpectra = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_MASS_TRACE_MIN_SPECTRA option from config. Using default: " + featureFinderMassTraceMinSpectra);
                    isDirty = true;
                }
                
                break;
            case "FEATURE_FINDER_MASS_TRACE_MAX_MISSING":
                if(PrimitiveUtils.isInteger(value)) {
                    featureFinderMassTraceMaxMissing = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_MASS_TRACE_MAX_MISSING option from config. Using default: " + featureFinderMassTraceMaxMissing);
                    isDirty = true;
                }
            
                break;                
            case "FEATURE_FINDER_MASS_TRACE_SLOPE_BOUND":
                if (PrimitiveUtils.isInteger(value)) {
                    featureFinderMassTraceSlopeBound = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_MASS_TRACE_SLOPE_BOUND option from config. Using default: " + featureFinderMassTraceSlopeBound);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_LOW":
                if (PrimitiveUtils.isInteger(value)) {
                    featureFinderIsotopicPatternChargeLow = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_LOW option from config. Using default: " + featureFinderIsotopicPatternChargeLow);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_HIGH":
                if (PrimitiveUtils.isInteger(value)) {
                    featureFinderIsotopicPatternChargeHigh = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_HIGH option from config. Using default: " + featureFinderIsotopicPatternChargeHigh);
                    isDirty = true;
                }
            
                break;                
            case "FEATURE_FINDER_ISOTOPIC_PATTERN_MZ_TOLERANCE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderIsotopicPatternMzTolerance = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_ISOTOPIC_PATTERN_MZ_TOLERANCE option from config. Using default: " + featureFinderIsotopicPatternMzTolerance);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_SEED_MIN_SCORE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderSeedMinScore = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_SEED_MIN_SCORE option from config. Using default: " + featureFinderSeedMinScore);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_FEATURE_MIN_SCORE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderFeatureMinScore = Double.parseDouble(value);
                } else {
                System.out.println("Could not read FEATURE_FINDER_FEATURE_MIN_SCORE option from config. Using default: " + featureFinderFeatureMinScore);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_FEATURE_MIN_ISOTOPE_FIT":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderFeatureMinIsotopeFit = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_FEATURE_MIN_ISOTOPE_FIT option from config. Using default: " + featureFinderFeatureMinIsotopeFit);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_FEATURE_MIN_TRACE_SCORE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderFeatureMinTraceScore = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_FEATURE_MIN_TRACE_SCORE option from config. Using default: " + featureFinderFeatureMinTraceScore);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_FINDER_FEATURE_MAX_RT_SPAN":
                if (PrimitiveUtils.isDouble(value)) {
                    featureFinderFeatureMaxRtSpan = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_FINDER_FEATURE_MAX_RT_SPAN option from config. Using default: " + featureFinderFeatureMaxRtSpan);
                    isDirty = true;
                }
            
                break;
            case "IDENT_ALIGNER_MIN_RUNS":
                if (PrimitiveUtils.isInteger(value)) {
                    identAlignerMinRuns = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read IDENT_ALIGNER_MIN_RUNS option from config. Using default: " + identAlignerMinRuns);
                    isDirty = true;
                }

                break;
            case "IDENT_ALIGNER_MAX_RT_SHIFT":
                if (PrimitiveUtils.isDouble(value)) {
                    identAlignerMaxRtShift = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read IDENT_ALIGNER_MAX_RT_SHIFT option from config. Using default: " + identAlignerMaxRtShift);
                    isDirty = true;
                }

                break;
            case "FEATURE_LINKER_USE_IDENTIFICATIONS":
                featureLinkerUseIdents = Boolean.parseBoolean(value);
                break;
            case "FEATURE_LINKER_DISTANCE_RT_MAX_DIFFERENCE":
                if (PrimitiveUtils.isInteger(value)) {
                    featureLinkerDistanceRtMaxDifference = Integer.parseInt(value);
                } else {
                    System.out.println("Could not read FEATURE_LINKER_DISTANCE_RT_MAX_DIFFERENCE option from config. Using default: " + featureLinkerDistanceRtMaxDifference);
                    isDirty = true;
                }
            
                break;
            case "FEATURE_LINKER_DISTANCE_MZ_MAX_DIFFERENCE":
                if (PrimitiveUtils.isDouble(value)) {
                    featureLinkerDistanceMzMaxDifference = Double.parseDouble(value);
                } else {
                    System.out.println("Could not read FEATURE_LINKER_DISTANCE_MZ_MAX_DIFFERENCE option from config. Using default: " + featureLinkerDistanceMzMaxDifference);
                    isDirty = true;
                }
            
                break;
                
//            case "INSTRUMENTS":
//                String[] instrumentStrings = value.split(";");
//                instruments.addAll(Arrays.asList(instrumentStrings));
//                break;
        }
    }
    
    private void writeFile() {
        try {
            File out = new File("global.pc");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
            writer.write("REMEMBERED_DIRECTORY=" + (remembered_directory != null ? remembered_directory : ""));
            writer.newLine();
            writer.write("INSTRUMENT_CONFIG=" + (instrument_specific_config ? "YES" : "NO"));
            writer.newLine();
            writer.write("FEATURE_FINDER_MASS_TRACE_MZ_TOLERANCE=" + featureFinderMassTraceMzTolerance);
            writer.newLine();
            writer.write("FEATURE_FINDER_MASS_TRACE_MIN_SPECTRA=" + featureFinderMassTraceMinSpectra);
            writer.newLine();
            writer.write("FEATURE_FINDER_MASS_TRACE_MAX_MISSING=" + featureFinderMassTraceMaxMissing);
            writer.newLine();
            writer.write("FEATURE_FINDER_MASS_TRACE_SLOPE_BOUND=" + featureFinderMassTraceSlopeBound);
            writer.newLine();
            writer.write("FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_LOW=" + featureFinderIsotopicPatternChargeLow);
            writer.newLine();
            writer.write("FEATURE_FINDER_ISOTOPIC_PATTERN_CHARGE_HIGH=" + featureFinderIsotopicPatternChargeHigh);
            writer.newLine();
            writer.write("FEATURE_FINDER_ISOTOPIC_PATTERN_MZ_TOLERANCE=" + featureFinderIsotopicPatternMzTolerance);
            writer.newLine();
            writer.write("FEATURE_FINDER_SEED_MIN_SCORE=" + featureFinderSeedMinScore);
            writer.newLine();
            writer.write("FEATURE_FINDER_FEATURE_MIN_SCORE=" + featureFinderFeatureMinScore);
            writer.newLine();
            writer.write("FEATURE_FINDER_FEATURE_MIN_ISOTOPE_FIT=" + featureFinderFeatureMinIsotopeFit);
            writer.newLine();
            writer.write("FEATURE_FINDER_FEATURE_MIN_TRACE_SCORE=" + featureFinderFeatureMinTraceScore);
            writer.newLine();
            writer.write("FEATURE_FINDER_FEATURE_MAX_RT_SPAN=" + featureFinderFeatureMaxRtSpan);
            writer.newLine();
            
            
            
            
            writer.write("IDENT_ALIGNER_MIN_RUNS=" + identAlignerMinRuns);
            writer.newLine();
            writer.write("IDENT_ALIGNER_MAX_RT_SHIFT=" + identAlignerMaxRtShift);            
            writer.newLine();
            
            writer.write("FEATURE_LINKER_USE_IDENTIFICATIONS=" + featureLinkerUseIdents);            
            writer.newLine();
            writer.write("FEATURE_LINKER_DISTANCE_RT_MAX_DIFFERENCE=" + featureLinkerDistanceRtMaxDifference);
            writer.newLine();
            writer.write("FEATURE_LINKER_DISTANCE_MZ_MAX_DIFFERENCE=" + featureLinkerDistanceMzMaxDifference);
            writer.newLine();
            
            writer.close();

        }
        catch (IOException ex) {
            System.out.println("Error: Problem writing global configuration file.");
            Logger.getLogger(GlobalConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveConfig() {
        if (!isDirty) {
            return;
        }

        System.out.println("Global config has changed - saving!");
        writeFile();      
    }
    
    public double getFeatureFinderMassTraceMzTolerance() {
        return this.featureFinderMassTraceMzTolerance;
    }
    
    public int getFeatureFinderMassTraceMinSpectra() {
        return this.featureFinderMassTraceMinSpectra;
    }
    
    public int getFeatureFinderMassTraceMaxMissing() {
        return this.featureFinderMassTraceMaxMissing;
    }
    
    public int getFeatureFinderMassTraceSlopeBound() {
        return this.featureFinderMassTraceSlopeBound;
    }
    
    public int getFeatureFinderIsotopicPatternChargeLow() {
        return this.featureFinderIsotopicPatternChargeLow;
    }
    
    public int getFeatureFinderIsotopicPatternChargeHigh() {
        return this.featureFinderIsotopicPatternChargeHigh;
    }
    
    public double getFeatureFinderIsotopicPatternMzTolerance() {
        return this.featureFinderIsotopicPatternMzTolerance;
    }
    
    public double getFeatureFinderSeedMinScore() {
        return this.featureFinderSeedMinScore;
    }
    
    public double getFeatureFinderFeatureMinScore() {
        return this.featureFinderFeatureMinScore;
    }
    
    public double getFeatureFinderFeatureMinIsotopeFit() {
        return this.featureFinderFeatureMinIsotopeFit;
    }
    
    public double getFeatureFinderFeatureMinTraceScore() {
        return this.featureFinderFeatureMinTraceScore;
    }
    
    public double getFeatureFinderFeatureMaxRtSpan() {
        return this.featureFinderFeatureMaxRtSpan;
    }    

    public int getIdentAlignerMinRuns() {
        return this.identAlignerMinRuns;
    }

    public double getIdentAlignerMaxRtShift() {
        return this.identAlignerMaxRtShift;
    }
    
    public boolean getFeatureLinkerUseIdentifications() {
        return this.featureLinkerUseIdents;
    }
    
    public int getFeatureLinkerDistanceRtMaxDifference() {
        return this.featureLinkerDistanceRtMaxDifference;
    }
    
    public double getFeatureLinkerDistanceMzMaxDifference() {
        return this.featureLinkerDistanceMzMaxDifference;
    }

    public String getRememberedDirectory() {
        return remembered_directory;
    }

    public void setRememberedDirectory(String dir) {
        this.remembered_directory = dir;
        this.isDirty = true;
    }

    

//    public Set<String> getInstrumentNames() {
//        return instruments;
//    }
    public boolean allowsInstrumentConfig() {
        return instrument_specific_config;
    }
}
