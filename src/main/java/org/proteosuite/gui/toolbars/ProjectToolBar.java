package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

public class ProjectToolBar extends JToolBar {

	public ProjectToolBar(final ProteoSuiteView proteoSuiteView, final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject,
			final TabbedProperties jtpProperties, final TabbedLog jtpLog,
			final TabbedChartViewer jtpViewer, 
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMGFExcel, final JButton jbExportMzMLExcel,
			final JButton jbSaveProject, final JComboBox<String> jcbTechnique,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JTable jtRawFiles,
			final JTable jtIdentFiles, final JTable jtQuantFiles,
			final JTable jtRawData, final JTable jtMzML, final JTable jtMGF,
			final JTable jtMzId, final JTable jtMascotXMLView,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant, final JComboBox<String> jcbOutputFormat, 
			final JLabel jlFileNameMGFText, final JTable jtMzIDProtGroup, final JTabbedPane jtpMzQuantMLDetail, 
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus)
	{
		JButton jbNewProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/new.gif")));
		jbNewProject.setToolTipText("New Project (Ctrl + N)");
		jbNewProject.setFocusable(false);
		jbNewProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbNewProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbNewProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.jmNewProjectActionPerformed(jcbTechnique, jmSaveProject,
						jmCloseProject, jtpLog, jtpViewer, jbSaveProject,
						jbExportMzMLExcel, jbExportMGFExcel,
						jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
						jbExportPepMZQExcel, jbExportProtMZQExcel,
						jbExportFeatMZQExcel, jcbOutputFormat, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant);
			}
		});

		add(jbNewProject);

		JButton jbImportFile = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/import.gif")));
		jbImportFile.setToolTipText("Import File (Ctrl + I)");
		jbImportFile.setFocusable(false);
		jbImportFile.setHorizontalTextPosition(SwingConstants.CENTER);
		jbImportFile.setMaximumSize(new Dimension(27, 21));
		jbImportFile.setMinimumSize(new Dimension(27, 21));
		jbImportFile.setPreferredSize(new Dimension(27, 21));
		jbImportFile.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbImportFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.jmImportFileActionPerformed(jtRawFiles, jlFileNameMGFText,
						jtFeatureQuant, jtMzIDProtGroup, jmSaveProject,
						jtpLog, jtpProperties, jtpMzQuantMLDetail, jtpViewer,
						jbExportPepMZQExcel, jbExportProtMZQExcel,
						jbExportFeatMZQExcel, jbExportMascotXMLExcel,
						jbExportMzIdentMLExcel, jbExportMGFExcel,
						jbExportMzMLExcel, jbSaveProject, jcbPSM, jepMZQView,
						jepMzIDView, jepMzMLView, jlFileNameMzQText,
						jlRawFilesStatus, jlIdentFilesStatus,
						jlFileNameMzIDText, jlFileNameMzMLText, jtIdentFiles,
						jtMGF, jtMascotXMLView, jtMzId, jtMzML, jtPeptideQuant,
						jtProteinQuant, jtQuantFiles);
			}
		});
		add(jbImportFile);
		



		JButton jbOpenProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/open.gif")));
		jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
		jbOpenProject.setFocusable(false);
		jbOpenProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbOpenProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbOpenProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.openProject(jtRawFiles, jlFileNameMGFText, jtMzIDProtGroup,
						jmCloseProject, jmSaveProject, jtpLog,
						jtpProperties, jtpViewer, jbSaveProject, jcbPSM,
						jepMzMLView, jepMzIDView, jlRawFilesStatus,
						jlIdentFilesStatus, jlFileNameMzMLText,
						jlFileNameMzIDText, jtIdentFiles, jtMGF,
						jtMascotXMLView, jtMzId, jtMzML, jtQuantFiles);
			}
		});
		add(jbOpenProject);

		jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
		jbSaveProject.setEnabled(false);
		jbSaveProject.setFocusable(false);
		jbSaveProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbSaveProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbSaveProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.jmSaveProjectActionPerformed(jtRawFiles, jcbTechnique,
						jmSaveProject, jmCloseProject, jbSaveProject,
						jcbOutputFormat, jtIdentFiles);
			}
		});
		add(jbSaveProject);
	}
}
