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
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.Task;
import uk.ac.man.mzqlib.postprocessing.ProteinAbundanceInference;

/**
 *
 * @author SPerkins
 */
public class ProteinInferenceHelper {
    public static final String REPORTER_ION_INTENSITY = "MS:1001847";
    public static final String LCMS_FEATURE_INTENSITY = "MS:1001840";
    public static final String LABEL_FREE_PEPTIDE = "MS:1001891";
    private static final String LABEL_FREE_PROTEIN = "MS:1001890";
    private static final String LABEL_FREE_PEPTIDE_DESC = "Progenesis: peptide normalised abundance";
    private static final String LABEL_FREE_PROTEIN_DESC = "Progenesis: protein normalised abundance";
    private static final String REPORTER_ION_INTENSITY_DESC = "reporter ion intensity";
    private static final String LCMS_FEATURE_INTENSITY_DESC = "LC-MS feature intensity";
    private static final String quantLayerType = "AssayQuantLayer";
    private static String outputFile = null;
    private ProteinInferenceHelper() {}
    public static void infer(final String inputFile, final String quantMethod, final String quantDataType, final String mergeOperator) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            
            @Override
            public Void doInBackground() {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setProteinInferenceProcessing();
                AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFile), "Inferring Proteins"));
                TasksTab.getInstance().refreshFromTasksModel();
                outputFile = inputFile.replaceAll(".mzq", "_protein_inference.mzq");
                try {
                    if (quantDataType.equals(REPORTER_ION_INTENSITY)) {
                        new ProteinAbundanceInference(inputFile, outputFile, quantMethod, mergeOperator, quantDataType,
                            LABEL_FREE_PROTEIN, LABEL_FREE_PROTEIN_DESC, quantLayerType);
                    } else if (quantDataType.equals(LCMS_FEATURE_INTENSITY)) {
                        new ProteinAbundanceInference(inputFile, outputFile, quantMethod, mergeOperator, quantDataType,
                            LABEL_FREE_PROTEIN, LABEL_FREE_PROTEIN_DESC, quantLayerType);
                    }                    
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
                    AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFile), "Inferring Proteins", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                    
                    AnalyseData.getInstance().getInspectModel().addQuantDataFile(new MzQuantMLFile(new File(outputFile)));
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                
            }
        };
        
        AnalyseData.getInstance().getExecutor().submit(worker);
    }
}
