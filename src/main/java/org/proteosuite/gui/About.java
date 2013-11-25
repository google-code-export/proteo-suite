/*
 * --------------------------------------------------------------------------
 * About.java
 * --------------------------------------------------------------------------
 * Description:       About ProteoSuite Form
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.SystemUtils;

/**
 * This form contains ProteoSuite license and distribution agreements.
 * 
 * @author fgonzalez
 * @param void
 * @return void
 */
public class About extends JPanel {

	private SystemUtils sysutils = new SystemUtils();

	public About() {
		initComponents();
		this.jlLogo.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("images/logo.png")));
	}

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel2 = new JLabel();
		jlLogo = new JLabel();
		jLabel4 = new JLabel();
		jLabel5 = new JLabel();
		jlURL = new JLabel();
		jSeparator1 = new JSeparator();
		jlCopyright = new JLabel();
		jlVersion = new JLabel();
		jbSystemInfo = new JButton();
		jbLicense = new JButton();

		setMaximumSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(400, 300));

		jLabel2.setFont(new Font("Tahoma", 1, 12));
		jLabel2.setText("Software for the Analysis of Quantitative Proteomics Data");

		jLabel4.setText("ProteoSuite is an open-source java application released under the Apache");

		jLabel5.setText("License 2.0. For more information please visit our website:");

		jlURL.setForeground(new Color(51, 102, 255));
		jlURL.setText("http://www.proteosuite.org");
		jlURL.setToolTipText("Go to ProteoSuite Website");
		jlURL.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlURL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jlURLMouseClicked(evt);
			}
		});

		jlCopyright.setText("Copyright © ProteoSuite 2011-2013 ");

		jlVersion.setText("Version 0.3.0 April 2013");

		jbSystemInfo.setText("System Info");
		jbSystemInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jbSystemInfoActionPerformed(evt);
			}
		});

		jbLicense.setText("License");
		jbLicense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jbLicenseActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
																.addComponent(
																		jLabel5,
																		GroupLayout.DEFAULT_SIZE,
																		367,
																		Short.MAX_VALUE)
																.addComponent(
																		jLabel4,
																		GroupLayout.DEFAULT_SIZE,
																		367,
																		Short.MAX_VALUE)
																.addComponent(
																		jSeparator1)
																.addComponent(
																		jlLogo,
																		GroupLayout.DEFAULT_SIZE,
																		357,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						jlURL,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addGap(131,
																						131,
																						131)
																				.addComponent(
																						jlVersion)
																				.addGap(18,
																						18,
																						18))
																.addComponent(
																		jLabel2,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addComponent(jlCopyright)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jbSystemInfo)
																.addGap(18, 18,
																		18)
																.addComponent(
																		jbLicense,
																		GroupLayout.PREFERRED_SIZE,
																		88,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jlLogo,
										GroupLayout.PREFERRED_SIZE,
										149,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										19, Short.MAX_VALUE)
								.addComponent(jLabel2)
								.addGap(5, 5, 5)
								.addComponent(jlCopyright)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator1,
										GroupLayout.PREFERRED_SIZE,
										10,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jLabel4)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel5)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(
														jlURL,
														GroupLayout.PREFERRED_SIZE,
														26,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(jlVersion))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(jbSystemInfo)
												.addComponent(jbLicense))
								.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void jlURLMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jlURLMouseClicked
		OpenURL url = new OpenURL("http://www.proteosuite.org");
	}// GEN-LAST:event_jlURLMouseClicked

	private void jbSystemInfoActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jbSystemInfoActionPerformed
		String sMessage = "";
		sMessage = "Used Memory: \t" + sysutils.getUsedMemory()
				+ "MB\n";
		sMessage = sMessage + "Free Memory: \t"
				+ sysutils.getFreeMemory() + "MB\n";
		sMessage = sMessage + "Total Memory: \t"
				+ sysutils.getTotalMemory() + "MB\n";
		sMessage = sMessage + "Max Memory: \t"
				+ sysutils.getMaxMemory() + "MB\n";
		JOptionPane.showMessageDialog(this, sMessage, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}// GEN-LAST:event_jbSystemInfoActionPerformed

	private void jbLicenseActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jbLicenseActionPerformed
		OpenURL url = new OpenURL("http://www.proteosuite.org/?q=licence");
	}// GEN-LAST:event_jbLicenseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JSeparator jSeparator1;
	private JButton jbLicense;
	private JButton jbSystemInfo;
	private JLabel jlCopyright;
	private JLabel jlLogo;
	private JLabel jlURL;
	private JLabel jlVersion;
	// End of variables declaration//GEN-END:variables
}
