package org.proteosuite.identification;

import java.io.File;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.RawDataFile;
import uk.ac.liv.mzidlib.ThresholdMzid;
import uk.ac.liv.mzidlib.fdr.FalseDiscoveryRateGlobal;

/**
 *
 * @author SPerkins
 */
public class FDRThresholdWrapper {

    private static final AnalyseData data = AnalyseData.getInstance();
    private static final String peptideLevelTerm = "MS:1002360";
    //private static final String psmLevelTerm = "MS:1002351";
    private static final String psmLevelTerm = "MS:1002355";
    private final IdentDataFile identDataFile;
    private final String fdrThresholdTerm;
    private final double fdrThreshold;
    private final boolean lowerValuesBetter;
    private final String fdrLevel;
    private final String decoyHitTag;

    private String outputPath;

    public FDRThresholdWrapper(IdentDataFile identDataFile, String fdrThresholdTerm, double fdrThreshold, boolean lowerValuesBetter, String fdrLevel, String decoyHitTag) {
        this.identDataFile = identDataFile;
        this.fdrThresholdTerm = fdrThresholdTerm;
        this.fdrThreshold = fdrThreshold;
        this.lowerValuesBetter = lowerValuesBetter;
        this.fdrLevel = fdrLevel;
        this.decoyHitTag = decoyHitTag;
    }

    public void doThresholding() {
        identDataFile.setThresholdStatus("Thresholding...");
        CleanIdentificationsStep.getInstance().refreshFromData();
        
        final BackgroundTask task = new BackgroundTask(new BackgroundTaskSubject() {
            @Override
            public String getSubjectName() {
                return identDataFile.getFileName();
            }
        }, "FDR Thresholding Identifications");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public String act(BackgroundTaskSubject argument) {
                FalseDiscoveryRateGlobal fdrGlobal = new FalseDiscoveryRateGlobal(identDataFile.getAbsoluteFileName(), "1", decoyHitTag, fdrThresholdTerm, lowerValuesBetter, fdrLevel, null);
                fdrGlobal.computeFDRusingJonesMethod();
                String fdrPath = identDataFile.getAbsoluteFileName().replace(".mzid", "_fdr.mzid");
                outputPath = fdrPath.replace(".mzid", "_thresholded.mzid");
                fdrGlobal.writeToMzIdentMLFile(fdrPath);

                File fdrFile = new File(fdrPath);
                if (fdrFile.exists()) {
                    new ThresholdMzid(fdrPath, outputPath, true, fdrLevel.equals("Peptide") ? peptideLevelTerm : psmLevelTerm, fdrThreshold, true, false);
                }

                return outputPath;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                RawDataFile rawDataFile = identDataFile.getParent();
                File newFile = new File(outputPath);
                if (!newFile.exists()) {
                    return null;
                }

                IdentDataFile newIdentFile = new MzIdentMLFile(newFile, rawDataFile);
                newIdentFile.setCleanable(false);
                rawDataFile.setIdentificationDataFile(newIdentFile);

                data.getInspectModel().removeIdentDataFile(identDataFile);
                data.getInspectModel().addIdentDataFile(newIdentFile);
                InspectTab.getInstance().refreshComboBox();

                CleanIdentificationsStep.getInstance().refreshFromData();
                
                boolean allCleaned = true;
                for (int i = 0; i < data.getRawDataCount(); i++) {
                    IdentDataFile identData = data.getRawDataFile(i).getIdentificationDataFile();
                    if (identDataFile != null && identData.isCleanable()) {
                        allCleaned = false;
                        break;
                    }
                }
                
                if (allCleaned) {
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setCleanIdentificationsDone();
                }

                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}