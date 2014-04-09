/*
 * --------------------------------------------------------------------------
 * About.java
 * --------------------------------------------------------------------------
 * Description:       About ProteoSuite Form
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             
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
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.SystemUtils;

/**
 * This form contains ProteoSuite license and distribution agreements.
 * 
 * @author fgonzalez
 */
public class About extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final SystemUtils SYS_UTILS = new SystemUtils();

	public About() {
		setTitle("About ProteoSuite");
		setResizable(false);
		setSize(400, 300);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = dim.width / 2;
		int y1 = dim.height / 2;
		int x2 = getSize().width / 2;
		int y2 = getSize().height / 2;
		setLocation(x1 - x2, y1 - y2);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage());
		setAlwaysOnTop(true);

		add(getContent());
		pack();
		setVisible(true);
	}

	private JPanel getContent() {
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.setBackground(Color.BLACK);
		content.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Logo
		JLabel logo = new JLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("images/logo.png")));
		logo.setAlignmentX(LEFT_ALIGNMENT);
		content.add(logo);
		content.add(Box.createVerticalStrut(10));

		// Subtitle
		JLabel subtitle = getLabel("Software for the Analysis of Quantitative Proteomics Data");
		subtitle.setFont(new Font("Tahoma", 1, 12));
		content.add(subtitle);
		content.add(Box.createVerticalStrut(5));

		// Copyright
		content.add(getLabel("Copyright " + "\u00A9" + " ProteoSuite 2011-2014"));
		content.add(Box.createVerticalStrut(10));
		content.add(new JSeparator());
		content.add(Box.createVerticalStrut(10));

		// Description
		content.add(getLabel("ProteoSuite is an open-source Java application released under the Apache License 2.0"));
		content.add(Box.createVerticalStrut(10));

		// URL / Version
		JPanel row = new JPanel();
		row.setAlignmentX(LEFT_ALIGNMENT);
		row.setBackground(content.getBackground());
		row.setLayout(new GridLayout(1, 2));

		JLabel url = getLabel("http://www.proteosuite.org");
		url.setForeground(new Color(51, 102, 255));
		url.setToolTipText("Go to ProteoSuite Website");
		url.setCursor(new Cursor(Cursor.HAND_CURSOR));
		url.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				OpenURL.open("http://www.proteosuite.org");
			}
		});
		row.add(url);
		row.add(getLabel("Version " + ProteoSuite.PROTEOSUITE_VERSION));
		content.add(row);
		content.add(Box.createVerticalStrut(10));

		// System Info / License
		row = new JPanel();
		row.setAlignmentX(LEFT_ALIGNMENT);
		row.setBackground(content.getBackground());
		row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
		row.add(getSystemInformation());
		row.add(getLicense());
		content.add(row);
				
		content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESCAPE");

		return content;
	}

	private JButton getSystemInformation() {
		JButton systemInfo = new JButton("System Info");
		systemInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jbSystemInfoActionPerformed();
			}
		});

		return systemInfo;
	}

	private JLabel getLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.ORANGE);
		label.setAlignmentX(0f);

		return label;
	}

	private JButton getLicense() {
		JButton license = new JButton("License");
		license.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				OpenURL.open("http://www.proteosuite.org/?q=licence");
			}
		});

		return license;
	}

	private void jbSystemInfoActionPerformed() {
		String sMessage = "Used Memory: \t" + SYS_UTILS.getUsedMemory()
				+ "MB\n";
		sMessage += "Free Memory: \t" + SYS_UTILS.getFreeMemory()
				+ "MB\n";
		sMessage += "Total Memory: \t" + SYS_UTILS.getTotalMemory()
				+ "MB\n";
		sMessage += "Max Memory: \t" + SYS_UTILS.getMaxMemory()
				+ "MB\n";
		sMessage += "Processors: \t" + SYS_UTILS.getAvailableProcessors()
				+ "\n";
		sMessage += "Java Version: \t" + SYS_UTILS.getRuntimeInfo()
				+ "\n";
		JOptionPane.showMessageDialog(this, sMessage, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
