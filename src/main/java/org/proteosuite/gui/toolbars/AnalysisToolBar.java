package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.IdentParamsView;


import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class AnalysisToolBar extends JToolBar {
	
	public AnalysisToolBar(final ProteoSuiteView proteoSuiteView, final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject,
			final JButton jbSaveProject, final JComboBox<String> jcbTechnique,			
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JComboBox<String> jcbOutputFormat, 
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus, final IdentParamsView identParamsExecute, 
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller)
	{
		setMaximumSize(new Dimension(34, 9));
		setMinimumSize(new Dimension(34, 9));
		setFloatable(false);
		


		JButton jbRunIdentAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/runid.gif")));
		jbRunIdentAnalysis.setToolTipText("Run Identification Pipeline (F7)");
		jbRunIdentAnalysis.setFocusable(false);
		jbRunIdentAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunIdentAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		add(jbRunIdentAnalysis);
		

		JButton jbRunQuantAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/run.gif")));
		jbRunQuantAnalysis.setToolTipText("Run Quantitation Pipeline (F5)");
		jbRunQuantAnalysis.setFocusable(false);
		jbRunQuantAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunQuantAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		add(jbRunQuantAnalysis);
	}
}
