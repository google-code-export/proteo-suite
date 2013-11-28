package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.chart.ChartSpectrum;
import org.proteosuite.utils.TwoDPlot;

import com.compomics.util.gui.spectrum.ChromatogramPanel;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedChartViewer extends JTabbedPane {
	private final JDesktopPane jdpMS = new JDesktopPane();
	private final JDesktopPane jdpTIC = new JDesktopPane();
	private final JPanel jp2D = new JPanel();
	
	public TabbedChartViewer()
	{
		jdpMS.setBackground(new Color(255, 255, 255));
		
		JPanel jpSpectrum = new JPanel();
		GroupLayout jpSpectrumLayout = new GroupLayout(jpSpectrum);
		jpSpectrum.setLayout(jpSpectrumLayout);
		jpSpectrumLayout
				.setHorizontalGroup(jpSpectrumLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 373, Short.MAX_VALUE)
						.addGroup(
								jpSpectrumLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(jdpMS,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 373,
												Short.MAX_VALUE)));
		jpSpectrumLayout
				.setVerticalGroup(jpSpectrumLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 321, Short.MAX_VALUE)
						.addGroup(
								jpSpectrumLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(jdpMS,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 321,
												Short.MAX_VALUE)));

		addTab("Spectrum View", jpSpectrum);

		JPanel jpTIC = new JPanel();
		jpTIC.setBackground(new Color(255, 255, 255));

		jdpTIC.setBackground(new Color(255, 255, 255));
		jdpTIC.setForeground(new Color(255, 255, 255));

		GroupLayout jpTICLayout = new GroupLayout(jpTIC);
		jpTIC.setLayout(jpTICLayout);
		jpTICLayout.setHorizontalGroup(jpTICLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jdpTIC,
				GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		jpTICLayout.setVerticalGroup(jpTICLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jdpTIC,
				GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE));

		addTab("TIC View", jpTIC);
		
		jp2D.setBackground(new Color(255, 255, 255));

		GroupLayout jp2DLayout = new GroupLayout(jp2D);
		jp2D.setLayout(jp2DLayout);
		jp2DLayout.setHorizontalGroup(jp2DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jp2DLayout.setVerticalGroup(jp2DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 321, Short.MAX_VALUE));

		addTab("2D View", jp2D);


		JPanel jp3D = new JPanel();
		jp3D.setBackground(new Color(255, 255, 255));

		GroupLayout jp3DLayout = new GroupLayout(jp3D);
		jp3D.setLayout(jp3DLayout);
		jp3DLayout.setHorizontalGroup(jp3DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jp3DLayout.setVerticalGroup(jp3DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 321, Short.MAX_VALUE));

		addTab("3D View", jp3D);
		
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

		Icon threeDIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/threed.gif"));
		JLabel jlThreeDIcon = new JLabel("3D View", threeDIcon,
				SwingConstants.RIGHT);
		jlThreeDIcon.setIconTextGap(5);
		setTabComponentAt(3, jlThreeDIcon);
		
		setSelectedIndex(0);
		
		reset();
	}
	
	public void reset()
	{
		jdpMS.removeAll();
		jdpTIC.removeAll();
	}

	/**
	 * Get internal frame dimensions
	 * 
	 * @param void
	 * @return JInternalFrame as a container
	 */
	private JInternalFrame getInternalFrame() {
		JInternalFrame internalFrame = new JInternalFrame("");
		Icon icon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif"));

		internalFrame.setLocation(0, 0);
		internalFrame.setSize(new Dimension(620, 420));
		internalFrame.setPreferredSize(new Dimension(620, 420));
		internalFrame.setFrameIcon(icon);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setClosable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setVisible(true);
		return internalFrame;
	}

	public void updateChromatogram(String title, MzMLUnmarshaller mzMLUnmarshaller) {
		jdpTIC.removeAll();
		
		ChromatogramPanel chromatogramPanel = ChartChromatogram.getChromatogram(mzMLUnmarshaller);
		if (chromatogramPanel != null)
		{
			JInternalFrame jifViewChromatogram = getInternalFrame();
			
			jifViewChromatogram.setTitle("Chromatogram <" + title + ">");
			jifViewChromatogram.add(chromatogramPanel);
			jdpTIC.add(jifViewChromatogram);
		}
		
		jdpTIC.revalidate();
		jdpTIC.repaint();
	}

	public void updateSpectrum(String sID, MzMLUnmarshaller unmarshaller) {
		JPanel spectrumPanel = ChartSpectrum.getSpectrum(unmarshaller, sID);
		
		jdpMS.removeAll();

		JInternalFrame jifViewSpectrum = getInternalFrame();
		
		jifViewSpectrum.setTitle("MS spectrum <ScanID: " + sID + ">");
		jifViewSpectrum.add(spectrumPanel, BorderLayout.CENTER);
		jifViewSpectrum.setSize(jdpMS.getSize());
		
		jdpMS.add(jifViewSpectrum, BorderLayout.CENTER);
		jdpMS.revalidate();
		jdpMS.repaint();
	}

	public void update2DPlot(String title, MzMLUnmarshaller unmarshaller) {
		
		TwoDPlot jifView2D = ChartPlot2D.get2DPlot(unmarshaller, title);
		if (jifView2D != null) {
			jp2D.add(jifView2D);
			jifView2D.pack();
			jifView2D.setVisible(true);
		}
		
	}
}
