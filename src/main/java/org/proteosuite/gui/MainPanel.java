package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
		
		jpViewerAndProperties.setLayout(new BorderLayout());;
		jpViewerAndProperties.add(jspViewerAndProperties);

		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(3);
		jspMainPanelView.setLeftComponent(jpLeftPanelView);
		jspMainPanelView.setRightComponent(jpViewerAndProperties);

		setLayout(new BorderLayout());
		add(jspMainPanelView);

		// Viewer Width (Viewer)
		jspViewerAndProperties.setDividerLocation(600);
		// Left Menu (Files)
		jspMainPanelView.setDividerLocation(250);
	}

	public JSplitPane getMainPanelView() {
		return jspMainPanelView;
	}

	public JSplitPane getViewerAndProperties() {
		return jspViewerAndProperties;
	}
}