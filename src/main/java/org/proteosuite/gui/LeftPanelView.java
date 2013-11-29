package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/**
 * 
 * @author Andrew Collins
 */
public class LeftPanelView extends JPanel {

	public LeftPanelView(JTable jtQuantFiles, JTable jtIdentFiles, JTable jtRawFiles) {		
		JSplitPane jspLeftPanelView = getLeftPanelView();
		jspLeftPanelView.setRightComponent(new ProjectDetails(jtQuantFiles,
				jtIdentFiles, jtRawFiles));

		JPanel jpLeftPanelView = this;
		jpLeftPanelView.setBackground(new Color(204, 204, 255));
		GroupLayout jpLeftPanelViewLayout = new GroupLayout(jpLeftPanelView);
		jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
		jpLeftPanelViewLayout.setHorizontalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView, GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));
		jpLeftPanelViewLayout.setVerticalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView, GroupLayout.DEFAULT_SIZE, 637,
						Short.MAX_VALUE));
	}

	private JSplitPane getLeftPanelView() {
		JSplitPane jspLeftPanelView = new JSplitPane();

		jspLeftPanelView.setDividerLocation(20);
		jspLeftPanelView.setDividerSize(1);
		jspLeftPanelView.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpProjectHeader = new JPanel();
		jpProjectHeader.setMaximumSize(new Dimension(32767, 25));
		jpProjectHeader.setMinimumSize(new Dimension(279, 25));
		jpProjectHeader.setPreferredSize(new Dimension(280, 25));

		JLabel jlFiles = new JLabel("Files");
		jlFiles.requestFocusInWindow();
		jlFiles.setBackground(new Color(255, 255, 255));
		jlFiles.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlFiles.setForeground(new Color(102, 102, 102));

		GroupLayout jpProjectHeaderLayout = new GroupLayout(jpProjectHeader);
		jpProjectHeader.setLayout(jpProjectHeaderLayout);
		jpProjectHeaderLayout.setHorizontalGroup(jpProjectHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpProjectHeaderLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlFiles)
								.addContainerGap(235, Short.MAX_VALUE)));
		jpProjectHeaderLayout.setVerticalGroup(jpProjectHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpProjectHeaderLayout.createSequentialGroup()
								.addComponent(jlFiles)
								.addContainerGap(7, Short.MAX_VALUE)));

		jspLeftPanelView.setTopComponent(jpProjectHeader);

		return jspLeftPanelView;
	}
}
