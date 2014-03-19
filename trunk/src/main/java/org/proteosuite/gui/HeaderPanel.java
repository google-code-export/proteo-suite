package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.toolbars.MasterToolBar;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class HeaderPanel extends JPanel {

	public HeaderPanel(ProteoSuiteView proteoSuiteView,
			JMenuItem jmCloseProject, JMenuItem jmSaveProject,
			TabbedChartViewer jtpViewer, JButton jbSaveProject,
			JComboBox<String> jcbTechnique,	JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
			JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText,			
			JComboBox<String> jcbOutputFormat, JLabel jlFileNameMGFText,
			JComboBox<String> jcbPSM,
			JLabel jlRawFilesStatus, IdentParamsView identParamsExecute,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		final JPanel jpToolBar = new MasterToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jtpViewer, jbSaveProject, jcbTechnique, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,				
				jcbOutputFormat, jlFileNameMGFText,
				jcbPSM, jlRawFilesStatus, identParamsExecute,
				aMzMLUnmarshaller, aMzIDUnmarshaller, aMzQUnmarshaller);

		final JPanel jpProjectStatus = new ProjectStatus(jcbTechnique,
				jcbOutputFormat, jlRawFilesStatus, jlIdentFilesStatus);

		setLayout(new BorderLayout());
		
	}
}
