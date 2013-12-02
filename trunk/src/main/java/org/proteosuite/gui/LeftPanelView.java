package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Andrew Collins
 */
public class LeftPanelView extends JPanel {

	public LeftPanelView(JTable jtQuantFiles, JTable jtIdentFiles, JTable jtRawFiles) {		
		JSplitPane jspLeftPanelView = getLeftPanelView();
		jspLeftPanelView.setRightComponent(new ProjectDetails(jtQuantFiles,
				jtIdentFiles, jtRawFiles));

		setBackground(new Color(204, 204, 255));
		setLayout(new BorderLayout());
		add(jspLeftPanelView);
	}

	private JSplitPane getLeftPanelView() {
		JSplitPane jspLeftPanelView = new JSplitPane();

		jspLeftPanelView.setDividerLocation(20);
		jspLeftPanelView.setDividerSize(1);
		jspLeftPanelView.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpProjectHeader = new JPanel();
		jpProjectHeader.setMinimumSize(new Dimension(279, 25));
		jpProjectHeader.setPreferredSize(new Dimension(280, 25));

		JLabel jlFiles = new JLabel("Files");
		jlFiles.requestFocusInWindow();
		jlFiles.setBackground(new Color(255, 255, 255));
		jlFiles.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlFiles.setForeground(new Color(102, 102, 102));
		
		jpProjectHeader.setLayout(new BorderLayout());
		jpProjectHeader.add(jlFiles);
		jlFiles.setBorder(new EmptyBorder(0, 10, 0, 0));

		jspLeftPanelView.setTopComponent(jpProjectHeader);

		return jspLeftPanelView;
	}
}
