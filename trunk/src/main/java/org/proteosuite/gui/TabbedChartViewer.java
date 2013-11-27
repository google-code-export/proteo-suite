package org.proteosuite.gui;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedChartViewer extends JTabbedPane {

	public TabbedChartViewer(final JDesktopPane jdpMS,
			final JDesktopPane jdpTIC, JPanel jp2D)
	{
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
	}
}
