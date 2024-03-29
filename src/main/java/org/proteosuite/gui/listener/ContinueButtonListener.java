package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.AnalyseStatusPanel;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.analyse.DefineConditionsStep;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.analyse.TMTStep;
import org.proteosuite.gui.tables.DefineConditionsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author SPerkins
 */
public class ContinueButtonListener implements ActionListener {

    private final JPanel panel;
    private static final AnalyseData data = AnalyseData.getInstance();
    private AnalyseDynamicTab parent;

    public ContinueButtonListener(JPanel panel) {
        this.panel = panel;        
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        parent = (AnalyseDynamicTab) panel.getParent();
        if (panel instanceof RawDataAndMultiplexingStep) {
            rawDataAndMultiplexingStep();
        } else if (panel instanceof DefineConditionsStep) {
            defineConditionsStep();
        } else if (panel instanceof CreateOrLoadIdentificationsStep) {
            identificationsStep();
        } else if (panel instanceof CleanIdentificationsStep) {
            cleanIdentificationsStep();
        }
    }   
    
    private void cleanIdentificationsStep() {
        boolean needsCleaning = false;
        for (int i = 0; i < data.getRawDataCount(); i++) {
            IdentDataFile identData = data.getRawDataFile(i).getIdentificationDataFile();
            if (identData != null && identData.isCleanable()) {
                needsCleaning = true;
                break;
            }
        }
        
        if (needsCleaning) {
            JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have not thresholded all of your data.\n"
                                + "Thresholding is a mandatory step to ensure the quality of your identifications.\n"
                                + "If you do not wish to do any quantitation, you do not need to proceed any further"
                                        + " and may visualise your data in the Inspect tab or exit ProteoSuite now.\n"
                                + "Otherwise please threshold your data to continue.",
                                "Identification Data Not Thresholded", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (data.doingIdentificationOnly()) {
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
            AnalyseStatusPanel.getInstance().setResultsAsCurrentStep();
            AnalyseStatusPanel.getInstance().setResultsProcessing();
            return;
        }
        
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

            AnalyseStatusPanel.getInstance().setQuantitationAsCurrentStep();
    }

    private void rawDataAndMultiplexingStep() {
        String selectedMultiplexing = (String) ((RawDataAndMultiplexingStep) panel)
                .getMultiplexingBox().getSelectedItem();
        boolean multiplexChange = !selectedMultiplexing.equals(data
                .getMultiplexing());
        data.setMultiplexing((String) ((RawDataAndMultiplexingStep) panel)
                .getMultiplexingBox().getSelectedItem());
        data.setGenomeAnnotationMode(((RawDataAndMultiplexingStep) panel).getGenomeAnnotationBox().isSelected());
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
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "iTRAQ 8-plex":
                    file.setAssays(new String[]{"113", "114", "115", "116", "117", "118", "119", "121"});
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "TMT 2-plex":
                    file.setAssays(new String[]{"126", "127"});
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "TMT 6-plex":
                    file.setAssays(new String[]{"126", "127", "128", "129", "130", "131"});
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "TMT 8-plex":
                    file.setAssays(new String[]{"126", "127N", "127C", "128", "129N", "129C", "130", "131"});
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "TMT 10-plex":
                    file.setAssays(new String[]{"126", "127N", "127C", "128N", "128C", "129N", "129C", "130N", "130C", "131"});
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
                case "None (label-free)":
                    file.setAssays(new String[]{""});
                    break;
                case "None (identification only)":
                    data.setIdentificationOnlyMode(true);
                    BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
                    break;
            }
        }

        boolean mzmlPresent = false;
        boolean mgfPresent = false;
        Set<String> rawErrorFiles = new LinkedHashSet<>();
        for (int i = 0; i < data.getRawDataCount(); i++) {
            RawDataFile dataFile = data.getRawDataFile(i);
            switch (dataFile.getFormat().toUpperCase()) {
                case "MZML":
                    mzmlPresent = true;
                    if (dataFile.getFile().length() < (Math.pow(1024, 2) * 50)) {
                        rawErrorFiles.add(dataFile.getFileName());
                    } 
                    
                    break;
                case "MGF":
                    mgfPresent = true;
                    // First check file is under upper limit.
                    if (dataFile.getFile().length() > Math.pow(1024, 3) || dataFile.getSpectraCount() > 25000) {
                        rawErrorFiles.add(dataFile.getFileName());
                    }
                    
                    // Now check file is over lower limit.
                    if (dataFile.getFile().length() < (Math.pow(1024, 2) * 10) || dataFile.getSpectraCount() < 1000) {                    
                        rawErrorFiles.add(dataFile.getFileName());
                    }

                    break;
            }
        }

        if (data.doingGenomeAnnotation()) {
            if (mzmlPresent) {
                // Needs fixing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have chosen to do a genome annotation run, but your raw data contains mzML files.\n"
                                + "ProteoSuite does not currently support genome annotation with mzML files.\n"
                                + "This may change at a later date.\n"
                                + "Please remove the mzML data to continue to the next stage.",
                                "mzML Data Present In Genome Annotation Run", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (data.getRawDataCount() > 1 && !data.getRawDataFile(0).isSelectedUsingFolderMode()) {
                JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have selected more than one MGF file for analysis in the genome annotation pipeline.\n"
                                + "But these files were not imported in the same step using \"Import MGFs From Folder\".\n"
                                + "To select multiple MGFs for genome annotation please clear the existing file selection and use \"Import MGFs From Folder\".\n",
                                "Multiple MGF Files : Different Data Source", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            if (rawErrorFiles.size() > 0) {
                JOptionPane
                        .showConfirmDialog(
                                panel,
                                "Some of your MGF files are too large or too small to process in the genome annotation pipeline.\n"
                                + "Files that are too large or too small to process:\n" + StringUtils.join("\n", rawErrorFiles) + "\n"
                                + "Your either have more than 1GB of data per file, or more than 25,000 spectra per file.\n"
                                + "Your data file may also be less than 10MB in size, or contain less than 1000 spectra.\n"
                                + "If you have imported a folder please remove the problem MGF file(s) from your chosen directory, then remove all MGFs from ProteoSuite and re-import your chosen folder.\n"
                                + "Otherwise delete the MGF file from ProteoSuite and import a file which meets these limits."
                                + "You may wish to split large MGF files into smaller MGF files.",
                                "MGF Files : Data Too Large Or Small", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);

                return;
            }
        } else {
            if (mgfPresent) {
                JOptionPane
                        .showConfirmDialog(
                                panel,
                                "You have not chosen to do a genome annotation run, but your raw data contains MGF files.\n"
                                + "ProteoSuite currently supports MGF files for pure identification, when not doing genome annotation.\n"
                                + "We do not support MGF data for quantitation.\n"                                
                                + "This may change at a later date.\n"
                                + "Please switch to mzML to do quantitation.",
                                "MGF Data Present", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);                
            }
            
            if (rawErrorFiles.size() > 0) {
                JOptionPane
                        .showConfirmDialog(
                                panel,
                                "Some of your raw data files are too large or too small to process in the identification pipeline.\n"
                                + "Files that are too large or too small to process:\n" + StringUtils.join("\n", rawErrorFiles) + "\n"
                                + "Your either have more than 1GB of data per MGF file, or more than 25,000 spectra per MGF file.\n"
                                + "Your data file may also be less than 10MB in size per MGF file (or less than 1000 spectra), or less than 50MB per mzML file.\n"
                                + "If you have imported a folder please remove the problem raw file(s) from your chosen directory, then remove all files from ProteoSuite and re-import your chosen folder.\n"
                                + "Otherwise delete the raw file from ProteoSuite and import a file which meets these limits."
                                + "You may wish to split large MGF files into smaller MGF files.",
                                "Raw Files : Data Too Large Or Small", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);

                return;
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
        
        if (data.doingGenomeAnnotation() || data.doingIdentificationOnly()) {
            ((CreateOrLoadIdentificationsStep) AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)
                    .refreshFromData();
            parent.moveToStep(AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP);            
            AnalyseStatusPanel.getInstance().setIdentificationsAsCurrentStep();
            if (data.doingGenomeAnnotation()) {
                AnalyseStatusPanel.getInstance().setGenomeAnnotationMode();
            }            
        } else {
            ((DefineConditionsStep) AnalyseDynamicTab.DEFINE_CONDITIONS_STEP)
                    .refreshFromData();
            parent.moveToStep(AnalyseDynamicTab.DEFINE_CONDITIONS_STEP);
            AnalyseStatusPanel.getInstance().setConditionsAsCurrentStep();
        }
    }

    private void identificationsStep() {
        boolean canMoveOn = true;
        for (int i = 0; i < data.getRawDataCount(); i++) {
            if (data.getRawDataFile(i).getIdentStatus().equals("<None>")) {
                canMoveOn = false;
                break;
            }
        }

        if (!canMoveOn) {
            JOptionPane
                    .showConfirmDialog(
                            panel,
                            "Not all raw files have identifications being loaded or created.\n"
                            + "Please correct this before moving to the next stage.",
                            "Missing Identifications", JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (data.doingGenomeAnnotation()) {
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
            AnalyseStatusPanel.getInstance().setResultsAsCurrentStep();
        } else {
            boolean identsNeedCleaning = false;
            for (int i = 0; i < data.getRawDataCount(); i++) {
                IdentDataFile file = data.getRawDataFile(i).getIdentificationDataFile();
                if (file != null && file.isCleanable()) {
                    identsNeedCleaning = true;
                    break;
                }
            }

            if (identsNeedCleaning) {
                ((CleanIdentificationsStep) AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP).refreshFromData();
                parent.moveToStep(AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP);
                AnalyseStatusPanel.getInstance().setCleanConditionsAsCurrentStep();
            } else {
               cleanIdentificationsStep();                
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
        AnalyseStatusPanel.getInstance().setIdentificationsAsCurrentStep();
    }
}