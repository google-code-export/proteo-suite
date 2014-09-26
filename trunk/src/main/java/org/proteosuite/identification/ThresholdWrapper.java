package org.proteosuite.identification;

import java.io.File;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.RawDataFile;
import uk.ac.liv.mzidlib.ThresholdMzid;

/**
 *
 * @author SPerkins
 */
public class ThresholdWrapper {

    private static final AnalyseData data = AnalyseData.getInstance();
    private final IdentDataFile dataFile;
    private final String thresholdOn;
    private final double thresholdValue;
    private final boolean higherValuesBetter;

    private String outputPath;

    public ThresholdWrapper(IdentDataFile dataFile, String thresholdOn, double thresholdValue, boolean higherValuesBetter) {
        this.dataFile = dataFile;
        this.thresholdOn = thresholdOn;
        this.thresholdValue = thresholdValue;
        this.higherValuesBetter = higherValuesBetter;

    }

    public IdentDataFile getOriginalIdentData() {
        return this.dataFile;
    }

    public String getThresholdedOutputPath() {
        return this.outputPath;
    }

    public void doThresholding() {
        dataFile.setThresholdStatus("Thresholding...");
        CleanIdentificationsStep.getInstance().refreshFromData();
        
        final BackgroundTask task = new BackgroundTask(new BackgroundTaskSubject() {
            @Override
            public String getSubjectName() {
                return dataFile.getFileName();
            }
        }, "Thresholding Identifications");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public String act(BackgroundTaskSubject argument) {
                outputPath = dataFile.getAbsoluteFileName().replace(".mzid", "_thresholded.mzid");
                new ThresholdMzid(dataFile.getAbsoluteFileName(), outputPath, true, thresholdOn, thresholdValue, !ThresholdWrapper.this.higherValuesBetter, false);
                return outputPath;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                RawDataFile rawDataFile = dataFile.getParent();
                File newFile = new File(outputPath);
                if (!newFile.exists()) {
                    return null;
                }

                IdentDataFile newIdentFile = new MzIdentMLFile(newFile, rawDataFile);
                newIdentFile.setCleanable(false);
                rawDataFile.setIdentificationDataFile(newIdentFile);

                data.getInspectModel().removeIdentDataFile(dataFile);
                data.getInspectModel().addIdentDataFile(newIdentFile);
                InspectTab.getInstance().refreshComboBox();

                CleanIdentificationsStep.getInstance().refreshFromData();
                
                boolean allCleaned = true;
                for (int i = 0; i < data.getRawDataCount(); i++) {
                    IdentDataFile identData = data.getRawDataFile(i).getIdentificationDataFile();
                    if (identData != null && identData.isCleanable()) {
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
