package org.proteosuite.gui;

import java.awt.Color;

import javax.swing.GroupLayout;
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
		GroupLayout jpLeftMenuBottomLayout = new GroupLayout(jpLeftMenuBottom);
		jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
		jpLeftMenuBottomLayout.setHorizontalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom, GroupLayout.DEFAULT_SIZE, 277,
						Short.MAX_VALUE));
		jpLeftMenuBottomLayout.setVerticalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom, GroupLayout.DEFAULT_SIZE, 483,
						Short.MAX_VALUE));

		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setBackground(new Color(255, 255, 255));
		jspProjectDetails.setDividerLocation(130);
		jspProjectDetails.setDividerSize(1);
		jspProjectDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspProjectDetails.setTopComponent(getLeftMenuTopPanel(jtRawFiles));
		jspProjectDetails.setRightComponent(jpLeftMenuBottom);

		setBackground(new Color(255, 255, 255));

		GroupLayout jpProjectDetailsLayout = new GroupLayout(this);
		setLayout(jpProjectDetailsLayout);
		jpProjectDetailsLayout.setHorizontalGroup(jpProjectDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProjectDetails));
		jpProjectDetailsLayout
				.setVerticalGroup(jpProjectDetailsLayout.createParallelGroup(
						GroupLayout.Alignment.LEADING).addComponent(
						jspProjectDetails, GroupLayout.Alignment.TRAILING));
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
		GroupLayout jpQuantFilesLayout = new GroupLayout(jpQuantFiles);
		jpQuantFiles.setLayout(jpQuantFilesLayout);
		jpQuantFilesLayout.setHorizontalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles, GroupLayout.DEFAULT_SIZE, 275,
						Short.MAX_VALUE));
		jpQuantFilesLayout.setVerticalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles, GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));

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
		GroupLayout jpLeftMenuTopLayout = new GroupLayout(jpLeftMenuTop);
		jpLeftMenuTop.setLayout(jpLeftMenuTopLayout);
		jpLeftMenuTopLayout.setHorizontalGroup(jpLeftMenuTopLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGap(0, 277, Short.MAX_VALUE)
				.addGroup(
						jpLeftMenuTopLayout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addComponent(
								jtpRawFiles, GroupLayout.DEFAULT_SIZE, 277,
								Short.MAX_VALUE)));
		jpLeftMenuTopLayout
				.setVerticalGroup(jpLeftMenuTopLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 129, Short.MAX_VALUE)
						.addGroup(
								jpLeftMenuTopLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(jtpRawFiles,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 129,
												Short.MAX_VALUE)));

		return jpLeftMenuTop;
	}

}
