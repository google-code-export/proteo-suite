/**
 * --------------------------------------------------------------------------
 * ProteoSuiteView.java
 * --------------------------------------------------------------------------
 * Description:       ProteoSuite Graphical Unit Interface
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite;

import edu.ucsd.msjava.mzml.MzMLAdapter;
import edu.ucsd.msjava.params.ParamManager;
import edu.ucsd.msjava.ui.MSGFPlus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.validation.Validator;

import org.proteosuite.fileformat.FileFormatMGF;
import org.proteosuite.fileformat.FileFormatMascot;
import org.proteosuite.fileformat.FileFormatMzIdentML;
import org.proteosuite.fileformat.FileFormatMzML;
import org.proteosuite.fileformat.FileFormatMzQuantML;
import org.proteosuite.gui.*;
import org.proteosuite.gui.tables.*;
import org.proteosuite.listener.*;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;
import org.proteosuite.utils.Unmarshaller;

import uk.ac.cranfield.xTracker.*;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;
import uk.ac.liv.mzidlib.MzIdentMLLib;

/**
 * This class corresponds to the main form in ProteoSuite. The form includes the
 * visualisation of data and other associated tools.
 * 
 * @author fgonzalez
 */
public class ProteoSuiteView extends JFrame {
	// Project settings
	public static final String PROTEOSUITE_VERSION = "0.3.3 ALPHA";
	public static String sProjectName = "";
	public static String sPreviousLocation = "user.home";
	public boolean isProjectModified = false;
	public static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
	public static final String mzMLVersion = "1.1";
	public static final String mzIDVersion = "1.1";
	public static final String MZQUANT_VERSION = "1.0.0";
	public static final String PSI_MS_VERSION = "3.37.0";
	public static final String PSI_MOD_VERSION = "1.2";
	public static final SystemUtils SYS_UTILS = new SystemUtils();

	private static final WorkSpace WORKSPACE = WorkSpace.getInstance();

	private IdentParamsView identParamsExecute;

	// List of unmarshaller objects
	private List<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
	private List<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
	private List<MzQuantMLUnmarshaller> aMzQUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();

	public ProteoSuiteView() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ActionListenerExit());

		// Load parameter settings
		identParamsExecute = new IdentParamsView("execute");

		// Initialising all components
		final JMenuItem jmCloseProject = new JMenuItem("Close Project");
		final JMenuItem jmSaveProject = new JMenuItem("Save Project",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/save.gif")));

		final JButton jbSaveProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/save.gif")));
		final JComboBox<String> jcbTechnique = new JComboBox<String>();
		final JEditorPane jepMGFView = new JEditorPane();
		final JEditorPane jepMZQView = new JEditorPane();
		final JEditorPane jepMascotXMLView = new JEditorPane();
		final JEditorPane jepMzIDView = new JEditorPane();
		final JEditorPane jepMzMLView = new JEditorPane();
		final JLabel jlFileNameMzIDText = new JLabel();
		final JLabel jlFileNameMzMLText = new JLabel();
		final JLabel jlFileNameMzQText = new JLabel();
		final JLabel jlIdentFilesStatus = new JLabel();

		final JTableRawData jtRawData = new JTableRawData();
		final JTable jtFeatureQuant = new JTableFeatureQuant();
		final JTable jtIdentFiles = new JTableIdentFiles();
		final JTable jtMGF = new JTableMGF();
		final JTable jtMascotXMLView = new JTableMascotXmlView();
		final JTable jtMzId = new JTableMzId();
		final JTable jtMzML = new JTableMzML();
		final JTable jtPeptideQuant = new JTablePeptideQuant();
		final JTable jtProteinQuant = new JTableProteinQuant();
		final JTable jtQuantFiles = new JTableQuantFiles();
		final JTable jtRawFiles = new JTableRawFiles();

		final TabbedChartViewer jtpViewer = new TabbedChartViewer();
		final TabbedLog jtpLog = new TabbedLog(jtRawData);

		final JLabel jlFileNameMGFText = new JLabel();
		final JTable jtMzIDProtGroup = new JTable();
		final JComboBox<String> jcbPSM = new JComboBox<String>();
		jcbPSM.setModel(new DefaultComboBoxModel<String>(new String[] { "<=1",
				"<=2", "<=3", "All" }));

		final TabbedProperties jtpProperties = new TabbedProperties(
				jlFileNameMzMLText, jepMzMLView, jtMzML, jlFileNameMGFText,
				jepMGFView, jtMGF, jtMzId, jlFileNameMzIDText, jcbPSM,
				jtMzIDProtGroup, jepMzIDView, jepMascotXMLView,
				jtMascotXMLView, jlFileNameMzQText, jepMZQView, jtPeptideQuant,
				jtProteinQuant, jtFeatureQuant);

		initComponents(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jtpProperties, jlFileNameMGFText,
				jtMzIDProtGroup, jcbPSM);

		// Project default settings
		initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jtpProperties);

		updateTitle();

		// Setting project icons
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage());

		// Check Java version and architecture
		System.out.println("****************************************");
		System.out.println("*****    P R O T E O S U I T E    ******");
		System.out.println("****************************************");
		SYS_UTILS.checkMemory("starting up");
		System.out.println("Java version: "
				+ System.getProperty("java.version"));
		System.out.println("Architecture: "
				+ System.getProperty("sun.arch.data.model") + "-bit");
		System.out.println("Classpath: "
				+ System.getProperty("java.class.path"));
		System.out.println("****************************************");

		// Setting Window Height and Width
		setMinimumSize(new Dimension(1024, 768));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Configuring exit events
		pack();
	}

	private void initComponents(final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject, final TabbedLog jtpLog,
			final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
			final JComboBox<String> jcbTechnique, final JEditorPane jepMGFView,
			final JEditorPane jepMZQView, final JEditorPane jepMascotXMLView,
			final JEditorPane jepMzIDView, final JEditorPane jepMzMLView,
			final JLabel jlFileNameMzMLText, final JLabel jlFileNameMzIDText,
			final JLabel jlIdentFilesStatus, final JLabel jlFileNameMzQText,
			final JTable jtRawFiles, final JTable jtIdentFiles,
			final JTable jtQuantFiles, final JTableRawData jtRawData,
			final JTable jtMzML, final JTable jtMGF, final JTable jtMzId,
			final JTable jtMascotXMLView, final JTable jtPeptideQuant,
			final JTable jtProteinQuant, final JTable jtFeatureQuant,
			final TabbedProperties jtpProperties,
			final JLabel jlFileNameMGFText, final JTable jtMzIDProtGroup,
			final JComboBox<String> jcbPSM) {

		jcbTechnique.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Select technique", "iTRAQ", "TMT", "emPAI" }));
		jcbTechnique.setToolTipText("Select a technique for the analysis");
		jcbTechnique.setBorder(BorderFactory.createEtchedBorder());

		jepMzMLView.setContentType("text/html");
		jepMzMLView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMzMLView.setPreferredSize(new Dimension(144, 84));

		jepMGFView.setContentType("text/html");
		jepMGFView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMGFView.setPreferredSize(new Dimension(144, 84));

		jtRawFiles.addMouseListener(new MouseListenerRawFiles(this,
				jtpProperties, jepMzMLView, jlFileNameMzMLText, jtMzML,
				aMzMLUnmarshaller, jlFileNameMGFText, jtMGF, jtpViewer));

		jtIdentFiles.addMouseListener(new MouseListenerIdentFiles(this,
				jtMascotXMLView, jtpProperties, jepMzIDView, jtMzIDProtGroup,
				jcbPSM, jtMzId, jlFileNameMzIDText));

		jtMzML.addMouseListener(new MouseListenerMzMl(jtRawFiles, jtRawData,
				jtpLog, aMzMLUnmarshaller, jtpViewer));
		jtMGF.addMouseListener(new MouseListenerMgf(this, jtRawFiles,
				jtRawData, jtpLog, jtpViewer));
		jcbPSM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Check file to visualize
				if (jlFileNameMzIDText.equals(""))
					return;

				int iIndex = getMzMLIndex(jlFileNameMzIDText.getText(),
						jtIdentFiles);
				loadMzIdentMLView(iIndex, jlFileNameMzIDText.getText(),
						jtMzIDProtGroup, jtpProperties, jcbPSM, jepMzIDView,
						jlFileNameMzIDText, jtMzId);
			}

			private int getMzMLIndex(String sFileName, JTable jtIdentFiles) {
				int iIndex = 0;
				for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
					if (jtIdentFiles.getValueAt(iI, 0).toString()
							.equals(sFileName)) {
						iIndex = iI;
						break;
					}
				}
				return iIndex;
			}
		});

		jtMzIDProtGroup.setAutoCreateRowSorter(true);
		jtMzIDProtGroup.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "ID", "Name", "Protein Accesions",
				"Representative Protein", "Scores", "P-values",
				"Number of peptides", "Is Decoy", "passThreshold" }));

		jepMzIDView.setContentType("text/html");
		jepMzIDView.setPreferredSize(new Dimension(144, 84));

		jepMascotXMLView.setContentType("text/html");
		jepMascotXMLView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMascotXMLView.setPreferredSize(new Dimension(144, 84));

		jtQuantFiles
				.addMouseListener(new MouseListenerQuantFiles(this,
						jtFeatureQuant, jtpLog, jtpProperties, jepMZQView,
						jlFileNameMzQText, jtPeptideQuant, jtProteinQuant,
						jtQuantFiles));

		jepMZQView.setContentType("text/html");
		jepMZQView.setPreferredSize(new Dimension(144, 84));

		final JComboBox<String> jcbOutputFormat = new JComboBox<String>();
		jcbOutputFormat.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Select format", "mzq" }));
		jcbOutputFormat.setToolTipText("Select an output format");

		final JLabel jlRawFilesStatus = new JLabel(new ImageIcon(getClass()
				.getClassLoader().getResource("images/empty.gif")));
		jlRawFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlRawFilesStatus.setForeground(new Color(51, 102, 0));

		jlIdentFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlIdentFilesStatus.setForeground(new Color(51, 102, 0));

		final MainPanel jpMainPanelView = new MainPanel(jtQuantFiles,
				jtIdentFiles, jtRawFiles, jtpViewer, jtpLog, jtpProperties);

		setJMenuBar(new MenuBar(this, jcbTechnique, jmSaveProject,
				jmCloseProject, jtpLog, jtpViewer, jbSaveProject,
				jcbOutputFormat, jepMGFView, jepMZQView, jepMascotXMLView,
				jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jlFileNameMGFText, jtMzIDProtGroup,
				jtpProperties, jcbPSM, jlRawFilesStatus, jpMainPanelView,
				identParamsExecute, aMzMLUnmarshaller, aMzIDUnmarshaller,
				aMzQUnmarshaller));

		add(new HeaderPanel(this, jmCloseProject, jmSaveProject, jtpProperties,
				jtpLog, jtpViewer, jbSaveProject, jcbTechnique, jepMGFView,
				jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
				jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
				jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
				jtRawData, jtMzML, jtMGF, jtMzId, jtMascotXMLView,
				jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
				jcbOutputFormat, jlFileNameMGFText, jtMzIDProtGroup, jcbPSM,
				jlRawFilesStatus, identParamsExecute, aMzMLUnmarshaller,
				aMzIDUnmarshaller, aMzQUnmarshaller), BorderLayout.PAGE_START);

		add(jpMainPanelView, BorderLayout.CENTER);

		pack();
	}

	public void jmNewProjectActionPerformed(JComboBox<String> jcbTechnique,
			JMenuItem jmSaveProject, JMenuItem jmCloseProject,
			TabbedLog jtpLog, final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject, JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzQText, JTable jtRawFiles, JTable jtIdentFiles,
			JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF,
			JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant,
			TabbedProperties jtpProperties) {
		// Check if the project needs to be saved
		if (isProjectModified) {
			int iOption = JOptionPane
					.showConfirmDialog(
							this,
							"You have not saved your changes. Do you want to save them now?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
			if (iOption == JOptionPane.OK_OPTION) {
				jmSaveProjectActionPerformed(jtRawFiles, jcbTechnique,
						jmSaveProject, jmCloseProject, jbSaveProject,
						jcbOutputFormat, jtIdentFiles);
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jcbTechnique, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jcbTechnique, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			}
		} else {
			initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
					jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
					jepMascotXMLView, jepMzIDView, jepMzMLView,
					jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
					jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
					jtRawData, jtMzML, jtMGF, jtMzId, jtMascotXMLView,
					jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
					jtpProperties);
		}
	}

	public void jmSaveProjectActionPerformed(JTable jtRawFiles,
			JComboBox<String> jcbTechnique, JMenuItem jmSaveProject,
			JMenuItem jmCloseProject, JButton jbSaveProject,
			JComboBox<String> jcbOutputFormat, JTable jtIdentFiles) {
		// Save data using a mzq file
		boolean isOK = saveProject(jtRawFiles, jcbTechnique, jmSaveProject,
				jmCloseProject, jbSaveProject, jcbOutputFormat, jtIdentFiles);
		if (isOK) {
			JOptionPane.showMessageDialog(this,
					"Your file was saved successfully!", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void jmCloseProjectActionPerformed(JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, TabbedLog jtpLog,
			final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
			JComboBox<String> jcbTechnique, JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, JLabel jlFileNameMzQText,
			JLabel jlIdentFilesStatus, JLabel jlFileNameMzIDText,
			JLabel jlFileNameMzMLText, JTable jtRawFiles, JTable jtIdentFiles,
			JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF,
			JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant,
			TabbedProperties jtpProperties) {
		// Check if the project needs to be saved
		if (isProjectModified) {
			int iOption = JOptionPane
					.showConfirmDialog(
							this,
							"You have not saved your changes. Do you want to save them now?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
			if (iOption == JOptionPane.OK_OPTION) {
				jmSaveProjectActionPerformed(jtRawFiles, jcbTechnique,
						jmSaveProject, jmCloseProject, jbSaveProject,
						jcbOutputFormat, jtIdentFiles);
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jcbTechnique, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jcbTechnique, jepMGFView,
						jepMZQView, jepMascotXMLView, jepMzIDView, jepMzMLView,
						jlFileNameMzMLText, jlFileNameMzIDText,
						jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant, jtpProperties);
			}
		} else {
			initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
					jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
					jepMascotXMLView, jepMzIDView, jepMzMLView,
					jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
					jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
					jtRawData, jtMzML, jtMGF, jtMzId, jtMascotXMLView,
					jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
					jtpProperties);
		}
	}

	public void jmRunQuantAnalysisActionPerformed(final TabbedLog jtpLog,
			final JMenuItem jmCloseProject, final JMenuItem jmSaveProject,
			final TabbedProperties jtpProperties, final JButton jbSaveProject,
			final JComboBox<String> jcbTechnique,
			final JComboBox<String> jcbOutputFormat,
			final JEditorPane jepMZQView, final JLabel jlFileNameMzQText,
			final JTable jtFeatureQuant, final JTable jtIdentFiles,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtQuantFiles, final JTable jtRawFiles) {
		// Check if there is a valid workspace
		boolean isOK = WORKSPACE.isValidWorkSpace();
		if (isOK) {
			// ... Validate if we got raw and identification files ...//
			if (jtRawFiles.getRowCount() <= 0) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please select at least one raw file. (Use the import file option in the menu).",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else if (jtIdentFiles.getRowCount() <= 0) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please select at least one identification file. (Use the import file option in the menu).",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else if (jcbTechnique.getSelectedItem()
					.equals("Select technique")) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please specify the technique used in your pipeline (e.g. iTRAQ, TMT, etc.)",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else if (jcbOutputFormat.getSelectedItem()
					.equals("Select format")) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please specify the output format (e.g. .mzq, .csv, etc.)",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				isOK = checkMapping(jtpLog, jtIdentFiles);
				if (isOK) {
					// ... In this thread, we call xTracker module (parameter
					// files are generated using proteosuite) ...//
					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "RunPipeline");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// ... Progress Bar ...//
							if ((jcbTechnique.getSelectedItem().toString().equals("iTRAQ"))
									|| (jcbTechnique.getSelectedItem()
											.toString().equals("TMT"))
									|| (jcbTechnique.getSelectedItem()
											.toString().equals("emPAI"))) {
								progressBarDialog.setTitle("Running Quantitation Analysis via x-Tracker");
								progressBarDialog.setVisible(true);
							}
						}
					}, "ProgressBarDialog");
					thread.start();
					new Thread("RunPipeline") {
						@Override
						public void run() {
							boolean isOK = false;
							if (jcbTechnique.getSelectedItem().toString()
									.equals("Label free")) {
								// Label free will be performed in proteosuite

								progressBarDialog.setVisible(false);
								progressBarDialog.dispose();
								JOptionPane
										.showMessageDialog(
												ProteoSuiteView.this,
												"SILAC, Label Free and 15N are under development. Please stay tuned for new releases.",
												"Information",
												JOptionPane.INFORMATION_MESSAGE);
							} else {
								if ((jcbTechnique.getSelectedItem().toString()
										.equals("iTRAQ"))
										|| (jcbTechnique.getSelectedItem()
												.toString().equals("TMT"))
										|| (jcbTechnique.getSelectedItem()
												.toString().equals("emPAI"))) {
									// ... Label free will be performed in
									// proteosuite ...//
									// ... Generate config files for xTracker
									// ...//
									isOK = generateFiles(jtRawFiles, jtpLog,
											jcbTechnique.getSelectedItem()
													.toString(),
											jcbOutputFormat, jtIdentFiles,
											jtQuantFiles);
									if (isOK) {
										// ... Run xTracker ...//
										System.out
												.println(SYS_UTILS.getTime()
														+ " - ****** Running x-Tracker *****");
										jtpLog.appendLog("\n"
												+ SYS_UTILS.getTime()
												+ " - ****** Running x-Tracker *****");
										new xTracker(WORKSPACE.getWorkSpace()
												.replace("\\", "/")
												+ "/"
												+ sProjectName, WORKSPACE
												.getWorkSpace().replace("\\",
														"/"));

										// ... Load the mzQuantML file into
										// memory (With results included) ...//
										File xmlFile = new File(
												WORKSPACE.getWorkSpace() + "/"
														+ sProjectName);
										aMzQUnmarshaller.clear();
										final DefaultTableModel model = new DefaultTableModel();
										jtQuantFiles.setModel(model);
										model.addColumn("Name");
										model.addColumn("Path");
										model.addColumn("Type");
										model.addColumn("Version");
										try {
											aMzQUnmarshaller.add(Unmarshaller
													.unmarshalMzQMLFile(model,
															xmlFile));
											System.out
													.println(SYS_UTILS
															.getTime()
															+ " - Displaying mzquantml results...");
											jtpLog.appendLog("\n"
													+ SYS_UTILS.getTime()
													+ " - Displaying mzquantml results...");
											jlFileNameMzQText.setText(WORKSPACE
													.getWorkSpace()
													+ "/"
													+ sProjectName); // ...
																		// Display
																		// of
																		// the
																		// results
																		// ...//
											// loadMzQuantMLView(0,
											// sProjectName);
											loadMzQuantMLView(0,
													WORKSPACE.getWorkSpace()
															+ "/"
															+ sProjectName,
													jtFeatureQuant, jtpLog,
													jtpProperties, jepMZQView,
													jlFileNameMzQText,
													jtPeptideQuant,
													jtProteinQuant,
													jtQuantFiles);
											System.out
													.println(SYS_UTILS
															.getTime()
															+ " - Execution finished...");
											jtpLog.appendLog("\n"
													+ SYS_UTILS.getTime()
													+ " - Execution finished...");

											jtpProperties
													.setExportPepMZQExcel(true);
											jtpProperties
													.setExportProtMZQExcel(true);
											jtpProperties
													.setExportFeatMZQExcel(true);

											JOptionPane
													.showMessageDialog(
															ProteoSuiteView.this,
															"Process finished. Quantitation results can be viewed in the mzQuantML View.",
															"Information",
															JOptionPane.INFORMATION_MESSAGE);

										} catch (Exception e) {
											jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
											jtpLog.appendLog(e.getMessage());
											e.printStackTrace();
										}

									}
									progressBarDialog.setVisible(false);
									progressBarDialog.dispose();
									updateTitle();
									isProjectModified = false;
									updateSaveProjectStatus(jmSaveProject,
											jbSaveProject);
									updateCloseProjectStatus(jmCloseProject);
								} else {
									progressBarDialog.setVisible(false);
									progressBarDialog.dispose();
									JOptionPane
											.showMessageDialog(
													ProteoSuiteView.this,
													"SILAC, Label Free and 15N are under development. Please stay tuned for new releases.",
													"Information",
													JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					}.start();
				}
			}
		}
	}

	public void jmRunIdentAnalysisActionPerformed(final JTable jtRawFiles,
			final JTable jtMzIDProtGroup,
			final IdentParamsView identParamsExecute, final TabbedLog jtpLog,
			final JTable jtIdentFiles,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final JLabel jlRawFilesStatus, final JLabel jlIdentFilesStatus,
			final JTabbedPane jtpProperties, final JComboBox<String> jcbPSM,
			final JEditorPane jepMzIDView, final JLabel jlFileNameMzIDText,
			final JTable jtMzId) {

		// Check if there is a valid workspace
		boolean bRun = false;
		boolean isOK = WORKSPACE.isValidWorkSpace();
		// sWorkspace = "C:/temp/proteosuite";

		if (isOK) {
			// Validate if we got raw and identification files
			if (jtRawFiles.getRowCount() <= 0) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please select at least one raw file. (Use the import file option in the menu).",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				final JDialog dialogIdentParamsExecute = new JDialog(this,
						"Set Identification Parameters",
						ModalityType.APPLICATION_MODAL);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
						KeyEvent.VK_ESCAPE, 0, false);
				final Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						identParamsExecute.setRun(false);
						dialogIdentParamsExecute.dispose();
					}
				};
				dialogIdentParamsExecute.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						escapeAction.actionPerformed(null);
					}
				});

				dialogIdentParamsExecute.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				dialogIdentParamsExecute.getRootPane().getActionMap()
						.put("ESCAPE", escapeAction);
				dialogIdentParamsExecute.getContentPane().add(
						identParamsExecute);
				dialogIdentParamsExecute.pack();
				dialogIdentParamsExecute.setLocationRelativeTo(null);

				dialogIdentParamsExecute.setVisible(true);

				// ... Get values from form ...//
				bRun = identParamsExecute.getRun();
				if (bRun) {
					// ... Start process ...//

					// ... In this thread, we call MSGF module (parameter files
					// are generated using proteosuite) ...//
					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "RunIdentification");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// ... Progress Bar ...//
							progressBarDialog.setTitle("Running Identification Analysis via MSGF+");
							progressBarDialog
									.setTaskName("This task may take up to several minutes. Please wait...");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();

					new Thread("RunIdentification") {
						@Override
						public void run() {
							System.out.println(SYS_UTILS.getTime()
									+ " - ****** Running MSGF+ (Ver "
									+ MSGFPlus.VERSION + ") *****");
							jtpLog.appendLog("\n" + SYS_UTILS.getTime()
									+ " - ****** Running MSGF+ (Ver "
									+ MSGFPlus.VERSION + ") *****");

							ParamManager paramManager = new ParamManager(
									"MS-GF+", MSGFPlus.VERSION,
									MSGFPlus.RELEASE_DATE, "java -Xmx"
											+ SYS_UTILS.getMaxMemory()
											+ "M -jar MSGFPlus.jar");
							paramManager.addMSGFPlusParams();

							Map<String, String> map = new HashMap<String, String>();
							map = identParamsExecute.getParams();

							boolean bProteinInference = identParamsExecute
									.getProteinInference();
							String sRegex = identParamsExecute.getRegex();
							String sFirstFile = "";

							for (int iFile = 0; iFile < jtRawFiles
									.getRowCount(); iFile++) {
								progressBarDialog
										.setTitle("Running MSGF+ (Ver "
												+ MSGFPlus.VERSION
												+ ") "
												+ "("
												+ (iFile + 1)
												+ "/"
												+ jtRawFiles.getRowCount()
												+ ") - "
												+ jtRawFiles.getValueAt(iFile,
														0).toString());
								map.put("-s", jtRawFiles.getValueAt(iFile, 1)
										.toString());
								File file = new File(jtRawFiles.getValueAt(
										iFile, 1).toString());
								String fileName = file.getName();
								fileName = fileName.replace("\\", "/");
								String fileExtension = "";
								String Path = "";
								int iIndex = fileName.lastIndexOf('.');
								if (iIndex > 0) {
									fileExtension = fileName.substring(iIndex);
								}
								fileName = fileName.replace(fileExtension,
										"_msgfplus.mzid");
								map.put("-o",
										file.getParent().replace("\\", "/")
												+ "/" + fileName);
								System.out.println(file.getParent().replace(
										"\\", "/")
										+ "/" + fileName);
								Path = file.getParent().replace("\\", "/")
										+ "/" + fileName;
								if (iFile == 0) {
									sFirstFile = file.getParent().replace("\\",
											"/")
											+ "/" + fileName;
								}
								String[] argv = new String[map.size() * 2];
								int iI = 0;
								for (Entry<String, String> entry : map
										.entrySet()) {
									argv[iI] = entry.getKey();
									System.out.print(" " + argv[iI]);
									argv[iI + 1] = entry.getValue();
									System.out.print(" " + argv[iI + 1]);
									iI = iI + 2;
								}

								if (argv.length == 0) {
									paramManager.printUsageInfo();
									return;
								}
								MzMLAdapter.turnOffLogs();

								// ... Parse parameters ...//
								String errMessage = paramManager
										.parseParams(argv);
								if (errMessage != null) {
									System.err.println(SYS_UTILS.getTime()
											+ " - [Error] " + errMessage);
									System.out.println();
									paramManager.printUsageInfo();
									return;
								}

								// ... Running MS-GF+ ...//
								paramManager.printToolInfo();
								// MSGFPlus run = new MSGFPlus();
								String errorMessage = null;
								try {
									errorMessage = MSGFPlus
											.runMSGFPlus(paramManager);
								} catch (Exception e) {
									e.printStackTrace();
									System.exit(-1);
								}
								if (errorMessage != null) {
									System.err.println(SYS_UTILS.getTime()
											+ " - [Error] " + errorMessage);
									jtpLog.appendLog("\n" + SYS_UTILS.getTime()
											+ " - [Error] " + errorMessage);
								} else {
									System.out.println(SYS_UTILS.getTime()
											+ " - MS-GF+ completed");
									jtpLog.appendLog("\n" + SYS_UTILS.getTime()
											+ " - MS-GF+ completed");
								}
								if (bProteinInference) {
									System.out
											.println(SYS_UTILS.getTime()
													+ " - Performing protein inference");
									jtpLog.appendLog("\n" + SYS_UTILS.getTime()
											+ " - Performing protein inference");

									MzIdentMLLib mzlib = new MzIdentMLLib();
									String[] args = { "FalseDiscoveryRate",
											Path,
											Path.replace(".mzid", "_fdr.mzid"),
											"-decoyRegex", sRegex,
											"-decoyValue", "1", "-cvTerm",
											"\"MS:1002053\"",
											"-betterScoresAreLower", "true",
											"-compress", "false" };
									try {
										System.out.println(SYS_UTILS.getTime()
												+ " - Performing FDR");
										jtpLog.appendLog("\n"
												+ SYS_UTILS.getTime()
												+ " - Performing FDR");
										mzlib.init(args);
										System.out.println(SYS_UTILS.getTime()
												+ " - Setting Threshold");
										jtpLog.appendLog("\n"
												+ SYS_UTILS.getTime()
												+ " - Setting Threshold");
										String[] args2 = {
												"Threshold",
												Path.replace(".mzid",
														"_fdr.mzid"),
												Path.replace(".mzid",
														"_fdr_thresh.mzid"),
												"-isPSMThreshold",
												"true",
												"-cvAccessionForScoreThreshold",
												"MS:1001874", "-threshValue",
												"0.01",
												"-betterScoresAreLower",
												"true",
												"-deleteUnderThreshold",
												"false", "-compress", "false" };
										mzlib.init(args2);
										System.out.println(SYS_UTILS.getTime()
												+ " - Grouping proteins");
										jtpLog.appendLog("\n"
												+ SYS_UTILS.getTime()
												+ " - Grouping proteins");
										sFirstFile = Path.replace(".mzid",
												"_fdr_thresh_group.mzid");
										String[] args3 = {
												"ProteoGrouper",
												Path.replace(".mzid",
														"_fdr_thresh.mzid"),
												sFirstFile,
												"-requireSIIsToPassThreshold",
												"true", "-verboseOutput",
												"false", "-cvAccForSIIScore",
												"MS:1001874", "-logTransScore",
												"true", "-compress", "false" };
										System.out
												.print("Command line arguments for proteogrouper:");

										mzlib.init(args3);

									} catch (Exception ex) {
										Logger.getLogger(
												ProteoSuiteView.class.getName())
												.log(Level.SEVERE, null, ex);
									}
								}
							}
							if (!sFirstFile.equals("")) {
								final DefaultTableModel model = new DefaultTableModel();
								jtIdentFiles.setModel(model);
								model.addColumn("Name");
								model.addColumn("Path");
								model.addColumn("Type");
								model.addColumn("Version");
								model.addColumn("Raw File");
								File xmlFile = new File(sFirstFile);

								aMzIDUnmarshaller.add(Unmarshaller.unmarshalMzIDFile(model, xmlFile,
										""));
								loadMzIdentMLView(0, xmlFile.getName(),
										jtMzIDProtGroup, jtpProperties, jcbPSM,
										jepMzIDView, jlFileNameMzIDText, jtMzId);
								renderIdentFiles(jtRawFiles, jtIdentFiles);
								updateStatusPipeline(jlRawFilesStatus,
										jtRawFiles, jlIdentFilesStatus,
										jtIdentFiles);
							}
							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
						}
					}.start();
				}
			}
		}
	}

	/**
	 * Mapping identification files with the corresponding raw file
	 * 
	 * @param void
	 * @return void
	 */
	public void renderIdentFiles(JTable jtRawFiles, JTable jtIdentFiles) {
		// Rendering
		JComboBox<String> combo = new JComboBox<String>();
		for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
			combo.addItem(jtRawFiles.getValueAt(iI, 0).toString());
		}
		TableColumn column = jtIdentFiles.getColumnModel().getColumn(4);
		column.setCellEditor(new DefaultCellEditor(combo));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Please specify the associated raw file");
		column.setCellRenderer(renderer);
		DefaultTableModel model = (DefaultTableModel) jtIdentFiles.getModel();
		model.fireTableDataChanged();
	}

	/**
	 * Check if all identification files are associated to one raw file
	 * 
	 * @param void
	 * @return true/false
	 */
	private boolean checkMapping(TabbedLog jtpLog, JTable jtIdentFiles) {
		// Check if all identification files have been assigned to one rawfile
		System.out.println(SYS_UTILS.getTime()
				+ " - Checking identification-spectra mapping");
		jtpLog.appendLog(SYS_UTILS.getTime()
				+ " - Checking identification-spectra mapping");
		jtpLog.setSelectedIndex(0);
		for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
			if (jtIdentFiles.getValueAt(iI, 4).equals("")) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please select a raw file for each identification file.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Update save icon
	 * 
	 * @param void
	 * @return void
	 */
	public void updateSaveProjectStatus(final JMenuItem jmSaveProject,
			final JButton jbSaveProject) {
		jmSaveProject.setEnabled(isProjectModified);
		jbSaveProject.setEnabled(isProjectModified);
	}

	/**
	 * Update close project icon
	 * 
	 * @param void
	 * @return void
	 */
	private void updateCloseProjectStatus(final JMenuItem jmCloseProject) {
		if (sProjectName.equals("New"))
			jmCloseProject.setEnabled(false);
		else
			jmCloseProject.setEnabled(true);
	}

	/**
	 * Save a project using a mzq file
	 * 
	 * @param jtIdentFiles
	 * @param void
	 * @return true/false
	 */
	private boolean saveProject(JTable jtRawFiles,
			JComboBox<String> jcbTechnique, JMenuItem jmSaveProject,
			JMenuItem jmCloseProject, JButton jbSaveProject,
			JComboBox<String> jcbOutputFormat, JTable jtIdentFiles) {
		if (jtRawFiles.getRowCount() <= 0) {
			JOptionPane
					.showMessageDialog(
							this,
							"Please select at least one raw file. (Use the import file option in the menu).",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (jtIdentFiles.getRowCount() <= 0) {
			JOptionPane
					.showMessageDialog(
							this,
							"Please select at least one identification file. (Use the import file option in the menu).",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (jcbTechnique.getSelectedItem().equals("Select technique")) {
			JOptionPane
					.showMessageDialog(
							this,
							"Please specify the technique to save your pipeline (e.g. iTRAQ, SILAC, 15N, etc.)",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		JFileChooser chooser = new JFileChooser(sPreviousLocation);
		chooser.setDialogTitle("Please enter the name of your project");

		// Applying file extension filters
		FileNameExtensionFilter filter3 = new FileNameExtensionFilter(
				"mzQuantML Files (*.mzq)", "mzq");
		// Filters must be in descending order
		chooser.addChoosableFileFilter(filter3);

		// Setting default directory
		if (sPreviousLocation == null || sPreviousLocation.contains("")) {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(WORKSPACE.getWorkSpace()));
		} else {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sPreviousLocation));
		}

		int returnVal = chooser.showSaveDialog(this);
		System.out.println(returnVal);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Check extension
			File file = chooser.getSelectedFile();
			int mid = 0;
			mid = file.getName().lastIndexOf(".mzq");
			String fileLocation = "";
			if (mid <= 0) {
				fileLocation = file.getPath() + ".mzq";
			} else {
				fileLocation = file.getPath();
			}
			System.out.println("Creating file=" + fileLocation);
			fileLocation.replace("\\", "/");
			File file2 = new File(fileLocation);

			// Check if file exists
			boolean exists = (new File(file2.getPath())).exists();
			if (exists) {
				int n = JOptionPane
						.showConfirmDialog(
								null,
								"The file already exists in this directory, do you want to overwrite it?",
								"Information", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.NO_OPTION) {
					return false;
				}
			}
			System.out.println("Saving mzq file...");
			boolean bOK = FileFormatMzQuantML.writeMzQuantML(jcbTechnique
					.getSelectedItem().toString(), file2.getName(), jtRawFiles,
					jcbOutputFormat, jtIdentFiles);
			if (bOK) {
				sProjectName = file2.getName();
				updateTitle();
				isProjectModified = false;
				updateSaveProjectStatus(jmSaveProject, jbSaveProject);
				updateCloseProjectStatus(jmCloseProject);
			}
		}

		return true;
	}

	/**
	 * Update window title main project
	 * 
	 * @param void
	 * @return void
	 */
	public void updateTitle() {
		setTitle("ProteoSuite " + PROTEOSUITE_VERSION + " - <Project: "
				+ WORKSPACE.getWorkSpace() + " - " + sProjectName
				+ ">      http://www.proteosuite.org");
	}

	/**
	 * Update status pipeline
	 * 
	 * @param void
	 * @return void
	 */
	public void updateStatusPipeline(JLabel jlRawFilesStatus,
			JTable jtRawFiles, JLabel jlIdentFilesStatus, JTable jtIdentFiles) {
		Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/fill.gif"));

		if (jtRawFiles.getRowCount() > 0)
			jlRawFilesStatus.setIcon(loadRawFilesIcon);

		if (jtIdentFiles.getRowCount() > 0)
			jlIdentFilesStatus.setIcon(loadRawFilesIcon);
	}

	/**
	 * Show raw data from a Mascot Generic Format (MGF) file
	 * 
	 * @param iIndex
	 *            - Index to the raw file
	 * @param sID
	 *            - Scan title
	 * @return void
	 */
	public void showRawDataMGF(int iIndex, String sID, JTable jtRawData,
			JTable jtRawFiles, JTabbedPane jtpLog) {
		DefaultTableModel model = new DefaultTableModel();
		jtRawData.setModel(model);
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");

		// Reading file
		try {
			BufferedReader in = new BufferedReader(new FileReader(jtRawFiles
					.getValueAt(iIndex, 1).toString()));
			long refLine = Long.parseLong(sID);
			String string = null;

			long lineNum = 0;
			int iCount = 0;
			while ((string = in.readLine()) != null) {
				lineNum++;
				string = string.trim();
				if (lineNum == refLine) {
					break;
				}
			}
			while ((string = in.readLine()) != null) {
				if (string.charAt(0) >= '1' && string.charAt(0) <= '9') {
					String[] split = string.split("\\s+");
					double mz = Double.parseDouble(split[0]);
					double intensity = Double.parseDouble(split[1]);
					iCount++;

					model.insertRow(model.getRowCount(), new Object[] { iCount,
							mz, intensity });
					continue;
				}
				if (string.startsWith("END IONS")) {
					break;
				}
			}
			in.close();
		} catch (Exception e) {
			System.exit(1);
		}

		jtpLog.setSelectedIndex(1);
	}

	/**
	 * Load MzIdentML viewer
	 * 
	 * @param iIndex
	 *            Index to the aMzIDUnmarshaller list
	 * @param sFileName
	 *            File name
	 * @param jepMzIDView
	 * @param jlFileNameMzIDText
	 * @param jtMzId
	 * @return void
	 */
	public void loadMzIdentMLView(final int iIndex, final String sFileName,
			JTable jtMzIDProtGroup, final JTabbedPane jtpProperties,
			JComboBox<String> jcbPSM, JEditorPane jepMzIDView,
			JLabel jlFileNameMzIDText, JTable jtMzId) {
		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,
				true, "LoadMzIDView");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// Progress Bar
				progressBarDialog.setTitle("Loading MzIdentML data");
				progressBarDialog.setTaskName("Please wait while loading the data ...");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");
		thread.start();

		Runnable mzIdentML = new FileFormatMzIdentML(jtpProperties,
				jlFileNameMzIDText, sFileName, 
				 jtMzId, jcbPSM, jtMzIDProtGroup, jepMzIDView, 
				 aMzIDUnmarshaller.get(iIndex)
				);
		mzIdentML.run();
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	}

	/**
	 * Display MZQ data
	 * 
	 * @param iIndex
	 *            - Index to the aMzQUnmarshaller list
	 * @param sFile
	 *            - File name
	 * @param jlFileNameMzQText
	 * @param jtPeptideQuant
	 * @param jtProteinQuant
	 * @param jtQuantFiles
	 * @return void
	 */
	public void loadMzQuantMLView(int iIndex, String sFile,
			JTable jtFeatureQuant, TabbedLog jtpLog,
			final TabbedProperties jtpProperties, JEditorPane jepMZQView,
			JLabel jlFileNameMzQText, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtQuantFiles) {
		try {
			if ((!jtQuantFiles.getValueAt(iIndex, 0).toString()
					.equals(jlFileNameMzQText.getText()))
					|| (jtPeptideQuant.getRowCount() <= 0)) {
				final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
						this, true, "LoadMZQView");
				final Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// ... Progress Bar ...//
						progressBarDialog.setTitle("Loading MZQ data");
						progressBarDialog.setVisible(true);
					}
				}, "ProgressBarDialog");
				thread.start();
				FileFormatMzQuantML mzQuantML = new FileFormatMzQuantML(
						jtpProperties,
						jtProteinQuant, jtPeptideQuant, jtFeatureQuant, sFile,
						aMzQUnmarshaller, jlFileNameMzQText, jepMZQView, jtpLog);
				mzQuantML.run();

				progressBarDialog.setVisible(false);
				progressBarDialog.dispose();
			}
		} catch (Exception e) {
			jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
			jtpLog.appendLog(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Loading mzML View
	 * 
	 * @param iIndex
	 *            - index for the aMzMLUnmarshaller list
	 * @param jepMzMLView
	 * @param jlFileNameMzMLText
	 * @param jtMzML
	 * @return void
	 */
	public void loadMzMLView(int iIndex, JTable jtRawFiles,
			final JTabbedPane jtpProperties, JEditorPane jepMzMLView,
			JLabel jlFileNameMzMLText, JTable jtMzML) {

		// Check if not previously loaded
		if (jtRawFiles.getValueAt(iIndex, 0).toString()
				.equals(jlFileNameMzMLText.getText()))
			return;

		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,
				true, "LoadMZMLView");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ... Progress Bar ...//
				progressBarDialog.setTitle("Loading mzML data");
				progressBarDialog.setTaskName("Please wait while loading the data ...");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");
		thread.start();

		Runnable mzML = new FileFormatMzML(jtMzML, jlFileNameMzMLText, jepMzMLView, jtpProperties,
				aMzMLUnmarshaller.get(iIndex));
		mzML.run();
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	}

	/**
	 * Displays MGF raw data
	 * 
	 * @param iIndex index for the array
	 * @param jtMGF
	 * @return void
	 */
	public void loadMGFView(int iIndex, JLabel jlFileNameMGFText,
			JTable jtRawFiles, final JTabbedPane jtpProperties, JTable jtMGF) {
		// Check if not previously loaded
		if (!jtRawFiles.getValueAt(iIndex, 0).toString()
				.equals(jlFileNameMGFText.getText()))
			return;

		final String sFileNameRef = jtRawFiles.getValueAt(iIndex, 0).toString();
		final String sFilePathRef = jtRawFiles.getValueAt(iIndex, 1).toString();

		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,
				true, "LoadMGF");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ... Progress Bar ...//
				progressBarDialog.setTitle("Loading MGF data");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");
		thread.start();
		FileFormatMGF mgf = new FileFormatMGF(jtMGF, jlFileNameMGFText,
				sFileNameRef, jtpProperties, sFilePathRef);
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
		mgf.run();
	}

	/**
	 * Initialise project settings (all modules)
	 * 
	 * @param jlFileNameMzMLText
	 * @param jlFileNameMzIDText
	 * @param jlIdentFilesStatus
	 * @param jlFileNameMzQText
	 * @param jtRawData
	 * @param jtpProperties
	 * @param void
	 * @return void
	 */
	private void initProjectValues(JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, TabbedLog jtpLog,
			final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
			JComboBox<String> jcbTechnique, final JEditorPane jepMGFView,
			final JEditorPane jepMZQView, final JEditorPane jepMascotXMLView,
			final JEditorPane jepMzIDView, final JEditorPane jepMzMLView,
			JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
			JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText,
			JTable jtRawFiles, JTable jtIdentFiles, JTable jtQuantFiles,
			JTable jtRawData, JTable jtMzML, JTable jtMGF, JTable jtMzId,
			JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant,
			TabbedProperties jtpProperties) {
		// Loading values from config file
		// From config file (workspace)
		initSettings(jmCloseProject, jmSaveProject, jbSaveProject);
		// Project pipeline
		initProjectStatusIcons(jcbTechnique, jlIdentFilesStatus);

		// Initialising components
		((JTableDefault) jtRawFiles).reset();
		((JTableDefault) jtIdentFiles).reset();
		((JTableDefault) jtQuantFiles).reset();
		((JTableDefault) jtRawData).reset();
		((JTableDefault) jtMzML).reset();
		((JTableDefault) jtMGF).reset();
		((JTableDefault) jtMzId).reset();
		((JTableDefault) jtMascotXMLView).reset();
		((JTableDefault) jtPeptideQuant).reset();
		((JTableDefault) jtProteinQuant).reset();
		((JTableDefault) jtFeatureQuant).reset();

		initTextAreas(jtpLog, jepMGFView, jepMZQView, jepMascotXMLView,
				jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlFileNameMzQText);
		jtpViewer.reset();

		// Project buttons
		jtpProperties.setExportMzMLExcel(false);
		jtpProperties.setExportMGFExcel(false);
		jtpProperties.setExportMzIdentMLExcel(false);
		jtpProperties.setExportMascotXMLExcel(false);
		jtpProperties.setExportPepMZQExcel(false);
		jtpProperties.setExportProtMZQExcel(false);
		jtpProperties.setExportFeatMZQExcel(false);

		// ISO 3166 country code - GB
		Locale.setDefault(new Locale("en", "GB"));
	}

	/**
	 * Initialise project settings (configuration XML file)
	 * 
	 * @param void
	 * @return void
	 */
	private void initSettings(JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, final JButton jbSaveProject) {
		// ... Validate if config file exists ...//
		if (!WORKSPACE.isValidConfigFile()) {
			String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
			sMessage += "under your installation directory. ProteoSuite will continue launching, however \n";
			sMessage += "it is recommended that you copy the file as indicated in the readme.txt file.";
			JOptionPane.showMessageDialog(this, sMessage, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		sProjectName = "New";
		isProjectModified = false;
		updateSaveProjectStatus(jmSaveProject, jbSaveProject);
		updateCloseProjectStatus(jmCloseProject);
		updateTitle();
	}

	/**
	 * Initialise project status icons
	 * 
	 * @param jlIdentFilesStatus
	 * @param void
	 * @return void
	 */
	private void initProjectStatusIcons(JComboBox<String> jcbTechnique,
			JLabel jlIdentFilesStatus) {
		// ... Project status pipeline ...//
		Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/empty.gif"));
		jlIdentFilesStatus.setIcon(loadRawFilesIcon);
		jcbTechnique.setSelectedIndex(0);
	}

	/**
	 * Initialise Text Areas
	 * 
	 * @param jlFileNameMzMLText
	 * @param jlFileNameMzIDText
	 * @param jlFileNameMzQText
	 * @param void
	 * @return void
	 */
	private void initTextAreas(final TabbedLog jtpLog,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlFileNameMzQText) {

		jtpLog.reset();

		jepMzMLView.setText("");
		jepMGFView.setText("");
		jlFileNameMzMLText.setText("");
		jlFileNameMzIDText.setText("");
		jlFileNameMzQText.setText("");
		jepMzIDView.setText("");
		jepMZQView.setText("");
		jepMascotXMLView.setText("");
	}

	/**
	 * Open the mzq file which will be the PS file project
	 * 
	 * @param jepMzMLView
	 * @param jepMzIDView
	 * @param jlRawFilesStatus
	 * @param jlIdentFilesStatus
	 * @param jlFileNameMzMLText
	 * @param jlFileNameMzIDText
	 * @param jtIdentFiles
	 * @param jtMGF
	 * @param jtMascotXMLView
	 * @param jtMzId
	 * @param jtMzML
	 * @param jtQuantFiles
	 * @param void
	 * @return void
	 */
	public void openProject(JTable jtRawFiles, JLabel jlFileNameMGFText,
			final JTable jtMzIDProtGroup, JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, TabbedLog jtpLog,
			final JTabbedPane jtpProperties, final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject, JComboBox<String> jcbPSM,
			JEditorPane jepMzMLView, JEditorPane jepMzIDView,
			JLabel jlRawFilesStatus, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
			JTable jtIdentFiles, JTable jtMGF, JTable jtMascotXMLView,
			final JTable jtMzId, JTable jtMzML, final JTable jtQuantFiles) {
		// Load project settings from a mzq file
		// Selecting file(s)
		JFileChooser chooser = new JFileChooser(sPreviousLocation);
		chooser.setDialogTitle("Select the file(s) to analyze");

		// Applying file extension filters
		FileNameExtensionFilter filter3 = new FileNameExtensionFilter(
				"mzQuantML Files (*.mzq, *.mzq.gz)", "mzq");
		// Filters must be in descending order
		chooser.addChoosableFileFilter(filter3);
		// Enable multiple file selection
		chooser.setMultiSelectionEnabled(false);

		// Setting default directory
		if (sPreviousLocation == null || sPreviousLocation.contains("")) {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(WORKSPACE.getWorkSpace()));
		} else {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sPreviousLocation));
		}
		// ... Retrieving selection from user ...//
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file != null) {
				if (file.getName().toLowerCase().indexOf(".mzq") > 0) {
					// ... Unmarshall mzquantml file ...//
					System.out.println(SYS_UTILS.getTime()
							+ " - (Validation) Validating " + file.getPath());
					Validator validator = XMLparser.getValidator(MZQ_XSD);
					boolean validFlag = XMLparser.validate(validator,
							file.getPath());
					if (!validFlag) {
						JOptionPane
								.showMessageDialog(
										this,
										"The file "
												+ file
												+ " is not valid. \n Please make sure that the file is a valid mzq file (Release "
												+ MZQ_XSD + ")", "Error",
										JOptionPane.ERROR_MESSAGE);
					} else {
						boolean isOK = false;
						aMzQUnmarshaller.clear();
						final DefaultTableModel model = new DefaultTableModel();
						jtQuantFiles.setModel(model);
						model.addColumn("Name");
						model.addColumn("Path");
						model.addColumn("Type");
						model.addColumn("Version");
						try {
							isOK = true;

							aMzQUnmarshaller.add(Unmarshaller.unmarshalMzQMLFile(model, file));
						} catch (Exception e) {
							jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
							jtpLog.appendLog(e.getMessage());
							e.printStackTrace();
							isOK = false;
						}
						if (isOK) {
							isOK = loadProjectFromMZQ(0, file, jtRawFiles,
									jlFileNameMGFText, jtMzIDProtGroup,
									jtpProperties, jtpViewer, jcbPSM,
									jepMzMLView, jepMzIDView, jlRawFilesStatus,
									jlIdentFilesStatus, jlFileNameMzMLText,
									jlFileNameMzIDText, jtIdentFiles, jtMGF,
									jtMascotXMLView, jtMzId, jtMzML,
									jtQuantFiles);
							if (isOK) {
								isProjectModified = false;

								sProjectName = file.getName();
								updateStatusPipeline(jlRawFilesStatus,
										jtRawFiles, jlIdentFilesStatus,
										jtIdentFiles);
								updateSaveProjectStatus(jmSaveProject,
										jbSaveProject);
								updateCloseProjectStatus(jmCloseProject);
							}
						} else {
							JOptionPane
									.showMessageDialog(
											this,
											"ProteoSuite was unable to unmarshall this file."
													+ "Please make sure you have selected a valid file.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
	}

	/**
	 * Load project from mzq
	 * 
	 * @param iIndex
	 *            - Index to the aMzQUnmarshaller list
	 * @param sFile
	 *            - File name
	 * @boolean Success
	 */
	private boolean loadProjectFromMZQ(int iIndex, File sFile,
			final JTable jtRawFiles, final JLabel jlFileNameMGFText,
			final JTable jtMzIDProtGroup, final JTabbedPane jtpProperties,
			final TabbedChartViewer jtpViewer, final JComboBox<String> jcbPSM,
			final JEditorPane jepMzMLView, final JEditorPane jepMzIDView,
			final JLabel jlRawFilesStatus, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzMLText, final JLabel jlFileNameMzIDText,
			final JTable jtIdentFiles, final JTable jtMGF,
			final JTable jtMascotXMLView, final JTable jtMzId,
			final JTable jtMzML, final JTable jtQuantFiles) {

		final DefaultTableModel model = new DefaultTableModel();
		final DefaultTableModel model2 = new DefaultTableModel();
		jtRawFiles.setModel(model);
		jtIdentFiles.setModel(model2);
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");

		model2.addColumn("Name");
		model2.addColumn("Path");
		model2.addColumn("Type");
		model2.addColumn("Version");
		model2.addColumn("Raw File");

		final File sFileRef = sFile;
		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,
				true, "LoadProjectFromMZQ");

		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ... Progress Bar ...//
				progressBarDialog.setTitle("Loading project");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");
		thread.start();

		new Thread("LoadProjectFromMZQ") {
			@Override
			public void run() {
				System.out
						.println(SYS_UTILS.getTime()
								+ " - (From Project) Loading settings from the mzq file ...");
				String sGroup = "";

				MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(
						new File(jtQuantFiles.getValueAt(0, 1).toString()));
				MzQuantML mzq = unmarshaller.unmarshall();
				System.out.println(SYS_UTILS.getTime()
						+ " - MzQuantML version=" + mzq.getVersion());
				System.out
						.println(SYS_UTILS.getTime() + " - ID=" + mzq.getId());
				System.out.println(SYS_UTILS.getTime() + " - Name="
						+ mzq.getName());

				InputFiles inputFiles = mzq.getInputFiles();

				// Load raw files
				List<RawFilesGroup> rawFilesGroup = inputFiles
						.getRawFilesGroup();
				System.out.println(SYS_UTILS.getTime() + " - RawFilesGroup="
						+ rawFilesGroup.size());
				int iMGFRaw = 0, iMzMLRaw = 0;
				for (RawFilesGroup group : rawFilesGroup) {
					sGroup = group.getId();
					List<RawFile> rawFileList = group.getRawFile();
					for (RawFile rawFile : rawFileList) {
						// Validate type of file
						File file = new File(rawFile.getLocation());
						if (!file.exists()) {
							JOptionPane
									.showMessageDialog(
											ProteoSuiteView.this,
											"The file "
													+ rawFile.getName()
													+ " was not found at "
													+ rawFile.getLocation()
													+ ".\n"
													+ "Please make sure that the file exist, or reupload the file again.",
											"Warning",
											JOptionPane.WARNING_MESSAGE);
						} else {
							progressBarDialog.setTitle("Reading "
									+ rawFile.getName());
							progressBarDialog.setVisible(true);

							int mid = rawFile.getName().lastIndexOf(".");
							String ext = "";
							ext = rawFile.getName().substring(mid + 1,
									rawFile.getName().length());
							if (ext.toLowerCase().equals("mgf")) {
								model.insertRow(model.getRowCount(),
										new String[] { rawFile.getName(),
												rawFile.getLocation(), "MGF",
												mzMLVersion });
								iMGFRaw++;
							}
							if (ext.toLowerCase().equals("mzml")) {
								aMzMLUnmarshaller.add(Unmarshaller.unmarshalMzMLFile(model, file, ""));
								iMzMLRaw++;
							}
						}
					}
				}
				if (iMGFRaw > 0) {
					System.out.println(SYS_UTILS.getTime()
							+ " - Loading MGF view ...");
					if (jtRawFiles.getRowCount() > 0) {
						loadMGFView(0, jlFileNameMGFText, jtRawFiles,
								jtpProperties, jtMGF);
					}
				}
				if (iMzMLRaw > 0) {
					System.out.println(SYS_UTILS.getTime()
							+ " - Loading mzML view ...");
					loadMzMLView(0, jtRawFiles, jtpProperties, jepMzMLView,
							jlFileNameMzMLText, jtMzML);
					if (jtRawFiles.getRowCount() > 0) {
						System.out.println(SYS_UTILS.getTime()
								+ " - Showing chromatogram ...");

						// Clear container
						jtpViewer.updateChromatogram(aMzMLUnmarshaller.get(0));
						System.out.println(SYS_UTILS.getTime()
								+ " - Showing 2D plot ...");

						jtpViewer.update2DPlot(aMzMLUnmarshaller.get(0));
					}
				}
				// Load ident files
				List<IdentificationFile> idFilesList = inputFiles
						.getIdentificationFiles().getIdentificationFile();
				System.out.println(SYS_UTILS.getTime()
						+ " - Loading identification files");
				int iXMLIdent = 0, iMzID = 0;
				for (IdentificationFile idFile : idFilesList) {
					// Validate type of file
					File file = new File(idFile.getLocation());
					if (!file.exists()) {
						JOptionPane
								.showMessageDialog(
										ProteoSuiteView.this,
										"The file "
												+ idFile.getName()
												+ " was not found at "
												+ idFile.getLocation()
												+ ".\n"
												+ "Please make sure that the file exist, or reupload the file again.",
										"Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						progressBarDialog.setTitle("Reading "
								+ idFile.getName());
						progressBarDialog.setVisible(true);
						System.out.println(SYS_UTILS.getTime() + " - Reading "
								+ idFile.getName());

						int mid = idFile.getName().lastIndexOf(".");
						String ext = "";
						ext = idFile.getName().substring(mid + 1,
								idFile.getName().length());
						if (ext.toLowerCase().equals("xml")) {
							model2.insertRow(
									model2.getRowCount(),
									new String[] { idFile.getName(),
											idFile.getLocation(), "xml",
											mzIDVersion, sGroup });
							iXMLIdent++;
						}
						if (ext.toLowerCase().equals("mzid")) {

							aMzIDUnmarshaller.add(Unmarshaller.unmarshalMzIDFile(model2, file, ""));
							iMzID++;
						}
					}
				}
				if (iXMLIdent > 0) {
					if (jtIdentFiles.getRowCount() > 0) {
						Runnable fileFormatMascot = new FileFormatMascot(jtIdentFiles
								.getValueAt(0, 0).toString(), jtIdentFiles
								.getValueAt(0, 1).toString(), jtMascotXMLView,
								jtpProperties);
						fileFormatMascot.run();
					}
				}
				if (iMzID > 0) {
					if (jtIdentFiles.getRowCount() > 0) {
						loadMzIdentMLView(0, jtIdentFiles.getValueAt(0, 0)
								.toString(), jtMzIDProtGroup, jtpProperties,
								jcbPSM, jepMzIDView, jlFileNameMzIDText, jtMzId);
					}
				}
				sProjectName = sFileRef.getName();
				WORKSPACE.setWorkSpace(sFileRef.getParent());
				WORKSPACE.setWorkSpace(WORKSPACE.getWorkSpace().replace("\\",
						"/"));
				System.out.println(SYS_UTILS.getTime()
						+ " - Project has been set up to: "
						+ WORKSPACE.getWorkSpace() + ", " + sProjectName);
				updateTitle();
				renderIdentFiles(jtRawFiles, jtIdentFiles);
				updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
						jlIdentFilesStatus, jtIdentFiles);
				progressBarDialog.setVisible(false);
				progressBarDialog.dispose();

				// loadMzQuantMLView(0, sFileRef.getName());
			}
		}.start();
		return true;
	}

	/**
	 * Generate files (Quant file and xtracker files)
	 * 
	 * @param jtIdentFiles
	 * @param jtQuantFiles
	 * @param void
	 * @return true/false
	 */
	private boolean generateFiles(JTable jtRawFiles, TabbedLog jtpLog,
			String selectedTechnique, JComboBox<String> jcbOutputFormat,
			JTable jtIdentFiles, JTable jtQuantFiles) {
		// Check project name
		String sFile = sProjectName;
		System.out.println(SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		jtpLog.appendLog("\n" + SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		if (sFile.equals("New")) {
			sFile = "test.mzq";
			String sMessage = "This project has not been saved. Proteosuite will create a test.mzq file \n";
			sMessage += " under " + WORKSPACE.getWorkSpace()
					+ " to run the pipeline. \n";
			JOptionPane.showMessageDialog(this, sMessage, "Information",
					JOptionPane.INFORMATION_MESSAGE);
			sProjectName = sFile;
		}

		// Generate mzq file
		System.out.println(SYS_UTILS.getTime() + " - Generating mzq file ...");
		jtpLog.appendLog("\n" + SYS_UTILS.getTime()
				+ " - Generating mzq file ...");
		boolean isOK = FileFormatMzQuantML.writeMzQuantML(selectedTechnique,
				sFile, jtRawFiles, jcbOutputFormat, jtIdentFiles);
		if (!isOK)
			return false;

		// Unmarshall mzquantml file
		Validator validator = XMLparser.getValidator(MZQ_XSD);
		boolean validFlag = XMLparser.validate(validator, WORKSPACE
				.getWorkSpace().replace("\\", "/") + "/" + sProjectName);
		System.out.println(SYS_UTILS.getTime() + " - Validating mzQuantML ...");
		jtpLog.appendLog("\n" + SYS_UTILS.getTime()
				+ " - Validating mzQuantML ...");
		if (!validFlag) {
			JOptionPane.showMessageDialog(this, "Invalid mzQuantML file",
					"Error", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Modify the mzQuantML structure according to the experiment
			XTracker xTracker = new XTracker();
			xTracker.writeXTrackerFiles(selectedTechnique, jtRawFiles,
					jcbOutputFormat, jtIdentFiles, jtQuantFiles);
		}

		return true;
	}

	/**
	 * Main
	 * 
	 * @param args
	 *            the command line arguments (leave empty)
	 * @return void
	 */
	public static void main(String args[]) {
		// Setting standard look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					// UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		} catch (InstantiationException exception) {
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		} catch (UnsupportedLookAndFeelException exception) {
			exception.printStackTrace();
		}

		// Create and display the form
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProteoSuiteView().setVisible(true);
			}
		});
	}
}