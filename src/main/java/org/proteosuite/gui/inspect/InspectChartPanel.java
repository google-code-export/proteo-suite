/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.inspect;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;

import javax.swing.JTabbedPane;

/**
 *
 * @author SPerkins
 */
public class InspectChartPanel extends JTabbedPane {
    private ChromatogramPanel chromPanel;
    private SpectrumPanel specPanel;
    
    public void setChromatogram(ChromatogramPanel chromPanel) {
        this.chromPanel = chromPanel;
        refreshTabView(0);
    }
    
    public void setSpectrum(SpectrumPanel specPanel) {
        this.specPanel = specPanel;
        refreshTabView(1);
    }
    
    public void refreshTabView(int index) {
        removeAll();
        if (chromPanel != null) {
            addTab("Chromatogram", chromPanel);
        }
        else
        	index--;
        
        if (specPanel != null)
            addTab("Spectrum", specPanel);
        
        setSelectedIndex(index);
        
        repaint();
    }
}
