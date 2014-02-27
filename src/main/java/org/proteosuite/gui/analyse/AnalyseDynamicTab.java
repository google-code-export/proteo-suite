/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.analyse;

import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class AnalyseDynamicTab extends JPanel {
    
    public static final JPanel RAW_DATA_AND_MULTIPLEXING_STEP = new RawDataAndMultiplexingStep();
    public static final JPanel DEFINE_CONDITIONS_STEP = new DefineConditionsStep();
    public static final JPanel CREATE_OR_LOAD_IDENTIFICATIONS_STEP = new CreateOrLoadIdentificationsStep();

    public AnalyseDynamicTab() {
        add(RAW_DATA_AND_MULTIPLEXING_STEP);
    }
    
    public void moveToStep(JPanel panel) {
        removeAll();
        add(panel);        
        repaint();
    }
}
