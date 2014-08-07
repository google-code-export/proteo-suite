package org.proteosuite.actions;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;

/**
 *
 * @author SPerkins
 */
public class IdentFilePostLoadAction implements ProteoSuiteAction<Void, IdentDataFile> {
    @Override
    public Void act(IdentDataFile identData) {
        AnalyseData.getInstance().getInspectModel().addIdentDataFile(identData);
        InspectTab.getInstance().refreshComboBox(); 
        
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateIdentificationsStatus();
        
        if (identData.getParent() != null) {
            identData.getParent().setIdentStatus("Done");
            ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();
        }
        
        identData.computePSMStats();
        
        return null;
    }
}