package org.proteosuite.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.listener.ActionListenerAbout;
import org.proteosuite.listener.ActionListenerCheckUpdate;
import org.proteosuite.listener.ActionListenerEditQuant;
import org.proteosuite.listener.ActionListenerExit;
import org.proteosuite.listener.ActionListenerMaxQ2MzQ;
import org.proteosuite.listener.ActionListenerMetaDataChanged;
import org.proteosuite.listener.ActionListenerMzML2MGF;
import org.proteosuite.listener.ActionListenerMzMLCompress;
import org.proteosuite.listener.ActionListenerOpenURL;
import org.proteosuite.listener.ActionListenerOptions;
import org.proteosuite.listener.ActionListenerProgenesis2MZQ;
import org.proteosuite.listener.ActionListenerViewerChanged;
import org.proteosuite.listener.ActionListenerViewerExtraChanged;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class MenuBar extends JMenuBar {

	public MenuBar(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final TabbedLog jtpLog, final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject, 
			final JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JTable jtRawFiles,
			final JTable jtIdentFiles, final JTable jtQuantFiles,
			final JTable jtRawData, final JTable jtMzML, final JTable jtMGF,
			final JTable jtMzId, final JTable jtMascotXMLView,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant, final JLabel jlFileNameMGFText,
			final JTable jtMzIDProtGroup, final TabbedProperties jtpProperties,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			MainPanel jpMainPanelView, IdentParamsView identParamsExecute,
			List<MzIdentMLUnmarshaller> aMzIDUnmarshaller) {
		JMenuBar jmMain = this;

		jmMain.add(getFileMenu(proteoSuiteView, jcbTechnique, jmSaveProject,
				jmCloseProject, jtpLog, jtpViewer, jbSaveProject, jcbOutputFormat,
				jepMGFView, jepMZQView, jepMascotXMLView, jepMzIDView,
				jepMzMLView, jlFileNameMzMLText, jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
				jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
				jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jlFileNameMGFText, jtMzIDProtGroup,
				jtpProperties, jcbPSM, jlRawFilesStatus));

		jmMain.add(getEditMenu());
		jmMain.add(getViewMenu(jpMainPanelView));
		jmMain.add(getProjectMenu(jtRawFiles, jcbTechnique));
		jmMain.add(getAnalyzeMenu(proteoSuiteView, jcbTechnique, jmSaveProject,
				jmCloseProject, jtpLog, jbSaveProject, jcbOutputFormat,
				jepMZQView, jepMzIDView, jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
				jtIdentFiles, jtQuantFiles, jtMzML, jtMGF, jtMzId,
				jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
				jtMzIDProtGroup, jtpProperties, jcbPSM,
				jlRawFilesStatus, identParamsExecute, aMzIDUnmarshaller));

		jmMain.add(getToolsMenu(proteoSuiteView));

		jmMain.add(getDatabasesMenu());

		jmMain.add(getWindowMenu(jtpViewer, jtpProperties, jtpLog));

		jmMain.add(getCustomHelpMenu());
	}

	private JMenu getFileMenu(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final TabbedLog jtpLog, final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject,
			final JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JTable jtRawFiles,
			final JTable jtIdentFiles, final JTable jtQuantFiles,
			final JTable jtRawData, final JTable jtMzML, final JTable jtMGF,
			final JTable jtMzId, final JTable jtMascotXMLView,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant, final JLabel jlFileNameMGFText,
			final JTable jtMzIDProtGroup, final TabbedProperties jtpProperties,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus) {
		JMenuItem jmNewProject = new JMenuItem("New Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/new.gif")));
		jmNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		jmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmNewProjectActionPerformed(jcbTechnique,
						jmSaveProject, jmCloseProject, jtpLog, jtpViewer,
						jbSaveProject, jcbOutputFormat, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			}
		});

		JMenuItem jmImportFile = new JMenuItem("Import File", new ImageIcon(
				getClass().getClassLoader().getResource("images/import.gif")));
		jmImportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_MASK));
		jmImportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmImportFileActionPerformed(jtRawFiles,
						jlFileNameMGFText, jtFeatureQuant, jtMzIDProtGroup,
						jmSaveProject, jtpLog, jtpProperties,
						jtpViewer, jbSaveProject,
						jcbPSM, jepMZQView, jepMzIDView, jepMzMLView,
						jlFileNameMzQText, jlRawFilesStatus,
						jlIdentFilesStatus, jlFileNameMzIDText,
						jlFileNameMzMLText, jtIdentFiles, jtMGF,
						jtMascotXMLView, jtMzId, jtMzML, jtPeptideQuant,
						jtProteinQuant, jtQuantFiles);
			}
		});

		JMenuItem jmOpenProject = new JMenuItem("Open Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/open.gif")));
		jmOpenProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		jmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.openProject(jtRawFiles, jlFileNameMGFText,
						jtMzIDProtGroup, jmCloseProject, jmSaveProject, jtpLog,
						jtpProperties, jtpViewer, jbSaveProject, jcbPSM,
						jepMzMLView, jepMzIDView, jlRawFilesStatus,
						jlIdentFilesStatus, jlFileNameMzMLText,
						jlFileNameMzIDText, jtIdentFiles, jtMGF,
						jtMascotXMLView, jtMzId, jtMzML, jtQuantFiles);
			}
		});

		JMenuItem jmOpenRecentProject = new JMenuItem("Open Recent Project");
		jmOpenRecentProject.setEnabled(false);

		jmCloseProject.setEnabled(false);
		jmCloseProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmCloseProjectActionPerformed(jmCloseProject,
						jmSaveProject, jtpLog, jtpViewer, jbSaveProject,
						jcbTechnique, jcbOutputFormat,
						jepMGFView, jepMZQView, jepMascotXMLView, jepMzIDView,
						jepMzMLView, jlFileNameMzQText, jlIdentFilesStatus,
						jlFileNameMzIDText, jlFileNameMzMLText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			}
		});

		jmSaveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		jmSaveProject.setEnabled(false);
		jmSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmSaveProjectActionPerformed(jtRawFiles,
						jcbTechnique, jmSaveProject, jmCloseProject,
						jbSaveProject, jcbOutputFormat, jtIdentFiles);
			}
		});

		JMenuItem jmPrint = new JMenuItem("Print", new ImageIcon(getClass()
				.getClassLoader().getResource("images/print.gif")));
		jmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_MASK));
		jmPrint.setEnabled(false);

		JMenuItem jmSaveProjectAs = new JMenuItem("Save Project As");
		jmSaveProjectAs.setEnabled(false);

		JMenuItem jmExit = new JMenuItem("Exit");
		jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		jmExit.addActionListener(new ActionListenerExit());

		JMenu jmFile = new JMenu("File");
		jmFile.add(jmNewProject);
		jmFile.add(jmImportFile);
		jmFile.add(new JPopupMenu.Separator());
		jmFile.add(jmOpenProject);
		jmFile.add(jmOpenRecentProject);
		jmFile.add(jmCloseProject);
		jmFile.add(new JPopupMenu.Separator());
		jmFile.add(jmSaveProject);
		jmFile.add(jmSaveProjectAs);
		jmFile.add(new JPopupMenu.Separator());
		jmFile.add(jmPrint);
		jmFile.add(new JPopupMenu.Separator());
		jmFile.add(jmExit);

		return jmFile;
	}

	private JMenu getEditMenu() {
		JMenu jmEdit = new JMenu("Edit");

		JMenuItem jmCut = new JMenuItem("Cut", new ImageIcon(getClass()
				.getClassLoader().getResource("images/cut.gif")));
		jmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_MASK));
		jmCut.setEnabled(false);
		jmEdit.add(jmCut);

		JMenuItem jmCopy = new JMenuItem("Copy", new ImageIcon(getClass()
				.getClassLoader().getResource("images/copy.gif")));
		jmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		jmCopy.setEnabled(false);
		jmEdit.add(jmCopy);

		JMenuItem jmPaste = new JMenuItem("Paste", new ImageIcon(getClass()
				.getClassLoader().getResource("images/paste.gif")));
		jmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_MASK));
		jmPaste.setEnabled(false);
		jmEdit.add(jmPaste);
		jmEdit.add(new JPopupMenu.Separator());

		return jmEdit;
	}

	private JMenu getViewMenu(final MainPanel jpMainPanelView) {
		JMenu jmView = new JMenu("View");

		final JMenuItem jmShowProjectFiles = new JMenuItem(
				"Show/Hide Project Files", new ImageIcon(getClass()
						.getClassLoader().getResource("images/thick.gif")));
		jmShowProjectFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jpMainPanelView.getMainPanelView().getDividerLocation() <= 5) {
					jpMainPanelView.getMainPanelView().setDividerLocation(250);
					Icon thick = new ImageIcon(getClass().getClassLoader()
							.getResource("images/thick.gif"));
					jmShowProjectFiles.setIcon(thick);
				} else {
					jpMainPanelView.getMainPanelView().setDividerLocation(0);
					jmShowProjectFiles.setIcon(null);
				}
			}
		});
		jmView.add(jmShowProjectFiles);

		final JMenuItem jmShowViewer = new JMenuItem("Show/Hide Viewer",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif")));
		jmShowViewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jpMainPanelView.getViewerAndProperties()
						.getDividerLocation() <= 5) {
					jpMainPanelView.getViewerAndProperties()
							.setDividerLocation(600);
					jmShowViewer.setIcon(new ImageIcon(getClass()
							.getClassLoader().getResource("images/thick.gif")));
				} else {
					jpMainPanelView.getViewerAndProperties()
							.setDividerLocation(0);
					jmShowViewer.setIcon(null);
				}
			}
		});
		jmView.add(jmShowViewer);

		JMenuItem jmShowProperties = new JMenuItem("Show/Hide Properties",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif")));
		jmShowProperties.setEnabled(false);
		jmView.add(jmShowProperties);

		return jmView;
	}

	private JMenu getProjectMenu(JTable jtRawFiles,
			JComboBox<String> jcbTechnique) {
		JMenu jmProject = new JMenu("Project");

		JMenuItem jmEditIdent = new JMenuItem("Set Identification Parameters",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/settings.gif")));
		jmProject.add(jmEditIdent);

		JMenuItem jmEditQuant = new JMenuItem("Set Quantitation Parameters",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/settings.gif")));
		jmEditQuant.addActionListener(new ActionListenerEditQuant(jtRawFiles,
				jcbTechnique));
		jmProject.add(jmEditQuant);

		return jmProject;
	}

	private JMenu getAnalyzeMenu(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final TabbedLog jtpLog, final JButton jbSaveProject,
			final JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMZQView, final JEditorPane jepMzIDView,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JTable jtRawFiles,
			final JTable jtIdentFiles, final JTable jtQuantFiles,
			final JTable jtMzML, final JTable jtMGF, final JTable jtMzId,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant, final JTable jtMzIDProtGroup,
			final TabbedProperties jtpProperties,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			final IdentParamsView identParamsExecute,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller) {
		JMenu jmAnalyze = new JMenu("Analyze");

		JMenuItem jmRunIdentAnalysis = new JMenuItem(
				"Run Identification Analysis", new ImageIcon(getClass()
						.getClassLoader().getResource("images/runid.gif")));
		jmRunIdentAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, 0));
		jmRunIdentAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmRunIdentAnalysisActionPerformed(jtRawFiles,
						jtMzIDProtGroup, identParamsExecute, jtpLog,
						jtIdentFiles, aMzIDUnmarshaller, jlRawFilesStatus,
						jlIdentFilesStatus, jtpProperties, jcbPSM, jepMzIDView,
						jlFileNameMzIDText, jtMzId);
			}
		});
		jmAnalyze.add(jmRunIdentAnalysis);

		JMenuItem jmRunQuantAnalysis = new JMenuItem("Run Quantition Analysis",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/run.gif")));
		jmRunQuantAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F5, 0));
		jmRunQuantAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				proteoSuiteView.jmRunQuantAnalysisActionPerformed(jtpLog,
						jmCloseProject, jmSaveProject, jtpProperties,
						jbSaveProject, jcbTechnique, jcbOutputFormat, jepMZQView,
						jlFileNameMzQText, jtFeatureQuant, jtIdentFiles,
						jtPeptideQuant, jtProteinQuant, jtQuantFiles,
						jtRawFiles);
			}
		});
		jmAnalyze.add(jmRunQuantAnalysis);

		return jmAnalyze;
	}

	private JMenu getToolsMenu(ProteoSuiteView proteoSuiteView) {
		JMenu jmTools = new JMenu("Tools");
		JMenu jmConverters = new JMenu("Converters");

		JMenuItem jmMzML2MGF = new JMenuItem("mzML to MGF");
		jmMzML2MGF.addActionListener(new ActionListenerMzML2MGF());
		jmConverters.add(jmMzML2MGF);

		JMenuItem jmMaxQ2MZQ = new JMenuItem("MaxQuant to mzQuantML");
		jmMaxQ2MZQ.addActionListener(new ActionListenerMaxQ2MzQ());
		jmConverters.add(jmMaxQ2MZQ);

		JMenuItem jmProgenesis2MZQ = new JMenuItem("Progenesis to mzQuantML");
		jmProgenesis2MZQ.addActionListener(new ActionListenerProgenesis2MZQ());
		jmConverters.add(jmProgenesis2MZQ);

		jmTools.add(jmConverters);

		final JMenuItem jMzMLCompressed = new JMenuItem("Compress MzML file");
		jMzMLCompressed.addActionListener(new ActionListenerMzMLCompress());
		jmTools.add(jMzMLCompressed);

		JMenuItem jmCustomize = new JMenuItem("Customize");
		jmCustomize.setEnabled(false);
		jmTools.add(jmCustomize);

		JMenuItem jmOptions = new JMenuItem("Options");
		jmOptions.addActionListener(new ActionListenerOptions(proteoSuiteView));
		jmTools.add(jmOptions);

		return jmTools;
	}

	private JMenu getDatabasesMenu() {
		JMenu jmDatabases = new JMenu("Databases");

		JMenuItem jmSubmitPRIDE = new JMenuItem("Submit to PRIDE");
		jmSubmitPRIDE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JOptionPane
						.showMessageDialog(
								null,
								"The module for submitting data into PRIDE is under development.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		jmDatabases.add(jmSubmitPRIDE);

		return jmDatabases;
	}

	private JMenu getWindowMenu(JTabbedPane jtpViewer,
			TabbedProperties jtpProperties, JTabbedPane jtpLog) {
		JMenu jmWindow = new JMenu("Window");
		final JMenuItem jm2DView = new JMenuItem("2D View");
		final JMenuItem jm3DView = new JMenuItem("3D View");
		final JMenuItem jmTIC = new JMenuItem("TIC");

		final JMenuItem jmSpectrum = new JMenuItem("Spectrum", new ImageIcon(
				getClass().getClassLoader().getResource("images/thick.gif")));

		jmSpectrum.addActionListener(new ActionListenerViewerChanged(jtpViewer,
				jmSpectrum, jmTIC, jm2DView, jm3DView, 0));
		jmWindow.add(jmSpectrum);

		jmTIC.addActionListener(new ActionListenerViewerChanged(jtpViewer,
				jmSpectrum, jmTIC, jm2DView, jm3DView, 1));
		jmWindow.add(jmTIC);

		jm2DView.addActionListener(new ActionListenerViewerChanged(jtpViewer,
				jmSpectrum, jmTIC, jm2DView, jm3DView, 1));
		jmWindow.add(jm2DView);

		jm3DView.addActionListener(new ActionListenerViewerChanged(jtpViewer,
				jmSpectrum, jmTIC, jm2DView, jm3DView, 1));
		jmWindow.add(jm3DView);

		jmWindow.add(new JPopupMenu.Separator());
		final JMenuItem jmMGFView = new JMenuItem("MGF View");
		final JMenuItem jmMascotXMLView = new JMenuItem("Mascot XML View");
		final JMenuItem jmMzIdentMLView = new JMenuItem("mzIdentML View");

		final JMenuItem jmMzMLView = new JMenuItem("mzML View", new ImageIcon(
				getClass().getClassLoader().getResource("images/thick.gif")));
		final JMenuItem jmMzQuantMLView = new JMenuItem("mzQuantML View");
		final JMenuItem jmRawData = new JMenuItem("Raw Data");

		jmMzMLView.addActionListener(new ActionListenerMetaDataChanged(
				jtpProperties, jmMzMLView, jmMGFView, jmMzIdentMLView,
				jmMascotXMLView, jmMzQuantMLView, 0));
		jmWindow.add(jmMzMLView);

		jmMGFView.addActionListener(new ActionListenerMetaDataChanged(
				jtpProperties, jmMzMLView, jmMGFView, jmMzIdentMLView,
				jmMascotXMLView, jmMzQuantMLView, 1));
		jmWindow.add(jmMGFView);

		jmMzIdentMLView.addActionListener(new ActionListenerMetaDataChanged(
				jtpProperties, jmMzMLView, jmMGFView, jmMzIdentMLView,
				jmMascotXMLView, jmMzQuantMLView, 2));
		jmWindow.add(jmMzIdentMLView);

		jmMascotXMLView.addActionListener(new ActionListenerMetaDataChanged(
				jtpProperties, jmMzMLView, jmMGFView, jmMzIdentMLView,
				jmMascotXMLView, jmMzQuantMLView, 3));
		jmWindow.add(jmMascotXMLView);

		jmMzQuantMLView.addActionListener(new ActionListenerMetaDataChanged(
				jtpProperties, jmMzMLView, jmMGFView, jmMzIdentMLView,
				jmMascotXMLView, jmMzQuantMLView, 4));
		jmWindow.add(jmMzQuantMLView);
		jmWindow.add(new JPopupMenu.Separator());

		final JMenuItem jmLog = new JMenuItem("Log", new ImageIcon(getClass()
				.getClassLoader().getResource("images/thick.gif")));
		jmLog.addActionListener(new ActionListenerViewerExtraChanged(jtpLog,
				jmLog, jmRawData, 0));
		jmWindow.add(jmLog);

		jmRawData.addActionListener(new ActionListenerViewerExtraChanged(
				jtpLog, jmLog, jmRawData, 1));
		jmWindow.add(jmRawData);

		return jmWindow;
	}

	private JMenu getCustomHelpMenu() {
		JMenu jmHelp = new JMenu("Help");

		JMenuItem jmHelpContent = new JMenuItem("ProteoSuite Help",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/help.gif")));
		jmHelpContent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		jmHelpContent.setToolTipText("Find how to use ProteoSuite");
		jmHelpContent.addActionListener(new ActionListenerOpenURL(
				"http://www.proteosuite.org/?q=tutorials"));

		jmHelp.add(jmHelpContent);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmContactUs = new JMenuItem("Contact us");
		jmContactUs
				.setToolTipText("Click here for contacting our group and collaborators");
		jmContactUs.addActionListener(new ActionListenerOpenURL(
				"http://www.proteosuite.org/?q=contact"));

		jmHelp.add(jmContactUs);

		JMenuItem jmCheckUpdates = new JMenuItem("Check for updates");
		jmCheckUpdates.addActionListener(new ActionListenerCheckUpdate());
		jmHelp.add(jmCheckUpdates);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmAbout = new JMenuItem("About ProteoSuite");
		jmAbout.addActionListener(new ActionListenerAbout());
		jmHelp.add(jmAbout);

		return jmHelp;
	}
}
