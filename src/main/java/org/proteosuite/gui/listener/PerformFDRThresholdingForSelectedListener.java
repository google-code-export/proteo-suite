package org.proteosuite.gui.listener;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.FalseDiscoveryRateThresholdingPopup;
import org.proteosuite.gui.tables.CleanIdentificationsTable;
import org.proteosuite.identification.FDRThresholdWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;

/**
 *
 * @author SPerkins
 */
public class PerformFDRThresholdingForSelectedListener implements ActionListener {
private final CleanIdentificationsStep step;
    private final AnalyseData data = AnalyseData.getInstance();

    public PerformFDRThresholdingForSelectedListener(CleanIdentificationsStep step) {
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
        
        if (identFileToProcess == null || !identFileToProcess.isCleanable() || identFileToProcess.getThresholdStatus().equals("Thresholding...") || identFileToProcess.getPSMCountPassingThreshold() == -1) {
            return;
        }

        Component parent = step.getParent();
        while (!(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        
        FalseDiscoveryRateThresholdingPopup popup = new FalseDiscoveryRateThresholdingPopup((Window) parent, thresholdables);
        
        popup.setVisible(true);

        if (popup.hasBeenClosed()) {
            return;
        }
        
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setCleanIdentificationsProcessing();
        
        String thresholdTerm = popup.getThresholdTermChosen();
        double fdrThresholdValue = popup.getFDRThresholdValueChosen();
        boolean lowerValuesBetter = popup.areLowerValuesBetter();
        String fdrLevel = popup.getFDRLevel();
        String decoyHitTag = popup.getDecoyHitTag();
        FDRThresholdWrapper wrapper = new FDRThresholdWrapper(identFileToProcess, thresholdTerm, fdrThresholdValue, lowerValuesBetter, fdrLevel, decoyHitTag);
        wrapper.doThresholding();
    }
}
