package org.proteosuite.utils;

import java.io.FileNotFoundException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.QuantDataFile;
import uk.ac.man.mzqlib.normalisation.PepProtAbundanceNormalisation;

/**
 *
 * @author SPerkins
 */
public class NormalisationHelper {

    private static final String RAW_FEATURE_INTENSITY_ACC = "MS:1001840";
    private static final String PEPTIDE_NORMALISED_ACC = "MS:1001850";
    private static final String PEPTIDE_NORMALISED_NAME = "normalized peptide value";

    private NormalisationHelper() {
    }

    public static void normalise(final QuantDataFile quantData) {

        final BackgroundTask task = new BackgroundTask(quantData, "Normalising Peptide Quantitation");
        task.setInvisibility(true);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<String, BackgroundTaskSubject>() {
            @Override
            public String act(BackgroundTaskSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setNormalisationProcessing();
                String outputFile = quantData.getAbsoluteFileName().replace(".mzq", "_normalised.mzq");
                try {
                    PepProtAbundanceNormalisation normalisation = new PepProtAbundanceNormalisation(quantData.getAbsoluteFileName(), outputFile, "AssayQuantLayer", RAW_FEATURE_INTENSITY_ACC, PEPTIDE_NORMALISED_ACC, PEPTIDE_NORMALISED_NAME, "peptide", "_REVERSED", null);
                    normalisation.multithreadingCalc();
                }
                catch (FileNotFoundException e) {
                    System.out.println("Problem computing normalisation!");
                    e.printStackTrace();
                }

                return outputFile;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setNormalisationDone();
                String outputFile = task.getResultOfClass(String.class);

                ProteinInferenceHelper.infer(outputFile, "Label-free", ProteinInferenceHelper.LCMS_FEATURE_INTENSITY, "sum");

                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
