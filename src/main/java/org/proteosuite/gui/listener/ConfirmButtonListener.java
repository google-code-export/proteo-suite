package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.quantitation.XTrackerITRAQWrapper;

/**
 *
 * @author SPerkins
 */
public class ConfirmButtonListener implements ActionListener {
    private JPanel panel;
    public ConfirmButtonListener(JPanel panel) {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseDynamicTab parent = (AnalyseDynamicTab) panel.getParent();
        if (panel instanceof LabelFreeStep) {
            List<RawDataFile> dataFiles = new ArrayList<RawDataFile>();
            for (int i = 0; i < AnalyseData.getInstance().getRawDataCount(); i++) {
                dataFiles.add(AnalyseData.getInstance().getRawDataFile(i));
            }
            
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationProcessing();
            
            OpenMSLabelFreeWrapper labelFree = new OpenMSLabelFreeWrapper(dataFiles);
            labelFree.compute();
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
        } else if (panel instanceof ITRAQStep) {
            List<RawDataFile> dataFiles = new ArrayList<RawDataFile>();
            for (int i = 0; i < AnalyseData.getInstance().getRawDataCount(); i++) {
                dataFiles.add(AnalyseData.getInstance().getRawDataFile(i));
            }
            
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationProcessing();
            
            XTrackerITRAQWrapper itraq = new XTrackerITRAQWrapper(dataFiles);
            itraq.compute();
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
        }
    }
}
