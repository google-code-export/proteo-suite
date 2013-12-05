package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
public class MainPanel extends JPanel {
	private JSplitPane jspMainPanelView = new JSplitPane();
	private JSplitPane jspViewerAndProperties = new JSplitPane();

	public MainPanel(JTable jtQuantFiles, JTable jtIdentFiles,
			JTable jtRawFiles, TabbedChartViewer jtpViewer, TabbedLog jtpLog,
			TabbedProperties jtpProperties) {
		jspViewerAndProperties.setDividerLocation(380);
		jspViewerAndProperties.setDividerSize(5);
		jspViewerAndProperties.setLeftComponent(getLeftViewer(jtpViewer, jtpLog));
		jspViewerAndProperties.setRightComponent(getPropertiesView(jtpProperties));
		jspViewerAndProperties.setBorder(null);

		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(5);
		jspMainPanelView.setLeftComponent(new LeftPanelView(jtQuantFiles, jtIdentFiles,
				jtRawFiles));
		jspMainPanelView.setRightComponent(jspViewerAndProperties);
		jspMainPanelView.setBorder(null);

		// Viewer Width (Viewer)
		jspViewerAndProperties.setDividerLocation(600);
		// Left Menu (Files)
		jspMainPanelView.setDividerLocation(250);

		setLayout(new BorderLayout());
		add(jspMainPanelView);
	}

	public JSplitPane getMainPanelView() {
		return jspMainPanelView;
	}

	public JSplitPane getViewerAndProperties() {
		return jspViewerAndProperties;
	}

	public JPanel getLeftViewer(TabbedChartViewer jtpViewer, TabbedLog jtpLog) {
		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));
		jlViewer.setBorder(new EmptyBorder(5, 10, 5, 0));

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jlViewer, BorderLayout.PAGE_START);
		jPanel.add(getLeftViewerDetails(jtpViewer, jtpLog), BorderLayout.CENTER);

		return jPanel;
	}

	private JSplitPane getLeftViewerDetails(TabbedChartViewer jtpViewer,
			TabbedLog jtpLog) {

		final JSplitPane jspLeftViewerDetails = new JSplitPane();
		jspLeftViewerDetails.setDividerLocation(350);
		jspLeftViewerDetails.setDividerSize(2);
		jspLeftViewerDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspLeftViewerDetails.setTopComponent(jtpViewer);
		jspLeftViewerDetails.setRightComponent(jtpLog);

		// Viewer Height (Viewer)
		jspLeftViewerDetails.setDividerLocation(480);

		return jspLeftViewerDetails;
	}

	public JPanel getPropertiesView(TabbedProperties jtpProperties) {
		JLabel jlProperties = new JLabel("Data and Metadata");
		jlProperties.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlProperties.setForeground(new Color(102, 102, 102));
		jlProperties.setBorder(new EmptyBorder(5, 10, 5, 0));

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jlProperties, BorderLayout.PAGE_START);
		jPanel.add(jtpProperties, BorderLayout.CENTER);

		return jPanel;
	}
}