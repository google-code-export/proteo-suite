package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class ProjectStatus extends JPanel {

	public ProjectStatus(JComboBox<String> jcbTechnique, JComboBox<String> jcbOutputFormat, JLabel jlRawFilesStatus, JLabel jlIdentFilesStatus)
	{
		JLabel jlQuantFilesStatus = new JLabel(new ImageIcon(getClass()
				.getClassLoader().getResource("images/empty.gif")));
		jlQuantFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlQuantFilesStatus.setForeground(new Color(51, 102, 0));
		
		
		JLabel jlRawFiles = new JLabel("1) Raw Files:");
		JLabel jlIdentFiles = new JLabel("2) Identification Files:");
		JLabel jlTechnique = new JLabel("3) Technique:");
		JLabel jlQuantFiles = new JLabel("4) Quant Files:");
		jlQuantFiles.setBackground(new Color(255, 255, 255));
		
		JPanel jpProjectStatus = this;
		jpProjectStatus.setBorder(BorderFactory.createEtchedBorder());
		jpProjectStatus.setForeground(new Color(153, 153, 153));
		jpProjectStatus.setPreferredSize(new Dimension(102, 30));

		GroupLayout jpProjectStatusLayout = new GroupLayout(jpProjectStatus);
		jpProjectStatus.setLayout(jpProjectStatusLayout);
		

		jpProjectStatusLayout
				.setHorizontalGroup(jpProjectStatusLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProjectStatusLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlRawFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlRawFilesStatus,
												GroupLayout.PREFERRED_SIZE, 37,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlIdentFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlIdentFilesStatus,
												GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlTechnique)
										.addGap(18, 18, 18)
										.addComponent(jcbTechnique,
												GroupLayout.PREFERRED_SIZE,
												119, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlQuantFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jcbOutputFormat,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlQuantFilesStatus,
												GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(499, Short.MAX_VALUE)));
		jpProjectStatusLayout
				.setVerticalGroup(jpProjectStatusLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProjectStatusLayout
										.createSequentialGroup()
										.addGroup(
												jpProjectStatusLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpProjectStatusLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlRawFiles)
																		.addComponent(
																				jlRawFilesStatus,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlIdentFiles)
																		.addComponent(
																				jlIdentFilesStatus,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlTechnique)
																		.addComponent(
																				jcbTechnique,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlQuantFiles)
																		.addComponent(
																				jcbOutputFormat,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jlQuantFilesStatus,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
	}
}
