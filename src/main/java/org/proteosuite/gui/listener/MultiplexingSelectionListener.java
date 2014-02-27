/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class MultiplexingSelectionListener implements ActionListener {
    private RawDataAndMultiplexingStep step;    
    public MultiplexingSelectionListener(RawDataAndMultiplexingStep step) {
        this.step = step;        
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        AnalyseData.getInstance().setMultiplexing((String)step.getMultiplexingBox().getSelectedItem());        
    }
}
