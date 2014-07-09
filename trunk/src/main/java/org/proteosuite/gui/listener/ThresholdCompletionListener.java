
package org.proteosuite.gui.listener;

import java.io.File;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.identification.ThresholdWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.ProteoSuiteActionEvent;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class ThresholdCompletionListener implements ProteoSuiteActionListener<ThresholdWrapper> {
    private static final AnalyseData data = AnalyseData.getInstance();
    @Override
    public void fire(ProteoSuiteActionEvent<ThresholdWrapper> event) {
        ThresholdWrapper wrapper = event.getSubject();
        IdentDataFile originalFile = wrapper.getOriginalIdentData();
        RawDataFile rawDataFile = originalFile.getParent();
        File newFile = new File(wrapper.getThresholdedOutputPath());
        if (!newFile.exists()) {
            return;
        }
        
        
        IdentDataFile newIdentFile = new MzIdentMLFile(newFile, rawDataFile);       
        newIdentFile.setCleanable(false);
        rawDataFile.setIdentificationDataFile(originalFile);
        
        data.getInspectModel().removeIdentDataFile(originalFile);
        data.getInspectModel().addIdentDataFile(newIdentFile);
        InspectTab.getInstance().refreshComboBox();
        
        CleanIdentificationsStep.getInstance().refreshFromData();
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
