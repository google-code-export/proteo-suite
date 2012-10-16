/*
 * --------------------------------------------------------------------------
 * ProteoSuiteView.java
 * --------------------------------------------------------------------------
 * Description:       ProteoSuite Graphical Unit Interface
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/Test
 * --------------------------------------------------------------------------
 */
package org.proteosuite;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
import org.proteosuite.data.psSyntheticArray;
import org.proteosuite.data.psTemplateQuant;
import org.proteosuite.data.psTemplate;
import org.proteosuite.gui.*;
import org.proteosuite.external.IPC;
import org.proteosuite.external.IPC.Options;
import org.proteosuite.external.IPC.Results;
import org.proteosuite.external.ViewChartGUI;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.TwoDPlot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.FileDescription;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;
import uk.ac.cranfield.xTracker.*;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisData;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftware;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftwareList;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroupList;
import uk.ac.liv.jmzqml.model.mzqml.AbstractParam;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.Column;
import uk.ac.liv.jmzqml.model.mzqml.Cv;
import uk.ac.liv.jmzqml.model.mzqml.CvList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.CvParamRef;
import uk.ac.liv.jmzqml.model.mzqml.DataMatrix;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFiles;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.Label;
import uk.ac.liv.jmzqml.model.mzqml.ModParam;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.Param;
import uk.ac.liv.jmzqml.model.mzqml.ParamList;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensus;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensusList;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.Protein;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.model.mzqml.SearchDatabase;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLMarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;
        
/**
 * This class corresponds to the main form in ProteoSuite. The form includes the visualisation of data and other associated tools.
 * @author faviel
 */
public class ProteoSuiteView extends JFrame {

    //... Project settings ...//
    private final String sPS_Version = "0.2.0";
    private String sProjectName;
    private String sWorkspace;
    private boolean bProjectModified;
    private final String MZQ_XSD = "mzQuantML_1_0_0-rc2.xsd";    

    //... List of unmarshaller objects ...//
    private ArrayList<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
    private ArrayList<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
    private ArrayList<MzQuantMLUnmarshaller> aMzQMLUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();
    private MzQuantML mzQuantML = null;
    
    public ProteoSuiteView() {
        
        //... Setting standard look and feel ...//
        try {
                  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (InstantiationException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (IllegalAccessException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (UnsupportedLookAndFeelException ex) {
                    System.out.println(ex.getStackTrace());
        }
        
        //... Initializing all componentes (handled by netbeans) ...//
        initComponents();
        
        //... Window default settings ...//
        initProjectValues();
        setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: " + sWorkspace + " - " + sProjectName +  ">         http://www.proteosuite.org");
        
        //... Setting project icons ...//        
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        setIconImage(iconApp);
        
        //... Main Menu icons ...//        
        //... File ...//
        Icon newIcon = new ImageIcon(getClass().getClassLoader().getResource("images/new.gif"));
        Icon importIcon = new ImageIcon(getClass().getClassLoader().getResource("images/import.gif"));
        Icon openIcon = new ImageIcon(getClass().getClassLoader().getResource("images/open.gif"));
        Icon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("images/save.gif"));
        Icon printIcon = new ImageIcon(getClass().getClassLoader().getResource("images/print.gif"));                       
        //... Edit ...//
        Icon cutIcon = new ImageIcon(getClass().getClassLoader().getResource("images/cut.gif"));
        Icon copyIcon = new ImageIcon(getClass().getClassLoader().getResource("images/copy.gif"));
        Icon pasteIcon = new ImageIcon(getClass().getClassLoader().getResource("images/paste.gif"));        
        //... Project ...//
        Icon runIcon = new ImageIcon(getClass().getClassLoader().getResource("images/run.gif"));        
        //... Help ...//
        Icon helpIcon = new ImageIcon(getClass().getClassLoader().getResource("images/help.gif"));
                
        jmNewProject.setIcon(newIcon);        
        jmImportFile.setIcon(importIcon);
        jmOpenProject.setIcon(openIcon);
        jmSaveProject.setIcon(saveIcon);        
        jmPrint.setIcon(printIcon);        
        jmCut.setIcon(cutIcon);
        jmCopy.setIcon(copyIcon);        
        jmPaste.setIcon(pasteIcon);        
        jmRunQuantAnalysis.setIcon(runIcon);        
        jmHelpContent.setIcon(helpIcon);        

        //... Toolbar icons ...//
        jbNewProject.setIcon(newIcon);
        jbImportFile.setIcon(importIcon);        
        jbOpenProject.setIcon(openIcon);        
        jbSaveProject.setIcon(saveIcon);
        jbCut.setIcon(cutIcon);
        jbCopy.setIcon(copyIcon);
        jbPaste.setIcon(pasteIcon);                
        jbRunQuantAnalysis.setIcon(runIcon);
        
        //... Tabsheet and other shortcut icons ...//
        JLabel jlRawFilesIcon = new JLabel("Raw Files                                 ");
        Icon rawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/raw_file.gif"));
        jlRawFilesIcon.setIcon(rawFilesIcon);
        jlRawFilesIcon.setIconTextGap(5);
        jlRawFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpRawFiles.setTabComponentAt(0, jlRawFilesIcon);
        
        JLabel jlIdentFilesIcon = new JLabel("Identification Files                    ");
        Icon identFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif"));
        jlIdentFilesIcon.setIcon(identFilesIcon);
        jlIdentFilesIcon.setIconTextGap(5);
        jlIdentFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);
        
        JLabel jlQuantFilesIcon = new JLabel("Quantitation Files                      ");
        Icon quantFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/quant_file.gif"));
        jlQuantFilesIcon.setIcon(quantFilesIcon);
        jlQuantFilesIcon.setIconTextGap(5);
        jlQuantFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);        
        
        JLabel jlSpectrumIcon = new JLabel("Spectrum");
        Icon spectrumIcon = new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif"));
        jlSpectrumIcon.setIcon(spectrumIcon);
        jlSpectrumIcon.setIconTextGap(5);
        jlSpectrumIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(0, jlSpectrumIcon);
        
        JLabel jlTICIcon = new JLabel("TIC");
        Icon TICIcon = new ImageIcon(getClass().getClassLoader().getResource("images/chromat.gif"));
        jlTICIcon.setIcon(TICIcon);
        jlTICIcon.setIconTextGap(5);
        jlTICIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(1, jlTICIcon);                
        
        JLabel jlTwoDIcon = new JLabel("2D View");
        Icon twoDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/twod.gif"));
        jlTwoDIcon.setIcon(twoDIcon);
        jlTwoDIcon.setIconTextGap(5);
        jlTwoDIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(2, jlTwoDIcon);           
        
        JLabel jlThreeDIcon = new JLabel("3D View");
        Icon threeDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/threed.gif"));
        jlThreeDIcon.setIcon(threeDIcon);
        jlThreeDIcon.setIconTextGap(5);
        jlThreeDIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(3, jlThreeDIcon);        

        JLabel jlLogIcon = new JLabel("Log  ");
        Icon logIcon = new ImageIcon(getClass().getClassLoader().getResource("images/log.gif"));
        jlLogIcon.setIcon(logIcon);
        jlLogIcon.setIconTextGap(5);
        jlLogIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpLog.setTabComponentAt(0, jlLogIcon);
        
        JLabel jlRawDataIcon = new JLabel("Raw data");
        Icon rawDataIcon = new ImageIcon(getClass().getClassLoader().getResource("images/raw_data.gif"));
        jlRawDataIcon.setIcon(rawDataIcon);
        jlRawDataIcon.setIconTextGap(5);
        jlRawDataIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpLog.setTabComponentAt(1, jlRawDataIcon);
        
        JLabel jlPropertiesMzMLIcon = new JLabel("mzML View");
        Icon propertiesMzMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzMLIcon.setIcon(propertiesMzMLIcon);
        jlPropertiesMzMLIcon.setIconTextGap(5);
        jlPropertiesMzMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(0, jlPropertiesMzMLIcon);
        
        JLabel jlPropertiesMGFIcon = new JLabel("MGF View");
        Icon propertiesMGFIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMGFIcon.setIcon(propertiesMGFIcon);
        jlPropertiesMGFIcon.setIconTextGap(5);
        jlPropertiesMGFIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(1, jlPropertiesMGFIcon);          
        
        JLabel jlPropertiesMzIdentMLIcon = new JLabel("mzIdentML View");
        Icon propertiesMzIdenMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzIdentMLIcon.setIcon(propertiesMzIdenMLIcon);
        jlPropertiesMzIdentMLIcon.setIconTextGap(5);
        jlPropertiesMzIdentMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(2, jlPropertiesMzIdentMLIcon);
        
        JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View");
        Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMascotIcon.setIcon(propertiesMascotIcon);
        jlPropertiesMascotIcon.setIconTextGap(5);
        jlPropertiesMascotIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(3, jlPropertiesMascotIcon);        
        
        JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View");
        Icon propertiesMzQuantMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzQuantMLIcon.setIcon(propertiesMzQuantMLIcon);
        jlPropertiesMzQuantMLIcon.setIconTextGap(5);
        jlPropertiesMzQuantMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(4, jlPropertiesMzQuantMLIcon);                
        
        //... Setting Window Height and Width ...//
        this.setMinimumSize(new Dimension(1024, 780));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //... Setting dividers ...//
        jspMainPanelView.setDividerLocation(250);           //... Left Menu ...//
        jspLeftMenuBottom.setDividerLocation(200);          //... Ident and Quantitation separator ...//
        jspLeftViewerDetails.setDividerLocation(480);       //... Viewer Height ...//
        jspViewerAndProperties.setDividerLocation(600);     //... Viewer Width  ...//
        jspMzML.setDividerLocation(100);                    //... MzML data ...//
        jspProjectDetails.setDividerLocation(200);          //... MzML files ...//
        jspLeftMenuBottom.setDividerLocation(200);          //... IdentML files ...//
        
        jtpViewer.setSelectedIndex(0);
        jlFiles.requestFocusInWindow();
        
        //... Setting default selection (Viewers) ...//
        jtpViewer.setSelectedIndex(0);        
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(thick);
        jtpProperties.setSelectedIndex(0);
        jmMzMLView.setIcon(thick);        
        jtpLog.setSelectedIndex(0);
        jmLog.setIcon(thick);                
        
        jmShowProjectFiles.setIcon(thick);        
        jmShowViewer.setIcon(thick);
        jmShowProperties.setIcon(thick);
        
//        jtMzML.setAutoCreateRowSorter(true);
//        jtMascotXMLView.setAutoCreateRowSorter(true);
//        jtRawData.setAutoCreateRowSorter(true);
        
        jtpLog.setEnabledAt(2, false);
        jtpLog.setEnabledAt(3, false);
        jtpLog.setEnabledAt(4, false);
        
        //... Configuring exit events...//
        this.pack();
    }

    /** This method is called from within the constructor to
     * initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpToolBar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jbNewProject = new javax.swing.JButton();
        jbImportFile = new javax.swing.JButton();
        jbOpenProject = new javax.swing.JButton();
        jbSaveProject = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jbCut = new javax.swing.JButton();
        jbCopy = new javax.swing.JButton();
        jbPaste = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jbRunQuantAnalysis = new javax.swing.JButton();
        jpMainPanelView = new javax.swing.JPanel();
        jspMainPanelView = new javax.swing.JSplitPane();
        jpLeftPanelView = new javax.swing.JPanel();
        jspLeftPanelView = new javax.swing.JSplitPane();
        jpProjectHeader = new javax.swing.JPanel();
        jlFiles = new javax.swing.JLabel();
        jpProjectDetails = new javax.swing.JPanel();
        jspProjectDetails = new javax.swing.JSplitPane();
        jpLeftMenuTop = new javax.swing.JPanel();
        jtpRawFiles = new javax.swing.JTabbedPane();
        jspRawFiles = new javax.swing.JScrollPane();
        jtRawFiles = new javax.swing.JTable();
        jpLeftMenuBottom = new javax.swing.JPanel();
        jspLeftMenuBottom = new javax.swing.JSplitPane();
        jpQuantFiles = new javax.swing.JPanel();
        jtpQuantFiles = new javax.swing.JTabbedPane();
        jspQuantFiles = new javax.swing.JScrollPane();
        jtQuantFiles = new javax.swing.JTable();
        jtpIdentFiles = new javax.swing.JTabbedPane();
        jspIdentFiles = new javax.swing.JScrollPane();
        jtIdentFiles = new javax.swing.JTable();
        jpViewerAndProperties = new javax.swing.JPanel();
        jspViewerAndProperties = new javax.swing.JSplitPane();
        jpLeftViewer = new javax.swing.JPanel();
        jspLeftViewer = new javax.swing.JSplitPane();
        jspLeftViewerHeader = new javax.swing.JPanel();
        jlViewer = new javax.swing.JLabel();
        jpLeftViewerDetails = new javax.swing.JPanel();
        jspLeftViewerDetails = new javax.swing.JSplitPane();
        jpLeftViewerBottom = new javax.swing.JPanel();
        jtpLog = new javax.swing.JTabbedPane();
        jspLog = new javax.swing.JScrollPane();
        jtaLog = new javax.swing.JTextArea();
        jpRawDataValues = new javax.swing.JPanel();
        jspRawDataValues = new javax.swing.JSplitPane();
        jpRawDataValuesMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtMSIndex = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtMSMz = new javax.swing.JTextField();
        jspRawData = new javax.swing.JScrollPane();
        jtRawData = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtTemplate1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTemplate2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jtpViewer = new javax.swing.JTabbedPane();
        jdpMS = new javax.swing.JDesktopPane();
        jpTIC = new javax.swing.JPanel();
        jdpTIC = new javax.swing.JDesktopPane();
        jp2D = new javax.swing.JPanel();
        jp3D = new javax.swing.JPanel();
        jpPropertiesBox = new javax.swing.JPanel();
        jspProperties = new javax.swing.JSplitPane();
        jpProperties = new javax.swing.JPanel();
        jlProperties = new javax.swing.JLabel();
        jpPropetiesTab = new javax.swing.JPanel();
        jtpProperties = new javax.swing.JTabbedPane();
        jpMzML = new javax.swing.JPanel();
        jspMzML = new javax.swing.JSplitPane();
        jspMzMLHeader = new javax.swing.JScrollPane();
        jtaMzML = new javax.swing.JTextArea();
        jspMzMLDetail = new javax.swing.JSplitPane();
        jspMzMLSubDetail = new javax.swing.JScrollPane();
        jtMzML = new javax.swing.JTable();
        jpMzMLMenu = new javax.swing.JPanel();
        jlScan = new javax.swing.JLabel();
        jtScan = new javax.swing.JTextField();
        jlRT = new javax.swing.JLabel();
        jtRT = new javax.swing.JTextField();
        jlSearchMzML = new javax.swing.JLabel();
        jpMGF = new javax.swing.JPanel();
        jspMGF = new javax.swing.JSplitPane();
        jspMGFHeader = new javax.swing.JScrollPane();
        jtaMGFView = new javax.swing.JTextArea();
        jspMGFDetail = new javax.swing.JSplitPane();
        jpMGFMenu = new javax.swing.JPanel();
        jlSearchMGF = new javax.swing.JLabel();
        jlScanTitle = new javax.swing.JLabel();
        jtScanTitle = new javax.swing.JTextField();
        jspMGFSubDetail = new javax.swing.JScrollPane();
        jtMGF = new javax.swing.JTable();
        jpMzId = new javax.swing.JPanel();
        jspMzId = new javax.swing.JSplitPane();
        jspMzIdDetail = new javax.swing.JSplitPane();
        jpMzIdMenu = new javax.swing.JPanel();
        jlSearchMzId = new javax.swing.JLabel();
        jlProteinMzId = new javax.swing.JLabel();
        jtProteinMzId = new javax.swing.JTextField();
        jlPeptideMzId = new javax.swing.JLabel();
        jtPeptideMzId = new javax.swing.JTextField();
        jpMzIdSubDetail = new javax.swing.JScrollPane();
        jtMzId = new javax.swing.JTable();
        jspMzIdHeader = new javax.swing.JScrollPane();
        jtaMzIdView = new javax.swing.JTextArea();
        jpMascotXML = new javax.swing.JPanel();
        jspMascotXML = new javax.swing.JSplitPane();
        jspMascotXMLHeader = new javax.swing.JScrollPane();
        jtaMascotXML = new javax.swing.JTextArea();
        jspMascotXMLSubDetail = new javax.swing.JSplitPane();
        jspMascotXMLDetail = new javax.swing.JScrollPane();
        jtMascotXMLView = new javax.swing.JTable();
        jpMascotXMLMenu = new javax.swing.JPanel();
        jlSearchMascotXML = new javax.swing.JLabel();
        jlProtein = new javax.swing.JLabel();
        jtProtein = new javax.swing.JTextField();
        jlPeptide = new javax.swing.JLabel();
        jtPeptide = new javax.swing.JTextField();
        jbShowIsotopeDistrib = new javax.swing.JButton();
        jpMzQuantML = new javax.swing.JPanel();
        jspMzQuantML = new javax.swing.JSplitPane();
        jspMzQuantMLHeader = new javax.swing.JScrollPane();
        jtaMzQuantML = new javax.swing.JTextArea();
        jtpMzQuantMLDetail = new javax.swing.JTabbedPane();
        jpPeptideQuant = new javax.swing.JPanel();
        jspPeptideQuant = new javax.swing.JSplitPane();
        jpPeptideQuantHeader = new javax.swing.JPanel();
        jspPeptideQuantDetail = new javax.swing.JScrollPane();
        jtPeptideQuant = new javax.swing.JTable();
        jpProteinQuant = new javax.swing.JPanel();
        jspProteinQuant = new javax.swing.JSplitPane();
        jpProteinQuantHeader = new javax.swing.JPanel();
        jspProteinQuantDetail = new javax.swing.JScrollPane();
        jtProteinQuant = new javax.swing.JTable();
        FeatureQuant = new javax.swing.JPanel();
        jspFeatureQuant = new javax.swing.JSplitPane();
        jpFeatureQuantHeader = new javax.swing.JPanel();
        jspFeatureQuantDetail = new javax.swing.JScrollPane();
        jtFeatureQuant = new javax.swing.JTable();
        jpProjectStatus = new javax.swing.JPanel();
        jlRawFiles = new javax.swing.JLabel();
        jlIdentFiles = new javax.swing.JLabel();
        jlTechnique = new javax.swing.JLabel();
        jlQuantFiles = new javax.swing.JLabel();
        jlRawFilesStatus = new javax.swing.JLabel();
        jlIdentFilesStatus = new javax.swing.JLabel();
        jlQuantFilesStatus = new javax.swing.JLabel();
        jcbTechnique = new javax.swing.JComboBox();
        jcbOutputFormat = new javax.swing.JComboBox();
        jmMain = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmNewProject = new javax.swing.JMenuItem();
        jmImportFile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmOpenProject = new javax.swing.JMenuItem();
        jmOpenRecentProject = new javax.swing.JMenuItem();
        jmCloseProject = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmSaveProject = new javax.swing.JMenuItem();
        jmSaveProjectAs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmPrint = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmExit = new javax.swing.JMenuItem();
        jmEdit = new javax.swing.JMenu();
        jmCut = new javax.swing.JMenuItem();
        jmCopy = new javax.swing.JMenuItem();
        jmPaste = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jmView = new javax.swing.JMenu();
        jmShowProjectFiles = new javax.swing.JMenuItem();
        jmShowViewer = new javax.swing.JMenuItem();
        jmShowProperties = new javax.swing.JMenuItem();
        jmProject = new javax.swing.JMenu();
        jmEditIdent = new javax.swing.JMenuItem();
        jmEditQuant = new javax.swing.JMenuItem();
        jmAnalyze = new javax.swing.JMenu();
        jmRunQuantAnalysis = new javax.swing.JMenuItem();
        jmTools = new javax.swing.JMenu();
        jmConverters = new javax.swing.JMenu();
        jmMzML2MGF = new javax.swing.JMenuItem();
        jmCustomize = new javax.swing.JMenuItem();
        jmOptions = new javax.swing.JMenuItem();
        jmDatabases = new javax.swing.JMenu();
        jmSubmitPRIDE = new javax.swing.JMenuItem();
        jmWindow = new javax.swing.JMenu();
        jmSpectrum = new javax.swing.JMenuItem();
        jmTIC = new javax.swing.JMenuItem();
        jm2DView = new javax.swing.JMenuItem();
        jm3DView = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jmMzMLView = new javax.swing.JMenuItem();
        jmMGFView = new javax.swing.JMenuItem();
        jmMzIdentMLView = new javax.swing.JMenuItem();
        jmMascotXMLView = new javax.swing.JMenuItem();
        jmMzQuantMLView = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jmLog = new javax.swing.JMenuItem();
        jmRawData = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jmHelpContent = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jmContactUs = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jmAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jpToolBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpToolBar.setPreferredSize(new java.awt.Dimension(933, 32));

        jbNewProject.setToolTipText("New Project (Ctrl + N)");
        jbNewProject.setFocusable(false);
        jbNewProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbNewProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbNewProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbNewProjectMouseClicked(evt);
            }
        });
        jToolBar1.add(jbNewProject);

        jbImportFile.setToolTipText("Import File (Ctrl + I)");
        jbImportFile.setFocusable(false);
        jbImportFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbImportFile.setMaximumSize(new java.awt.Dimension(27, 21));
        jbImportFile.setMinimumSize(new java.awt.Dimension(27, 21));
        jbImportFile.setPreferredSize(new java.awt.Dimension(27, 21));
        jbImportFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbImportFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbImportFileMouseClicked(evt);
            }
        });
        jToolBar1.add(jbImportFile);

        jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
        jbOpenProject.setFocusable(false);
        jbOpenProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbOpenProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbOpenProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbOpenProjectMouseClicked(evt);
            }
        });
        jToolBar1.add(jbOpenProject);

        jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
        jbSaveProject.setFocusable(false);
        jbSaveProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbSaveProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbSaveProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbSaveProjectMouseClicked(evt);
            }
        });
        jToolBar1.add(jbSaveProject);

        jbCut.setToolTipText("Cut (Ctrl + X)");
        jbCut.setEnabled(false);
        jbCut.setFocusable(false);
        jbCut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbCut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jbCut);

        jbCopy.setEnabled(false);
        jbCopy.setFocusable(false);
        jbCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jbCopy);

        jbPaste.setEnabled(false);
        jbPaste.setFocusable(false);
        jbPaste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbPaste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jbPaste);

        jToolBar3.setMaximumSize(new java.awt.Dimension(34, 9));
        jToolBar3.setMinimumSize(new java.awt.Dimension(34, 9));

        jbRunQuantAnalysis.setToolTipText("Run (F5)");
        jbRunQuantAnalysis.setFocusable(false);
        jbRunQuantAnalysis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbRunQuantAnalysis.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbRunQuantAnalysis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbRunQuantAnalysisMouseClicked(evt);
            }
        });
        jToolBar3.add(jbRunQuantAnalysis);

        javax.swing.GroupLayout jpToolBarLayout = new javax.swing.GroupLayout(jpToolBar);
        jpToolBar.setLayout(jpToolBarLayout);
        jpToolBarLayout.setHorizontalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(688, Short.MAX_VALUE))
        );
        jpToolBarLayout.setVerticalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpToolBarLayout.createSequentialGroup()
                .addGroup(jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jspMainPanelView.setDividerLocation(280);
        jspMainPanelView.setDividerSize(3);

        jpLeftPanelView.setBackground(new java.awt.Color(204, 204, 255));

        jspLeftPanelView.setBorder(null);
        jspLeftPanelView.setDividerLocation(20);
        jspLeftPanelView.setDividerSize(1);
        jspLeftPanelView.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpProjectHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
        jpProjectHeader.setMinimumSize(new java.awt.Dimension(279, 25));
        jpProjectHeader.setPreferredSize(new java.awt.Dimension(280, 25));

        jlFiles.setBackground(new java.awt.Color(255, 255, 255));
        jlFiles.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jlFiles.setForeground(new java.awt.Color(102, 102, 102));
        jlFiles.setText("Files");

        javax.swing.GroupLayout jpProjectHeaderLayout = new javax.swing.GroupLayout(jpProjectHeader);
        jpProjectHeader.setLayout(jpProjectHeaderLayout);
        jpProjectHeaderLayout.setHorizontalGroup(
            jpProjectHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlFiles)
                .addContainerGap(239, Short.MAX_VALUE))
        );
        jpProjectHeaderLayout.setVerticalGroup(
            jpProjectHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectHeaderLayout.createSequentialGroup()
                .addComponent(jlFiles)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspLeftPanelView.setTopComponent(jpProjectHeader);

        jpProjectDetails.setBackground(new java.awt.Color(255, 255, 255));

        jspProjectDetails.setBackground(new java.awt.Color(255, 255, 255));
        jspProjectDetails.setDividerLocation(130);
        jspProjectDetails.setDividerSize(1);
        jspProjectDetails.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtpRawFiles.setBackground(new java.awt.Color(255, 255, 255));

        jspRawFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtRawFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Path", "Type", "Version", "Scans"
            }
        ));
        jtRawFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtRawFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtRawFilesMouseClicked(evt);
            }
        });
        jspRawFiles.setViewportView(jtRawFiles);

        jtpRawFiles.addTab("Raw Files", jspRawFiles);

        javax.swing.GroupLayout jpLeftMenuTopLayout = new javax.swing.GroupLayout(jpLeftMenuTop);
        jpLeftMenuTop.setLayout(jpLeftMenuTopLayout);
        jpLeftMenuTopLayout.setHorizontalGroup(
            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpRawFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );
        jpLeftMenuTopLayout.setVerticalGroup(
            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpRawFiles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );

        jspProjectDetails.setTopComponent(jpLeftMenuTop);

        jspLeftMenuBottom.setBorder(null);
        jspLeftMenuBottom.setDividerLocation(200);
        jspLeftMenuBottom.setDividerSize(1);
        jspLeftMenuBottom.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtpQuantFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtQuantFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Path", "Type", "Version"
            }
        ));
        jtQuantFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtQuantFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtQuantFilesMouseClicked(evt);
            }
        });
        jspQuantFiles.setViewportView(jtQuantFiles);

        jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);

        javax.swing.GroupLayout jpQuantFilesLayout = new javax.swing.GroupLayout(jpQuantFiles);
        jpQuantFiles.setLayout(jpQuantFilesLayout);
        jpQuantFilesLayout.setHorizontalGroup(
            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
        );
        jpQuantFilesLayout.setVerticalGroup(
            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        jspLeftMenuBottom.setRightComponent(jpQuantFiles);

        jtpIdentFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtIdentFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Path", "Type", "Version"
            }
        ));
        jtIdentFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtIdentFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtIdentFilesMouseClicked(evt);
            }
        });
        jspIdentFiles.setViewportView(jtIdentFiles);

        jtpIdentFiles.addTab("Identification Files", jspIdentFiles);

        jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

        javax.swing.GroupLayout jpLeftMenuBottomLayout = new javax.swing.GroupLayout(jpLeftMenuBottom);
        jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
        jpLeftMenuBottomLayout.setHorizontalGroup(
            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
        );
        jpLeftMenuBottomLayout.setVerticalGroup(
            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
        );

        jspProjectDetails.setRightComponent(jpLeftMenuBottom);

        javax.swing.GroupLayout jpProjectDetailsLayout = new javax.swing.GroupLayout(jpProjectDetails);
        jpProjectDetails.setLayout(jpProjectDetailsLayout);
        jpProjectDetailsLayout.setHorizontalGroup(
            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProjectDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
        );
        jpProjectDetailsLayout.setVerticalGroup(
            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProjectDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );

        jspLeftPanelView.setRightComponent(jpProjectDetails);

        javax.swing.GroupLayout jpLeftPanelViewLayout = new javax.swing.GroupLayout(jpLeftPanelView);
        jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
        jpLeftPanelViewLayout.setHorizontalGroup(
            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
        );
        jpLeftPanelViewLayout.setVerticalGroup(
            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        jspMainPanelView.setLeftComponent(jpLeftPanelView);

        jpViewerAndProperties.setBackground(new java.awt.Color(204, 204, 255));

        jspViewerAndProperties.setBorder(null);
        jspViewerAndProperties.setDividerLocation(380);
        jspViewerAndProperties.setDividerSize(3);

        jspLeftViewer.setDividerLocation(20);
        jspLeftViewer.setDividerSize(1);
        jspLeftViewer.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jspLeftViewerHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
        jspLeftViewerHeader.setMinimumSize(new java.awt.Dimension(380, 25));
        jspLeftViewerHeader.setPreferredSize(new java.awt.Dimension(380, 25));

        jlViewer.setBackground(new java.awt.Color(255, 255, 255));
        jlViewer.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jlViewer.setForeground(new java.awt.Color(102, 102, 102));
        jlViewer.setText("Viewer");

        javax.swing.GroupLayout jspLeftViewerHeaderLayout = new javax.swing.GroupLayout(jspLeftViewerHeader);
        jspLeftViewerHeader.setLayout(jspLeftViewerHeaderLayout);
        jspLeftViewerHeaderLayout.setHorizontalGroup(
            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlViewer)
                .addContainerGap(316, Short.MAX_VALUE))
        );
        jspLeftViewerHeaderLayout.setVerticalGroup(
            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                .addComponent(jlViewer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspLeftViewer.setTopComponent(jspLeftViewerHeader);

        jpLeftViewerDetails.setBackground(new java.awt.Color(255, 255, 255));

        jspLeftViewerDetails.setBorder(null);
        jspLeftViewerDetails.setDividerLocation(350);
        jspLeftViewerDetails.setDividerSize(2);
        jspLeftViewerDetails.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaLog.setColumns(20);
        jtaLog.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaLog.setRows(5);
        jspLog.setViewportView(jtaLog);

        jtpLog.addTab("Log", jspLog);

        jspRawDataValues.setDividerLocation(40);
        jspRawDataValues.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpRawDataValuesMenu.setMaximumSize(new java.awt.Dimension(32767, 50));
        jpRawDataValuesMenu.setMinimumSize(new java.awt.Dimension(0, 50));
        jpRawDataValuesMenu.setPreferredSize(new java.awt.Dimension(462, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Search:");

        jtMSIndex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtMSIndexKeyPressed(evt);
            }
        });

        jLabel2.setText("Index:");

        jLabel3.setText("m/z:");

        jtMSMz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtMSMzKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpRawDataValuesMenuLayout = new javax.swing.GroupLayout(jpRawDataValuesMenu);
        jpRawDataValuesMenu.setLayout(jpRawDataValuesMenuLayout);
        jpRawDataValuesMenuLayout.setHorizontalGroup(
            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRawDataValuesMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jtMSIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtMSMz, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jpRawDataValuesMenuLayout.setVerticalGroup(
            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRawDataValuesMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jtMSIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jtMSMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspRawDataValues.setLeftComponent(jpRawDataValuesMenu);

        jtRawData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "m/z", "Intensity"
            }
        ));
        jtRawData.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jspRawData.setViewportView(jtRawData);

        jspRawDataValues.setBottomComponent(jspRawData);

        javax.swing.GroupLayout jpRawDataValuesLayout = new javax.swing.GroupLayout(jpRawDataValues);
        jpRawDataValues.setLayout(jpRawDataValuesLayout);
        jpRawDataValuesLayout.setHorizontalGroup(
            jpRawDataValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspRawDataValues, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        jpRawDataValuesLayout.setVerticalGroup(
            jpRawDataValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspRawDataValues, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );

        jtpLog.addTab("Raw Data", jpRawDataValues);

        jtTemplate1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "m/z Index", "Scan Index", "Quant", "Template2 Index"
            }
        ));
        jScrollPane1.setViewportView(jtTemplate1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
        );

        jtpLog.addTab("Template 1", jPanel1);

        jtTemplate2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Template Index", "x", "y", "i"
            }
        ));
        jScrollPane2.setViewportView(jtTemplate2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
        );

        jtpLog.addTab("Template 2", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Scan Number"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );

        jtpLog.addTab("Synthetic Array", jPanel3);

        javax.swing.GroupLayout jpLeftViewerBottomLayout = new javax.swing.GroupLayout(jpLeftViewerBottom);
        jpLeftViewerBottom.setLayout(jpLeftViewerBottomLayout);
        jpLeftViewerBottomLayout.setHorizontalGroup(
            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        jpLeftViewerBottomLayout.setVerticalGroup(
            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
        );

        jspLeftViewerDetails.setRightComponent(jpLeftViewerBottom);

        jdpMS.setBackground(new java.awt.Color(255, 255, 255));
        jtpViewer.addTab("Spectrum", jdpMS);

        jpTIC.setBackground(new java.awt.Color(255, 255, 255));

        jdpTIC.setBackground(new java.awt.Color(255, 255, 255));
        jdpTIC.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpTICLayout = new javax.swing.GroupLayout(jpTIC);
        jpTIC.setLayout(jpTICLayout);
        jpTICLayout.setHorizontalGroup(
            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        );
        jpTICLayout.setVerticalGroup(
            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
        );

        jtpViewer.addTab("TIC", jpTIC);

        jp2D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp2DLayout = new javax.swing.GroupLayout(jp2D);
        jp2D.setLayout(jp2DLayout);
        jp2DLayout.setHorizontalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
        jp2DLayout.setVerticalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );

        jtpViewer.addTab("2D View", jp2D);

        jp3D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp3DLayout = new javax.swing.GroupLayout(jp3D);
        jp3D.setLayout(jp3DLayout);
        jp3DLayout.setHorizontalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
        jp3DLayout.setVerticalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );

        jtpViewer.addTab("3D View", jp3D);

        jspLeftViewerDetails.setTopComponent(jtpViewer);

        javax.swing.GroupLayout jpLeftViewerDetailsLayout = new javax.swing.GroupLayout(jpLeftViewerDetails);
        jpLeftViewerDetails.setLayout(jpLeftViewerDetailsLayout);
        jpLeftViewerDetailsLayout.setHorizontalGroup(
            jpLeftViewerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftViewerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        jpLeftViewerDetailsLayout.setVerticalGroup(
            jpLeftViewerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftViewerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );

        jspLeftViewer.setRightComponent(jpLeftViewerDetails);

        javax.swing.GroupLayout jpLeftViewerLayout = new javax.swing.GroupLayout(jpLeftViewer);
        jpLeftViewer.setLayout(jpLeftViewerLayout);
        jpLeftViewerLayout.setHorizontalGroup(
            jpLeftViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
        );
        jpLeftViewerLayout.setVerticalGroup(
            jpLeftViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        jspViewerAndProperties.setLeftComponent(jpLeftViewer);

        jspProperties.setDividerLocation(20);
        jspProperties.setDividerSize(1);
        jspProperties.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpProperties.setMaximumSize(new java.awt.Dimension(32767, 25));
        jpProperties.setMinimumSize(new java.awt.Dimension(302, 25));
        jpProperties.setPreferredSize(new java.awt.Dimension(313, 25));

        jlProperties.setBackground(new java.awt.Color(255, 255, 255));
        jlProperties.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jlProperties.setForeground(new java.awt.Color(102, 102, 102));
        jlProperties.setText("Properties");

        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
        jpProperties.setLayout(jpPropertiesLayout);
        jpPropertiesLayout.setHorizontalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlProperties)
                .addContainerGap(339, Short.MAX_VALUE))
        );
        jpPropertiesLayout.setVerticalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPropertiesLayout.createSequentialGroup()
                .addComponent(jlProperties)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspProperties.setTopComponent(jpProperties);

        jpPropetiesTab.setBackground(new java.awt.Color(255, 255, 255));
        jpPropetiesTab.setForeground(new java.awt.Color(153, 153, 255));

        jtpProperties.setBackground(new java.awt.Color(255, 255, 255));

        jspMzML.setBackground(new java.awt.Color(255, 255, 255));
        jspMzML.setBorder(null);
        jspMzML.setDividerLocation(80);
        jspMzML.setDividerSize(1);
        jspMzML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzML.setColumns(20);
        jtaMzML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMzML.setRows(5);
        jspMzMLHeader.setViewportView(jtaMzML);

        jspMzML.setLeftComponent(jspMzMLHeader);

        jspMzMLDetail.setBorder(null);
        jspMzMLDetail.setDividerLocation(40);
        jspMzMLDetail.setDividerSize(1);
        jspMzMLDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtMzML.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Index", "ID", "MS", "Base peak m/z", "Base peak int", "RT (sec)", "RT (min)"
            }
        ));
        jtMzML.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtMzML.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMzMLMouseClicked(evt);
            }
        });
        jspMzMLSubDetail.setViewportView(jtMzML);

        jspMzMLDetail.setRightComponent(jspMzMLSubDetail);

        jpMzMLMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlScan.setText("Scan Index:");

        jtScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtScanKeyPressed(evt);
            }
        });

        jlRT.setText("RT (sec):");

        jtRT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtRTKeyPressed(evt);
            }
        });

        jlSearchMzML.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlSearchMzML.setText("Search:");

        javax.swing.GroupLayout jpMzMLMenuLayout = new javax.swing.GroupLayout(jpMzMLMenu);
        jpMzMLMenu.setLayout(jpMzMLMenuLayout);
        jpMzMLMenuLayout.setHorizontalGroup(
            jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlSearchMzML)
                .addGap(18, 18, 18)
                .addComponent(jlScan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtScan, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlRT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtRT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jpMzMLMenuLayout.setVerticalGroup(
            jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jlSearchMzML)
                    .addComponent(jlScan)
                    .addComponent(jtScan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlRT)
                    .addComponent(jtRT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspMzMLDetail.setLeftComponent(jpMzMLMenu);

        jspMzML.setBottomComponent(jspMzMLDetail);

        javax.swing.GroupLayout jpMzMLLayout = new javax.swing.GroupLayout(jpMzML);
        jpMzML.setLayout(jpMzMLLayout);
        jpMzMLLayout.setHorizontalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jpMzMLLayout.setVerticalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzML View", jpMzML);

        jspMGF.setBorder(null);
        jspMGF.setDividerLocation(80);
        jspMGF.setDividerSize(1);
        jspMGF.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMGFView.setColumns(20);
        jtaMGFView.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMGFView.setRows(5);
        jspMGFHeader.setViewportView(jtaMGFView);

        jspMGF.setTopComponent(jspMGFHeader);

        jspMGFDetail.setBorder(null);
        jspMGFDetail.setDividerLocation(50);
        jspMGFDetail.setDividerSize(1);
        jspMGFDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jlSearchMGF.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlSearchMGF.setText("Search:");

        jlScanTitle.setText("Scan title:");

        jtScanTitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtScanTitleKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpMGFMenuLayout = new javax.swing.GroupLayout(jpMGFMenu);
        jpMGFMenu.setLayout(jpMGFMenuLayout);
        jpMGFMenuLayout.setHorizontalGroup(
            jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMGFMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlSearchMGF)
                .addGap(18, 18, 18)
                .addComponent(jlScanTitle)
                .addGap(18, 18, 18)
                .addComponent(jtScanTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jpMGFMenuLayout.setVerticalGroup(
            jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMGFMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlSearchMGF)
                    .addComponent(jlScanTitle)
                    .addComponent(jtScanTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jspMGFDetail.setTopComponent(jpMGFMenu);

        jtMGF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Scan Title", "Peptide Mass", "Charge", "Reference Line"
            }
        ));
        jtMGF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMGFMouseClicked(evt);
            }
        });
        jspMGFSubDetail.setViewportView(jtMGF);

        jspMGFDetail.setRightComponent(jspMGFSubDetail);

        jspMGF.setRightComponent(jspMGFDetail);

        javax.swing.GroupLayout jpMGFLayout = new javax.swing.GroupLayout(jpMGF);
        jpMGF.setLayout(jpMGFLayout);
        jpMGFLayout.setHorizontalGroup(
            jpMGFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMGF, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jpMGFLayout.setVerticalGroup(
            jpMGFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMGF, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jtpProperties.addTab("MGF View", jpMGF);

        jspMzId.setBorder(null);
        jspMzId.setDividerLocation(80);
        jspMzId.setDividerSize(1);
        jspMzId.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jspMzIdDetail.setBorder(null);
        jspMzIdDetail.setDividerLocation(50);
        jspMzIdDetail.setDividerSize(1);
        jspMzIdDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jlSearchMzId.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlSearchMzId.setText("Search:");

        jlProteinMzId.setText("Protein:");

        jtProteinMzId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProteinMzIdKeyPressed(evt);
            }
        });

        jlPeptideMzId.setText("Peptide:");

        jtPeptideMzId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPeptideMzIdKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpMzIdMenuLayout = new javax.swing.GroupLayout(jpMzIdMenu);
        jpMzIdMenu.setLayout(jpMzIdMenuLayout);
        jpMzIdMenuLayout.setHorizontalGroup(
            jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlSearchMzId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlProteinMzId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtProteinMzId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlPeptideMzId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtPeptideMzId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jpMzIdMenuLayout.setVerticalGroup(
            jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlSearchMzId)
                    .addComponent(jlProteinMzId)
                    .addComponent(jtProteinMzId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlPeptideMzId)
                    .addComponent(jtPeptideMzId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jspMzIdDetail.setTopComponent(jpMzIdMenu);

        jtMzId.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Protein", "Peptide", "Rank", "Score", "Spectrum ID"
            }
        ));
        jpMzIdSubDetail.setViewportView(jtMzId);

        jspMzIdDetail.setRightComponent(jpMzIdSubDetail);

        jspMzId.setRightComponent(jspMzIdDetail);

        jtaMzIdView.setColumns(20);
        jtaMzIdView.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMzIdView.setRows(5);
        jspMzIdHeader.setViewportView(jtaMzIdView);

        jspMzId.setLeftComponent(jspMzIdHeader);

        javax.swing.GroupLayout jpMzIdLayout = new javax.swing.GroupLayout(jpMzId);
        jpMzId.setLayout(jpMzIdLayout);
        jpMzIdLayout.setHorizontalGroup(
            jpMzIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzId, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jpMzIdLayout.setVerticalGroup(
            jpMzIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzIdentML View", jpMzId);

        jspMascotXML.setBorder(null);
        jspMascotXML.setDividerLocation(80);
        jspMascotXML.setDividerSize(1);
        jspMascotXML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMascotXML.setColumns(20);
        jtaMascotXML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMascotXML.setRows(5);
        jspMascotXMLHeader.setViewportView(jtaMascotXML);

        jspMascotXML.setTopComponent(jspMascotXMLHeader);

        jspMascotXMLSubDetail.setBorder(null);
        jspMascotXMLSubDetail.setDividerLocation(80);
        jspMascotXMLSubDetail.setDividerSize(1);
        jspMascotXMLSubDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtMascotXMLView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Protein", "Peptide", "Composition", "Exp Mz", "Exp Mr", "Charge", "Score", "Scan", "Scan ID", "RT (sec)"
            }
        ));
        jspMascotXMLDetail.setViewportView(jtMascotXMLView);

        jspMascotXMLSubDetail.setRightComponent(jspMascotXMLDetail);

        jlSearchMascotXML.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlSearchMascotXML.setText("Search:");

        jlProtein.setText("Protein:");

        jtProtein.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtProteinKeyPressed(evt);
            }
        });

        jlPeptide.setText("Peptide:");

        jtPeptide.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPeptideKeyPressed(evt);
            }
        });

        jbShowIsotopeDistrib.setText("Show Isotopes");
        jbShowIsotopeDistrib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbShowIsotopeDistribActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpMascotXMLMenuLayout = new javax.swing.GroupLayout(jpMascotXMLMenu);
        jpMascotXMLMenu.setLayout(jpMascotXMLMenuLayout);
        jpMascotXMLMenuLayout.setHorizontalGroup(
            jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                        .addComponent(jlSearchMascotXML)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlProtein)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtProtein, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jlPeptide)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtPeptide, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                    .addComponent(jbShowIsotopeDistrib))
                .addContainerGap())
        );
        jpMascotXMLMenuLayout.setVerticalGroup(
            jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jbShowIsotopeDistrib)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPeptide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlSearchMascotXML)
                    .addComponent(jlProtein)
                    .addComponent(jtProtein, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlPeptide))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jspMascotXMLSubDetail.setLeftComponent(jpMascotXMLMenu);

        jspMascotXML.setRightComponent(jspMascotXMLSubDetail);

        javax.swing.GroupLayout jpMascotXMLLayout = new javax.swing.GroupLayout(jpMascotXML);
        jpMascotXML.setLayout(jpMascotXMLLayout);
        jpMascotXMLLayout.setHorizontalGroup(
            jpMascotXMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMascotXML, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jpMascotXMLLayout.setVerticalGroup(
            jpMascotXMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMascotXML, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jtpProperties.addTab("Mascot XML View", jpMascotXML);

        jspMzQuantML.setBorder(null);
        jspMzQuantML.setDividerLocation(40);
        jspMzQuantML.setDividerSize(1);
        jspMzQuantML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzQuantML.setColumns(20);
        jtaMzQuantML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMzQuantML.setRows(5);
        jspMzQuantMLHeader.setViewportView(jtaMzQuantML);

        jspMzQuantML.setTopComponent(jspMzQuantMLHeader);

        jspPeptideQuant.setBorder(null);
        jspPeptideQuant.setDividerLocation(40);
        jspPeptideQuant.setDividerSize(1);
        jspPeptideQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jpPeptideQuantHeaderLayout = new javax.swing.GroupLayout(jpPeptideQuantHeader);
        jpPeptideQuantHeader.setLayout(jpPeptideQuantHeaderLayout);
        jpPeptideQuantHeaderLayout.setHorizontalGroup(
            jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );
        jpPeptideQuantHeaderLayout.setVerticalGroup(
            jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        jspPeptideQuant.setTopComponent(jpPeptideQuantHeader);

        jtPeptideQuant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Peptide", "Label 1", "Label 2", "Label 3", "Label 4"
            }
        ));
        jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

        jspPeptideQuant.setRightComponent(jspPeptideQuantDetail);

        javax.swing.GroupLayout jpPeptideQuantLayout = new javax.swing.GroupLayout(jpPeptideQuant);
        jpPeptideQuant.setLayout(jpPeptideQuantLayout);
        jpPeptideQuantLayout.setHorizontalGroup(
            jpPeptideQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspPeptideQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        jpPeptideQuantLayout.setVerticalGroup(
            jpPeptideQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspPeptideQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );

        jtpMzQuantMLDetail.addTab("Peptide Quantitation", jpPeptideQuant);

        jspProteinQuant.setBorder(null);
        jspProteinQuant.setDividerLocation(40);
        jspProteinQuant.setDividerSize(1);
        jspProteinQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jpProteinQuantHeaderLayout = new javax.swing.GroupLayout(jpProteinQuantHeader);
        jpProteinQuantHeader.setLayout(jpProteinQuantHeaderLayout);
        jpProteinQuantHeaderLayout.setHorizontalGroup(
            jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );
        jpProteinQuantHeaderLayout.setVerticalGroup(
            jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        jspProteinQuant.setTopComponent(jpProteinQuantHeader);

        jtProteinQuant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Protein", "Label 1", "Label 2", "Label 3", "Label 4"
            }
        ));
        jspProteinQuantDetail.setViewportView(jtProteinQuant);

        jspProteinQuant.setRightComponent(jspProteinQuantDetail);

        javax.swing.GroupLayout jpProteinQuantLayout = new javax.swing.GroupLayout(jpProteinQuant);
        jpProteinQuant.setLayout(jpProteinQuantLayout);
        jpProteinQuantLayout.setHorizontalGroup(
            jpProteinQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProteinQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        jpProteinQuantLayout.setVerticalGroup(
            jpProteinQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProteinQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );

        jtpMzQuantMLDetail.addTab("Protein Quantitation", jpProteinQuant);

        jspFeatureQuant.setBorder(null);
        jspFeatureQuant.setDividerLocation(40);
        jspFeatureQuant.setDividerSize(1);
        jspFeatureQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jpFeatureQuantHeaderLayout = new javax.swing.GroupLayout(jpFeatureQuantHeader);
        jpFeatureQuantHeader.setLayout(jpFeatureQuantHeaderLayout);
        jpFeatureQuantHeaderLayout.setHorizontalGroup(
            jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );
        jpFeatureQuantHeaderLayout.setVerticalGroup(
            jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        jspFeatureQuant.setTopComponent(jpFeatureQuantHeader);

        jtFeatureQuant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Feature", "Label 1", "Label 2", "Label 3", "Label 4"
            }
        ));
        jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

        jspFeatureQuant.setRightComponent(jspFeatureQuantDetail);

        javax.swing.GroupLayout FeatureQuantLayout = new javax.swing.GroupLayout(FeatureQuant);
        FeatureQuant.setLayout(FeatureQuantLayout);
        FeatureQuantLayout.setHorizontalGroup(
            FeatureQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspFeatureQuant, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        FeatureQuantLayout.setVerticalGroup(
            FeatureQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspFeatureQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );

        jtpMzQuantMLDetail.addTab("Feature Quantitation", FeatureQuant);

        jspMzQuantML.setRightComponent(jtpMzQuantMLDetail);

        javax.swing.GroupLayout jpMzQuantMLLayout = new javax.swing.GroupLayout(jpMzQuantML);
        jpMzQuantML.setLayout(jpMzQuantMLLayout);
        jpMzQuantMLLayout.setHorizontalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzQuantML, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jpMzQuantMLLayout.setVerticalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzQuantML, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzQuantML View", jpMzQuantML);

        javax.swing.GroupLayout jpPropetiesTabLayout = new javax.swing.GroupLayout(jpPropetiesTab);
        jpPropetiesTab.setLayout(jpPropetiesTabLayout);
        jpPropetiesTabLayout.setHorizontalGroup(
            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
        );
        jpPropetiesTabLayout.setVerticalGroup(
            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );

        jspProperties.setRightComponent(jpPropetiesTab);

        javax.swing.GroupLayout jpPropertiesBoxLayout = new javax.swing.GroupLayout(jpPropertiesBox);
        jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
        jpPropertiesBoxLayout.setHorizontalGroup(
            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        );
        jpPropertiesBoxLayout.setVerticalGroup(
            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        jspViewerAndProperties.setRightComponent(jpPropertiesBox);

        javax.swing.GroupLayout jpViewerAndPropertiesLayout = new javax.swing.GroupLayout(jpViewerAndProperties);
        jpViewerAndProperties.setLayout(jpViewerAndPropertiesLayout);
        jpViewerAndPropertiesLayout.setHorizontalGroup(
            jpViewerAndPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspViewerAndProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        jpViewerAndPropertiesLayout.setVerticalGroup(
            jpViewerAndPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspViewerAndProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        jspMainPanelView.setRightComponent(jpViewerAndProperties);

        javax.swing.GroupLayout jpMainPanelViewLayout = new javax.swing.GroupLayout(jpMainPanelView);
        jpMainPanelView.setLayout(jpMainPanelViewLayout);
        jpMainPanelViewLayout.setHorizontalGroup(
            jpMainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMainPanelView)
        );
        jpMainPanelViewLayout.setVerticalGroup(
            jpMainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );

        jpProjectStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Project: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(102, 102, 102))); // NOI18N
        jpProjectStatus.setForeground(new java.awt.Color(153, 153, 153));
        jpProjectStatus.setPreferredSize(new java.awt.Dimension(102, 30));

        jlRawFiles.setText("1) Raw Files:");

        jlIdentFiles.setText("2) Identification Files:");

        jlTechnique.setText("3) Technique:");

        jlQuantFiles.setBackground(new java.awt.Color(255, 255, 255));
        jlQuantFiles.setText("4) Quant Files:");

        jlRawFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlRawFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

        jlIdentFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlIdentFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

        jlQuantFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlQuantFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

        jcbTechnique.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select technique", "SILAC", "iTRAQ", "15N", "Label free" }));
        jcbTechnique.setToolTipText("Select a technique for the analysis");
        jcbTechnique.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jcbOutputFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select format", "mzq", "CSV" }));

        javax.swing.GroupLayout jpProjectStatusLayout = new javax.swing.GroupLayout(jpProjectStatus);
        jpProjectStatus.setLayout(jpProjectStatusLayout);
        jpProjectStatusLayout.setHorizontalGroup(
            jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlRawFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlRawFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlIdentFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlIdentFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlTechnique)
                .addGap(18, 18, 18)
                .addComponent(jcbTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlQuantFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbOutputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jpProjectStatusLayout.setVerticalGroup(
            jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectStatusLayout.createSequentialGroup()
                .addGroup(jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlRawFiles)
                        .addComponent(jlRawFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlIdentFiles)
                        .addComponent(jlIdentFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlTechnique)
                        .addComponent(jcbTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlQuantFiles)
                        .addComponent(jcbOutputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jmFile.setText("File");

        jmNewProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jmNewProject.setText("New Project");
        jmNewProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmNewProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmNewProject);

        jmImportFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jmImportFile.setText("Import File");
        jmImportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmImportFileActionPerformed(evt);
            }
        });
        jmFile.add(jmImportFile);
        jmFile.add(jSeparator1);

        jmOpenProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jmOpenProject.setText("Open Project");
        jmOpenProject.setEnabled(false);
        jmOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmOpenProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmOpenProject);

        jmOpenRecentProject.setText("Open Recent Project");
        jmOpenRecentProject.setEnabled(false);
        jmOpenRecentProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmOpenRecentProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmOpenRecentProject);

        jmCloseProject.setText("Close Project");
        jmCloseProject.setEnabled(false);
        jmCloseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCloseProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmCloseProject);
        jmFile.add(jSeparator3);

        jmSaveProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmSaveProject.setText("Save Project");
        jmSaveProject.setEnabled(false);
        jmSaveProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSaveProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmSaveProject);

        jmSaveProjectAs.setText("Save Project As");
        jmSaveProjectAs.setEnabled(false);
        jmSaveProjectAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSaveProjectAsActionPerformed(evt);
            }
        });
        jmFile.add(jmSaveProjectAs);
        jmFile.add(jSeparator2);

        jmPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jmPrint.setText("Print");
        jmPrint.setEnabled(false);
        jmFile.add(jmPrint);
        jmFile.add(jSeparator4);

        jmExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jmExit.setText("Exit");
        jmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmExitActionPerformed(evt);
            }
        });
        jmFile.add(jmExit);

        jmMain.add(jmFile);

        jmEdit.setText("Edit");

        jmCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jmCut.setText("Cut");
        jmCut.setEnabled(false);
        jmEdit.add(jmCut);

        jmCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jmCopy.setText("Copy");
        jmCopy.setEnabled(false);
        jmEdit.add(jmCopy);

        jmPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jmPaste.setText("Paste");
        jmPaste.setEnabled(false);
        jmEdit.add(jmPaste);
        jmEdit.add(jSeparator6);

        jmMain.add(jmEdit);

        jmView.setText("View");

        jmShowProjectFiles.setText("Show/Hide Project Files");
        jmShowProjectFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowProjectFilesActionPerformed(evt);
            }
        });
        jmView.add(jmShowProjectFiles);

        jmShowViewer.setText("Show/Hide Viewer");
        jmShowViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowViewerActionPerformed(evt);
            }
        });
        jmView.add(jmShowViewer);

        jmShowProperties.setText("Show/Hide Properties");
        jmShowProperties.setEnabled(false);
        jmShowProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowPropertiesActionPerformed(evt);
            }
        });
        jmView.add(jmShowProperties);

        jmMain.add(jmView);

        jmProject.setText("Project");

        jmEditIdent.setText("Set Identification Parameters");
        jmEditIdent.setEnabled(false);
        jmEditIdent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmEditIdentActionPerformed(evt);
            }
        });
        jmProject.add(jmEditIdent);

        jmEditQuant.setText("Set Quantitation Parameters");
        jmEditQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmEditQuantActionPerformed(evt);
            }
        });
        jmProject.add(jmEditQuant);

        jmMain.add(jmProject);

        jmAnalyze.setText("Analyze");

        jmRunQuantAnalysis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jmRunQuantAnalysis.setText("Run Quantition Analysis");
        jmRunQuantAnalysis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRunQuantAnalysisActionPerformed(evt);
            }
        });
        jmAnalyze.add(jmRunQuantAnalysis);

        jmMain.add(jmAnalyze);

        jmTools.setText("Tools");

        jmConverters.setText("Converters");

        jmMzML2MGF.setText("mzML to MGF");
        jmMzML2MGF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMzML2MGFActionPerformed(evt);
            }
        });
        jmConverters.add(jmMzML2MGF);

        jmTools.add(jmConverters);

        jmCustomize.setText("Customize");
        jmCustomize.setEnabled(false);
        jmTools.add(jmCustomize);

        jmOptions.setText("Options");
        jmOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmOptionsActionPerformed(evt);
            }
        });
        jmTools.add(jmOptions);

        jmMain.add(jmTools);

        jmDatabases.setText("Databases");

        jmSubmitPRIDE.setText("Submit to PRIDE");
        jmSubmitPRIDE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSubmitPRIDEActionPerformed(evt);
            }
        });
        jmDatabases.add(jmSubmitPRIDE);

        jmMain.add(jmDatabases);

        jmWindow.setText("Window");

        jmSpectrum.setText("Spectrum");
        jmSpectrum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSpectrumActionPerformed(evt);
            }
        });
        jmWindow.add(jmSpectrum);

        jmTIC.setText("TIC");
        jmTIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmTICActionPerformed(evt);
            }
        });
        jmWindow.add(jmTIC);

        jm2DView.setText("2D View");
        jm2DView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm2DViewActionPerformed(evt);
            }
        });
        jmWindow.add(jm2DView);

        jm3DView.setText("3D View");
        jm3DView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm3DViewActionPerformed(evt);
            }
        });
        jmWindow.add(jm3DView);
        jmWindow.add(jSeparator8);

        jmMzMLView.setText("mzML View");
        jmMzMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMzMLViewActionPerformed(evt);
            }
        });
        jmWindow.add(jmMzMLView);

        jmMGFView.setText("MGF View");
        jmMGFView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMGFViewActionPerformed(evt);
            }
        });
        jmWindow.add(jmMGFView);

        jmMzIdentMLView.setText("mzIdentML View");
        jmMzIdentMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMzIdentMLViewActionPerformed(evt);
            }
        });
        jmWindow.add(jmMzIdentMLView);

        jmMascotXMLView.setText("Mascot XML View");
        jmMascotXMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMascotXMLViewActionPerformed(evt);
            }
        });
        jmWindow.add(jmMascotXMLView);

        jmMzQuantMLView.setText("mzQuantML View");
        jmMzQuantMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMzQuantMLViewActionPerformed(evt);
            }
        });
        jmWindow.add(jmMzQuantMLView);
        jmWindow.add(jSeparator9);

        jmLog.setText("Log");
        jmLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmLogActionPerformed(evt);
            }
        });
        jmWindow.add(jmLog);

        jmRawData.setText("Raw Data");
        jmRawData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRawDataActionPerformed(evt);
            }
        });
        jmWindow.add(jmRawData);

        jmMain.add(jmWindow);

        jmHelp.setText("Help");

        jmHelpContent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jmHelpContent.setText("ProteoSuite Help");
        jmHelpContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmHelpContentActionPerformed(evt);
            }
        });
        jmHelp.add(jmHelpContent);
        jmHelp.add(jSeparator5);

        jmContactUs.setText("Contact us");
        jmContactUs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmContactUsActionPerformed(evt);
            }
        });
        jmHelp.add(jmContactUs);

        jMenuItem2.setText("Check for updates");
        jMenuItem2.setEnabled(false);
        jmHelp.add(jMenuItem2);
        jmHelp.add(jSeparator7);

        jmAbout.setText("About ProteoSuite");
        jmAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmAboutActionPerformed(evt);
            }
        });
        jmHelp.add(jmAbout);

        jmMain.add(jmHelp);

        setJMenuBar(jmMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpProjectStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
                    .addComponent(jpToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpProjectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jpMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
        //... Exiting from proteosuite ...//
        int iExit = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exiting from ProteoSuite", JOptionPane.YES_NO_OPTION);
        if (iExit == JOptionPane.YES_OPTION) 
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jmExitActionPerformed

    private void jmNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNewProjectActionPerformed
        
    }//GEN-LAST:event_jmNewProjectActionPerformed
   
    private void jmImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmImportFileActionPerformed
                
        //... Selecting file(s) ...//
        JFileChooser chooser = new JFileChooser("user.home");
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Raw Files (*.mzML, *.mzML.gz, *.mgf)", "mzML", "gz", "mgf");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Identification Files (*.mzid, *.mzid.gz, *.xml)", "mzid", "gz", "xml");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq, *.mzq.gz)", "mzq");

        //... Filters must be in descending order ...//
        chooser.setFileFilter(filter3);
        chooser.setFileFilter(filter2);
        chooser.setFileFilter(filter1);

        //... Enable multiple file selection ...//
        chooser.setMultiSelectionEnabled(true);

        //... Setting default directory ...//
        chooser.setCurrentDirectory(new java.io.File(this.sWorkspace)); //... If not found it goes to Home, exception not needed ...//
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            bProjectModified = true;
            
            //... A common experiment will have around 4-20 raw files ....//
            final File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0)
            {
                
//---------------//
//  Read mzML    //
//---------------//                
                if ((aFiles[0].getName().indexOf(".mzML") > 0)||(aFiles[0].getName().indexOf(".mzML.gz") > 0))
                {                                                                                                  
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
                    model.addColumn("Scans");   
                    
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("LoadingThread"){
                        @Override
                        public void run(){                            
                            long lTime = System.currentTimeMillis();
                            String sTimeUncompress = "";
                            String sTimeUnmarshalling = "";                            
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                File xmlFile = new File(aFiles[iI].getPath());
                                //... Uncompress .gz files ...//
                                if (aFiles[0].getName().indexOf(".mzML.gz") > 0)
                                {
                                    try{                                                                            
                                        progressBarDialog.setTitle("Uncompressing " + xmlFile.getName());
                                        progressBarDialog.setVisible(true);
                                
                                        File outFile = null;
                                        FileOutputStream fos = null;

                                        GZIPInputStream gin = new GZIPInputStream(new FileInputStream(xmlFile));
                                        outFile = new File(aFiles[0].getParent(), aFiles[0].getName().replaceAll("\\.gz$", ""));
                                        fos = new FileOutputStream(outFile);
                                        byte[] buf = new byte[100000];
                                        int len;
                                        while ((len = gin.read(buf)) > 0) {
                                            fos.write(buf, 0, len);
                                        }
                                        fos.close();      
                                        xmlFile = outFile;
                                        lTime = System.currentTimeMillis()-lTime;
                                        sTimeUncompress = "Uncompressing ends after " + (lTime/1000) + " secs, ";            
                                        lTime = System.currentTimeMillis();
                                    }
                                    catch(IOException ioe){
                                          System.out.println("Exception has been thrown" + ioe);
                                    }                                    
                                }
                                progressBarDialog.setTitle("Reading " + xmlFile.getName());
                                progressBarDialog.setVisible(true);
                                //... Unmarshall data using jzmzML API ...//
                                unmarshalMzMLFile(model, xmlFile);
                            } //... For files ...//
                            lTime = System.currentTimeMillis()-lTime;
                            sTimeUnmarshalling = "Unmarshalling ends after " + (lTime/1000) + " secs";
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                            jtaLog.setText("Raw files imported successfully! " + sTimeUncompress + sTimeUnmarshalling);
                        }
                    }.start();  
                    
                    //... Project status pipeline ...//
                    Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));
                    jlRawFilesStatus.setIcon(loadRawFilesIcon);                    
                } //... From reading mzML files ...//
//---------------//
//  Read MGF     //
//---------------//
                
                if (aFiles[0].getName().indexOf(".mgf") > 0) 
                {                    
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
    
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading MGF files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("LoadingThread"){
                        @Override
                        public void run(){
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), aFiles[iI].getPath().toString().replace("\\", "/"),
                                                                                          "MGF",
                                                                                          "N/A"});
                            } //... For files ...//    
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();                                     
                    
                    //... Project status pipeline ...//
                    Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));
                    jlRawFilesStatus.setIcon(loadRawFilesIcon);
                    jtaLog.setText("Raw files imported successfully!");
                } //... From reading mzML files ...//                
//-------------------//
//  Read Mascot XML  //
//-------------------//
                if (aFiles[0].getName().indexOf(".xml") >0) 
                {                    
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtIdentFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading Mascot XML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("LoadingThread"){
                        @Override
                        public void run(){
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), aFiles[iI].getPath().toString().replace("\\", "/"), "mascot_xml", "N/A"});
                                loadMascotView(aFiles[iI].getName(), aFiles[iI].getPath().toString().replace("\\", "/"));
                            } //... For files ...// 
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();                                                           

                    //... Project status pipeline ...//
                    Icon loadIdentFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));
                    jlIdentFilesStatus.setIcon(loadIdentFilesIcon);         
                    jtaLog.setText("Identification files imported successfully!");
                }                
//-------------------//
//  Read mzIdentML   //
//-------------------//
                if (aFiles[0].getName().indexOf(".mzid") >0) 
                {   
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtIdentFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzIdentML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("LoadingThread"){
                        @Override
                        public void run(){                            
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                File xmlFile = new File(aFiles[iI].getPath());
                                //... Uncompress .gz files ...//
                                if (aFiles[0].getName().indexOf(".mzML.gz") > 0)
                                {
                                    try{                                                                            
                                        progressBarDialog.setTitle("Uncompressing " + xmlFile.getName());
                                        progressBarDialog.setVisible(true);
                                
                                        File outFile = null;
                                        FileOutputStream fos = null;

                                        GZIPInputStream gin = new GZIPInputStream(new FileInputStream(xmlFile));
                                        outFile = new File(aFiles[0].getParent(), aFiles[0].getName().replaceAll("\\.gz$", ""));
                                        fos = new FileOutputStream(outFile);
                                        byte[] buf = new byte[100000];
                                        int len;
                                        while ((len = gin.read(buf)) > 0) {
                                            fos.write(buf, 0, len);
                                        }
                                        fos.close();      
                                        xmlFile = outFile;
                                    }
                                    catch(IOException ioe){
                                          System.out.println("Exception has been thrown" + ioe);
                                    }                                    
                                }                                                               
                                
                                progressBarDialog.setTitle("Reading " + xmlFile.getName());
                                progressBarDialog.setVisible(true);
                                //... Unmarshall data using jzmzML API ...//
                                unmarshalMzIDFile(model, iI, xmlFile);
                            } //... For files ...// 
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();

                    //... Project status pipeline ...//
                    Icon loadIdentFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));
                    jlIdentFilesStatus.setIcon(loadIdentFilesIcon);         
                    jtaLog.setText("Identification files imported successfully!");
                }          
//-------------------//
//  Read mzQuantML   //
//-------------------//
                if (aFiles[0].getName().indexOf(".mzq") >0) 
                {
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtQuantFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzQuantML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("LoadingThread"){
                        @Override
                        public void run(){                            
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                File xmlFile = new File(aFiles[iI].getPath());
                                progressBarDialog.setTitle("Reading " + xmlFile.getName());
                                progressBarDialog.setVisible(true);
                                //... Unmarshall data using jzmzML API ...//
                                unmarshalMzQMLFile(model, iI, xmlFile);
                            } //... For files ...// 
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();
                    jtaLog.setText("Quantitation files imported successfully!");
                }
	    } //... From Files            
        }  //... From If                
    }//GEN-LAST:event_jmImportFileActionPerformed

    private void jmOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenProjectActionPerformed
                
    }//GEN-LAST:event_jmOpenProjectActionPerformed

    private void jmOpenRecentProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenRecentProjectActionPerformed
        
        
    }//GEN-LAST:event_jmOpenRecentProjectActionPerformed

    private void jmSaveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectActionPerformed
        
    }//GEN-LAST:event_jmSaveProjectActionPerformed

    private void jmSaveProjectAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectAsActionPerformed
        
    }//GEN-LAST:event_jmSaveProjectAsActionPerformed

    private void jmCloseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCloseProjectActionPerformed

    }//GEN-LAST:event_jmCloseProjectActionPerformed

    private void jbNewProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbNewProjectMouseClicked
        this.jmNewProjectActionPerformed(null);
    }//GEN-LAST:event_jbNewProjectMouseClicked

    private void jbImportFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbImportFileMouseClicked
        
        this.jmImportFileActionPerformed(null);
    }//GEN-LAST:event_jbImportFileMouseClicked

    private void jbOpenProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbOpenProjectMouseClicked
        
        this.jmOpenProjectActionPerformed(null);        
    }//GEN-LAST:event_jbOpenProjectMouseClicked

    private void jbSaveProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSaveProjectMouseClicked
        
        this.jmSaveProjectActionPerformed(null);
    }//GEN-LAST:event_jbSaveProjectMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        this.jmExitActionPerformed(null);
    }//GEN-LAST:event_formWindowClosed

    private void jmShowProjectFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowProjectFilesActionPerformed
        if (jspMainPanelView.getDividerLocation() <= 5)
        {
            jspMainPanelView.setDividerLocation(250); 
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));            
            jmShowProjectFiles.setIcon(thick);
        }
        else
        {
            jspMainPanelView.setDividerLocation(0);            
            jmShowProjectFiles.setIcon(null);
        }
    }//GEN-LAST:event_jmShowProjectFilesActionPerformed

    private void jmShowViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowViewerActionPerformed
        if (jspViewerAndProperties.getDividerLocation() <= 5)
        {
            jspViewerAndProperties.setDividerLocation(600); 
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
            jmShowViewer.setIcon(thick);
        }
        else
        {
            jspViewerAndProperties.setDividerLocation(0); 
            jmShowViewer.setIcon(null);
        }
    }//GEN-LAST:event_jmShowViewerActionPerformed

    private void jmShowPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowPropertiesActionPerformed
        
    }//GEN-LAST:event_jmShowPropertiesActionPerformed

    private void jmAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmAboutActionPerformed
        About about = new About();
        final JFrame jfAbout = new JFrame("About ProteoSuite");
        jfAbout.setResizable(false);
        jfAbout.setSize(400, 300);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfAbout.dispose();
            }
        };        
        jfAbout.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfAbout.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfAbout.getSize().width / 2;
        int y2 = jfAbout.getSize().height / 2;        
        jfAbout.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfAbout.setIconImage(iconApp);
        jfAbout.setAlwaysOnTop(true);
       
        jfAbout.add(about);
        jfAbout.pack();
        jfAbout.setVisible(true);       
    }//GEN-LAST:event_jmAboutActionPerformed

    private void jtMzMLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMzMLMouseClicked
        if (evt.getButton()== 1)
        {                        
            showSpectrum(jtRawFiles.getSelectedRow(), jtMzML.getValueAt(jtMzML.getSelectedRow(), 2).toString());
            showRawData(jtRawFiles.getSelectedRow(), jtMzML.getValueAt(jtMzML.getSelectedRow(), 2).toString());
            
            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(jtRawFiles.getSelectedRow());
            
            //... Check if mzML contains MS1 data ...//
            Set<String> chromats = unmarshaller.getChromatogramIDs();
            if(!chromats.isEmpty())
            {
                show2DPlot(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
            }
            jtpViewer.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jtMzMLMouseClicked

    private void jtRawFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtRawFilesMouseClicked
        if ((evt.getButton() == 1)&&(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2).toString().equals("mzML"))) {
            loadMzMLView(jtRawFiles.getSelectedRow());
            showChromatogram(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
        }
        if ((evt.getButton() == 1)&&(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2).toString().equals("MGF"))) {                        
            loadMGFView(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 0).toString(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_jtRawFilesMouseClicked

    private void jmMzML2MGFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzML2MGFActionPerformed
        
        //... Load MzML2MGF GUI ...//
        final JFrame jfWinParams = new JFrame("Convert mzML files to MGF");        
        MzML2MGFView winParams = new MzML2MGFView(jfWinParams, this.sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(500, 450);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
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
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
       
        jfWinParams.add(winParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);
    }//GEN-LAST:event_jmMzML2MGFActionPerformed

    private void jmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOptionsActionPerformed
        ProjectParamsView panelParams = new ProjectParamsView();
        final JFrame jfWinParams = new JFrame("Options");
        jfWinParams.setResizable(false);
        jfWinParams.setSize(580, 370);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
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
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
       
        jfWinParams.add(panelParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);    
    }//GEN-LAST:event_jmOptionsActionPerformed

    private void jmSubmitPRIDEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSubmitPRIDEActionPerformed
        JOptionPane.showMessageDialog(this, "The module for submitting data into PRIDE is under development.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jmSubmitPRIDEActionPerformed

    private void jmEditIdentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEditIdentActionPerformed
        IdentParamsView identParams = new IdentParamsView();
        final JFrame jfIdentParams = new JFrame("Edit Identification Parameters");
        jfIdentParams.setResizable(false);
        jfIdentParams.setSize(638, 585);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfIdentParams.dispose();
            }
        };        
        jfIdentParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfIdentParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfIdentParams.getSize().width / 2;
        int y2 = jfIdentParams.getSize().height / 2;        
        jfIdentParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfIdentParams.setIconImage(iconApp);
        jfIdentParams.setAlwaysOnTop(true);
       
        jfIdentParams.add(identParams);
        jfIdentParams.pack();
        jfIdentParams.setVisible(true);
    }//GEN-LAST:event_jmEditIdentActionPerformed

    private void jmEditQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEditQuantActionPerformed
        QuantParamsView quantParams = new QuantParamsView();
        final JFrame jfQuantParams = new JFrame("Edit Quantitation Parameters");
        jfQuantParams.setResizable(false);
        jfQuantParams.setSize(638, 585);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfQuantParams.dispose();
            }
        };        
        jfQuantParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfQuantParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfQuantParams.getSize().width / 2;
        int y2 = jfQuantParams.getSize().height / 2;        
        jfQuantParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfQuantParams.setIconImage(iconApp);
        jfQuantParams.setAlwaysOnTop(true);
       
        jfQuantParams.add(quantParams);
        jfQuantParams.pack();
        jfQuantParams.setVisible(true);        
    }//GEN-LAST:event_jmEditQuantActionPerformed
    /*
     * Description: This method allows the user to read our online documentation of proteosuite consisting of tutorials and user manuals.     
     */
    private void jmHelpContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHelpContentActionPerformed
	OpenURL url = new OpenURL("http://www.proteosuite.org/?q=tutorials");
    }//GEN-LAST:event_jmHelpContentActionPerformed

    private void jbRunQuantAnalysisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRunQuantAnalysisMouseClicked
        //... Call the quantitation method ...//
        jmRunQuantAnalysisActionPerformed(null);
    }//GEN-LAST:event_jbRunQuantAnalysisMouseClicked

    private void jmRunQuantAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRunQuantAnalysisActionPerformed
        //... Validate if we got some raw and identification files ...//
        if (jtRawFiles.getRowCount()<=0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one raw file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else if (jtIdentFiles.getRowCount()<=0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one identification file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (jcbTechnique.getSelectedItem().equals("Select technique"))
        {
            JOptionPane.showMessageDialog(this, "Please specify the technique used in your pipeline (e.g. iTRAQ, SILAC, 15N, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (jcbOutputFormat.getSelectedItem().equals("Select format"))
        {
            JOptionPane.showMessageDialog(this, "Please specify the output format (e.g. .mzq, .csv, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            //... In this thread, we call xTracker module (parameter files are generated using proteosuite) ...//
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    //... Progress Bar ...//
                    progressBarDialog.setTitle("Running Quantitation Analysis via xTracker");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();
            new Thread("LoadingThread"){
                @Override
                public void run(){
                    if (jcbTechnique.getSelectedItem().toString().equals("Label free")) //... Label free will be performed in proteosuite ...//
                    {                                                
                        progressBarDialog.setVisible(false);
                        progressBarDialog.dispose();        
                        JOptionPane.showMessageDialog(null, "The Label Free Pipeline is under development. Please stay tuned for new releases.", "Information", JOptionPane.INFORMATION_MESSAGE);                        
                        
                        //... THIS IS BEING DONE IN COLLABORATION WITH ANDREW DOWSEY, DON'T DELETE ...//
//                        final JPanel run = new JPanel();                        
//                        JLabel jLabel1 = new JLabel("Please specify the parameters to perform the label free method.");
//                        JLabel jLabel2 = new JLabel("Scan Window:");
//                        JLabel jLabel3 = new JLabel("To:");
//                        JTextField jTextField1 = new JTextField("");
//                        JTextField jTextField2 = new JTextField("");
//                        GridLayout layout=new GridLayout(5,1);
//                        jTextField1.requestFocusInWindow();                        
//                        run.setLayout(layout);
//                        run.add(jLabel1);
//                        run.add(jLabel2);
//                        run.add(jTextField1);
//                        run.add(jLabel3);
//                        run.add(jTextField2);
//                        int iOption = JOptionPane.showConfirmDialog(null, run, "Edit Quantitation Parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                        if (iOption == JOptionPane.OK_OPTION) 
//                        {                        
//                            generateTemplate(Integer.parseInt(jTextField1.getText()), Integer.parseInt(jTextField2.getText()));
//                        }
                    }
                    else
                    {
                        if (jcbTechnique.getSelectedItem().toString().equals("iTRAQ")) //... Label free will be performed in proteosuite ...//
                        {    
                                //... Generate config files for xTracker ...//
                                writeXTrackerConfigFiles();
                                //... Run xTracker ...///
                                System.out.println("****** xTRACKER *****");
                                xTracker run = new xTracker(sWorkspace + "/" + sProjectName);
                                progressBarDialog.setVisible(false);
                                progressBarDialog.dispose();
                                JOptionPane.showMessageDialog(null, "Process finished. Results files can be found in the \"Quantitation Files\" section.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
                                setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: " + sWorkspace + " - " + sProjectName +  ">         http://www.proteosuite.org");
                        }
                        else
                        {
                                progressBarDialog.setVisible(false);
                                progressBarDialog.dispose();                                
                                JOptionPane.showMessageDialog(null, "SILAC and 15N are under development. Please stay tuned for new releases.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }.start();                    
        }       
    }//GEN-LAST:event_jmRunQuantAnalysisActionPerformed

    private void jmSpectrumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSpectrumActionPerformed
        jtpViewer.setSelectedIndex(0);        
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(thick);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(null);
        jm3DView.setIcon(null); 
    }//GEN-LAST:event_jmSpectrumActionPerformed

    private void jmTICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmTICActionPerformed
        jtpViewer.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(thick);
        jm2DView.setIcon(null);
        jm3DView.setIcon(null);         
    }//GEN-LAST:event_jmTICActionPerformed

    private void jm2DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm2DViewActionPerformed
        jtpViewer.setSelectedIndex(2);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(thick);
        jm3DView.setIcon(null);
    }//GEN-LAST:event_jm2DViewActionPerformed

    private void jm3DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm3DViewActionPerformed
        jtpViewer.setSelectedIndex(3);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(null);
        jm3DView.setIcon(thick);
    }//GEN-LAST:event_jm3DViewActionPerformed

    private void jmMzMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzMLViewActionPerformed
        jtpProperties.setSelectedIndex(0);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(thick);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);
    }//GEN-LAST:event_jmMzMLViewActionPerformed

    private void jmMzQuantMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzQuantMLViewActionPerformed
        jtpProperties.setSelectedIndex(4);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(thick);
    }//GEN-LAST:event_jmMzQuantMLViewActionPerformed

    private void jmLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmLogActionPerformed
        jtpLog.setSelectedIndex(0);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmLog.setIcon(thick);
        jmRawData.setIcon(null);
    }//GEN-LAST:event_jmLogActionPerformed

    private void jmRawDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRawDataActionPerformed
        jtpLog.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmLog.setIcon(null);
        jmRawData.setIcon(thick);
    }//GEN-LAST:event_jmRawDataActionPerformed

    private void jmMascotXMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMascotXMLViewActionPerformed
        jtpProperties.setSelectedIndex(3);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(thick);
        jmMzQuantMLView.setIcon(null);
    }//GEN-LAST:event_jmMascotXMLViewActionPerformed

    private void jtIdentFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtIdentFilesMouseClicked
        if ((evt.getButton() == 1)&&(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2).toString().equals("Mascot XML file (.xml)"))) {            
            loadMascotView(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 0).toString(), jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 1).toString());
        }        
        if ((evt.getButton() == 1)&&(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2).toString().equals("mzid"))) {            
            loadMzIdentMLView();
        }                
    }//GEN-LAST:event_jtIdentFilesMouseClicked

    private void jtScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtScanKeyPressed
        String sChain = "";
        sChain = jtScan.getText() + evt.getKeyChar();
        searchValueInMzML(sChain, 1);
    }//GEN-LAST:event_jtScanKeyPressed

    private void jtRTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtRTKeyPressed
        String sChain = "";
        sChain = jtRT.getText() + evt.getKeyChar();
        searchValueInMzML(sChain, 6);        
    }//GEN-LAST:event_jtRTKeyPressed

    private void jtProteinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProteinKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtProtein.getText() + evt.getKeyChar();
            sChain = sChain.toUpperCase();
            searchValueInMascotXML(sChain, 1); 
        }
    }//GEN-LAST:event_jtProteinKeyPressed

    private void jtPeptideKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPeptideKeyPressed
       
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtPeptide.getText() + evt.getKeyChar();            
            sChain = sChain.toUpperCase();
            searchValueInMascotXML(sChain, 2); 
        }
    }//GEN-LAST:event_jtPeptideKeyPressed

    private void jbShowIsotopeDistribActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbShowIsotopeDistribActionPerformed
        if (jtMascotXMLView.getSelectedRow() < 0)
        {
            JOptionPane.showMessageDialog(this, "Please select a valid identification record.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            new ViewChartGUI(jtMascotXMLView.getValueAt(jtMascotXMLView.getSelectedRow(), 2).toString(), 
                        jtMascotXMLView.getValueAt(jtMascotXMLView.getSelectedRow(), 6).toString(),
                        "10000").setVisible(true);            
        }
    }//GEN-LAST:event_jbShowIsotopeDistribActionPerformed

    private void jtMSMzKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMSMzKeyPressed
        String sChain = "";         
        sChain = "" + evt.getKeyChar();          
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");         
        Matcher m = p.matcher(sChain);         
        if (m.find()) 
        {             
            sChain = jtMSMz.getText() + evt.getKeyChar();             
            searchValueInRawData(sChain, 1);         
        }
    }//GEN-LAST:event_jtMSMzKeyPressed

    private void jtMSIndexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMSIndexKeyPressed
        String sChain = "";         
        sChain = "" + evt.getKeyChar();          
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");         
        Matcher m = p.matcher(sChain);         
        if (m.find()) 
        {             
            sChain = jtMSIndex.getText() + evt.getKeyChar();             
            searchValueInRawData(sChain, 0);         
        }
    }//GEN-LAST:event_jtMSIndexKeyPressed

    private void jmContactUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmContactUsActionPerformed
        OpenURL url = new OpenURL("http://www.proteosuite.org/?q=contact");
    }//GEN-LAST:event_jmContactUsActionPerformed

    private void jmMGFViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMGFViewActionPerformed
        jtpProperties.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(thick);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);        
    }//GEN-LAST:event_jmMGFViewActionPerformed

    private void jmMzIdentMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzIdentMLViewActionPerformed
        jtpProperties.setSelectedIndex(2);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(thick);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);        
    }//GEN-LAST:event_jmMzIdentMLViewActionPerformed

    private void jtMGFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMGFMouseClicked
        if (evt.getButton()== 1)
        {                        
            showRawDataMGF(jtRawFiles.getSelectedRow(), jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString());
            jtpViewer.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jtMGFMouseClicked

    private void jtScanTitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtScanTitleKeyPressed
        String sChain = "";
        sChain = jtScanTitle.getText() + evt.getKeyChar();
        searchValueInMGF(sChain, 1);
    }//GEN-LAST:event_jtScanTitleKeyPressed

    private void jtProteinMzIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProteinMzIdKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtProteinMzId.getText() + evt.getKeyChar();
            sChain = sChain.toUpperCase();
            searchValueInIdentML(sChain, 1); 
        }
    }//GEN-LAST:event_jtProteinMzIdKeyPressed

    private void jtPeptideMzIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPeptideMzIdKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtPeptideMzId.getText() + evt.getKeyChar();            
            sChain = sChain.toUpperCase();
            searchValueInIdentML(sChain, 2); 
        }        
    }//GEN-LAST:event_jtPeptideMzIdKeyPressed

    private void jtQuantFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtQuantFilesMouseClicked
        if ((evt.getButton() == 1)&&(jtQuantFiles.getValueAt(jtQuantFiles.getSelectedRow(), 2).toString().equals("mzq"))) {            
            loadMzQuantMLView();
        }   
    }//GEN-LAST:event_jtQuantFilesMouseClicked
    private void generateTemplate(int scanIndex1, int scanIndex2)     
    {
        int iFileIndex = 0; //... Index to mzML raw data ...//
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
                
        //... 1) Select MS/MS identifications that are specified in the parameter window (e.g. From scan 1671 to 1671)  ...//
        Map<String, ArrayList<String>> hmPeptides = new HashMap<String, ArrayList<String>>();
        int iCountPeptides = 0; //... Peptide counter ...//
        boolean blnExists = false; 
        for(int iI=0; iI<jtMascotXMLView.getRowCount(); iI++)
        {
            //... Range based on scan number ...//
            if ((scanIndex1<=Integer.parseInt(jtMascotXMLView.getValueAt(iI, 8).toString()))&&(scanIndex2>=Integer.parseInt(jtMascotXMLView.getValueAt(iI, 8).toString())))
            {
                ArrayList al = new ArrayList();                         
                al.add(Integer.toString(iI));                           //... Index in the grid ...//                
                al.add(jtMascotXMLView.getValueAt(iI, 3).toString());   //... 2) Peptide molecular composition which was calculated previously ...//
                al.add(jtMascotXMLView.getValueAt(iI, 8).toString());   //... Scan index ...//
                al.add(jtMascotXMLView.getValueAt(iI, 9).toString());   //... Scan ID (jmzml API only supports getElementBy(scanID) ...//
                al.add(jtMascotXMLView.getValueAt(iI, 10).toString());  //... Retention time ...//
                //... Check if peptide has been added previously ...//
                blnExists = hmPeptides.containsKey(jtMascotXMLView.getValueAt(iI, 2).toString());
                if (blnExists == false)
                {
                    hmPeptides.put(jtMascotXMLView.getValueAt(iI, 2).toString(), al); //... This eliminates peptides which have been identified more than once (e.g. for different proteins) ...//
                    iCountPeptides++; //... Total peptides (identifications) in the scan range ...//
                }
            }
        }
        if (iCountPeptides > 0)
        {            
            System.out.println("Peptides in range = " + iCountPeptides);

            final int RESOLUTION = 4; //... Number of peaks to retrieve (width of the template, e.g. 4 left, 4 right) ...//
            final int MAX_ISOTOPES = 6; //... Number of isotopes estimated by IPC ...//
            final int NUMBER_PEAKS = RESOLUTION*2+1; //... Number of peaks for each isotope ...//

            System.out.println("RESOLUTION="+RESOLUTION);
            System.out.println("MAX_ISOTOPES="+MAX_ISOTOPES);
            System.out.println("NUMBER_PEAKS="+NUMBER_PEAKS);

            int iTemp2Index = 0; //... Index for template 2 ...//
            int iCountTemp1 = 0; //... Counter for template 1 ...//

            int[] aPeakIndexes = new int[RESOLUTION*2+1];
            psTemplate[] aTemplate2 = new psTemplate[iCountPeptides];
            psTemplateQuant[] aTemplate1 = new psTemplateQuant[iCountPeptides*NUMBER_PEAKS];

            //... Initialize arrays (Template 1 and Template 2) ...//S
            for(int iI=0; iI<aTemplate2.length; iI++)
            {
                aTemplate2[iI] = new psTemplate();
            }
            for(int iI=0; iI<aTemplate1.length; iI++)
            {
                aTemplate1[iI] = new psTemplateQuant();            
            }

            //... For each peptide, calculate the isotopic pattern distribution ...//
            String sScanID = "";        
            int iScanIndex = 0;
            for (Map.Entry<String, ArrayList<String>> entry : hmPeptides.entrySet())
            {
                System.out.println("Calculating the Isotopic Patter Distribution (IPD) for peptide " + entry.getKey());

                //... Specify parameters ...//
                ArrayList<String> saParams = entry.getValue();
                Object saArray[] = saParams.toArray();
                Iterator<String> itr = saParams.iterator();
                while (itr.hasNext()) //... Show the parameters in the array list (index, molecular composition, scanNumber, scanID, rt) ...//
                {
                    System.out.println("Param = " + itr.next().toString() + "; "); 
                }
                //... 3) Using IPC to calculate the isotopic pattern distribution ...//
                String[] args = {};
                args = new String[]{"-a", entry.getKey().toString(), 
                    "-fc", "-z", jtMascotXMLView.getValueAt(Integer.parseInt(saArray[0].toString()), 6).toString(), 
                    "-f", "1000", "-r", "10000"};
                if (args.length == 0) {
                    System.exit(0);
                }
                IPC ipc = new IPC();                 
                Options options = Options.parseArgs(args);
                options.setPrintOutput(false);
                Results res = ipc.execute(options);
                Object[] objArray = res.getPeaks().toArray();     
                float[] aMz = new float[objArray.length];           //... There will be six isotopes estimated using the IPC class ...//
                float[] aRelIntens = new float[objArray.length];     
                System.out.println("==== IPD ====");
                for(int iI=0; iI<objArray.length; iI++)
                {
                    aMz[iI] = Float.parseFloat(String.format("%.4f", res.getPeaks().first().getMass()));
                    aRelIntens[iI] = Float.parseFloat(String.format("%.4f", res.getPeaks().first().getRelInt()*100));        
                    System.out.println(aMz[iI] + "\t" + aRelIntens[iI]);
                    res.getPeaks().pollFirst();
                }

                //... 4) Generate templates for seaMass by getting the m/z indexes for each isotope ...//                                                
                MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
                try{
                    //... 4.1) Get precursor ion ...//
                    sScanID = saArray[3].toString();
                    iScanIndex = Integer.parseInt(saArray[2].toString());
                    Spectrum spectrum = unmarshaller.getSpectrumById(sScanID);
                    PrecursorList plist = spectrum.getPrecursorList(); //... Get precursor ion ...//
                    if (plist != null)
                    {                    
                        if (plist.getCount().intValue() == 1)
                        {
                            Spectrum precursor = unmarshaller.getSpectrumById(plist.getPrecursor().get(0).getSpectrumRef());

                            //... Get binary raw data from the precursor ion ...//
                            List<BinaryDataArray> bdal = precursor.getBinaryDataArrayList().getBinaryDataArray();

                            //... Reading m/z Values (Peaks) ...//
                            BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
                            Number[] mzValues = mzBinaryDataArray.getBinaryDataAsNumberArray();

                            //... Perform a binary search to find the m/z index based on the m/z values estimated from the IPC ...//
                            int iPos = -1, iPosPrev = -1;
                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[0]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);  //... Get n=9 values (4 left and 4 right) from the array ...//    

                            //... Generate psTemplate (index, {x, y, i}) ...//
                            aTemplate2[iTemp2Index].setIndex(iTemp2Index);
                            aTemplate2[iTemp2Index].setCoords(0, aRelIntens[0], 0);

                            //... Generate psTemplateQuant (mzIndex, scanIndex, quant, temp2Index) ...//
                            for (int iI=0; iI<NUMBER_PEAKS; iI++)
                            {
                                aTemplate1[iI+iCountTemp1].setTemplate(aPeakIndexes[iI], plist.getPrecursor().get(0).getSpectrumRef().toString(), iScanIndex, 1, iTemp2Index);
                            }
                            iCountTemp1+=NUMBER_PEAKS;
                            iPosPrev = iPos;                       

                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[1]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);
                            aTemplate2[iTemp2Index].setCoords(iPos - iPosPrev, aRelIntens[1], 1);                

                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[2]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);
                            aTemplate2[iTemp2Index].setCoords(iPos - iPosPrev, aRelIntens[2], 2);

                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[3]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);
                            aTemplate2[iTemp2Index].setCoords(iPos - iPosPrev, aRelIntens[3], 3);

                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[4]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);
                            aTemplate2[iTemp2Index].setCoords(iPos - iPosPrev, aRelIntens[4], 4);

                            iPos = binarySearch(mzValues, 0, mzValues.length, aMz[5]);
                            aPeakIndexes = getPeaks(mzValues, iPos, RESOLUTION);
                            aTemplate2[iTemp2Index].setCoords(iPos - iPosPrev, aRelIntens[5], 5);                           
                            iTemp2Index++;
                        }                    
                    }  //... If precursor ion ...//
                    jtpLog.setSelectedIndex(2);
                }
                catch(MzMLUnmarshallerException e){
                    System.out.println(e);
                }            
            } //... From For ...//
            
            //... Perform normalisation ...//
            float fSum=0.0f;
            float fNewValue=0.0f;
            for(int iI=0; iI<aTemplate2.length; iI++)
            {
                for (int iJ=0; iJ<aTemplate2[iI].getCoords().length; iJ++)
                {                                
                    fSum+=Math.pow(aTemplate2[iI].getCoord(iJ).getRelIntensity(), 2.0);  
                }
                for (int iJ=0; iJ<aTemplate2[iI].getCoords().length; iJ++)
                {                                
                    fNewValue = aTemplate2[iI].getCoord(iJ).getRelIntensity()/fSum;  
                    aTemplate2[iI].setCoords(aTemplate2[iI].getCoord(iJ).getX(), aTemplate2[iI].getCoord(iJ).getY(), fNewValue, iJ);
                }                
            }            
            
            //... Generate the synthetic array which will be used by seaMass to calculate the quantitation values ...//
            Map<String, float[]> hmSyntheticArray = new HashMap<String, float[]>();
            int iScanCounter = 0;
            for(int iI=0; iI<aTemplate1.length; iI++)
            {
                blnExists = hmPeptides.containsKey(aTemplate1[iI].getScanID());
                if (blnExists == false)
                {
                    //... Determine the length of the spectra ...//
                    int iMAX=0;
                    MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
                    try{
                        Spectrum precursor = unmarshaller.getSpectrumById(aTemplate1[iI].getScanID());
                        List<BinaryDataArray> bdal = precursor.getBinaryDataArrayList().getBinaryDataArray();    
                        iMAX = bdal.size();
                    }
                    catch(MzMLUnmarshallerException e){
                        System.out.println(e);
                    }                     
                            
                    float[] al = new float[iMAX];   
                    
                    //... Using template 2 to 
                    aTemplate1[iI].getTemplate2Index(); 
                    
                    al[aTemplate1[iI].getMzIndex()]=aTemplate1[iI].getQuantIntensities(); 
                    hmSyntheticArray.put(aTemplate1[iI].getScanID(), al); 
                    iScanCounter++; 
                }
                else //... Update array ...//
                {
                    
                }                                        
            }
            
            //... Populate the Grids ...//
            for(int iI=0; iI<aTemplate1.length; iI++)
            {
                model.insertRow(model.getRowCount(), new Object[]{aTemplate1[iI].getMzIndex(),
                aTemplate1[iI].getScanID(),
                aTemplate1[iI].getQuantIntensities(),
                aTemplate1[iI].getTemplate2Index()
                });                            
            }
            for(int iI=0; iI<aTemplate2.length; iI++)
            {                            
                for (int iJ=0; iJ<aTemplate2[iI].getCoords().length; iJ++)
                {                                
                    model2.insertRow(model2.getRowCount(), new Object[]{aTemplate2[iI].getIndex(),
                    aTemplate2[iI].getCoord(iJ).getX(),
                    aTemplate2[iI].getCoord(iJ).getY(),
                    aTemplate2[iI].getCoord(iJ).getRelIntensity()
                    });  
                }                           
            }    
            
         
            
        } //... From if countPeptides ...//        
    }    
    public static int[] getPeaks(Number[] nArray, int iIndex, int iOffset)
    {
        int[] aPeaks = new int[iOffset*2+1];
        int iCount = 0;
        if ((iIndex-iOffset > 0)&&(iIndex+iOffset < nArray.length))
        {
            for (int iI=iIndex-iOffset; iI<=iIndex+iOffset; iI++)
            {
                aPeaks[iCount] = iI;
                iCount++;
                System.out.println("Index="+iI+"\t"+nArray[iI].floatValue());
            }
        }
        return aPeaks;
    }    
    public static int binarySearch(Number[] nArray, int iLowerBound, int iUpperBound, float fKey)
    {
        int iPos;
        int iCompCount = 1;    

        iPos = (iLowerBound + iUpperBound) / 2;
        while((Round(nArray[iPos].floatValue(), 4) != Round(fKey,4)) && (iLowerBound <= iUpperBound))
        {
             iCompCount++;
             if (Round(nArray[iPos].floatValue(), 4) > Round(fKey,4))             
             {                                              
                iUpperBound = iPos - 1;  
             }                                                            
             else                                                  
             {                                                       
                iLowerBound = iPos + 1;   
             }
             iPos = (iLowerBound + iUpperBound) / 2;
        }
        System.out.println("Searching for m/z = " + Round(fKey,4) + " in array");
        if (iLowerBound <= iUpperBound)
        {
            System.out.println("The number was found in array at position " + iPos);
            System.out.println("The binary search found the number after " + iCompCount + " comparisons.");
        }
        else
        {
            System.out.println("Sorry, the number is not in this array. The binary search made " + iCompCount  + " comparisons."); 
            System.out.println("The closest indexes were iLowerBound=" + iLowerBound + " and iUpperBound=" + iUpperBound); 
            iPos = iLowerBound;
            if (Math.abs(fKey-nArray[iPos].floatValue())>Math.abs(fKey-nArray[iPos-1].floatValue()))
            {
                iPos = iPos - 1;
            }   
            System.out.println("The pointer was set up to Index=" + iPos); 
        }
        return iPos;
    }     
    public static float Round(float Rval, int Rpl) {
        float p = (float)Math.pow(10,Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float)tmp/p;
    }
    private void searchValueInRawData(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtRawData.getModel();
        int nRow = dtm.getRowCount();
        for (int iI = 0 ; iI < nRow ; iI++)
        {                
            if (dtm.getValueAt(iI,iColumn).toString().startsWith(sChain))
            {
                jtRawData.setRowSelectionInterval(iI, iI);
                jtRawData.scrollRectToVisible(new Rectangle(jtRawData.getCellRect(iI, 0, true)));
                break;
            }
        }
    }   
    private void searchValueInMGF(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMGF.getModel();
        int nRow = dtm.getRowCount();
        for (int iI=0; iI < nRow ; iI++)
        {                
            if (dtm.getValueAt(iI,iColumn).toString().trim().startsWith(sChain))
            {
                jtMGF.setRowSelectionInterval(iI, iI);
                jtMGF.scrollRectToVisible(new Rectangle(jtMGF.getCellRect(iI, 0, true)));
                break;
            }
        }
    }    
    private void searchValueInMzML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMzML.getModel();
        int nRow = dtm.getRowCount();
        for (int iI=0; iI < nRow ; iI++)
        {                
            if (dtm.getValueAt(iI,iColumn).toString().trim().startsWith(sChain))
            {
                jtMzML.setRowSelectionInterval(iI, iI);
                jtMzML.scrollRectToVisible(new Rectangle(jtMzML.getCellRect(iI, 0, true)));
                break;
            }
        }
    }
    private void searchValueInIdentML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMzId.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++)
        {
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain))
            {
                jtMzId.setRowSelectionInterval(iI, iI);
                jtMzId.scrollRectToVisible(new Rectangle(jtMzId.getCellRect(iI, 0, true)));
                break;
            }
        }
    }     
    private void searchValueInMascotXML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMascotXMLView.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++)
        {
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain))
            {
                jtMascotXMLView.setRowSelectionInterval(iI, iI);
                jtMascotXMLView.scrollRectToVisible(new Rectangle(jtMascotXMLView.getCellRect(iI, 0, true)));
                break;
            }
        }
    }    
    private void showSpectrum(int iIndex, String sID) {                                             
                  
        jtpLog.setSelectedIndex(0);
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);            
        try
        {
           Spectrum spectrum = unmarshaller.getSpectrumById(sID);
           List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

           //... Reading mz Values ...//           
           BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
           Number[] mzNumbers = mzBinaryDataArray.getBinaryDataAsNumberArray();
           double[] mz = new double[mzNumbers.length];
           for (int iI = 0; iI < mzNumbers.length; iI++)
           {
               mz[iI] = mzNumbers[iI].doubleValue();
           }

           //... Reading Intensities ...//
           BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
           Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
           double[] intensities = new double[intenNumbers.length];
           for (int iI = 0; iI < intenNumbers.length; iI++)
           {
               intensities[iI] = intenNumbers[iI].doubleValue();
           }
           jdpMS.removeAll();
           JInternalFrame jifViewSpectrum = getInternalFrame();            
           JPanel specpanel = new SpectrumPanel(mz, intensities, 0.0, "", sID, 50, false, true, true, true, 1);           
           jifViewSpectrum.setTitle("MS spectrum <" + sID + ">");
           specpanel.setSize(new java.awt.Dimension(600, 400));
           specpanel.setPreferredSize(new java.awt.Dimension(600, 400));
           jifViewSpectrum.add(specpanel);
           jdpMS.add(jifViewSpectrum);
           jdpMS.revalidate();
           jdpMS.repaint();
        }
        catch (MzMLUnmarshallerException ume)
        {
            System.out.println(ume.getMessage());
        }
    }    
    private void showRawDataMGF(int iIndex, String sID) {                                             
                
        DefaultTableModel model = new DefaultTableModel();
        jtRawData.setModel(model);
        model.addColumn("Index");
        model.addColumn("m/z");
        model.addColumn("Intensity");
        
        //... Reading file ...//
        try{
            BufferedReader in = new BufferedReader(new FileReader(jtRawFiles.getValueAt(iIndex, 1).toString()));
            long refLine=Long.parseLong(sID);
            String string = null;
            
            long lineNum = 0; 
            int iCount = 0;
            while ((string = in.readLine()) != null) {
                lineNum++;
                string = string.trim();
                if (lineNum==refLine) {
                    break;
                }
            }                     
            while ((string = in.readLine()) != null) {               
                if (string.charAt(0) >= '1' && string.charAt(0) <= '9') {
                    String[] split = string.split("\\s+");
                    double mz = Double.parseDouble(split[0]);
                    double intensity = Double.parseDouble(split[1]);
                    iCount++;
                    
                   model.insertRow(model.getRowCount(), new Object[]{
                        iCount,
                        mz,
                        intensity
                        });
                    continue;
                }
                if (string.startsWith("END IONS"))
                {
                    break;
                }
            }
        }catch(Exception e){
            System.exit(1);
        }
        
        jtpLog.setSelectedIndex(1);
    }
    private void showRawData(int iIndex, String sID) {                                             
                
        DefaultTableModel model = new DefaultTableModel();
        jtRawData.setModel(model);
        model.addColumn("Index");
        model.addColumn("m/z");
        model.addColumn("Intensity");
        
        jtpLog.setSelectedIndex(1);
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);            
        try
        {
           Spectrum spectrum = unmarshaller.getSpectrumById(sID);
           List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

           //... Reading mz Values ...//
           BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
           Number[] mzNumbers = mzBinaryDataArray.getBinaryDataAsNumberArray();
           //... Reading Intensities ...//
           BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
           Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
           
           double dSumIntensities = 0.0;
           
           for (int iI = 0; iI < mzNumbers.length; iI++)
           {
               //if (intenNumbers[iI].doubleValue() > 0)
               //{
                   model.insertRow(model.getRowCount(), new Object[]{
                        iI,
                        Float.parseFloat(String.format("%.4f", mzNumbers[iI].doubleValue())),
                        Float.parseFloat(String.format("%.2f", intenNumbers[iI].doubleValue()))
                        });      
                    dSumIntensities = dSumIntensities + intenNumbers[iI].doubleValue();
               //}
           }               
//            model.insertRow(model.getRowCount(), new Object[]{
//                "",
//                "Sum intensities",
//                String.format("%.1f", dSumIntensities)
//                });       
//           RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
//            
//           jtRawData.setRowSorter(sorter);            
        }
        catch (MzMLUnmarshallerException ume)
        {
            System.out.println(ume.getMessage());
        }
    }         
    private JInternalFrame getInternalFrame(){ 
        JInternalFrame internalFrame = new JInternalFrame("");  
        Icon icon = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif"));
        
        internalFrame.setLocation(0,0);
        internalFrame.setSize(new java.awt.Dimension(620, 420));  
        internalFrame.setPreferredSize(new java.awt.Dimension(620, 420));  
        internalFrame.setFrameIcon(icon);
        internalFrame.setResizable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setClosable(true);
        internalFrame.setIconifiable(true);        
        internalFrame.setVisible(true);  
        return internalFrame;
    }
    private void showChromatogram(int iIndex, String sTitle) {                                             
        
            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);
            try
            {
               //... Check if mzML contains MS1 data ...//
               Set<String> chromats = unmarshaller.getChromatogramIDs();
               if(!chromats.isEmpty())
               {
                   Chromatogram chromatogram = unmarshaller.getChromatogramById("TIC");

                   List<BinaryDataArray> bdal = chromatogram.getBinaryDataArrayList().getBinaryDataArray();

                   //... Reading mz Values ...//
                   BinaryDataArray rtBinaryDataArray = (BinaryDataArray) bdal.get(0);
                   Number[] rtNumbers = rtBinaryDataArray.getBinaryDataAsNumberArray();
                   double[] rt = new double[rtNumbers.length];
                   for (int iI = 0; iI < rtNumbers.length; iI++)
                   {
                       rt[iI] = rtNumbers[iI].doubleValue();
                   }

                   //... Reading Intensities ...//
                   BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
                   Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
                   double[] intensities = new double[intenNumbers.length];
                   for (int iI = 0; iI < intenNumbers.length; iI++)
                   {
                       intensities[iI] = intenNumbers[iI].doubleValue();
                   }
                   jdpTIC.removeAll();
                   JInternalFrame jifViewChromatogram = getInternalFrame();            
                   ChromatogramPanel panel = new ChromatogramPanel(rt, intensities, "RT (mins)", "Intensity (counts)");
                   jifViewChromatogram.setTitle("Chromatogram <" + sTitle + ">");
                   panel.setSize(new java.awt.Dimension(600, 400));
                   panel.setPreferredSize(new java.awt.Dimension(600, 400));
                   jifViewChromatogram.add(panel);
                   jdpTIC.add(jifViewChromatogram);
                   jdpTIC.revalidate();
                   jdpTIC.repaint();
               }
            }
            catch (MzMLUnmarshallerException ume)
            {
                System.out.println(ume.getMessage());
            }
    }         
    private void show2DPlot(int iIndex, String sTitle) {                                             
       
            double[] mz = new double[1000000];
            double[] intensities = new double[1000000];
            double[] art = new double[1000000];

            double rt = 0;
            int iCounter = 0;

            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);            

            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            int iK = 0;
            String unitRT = "";
            while ((spectrumIterator.hasNext())&&(iK<1000))
            //while (spectrumIterator.hasNext())
            {
                Spectrum spectrumobj = spectrumIterator.next();
                String spectrumid = spectrumobj.getId();
                try
                {
                   Spectrum spectrum = unmarshaller.getSpectrumById(spectrumid);

                   List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                   for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();)
                   {
                       CVParam lCVParam = (CVParam) lCVParamIterator.next();
                       if (lCVParam.getAccession().equals("MS:1000016"))
                       {
                           rt = Double.parseDouble(lCVParam.getValue().trim());
                           
                           unitRT = lCVParam.getUnitAccession().trim();
                           if (unitRT.equals("UO:0000031"))
                           {
                               rt = Float.parseFloat(lCVParam.getValue().trim());
                               rt = rt * 60;
                           }
                           else
                           {
                               rt = Float.parseFloat(lCVParam.getValue().trim());
                           }                           
                       }
                   }

                   //... Binary data ...//
                   List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

                   //... Reading mz Values ...//
                   BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
                   BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
                   Number[] mzNumbers = mzBinaryDataArray.getBinaryDataAsNumberArray();
                   Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();

                   int iI=0;
                   while ((iI < mzNumbers.length)&&(iI < 1000))
                   {
                       mz[iCounter] = mzNumbers[iI].doubleValue();                                              
                       intensities[iCounter] = intenNumbers[iI].doubleValue();
                       art[iCounter] = rt;

                       iCounter++;
                       iI++;
                   }
                }
                catch (MzMLUnmarshallerException ume)
                {
                    System.out.println(ume.getMessage());
                }
                iK++;
            }
            TwoDPlot demo2 = new TwoDPlot(sTitle, mz, intensities, art);
            jp2D.add(demo2);
            demo2.pack();
            demo2.setVisible(true);
            
//            jdp2D.removeAll();
//            JInternalFrame jifView2D = getInternalFrame();            
//            TwoDPlot panel = new TwoDPlot(sTitle, mz, intensities, art);
//            panel.setSize(new java.awt.Dimension(600, 400));
//            panel.setPreferredSize(new java.awt.Dimension(600, 400));
//            jifView2D.add(panel);
//            jdp2D.add(jifView2D);
//            jdp2D.revalidate();
//            jdp2D.repaint();
    } 
    private boolean saveProject() 
    {
        JFileChooser chooser = new JFileChooser("user.home");
        String sFileName = "";
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ProteoSuite Project (*.psx)", "psx");
        chooser.setFileFilter(filter);

        //... Setting default directory ...//
        chooser.setCurrentDirectory(new java.io.File(this.sWorkspace)); //... If not found it goes to Home, exception handle not needed ...//        
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showSaveDialog(this);        
        
        //... Write file ...//
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            sFileName = file.toString();
            if (file.exists())
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to overwrite this project?", "Confirm Overwrite", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    this.sProjectName = sFileName;
                    writeConfigFile(sFileName);
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                writeConfigFile(sFileName);
                return true;
            }
        }
        else
        {
            return false;
        }
        
    }        
    private void writeConfigFile(String sFileName) 
    {
        if (sFileName.indexOf(".psx") <= 0)
        {
            sFileName = sFileName + ".psx";
        }
        try{

            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<ProteoSuiteProject xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
            out.write("xsi:schemaLocation=\"ProteoSuite.xsd\" name=\"" + this.sProjectName + "\" workspace=\"" + this.sWorkspace + "\">");
            out.newLine();
            out.write(" <configSettings>");
            out.newLine();
            out.write("     <rawDataSettings>");
            out.newLine();
            for (int iI=0; iI<jtRawFiles.getRowCount(); iI++)
            {
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
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    private void loadMzIdentMLView() 
    {                            
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading values");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadingThread"){
            @Override
            public void run(){                    
                DefaultTableModel model = new DefaultTableModel();
                jtMzId.setModel(model);
                model.addColumn("Index");
                model.addColumn("Protein");
                model.addColumn("Peptide");
                model.addColumn("Rank");
                model.addColumn("Score");        
                model.addColumn("Spectrum ID");  
                String sOutput="";

                MzIdentMLUnmarshaller unmarshaller = aMzIDUnmarshaller.get(jtIdentFiles.getSelectedRow());

                //... File Name and Version ...//
                sOutput = "File Name:\t\t" + unmarshaller.getMzIdentMLId() + "\n";
                sOutput = sOutput + "mzML Version:\t" + unmarshaller.getMzIdentMLVersion() + "\n";

                //... File Content ...//
                sOutput = sOutput + "Analysis Software:\n";
                AnalysisSoftwareList softwareList = unmarshaller.unmarshal(MzIdentMLElement.AnalysisSoftwareList);
                if(softwareList!=null){
                    for(AnalysisSoftware software : softwareList.getAnalysisSoftware()){
                        uk.ac.ebi.jmzidml.model.mzidml.CvParam cvSoftware = software.getSoftwareName().getCvParam();                                                 
                        sOutput = sOutput + "\t\t" + cvSoftware.getName().trim()+"\n";                            
                    }
                }
                int iCount=0;
                AnalysisData analysisData = unmarshaller.unmarshal(MzIdentMLElement.AnalysisData);
                List<SpectrumIdentificationList> silList = analysisData.getSpectrumIdentificationList();
                for (SpectrumIdentificationList sil : silList) {
                    List<SpectrumIdentificationResult> sirList = sil.getSpectrumIdentificationResult();
                    for (SpectrumIdentificationResult sir : sirList) {
                        SpectrumIdentificationItem selected = null;
                        List<SpectrumIdentificationItem> siiList = sir.getSpectrumIdentificationItem();
                        for (SpectrumIdentificationItem sii : siiList) {
                                selected = sii;
                                iCount++;
                                Peptide peptide = selected.getPeptide();
                                if (peptide != null) {
                                    List<PeptideEvidenceRef> pepRefList = selected.getPeptideEvidenceRef();
                                    PeptideEvidence pe = pepRefList.get(0).getPeptideEvidence();
                                    DBSequence dbs = pe.getDBSequence();                                    
                                    model.insertRow(model.getRowCount(), new Object[]{
                                        iCount,
                                        dbs.getAccession().replace("|", "-"),
                                        peptide.getPeptideSequence(), 
                                        sii.getRank(),
                                        sii.getCvParam().get(0).getValue(),
                                        sir.getSpectrumID()
                                        });
                                }
                        }
                    }
                }
                
                jtaMzIdView.setText(sOutput);
                jtpProperties.setSelectedIndex(2);
                
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();                
    }       
    private void loadMzQuantMLView() 
    {                            
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading values");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadingThread"){
            @Override
            public void run(){                    
                jtpProperties.setSelectedIndex(4);
                DefaultTableModel model = new DefaultTableModel();
                DefaultTableModel model2 = new DefaultTableModel();
                DefaultTableModel model3 = new DefaultTableModel();
                jtProteinQuant.setModel(model);
                jtPeptideQuant.setModel(model2);
                jtFeatureQuant.setModel(model3);
                model.addColumn("Protein");
                model2.addColumn("Peptide");
                model3.addColumn("Feature");
                MzQuantMLUnmarshaller unmarshaller = aMzQMLUnmarshaller.get(jtQuantFiles.getSelectedRow());
                mzQuantML = unmarshaller.unmarshall();
                
//============================//
//... Protein Quantitation ...//                
//============================//                
                //... Based on the the assay list and study variables we will include the different columns ...//                
                List<Assay> assayList = mzQuantML.getAssayList().getAssay();
                int iLabels = 0;
                for(Assay assay:assayList){
                        model.addColumn(assay.getName());
                        iLabels++;
                }
                List<StudyVariable> studyList = mzQuantML.getStudyVariableList().getStudyVariable();
                int iStudyVars = 0;
                for(StudyVariable study:studyList){
                        model.addColumn(study.getId());
                        iStudyVars++;
                }                               
                //... Fill rows ...//           
                Map<String, ArrayList<String>> hmProtein = new HashMap<String, ArrayList<String>>();

                //... Getting DataMatrix from AssayQuantLayer ...//
                List<Row> dataMatrix = mzQuantML.getProteinList().getAssayQuantLayer().get(0).getDataMatrix().getRow();
                for(Row row:dataMatrix){
                    Protein prot = (Protein) row.getObjectRef();
                    List<String> values = row.getValue();       
                    ArrayList al = (ArrayList) values;
                    hmProtein.put(prot.getId(), al);        
                }
                //... Getting DataMatrix from StudyVariableQuantLayer ...//
                List<Row> dataMatrix2 = mzQuantML.getProteinList().getStudyVariableQuantLayer().get(0).getDataMatrix().getRow();
                for(Row row:dataMatrix2){
                    Protein prot = (Protein) row.getObjectRef();
                    List<String> values = row.getValue();       
                    ArrayList al = (ArrayList) values;
                    ArrayList al2 = hmProtein.get(prot.getId());
                    for(Object obj:al)
                    {
                        al2.add(obj);
                    }
                    hmProtein.put(prot.getId(), al2);
                }
                for (Map.Entry<String, ArrayList<String>> entry : hmProtein.entrySet())
                {                    
                    Object[] aObj = new Object[iLabels+iStudyVars+1];                    
                    aObj[0] = entry.getKey();
                    
                    ArrayList<String> saParams = entry.getValue();
                    Iterator<String> itr = saParams.iterator();
                    int iI = 1;
                    while (itr.hasNext())
                    {
                        aObj[iI] = itr.next().toString(); 
                        iI++;
                    }
                    model.insertRow(model.getRowCount(), aObj);
                }
                
//============================//
//... Peptide Quantitation ...//                
//============================//
                //... Based on the the assay list and study variables we will include the different columns ...//                
                for(Assay assay:assayList){
                        model2.addColumn(assay.getName());
                }                
                //... Fill rows ...//           
                
                //... Getting DataMatrix from AssayQuantLayer ...//
                List<Row> dataMatrix3 = mzQuantML.getPeptideConsensusList().get(0).getAssayQuantLayer().get(0).getDataMatrix().getRow();
                for(Row row:dataMatrix3){
                    Object[] aObj = new Object[iLabels+1];                                                            
                    PeptideConsensus pepConsensus = (PeptideConsensus) row.getObjectRef();
                    
                    aObj[0] = pepConsensus.getId();                    
                    List<String> values = row.getValue();       
                    ArrayList al = (ArrayList) values;
                    Iterator<String> itr = al.iterator();
                    int iI = 1;
                    while (itr.hasNext()) 
                    {
                        aObj[iI] = itr.next().toString(); 
                        iI++;
                    }
                    model2.insertRow(model2.getRowCount(), aObj);
                }                                
                 
//============================//
//... Feature Quantitation ...//                
//============================//
                //... Based on the the assay list and study variables we will include the different columns ...//
                for(Assay assay:assayList){
                        model3.addColumn(assay.getName());
                }
                //... Fill rows ...//
                
                //... Getting DataMatrix from AssayQuantLayer ...//
                List<Row> dataMatrix4 = mzQuantML.getFeatureList().get(0).getMS2AssayQuantLayer().get(0).getDataMatrix().getRow();
                for(Row row:dataMatrix4){
                    Object[] aObj = new Object[iLabels+1];
                    Feature feature = (Feature) row.getObjectRef();
                    
                    aObj[0] = feature.getId();
                    List<String> values = row.getValue();
                    ArrayList al = (ArrayList) values;
                    Iterator<String> itr = al.iterator();
                    int iI = 1;
                    while (itr.hasNext()) 
                    {
                        aObj[iI] = itr.next().toString(); 
                        iI++;
                    }
                    model3.insertRow(model3.getRowCount(), aObj);
                }
                String sOutput="";

                //... File Name and Version ...//
                sOutput = "File Name:\t\t" + jtQuantFiles.getValueAt(jtQuantFiles.getSelectedRow(), 0) + "\n";
                sOutput = sOutput + "mzML Version:\t" + mzQuantML.getVersion() + "\n";
                
                jtaMzQuantML.setText(sOutput);
                jtpProperties.setSelectedIndex(4);
                
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();                
    }   
    private void loadMzMLView(int iIndex) 
    {                            
        final int iIndexRef = iIndex;
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading values");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadingThread"){
            @Override
            public void run(){
                    
                DefaultTableModel model = new DefaultTableModel();
                jtMzML.setModel(model);
                model.addColumn("");
                model.addColumn("Index");
                model.addColumn("ID");
                model.addColumn("MS");
                model.addColumn("Base peak m/z");        
                model.addColumn("Base peak int");       
                model.addColumn("RT (sec)");
                model.addColumn("RT (min)");
                String sOutput="";

                MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndexRef);

                //... File Name and Version ...//
                sOutput = "File Name:\t\t" + unmarshaller.getMzMLId() + "\n";
                sOutput = sOutput + "mzML Version:\t" + unmarshaller.getMzMLVersion() + "\n";

                //... File Content ...//
                sOutput = sOutput + "File Content:\n";
                FileDescription fdList = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);
                List<CVParam> fileContent = fdList.getFileContent().getCvParam();
                for (Iterator lCVParamIterator = fileContent.iterator(); lCVParamIterator.hasNext();)
                {
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    sOutput = sOutput + "\t\t" + lCVParam.getName().trim()+"\n";
                } 
                
                //... Source File ...//
                sOutput = sOutput + "Source File:\n";
                List<CVParam> sourceParam = fdList.getSourceFileList().getSourceFile().get(0).getCvParam();
                for (Iterator lCVParamIterator = sourceParam.iterator(); lCVParamIterator.hasNext();)
                {
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    sOutput = sOutput + "\t\t" + lCVParam.getName().trim()+"\n";
                } 
                
                sOutput = sOutput + "Software:\n";                
                uk.ac.ebi.jmzml.model.mzml.SoftwareList softList = unmarshaller.unmarshalFromXpath("/softwareList", uk.ac.ebi.jmzml.model.mzml.SoftwareList.class);
                List<CVParam> softParam = softList.getSoftware().get(0).getCvParam();
                for (Iterator lCVParamIterator = softParam.iterator(); lCVParamIterator.hasNext();)
                {
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    sOutput = sOutput + "\t\t" + lCVParam.getName().trim() + "\n";
                }
                jtaMzML.setText(sOutput);
                jtpProperties.setSelectedIndex(0);

                //... Reading spectrum data ...//
                MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
                String msLevel = "";
                float basePeakMZ = 0;
                float basePeakInt = 0;
                String unitRT = "";
                float rt = 0;
                float rtMin = 0;
                int iCount = 1;
                double dScansMS1 = 0;
                double dScansMS2 = 0;
                while (spectrumIterator.hasNext())
                {
                    Spectrum spectrum = spectrumIterator.next();

                    List<CVParam> specParam = spectrum.getCvParam();
                    for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();)
                    {
                       CVParam lCVParam = (CVParam) lCVParamIterator.next();
                       if (lCVParam.getAccession().equals("MS:1000511")) 
                       {
                           msLevel = lCVParam.getValue().trim();
                           if (msLevel.equals("1"))
                           {
                               dScansMS1++;
                           }
                           else
                           {
                               dScansMS2++;
                           }
                       }
                       if (lCVParam.getAccession().equals("MS:1000504")) 
                       {
                           basePeakMZ = Float.parseFloat(lCVParam.getValue().trim());
                       }
                       if (lCVParam.getAccession().equals("MS:1000505")) 
                       {
                           basePeakInt = Float.parseFloat(lCVParam.getValue().trim());
                       }
                    }
                    List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                    for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();)
                    {
                        CVParam lCVParam = (CVParam) lCVParamIterator.next();
                        if (lCVParam.getAccession().equals("MS:1000016"))
                        {
                            unitRT = lCVParam.getUnitAccession().trim();
                            if (unitRT.equals("UO:0000031"))
                            {
                                rt = Float.parseFloat(lCVParam.getValue().trim());
                                rtMin = rt;
                                rt = rt * 60;
                            }
                            else
                            {
                                rt = Float.parseFloat(lCVParam.getValue().trim());
                                rtMin = rt/60;
                            }
                        }
                    }
                    model.insertRow(model.getRowCount(), new Object[]{
                        "",
                        Integer.parseInt(spectrum.getIndex().toString()), 
                        spectrum.getId().toString(),
                        msLevel,
                        Float.parseFloat(String.format("%.2f", basePeakMZ)),
                        Float.parseFloat(String.format("%.2f", basePeakInt)),
                        Float.parseFloat(String.format("%.2f", rt)),
                        Float.parseFloat(String.format("%.2f", rtMin))                
                        });
                    iCount++;
                }
                
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();                
    }   
    private void loadMGFView(String sFileName, String sFilePath) 
    {  
        final String sFileNameRef = sFileName;
        final String sFilePathRef = sFilePath;
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);        
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading values");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadingThread"){
            @Override
            public void run(){                
                DefaultTableModel model = new DefaultTableModel()
                {  
                    Class[] types = new Class [] {             
                        java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
                    };  
                    @Override  
                    public Class getColumnClass(int columnIndex) {  
                        return types [columnIndex];  
                    }  
                };
                jtMGF.setModel(model);
                model.addColumn("Index");
                model.addColumn("Scan Title");
                model.addColumn("Peptide Mass");
                model.addColumn("Charge");
                model.addColumn("Reference Line");        
                String sOutput="";

                sOutput = "Name:\t" + sFileNameRef + "\n";
                sOutput = sOutput + "Path:\t" + sFilePathRef + "\n";
                jtaMGFView.setText(sOutput);  
                jtpProperties.setSelectedIndex(1);       

                //... Reading file ...//
                try{
                    BufferedReader in = new BufferedReader(new FileReader(sFilePathRef));

                    String title = "", charge="";            
                    double pepmass=0.0;
                    long refLine=0;
                    String string = null;

                    long lineNum = 0; 
                    int iCount = 0;
                    while ((string = in.readLine()) != null) {
                        lineNum++;
                        string = string.trim();
                        if (string.startsWith("BEGIN IONS")) {
                            break;
                        }
                    }                     
                    while ((string = in.readLine()) != null) {
                        lineNum++;
                        string = string.trim();
                        if (string.equals("")) {
                            continue;
                        }
                        if (string.startsWith("PEPMASS")) {
                            pepmass = Double.parseDouble(string.substring(8));
                            continue;
                        }
                        if (string.startsWith("TITLE")) {
                            title = string.substring(6);
                            continue;
                        }
                        if (string.startsWith("CHARGE")) {
                            charge = string.substring(7);
                            refLine = lineNum;
                            continue;
                        }                
                        if (string.startsWith("BEGIN IONS")) {
                            continue;
                        }
                        if (string.contains("END IONS")) {
                            iCount++;
                            //... Insert rows ...//
                            model.insertRow(model.getRowCount(), new Object[]{
                            iCount,
                            title,
                            pepmass,
                            charge,
                            refLine});
                            continue;
                        }                
                        if (string.charAt(0) >= '1' && string.charAt(0) <= '9') {
                            continue;
                        }
                    }
                }catch(Exception e){
                    System.exit(1);
                }                
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();        
    }        
    private void loadMascotView(String sFileName, String sFilePath) 
    {  
        DefaultTableModel model = new DefaultTableModel()
        {  
            Class[] types = new Class [] {             
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };  
            @Override  
            public Class getColumnClass(int columnIndex) {  
                return types [columnIndex];  
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
        String sOutput="";
                       
        sOutput = "Name:\t" + sFileName + "\n";
        sOutput = sOutput + "Path:\t" + sFilePath + "\n";
        jtaMascotXML.setText(sOutput);  
        jtpProperties.setSelectedIndex(3);
        
        //... Open mascot file and extract identifications ...//
        File file = new File(sFilePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            Node nodeLst = doc.getElementsByTagName("hits").item(0); //... Reading hits ...//
            NodeList hitsList = nodeLst.getChildNodes();                             
            
            //... Initializing proteinId, peptide sequence and other variables ...//
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
            String sChainToInsert="";
                        
            for (int iI = 0; iI < hitsList.getLength(); iI++) //... Identifying all protein hits from the search ...//
            {                
                NodeList subHitsList = hitsList.item(iI).getChildNodes();
                for (int iJ = 0; iJ < subHitsList.getLength(); iJ++) 
                {
                    Node proteinItem = subHitsList.item(iJ);
                    if (proteinItem.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        if (proteinItem.getNodeName().equals("protein")) //... Reading the protein ...//
                        {
                            iProteins++;
                            proteinId = proteinItem.getAttributes().item(0).getTextContent().toString();
                            NodeList subProteinList = proteinItem.getChildNodes(); //... Protein subnodes ...//
                            for (int iK = 0; iK < subProteinList.getLength(); iK++) 
                            {
                                Node peptideItem = subProteinList.item(iK);
                                if (peptideItem.getNodeType() == Node.ELEMENT_NODE) 
                                {
                                    if (peptideItem.getNodeName().equals("peptide")) //... Reading peptides ...//
                                    {
                                        iPeptides++;                                        
                                        NodeList peptideDataList = peptideItem.getChildNodes(); //... Peptide subnodes ...//
                                        
                                        //... Initializing variables ...//
                                        for (int iA=0;iA<6;iA++) //... Cleaning array ...//
                                        {
                                            modification[iA] = null;
                                        }
                                        int iContMod = 0;
                                        isVariableMod = false;
                                        numMods = -1;
                                        dbCharge = -1;
                                        sChainToInsert = "";
                                        for (int iL = 0; iL < peptideDataList.getLength(); iL++) 
                                        {
                                            Node peptideElem = peptideDataList.item(iL);
                                            if (peptideElem.getNodeType() == Node.ELEMENT_NODE) 
                                            {
                                                if (peptideElem.getNodeName().equals("pep_seq")) 
                                                {
                                                    peptideSeq = peptideElem.getTextContent().toString();
                                                    
                                                    //... Calculate the residue composition ...//
                                                    peptideCompos = getResidueComposition(peptideSeq);
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_exp_mz")) 
                                                {
                                                    parIonMz = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_exp_mr")) 
                                                {
                                                    parIonMr = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                }                                                 
                                                else if (peptideElem.getNodeName().equals("pep_exp_z")) 
                                                {
                                                    dbCharge = Integer.valueOf(peptideElem.getTextContent().toString()).intValue();
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_score")) 
                                                {
                                                    hitScore = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                } 
                                                else if (hitScore >= 20) //... Verify if hits are over minimum threshold ...//
                                                {
                                                    if (peptideElem.getNodeName().equals("pep_var_mod")) //... Modification variables ...//
                                                    {
                                                        String sModVariable = peptideElem.getTextContent().toString();
                                                        
                                                        String[] differentMods = new String[3];
                                                        differentMods = sModVariable.split(";"); //... Check if different modifications are separated by colons ..//
                                                        if (differentMods[0].equals("")) //... Verify if has data ...//
                                                        {
                                                            isVariableMod = false; 
                                                        }
                                                        else
                                                        {
                                                            for (int iDiffMod=0; iDiffMod < differentMods.length; iDiffMod++)
                                                            {
                                                                //... This code parses the number of modifications (1, 2, n) once we have identified the different modif variables ...//                                                            
                                                                if (differentMods[iDiffMod].startsWith(" "))
                                                                {
                                                                     differentMods[iDiffMod] = differentMods[iDiffMod].substring(1, differentMods[iDiffMod].length());
                                                                }
                                                                if (differentMods[iDiffMod].length() > 0) 
                                                                {
                                                                    String[] multipleMods = new String[3]; //... New array for multiple modifications in different modifications ...//
                                                                    numMods = 1;
                                                                    if (differentMods[iDiffMod].matches("^\\d+[\\w|\\D|\\S|\\s|\\W]*")) 
                                                                    {
                                                                        multipleMods = differentMods[iDiffMod].split(" ");
                                                                        numMods = Integer.valueOf(multipleMods[0]).intValue();
                                                                        sChainToInsert = differentMods[iDiffMod].replace(numMods + " ", "");
                                                                        numMods = 1;
                                                                    }
                                                                    else
                                                                    {
                                                                        sChainToInsert = differentMods[iDiffMod];
                                                                        numMods = 1;
                                                                    }
                                                                    for (int iFound=0; iFound<numMods; iFound++)
                                                                    {
                                                                        modification[iContMod] = sChainToInsert;
                                                                        iContMod++;
                                                                    }
                                                                } 
                                                            }
                                                            isVariableMod = true;         
                                                        }
                                                    } 
                                                    else if (peptideElem.getNodeName().equals("pep_var_mod_pos") && isVariableMod) 
                                                    {
                                                        mascotPos = peptideElem.getTextContent().toString();
                                                        mascotPos = mascotPos.replace(".", "");
                                                    } 
                                                    else if (peptideElem.getNodeName().equals("pep_scan_title")) //... Only in some cases the rt is specified in the scan title ...//
                                                    {
                                                        if (peptideElem.getTextContent().toString().indexOf("rt:")>0) //... Option 1, reading on scan title ...//
                                                        {
                                                            String myTmpString = peptideElem.getTextContent().toString();
                                                            int ind = 0;
                                                            int ind1 = 0;

                                                            //... Get retention time ...//
                                                            ind = myTmpString.indexOf("(rt:");
                                                            if(ind>0)
                                                            {
                                                                ind1 = myTmpString.indexOf(")");
                                                                retTime = Double.valueOf(myTmpString.substring(ind + 4, ind1)).floatValue();
                                                            }
                                                            else
                                                            {
                                                                retTime=0;
                                                            }
                                                            
                                                            //... Get ID ref ...//
                                                            ind = myTmpString.indexOf("(id:");
                                                            if(ind>0)
                                                            {
                                                                ind1 = myTmpString.lastIndexOf(")");
                                                                idRef = myTmpString.substring(ind + 4, ind1);
                                                            }
                                                            else
                                                            {
                                                                idRef="";
                                                            }                                                            
                                                            
                                                            //... Get scan number ...//
                                                            ind = myTmpString.indexOf("Scan:");
                                                            if(ind>-1)
                                                            {
                                                                ind1 = myTmpString.indexOf(",");
                                                                scanNumber = Double.valueOf(myTmpString.substring(ind + 5, ind1)).intValue();
                                                            }
                                                            else
                                                            {
                                                                scanNumber=0;
                                                            }
                                                        } 
                                                        else 
                                                        {
                                                            //... Read mzML file or MGF (TO DO) ...//                                                            
                                                            retTime = 0;                                                            
                                                        }
                                                        model.insertRow(model.getRowCount(), new Object[]{
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
                                                            retTime});
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
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            jtMascotXMLView.setRowSorter(sorter);
        } 
        catch (Exception e) 
        {
            System.out.println("LoadMascotIdent: Exception while reading " + file + "\n" + e);
            System.exit(1);
        }
    }    
    private String getResidueComposition(String sPeptide) 
    {
        /*         
         * -------------------------------------------------------------
         * Name            3-Sym  1-Sym     Mono        Avg     Residues
         * -------------------------------------------------------------
         * Alanine         Ala     A        71.03711    71.08   C3H5NO
         * Arginine        Arg     R        156.10111   156.2   C6H12N4O
         * Asparagine      Asn     N        114.04293   114.1   C4H6N2O2
         * Aspartic Acid   Asp     D        115.02694   115.1   C4H5NO3
         * Cysteine        Cys     C        103.00919   103.1   C3H5NOS
         * Glutamic Acid   Glu     E        129.04259   129.1   C5H7NO3 
         * Glutamine       Gln     Q        128.05858   128.1   C5H8N2O2
         * Glycine         Gly     G        57.02146    57.05   C2H3NO
         * Histidine       His     H        137.05891   137.1   C6H7N3O
         * Isoleucine      Ile     I        113.08406   113.2   C6H11NO
         * Leucine         Leu     L        113.08406   113.2   C6H11NO
         * Lysine          Lys     K        128.09496   128.2   C6H12N2O
         * Methionine      Met     M        131.04049   131.2   C5H9NOS
         * Phenyalanine    Phe     F        147.06841   147.2   C9H9NO
         * Proline         Pro     P        97.05276    97.12   C5H7NO
         * Serine          Ser     S        87.03203    87.08   C3H5NO2
         * Threonine       Thr     T        101.04768   101.1   C4H7NO2
         * Tryptophan      Trp     W        186.07931   186.2   C11H10N2O
         * Tyrosine        Tyr     Y        163.06333   163.2   C9H9NO2
         * Valine          Val     V        99.06841    99.13   C5H9NO
        */
        
        int iCarb=0, iHydro=3, iNitro=0, iOxy=1, iSulf=0; //... Water molecule (H2O) + H1 ...//
        String sPeptideRet="";
        for (int iI = 0; iI < sPeptide.length(); iI++) {
            switch (sPeptide.toUpperCase().charAt(iI)) {
                case 'A': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'R': {
                    iCarb+=6;
                    iHydro+=12;
                    iNitro+=4;
                    iOxy++;
                    break;
                }                       
                case 'N': {
                    iCarb+=4;
                    iHydro+=6;
                    iNitro+=2;
                    iOxy+=2;
                    break;
                }   
                case 'D': {
                    iCarb+=4;
                    iHydro+=5;
                    iNitro++;
                    iOxy+=3;
                    break;
                }   
                case 'C': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                }   
                case 'E': {
                    iCarb+=5;
                    iHydro+=7;
                    iNitro++;
                    iOxy+=3;
                    break;
                }   
                case 'Q': {
                    iCarb+=5;
                    iHydro+=8;
                    iNitro+=2;
                    iOxy+=2;
                    break;
                }   
                case 'G': {
                    iCarb+=2;
                    iHydro+=3;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'H': {
                    iCarb+=6;
                    iHydro+=7;
                    iNitro+=3;
                    iOxy++;
                    break;
                }   
                case 'I': {
                    iCarb+=6;
                    iHydro+=11;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'L': {
                    iCarb+=6;
                    iHydro+=11;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'K': {
                    iCarb+=6;
                    iHydro+=12;
                    iNitro+=2;
                    iOxy++;
                    break;
                }   
                case 'M': {
                    iCarb+=5;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                }   
                case 'F': {
                    iCarb+=9;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'P': {
                    iCarb+=5;
                    iHydro+=7;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'S': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'T': {
                    iCarb+=4;
                    iHydro+=7;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'W': {
                    iCarb+=11;
                    iHydro+=10;
                    iNitro+=2;
                    iOxy++;
                    break;
                }   
                case 'Y': {
                    iCarb+=9;
                    iHydro+=9;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'V': {
                    iCarb+=5;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    break;
                }    
            }
        }
        if (iCarb>0)
        {
            sPeptideRet = "C" + iCarb;
        }
        if (iHydro>0)
        {
            sPeptideRet = sPeptideRet + "H" + iHydro;
        }
        if (iNitro>0)
        {
            sPeptideRet = sPeptideRet + "N" + iNitro;
        }
        if (iOxy>0)
        {
            sPeptideRet = sPeptideRet + "O" + iOxy;
        }
        if (iSulf>0)
        {
            sPeptideRet = sPeptideRet + "S" + iSulf;
        }        
        return sPeptideRet;
    }    
    private class ImageRenderer extends DefaultTableCellRenderer {
        JLabel lbl = new JLabel();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            lbl.setText((String) value);
            lbl.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif")));
            return lbl;
        }
    }
    //... Initialise project settings (all modules) ...//
    private void initProjectValues() 
    {
        //... Loading values from config file ...//
        initSettings(); //... From config file ...//
        initProjectStatusIcons();
        
        //... Initialising components ...//
        initTables();                       
        initTextAreas();
        initViews();
    }     
    //... Initialise project settings (configuration XML file) ...//
    private void initSettings() 
    {
        //... Validate if config file exists ...//
        boolean exists = (new File("config.xml")).exists();
        if (exists)
        {
            //... Read files using SAX (get workspace) ...//
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() 
                {                   
                    boolean bWorkspace = false;
                    
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes)
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
        }
        else
        {            
            this.sWorkspace = "";            
            String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
            sMessage = sMessage + "under your installation folder. ProteoSuite will continue launching, however \n";
            sMessage = sMessage + "it is recommended that you copy the file as indicated in the readme.txt file.";
            JOptionPane.showMessageDialog(this, sMessage, "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
        this.sProjectName = "New";
        this.bProjectModified = false;
    }
    //... Initialise project status ...//
    private void initProjectStatusIcons()
    {    
        //... Project status pipeline ...//
        Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/empty.gif"));
        jlRawFilesStatus.setIcon(loadRawFilesIcon);
        jlIdentFilesStatus.setIcon(loadRawFilesIcon);
        jlQuantFilesStatus.setIcon(loadRawFilesIcon);
        jcbTechnique.setSelectedIndex(0);
    }    
    //... Initialise table values ...//
    private void initTables()
    {
        DefaultTableModel model = new DefaultTableModel();        
        jtRawFiles.setModel(model);
        model.addColumn("Name");
        model.addColumn("Path");        
        model.addColumn("Type");
        model.addColumn("Version");
        model.addColumn("Scans");
        
        DefaultTableModel model2 = new DefaultTableModel();        
        jtIdentFiles.setModel(model2);
        model2.addColumn("Name");      
        model2.addColumn("Path");        
        model2.addColumn("Type");
        model2.addColumn("Version");
        
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
        model5.addColumn("Total ion current");
        model5.addColumn("Lowest obs m/z");
        model5.addColumn("Highest obs m/z");
        model5.addColumn("Ret Time (sec-min)");
        model5.addColumn("Scan Win Min");
        model5.addColumn("Scan Win Max");
        
        DefaultTableModel model6 = new DefaultTableModel();
        jtMascotXMLView.setModel(model6);     
        model6.addColumn("Index");
        model6.addColumn("Protein");
        model6.addColumn("Peptide");
        model6.addColumn("Composition");
        model6.addColumn("Exp Mz");
        model6.addColumn("Exp Mr");
        model6.addColumn("Charge");
        model6.addColumn("Score");
        model6.addColumn("Scan");
        model6.addColumn("Scan ID");
        model6.addColumn("RT (sec)");
        
        DefaultTableModel model7 = new DefaultTableModel();
        jtMascotXMLView.setModel(model7);     
        model7.addColumn("mzQuantML output");        
    }
    //... Initialise Text Areas ...//
    private void initTextAreas()
    {
        jtaLog.setText("");
        jtaMzML.setText("");
        jtaMascotXML.setText("");
        jtaMzQuantML.setText("");               
    }    
    //... Initialise Visualisation Windows ...//
    private void initViews()
    {
        jdpMS.removeAll();
        jdpTIC.removeAll();
    }        
    private boolean openProject() 
    {
        JFileChooser chooser = new JFileChooser("user.home");
        String sFileName = "";
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ProteoSuite Project (*.psx)", "psx");
        chooser.setFileFilter(filter);
        
        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);        

        //... Setting default directory ...//
        chooser.setCurrentDirectory(new java.io.File(this.sWorkspace)); //... If not found it goes to Home, exception handle not needed ...//
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);        
        
        //... Write file ...//
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            sFileName = file.toString();
            if (file.exists())
            {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    readConfigFile(sFileName);
                    
                    //... Unmarshall ...//
                    aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
                    //jtMainTree.removeAll();       
                    
                    //... Fill JTable ...//
                    DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Type");
                    model.addColumn("Version");
                    model.addColumn("Scans");                   
                    
                    for (int iI=0; iI<jtRawFiles.getRowCount(); iI++)
                    {                    
                        File xmlFile = new File(this.sWorkspace+"/"+jtRawFiles.getValueAt(iI, 1).toString());
                        unmarshalMzMLFile(model, xmlFile);
                    }
                    //... Draw tree ...//
                    //jtMainTree.setModel(new DefaultTreeModel(rootNode));
                    //jtMainTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);                     
                    
                    setCursor(null);
                    return true;
            }
            else
            {
                return false;
            }
        }
        else        
        {
            return false;
        }
        
    }    
    private void readConfigFile(String sFileName) {
        
        try{
            
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(sFileName));

            doc.getDocumentElement().normalize();
            NodeList projectList = doc.getElementsByTagName("ProteoSuiteProject");
            Node projectNode = projectList.item(0);
            Element projectElement = (Element)projectNode;
            this.sProjectName = projectElement.getAttribute("name").toString();
            this.sWorkspace = projectElement.getAttribute("workspace").toString();
            this.bProjectModified = false;

            //... Fill JTable ...//
            DefaultTableModel model = new DefaultTableModel();
            jtRawFiles.setModel(model);
            model.addColumn("Name");
            model.addColumn("Path");
            model.addColumn("Type");
            model.addColumn("Version");                
            model.addColumn("Scans");

            //... Reading raw files ...//
            NodeList rawFiles = doc.getElementsByTagName("rawFile");
            int iRawFiles = rawFiles.getLength();
            System.out.println("RawFiles : " + iRawFiles);

            for(int iI=0; iI<rawFiles.getLength() ; iI++)
            {
                Node rawFileNode = rawFiles.item(iI);
                if(rawFileNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element rawFileElement = (Element)rawFileNode;
                    
                    model.insertRow(model.getRowCount(), new Object[]{rawFileElement.getAttribute("name").toString(),
                                                                      rawFileElement.getAttribute("path").toString(),
                                                                      rawFileElement.getAttribute("type").toString(),
                                                                      rawFileElement.getAttribute("version").toString(),
                                                                      rawFileElement.getAttribute("scans").toString()});
                }
            }
        }catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());
        }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();
        }catch (Throwable t) {
            t.printStackTrace ();
        }
    }       
    private void unmarshalMzMLFile(DefaultTableModel model, File xmlFile)
    {
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
            aMzMLUnmarshaller.add(unmarshaller);

            int spectra = unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum");

            int mid= xmlFile.getName().lastIndexOf(".");
            String ext="";
            ext=xmlFile.getName().substring(mid+1,xmlFile.getName().length());             
            model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(),
                                                                      xmlFile.getPath().toString().replace("\\", "/"),
                                                                      ext,
                                                                      unmarshaller.getMzMLVersion(),
                                                                      spectra});
    }    
    private void unmarshalMzIDFile(DefaultTableModel model, int iIndex, File xmlFile)
    {
        MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(xmlFile);
        aMzIDUnmarshaller.add(unmarshaller);
        model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(), xmlFile.getPath().replace("\\", "/"), "mzid", "1.1"});
    }
    private void unmarshalMzQMLFile(DefaultTableModel model, int iIndex, File xmlFile)
    {        
        MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(xmlFile.toString());
        aMzQMLUnmarshaller.add(unmarshaller);
        model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(), xmlFile.getPath().replace("\\", "/"), "mzq", "1.0"});
    }    
    //... Writing the XTrackerConfigFile module ...//
    private void writeXTrackerConfigFiles() 
    {
        //... Check project name ...//  
        String sFile = sProjectName;
        if (sFile.equals("New"))
        {
            sFile = "test.mzq";
            String sMessage = "You have not saved this project. Proteosuite will create a test.mzq file to run the pipeline. \n";
            sMessage = sMessage + "Do not forget to save the project if you want to reuse the parameters set for this analysis.";
            JOptionPane.showMessageDialog(null, sMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
            sProjectName = sFile;
        }
        writeMzQuantML(jcbTechnique.getSelectedItem().toString(), sFile);        
        
        //... Unmarshall mzquantml file ...//
        Validator validator = XMLparser.getValidator(MZQ_XSD);
        boolean validFlag = XMLparser.validate(validator, sWorkspace + "/" +  sProjectName);
        if(!validFlag){
            JOptionPane.showMessageDialog(null, "Invalid mzQuantML file", "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(1);
        }
        
        //... Load the mzQuantML file into memory ...//
        MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(sWorkspace + "/" +  sProjectName);
        mzQuantML = unmarshaller.unmarshall();
        
        //... Modify the mzQuantML structure according to the experiment ...//
        writeXTrackerMain(jcbTechnique.getSelectedItem().toString());              
    }  
    //... Write xTrackerMain based on the technique ...//
    private void writeMzQuantML(String sExperiment, String sFile) 
    {   
        //... Create object ...//
        MzQuantML qml = new MzQuantML();
        
        //... Set version ...//
        String Version = "1.0.0";
        qml.setVersion(Version);
        Calendar rightNow = Calendar.getInstance();
        qml.setCreationDate(rightNow);
        
        //----------------------//
        //... CREATE CV LIST ...//
        //----------------------//
        CvList cvs = new CvList();
        List<Cv> cvList = cvs.getCv();
        Cv cvPSI_MS = new Cv();
        cvPSI_MS.setId("PSI-MS");
        cvPSI_MS.setUri("http://psidev.cvs.sourceforge.net/viewvc/*checkout*/psidev/psi/psi-ms/mzML/controlledVocabulary/psi-ms.obo");
        cvPSI_MS.setFullName("Proteomics Standards Initiative Mass Spectrometry Vocabularies");
        cvPSI_MS.setVersion("3.32.0");

        Cv cvUO = new Cv();
        cvUO.setId("UO");
        cvUO.setUri("http://obo.cvs.sourceforge.net/*checkout*/obo/obo/ontology/phenotype/unit.obo");
        cvUO.setFullName("Unit Ontology");
        
        Cv cvPSI_MOD = new Cv();
        cvPSI_MOD.setId("PSI-MOD");
        cvPSI_MOD.setUri("http://psidev.cvs.sourceforge.net/psidev/psi/mod/data/PSI-MOD.obo");
        cvPSI_MOD.setFullName("Proteomics Standards Initiative Protein Modifications Vocabularies");        
        cvPSI_MOD.setVersion("1.2");

        Cv cvUNI_MOD = new Cv();
        cvUNI_MOD.setId("UNIMOD");
        cvUNI_MOD.setUri("http://www.unimod.org/obo/unimod.obo");
        cvUNI_MOD.setFullName("UNIMOD");
        
        cvList.add(cvPSI_MS);
        cvList.add(cvUO);
        cvList.add(cvPSI_MOD);
        cvList.add(cvUNI_MOD);
        qml.setCvList(cvs);
        
        //-------------------------------//
        //... CREATE ANALYSIS SUMMARY ...//
        //-------------------------------//
        ParamList pl = new ParamList();
        List<AbstractParam> cvParamList = pl.getParamGroup();
        CvParam cvp = new CvParam();
        cvp.setAccession("MS:1001837");
        cvp.setCvRef(cvPSI_MS);
        cvp.setName("iTRAQ quantitation analysis");
        
        CvParam cvp2 = new CvParam();
        cvp2.setAccession("MS:1002024");
        cvp2.setCvRef(cvPSI_MS);
        cvp2.setValue("true");
        cvp2.setName("MS2 tag-based analysis feature level quantitation");        

        CvParam cvp3 = new CvParam();
        cvp3.setAccession("MS:1002025");
        cvp3.setCvRef(cvPSI_MS);
        cvp3.setValue("true");
        cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

        CvParam cvp4 = new CvParam();
        cvp4.setAccession("MS:1002026");
        cvp4.setCvRef(cvPSI_MS);
        cvp4.setValue("true");
        cvp4.setName("MS2 tag-based analysis protein level quantitation");
        
        CvParam cvp5 = new CvParam();
        cvp5.setAccession("MS:1002027");
        cvp5.setCvRef(cvPSI_MS);
        cvp5.setValue("false");
        cvp5.setName("MS2 tag-based analysis protein group level quantitation");        
        
        cvParamList.add(cvp);
        cvParamList.add(cvp2);
        cvParamList.add(cvp3);
        cvParamList.add(cvp4);
        cvParamList.add(cvp5);
        
        qml.setAnalysisSummary(pl);
        
        //--------------------------//
        //... CREATE INPUT FILES ...//
        //--------------------------//
        InputFiles inputFiles = new InputFiles();
        
        //... Define raw and id group lists ...//
        List<RawFilesGroup> rawFilesGroupList = inputFiles.getRawFilesGroup();                
        
        //... Define those structures that will be used in further (e.g. in AssayList) ...//
        HashMap<String, String> rawFileNameIdMap = new HashMap<String, String>(); 
        HashMap<String, ArrayList<String>> rgIdrawIdMap = new HashMap<String, ArrayList<String>>();  
            
        //... Raw file containers ...//
        RawFilesGroup rawFilesGroup = new RawFilesGroup();
        List<RawFile> rawFilesList = rawFilesGroup.getRawFile();
            
        //... Select all raw files from grid ...//
        for (int iI=0; iI<jtRawFiles.getRowCount(); iI++)
        {
            //... Raw files ...//
            String rawFname = jtRawFiles.getValueAt(iI, 0).toString();
            String rawId = "r" + Integer.toString(iI+1);
            
            RawFile rawFile = new RawFile();
            rawFile.setName(rawFname);
            rawFile.setId(rawId);
            rawFile.setLocation(jtRawFiles.getValueAt(iI, 1).toString());            
            rawFilesList.add(rawFile);
            rawFileNameIdMap.put(rawFname, rawId); //... Saving hashmap for AssayList ...//
        }
        String rgId = "raw1";
        rawFilesGroup.setId(rgId);
        rawFilesGroupList.add(rawFilesGroup);

        //... Define those structures that will be used in further (e.g. in AssayList) ...//
        HashMap<String, String> idFileNameIdMap = new HashMap<String, String>();         
        
        //... Identification file containers ...//
        IdentificationFiles idFiles = inputFiles.getIdentificationFiles();
        if(idFiles==null)
        {
            idFiles = new IdentificationFiles();
        }
        List<IdentificationFile> idFilesList = idFiles.getIdentificationFile();
            
        //... Select all raw files from grid ...//
        for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++)
        {
            //... Identification files ...//
            String idFname = jtIdentFiles.getValueAt(iI, 0).toString();
            String idId = "id_file" + Integer.toString(iI+1);
            
            IdentificationFile idFile = new IdentificationFile();
            idFile.setName(idFname);
            idFile.setId(idId);
            idFile.setLocation(jtIdentFiles.getValueAt(iI, 1).toString().replace("\\", "/"));            
            idFilesList.add(idFile);
            idFileNameIdMap.put(idFname, idId); //... Saving hashmap for AssayList ...//                        
        }
        inputFiles.setIdentificationFiles(idFiles);
        qml.setInputFiles(inputFiles);
        
        //---------------------------//
        //... Create SoftwareList ...//
        //---------------------------//
        SoftwareList softwareList = new SoftwareList();
        Software software = new Software();
        softwareList.getSoftware().add(software);
        software.setId("xTRACKER");
        software.setVersion("1");
        qml.setSoftwareList(softwareList);
        
        //---------------------------------//
        //... Create DataProcessingList ...//
        //---------------------------------//
        DataProcessingList dataProcessingList = new DataProcessingList();
        DataProcessing dataProcessing = new DataProcessing();
        dataProcessing.setId("DP1");
        dataProcessing.setSoftwareRef(software);
        dataProcessing.setOrder(BigInteger.ONE);
        
        //... Based on the technique, select the plugins that are available to perform the quantitation ...//
        String[] sPipeline;
        sPipeline = new String[4];            
        sPipeline = getPlugins(sExperiment); 
        
        //... Processing methods ...//
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
        up3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[0] + ".xtp");
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
        up2_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[1] + ".xtp");
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
        up3_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[2] + ".xtp");
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
        up4_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[3] + ".xtp");
        pmList4.add(up4_3);
        dataProcessing.getProcessingMethod().add(processingMethod4);

        dataProcessingList.getDataProcessing().add(dataProcessing);
        qml.setDataProcessingList(dataProcessingList);
        
        //------------------------//
        //... Create AssayList ...//        
        //------------------------//
        AssayList assays = new AssayList();
        assays.setId("AssayList1");
        List<Assay> assayList = assays.getAssay();
        HashMap<String, ArrayList<Assay>> studyVarAssayID = new HashMap<String, ArrayList<Assay>>(); //... This will be used in StudyVariableList ...//

        //... Assay list will be retrieved from the parameters set up in the configQuantML file ...//
        
        try{
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); 
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse("configQuant.xml");
            XPath xpath = XPathFactory.newInstance().newXPath();

            //... Reading mzRanges (min and max) ...//
            XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/AssayParam");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            boolean blnExists = false;
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                Node node = nodes.item(iI);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    NodeList nodelist = element.getElementsByTagName("AssayID");
                    Element element2 = (Element) nodelist.item(0);
                    NodeList fstNm1 = element2.getChildNodes();
                    
                    Element element3 = (Element) node;
                    NodeList nodelist2 = element3.getElementsByTagName("AssayName");
                    Element element4 = (Element) nodelist2.item(0);
                    NodeList fstNm2 = element4.getChildNodes();                     
                    
                    Element element5 = (Element) node;
                    NodeList nodelist3 = element5.getElementsByTagName("mzValue");
                    Element element6 = (Element) nodelist3.item(0);
                    NodeList fstNm3 = element6.getChildNodes();       
                    
                    Element element7 = (Element) node;
                    NodeList nodelist4 = element7.getElementsByTagName("StudyVariable");
                    Element element8 = (Element) nodelist4.item(0);
                    NodeList fstNm4 = element8.getChildNodes();
                    
                    Assay assay = new Assay();
                    String assName = fstNm1.item(0).getNodeValue().toString();
                    String assId = "_" + fstNm1.item(0).getNodeValue().toString();
                    String studyVar = fstNm4.item(0).getNodeValue().toString();
                    assay.setId(assId);
                    assay.setName(assName);
                    
                    //... Check if there is a study variable with that value ...//
                    blnExists = studyVarAssayID.containsKey(studyVar);
                    if (blnExists == false)
                    {                        
                        ArrayList al = new ArrayList();
                        al.add(assay);
                        studyVarAssayID.put(studyVar, al);
                    }                    
                    else
                    {
                        ArrayList al2 = studyVarAssayID.get(studyVar);                   
                        al2.add(assay);
                        studyVarAssayID.put(studyVar, al2);
                    }
                    
                    assay.setRawFilesGroupRef(rawFilesGroup);  
                    Label label = new Label();
                    CvParam labelCvParam = new CvParam();
                    labelCvParam.setAccession("");
                    labelCvParam.setName(fstNm2.item(0).getNodeValue().toString());
                    labelCvParam.setValue(fstNm3.item(0).getNodeValue().toString());
                    labelCvParam.setCvRef(cvPSI_MOD);
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
        catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        } catch ( XPathExpressionException  e) {
          e.printStackTrace();
        }               
        
        qml.setAssayList(assays);
        
        //--------------------------------//
        //... Create StudyVariableList ...//        
        //--------------------------------//        
        StudyVariableList studyVariables = new StudyVariableList();
        List<StudyVariable> studyVariableList = studyVariables.getStudyVariable();

        Iterator it = studyVarAssayID.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            String group = pairs.getKey().toString();
            ArrayList al = (ArrayList) pairs.getValue();
            StudyVariable studyVariable = new StudyVariable();
            studyVariable.setName(group);
            studyVariable.setId("SV_"+group);
            List<Object> assayRefList = studyVariable.getAssayRefs();        
            for(Object obj:al)
            {
                assayRefList.add(obj);
            }
            CvParam cvp9 = new CvParam();
            cvp9.setAccession("MS:1001807");
            cvp9.setCvRef(cvPSI_MS);
            cvp9.setValue("1");
            cvp9.setName("StudyVariable attribute");
            List<AbstractParam> paramList = studyVariable.getParamGroup();
            paramList.add(cvp9);
            studyVariableList.add(studyVariable);            
        }        
        
        qml.setStudyVariableList(studyVariables);
        
        //... Marshal mzQuantML object ...//
        MzQuantMLMarshaller marshaller = new MzQuantMLMarshaller(sWorkspace + "/" + sFile);
        marshaller.marshall(qml);
    }
    //... Write xTrackerMain based on the technique ...//
    private void writeXTrackerMain(String sExperiment) 
    {            
        //... Based on the technique, select the plugins that are available to perform the quantitation ...//
        String[] sPipeline;
        sPipeline = new String[4];            
        sPipeline = getPlugins(sExperiment);               

        //... xTracker consists of 4 main plugins (read more on www.x-tracker.info) ...//                
        writeXTrackerIdent(sPipeline[0]);         
        writeXTrackerRaw(sPipeline[1]);        
        writeXTrackerQuant(sPipeline[2]);        
        writeXTrackerOutput(sPipeline[3]);  
    }
    //... This method gets the plugins based on the selected pipeline ...//
    private String[] getPlugins(String sExperiment)
    {
        final List<List<String>> alPlugins = new ArrayList<List<String>>();
        String[] sPipeline;
        sPipeline = new String[5];                        
        
        //... Read files using XML parser (Creates an Array of ArrayList) ...//
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File("config.xml"));

            NodeList nodeList = document.getElementsByTagName("pluginLoadIdentFiles");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginLoadRawFiles");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginQuantitation");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginOutput");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
        }catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        }
        
        //... Using the array list we need to find the pipeline and corresponding plugins ...//   
        
        //... Find ident file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++)
        {
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jtIdentFiles.getValueAt(0, 2).toString().toLowerCase()))
            {
                sPipeline[0] = arrayOfStrings[2].toString();
                break;
            }
        }
        //... Find raw file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++)
        {
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jtRawFiles.getValueAt(0, 2).toString().toLowerCase()))
            {
                sPipeline[1] = arrayOfStrings[2].toString();
                break;
            }
        }        
        //... Find quant file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++)
        {
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(sExperiment.toLowerCase()))
            {
                sPipeline[2] = arrayOfStrings[2].toString();
                break;
            }
        }        
        //... Find output file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++)
        {
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jcbOutputFormat.getSelectedItem().toString().toLowerCase()))
            {
                sPipeline[3] = arrayOfStrings[2].toString();
                break;
            }
        }
        
        return sPipeline;
    }
    private void writeXTrackerIdent(String sPlugin) 
    {        
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!-- ");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();
            out.write("-->");
            out.newLine();
            if (sPlugin.equals("loadMascotIdent"))
            {
                out.write("<!-- ");                
                out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
                out.newLine();
                out.write("    Also, modifications and mass shifts are specified here in case mascot does not report fixed modification mass shifts.");
                out.newLine();
                out.write("    Finally, the minimum threshold use for the search engine is specified in the pop_score_threshold tag. ");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadMascotIdent.xsd\">");
                out.newLine();
                out.write("    <inputFiles>");
                out.newLine();
                for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++)
                {
                    out.write("        <datafile identification_file=\"" + jtIdentFiles.getValueAt(iI, 1) + "\">" + jtRawFiles.getValueAt(iI, 1) + "</datafile>");
                    out.newLine();
                }           
                out.write("    </inputFiles>");
                out.newLine();
                out.write("<!-- Modifications (including amino-acid affected) and mass shifts considered in the search are reported below.");
                out.newLine();
                out.write("    NOTE THAT MONOISOTOPIC MASSES ARE USED IN THIS FILE!!!");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("    <modificationData>");
                out.newLine();            
                out.write("        <modification delta=\"57.021469\">Carbamidomethyl (C)</modification>");
                out.newLine();                    
                out.write("        <modification delta=\"144.1020633\">iTRAQ4plex (K)</modification>");
                out.newLine();
                out.write("        <modification delta=\"144.102063\">iTRAQ4plex (N-term)</modification>");
                out.newLine();
                out.write("    </modificationData>");
                out.newLine();
                
                //... Read files using XPath xml parser ...//
                String SearchScore = "";
                List<List<String>> twoDim = new ArrayList<List<String>>();
                try{
                    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                    domFactory.setNamespaceAware(true); 
                    DocumentBuilder builder = domFactory.newDocumentBuilder();
                    Document doc = builder.parse("configQuant.xml");
                    XPath xpath = XPathFactory.newInstance().newXPath();

                    //... Reading mzRanges (min and max) ...//
                    XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
                    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    for (int iI = 0; iI < nodes.getLength(); iI++) {
                        SearchScore = nodes.item(iI).getTextContent();
                    }
                }
                catch ( ParserConfigurationException e) {
                  e.printStackTrace();
                } catch ( SAXException e) {
                  e.printStackTrace();
                } catch ( IOException e) {
                  e.printStackTrace();
                } catch ( XPathExpressionException  e) {
                  e.printStackTrace();
                }
                out.write("    <pep_score_threshold>" + SearchScore + "</pep_score_threshold>");
                out.newLine();   
                out.write("</param> ");                
            }
            if (sPlugin.equals("loadMzIdentML"))
            {
                out.write("<!-- ");                
                out.newLine();
                out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<SpectralIdentificationList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/loadMzIdentML.xsd\">");
                out.newLine();
                for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++)
                {
                    out.write("    <SpectralIdentificationPair spectra=\"" + jtRawFiles.getValueAt(iI, 1) + "\" identification=\"" + jtIdentFiles.getValueAt(iI, 1) + "\" />");
                    out.newLine();
                }
                out.write("</SpectralIdentificationList>");
                out.newLine();                
            }
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    //... Write the raw files selected for quantitation ...//
    private void writeXTrackerRaw(String sPlugin) 
    {
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();            
            out.write("-->");
            out.newLine();            
            out.write("<!-- ");                
            out.write("    This XML file specifies a list of raw data files which will be used in the analysis.");
            out.newLine();
            out.write("-->");            
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadRawMGF.xsd\">");
            out.newLine();
            for (int iI=0; iI<jtRawFiles.getRowCount(); iI++)
            {
                out.write("    <datafile>" + jtRawFiles.getValueAt(iI, 1).toString() + "</datafile>");
                out.newLine();
            }
            out.write("</param>");
            out.newLine();
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    private void writeXTrackerQuant(String sPlugin) 
    {       
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        if (sPlugin.equals("SILAC"))
        {            
            
        }
        else if (sPlugin.equals("iTraqQuantitation"))
        {
            //... Read files using XPath xml parser ...//
            String mzRangeMin = "", mzRangeMax="", integrationMethod="";
            List<List<String>> twoDim = new ArrayList<List<String>>();
            try{
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true); 
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("configQuant.xml");
                XPath xpath = XPathFactory.newInstance().newXPath();

                //... Reading mzRanges (min and max) ...//
                XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/minus");
                NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    mzRangeMin = nodes.item(iI).getTextContent();
                }
                expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/plus");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    mzRangeMax = nodes.item(iI).getTextContent();
                }
                expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/IntegrationMethod");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    integrationMethod = nodes.item(iI).getTextContent();
                }

                //... Assay Parameters (Labels) ...//
                expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/AssayParam");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                String sAssayName = "", sMzValue="";
                String[] sCorrFactors = new String[4];            
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    Node node = nodes.item(iI);
                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element = (Element) node;
                        NodeList nodelist = element.getElementsByTagName("AssayName");
                        Element element1 = (Element) nodelist.item(0);
                        NodeList fstNm = element1.getChildNodes();
                        sAssayName = fstNm.item(0).getNodeValue();

                        Element element2 = (Element) node;
                        NodeList nodelist1 = element2.getElementsByTagName("mzValue");
                        Element element3 = (Element) nodelist1.item(0);
                        NodeList fstNm1 = element3.getChildNodes();
                        sMzValue = fstNm1.item(0).getNodeValue();

                        Element element4 = (Element) node;
                        NodeList nodelist2 = element2.getElementsByTagName("factor");
                        for (int iJ = 0; iJ < nodelist2.getLength(); iJ++)
                        {
                            Element element5 = (Element) nodelist2.item(iJ);
                            NodeList fstNm2 = element5.getChildNodes();
                            sCorrFactors[iJ] = fstNm2.item(0).getNodeValue();
                        }
                        twoDim.add(Arrays.asList(sAssayName, sMzValue, sCorrFactors[0], sCorrFactors[1], sCorrFactors[2], sCorrFactors[3]));
                    }
                }
            }
            catch ( ParserConfigurationException e) {
              e.printStackTrace();
            } catch ( SAXException e) {
              e.printStackTrace();
            } catch ( IOException e) {
              e.printStackTrace();
            } catch ( XPathExpressionException  e) {
              e.printStackTrace();
            }
            
            //... Write configuration file ...//
            try{
                FileWriter fstream = new FileWriter(sFileName);
                BufferedWriter out = new BufferedWriter(fstream);            
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.newLine();
                out.write("<!-- ");
                out.newLine();
                out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
                out.newLine();                
                out.write("    This plugin allows the specification of the parameters used for the quantitation process in iTRAQ. Here, we");
                out.newLine();
                out.write("    specify the quantitation method and the correction factors used for the reporter ions.");
                out.newLine();
                out.write("-->");                                
                out.newLine();
                out.write("<iTraqQuantitation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/iTraqQuantitation.xsd\">");
                out.newLine();
                out.write("	<AssayParamList>");
                out.newLine();

                for (int iI=0; iI<twoDim.size(); iI++)
                {
                    out.write("		<AssayParam>");
                    out.newLine();                                                            
                    List<String> lList = twoDim.get(iI);
                    out.write("			<AssayName>" + lList.get(0).toString() + "</AssayName>");
                    out.newLine();                    
                    out.write("			<mzValue>" + lList.get(1).toString() + "</mzValue>");
                    out.newLine();                    
                    out.write("			<CorrectionFactors>");
                    out.newLine();                     
                    out.write("				<factor deltaMass=\"-2\">" + lList.get(2) + "</factor>");
                    out.newLine();                    
                    out.write("				<factor deltaMass=\"-1\">" + lList.get(3) + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+1\">" + lList.get(4) + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+2\">" + lList.get(5) + "</factor>");
                    out.newLine();                                        
                    out.write("			</CorrectionFactors>");
                    out.newLine();                     
                    out.write("		</AssayParam>");
                    out.newLine();                     
                }
                out.write("	</AssayParamList>");
                out.newLine();
                out.write("	<Setting>");
                out.newLine();
                out.write("		<mzRange>");
                out.newLine();                
                out.write("    			<minus>" + mzRangeMin + "</minus>");
                out.newLine();                
                out.write("    			<plus>" + mzRangeMax + "</plus>");
                out.newLine();
                out.write("		</mzRange>");
                out.newLine();
                out.write("		<IntegrationMethod>" + integrationMethod + "</IntegrationMethod><!--the method used to calculate the area of the peak-->");
                out.newLine();
                out.write("	</Setting>");
                out.newLine();
                out.write("</iTraqQuantitation>");
                out.newLine();
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }   
        }        
    }
    private void writeXTrackerOutput(String sPlugin) 
    {
        String sFileName = sWorkspace.replace("\\", "/") + "/xTracker_" + sPlugin + ".xtp";
        String sType="", sVersion="";        
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
            out.newLine();                 
            out.write("    This plugin allows the specification of the output format. ");
            out.newLine();
	    out.write("-->");
            out.newLine();
            if (sPlugin.equals("outputCSV"))
            {
                out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
                out.newLine();                
                out.write("    <outputFilename>" + sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", ".csv") +"</outputFilename>");
                out.newLine();
                out.write("</output>");            
                out.newLine();  
                sType = "CSV";
                sVersion = "N/A";
            }
            if (sPlugin.equals("outputMZQ"))
            {
                out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
                out.newLine();                
                String sTemp = sProjectName.replace(".mzq", "");
                out.write("    <outputFilename>" + sWorkspace.replace("\\", "/") + "/" + sTemp + "_output.mzq</outputFilename>");
                out.newLine();
                out.write("</output>");
                out.newLine();
                sType = "MZQ";
                sVersion = "1.0.0-rc2";
            }
            out.close();                 
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
        //... Add file on the Quantitation Files tabsheet ...//
        final DefaultTableModel model = new DefaultTableModel();
        jtQuantFiles.setModel(model);
        model.addColumn("Name");
        model.addColumn("Path");
        model.addColumn("Type");
        model.addColumn("Version");
        if (sType.equals("MZQ"))
        {
            model.insertRow(model.getRowCount(), new Object[]{
                sProjectName.replace(".mzq", "") + "_output.mzq", 
                sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", "") + "_output.mzq", 
                sType.toLowerCase(), 
                sVersion});
            
            MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", "") + "_output.mzq");
            aMzQMLUnmarshaller.add(unmarshaller);
        }
        if (sType.equals("CSV"))
        {
            model.insertRow(model.getRowCount(), new Object[]{
                sProjectName.replace(".mzq", ".csv"), 
                sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", ".csv"), 
                sType.toLowerCase(), 
                sVersion});
        }
    }
    /**
     * @param args the command line arguments (leave empty)
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new ProteoSuiteView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FeatureQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton jbCopy;
    private javax.swing.JButton jbCut;
    private javax.swing.JButton jbImportFile;
    private javax.swing.JButton jbNewProject;
    private javax.swing.JButton jbOpenProject;
    private javax.swing.JButton jbPaste;
    private javax.swing.JButton jbRunQuantAnalysis;
    private javax.swing.JButton jbSaveProject;
    private javax.swing.JButton jbShowIsotopeDistrib;
    private javax.swing.JComboBox jcbOutputFormat;
    private javax.swing.JComboBox jcbTechnique;
    private javax.swing.JDesktopPane jdpMS;
    private javax.swing.JDesktopPane jdpTIC;
    private javax.swing.JLabel jlFiles;
    private javax.swing.JLabel jlIdentFiles;
    private javax.swing.JLabel jlIdentFilesStatus;
    private javax.swing.JLabel jlPeptide;
    private javax.swing.JLabel jlPeptideMzId;
    private javax.swing.JLabel jlProperties;
    private javax.swing.JLabel jlProtein;
    private javax.swing.JLabel jlProteinMzId;
    private javax.swing.JLabel jlQuantFiles;
    private javax.swing.JLabel jlQuantFilesStatus;
    private javax.swing.JLabel jlRT;
    private javax.swing.JLabel jlRawFiles;
    private javax.swing.JLabel jlRawFilesStatus;
    private javax.swing.JLabel jlScan;
    private javax.swing.JLabel jlScanTitle;
    private javax.swing.JLabel jlSearchMGF;
    private javax.swing.JLabel jlSearchMascotXML;
    private javax.swing.JLabel jlSearchMzId;
    private javax.swing.JLabel jlSearchMzML;
    private javax.swing.JLabel jlTechnique;
    private javax.swing.JLabel jlViewer;
    private javax.swing.JMenuItem jm2DView;
    private javax.swing.JMenuItem jm3DView;
    private javax.swing.JMenuItem jmAbout;
    private javax.swing.JMenu jmAnalyze;
    private javax.swing.JMenuItem jmCloseProject;
    private javax.swing.JMenuItem jmContactUs;
    private javax.swing.JMenu jmConverters;
    private javax.swing.JMenuItem jmCopy;
    private javax.swing.JMenuItem jmCustomize;
    private javax.swing.JMenuItem jmCut;
    private javax.swing.JMenu jmDatabases;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenuItem jmEditIdent;
    private javax.swing.JMenuItem jmEditQuant;
    private javax.swing.JMenuItem jmExit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenuItem jmHelpContent;
    private javax.swing.JMenuItem jmImportFile;
    private javax.swing.JMenuItem jmLog;
    private javax.swing.JMenuItem jmMGFView;
    private javax.swing.JMenuBar jmMain;
    private javax.swing.JMenuItem jmMascotXMLView;
    private javax.swing.JMenuItem jmMzIdentMLView;
    private javax.swing.JMenuItem jmMzML2MGF;
    private javax.swing.JMenuItem jmMzMLView;
    private javax.swing.JMenuItem jmMzQuantMLView;
    private javax.swing.JMenuItem jmNewProject;
    private javax.swing.JMenuItem jmOpenProject;
    private javax.swing.JMenuItem jmOpenRecentProject;
    private javax.swing.JMenuItem jmOptions;
    private javax.swing.JMenuItem jmPaste;
    private javax.swing.JMenuItem jmPrint;
    private javax.swing.JMenu jmProject;
    private javax.swing.JMenuItem jmRawData;
    private javax.swing.JMenuItem jmRunQuantAnalysis;
    private javax.swing.JMenuItem jmSaveProject;
    private javax.swing.JMenuItem jmSaveProjectAs;
    private javax.swing.JMenuItem jmShowProjectFiles;
    private javax.swing.JMenuItem jmShowProperties;
    private javax.swing.JMenuItem jmShowViewer;
    private javax.swing.JMenuItem jmSpectrum;
    private javax.swing.JMenuItem jmSubmitPRIDE;
    private javax.swing.JMenuItem jmTIC;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenu jmView;
    private javax.swing.JMenu jmWindow;
    private javax.swing.JPanel jp2D;
    private javax.swing.JPanel jp3D;
    private javax.swing.JPanel jpFeatureQuantHeader;
    private javax.swing.JPanel jpLeftMenuBottom;
    private javax.swing.JPanel jpLeftMenuTop;
    private javax.swing.JPanel jpLeftPanelView;
    private javax.swing.JPanel jpLeftViewer;
    private javax.swing.JPanel jpLeftViewerBottom;
    private javax.swing.JPanel jpLeftViewerDetails;
    private javax.swing.JPanel jpMGF;
    private javax.swing.JPanel jpMGFMenu;
    private javax.swing.JPanel jpMainPanelView;
    private javax.swing.JPanel jpMascotXML;
    private javax.swing.JPanel jpMascotXMLMenu;
    private javax.swing.JPanel jpMzId;
    private javax.swing.JPanel jpMzIdMenu;
    private javax.swing.JScrollPane jpMzIdSubDetail;
    private javax.swing.JPanel jpMzML;
    private javax.swing.JPanel jpMzMLMenu;
    private javax.swing.JPanel jpMzQuantML;
    private javax.swing.JPanel jpPeptideQuant;
    private javax.swing.JPanel jpPeptideQuantHeader;
    private javax.swing.JPanel jpProjectDetails;
    private javax.swing.JPanel jpProjectHeader;
    private javax.swing.JPanel jpProjectStatus;
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpPropertiesBox;
    private javax.swing.JPanel jpPropetiesTab;
    private javax.swing.JPanel jpProteinQuant;
    private javax.swing.JPanel jpProteinQuantHeader;
    private javax.swing.JPanel jpQuantFiles;
    private javax.swing.JPanel jpRawDataValues;
    private javax.swing.JPanel jpRawDataValuesMenu;
    private javax.swing.JPanel jpTIC;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpViewerAndProperties;
    private javax.swing.JSplitPane jspFeatureQuant;
    private javax.swing.JScrollPane jspFeatureQuantDetail;
    private javax.swing.JScrollPane jspIdentFiles;
    private javax.swing.JSplitPane jspLeftMenuBottom;
    private javax.swing.JSplitPane jspLeftPanelView;
    private javax.swing.JSplitPane jspLeftViewer;
    private javax.swing.JSplitPane jspLeftViewerDetails;
    private javax.swing.JPanel jspLeftViewerHeader;
    private javax.swing.JScrollPane jspLog;
    private javax.swing.JSplitPane jspMGF;
    private javax.swing.JSplitPane jspMGFDetail;
    private javax.swing.JScrollPane jspMGFHeader;
    private javax.swing.JScrollPane jspMGFSubDetail;
    private javax.swing.JSplitPane jspMainPanelView;
    private javax.swing.JSplitPane jspMascotXML;
    private javax.swing.JScrollPane jspMascotXMLDetail;
    private javax.swing.JScrollPane jspMascotXMLHeader;
    private javax.swing.JSplitPane jspMascotXMLSubDetail;
    private javax.swing.JSplitPane jspMzId;
    private javax.swing.JSplitPane jspMzIdDetail;
    private javax.swing.JScrollPane jspMzIdHeader;
    private javax.swing.JSplitPane jspMzML;
    private javax.swing.JSplitPane jspMzMLDetail;
    private javax.swing.JScrollPane jspMzMLHeader;
    private javax.swing.JScrollPane jspMzMLSubDetail;
    private javax.swing.JSplitPane jspMzQuantML;
    private javax.swing.JScrollPane jspMzQuantMLHeader;
    private javax.swing.JSplitPane jspPeptideQuant;
    private javax.swing.JScrollPane jspPeptideQuantDetail;
    private javax.swing.JSplitPane jspProjectDetails;
    private javax.swing.JSplitPane jspProperties;
    private javax.swing.JSplitPane jspProteinQuant;
    private javax.swing.JScrollPane jspProteinQuantDetail;
    private javax.swing.JScrollPane jspQuantFiles;
    private javax.swing.JScrollPane jspRawData;
    private javax.swing.JSplitPane jspRawDataValues;
    private javax.swing.JScrollPane jspRawFiles;
    private javax.swing.JSplitPane jspViewerAndProperties;
    private javax.swing.JTable jtFeatureQuant;
    private javax.swing.JTable jtIdentFiles;
    private javax.swing.JTable jtMGF;
    private javax.swing.JTextField jtMSIndex;
    private javax.swing.JTextField jtMSMz;
    private javax.swing.JTable jtMascotXMLView;
    private javax.swing.JTable jtMzId;
    private javax.swing.JTable jtMzML;
    private javax.swing.JTextField jtPeptide;
    private javax.swing.JTextField jtPeptideMzId;
    private javax.swing.JTable jtPeptideQuant;
    private javax.swing.JTextField jtProtein;
    private javax.swing.JTextField jtProteinMzId;
    private javax.swing.JTable jtProteinQuant;
    private javax.swing.JTable jtQuantFiles;
    private javax.swing.JTextField jtRT;
    private javax.swing.JTable jtRawData;
    private javax.swing.JTable jtRawFiles;
    private javax.swing.JTextField jtScan;
    private javax.swing.JTextField jtScanTitle;
    private javax.swing.JTable jtTemplate1;
    private javax.swing.JTable jtTemplate2;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextArea jtaMGFView;
    private javax.swing.JTextArea jtaMascotXML;
    private javax.swing.JTextArea jtaMzIdView;
    private javax.swing.JTextArea jtaMzML;
    private javax.swing.JTextArea jtaMzQuantML;
    private javax.swing.JTabbedPane jtpIdentFiles;
    private javax.swing.JTabbedPane jtpLog;
    private javax.swing.JTabbedPane jtpMzQuantMLDetail;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpQuantFiles;
    private javax.swing.JTabbedPane jtpRawFiles;
    private javax.swing.JTabbedPane jtpViewer;
    // End of variables declaration//GEN-END:variables
}
