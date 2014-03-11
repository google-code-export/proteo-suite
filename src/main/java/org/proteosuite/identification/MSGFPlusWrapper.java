/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.identification;

import edu.ucsd.msjava.params.ParamManager;
import edu.ucsd.msjava.ui.MSGFPlus;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import static org.proteosuite.ProteoSuiteView.SYS_UTILS;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class MSGFPlusWrapper extends SearchEngineBase implements SearchEngine {

    // MSGF+ param manager.
    private ParamManager paramManager;

    // The input spectrum file for the search we will do.
    private RawDataFile inputSpectrum;

    private String inputSpectrumString;
    
    private String modificationFileName = null;

    private String outputFileName = null;

    // Map to store MSGF+ command line options.
    Map<String, String> map;

    // Array to store MSGF+ command line options (from map).
    String[] parameterArray;

    public MSGFPlusWrapper(Map<String, String> map) {
        // Create a paramManager to define our run.
        paramManager = new ParamManager(
                "MS-GF+", MSGFPlus.VERSION,
                MSGFPlus.RELEASE_DATE, "java -Xmx"
                + SYS_UTILS.getMaxMemory()
                + "M -jar MSGFPlus.jar");
        paramManager.addMSGFPlusParams();
        this.map = map;
    }

    public void setInputSpectrum(RawDataFile inputSpectrum) {
        this.inputSpectrum = inputSpectrum;
    }

    public void setInputSpectrumString(String inputSpectrumString) {
        this.inputSpectrumString = inputSpectrumString;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
    
    public void setModificationFileName(String modificationFile) {
        this.modificationFileName = modificationFile;
    }

    public String getOutputFileName() {
        if (outputFileName == null) {
            generateOutputFileName();
        }

        return outputFileName;
    }

    public void performSearch(final int executionDelay) {        
        ExecutorService executor = AnalyseData.getInstance().getExecutor();
        buildModificationFile();
        buildMap();
        buildParameterArray();
        if (parameterArray.length == 0) {
            // This is a problem, we are supplying no arguments to MSGF+.
        }

        String parameterParseError = paramManager.parseParams(parameterArray);
        if (parameterParseError != null) {
            System.out.println("Parameter parse error: " + parameterParseError);
            // Problem with the parameters! Do something!
            // Possible printUsageInfo();
        }

        paramManager.printToolInfo();
        SwingWorker<String, Void> msgfSearchWorker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                try {
                    Thread.sleep(executionDelay * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MSGFPlusWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }
                String searchError = MSGFPlus.runMSGFPlus(paramManager);
                return searchError;
//                return null;
            }

            @Override
            protected void done() {
                try {
                    System.out.println("MSGF run finished for: " + inputSpectrum == null ? inputSpectrum.getFileName() : inputSpectrumString);
                    String searchErrorMessage = get();
                    if (searchErrorMessage == null) {
                        AnalyseData data = AnalyseData.getInstance();
                        for (int i = 0; i < data.getRawDataCount(); i++) {
                            RawDataFile rawDataFile = data.getRawDataFile(i);
                            if (rawDataFile.equals(inputSpectrum)) {
                                rawDataFile.setIdentificationDataFile(new MzIdentMLFile(new File(outputFileName)));
                                ((CreateOrLoadIdentificationsStep)AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP).refreshFromData();
                            }
                        }
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Interrupted exception: " + ex.getLocalizedMessage());
                    ex.printStackTrace();
                } catch (ExecutionException ex) {
                    System.out.println("Execution exception: " + ex.getLocalizedMessage());
                    ex.printStackTrace();
                }
            }
        };

        executor.submit(msgfSearchWorker);

    }
    
    private void buildModificationFile() {
        if (modificationFileName == null) {
            return;
        }
        
        try {
            File modFile = File.createTempFile("msgf_mod_file", ".cfg");
            Files.copy(new File(modificationFileName).toPath(), modFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            modificationFileName = modFile.getAbsolutePath();
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private void buildMap() {
        if (inputSpectrum == null) {
            map.put("-s", inputSpectrumString);
        } else {
            map.put("-s", inputSpectrum.getAbsoluteFileName());
        }

        if (outputFileName == null) {
            generateOutputFileName();
        }

        map.put("-o", outputFileName);
        
        if (modificationFileName != null) {
            map.put("-mod", modificationFileName);
        }
    }

    private void buildParameterArray() {        
        parameterArray = new String[map.size() * 2];
        int arrayIndex = 0;
        for (Map.Entry<String, String> entry : map
                .entrySet()) {            
            
            parameterArray[arrayIndex] = entry.getKey();

            parameterArray[arrayIndex + 1] = entry.getValue();

            arrayIndex += 2;
        }
    }

    private void generateOutputFileName() {
        String outputFileName;
        if (inputSpectrum == null) {
            File inputSpectrum = new File(inputSpectrumString);
            String[] fileNameParts = inputSpectrum.getName().split("\\.");
            String fileExtension = fileNameParts[fileNameParts.length - 1];
            outputFileName = inputSpectrum.getAbsolutePath().replace('.' + fileExtension, "_msgfplus.mzid");
        } else {
            String[] fileNameParts = inputSpectrum.getFile().getName().split("\\.");
            String fileExtension = fileNameParts[fileNameParts.length - 1];
            outputFileName = inputSpectrum.getAbsoluteFileName().replace('.' + fileExtension, "_msgfplus.mzid");
        }

        String outputFileNameClean = outputFileName;//.replace("\\", "/");
        this.outputFileName = outputFileNameClean;
    }
}
