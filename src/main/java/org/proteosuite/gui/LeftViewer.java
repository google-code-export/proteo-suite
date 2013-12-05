package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Andrew Collins
 */
public class LeftViewer extends JPanel {

	public LeftViewer(TabbedChartViewer jtpViewer, TabbedLog jtpLog) {
		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));
		jlViewer.setBorder(new EmptyBorder(5, 10, 5, 0));
		
		setLayout(new BorderLayout());
		add(jlViewer, BorderLayout.PAGE_START);
		add(getLeftViewerDetails(jtpViewer, jtpLog), BorderLayout.CENTER);
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
}
