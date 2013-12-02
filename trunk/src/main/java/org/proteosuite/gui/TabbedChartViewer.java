package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.chart.ChartSpectrum;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedChartViewer extends JTabbedPane {
	private final JDesktopPane jdpMS = new JDesktopPane();
	private final JDesktopPane jdpTIC = new JDesktopPane();
	private final JDesktopPane jp2D = new JDesktopPane();
	
	public TabbedChartViewer()
	{
		jdpMS.setBackground(new Color(255, 255, 255));
		jdpMS.setLayout(new BorderLayout());
		addTab("Spectrum View", jdpMS);

		jdpTIC.setBackground(new Color(255, 255, 255));
		jdpTIC.setForeground(new Color(255, 255, 255));
		jdpTIC.setLayout(new BorderLayout());
		addTab("TIC View", jdpTIC);
		
		jp2D.setBackground(new Color(255, 255, 255));
		jp2D.setLayout(new BorderLayout());
		addTab("2D View", jp2D);
		
		Icon spectrumIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/ident_file.gif"));
		JLabel jlSpectrumIcon = new JLabel("Spectrum", spectrumIcon,
				SwingConstants.RIGHT);
		jlSpectrumIcon.setIconTextGap(5);
		setTabComponentAt(0, jlSpectrumIcon);

		Icon TICIcon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/chromat.gif"));
		JLabel jlTICIcon = new JLabel("TIC", TICIcon, SwingConstants.RIGHT);
		jlTICIcon.setIconTextGap(5);
		setTabComponentAt(1, jlTICIcon);

		Icon twoDIcon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/twod.gif"));
		JLabel jlTwoDIcon = new JLabel("2D View", twoDIcon,
				SwingConstants.RIGHT);
		jlTwoDIcon.setIconTextGap(5);
		setTabComponentAt(2, jlTwoDIcon);
		
		setSelectedIndex(0);
		
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
