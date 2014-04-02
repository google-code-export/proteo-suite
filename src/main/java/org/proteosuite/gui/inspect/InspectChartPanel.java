package org.proteosuite.gui.inspect;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author SPerkins
 */
public class InspectChartPanel extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	private ChromatogramPanel chromPanel;
    private SpectrumPanel specPanel;
    private JPanel chartPanel;
    
    public void setChromatogram(ChromatogramPanel chromPanel) {
        this.chromPanel = chromPanel;
        refreshTabView(0);
    }
    
    public void setSpectrum(SpectrumPanel specPanel) {
        this.specPanel = specPanel;
        refreshTabView(1);
    }
    
    public void set2D(JPanel chartPanel) {
        this.chartPanel = chartPanel;
        refreshTabView(2);
    }
    
    public void refreshTabView(int index) {
        removeAll();
        if (chromPanel != null)
            addTab("Chromatogram", chromPanel);
        else
        	index--;
        
        if (specPanel != null)
            addTab("Spectrum", specPanel);
        else
        	index--;
        
        if (chartPanel != null)
            addTab("2D", chartPanel);
        
        if (index > 0 && index < this.getTabCount())
            setSelectedIndex(index);
        
        repaint();
    }
}
