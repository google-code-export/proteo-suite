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

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;

import edu.ucsd.msjava.mzml.MzMLAdapter;
import edu.ucsd.msjava.params.ParamManager;
import edu.ucsd.msjava.ui.MSGFPlus;

import java.awt.BorderLayout;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.proteosuite.data.psTemplate;
import org.proteosuite.data.psTemplateQuant;
import org.proteosuite.external.IPC;
import org.proteosuite.external.IPC.Options;
import org.proteosuite.external.IPC.Results;
import org.proteosuite.fileformat.FileFormatMGF;
import org.proteosuite.fileformat.FileFormatMzIdentML;
import org.proteosuite.fileformat.FileFormatMzML;
import org.proteosuite.fileformat.FileFormatMzQuantML;
import org.proteosuite.gui.*;
import org.proteosuite.listener.*;
import org.proteosuite.utils.BinaryUtils;
import org.proteosuite.utils.ExcelExporter;
import org.proteosuite.utils.Homeless;
import org.proteosuite.utils.NumericalUtils;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;
import org.proteosuite.utils.TwoDPlot;
import org.proteosuite.utils.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cranfield.xTracker.*;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;
import uk.ac.liv.jmzqml.model.mzqml.AbstractParam;
import uk.ac.liv.jmzqml.model.mzqml.AnalysisSummary;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.Cv;
import uk.ac.liv.jmzqml.model.mzqml.CvList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFiles;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.Label;
import uk.ac.liv.jmzqml.model.mzqml.ModParam;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLMarshaller;
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
	private static final String sPS_Version = "0.3.1";
	private static String sProjectName = "";
	private static String sWorkspace = "";
	private static String sPreviousLocation = "user.home";
	private boolean bProjectModified = false;
	private static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
	private static final String mzMLVersion = "1.1";
	private static final String mzIDVersion = "1.1";
	public static final String MZQUANT_VERSION = "1.0.0";
	private static final String PSI_MS_VERSION = "3.37.0";
	private static final String PSI_MOD_VERSION = "1.2";
	private static final SystemUtils SYS_UTILS = new SystemUtils();

	private IdentParamsView identParamsExecute;
	private JDialog dialogIdentParamsExecute;

	// List of unmarshaller objects
	private List<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
	private List<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
	private List<MzQuantMLUnmarshaller> aMzQUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();

	public ProteoSuiteView() {
		// ... Initializing all components (handled by netbeans) ...//
		initComponents();

		// ... Load parameter settings ...//
		identParamsExecute = new IdentParamsView(sWorkspace, "execute");

		// ... Project default settings ...//
		initProjectValues();

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
		Icon spectrumIcon = new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif"));
		JLabel jlSpectrumIcon = new JLabel("Spectrum", spectrumIcon, SwingConstants.RIGHT);
		jlSpectrumIcon.setIconTextGap(5);
		jtpViewer.setTabComponentAt(0, jlSpectrumIcon);

		Icon TICIcon = new ImageIcon(getClass().getClassLoader().getResource("images/chromat.gif"));
		JLabel jlTICIcon = new JLabel("TIC", TICIcon, SwingConstants.RIGHT);
		jlTICIcon.setIconTextGap(5);
		jtpViewer.setTabComponentAt(1, jlTICIcon);

		Icon twoDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/twod.gif"));
		JLabel jlTwoDIcon = new JLabel("2D View", twoDIcon, SwingConstants.RIGHT);
		jlTwoDIcon.setIconTextGap(5);
		jtpViewer.setTabComponentAt(2, jlTwoDIcon);

		Icon threeDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/threed.gif"));
		JLabel jlThreeDIcon = new JLabel("3D View", threeDIcon, SwingConstants.RIGHT);
		jlThreeDIcon.setIconTextGap(5);
		jtpViewer.setTabComponentAt(3, jlThreeDIcon);

		Icon logIcon = new ImageIcon(getClass().getClassLoader().getResource("images/log.gif"));
		JLabel jlLogIcon = new JLabel("Log  ", logIcon, SwingConstants.RIGHT);
		jlLogIcon.setIconTextGap(5);
		jtpLog.setTabComponentAt(0, jlLogIcon);

		Icon rawDataIcon = new ImageIcon(getClass().getClassLoader().getResource("images/raw_data.gif"));
		JLabel jlRawDataIcon = new JLabel("Raw data", rawDataIcon, SwingConstants.RIGHT);
		jlRawDataIcon.setIconTextGap(5);
		jtpLog.setTabComponentAt(1, jlRawDataIcon);

		Icon propertiesMzMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMzMLIcon = new JLabel("mzML View", propertiesMzMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(0, jlPropertiesMzMLIcon);

		Icon propertiesMGFIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMGFIcon = new JLabel("MGF View", propertiesMGFIcon, SwingConstants.RIGHT);
		jlPropertiesMGFIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(1, jlPropertiesMGFIcon);

		Icon propertiesMzIdenMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMzIdentMLIcon = new JLabel("mzIdentML View", propertiesMzIdenMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzIdentMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(2, jlPropertiesMzIdentMLIcon);

		Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View", propertiesMascotIcon, SwingConstants.RIGHT);
		jlPropertiesMascotIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(3, jlPropertiesMascotIcon);

		Icon propertiesMzQuantMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View", propertiesMzQuantMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzQuantMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(4, jlPropertiesMzQuantMLIcon);

		// ... Buttons ...//
		Icon iconExcel = new ImageIcon(getClass().getClassLoader().getResource("images/xls.gif"));
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
		jtpViewer.setSelectedIndex(0);
		jtpLog.setSelectedIndex(0);
		jtpProperties.setSelectedIndex(0);

		// ... Configuring exit events...//
		pack();
	}

	/**
	 * This method is called from within the constructor to initialise the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new ActionListenerExit());

		JButton jbNewProject = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/new.gif")));
		jbNewProject.setToolTipText("New Project (Ctrl + N)");
		jbNewProject.setFocusable(false);
		jbNewProject
				.setHorizontalTextPosition(SwingConstants.CENTER);
		jbNewProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbNewProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmNewProjectActionPerformed(null, jtRawFiles);
			}
		});
		JToolBar jToolBar1 = new JToolBar();
		jToolBar1.add(jbNewProject);

		JButton jbImportFile = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/import.gif")));
		jbImportFile.setToolTipText("Import File (Ctrl + I)");
		jbImportFile.setFocusable(false);
		jbImportFile
				.setHorizontalTextPosition(SwingConstants.CENTER);
		jbImportFile.setMaximumSize(new Dimension(27, 21));
		jbImportFile.setMinimumSize(new Dimension(27, 21));
		jbImportFile.setPreferredSize(new Dimension(27, 21));
		jbImportFile.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbImportFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmImportFileActionPerformed(null, jtRawFiles);
			}
		});
		jToolBar1.add(jbImportFile);

		JButton jbOpenProject = new JButton(new ImageIcon(getClass().getClassLoader().getResource(
				"images/open.gif")));
		jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
		jbOpenProject.setFocusable(false);
		jbOpenProject
				.setHorizontalTextPosition(SwingConstants.CENTER);
		jbOpenProject
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbOpenProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				openProject(sPreviousLocation, sWorkspace, jtRawFiles);
			}
		});
		jToolBar1.add(jbOpenProject);

		jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
		jbSaveProject.setEnabled(false);
		jbSaveProject.setFocusable(false);
		jbSaveProject
				.setHorizontalTextPosition(SwingConstants.CENTER);
		jbSaveProject
				.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbSaveProject.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmSaveProjectActionPerformed(null, jtRawFiles);
			}
		});
		jToolBar1.add(jbSaveProject);

		JButton jbCut = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/cut.gif")));
		jbCut.setToolTipText("Cut (Ctrl + X)");
		jbCut.setEnabled(false);
		jbCut.setFocusable(false);
		jbCut.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCut.setVerticalTextPosition(SwingConstants.BOTTOM);

		JToolBar jToolBar2 = new JToolBar();
		jToolBar2.add(jbCut);

		JButton jbCopy = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/copy.gif")));
		jbCopy.setEnabled(false);
		jbCopy.setFocusable(false);
		jbCopy.setHorizontalTextPosition(SwingConstants.CENTER);
		jbCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
		jToolBar2.add(jbCopy);

		JButton jbPaste = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/paste.gif")));
		jbPaste.setEnabled(false);
		jbPaste.setFocusable(false);
		jbPaste.setHorizontalTextPosition(SwingConstants.CENTER);
		jbPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
		jToolBar2.add(jbPaste);


		JToolBar jToolBar3 = new JToolBar();
		jToolBar3.setMaximumSize(new Dimension(34, 9));
		jToolBar3.setMinimumSize(new Dimension(34, 9));

		JButton jbRunIdentAnalysis = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/runid.gif")));
		jbRunIdentAnalysis.setToolTipText("Run Identification Pipeline (F7)");
		jbRunIdentAnalysis.setFocusable(false);
		jbRunIdentAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunIdentAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunIdentAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmRunIdentAnalysisActionPerformed(null, jtRawFiles);
			}
		});
		jToolBar3.add(jbRunIdentAnalysis);

		JButton jbRunQuantAnalysis = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/run.gif")));
		jbRunQuantAnalysis.setToolTipText("Run Quantitation Pipeline (F5)");
		jbRunQuantAnalysis.setFocusable(false);
		jbRunQuantAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunQuantAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunQuantAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jmRunQuantAnalysisActionPerformed(null, jtRawFiles, jtpLog);
			}
		});
		jToolBar3.add(jbRunQuantAnalysis);

		jpToolBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		jpToolBar.setPreferredSize(new Dimension(933, 32));
		GroupLayout jpToolBarLayout = new GroupLayout(jpToolBar);
		jpToolBar.setLayout(jpToolBarLayout);
		jpToolBarLayout
				.setHorizontalGroup(jpToolBarLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpToolBarLayout
										.createSequentialGroup()
										.addComponent(
												jToolBar1,
												GroupLayout.PREFERRED_SIZE,
												125,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jToolBar2,
												GroupLayout.PREFERRED_SIZE,
												132,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jToolBar3,
												GroupLayout.PREFERRED_SIZE,
												121,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(826, Short.MAX_VALUE)));
		jpToolBarLayout
				.setVerticalGroup(jpToolBarLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jpToolBarLayout
										.createSequentialGroup()
										.addGroup(
												jpToolBarLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jToolBar3,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jToolBar2,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jToolBar1,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																25,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		jspMainPanelView.setDividerLocation(280);
		jspMainPanelView.setDividerSize(3);

		JPanel jpLeftPanelView = new JPanel();
		jpLeftPanelView.setBackground(new Color(204, 204, 255));

		JSplitPane jspLeftPanelView = new JSplitPane();
		jspLeftPanelView.setDividerLocation(20);
		jspLeftPanelView.setDividerSize(1);
		jspLeftPanelView.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel jpProjectHeader = new JPanel();
		jpProjectHeader.setMaximumSize(new Dimension(32767, 25));
		jpProjectHeader.setMinimumSize(new Dimension(279, 25));
		jpProjectHeader.setPreferredSize(new Dimension(280, 25));

		JLabel jlFiles = new JLabel("Files");
		jlFiles.requestFocusInWindow();
		jlFiles.setBackground(new Color(255, 255, 255));
		jlFiles.setFont(new Font("Verdana", 1, 14)); // NOI18N
		jlFiles.setForeground(new Color(102, 102, 102));

		GroupLayout jpProjectHeaderLayout = new GroupLayout(jpProjectHeader);
		jpProjectHeader.setLayout(jpProjectHeaderLayout);
		jpProjectHeaderLayout.setHorizontalGroup(jpProjectHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						jpProjectHeaderLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlFiles)
								.addContainerGap(235, Short.MAX_VALUE)));
		jpProjectHeaderLayout.setVerticalGroup(jpProjectHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						jpProjectHeaderLayout.createSequentialGroup()
								.addComponent(jlFiles)
								.addContainerGap(7, Short.MAX_VALUE)));

		jspLeftPanelView.setTopComponent(jpProjectHeader);

		JPanel jpProjectDetails = new JPanel();
		jpProjectDetails.setBackground(new Color(255, 255, 255));

		JSplitPane jspProjectDetails = new JSplitPane();
		jspProjectDetails.setBackground(new Color(255, 255, 255));
		jspProjectDetails.setDividerLocation(130);
		jspProjectDetails.setDividerSize(1);
		jspProjectDetails.setOrientation(JSplitPane.VERTICAL_SPLIT);


		JScrollPane jspRawFiles = new JScrollPane();
		jspRawFiles.setBackground(new Color(255, 255, 255));

		jtRawFiles.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Name", "Path", "Type", "Version" }));
		jtRawFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtRawFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtRawFiles = (JTable) evt.getSource();
				
				// ... Right click event for displaying mzML data ...//
				if ((evt.getButton() == 1)
						&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2)
								.toString().equals("mzML"))) {
					loadMzMLView(jtRawFiles.getSelectedRow(), jtRawFiles);
					showChromatogram(jtRawFiles.getSelectedRow(), jtRawFiles
							.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
				}
				// ... Right click event for displaying MGF data ...//
				if ((evt.getButton() == 1)
						&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2)
								.toString().equals("MGF"))) {
					loadMGFView(jtRawFiles.getSelectedRow(), jlFileNameMGFText, jtRawFiles);
				}
			}
		});
		jspRawFiles.setViewportView(jtRawFiles);
		
		JLabel jlRawFilesIcon = new JLabel("Raw Files                                 ", new ImageIcon(getClass().getClassLoader().getResource("images/raw_file.gif")), SwingConstants.RIGHT);
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
								GroupLayout.Alignment.LEADING)
								.addComponent(jtpRawFiles,
										GroupLayout.DEFAULT_SIZE,
										277, Short.MAX_VALUE)));
		jpLeftMenuTopLayout
				.setVerticalGroup(jpLeftMenuTopLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGap(0, 129, Short.MAX_VALUE)
						.addGroup(
								jpLeftMenuTopLayout
										.createParallelGroup(
												GroupLayout.Alignment.LEADING)
										.addComponent(
												jtpRawFiles,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												129, Short.MAX_VALUE)));

		jspProjectDetails.setTopComponent(jpLeftMenuTop);

		final JSplitPane jspLeftMenuBottom = new JSplitPane();
		 // ... Ident and Quantitation separator
		jspLeftMenuBottom.setDividerLocation(200);
		jspLeftMenuBottom.setDividerSize(1);
		jspLeftMenuBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jtQuantFiles.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Name", "Path", "Type", "Version" }));
		jtQuantFiles
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtQuantFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtQuantFiles = (JTable) evt.getSource();
				
				if ((evt.getButton() == 1)
						&& (jtQuantFiles.getValueAt(jtQuantFiles.getSelectedRow(), 2)
								.toString().equals("mzq"))) {
					loadMzQuantMLView(jtQuantFiles.getSelectedRow(), jtQuantFiles
							.getValueAt(jtQuantFiles.getSelectedRow(), 1).toString());
				}
			}
		});
		JScrollPane jspQuantFiles = new JScrollPane();
		jspQuantFiles.setViewportView(jtQuantFiles);

		JLabel jlQuantFilesIcon = new JLabel("Quantitation Files                      ", new ImageIcon(getClass().getClassLoader().getResource("images/quant_file.gif")), SwingConstants.RIGHT);
		jlQuantFilesIcon.setIconTextGap(5);
		JTabbedPane jtpQuantFiles = new JTabbedPane();
		jtpQuantFiles.setBackground(new Color(255, 255, 255));
		jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);
		jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);

		JPanel jpQuantFiles = new JPanel();
		GroupLayout jpQuantFilesLayout = new GroupLayout(
				jpQuantFiles);
		jpQuantFiles.setLayout(jpQuantFilesLayout);
		jpQuantFilesLayout.setHorizontalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles,
						GroupLayout.DEFAULT_SIZE, 275,
						Short.MAX_VALUE));
		jpQuantFilesLayout.setVerticalGroup(jpQuantFilesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpQuantFiles,
						GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));

		jspLeftMenuBottom.setRightComponent(jpQuantFiles);
		
		jtIdentFiles
				.setModel(new DefaultTableModel(
						new String[][] {

						}, new String[] { "Name", "Path", "Type", "Version",
								"Raw File" }));
		jtIdentFiles
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtIdentFiles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JTable jtIdentFiles = (JTable) evt.getSource();
				if ((evt.getButton() == 1)
						&& (jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2)
								.toString().equals("mascot_xml"))) {
					loadMascotView(
							jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 0)
									.toString(),
							jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 1)
									.toString());
				}
				if ((evt.getButton() == 1)
						&& (jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2)
								.toString().equals("mzid"))) {
					loadMzIdentMLView(jtIdentFiles.getSelectedRow(), jtIdentFiles
							.getValueAt(jtIdentFiles.getSelectedRow(), 0).toString());
				}
			}
		});
		JScrollPane jspIdentFiles = new JScrollPane();
		jspIdentFiles.setViewportView(jtIdentFiles);

		JLabel jlIdentFilesIcon = new JLabel("Identification Files                    ", new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif")), SwingConstants.RIGHT);
		jlIdentFilesIcon.setIconTextGap(5);
		JTabbedPane jtpIdentFiles = new JTabbedPane();
		jtpIdentFiles.setBackground(new Color(255, 255, 255));
		jtpIdentFiles.addTab("Identification Files", jspIdentFiles);
		jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);


		jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

		JPanel jpLeftMenuBottom = new JPanel();
		GroupLayout jpLeftMenuBottomLayout = new GroupLayout(
				jpLeftMenuBottom);
		jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
		jpLeftMenuBottomLayout.setHorizontalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom,
						GroupLayout.DEFAULT_SIZE, 277,
						Short.MAX_VALUE));
		jpLeftMenuBottomLayout.setVerticalGroup(jpLeftMenuBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftMenuBottom,
						GroupLayout.DEFAULT_SIZE, 483,
						Short.MAX_VALUE));

		jspProjectDetails.setRightComponent(jpLeftMenuBottom);

		GroupLayout jpProjectDetailsLayout = new GroupLayout(
				jpProjectDetails);
		jpProjectDetails.setLayout(jpProjectDetailsLayout);
		jpProjectDetailsLayout.setHorizontalGroup(jpProjectDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProjectDetails));
		jpProjectDetailsLayout.setVerticalGroup(jpProjectDetailsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProjectDetails,
						GroupLayout.Alignment.TRAILING));

		jspLeftPanelView.setRightComponent(jpProjectDetails);

		GroupLayout jpLeftPanelViewLayout = new GroupLayout(
				jpLeftPanelView);
		jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
		jpLeftPanelViewLayout.setHorizontalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView,
						GroupLayout.DEFAULT_SIZE, 281,
						Short.MAX_VALUE));
		jpLeftPanelViewLayout.setVerticalGroup(jpLeftPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftPanelView,
						GroupLayout.DEFAULT_SIZE, 637,
						Short.MAX_VALUE));

		jspMainPanelView.setLeftComponent(jpLeftPanelView);

		jspViewerAndProperties.setDividerLocation(380);
		jspViewerAndProperties.setDividerSize(3);

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
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						jspLeftViewerHeaderLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlViewer)
								.addContainerGap(316, Short.MAX_VALUE)));
		jspLeftViewerHeaderLayout.setVerticalGroup(jspLeftViewerHeaderLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jtMSIndex,
												GroupLayout.PREFERRED_SIZE,
												54,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel3)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jtMSMz,
												GroupLayout.PREFERRED_SIZE,
												69,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(103, Short.MAX_VALUE)));
		jpRawDataValuesMenuLayout
				.setVerticalGroup(jpRawDataValuesMenuLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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

		jtRawData.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Index", "m/z", "Intensity" }));
		jtRawData
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane jspRawData = new JScrollPane();
		jspRawData.setViewportView(jtRawData);

		jspRawDataValues.setBottomComponent(jspRawData);

		JPanel jpRawDataValues = new JPanel();
		GroupLayout jpRawDataValuesLayout = new GroupLayout(
				jpRawDataValues);
		jpRawDataValues.setLayout(jpRawDataValuesLayout);
		jpRawDataValuesLayout.setHorizontalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues,
						GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpRawDataValuesLayout.setVerticalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues,
						GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 232,
						Short.MAX_VALUE));

		jtpLog.addTab("Raw Data", jpRawDataValues);

		jtTemplate1.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "m/z Index", "Scan Index", "Quant",
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
		
		GroupLayout jPanel5Layout = new GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373,
				Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 51,
				Short.MAX_VALUE));

		GroupLayout jPanel1Layout = new GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane1,
						GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addComponent(
												jPanel5,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												GroupLayout.DEFAULT_SIZE,
												175, Short.MAX_VALUE)));

		jtpLog.addTab("Template 1", jPanel1);

		jtTemplate2.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Template Index", "x", "y", "i" }));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jtTemplate2);

		GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 52, Short.MAX_VALUE));

		GroupLayout jPanel2Layout = new GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel6, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane2,
						GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addComponent(
												jPanel6,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane2,
												GroupLayout.DEFAULT_SIZE,
												174, Short.MAX_VALUE)));

		jtpLog.addTab("Template 2", jPanel2);

		jTable1.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane3 = new JScrollPane();
		jScrollPane3.setViewportView(jTable1);

		GroupLayout jPanel4Layout = new GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373,
				Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 58,
				Short.MAX_VALUE));

		GroupLayout jPanel3Layout = new GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel4, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane3,
						GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addComponent(
												jPanel4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane3,
												GroupLayout.DEFAULT_SIZE,
												168, Short.MAX_VALUE)));

		jtpLog.addTab("Synth Array", jPanel3);

		jTable2.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane7 = new JScrollPane();
		jScrollPane7.setViewportView(jTable2);

		GroupLayout jPanel7Layout = new GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373,
				Short.MAX_VALUE));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 57,
				Short.MAX_VALUE));

		JPanel jpPanel4 = new JPanel();
		GroupLayout jpPanel4Layout = new GroupLayout(
				jpPanel4);
		jpPanel4.setLayout(jpPanel4Layout);
		jpPanel4Layout.setHorizontalGroup(jpPanel4Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel7, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane7,
						GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpPanel4Layout
				.setVerticalGroup(jpPanel4Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPanel4Layout
										.createSequentialGroup()
										.addComponent(
												jPanel7,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane7,
												GroupLayout.DEFAULT_SIZE,
												169, Short.MAX_VALUE)));

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

		JPanel jpSpectrum = new JPanel();
		GroupLayout jpSpectrumLayout = new GroupLayout(
				jpSpectrum);
		jpSpectrum.setLayout(jpSpectrumLayout);
		jpSpectrumLayout
				.setHorizontalGroup(jpSpectrumLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGap(0, 373, Short.MAX_VALUE)
						.addGroup(
								jpSpectrumLayout
										.createParallelGroup(
												GroupLayout.Alignment.LEADING)
										.addComponent(
												jdpMS,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												373, Short.MAX_VALUE)));
		jpSpectrumLayout
				.setVerticalGroup(jpSpectrumLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGap(0, 321, Short.MAX_VALUE)
						.addGroup(
								jpSpectrumLayout
										.createParallelGroup(
												GroupLayout.Alignment.LEADING)
										.addComponent(
												jdpMS,
												GroupLayout.Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												321, Short.MAX_VALUE)));

		jtpViewer.addTab("Spectrum View", jpSpectrum);

		JPanel jpTIC = new JPanel();
		jpTIC.setBackground(new Color(255, 255, 255));

		jdpTIC.setBackground(new Color(255, 255, 255));
		jdpTIC.setForeground(new Color(255, 255, 255));

		GroupLayout jpTICLayout = new GroupLayout(jpTIC);
		jpTIC.setLayout(jpTICLayout);
		jpTICLayout.setHorizontalGroup(jpTICLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jdpTIC,
				GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		jpTICLayout.setVerticalGroup(jpTICLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jdpTIC,
				GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE));

		jtpViewer.addTab("TIC View", jpTIC);

		jp2D.setBackground(new Color(255, 255, 255));

		GroupLayout jp2DLayout = new GroupLayout(jp2D);
		jp2D.setLayout(jp2DLayout);
		jp2DLayout.setHorizontalGroup(jp2DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373,
				Short.MAX_VALUE));
		jp2DLayout.setVerticalGroup(jp2DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 321,
				Short.MAX_VALUE));

		jtpViewer.addTab("2D View", jp2D);

		JPanel jp3D = new JPanel();
		jp3D.setBackground(new Color(255, 255, 255));

		GroupLayout jp3DLayout = new GroupLayout(jp3D);
		jp3D.setLayout(jp3DLayout);
		jp3DLayout.setHorizontalGroup(jp3DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373,
				Short.MAX_VALUE));
		jp3DLayout.setVerticalGroup(jp3DLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 321,
				Short.MAX_VALUE));

		jtpViewer.addTab("3D View", jp3D);

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
		GroupLayout jpLeftViewerLayout = new GroupLayout(
				jpLeftViewer);
		jpLeftViewer.setLayout(jpLeftViewerLayout);
		jpLeftViewerLayout.setHorizontalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer));
		jpLeftViewerLayout.setVerticalGroup(jpLeftViewerLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspLeftViewer,
						GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));

		jspViewerAndProperties.setLeftComponent(jpLeftViewer);

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

		GroupLayout jpPropertiesLayout = new GroupLayout(
				jpProperties);
		jpProperties.setLayout(jpPropertiesLayout);
		jpPropertiesLayout.setHorizontalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						jpPropertiesLayout.createSequentialGroup()
								.addContainerGap().addComponent(jlProperties)
								.addContainerGap(417, Short.MAX_VALUE)));
		jpPropertiesLayout.setVerticalGroup(jpPropertiesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
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
		jtbMzMLOptions
				.setBorder(BorderFactory.createEtchedBorder());
		jtbMzMLOptions.setFloatable(false);
		jtbMzMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzMLExcel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jbExportMzMLExcelMouseClicked(evt);
			}
		});
		jtbMzMLOptions.add(jbExportMzMLExcel);

		JLabel jlExportMzMLXLS = new JLabel("Export to:");

		GroupLayout jpMzMLMenuLayout = new GroupLayout(
				jpMzMLMenu);
		jpMzMLMenu.setLayout(jpMzMLMenuLayout);
		jpMzMLMenuLayout
				.setHorizontalGroup(jpMzMLMenuLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
				
				// ... Right click event for displaying MS raw data and spectrum ...//
				if (evt.getButton() == 1) {
					if (jtRawFiles.getSelectedRow() > -1) {
						showRawData(jtRawFiles.getSelectedRow(),
								jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
										.toString(), jtRawData, jtpLog);
						showSpectrum(jtRawFiles.getSelectedRow(),
								jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
										.toString());
					} else {
						showRawData(0, jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), jtRawData, jtpLog);
						showSpectrum(0, jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString());
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
		GroupLayout jpMzMLViewHeaderLayout = new GroupLayout(
				jpMzMLViewHeader);
		jpMzMLViewHeader.setLayout(jpMzMLViewHeaderLayout);
		jpMzMLViewHeaderLayout
				.setHorizontalGroup(jpMzMLViewHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jScrollPane9,
												GroupLayout.PREFERRED_SIZE,
												52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(18, Short.MAX_VALUE)));

		jspMzML.setLeftComponent(jpMzMLViewHeader);

		JPanel jpMzML = new JPanel();
		GroupLayout jpMzMLLayout = new GroupLayout(
				jpMzML);
		jpMzML.setLayout(jpMzMLLayout);
		jpMzMLLayout.setHorizontalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addComponent(jspMzML));
		jpMzMLLayout.setVerticalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(
				jspMzML, GroupLayout.DEFAULT_SIZE, 585,
				Short.MAX_VALUE));

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

		GroupLayout jpMGFViewHeaderLayout = new GroupLayout(
				jpMGFViewHeader);
		jpMGFViewHeader.setLayout(jpMGFViewHeaderLayout);
		jpMGFViewHeaderLayout
				.setHorizontalGroup(jpMGFViewHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jScrollPane10,
												GroupLayout.PREFERRED_SIZE,
												52,
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
		jtScanTitle.setToolTipText("Enter any character(s) that is (are) specified in the scan title");
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
		jbExportMGFExcel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jbExportMGFExcelMouseClicked(evt);
			}
		});
		jtbMGFOptions.add(jbExportMGFExcel);

		JLabel jlExportMGFXLS = new JLabel("Export To:");
		JPanel jpMGFMenu = new JPanel();
		GroupLayout jpMGFMenuLayout = new GroupLayout(
				jpMGFMenu);
		jpMGFMenu.setLayout(jpMGFMenuLayout);
		jpMGFMenuLayout
				.setHorizontalGroup(jpMGFMenuLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						showRawDataMGF(jtRawFiles.getSelectedRow(),
								jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString(), jtRawData, jtRawFiles, jtpLog);
					} else {
						showRawDataMGF(0, jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString(), jtRawData, jtRawFiles, jtpLog);
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
		jtbMzIdentMLOptions.setBorder(BorderFactory
				.createEtchedBorder());
		jtbMzIdentMLOptions.setFloatable(false);
		jtbMzIdentMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzIdentMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel
				.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jbExportMzIdentMLExcelMouseClicked(evt);
					}
				});
		jtbMzIdentMLOptions.add(jbExportMzIdentMLExcel);

		JLabel jlExportMzIDXLS = new JLabel("Export to:");
		jtMzId.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Index", "Protein", "Peptide", "Rank", "Score",
				"Spectrum ID" }));
		JScrollPane jspMzIdProtGroup = new JScrollPane();
		jspMzIdProtGroup.setViewportView(jtMzId);

		JLabel jlPeptideSpectrumMatches = new JLabel("Peptide-Spectrum matches with rank:");

		jcbPSM.setModel(new DefaultComboBoxModel<String>(new String[] {
				"<=1", "<=2", "<=3", "All" }));
		jcbPSM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// ... Check file to visualize .../
				if (!jlFileNameMzIDText.equals("")) {
					int iIndex = getMzMLIndex(jlFileNameMzIDText.getText());
					loadMzIdentMLView(iIndex, jlFileNameMzIDText.getText());
				}
			}
		});


		JPanel jpMzIdMenu = new JPanel();
		GroupLayout jpMzIdMenuLayout = new GroupLayout(
				jpMzIdMenu);
		jpMzIdMenu.setLayout(jpMzIdMenuLayout);
		jpMzIdMenuLayout
				.setHorizontalGroup(jpMzIdMenuLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addComponent(jspMzIdProtGroup,
								GroupLayout.Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 563,
								Short.MAX_VALUE)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jspMzIdProtGroup,
												GroupLayout.DEFAULT_SIZE,
												155, Short.MAX_VALUE)
										.addGap(23, 23, 23)));

		jspMzIdDetail.setTopComponent(jpMzIdMenu);

		jtMzIDProtGroup.setAutoCreateRowSorter(true);
		jtMzIDProtGroup.setModel(new DefaultTableModel(
				new String[][] {

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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jspMzIDView,
												GroupLayout.PREFERRED_SIZE,
												52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(17, Short.MAX_VALUE)));

		jspMzId.setLeftComponent(jpMzIdentMLViewHeader);

		JPanel jpMzId = new JPanel();
		GroupLayout jpMzIdLayout = new GroupLayout(
				jpMzId);
		jpMzId.setLayout(jpMzIdLayout);
		jpMzIdLayout.setHorizontalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addComponent(jspMzId));
		jpMzIdLayout.setVerticalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(
				jspMzId, GroupLayout.Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE));

		jtpProperties.addTab("mzIdentML View", jpMzId);

		JSplitPane jspMascotXML = new JSplitPane();
		jspMascotXML.setDividerLocation(110);
		jspMascotXML.setDividerSize(1);
		jspMascotXML.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane jspMascotXMLSubDetail = new JSplitPane();
		jspMascotXMLSubDetail.setDividerLocation(80);
		jspMascotXMLSubDetail.setDividerSize(1);
		jspMascotXMLSubDetail
				.setOrientation(JSplitPane.VERTICAL_SPLIT);

		jtMascotXMLView.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Index", "Protein", "Peptide", "Composition",
						"Exp Mz", "Exp Mr", "Charge", "Score", "Scan",
						"Scan ID", "RT (sec)" }));
		JScrollPane jspMascotXMLDetail = new JScrollPane();
		jspMascotXMLDetail.setViewportView(jtMascotXMLView);

		jspMascotXMLSubDetail.setRightComponent(jspMascotXMLDetail);

		JLabel jlSearchMascotXML = new JLabel("Search:");
		jlSearchMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlProtein = new JLabel("Protein:");

		final JTextField jtProtein = new JTextField();
		jtProtein.addKeyListener(new KeyListenerSearch(1, jtMascotXMLView, true));

		JLabel jlPeptide = new JLabel("Peptide:");

		final JTextField jtPeptide = new JTextField();
		jtPeptide.addKeyListener(new KeyListenerSearch(2, jtMascotXMLView, true));

		JLabel jlExportMzIDXLS1 = new JLabel("Export to:");

		JToolBar jtbMzIdentMLOptions1 = new JToolBar();
		jtbMzIdentMLOptions1.setBorder(BorderFactory
				.createEtchedBorder());
		jtbMzIdentMLOptions1.setFloatable(false);
		jtbMzIdentMLOptions1.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setPreferredSize(new Dimension(100, 23));

		jbExportMascotXMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMascotXMLExcel
				.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jbExportMascotXMLExcelMouseClicked(evt);
					}
				});
		jtbMzIdentMLOptions1.add(jbExportMascotXMLExcel);

		JPanel jpMascotXMLMenu = new JPanel();
		GroupLayout jpMascotXMLMenuLayout = new GroupLayout(
				jpMascotXMLMenu);
		jpMascotXMLMenu.setLayout(jpMascotXMLMenuLayout);
		jpMascotXMLMenuLayout
				.setHorizontalGroup(jpMascotXMLMenuLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jspMascotXMLView,
												GroupLayout.PREFERRED_SIZE,
												52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(19, Short.MAX_VALUE)));

		jspMascotXML.setLeftComponent(jpMascotXMLViewHeader);

		JPanel jpMascotXML = new JPanel();
		GroupLayout jpMascotXMLLayout = new GroupLayout(
				jpMascotXML);
		jpMascotXML.setLayout(jpMascotXMLLayout);
		jpMascotXMLLayout.setHorizontalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML));
		jpMascotXMLLayout.setVerticalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML,
						GroupLayout.DEFAULT_SIZE, 585,
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
		jtPeptideMZQ.addKeyListener(new KeyListenerSearch(0, jtPeptideQuant, false));

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
		jbExportPepMZQExcel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				jbExportPepMZQExcelMouseClicked(evt);
			}
		});
		jtbPepMZQ.add(jbExportPepMZQExcel);
		
		JPanel jpPeptideQuantHeader = new JPanel();
		GroupLayout jpPeptideQuantHeaderLayout = new GroupLayout(
				jpPeptideQuantHeader);
		jpPeptideQuantHeader.setLayout(jpPeptideQuantHeaderLayout);
		jpPeptideQuantHeaderLayout
				.setHorizontalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPeptideQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQPep)
										.addGap(18, 18, 18)
										.addComponent(jlPeptideMZQ)
										.addGap(18, 18, 18)
										.addComponent(
												jtPeptideMZQ,
												GroupLayout.PREFERRED_SIZE,
												209,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jtbPepMZQ,
												GroupLayout.DEFAULT_SIZE,
												193, Short.MAX_VALUE)
										.addContainerGap()));
		jpPeptideQuantHeaderLayout
				.setVerticalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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

		jtPeptideQuant.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Peptide", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspPeptideQuantDetail = new JScrollPane();
		jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

		jspPeptideQuant.setRightComponent(jspPeptideQuantDetail);

		JPanel jpPeptideQuant = new JPanel();
		GroupLayout jpPeptideQuantLayout = new GroupLayout(
				jpPeptideQuant);
		jpPeptideQuant.setLayout(jpPeptideQuantLayout);
		jpPeptideQuantLayout.setHorizontalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant));
		jpPeptideQuantLayout.setVerticalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant,
						GroupLayout.DEFAULT_SIZE, 445,
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
		jtProteinMZQ.addKeyListener(new KeyListenerSearch(0, jtProteinQuant, false));

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
		jbExportProtMZQExcel
				.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jbExportProtMZQExcelMouseClicked(evt);
					}
				});
		jtbProtMZQ.add(jbExportProtMZQExcel);

		JPanel jpProteinQuantHeader = new JPanel();
		GroupLayout jpProteinQuantHeaderLayout = new GroupLayout(
				jpProteinQuantHeader);
		jpProteinQuantHeader.setLayout(jpProteinQuantHeaderLayout);
		jpProteinQuantHeaderLayout
				.setHorizontalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProteinQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQProt)
										.addGap(18, 18, 18)
										.addComponent(jlProteinMZQ)
										.addGap(18, 18, 18)
										.addComponent(
												jtProteinMZQ,
												GroupLayout.PREFERRED_SIZE,
												209,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jtbProtMZQ,
												GroupLayout.DEFAULT_SIZE,
												195, Short.MAX_VALUE)
										.addContainerGap()));
		jpProteinQuantHeaderLayout
				.setVerticalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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

		jtProteinQuant.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Protein", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspProteinQuantDetail = new JScrollPane();
		jspProteinQuantDetail.setViewportView(jtProteinQuant);

		jspProteinQuant.setRightComponent(jspProteinQuantDetail);

		JPanel jpProteinQuant = new JPanel();
		GroupLayout jpProteinQuantLayout = new GroupLayout(
				jpProteinQuant);
		jpProteinQuant.setLayout(jpProteinQuantLayout);
		jpProteinQuantLayout.setHorizontalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant));
		jpProteinQuantLayout.setVerticalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant,
						GroupLayout.DEFAULT_SIZE, 445,
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
		jtFeatureMZQ.addKeyListener(new KeyListenerSearch(0, jtFeatureQuant, false));

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
		jbExportFeatMZQExcel
				.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jbExportFeatMZQExcelMouseClicked(evt);
					}
				});
		jtbFeatMZQ.add(jbExportFeatMZQExcel);

		JPanel jpFeatureQuantHeader = new JPanel();
		GroupLayout jpFeatureQuantHeaderLayout = new GroupLayout(
				jpFeatureQuantHeader);
		jpFeatureQuantHeader.setLayout(jpFeatureQuantHeaderLayout);
		jpFeatureQuantHeaderLayout
				.setHorizontalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFeatureQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQFeat)
										.addGap(18, 18, 18)
										.addComponent(jlFeatureMZQ)
										.addGap(18, 18, 18)
										.addComponent(
												jtFeatureMZQ,
												GroupLayout.PREFERRED_SIZE,
												209,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jtbFeatMZQ,
												GroupLayout.DEFAULT_SIZE,
												191, Short.MAX_VALUE)
										.addContainerGap()));
		jpFeatureQuantHeaderLayout
				.setVerticalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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

		jtFeatureQuant.setModel(new DefaultTableModel(
				new String[][] {

				}, new String[] { "Feature", "Label 1", "Label 2", "Label 3",
						"Label 4" }));
		JScrollPane jspFeatureQuantDetail = new JScrollPane();
		jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

		jspFeatureQuant.setRightComponent(jspFeatureQuantDetail);

		final JPanel FeatureQuant = new JPanel();
		GroupLayout FeatureQuantLayout = new GroupLayout(
				FeatureQuant);
		FeatureQuant.setLayout(FeatureQuantLayout);
		FeatureQuantLayout.setHorizontalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant,
						GroupLayout.Alignment.TRAILING));
		FeatureQuantLayout.setVerticalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant,
						GroupLayout.DEFAULT_SIZE, 445,
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
		GroupLayout jpMzQuantMLHeaderLayout = new GroupLayout(
				jpMzQuantMLHeader);
		jpMzQuantMLHeader.setLayout(jpMzQuantMLHeaderLayout);
		jpMzQuantMLHeaderLayout
				.setHorizontalGroup(jpMzQuantMLHeaderLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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
										.addComponent(
												jScrollPane12,
												GroupLayout.PREFERRED_SIZE,
												52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(13, Short.MAX_VALUE)));

		jspMzQuantML.setLeftComponent(jpMzQuantMLHeader);

		JPanel jpMzQuantML = new JPanel();
		GroupLayout jpMzQuantMLLayout = new GroupLayout(
				jpMzQuantML);
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
		GroupLayout jpPropetiesTabLayout = new GroupLayout(
				jpPropetiesTab);
		jpPropetiesTab.setLayout(jpPropetiesTabLayout);
		jpPropetiesTabLayout.setHorizontalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));
		jpPropetiesTabLayout.setVerticalGroup(jpPropetiesTabLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jtpProperties));

		jspProperties.setRightComponent(jpPropetiesTab);

		JPanel jpPropertiesBox = new JPanel();
		GroupLayout jpPropertiesBoxLayout = new GroupLayout(
				jpPropertiesBox);
		jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
		jpPropertiesBoxLayout.setHorizontalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties));
		jpPropertiesBoxLayout.setVerticalGroup(jpPropertiesBoxLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProperties,
						GroupLayout.DEFAULT_SIZE, 635,
						Short.MAX_VALUE));

		jspViewerAndProperties.setRightComponent(jpPropertiesBox);

		JPanel jpViewerAndProperties = new JPanel();
		jpViewerAndProperties.setBackground(new Color(204, 204, 255));
		GroupLayout jpViewerAndPropertiesLayout = new GroupLayout(
				jpViewerAndProperties);
		jpViewerAndProperties.setLayout(jpViewerAndPropertiesLayout);
		jpViewerAndPropertiesLayout
				.setHorizontalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties,
								GroupLayout.DEFAULT_SIZE, 958,
								Short.MAX_VALUE));
		jpViewerAndPropertiesLayout
				.setVerticalGroup(jpViewerAndPropertiesLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addComponent(jspViewerAndProperties));

		jspMainPanelView.setRightComponent(jpViewerAndProperties);

		GroupLayout jpMainPanelViewLayout = new GroupLayout(jpMainPanelView);
		jpMainPanelView.setLayout(jpMainPanelViewLayout);
		
		jpMainPanelViewLayout.setHorizontalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView,
						GroupLayout.DEFAULT_SIZE, 1242,
						Short.MAX_VALUE));
		jpMainPanelViewLayout.setVerticalGroup(jpMainPanelViewLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMainPanelView));

		jpProjectStatus.setBorder(BorderFactory
				.createEtchedBorder());
		jpProjectStatus.setForeground(new Color(153, 153, 153));
		jpProjectStatus.setPreferredSize(new Dimension(102, 30));

		JLabel jlRawFiles = new JLabel("1) Raw Files:");
		JLabel jlIdentFiles = new JLabel("2) Identification Files:");
		JLabel jlTechnique = new JLabel("3) Technique:");
		JLabel jlQuantFiles = new JLabel("4) Quant Files:");
		
		jlQuantFiles.setBackground(new Color(255, 255, 255));

		jlRawFilesStatus.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/empty.gif")));
		jlRawFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlRawFilesStatus.setForeground(new Color(51, 102, 0));

		jlIdentFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlIdentFilesStatus.setForeground(new Color(51, 102, 0));

		JLabel jlQuantFilesStatus = new JLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("images/empty.gif")));
		jlQuantFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		jlQuantFilesStatus.setForeground(new Color(51, 102, 0));

		jcbTechnique.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Select technique", "iTRAQ", "TMT", "emPAI" }));
		jcbTechnique.setToolTipText("Select a technique for the analysis");
		jcbTechnique.setBorder(BorderFactory.createEtchedBorder());

		jcbOutputFormat.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Select format", "mzq" }));
		jcbOutputFormat.setToolTipText("Select an output format");

		GroupLayout jpProjectStatusLayout = new GroupLayout(
				jpProjectStatus);
		jpProjectStatus.setLayout(jpProjectStatusLayout);
		jpProjectStatusLayout
				.setHorizontalGroup(jpProjectStatusLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProjectStatusLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlRawFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jlRawFilesStatus,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlIdentFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jlIdentFilesStatus,
												GroupLayout.PREFERRED_SIZE,
												34,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlTechnique)
										.addGap(18, 18, 18)
										.addComponent(
												jcbTechnique,
												GroupLayout.PREFERRED_SIZE,
												119,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jlQuantFiles)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jcbOutputFormat,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jlQuantFilesStatus,
												GroupLayout.PREFERRED_SIZE,
												43,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(499, Short.MAX_VALUE)));
		jpProjectStatusLayout
				.setVerticalGroup(jpProjectStatusLayout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
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

		JMenuItem jmNewProject = new JMenuItem("New Project", new ImageIcon(getClass().getClassLoader().getResource("images/new.gif")));
		jmNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		jmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmNewProjectActionPerformed(evt, jtRawFiles);
			}
		});
		jmFile.add(jmNewProject);
		
		
		JMenuItem jmImportFile = new JMenuItem("Import File", new ImageIcon(getClass().getClassLoader().getResource("images/import.gif")));
		jmImportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		jmImportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmImportFileActionPerformed(evt, jtRawFiles);
			}
		});
		jmFile.add(jmImportFile);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmOpenProject = new JMenuItem("Open Project", new ImageIcon(getClass().getClassLoader().getResource("images/open.gif")));
		jmOpenProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		jmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				openProject(sPreviousLocation, sWorkspace, jtRawFiles);
			}
		});
		jmFile.add(jmOpenProject);
		
		JMenuItem jmOpenRecentProject = new JMenuItem("Open Recent Project");
		jmOpenRecentProject.setEnabled(false);
		jmFile.add(jmOpenRecentProject);

		jmCloseProject.setEnabled(false);
		jmCloseProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmCloseProjectActionPerformed(evt, jtRawFiles);
			}
		});
		jmFile.add(jmCloseProject);
		jmFile.add(new JPopupMenu.Separator());

		jmSaveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		jmSaveProject.setEnabled(false);
		jmSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmSaveProjectActionPerformed(evt, jtRawFiles);
			}
		});
		jmFile.add(jmSaveProject);

		JMenuItem jmSaveProjectAs = new JMenuItem("Save Project As");
		jmSaveProjectAs.setEnabled(false);
		jmFile.add(jmSaveProjectAs);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmPrint = new JMenuItem("Print", new ImageIcon(getClass().getClassLoader().getResource("images/print.gif")));
		jmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		jmPrint.setEnabled(false);
		jmFile.add(jmPrint);
		jmFile.add(new JPopupMenu.Separator());

		JMenuItem jmExit = new JMenuItem("Exit");
		jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		jmExit.addActionListener(new ActionListenerExit());
		jmFile.add(jmExit);

		JMenuBar jmMain = new JMenuBar();
		jmMain.add(jmFile);

		JMenu jmEdit = new JMenu("Edit");

		JMenuItem jmCut = new JMenuItem("Cut", new ImageIcon(getClass().getClassLoader().getResource("images/cut.gif")));
		jmCut.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C,
				InputEvent.CTRL_MASK));
		jmCut.setEnabled(false);
		jmEdit.add(jmCut);

		JMenuItem jmCopy = new JMenuItem("Copy", new ImageIcon(getClass().getClassLoader().getResource(
				"images/copy.gif")));
		jmCopy.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		jmCopy.setEnabled(false);
		jmEdit.add(jmCopy);


		JMenuItem jmPaste = new JMenuItem("Paste", new ImageIcon(getClass().getClassLoader().getResource(
				"images/paste.gif")));
		jmPaste.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_V,
				InputEvent.CTRL_MASK));
		jmPaste.setEnabled(false);
		jmEdit.add(jmPaste);
		jmEdit.add(new JPopupMenu.Separator());

		jmMain.add(jmEdit);

		JMenu jmView = new JMenu("View");

		final JMenuItem jmShowProjectFiles = new JMenuItem("Show/Hide Project Files", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		jmShowProjectFiles
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (jspMainPanelView.getDividerLocation() <= 5) {
							jspMainPanelView.setDividerLocation(250);
							Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
									"images/thick.gif"));
							jmShowProjectFiles.setIcon(thick);
						} else {
							jspMainPanelView.setDividerLocation(0);
							jmShowProjectFiles.setIcon(null);
						}
					}
				});
		jmView.add(jmShowProjectFiles);

		final JMenuItem jmLog = new JMenuItem("Log", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		final JMenuItem jmShowViewer = new JMenuItem("Show/Hide Viewer", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		jmShowViewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jspViewerAndProperties.getDividerLocation() <= 5) {
					jspViewerAndProperties.setDividerLocation(600);
					jmShowViewer.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
				} else {
					jspViewerAndProperties.setDividerLocation(0);
					jmShowViewer.setIcon(null);
				}
			}
		});
		jmView.add(jmShowViewer);

		JMenuItem jmShowProperties = new JMenuItem("Show/Hide Properties", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		jmShowProperties.setEnabled(false);
		jmShowProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
			}
		});
		jmView.add(jmShowProperties);

		jmMain.add(jmView);

		JMenu jmProject = new JMenu("Project");

		JMenuItem jmEditIdent = new JMenuItem("Set Identification Parameters", new ImageIcon(getClass().getClassLoader().getResource(
				"images/settings.gif")));
		jmProject.add(jmEditIdent);


		JMenuItem jmEditQuant = new JMenuItem("Set Quantitation Parameters", new ImageIcon(getClass().getClassLoader().getResource("images/settings.gif")));
		jmEditQuant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jmEditQuantActionPerformed(evt, jtRawFiles);
			}
		});
		jmProject.add(jmEditQuant);

		jmMain.add(jmProject);

		JMenu jmAnalyze = new JMenu("Analyze");

		JMenuItem jmRunIdentAnalysis = new JMenuItem("Run Identification Analysis", new ImageIcon(getClass().getClassLoader().getResource(
				"images/runid.gif")));
		jmRunIdentAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, 0));
		jmRunIdentAnalysis
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jmRunIdentAnalysisActionPerformed(evt, jtRawFiles);
					}
				});
		jmAnalyze.add(jmRunIdentAnalysis);

		JMenuItem jmRunQuantAnalysis = new JMenuItem("Run Quantition Analysis", new ImageIcon(getClass().getClassLoader().getResource(
				"images/run.gif")));
		jmRunQuantAnalysis.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F5, 0));
		jmRunQuantAnalysis
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jmRunQuantAnalysisActionPerformed(evt, jtRawFiles, jtpLog);
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
				MaxQ2MZQView winParams = new MaxQ2MZQView(jfWinParams, sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 150);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
						0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader().getResource(
						"images/icon.gif")).getImage();
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
				final JFrame jfWinParams = new JFrame("Convert Progenesis result files to mzQuantML");
				Prog2MZQView winParams = new Prog2MZQView(jfWinParams, sWorkspace);
				jfWinParams.setResizable(false);
				jfWinParams.setSize(500, 180);
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
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
				KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
						0, false);
				Action escapeAction = new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						jfWinParams.dispose();
					}
				};
				jfWinParams.getRootPane()
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(escapeKeyStroke, "ESCAPE");
				jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x1 = dim.width / 2;
				int y1 = dim.height / 2;
				int x2 = jfWinParams.getSize().width / 2;
				int y2 = jfWinParams.getSize().height / 2;
				jfWinParams.setLocation(x1 - x2, y1 - y2);
				Image iconApp = new ImageIcon(getClass().getClassLoader().getResource(
						"images/icon.gif")).getImage();
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
				jmSubmitPRIDEActionPerformed(evt);
			}
		});
		jmDatabases.add(jmSubmitPRIDE);

		jmMain.add(jmDatabases);

		JMenu jmWindow = new JMenu("Window");
		final JMenuItem jm2DView = new JMenuItem("2D View");
		final JMenuItem jm3DView = new JMenuItem("3D View");
		final JMenuItem jmTIC = new JMenuItem("TIC");
		
		final JMenuItem jmSpectrum = new JMenuItem("Spectrum", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		jmSpectrum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpViewer.setSelectedIndex(0);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmSpectrum.setIcon(thick);
				jmTIC.setIcon(null);
				jm2DView.setIcon(null);
				jm3DView.setIcon(null);
			}
		});
		jmWindow.add(jmSpectrum);

		jmTIC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpViewer.setSelectedIndex(1);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmSpectrum.setIcon(null);
				jmTIC.setIcon(thick);
				jm2DView.setIcon(null);
				jm3DView.setIcon(null);
			}
		});
		jmWindow.add(jmTIC);

		jm2DView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpViewer.setSelectedIndex(2);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmSpectrum.setIcon(null);
				jmTIC.setIcon(null);
				jm2DView.setIcon(thick);
				jm3DView.setIcon(null);
			}
		});
		jmWindow.add(jm2DView);

		jm3DView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpViewer.setSelectedIndex(3);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmSpectrum.setIcon(null);
				jmTIC.setIcon(null);
				jm2DView.setIcon(null);
				jm3DView.setIcon(thick);
			}
		});
		jmWindow.add(jm3DView);
		jmWindow.add(new JPopupMenu.Separator());
		final JMenuItem jmMGFView = new JMenuItem("MGF View");
		final JMenuItem jmMascotXMLView = new JMenuItem("Mascot XML View");
		final JMenuItem jmMzIdentMLView = new JMenuItem("mzIdentML View");

		final JMenuItem jmMzMLView = new JMenuItem("mzML View", new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
		final JMenuItem jmMzQuantMLView = new JMenuItem("mzQuantML View");
		final JMenuItem jmRawData = new JMenuItem("Raw Data");
		
		
		jmMzMLView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpProperties.setSelectedIndex(0);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmMzMLView.setIcon(thick);
				jmMGFView.setIcon(null);
				jmMzIdentMLView.setIcon(null);
				jmMascotXMLView.setIcon(null);
				jmMzQuantMLView.setIcon(null);
			}
		});
		jmWindow.add(jmMzMLView);

		jmMGFView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpProperties.setSelectedIndex(1);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmMzMLView.setIcon(null);
				jmMGFView.setIcon(thick);
				jmMzIdentMLView.setIcon(null);
				jmMascotXMLView.setIcon(null);
				jmMzQuantMLView.setIcon(null);
			}
		});
		jmWindow.add(jmMGFView);

		jmMzIdentMLView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpProperties.setSelectedIndex(2);
				jmMzMLView.setIcon(null);
				jmMGFView.setIcon(null);
				jmMzIdentMLView.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
				jmMascotXMLView.setIcon(null);
				jmMzQuantMLView.setIcon(null);
			}
		});
		jmWindow.add(jmMzIdentMLView);

		jmMascotXMLView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpProperties.setSelectedIndex(3);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmMzMLView.setIcon(null);
				jmMGFView.setIcon(null);
				jmMzIdentMLView.setIcon(null);
				jmMascotXMLView.setIcon(thick);
				jmMzQuantMLView.setIcon(null);
			}
		});
		jmWindow.add(jmMascotXMLView);

		jmMzQuantMLView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpProperties.setSelectedIndex(4);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmMzMLView.setIcon(null);
				jmMGFView.setIcon(null);
				jmMzIdentMLView.setIcon(null);
				jmMascotXMLView.setIcon(null);
				jmMzQuantMLView.setIcon(thick);
			}
		});
		jmWindow.add(jmMzQuantMLView);
		jmWindow.add(new JPopupMenu.Separator());

		jmLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpLog.setSelectedIndex(0);
				jmLog.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif")));
				jmRawData.setIcon(null);
			}
		});
		jmWindow.add(jmLog);

		jmRawData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jtpLog.setSelectedIndex(1);
				Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
						"images/thick.gif"));
				jmLog.setIcon(null);
				jmRawData.setIcon(thick);
			}
		});
		jmWindow.add(jmRawData);

		jmMain.add(jmWindow);

		JMenu jmHelp = new JMenu("Help");

		JMenuItem jmHelpContent = new JMenuItem("ProteoSuite Help", new ImageIcon(getClass().getClassLoader().getResource(
				"images/help.gif")));
		jmHelpContent.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F1, 0));
		jmHelpContent.setToolTipText("Find how to use ProteoSuite");
		jmHelpContent.addActionListener(new ActionListenerOpenURL("http://www.proteosuite.org/?q=tutorials"));

		jmHelp.add(jmHelpContent);
		jmHelp.add(new JPopupMenu.Separator());

		JMenuItem jmContactUs = new JMenuItem("Contact us");
		jmContactUs.setToolTipText("Click here for contacting our group and collaborators");
		jmContactUs.addActionListener(new ActionListenerOpenURL("http://www.proteosuite.org/?q=contact"));

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
				.addComponent(jpMainPanelView,
						GroupLayout.DEFAULT_SIZE,
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
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jpToolBar,
										GroupLayout.PREFERRED_SIZE,
										40,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpProjectStatus,
										GroupLayout.PREFERRED_SIZE,
										37,
										GroupLayout.PREFERRED_SIZE)
								.addGap(23, 23, 23)
								.addComponent(jpMainPanelView,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jmNewProjectActionPerformed(ActionEvent evt, JTable jtRawFiles) {// GEN-FIRST:event_jmNewProjectActionPerformed
		// ... Check if the project needs to be saved ...//
		if (bProjectModified) {
			int iOption = JOptionPane
					.showConfirmDialog(
							this,
							"You have not saved your changes. Do you want to save them now?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
			if (iOption == JOptionPane.OK_OPTION) {
				jmSaveProjectActionPerformed(null, jtRawFiles);
				initProjectValues();
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues();
			}
		} else {
			initProjectValues();
		}
	}// GEN-LAST:event_jmNewProjectActionPerformed

	private void jmImportFileActionPerformed(ActionEvent evt, final JTable jtRawFiles) {// GEN-FIRST:event_jmImportFileActionPerformed
		// ... Selecting file(s) ...//
		JFileChooser chooser = new JFileChooser(sPreviousLocation);
		chooser.setDialogTitle("Select the file(s) to analyze");

		// ... Filters must be in descending order ...//
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("mzQuantML Files (*.mzq, *.mzq.gz)", "mzq"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Identification Files (*.mzid, *.mzid.gz, *.xml)", "mzid", "gz", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Raw Files (*.mzML, *.mzML.gz, *.mgf)", "mzML", "gz", "mgf"));

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
				updateSaveProjectStatus(jmSaveProject);
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
									Unmarshaller.unmarshalMzMLFile(model, xmlFile, "", aMzMLUnmarshaller);
									
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
							loadMzMLView(0, jtRawFiles);
							sMessage = SYS_UTILS.getTime()
									+ " - Displaying chromatogram ";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							showChromatogram(0, aFiles[0].getPath());
							sMessage = SYS_UTILS.getTime()
									+ " - Displaying 2D Plot";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							show2DPlot(0, aFiles[0].getPath(), jp2D);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMzMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
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
							loadMGFView(0, jlFileNameMGFText, jtRawFiles);

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Raw files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMGFExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
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
									Unmarshaller.unmarshalMzIDFile(model, xmlFile, "", aMzIDUnmarshaller);
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
							loadMzIdentMLView(0, aFiles[0].getName());

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();

							sMessage = SYS_UTILS.getTime()
									+ " - Identification files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMzIdentMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
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
							loadMascotView(aFiles[0].getName(), aFiles[0]
									.getPath().toString().replace("\\", "/"));

							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
							sMessage = SYS_UTILS.getTime()
									+ " - Identifiation files imported successfully!";
							System.out.println(sMessage);
							sOutput = sOutput + sMessage + "\n";
							jtaLog.setText(sOutput);
							jbExportMascotXMLExcel.setEnabled(true);
							renderIdentFiles(jtRawFiles, jtIdentFiles);
							updateStatusPipeline(jlRawFilesStatus, jtRawFiles);

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

							// ... Reading selected files ...//
							boolean isOK = true;
							for (int iI = 0; iI < aFiles.length; iI++) {
								// ... Validate file extension (mixed files)
								// ...//
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

									// ... Unmarshall data using jmzIdentML API
									// ...//
									sMessage = SYS_UTILS.getTime()
											+ " - Unmarshalling "
											+ xmlFile.getName() + " starts ";
									System.out.println(sMessage);
									sOutput = sOutput + sMessage + "\n";
									jtaLog.setText(sOutput);

									try {
										isOK = Unmarshaller.unmarshalMzQMLFile(model, xmlFile, aMzQUnmarshaller);
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
							} // ... For files ...//
							if (isOK) {
								// ... Display first element ...//
								sMessage = SYS_UTILS.getTime()
										+ " - Loading mzQuantML view ";
								System.out.println(sMessage);
								sOutput = sOutput + sMessage + "\n";
								jtaLog.setText(sOutput);
								// loadMzQuantMLView(0, aFiles[0].getName());
								loadMzQuantMLView(0,
										aFiles[0].getAbsolutePath());

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
	}// GEN-LAST:event_jmImportFileActionPerformed

	private void jmSaveProjectActionPerformed(ActionEvent evt, JTable jtRawFiles) {// GEN-FIRST:event_jmSaveProjectActionPerformed
		// ... Save data using a mzq file ...//
		boolean isOK = saveProject(jtRawFiles);
		if (isOK) {
			JOptionPane.showMessageDialog(this,
					"Your file was saved successfully!", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jmSaveProjectActionPerformed

	private void jmCloseProjectActionPerformed(ActionEvent evt, JTable jtRawFiles) {// GEN-FIRST:event_jmCloseProjectActionPerformed
		// ... Check if the project needs to be saved ...//
		if (bProjectModified) {
			int iOption = JOptionPane
					.showConfirmDialog(
							this,
							"You have not saved your changes. Do you want to save them now?",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
			if (iOption == JOptionPane.OK_OPTION) {
				jmSaveProjectActionPerformed(null, jtRawFiles);
				initProjectValues();
			} else if (iOption == JOptionPane.NO_OPTION) {
				initProjectValues();
			}
		} else {
			initProjectValues();
		}
	}// GEN-LAST:event_jmCloseProjectActionPerformed

	private void jmSubmitPRIDEActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jmSubmitPRIDEActionPerformed
		JOptionPane
				.showMessageDialog(
						this,
						"The module for submitting data into PRIDE is under development.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
	}// GEN-LAST:event_jmSubmitPRIDEActionPerformed

	private void jmEditQuantActionPerformed(ActionEvent evt, JTable jtRawFiles) {// GEN-FIRST:event_jmEditQuantActionPerformed
		if (jcbTechnique.getSelectedItem().equals("Select technique")) {
			JOptionPane
					.showMessageDialog(
							this,
							"Please specify the technique used in your pipeline (e.g. iTRAQ, TMT, etc.)",
							"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			List<String> alRawFiles = new ArrayList<String>();
			for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
				alRawFiles.add((String) jtRawFiles.getValueAt(iI, 0));
			}
			QuantParamsView quantParams = new QuantParamsView(alRawFiles,
					sWorkspace, jcbTechnique.getSelectedItem().toString());
			final JFrame jfQuantParams = new JFrame(
					"Edit Quantitation Parameters");
			jfQuantParams.setResizable(false);
			jfQuantParams.setSize(638, 585);
			KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
					KeyEvent.VK_ESCAPE, 0, false);
			final Action escapeAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					jfQuantParams.dispose();
				}
			};
			jfQuantParams.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					escapeAction.actionPerformed(null);
				}
			});

			jfQuantParams.getRootPane()
					.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(escapeKeyStroke, "ESCAPE");
			jfQuantParams.getRootPane().getActionMap()
					.put("ESCAPE", escapeAction);

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x1 = dim.width / 2;
			int y1 = dim.height / 2;
			int x2 = jfQuantParams.getSize().width / 2;
			int y2 = jfQuantParams.getSize().height / 2;
			jfQuantParams.setLocation(x1 - x2, y1 - y2);
			Image iconApp = new ImageIcon(getClass().getClassLoader()
					.getResource("images/icon.gif")).getImage();
			jfQuantParams.setIconImage(iconApp);
			jfQuantParams.setAlwaysOnTop(true);

			jfQuantParams.add(quantParams);
			jfQuantParams.pack();
			jfQuantParams.setVisible(true);
		}
	}// GEN-LAST:event_jmEditQuantActionPerformed

	private void jmRunQuantAnalysisActionPerformed(ActionEvent evt, final JTable jtRawFiles, JTabbedPane jtpLog) {// GEN-FIRST:event_jmRunQuantAnalysisActionPerformed
		// ... Check if there is a valid workspace ...//
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
				isOK = checkMapping(jtpLog);
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
									isOK = generateFiles(jtRawFiles);
									if (isOK) {
										// ... Run xTracker ...//
										System.out
												.println(SYS_UTILS.getTime()
														+ " - ****** Running x-Tracker *****");
										jtaLog.append("\n"
												+ SYS_UTILS.getTime()
												+ " - ****** Running x-Tracker *****");
										new xTracker(sWorkspace.replace("\\", "/") + "/" + sProjectName, sWorkspace.replace("\\", "/"));

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
											isOK = Unmarshaller.unmarshalMzQMLFile(model, xmlFile, aMzQUnmarshaller);
											System.out
													.println(SYS_UTILS.getTime()
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
													+ "/" + sProjectName);
											System.out
													.println(SYS_UTILS.getTime()
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
									updateSaveProjectStatus(jmSaveProject);
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
	}// GEN-LAST:event_jmRunQuantAnalysisActionPerformed

	private void jbExportMzMLExcelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jbExportMzMLExcelMouseClicked
		if (jtMzML.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// ... Applying file extension filters ...//
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order ...//
				chooser.addChoosableFileFilter(filter);

				// ... Setting default directory ...//
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sWorkspace));
				} else {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sPreviousLocation)); 
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// ... Check if file exists ...//
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtMzML, new File(fileLocation), true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at " + fileLocation, "Information",	JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportMzMLExcelMouseClicked

	private void jbExportPepMZQExcelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jbExportPepMZQExcelMouseClicked
		if (jtPeptideQuant.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// Applying file extension filters
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
				// Filters must be in descending order
				chooser.addChoosableFileFilter(filter);

				// Setting default directory
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sWorkspace));
				} else {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sPreviousLocation)); 
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					//Check if file exists
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtPeptideQuant, new File(fileLocation),
								true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportPepMZQExcelMouseClicked

	private void jbExportProtMZQExcelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jbExportProtMZQExcelMouseClicked
		if (jtProteinQuant.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// Applying file extension filters
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order
				chooser.addChoosableFileFilter(filter);

				// Setting default directory
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sWorkspace));
				} else {
					// If not found it goes to Home, exception not needed
					chooser.setCurrentDirectory(new File(sPreviousLocation));
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// Check if file exists
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtProteinQuant, new File(fileLocation),
								true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportProtMZQExcelMouseClicked

	private void jbExportFeatMZQExcelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jbExportFeatMZQExcelMouseClicked
		if (jtFeatureQuant.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// ... Applying file extension filters ...//
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order ...//
				chooser.addChoosableFileFilter(filter);

				// ... Setting default directory ...//
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					chooser.setCurrentDirectory(new File(sWorkspace)); // ...
																				// If
																				// not
																				// found
																				// it
																				// goes
																				// to
																				// Home,
																				// exception
																				// not
																				// needed
																				// ...//
				} else {
					chooser.setCurrentDirectory(new File(
							sPreviousLocation)); // ... If not found it goes to
													// Home, exception not
													// needed ...//
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// ... Check if file exists ...//
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtFeatureQuant, new File(fileLocation),
								true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportFeatMZQExcelMouseClicked

	private void jbExportMGFExcelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jbExportMGFExcelMouseClicked
		if (jtMGF.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// ... Applying file extension filters ...//
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order ...//
				chooser.addChoosableFileFilter(filter);

				// ... Setting default directory ...//
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					chooser.setCurrentDirectory(new java.io.File(sWorkspace)); // ...
																				// If
																				// not
																				// found
																				// it
																				// goes
																				// to
																				// Home,
																				// exception
																				// not
																				// needed
																				// ...//
				} else {
					chooser.setCurrentDirectory(new java.io.File(
							sPreviousLocation)); // ... If not found it goes to
													// Home, exception not
													// needed ...//
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// ... Check if file exists ...//
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtMGF, new File(fileLocation), true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportMGFExcelMouseClicked

	private void jbExportMzIdentMLExcelMouseClicked(
			MouseEvent evt) {// GEN-FIRST:event_jbExportMzIdentMLExcelMouseClicked
		if (jtMzId.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// ... Applying file extension filters ...//
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order ...//
				chooser.addChoosableFileFilter(filter);

				// ... Setting default directory ...//
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					chooser.setCurrentDirectory(new java.io.File(sWorkspace)); // ...
																				// If
																				// not
																				// found
																				// it
																				// goes
																				// to
																				// Home,
																				// exception
																				// not
																				// needed
																				// ...//
				} else {
					chooser.setCurrentDirectory(new java.io.File(
							sPreviousLocation)); // ... If not found it goes to
													// Home, exception not
													// needed ...//
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// ... Check if file exists ...//
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtMzId, new File(fileLocation), true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportMzIdentMLExcelMouseClicked

	private void jmRunIdentAnalysisActionPerformed(ActionEvent evt, final JTable jtRawFiles) {

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
				if (dialogIdentParamsExecute == null) {
					dialogIdentParamsExecute = new JDialog(this,
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
					dialogIdentParamsExecute
							.addWindowListener(new WindowAdapter() {
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
				}
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
								for (Entry<String, String> entry : map.entrySet()) {
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
								//MSGFPlus run = new MSGFPlus();
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
										jtaLog.append("\n" + SYS_UTILS.getTime()
												+ " - Performing FDR");
										mzlib.init(args);
										System.out.println(SYS_UTILS.getTime()
												+ " - Setting Threshold");
										jtaLog.append("\n" + SYS_UTILS.getTime()
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
										jtaLog.append("\n" + SYS_UTILS.getTime()
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
								Unmarshaller.unmarshalMzIDFile(model, xmlFile, "", aMzIDUnmarshaller);
								loadMzIdentMLView(0, xmlFile.getName());
								renderIdentFiles(jtRawFiles, jtIdentFiles);
								updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
							}
							progressBarDialog.setVisible(false);
							progressBarDialog.dispose();
						}
					}.start();
				}
			}
		}
	}// GEN-LAST:event_jmRunIdentAnalysisActionPerformed

	private void jbExportMascotXMLExcelMouseClicked(
			MouseEvent evt) {// GEN-FIRST:event_jbExportMascotXMLExcelMouseClicked
		if (jtMascotXMLView.getRowCount() > 0) {
			try {
				JFileChooser chooser = new JFileChooser(sPreviousLocation);
				chooser.setDialogTitle("Please type the file name");

				// ... Applying file extension filters ...//
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Excel Files (*.xls)", "xls");
				// ... Filters must be in descending order ...//
				chooser.addChoosableFileFilter(filter);

				// ... Setting default directory ...//
				if (sPreviousLocation == null || sPreviousLocation.contains("")) {
					chooser.setCurrentDirectory(new java.io.File(sWorkspace)); // ...
																				// If
																				// not
																				// found
																				// it
																				// goes
																				// to
																				// Home,
																				// exception
																				// not
																				// needed
																				// ...//
				} else {
					chooser.setCurrentDirectory(new java.io.File(
							sPreviousLocation)); // ... If not found it goes to
													// Home, exception not
													// needed ...//
				}

				int returnVal = chooser.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					int mid = 0;
					mid = file.getName().lastIndexOf(".xls");
					String fileLocation = "";
					if (mid <= 0) {
						fileLocation = file.getPath() + ".xls";
					} else {
						fileLocation = file.getPath();
					}
					fileLocation.replace("\\", "/");

					// ... Check if file exists ...//
					boolean exists = (new File(fileLocation).exists());
					boolean write = true;
					if (exists) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file already exists in this directory, do you want to overwrite it?",
										"Information",
										JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.NO_OPTION) {
							write = false;
						}
					}
					if (write) {
						ExcelExporter exp = new ExcelExporter();
						exp.fillData(jtMascotXMLView, new File(fileLocation),
								true, 0);
						JOptionPane.showMessageDialog(this, "Data saved at "
								+ fileLocation, "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbExportMascotXMLExcelMouseClicked

	private int getMzMLIndex(String sFileName) {
		int iIndex = 0;
		for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
			if (jtIdentFiles.getValueAt(iI, 0).toString().equals(sFileName)) {
				iIndex = iI;
				break;
			}
		}
		return iIndex;
	}

	/**
	 * Mapping identification files with the corresponding raw file 
	 * @param void
	 * @return void
	 */
	private void renderIdentFiles(JTable jtRawFiles, JTable jtIdentFiles) {
		// ... Rendering ...//
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

	/*------------------------------------------------------------------
	 * Check if all identification files are associated to one raw file
	 * @param void
	 * @return true/false
	 -------------------------------------------------------------------*/
	private boolean checkMapping(JTabbedPane jtpLog) {
		// ... Check if all identification files have been assigned to one raw
		// file ...//
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
	 * @param void
	 * @return void
	 */
	private void updateSaveProjectStatus(JMenuItem jmSaveProject) {
		jmSaveProject.setEnabled(bProjectModified);
		jbSaveProject.setEnabled(bProjectModified);
	}

	/**
	 * Update close project icon
	 * @param void
	 * @return void
	 */
	private void updateCloseProjectStatus(JMenuItem jmCloseProject) {
		if (sProjectName.equals("New")) {
			jmCloseProject.setEnabled(false);
		} else {
			jmCloseProject.setEnabled(true);
		}
	}

	/**
	 * Save a project using a mzq file
	 * @param void
	 * @return true/false
	 */
	private boolean saveProject(JTable jtRawFiles) {
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
			chooser.setCurrentDirectory(new File(sWorkspace)); // ... If
																		// not
																		// found
																		// it
																		// goes
																		// to
																		// Home,
																		// exception
																		// not
																		// needed
																		// ...//
		} else {
			chooser.setCurrentDirectory(new File(sPreviousLocation)); // ...
																				// If
																				// not
																				// found
																				// it
																				// goes
																				// to
																				// Home,
																				// exception
																				// not
																				// needed
																				// ...//
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
			boolean bOK = writeMzQuantML(jcbTechnique.getSelectedItem().toString(), file2.getName(), jtRawFiles);
			if (bOK) {
				sProjectName = file2.getName();
				updateTitle();
				bProjectModified = false;
				updateSaveProjectStatus(jmSaveProject);
				updateCloseProjectStatus(jmCloseProject);
			}
		}

		return true;
	}

	/**
	 * Update window title main project
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
	 * @param void  
	 * @return void
	 */
	private void updateStatusPipeline(JLabel jlRawFilesStatus, JTable jtRawFiles) {
		Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));

		if (jtRawFiles.getRowCount() > 0)
			jlRawFilesStatus.setIcon(loadRawFilesIcon);
		
		if (jtIdentFiles.getRowCount() > 0)
			jlIdentFilesStatus.setIcon(loadRawFilesIcon);
	}

	/**
	 * Checks if the working space is valid
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

	/*--------------------------------------------
	 * Generate template for Label Free Analysis
	 * @param scanIndex1 - Scan number e.g. 1671
	 * @param scanIndex2 - Scan number e.g. 1672
	 * @param mzStarts - m/z starts from
	 * @param mzEnds - m/z ends at
	 * @param resolution - peaks left-right
	 * @return - void
	 ---------------------------------------------*/
	private void generateTemplate(int scanIndex1, int scanIndex2,
			double mzStarts, double mzEnds, int peaks, JTabbedPane jtpLog) {
		int iFileIndex = 0; // ... Index to mzML raw data ...//
		DefaultTableModel model = new DefaultTableModel();
		DefaultTableModel model2 = new DefaultTableModel();
		jtTemplate1.setModel(model);
		jtTemplate2.setModel(model2);
		model.addColumn("m/z Index");
		model.addColumn("Scan Index");
		model.addColumn("Quant");
		model.addColumn("Template2 Index");
		model2.addColumn("Template Index");
		model2.addColumn("x");
		model2.addColumn("y");
		model2.addColumn("i");

		System.out.println("Starting label free plugin...");

		// ... 1) Select MS/MS identifications that are specified in the
		// parameter window (e.g. From scan 1671 to 1672) ...//
		Map<String, List<String>> hmPeptides = new HashMap<String, List<String>>();
		int iCountPeptides = 0; // ... Peptide counter ...//
		boolean blnExists = false;
		for (int iI = 0; iI < jtMascotXMLView.getRowCount(); iI++) {
			// ... Range based on scan index window ...//
			if ((scanIndex1 <= Integer.parseInt(jtMascotXMLView.getValueAt(iI,
					8).toString()))
					&& (scanIndex2 >= Integer.parseInt(jtMascotXMLView
							.getValueAt(iI, 8).toString()))) {
				List<String> al = new ArrayList<String>();
				al.add(Integer.toString(iI)); // ... Index in the grid ...//
				al.add(jtMascotXMLView.getValueAt(iI, 3).toString()); // ... 2)
																		// Peptide
																		// molecular
																		// composition
																		// which
																		// was
																		// calculated
																		// previously
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 8).toString()); // ...
																		// Scan
																		// index
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 9).toString()); // ...
																		// Scan
																		// ID
																		// (jmzml
																		// API
																		// only
																		// supports
																		// getElementBy(scanID)
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 10).toString()); // ...
																		// Retention
																		// time
																		// ...//
				// ... Check if peptide has been added previously ...//
				blnExists = hmPeptides.containsKey(jtMascotXMLView.getValueAt(
						iI, 2).toString());
				if (blnExists == false) {
					hmPeptides.put(
							jtMascotXMLView.getValueAt(iI, 2).toString(), al); // ...
																				// This
																				// eliminates
																				// peptides
																				// which
																				// have
																				// been
																				// identified
																				// more
																				// than
																				// once
																				// (e.g.
																				// for
																				// different
																				// proteins)
																				// ...//
					iCountPeptides++; // ... Total peptides (identifications) in
										// the scan range ...//
				}
			}
		}
		if (iCountPeptides > 0) {
			System.out.println("Peptides in range = " + iCountPeptides);

			// ... Determine the length (resolution) of the resampling array.
			// ...//
			Map<String, float[][]> hmResamplingArray = new HashMap<String, float[][]>();
			int mzWindow = (int) (mzEnds - mzStarts);
			System.out.println("mzWindow=" + mzWindow);
			float resamplingFactor = 1.0f / 120.0f; // ... e.g. 0.008333334
													// Daltons ...//
			System.out.println("Resampling factor=" + resamplingFactor);
			float lengthArray = NumericalUtils.Round(mzWindow / resamplingFactor, 1);
			System.out.println("length=" + lengthArray);
			final int MZ_SIZE = (int) lengthArray;
			System.out.println("SIZE=" + MZ_SIZE);
			float resolution = (float) NumericalUtils.Truncate(
					(NumericalUtils.Round((float) (resamplingFactor), 8)), 8);
			System.out.println("resolution=" + resolution);
			float[][] resamplingArray = new float[2][MZ_SIZE];
			for (int iI = 0; iI < MZ_SIZE; iI++) {
				resamplingArray[0][iI] = (float) mzStarts + (resolution * iI);
				resamplingArray[1][iI] = 0;
			}
			Number[] mzSyntValues = new Number[MZ_SIZE];
			for (int iX = 0; iX < resamplingArray[0].length; iX++) {
				mzSyntValues[iX] = resamplingArray[0][iX];
			}

			final int PEAKS = peaks; // ... Number of peaks to retrieve (width
										// of the template, e.g. 4 left, 4
										// right) ...//
			final int NUMBER_PEAKS = PEAKS * 2 + 1; // ... Number of peaks for
													// each isotope ...//
			int[] aPeakIndexes = new int[PEAKS * 2 + 1];

			System.out.println("PEAKS_PARAM=" + PEAKS);
			System.out.println("TOTAL_PEAKS=" + NUMBER_PEAKS);

			// .. Define templates (A and C) ...//
			int iTempAIndex = 0; // ... Index for Template A ...//
			int iQuantTemplateIndex = 0; // ... Index for Template C ...//

			psTemplate[] templates = new psTemplate[iCountPeptides];
			psTemplateQuant[] quantTemplate = new psTemplateQuant[iCountPeptides
					* NUMBER_PEAKS];
			// psTemplateQuant2[] quantTemplate2 = new
			// psTemplateQuant2[iCountPeptides*NUMBER_PEAKS]; //... This will be
			// the final template once the resampling is done ...//

			// ... Initialize arrays via their constructors (TemplateA=templates
			// and TemplateC=templateQuant) ...//
			for (int iI = 0; iI < templates.length; iI++) {
				templates[iI] = new psTemplate();
			}
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				quantTemplate[iI] = new psTemplateQuant();
			}

			// ... 3) For each peptide, calculate the isotopic pattern
			// distribution ...//
			String sScanID = "";
			int iScanIndex = 0;
			for (Map.Entry<String, List<String>> entry : hmPeptides
					.entrySet()) {
				System.out
						.println("Calculating the Isotopic Patter Distribution (IPD) for peptide "
								+ entry.getKey());

				// ... Specify parameters ...//
				List<String> saParams = entry.getValue();
				Object saArray[] = saParams.toArray();
				for (String saParam : saParams)
				{
					// ... Show the parameters in the array
					// list (index, molecular composition,
					// scanNumber, scanID, rt) ...//
					System.out.println("Param = " + saParam + "; ");
				}
				// ... Using IPC to calculate the isotopic pattern distribution
				// ...//
				String[] args = {};
				String sCharge = jtMascotXMLView.getValueAt(
						Integer.parseInt(saArray[0].toString()), 6).toString();
				args = new String[] { "-a", entry.getKey().toString(), "-fc",
						"-z", sCharge, "-f", "1000", "-r", "10000" };
				if (args.length == 0) {
					System.exit(0);
				}
				IPC ipc = new IPC();
				Options options = Options.parseArgs(args);
				options.setPrintOutput(false);
				Results res = ipc.execute(options);
				Object[] objArray = res.getPeaks().toArray();
				float[] aMz = new float[objArray.length]; // ... There will be
															// six isotopes
															// estimated using
															// the IPC class
															// ...//
				float[] aRelIntens = new float[objArray.length];
				System.out.println("==== IPD ====");
				for (int iI = 0; iI < objArray.length; iI++) {
					aMz[iI] = Float.parseFloat(String.format("%.4f", res
							.getPeaks().first().getMass()));
					aRelIntens[iI] = Float.parseFloat(String.format("%.4f", res
							.getPeaks().first().getRelInt() * 100));
					System.out.println(aMz[iI] + "\t" + aRelIntens[iI]);
					res.getPeaks().pollFirst();
				}

				// ... 4) Generate templates for seaMass by getting the m/z
				// indexes for each isotope ...//
				MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller
						.get(iFileIndex);
				try {
					// ... 4.1) Get precursor ion (from 1671 would be 1666 on
					// the CTPAC example) ...//
					sScanID = saArray[3].toString();
					iScanIndex = Integer.parseInt(saArray[2].toString());
					Spectrum spectrum = unmarshaller.getSpectrumById(sScanID);
					PrecursorList plist = spectrum.getPrecursorList(); // ...
																		// Get
																		// precursor
																		// ion
																		// ...//
					if (plist != null) {
						if (plist.getCount().intValue() == 1) {

							// ... Perform a binary search to find the m/z index
							// closed to the m/z values estimated from the IPC
							// ...//
							int iPos = -1, iPosPrev = -1;
							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[0], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);

							// ... Generate psTemplate (index, {x, y, i}) ...//
							templates[iTempAIndex].setIndex(iTempAIndex);
							templates[iTempAIndex].setCoords(0, aRelIntens[0],
									0); // ... aRelIntens[0] contains the
										// highest value i.e. 100% ...//

							// ... Generate psTemplateQuant (mzIndex, scanIndex,
							// quant, temp2Index) ...//
							for (int iI = 0; iI < NUMBER_PEAKS; iI++) {
								quantTemplate[iI + iQuantTemplateIndex]
										.setTemplate(aPeakIndexes[iI], // ...
																		// mzIndex
																		// ...//
												plist.getPrecursor().get(0)
														.getSpectrumRef()
														.toString(), // ... scan
																		// ID
																		// ...//
												iScanIndex, // ... scanIndex
															// ...//
												1, // ... Initial quant value
													// ...//
												iTempAIndex); // ... Index to
																// template A
																// ...//
							}
							iQuantTemplateIndex += NUMBER_PEAKS; // ... Skip 9
																	// positions
																	// (2*4+1)
																	// ..../
							iPosPrev = iPos;

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[1], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[1], 1);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[2], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[2], 2);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[3], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[3], 3);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[4], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[4], 4);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[5], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[5], 5);
							iTempAIndex++;
						}
					} // ... If precursor ion ...//
					jtpLog.setSelectedIndex(2);
				} catch (MzMLUnmarshallerException e) {
					System.out.println(e);
				}
			} // ... From For ...//

			// ... Perform normalisation ...//
			float fSum = 0.0f;
			float fNewValue = 0.0f;
			for (int iI = 0; iI < templates.length; iI++) {
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					fSum += Math.pow(templates[iI].getCoord(iJ)
							.getRelIntensity(), 2.0);
				}
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					fNewValue = templates[iI].getCoord(iJ).getRelIntensity()
							/ fSum;
					templates[iI].setCoords(templates[iI].getCoord(iJ).getX(),
							templates[iI].getCoord(iJ).getY(), fNewValue, iJ);
				}
			}
			System.out.println("Normalisation finished!");

			// ... Populate the Grids ...//
			System.out.println("Populating grids...");
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				model.insertRow(model.getRowCount(),
						new Object[] { quantTemplate[iI].getMzIndex(),
								quantTemplate[iI].getScanID(),
								quantTemplate[iI].getQuantIntensities(),
								quantTemplate[iI].getTemplate2Index() });
			}
			for (int iI = 0; iI < templates.length; iI++) {
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					model2.insertRow(model2.getRowCount(), new Object[] {
							templates[iI].getIndex(),
							templates[iI].getCoord(iJ).getX(),
							templates[iI].getCoord(iJ).getY(),
							templates[iI].getCoord(iJ).getRelIntensity() });
				}
			}

			// ... Generate the synthetic array which will be used by seaMass to
			// calculate the quantitation values ...//
			Map<String, float[][]> hmSyntheticArray = new HashMap<String, float[][]>();
			MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
			String sPrev = "", sNext = "";

			// ... Parse MS1 data ...//
			System.out.println("Parsing MS1 data ...");
			List<String[]> array = getMS1DataRefs(unmarshaller);
			String scanID = "";
			String[] triplets = new String[] { "", "", "" };

			System.out.println("Generate resampling array ...");
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				scanID = quantTemplate[iI].getScanID();
				blnExists = hmSyntheticArray.containsKey(quantTemplate[iI]
						.getScanID());
				if (blnExists == false) {
					// ... Get Previous and Next MS1 scans ...//
					sPrev = getPrevScan(array, scanID);
					sNext = getNextScan(array, scanID);
					System.out.println("sPrev=" + sPrev);
					System.out.println("scanID=" + scanID);
					System.out.println("sNext=" + sNext);
					triplets[0] = sPrev;
					triplets[1] = scanID;
					triplets[2] = sNext;

					for (int iTrip = 0; iTrip < triplets.length; iTrip++) {
						// ... Go to the raw data and create an resampling array
						// ...//
						try {
							Spectrum spectrum = unmarshaller
									.getSpectrumById(triplets[iTrip]);
							List<BinaryDataArray> bdal = spectrum
									.getBinaryDataArrayList()
									.getBinaryDataArray();

							Number[] mzNumbers = null;
							Number[] intenNumbers = null;
							// ... Reading mz and intensity values ...//
							for (BinaryDataArray bda : bdal) {
								List<CVParam> cvpList = bda.getCvParam();
								for (CVParam cvp : cvpList) {
									if (cvp.getAccession().equals("MS:1000514")) {
										mzNumbers = bda
												.getBinaryDataAsNumberArray();
									}
									if (cvp.getAccession().equals("MS:1000515")) {
										intenNumbers = bda
												.getBinaryDataAsNumberArray();
									}
								}
							}
							// ... Create the hashmap for the resampling array
							// ...//
							blnExists = hmResamplingArray.containsKey(triplets[iTrip]);
							if (blnExists == false) {
								System.out.println("Performing resampling in scan = " + triplets[iTrip]);
								float[][] resArray = BinaryUtils.binArray(resamplingArray, mzNumbers, intenNumbers, mzWindow, true);
								hmResamplingArray.put(triplets[iTrip], resArray);
								hmSyntheticArray.put(triplets[iTrip],
										resamplingArray); // ... Initalising
															// synthetic array
															// ...//
							}
						} catch (MzMLUnmarshallerException ume) {
							System.out.println(ume.getMessage());
						}
					}
				}
			}

			System.out.println("Creating mapping for scan indexes...");

			// ... Map keys for Scan IDs ...//
			String[] scanMap = new String[hmResamplingArray.size()];
			int iMapIndex = 0;
			for (Entry<String, float[][]> entry : hmResamplingArray
					.entrySet()) {
				scanMap[iMapIndex] = entry.getKey();
				iMapIndex++;
			}
			// ... Sort array ...//
			Arrays.sort(scanMap);

			// ... Populate Arrays ...//
			DefaultTableModel model3 = new DefaultTableModel();
			DefaultTableModel model4 = new DefaultTableModel();
			jTable1.setModel(model3);
			jTable2.setModel(model4);
			model3.addColumn("m/z Index");
			model3.addColumn("m/z Value");
			model4.addColumn("m/z Index");
			model4.addColumn("m/z Value");

			// ... Adding column headers ...//
			for (int iI = 0; iI < scanMap.length; iI++) {
				model3.addColumn(scanMap[iI]);
				model4.addColumn(scanMap[iI]);
			}

			System.out.println("Populating arrays  (inseting rows) ...");
			if (scanMap.length >= 0) {
				blnExists = hmResamplingArray.containsKey(scanMap[0]);
				if (blnExists == true) {
					float[][] temp = hmResamplingArray.get(scanMap[0]);
					for (int iI = 0; iI < temp[0].length; iI++) {
						model3.insertRow(model3.getRowCount(), new Object[] {
								iI, temp[0][iI], temp[1][iI] });
						model4.insertRow(model4.getRowCount(), new Object[] {
								iI, temp[0][iI], temp[1][iI] });
					}
				}
			}
			System.out
					.println("Populating arrays  (updating rows from hmSyntheticArray) ...");
			// ... Now it's time to load the rest of the columns ...//
			for (int iJ = 1; iJ < scanMap.length; iJ++) {
				blnExists = hmSyntheticArray.containsKey(scanMap[iJ]);
				if (blnExists == true) {
					float[][] temp2 = hmSyntheticArray.get(scanMap[iJ]);
					for (int iI = 0; iI < temp2[0].length; iI++) {
						model3.setValueAt(temp2[1][iI], iI, iJ + 2);
					}
				}
			}
			System.out
					.println("Populating arrays  (updating rows from hmResamplingArray) ...");
			// ... Now it's time to load the rest of the columns ...//
			for (int iJ = 1; iJ < scanMap.length; iJ++) {
				System.out.println("updating rows for hmsResamplingArray "
						+ scanMap[iJ]);
				blnExists = hmResamplingArray.containsKey(scanMap[iJ]);
				if (blnExists == true) {
					float[][] temp3 = hmResamplingArray.get(scanMap[iJ]);
					System.out.println("In_memory2=" + temp3[0][16] + "\tmz="
							+ temp3[1][16]);
					for (int iI = 0; iI < temp3[0].length; iI++) {
						model4.setValueAt(temp3[1][iI], iI, iJ + 2);
					}
				}
			}
			// //... Update synthetic array and quant templates ...//
			// System.out.println("Updating synthetic array and quant templates...");
			// int iTemp1Index = 0, iMzIndexResamplArray = 0;
			// float quantValue = 0.0f;
			// int iX=0, iY=0;
			// float fI=0.0f;
			// for(int iI=0; iI<quantTemplate.length; iI++){
			// scanID = quantTemplate[iI].getScanID();
			// blnExists =
			// hmSyntheticArray.containsKey(quantTemplate[iI].getScanID());
			// if (blnExists){
			// System.out.println("Updating scanID="+scanID);
			// //... Get previous and next scans (-1, 0 and +1 positions in rt)
			// ...//
			// sPrev = getPrevScan(array, scanID);
			// sNext = getNextScan(array, scanID);
			//
			// triplets[0] = sPrev;
			// triplets[1] = scanID;
			// triplets[2] = sNext;
			//
			// iTemp1Index = quantTemplate[iI].getTemplate2Index();
			// iMzIndexResamplArray = quantTemplate[iI].getMzIndex(); //... This
			// will be used to calculate -1, 0, +1 in m/z ...//
			// quantValue = quantTemplate[iI].getQuantIntensities();
			//
			// for(int iZ=0; iZ<templates[iTemp1Index].getCoords().length;
			// iZ++){ //... 54 positions (9 points x 6 isotopes) ...//
			// //... Update values using template A ...//
			// iX = templates[iI].getCoord(iZ).getX();
			// iY = templates[iI].getCoord(iZ).getY();
			// fI = templates[iI].getCoord(iZ).getRelIntensity();
			//
			// //... Get synthetic array for each scan ...//
			// float[][] temp = hmSyntheticArray.get(triplets[iY+1]);
			// temp[1][iTemp1Index+iX] = temp[1][iTemp1Index+iX] + (quantValue *
			// fI);
			// hmSyntheticArray.put(triplets[iY+1], temp);
			// }
			// }
			// }

		} // ... From if countPeptides ...//
	}

	/*----------------------------------------------------------
	 * Get spectrum IDs and spectrum References across MS1 data
	 * @param unmarshall - Raw file
	 * @return - array of strings containing the spectrum IDs
	 -----------------------------------------------------------*/
	private List<String[]> getMS1DataRefs(MzMLUnmarshaller unmarshaller) {
		List<String[]> array = new ArrayList<String[]>();
		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);
		while (spectrumIterator.hasNext()) {
			Spectrum spectrum = spectrumIterator.next();

			// ... Identify MS1 data ...//
			String mslevel = "";
			List<CVParam> specParam = spectrum.getCvParam();
			for(CVParam lCVParam : spectrum.getCvParam())
			{
				if (lCVParam.getAccession().equals("MS:1000511")) {
					mslevel = lCVParam.getValue().trim();
				}
			}
			if (mslevel.equals("1")) {
				array.add(new String[] { spectrum.getIndex().toString(),
						spectrum.getId() });
			}
		}
		// ... Sort by spectrum Index ...//
		Collections.sort(array, new Comparator<String[]>() {
			public int compare(String[] strings1, String[] strings2) {
				strings1[0] = String.format("%08d",
						Integer.parseInt(strings1[0]));
				strings2[0] = String.format("%08d",
						Integer.parseInt(strings2[0]));
				return strings1[0].compareTo(strings2[0]);
			}
		});
		return array;
	}

	/*----------------------------------------
	 * Get previous MS1 scan
	 * @param array - Array with MS1 scan ids
	 * @param sScanID - Scan ID reference 
	 -----------------------------------------*/
	private String getPrevScan(List<String[]> array, String sScanID) {
		String sPrev = "";
		//int iJ = 0;
		for (String[] value : array) {
			if (value[1].equals(sScanID)) {
				break;
			} else {
				sPrev = value[1];
			}
			//iJ++;
		}
		return sPrev;
	}

	/*----------------------------------------
	 * Get next MS1 scan
	 * @param array - Array with MS1 scan ids
	 * @param sScanID - Scan ID reference 
	 -----------------------------------------*/
	private String getNextScan(List<String[]> array, String sScanID) {
		String sNext = "";
		int iJ = 0;
		for (String[] value : array) {
			if (value[1].equals(sScanID)) {
				if (iJ < array.size()) {
					sNext = array.get(iJ + 1)[1];
					break;
				} else {
					sNext = value[1];
				}
			}
			iJ++;
		}
		return sNext;
	}

	/**
	 * Get neighbour peaks from a given mz value 
	 * @param array - Array with MS data
	 * @param iIndex - location in the array
	 * @param iOffset - Number of peaks to cover
	 * @return array of integers with peak indexes 
	 */
	private int[] getPeaks(Number[] nArray, int iIndex, int iOffset) {
		int[] aPeaks = new int[iOffset * 2 + 1];
		int iCount = 0;
		if ((iIndex - iOffset > 0) && (iIndex + iOffset < nArray.length)) {
			for (int iI = iIndex - iOffset; iI <= iIndex + iOffset; iI++) {
				aPeaks[iCount] = iI;
				iCount++;
				System.out.println("Index=" + iI + "\t"
						+ nArray[iI].floatValue());
			}
		}
		return aPeaks;
	}

	/**
	 * Display MS spectrum 
	 * @param iIndex - Index to the aMzMLUnmarshaller list
	 * @param sID - spectrum ID
	 * @return void
	 */
	private void showSpectrum(int iIndex, String sID) {
		// ... Get index from spectra ...//
		MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);
		try {
			Spectrum spectrum = unmarshaller.getSpectrumById(sID);

			// ... Reading CvParam to identify the MS level (1, 2) ...//
			String mslevel = "";
			for (CVParam lCVParam : spectrum.getCvParam())
			{
				if (lCVParam.getAccession().equals("MS:1000511")) {
					mslevel = lCVParam.getValue().trim();
				}
			}
			float parIonMz = 0;
			int parCharge = 0;
			if (mslevel.toString().indexOf("2") >= 0) {
				PrecursorList plist = spectrum.getPrecursorList(); // ... Get
																	// precursor
																	// ion ...//
				if (plist != null) {
					if (plist.getCount().intValue() == 1) {
						// ... Detect parent ion m/z and charge ...//
						
						for (CVParam lCVParam : plist.getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam())
						{
							if (lCVParam.getAccession().equals("MS:1000744")) {
								parIonMz = Float.parseFloat(lCVParam.getValue()	.trim());
							}
							if (lCVParam.getAccession().equals("MS:1000041")) {
								parCharge = Integer.parseInt(lCVParam.getValue().trim());
							}
						}
					}
				}
			}

			Number[] mzNumbers = null;
			Number[] intenNumbers = null;

			boolean bCompressed = false;
			// Reading mz Values
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
							mzNumbers = NumericalUtils.decodeMzDeltas(mzNumbers);
						}
					}
					if (cvp.getAccession().equals("MS:1000515")) {
						intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
			}

			double[] mz = new double[mzNumbers.length];
			for (int iI = 0; iI < mzNumbers.length; iI++) {
				mz[iI] = mzNumbers[iI].doubleValue();
			}
			double[] intensities = new double[intenNumbers.length];
			for (int iI = 0; iI < intenNumbers.length; iI++) {
				intensities[iI] = intenNumbers[iI].doubleValue();
			}
			// ... Call the spectrum panel from compomics.org ...//
			jdpMS.removeAll();
			JInternalFrame jifViewSpectrum = getInternalFrame();

			JPanel specpanel = new SpectrumPanel(mz, intensities, parIonMz,
					Integer.toString(parCharge), sID, 50, false, true, true,
					true, Integer.parseInt(mslevel));
			jifViewSpectrum.setTitle("MS spectrum <ScanID: " + sID + ">");
			specpanel.setSize(new Dimension(600, 400));
			specpanel.setPreferredSize(new Dimension(600, 400));
			jifViewSpectrum.add(specpanel, BorderLayout.CENTER);
			jifViewSpectrum.setSize(jdpMS.getSize());
			jdpMS.add(jifViewSpectrum, BorderLayout.CENTER);
			jdpMS.revalidate();
			jdpMS.repaint();
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
	}

	/*-------------------------------------------------------
	 * Show raw data from a Mascot Generic Format (MGF) file
	 * @param iIndex - Index to the raw file
	 * @param sID - Scan title
	 * @return void
	 --------------------------------------------------------*/
	private void showRawDataMGF(int iIndex, String sID, JTable jtRawData, JTable jtRawFiles, JTabbedPane jtpLog) {

		DefaultTableModel model = new DefaultTableModel();
		jtRawData.setModel(model);
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");

		// ... Reading file ...//
		try {
			BufferedReader in = new BufferedReader(new FileReader(jtRawFiles.getValueAt(iIndex, 1).toString()));
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

	/*---------------------------------------------------
	 * Displays raw data
	 * @iIndex - Index to the aMzMLUnmarshaller arraylist 
	 * @sID - scan ID
	 * @return void
	 ---------------------------------------------------*/
	private void showRawData(int iIndex, String sID, final JTable jtRawData, JTabbedPane jtpLog) {
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
							mzNumbers = NumericalUtils.decodeMzDeltas(mzNumbers);
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

	/*---------------------------------------
	 * Get internal frame dimensions
	 * @param void 
	 * @return JInternalFrame as a container
	 ----------------------------------------*/
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

	/*
	 * --------------------------------------------------------- Display total
	 * ion chromatogram
	 * 
	 * @ iIndex - Index to the aMzMLUnmarshaller arraylist ...//
	 * 
	 * @ sTitle - Window title
	 * 
	 * @return void ---------------------------------------------------------
	 */
	private void showChromatogram(int iIndex, String sTitle) {
		// ... Get index from spectra ...//
		MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);
		try {
			// ... Check if mzML contains MS1 data ...//
			Set<String> chromats = unmarshaller.getChromatogramIDs();
			if (!chromats.isEmpty()) {
				Chromatogram chromatogram = unmarshaller
						.getChromatogramById("TIC");
				Number[] rtNumbers = null;
				Number[] intenNumbers = null;
				List<BinaryDataArray> bdal = chromatogram
						.getBinaryDataArrayList().getBinaryDataArray();

				for (BinaryDataArray bda : bdal) {
					List<CVParam> cvpList = bda.getCvParam();
					for (CVParam cvp : cvpList) {
						if (cvp.getAccession().equals("MS:1000595")) {
							rtNumbers = bda.getBinaryDataAsNumberArray();
						}
						if (cvp.getAccession().equals("MS:1000515")) {
							intenNumbers = bda.getBinaryDataAsNumberArray();
						}
					}
				}
				// ... Converting numbers to doubles ...//
				double[] rt = new double[rtNumbers.length];
				for (int iI = 0; iI < rtNumbers.length; iI++) {
					rt[iI] = rtNumbers[iI].doubleValue();
				}
				double[] intensities = new double[intenNumbers.length];
				for (int iI = 0; iI < intenNumbers.length; iI++) {
					intensities[iI] = intenNumbers[iI].doubleValue();
				}
				// ... Clear container ...//
				jdpTIC.removeAll();
				JInternalFrame jifViewChromatogram = getInternalFrame();
				// ... Class chromatogram from compomics.org ...//
				ChromatogramPanel panel = new ChromatogramPanel(rt,
						intensities, "RT (mins)", "Intensity (counts)");
				jifViewChromatogram.setTitle("Chromatogram <" + sTitle + ">");
				panel.setSize(new Dimension(600, 400));
				panel.setPreferredSize(new Dimension(600, 400));
				jifViewChromatogram.add(panel);
				jdpTIC.add(jifViewChromatogram);
				jdpTIC.revalidate();
				jdpTIC.repaint();
			} else {
				System.out.println(SYS_UTILS.getTime()
						+ " - This mzML file doesn't contain MS2 raw data.");
			}
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
	}

	/**
	 * Displays the MS1 raw data as 2D plots
	 * @param iIndex - Index to the aMzMLUnmarshaller list
	 * @return void
	 */
	private void show2DPlot(int iIndex, String sTitle, JPanel jp2D) {

		// CheckMemory chm = new
		// CheckMemory("Before allocating memory for 2D plot");
		// ... Display about 27 x 3 = 51 MB maximum for now ...//
		float[] mz = new float[200000];
		float[] intensities = new float[200000];
		float[] art = new float[200000];
		// CheckMemory chm2 = new
		// CheckMemory("After allocating memory for 2D plot");

		float rt = 0;
		int iCounter = 0;

		// ... Get index from spectra ...//
		MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);

		// ... Check if mzML contains MS1 data ...//
		Set<String> chromats = unmarshaller.getChromatogramIDs();
		if (!chromats.isEmpty()) {
			MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
					.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
							Spectrum.class);
			int iK = 0;
			String unitRT = "";
			// ... Display first 100 scans for now ...//
			while ((spectrumIterator.hasNext()) && (iK < 100)) {
				Spectrum spectrumobj = spectrumIterator.next();
				String spectrumid = spectrumobj.getId();

				// ... Identify MS1 data ...//
				String mslevel = "";
				for (CVParam lCVParam : spectrumobj.getCvParam())
				{
					if (lCVParam.getAccession().equals("MS:1000511")) {
						mslevel = lCVParam.getValue().trim();
					}
				}
				if (mslevel.equals("1")) {
					try {
						Spectrum spectrum = unmarshaller.getSpectrumById(spectrumid);
						for (CVParam lCVParam : spectrum.getScanList().getScan().get(0).getCvParam())
						{
							if (lCVParam.getAccession().equals("MS:1000016")) { // ...
																				// Get
																				// RT
																				// ...//
								rt = Float.parseFloat(lCVParam.getValue().trim());
								unitRT = lCVParam.getUnitAccession().trim();
								if (unitRT.equals("UO:0000031")) { // ...
																	// Convert
																	// RT into
																	// seconds
																	// ...//
									rt = Float.parseFloat(lCVParam.getValue()
											.trim());
									rt *= 60;
								} else {
									rt = Float.parseFloat(lCVParam.getValue()
											.trim());
								}
							}
						}
						Number[] mzNumbers = null;
						Number[] intenNumbers = null;
						// ... Reading mz Values ...//
						List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
						for (BinaryDataArray bda : bdal) {
							List<CVParam> cvpList = bda.getCvParam();
							for (CVParam cvp : cvpList) {
								if (cvp.getAccession().equals("MS:1000514")) {
									mzNumbers = bda
											.getBinaryDataAsNumberArray();
								}
								if (cvp.getAccession().equals("MS:1000515")) {
									intenNumbers = bda
											.getBinaryDataAsNumberArray();
								}
							}
						}
						int iI = 0;
						while (iI < mzNumbers.length) {
							if (intenNumbers[iI].doubleValue() > 0) { // ...
																		// Removing
																		// zero
																		// values
																		// ...//
								if (iCounter < 200000) {
									mz[iCounter] = mzNumbers[iI].floatValue();
									intensities[iCounter] = intenNumbers[iI]
											.floatValue();
									art[iCounter] = rt;
									iCounter++;
								}
							}
							iI += 10;
						}
					} catch (MzMLUnmarshallerException ume) {
						System.out.println(ume.getMessage());
					}
					iK++;
				}
			}
			System.out.println(SYS_UTILS.getTime() + " - 2D view holding "
					+ iCounter + " elements.");
			TwoDPlot jifView2D = new TwoDPlot(sTitle, mz, intensities, art);
			jp2D.add(jifView2D);
			jifView2D.pack();
			jifView2D.setVisible(true);
		} else {
			System.out.println(SYS_UTILS.getTime()
					+ " - This mzML file doesn't contain MS2 raw data.");
		}
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

	/*----------------------------------------------------------
	 * Load MzIdentML viewer
	 * @param iIndex - Index to the aMzIDUnmarshaller arraylist 
	 * @param sFileName - File name
	 * @return void
	 -----------------------------------------------------------*/
	private void loadMzIdentMLView(final int iIndex, final String sFileName) {
		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadMzIDView");
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
		
		FileFormatMzIdentML mzIdentML = new FileFormatMzIdentML(jtpProperties, jlFileNameMzIDText, sFileName, 
			aMzIDUnmarshaller, iIndex, SYS_UTILS, jtMzId, jcbPSM, jtMzIDProtGroup, jepMzIDView, progressBarDialog);
		mzIdentML.start();
	}

	/*--------------------------------------------------------
	 * Display MZQ data
	 * @param iIndex - Index to the aMzQUnmarshaller arraylist 
	 * @param sFile - File name
	 * @return void
	 ---------------------------------------------------------*/
	private void loadMzQuantMLView(int iIndex, String sFile) {
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
				FileFormatMzQuantML mzQuantML = new FileFormatMzQuantML(jtpProperties, iIndex, SYS_UTILS, progressBarDialog, 
						jtProteinQuant, jtPeptideQuant, jtFeatureQuant, sFile, aMzQUnmarshaller, 
						jlFileNameMzQText, jepMZQView, jtaLog, jtpMzQuantMLDetail);
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
	 * @param iIndex - index for the aMzMLUnmarshaller list
	 * @return void
	 */
	private void loadMzMLView(int iIndex, JTable jtRawFiles) {
		// ... Check if not previously loaded ...//
		if (!jtRawFiles.getValueAt(iIndex, 0).toString()
				.equals(jlFileNameMzMLText.getText())) {
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					this, true, "LoadMZMLView");
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

			FileFormatMzML mzML = new FileFormatMzML(jtMzML, aMzMLUnmarshaller, iIndex, jlFileNameMzMLText,	jepMzMLView, jtpProperties, progressBarDialog);
			mzML.start();
		}
	}

	/**
	 * Displays MGF raw data
	 * @param iIndex - index for the array
	 * @return void
	 */
	private void loadMGFView(int iIndex, JLabel jlFileNameMGFText, JTable jtRawFiles) {
		// ... Check if not previously loaded
		if (!jtRawFiles.getValueAt(iIndex, 0).toString()
				.equals(jlFileNameMGFText.getText())) {
			final String sFileNameRef = jtRawFiles.getValueAt(iIndex, 0)
					.toString();
			final String sFilePathRef = jtRawFiles.getValueAt(iIndex, 1)
					.toString();
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					this, true, "LoadMGF");
			final Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// ... Progress Bar ...//
					progressBarDialog.setTitle("Loading MGF data");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");
			thread.start();
			FileFormatMGF mgf = new FileFormatMGF(jtMGF, jlFileNameMGFText, sFileNameRef, jtpProperties, sFilePathRef,
					progressBarDialog);
			mgf.start();
		}
	}

	/**
	 * Display Mascot XML files
	 * @param sFileName - File name
	 * @param sFilePath - File location
	 * @return void
	 */
	private void loadMascotView(String sFileName, String sFilePath) {
		DefaultTableModel model = new DefaultTableModel() {
			Class<?>[] types = new Class[] { Integer.class, String.class,
					String.class, String.class, Float.class, Float.class,
					Integer.class, Float.class, Integer.class, String.class,
					Float.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
		jtMascotXMLView.setModel(model);
		model.addColumn("Index");
		model.addColumn("Protein");
		model.addColumn("Peptide");
		model.addColumn("Composition");
		model.addColumn("Exp m/z");
		model.addColumn("Exp Mr");
		model.addColumn("Charge");
		model.addColumn("Score");
		model.addColumn("Scan");
		model.addColumn("Scan ID");
		model.addColumn("RT (sec)");

		jtpProperties.setSelectedIndex(3);
		JLabel jlFileNameMascotXMLText = new JLabel(sFileName);

		// ... Open mascot file and extract identifications ...//
		File file = new File(sFilePath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			// Reading hits
			Node nodeLst = doc.getElementsByTagName("hits").item(0);
			NodeList hitsList = nodeLst.getChildNodes();

			// Initializing proteinId, peptide sequence and other variables
			Integer iIndex = 0;
			String proteinId = "";
			String peptideSeq = "";
			String peptideCompos = "";
			float hitScore = 0.0f;
			float retTime = 0.0f;
			String idRef = "";
			int scanNumber = 0;
			float parIonMz = 0.0f;
			float parIonMr = 0.0f;

			String[] modification = new String[6];
			int numMods = 1;
			boolean isVariableMod = false;
			int dbCharge = -1;
			String mascotPos = "";
			int iProteins = 0;
			int iPeptides = 0;
			String sChainToInsert = "";

			for (int iI = 0; iI < hitsList.getLength(); iI++) { // ...
																// Identifying
																// all protein
																// hits from the
																// search ...//
				NodeList subHitsList = hitsList.item(iI).getChildNodes();
				for (int iJ = 0; iJ < subHitsList.getLength(); iJ++) {
					Node proteinItem = subHitsList.item(iJ);
					if (proteinItem.getNodeType() == Node.ELEMENT_NODE) {
						if (proteinItem.getNodeName().equals("protein")) { // ...
																			// Reading
																			// the
																			// protein
																			// ...//
							iProteins++;
							proteinId = proteinItem.getAttributes().item(0)
									.getTextContent().toString();
							NodeList subProteinList = proteinItem
									.getChildNodes(); // ... Protein subnodes
														// ...//
							for (int iK = 0; iK < subProteinList.getLength(); iK++) {
								Node peptideItem = subProteinList.item(iK);
								if (peptideItem.getNodeType() == Node.ELEMENT_NODE) {
									if (peptideItem.getNodeName().equals(
											"peptide")) { // ... Reading
															// peptides ...//
										iPeptides++;
										NodeList peptideDataList = peptideItem
												.getChildNodes(); // ... Peptide
																	// subnodes
																	// ...//
										// ... Initializing variables ...//
										for (int iA = 0; iA < 6; iA++) { // ...
																			// Cleaning
																			// array
																			// ...//
											modification[iA] = null;
										}
										int iContMod = 0;
										isVariableMod = false;
										numMods = -1;
										dbCharge = -1;
										sChainToInsert = "";
										for (int iL = 0; iL < peptideDataList
												.getLength(); iL++) {
											Node peptideElem = peptideDataList
													.item(iL);
											if (peptideElem.getNodeType() == Node.ELEMENT_NODE) {
												if (peptideElem.getNodeName()
														.equals("pep_seq")) {
													peptideSeq = peptideElem
															.getTextContent()
															.toString();
													// ... Calculate the residue
													// composition ...//
													peptideCompos = Homeless.getResidueComposition(peptideSeq);
												} else if (peptideElem
														.getNodeName().equals(
																"pep_exp_mz")) {
													parIonMz = Double
															.valueOf(
																	peptideElem
																			.getTextContent()
																			.toString())
															.floatValue();
												} else if (peptideElem
														.getNodeName().equals(
																"pep_exp_mr")) {
													parIonMr = Double
															.valueOf(
																	peptideElem
																			.getTextContent()
																			.toString())
															.floatValue();
												} else if (peptideElem
														.getNodeName().equals(
																"pep_exp_z")) {
													dbCharge = Integer
															.valueOf(
																	peptideElem
																			.getTextContent()
																			.toString())
															.intValue();
												} else if (peptideElem
														.getNodeName().equals(
																"pep_score")) {
													hitScore = Double
															.valueOf(
																	peptideElem
																			.getTextContent()
																			.toString())
															.floatValue();
												} else if (hitScore >= 20) { // ...
																				// Verify
																				// if
																				// hits
																				// are
																				// over
																				// minimum
																				// threshold
																				// ...//
													if (peptideElem
															.getNodeName()
															.equals("pep_var_mod")) { // ...
																						// Modification
																						// variables
																						// ...//
														String sModVariable = peptideElem
																.getTextContent()
																.toString();

														String[] differentMods = new String[3];
														differentMods = sModVariable
																.split(";"); // ...
																				// Check
																				// if
																				// different
																				// modifications
																				// are
																				// separated
																				// by
																				// colons
																				// ..//
														if (differentMods[0]
																.equals("")) { // ...
																				// Verify
																				// if
																				// has
																				// data
																				// ...//
															isVariableMod = false;
														} else {
															for (int iDiffMod = 0; iDiffMod < differentMods.length; iDiffMod++) {
																// ... This code
																// parses the
																// number of
																// modifications
																// (1, 2, n)
																// once we have
																// identified
																// the different
																// modif
																// variables
																// ...//
																if (differentMods[iDiffMod]
																		.startsWith(" ")) {
																	differentMods[iDiffMod] = differentMods[iDiffMod]
																			.substring(
																					1,
																					differentMods[iDiffMod]
																							.length());
																}
																if (differentMods[iDiffMod]
																		.length() > 0) {
																	String[] multipleMods = new String[3]; // ...
																											// New
																											// array
																											// for
																											// multiple
																											// modifications
																											// in
																											// different
																											// modifications
																											// ...//
																	numMods = 1;
																	if (differentMods[iDiffMod]
																			.matches("^\\d+[\\w|\\D|\\S|\\s|\\W]*")) {
																		multipleMods = differentMods[iDiffMod]
																				.split(" ");
																		numMods = Integer
																				.valueOf(
																						multipleMods[0])
																				.intValue();
																		sChainToInsert = differentMods[iDiffMod]
																				.replace(
																						numMods
																								+ " ",
																						"");
																		numMods = 1;
																	} else {
																		sChainToInsert = differentMods[iDiffMod];
																		numMods = 1;
																	}
																	for (int iFound = 0; iFound < numMods; iFound++) {
																		modification[iContMod] = sChainToInsert;
																		iContMod++;
																	}
																}
															}
															isVariableMod = true;
														}
													} else if (peptideElem
															.getNodeName()
															.equals("pep_var_mod_pos")
															&& isVariableMod) {
														mascotPos = peptideElem
																.getTextContent()
																.toString();
														mascotPos = mascotPos
																.replace(".",
																		"");
													} else if (peptideElem
															.getNodeName()
															.equals("pep_scan_title")) { // ...
																							// Only
																							// in
																							// some
																							// cases
																							// the
																							// rt
																							// is
																							// specified
																							// in
																							// the
																							// scan
																							// title
																							// ...//
														if (peptideElem
																.getTextContent()
																.toString()
																.indexOf("rt:") > 0) { // ...
																						// Option
																						// 1,
																						// reading
																						// on
																						// scan
																						// title
																						// ...//
															String myTmpString = peptideElem
																	.getTextContent()
																	.toString();
															int ind = 0;
															int ind1 = 0;

															// ... Get retention
															// time ...//
															ind = myTmpString
																	.indexOf("(rt:");
															if (ind > 0) {
																ind1 = myTmpString
																		.indexOf(")");
																retTime = Double
																		.valueOf(
																				myTmpString
																						.substring(
																								ind + 4,
																								ind1))
																		.floatValue();
															} else {
																retTime = 0;
															}

															// ... Get ID ref
															// ...//
															ind = myTmpString
																	.indexOf("(id:");
															if (ind > 0) {
																ind1 = myTmpString
																		.lastIndexOf(")");
																idRef = myTmpString
																		.substring(
																				ind + 4,
																				ind1);
															} else {
																idRef = "";
															}

															// Get scan number
															ind = myTmpString
																	.indexOf("Scan:");
															if (ind > -1) {
																ind1 = myTmpString
																		.indexOf(",");
																scanNumber = Double
																		.valueOf(
																				myTmpString
																						.substring(
																								ind + 5,
																								ind1))
																		.intValue();
															} else {
																scanNumber = 0;
															}
														} else {
															// ... Read mzML
															// file or MGF (TO
															// DO) ...//
															retTime = 0;
														}
														model.insertRow(
																model.getRowCount(),
																new Object[] {
																		iIndex,
																		proteinId,
																		peptideSeq,
																		peptideCompos,
																		parIonMz,
																		parIonMr,
																		dbCharge,
																		hitScore,
																		scanNumber,
																		idRef,
																		retTime });
														iIndex++;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			jtMascotXMLView.setAutoCreateRowSorter(true);
		} catch (Exception e) {
			System.out.println("LoadMascotIdent: Exception while reading "
					+ file + "\n" + e);
			System.exit(1);
		}
	}

	/*-------------------------------------------
	 * Initialise project settings (all modules) 
	 * @param void
	 * @return void
	 --------------------------------------------*/
	private void initProjectValues() {
		// ... Loading values from config file ...//
		initSettings(); // ... From config file (workspace) ...//
		initProjectStatusIcons(); // ... Project pipeline ...//

		// ... Initialising components ...//
		initTables();
		initTextAreas();
		jdpMS.removeAll();
		jdpTIC.removeAll();
		initButtons();

		// ISO 3166 country code - GB
		Locale.setDefault(new Locale("en", "GB"));
	}

	/**
	 * Initialise project settings (configuration XML file) 
	 * @param void
	 * @return void
	 */
	private void initSettings() {
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
					JOptionPane.INFORMATION_MESSAGE);
		}
		sProjectName = "New";
		bProjectModified = false;
		updateSaveProjectStatus(jmSaveProject);
		updateCloseProjectStatus(jmCloseProject);
		updateTitle();
	}

	/**
	 * Initialise project status icons
	 * @param void
	 * @return void
	 */
	private void initProjectStatusIcons() {
		// ... Project status pipeline ...//
		Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/empty.gif"));
		jlIdentFilesStatus.setIcon(loadRawFilesIcon);
		jcbTechnique.setSelectedIndex(0);
	}

	/**
	 * Initialise table values 
	 * @param void
	 * @return void
	 */
	private void initTables() {
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
	 * @param void
	 * @return void
	 */
	private void initTextAreas() {
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
	 * @param void
	 * @return void
	 */
	private void initButtons() {
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
	 * @param void
	 * @return void
	 */
	private void openProject(String sPreviousLocation, String sWorkspace, JTable jtRawFiles) {
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
							isOK = Unmarshaller.unmarshalMzQMLFile(model, file, aMzQUnmarshaller);
						} catch (Exception e) {
							jtaLog.append("Error reading mzQuantML - the mzQuantML file may be invalid.\nError: ");
							jtaLog.append(e.getMessage());
							e.printStackTrace();
							isOK = false;
						}
						if (isOK) {
							isOK = loadProjectFromMZQ(0, file, jtRawFiles);
							if (isOK) {
								bProjectModified = false;

								sProjectName = file.getName();
								updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
								updateSaveProjectStatus(jmSaveProject);
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

	/*----------------------------------------------
	 * Load project from mzq 
	 * @param iIndex - Index to the aMzQUnmarshaller arraylist 
	 * @param sFile - File name 
	 * @boolean Success
	 ----------------------------------------------*/
	private boolean loadProjectFromMZQ(int iIndex, File sFile, final JTable jtRawFiles) {
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
		final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this,	true, "LoadProjectFromMZQ");
		
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
				System.out.println(SYS_UTILS.getTime()+ " - (From Project) Loading settings from the mzq file ...");
				String sGroup = "";

				MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(
						new File(jtQuantFiles.getValueAt(0, 1).toString()));
				MzQuantML mzq = unmarshaller.unmarshall();
				System.out.println(SYS_UTILS.getTime() + " - MzQuantML version="	+ mzq.getVersion());
				System.out.println(SYS_UTILS.getTime() + " - ID=" + mzq.getId());
				System.out.println(SYS_UTILS.getTime() + " - Name=" + mzq.getName());

				InputFiles inputFiles = mzq.getInputFiles();

				// ... Load raw files ...//
				List<RawFilesGroup> rawFilesGroup = inputFiles.getRawFilesGroup();
				System.out.println(SYS_UTILS.getTime() + " - RawFilesGroup=" + rawFilesGroup.size());
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
								Unmarshaller.unmarshalMzMLFile(model, file, "", aMzMLUnmarshaller);
								iMzMLRaw++;
							}
						}
					}
				}
				if (iMGFRaw > 0) {
					System.out.println(SYS_UTILS.getTime()
							+ " - Loading MGF view ...");
					if (jtRawFiles.getRowCount() > 0) {
						loadMGFView(0, jlFileNameMGFText, jtRawFiles);
					}
				}
				if (iMzMLRaw > 0) {
					System.out.println(SYS_UTILS.getTime()
							+ " - Loading mzML view ...");
					loadMzMLView(0, jtRawFiles);
					if (jtRawFiles.getRowCount() > 0) {
						System.out.println(SYS_UTILS.getTime()
								+ " - Showing chromatogram ...");
						showChromatogram(0, jtRawFiles.getValueAt(0, 1)
								.toString());
						System.out.println(SYS_UTILS.getTime()
								+ " - Showing 2D plot ...");
						show2DPlot(0, jtRawFiles.getValueAt(0, 1).toString(), jp2D);
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
							Unmarshaller.unmarshalMzIDFile(model2, file, "", aMzIDUnmarshaller);
							iMzID++;
						}
					}
				}
				if (iXMLIdent > 0) {
					if (jtIdentFiles.getRowCount() > 0) {
						loadMascotView(
								jtIdentFiles.getValueAt(0, 0).toString(),
								jtIdentFiles.getValueAt(0, 1).toString());
					}
				}
				if (iMzID > 0) {
					if (jtIdentFiles.getRowCount() > 0) {
						loadMzIdentMLView(0, jtIdentFiles.getValueAt(0, 0)
								.toString());
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
				updateStatusPipeline(jlRawFilesStatus, jtRawFiles);
				progressBarDialog.setVisible(false);
				progressBarDialog.dispose();

				// loadMzQuantMLView(0, sFileRef.getName());
			}
		}.start();
		return true;
	}

	/**
	 * Generate files (Quant file and xtracker files)    
	 * @param void
	 * @return true/false
	 */
	private boolean generateFiles(JTable jtRawFiles) {
		// ... Check project name ...//
		String sFile = sProjectName;
		System.out.println(SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		jtaLog.append("\n" + SYS_UTILS.getTime()
				+ " - Generating files for the pipeline ...");
		if (sFile.equals("New")) {
			sFile = "test.mzq";
			String sMessage = "This project has not been saved. Proteosuite will create a test.mzq file \n";
			sMessage = sMessage + " under " + sWorkspace + " to run the pipeline. \n";
			JOptionPane.showMessageDialog(this, sMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
			sProjectName = sFile;
		}

		// ... Generate mzq file ...//
		System.out.println(SYS_UTILS.getTime() + " - Generating mzq file ...");
		jtaLog.append("\n" + SYS_UTILS.getTime() + " - Generating mzq file ...");
		boolean isOK = writeMzQuantML(jcbTechnique.getSelectedItem().toString(), sFile, jtRawFiles);
		if (!isOK)
			return false;
		
		// ... Unmarshall mzquantml file ...//
		Validator validator = XMLparser.getValidator(MZQ_XSD);
		boolean validFlag = XMLparser.validate(validator,
				sWorkspace.replace("\\", "/") + "/" + sProjectName);
		System.out.println(SYS_UTILS.getTime()
				+ " - Validating mzQuantML ...");
		jtaLog.append("\n" + SYS_UTILS.getTime()
				+ " - Validating mzQuantML ...");
		if (!validFlag) {
			JOptionPane.showMessageDialog(this, "Invalid mzQuantML file",
					"Error", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// ... Modify the mzQuantML structure according to the
			// experiment ...//
			writeXTrackerFiles(jcbTechnique.getSelectedItem().toString(), jtRawFiles);
		}
		
		return true;
	}

	/**
	 * Write mzQuantML file 
	 * @param sExperiment - Type of experiment 
	 * @param sFile - File name
	 * @return true/false
	 */
	private boolean writeMzQuantML(String sExperiment, String sFile, JTable jtRawFiles) {
		boolean ret = true;

		// ... Create object ...//
		MzQuantML qml = new MzQuantML();

		// ... Set version ...//
		String Version = "1.0.0";
		qml.setVersion(Version);
		Calendar rightNow = Calendar.getInstance();
		qml.setCreationDate(rightNow);
		qml.setId(sFile);

		// ----------------------//
		// ... CREATE CV LIST ...//
		// ----------------------//
		CvList cvs = new CvList();
		List<Cv> cvList = cvs.getCv();
		Cv cvPSI_MS = new Cv();
		cvPSI_MS.setId("PSI-MS");
		cvPSI_MS.setUri("http://psidev.cvs.sourceforge.net/viewvc/*checkout*/psidev/psi/psi-ms/mzML/controlledVocabulary/psi-ms.obo");
		cvPSI_MS.setFullName("Proteomics Standards Initiative Mass Spectrometry Vocabulary");
		cvPSI_MS.setVersion(PSI_MS_VERSION);

		Cv cvUO = new Cv();
		cvUO.setId("UO");
		cvUO.setUri("http://obo.cvs.sourceforge.net/*checkout*/obo/obo/ontology/phenotype/unit.obo");
		cvUO.setFullName("Unit Ontology");

		Cv cvPSI_MOD = new Cv();
		cvPSI_MOD.setId("PSI-MOD");
		cvPSI_MOD
				.setUri("http://psidev.cvs.sourceforge.net/psidev/psi/mod/data/PSI-MOD.obo");
		cvPSI_MOD
				.setFullName("Proteomics Standards Initiative Protein Modifications");
		cvPSI_MOD.setVersion(PSI_MOD_VERSION);

		Cv cvUNI_MOD = new Cv();
		cvUNI_MOD.setId("UNIMOD");
		cvUNI_MOD.setUri("http://www.unimod.org/obo/unimod.obo");
		cvUNI_MOD.setFullName("UNIMOD");

		cvList.add(cvPSI_MS);
		cvList.add(cvUO);
		cvList.add(cvPSI_MOD);
		cvList.add(cvUNI_MOD);
		qml.setCvList(cvs);

		// -------------------------------//
		// ... CREATE ANALYSIS SUMMARY ...//
		// -------------------------------//

		if (sExperiment.contains("iTRAQ")) {

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			// ParamList pl = new ParamList();
			// List<AbstractParam> cvParamList = pl.getParamGroup();
			/*
			 * CvParam cvp = new CvParam(); //cvp.setAccession("MS:1002010");
			 * cvp.setAccession("MS:1001837"); cvp.setCv(cvPSI_MS);
			 * //cvp.setName("TMT quantitation analysis");
			 * cvp.setName("iTRAQ quantitation analysis"); cvParamList.add(cvp);
			 */
			// id: MS:1001837
			// name: iTRAQ quantitation analysis

			CvParam cvp6 = new CvParam();
			cvp6.setAccession("MS:1002023");
			cvp6.setCv(cvPSI_MS);
			cvp6.setName("MS2 tag-based analysis");
			cvParamList.add(cvp6);

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002024");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("MS2 tag-based analysis feature level quantitation");
			cvParamList.add(cvp2);

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002025");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("true");
			cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");
			cvParamList.add(cvp3);

			CvParam cvp4 = new CvParam();
			cvp4.setAccession("MS:1002026");
			cvp4.setCv(cvPSI_MS);
			cvp4.setValue("true");
			cvp4.setName("MS2 tag-based analysis protein level quantitation");
			cvParamList.add(cvp4);

			CvParam cvp5 = new CvParam();
			cvp5.setAccession("MS:1002027");
			cvp5.setCv(cvPSI_MS);
			cvp5.setValue("false");
			cvp5.setName("MS2 tag-based analysis protein group level quantitation");
			cvParamList.add(cvp5);

			qml.setAnalysisSummary(as);
		}
		if (sExperiment.contains("TMT")) {

			CvParam cvp = new CvParam();
			cvp.setAccession("MS:1001837");
			cvp.setCv(cvPSI_MS);
			cvp.setName("TMT quantitation analysis");

			CvParam cvp6 = new CvParam();
			cvp6.setAccession("MS:1002023");
			cvp6.setCv(cvPSI_MS);
			cvp6.setName("MS2 tag-based analysis");

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002024");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("MS2 tag-based analysis feature level quantitation");

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002025");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("true");
			cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

			CvParam cvp4 = new CvParam();
			cvp4.setAccession("MS:1002026");
			cvp4.setCv(cvPSI_MS);
			cvp4.setValue("true");
			cvp4.setName("MS2 tag-based analysis protein level quantitation");

			CvParam cvp5 = new CvParam();
			cvp5.setAccession("MS:1002027");
			cvp5.setCv(cvPSI_MS);
			cvp5.setValue("false");
			cvp5.setName("MS2 tag-based analysis protein group level quantitation");

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			cvParamList.add(cvp6);
			cvParamList.add(cvp2);
			cvParamList.add(cvp3);
			cvParamList.add(cvp4);
			cvParamList.add(cvp5);

			qml.setAnalysisSummary(as);
		}
		if (sExperiment.contains("emPAI")) {

			CvParam cvp = new CvParam();
			cvp.setAccession("MS:1001836");
			cvp.setCv(cvPSI_MS);
			cvp.setName("spectral counting quantitation analysis");

			CvParam cvp1 = new CvParam();
			cvp1.setAccession("MS:1002015");
			cvp1.setCv(cvPSI_MS);
			cvp1.setValue("false");
			cvp1.setName("spectral count peptide level quantitation");

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002016");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("spectral count protein level quantitation");

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002017");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("false");
			cvp3.setName("spectral count proteingroup level quantitation");

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			cvParamList.add(cvp);
			cvParamList.add(cvp1);
			cvParamList.add(cvp2);
			cvParamList.add(cvp3);
			qml.setAnalysisSummary(as);
		}

		// --------------------------//
		// ... CREATE INPUT FILES ...//
		// --------------------------//
		InputFiles inputFiles = new InputFiles();

		// --------------------------//
		// ... RAW FILES ...//
		// --------------------------//

		// ... Get raw files ...//
		List<RawFilesGroup> rawFilesGroupList = inputFiles.getRawFilesGroup();
		Map<String, List<String[]>> hmRawFiles = new HashMap<String, List<String[]>>();

		boolean bExists = false;
		// ... Select all raw files from grid ...//
		for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
			// ... Check if group has been added previously ...//
			String sKey = jtRawFiles.getValueAt(iI, 0).toString();
			bExists = hmRawFiles.containsKey(sKey);
			if (bExists == false) {
				List<String[]> al = new ArrayList<String[]>();
				String[] sTemp = new String[2];
				sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
				sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
				al.add(sTemp);
				hmRawFiles.put(sKey, al);
			} else {
				List<String[]> al2 = hmRawFiles.get(jtRawFiles.getValueAt(iI, 4)
						.toString());
				String[] sTemp = new String[2];
				sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
				sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
				al2.add(sTemp);
				hmRawFiles.put(sKey, al2);
			}
		}
		// ... Set raw files groups ...//
		int iCounter = 0;
		for (Entry<String, List<String[]>> entry : hmRawFiles
				.entrySet()) {

			RawFilesGroup rawFilesGroup = new RawFilesGroup();
			List<RawFile> rawFilesList = rawFilesGroup.getRawFile();

			// String rgId = "raw_"+Integer.toString(iCounter+1);
			String rgId = jtRawFiles.getValueAt(iCounter, 0).toString();
			List<String[]> saGroups = entry.getValue();
			for (String[] slFiles : saGroups)
			{
				// ... Identify the corresponding raw files
				String rawFname = slFiles[0];
				String rawId = "r" + Integer.toString(iCounter + 1);
				// ... Raw files ...//
				RawFile rawFile = new RawFile();
				rawFile.setName(rawFname);
				rawFile.setId(rawId);
				rawFile.setLocation(slFiles[1]);
				rawFilesList.add(rawFile);
				iCounter++;
			}
			rawFilesGroup.setId(rgId);
			rawFilesGroupList.add(rawFilesGroup);
		}

		// -----------------------------//
		// ... IDENTIFICATION FILES ...//
		// -----------------------------//

		// ... Define those structures that will be further used (e.g. in
		// AssayList) ...//
		Map<String, String> idFileNameIdMap = new HashMap<String, String>();

		// ... Identification file containers ...//
		IdentificationFiles idFiles = inputFiles.getIdentificationFiles();
		if (idFiles == null) {
			idFiles = new IdentificationFiles();
		}
		List<IdentificationFile> idFilesList = idFiles.getIdentificationFile();

		// ... Select all raw files from grid ...//
		for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
			// ... Identification files ...//
			String idFname = jtIdentFiles.getValueAt(iI, 0).toString();
			String idId = "id_file" + Integer.toString(iI + 1);

			IdentificationFile idFile = new IdentificationFile();
			idFile.setName(idFname);
			idFile.setId(idId);
			idFile.setLocation(jtIdentFiles.getValueAt(iI, 1).toString()
					.replace("\\", "/"));
			idFilesList.add(idFile);
			idFileNameIdMap.put(idFname, idId); // ... Saving hashmap for
												// AssayList ...//
		}
		inputFiles.setIdentificationFiles(idFiles);
		qml.setInputFiles(inputFiles);

		// ---------------------------//
		// ... Create SoftwareList ...//
		// ---------------------------//
		SoftwareList softwareList = new SoftwareList();
		Software software = new Software();
		softwareList.getSoftware().add(software);
		software.setId("x-Tracker");
		software.setVersion("2.0-ALPHA");
		CvParam cvpSW = new CvParam();
		cvpSW.setAccession("MS:1002123");
		cvpSW.setCv(cvPSI_MS);
		cvpSW.setName("x-Tracker");
		software.getCvParam().add(cvpSW);

		Software software2 = new Software();
		softwareList.getSoftware().add(software2);
		software2.setId("ProteoSuite");
		software2.setVersion(sPS_Version);
		CvParam cvpSW2 = new CvParam();
		cvpSW2.setAccession("MS:1002124");
		cvpSW2.setCv(cvPSI_MS);
		cvpSW2.setName("ProteoSuite");
		software2.getCvParam().add(cvpSW2);
		qml.setSoftwareList(softwareList);

		// ---------------------------------//
		// ... Create DataProcessingList ...//
		// ---------------------------------//
		DataProcessingList dataProcessingList = new DataProcessingList();
		DataProcessing dataProcessing = new DataProcessing();
		dataProcessing.setId("DP1");
		dataProcessing.setSoftware(software);
		dataProcessing.setOrder(BigInteger.ONE);

		// ... Based on the technique, select the plugins that are available to
		// perform the quantitation ...//
		if (sExperiment.contains("iTRAQ")) {
			String[] sPipeline;
			sPipeline = new String[4];
			sPipeline = getPlugins(sExperiment, jtRawFiles);

			// ... Processing methods ...//
			ProcessingMethod processingMethod = new ProcessingMethod();
			processingMethod.setOrder(BigInteger.ONE);
			List<AbstractParam> pmList = processingMethod.getParamGroup();
			UserParam up1 = new UserParam();
			up1.setName("Plugin type");
			up1.setValue("load identification");
			pmList.add(up1);
			UserParam up2 = new UserParam();
			up2.setName("Plugin name");
			up2.setValue(sPipeline[0]);
			pmList.add(up2);
			UserParam up3 = new UserParam();
			up3.setName("Plugin configuration file");
			up3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[0] + ".xtp");
			pmList.add(up3);
			dataProcessing.getProcessingMethod().add(processingMethod);

			ProcessingMethod processingMethod2 = new ProcessingMethod();
			processingMethod2.setOrder(BigInteger.valueOf(2));
			List<AbstractParam> pmList2 = processingMethod2.getParamGroup();
			UserParam up2_1 = new UserParam();
			up2_1.setName("Plugin type");
			up2_1.setValue("load raw spectra");
			pmList2.add(up2_1);
			UserParam up2_2 = new UserParam();
			up2_2.setName("Plugin name");
			up2_2.setValue(sPipeline[1]);
			pmList2.add(up2_2);
			UserParam up2_3 = new UserParam();
			up2_3.setName("Plugin configuration file");
			up2_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[1] + ".xtp");
			pmList2.add(up2_3);
			dataProcessing.getProcessingMethod().add(processingMethod2);

			ProcessingMethod processingMethod3 = new ProcessingMethod();
			processingMethod3.setOrder(BigInteger.valueOf(3));
			List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
			UserParam up3_1 = new UserParam();
			up3_1.setName("Plugin type");
			up3_1.setValue("feature detection and quantitation");
			pmList3.add(up3_1);
			UserParam up3_2 = new UserParam();
			up3_2.setName("Plugin name");
			up3_2.setValue(sPipeline[2]);
			pmList3.add(up3_2);
			UserParam up3_3 = new UserParam();
			up3_3.setName("Plugin configuration file");
			up3_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[2] + ".xtp");
			pmList3.add(up3_3);
			UserParam up3_4 = new UserParam();
			up3_4.setName("Feature to peptide inference method");
			up3_4.setValue("mean");
			pmList3.add(up3_4);
			UserParam up3_5 = new UserParam();
			up3_5.setName("Peptide to protein inference method");
			up3_5.setValue("weightedAverage");
			pmList3.add(up3_5);
			UserParam up3_6 = new UserParam();
			up3_6.setName("Assay to Study Variables inference method");
			up3_6.setValue("sum");
			pmList3.add(up3_6);
			UserParam up3_7 = new UserParam();
			up3_7.setName("Protein ratio calculation infer from peptide ratio");
			up3_7.setValue("true");
			pmList3.add(up3_7);
			dataProcessing.getProcessingMethod().add(processingMethod3);

			ProcessingMethod processingMethod4 = new ProcessingMethod();
			processingMethod4.setOrder(BigInteger.valueOf(4));
			List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
			UserParam up4_1 = new UserParam();
			up4_1.setName("Plugin type");
			up4_1.setValue("Output");
			pmList4.add(up4_1);
			UserParam up4_2 = new UserParam();
			up4_2.setName("Plugin name");
			up4_2.setValue(sPipeline[3]);
			pmList4.add(up4_2);
			UserParam up4_3 = new UserParam();
			up4_3.setName("Plugin configuration file");
			up4_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[3] + ".xtp");
			pmList4.add(up4_3);
			dataProcessing.getProcessingMethod().add(processingMethod4);
		}
		if (sExperiment.contains("emPAI")) {
			String[] sPipeline;
			sPipeline = new String[4];
			sPipeline = getPlugins(sExperiment, jtRawFiles);

			// ... Processing methods ...//
			ProcessingMethod processingMethod = new ProcessingMethod();
			processingMethod.setOrder(BigInteger.ONE);
			List<AbstractParam> pmList = processingMethod.getParamGroup();
			UserParam up1 = new UserParam();
			up1.setName("Plugin type");
			up1.setValue("load identification");
			pmList.add(up1);
			UserParam up2 = new UserParam();
			up2.setName("Plugin name");
			up2.setValue(sPipeline[0]);
			pmList.add(up2);
			UserParam up3 = new UserParam();
			up3.setName("Plugin configuration file");
			up3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[0] + ".xtp");
			pmList.add(up3);
			dataProcessing.getProcessingMethod().add(processingMethod);

			ProcessingMethod processingMethod3 = new ProcessingMethod();
			processingMethod3.setOrder(BigInteger.valueOf(3));
			List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
			UserParam up3_1 = new UserParam();
			up3_1.setName("Plugin type");
			up3_1.setValue("feature detection and quantitation");
			pmList3.add(up3_1);
			UserParam up3_2 = new UserParam();
			up3_2.setName("Plugin name");
			up3_2.setValue(sPipeline[2]);
			pmList3.add(up3_2);
			UserParam up3_3 = new UserParam();
			up3_3.setName("Plugin configuration file");
			up3_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[2] + ".xtp");
			pmList3.add(up3_3);
			dataProcessing.getProcessingMethod().add(processingMethod3);

			ProcessingMethod processingMethod4 = new ProcessingMethod();
			processingMethod4.setOrder(BigInteger.valueOf(4));
			List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
			UserParam up4_1 = new UserParam();
			up4_1.setName("Plugin type");
			up4_1.setValue("Output");
			pmList4.add(up4_1);
			UserParam up4_2 = new UserParam();
			up4_2.setName("Plugin name");
			up4_2.setValue(sPipeline[3]);
			pmList4.add(up4_2);
			UserParam up4_3 = new UserParam();
			up4_3.setName("Plugin configuration file");
			up4_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[3] + ".xtp");
			pmList4.add(up4_3);
			dataProcessing.getProcessingMethod().add(processingMethod4);
		}
		dataProcessingList.getDataProcessing().add(dataProcessing);
		qml.setDataProcessingList(dataProcessingList);

		// ------------------------//
		// ... Create AssayList ...//
		// ------------------------//
		AssayList assays = new AssayList();
		assays.setId("AssayList1");
		List<Assay> assayList = assays.getAssay();
		// This will be used in StudyVariableList
		Map<String, List<Assay>> studyVarAssayID = new HashMap<String, List<Assay>>();																										

		if (sExperiment.contains("iTRAQ")) {
			// ... Assay list will be retrieved from the parameters set up in
			// the configQuantML file ...//
			try {
				DocumentBuilderFactory domFactory = DocumentBuilderFactory
						.newInstance();
				domFactory.setNamespaceAware(true);
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				Document doc = builder.parse("configQuant.xml");
				XPath xpath = XPathFactory.newInstance().newXPath();

				// ... Check raw files ...//
				for (int iJ = 0; iJ < jtRawFiles.getRowCount(); iJ++) {
					XPathExpression expr = xpath
							.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
									+ jtRawFiles.getValueAt(iJ, 0) + "']");
					NodeList nodes = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					if (nodes.getLength() <= 0) {
						JOptionPane
								.showMessageDialog(
										this,
										"Please specify the parameter settings for each raw file in Project->Set Quantitation Parameters->iTRAQ.",
										"Error",
										JOptionPane.INFORMATION_MESSAGE);
						return false;
					}
					// ... Validating files ...//
					expr = xpath
							.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
									+ jtRawFiles.getValueAt(iJ, 0)
									+ "']/AssayParam");
					nodes = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					boolean blnExists = false;
					for (int iI = 0; iI < nodes.getLength(); iI++) {
						Node node = nodes.item(iI);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element) node;
							NodeList nodelist = element
									.getElementsByTagName("AssayID");
							Element element2 = (Element) nodelist.item(0);
							NodeList fstNm1 = element2.getChildNodes();
							String sAssayID = fstNm1.item(0).getNodeValue()
									.toString();

							Element element3 = (Element) node;
							NodeList nodelist2 = element3
									.getElementsByTagName("AssayName");
							Element element4 = (Element) nodelist2.item(0);
							NodeList fstNm2 = element4.getChildNodes();
							String sAssayName = fstNm2.item(0).getNodeValue()
									.toString();

							Element element5 = (Element) node;
							NodeList nodelist3 = element5
									.getElementsByTagName("mzValue");
							Element element6 = (Element) nodelist3.item(0);
							NodeList fstNm3 = element6.getChildNodes();
							String sMzValue = fstNm3.item(0).getNodeValue()
									.toString();

							Element element7 = (Element) node;
							NodeList nodelist4 = element7
									.getElementsByTagName("StudyVariable");
							Element element8 = (Element) nodelist4.item(0);
							NodeList fstNm4 = element8.getChildNodes();
							String sStudyVariable = fstNm4.item(0)
									.getNodeValue().toString();

							Assay assay = new Assay();
							String sRawFile = jtRawFiles.getValueAt(iJ, 0).toString();
							String assName = sAssayID; // ... e.g. 114 ...//
							String assId = "i" + (iJ + 1) + "_" + sAssayID;
							assay.setId(assId);
							assay.setName(assName);

							// ... Create the study variable tree ...//
							blnExists = studyVarAssayID.containsKey(sStudyVariable);
							List<Assay> al;
							if (!blnExists)
								al = new ArrayList<Assay>();
							else
								al = studyVarAssayID.get(sStudyVariable);

							al.add(assay);
							studyVarAssayID.put(sStudyVariable, al);
							

							// ... Check the rawFileGroup associated to that
							// assay ...//
							int iK = 0;
							for (RawFilesGroup rfg : rawFilesGroupList) {
								String sKey = sRawFile;
								RawFilesGroup rfGroup = rawFilesGroupList
										.get(iK);
								if (rfg.getId().equals(sKey)) {
									assay.setRawFilesGroup(rfGroup);
									break;
								}
								iK++;
							}
							Label label = new Label();
							CvParam labelCvParam = new CvParam();
							labelCvParam.setAccession("");
							labelCvParam.setName(sAssayName);
							labelCvParam.setValue(sMzValue);
							labelCvParam.setCv(cvPSI_MOD);
							List<ModParam> modParams = label.getModification();
							ModParam modParam = new ModParam();
							modParam.setCvParam(labelCvParam);
							modParam.setMassDelta(145.0f);
							modParams.add(modParam);
							assay.setLabel(label);
							assayList.add(assay);
						}
					}
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		if (sExperiment.contains("emPAI")) {
			// ... Check the rawFileGroup associated to that assay ...//
			int iK = 0;
			for (RawFilesGroup rfg : rawFilesGroupList) {
				Assay assay = new Assay();
				assay.setId("assay_" + Integer.toString(iK + 1));
				assay.setName("assay_" + Integer.toString(iK + 1));
				assay.setRawFilesGroup(rfg);
				CvParam labelCvParam = new CvParam();
				labelCvParam.setAccession("MS:1002038");
				labelCvParam.setName("unlabeled sample");
				labelCvParam.setCv(cvPSI_MS);
				Label label = new Label();
				List<ModParam> modParams = label.getModification();
				ModParam modParam = new ModParam();
				modParam.setCvParam(labelCvParam);
				modParam.setMassDelta(0.0f);
				modParams.add(modParam);
				assay.setLabel(label);
				assayList.add(assay);
				iK++;
			}
		}
		qml.setAssayList(assays);

		// --------------------------------//
		// ... Create StudyVariableList ...//
		// --------------------------------//

		StudyVariableList studyVariables = new StudyVariableList();
		List<StudyVariable> studyVariableList = studyVariables.getStudyVariable();

		if (sExperiment.contains("iTRAQ")) {			
			for (Entry<String, List<Assay>> pairs : studyVarAssayID.entrySet())
			{
				String group = pairs.getKey();
				List<Assay> al = pairs.getValue();
				StudyVariable studyVariable = new StudyVariable();
				studyVariable.setName(group);
				studyVariable.setId("SV_" + group);
				List<String> assayRefList = studyVariable.getAssayRefs();
				for (Assay assay : al) {
					assayRefList.add(assay.getId());
				}
				CvParam cvp9 = new CvParam();
				cvp9.setAccession("MS:1001807");
				cvp9.setCv(cvPSI_MS);
				cvp9.setValue("1");
				cvp9.setName("StudyVariable attribute");
				List<AbstractParam> paramList = studyVariable.getParamGroup();
				paramList.add(cvp9);
				studyVariableList.add(studyVariable);
			}
		}
		if (sExperiment.contains("emPAI")) {
			StudyVariable studyVariable = new StudyVariable();
			studyVariable.setName("Group");
			studyVariable.setId("SV_group");
			List<String> assayRefList = studyVariable.getAssayRefs();
			assayRefList.add(assayList.get(0).getId());
			studyVariableList.add(studyVariable);
		}
		qml.setStudyVariableList(studyVariables);

		// ... Marshal mzQuantML object ...//
		MzQuantMLMarshaller marshaller = new MzQuantMLMarshaller(sWorkspace
				+ "/" + sFile);
		marshaller.marshall(qml);
		return ret;
	}

	/**
	 * Write xTrackerMain based on the technique 
	 * @param sExperiment - Pipeline type 
	 * @return void
	 **/
	private void writeXTrackerFiles(String sExperiment, JTable jtRawFiles) {
		XTracker xTracker = new XTracker();
		// ... Based on the technique, select the plugins that are available to
		// perform the quantitation ...//
		String[] sPipeline = getPlugins(sExperiment, jtRawFiles);

		// ... xTracker consists of 4 main plugins (read more on
		// www.x-tracker.info) ...//
		xTracker.writeXTrackerIdent(sExperiment, sPipeline[0], sWorkspace, jtIdentFiles, jtRawFiles);
		xTracker.writeXTrackerRaw(sPipeline[1], jtRawFiles, sWorkspace);
		xTracker.writeXTrackerQuant(sPipeline[2], jtRawFiles, sWorkspace);
		xTracker.writeXTrackerOutput(sPipeline[3], jtQuantFiles, sWorkspace, sProjectName);
	}

	/**
	 * This method gets the plugins based on the selected pipeline
	 * @param sExperiment - Pipeline type 
	 * @return Returns an array with the different plugins
	 **/
	private String[] getPlugins(String sExperiment, JTable jtRawFiles) {
		final List<List<String>> alPlugins = new ArrayList<List<String>>();
		String[] sPipeline;
		sPipeline = new String[5];

		// ... Read files using XML parser (Creates an Array of ArrayList) ...//
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File("config.xml"));

			NodeList nodeList = document
					.getElementsByTagName("pluginLoadIdentFiles");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginLoadRawFiles");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginQuantitation");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginOutput");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ... Using the array list we need to find the pipeline and
		// corresponding plugins ...//

		// ... Find ident file plugin ...//
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(jtIdentFiles.getValueAt(0, 2).toString()
							.toLowerCase())) {
				sPipeline[0] = arrayOfStrings[2].toString();
				break;
			}
		}
		// ... Find raw file plugin ...//
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(jtRawFiles.getValueAt(0, 2).toString()
							.toLowerCase())) {
				sPipeline[1] = arrayOfStrings[2].toString();
				break;
			}
		}
		// ... Find quant file plugin ...//
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1].toString().toLowerCase()
					.equals(sExperiment.toLowerCase())) {
				sPipeline[2] = arrayOfStrings[2].toString();
				break;
			}
		}
		// Find output file plugin
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(jcbOutputFormat.getSelectedItem().toString()
							.toLowerCase())) {
				sPipeline[3] = arrayOfStrings[2].toString();
				break;
			}
		}
		return sPipeline;
	}

	/**
	 * Main
	 * @param args the command line arguments (leave empty)
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

	private final JButton jbExportFeatMZQExcel = new JButton();
	private final JButton jbExportMGFExcel = new JButton();
	private final JButton jbExportMascotXMLExcel = new JButton();
	private final JButton jbExportMzIdentMLExcel = new JButton();
	private final JButton jbExportMzMLExcel = new JButton();
	private final JButton jbExportPepMZQExcel = new JButton();
	private final JButton jbExportProtMZQExcel = new JButton();
	private final JButton jbSaveProject = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/save.gif")));
	private final JComboBox<String> jcbOutputFormat = new JComboBox<String>();
	private final JComboBox<String> jcbPSM = new JComboBox<String>();
	private final JComboBox<String> jcbTechnique = new JComboBox<String>();
	private final JDesktopPane jdpMS = new JDesktopPane();
	private final JDesktopPane jdpTIC = new JDesktopPane();
	private final JEditorPane jepMGFView = new JEditorPane();
	private final JEditorPane jepMZQView = new JEditorPane();
	private final JEditorPane jepMascotXMLView = new JEditorPane();
	private final JEditorPane jepMzIDView = new JEditorPane();
	private final JEditorPane jepMzMLView = new JEditorPane();
	private final JLabel jlFileNameMGFText = new JLabel();
	private final JLabel jlFileNameMzIDText = new JLabel();
	private final JLabel jlFileNameMzMLText = new JLabel();
	private final JLabel jlFileNameMzQText = new JLabel();
	private final JLabel jlIdentFilesStatus = new JLabel();
	private final JLabel jlRawFilesStatus = new JLabel();
	private final JMenuItem jmCloseProject = new JMenuItem("Close Project");
	private final JMenuItem jmSaveProject = new JMenuItem("Save Project", new ImageIcon(getClass().getClassLoader().getResource("images/save.gif")));
	private final JPanel jp2D = new JPanel();
	private final JPanel jpMainPanelView = new JPanel();
	private final JPanel jpProjectStatus = new JPanel();
	private final JPanel jpToolBar = new JPanel();
	private final JSplitPane jspLeftViewer = new JSplitPane();
	private final JSplitPane jspLeftViewerDetails = new JSplitPane();
	private final JSplitPane jspMainPanelView = new JSplitPane();
	private final JSplitPane jspMzML = new JSplitPane();
	private final JSplitPane jspProperties = new JSplitPane();
	private final JSplitPane jspViewerAndProperties = new JSplitPane();	
	private final JTextArea jtaLog = new JTextArea();
	private final JTable jTable1 = new JTable();
	private final JTable jTable2 = new JTable();
	
	private final JTable jtFeatureQuant = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtIdentFiles = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			if (colIndex < 4) {
				return false;
			} else {
				return true;
			}
		}
	};
	private final JTable jtMGF = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtMascotXMLView = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtMzIDProtGroup = new JTable();
	private final JTable jtMzId = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtMzML = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtPeptideQuant = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtProteinQuant = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtQuantFiles = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtRawData = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtRawFiles = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			if (colIndex < 4) {
				return false;
			} else {
				return true;
			}
		}
	};
	private final JTable jtTemplate1 = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTable jtTemplate2 = new JTable() {
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};
	private final JTabbedPane jtpLog = new JTabbedPane();
	private final JTabbedPane jtpMzQuantMLDetail = new JTabbedPane();
	private final JTabbedPane jtpProperties = new JTabbedPane();
	private final JTabbedPane jtpViewer = new JTabbedPane();
}