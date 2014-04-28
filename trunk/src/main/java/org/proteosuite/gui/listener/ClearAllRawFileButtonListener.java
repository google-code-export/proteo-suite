package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class ClearAllRawFileButtonListener implements ActionListener {
    private final RawDataAndMultiplexingStep step;
    
    public ClearAllRawFileButtonListener(RawDataAndMultiplexingStep step) {
        this.step = step;    
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        int result = JOptionPane.showConfirmDialog(step, "Are you sure you wish to clear all raw data files?\nAny progress you have made will be lost.","Clear All Raw Data Files", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION)
        	return;
        	
        AnalyseData.getInstance().clear();
        step.refreshFromData();
        InspectTab.getInstance().refreshComboBox();
    }
}
