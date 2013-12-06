package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

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
			TabbedProperties jtpProperties, TabbedLog jtpLog,
			TabbedChartViewer jtpViewer, 
			JButton jbSaveProject, JComboBox<String> jcbTechnique,
			JEditorPane jepMGFView, JEditorPane jepMZQView,
			JEditorPane jepMascotXMLView, JEditorPane jepMzIDView,
			JEditorPane jepMzMLView, JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzQText, JTable jtRawFiles, JTable jtIdentFiles,
			JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF,
			JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant,
			JComboBox<String> jcbOutputFormat, JLabel jlFileNameMGFText,
			JTable jtMzIDProtGroup,
			JComboBox<String> jcbPSM, JLabel jlRawFilesStatus,
			IdentParamsView identParamsExecute,
			List<MzMLUnmarshaller> aMzMLUnmarshaller,
			List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		JToolBar projectToolBar = new ProjectToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jtpProperties, jtpLog,
				jtpViewer, 
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jcbOutputFormat, jlFileNameMGFText,
				jtMzIDProtGroup, jcbPSM, jlRawFilesStatus,
				aMzMLUnmarshaller,
				aMzIDUnmarshaller,
				aMzQUnmarshaller);
		JToolBar manipulationToolBar = new ManipulationToolBar();
		JToolBar analysisToolBar = new AnalysisToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jtpProperties, jtpLog,
				jbSaveProject, jcbTechnique, jepMZQView,
				jepMzIDView, jlFileNameMzIDText, jlIdentFilesStatus,
				jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
				jtMzId, jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
				jcbOutputFormat, jtMzIDProtGroup,  jcbPSM,
				jlRawFilesStatus, identParamsExecute, aMzIDUnmarshaller);

		setPreferredSize(new Dimension(933, 32));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(projectToolBar);
		add(manipulationToolBar);
		add(analysisToolBar);
	}
}
