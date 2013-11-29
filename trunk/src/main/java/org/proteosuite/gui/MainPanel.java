package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Component;
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
public class MainPanel extends JPanel {
	private JSplitPane jspMainPanelView = new JSplitPane();
	private JSplitPane jspViewerAndProperties = new JSplitPane();

	public MainPanel(JPanel jpLeftPanelView, JPanel jpLeftViewer,
			JPanel jpPropertiesBox) {
		jspViewerAndProperties.setDividerLocation(380);
		jspViewerAndProperties.setDividerSize(3);
		jspViewerAndProperties.setLeftComponent(jpLeftViewer);
		jspViewerAndProperties.setRightComponent(jpPropertiesBox);

		JPanel jpViewerAndProperties = new JPanel();
		jpViewerAndProperties.setBackground(new Color(204, 204, 255));
		GroupLayout jpViewerAndPropertiesLayout = new GroupLayout(
				jpViewerAndProperties);
		jpViewerAndProperties.setLayout(jpViewerAndPropertiesLayout);
		jpViewerAndPropertiesLayout
				.setHorizontalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties,
								GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE));
		jpViewerAndPropertiesLayout
				.setVerticalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties));

		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(3);
		jspMainPanelView.setLeftComponent(jpLeftPanelView);
		jspMainPanelView.setRightComponent(jpViewerAndProperties);

		JPanel jpMainPanelView = this;

		GroupLayout jpMainPanelViewLayout = new GroupLayout(jpMainPanelView);
		jpMainPanelView.setLayout(jpMainPanelViewLayout);

		jpMainPanelViewLayout.setHorizontalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView, GroupLayout.DEFAULT_SIZE, 1242,
						Short.MAX_VALUE));
		jpMainPanelViewLayout.setVerticalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView));

		// Viewer Width (Viewer)
		jspViewerAndProperties.setDividerLocation(600);
		// Left Menu (Files)
		jspMainPanelView.setDividerLocation(250);
	}

	private JPanel getLeftViewer(JSplitPane jspLeftViewer) {
		JPanel jpLeftViewer = new JPanel();
		GroupLayout jpLeftViewerLayout = new GroupLayout(jpLeftViewer);
		jpLeftViewer.setLayout(jpLeftViewerLayout);
		jpLeftViewerLayout.setHorizontalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer));
		jpLeftViewerLayout.setVerticalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer, GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));

		return null;
	}

	public JSplitPane getMainPanelView() {
		return jspMainPanelView;
	}

	public JSplitPane getViewerAndProperties() {
		return jspViewerAndProperties;
	}
}