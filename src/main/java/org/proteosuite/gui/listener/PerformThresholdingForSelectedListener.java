package org.proteosuite.gui.listener;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.ThresholdingPopup;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tables.CleanIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.RawDataFile;
import uk.ac.liv.mzidlib.ThresholdMzid;

/**
 *
 * @author SPerkins
 */
public class PerformThresholdingForSelectedListener implements ActionListener {

    private CleanIdentificationsStep step;
    private AnalyseData data;
    public PerformThresholdingForSelectedListener(CleanIdentificationsStep step) {
        this.step = step;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AnalyseData data = AnalyseData.getInstance();
        Map<String, String> thresholdables = new HashMap<>();

        for (int i = 0; i < data.getRawDataCount(); i++) {
            Map<String, String> thresholdablesLocal = data.getRawDataFile(i).getIdentificationDataFile().getThresholdables();
            thresholdables.putAll(thresholdablesLocal);
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
        CleanIdentificationsTable table =  step.getCleanIdentificationsTable();
        int[] selectedRows = table.getSelectedRows();
        for (int fileIndex : selectedRows) {
            String fileName = (String)table.getValueAt(fileIndex, 0);
            for (int i = 0; i < data.getRawDataCount(); i++) {
                 IdentDataFile identDataFile = data.getRawDataFile(i).getIdentificationDataFile();
                 if (identDataFile != null && identDataFile.getFileName().equals(fileName)) {
                     compute(identDataFile, thresholdToPerform);
                 }
            }
        }              
    }
    
    private void compute(final IdentDataFile identData, final String[] thresholdToPerform) {
        final String outputPath = identData.getAbsoluteFileName().replace(".mzid", "_thresholded.mzid");
        SwingWorker<Void, Void> thresholdWorker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() {  
                new ThresholdMzid(identData.getAbsoluteFileName(), outputPath, true, thresholdToPerform[0], Double.parseDouble(thresholdToPerform[2]), thresholdToPerform.equals("<"), false);
                return null;
            }
            
            @Override
            protected void done() {
                RawDataFile rawData = identData.getParent();
                IdentDataFile newIdentFile = new MzIdentMLFile(new File(outputPath), rawData);
                rawData.setIdentificationDataFile(newIdentFile);     
                
                data.getInspectModel().removeIdentDataFile(identData);
                data.getInspectModel().addIdentDataFile(newIdentFile);
                InspectTab.getInstance().refreshComboBox();
                
                step.refreshFromData();
            }        
        };
        
        data.getGenericExecutor().submit(thresholdWorker); 
    }
}
