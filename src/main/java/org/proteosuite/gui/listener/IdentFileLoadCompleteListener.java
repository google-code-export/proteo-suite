/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.listener;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.ProteoSuiteActionEvent;
import org.proteosuite.model.Task;

/**
 *
 * @author SPerkins
 */
public class IdentFileLoadCompleteListener implements ProteoSuiteActionListener<IdentDataFile> {

    @Override
    public void fire(ProteoSuiteActionEvent<IdentDataFile> event) {
        IdentDataFile dataFile = null;
        if (event.getSubject() instanceof IdentDataFile) {
            dataFile = (IdentDataFile) event.getSubject();
        } else {
            return;
        }

        AnalyseData.getInstance().getInspectModel().addIdentDataFile(dataFile);
        InspectTab.getInstance().refreshComboBox();

        AnalyseData.getInstance().getTasksModel().set(new Task(dataFile.getFileName(), "Loading Identifications", "Complete"));
        TasksTab.getInstance().refreshFromTasksModel();

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateIdentificationsStatus();

        if (dataFile.getParent() != null) {
            dataFile.getParent().setIdentStatus("Done");
            ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();
        }
        
        dataFile.computePSMStats();
    }

}
