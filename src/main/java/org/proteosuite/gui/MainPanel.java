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
public class MainPanel extends JSplitPane {
	private final JSplitPane jspViewerAndProperties = new JSplitPane();

	public MainPanel(JTable jtQuantFiles, JTable jtIdentFiles,
			JTable jtRawFiles, TabbedChartViewer jtpViewer, TabbedLog jtpLog,
			TabbedProperties jtpProperties) {
		jspViewerAndProperties.setDividerSize(5);
		jspViewerAndProperties.setLeftComponent(getCentralPanel(jtpViewer, jtpLog));
		jspViewerAndProperties.setRightComponent(getPropertiesView(jtpProperties));
		jspViewerAndProperties.setBorder(null);

		setDividerSize(5);
		setLeftComponent(new LeftPanelView(jtQuantFiles, jtIdentFiles,
				jtRawFiles));
		setRightComponent(jspViewerAndProperties);
		setBorder(null);

		// Viewer Width (Viewer)
		jspViewerAndProperties.setDividerLocation(600);
		// Left Menu (Files)
		setDividerLocation(250);
	}

	public JSplitPane getViewerAndProperties() {
		return jspViewerAndProperties;
	}

	public JPanel getCentralPanel(TabbedChartViewer jtpViewer, TabbedLog jtpLog) {
		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));
		jlViewer.setBorder(new EmptyBorder(5, 10, 5, 0));

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jlViewer, BorderLayout.PAGE_START);
		jPanel.add(getCentralComponents(jtpViewer, jtpLog), BorderLayout.CENTER);

		return jPanel;
	}

	private JSplitPane getCentralComponents(TabbedChartViewer jtpViewer,
			TabbedLog jtpLog) {

		final JSplitPane jspLeftViewerDetails = new JSplitPane();
		jspLeftViewerDetails.setDividerSize(5);
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