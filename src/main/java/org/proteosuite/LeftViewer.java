package org.proteosuite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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

		JPanel jpLeftViewer = this;
		GroupLayout jpLeftViewerLayout = new GroupLayout(jpLeftViewer);
		jpLeftViewer.setLayout(jpLeftViewerLayout);
		jpLeftViewerLayout.setHorizontalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer));
		jpLeftViewerLayout.setVerticalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer, GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));
	}

	private JPanel getLeftViewerBottom(TabbedLog jtpLog) {
		JPanel jpLeftViewerBottom = new JPanel();

		GroupLayout jpLeftViewerBottomLayout = new GroupLayout(
				jpLeftViewerBottom);
		jpLeftViewerBottom.setLayout(jpLeftViewerBottomLayout);
		jpLeftViewerBottomLayout.setHorizontalGroup(jpLeftViewerBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpLog));
		jpLeftViewerBottomLayout.setVerticalGroup(jpLeftViewerBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpLog));

		return jpLeftViewerBottom;
	}

	private JPanel getLeftViewerHeader() {
		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setBackground(new Color(255, 255, 255));
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));

		JPanel jspLeftViewerHeader = new JPanel();
		jspLeftViewerHeader.setMaximumSize(new Dimension(32767, 25));
		jspLeftViewerHeader.setMinimumSize(new Dimension(380, 25));
		jspLeftViewerHeader.setPreferredSize(new Dimension(380, 25));
		GroupLayout jspLeftViewerHeaderLayout = new GroupLayout(
				jspLeftViewerHeader);
		jspLeftViewerHeader.setLayout(jspLeftViewerHeaderLayout);
		jspLeftViewerHeaderLayout.setHorizontalGroup(jspLeftViewerHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jspLeftViewerHeaderLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlViewer)
								.addContainerGap(316, Short.MAX_VALUE)));
		jspLeftViewerHeaderLayout.setVerticalGroup(jspLeftViewerHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jspLeftViewerHeaderLayout.createSequentialGroup()
								.addComponent(jlViewer)
								.addContainerGap(7, Short.MAX_VALUE)));

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
		GroupLayout jpLeftViewerDetailsLayout = new GroupLayout(
				jpLeftViewerDetails);
		jpLeftViewerDetails.setLayout(jpLeftViewerDetailsLayout);
		jpLeftViewerDetailsLayout.setHorizontalGroup(jpLeftViewerDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewerDetails));
		jpLeftViewerDetailsLayout.setVerticalGroup(jpLeftViewerDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewerDetails));

		// Viewer Height (Viewer)
		jspLeftViewerDetails.setDividerLocation(480);

		return jpLeftViewerDetails;
	}
}
