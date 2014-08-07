package org.proteosuite.actions;

import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.QuantDataFile;

/**
 *
 * @author SPerkins
 */
public class QuantFilePostLoadAction implements ProteoSuiteAction<Void, QuantDataFile> {
    @Override
    public Void act(QuantDataFile quantData) {
        AnalyseData.getInstance().getInspectModel()
                .addQuantDataFile(quantData);
        InspectTab.getInstance().refreshComboBox();
        
        return null;
    }

}
