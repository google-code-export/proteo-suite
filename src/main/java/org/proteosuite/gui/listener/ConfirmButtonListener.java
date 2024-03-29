package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.analyse.TMTStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.quantitation.XTrackerITRAQWrapper;
import org.proteosuite.quantitation.XTrackerTMTWrapper;

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

        List<RawDataFile> dataFiles = new ArrayList<>();
        for (int i = 0; i < AnalyseData.getInstance().getRawDataCount(); i++) {
            dataFiles.add(AnalyseData.getInstance().getRawDataFile(i));
        }

        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationProcessing();

        if (panel instanceof LabelFreeStep) {
            OpenMSLabelFreeWrapper labelFree = new OpenMSLabelFreeWrapper(dataFiles);
            labelFree.compute();
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
        } else if (panel instanceof ITRAQStep) {
            XTrackerITRAQWrapper itraq = new XTrackerITRAQWrapper(dataFiles);
            itraq.compute();
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
        } else if (panel instanceof TMTStep) {
            XTrackerTMTWrapper tmt = new XTrackerTMTWrapper(dataFiles);
            tmt.compute();
            parent.moveToStep(AnalyseDynamicTab.DONE_STEP);
        }
    }
}