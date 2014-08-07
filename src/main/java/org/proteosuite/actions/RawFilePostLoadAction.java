package org.proteosuite.actions;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class RawFilePostLoadAction implements ProteoSuiteAction<Void, RawDataFile> {

    @Override
    public Void act(RawDataFile rawData) {
        if (rawData == null) {
            return null;
        }
        
        RawDataAndMultiplexingStep.getInstance().refreshFromData();
        
        AnalyseData.getInstance().getInspectModel()
                .addRawDataFile(rawData);
        InspectTab.getInstance().refreshComboBox();
        
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .checkAndUpdateRawDataStatus();
        
        return null;        
    }
}