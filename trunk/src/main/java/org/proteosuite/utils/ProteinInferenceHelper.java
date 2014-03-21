/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzQuantMLFile;
import uk.ac.man.mzqlib.postprocessing.ProteinAbundanceInference;

/**
 *
 * @author SPerkins
 */
public class ProteinInferenceHelper {
    public static final String REPORTER_ION_INTENSITY = "MS:1001847";
    private static final String peptideDataParamAccession = "MS:1001891";
    private static final String proteinDataParamAccession = "MS:1001890";
    private static final String proteinDataParamName = "Progenesis: protein normalised abundance";
    private static final String quantLayerType = "AssayQuantLayer";
    private static String outputFile = null;
    private ProteinInferenceHelper() {}
    public static void infer(final String inputFile, final String quantMethod, final String quantDataType, final String mergeOperator) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            
            @Override
            public Void doInBackground() {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setProteinInferenceProcessing();
                String outputFile = inputFile.replaceAll(".mzq", "_protein_inference.mzq");
                try {
                    new ProteinAbundanceInference(inputFile, outputFile, quantMethod, mergeOperator, quantDataType,
                            proteinDataParamAccession, proteinDataParamName, quantLayerType);
                } catch (FileNotFoundException ex) {
                    System.out.println("Protein inference error: " + ex.getLocalizedMessage());
                }
                
                return null;
            }
            
            @Override
            public void done() {
                try {
                    get();
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setProteinInferenceDone();
                    
                    AnalyseData.getInstance().getInspectModel().addQuantDataFile(new MzQuantMLFile(new File(outputFile)));
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                
            }
        };
        
        AnalyseData.getInstance().getExecutor().submit(worker);
    }
}
