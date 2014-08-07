package org.proteosuite.utils;

import java.io.File;
import java.io.FileNotFoundException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.MzQuantMLFile;
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

    private ProteinInferenceHelper() {
    }

    public static void infer(final String inputFile, final String quantMethod,
            final String quantDataType, final String mergeOperator) {

        final String fileName = new File(inputFile).getName();

        BackgroundTask task = new BackgroundTask(new BackgroundTaskSubject() {
            @Override
            public String getSubjectName() {
                return fileName;
            }
        }, "Inferring Proteins");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setProteinInferenceProcessing();

                outputFile = inputFile.replaceAll(".mzq",
                        "_protein_inference.mzq");

                try {
                    if (quantDataType.equals(REPORTER_ION_INTENSITY)) {
                        new ProteinAbundanceInference(inputFile, outputFile,
                                quantMethod, mergeOperator, quantDataType,
                                LABEL_FREE_PROTEIN, LABEL_FREE_PROTEIN_DESC,
                                quantLayerType);
                    } else if (quantDataType.equals(LCMS_FEATURE_INTENSITY)) {
                        new ProteinAbundanceInference(inputFile, outputFile,
                                quantMethod, mergeOperator, quantDataType,
                                LABEL_FREE_PROTEIN, LABEL_FREE_PROTEIN_DESC,
                                quantLayerType);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Protein inference error: "
                            + ex.getLocalizedMessage());
                }

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setProteinInferenceDone();

                AnalyseData
                        .getInstance()
                        .getInspectModel()
                        .addQuantDataFile(
                                new MzQuantMLFile(new File(outputFile)));
                
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
