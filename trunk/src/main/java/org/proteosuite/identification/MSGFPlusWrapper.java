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
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getOutputFileName() {
        if (outputFileName == null) {
            generateOutputFileName();
        }

        return outputFileName;
    }

    public static void doSearch(ParamManager paramManager) {
        MSGFPlus.runMSGFPlus(paramManager);
    }

    public void performSearch() {
        ExecutorService executor = AnalyseData.getInstance().getExecutor();
        buildMap();
        buildParameterArray();
        if (parameterArray.length == 0) {
            // This is a problem, we are supplying no arguments to MSGF+.
        }
        
        String parameterParseError = paramManager.parseParams(parameterArray);
        if (parameterParseError != null ) {
            // Problem with the parameters! Do something!
            // Possible printUsageInfo();
        }
        
        paramManager.printToolInfo();
        SwingWorker<String, Void> msgfSearchWorker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                String searchError = MSGFPlus.runMSGFPlus(paramManager);
                return searchError;
            }
            
            @Override
            protected void done() {
                try {
                    String searchErrorMessage = get();
                    if (searchErrorMessage == null) {
                        AnalyseData data = AnalyseData.getInstance();
                        for (int i = 0; i < data.getRawDataFiles().size(); i++) {
                            RawDataFile rawDataFile = data.getRawDataFiles().get(i);
                            if (rawDataFile.equals(inputSpectrum)) {
                                rawDataFile.setIdentificationDataFile(new MzIdentMLFile(new File(outputFileName)));
                                ((CreateOrLoadIdentificationsStep)AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP).refreshFromData();
                            }
                        }
                    }
                    
                } catch (InterruptedException ex) {
                    
                } catch (ExecutionException ex) {
                    
                }
            }
        };       
        
        executor.submit(msgfSearchWorker);
            
    }

    private void buildMap() {
        map.put("-s", inputSpectrum.getAbsoluteFileName());
        if (outputFileName == null) {
            generateOutputFileName();
        }

        map.put("-o", outputFileName);
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
        String[] fileNameParts = inputSpectrum.getFile().getName().split("\\.");        
        String fileExtension = fileNameParts[fileNameParts.length - 1];
        String outputFileName = inputSpectrum.getAbsoluteFileName().replace('.' + fileExtension, "_msgfplus.mzid");
        String outputFileNameClean = outputFileName.replace("\\", "/");
        this.outputFileName = outputFileNameClean;
    }
}
