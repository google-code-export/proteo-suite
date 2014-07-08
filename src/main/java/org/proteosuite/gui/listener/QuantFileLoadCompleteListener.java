/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.listener;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.ProteoSuiteActionEvent;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.Task;

/**
 *
 * @author SPerkins
 */
public class QuantFileLoadCompleteListener implements ProteoSuiteActionListener {

    @Override
    public void fire(ProteoSuiteActionEvent event) {
        QuantDataFile dataFile = null;
        if (event.getSubject() instanceof QuantDataFile) {
            dataFile = (QuantDataFile) event.getSubject();
        } else {
            return;
        }

        InspectTab.getInstance().refreshComboBox();
        AnalyseData.getInstance().getTasksModel().set(new Task(dataFile.getFileName(), "Load Quantitation Data", "Complete"));
        TasksTab.getInstance().refreshFromTasksModel();

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateRawDataStatus();
    }

}
