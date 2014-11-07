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

    private static final AnalyseData data = AnalyseData.getInstance();

    @Override
    public Void act(IdentDataFile identData) {
        AnalyseData.getInstance().getInspectModel().addIdentDataFile(identData);
        InspectTab.getInstance().refreshComboBox();        

        if (identData.getParent() != null) {
            if (identData.getParent().getIdentificationDataFile() == null) {
                identData.getParent().setIdentificationDataFile(identData);
            }
            
            identData.getParent().setIdentStatus("Done");
            identData.computePSMStats();
        } else if (data.doingGenomeAnnotation()) {
            for (int i = 0; i < data.getRawDataCount(); i++) {
                data.getRawDataFile(i).setIdentStatus("Done");
            }
        }
        
        boolean identsAllDone = AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateIdentificationsStatus();
        if (data.doingIdentificationOnly() && identsAllDone) {
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setResultsDone();
        }

        ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();

        return null;
    }
}
