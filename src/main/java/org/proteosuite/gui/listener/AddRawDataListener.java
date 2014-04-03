package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawMzMLFile;

/**
 *
 * @author SPerkins
 */
public class AddRawDataListener implements ActionListener {
    private final RawDataAndMultiplexingStep step;
    
    public AddRawDataListener(RawDataAndMultiplexingStep step) {
        this.step = step;    
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseData data = AnalyseData.getInstance();
        JFileChooser chooser = new JFileChooser(ProteoSuiteView.sPreviousLocation);
        FileNameExtensionFilter mzML_filter = new FileNameExtensionFilter(
        "mzML Data Files", "mzML", "mzML");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(mzML_filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int returnVal = chooser.showOpenDialog(step);
        if (returnVal != JFileChooser.APPROVE_OPTION)
        	return;
        	
        	
            File[] files = chooser.getSelectedFiles(); 
            if (!files[0].getParent().equals(ProteoSuiteView.sPreviousLocation)) {
                ProteoSuiteView.sPreviousLocation = files[0].getParent();
            }
            
            for (File f : files) {
                data.addRawDataFile(new RawMzMLFile(f));               
            }
        
        
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setRawDataProcessing();
        step.refreshFromData();
    }
}