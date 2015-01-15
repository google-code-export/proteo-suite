package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionResult;
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

        final BackgroundTask<QuantDataFile> task = new BackgroundTask<>(quantData, "Normalising Peptide Quantitation");
        task.setInvisibility(true);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>() {
            @Override
            public ProteoSuiteActionResult<File> act(QuantDataFile argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setNormalisationProcessing();
                File outputFile = null;
                try {
                    outputFile = new File(quantData.getFile().getCanonicalPath().replaceFirst("\\.[Mm][Zz][Qq]$", "_normalised.mzq"));
                    PepProtAbundanceNormalisation normalisation = new PepProtAbundanceNormalisation(quantData.getAbsoluteFileName(), outputFile.getCanonicalPath(), "peptide", "AssayQuantLayer", RAW_FEATURE_INTENSITY_ACC, PEPTIDE_NORMALISED_ACC, PEPTIDE_NORMALISED_NAME, "_REVERSED", null);
                    normalisation.multithreadingCalc();
                    int existsCheckCount = 0;
                    while (!outputFile.exists()) {
                        System.out.println("Normalisation output file does not exist after poll attempt " + ++existsCheckCount);
                        if (existsCheckCount == 10) {
                            System.out.println("Not checking any more times.");
                            ProteoSuiteException pex = new ProteoSuiteException("File result of normalisation does not exist.");
                            return new ProteoSuiteActionResult(pex);
                        }

                        System.out.println("Will check " + (10 - existsCheckCount) + " more times starting in 30 seconds.");
                        Thread.sleep(30 * 1000);
                    }
                }
                catch (IOException | InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        ProteoSuiteException pex = new ProteoSuiteException("Failure to sleep thread in normalisation routine.", e);
                        return new ProteoSuiteActionResult(pex);
                    }
                    
                    ProteoSuiteException pex = new ProteoSuiteException("Normalisation routine failure.", e);
                    pex.printStackTrace();
                    return new ProteoSuiteActionResult(pex);
                }

                return new ProteoSuiteActionResult<File>(outputFile);
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>() {
            @Override
            public ProteoSuiteActionResult act(QuantDataFile argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setNormalisationDone();
                File outputFile = task.getResultOfClass(File.class);
                if (outputFile == null) {
                    ProteoSuiteException pex = new ProteoSuiteException("Unable to retrieve File result of normalisation.");
                    return new ProteoSuiteActionResult(pex);
                }

                ProteinInferenceHelper.infer(outputFile, "Label-free", ProteinInferenceHelper.LCMS_FEATURE_INTENSITY, "sum");

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
