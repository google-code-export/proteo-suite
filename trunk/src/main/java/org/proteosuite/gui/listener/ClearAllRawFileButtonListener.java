/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class ClearAllRawFileButtonListener implements ActionListener {
    private RawDataAndMultiplexingStep step;    
    public ClearAllRawFileButtonListener(RawDataAndMultiplexingStep step) {
        this.step = step;        
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        int result = JOptionPane.showConfirmDialog(step, "Are you sure you wish to clear all raw data files?\nAny progress you have made will be lost.","Clear All Raw Data Files", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            AnalyseData.getInstance().clear();
            step.refreshFromData();
        }
    }
}