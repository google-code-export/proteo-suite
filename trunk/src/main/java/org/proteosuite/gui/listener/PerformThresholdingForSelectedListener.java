package org.proteosuite.gui.listener;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.ThresholdingPopup;
import org.proteosuite.gui.tables.CleanIdentificationsTable;
import org.proteosuite.identification.ThresholdWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;

/**
 *
 * @author SPerkins
 */
public class PerformThresholdingForSelectedListener implements ActionListener {

    private CleanIdentificationsStep step;
    private final AnalyseData data = AnalyseData.getInstance();

    public PerformThresholdingForSelectedListener(CleanIdentificationsStep step) {
        this.step = step;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, String> thresholdables = null;

        CleanIdentificationsTable table = step.getCleanIdentificationsTable();
        int selectedRow;
        if ((selectedRow = table.getSelectedRow()) < 0) {
            return;
        }

        IdentDataFile identFileToProcess = null;
        String fileName = (String) table.getModel().getValueAt(selectedRow, 0);
        for (int i = 0; i < data.getRawDataCount(); i++) {
            IdentDataFile identDataFile = data.getRawDataFile(i).getIdentificationDataFile();
            if (identDataFile != null && identDataFile.getFileName().equals(fileName)) {
                    // do something!
                thresholdables = identDataFile.getThresholdables();
                identFileToProcess = identDataFile;
                break;
            }
        }        
        
        if (identFileToProcess == null) {
            return;
        }

        Component parent = step.getParent();
        while (!(parent instanceof JFrame)) {
            parent = parent.getParent();
        }

        ThresholdingPopup popup = new ThresholdingPopup((Window) parent, thresholdables);

        popup.setVisible(true);

        if (popup.hasBeenClosed()) {
            return;
        }

        String[] thresholdToPerform = popup.getThresholdingChosen();
        ThresholdWrapper wrapper = new ThresholdWrapper(identFileToProcess, thresholdToPerform[0], Double.valueOf(thresholdToPerform[2]), thresholdToPerform[3].equals(">"));
        wrapper.doThresholding();
    }   
}