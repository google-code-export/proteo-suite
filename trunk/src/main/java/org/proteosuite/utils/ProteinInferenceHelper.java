package org.proteosuite.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;
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

    private static final String LABEL_FREE_PROTEIN_GROUP_NORMALISED_ACC = "MS:1002518";
    private static final String LABEL_FREE_PROTEIN_GROUP_NORMALISED_NAME = "Progenesis: protein group normalised abundance";
    private static final String LABEL_FREE_PROTEIN_GROUP_RAW_ACC = "MS:1002519";
    private static final String LABEL_FREE_PROTEIN_GROUP_RAW_NAME = "Progenesis: protein group raw abundance";
    private static final String REPORTER_ION_INTENSITY_DESC = "reporter ion intensity";
    private static final String LCMS_FEATURE_INTENSITY_DESC = "LC-MS feature intensity";
    private static final String quantLayerType = "AssayQuantLayer";    

    private ProteinInferenceHelper() {
    }

    public static void infer(final File inputFile, final String quantMethod,
            final String quantDataType, final String mergeOperator) {

        BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(new ProteoSuiteActionSubject() {
            @Override
            public String getSubjectName() {
                return inputFile.getName();
            }
        }, "Inferring Proteins");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult<File> act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setProteinInferenceProcessing();

                File outputFile = null;
                try {
                    outputFile = new File(inputFile.getCanonicalPath().replaceFirst("\\.[Mm][Zz][Qq]$", "_protein_inference.mzq"));
                }
                catch (IOException e) {
                    if (outputFile == null) {
                        ProteoSuiteException pex = new ProteoSuiteException("Unable to generate output file for ProteinInference.", e);
                        return new ProteoSuiteActionResult(pex);
                    }
                }                

                ProteinAbundanceInference inference = null;
                try {
                    switch (quantDataType) {
                        case REPORTER_ION_INTENSITY:
//                            inference = new ProteinAbundanceInference(inputFile, outputFile,
//                                    quantMethod, mergeOperator, quantDataType,
//                                    "inputPeptideQuantLayerID",
//                                    LABEL_FREE_PROTEIN, LABEL_FREE_PROTEIN_DESC,
//                                    quantLayerType);
                            break;
                        case LCMS_FEATURE_INTENSITY:
//                            inference = new ProteinAbundanceInference(inputFile.getCanonicalPath(), outputFile, "AssayQuantLayer",
//                                    "MS:1001840", "MS:1001850", LABEL_FREE_PROTEIN_GROUP_NORMALISED_ACC, LABEL_FREE_PROTEIN_GROUP_NORMALISED_NAME,
//                                    LABEL_FREE_PROTEIN_GROUP_RAW_ACC, LABEL_FREE_PROTEIN_GROUP_RAW_NAME, "sum");
                            inference = new ProteinAbundanceInference(inputFile.getCanonicalPath(), outputFile.getCanonicalPath(), "sum",
                                    "MS:1001840", "MS:1001850", LABEL_FREE_PROTEIN_GROUP_NORMALISED_ACC, LABEL_FREE_PROTEIN_GROUP_NORMALISED_NAME,
                                    LABEL_FREE_PROTEIN_GROUP_RAW_ACC, LABEL_FREE_PROTEIN_GROUP_RAW_NAME, "AssayQuantLayer");                            
                            break;
                    }
                    
                    inference.proteinInference();
                }
                catch (IllegalArgumentException | IOException ex) {
                    ProteoSuiteException pex = null;
                    if (ex instanceof IllegalArgumentException) {
                        pex = new ProteoSuiteException("Problem with passing correct arguments to normalisation module: " + ex.getLocalizedMessage(), ex);
                    } else if (ex instanceof IOException) {
                        if (ex instanceof FileNotFoundException) {
                            pex = new ProteoSuiteException("Problem with normalisation module reading mzq file.", ex);
                        } else {
                            pex = new ProteoSuiteException("Problem reading path of input file in ProteinInferenceHelper.", ex);
                        }
                    }                    
                    
                    return new ProteoSuiteActionResult(pex);                 
                }              

                return new ProteoSuiteActionResult<>(outputFile);
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setProteinInferenceDone();
                
                File outputFile = task.getResultOfClass(File.class);
                
                if (outputFile == null) {
                    ProteoSuiteException pex = new ProteoSuiteException("Unable to retrieve File result of protein inference.");
                    return new ProteoSuiteActionResult(pex);
                }
                
                AnovaHelper.anova(outputFile);

                

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
