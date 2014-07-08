package org.proteosuite.gui.listener;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.ProteoSuiteActionEvent;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.Task;

/**
 *
 * @author SPerkins
 */
public class RawFileLoadCompleteListener implements ProteoSuiteActionListener<RawDataFile> {

    @Override
    public void fire(ProteoSuiteActionEvent<RawDataFile> event) {
        RawDataFile dataFile = null;
        if (event.getSubject() instanceof RawDataFile) {
            dataFile = (RawDataFile) event.getSubject();
        } else {
            return;
        }

        RawDataAndMultiplexingStep.getInstance().refreshFromData();

        AnalyseData.getInstance().getInspectModel()
                .addRawDataFile(dataFile);
        InspectTab.getInstance().refreshComboBox();
        AnalyseData
                .getInstance()
                .getTasksModel()
                .set(new Task(dataFile.getFileName(), "Load Raw Data",
                                "Complete"));
        TasksTab.getInstance().refreshFromTasksModel();

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .checkAndUpdateRawDataStatus();

    }
}
