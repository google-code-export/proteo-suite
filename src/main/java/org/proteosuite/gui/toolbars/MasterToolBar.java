package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.TabbedChartViewer;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class MasterToolBar extends JPanel {

	public MasterToolBar(ProteoSuiteView proteoSuiteView,
			JMenuItem jmCloseProject, JMenuItem jmSaveProject,
			TabbedChartViewer jtpViewer, 
			JButton jbSaveProject, JComboBox<String> jcbTechnique,			
			JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzQText,
			JComboBox<String> jcbOutputFormat, JLabel jlFileNameMGFText,			
			JComboBox<String> jcbPSM, JLabel jlRawFilesStatus,
			IdentParamsView identParamsExecute,
			List<MzMLUnmarshaller> aMzMLUnmarshaller,
			List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		JToolBar projectToolBar = new ProjectToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jtpViewer, 
				jbSaveProject, jcbTechnique, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jcbOutputFormat, jlFileNameMGFText,
				jcbPSM, jlRawFilesStatus,
				aMzMLUnmarshaller,
				aMzIDUnmarshaller,
				aMzQUnmarshaller);
		JToolBar manipulationToolBar = new ManipulationToolBar();
		JToolBar analysisToolBar = new AnalysisToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jbSaveProject, jcbTechnique, jlFileNameMzIDText, jlIdentFilesStatus,
				jlFileNameMzQText, jcbOutputFormat, jcbPSM,
				jlRawFilesStatus, identParamsExecute, aMzIDUnmarshaller);

		setPreferredSize(new Dimension(933, 32));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(projectToolBar);
		add(manipulationToolBar);
		add(analysisToolBar);
	}
}
