package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.proteosuite.Launcher;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.TabbedMainPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.identification.SearchGuiViaMzidLibWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CreateIdentificationsForSelectedListener implements ActionListener {

    private final CreateOrLoadIdentificationsStep step;

    public CreateIdentificationsForSelectedListener(
            CreateOrLoadIdentificationsStep step) {
        this.step = step;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseData data = AnalyseData.getInstance();

        // Get the identParamsView.
        final IdentParamsView identDialog = step.getIdentParamsView();

        // Add the view to the content pane.
        identDialog.setVisible(true);

        if (identDialog.hasRunSuccessfully()) {
            step.setCreateButtonEnabled(false);
        } else {
            return;
        }

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .setIdentificationsProcessing();

        Map<String, String> runParams = identDialog.getSearchGUIParameterSet();

        if (data.doingGenomeAnnotation()) {
            Set<RawDataFile> rawDataFiles = new HashSet<>();
            for (int i = 0; i < data.getRawDataCount(); i++) {
                RawDataFile rawDataFile = data.getRawDataFile(i);
                if (rawDataFile instanceof MascotGenericFormatFile) {
                    rawDataFile.setIdentStatus("Creating...");
                    rawDataFiles.add(rawDataFile);
                }
            }

            step.refreshFromData();

            try {
                SearchGuiViaMzidLibWrapper wrapper = new SearchGuiViaMzidLibWrapper(
                        rawDataFiles,
                        identDialog.getGeneModel(),
                        identDialog.getOtherGeneModels(),
                        runParams,
                        identDialog.getOutputFilePrefix(),
                        identDialog.getPeptideLevelThresholding(),
                        identDialog.getProteinLevelThresholding());

                wrapper.printDebugInfo();
                wrapper.compute();
            }
            catch (ProteoSuiteException ex) {
                Launcher.handleException(ex);
            }

            TabbedMainPanel.getInstance().setSelectedIndex(2);
        } else {
            // THIS IS REALLY INELEGANT - CHANGE ME!!!
            if (data.getRawDataCount() > 0 && data.getRawDataFile(0).isSelectedUsingFolderMode()) {
                Set<RawDataFile> rawDataFiles = new HashSet<>();
                for (int i = 0; i < data.getRawDataCount(); i++) {
                    RawDataFile rawDataFile = data.getRawDataFile(i);
                    rawDataFile.setIdentStatus("Creating...");
                    rawDataFiles.add(rawDataFile);

                }

                step.refreshFromData();

                try {
                SearchGuiViaMzidLibWrapper wrapper = new SearchGuiViaMzidLibWrapper(rawDataFiles,
                        identDialog.getSingleDatabasePath(),
                        runParams,
                        identDialog.getPeptideLevelThresholding(),
                        identDialog.getProteinLevelThresholding());

                wrapper.compute();} catch (ProteoSuiteException ex) {
                    Launcher.handleException(ex);
                }
                
                return;
            }

            // Get the selected raw files from the table to know which ones to run
            // ident for.           
            int[] selectedRawFiles = step.getIdentificationsTable()
                    .getSelectedRows();
            for (int fileIndex = 0; fileIndex < selectedRawFiles.length; fileIndex++) {
                RawDataFile rawDataFile = data
                        .getRawDataFile(selectedRawFiles[fileIndex]);
                rawDataFile.setIdentStatus("Creating...");
                step.refreshFromData();

                try {
                SearchGuiViaMzidLibWrapper wrapper = new SearchGuiViaMzidLibWrapper(
                        Collections.singleton(rawDataFile),
                        identDialog.getSingleDatabasePath(),
                        runParams,
                        identDialog.getPeptideLevelThresholding(),
                        identDialog.getProteinLevelThresholding());

                wrapper.compute();
                } catch (ProteoSuiteException ex) {
                    Launcher.handleException(ex);
                }
            }
        }
    }
}
