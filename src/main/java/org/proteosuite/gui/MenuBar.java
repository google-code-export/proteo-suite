package org.proteosuite.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.proteosuite.ProteoSuiteView;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class MenuBar extends JMenuBar {

	public MenuBar(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject, 
			final JComboBox<String> jcbOutputFormat,			
			final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			IdentParamsView identParamsExecute,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		JMenuBar jmMain = this;

		jmMain.add(getFileMenu(proteoSuiteView, jcbTechnique, jmSaveProject,
				jmCloseProject, jtpViewer, jbSaveProject, jcbOutputFormat,				
				jlFileNameMzMLText, jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText,
				jcbPSM, jlRawFilesStatus,
				aMzMLUnmarshaller,
				aMzIDUnmarshaller,
				aMzQUnmarshaller));

		jmMain.add(getEditMenu());
		
		
		jmMain.add(getAnalyzeMenu(proteoSuiteView, jcbTechnique, jmSaveProject,
				jmCloseProject, jbSaveProject, jcbOutputFormat,
				jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText, jcbPSM,
				jlRawFilesStatus, identParamsExecute, aMzIDUnmarshaller));

		jmMain.add(getToolsMenu(proteoSuiteView));

		jmMain.add(getDatabasesMenu());

		jmMain.add(getWindowMenu(jtpViewer));

		jmMain.add(getCustomHelpMenu());
	}

	private JMenu getFileMenu(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject,
			final JComboBox<String> jcbOutputFormat,			
			final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		JMenuItem jmNewProject = new JMenuItem("New Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/new.gif")));
		jmNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));		

		JMenuItem jmImportFile = new JMenuItem("Import File", new ImageIcon(
				getClass().getClassLoader().getResource("images/import.gif")));
		jmImportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_MASK));
		

		JMenuItem jmOpenProject = new JMenuItem("Open Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/open.gif")));
		jmOpenProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));		

		JMenuItem jmOpenRecentProject = new JMenuItem("Open Recent Project");
		jmOpenRecentProject.setEnabled(false);

		jmCloseProject.setEnabled(false);		

		jmSaveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		jmSaveProject.setEnabled(false);		

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

	private JMenu getAnalyzeMenu(final ProteoSuiteView proteoSuiteView,
			final JComboBox<String> jcbTechnique,
			final JMenuItem jmSaveProject, final JMenuItem jmCloseProject,
			final JButton jbSaveProject,
			final JComboBox<String> jcbOutputFormat,			
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			final IdentParamsView identParamsExecute,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller) {
		JMenu jmAnalyze = new JMenu("Analyze");

		JMenuItem jmRunIdentAnalysis = new JMenuItem(
				"Run Identification Analysis", new ImageIcon(getClass()
						.getClassLoader().getResource("images/runid.gif")));
		jmRunIdentAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, 0));
		
		jmAnalyze.add(jmRunIdentAnalysis);

		JMenuItem jmRunQuantAnalysis = new JMenuItem("Run Quantition Analysis",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/run.gif")));
		jmRunQuantAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F5, 0));
		
		jmAnalyze.add(jmRunQuantAnalysis);

		return jmAnalyze;
	}

	private JMenu getToolsMenu(ProteoSuiteView proteoSuiteView) {
		JMenu jmTools = new JMenu("Tools");
		JMenu jmConverters = new JMenu("Converters");

		JMenuItem jmMzML2MGF = new JMenuItem("mzML to MGF");
		
		jmMzML2MGF.setVisible(false);
		jmConverters.add(jmMzML2MGF);

		JMenuItem jmMaxQ2MZQ = new JMenuItem("MaxQuant to mzQuantML");
		
		jmConverters.add(jmMaxQ2MZQ);

		JMenuItem jmProgenesis2MZQ = new JMenuItem("Progenesis to mzQuantML");
		
		jmConverters.add(jmProgenesis2MZQ);

		jmTools.add(jmConverters);

		final JMenuItem jMzMLCompressed = new JMenuItem("Compress MzML file");
		
		jMzMLCompressed.setVisible(false);
		jmTools.add(jMzMLCompressed);

		JMenuItem jmCustomize = new JMenuItem("Customize");
		jmCustomize.setEnabled(false);
		jmTools.add(jmCustomize);

		JMenuItem jmOptions = new JMenuItem("Options");
		
		jmTools.add(jmOptions);

		return jmTools;
	}

	private JMenu getDatabasesMenu() {
		JMenu jmDatabases = new JMenu("Databases");

		JMenuItem jmSubmitPRIDE = new JMenuItem("Submit to PRIDE");		
		jmDatabases.add(jmSubmitPRIDE);

		return jmDatabases;
	}

	private JMenu getWindowMenu(JTabbedPane jtpViewer) {
		JMenu jmWindow = new JMenu("Window");
		final JMenuItem jm2DView = new JMenuItem("2D View");
		final JMenuItem jm3DView = new JMenuItem("3D View");
		final JMenuItem jmTIC = new JMenuItem("TIC");

		final JMenuItem jmSpectrum = new JMenuItem("Spectrum", new ImageIcon(
				getClass().getClassLoader().getResource("images/thick.gif")));

		
		jmWindow.add(jmSpectrum);

		
		jmWindow.add(jmTIC);

		
		jmWindow.add(jm2DView);

		
		jmWindow.add(jm3DView);

		jmWindow.add(new JPopupMenu.Separator());
		final JMenuItem jmMGFView = new JMenuItem("MGF View");
		final JMenuItem jmMascotXMLView = new JMenuItem("Mascot XML View");
		final JMenuItem jmMzIdentMLView = new JMenuItem("mzIdentML View");

		final JMenuItem jmMzMLView = new JMenuItem("mzML View", new ImageIcon(
				getClass().getClassLoader().getResource("images/thick.gif")));
		final JMenuItem jmMzQuantMLView = new JMenuItem("mzQuantML View");
		final JMenuItem jmRawData = new JMenuItem("Raw Data");

		
		jmWindow.add(jmMzMLView);

		
		jmWindow.add(jmMGFView);

		
		jmWindow.add(jmMzIdentMLView);

		
		jmWindow.add(jmMascotXMLView);

		
		jmWindow.add(jmMzQuantMLView);
		jmWindow.add(new JPopupMenu.Separator());

		final JMenuItem jmLog = new JMenuItem("Log", new ImageIcon(getClass()
				.getClassLoader().getResource("images/thick.gif")));
		
		jmWindow.add(jmLog);

		
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
		

		jmHelp.add(jmHelpContent);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmContactUs = new JMenuItem("Contact us");
		jmContactUs
				.setToolTipText("Click here for contacting our group and collaborators");
		

		jmHelp.add(jmContactUs);

		JMenuItem jmCheckUpdates = new JMenuItem("Check for updates");
		
		jmHelp.add(jmCheckUpdates);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmAbout = new JMenuItem("About ProteoSuite");
		
		jmHelp.add(jmAbout);

		return jmHelp;
	}
}
