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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
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
import org.proteosuite.gui.toolbars.*;
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
	public static final String sPS_Version = "0.3.1";
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

		final JButton jbExportFeatMZQExcel = new JButton();
		final JButton jbExportMGFExcel = new JButton();
		final JButton jbExportMascotXMLExcel = new JButton();
		final JButton jbExportMzIdentMLExcel = new JButton();
		final JButton jbExportMzMLExcel = new JButton();
		final JButton jbExportPepMZQExcel = new JButton();
		final JButton jbExportProtMZQExcel = new JButton();
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

		initComponents(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
				jbExportPepMZQExcel, jbExportProtMZQExcel,
				jbExportFeatMZQExcel, jbExportMascotXMLExcel,
				jbExportMzIdentMLExcel, jbExportMGFExcel, jbExportMzMLExcel,
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant);

		// Project default settings
		initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
				jbSaveProject, jbExportMzMLExcel, jbExportMGFExcel,
				jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
				jbExportPepMZQExcel, jbExportProtMZQExcel,
				jbExportFeatMZQExcel, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant);

		setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: "
				+ WORKSPACE.getWorkSpace() + " - " + sProjectName
				+ ">         http://www.proteosuite.org");

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

		// Buttons
		Icon iconExcel = new ImageIcon(getClass().getClassLoader().getResource(
				"images/xls.gif"));
		jbExportMzMLExcel.setIcon(iconExcel);
		jbExportMzMLExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMGFExcel.setIcon(iconExcel);
		jbExportMGFExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMzIdentMLExcel.setIcon(iconExcel);
		jbExportMzIdentMLExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMascotXMLExcel.setIcon(iconExcel);
		jbExportMascotXMLExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportPepMZQExcel.setIcon(iconExcel);
		jbExportPepMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportProtMZQExcel.setIcon(iconExcel);
		jbExportProtMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportFeatMZQExcel.setIcon(iconExcel);
		jbExportFeatMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");

		// Setting Window Height and Width
		setMinimumSize(new Dimension(1024, 768));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Configuring exit events
		pack();
	}

	private void initComponents(final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject, final TabbedLog jtpLog,
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
			final JTableRawData jtRawData, final JTable jtMzML, final JTable jtMGF,
			final JTable jtMzId, final JTable jtMascotXMLView,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant) {

		jcbTechnique.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Select technique", "iTRAQ", "TMT", "emPAI" }));
		jcbTechnique.setToolTipText("Select a technique for the analysis");
		jcbTechnique.setBorder(BorderFactory.createEtchedBorder());

		final JLabel jlFileNameMGFText = new JLabel();
		final JTable jtMzIDProtGroup = new JTable();
		final JComboBox<String> jcbPSM = new JComboBox<String>();
		jcbPSM.setModel(new DefaultComboBoxModel<String>(new String[] { "<=1",
				"<=2", "<=3", "All" }));

		jepMzMLView.setContentType("text/html");
		jepMzMLView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMzMLView.setPreferredSize(new Dimension(144, 84));

		jepMGFView.setContentType("text/html");
		jepMGFView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMGFView.setPreferredSize(new Dimension(144, 84));

		final JTabbedPane jtpMzQuantMLDetail = new TabbedMzQuantMLDetail(
				jtPeptideQuant, jtProteinQuant, jtFeatureQuant,
				jbExportPepMZQExcel, jbExportProtMZQExcel, jbExportFeatMZQExcel);

		final TabbedProperties jtpProperties = new TabbedProperties(
				jlFileNameMzMLText, jepMzMLView, jtMzML, jbExportMzMLExcel,
				jlFileNameMGFText, jepMGFView, jtMGF, jbExportMGFExcel, jtMzId,
				jbExportMzIdentMLExcel, jlFileNameMzIDText, jcbPSM,
				jtMzIDProtGroup, jepMzIDView, jepMascotXMLView,
				jbExportMascotXMLExcel, jtMascotXMLView, jtpMzQuantMLDetail,
				jlFileNameMzQText, jepMZQView);

		jtRawFiles.addMouseListener(new MouseListenerRawFiles(this,
				jtpProperties, jepMzMLView, jlFileNameMzMLText, jtMzML,
				aMzMLUnmarshaller, jlFileNameMGFText, jtMGF, jtpViewer));

		jtIdentFiles.addMouseListener(new MouseListenerIdentFiles(this,
				jtMascotXMLView, jtpProperties, jepMzIDView, jtMzIDProtGroup,
				jcbPSM, jtMzId, jlFileNameMzIDText));

		jtMzML.addMouseListener(new MouseListenerMzMl(jtRawFiles,
				jtRawData, jtpLog, aMzMLUnmarshaller, jtpViewer));
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

		jtQuantFiles.addMouseListener(new MouseListenerQuantFiles(this,
				jtFeatureQuant, jtpLog, jtpProperties, jtpMzQuantMLDetail,
				jepMZQView, jlFileNameMzQText, jtPeptideQuant, jtProteinQuant,
				jtQuantFiles));

		jepMZQView.setContentType("text/html");
		jepMZQView.setPreferredSize(new Dimension(144, 84));

		JPanel jpLeftPanelView = new LeftPanelView(jtQuantFiles, jtIdentFiles,
				jtRawFiles);
		JPanel jpLeftViewer = new LeftViewer(jtpViewer, jtpLog);
		JPanel jpPropertiesBox = new PropertiesView(jtpProperties);
		final MainPanel jpMainPanelView = new MainPanel(jpLeftPanelView,
				jpLeftViewer, jpPropertiesBox);

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

		setJMenuBar(new MenuBar(this, jcbTechnique, jmSaveProject,
				jmCloseProject, jtpLog, jtpViewer, jbSaveProject,
				jbExportMzMLExcel, jbExportMGFExcel, jbExportMzIdentMLExcel,
				jbExportMascotXMLExcel, jbExportPepMZQExcel,
				jbExportProtMZQExcel, jbExportFeatMZQExcel, jcbOutputFormat,
				jepMGFView, jepMZQView, jepMascotXMLView, jepMzIDView,
				jepMzMLView, jlFileNameMzMLText, jlFileNameMzIDText,
				jlIdentFilesStatus, jlFileNameMzQText, jtRawFiles,
				jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
				jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jlFileNameMGFText, jtMzIDProtGroup,
				jtpProperties, jtpMzQuantMLDetail, jcbPSM, jlRawFilesStatus,
				jpMainPanelView, identParamsExecute, aMzIDUnmarshaller));

		final JPanel jpToolBar = new MasterToolBar(this, jmCloseProject,
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
				jtMzIDProtGroup, jtpMzQuantMLDetail, jcbPSM, jlRawFilesStatus,
				identParamsExecute, aMzIDUnmarshaller);

		final JPanel jpProjectStatus = new ProjectStatus(jcbTechnique,
				jcbOutputFormat, jlRawFilesStatus, jlIdentFilesStatus);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jpMainPanelView, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
												.addComponent(
														jpProjectStatus,
														GroupLayout.Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														1232, Short.MAX_VALUE)
												.addComponent(
														jpToolBar,
														GroupLayout.DEFAULT_SIZE,
														1232, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jpToolBar,
										GroupLayout.PREFERRED_SIZE, 40,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpProjectStatus,
										GroupLayout.PREFERRED_SIZE, 37,
										GroupLayout.PREFERRED_SIZE)
								.addGap(23, 23, 23)
								.addComponent(jpMainPanelView,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		pack();
	}

	public void jmNewProjectActionPerformed(JComboBox<String> jcbTechnique,
			JMenuItem jmSaveProject, JMenuItem jmCloseProject,
			TabbedLog jtpLog, final TabbedChartViewer jtpViewer,
			final JButton jbSaveProject, final JButton jbExportMzMLExcel,
			final JButton jbExportMGFExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel,
			JComboBox<String> jcbOutputFormat, final JEditorPane jepMGFView,
			final JEditorPane jepMZQView, final JEditorPane jepMascotXMLView,
			final JEditorPane jepMzIDView, final JEditorPane jepMzMLView,
			JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
			JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText,
			JTable jtRawFiles, JTable jtIdentFiles, JTable jtQuantFiles,
			JTable jtRawData, JTable jtMzML, JTable jtMGF, JTable jtMzId,
			JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant) {
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
						jtpViewer, jbSaveProject, jbExportMzMLExcel,
						jbExportMGFExcel, jbExportMzIdentMLExcel,
						jbExportMascotXMLExcel, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jepMGFView, jepMZQView, jepMascotXMLView,
						jepMzIDView, jepMzMLView, jlFileNameMzMLText,
						jlFileNameMzIDText, jlIdentFilesStatus,
						jlFileNameMzQText, jtRawFiles, jtIdentFiles,
						jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
						jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
						jtFeatureQuant);
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jbExportMzMLExcel,
						jbExportMGFExcel, jbExportMzIdentMLExcel,
						jbExportMascotXMLExcel, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jepMGFView, jepMZQView, jepMascotXMLView,
						jepMzIDView, jepMzMLView, jlFileNameMzMLText,
						jlFileNameMzIDText, jlIdentFilesStatus,
						jlFileNameMzQText, jtRawFiles, jtIdentFiles,
						jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
						jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
						jtFeatureQuant);
			}
		} else {
			initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
					jbSaveProject, jbExportMzMLExcel, jbExportMGFExcel,
					jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
					jbExportPepMZQExcel, jbExportProtMZQExcel,
					jbExportFeatMZQExcel, jcbTechnique, jepMGFView, jepMZQView,
					jepMascotXMLView, jepMzIDView, jepMzMLView,
					jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
					jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
					jtRawData, jtMzML, jtMGF, jtMzId, jtMascotXMLView,
					jtPeptideQuant, jtProteinQuant, jtFeatureQuant);
		}
	}

	public void jmImportFileActionPerformed(final JTable jtRawFiles,
			final JLabel jlFileNameMGFText, final JTable jtFeatureQuant,
			final JTable jtMzIDProtGroup, JMenuItem jmSaveProject,
			final TabbedLog jtpLog, final JTabbedPane jtpProperties,
			final JTabbedPane jtpMzQuantMLDetail,
			final TabbedChartViewer jtpViewer,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMGFExcel, final JButton jbExportMzMLExcel,
			final JButton jbSaveProject, final JComboBox<String> jcbPSM,
			final JEditorPane jepMZQView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzQText,
			final JLabel jlRawFilesStatus, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzIDText, final JLabel jlFileNameMzMLText,
			final JTable jtIdentFiles, final JTable jtMGF,
			final JTable jtMascotXMLView, final JTable jtMzId,
			final JTable jtMzML, final JTable jtPeptideQuant,
			final JTable jtProteinQuant, final JTable jtQuantFiles) {
		// Selecting file(s)
		JFileChooser chooser = new JFileChooser(sPreviousLocation);
		chooser.setDialogTitle("Select the file(s) to analyze");

		// ... Filters must be in descending order ...//
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"mzQuantML Files (*.mzq, *.mzq.gz)", "mzq"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"Identification Files (*.mzid, *.mzid.gz, *.xml)", "mzid",
				"gz", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"Raw Files (*.mzML, *.mzML.gz, *.mgf)", "mzML", "gz", "mgf"));

		// Enable multiple file selection
		chooser.setMultiSelectionEnabled(true);

		// Setting default directory
		if (sPreviousLocation == null || sPreviousLocation.contains("")) {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(WORKSPACE.getWorkSpace()));
		} else {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sPreviousLocation));
		}

		// Retrieving selection from user
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			final File[] aFiles = chooser.getSelectedFiles();
			if (aFiles != null && aFiles.length > 0) {
				isProjectModified = true;
				updateSaveProjectStatus(jmSaveProject, jbSaveProject);
				sPreviousLocation = aFiles[0].getParent();
				// ---------------//
				// Read mzML //
				// ---------------//
				// Usually, the first element will contain the type of
				// files, but we can also expect mixed entries
				if ((aFiles[0].getName().toLowerCase().indexOf(".mzml") > 0)
						|| (aFiles[0].getName().toLowerCase()
								.indexOf(".mzml.gz") > 0)) {
					// Fill JTable
					final DefaultTableModel model = new DefaultTableModel();
					jtRawFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");

					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "ReadingMzML");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// Progress Bar
							progressBarDialog.setTitle("Reading mzML files");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();
					new Thread("ReadingMzML") {
						@Override
						public void run() {
							String sOutput = "";
							String sMessage = "";
							jtpLog.resetLog();

							// Release unmarshallers
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
								aMzMLUnmarshaller.clear();
							}
							// Reading selected files
							for (int iI = 0; iI < aFiles.length; iI++) {
								// Validate file extension (mixed files)								
								if ((aFiles[iI].getName().toLowerCase()
										.indexOf(".mzml") > 0)
										|| (aFiles[iI].getName().toLowerCase()
												.indexOf(".mzml.gz") > 0)) {
									File xmlFile = new File(
											aFiles[iI].getPath());

									progressBarDialog
											.setTitle("Reading mzml files ("
													+ (iI + 1) + "/"
													+ aFiles.length + ") - "
													+ xmlFile.getName());
									progressBarDialog.setVisible(true);

									// Uncompress mzML.gz files
									if (aFiles[iI].getName().toLowerCase()
											.indexOf(".mzml.gz") > 0) {
										try {
											sMessage = SYS_UTILS.getTime()
													+ " - Uncompressing "
													+ xmlFile.getName();
											progressBarDialog
													.setTitle("Uncompressing "
															+ xmlFile.getName());
											progressBarDialog.setVisible(true);

											System.out.println(sMessage);
											sOutput = sOutput + sMessage + "\n";
											jtpLog.setLog(sOutput);

											File outFile = null;
											FileOutputStream fos = null;

											GZIPInputStream gin = new GZIPInputStream(
													new FileInputStream(xmlFile));
											outFile = new File(
													aFiles[0].getParent(),
													aFiles[0].getName()
															.replaceAll(
																	"\\.gz$",
																	""));
											fos = new FileOutputStream(outFile);
											byte[] buf = new byte[100000];
											int len;
											while ((len = gin.read(buf)) > 0) {
												fos.write(buf, 0, len);
											}
											gin.close();
											fos.close();
											xmlFile = outFile;
											sMessage = SYS_UTILS.getTime()
													+ " - Uncompressing ends ";
											System.out.println(sMessage);
											sOutput = sOutput + sMessage + "\n";
										} catch (IOException ioe) {
											System.out
													.println("Exception has been thrown"
															+ ioe);
										}
									}
									// Unmarshall data using jzmzML API									
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtpLog.setLog(sOutput);
									Unmarshaller.unmarshalMzMLFile(model,
											xmlFile, "", aMzMLUnmarshaller);

									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling ends ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtpLog.setLog(sOutput);
								}
							} // For files

							// We then display the first mzML element, the
							// corresponding chromatogram and the 2D plot
							sMessage = SYS_UTILS.getTime()
									+ " - Loading mzML view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							loadMzMLView(0, jtRawFiles, jtpProperties,
									jepMzMLView, jlFileNameMzMLText, jtMzML);
							sMessage = SYS_UTILS.getTime()
									+ " - Displaying chromatogram ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);

							// Clear container
							jtpViewer.updateChromatogram(aMzMLUnmarshaller.get(0));

							sMessage = SYS_UTILS.getTime()
									+ " - Displaying 2D Plot";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);

							jtpViewer.update2DPlot(aMzMLUnmarshaller.get(0));

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							jbExportMzMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);
						}
					}.start();
				} // From reading mzML files
					// ---------------//
					// Read MGF //
					// ---------------//
				if (aFiles[0].getName().toLowerCase().indexOf(".mgf") > 0) {
					// Fill JTable
					final DefaultTableModel model = new DefaultTableModel();
					jtRawFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");

					final ProgressBarThread thread = new ProgressBarThread("ProgressBarDialog",
							this, true, "ReadingMGF");
					thread.start();
					new Thread("ReadingMGF") {
						@Override
						public void run() {
							String sMessage = "";
							String sOutput = "";
							jtpLog.setLog(sOutput);
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading MGF files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
							}

							// Reading selected files
							for (int iI = 0; iI < aFiles.length; iI++) {
								thread
										.setTitle("Reading MGF files ("
												+ (iI + 1) + "/"
												+ aFiles.length + ") - "
												+ aFiles[iI].getName());
								thread.setVisible(true);
								// Validate file extension (mixed files)
								if (aFiles[iI].getName().toLowerCase()
										.indexOf(".mgf") > 0) {
									model.insertRow(
											model.getRowCount(),
											new String[] {
													aFiles[iI].getName(),
													aFiles[iI].getPath()
															.toString()
															.replace("\\", "/"),
													"MGF", "N/A" });
								}
							} // For files

							// Display data for the first element
							sMessage = SYS_UTILS.getTime()
									+ " - Loading MGF view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							loadMGFView(0, jlFileNameMGFText, jtRawFiles,
									jtpProperties, jtMGF);

							thread.setVisible(false);
							thread.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							jbExportMGFExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);
						}
					}.start();
				} // From reading MGF files
					// -------------------//
					// Read mzIdentML //
					// -------------------//
				if ((aFiles[0].getName().toLowerCase().indexOf(".mzid") > 0)
						|| (aFiles[0].getName().toLowerCase()
								.indexOf(".mzid.gz") > 0)) {
					// Fill JTable
					final DefaultTableModel model = new DefaultTableModel();
					jtIdentFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");
					model.addColumn("Raw File");

					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "ReadingMzID");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// Progress Bar
							progressBarDialog.setTitle("Reading mzIdentML files");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();
					new Thread("ReadingMzID") {
						@Override
						public void run() {
							String sOutput = "";
							String sMessage = "";
							jtpLog.setLog(sOutput);

							// Release unmarshallers
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
								aMzIDUnmarshaller.clear();
							}
							// Reading selected files
							for (int iI = 0; iI < aFiles.length; iI++) {
								// Validate file extension (mixed files)
								if ((aFiles[iI].getName().toLowerCase()
										.indexOf(".mzid") > 0)
										|| (aFiles[iI].getName().toLowerCase()
												.indexOf(".mzid.gz") > 0)) {
									File xmlFile = new File(
											aFiles[iI].getPath());

									progressBarDialog
											.setTitle("Reading mzid Files ("
													+ (iI + 1) + "/"
													+ aFiles.length + ") - "
													+ xmlFile.getName());
									progressBarDialog.setVisible(true);

									// Uncompress .gz files
									if (aFiles[iI].getName().toLowerCase()
											.indexOf(".mzid.gz") > 0) {
										try {
											sMessage = SYS_UTILS.getTime()
													+ " - Uncompressing "
													+ xmlFile.getName();
											progressBarDialog
													.setTitle("Uncompressing "
															+ xmlFile.getName());
											progressBarDialog.setVisible(true);

											File outFile = null;
											FileOutputStream fos = null;

											GZIPInputStream gin = new GZIPInputStream(
													new FileInputStream(xmlFile));
											outFile = new File(
													aFiles[0].getParent(),
													aFiles[0].getName()
															.replaceAll(
																	"\\.gz$",
																	""));
											fos = new FileOutputStream(outFile);
											byte[] buf = new byte[100000];
											int len;
											while ((len = gin.read(buf)) > 0) {
												fos.write(buf, 0, len);
											}
											gin.close();
											fos.close();
											xmlFile = outFile;
											sMessage = SYS_UTILS.getTime()
													+ " - Uncompressing ends ";
											System.out.println(sMessage);
											sOutput = sOutput + sMessage + "\n";
											jtpLog.setLog(sOutput);
										} catch (IOException ioe) {
											System.out
													.println("Exception has been thrown"
															+ ioe);
										}
									}
									// Unmarshall data using jmzIdentML API
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtpLog.setLog(sOutput);
									Unmarshaller.unmarshalMzIDFile(model,
											xmlFile, "", aMzIDUnmarshaller);
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling ends ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtpLog.setLog(sOutput);
								}
							} // ... For files ...//

							// ... Display first element ...//
							sMessage = SYS_UTILS.getTime()
									+ " - Loading mzIdentML view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							loadMzIdentMLView(0, aFiles[0].getName(),
									jtMzIDProtGroup, jtpProperties, jcbPSM,
									jepMzIDView, jlFileNameMzIDText, jtMzId);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();

							sMessage = SYS_UTILS.getTime()
									+ " - Identification files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							jbExportMzIdentMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);
						}
					}.start();
				}
				// -------------------//
				// Read Mascot XML //
				// -------------------//
				if (aFiles[0].getName().toLowerCase().indexOf(".xml") > 0) {
					// ... Fill JTable ...//
					final DefaultTableModel model = new DefaultTableModel();
					jtIdentFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");
					model.addColumn("Raw File");

					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "ReadingMascot");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// ... Progress Bar ...//
							progressBarDialog.setTitle("Reading Mascot XML files");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();

					new Thread("ReadingMascot") {
						@Override
						public void run() {
							String sMessage = "";
							String sOutput = "";
							jtpLog.setLog(sOutput);
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading Mascot XML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
							}

							// ... Reading selected files ...//
							for (int iI = 0; iI < aFiles.length; iI++) {
								progressBarDialog
										.setTitle("Reading Mascot XML files ("
												+ (iI + 1) + "/"
												+ aFiles.length + ") - "
												+ aFiles[iI].getName());
								progressBarDialog.setVisible(true);

								// Validate file extension (mixed files)
								if (aFiles[iI].getName().toLowerCase()
										.indexOf(".xml") > 0) {
									model.insertRow(
											model.getRowCount(),
											new String[] {
													aFiles[iI].getName(),
													aFiles[iI].getPath()
															.toString()
															.replace("\\", "/"),
													"mascot_xml", "N/A", "" });
								}
							} // ... For files ...//

							// ... Display data for the first element ...//
							sMessage = SYS_UTILS.getTime()
									+ " - Loading Mascot XML view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							FileFormatMascot.loadMascotView(
									aFiles[0].getName(), aFiles[0].getPath()
											.toString().replace("\\", "/"),
									jtMascotXMLView, jtpProperties);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Identifiation files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtpLog.setLog(sOutput);
							jbExportMascotXMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);

						}
					}.start();
				}
				// -------------------//
				// Read mzQuantML //
				// -------------------//
				if (aFiles[0].getName().toLowerCase().indexOf(".mzq") > 0) {
					// ... Fill JTable ...//
					final DefaultTableModel model = new DefaultTableModel();
					jtQuantFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");

					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "ReadingMZQ");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// ... Progress Bar ...//
							progressBarDialog.setTitle("Reading mzQuantML files");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();
					new Thread("ReadingMZQ") {
						@Override
						public void run() {
							String sOutput = "";
							String sMessage = "";
							jtpLog.setLog(sOutput);

							// ... Release unmarshallers ...//
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzQuantML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
								aMzQUnmarshaller.clear();
							}

							// Reading selected files
							boolean isOK = true;
							for (int iI = 0; iI < aFiles.length; iI++) {
								// Validate file extension (mixed files)
								//
								if (aFiles[iI].getName().toLowerCase()
										.indexOf(".mzq") > 0) {
									File xmlFile = new File(
											aFiles[iI].getPath());
									progressBarDialog
											.setTitle("Reading mzq Files ("
													+ (iI + 1) + "/"
													+ aFiles.length + ") - "
													+ xmlFile.getName());
									progressBarDialog.setVisible(true);

									// Unmarshall data using jmzIdentML API
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtpLog.setLog(sOutput);

									try {
										isOK = Unmarshaller.unmarshalMzQMLFile(
												model, xmlFile,
												aMzQUnmarshaller);
										// Invalid mzq file
										if (!isOK) {
											break;
										}

										sMessage = SYS_UTILS.getTime()
												+ " - Unmarshalling ends ";
										System.out.println(sMessage);
										sOutput = sOutput + sMessage + "\n";
										jtpLog.setLog(sOutput);
									} catch (Exception e) {
										jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
										jtpLog.appendLog(e.getMessage());
										e.printStackTrace();
										isOK = false;
									}

								}
							}
							// For files
							if (isOK) {
								// Display first element
								sMessage = SYS_UTILS.getTime()
										+ " - Loading mzQuantML view ";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
								// loadMzQuantMLView(0, aFiles[0].getName());
								loadMzQuantMLView(0,
										aFiles[0].getAbsolutePath(),
										jtFeatureQuant, jtpLog, jtpProperties,
										jtpMzQuantMLDetail, jepMZQView,
										jlFileNameMzQText, jtPeptideQuant,
										jtProteinQuant, jtQuantFiles);

								sMessage = SYS_UTILS.getTime()
										+ " - Quantification files imported successfully!";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtpLog.setLog(sOutput);
							}
							jbExportPepMZQExcel.setEnabled(true);
							jbExportProtMZQExcel.setEnabled(true);
							jbExportFeatMZQExcel.setEnabled(true);
							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
						}
					}.start();
				}
			} // ... From Files
		} // ... From If
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
			final JButton jbExportMzMLExcel, final JButton jbExportMGFExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel, JComboBox<String> jcbTechnique,
			JComboBox<String> jcbOutputFormat, final JEditorPane jepMGFView,
			final JEditorPane jepMZQView, final JEditorPane jepMascotXMLView,
			final JEditorPane jepMzIDView, final JEditorPane jepMzMLView,
			JLabel jlFileNameMzQText, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzIDText, JLabel jlFileNameMzMLText,
			JTable jtRawFiles, JTable jtIdentFiles, JTable jtQuantFiles,
			JTable jtRawData, JTable jtMzML, JTable jtMGF, JTable jtMzId,
			JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant) {
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
						jtpViewer, jbSaveProject, jbExportMzMLExcel,
						jbExportMGFExcel, jbExportMzIdentMLExcel,
						jbExportMascotXMLExcel, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jepMGFView, jepMZQView, jepMascotXMLView,
						jepMzIDView, jepMzMLView, jlFileNameMzMLText,
						jlFileNameMzIDText, jlIdentFilesStatus,
						jlFileNameMzQText, jtRawFiles, jtIdentFiles,
						jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
						jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
						jtFeatureQuant);
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues(jmCloseProject, jmSaveProject, jtpLog,
						jtpViewer, jbSaveProject, jbExportMzMLExcel,
						jbExportMGFExcel, jbExportMzIdentMLExcel,
						jbExportMascotXMLExcel, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jepMGFView, jepMZQView, jepMascotXMLView,
						jepMzIDView, jepMzMLView, jlFileNameMzMLText,
						jlFileNameMzIDText, jlIdentFilesStatus,
						jlFileNameMzQText, jtRawFiles, jtIdentFiles,
						jtQuantFiles, jtRawData, jtMzML, jtMGF, jtMzId,
						jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
						jtFeatureQuant);
			}
		} else {
			initProjectValues(jmCloseProject, jmSaveProject, jtpLog, jtpViewer,
					jbSaveProject, jbExportMzMLExcel, jbExportMGFExcel,
					jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
					jbExportPepMZQExcel, jbExportProtMZQExcel,
					jbExportFeatMZQExcel, jcbTechnique, jepMGFView, jepMZQView,
					jepMascotXMLView, jepMzIDView, jepMzMLView,
					jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
					jlFileNameMzQText, jtRawFiles, jtIdentFiles, jtQuantFiles,
					jtRawData, jtMzML, jtMGF, jtMzId, jtMascotXMLView,
					jtPeptideQuant, jtProteinQuant, jtFeatureQuant);
		}
	}

	public void jmRunQuantAnalysisActionPerformed(final TabbedLog jtpLog,
			final JMenuItem jmCloseProject, final JMenuItem jmSaveProject,
			final JTabbedPane jtpProperties,
			final JTabbedPane jtpMzQuantMLDetail, final JButton jbSaveProject,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel,
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
									.equals("Label free")) { // ... Label free
																// will be
																// performed in
																// proteosuite
																// ...//

								progressBarDialog.setVisible(false);
								progressBarDialog.dispose();
								JOptionPane
										.showMessageDialog(
												ProteoSuiteView.this,
												"SILAC, Label Free and 15N are under development. Please stay tuned for new releases.",
												"Information",
												JOptionPane.INFORMATION_MESSAGE);

								// final JPanel run = new JPanel();
								// JLabel jLabel1 = new
								// JLabel("Please specify the parameters to perform the label free method.");
								// JLabel jLabel2 = new JLabel("Scan Window:");
								// JLabel jLabel3 = new JLabel("To:");
								// JLabel jLabel4 = new JLabel("m/z Window:");
								// JLabel jLabel5 = new JLabel("To:");
								// JLabel jLabel6 = new
								// JLabel("Peaks left-right:");
								// JTextField jTextField1 = new
								// JTextField("1671");
								// JTextField jTextField2 = new
								// JTextField("1672");
								// JTextField jTextField3 = new
								// JTextField("300.00");
								// JTextField jTextField4 = new
								// JTextField("1000.00");
								// JTextField jTextField5 = new JTextField("4");
								// GridLayout layout=new GridLayout(11,1);
								// jTextField1.requestFocusInWindow();
								// run.setLayout(layout);
								// run.add(jLabel1);
								// run.add(jLabel2);
								// run.add(jTextField1);
								// run.add(jLabel3);
								// run.add(jTextField2);
								// run.add(jLabel4);
								// run.add(jTextField3);
								// run.add(jLabel5);
								// run.add(jTextField4);
								// run.add(jLabel6);
								// run.add(jTextField5);
								// int iOption =
								// JOptionPane.showConfirmDialog(null, run,
								// "Edit Quantitation Parameters",
								// JOptionPane.OK_CANCEL_OPTION,
								// JOptionPane.PLAIN_MESSAGE);
								// if (iOption == JOptionPane.OK_OPTION) {
								// generateTemplate(Integer.parseInt(jTextField1.getText()),
								// Integer.parseInt(jTextField2.getText()),
								// Double.parseDouble(jTextField3.getText()),
								// Double.parseDouble(jTextField4.getText()),
								// Integer.parseInt(jTextField5.getText())
								// );
								// }
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
											isOK = Unmarshaller
													.unmarshalMzQMLFile(model,
															xmlFile,
															aMzQUnmarshaller);
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
													jtpProperties,
													jtpMzQuantMLDetail,
													jepMZQView,
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

											jbExportPepMZQExcel
													.setEnabled(true);
											jbExportProtMZQExcel
													.setEnabled(true);
											jbExportFeatMZQExcel
													.setEnabled(true);

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
								Unmarshaller.unmarshalMzIDFile(model, xmlFile,
										"", aMzIDUnmarshaller);
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
	private void renderIdentFiles(JTable jtRawFiles, JTable jtIdentFiles) {
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
	private void updateSaveProjectStatus(final JMenuItem jmSaveProject,
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
		setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: "
				+ WORKSPACE.getWorkSpace() + " - " + sProjectName
				+ ">         http://www.proteosuite.org");
	}

	/**
	 * Update status pipeline
	 * 
	 * @param void
	 * @return void
	 */
	private void updateStatusPipeline(JLabel jlRawFilesStatus,
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

		FileFormatMzIdentML mzIdentML = new FileFormatMzIdentML(jtpProperties,
				jlFileNameMzIDText, sFileName, aMzIDUnmarshaller, iIndex,
				SYS_UTILS, jtMzId, jcbPSM, jtMzIDProtGroup, jepMzIDView,
				progressBarDialog);
		mzIdentML.start();
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
			final JTabbedPane jtpProperties,
			final JTabbedPane jtpMzQuantMLDetail, JEditorPane jepMZQView,
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
						jtpProperties, iIndex, SYS_UTILS, progressBarDialog,
						jtProteinQuant, jtPeptideQuant, jtFeatureQuant, sFile,
						aMzQUnmarshaller, jlFileNameMzQText, jepMZQView,
						jtpLog, jtpMzQuantMLDetail);
				mzQuantML.start();
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

		FileFormatMzML mzML = new FileFormatMzML(jtMzML, aMzMLUnmarshaller,
				iIndex, jlFileNameMzMLText, jepMzMLView, jtpProperties,
				progressBarDialog);
		mzML.start();
	}

	/**
	 * Displays MGF raw data
	 * 
	 * @param iIndex
	 *            - index for the array
	 * @param jtMGF
	 * @return void
	 */
	public void loadMGFView(int iIndex, JLabel jlFileNameMGFText,
			JTable jtRawFiles, final JTabbedPane jtpProperties, JTable jtMGF) {
		// ... Check if not previously loaded
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
				sFileNameRef, jtpProperties, sFilePathRef, progressBarDialog);
		mgf.start();
	}

	/**
	 * Initialise project settings (all modules)
	 * 
	 * @param jlFileNameMzMLText
	 * @param jlFileNameMzIDText
	 * @param jlIdentFilesStatus
	 * @param jlFileNameMzQText
	 * @param jtRawData
	 * @param void
	 * @return void
	 */
	private void initProjectValues(JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, TabbedLog jtpLog,
			final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
			final JButton jbExportMzMLExcel, final JButton jbExportMGFExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel, JComboBox<String> jcbTechnique,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlIdentFilesStatus,
			JLabel jlFileNameMzQText, JTable jtRawFiles, JTable jtIdentFiles,
			JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF,
			JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant) {
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
		jbExportMzMLExcel.setEnabled(false);
		jbExportMGFExcel.setEnabled(false);
		jbExportMzIdentMLExcel.setEnabled(false);
		jbExportMascotXMLExcel.setEnabled(false);
		jbExportPepMZQExcel.setEnabled(false);
		jbExportProtMZQExcel.setEnabled(false);
		jbExportFeatMZQExcel.setEnabled(false);

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
							isOK = Unmarshaller.unmarshalMzQMLFile(model, file,
									aMzQUnmarshaller);
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
								Unmarshaller.unmarshalMzMLFile(model, file, "",
										aMzMLUnmarshaller);
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
							Unmarshaller.unmarshalMzIDFile(model2, file, "",
									aMzIDUnmarshaller);
							iMzID++;
						}
					}
				}
				if (iXMLIdent > 0) {
					if (jtIdentFiles.getRowCount() > 0) {
						FileFormatMascot.loadMascotView(jtIdentFiles
								.getValueAt(0, 0).toString(), jtIdentFiles
								.getValueAt(0, 1).toString(), jtMascotXMLView,
								jtpProperties);
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
			sMessage += " under " + WORKSPACE.getWorkSpace() + " to run the pipeline. \n";
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