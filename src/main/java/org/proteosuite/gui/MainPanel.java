package org.proteosuite.gui;

import java.awt.BorderLayout;

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

	public MainPanel(JTable jtQuantFiles, JTable jtIdentFiles, JTable jtRawFiles, TabbedChartViewer jtpViewer, 
			TabbedLog jtpLog, TabbedProperties jtpProperties) {
		JPanel jpLeftPanelView = new LeftPanelView(jtQuantFiles, jtIdentFiles,
				jtRawFiles);
		JPanel jpLeftViewer = new LeftViewer(jtpViewer, jtpLog);
		JPanel jpPropertiesBox = new PropertiesView(jtpProperties);
		
		
		jspViewerAndProperties.setDividerLocation(380);
		jspViewerAndProperties.setDividerSize(3);
		jspViewerAndProperties.setLeftComponent(jpLeftViewer);
		jspViewerAndProperties.setRightComponent(jpPropertiesBox);
		
		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(3);
		jspMainPanelView.setLeftComponent(jpLeftPanelView);
		jspMainPanelView.setRightComponent(jspViewerAndProperties);

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
}