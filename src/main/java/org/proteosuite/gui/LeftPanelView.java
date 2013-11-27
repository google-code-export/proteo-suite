package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LeftPanelView extends JSplitPane {

	public LeftPanelView()
	{
		setDividerLocation(20);
		setDividerSize(1);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		
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

		setTopComponent(jpProjectHeader);
	}
}
