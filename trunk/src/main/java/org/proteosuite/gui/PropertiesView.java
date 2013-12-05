package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Andrew Collins
 */
public class PropertiesView extends JPanel {

	public PropertiesView(TabbedProperties jtpProperties) {		
		JLabel jlProperties = new JLabel("Data and Metadata");
		jlProperties.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlProperties.setForeground(new Color(102, 102, 102));
		jlProperties.setBorder(new EmptyBorder(5, 10, 5, 0));

		setLayout(new BorderLayout());
		add(jlProperties, BorderLayout.PAGE_START);
		add(jtpProperties, BorderLayout.CENTER);
	}
}
