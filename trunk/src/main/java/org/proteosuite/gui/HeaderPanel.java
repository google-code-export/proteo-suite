package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;

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
			TabbedProperties jtpProperties, TabbedLog jtpLog,
			TabbedChartViewer jtpViewer, JButton jbSaveProject,
			JComboBox<String> jcbTechnique, JEditorPane jepMGFView,
			JEditorPane jepMZQView, JEditorPane jepMascotXMLView,
			JEditorPane jepMzIDView, JEditorPane jepMzMLView,
			JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
			JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText,
			JTable jtRawFiles, JTable jtIdentFiles, JTable jtQuantFiles,
			JTable jtRawData, JTable jtMzML, JTable jtMGF, JTable jtMzId,
			JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant,
			JComboBox<String> jcbOutputFormat, JLabel jlFileNameMGFText,
			JTable jtMzIDProtGroup, JComboBox<String> jcbPSM,
			JLabel jlRawFilesStatus, IdentParamsView identParamsExecute,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		final JPanel jpToolBar = new MasterToolBar(proteoSuiteView,
				jmCloseProject, jmSaveProject, jtpProperties, jtpLog,
				jtpViewer, jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jcbOutputFormat, jlFileNameMGFText,
				jtMzIDProtGroup, jcbPSM, jlRawFilesStatus, identParamsExecute,
				aMzMLUnmarshaller, aMzIDUnmarshaller, aMzQUnmarshaller);

		final JPanel jpProjectStatus = new ProjectStatus(jcbTechnique,
				jcbOutputFormat, jlRawFilesStatus, jlIdentFilesStatus);

		setLayout(new BorderLayout());
		add(jpToolBar, BorderLayout.PAGE_START);
		add(jpProjectStatus, BorderLayout.PAGE_END);
	}
}
