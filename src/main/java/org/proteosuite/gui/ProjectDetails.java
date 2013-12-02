package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * 
 * @author Andrew Collins
 */
public class ProjectDetails extends JPanel {

	public ProjectDetails(JTable jtQuantFiles, JTable jtIdentFiles,
			JTable jtRawFiles) {
		// Ident and Quantitation separator
		JSplitPane jspLeftMenuBottom = getLeftMenuBottom(jtQuantFiles,
				jtIdentFiles);

		JPanel jpLeftMenuBottom = new JPanel();
		jpLeftMenuBottom.setLayout(new BorderLayout());
		jpLeftMenuBottom.add(jspLeftMenuBottom);

		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setBackground(new Color(255, 255, 255));
		jspProjectDetails.setDividerLocation(130);
		jspProjectDetails.setDividerSize(1);
		jspProjectDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspProjectDetails.setTopComponent(getLeftMenuTopPanel(jtRawFiles));
		jspProjectDetails.setRightComponent(jpLeftMenuBottom);

		setBackground(new Color(255, 255, 255));

		setLayout(new BorderLayout());
		add(jspProjectDetails);
	}

	private JSplitPane getLeftMenuBottom(JTable jtQuantFiles,
			JTable jtIdentFiles) {
		JSplitPane jspLeftMenuBottom = new JSplitPane();
		jspLeftMenuBottom.setDividerLocation(200);
		jspLeftMenuBottom.setDividerSize(1);
		jspLeftMenuBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspLeftMenuBottom.setRightComponent(getQuantFilesPanel(jtQuantFiles));
		jspLeftMenuBottom.setTopComponent(getIdentFilesPanel(jtIdentFiles));

		return jspLeftMenuBottom;
	}

	private JTabbedPane getIdentFilesPanel(JTable jtIdentFiles) {
		JScrollPane jspIdentFiles = new JScrollPane();
		jspIdentFiles.setViewportView(jtIdentFiles);

		JLabel jlIdentFilesIcon = new JLabel(
				"Identification Files                    ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/ident_file.gif")), SwingConstants.RIGHT);
		jlIdentFilesIcon.setIconTextGap(5);

		JTabbedPane jtpIdentFiles = new JTabbedPane();
		jtpIdentFiles.setBackground(new Color(255, 255, 255));
		jtpIdentFiles.addTab("Identification Files", jspIdentFiles);
		jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);

		return jtpIdentFiles;
	}

	private JPanel getQuantFilesPanel(JTable jtQuantFiles) {
		JScrollPane jspQuantFiles = new JScrollPane();
		jspQuantFiles.setViewportView(jtQuantFiles);

		JLabel jlQuantFilesIcon = new JLabel(
				"Quantitation Files                      ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/quant_file.gif")), SwingConstants.RIGHT);
		jlQuantFilesIcon.setIconTextGap(5);
		JTabbedPane jtpQuantFiles = new JTabbedPane();
		jtpQuantFiles.setBackground(new Color(255, 255, 255));
		jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);
		jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);

		JPanel jpQuantFiles = new JPanel();
		jpQuantFiles.setLayout(new BorderLayout());
		jpQuantFiles.add(jtpQuantFiles);

		return jpQuantFiles;
	}

	private JPanel getLeftMenuTopPanel(JTable jtRawFiles) {
		JScrollPane jspRawFiles = new JScrollPane();
		jspRawFiles.setBackground(new Color(255, 255, 255));
		jspRawFiles.setViewportView(jtRawFiles);

		JLabel jlRawFilesIcon = new JLabel(
				"Raw Files                                 ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/raw_file.gif")), SwingConstants.RIGHT);
		jlRawFilesIcon.setIconTextGap(5);

		final JTabbedPane jtpRawFiles = new JTabbedPane();
		jtpRawFiles.setBackground(new Color(255, 255, 255));
		jtpRawFiles.addTab("Raw Files", jspRawFiles);
		jtpRawFiles.setTabComponentAt(0, jlRawFilesIcon);

		JPanel jpLeftMenuTop = new JPanel();
		jpLeftMenuTop.setLayout(new BorderLayout());
		jpLeftMenuTop.add(jtpRawFiles);
		

		return jpLeftMenuTop;
	}

}
