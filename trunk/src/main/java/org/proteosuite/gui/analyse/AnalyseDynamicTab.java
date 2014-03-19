/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class AnalyseDynamicTab extends JPanel {
    private static AnalyseDynamicTab instance = null;
    private final BorderLayout layout = new BorderLayout();
    public static final JPanel RAW_DATA_AND_MULTIPLEXING_STEP = new RawDataAndMultiplexingStep();
    public static final JPanel DEFINE_CONDITIONS_STEP = new DefineConditionsStep();
    public static final JPanel CREATE_OR_LOAD_IDENTIFICATIONS_STEP = new CreateOrLoadIdentificationsStep();
    public static final JPanel LABEL_FREE_STEP = new LabelFreeStep();
    public static final JPanel ITRAQ_STEP = new ITRAQStep();
    public static final JPanel DONE_STEP = new DoneStep();
    private static final AnalyseStatusPanel analysisStatusPanel = new AnalyseStatusPanel();
    
    private AnalyseDynamicTab() {
        setLayout(layout);
        add(analysisStatusPanel, BorderLayout.PAGE_START);        
        add(RAW_DATA_AND_MULTIPLEXING_STEP, BorderLayout.CENTER);        
    }
    
    public static AnalyseDynamicTab getInstance() {
        if (instance == null) {
            instance = new AnalyseDynamicTab();
        }
        
        return instance;
    }
    
    public void moveToStep(JPanel panel) {
        removeAll();
        add(analysisStatusPanel);
        add(panel);        
        repaint();
    }
    
    public AnalyseStatusPanel getAnalyseStatusPanel() {
        return analysisStatusPanel;
    }
}