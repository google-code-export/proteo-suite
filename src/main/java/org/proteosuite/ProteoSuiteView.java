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
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Validator;

import org.proteosuite.fileformat.FileFormatMGF;
import org.proteosuite.fileformat.FileFormatMascot;
import org.proteosuite.fileformat.FileFormatMzIdentML;
import org.proteosuite.fileformat.FileFormatMzML;
import org.proteosuite.fileformat.FileFormatMzQuantML;
import org.proteosuite.gui.*;
import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.chart.ChartSpectrum;
import org.proteosuite.listener.*;
import org.proteosuite.utils.DeltaConversion;
import org.proteosuite.utils.DeltaConversion.DeltaEncodedDataFormatException;
import org.proteosuite.utils.NumericalUtils;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;
import org.proteosuite.utils.TwoDPlot;
import org.proteosuite.utils.Unmarshaller;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cranfield.xTracker.*;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;
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
	public static String sWorkspace = "";
	public static String sPreviousLocation = "user.home";
	public boolean bProjectModified = false;
	public static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
	public static final String mzMLVersion = "1.1";
	public static final String mzIDVersion = "1.1";
	public static final String MZQUANT_VERSION = "1.0.0";
	public static final String PSI_MS_VERSION = "3.37.0";
	public static final String PSI_MOD_VERSION = "1.2";
	public static final SystemUtils SYS_UTILS = new SystemUtils();

	private IdentParamsView identParamsExecute;
	// private JDialog dialogIdentParamsExecute;

	// List of unmarshaller objects
	private List<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
	private List<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
	private List<MzQuantMLUnmarshaller> aMzQUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();

	public ProteoSuiteView() {
		// Initializing all components (handled by netbeans)
		final JMenuItem jmCloseProject = new JMenuItem("Close Project");
		final JMenuItem jmSaveProject = new JMenuItem("Save Project",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/save.gif")));
		final JTextArea jtaLog = new JTextArea();
		final JTabbedPane jtpLog = new JTabbedPane();
		final JTabbedPane jtpProperties = new JTabbedPane();
		final JSplitPane jspLeftViewerDetails = new JSplitPane();
		final JSplitPane jspMainPanelView = new JSplitPane();
		final JSplitPane jspMzML = new JSplitPane();
		final JSplitPane jspViewerAndProperties = new JSplitPane();
		final JDesktopPane jdpTIC = new JDesktopPane();
		final JDesktopPane jdpMS = new JDesktopPane();

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

		final JTable jtRawData = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};

		final JTable jtFeatureQuant = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtIdentFiles = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex < 4) {
					return false;
				} else {
					return true;
				}
			}
		};
		final JTable jtMGF = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtMascotXMLView = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtMzIDProtGroup = new JTable();
		final JTable jtMzId = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtMzML = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtPeptideQuant = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtProteinQuant = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtQuantFiles = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		final JTable jtRawFiles = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex < 4) {
					return false;
				} else {
					return true;
				}
			}
		};

		final JPanel jp2D = new JPanel();
		final JTabbedPane jtpViewer = new TabbedChartViewer(jdpMS, jdpTIC, jp2D);
		
		initComponents(jmCloseProject, jmSaveProject, jtaLog, jtpProperties,
				jtpLog, jtpViewer, jspLeftViewerDetails, jspMainPanelView,
				jspMzML, jspViewerAndProperties, jdpTIC, jdpMS,
				jbExportPepMZQExcel, jbExportProtMZQExcel,
				jbExportFeatMZQExcel, jbExportMascotXMLExcel,
				jbExportMzIdentMLExcel, jbExportMGFExcel, jbExportMzMLExcel,
				jbSaveProject, jcbTechnique, jepMGFView, jepMZQView,
				jepMascotXMLView, jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
				jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant, jp2D);

		// ... Load parameter settings ...//
		identParamsExecute = new IdentParamsView(sWorkspace, "execute");

		// ... Project default settings ...//
		initProjectValues(jmCloseProject, jmSaveProject, jtaLog, jdpTIC, jdpMS,
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
				+ sWorkspace + " - " + sProjectName
				+ ">         http://www.proteosuite.org");

		// ... Setting project icons ...//
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage());

		// .. Check Java version and architecture ...//
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

		// ... Tabsheet and other shortcut icons ...//
		Icon logIcon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/log.gif"));
		JLabel jlLogIcon = new JLabel("Log  ", logIcon, SwingConstants.RIGHT);
		jlLogIcon.setIconTextGap(5);
		jtpLog.setTabComponentAt(0, jlLogIcon);

		Icon rawDataIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/raw_data.gif"));
		JLabel jlRawDataIcon = new JLabel("Raw data", rawDataIcon,
				SwingConstants.RIGHT);
		jlRawDataIcon.setIconTextGap(5);
		jtpLog.setTabComponentAt(1, jlRawDataIcon);

		Icon propertiesMzMLIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMzMLIcon = new JLabel("mzML View",
				propertiesMzMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(0, jlPropertiesMzMLIcon);

		Icon propertiesMGFIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMGFIcon = new JLabel("MGF View", propertiesMGFIcon,
				SwingConstants.RIGHT);
		jlPropertiesMGFIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(1, jlPropertiesMGFIcon);

		Icon propertiesMzIdenMLIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMzIdentMLIcon = new JLabel("mzIdentML View",
				propertiesMzIdenMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzIdentMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(2, jlPropertiesMzIdentMLIcon);

		Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View",
				propertiesMascotIcon, SwingConstants.RIGHT);
		jlPropertiesMascotIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(3, jlPropertiesMascotIcon);

		Icon propertiesMzQuantMLIcon = new ImageIcon(getClass()
				.getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View",
				propertiesMzQuantMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzQuantMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(4, jlPropertiesMzQuantMLIcon);

		// ... Buttons ...//
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

		// ... Setting Window Height and Width ...//
		setMinimumSize(new Dimension(1024, 768));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// ... Setting dividers ...//
		jspMainPanelView.setDividerLocation(250); // ... Left Menu (Files) ...//

		jspLeftViewerDetails.setDividerLocation(480); // ... Viewer Height
														// (Viewer) ...//
		jspViewerAndProperties.setDividerLocation(600); // ... Viewer Width
														// (Viewer) ...//

		jspMzML.setDividerLocation(100); // ... MzML data ...//

		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setDividerLocation(200); // ... MzML files ...//

		// ... Setting default selection (Viewers) ...//
		jtpLog.setSelectedIndex(0);
		jtpProperties.setSelectedIndex(0);

		// ... Configuring exit events...//
		pack();
	}

	private void initComponents(final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject, final JTextArea jtaLog,
			final JTabbedPane jtpProperties, final JTabbedPane jtpLog,
			final JTabbedPane jtpViewer, final JSplitPane jspLeftViewerDetails,
			final JSplitPane jspMainPanelView, final JSplitPane jspMzML,
			final JSplitPane jspViewerAndProperties, final JDesktopPane jdpTIC,
			final JDesktopPane jdpMS, final JButton jbExportPepMZQExcel,
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
			final JTable jtFeatureQuant, final JPanel jp2D) {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ActionListenerExit());

		final JComboBox<String> jcbOutputFormat = new JComboBox<String>();
		JButton jbNewProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/new.gif")));
		jbNewProject.setToolTipText("New Project (Ctrl + N)");
		jbNewProject.setFocusable(false);
		jbNewProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbNewProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbNewProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmNewProjectActionPerformed(jcbTechnique, jmSaveProject,
						jmCloseProject, jtaLog, jdpTIC, jdpMS, jbSaveProject,
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
		JToolBar jToolBar1 = new JToolBar();
		jToolBar1.add(jbNewProject);

		final JTabbedPane jtpMzQuantMLDetail = new JTabbedPane();
		final JLabel jlFileNameMGFText = new JLabel();
		final JLabel jlRawFilesStatus = new JLabel();
		JButton jbImportFile = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/import.gif")));

		final JTable jtMzIDProtGroup = new JTable();
		final JComboBox<String> jcbPSM = new JComboBox<String>();
		jbImportFile.setToolTipText("Import File (Ctrl + I)");
		jbImportFile.setFocusable(false);
		jbImportFile.setHorizontalTextPosition(SwingConstants.CENTER);
		jbImportFile.setMaximumSize(new Dimension(27, 21));
		jbImportFile.setMinimumSize(new Dimension(27, 21));
		jbImportFile.setPreferredSize(new Dimension(27, 21));
		jbImportFile.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbImportFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmImportFileActionPerformed(jtRawFiles, jlFileNameMGFText,
						jtFeatureQuant, jtMzIDProtGroup, jmSaveProject, jp2D,
						jtaLog, jtpProperties, jtpMzQuantMLDetail, jdpTIC,
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
		jToolBar1.add(jbImportFile);

		JButton jbOpenProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/open.gif")));
		jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
		jbOpenProject.setFocusable(false);
		jbOpenProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbOpenProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbOpenProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				openProject(jtRawFiles, jlFileNameMGFText, jtMzIDProtGroup,
						jmCloseProject, jmSaveProject, jp2D, jtaLog,
						jtpProperties, jdpTIC, jbSaveProject, jcbPSM,
						jepMzMLView, jepMzIDView, jlRawFilesStatus,
						jlIdentFilesStatus, jlFileNameMzMLText,
						jlFileNameMzIDText, jtIdentFiles, jtMGF,
						jtMascotXMLView, jtMzId, jtMzML, jtQuantFiles);
			}
		});
		jToolBar1.add(jbOpenProject);

		jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
		jbSaveProject.setEnabled(false);
		jbSaveProject.setFocusable(false);
		jbSaveProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbSaveProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbSaveProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmSaveProjectActionPerformed(jtRawFiles, jcbTechnique,
						jmSaveProject, jmCloseProject, jbSaveProject,
						jcbOutputFormat, jtIdentFiles);
			}
		});
		jToolBar1.add(jbSaveProject);

		JButton jbCut = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/cut.gif")));
		jbCut.setToolTipText("Cut (Ctrl + X)");
		jbCut.setEnabled(false);
		jbCut.setFocusable(false);
		jbCut.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCut.setVerticalTextPosition(SwingConstants.BOTTOM);

		JToolBar jToolBar2 = new JToolBar();
		jToolBar2.add(jbCut);

		JButton jbCopy = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/copy.gif")));
		jbCopy.setEnabled(false);
		jbCopy.setFocusable(false);
		jbCopy.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
		jToolBar2.add(jbCopy);

		JButton jbPaste = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/paste.gif")));
		jbPaste.setEnabled(false);
		jbPaste.setFocusable(false);
		jbPaste.setHorizontalTextPosition(SwingConstants.CENTER);
		jbPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
		jToolBar2.add(jbPaste);

		JToolBar jToolBar3 = new JToolBar();
		jToolBar3.setMaximumSize(new Dimension(34, 9));
		jToolBar3.setMinimumSize(new Dimension(34, 9));

		JButton jbRunIdentAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/runid.gif")));
		jbRunIdentAnalysis.setToolTipText("Run Identification Pipeline (F7)");
		jbRunIdentAnalysis.setFocusable(false);
		jbRunIdentAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunIdentAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunIdentAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmRunIdentAnalysisActionPerformed(null, jtRawFiles,
						jtMzIDProtGroup, identParamsExecute, jtaLog,
						jtIdentFiles, aMzIDUnmarshaller, jlRawFilesStatus,
						jlIdentFilesStatus, jtpProperties, jcbPSM, jepMzIDView,
						jlFileNameMzIDText, jtMzId);
			}
		});
		jToolBar3.add(jbRunIdentAnalysis);

		JButton jbRunQuantAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/run.gif")));
		jbRunQuantAnalysis.setToolTipText("Run Quantitation Pipeline (F5)");
		jbRunQuantAnalysis.setFocusable(false);
		jbRunQuantAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunQuantAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunQuantAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmRunQuantAnalysisActionPerformed(jtpLog, jmCloseProject,
						jmSaveProject, jtaLog, jtpProperties,
						jtpMzQuantMLDetail, jbSaveProject, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jcbOutputFormat, jepMZQView,
						jlFileNameMzQText, jtFeatureQuant, jtIdentFiles,
						jtPeptideQuant, jtProteinQuant, jtQuantFiles,
						jtRawFiles);
			}
		});
		jToolBar3.add(jbRunQuantAnalysis);

		final JPanel jpToolBar = new JPanel();
		jpToolBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		jpToolBar.setPreferredSize(new Dimension(933, 32));
		GroupLayout jpToolBarLayout = new GroupLayout(jpToolBar);
		jpToolBar.setLayout(jpToolBarLayout);
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

		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(3);

		JPanel jpLeftPanelView = new JPanel();
		jpLeftPanelView.setBackground(new Color(204, 204, 255));


		JPanel jpProjectDetails = new JPanel();
		jpProjectDetails.setBackground(new Color(255, 255, 255));

		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setBackground(new Color(255, 255, 255));
		jspProjectDetails.setDividerLocation(130);
		jspProjectDetails.setDividerSize(1);
		jspProjectDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JScrollPane jspRawFiles = new JScrollPane();
		jspRawFiles.setBackground(new Color(255, 255, 255));

		jtRawFiles.setModel(new DefaultTableModel(new String[][] {},
				new String[] { "Name", "Path", "Type", "Version" }));
		jtRawFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtRawFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtRawFiles = (JTable) evt.getSource();

				// ... Right click event for displaying mzML data ...//
				if ((evt.getButton() == 1)
						&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(),
								2).toString().equals("mzML"))) {
					loadMzMLView(jtRawFiles.getSelectedRow(), jtRawFiles,
							jtpProperties, jepMzMLView, jlFileNameMzMLText,
							jtMzML);

					// Clear container
					jdpTIC.removeAll();
					JInternalFrame jifViewChromatogram = getInternalFrame();
					jifViewChromatogram.setTitle("Chromatogram <"
							+ jtRawFiles.getValueAt(
									jtRawFiles.getSelectedRow(), 1) + ">");
					jifViewChromatogram.add(ChartChromatogram
							.getChromatogram(aMzMLUnmarshaller.get(jtRawFiles
									.getSelectedRow())));

					jdpTIC.add(jifViewChromatogram);
					jdpTIC.revalidate();
					jdpTIC.repaint();
				}
				// ... Right click event for displaying MGF data ...//
				if ((evt.getButton() == 1)
						&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(),
								2).toString().equals("MGF"))) {
					loadMGFView(jtRawFiles.getSelectedRow(), jlFileNameMGFText,
							jtRawFiles, jtpProperties, jtMGF);
				}
			}
		});
		jspRawFiles.setViewportView(jtRawFiles);

		JLabel jlRawFilesIcon = new JLabel(
				"Raw Files                                 ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/raw_file.gif")), SwingConstants.RIGHT);
		jlRawFilesIcon.setIconTextGap(5);
		final JTabbedPane jtpRawFiles = new JTabbedPane();
		jtpRawFiles.setBackground(new Color(255, 255, 255));
		jtpRawFiles.addTab("Raw Files", jspRawFiles);
		jtpRawFiles.setTabComponentAt(0, jlRawFilesIcon);

		JPanel jpLeftMenuTop = new JPanel();
		GroupLayout jpLeftMenuTopLayout = new GroupLayout(jpLeftMenuTop);
		jpLeftMenuTop.setLayout(jpLeftMenuTopLayout);
		jpLeftMenuTopLayout.setHorizontalGroup(jpLeftMenuTopLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGap(0, 277, Short.MAX_VALUE)
				.addGroup(
						jpLeftMenuTopLayout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addComponent(
								jtpRawFiles, GroupLayout.DEFAULT_SIZE, 277,
								Short.MAX_VALUE)));
		jpLeftMenuTopLayout
				.setVerticalGroup(jpLeftMenuTopLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 129, Short.MAX_VALUE)
						.addGroup(
								jpLeftMenuTopLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(jtpRawFiles,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 129,
												Short.MAX_VALUE)));

		jspProjectDetails.setTopComponent(jpLeftMenuTop);

		final JSplitPane jspLeftMenuBottom = new JSplitPane();
		// ... Ident and Quantitation separator
		jspLeftMenuBottom.setDividerLocation(200);
		jspLeftMenuBottom.setDividerSize(1);
		jspLeftMenuBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jtQuantFiles.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Name", "Path", "Type", "Version" }));
		jtQuantFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtQuantFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtQuantFiles = (JTable) evt.getSource();

				if ((evt.getButton() == 1)
						&& (jtQuantFiles.getValueAt(
								jtQuantFiles.getSelectedRow(), 2).toString()
								.equals("mzq"))) {
					loadMzQuantMLView(
							jtQuantFiles.getSelectedRow(),
							jtQuantFiles.getValueAt(
									jtQuantFiles.getSelectedRow(), 1)
									.toString(), jtFeatureQuant, jtaLog,
							jtpProperties, jtpMzQuantMLDetail, jepMZQView,
							jlFileNameMzQText, jtPeptideQuant, jtProteinQuant,
							jtQuantFiles);
				}
			}
		});
		JScrollPane jspQuantFiles = new JScrollPane();
		jspQuantFiles.setViewportView(jtQuantFiles);

		JLabel jlQuantFilesIcon = new JLabel(
				"Quantitation Files                      ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/quant_file.gif")), SwingConstants.RIGHT);
		jlQuantFilesIcon.setIconTextGap(5);
		JTabbedPane jtpQuantFiles = new JTabbedPane();
		jtpQuantFiles.setBackground(new Color(255, 255, 255));
		jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);
		jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);

		JPanel jpQuantFiles = new JPanel();
		GroupLayout jpQuantFilesLayout = new GroupLayout(jpQuantFiles);
		jpQuantFiles.setLayout(jpQuantFilesLayout);
		jpQuantFilesLayout.setHorizontalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles, GroupLayout.DEFAULT_SIZE, 275,
						Short.MAX_VALUE));
		jpQuantFilesLayout.setVerticalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles, GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));

		jspLeftMenuBottom.setRightComponent(jpQuantFiles);

		jtIdentFiles.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Name", "Path", "Type", "Version", "Raw File" }));
		jtIdentFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtIdentFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtIdentFiles = (JTable) evt.getSource();
				if ((evt.getButton() == 1)
						&& (jtIdentFiles.getValueAt(
								jtIdentFiles.getSelectedRow(), 2).toString()
								.equals("mascot_xml"))) {
					FileFormatMascot.loadMascotView(
							jtIdentFiles.getValueAt(
									jtIdentFiles.getSelectedRow(), 0)
									.toString(),
							jtIdentFiles.getValueAt(
									jtIdentFiles.getSelectedRow(), 1)
									.toString(), jtMascotXMLView, jtpProperties);
				}
				if ((evt.getButton() == 1)
						&& (jtIdentFiles.getValueAt(
								jtIdentFiles.getSelectedRow(), 2).toString()
								.equals("mzid"))) {
					loadMzIdentMLView(
							jtIdentFiles.getSelectedRow(),
							jtIdentFiles.getValueAt(
									jtIdentFiles.getSelectedRow(), 0)
									.toString(), jtMzIDProtGroup,
							jtpProperties, jcbPSM, jepMzIDView,
							jlFileNameMzIDText, jtMzId);
				}
			}
		});
		JScrollPane jspIdentFiles = new JScrollPane();
		jspIdentFiles.setViewportView(jtIdentFiles);

		JLabel jlIdentFilesIcon = new JLabel(
				"Identification Files                    ", new ImageIcon(
						getClass().getClassLoader().getResource(
								"images/ident_file.gif")), SwingConstants.RIGHT);
		jlIdentFilesIcon.setIconTextGap(5);
		JTabbedPane jtpIdentFiles = new JTabbedPane();
		jtpIdentFiles.setBackground(new Color(255, 255, 255));
		jtpIdentFiles.addTab("Identification Files", jspIdentFiles);
		jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);

		jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

		JPanel jpLeftMenuBottom = new JPanel();
		GroupLayout jpLeftMenuBottomLayout = new GroupLayout(jpLeftMenuBottom);
		jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
		jpLeftMenuBottomLayout.setHorizontalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom, GroupLayout.DEFAULT_SIZE, 277,
						Short.MAX_VALUE));
		jpLeftMenuBottomLayout.setVerticalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom, GroupLayout.DEFAULT_SIZE, 483,
						Short.MAX_VALUE));

		jspProjectDetails.setRightComponent(jpLeftMenuBottom);

		GroupLayout jpProjectDetailsLayout = new GroupLayout(jpProjectDetails);
		jpProjectDetails.setLayout(jpProjectDetailsLayout);
		jpProjectDetailsLayout.setHorizontalGroup(jpProjectDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProjectDetails));
		jpProjectDetailsLayout
				.setVerticalGroup(jpProjectDetailsLayout.createParallelGroup(
						GroupLayout.Alignment.LEADING).addComponent(
						jspProjectDetails, GroupLayout.Alignment.TRAILING));

		JSplitPane jspLeftPanelView = new LeftPanelView();
		jspLeftPanelView.setRightComponent(jpProjectDetails);

		GroupLayout jpLeftPanelViewLayout = new GroupLayout(jpLeftPanelView);
		jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
		jpLeftPanelViewLayout.setHorizontalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView, GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));
		jpLeftPanelViewLayout.setVerticalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView, GroupLayout.DEFAULT_SIZE, 637,
						Short.MAX_VALUE));

		jspMainPanelView.setLeftComponent(jpLeftPanelView);

		jspViewerAndProperties.setDividerLocation(380);
		jspViewerAndProperties.setDividerSize(3);

		final JSplitPane jspLeftViewer = new JSplitPane();
		jspLeftViewer.setDividerLocation(20);
		jspLeftViewer.setDividerSize(1);
		jspLeftViewer.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jspLeftViewerHeader = new JPanel();
		jspLeftViewerHeader.setMaximumSize(new Dimension(32767, 25));
		jspLeftViewerHeader.setMinimumSize(new Dimension(380, 25));
		jspLeftViewerHeader.setPreferredSize(new Dimension(380, 25));

		JLabel jlViewer = new JLabel("Viewer");
		jlViewer.setBackground(new Color(255, 255, 255));
		jlViewer.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlViewer.setForeground(new Color(102, 102, 102));

		GroupLayout jspLeftViewerHeaderLayout = new GroupLayout(
				jspLeftViewerHeader);
		jspLeftViewerHeader.setLayout(jspLeftViewerHeaderLayout);
		jspLeftViewerHeaderLayout.setHorizontalGroup(jspLeftViewerHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jspLeftViewerHeaderLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlViewer)
								.addContainerGap(316, Short.MAX_VALUE)));
		jspLeftViewerHeaderLayout.setVerticalGroup(jspLeftViewerHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jspLeftViewerHeaderLayout.createSequentialGroup()
								.addComponent(jlViewer)
								.addContainerGap(7, Short.MAX_VALUE)));

		jspLeftViewer.setTopComponent(jspLeftViewerHeader);

		jspLeftViewerDetails.setDividerLocation(350);
		jspLeftViewerDetails.setDividerSize(2);
		jspLeftViewerDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jtaLog.setColumns(20);
		jtaLog.setFont(new Font("Tahoma", 0, 11)); // NOI18N
		jtaLog.setRows(5);
		JScrollPane jspLog = new JScrollPane();
		jspLog.setViewportView(jtaLog);

		jtpLog.addTab("Log", jspLog);

		JSplitPane jspRawDataValues = new JSplitPane();
		jspRawDataValues.setDividerLocation(40);
		jspRawDataValues.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpRawDataValuesMenu = new JPanel();
		jpRawDataValuesMenu.setMaximumSize(new Dimension(32767, 50));
		jpRawDataValuesMenu.setMinimumSize(new Dimension(0, 50));
		jpRawDataValuesMenu.setPreferredSize(new Dimension(462, 50));

		final JLabel jLabel1 = new JLabel("Search:");
		jLabel1.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		final JTextField jtMSIndex = new JTextField();
		jtMSIndex.setToolTipText("Enter the index");
		jtMSIndex.addKeyListener(new KeyListenerSearch(0, jtRawData, true));

		JLabel jLabel2 = new JLabel("Index:");
		JLabel jLabel3 = new JLabel("m/z:");

		final JTextField jtMSMz = new JTextField();
		jtMSMz.setToolTipText("Enter the m/z value ");
		jtMSMz.addKeyListener(new KeyListenerSearch(1, jtRawData, true));

		GroupLayout jpRawDataValuesMenuLayout = new GroupLayout(
				jpRawDataValuesMenu);
		jpRawDataValuesMenu.setLayout(jpRawDataValuesMenuLayout);
		jpRawDataValuesMenuLayout
				.setHorizontalGroup(jpRawDataValuesMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpRawDataValuesMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel1)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel2)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jtMSIndex,
												GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel3)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jtMSMz,
												GroupLayout.PREFERRED_SIZE, 69,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(103, Short.MAX_VALUE)));
		jpRawDataValuesMenuLayout
				.setVerticalGroup(jpRawDataValuesMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpRawDataValuesMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpRawDataValuesMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(jLabel2)
														.addComponent(
																jtMSIndex,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel3)
														.addComponent(
																jtMSMz,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(19, Short.MAX_VALUE)));

		jspRawDataValues.setLeftComponent(jpRawDataValuesMenu);

		jtRawData.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Index", "m/z", "Intensity" }));
		jtRawData
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane jspRawData = new JScrollPane();
		jspRawData.setViewportView(jtRawData);

		jspRawDataValues.setBottomComponent(jspRawData);

		JPanel jpRawDataValues = new JPanel();
		GroupLayout jpRawDataValuesLayout = new GroupLayout(jpRawDataValues);
		jpRawDataValues.setLayout(jpRawDataValuesLayout);
		jpRawDataValuesLayout.setHorizontalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpRawDataValuesLayout.setVerticalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues, GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE));

		jtpLog.addTab("Raw Data", jpRawDataValues);

		final JTable jtTemplate1 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate1.setModel(new DefaultTableModel(new String[][] {

		},
				new String[] { "m/z Index", "Scan Index", "Quant",
						"Template2 Index" }));
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(jtTemplate1);

		JPanel jPanel1 = new JPanel();
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();
		JPanel jPanel5 = new JPanel();
		JPanel jPanel6 = new JPanel();
		JPanel jPanel7 = new JPanel();

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 51, Short.MAX_VALUE));

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel1Layout
								.createSequentialGroup()
								.addComponent(jPanel5,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										GroupLayout.DEFAULT_SIZE, 175,
										Short.MAX_VALUE)));

		jtpLog.addTab("Template 1", jPanel1);

		final JTable jtTemplate2 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Template Index", "x", "y", "i" }));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jtTemplate2);

		GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 52, Short.MAX_VALUE));

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel6, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel2Layout
								.createSequentialGroup()
								.addComponent(jPanel6,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2,
										GroupLayout.DEFAULT_SIZE, 174,
										Short.MAX_VALUE)));

		jtpLog.addTab("Template 2", jPanel2);

		final JTable jTable1 = new JTable();
		jTable1.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane3 = new JScrollPane();
		jScrollPane3.setViewportView(jTable1);

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 58, Short.MAX_VALUE));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel4, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel3Layout
								.createSequentialGroup()
								.addComponent(jPanel4,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane3,
										GroupLayout.DEFAULT_SIZE, 168,
										Short.MAX_VALUE)));

		jtpLog.addTab("Synth Array", jPanel3);

		final JTable jTable2 = new JTable();
		jTable2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane7 = new JScrollPane();
		jScrollPane7.setViewportView(jTable2);

		GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 57, Short.MAX_VALUE));

		JPanel jpPanel4 = new JPanel();
		GroupLayout jpPanel4Layout = new GroupLayout(jpPanel4);
		jpPanel4.setLayout(jpPanel4Layout);
		jpPanel4Layout.setHorizontalGroup(jpPanel4Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel7, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpPanel4Layout.setVerticalGroup(jpPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jpPanel4Layout
								.createSequentialGroup()
								.addComponent(jPanel7,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane7,
										GroupLayout.DEFAULT_SIZE, 169,
										Short.MAX_VALUE)));

		jtpLog.addTab("Resamp Array", jpPanel4);

		JPanel jpLeftViewerBottom = new JPanel();
		GroupLayout jpLeftViewerBottomLayout = new GroupLayout(
				jpLeftViewerBottom);
		jpLeftViewerBottom.setLayout(jpLeftViewerBottomLayout);
		jpLeftViewerBottomLayout.setHorizontalGroup(jpLeftViewerBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpLog));
		jpLeftViewerBottomLayout.setVerticalGroup(jpLeftViewerBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpLog));

		jspLeftViewerDetails.setRightComponent(jpLeftViewerBottom);

		jdpMS.setBackground(new Color(255, 255, 255));

		jspLeftViewerDetails.setTopComponent(jtpViewer);

		JPanel jpLeftViewerDetails = new JPanel();
		jpLeftViewerDetails.setBackground(new Color(255, 255, 255));
		GroupLayout jpLeftViewerDetailsLayout = new GroupLayout(
				jpLeftViewerDetails);
		jpLeftViewerDetails.setLayout(jpLeftViewerDetailsLayout);
		jpLeftViewerDetailsLayout.setHorizontalGroup(jpLeftViewerDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewerDetails));
		jpLeftViewerDetailsLayout.setVerticalGroup(jpLeftViewerDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewerDetails));

		jspLeftViewer.setRightComponent(jpLeftViewerDetails);

		JPanel jpLeftViewer = new JPanel();
		GroupLayout jpLeftViewerLayout = new GroupLayout(jpLeftViewer);
		jpLeftViewer.setLayout(jpLeftViewerLayout);
		jpLeftViewerLayout.setHorizontalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer));
		jpLeftViewerLayout.setVerticalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer, GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));

		jspViewerAndProperties.setLeftComponent(jpLeftViewer);

		final JSplitPane jspProperties = new JSplitPane();
		jspProperties.setDividerLocation(20);
		jspProperties.setDividerSize(1);
		jspProperties.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpProperties = new JPanel();
		jpProperties.setMaximumSize(new Dimension(32767, 25));
		jpProperties.setMinimumSize(new Dimension(302, 25));
		jpProperties.setPreferredSize(new Dimension(313, 25));

		JLabel jlProperties = new JLabel("Data and Metadata");
		jlProperties.setBackground(new Color(255, 255, 255));
		jlProperties.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlProperties.setForeground(new Color(102, 102, 102));

		GroupLayout jpPropertiesLayout = new GroupLayout(jpProperties);
		jpProperties.setLayout(jpPropertiesLayout);
		jpPropertiesLayout.setHorizontalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpPropertiesLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlProperties)
								.addContainerGap(417, Short.MAX_VALUE)));
		jpPropertiesLayout.setVerticalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jpPropertiesLayout.createSequentialGroup()
								.addComponent(jlProperties)
								.addContainerGap(7, Short.MAX_VALUE)));

		jspProperties.setTopComponent(jpProperties);

		jtpProperties.setBackground(new Color(255, 255, 255));

		jspMzML.setBackground(new Color(255, 255, 255));
		jspMzML.setDividerLocation(110);
		jspMzML.setDividerSize(1);
		jspMzML.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane jspMzMLDetail = new JSplitPane();
		jspMzMLDetail.setDividerLocation(90);
		jspMzMLDetail.setDividerSize(1);
		jspMzMLDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpMzMLMenu = new JPanel();
		jpMzMLMenu.setBorder(BorderFactory.createEtchedBorder());

		JLabel jlScan = new JLabel("Scan Index:");

		final JTextField jtScan = new JTextField();
		jtScan.setToolTipText("Enter the scan number");
		jtScan.addKeyListener(new KeyListenerSearch(0, jtMzML, false));

		JLabel jlRT = new JLabel("RT (sec):");
		final JTextField jtRT = new JTextField();

		jtRT.setToolTipText("Enter a retention time value (in seconds)");
		jtRT.addKeyListener(new KeyListenerSearch(5, jtMzML, false));

		JLabel jlSearchMzML = new JLabel("Search:");
		jlSearchMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JToolBar jtbMzMLOptions = new JToolBar();
		jtbMzMLOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMzMLOptions.setFloatable(false);
		jtbMzMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzMLExcel
				.addMouseListener(new MouseListenerExcelExport(jtMzML));
		jtbMzMLOptions.add(jbExportMzMLExcel);

		JLabel jlExportMzMLXLS = new JLabel("Export to:");

		GroupLayout jpMzMLMenuLayout = new GroupLayout(jpMzMLMenu);
		jpMzMLMenu.setLayout(jpMzMLMenuLayout);
		jpMzMLMenuLayout
				.setHorizontalGroup(jpMzMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jlSearchMzML)
														.addComponent(
																jlExportMzMLXLS))
										.addGap(18, 18, 18)
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jtbMzMLOptions,
																GroupLayout.Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlScan)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jtScan,
																				GroupLayout.PREFERRED_SIZE,
																				62,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlRT)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtRT,
																				GroupLayout.PREFERRED_SIZE,
																				62,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(210, Short.MAX_VALUE)));
		jpMzMLMenuLayout
				.setVerticalGroup(jpMzMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(jtScan)
														.addComponent(jtRT)
														.addGroup(
																jpMzMLMenuLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzML)
																		.addComponent(
																				jlScan)
																		.addComponent(
																				jlRT)))
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzMLOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addGap(17,
																				17,
																				17)
																		.addComponent(
																				jlExportMzMLXLS)))
										.addGap(13, 13, 13)));

		jspMzMLDetail.setLeftComponent(jpMzMLMenu);

		jtMzML.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "", "Index", "ID", "MS", "Base peak m/z",
				"Base peak int", "RT (sec)", "Precurs m/z" }));
		jtMzML.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtMzML.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtMzML = (JTable) evt.getSource();

				// Right click event for displaying MS raw data and spectrum
				if (evt.getButton() == 1) {
					if (jtRawFiles.getSelectedRow() > -1) {
						showRawData(jtRawFiles.getSelectedRow(), jtMzML
								.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), jtRawData, jtpLog,
								aMzMLUnmarshaller);

						ChartSpectrum.getSpectrum(aMzMLUnmarshaller
								.get(jtRawFiles.getSelectedRow()), jtMzML
								.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), jdpMS, getInternalFrame());
					} else {
						showRawData(0,
								jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
										.toString(), jtRawData, jtpLog,
								aMzMLUnmarshaller);

						ChartSpectrum.getSpectrum(aMzMLUnmarshaller.get(0),
								jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
										.toString(), jdpMS, getInternalFrame());
					}
					jtpViewer.setSelectedIndex(0);
				}
			}
		});
		JScrollPane jspMzMLSubDetail = new JScrollPane();
		jspMzMLSubDetail.setViewportView(jtMzML);

		jspMzMLDetail.setRightComponent(jspMzMLSubDetail);

		jspMzML.setBottomComponent(jspMzMLDetail);

		JLabel jlFileNameMzML = new JLabel("File:");
		jlFileNameMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		jepMzMLView.setContentType("text/html");
		jepMzMLView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMzMLView.setPreferredSize(new Dimension(144, 84));
		JScrollPane jScrollPane9 = new JScrollPane();
		jScrollPane9.setViewportView(jepMzMLView);

		JPanel jpMzMLViewHeader = new JPanel();
		GroupLayout jpMzMLViewHeaderLayout = new GroupLayout(jpMzMLViewHeader);
		jpMzMLViewHeader.setLayout(jpMzMLViewHeaderLayout);
		jpMzMLViewHeaderLayout
				.setHorizontalGroup(jpMzMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzML)
																		.addGap(38,
																				38,
																				38)
																		.addComponent(
																				jlFileNameMzMLText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane9,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzMLViewHeaderLayout
				.setVerticalGroup(jpMzMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												jpMzMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMzML)
														.addComponent(
																jlFileNameMzMLText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane9,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(18, Short.MAX_VALUE)));

		jspMzML.setLeftComponent(jpMzMLViewHeader);

		JPanel jpMzML = new JPanel();
		GroupLayout jpMzMLLayout = new GroupLayout(jpMzML);
		jpMzML.setLayout(jpMzMLLayout);
		jpMzMLLayout.setHorizontalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzML));
		jpMzMLLayout.setVerticalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzML,
				GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE));

		jtpProperties.addTab("mzML View", jpMzML);

		JSplitPane jspMGF = new JSplitPane();
		jspMGF.setDividerLocation(110);
		jspMGF.setDividerSize(1);
		jspMGF.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpMGFViewHeader = new JPanel();
		jpMGFViewHeader.setPreferredSize(new Dimension(604, 115));

		JLabel jlFileNameMGF = new JLabel("File:");
		jlFileNameMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		jepMGFView.setContentType("text/html");
		jepMGFView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMGFView.setPreferredSize(new Dimension(144, 84));
		JScrollPane jScrollPane10 = new JScrollPane();
		jScrollPane10.setViewportView(jepMGFView);

		GroupLayout jpMGFViewHeaderLayout = new GroupLayout(jpMGFViewHeader);
		jpMGFViewHeader.setLayout(jpMGFViewHeaderLayout);
		jpMGFViewHeaderLayout
				.setHorizontalGroup(jpMGFViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMGFViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMGF)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMGFText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane10,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMGFViewHeaderLayout
				.setVerticalGroup(jpMGFViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFViewHeaderLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												jpMGFViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMGFText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlFileNameMGF))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane10,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(19, Short.MAX_VALUE)));

		jspMGF.setLeftComponent(jpMGFViewHeader);

		JSplitPane jspMGFDetail = new JSplitPane();
		jspMGFDetail.setDividerLocation(90);
		jspMGFDetail.setDividerSize(1);
		jspMGFDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JLabel jlSearchMGF = new JLabel("Search:");
		jlSearchMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlScanTitle = new JLabel("Scan title:");

		final JTextField jtScanTitle = new JTextField();
		jtScanTitle
				.setToolTipText("Enter any character(s) that is (are) specified in the scan title");
		jtScanTitle.addKeyListener(new KeyListenerSearch(1, jtMGF, false));

		JToolBar jtbMGFOptions = new JToolBar();
		jtbMGFOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMGFOptions.setFloatable(false);
		jtbMGFOptions.setMaximumSize(new Dimension(30, 23));
		jtbMGFOptions.setMinimumSize(new Dimension(30, 23));
		jtbMGFOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMGFExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMGFExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMGFExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMGFExcel.addMouseListener(new MouseListenerExcelExport(jtMGF));
		jtbMGFOptions.add(jbExportMGFExcel);

		JLabel jlExportMGFXLS = new JLabel("Export To:");
		JPanel jpMGFMenu = new JPanel();
		GroupLayout jpMGFMenuLayout = new GroupLayout(jpMGFMenu);
		jpMGFMenu.setLayout(jpMGFMenuLayout);
		jpMGFMenuLayout
				.setHorizontalGroup(jpMGFMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jlSearchMGF)
														.addComponent(
																jlExportMGFXLS))
										.addGap(18, 18, 18)
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jtbMGFOptions,
																GroupLayout.Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlScanTitle)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jtScanTitle,
																				GroupLayout.PREFERRED_SIZE,
																				223,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(197, Short.MAX_VALUE)));
		jpMGFMenuLayout
				.setVerticalGroup(jpMGFMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlSearchMGF)
														.addComponent(
																jlScanTitle)
														.addComponent(
																jtScanTitle,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMGFOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlExportMGFXLS)))
										.addContainerGap(17, Short.MAX_VALUE)));

		jspMGFDetail.setTopComponent(jpMGFMenu);

		jtMGF.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Index", "Scan Title", "Peptide Mass", "Charge",
				"Reference Line" }));
		jtMGF.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtMGF = (JTable) evt.getSource();

				// ... Validate right click selection for MGF files ...//
				if (evt.getButton() == 1) {
					if (jtRawFiles.getSelectedRow() > -1) {
						showRawDataMGF(jtRawFiles.getSelectedRow(), jtMGF
								.getValueAt(jtMGF.getSelectedRow(), 4)
								.toString(), jtRawData, jtRawFiles, jtpLog);
					} else {
						showRawDataMGF(0,
								jtMGF.getValueAt(jtMGF.getSelectedRow(), 4)
										.toString(), jtRawData, jtRawFiles,
								jtpLog);
					}
					jtpViewer.setSelectedIndex(0);
				}
			}
		});
		JScrollPane jspMGFSubDetail = new JScrollPane();
		jspMGFSubDetail.setViewportView(jtMGF);

		jspMGFDetail.setRightComponent(jspMGFSubDetail);

		jspMGF.setRightComponent(jspMGFDetail);

		JPanel jpMGF = new JPanel();
		GroupLayout jpMGFLayout = new GroupLayout(jpMGF);
		jpMGF.setLayout(jpMGFLayout);
		jpMGFLayout.setHorizontalGroup(jpMGFLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMGF,
				GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE));
		jpMGFLayout.setVerticalGroup(jpMGFLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMGF,
				GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE));

		jtpProperties.addTab("MGF View", jpMGF);

		JSplitPane jspMzId = new JSplitPane();
		jspMzId.setDividerLocation(110);
		jspMzId.setDividerSize(1);
		jspMzId.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane jspMzIdDetail = new JSplitPane();
		jspMzIdDetail.setDividerLocation(300);
		jspMzIdDetail.setDividerSize(1);
		jspMzIdDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JLabel jlSearchMzId = new JLabel("Search:");
		jlSearchMzId.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlProteinMzId = new JLabel("Protein:");

		final JTextField jtProteinMzId = new JTextField();
		jtProteinMzId.setToolTipText("Enter protein");
		jtProteinMzId.addKeyListener(new KeyListenerSearch(1, jtMzId, true));

		JLabel jlPeptideMzId = new JLabel("Peptide:");

		final JTextField jtPeptideMzId = new JTextField();
		jtPeptideMzId.setToolTipText("Enter peptide sequence");
		jtPeptideMzId.addKeyListener(new KeyListenerSearch(2, jtMzId, true));

		JToolBar jtbMzIdentMLOptions = new JToolBar();
		jtbMzIdentMLOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMzIdentMLOptions.setFloatable(false);
		jtbMzIdentMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzIdentMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.addMouseListener(new MouseListenerExcelExport(
				jtMzId));
		jtbMzIdentMLOptions.add(jbExportMzIdentMLExcel);

		JLabel jlExportMzIDXLS = new JLabel("Export to:");
		jtMzId.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Index", "Protein", "Peptide", "Rank", "Score",
				"Spectrum ID" }));
		JScrollPane jspMzIdProtGroup = new JScrollPane();
		jspMzIdProtGroup.setViewportView(jtMzId);

		JLabel jlPeptideSpectrumMatches = new JLabel(
				"Peptide-Spectrum matches with rank:");

		jcbPSM.setModel(new DefaultComboBoxModel<String>(new String[] { "<=1",
				"<=2", "<=3", "All" }));
		jcbPSM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// ... Check file to visualize .../
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

		JPanel jpMzIdMenu = new JPanel();
		GroupLayout jpMzIdMenuLayout = new GroupLayout(jpMzIdMenu);
		jpMzIdMenu.setLayout(jpMzIdMenuLayout);
		jpMzIdMenuLayout
				.setHorizontalGroup(jpMzIdMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspMzIdProtGroup,
								GroupLayout.Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
						.addGroup(
								jpMzIdMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpMzIdMenuLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlSearchMzId)
																						.addComponent(
																								jlExportMzIDXLS))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpMzIdMenuLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								jtbMzIdentMLOptions,
																								GroupLayout.Alignment.TRAILING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								jpMzIdMenuLayout
																										.createSequentialGroup()
																										.addComponent(
																												jlProteinMzId)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jtProteinMzId,
																												GroupLayout.PREFERRED_SIZE,
																												79,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jlPeptideMzId)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jtPeptideMzId,
																												GroupLayout.PREFERRED_SIZE,
																												191,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlPeptideSpectrumMatches)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jcbPSM,
																				GroupLayout.PREFERRED_SIZE,
																				82,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		jpMzIdMenuLayout
				.setVerticalGroup(jpMzIdMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlSearchMzId)
														.addComponent(
																jlProteinMzId)
														.addComponent(
																jtProteinMzId,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlPeptideMzId)
														.addComponent(
																jtPeptideMzId,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzIdentMLOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addGap(16,
																				16,
																				16)
																		.addComponent(
																				jlExportMzIDXLS)))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jcbPSM,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlPeptideSpectrumMatches))
										.addGap(18, 18, 18)
										.addComponent(jspMzIdProtGroup,
												GroupLayout.DEFAULT_SIZE, 155,
												Short.MAX_VALUE)
										.addGap(23, 23, 23)));

		jspMzIdDetail.setTopComponent(jpMzIdMenu);

		jtMzIDProtGroup.setAutoCreateRowSorter(true);
		jtMzIDProtGroup.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "ID", "Name", "Protein Accesions",
				"Representative Protein", "Scores", "P-values",
				"Number of peptides", "Is Decoy", "passThreshold" }));

		JScrollPane jspMzIDProtGroup = new JScrollPane();
		jspMzIDProtGroup.setViewportView(jtMzIDProtGroup);

		jspMzIdDetail.setRightComponent(jspMzIDProtGroup);

		jspMzId.setRightComponent(jspMzIdDetail);

		JLabel jlFileNameMzID = new JLabel("File:");
		jlFileNameMzID.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		jepMzIDView.setContentType("text/html");
		jepMzIDView.setPreferredSize(new Dimension(144, 84));
		JScrollPane jspMzIDView = new JScrollPane();
		jspMzIDView.setViewportView(jepMzIDView);

		JPanel jpMzIdentMLViewHeader = new JPanel();
		GroupLayout jpMzIdentMLViewHeaderLayout = new GroupLayout(
				jpMzIdentMLViewHeader);
		jpMzIdentMLViewHeader.setLayout(jpMzIdentMLViewHeaderLayout);
		jpMzIdentMLViewHeaderLayout
				.setHorizontalGroup(jpMzIdentMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdentMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdentMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdentMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzID)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jlFileNameMzIDText,
																				GroupLayout.PREFERRED_SIZE,
																				308,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jspMzIDView,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzIdentMLViewHeaderLayout
				.setVerticalGroup(jpMzIdentMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdentMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jpMzIdentMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMzID)
														.addComponent(
																jlFileNameMzIDText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jspMzIDView,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(17, Short.MAX_VALUE)));

		jspMzId.setLeftComponent(jpMzIdentMLViewHeader);

		JPanel jpMzId = new JPanel();
		GroupLayout jpMzIdLayout = new GroupLayout(jpMzId);
		jpMzId.setLayout(jpMzIdLayout);
		jpMzIdLayout.setHorizontalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzId));
		jpMzIdLayout.setVerticalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzId,
				GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 585,
				Short.MAX_VALUE));

		jtpProperties.addTab("mzIdentML View", jpMzId);

		JSplitPane jspMascotXML = new JSplitPane();
		jspMascotXML.setDividerLocation(110);
		jspMascotXML.setDividerSize(1);
		jspMascotXML.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane jspMascotXMLSubDetail = new JSplitPane();
		jspMascotXMLSubDetail.setDividerLocation(80);
		jspMascotXMLSubDetail.setDividerSize(1);
		jspMascotXMLSubDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jtMascotXMLView.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Index", "Protein", "Peptide", "Composition",
				"Exp Mz", "Exp Mr", "Charge", "Score", "Scan", "Scan ID",
				"RT (sec)" }));
		JScrollPane jspMascotXMLDetail = new JScrollPane();
		jspMascotXMLDetail.setViewportView(jtMascotXMLView);

		jspMascotXMLSubDetail.setRightComponent(jspMascotXMLDetail);

		JLabel jlSearchMascotXML = new JLabel("Search:");
		jlSearchMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlProtein = new JLabel("Protein:");

		final JTextField jtProtein = new JTextField();
		jtProtein
				.addKeyListener(new KeyListenerSearch(1, jtMascotXMLView, true));

		JLabel jlPeptide = new JLabel("Peptide:");

		final JTextField jtPeptide = new JTextField();
		jtPeptide
				.addKeyListener(new KeyListenerSearch(2, jtMascotXMLView, true));

		JLabel jlExportMzIDXLS1 = new JLabel("Export to:");

		JToolBar jtbMzIdentMLOptions1 = new JToolBar();
		jtbMzIdentMLOptions1.setBorder(BorderFactory.createEtchedBorder());
		jtbMzIdentMLOptions1.setFloatable(false);
		jtbMzIdentMLOptions1.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setPreferredSize(new Dimension(100, 23));

		jbExportMascotXMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.addMouseListener(new MouseListenerExcelExport(
				jtMascotXMLView));

		jtbMzIdentMLOptions1.add(jbExportMascotXMLExcel);

		JPanel jpMascotXMLMenu = new JPanel();
		GroupLayout jpMascotXMLMenuLayout = new GroupLayout(jpMascotXMLMenu);
		jpMascotXMLMenu.setLayout(jpMascotXMLMenuLayout);
		jpMascotXMLMenuLayout
				.setHorizontalGroup(jpMascotXMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlSearchMascotXML)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jlProtein)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtProtein,
																				GroupLayout.PREFERRED_SIZE,
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(14,
																				14,
																				14)
																		.addComponent(
																				jlPeptide)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtPeptide,
																				GroupLayout.PREFERRED_SIZE,
																				211,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlExportMzIDXLS1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtbMzIdentMLOptions1,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap(112, Short.MAX_VALUE)));
		jpMascotXMLMenuLayout
				.setVerticalGroup(jpMascotXMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jtPeptide,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlSearchMascotXML)
														.addComponent(jlProtein)
														.addComponent(
																jtProtein,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jlPeptide))
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addGap(16,
																				16,
																				16)
																		.addComponent(
																				jlExportMzIDXLS1))
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzIdentMLOptions1,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jspMascotXMLSubDetail.setLeftComponent(jpMascotXMLMenu);

		jspMascotXML.setRightComponent(jspMascotXMLSubDetail);

		JLabel jlFileNameMascotXML = new JLabel("File:");
		jlFileNameMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		jepMascotXMLView.setContentType("text/html");
		jepMascotXMLView.setFont(new Font("Tahoma", 0, 10)); // NOI18N
		jepMascotXMLView.setPreferredSize(new Dimension(144, 84));
		JScrollPane jspMascotXMLView = new JScrollPane();
		jspMascotXMLView.setViewportView(jepMascotXMLView);
		final JLabel jlFileNameMascotXMLText = new JLabel();

		JPanel jpMascotXMLViewHeader = new JPanel();
		GroupLayout jpMascotXMLViewHeaderLayout = new GroupLayout(
				jpMascotXMLViewHeader);
		jpMascotXMLViewHeader.setLayout(jpMascotXMLViewHeaderLayout);
		jpMascotXMLViewHeaderLayout
				.setHorizontalGroup(jpMascotXMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMascotXMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMascotXML)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMascotXMLText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jspMascotXMLView,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMascotXMLViewHeaderLayout
				.setVerticalGroup(jpMascotXMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												jpMascotXMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMascotXMLText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlFileNameMascotXML))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jspMascotXMLView,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(19, Short.MAX_VALUE)));

		jspMascotXML.setLeftComponent(jpMascotXMLViewHeader);

		JPanel jpMascotXML = new JPanel();
		GroupLayout jpMascotXMLLayout = new GroupLayout(jpMascotXML);
		jpMascotXML.setLayout(jpMascotXMLLayout);
		jpMascotXMLLayout.setHorizontalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML));
		jpMascotXMLLayout.setVerticalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML, GroupLayout.DEFAULT_SIZE, 585,
						Short.MAX_VALUE));

		jtpProperties.addTab("Mascot XML View", jpMascotXML);

		JSplitPane jspMzQuantML = new JSplitPane();
		jspMzQuantML.setDividerLocation(110);
		jspMzQuantML.setDividerSize(1);
		jspMzQuantML.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane jspPeptideQuant = new JSplitPane();
		jspPeptideQuant.setDividerLocation(40);
		jspPeptideQuant.setDividerSize(1);
		jspPeptideQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JLabel jlSearchMzQPep = new JLabel("Search:");
		jlSearchMzQPep.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlPeptideMZQ = new JLabel("Peptide:");

		final JTextField jtPeptideMZQ = new JTextField();
		jtPeptideMZQ.setToolTipText("Enter the scan number");
		jtPeptideMZQ.addKeyListener(new KeyListenerSearch(0, jtPeptideQuant,
				false));

		JToolBar jtbPepMZQ = new JToolBar();
		jtbPepMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbPepMZQ.setFloatable(false);
		jtbPepMZQ.setMaximumSize(new Dimension(30, 23));
		jtbPepMZQ.setMinimumSize(new Dimension(30, 23));
		jtbPepMZQ.setPreferredSize(new Dimension(100, 23));

		jbExportPepMZQExcel.setIconTextGap(1);
		jbExportPepMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportPepMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtPeptideQuant));
		jtbPepMZQ.add(jbExportPepMZQExcel);

		JPanel jpPeptideQuantHeader = new JPanel();
		GroupLayout jpPeptideQuantHeaderLayout = new GroupLayout(
				jpPeptideQuantHeader);
		jpPeptideQuantHeader.setLayout(jpPeptideQuantHeaderLayout);
		jpPeptideQuantHeaderLayout
				.setHorizontalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPeptideQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQPep)
										.addGap(18, 18, 18)
										.addComponent(jlPeptideMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtPeptideMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbPepMZQ,
												GroupLayout.DEFAULT_SIZE, 193,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpPeptideQuantHeaderLayout
				.setVerticalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPeptideQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpPeptideQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbPepMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpPeptideQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQPep)
																		.addComponent(
																				jlPeptideMZQ)
																		.addComponent(
																				jtPeptideMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		jspPeptideQuant.setTopComponent(jpPeptideQuantHeader);

		jtPeptideQuant.setModel(new DefaultTableModel(new String[][] {

		},
				new String[] { "Peptide", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspPeptideQuantDetail = new JScrollPane();
		jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

		jspPeptideQuant.setRightComponent(jspPeptideQuantDetail);

		JPanel jpPeptideQuant = new JPanel();
		GroupLayout jpPeptideQuantLayout = new GroupLayout(jpPeptideQuant);
		jpPeptideQuant.setLayout(jpPeptideQuantLayout);
		jpPeptideQuantLayout.setHorizontalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant));
		jpPeptideQuantLayout.setVerticalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));

		jtpMzQuantMLDetail.addTab("Peptide Quantitation", jpPeptideQuant);

		JSplitPane jspProteinQuant = new JSplitPane();
		jspProteinQuant.setDividerLocation(40);
		jspProteinQuant.setDividerSize(1);
		jspProteinQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JLabel jlSearchMzQProt = new JLabel("Search:");
		jlSearchMzQProt.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlProteinMZQ = new JLabel("Protein:");

		final JTextField jtProteinMZQ = new JTextField();
		jtProteinMZQ.setToolTipText("Enter the scan number");
		jtProteinMZQ.addKeyListener(new KeyListenerSearch(0, jtProteinQuant,
				false));

		JToolBar jtbProtMZQ = new JToolBar();
		jtbProtMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbProtMZQ.setFloatable(false);
		jtbProtMZQ.setMaximumSize(new Dimension(30, 23));
		jtbProtMZQ.setMinimumSize(new Dimension(30, 23));
		jtbProtMZQ.setPreferredSize(new Dimension(100, 23));

		jbExportProtMZQExcel.setIconTextGap(1);
		jbExportProtMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportProtMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtProteinQuant));

		jtbProtMZQ.add(jbExportProtMZQExcel);

		JPanel jpProteinQuantHeader = new JPanel();
		GroupLayout jpProteinQuantHeaderLayout = new GroupLayout(
				jpProteinQuantHeader);
		jpProteinQuantHeader.setLayout(jpProteinQuantHeaderLayout);
		jpProteinQuantHeaderLayout
				.setHorizontalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProteinQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQProt)
										.addGap(18, 18, 18)
										.addComponent(jlProteinMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtProteinMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbProtMZQ,
												GroupLayout.DEFAULT_SIZE, 195,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpProteinQuantHeaderLayout
				.setVerticalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProteinQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpProteinQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbProtMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpProteinQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQProt)
																		.addComponent(
																				jlProteinMZQ)
																		.addComponent(
																				jtProteinMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		jspProteinQuant.setTopComponent(jpProteinQuantHeader);

		jtProteinQuant.setModel(new DefaultTableModel(new String[][] {

		},
				new String[] { "Protein", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspProteinQuantDetail = new JScrollPane();
		jspProteinQuantDetail.setViewportView(jtProteinQuant);

		jspProteinQuant.setRightComponent(jspProteinQuantDetail);

		JPanel jpProteinQuant = new JPanel();
		GroupLayout jpProteinQuantLayout = new GroupLayout(jpProteinQuant);
		jpProteinQuant.setLayout(jpProteinQuantLayout);
		jpProteinQuantLayout.setHorizontalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant));
		jpProteinQuantLayout.setVerticalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));

		jtpMzQuantMLDetail.addTab("Protein Quantitation", jpProteinQuant);

		JSplitPane jspFeatureQuant = new JSplitPane();
		jspFeatureQuant.setDividerLocation(40);
		jspFeatureQuant.setDividerSize(1);
		jspFeatureQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JLabel jlSearchMzQFeat = new JLabel("Search:");
		jlSearchMzQFeat.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlFeatureMZQ = new JLabel("Feature:");

		final JTextField jtFeatureMZQ = new JTextField();
		jtFeatureMZQ.setToolTipText("Enter the scan number");
		jtFeatureMZQ.addKeyListener(new KeyListenerSearch(0, jtFeatureQuant,
				false));

		JToolBar jtbFeatMZQ = new JToolBar();
		jtbFeatMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbFeatMZQ.setFloatable(false);
		jtbFeatMZQ.setMaximumSize(new Dimension(30, 23));
		jtbFeatMZQ.setMinimumSize(new Dimension(30, 23));
		jtbFeatMZQ.setPreferredSize(new Dimension(100, 23));

		jbExportFeatMZQExcel.setIconTextGap(1);
		jbExportFeatMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtFeatureQuant));
		jtbFeatMZQ.add(jbExportFeatMZQExcel);

		JPanel jpFeatureQuantHeader = new JPanel();
		GroupLayout jpFeatureQuantHeaderLayout = new GroupLayout(
				jpFeatureQuantHeader);
		jpFeatureQuantHeader.setLayout(jpFeatureQuantHeaderLayout);
		jpFeatureQuantHeaderLayout
				.setHorizontalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFeatureQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQFeat)
										.addGap(18, 18, 18)
										.addComponent(jlFeatureMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtFeatureMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbFeatMZQ,
												GroupLayout.DEFAULT_SIZE, 191,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpFeatureQuantHeaderLayout
				.setVerticalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFeatureQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpFeatureQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbFeatMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpFeatureQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQFeat)
																		.addComponent(
																				jlFeatureMZQ)
																		.addComponent(
																				jtFeatureMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		jspFeatureQuant.setTopComponent(jpFeatureQuantHeader);

		jtFeatureQuant.setModel(new DefaultTableModel(new String[][] {

		},
				new String[] { "Feature", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspFeatureQuantDetail = new JScrollPane();
		jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

		jspFeatureQuant.setRightComponent(jspFeatureQuantDetail);

		final JPanel FeatureQuant = new JPanel();
		GroupLayout FeatureQuantLayout = new GroupLayout(FeatureQuant);
		FeatureQuant.setLayout(FeatureQuantLayout);
		FeatureQuantLayout.setHorizontalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant, GroupLayout.Alignment.TRAILING));
		FeatureQuantLayout.setVerticalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));

		jtpMzQuantMLDetail.addTab("Feature Quantitation", FeatureQuant);

		jspMzQuantML.setRightComponent(jtpMzQuantMLDetail);

		JLabel jlFileNameMzQ = new JLabel("File:");
		jlFileNameMzQ.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		jepMZQView.setContentType("text/html");
		jepMZQView.setPreferredSize(new Dimension(144, 84));
		JScrollPane jScrollPane12 = new JScrollPane();
		jScrollPane12.setViewportView(jepMZQView);

		JPanel jpMzQuantMLHeader = new JPanel();
		GroupLayout jpMzQuantMLHeaderLayout = new GroupLayout(jpMzQuantMLHeader);
		jpMzQuantMLHeader.setLayout(jpMzQuantMLHeaderLayout);
		jpMzQuantMLHeaderLayout
				.setHorizontalGroup(jpMzQuantMLHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzQuantMLHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzQuantMLHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzQuantMLHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzQ)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMzQText,
																				GroupLayout.PREFERRED_SIZE,
																				265,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane12,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzQuantMLHeaderLayout
				.setVerticalGroup(jpMzQuantMLHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzQuantMLHeaderLayout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												jpMzQuantMLHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jlFileNameMzQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jlFileNameMzQText,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jScrollPane12,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(13, Short.MAX_VALUE)));

		jspMzQuantML.setLeftComponent(jpMzQuantMLHeader);

		JPanel jpMzQuantML = new JPanel();
		GroupLayout jpMzQuantMLLayout = new GroupLayout(jpMzQuantML);
		jpMzQuantML.setLayout(jpMzQuantMLLayout);
		jpMzQuantMLLayout.setHorizontalGroup(jpMzQuantMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMzQuantML));
		jpMzQuantMLLayout.setVerticalGroup(jpMzQuantMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMzQuantML));

		jtpProperties.addTab("mzQuantML View", jpMzQuantML);

		JPanel jpPropetiesTab = new JPanel();
		jpPropetiesTab.setBackground(new Color(255, 255, 255));
		jpPropetiesTab.setForeground(new Color(153, 153, 255));
		GroupLayout jpPropetiesTabLayout = new GroupLayout(jpPropetiesTab);
		jpPropetiesTab.setLayout(jpPropetiesTabLayout);
		jpPropetiesTabLayout.setHorizontalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));
		jpPropetiesTabLayout.setVerticalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));

		jspProperties.setRightComponent(jpPropetiesTab);

		JPanel jpPropertiesBox = new JPanel();
		GroupLayout jpPropertiesBoxLayout = new GroupLayout(jpPropertiesBox);
		jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
		jpPropertiesBoxLayout.setHorizontalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties));
		jpPropertiesBoxLayout.setVerticalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties, GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));

		jspViewerAndProperties.setRightComponent(jpPropertiesBox);

		JPanel jpViewerAndProperties = new JPanel();
		jpViewerAndProperties.setBackground(new Color(204, 204, 255));
		GroupLayout jpViewerAndPropertiesLayout = new GroupLayout(
				jpViewerAndProperties);
		jpViewerAndProperties.setLayout(jpViewerAndPropertiesLayout);
		jpViewerAndPropertiesLayout
				.setHorizontalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties,
								GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE));
		jpViewerAndPropertiesLayout
				.setVerticalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties));

		jspMainPanelView.setRightComponent(jpViewerAndProperties);

		final JPanel jpMainPanelView = new JPanel();
		GroupLayout jpMainPanelViewLayout = new GroupLayout(jpMainPanelView);
		jpMainPanelView.setLayout(jpMainPanelViewLayout);

		jpMainPanelViewLayout.setHorizontalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView, GroupLayout.DEFAULT_SIZE, 1242,
						Short.MAX_VALUE));
		jpMainPanelViewLayout.setVerticalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView));

		JLabel jlRawFiles = new JLabel("1) Raw Files:");
		JLabel jlIdentFiles = new JLabel("2) Identification Files:");
		JLabel jlTechnique = new JLabel("3) Technique:");
		JLabel jlQuantFiles = new JLabel("4) Quant Files:");

		jlQuantFiles.setBackground(new Color(255, 255, 255));

		jlRawFilesStatus.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("images/empty.gif")));
		jlRawFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlRawFilesStatus.setForeground(new Color(51, 102, 0));

		jlIdentFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlIdentFilesStatus.setForeground(new Color(51, 102, 0));

		JLabel jlQuantFilesStatus = new JLabel(new ImageIcon(getClass()
				.getClassLoader().getResource("images/empty.gif")));
		jlQuantFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlQuantFilesStatus.setForeground(new Color(51, 102, 0));

		jcbTechnique.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Select technique", "iTRAQ", "TMT", "emPAI" }));
		jcbTechnique.setToolTipText("Select a technique for the analysis");
		jcbTechnique.setBorder(BorderFactory.createEtchedBorder());

		jcbOutputFormat.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Select format", "mzq" }));
		jcbOutputFormat.setToolTipText("Select an output format");

		final JPanel jpProjectStatus = new JPanel();
		jpProjectStatus.setBorder(BorderFactory.createEtchedBorder());
		jpProjectStatus.setForeground(new Color(153, 153, 153));
		jpProjectStatus.setPreferredSize(new Dimension(102, 30));
		
		GroupLayout jpProjectStatusLayout = new GroupLayout(jpProjectStatus);
		jpProjectStatus.setLayout(jpProjectStatusLayout);
		jpProjectStatusLayout
				.setHorizontalGroup(jpProjectStatusLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProjectStatusLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlRawFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlRawFilesStatus,
												GroupLayout.PREFERRED_SIZE, 37,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlIdentFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlIdentFilesStatus,
												GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlTechnique)
										.addGap(18, 18, 18)
										.addComponent(jcbTechnique,
												GroupLayout.PREFERRED_SIZE,
												119, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlQuantFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jcbOutputFormat,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlQuantFilesStatus,
												GroupLayout.PREFERRED_SIZE, 43,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(499, Short.MAX_VALUE)));
		jpProjectStatusLayout
				.setVerticalGroup(jpProjectStatusLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProjectStatusLayout
										.createSequentialGroup()
										.addGroup(
												jpProjectStatusLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpProjectStatusLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlRawFiles)
																		.addComponent(
																				jlRawFilesStatus,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlIdentFiles)
																		.addComponent(
																				jlIdentFilesStatus,
																				GroupLayout.PREFERRED_SIZE,
																				20,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlTechnique)
																		.addComponent(
																				jcbTechnique,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jlQuantFiles)
																		.addComponent(
																				jcbOutputFormat,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jlQuantFilesStatus,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		JMenu jmFile = new JMenu("File");

		JMenuItem jmNewProject = new JMenuItem("New Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/new.gif")));
		jmNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		jmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmNewProjectActionPerformed(jcbTechnique, jmSaveProject,
						jmCloseProject, jtaLog, jdpTIC, jdpMS, jbSaveProject,
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
		jmFile.add(jmNewProject);

		JMenuItem jmImportFile = new JMenuItem("Import File", new ImageIcon(
				getClass().getClassLoader().getResource("images/import.gif")));
		jmImportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_MASK));
		jmImportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmImportFileActionPerformed(jtRawFiles, jlFileNameMGFText,
						jtFeatureQuant, jtMzIDProtGroup, jmSaveProject, jp2D,
						jtaLog, jtpProperties, jtpMzQuantMLDetail, jdpTIC,
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
		jmFile.add(jmImportFile);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmOpenProject = new JMenuItem("Open Project", new ImageIcon(
				getClass().getClassLoader().getResource("images/open.gif")));
		jmOpenProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		jmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				openProject(jtRawFiles, jlFileNameMGFText, jtMzIDProtGroup,
						jmCloseProject, jmSaveProject, jp2D, jtaLog,
						jtpProperties, jdpTIC, jbSaveProject, jcbPSM,
						jepMzMLView, jepMzIDView, jlRawFilesStatus,
						jlIdentFilesStatus, jlFileNameMzMLText,
						jlFileNameMzIDText, jtIdentFiles, jtMGF,
						jtMascotXMLView, jtMzId, jtMzML, jtQuantFiles);
			}
		});
		jmFile.add(jmOpenProject);

		JMenuItem jmOpenRecentProject = new JMenuItem("Open Recent Project");
		jmOpenRecentProject.setEnabled(false);
		jmFile.add(jmOpenRecentProject);

		jmCloseProject.setEnabled(false);
		jmCloseProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmCloseProjectActionPerformed(evt, jmCloseProject,
						jmSaveProject, jtaLog, jdpTIC, jdpMS, jbSaveProject,
						jbExportMzMLExcel, jbExportMGFExcel,
						jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
						jbExportPepMZQExcel, jbExportProtMZQExcel,
						jbExportFeatMZQExcel, jcbTechnique, jcbOutputFormat,
						jepMGFView, jepMZQView, jepMascotXMLView, jepMzIDView,
						jepMzMLView, jlFileNameMzQText, jlIdentFilesStatus,
						jlFileNameMzIDText, jlFileNameMzMLText, jtRawFiles,
						jtIdentFiles, jtQuantFiles, jtRawData, jtMzML, jtMGF,
						jtMzId, jtMascotXMLView, jtPeptideQuant,
						jtProteinQuant, jtFeatureQuant);
			}
		});
		jmFile.add(jmCloseProject);
		jmFile.add(new JPopupMenu.Separator());

		jmSaveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		jmSaveProject.setEnabled(false);
		jmSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmSaveProjectActionPerformed(jtRawFiles, jcbTechnique,
						jmSaveProject, jmCloseProject, jbSaveProject,
						jcbOutputFormat, jtIdentFiles);
			}
		});
		jmFile.add(jmSaveProject);

		JMenuItem jmSaveProjectAs = new JMenuItem("Save Project As");
		jmSaveProjectAs.setEnabled(false);
		jmFile.add(jmSaveProjectAs);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmPrint = new JMenuItem("Print", new ImageIcon(getClass()
				.getClassLoader().getResource("images/print.gif")));
		jmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_MASK));
		jmPrint.setEnabled(false);
		jmFile.add(jmPrint);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmExit = new JMenuItem("Exit");
		jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		jmExit.addActionListener(new ActionListenerExit());
		jmFile.add(jmExit);

		JMenuBar jmMain = new JMenuBar();
		jmMain.add(jmFile);

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

		jmMain.add(jmEdit);

		JMenu jmView = new JMenu("View");

		final JMenuItem jmShowProjectFiles = new JMenuItem(
				"Show/Hide Project Files", new ImageIcon(getClass()
						.getClassLoader().getResource("images/thick.gif")));
		jmShowProjectFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jspMainPanelView.getDividerLocation() <= 5) {
					jspMainPanelView.setDividerLocation(250);
					Icon thick = new ImageIcon(getClass().getClassLoader()
							.getResource("images/thick.gif"));
					jmShowProjectFiles.setIcon(thick);
				} else {
					jspMainPanelView.setDividerLocation(0);
					jmShowProjectFiles.setIcon(null);
				}
			}
		});
		jmView.add(jmShowProjectFiles);

		final JMenuItem jmLog = new JMenuItem("Log", new ImageIcon(getClass()
				.getClassLoader().getResource("images/thick.gif")));
		final JMenuItem jmShowViewer = new JMenuItem("Show/Hide Viewer",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif")));
		jmShowViewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jspViewerAndProperties.getDividerLocation() <= 5) {
					jspViewerAndProperties.setDividerLocation(600);
					jmShowViewer.setIcon(new ImageIcon(getClass()
							.getClassLoader().getResource("images/thick.gif")));
				} else {
					jspViewerAndProperties.setDividerLocation(0);
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

		jmMain.add(jmView);

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

		jmMain.add(jmProject);

		JMenu jmAnalyze = new JMenu("Analyze");

		JMenuItem jmRunIdentAnalysis = new JMenuItem(
				"Run Identification Analysis", new ImageIcon(getClass()
						.getClassLoader().getResource("images/runid.gif")));
		jmRunIdentAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, 0));
		jmRunIdentAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmRunIdentAnalysisActionPerformed(evt, jtRawFiles,
						jtMzIDProtGroup, identParamsExecute, jtaLog,
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
				jmRunQuantAnalysisActionPerformed(jtpLog, jmCloseProject,
						jmSaveProject, jtaLog, jtpProperties,
						jtpMzQuantMLDetail, jbSaveProject, jbExportPepMZQExcel,
						jbExportProtMZQExcel, jbExportFeatMZQExcel,
						jcbTechnique, jcbOutputFormat, jepMZQView,
						jlFileNameMzQText, jtFeatureQuant, jtIdentFiles,
						jtPeptideQuant, jtProteinQuant, jtQuantFiles,
						jtRawFiles);
			}
		});
		jmAnalyze.add(jmRunQuantAnalysis);

		jmMain.add(jmAnalyze);

		JMenu jmTools = new JMenu("Tools");

		JMenu jmConverters = new JMenu("Converters");

		JMenuItem jmMzML2MGF = new JMenuItem("mzML to MGF");
		jmMzML2MGF.addActionListener(new ActionListenerMzML2MGF());
		jmConverters.add(jmMzML2MGF);

		JMenuItem jmMaxQ2MZQ = new JMenuItem("MaxQuant to mzQuantML");
		jmMaxQ2MZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// ... Load MaxQuant2MZQ GUI ...//
				final JFrame jfWinParams = new JFrame(
						"Convert MaxQuant result files to mzQuantML");
				MaxQ2MZQView winParams = new MaxQ2MZQView(jfWinParams,
						sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 150);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
						KeyEvent.VK_ESCAPE, 0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap()
						.put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader()
						.getResource("images/icon.gif")).getImage();
				jfWinParams.setIconImage(iconApp);
				jfWinParams.setAlwaysOnTop(true);

				jfWinParams.add(winParams);
				jfWinParams.pack();
				jfWinParams.setVisible(true);
			}
		});
		jmConverters.add(jmMaxQ2MZQ);

		JMenuItem jmProgenesis2MZQ = new JMenuItem("Progenesis to mzQuantML");
		jmProgenesis2MZQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				final JFrame jfWinParams = new JFrame(
						"Convert Progenesis result files to mzQuantML");
				Prog2MZQView winParams = new Prog2MZQView(jfWinParams,
						sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 180);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
						KeyEvent.VK_ESCAPE, 0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap()
						.put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader()
						.getResource("images/icon.gif")).getImage();
				jfWinParams.setIconImage(iconApp);
				jfWinParams.setAlwaysOnTop(true);

				jfWinParams.add(winParams);
				jfWinParams.pack();
				jfWinParams.setVisible(true);
			}
		});
		jmConverters.add(jmProgenesis2MZQ);

		jmTools.add(jmConverters);

		final JMenuItem jMzMLCompressed = new JMenuItem("Compress MzML file");
		jMzMLCompressed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// ... Load MzML2MGF GUI ...//
				final JFrame jfWinParams = new JFrame("Compress mzML files");
				MzMLCompressView winParams = new MzMLCompressView(jfWinParams,
						sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 450);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
						KeyEvent.VK_ESCAPE, 0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap()
						.put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader()
						.getResource("images/icon.gif")).getImage();
				jfWinParams.setIconImage(iconApp);
				jfWinParams.setAlwaysOnTop(true);

				jfWinParams.add(winParams);
				jfWinParams.pack();
				jfWinParams.setVisible(true);
			}
		});
		jmTools.add(jMzMLCompressed);

		JMenuItem jmCustomize = new JMenuItem("Customize");
		jmCustomize.setEnabled(false);
		jmTools.add(jmCustomize);

		JMenuItem jmOptions = new JMenuItem("Options");
		jmOptions.addActionListener(new ActionListenerOptions(this));
		jmTools.add(jmOptions);

		jmMain.add(jmTools);

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

		jmMain.add(jmDatabases);

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

		jmLog.addActionListener(new ActionListenerViewerExtraChanged(jtpLog,
				jmLog, jmRawData, 0));
		jmWindow.add(jmLog);

		jmRawData.addActionListener(new ActionListenerViewerExtraChanged(
				jtpLog, jmLog, jmRawData, 1));
		jmWindow.add(jmRawData);

		jmMain.add(jmWindow);

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

		jmMain.add(jmHelp);

		setJMenuBar(jmMain);

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

	private void jmNewProjectActionPerformed(JComboBox<String> jcbTechnique,
			JMenuItem jmSaveProject, JMenuItem jmCloseProject,
			JTextArea jtaLog, final JDesktopPane jdpTIC,
			final JDesktopPane jdpMS, final JButton jbSaveProject,
			final JButton jbExportMzMLExcel, final JButton jbExportMGFExcel,
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
		if (bProjectModified) {
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
				initProjectValues(jmCloseProject, jmSaveProject, jtaLog,
						jdpTIC, jdpMS, jbSaveProject, jbExportMzMLExcel,
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
				initProjectValues(jmCloseProject, jmSaveProject, jtaLog,
						jdpTIC, jdpMS, jbSaveProject, jbExportMzMLExcel,
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
			initProjectValues(jmCloseProject, jmSaveProject, jtaLog, jdpTIC,
					jdpMS, jbSaveProject, jbExportMzMLExcel, jbExportMGFExcel,
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

	private void jmImportFileActionPerformed(final JTable jtRawFiles,
			final JLabel jlFileNameMGFText, final JTable jtFeatureQuant,
			final JTable jtMzIDProtGroup, JMenuItem jmSaveProject,
			final JPanel jp2D, final JTextArea jtaLog,
			final JTabbedPane jtpProperties,
			final JTabbedPane jtpMzQuantMLDetail, final JDesktopPane jdpTIC,
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

		// ... Enable multiple file selection ...//
		chooser.setMultiSelectionEnabled(true);

		// ... Setting default directory ...//
		if (sPreviousLocation == null || sPreviousLocation.contains("")) {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sWorkspace));
		} else {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sPreviousLocation));
		}

		// ... Retrieving selection from user ...//
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			final File[] aFiles = chooser.getSelectedFiles();
			if (aFiles != null && aFiles.length > 0) {
				bProjectModified = true;
				updateSaveProjectStatus(jmSaveProject, jbSaveProject);
				sPreviousLocation = aFiles[0].getParent();
				// ---------------//
				// Read mzML //
				// ---------------//
				// ... Usually, the first element will contain the type of
				// files, but we can also expect mixed entries ...//
				if ((aFiles[0].getName().toLowerCase().indexOf(".mzml") > 0)
						|| (aFiles[0].getName().toLowerCase()
								.indexOf(".mzml.gz") > 0)) {
					// ... Fill JTable ...//
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
							// ... Progress Bar ...//
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
							jtaLog.setText(sOutput);

							// ... Release unmarshallers ...//
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
								aMzMLUnmarshaller.clear();
							}
							// ... Reading selected files ...//
							for (int iI = 0; iI < aFiles.length; iI++) {
								// ... Validate file extension (mixed files)
								// ...//
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

									// ... Uncompress mzML.gz files ...//
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
											jtaLog.setText(sOutput);

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
									// ... Unmarshall data using jzmzML API
									// ...//
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtaLog.setText(sOutput);
									Unmarshaller.unmarshalMzMLFile(model,
											xmlFile, "", aMzMLUnmarshaller);

									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling ends ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtaLog.setText(sOutput);
								}
							} // ... For files ...//

							// ... We then display the first mzML element, the
							// corresponding chromatogram and the 2D plot ...//
							sMessage = SYS_UTILS.getTime()
									+ " - Loading mzML view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							loadMzMLView(0, jtRawFiles, jtpProperties,
									jepMzMLView, jlFileNameMzMLText, jtMzML);
							sMessage = SYS_UTILS.getTime()
									+ " - Displaying chromatogram ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);

							// Clear container
							jdpTIC.removeAll();
							JInternalFrame jifViewChromatogram = getInternalFrame();
							jifViewChromatogram.setTitle("Chromatogram <"
									+ aFiles[0].getPath() + ">");
							jifViewChromatogram.add(ChartChromatogram
									.getChromatogram(aMzMLUnmarshaller.get(0)));

							jdpTIC.add(jifViewChromatogram);
							jdpTIC.revalidate();
							jdpTIC.repaint();

							sMessage = SYS_UTILS.getTime()
									+ " - Displaying 2D Plot";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							TwoDPlot jifView2D = ChartPlot2D.get2DPlot(
									aMzMLUnmarshaller.get(0),
									aFiles[0].getPath());
							if (jifView2D != null) {
								jp2D.add(jifView2D);
								jifView2D.pack();
								jifView2D.setVisible(true);
							}

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMzMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);
						}
					}.start();
				} // ... From reading mzML files ...//
					// ---------------//
					// Read MGF //
					// ---------------//
				if (aFiles[0].getName().toLowerCase().indexOf(".mgf") > 0) {
					// ... Fill JTable ...//
					final DefaultTableModel model = new DefaultTableModel();
					jtRawFiles.setModel(model);
					model.addColumn("Name");
					model.addColumn("Path");
					model.addColumn("Type");
					model.addColumn("Version");

					final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
							this, true, "ReadingMGF");
					final Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// ... Progress Bar ...//
							progressBarDialog.setTitle("Reading MGF files");
							progressBarDialog.setVisible(true);
						}
					}, "ProgressBarDialog");
					thread.start();
					new Thread("ReadingMGF") {
						@Override
						public void run() {
							String sMessage = "";
							String sOutput = "";
							jtaLog.setText(sOutput);
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading MGF files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
							}

							// ... Reading selected files ...//
							for (int iI = 0; iI < aFiles.length; iI++) {
								progressBarDialog
										.setTitle("Reading MGF files ("
												+ (iI + 1) + "/"
												+ aFiles.length + ") - "
												+ aFiles[iI].getName());
								progressBarDialog.setVisible(true);
								// ... Validate file extension (mixed files)
								// ...//
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
							} // ... For files ...//

							// ... Display data for the first element ...//
							sMessage = SYS_UTILS.getTime()
									+ " - Loading MGF view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							loadMGFView(0, jlFileNameMGFText, jtRawFiles,
									jtpProperties, jtMGF);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMGFExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
									jlIdentFilesStatus, jtIdentFiles);
						}
					}.start();
				} // ... From reading MGF files ...//
					// -------------------//
					// Read mzIdentML //
					// -------------------//
				if ((aFiles[0].getName().toLowerCase().indexOf(".mzid") > 0)
						|| (aFiles[0].getName().toLowerCase()
								.indexOf(".mzid.gz") > 0)) {
					// ... Fill JTable ...//
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
							// ... Progress Bar ...//
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
							jtaLog.setText(sOutput);

							// ... Release unmarshallers ...//
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
								aMzIDUnmarshaller.clear();
							}
							// ... Reading selected files ...//
							for (int iI = 0; iI < aFiles.length; iI++) {
								// ... Validate file extension (mixed files)
								// ...//
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

									// ... Uncompress .gz files ...//
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
											jtaLog.setText(sOutput);
										} catch (IOException ioe) {
											System.out
													.println("Exception has been thrown"
															+ ioe);
										}
									}
									// ... Unmarshall data using jmzIdentML API
									// ...//
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtaLog.setText(sOutput);
									Unmarshaller.unmarshalMzIDFile(model,
											xmlFile, "", aMzIDUnmarshaller);
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling ends ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtaLog.setText(sOutput);
								}
							} // ... For files ...//

							// ... Display first element ...//
							sMessage = SYS_UTILS.getTime()
									+ " - Loading mzIdentML view ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							loadMzIdentMLView(0, aFiles[0].getName(),
									jtMzIDProtGroup, jtpProperties, jcbPSM,
									jepMzIDView, jlFileNameMzIDText, jtMzId);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();

							sMessage = SYS_UTILS.getTime()
									+ " - Identification files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
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
							jtaLog.setText(sOutput);
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading Mascot XML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
							}

							// ... Reading selected files ...//
							for (int iI = 0; iI < aFiles.length; iI++) {
								progressBarDialog
										.setTitle("Reading Mascot XML files ("
												+ (iI + 1) + "/"
												+ aFiles.length + ") - "
												+ aFiles[iI].getName());
								progressBarDialog.setVisible(true);

								// ... Validate file extension (mixed files)
								// ...//
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
							jtaLog.setText(sOutput);
							FileFormatMascot.loadMascotView(aFiles[0].getName(), aFiles[0]
									.getPath().toString().replace("\\", "/"),
									jtMascotXMLView, jtpProperties);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Identifiation files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
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
							jtaLog.setText(sOutput);

							// ... Release unmarshallers ...//
							if (aFiles.length > 0) {
								sMessage = SYS_UTILS.getTime()
										+ " - Reading mzQuantML files (Total="
										+ aFiles.length + ")";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
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
									jtaLog.setText(sOutput);

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
										jtaLog.setText(sOutput);
									} catch (Exception e) {
										jtaLog.append("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
										jtaLog.append(e.getMessage());
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
								jtaLog.setText(sOutput);
								// loadMzQuantMLView(0, aFiles[0].getName());
								loadMzQuantMLView(0,
										aFiles[0].getAbsolutePath(),
										jtFeatureQuant, jtaLog, jtpProperties,
										jtpMzQuantMLDetail, jepMZQView,
										jlFileNameMzQText, jtPeptideQuant,
										jtProteinQuant, jtQuantFiles);

								sMessage = SYS_UTILS.getTime()
										+ " - Quantification files imported successfully!";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
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

	private void jmSaveProjectActionPerformed(JTable jtRawFiles,
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

	private void jmCloseProjectActionPerformed(ActionEvent evt,
			JMenuItem jmCloseProject, JMenuItem jmSaveProject,
			JTextArea jtaLog, final JDesktopPane jdpTIC,
			final JDesktopPane jdpMS, final JButton jbSaveProject,
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
		if (bProjectModified) {
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
				initProjectValues(jmCloseProject, jmSaveProject, jtaLog,
						jdpTIC, jdpMS, jbSaveProject, jbExportMzMLExcel,
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
				initProjectValues(jmCloseProject, jmSaveProject, jtaLog,
						jdpTIC, jdpMS, jbSaveProject, jbExportMzMLExcel,
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
			initProjectValues(jmCloseProject, jmSaveProject, jtaLog, jdpTIC,
					jdpMS, jbSaveProject, jbExportMzMLExcel, jbExportMGFExcel,
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

	private void jmRunQuantAnalysisActionPerformed(JTabbedPane jtpLog,
			final JMenuItem jmCloseProject, final JMenuItem jmSaveProject,
			final JTextArea jtaLog, final JTabbedPane jtpProperties,
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
		boolean isOK = getWorkspace();
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
				isOK = checkMapping(jtpLog, jtaLog, jtIdentFiles);
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
									isOK = generateFiles(jtRawFiles, jtaLog,
											jcbTechnique.getSelectedItem()
													.toString(),
											jcbOutputFormat, jtIdentFiles,
											jtQuantFiles);
									if (isOK) {
										// ... Run xTracker ...//
										System.out
												.println(SYS_UTILS.getTime()
														+ " - ****** Running x-Tracker *****");
										jtaLog.append("\n"
												+ SYS_UTILS.getTime()
												+ " - ****** Running x-Tracker *****");
										new xTracker(sWorkspace.replace("\\",
												"/") + "/" + sProjectName,
												sWorkspace.replace("\\", "/"));

										// ... Load the mzQuantML file into
										// memory (With results included) ...//
										File xmlFile = new File(sWorkspace
												+ "/" + sProjectName);
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
											jtaLog.append("\n"
													+ SYS_UTILS.getTime()
													+ " - Displaying mzquantml results...");
											jlFileNameMzQText
													.setText(sWorkspace + "/"
															+ sProjectName); // ...
																				// Display
																				// of
																				// the
																				// results
																				// ...//
											// loadMzQuantMLView(0,
											// sProjectName);
											loadMzQuantMLView(0, sWorkspace
													+ "/" + sProjectName,
													jtFeatureQuant, jtaLog,
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
											jtaLog.append("\n"
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
											jtaLog.append("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
											jtaLog.append(e.getMessage());
											e.printStackTrace();
										}

									}
									progressBarDialog.setVisible(false);
									progressBarDialog.dispose();
									updateTitle();
									bProjectModified = false;
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

	private void jmRunIdentAnalysisActionPerformed(ActionEvent evt,
			final JTable jtRawFiles, final JTable jtMzIDProtGroup,
			final IdentParamsView identParamsExecute, final JTextArea jtaLog,
			final JTable jtIdentFiles,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final JLabel jlRawFilesStatus, final JLabel jlIdentFilesStatus,
			final JTabbedPane jtpProperties, final JComboBox<String> jcbPSM,
			final JEditorPane jepMzIDView, final JLabel jlFileNameMzIDText,
			final JTable jtMzId) {

		// Check if there is a valid workspace
		boolean bRun = false;
		boolean isOK = getWorkspace();
		identParamsExecute.setWorkspace(sWorkspace);
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
							jtaLog.append("\n" + SYS_UTILS.getTime()
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
									jtaLog.append("\n" + SYS_UTILS.getTime()
											+ " - [Error] " + errorMessage);
								} else {
									System.out.println(SYS_UTILS.getTime()
											+ " - MS-GF+ completed");
									jtaLog.append("\n" + SYS_UTILS.getTime()
											+ " - MS-GF+ completed");
								}
								if (bProteinInference) {
									System.out
											.println(SYS_UTILS.getTime()
													+ " - Performing protein inference");
									jtaLog.append("\n" + SYS_UTILS.getTime()
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
										jtaLog.append("\n"
												+ SYS_UTILS.getTime()
												+ " - Performing FDR");
										mzlib.init(args);
										System.out.println(SYS_UTILS.getTime()
												+ " - Setting Threshold");
										jtaLog.append("\n"
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
										jtaLog.append("\n"
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
	private boolean checkMapping(JTabbedPane jtpLog, JTextArea jtaLog,
			JTable jtIdentFiles) {
		// Check if all identification files have been assigned to one rawfile
		System.out.println(SYS_UTILS.getTime()
				+ " - Checking identification-spectra mapping");
		jtaLog.append(SYS_UTILS.getTime()
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
		jmSaveProject.setEnabled(bProjectModified);
		jbSaveProject.setEnabled(bProjectModified);
	}

	/**
	 * Update close project icon
	 * 
	 * @param void
	 * @return void
	 */
	private void updateCloseProjectStatus(final JMenuItem jmCloseProject) {
		if (sProjectName.equals("New")) {
			jmCloseProject.setEnabled(false);
		} else {
			jmCloseProject.setEnabled(true);
		}
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

		// ... Applying file extension filters ...//
		FileNameExtensionFilter filter3 = new FileNameExtensionFilter(
				"mzQuantML Files (*.mzq)", "mzq");
		// ... Filters must be in descending order ...//
		chooser.addChoosableFileFilter(filter3);

		// ... Setting default directory ...//
		if (sPreviousLocation == null || sPreviousLocation.contains("")) {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sWorkspace));
		} else {
			// If not found it goes to Home, exception not needed
			chooser.setCurrentDirectory(new File(sPreviousLocation));
		}

		int returnVal = chooser.showSaveDialog(this);
		System.out.println(returnVal);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// ... Check extension ...//
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

			// ... Check if file exists ...//
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
				bProjectModified = false;
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
				+ sWorkspace + " - " + sProjectName
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
	 * Checks if the working space is valid
	 * 
	 * @param void
	 * @return true/false
	 */
	public boolean getWorkspace() {
		// ... Validate if config file exists ...//
		boolean exists = (new File("config.xml")).exists();
		if (exists) {
			// ... Read files using SAX (get workspace) ...//
			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();

				DefaultHandler handler = new DefaultHandler() {
					boolean bWorkspace = false;

					@Override
					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {
						if (qName.equalsIgnoreCase("workspace")) {
							bWorkspace = true;
						}
					}

					@Override
					public void characters(char ch[], int start, int length)
							throws SAXException {
						if (bWorkspace) {
							bWorkspace = false;
							sWorkspace = new String(ch, start, length);
						}
					}
				};
				saxParser.parse("config.xml", handler);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ... Check if default workspace is valid ...//
			exists = (new File(sWorkspace)).exists();
			if (!exists) {
				JOptionPane
						.showMessageDialog(
								this,
								"The default \"workspace\" does not exist. Please set up your directory in \"Tools\"->\"Options\" ",
								"Error", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} else {
			return false;
		}
		return true;
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
	private void showRawDataMGF(int iIndex, String sID, JTable jtRawData,
			JTable jtRawFiles, JTabbedPane jtpLog) {
		DefaultTableModel model = new DefaultTableModel();
		jtRawData.setModel(model);
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");

		// ... Reading file ...//
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
	 * Displays raw data
	 * 
	 * @iIndex - Index to the aMzMLUnmarshaller list
	 * @sID - scan ID
	 * @return void
	 */
	private void showRawData(int iIndex, String sID, final JTable jtRawData,
			JTabbedPane jtpLog, List<MzMLUnmarshaller> aMzMLUnmarshaller) {
		DefaultTableModel model = new DefaultTableModel() {
			Class<?>[] types = new Class[] { Integer.class, Float.class,
					Float.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
		jtRawData.setModel(model);
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");
		jtpLog.setSelectedIndex(1);

		// ... Get index from spectra ...//
		MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);
		try {
			Spectrum spectrum = unmarshaller.getSpectrumById(sID);
			Number[] mzNumbers = null;
			Number[] intenNumbers = null;

			boolean bCompressed = false;
			// ... Reading mz Values ...//
			List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
					.getBinaryDataArray();
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000000")) {
						bCompressed = true;
					}
				}
			}

			// ... Reading mz and intensity values ...//
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000514")) {
						mzNumbers = bda.getBinaryDataAsNumberArray();
						if (bCompressed) {
							try {
                                DeltaConversion.fromDeltaNumberFormat(mzNumbers);
                            } catch (DeltaEncodedDataFormatException dex) {
                                System.out.println("Problem converting back from delta m/z format: " + dex.getLocalizedMessage());
                                return;
                            }
						}
					}
					if (cvp.getAccession().equals("MS:1000515")) {
						intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
			}
			for (int iI = 0; iI < mzNumbers.length; iI++) {
				model.insertRow(model.getRowCount(),
						new Object[] { iI, mzNumbers[iI].doubleValue(),
								intenNumbers[iI].doubleValue() });
			}
			jtRawData.getTableHeader().setDefaultRenderer(
					new TableCellRenderer() {
						final TableCellRenderer defaultRenderer = jtRawData
								.getTableHeader().getDefaultRenderer();

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							JComponent component = (JComponent) defaultRenderer
									.getTableCellRendererComponent(table,
											value, isSelected, hasFocus, row,
											column);
							component.setToolTipText(""
									+ jtRawData.getColumnName(column));
							return component;
						}
					});
			jtRawData.setAutoCreateRowSorter(true);
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
	}

	/**
	 * Get internal frame dimensions
	 * 
	 * @param void
	 * @return JInternalFrame as a container
	 */
	private JInternalFrame getInternalFrame() {
		JInternalFrame internalFrame = new JInternalFrame("");
		Icon icon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif"));

		internalFrame.setLocation(0, 0);
		internalFrame.setSize(new Dimension(620, 420));
		internalFrame.setPreferredSize(new Dimension(620, 420));
		internalFrame.setFrameIcon(icon);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setClosable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setVisible(true);
		return internalFrame;
	}

	/*--------------------------
	 * TO DO
	 ---------------------------*/
	private void writeConfigFile(String sFileName, JTable jtRawFiles) {
		if (sFileName.indexOf(".psx") <= 0) {
			sFileName = sFileName + ".psx";
		}
		try {

			FileWriter fstream = new FileWriter(sFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.newLine();
			out.write("<ProteoSuiteProject xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
			out.write("xsi:schemaLocation=\"ProteoSuite.xsd\" name=\""
					+ sProjectName + "\" workspace=\"" + sWorkspace + "\">");
			out.newLine();
			out.write(" <configSettings>");
			out.newLine();
			out.write("     <rawDataSettings>");
			out.newLine();
			for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
				out.write("         <rawFile ");
				out.write(" name=\"" + jtRawFiles.getValueAt(iI, 0) + "\"");
				out.write(" path=\"" + jtRawFiles.getValueAt(iI, 1) + "\"");
				out.write(" type=\"" + jtRawFiles.getValueAt(iI, 2) + "\"");
				out.write(" version=\"" + jtRawFiles.getValueAt(iI, 3) + "\"");
				out.write(" scans=\"" + jtRawFiles.getValueAt(iI, 4) + "\" >");
				out.newLine();
				out.write("         </rawFile>");
				out.newLine();
			}
			out.write("     </rawDataSettings>");
			out.newLine();
			out.write("     <identDataSettings>");
			out.newLine();
			out.write("     </identDataSettings>");
			out.newLine();
			out.write("     <quantDataSettings>");
			out.newLine();
			out.write("     </quantDataSettings>");
			out.newLine();
			out.write(" </configSettings>");
			out.newLine();
			out.write("</ProteoSuiteProject>");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Load MzIdentML viewer
	 * 
	 * @param iIndex
	 *            - Index to the aMzIDUnmarshaller list
	 * @param sFileName
	 *            - File name
	 * @param jepMzIDView
	 * @param jlFileNameMzIDText
	 * @param jtMzId
	 * @return void
	 */
	private void loadMzIdentMLView(final int iIndex, final String sFileName,
			JTable jtMzIDProtGroup, final JTabbedPane jtpProperties,
			JComboBox<String> jcbPSM, JEditorPane jepMzIDView,
			JLabel jlFileNameMzIDText, JTable jtMzId) {
		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,
				true, "LoadMzIDView");
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ... Progress Bar ...//
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
	private void loadMzQuantMLView(int iIndex, String sFile,
			JTable jtFeatureQuant, JTextArea jtaLog,
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
						jtaLog, jtpMzQuantMLDetail);
				mzQuantML.start();
			}
		} catch (Exception e) {
			jtaLog.append("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
			jtaLog.append(e.getMessage());
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
	private void loadMzMLView(int iIndex, JTable jtRawFiles,
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
	private void loadMGFView(int iIndex, JLabel jlFileNameMGFText,
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
			JMenuItem jmSaveProject, JTextArea jtaLog,
			final JDesktopPane jdpTIC, final JDesktopPane jdpMS,
			final JButton jbSaveProject, final JButton jbExportMzMLExcel,
			final JButton jbExportMGFExcel,
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
		initTables(jtRawFiles, jtIdentFiles, jtQuantFiles, jtRawData, jtMzML,
				jtMGF, jtMzId, jtMascotXMLView, jtPeptideQuant, jtProteinQuant,
				jtFeatureQuant);
		initTextAreas(jtaLog, jepMGFView, jepMZQView, jepMascotXMLView,
				jepMzIDView, jepMzMLView, jlFileNameMzMLText,
				jlFileNameMzIDText, jlFileNameMzQText);
		jdpMS.removeAll();
		jdpTIC.removeAll();
		initButtons(jbExportMzMLExcel, jbExportMGFExcel,
				jbExportMzIdentMLExcel, jbExportMascotXMLExcel,
				jbExportPepMZQExcel, jbExportProtMZQExcel, jbExportFeatMZQExcel);

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
		boolean exists = (new File("config.xml")).exists();
		if (exists) {
			// ... Read files using SAX (get workspace) ...//
			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();

				DefaultHandler handler = new DefaultHandler() {
					boolean bWorkspace = false;

					@Override
					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {
						if (qName.equalsIgnoreCase("workspace")) {
							bWorkspace = true;
						}
					}

					@Override
					public void characters(char ch[], int start, int length)
							throws SAXException {
						if (bWorkspace) {
							bWorkspace = false;
							sWorkspace = new String(ch, start, length);
						}
					}
				};
				saxParser.parse("config.xml", handler);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			sWorkspace = "";
			String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
			sMessage = sMessage
					+ "under your installation directory. ProteoSuite will continue launching, however \n";
			sMessage = sMessage
					+ "it is recommended that you copy the file as indicated in the readme.txt file.";
			JOptionPane.showMessageDialog(this, sMessage, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		sProjectName = "New";
		bProjectModified = false;
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
	 * Initialise table values
	 * 
	 * @param jtRawData
	 * @param void
	 * @return void
	 */
	private void initTables(JTable jtRawFiles, JTable jtIdentFiles,
			JTable jtQuantFiles, JTable jtRawData, JTable jtMzML, JTable jtMGF,
			JTable jtMzId, JTable jtMascotXMLView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant) {
		DefaultTableModel model = new DefaultTableModel();
		jtRawFiles.setModel(model);
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");

		DefaultTableModel model2 = new DefaultTableModel();
		jtIdentFiles.setModel(model2);
		model2.addColumn("Name");
		model2.addColumn("Path");
		model2.addColumn("Type");
		model2.addColumn("Version");
		model2.addColumn("Raw File");

		DefaultTableModel model3 = new DefaultTableModel();
		jtQuantFiles.setModel(model3);
		model3.addColumn("Name");
		model3.addColumn("Path");
		model3.addColumn("Type");
		model3.addColumn("Version");

		DefaultTableModel model4 = new DefaultTableModel();
		jtRawData.setModel(model4);
		model4.addColumn("Index");
		model4.addColumn("m/z");
		model4.addColumn("Intensity");

		DefaultTableModel model5 = new DefaultTableModel();
		jtMzML.setModel(model5);
		model5.addColumn("Index");
		model5.addColumn("ID");
		model5.addColumn("MS");
		model5.addColumn("Base peak m/z");
		model5.addColumn("Base peak int");
		model5.addColumn("RT (sec)");
		model5.addColumn("Precurs m/z");

		DefaultTableModel model6 = new DefaultTableModel();
		jtMGF.setModel(model6);
		model6.addColumn("Index");
		model6.addColumn("Scan Title");
		model6.addColumn("Peptide Mass");
		model6.addColumn("Charge");
		model6.addColumn("Reference Line");

		DefaultTableModel model7 = new DefaultTableModel();
		jtMzId.setModel(model7);
		model7.addColumn("Index");
		model7.addColumn("Protein");
		model7.addColumn("Peptide");
		model7.addColumn("Rank");
		model7.addColumn("Score");
		model7.addColumn("Spectrum ID");

		DefaultTableModel model8 = new DefaultTableModel();
		jtMascotXMLView.setModel(model8);
		model8.addColumn("Index");
		model8.addColumn("Protein");
		model8.addColumn("Peptide");
		model8.addColumn("Composition");
		model8.addColumn("Exp Mz");
		model8.addColumn("Exp Mr");
		model8.addColumn("Charge");
		model8.addColumn("Score");
		model8.addColumn("Scan");
		model8.addColumn("Scan ID");
		model8.addColumn("RT (sec)");

		DefaultTableModel model9 = new DefaultTableModel();
		jtPeptideQuant.setModel(model9);
		model9.addColumn("Peptide");
		model9.addColumn("Label 1");
		model9.addColumn("Label 2");
		model9.addColumn("Label 3");
		model9.addColumn("Label 4");

		DefaultTableModel model10 = new DefaultTableModel();
		jtProteinQuant.setModel(model10);
		model10.addColumn("Protein");
		model10.addColumn("Label 1");
		model10.addColumn("Label 2");
		model10.addColumn("Label 3");
		model10.addColumn("Label 4");

		DefaultTableModel model11 = new DefaultTableModel();
		jtFeatureQuant.setModel(model11);
		model11.addColumn("Feature");
		model11.addColumn("Label 1");
		model11.addColumn("Label 2");
		model11.addColumn("Label 3");
		model11.addColumn("Label 4");
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
	private void initTextAreas(final JTextArea jtaLog,
			final JEditorPane jepMGFView, final JEditorPane jepMZQView,
			final JEditorPane jepMascotXMLView, final JEditorPane jepMzIDView,
			final JEditorPane jepMzMLView, final JLabel jlFileNameMzMLText,
			JLabel jlFileNameMzIDText, JLabel jlFileNameMzQText) {

		jtaLog.setText("");
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
	 * Initialise project buttons
	 * 
	 * @param void
	 * @return void
	 */
	private void initButtons(final JButton jbExportMzMLExcel,
			final JButton jbExportMGFExcel,
			final JButton jbExportMzIdentMLExcel,
			final JButton jbExportMascotXMLExcel,
			final JButton jbExportPepMZQExcel,
			final JButton jbExportProtMZQExcel,
			final JButton jbExportFeatMZQExcel) {

		// Project buttons
		jbExportMzMLExcel.setEnabled(false);
		jbExportMGFExcel.setEnabled(false);
		jbExportMzIdentMLExcel.setEnabled(false);
		jbExportMascotXMLExcel.setEnabled(false);
		jbExportPepMZQExcel.setEnabled(false);
		jbExportProtMZQExcel.setEnabled(false);
		jbExportFeatMZQExcel.setEnabled(false);
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
	private void openProject(JTable jtRawFiles, JLabel jlFileNameMGFText,
			final JTable jtMzIDProtGroup, JMenuItem jmCloseProject,
			JMenuItem jmSaveProject, final JPanel jp2D, JTextArea jtaLog,
			final JTabbedPane jtpProperties, final JDesktopPane jdpTIC,
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
			chooser.setCurrentDirectory(new File(sWorkspace));
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
							jtaLog.append("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
							jtaLog.append(e.getMessage());
							e.printStackTrace();
							isOK = false;
						}
						if (isOK) {
							isOK = loadProjectFromMZQ(0, file, jtRawFiles,
									jlFileNameMGFText, jtMzIDProtGroup, jp2D,
									jtpProperties, jdpTIC, jcbPSM, jepMzMLView,
									jepMzIDView, jlRawFilesStatus,
									jlIdentFilesStatus, jlFileNameMzMLText,
									jlFileNameMzIDText, jtIdentFiles, jtMGF,
									jtMascotXMLView, jtMzId, jtMzML,
									jtQuantFiles);
							if (isOK) {
								bProjectModified = false;

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
			final JTable jtMzIDProtGroup, final JPanel jp2D,
			final JTabbedPane jtpProperties, final JDesktopPane jdpTIC,
			final JComboBox<String> jcbPSM, final JEditorPane jepMzMLView,
			final JEditorPane jepMzIDView, final JLabel jlRawFilesStatus,
			final JLabel jlIdentFilesStatus, final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JTable jtIdentFiles,
			final JTable jtMGF, final JTable jtMascotXMLView,
			final JTable jtMzId, final JTable jtMzML, final JTable jtQuantFiles) {

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

				// ... Load raw files ...//
				List<RawFilesGroup> rawFilesGroup = inputFiles
						.getRawFilesGroup();
				System.out.println(SYS_UTILS.getTime() + " - RawFilesGroup="
						+ rawFilesGroup.size());
				int iMGFRaw = 0, iMzMLRaw = 0;
				for (RawFilesGroup group : rawFilesGroup) {
					sGroup = group.getId();
					List<RawFile> rawFileList = group.getRawFile();
					for (RawFile rawFile : rawFileList) {
						// ... Validate type of file ...//
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
						jdpTIC.removeAll();
						JInternalFrame jifViewChromatogram = getInternalFrame();
						jifViewChromatogram.setTitle("Chromatogram <"
								+ jtRawFiles.getValueAt(0, 1) + ">");
						jifViewChromatogram.add(ChartChromatogram
								.getChromatogram(aMzMLUnmarshaller.get(0)));

						jdpTIC.add(jifViewChromatogram);
						jdpTIC.revalidate();
						jdpTIC.repaint();
						System.out.println(SYS_UTILS.getTime()
								+ " - Showing 2D plot ...");

						TwoDPlot jifView2D = ChartPlot2D.get2DPlot(
								aMzMLUnmarshaller.get(0), jtRawFiles
										.getValueAt(0, 1).toString());
						if (jifView2D != null) {
							jp2D.add(jifView2D);
							jifView2D.pack();
							jifView2D.setVisible(true);
						}
					}
				}
				// ... Load ident files ...//
				List<IdentificationFile> idFilesList = inputFiles
						.getIdentificationFiles().getIdentificationFile();
				System.out.println(SYS_UTILS.getTime()
						+ " - Loading identification files");
				int iXMLIdent = 0, iMzID = 0;
				for (IdentificationFile idFile : idFilesList) {
					// ... Validate type of file ...//
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
						FileFormatMascot.loadMascotView(
								jtIdentFiles.getValueAt(0, 0).toString(),
								jtIdentFiles.getValueAt(0, 1).toString(),
								jtMascotXMLView, jtpProperties);
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
				sWorkspace = sFileRef.getParent();
				sWorkspace = sWorkspace.replace("\\", "/");
				System.out.println(SYS_UTILS.getTime()
						+ " - Project has been set up to: " + sWorkspace + ", "
						+ sProjectName);
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
	private boolean generateFiles(JTable jtRawFiles, JTextArea jtaLog,
			String selectedTechnique, JComboBox<String> jcbOutputFormat,
			JTable jtIdentFiles, JTable jtQuantFiles) {
		// ... Check project name ...//
		String sFile = sProjectName;
		System.out.println(SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		jtaLog.append("\n" + SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		if (sFile.equals("New")) {
			sFile = "test.mzq";
			String sMessage = "This project has not been saved. Proteosuite will create a test.mzq file \n";
			sMessage = sMessage + " under " + sWorkspace
					+ " to run the pipeline. \n";
			JOptionPane.showMessageDialog(this, sMessage, "Information",
					JOptionPane.INFORMATION_MESSAGE);
			sProjectName = sFile;
		}

		// ... Generate mzq file ...//
		System.out.println(SYS_UTILS.getTime() + " - Generating mzq file ...");
		jtaLog.append("\n" + SYS_UTILS.getTime() + " - Generating mzq file ...");
		boolean isOK = FileFormatMzQuantML.writeMzQuantML(selectedTechnique,
				sFile, jtRawFiles, jcbOutputFormat, jtIdentFiles);
		if (!isOK)
			return false;

		// ... Unmarshall mzquantml file ...//
		Validator validator = XMLparser.getValidator(MZQ_XSD);
		boolean validFlag = XMLparser.validate(validator,
				sWorkspace.replace("\\", "/") + "/" + sProjectName);
		System.out.println(SYS_UTILS.getTime() + " - Validating mzQuantML ...");
		jtaLog.append("\n" + SYS_UTILS.getTime()
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