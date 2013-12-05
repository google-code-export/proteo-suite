package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.chart.ChartSpectrum;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedChartViewer extends JTabbedPane {
	private final JPanel jdpMS = new JPanel();
	private final JPanel jdpTIC = new JPanel();
	private final JPanel jp2D = new JPanel();
	
	public TabbedChartViewer()
	{
		jdpMS.setBackground(new Color(255, 255, 255));
		jdpMS.setLayout(new BorderLayout());
		addTab("Spectrum", new ImageIcon(getClass().getClassLoader()
				.getResource("images/ident_file.gif")), jdpMS);

		jdpTIC.setBackground(new Color(255, 255, 255));
		jdpTIC.setForeground(new Color(255, 255, 255));
		jdpTIC.setLayout(new BorderLayout());
		addTab("TIC", new ImageIcon(getClass().getClassLoader().getResource(
				"images/chromat.gif")), jdpTIC);
		
		jp2D.setBackground(new Color(255, 255, 255));
		jp2D.setLayout(new BorderLayout());
		addTab("2D", new ImageIcon(getClass().getClassLoader().getResource(
				"images/twod.gif")), jp2D);
		
		reset();
	}
	
	public void reset()
	{
		jdpMS.removeAll();
		jdpTIC.removeAll();
		jp2D.removeAll();
	}

	public void updateChromatogram(MzMLUnmarshaller mzMLUnmarshaller) {
		jdpTIC.removeAll();
		
		JPanel chromatogramPanel = ChartChromatogram.getChromatogram(mzMLUnmarshaller);
		if (chromatogramPanel != null)
			jdpTIC.add(chromatogramPanel, BorderLayout.CENTER);
		
		jdpTIC.revalidate();
		jdpTIC.repaint();
	}

	public void updateSpectrum(MzMLUnmarshaller unmarshaller, String sID) {
		jdpMS.removeAll();
		
		JPanel spectrumPanel = ChartSpectrum.getSpectrum(unmarshaller, sID);

		jdpMS.add(spectrumPanel, BorderLayout.CENTER);
		jdpMS.revalidate();
		jdpMS.repaint();
	}

	public void update2DPlot(MzMLUnmarshaller unmarshaller) {
		jp2D.removeAll();
        
		JPanel twoDPanel = ChartPlot2D.get2DPlot(unmarshaller);
		if (twoDPanel != null)
			jp2D.add(twoDPanel, BorderLayout.CENTER);
		
		jp2D.revalidate();
		jp2D.repaint();
	}
}
