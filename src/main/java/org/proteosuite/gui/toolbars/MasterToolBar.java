package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.border.EtchedBorder;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class MasterToolBar extends JPanel {

	public MasterToolBar(ProteoSuiteView proteoSuiteView, JMenuItem jmCloseProject, JMenuItem jmSaveProject, TabbedProperties jtpProperties, TabbedLog jtpLog, TabbedChartViewer jtpViewer, JButton jbExportPepMZQExcel, JButton jbExportProtMZQExcel, JButton jbExportFeatMZQExcel, JButton jbExportMascotXMLExcel, JButton jbExportMzIdentMLExcel, JButton jbExportMGFExcel, JButton jbExportMzMLExcel, JButton jbSaveProject, JComboBox<String> jcbTechnique, JEditorPane jepMGFView, JEditorPane jepMZQView, JEditorPane jepMascotXMLView, JEditorPane jepMzIDView, JEditorPane jepMzMLView, JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText, JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText, JTable jtRawFiles, JTable jtIdentFiles, JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF, JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant, JTable jtProteinQuant, JTable jtFeatureQuant, JComboBox<String> jcbOutputFormat, JLabel jlFileNameMGFText, JTable jtMzIDProtGroup, JTabbedPane jtpMzQuantMLDetail, JComboBox<String> jcbPSM, JLabel jlRawFilesStatus, IdentParamsView identParamsExecute, List<MzIdentMLUnmarshaller> aMzIDUnmarshaller)
	{
		JToolBar jToolBar1 = new ProjectToolBar(proteoSuiteView, jmCloseProject,
				jmSaveProject, jtpProperties, jtpLog, jtpViewer,
				jbExportPepMZQExcel, jbExportProtMZQExcel,
				jbExportFeatMZQExcel, jbExportMascotXMLExcel,
				jbExportMzIdentMLExcel, jbExportMGFExcel, jbExportMzMLExcel,
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jcbOutputFormat, jlFileNameMGFText,
				jtMzIDProtGroup, jtpMzQuantMLDetail, jcbPSM, jlRawFilesStatus);
		JToolBar jToolBar2 = new ManipulationToolBar();
		JToolBar jToolBar3 = new AnalysisToolBar(proteoSuiteView, jmCloseProject,
				jmSaveProject, jtpProperties, jtpLog, jbExportPepMZQExcel,
				jbExportProtMZQExcel, jbExportFeatMZQExcel, jbSaveProject,
				jcbTechnique, jepMZQView, jepMzIDView, jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
				jtIdentFiles, jtQuantFiles, jtMzId, jtPeptideQuant,
				jtProteinQuant, jtFeatureQuant, jcbOutputFormat,
				jtMzIDProtGroup, jtpMzQuantMLDetail, jcbPSM, jlRawFilesStatus,
				identParamsExecute, aMzIDUnmarshaller);
		
		
		setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(933, 32));
		GroupLayout jpToolBarLayout = new GroupLayout(this);
		setLayout(jpToolBarLayout);
		jpToolBarLayout.setHorizontalGroup(jpToolBarLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jpToolBarLayout
								.createSequentialGroup()
								.addComponent(jToolBar1,
										GroupLayout.PREFERRED_SIZE, 125,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jToolBar2,
										GroupLayout.PREFERRED_SIZE, 132,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jToolBar3,
										GroupLayout.PREFERRED_SIZE, 121,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(826, Short.MAX_VALUE)));
		jpToolBarLayout.setVerticalGroup(jpToolBarLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jpToolBarLayout
						.createSequentialGroup()
						.addGroup(
								jpToolBarLayout
										.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
										.addComponent(jToolBar3,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jToolBar2,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jToolBar1,
												GroupLayout.Alignment.LEADING,
												GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
	}
}
