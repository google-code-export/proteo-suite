package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
public class PropertiesView extends JPanel {

	public PropertiesView(TabbedProperties jtpProperties) {

		JPanel jpPropetiesTab = getPropertiesTab(jtpProperties);
		JPanel jpProperties = getPropertiesPanel();

		final JSplitPane jspProperties = new JSplitPane();
		jspProperties.setDividerLocation(20);
		jspProperties.setDividerSize(1);
		jspProperties.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspProperties.setTopComponent(jpProperties);
		jspProperties.setRightComponent(jpPropetiesTab);

		JPanel jpPropertiesBox = this;
		GroupLayout jpPropertiesBoxLayout = new GroupLayout(jpPropertiesBox);
		jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
		jpPropertiesBoxLayout.setHorizontalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties));
		jpPropertiesBoxLayout.setVerticalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties, GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));
	}

	private JPanel getPropertiesPanel() {
		JLabel jlProperties = new JLabel("Data and Metadata");
		jlProperties.setBackground(new Color(255, 255, 255));
		jlProperties.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlProperties.setForeground(new Color(102, 102, 102));

		JPanel jpProperties = new JPanel();

		jpProperties.setMaximumSize(new Dimension(32767, 25));
		jpProperties.setMinimumSize(new Dimension(302, 25));
		jpProperties.setPreferredSize(new Dimension(313, 25));
		GroupLayout jpPropertiesLayout = new GroupLayout(jpProperties);
		jpProperties.setLayout(jpPropertiesLayout);
		jpPropertiesLayout.setHorizontalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpPropertiesLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlProperties)
								.addContainerGap(417, Short.MAX_VALUE)));
		jpPropertiesLayout.setVerticalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpPropertiesLayout.createSequentialGroup()
								.addComponent(jlProperties)
								.addContainerGap(7, Short.MAX_VALUE)));

		return jpProperties;
	}

	private JPanel getPropertiesTab(TabbedProperties jtpProperties) {

		JPanel jpPropetiesTab = new JPanel();
		jpPropetiesTab.setBackground(new Color(255, 255, 255));
		jpPropetiesTab.setForeground(new Color(153, 153, 255));
		GroupLayout jpPropetiesTabLayout = new GroupLayout(jpPropetiesTab);
		jpPropetiesTab.setLayout(jpPropetiesTabLayout);
		jpPropetiesTabLayout.setHorizontalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));
		jpPropetiesTabLayout.setVerticalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));

		return jpPropetiesTab;
	}
}
