package org.proteosuite.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectStatus extends JPanel {

	public ProjectStatus(JComboBox<String> jcbTechnique, JComboBox<String> jcbOutputFormat, JLabel jlRawFilesStatus, 
			JLabel jlIdentFilesStatus)
	{
		JLabel jlQuantFilesStatus = new JLabel(new ImageIcon(getClass()
				.getClassLoader().getResource("images/empty.gif")));
		jlQuantFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		
		
		JLabel jlRawFiles = new JLabel("1) Raw Files:");
		JLabel jlIdentFiles = new JLabel("2) Identification Files:");
		JLabel jlTechnique = new JLabel("3) Technique:");
		JLabel jlQuantFiles = new JLabel("4) Quant Files:");
		
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(102, 30));

		setLayout(new FlowLayout(FlowLayout.LEFT, 20, 1));
		add(jlRawFiles);
		add(jlRawFilesStatus);

		add(jlIdentFiles);
		add(jlIdentFilesStatus);

		add(jlTechnique);
		add(jcbTechnique);

		add(jlQuantFiles);
		add(jcbOutputFormat);
		add(jlQuantFilesStatus);
	}
}
