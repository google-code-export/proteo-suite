package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		JPanel jpLeftViewerBottom = getLeftViewerBottom(jtpLog);
		JPanel jpLeftViewerDetails = getLeftViewerDetails(jtpViewer,
				jpLeftViewerBottom);
		JPanel jspLeftViewerHeader = getLeftViewerHeader();

		final JSplitPane jspLeftViewer = new JSplitPane();
		jspLeftViewer.setDividerLocation(20);
		jspLeftViewer.setDividerSize(1);
		jspLeftViewer.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspLeftViewer.setTopComponent(jspLeftViewerHeader);
		jspLeftViewer.setRightComponent(jpLeftViewerDetails);

		setLayout(new BorderLayout());
		add(jspLeftViewer);
	}

	private JPanel getLeftViewerBottom(TabbedLog jtpLog) {
		JPanel jpLeftViewerBottom = new JPanel();
		jpLeftViewerBottom.setLayout(new BorderLayout());
		jpLeftViewerBottom.add(jtpLog);

		return jpLeftViewerBottom;
	}

	private JPanel getLeftViewerHeader() {
		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setBackground(new Color(255, 255, 255));
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));

		JPanel jspLeftViewerHeader = new JPanel();
		jspLeftViewerHeader.setMinimumSize(new Dimension(380, 25));
		jspLeftViewerHeader.setPreferredSize(new Dimension(380, 25));
		jspLeftViewerHeader.setLayout(new BorderLayout());
		jspLeftViewerHeader.add(jlViewer);
		jlViewer.setBorder(new EmptyBorder(0, 10, 0, 0));

		return jspLeftViewerHeader;
	}

	private JPanel getLeftViewerDetails(TabbedChartViewer jtpViewer,
			JPanel jpLeftViewerBottom) {
		final JSplitPane jspLeftViewerDetails = new JSplitPane();
		jspLeftViewerDetails.setDividerLocation(350);
		jspLeftViewerDetails.setDividerSize(2);
		jspLeftViewerDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspLeftViewerDetails.setRightComponent(jpLeftViewerBottom);
		jspLeftViewerDetails.setTopComponent(jtpViewer);

		JPanel jpLeftViewerDetails = new JPanel();
		jpLeftViewerDetails.setBackground(new Color(255, 255, 255));
		jpLeftViewerDetails.setLayout(new BorderLayout());
		jpLeftViewerDetails.add(jspLeftViewerDetails);

		// Viewer Height (Viewer)
		jspLeftViewerDetails.setDividerLocation(480);

		return jpLeftViewerDetails;
	}
}
