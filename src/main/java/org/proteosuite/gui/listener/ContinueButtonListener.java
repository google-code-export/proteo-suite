package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.analyse.DefineConditionsStep;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.analyse.TMTStep;
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
            String selectedMultiplexing = (String) ((RawDataAndMultiplexingStep) panel)
                    .getMultiplexingBox().getSelectedItem();
            boolean multiplexChange = !selectedMultiplexing.equals(data
                    .getMultiplexing());
            data.setMultiplexing((String) ((RawDataAndMultiplexingStep) panel)
                    .getMultiplexingBox().getSelectedItem());
            boolean allMS1PeakPicked = true;
            boolean allMS1Present = true;
            for (int i = 0; i < data.getRawDataCount(); i++) {
                RawDataFile file = data.getRawDataFile(i);
                // Check if this file is not MS1 peak picked.
                if (!file.getPeakPicking()[0]) {
                    allMS1PeakPicked = false;
                }
                
                if (!file.getMSLevelPresence()[0]) {
                    allMS1Present = false;
                }

                if (multiplexChange) {
                    file.resetAssay();
                }

                switch (data.getMultiplexing()) {
                    case "iTRAQ 4-plex":
                        file.setAssays(new String[]{"114", "115", "116", "117"});
                        break;
                    case "iTRAQ 8-plex":
                        file.setAssays(new String[]{"113", "114", "115", "116", "117", "118", "119", "121"});
                        break;
                    case "TMT 2-plex":
                        file.setAssays(new String[]{"126", "127"});
                        break;
                    case "TMT 6-plex":
                        file.setAssays(new String[]{"126", "127", "128", "129", "130", "131"});
                        break;
                    case "TMT 8-plex":
                        file.setAssays(new String[]{"126", "127N", "127C", "128", "129N", "129C", "130", "131"});
                        break;
                    case "TMT 10-plex":
                        file.setAssays(new String[]{"126", "127N", "127C", "128N", "128C", "129N", "129C", "130N", "130C", "131"});
                        break;
                    case "None (label-free)":
                        file.setAssays(new String[]{""});
                        break;
                }                
            }
            
            if (data.getMultiplexing().equals("None (label-free)")) {
                if (!allMS1Present) {
                    JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have chosen \"None (label-free)\" as your multiplexing, but not all files appear to contain MS1 data.\n"
                                        + "This detection is based on a sampling and may be incorrect.\n"
                                        + "Raw MS1 data must be present before a label-free analysis can be performed.\n"                                        
                                        + "Please correct this before moving to the next stage.",
                                "Raw MS1 Data Not Present", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
                return;
                }
                
                if (!allMS1PeakPicked) {
                    JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have chosen \"None (label-free)\" as your multiplexing, but not all MS1 data is peak-picked.\n"
                                        + "\"This detection is based on a sampling and may be incorrect.\n"
                                        + "Raw MS1 data must already be peak picked before a label-free analysis can be performed.\nPlease correct this before moving to the next stage.",
                                "Raw MS1 Data Not Peak Picked", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
                return;
                }
            }           

            ((DefineConditionsStep) AnalyseDynamicTab.DEFINE_CONDITIONS_STEP)
                    .refreshFromData();

            parent.moveToStep(AnalyseDynamicTab.DEFINE_CONDITIONS_STEP);

        } else if (panel instanceof DefineConditionsStep) {
            defineConditionsStep();
        } else if (panel instanceof CreateOrLoadIdentificationsStep) {
            ((CleanIdentificationsStep) AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP).refreshFromData();
            parent.moveToStep(AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP);
        } else if (panel instanceof CleanIdentificationsStep) {
            switch (data.getMultiplexing()) {
                case "None (label-free)":
                    ((LabelFreeStep) AnalyseDynamicTab.LABEL_FREE_STEP)
                            .refreshFromData();
                    parent.moveToStep(AnalyseDynamicTab.LABEL_FREE_STEP);
                    break;
                case "iTRAQ 4-plex":
                case "iTRAQ 8-plex":
                    ((ITRAQStep) AnalyseDynamicTab.ITRAQ_STEP).refreshFromData();
                    parent.moveToStep(AnalyseDynamicTab.ITRAQ_STEP);
                    break;
                case "TMT 2-plex":
                case "TMT 6-plex":
                case "TMT 8-plex":
                case "TMT 10-plex":
                    ((TMTStep) AnalyseDynamicTab.TMT_STEP).refreshFromData();
                    parent.moveToStep(AnalyseDynamicTab.TMT_STEP);
                    break;
            }
        }
    }

    private void defineConditionsStep() {
        DefineConditionsTable conditionsTable = ((DefineConditionsStep) panel)
                .getConditionsTable();
        if (!conditionsTable.isConditionsValid()) {
            JOptionPane
                    .showConfirmDialog(
                            panel,
                            "You have not set a condition for all multiplex assays.\nPlease correct this before moving to the next stage.",
                            "Missing Conditions", JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.ERROR_MESSAGE);
            return;
        }

        AnalyseData data = AnalyseData.getInstance();
        AnalyseDynamicTab parent = (AnalyseDynamicTab) panel.getParent();

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .setConditionsProcessing();

        for (int i = 0; i < conditionsTable.getRowCount(); i++) {
            String condition = (String) conditionsTable.getModel().getValueAt(
                    i, 0);

            String fileName = (String) conditionsTable.getModel().getValueAt(i,
                    1);
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

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .setConditionsDone();

        ((CreateOrLoadIdentificationsStep) AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)
                .refreshFromData();
        parent.moveToStep(AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP);
    }
}
