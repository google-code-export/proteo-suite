/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.inspect;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author SPerkins
 */
public class InspectChartPanel extends JPanel {
    private final JTabbedPane tabbedPane;
    private ChromatogramPanel chromPanel;
    private SpectrumPanel specPanel;
    
    public InspectChartPanel() {
        super();
        tabbedPane = new JTabbedPane();
    }
    
    public void setChromatogram(ChromatogramPanel chromPanel) {
        this.chromPanel = chromPanel;
        refreshTabView();
    }
    
    public void setSpectrum(SpectrumPanel specPanel) {
        this.specPanel = specPanel;
        refreshTabView();
    }
    
    public void refreshTabView() {
        removeAll();
        tabbedPane.removeAll();
        if (chromPanel != null) {
            tabbedPane.addTab("Chromatogram", null, chromPanel);
        }
        
        if (specPanel != null) {
            tabbedPane.addTab("Spectrum", null, specPanel);
        }
        
        add(tabbedPane);
    }
}
