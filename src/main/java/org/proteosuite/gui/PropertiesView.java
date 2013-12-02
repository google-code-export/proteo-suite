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

		setLayout(new BorderLayout());
		add(jspProperties);
	}

	private JPanel getPropertiesPanel() {
		JLabel jlProperties = new JLabel("Data and Metadata");
		jlProperties.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlProperties.setForeground(new Color(102, 102, 102));

		JPanel jpProperties = new JPanel();

		jpProperties.setMinimumSize(new Dimension(302, 25));
		jpProperties.setPreferredSize(new Dimension(313, 25));
		jpProperties.setLayout(new BorderLayout());
		jpProperties.add(jlProperties);
		jlProperties.setBorder(new EmptyBorder(0, 10, 0, 0));

		return jpProperties;
	}

	private JPanel getPropertiesTab(TabbedProperties jtpProperties) {

		JPanel jpPropetiesTab = new JPanel();
		
		jpPropetiesTab.setLayout(new BorderLayout());
		jpPropetiesTab.add(jtpProperties);

		return jpPropetiesTab;
	}
}
