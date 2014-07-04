package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.AnalyseStatusPanel;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.analyse.DefineConditionsStep;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.tables.DefineConditionsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class PreviousButtonListener implements ActionListener {
    private JPanel panel;    
    public PreviousButtonListener(JPanel panel) {
        this.panel = panel;        
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseData data = AnalyseData.getInstance();
        AnalyseDynamicTab parent = (AnalyseDynamicTab)panel.getParent();
        if (panel instanceof DefineConditionsStep) {      
            DefineConditionsTable conditionsTable = ((DefineConditionsStep) panel).getConditionsTable();
            for (int i = 0; i < conditionsTable.getRowCount(); i++) {
                String condition = (String) conditionsTable.getModel().getValueAt(i, 0);                

                String fileName = (String) conditionsTable.getModel().getValueAt(i, 1);
                String assay = (String) conditionsTable.getModel().getValueAt(i, 2);
                for (int j = 0; j < data.getRawDataCount(); j++) {
                    RawDataFile dataFile = data.getRawDataFile(j);
                    if (dataFile.getFileName().equals(fileName)) {
                        if (dataFile.getConditions().containsKey(assay)) {
                            dataFile.getConditions().put(assay, condition);
                        }
                    }
                }
            }           
            
            parent.moveToStep(AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP);  
            AnalyseStatusPanel.getInstance().setRawDataAsCurrentStep();
        } else if (panel instanceof CreateOrLoadIdentificationsStep) {
            if (!data.getGenomeAnnotationMode()) {
                parent.moveToStep(AnalyseDynamicTab.DEFINE_CONDITIONS_STEP);
                AnalyseStatusPanel.getInstance().setConditionsAsCurrentStep();
            } else {
                parent.moveToStep(AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP);
                AnalyseStatusPanel.getInstance().setRawDataAsCurrentStep();
            }            
        } else if (panel instanceof CleanIdentificationsStep) {
            parent.moveToStep(AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP);
            AnalyseStatusPanel.getInstance().setIdentificationsAsCurrentStep();
        } else if (panel instanceof LabelFreeStep || panel instanceof ITRAQStep) {
            parent.moveToStep(AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP);
            AnalyseStatusPanel.getInstance().setCleanConditionsAsCurrentStep();
        }
    }  
}
