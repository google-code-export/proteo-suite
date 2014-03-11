/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.analyse.DefineConditionsStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.tables.DefineConditionsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class ContinueButtonListener implements ActionListener {

    private JPanel panel;

    public ContinueButtonListener(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseDynamicTab parent = (AnalyseDynamicTab) panel.getParent();
        AnalyseData data = AnalyseData.getInstance();
        if (panel instanceof RawDataAndMultiplexingStep) {
            for (int i  = 0; i < data.getRawDataCount(); i++) {
                RawDataFile file = data.getRawDataFile(i);
                if (data.getMultiplexing().equals("iTRAQ 4-plex")) {
                    file.setAssays(new String[]{"114", "115", "116", "117"});
                } else if (data.getMultiplexing().equals("None (label-free)")) {
                    file.setAssays(new String[]{""});
                }
            }

            ((DefineConditionsStep) AnalyseDynamicTab.DEFINE_CONDITIONS_STEP).refreshFromData();
            parent.moveToStep(AnalyseDynamicTab.DEFINE_CONDITIONS_STEP);

        } else if (panel instanceof DefineConditionsStep) {
            DefineConditionsTable conditionsTable = ((DefineConditionsStep) panel).getConditionsTable();

            for (int i = 0; i < conditionsTable.getRowCount(); i++) {
                String condition = (String) conditionsTable.getModel().getValueAt(i, 0);
                if (condition.equals("")) {
                    int result = JOptionPane.showConfirmDialog(panel, "You have not set a condition for all multiplex assays.\nPlease correct this before moving to the next stage.", "Missing Conditions", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
                    return;
                }

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

            ((CreateOrLoadIdentificationsStep) AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP).refreshFromData();
            parent.moveToStep(AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP);
        } else if (panel instanceof CreateOrLoadIdentificationsStep) {
            if (data.getMultiplexing().equals("None (label-free)")) {
                ((LabelFreeStep) AnalyseDynamicTab.LABEL_FREE_STEP).refreshFromData();
                parent.moveToStep(AnalyseDynamicTab.LABEL_FREE_STEP);
            } else if (data.getMultiplexing().equals("iTRAQ 4-plex")) {
                ((LabelFreeStep) AnalyseDynamicTab.ITRAQ_STEP).refreshFromData();
                parent.moveToStep(AnalyseDynamicTab.ITRAQ_STEP);
            } else if (data.getMultiplexing().equals("iTRAQ 8-plex")) {
            
            }
        }
    }
}
