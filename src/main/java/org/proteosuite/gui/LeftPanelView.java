package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Andrew Collins
 */
public class LeftPanelView extends JPanel {

	public LeftPanelView(JTable jtQuantFiles, JTable jtIdentFiles, JTable jtRawFiles) {		
		JLabel jlFiles = new JLabel("Files");
		jlFiles.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlFiles.setForeground(new Color(102, 102, 102));
		jlFiles.setBorder(new EmptyBorder(5, 10, 5, 0));
		
		setLayout(new BorderLayout());
		add(jlFiles, BorderLayout.PAGE_START);
		add(getProjectDetails(jtQuantFiles,
				jtIdentFiles, jtRawFiles), BorderLayout.CENTER);
	}

	public JSplitPane getProjectDetails(JTable jtQuantFiles, JTable jtIdentFiles,
			JTable jtRawFiles) {
		// Ident and Quantitation separator
		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setDividerLocation(130);
		jspProjectDetails.setDividerSize(1);
		jspProjectDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspProjectDetails.setTopComponent(getLeftMenuTopPanel(jtRawFiles));
		jspProjectDetails.setRightComponent(getLeftMenuBottom(jtQuantFiles,
				jtIdentFiles));
		
		return jspProjectDetails;
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
		jtpIdentFiles.addTab("Identification Files", jspIdentFiles);
		jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);

		return jtpIdentFiles;
	}

	private JTabbedPane getQuantFilesPanel(JTable jtQuantFiles) {
		JScrollPane jspQuantFiles = new JScrollPane();
		jspQuantFiles.setViewportView(jtQuantFiles);

		JLabel jlQuantFilesIcon = new JLabel(
				"Quantitation Files                      ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/quant_file.gif")), SwingConstants.RIGHT);
		jlQuantFilesIcon.setIconTextGap(5);
		JTabbedPane jtpQuantFiles = new JTabbedPane();
		jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);
		jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);

		return jtpQuantFiles;
	}

	private JTabbedPane getLeftMenuTopPanel(JTable jtRawFiles) {
		JScrollPane jspRawFiles = new JScrollPane();
		jspRawFiles.setViewportView(jtRawFiles);

		JLabel jlRawFilesIcon = new JLabel(
				"Raw Files                                 ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/raw_file.gif")), SwingConstants.RIGHT);
		jlRawFilesIcon.setIconTextGap(5);

		final JTabbedPane jtpRawFiles = new JTabbedPane();
		jtpRawFiles.addTab("Raw Files", jspRawFiles);
		jtpRawFiles.setTabComponentAt(0, jlRawFilesIcon);

		return jtpRawFiles;
	}
}
