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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.proteosuite.gui.*;
import org.proteosuite.test.IPC;
import org.proteosuite.test.IPC.Options;
import org.proteosuite.test.IPC.Results;
import org.proteosuite.test.ViewChartGUI;
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
import uk.ac.ebi.jmzml.model.mzml.InstrumentConfigurationList;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroupList;
import uk.ac.ebi.jmzml.model.mzml.SoftwareList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;
import xtracker.xTracker;

        
/**
 * This class corresponds to the main form in ProteoSuite. The form includes the visualisation of data and other associated tools.
 * @author faviel
 */
public class ProteoSuiteView extends JFrame {

    //... Project settings ...//
    private String sProjectName;
    private String sWorkspace;
    private boolean bProjectModified;

    //... List of unmarshaller objects ...//
    private ArrayList<MzMLUnmarshaller> alUnmarshaller = null;    
    
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
        setTitle("ProteoSuite 0.2.0 (Beta Version) - <Project: " + sProjectName +  ">         http://www.proteosuite.org");
        
        //... Setting project icons ...//        
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
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
        
        JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View");
        Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMascotIcon.setIcon(propertiesMascotIcon);
        jlPropertiesMascotIcon.setIconTextGap(5);
        jlPropertiesMascotIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(1, jlPropertiesMascotIcon);        
        
        JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View");
        Icon propertiesMzQuantMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzQuantMLIcon.setIcon(propertiesMzQuantMLIcon);
        jlPropertiesMzQuantMLIcon.setIconTextGap(5);
        jlPropertiesMzQuantMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(2, jlPropertiesMzQuantMLIcon);                
        
        //... Visualisation Icons ...//
        jbShowChromatogram.setIcon(TICIcon);
        jbShow2D.setIcon(twoDIcon);
        
        //... Setting Window Height and Width ...//
        this.setMinimumSize(new Dimension(1024, 780));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //... Setting dividers ...//
        jspMainPanelView.setDividerLocation(250); //... Left Menu ...//
        jspLeftMenuBottom.setDividerLocation(200); //... Ident and Quantitation separator ...//
        jspLeftViewerDetails.setDividerLocation(480); //... Viewer Height ...//
        jspViewerAndProperties.setDividerLocation(600); //... Viewer Width  ...//
        jspMzML.setDividerLocation(100); //... MzML data ...//
        jspProjectDetails.setDividerLocation(200); //... MzML files ...//
        jspLeftMenuBottom.setDividerLocation(200); //... IdentML files ...//
        jtpViewer.setSelectedIndex(0);
        jlFiles.requestFocusInWindow();
        
//        jtMzML.setAutoCreateRowSorter(true);
//        jtMascotXMLView.setAutoCreateRowSorter(true);
//        jtRawData.setAutoCreateRowSorter(true);
        
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
        jspRawData = new javax.swing.JScrollPane();
        jtRawData = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtTemplate = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTemplate2 = new javax.swing.JTable();
        jtpViewer = new javax.swing.JTabbedPane();
        jdpMS = new javax.swing.JDesktopPane();
        jpTIC = new javax.swing.JPanel();
        jdpTIC = new javax.swing.JDesktopPane();
        jp2D = new javax.swing.JPanel();
        jdp2D = new javax.swing.JDesktopPane();
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
        jlVisualisationOptions = new javax.swing.JLabel();
        jbShowChromatogram = new javax.swing.JButton();
        jbShow2D = new javax.swing.JButton();
        jlScan = new javax.swing.JLabel();
        jtScan = new javax.swing.JTextField();
        jlRT = new javax.swing.JLabel();
        jtRT = new javax.swing.JTextField();
        jlSearchMzML = new javax.swing.JLabel();
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
        jbGenerateTemplate = new javax.swing.JButton();
        jbShowIsotopeDistrib = new javax.swing.JButton();
        jpMzQuantML = new javax.swing.JPanel();
        jspMzQuantML = new javax.swing.JSplitPane();
        jspMzQuantMLHeader = new javax.swing.JScrollPane();
        jtaMzQuantML = new javax.swing.JTextArea();
        jspMzQuantMLView = new javax.swing.JScrollPane();
        jtMzQuantMLView = new javax.swing.JTable();
        jpProjectStatus = new javax.swing.JPanel();
        jlRawFiles = new javax.swing.JLabel();
        jlIdentFiles = new javax.swing.JLabel();
        jlTechnique = new javax.swing.JLabel();
        jlQuantFiles = new javax.swing.JLabel();
        jlRawFilesStatus = new javax.swing.JLabel();
        jlIdentFilesStatus = new javax.swing.JLabel();
        jlQuantFilesStatus = new javax.swing.JLabel();
        jcbTechnique = new javax.swing.JComboBox();
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
        jmStatistics = new javax.swing.JMenu();
        jmTools = new javax.swing.JMenu();
        jmConverters = new javax.swing.JMenu();
        jmMzML2MGF = new javax.swing.JMenuItem();
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
        jmMascotXMLView = new javax.swing.JMenuItem();
        jmMzQuantMLView = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jmLog = new javax.swing.JMenuItem();
        jmRawData = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jmHelpContent = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
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

        jpMainPanelView.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jspMainPanelView.setDividerLocation(280);
        jspMainPanelView.setDividerSize(3);

        jpLeftPanelView.setBackground(new java.awt.Color(204, 204, 255));

        jspLeftPanelView.setDividerLocation(20);
        jspLeftPanelView.setDividerSize(1);
        jspLeftPanelView.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpProjectHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
        jpProjectHeader.setMinimumSize(new java.awt.Dimension(279, 25));
        jpProjectHeader.setPreferredSize(new java.awt.Dimension(280, 25));

        jlFiles.setBackground(new java.awt.Color(255, 255, 255));
        jlFiles.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlFiles.setForeground(new java.awt.Color(102, 102, 102));
        jlFiles.setText("Files");

        javax.swing.GroupLayout jpProjectHeaderLayout = new javax.swing.GroupLayout(jpProjectHeader);
        jpProjectHeader.setLayout(jpProjectHeaderLayout);
        jpProjectHeaderLayout.setHorizontalGroup(
            jpProjectHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlFiles)
                .addContainerGap(244, Short.MAX_VALUE))
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
        jspProjectDetails.setDividerSize(2);
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtRawFilesMouseEntered(evt);
            }
        });
        jspRawFiles.setViewportView(jtRawFiles);

        jtpRawFiles.addTab("Raw Files", jspRawFiles);

        javax.swing.GroupLayout jpLeftMenuTopLayout = new javax.swing.GroupLayout(jpLeftMenuTop);
        jpLeftMenuTop.setLayout(jpLeftMenuTopLayout);
        jpLeftMenuTopLayout.setHorizontalGroup(
            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpRawFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
        );
        jpLeftMenuTopLayout.setVerticalGroup(
            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpRawFiles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );

        jspProjectDetails.setTopComponent(jpLeftMenuTop);

        jspLeftMenuBottom.setDividerLocation(200);
        jspLeftMenuBottom.setDividerSize(2);
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
        jspQuantFiles.setViewportView(jtQuantFiles);

        jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);

        javax.swing.GroupLayout jpQuantFilesLayout = new javax.swing.GroupLayout(jpQuantFiles);
        jpQuantFiles.setLayout(jpQuantFilesLayout);
        jpQuantFilesLayout.setHorizontalGroup(
            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        jpQuantFilesLayout.setVerticalGroup(
            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtIdentFilesMouseEntered(evt);
            }
        });
        jspIdentFiles.setViewportView(jtIdentFiles);

        jtpIdentFiles.addTab("Identification Files", jspIdentFiles);

        jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

        javax.swing.GroupLayout jpLeftMenuBottomLayout = new javax.swing.GroupLayout(jpLeftMenuBottom);
        jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
        jpLeftMenuBottomLayout.setHorizontalGroup(
            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );
        jpLeftMenuBottomLayout.setVerticalGroup(
            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpLeftMenuBottomLayout.createSequentialGroup()
                .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );

        jspProjectDetails.setRightComponent(jpLeftMenuBottom);

        javax.swing.GroupLayout jpProjectDetailsLayout = new javax.swing.GroupLayout(jpProjectDetails);
        jpProjectDetails.setLayout(jpProjectDetailsLayout);
        jpProjectDetailsLayout.setHorizontalGroup(
            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProjectDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
        jpProjectDetailsLayout.setVerticalGroup(
            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProjectDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );

        jspLeftPanelView.setRightComponent(jpProjectDetails);

        javax.swing.GroupLayout jpLeftPanelViewLayout = new javax.swing.GroupLayout(jpLeftPanelView);
        jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
        jpLeftPanelViewLayout.setHorizontalGroup(
            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
        );
        jpLeftPanelViewLayout.setVerticalGroup(
            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        jspMainPanelView.setLeftComponent(jpLeftPanelView);

        jpViewerAndProperties.setBackground(new java.awt.Color(204, 204, 255));

        jspViewerAndProperties.setDividerLocation(380);
        jspViewerAndProperties.setDividerSize(3);

        jspLeftViewer.setDividerLocation(20);
        jspLeftViewer.setDividerSize(1);
        jspLeftViewer.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jspLeftViewerHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
        jspLeftViewerHeader.setMinimumSize(new java.awt.Dimension(380, 25));
        jspLeftViewerHeader.setPreferredSize(new java.awt.Dimension(380, 25));

        jlViewer.setBackground(new java.awt.Color(255, 255, 255));
        jlViewer.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlViewer.setForeground(new java.awt.Color(102, 102, 102));
        jlViewer.setText("Viewer");

        javax.swing.GroupLayout jspLeftViewerHeaderLayout = new javax.swing.GroupLayout(jspLeftViewerHeader);
        jspLeftViewerHeader.setLayout(jspLeftViewerHeaderLayout);
        jspLeftViewerHeaderLayout.setHorizontalGroup(
            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlViewer)
                .addContainerGap(332, Short.MAX_VALUE))
        );
        jspLeftViewerHeaderLayout.setVerticalGroup(
            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                .addComponent(jlViewer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspLeftViewer.setTopComponent(jspLeftViewerHeader);

        jpLeftViewerDetails.setBackground(new java.awt.Color(255, 255, 255));

        jspLeftViewerDetails.setDividerLocation(350);
        jspLeftViewerDetails.setDividerSize(2);
        jspLeftViewerDetails.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaLog.setColumns(20);
        jtaLog.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaLog.setRows(5);
        jspLog.setViewportView(jtaLog);

        jtpLog.addTab("Log", jspLog);

        jspRawDataValues.setDividerLocation(26);
        jspRawDataValues.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpRawDataValuesMenu.setMaximumSize(new java.awt.Dimension(32767, 50));
        jpRawDataValuesMenu.setMinimumSize(new java.awt.Dimension(0, 50));
        jpRawDataValuesMenu.setPreferredSize(new java.awt.Dimension(462, 50));

        javax.swing.GroupLayout jpRawDataValuesMenuLayout = new javax.swing.GroupLayout(jpRawDataValuesMenu);
        jpRawDataValuesMenu.setLayout(jpRawDataValuesMenuLayout);
        jpRawDataValuesMenuLayout.setHorizontalGroup(
            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        jpRawDataValuesMenuLayout.setVerticalGroup(
            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
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
            .addComponent(jspRawDataValues, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );
        jpRawDataValuesLayout.setVerticalGroup(
            jpRawDataValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspRawDataValues, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );

        jtpLog.addTab("Raw Data", jpRawDataValues);

        jtTemplate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Peptide Index", "Isotope m/z", "Isotope Rel Int"
            }
        ));
        jScrollPane1.setViewportView(jtTemplate);

        jButton1.setText("Generate Second Template");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(200, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
        );

        jtpLog.addTab("Template 1", jPanel1);

        jtTemplate2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "m/z Index", "x", "y", "i"
            }
        ));
        jScrollPane2.setViewportView(jtTemplate2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );

        jtpLog.addTab("Template 2", jPanel2);

        javax.swing.GroupLayout jpLeftViewerBottomLayout = new javax.swing.GroupLayout(jpLeftViewerBottom);
        jpLeftViewerBottom.setLayout(jpLeftViewerBottomLayout);
        jpLeftViewerBottomLayout.setHorizontalGroup(
            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        jpLeftViewerBottomLayout.setVerticalGroup(
            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
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
            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );
        jpTICLayout.setVerticalGroup(
            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
        );

        jtpViewer.addTab("TIC", jpTIC);

        jp2D.setBackground(new java.awt.Color(255, 255, 255));

        jdp2D.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp2DLayout = new javax.swing.GroupLayout(jp2D);
        jp2D.setLayout(jp2DLayout);
        jp2DLayout.setHorizontalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp2DLayout.createSequentialGroup()
                .addContainerGap(347, Short.MAX_VALUE)
                .addComponent(jdp2D, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jp2DLayout.setVerticalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp2DLayout.createSequentialGroup()
                .addContainerGap(282, Short.MAX_VALUE)
                .addComponent(jdp2D, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtpViewer.addTab("2D View", jp2D);

        jp3D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp3DLayout = new javax.swing.GroupLayout(jp3D);
        jp3D.setLayout(jp3DLayout);
        jp3DLayout.setHorizontalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
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
            .addComponent(jspLeftViewerDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
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
            .addComponent(jspLeftViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );

        jspViewerAndProperties.setLeftComponent(jpLeftViewer);

        jspProperties.setDividerLocation(20);
        jspProperties.setDividerSize(1);
        jspProperties.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpProperties.setMaximumSize(new java.awt.Dimension(32767, 25));
        jpProperties.setMinimumSize(new java.awt.Dimension(302, 25));
        jpProperties.setPreferredSize(new java.awt.Dimension(313, 25));

        jlProperties.setBackground(new java.awt.Color(255, 255, 255));
        jlProperties.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlProperties.setForeground(new java.awt.Color(102, 102, 102));
        jlProperties.setText("Properties");

        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
        jpProperties.setLayout(jpPropertiesLayout);
        jpPropertiesLayout.setHorizontalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPropertiesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlProperties)
                .addContainerGap(361, Short.MAX_VALUE))
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
        jspMzML.setDividerSize(2);
        jspMzML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzML.setColumns(20);
        jtaMzML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMzML.setRows(5);
        jspMzMLHeader.setViewportView(jtaMzML);

        jspMzML.setLeftComponent(jspMzMLHeader);

        jspMzMLDetail.setDividerLocation(80);
        jspMzMLDetail.setDividerSize(2);
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

        jlVisualisationOptions.setFont(new java.awt.Font("Tahoma", 1, 11));
        jlVisualisationOptions.setText("View:");

        jbShowChromatogram.setToolTipText("Show Chromatogram");
        jbShowChromatogram.setMaximumSize(new java.awt.Dimension(24, 24));
        jbShowChromatogram.setMinimumSize(new java.awt.Dimension(24, 24));
        jbShowChromatogram.setPreferredSize(new java.awt.Dimension(24, 24));
        jbShowChromatogram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbShowChromatogramMouseClicked(evt);
            }
        });

        jbShow2D.setToolTipText("Show 2D Plot");
        jbShow2D.setMaximumSize(new java.awt.Dimension(24, 24));
        jbShow2D.setMinimumSize(new java.awt.Dimension(24, 24));
        jbShow2D.setPreferredSize(new java.awt.Dimension(24, 24));
        jbShow2D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbShow2DMouseClicked(evt);
            }
        });
        jbShow2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbShow2DActionPerformed(evt);
            }
        });

        jlScan.setText("Scan:");

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
                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlVisualisationOptions)
                    .addComponent(jlSearchMzML))
                .addGap(18, 18, 18)
                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                        .addComponent(jlScan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtScan, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlRT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtRT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                        .addComponent(jbShowChromatogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jbShow2D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jpMzMLMenuLayout.setVerticalGroup(
            jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jlVisualisationOptions)
                    .addComponent(jbShowChromatogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbShow2D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );
        jpMzMLLayout.setVerticalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzML View", jpMzML);

        jspMascotXML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMascotXML.setColumns(20);
        jtaMascotXML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMascotXML.setRows(5);
        jspMascotXMLHeader.setViewportView(jtaMascotXML);

        jspMascotXML.setTopComponent(jspMascotXMLHeader);

        jspMascotXMLSubDetail.setDividerLocation(80);
        jspMascotXMLSubDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtMascotXMLView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Protein", "Peptide", "Composition", "Exp Mz", "Exp Mr", "Charge", "Score", "Scan", "RT (sec)"
            }
        ));
        jtMascotXMLView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMascotXMLViewMouseClicked(evt);
            }
        });
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

        jbGenerateTemplate.setText("Generate Template");
        jbGenerateTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerateTemplateActionPerformed(evt);
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
                        .addComponent(jtPeptide, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                        .addComponent(jbGenerateTemplate)
                        .addGap(18, 18, 18)
                        .addComponent(jbShowIsotopeDistrib)))
                .addContainerGap())
        );
        jpMascotXMLMenuLayout.setVerticalGroup(
            jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGenerateTemplate)
                    .addComponent(jbShowIsotopeDistrib))
                .addGap(11, 11, 11)
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
            .addComponent(jspMascotXML, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );
        jpMascotXMLLayout.setVerticalGroup(
            jpMascotXMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMascotXML, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );

        jtpProperties.addTab("Mascot XML View", jpMascotXML);

        jspMzQuantML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzQuantML.setColumns(20);
        jtaMzQuantML.setRows(5);
        jspMzQuantMLHeader.setViewportView(jtaMzQuantML);

        jspMzQuantML.setTopComponent(jspMzQuantMLHeader);

        jtMzQuantMLView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Column 1", "Column 2", "Column 3", "Column 4"
            }
        ));
        jspMzQuantMLView.setViewportView(jtMzQuantMLView);

        jspMzQuantML.setRightComponent(jspMzQuantMLView);

        javax.swing.GroupLayout jpMzQuantMLLayout = new javax.swing.GroupLayout(jpMzQuantML);
        jpMzQuantML.setLayout(jpMzQuantMLLayout);
        jpMzQuantMLLayout.setHorizontalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzQuantML, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
        );
        jpMzQuantMLLayout.setVerticalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzQuantML, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzQuantML View", jpMzQuantML);

        javax.swing.GroupLayout jpPropetiesTabLayout = new javax.swing.GroupLayout(jpPropetiesTab);
        jpPropetiesTab.setLayout(jpPropetiesTabLayout);
        jpPropetiesTabLayout.setHorizontalGroup(
            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        jpPropetiesTabLayout.setVerticalGroup(
            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
        );

        jspProperties.setRightComponent(jpPropetiesTab);

        javax.swing.GroupLayout jpPropertiesBoxLayout = new javax.swing.GroupLayout(jpPropertiesBox);
        jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
        jpPropertiesBoxLayout.setHorizontalGroup(
            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );
        jpPropertiesBoxLayout.setVerticalGroup(
            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
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
            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        jpMainPanelViewLayout.setVerticalGroup(
            jpMainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );

        jpProjectStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Project: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(102, 102, 102))); // NOI18N
        jpProjectStatus.setForeground(new java.awt.Color(153, 153, 153));
        jpProjectStatus.setPreferredSize(new java.awt.Dimension(102, 26));

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
                .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(446, Short.MAX_VALUE))
        );
        jpProjectStatusLayout.setVerticalGroup(
            jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jlRawFiles)
                .addComponent(jlRawFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jlIdentFiles)
                .addComponent(jlIdentFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jlTechnique)
                .addComponent(jcbTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jlQuantFiles)
                .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jmCloseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCloseProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmCloseProject);
        jmFile.add(jSeparator3);

        jmSaveProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmSaveProject.setText("Save Project");
        jmSaveProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSaveProjectActionPerformed(evt);
            }
        });
        jmFile.add(jmSaveProject);

        jmSaveProjectAs.setText("Save Project As");
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

        jmShowProjectFiles.setText("Show Project Files");
        jmShowProjectFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowProjectFilesActionPerformed(evt);
            }
        });
        jmView.add(jmShowProjectFiles);

        jmShowViewer.setText("Show Viewer");
        jmShowViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowViewerActionPerformed(evt);
            }
        });
        jmView.add(jmShowViewer);

        jmShowProperties.setText("Show Properties");
        jmShowProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmShowPropertiesActionPerformed(evt);
            }
        });
        jmView.add(jmShowProperties);

        jmMain.add(jmView);

        jmProject.setText("Project");

        jmEditIdent.setText("Set Identification Parameters");
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

        jmStatistics.setText("Statistics");
        jmMain.add(jmStatistics);

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
                .addComponent(jpProjectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        //... Validate modification options ...//
        if (this.bProjectModified) //... Project changes = false when initialised ...//
        {
            if (this.sProjectName.equals("New")) //... 
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save this project?", "New Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    System.out.println("Yes, I want to save all changes.");
                    boolean bSaved = saveProject();
                    if (bSaved)
                    {
                        initProjectValues();
                    }                    
                }
                else
                {                    
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        System.out.println("No, I don't want to save any changes.");
                        initProjectValues();
                    }
                }
            }            
            else
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save recent changes?", "New Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    //saveProject();
                    initProjectValues();
                }                
                else
                {
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        initProjectValues();
                    }
                }
            }
        }
    }//GEN-LAST:event_jmNewProjectActionPerformed
   
    private void jmImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmImportFileActionPerformed
                
        //... Selecting file(s) ...//
        JFileChooser chooser = new JFileChooser("user.home");
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Raw Files (*.mzML, *.mgf)", "mzML", "mgf");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Identification Files (*.mzid, *.xml)", "mzid", "xml");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq)", "mzq");

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
            this.bProjectModified = true;
            
            //... A common experiment will have around 4-20 raw files ....//
            final File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0)
            {
                //... Code to be inserted here ...//
                if (aFiles[0].getName().indexOf(".mzML") > 0) 
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
                            alUnmarshaller = new ArrayList<MzMLUnmarshaller>();
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++)
                            {
                                File xmlFile = new File(aFiles[iI].getPath());                        

                                progressBarDialog.setTitle("Reading " + xmlFile.getName());
                                progressBarDialog.setVisible(true);                                
                                //... Unmarshall data using jzmzML API ...//
                                unmarshalMzMLFile(model, iI, xmlFile);
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
                                model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), aFiles[iI].getPath(),
                                                                                          "Mascot Generic File (.mgf)",
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
                                //... Unmarshall data using jzmzML API ...//
                                model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), aFiles[iI].getPath(), "Mascot XML file (.xml)", "N/A"});
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
                if (aFiles[0].getName().indexOf(".mzid") >0) 
                {   
                    JOptionPane.showMessageDialog(this, "The module for .mzid files is under development.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
                }                
                if (aFiles[0].getName().indexOf(".mzq") >0) 
                {
                    JOptionPane.showMessageDialog(this, "The module for .mzq files is under development.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
                }
	    } //... From Files
            
        }  //... From If                
    }//GEN-LAST:event_jmImportFileActionPerformed

    private void jmOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenProjectActionPerformed
        
        //... Validate modification options ...//
        if (this.bProjectModified)
        {
            if (this.sProjectName.equals("New"))
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save the current project?", "Open Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    boolean bSaved = saveProject();
                    if (bSaved)
                    {
                        initProjectValues();
                        openProject();
                    }                    
                }
                else
                {                    
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        initProjectValues();
                        openProject();
                    }
                }
            }            
            else
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save recent changes?", "New Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    boolean bSaved = saveProject();
                    if (bSaved)
                    {
                        initProjectValues();
                        openProject();
                    }                  
                }                
                else
                {
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        initProjectValues();
                        openProject();
                    }
                }
            }
        }
        else
        {
            initProjectValues();
            openProject();            
        }
        
    }//GEN-LAST:event_jmOpenProjectActionPerformed

    private void jmOpenRecentProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenRecentProjectActionPerformed
        
        
    }//GEN-LAST:event_jmOpenRecentProjectActionPerformed

    private void jmSaveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectActionPerformed
        
        if (this.bProjectModified)
        {
            writeConfigFile(sProjectName);
        }        
    }//GEN-LAST:event_jmSaveProjectActionPerformed

    private void jmSaveProjectAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectAsActionPerformed
        
        saveProject();
    }//GEN-LAST:event_jmSaveProjectAsActionPerformed

    private void jmCloseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCloseProjectActionPerformed
        //... Validate modification options ...//
        if (this.bProjectModified)
        {
            if (this.sProjectName.equals("New"))
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save this project?", "Closing project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    boolean bSaved = saveProject();
                    if (bSaved)
                    {
                        initProjectValues();
                    }                    
                }
                else
                {                    
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        initProjectValues();
                    }
                }
            }            
            else
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save recent changes?", "Closing Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    //saveProject();
                    initProjectValues();
                }                
                else
                {
                    if (option == JOptionPane.NO_OPTION) 
                    {                    
                        initProjectValues();                        
                    }
                }
            }
        }
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
        if (jspMainPanelView.getDividerLocation() == 0)
        {
            jspMainPanelView.setDividerLocation(250); 
            jmShowProjectFiles.setIcon(null);
        }
        else
        {
            jspMainPanelView.setDividerLocation(0);
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
            jmShowProjectFiles.setIcon(thick);
        }
    }//GEN-LAST:event_jmShowProjectFilesActionPerformed

    private void jmShowViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowViewerActionPerformed
        if (jspViewerAndProperties.getDividerLocation() == 0)
        {
            jspViewerAndProperties.setDividerLocation(600); 
            jmShowViewer.setIcon(null);
        }
        else
        {
            jspViewerAndProperties.setDividerLocation(0); 
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
            jmShowViewer.setIcon(thick);
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
        }
    }//GEN-LAST:event_jtMzMLMouseClicked

    private void jtRawFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtRawFilesMouseClicked
       
        if ((evt.getButton() == 1)&&(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2).toString().equals("mzML"))) {            
            loadMzMLView();         
        }
    }//GEN-LAST:event_jtRawFilesMouseClicked

    private void jmMzML2MGFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzML2MGFActionPerformed
        
        //... Load MzML2MGF GUI ...//
        final JFrame jfWinParams = new JFrame("Convert mzML files to MGF");        
        MzML2MGFView winParams = new MzML2MGFView(jfWinParams, this.sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(400, 300);
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

    private void jbShowChromatogramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbShowChromatogramMouseClicked
        //... Display chromatogram view ...//
        if (jtRawFiles.getSelectedRow() < 0)
        {
            JOptionPane.showMessageDialog(this, "Select one raw file to display the chromatogram view", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            showChromatogram(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_jbShowChromatogramMouseClicked

    private void jbShow2DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbShow2DMouseClicked
        //... Display 2D view ...//
        if (jtRawFiles.getSelectedRow() < 0)
        {
            JOptionPane.showMessageDialog(this, "Select one raw file to display the 2D view", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            this.show2DPlot(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
        }
    }//GEN-LAST:event_jbShow2DMouseClicked

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
        final JFrame jfQuantParams = new JFrame("Edit Identification Parameters");
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
        else
        {
            //... In this thread, we call xTracker module based on the parameter files we generated using proteosuite ...//
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
                    //... Generate config files for xTracker ...//
                    writeXTrackerConfigFiles();
                    //... Run xTracker ...///
                    xTracker run = new xTracker(sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_Conf.xtc");
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();
                }
            }.start();                    
        }       
    }//GEN-LAST:event_jmRunQuantAnalysisActionPerformed

    private void jmSpectrumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSpectrumActionPerformed
        jtpViewer.setSelectedIndex(0);
    }//GEN-LAST:event_jmSpectrumActionPerformed

    private void jmTICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmTICActionPerformed
        jtpViewer.setSelectedIndex(1);
    }//GEN-LAST:event_jmTICActionPerformed

    private void jm2DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm2DViewActionPerformed
        jtpViewer.setSelectedIndex(2);
    }//GEN-LAST:event_jm2DViewActionPerformed

    private void jm3DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm3DViewActionPerformed
        jtpViewer.setSelectedIndex(3);
    }//GEN-LAST:event_jm3DViewActionPerformed

    private void jmMzMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzMLViewActionPerformed
        jtpProperties.setSelectedIndex(0);
    }//GEN-LAST:event_jmMzMLViewActionPerformed

    private void jmMzQuantMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzQuantMLViewActionPerformed
        jtpProperties.setSelectedIndex(2);
    }//GEN-LAST:event_jmMzQuantMLViewActionPerformed

    private void jmLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmLogActionPerformed
        jtpLog.setSelectedIndex(0);
    }//GEN-LAST:event_jmLogActionPerformed

    private void jmRawDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRawDataActionPerformed
        jtpLog.setSelectedIndex(1);
    }//GEN-LAST:event_jmRawDataActionPerformed

    private void jmMascotXMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMascotXMLViewActionPerformed
        jtpProperties.setSelectedIndex(1);
    }//GEN-LAST:event_jmMascotXMLViewActionPerformed

    private void jtIdentFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtIdentFilesMouseClicked
        if ((evt.getButton() == 1)&&(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2).toString().equals("Mascot XML file (.xml)"))) {            
            loadMascotView();
        }        
    }//GEN-LAST:event_jtIdentFilesMouseClicked

    private void jtRawFilesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtRawFilesMouseEntered
        
    }//GEN-LAST:event_jtRawFilesMouseEntered

    private void jbShow2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbShow2DActionPerformed
        
    }//GEN-LAST:event_jbShow2DActionPerformed

    private void jtScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtScanKeyPressed
        String sChain = "";
        sChain = jtScan.getText() + evt.getKeyChar();
        searchValueInMzML(sChain, 2);
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
            searchValueInMascotXML(sChain, 0); 
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
            searchValueInMascotXML(sChain, 1); 
        }
    }//GEN-LAST:event_jtPeptideKeyPressed

    private void jtMascotXMLViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMascotXMLViewMouseClicked

    }//GEN-LAST:event_jtMascotXMLViewMouseClicked

    private void jbGenerateTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerateTemplateActionPerformed
        //... Check selection .../
        if (jtMascotXMLView.getSelectedRow()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one peptide", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            loadTemplate();
        }
    }//GEN-LAST:event_jbGenerateTemplateActionPerformed

    private void jtIdentFilesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtIdentFilesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtIdentFilesMouseEntered

    private void jbShowIsotopeDistribActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbShowIsotopeDistribActionPerformed
          new ViewChartGUI(jtMascotXMLView.getValueAt(jtMascotXMLView.getSelectedRow(), 2).toString(), 
                    jtMascotXMLView.getValueAt(jtMascotXMLView.getSelectedRow(), 6).toString(),
                    "10000").setVisible(true);
                
    }//GEN-LAST:event_jbShowIsotopeDistribActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //... Check selection .../
        if (jtTemplate.getSelectedRow()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one peptide", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            loadTemplate2();
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private void loadTemplate()     
    {
        DefaultTableModel model = new DefaultTableModel();
        jtTemplate.setModel(model);
        model.addColumn("Peptide Index");
        model.addColumn("Isotope m/z");
        model.addColumn("Isotope Relative Intensity");
        
        int rowIndexStart = jtMascotXMLView.getSelectedRow();
        int rowIndexEnd = jtMascotXMLView.getSelectionModel().getMaxSelectionIndex();

        for (int r=rowIndexStart; r<=rowIndexEnd; r++) {
            String[] args = {};
            args = new String[]{"-a", jtMascotXMLView.getValueAt(r, 2).toString(), 
                "-fc", "-z", jtMascotXMLView.getValueAt(r, 6).toString(), 
                "-f", "1000", "-r", "10000"};
            if (args.length == 0) {
                System.exit(0);
            }
            IPC ipc = new IPC();                 
            Options options = Options.parseArgs(args);
            options.setPrintOutput(false);
            Results res = ipc.execute(options);
            Object[] objArray = res.getPeaks().toArray();
            for(int iI=0; iI<objArray.length; iI++)
            {
                model.insertRow(model.getRowCount(), new Object[]{
                        Integer.parseInt(jtMascotXMLView.getValueAt(r, 0).toString()),
                        Float.parseFloat(String.format("%.4f", res.getPeaks().first().getMass())),
                        Float.parseFloat(String.format("%.4f", res.getPeaks().first().getRelInt()*100))                        
                    });                
                res.getPeaks().pollFirst();
            }                    
        }
        jtpLog.setSelectedIndex(2);
                
    }    
    private void loadTemplate2()     
    {
        DefaultTableModel model = new DefaultTableModel();
        jtTemplate2.setModel(model);
        model.addColumn("m/z Index");
        model.addColumn("x");
        model.addColumn("y");
        model.addColumn("i");
        
        int rowIndexStart = jtTemplate.getSelectedRow();
        int rowIndexEnd = jtTemplate.getSelectionModel().getMaxSelectionIndex();
        float factor1 = 0.0f, factor2 = 0.0f, relInt=0.0f;
        factor1 = (float)1/36;
        factor2 = (float)4/9;

        for (int r=rowIndexStart; r<=rowIndexEnd; r++) {
            relInt = Float.valueOf(jtTemplate.getValueAt(r, 2).toString().trim()).floatValue();

            for (int x=-1; x<2; x++)
            {
                for (int y=-1;y<2;y++)
                {
                    if((x!=0)||(y!=0))
                    {
                        model.insertRow(model.getRowCount(), new Object[]{
                            Float.parseFloat(jtTemplate.getValueAt(r, 1).toString()),
                            x,
                            y,
                            factor1*relInt
                        });
                    }
                }
            }
            model.insertRow(model.getRowCount(), new Object[]{
                Float.parseFloat(jtTemplate.getValueAt(r, 1).toString()),
                0,
                0,
                factor2*relInt
            });
        }
        jtpLog.setSelectedIndex(3);
    }
    private void searchValueInMzML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMzML.getModel();
        int nRow = dtm.getRowCount();
        for (int iI = 0 ; iI < nRow ; iI++)
        {                
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain))
            {
                jtMzML.setRowSelectionInterval(iI, iI);
                jtMzML.scrollRectToVisible(new Rectangle(jtMzML.getCellRect(iI, 0, true)));
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
        MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);            
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
    private void showRawData(int iIndex, String sID) {                                             
                
        DefaultTableModel model = new DefaultTableModel();
//        {  
//            Class[] types = new Class [] {             
//                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
//            };  
//            @Override  
//            public Class getColumnClass(int columnIndex) {  
//                return types [columnIndex];  
//            }  
//        };
        jtRawData.setModel(model);
        model.addColumn("Index");
        model.addColumn("m/z");
        model.addColumn("Intensity");
        
        jtpLog.setSelectedIndex(1);
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);            
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
               if (intenNumbers[iI].doubleValue() > 0)
               {
                   model.insertRow(model.getRowCount(), new Object[]{
                        iI,
                        Float.parseFloat(String.format("%.4f", mzNumbers[iI].doubleValue())),
                        Float.parseFloat(String.format("%.2f", intenNumbers[iI].doubleValue()))
                        });      
                    dSumIntensities = dSumIntensities + intenNumbers[iI].doubleValue();
               }
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
        
            jtpViewer.setSelectedIndex(1);

            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);
            try
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
            
            jtpViewer.setSelectedIndex(2);

            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);            

            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            //int iK = 0;
            String unitRT = "";
            //while ((spectrumIterator.hasNext())&&(iK<1000))
            while (spectrumIterator.hasNext())
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
                //iK++;
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
    private void loadMzMLView() 
    {                    
        DefaultTableModel model = new DefaultTableModel();
//        {  
//            Class[] types = new Class [] {             
//                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
//                java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
//            };  
//            @Override  
//            public Class getColumnClass(int columnIndex) {  
//                return types [columnIndex];  
//            }  
//        };
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
        String sVar="";
        
        MzMLUnmarshaller unmarshaller = alUnmarshaller.get(jtRawFiles.getSelectedRow());
        
        sOutput = "Name:\t\t" + unmarshaller.getMzMLId() + "\n";
        FileDescription fdList = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);
        List<CVParam> descParam = fdList.getSourceFileList().getSourceFile().get(0).getCvParam();
        for (Iterator lCVParamIterator = descParam.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            if (lCVParam.getAccession().equals("MS:1000768"))
            {
                sVar = lCVParam.getName().trim();
            }                        
        } 
        sOutput = sOutput + "Format:\t\t" + sVar + "\n";
        
        sVar = "";
        ReferenceableParamGroupList rpgList = unmarshaller.unmarshalFromXpath("/referenceableParamGroupList", ReferenceableParamGroupList.class);
        List<CVParam> refParam = rpgList.getReferenceableParamGroup().get(0).getCvParam();
        for (Iterator lCVParamIterator = refParam.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            sVar = sVar + lCVParam.getName().trim() + " ";
        } 
        sOutput = sOutput + "Instrument:\t\t" + sVar + "\n";
        
        sVar = "";
        SoftwareList softList = unmarshaller.unmarshalFromXpath("/softwareList", SoftwareList.class);
        List<CVParam> softParam = softList.getSoftware().get(0).getCvParam();
        for (Iterator lCVParamIterator = softParam.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            sVar = sVar + lCVParam.getName().trim() + " ";
        }
        sOutput = sOutput + "Software:\t\t" + sVar + "\n";

        sVar = "Source: ";
        InstrumentConfigurationList icList = unmarshaller.unmarshalFromXpath("/instrumentConfigurationList", InstrumentConfigurationList.class);
        List<CVParam> icParam = icList.getInstrumentConfiguration().get(0).getComponentList().getSource().get(0).getCvParam();
        for (Iterator lCVParamIterator = icParam.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            sVar = sVar + lCVParam.getName().trim() + ", ";
        }
        sVar = sVar + " Analyzer: ";
        List<CVParam> icParam2 = icList.getInstrumentConfiguration().get(0).getComponentList().getAnalyzer().get(0).getCvParam();
        for (Iterator lCVParamIterator = icParam2.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            sVar = sVar + lCVParam.getName().trim() + " ";
        }
        sVar = sVar + " Detector: ";
        List<CVParam> icParam3 = icList.getInstrumentConfiguration().get(0).getComponentList().getDetector().get(0).getCvParam();
        for (Iterator lCVParamIterator = icParam3.iterator(); lCVParamIterator.hasNext();)
        {
            CVParam lCVParam = (CVParam) lCVParamIterator.next();
            sVar = sVar + lCVParam.getName().trim() + " ";
        }
        sOutput = sOutput + "Configuration:\t" + sVar + "\n";
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
        
//        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
//
//        jtMzML.setRowSorter(sorter);
        
//        model.insertRow(model.getRowCount(), new Object[]{
//            "",
//            "", 
//            "Total MS1",
//            String.format("%.0f", dScansMS1),
//            "Total MS2",
//            String.format("%.0f", dScansMS2),
//            ""
//            });
        this.jtMzML.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        
        for (int i = 0; i < jtMzML.getModel().getRowCount(); i++) {
           for (int j = 0; j < jtMzML.getModel().getColumnCount(); j++) {
              DefaultTableCellRenderer renderer =
                 (DefaultTableCellRenderer)jtMzML.getCellRenderer(i, j);
              renderer.setHorizontalAlignment(JTextField.RIGHT);
           } 
        }        
    }   
    private void loadMascotView() 
    {  
        DefaultTableModel model = new DefaultTableModel()
        {  
            Class[] types = new Class [] {             
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
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
        model.addColumn("RT (sec)");
        String sOutput="";
                       
        sOutput = "Name:\t" + jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 0).toString() + "\n";
        sOutput = sOutput + "Path:\t" + jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 1).toString() + "\n";
        jtaMascotXML.setText(sOutput);  
        jtpProperties.setSelectedIndex(1);
        
        //... Open mascot file and extract identifications ...//
        File file = new File(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 1).toString());
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
                                                        if (peptideElem.getTextContent().toString().indexOf("rt=")>0) //... Option 1, reading on scan title ...//
                                                        {
                                                            String myTmpString = peptideElem.getTextContent().toString();
                                                            int ind = 0;
                                                            int ind1 = 0;

                                                            ind = myTmpString.indexOf("(rt=");
                                                            if(ind>0)
                                                            {
                                                                ind1 = myTmpString.indexOf(")");
                                                                retTime = Double.valueOf(myTmpString.substring(ind + 4, ind1)).floatValue();
                                                            }
                                                            else
                                                            {
                                                                retTime=0;
                                                            }
                                                            ind = myTmpString.indexOf("scans:");
                                                            if(ind>0)
                                                            {
                                                                ind1 = myTmpString.indexOf(",");
                                                                scanNumber = Double.valueOf(myTmpString.substring(ind + 6, ind1)).intValue();
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
        initSettings();                     
        initProjectStatus();
        
        //... Initialising components ...//
        initTables();                       
        initTextAreas();
        initViews();
    }     
    //... Initialise project settings (configuration file) ...//
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
            this.sProjectName = "New";
            this.sWorkspace = "";
            this.bProjectModified = false;
            String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
            sMessage = sMessage + "under your installation folder. ProteoSuite will continue launching, however \n";
            sMessage = sMessage + "it is recommended that you copy the file as indicated in the readme.txt file.";
            JOptionPane.showMessageDialog(this, sMessage, "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //... Initialise project status ...//
    private void initProjectStatus()
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
    }
    //... Initialise Text Areas ...//
    private void initTextAreas()
    {
        jtaLog.setText("");
        jtaMzML.setText("");
        jtaMzQuantML.setText("");               
    }    
    //... Initialise Visualisation Windows ...//
    private void initViews()
    {
        jdpMS.removeAll();
        jdpTIC.removeAll();
        jdp2D.removeAll();
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
                    alUnmarshaller = new ArrayList<MzMLUnmarshaller>();
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
                        File xmlFile = new File(this.sWorkspace+"\\"+jtRawFiles.getValueAt(iI, 1).toString());
                        unmarshalMzMLFile(model, iI, xmlFile);
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
    private void unmarshalMzMLFile(DefaultTableModel model, int iIndex, File xmlFile)
    {
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
            alUnmarshaller.add(unmarshaller);

            //... Creating object for samples ...//
//            aSamples[iI] = new Sample(unmarshaller.getMzMLId(),
//                                        aFiles[iI].getName(),
//                                        unmarshaller.getMzMLVersion(),
//                                        unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));


            //... Populate the JTree with spectra ...//                    
            int spectra = unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum");

            //... Reading spectrum data ...//
            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            int iJ = 0; //... For Spectrum Counter ...//
            int iK = 0; //... For Spectrum Counter ...//
            
            while (spectrumIterator.hasNext())
            {

                Spectrum spectrum = spectrumIterator.next();

                //... Reading CV param for retention time and MS level ...//
                String mslevel = "";                        
                List<CVParam> specParam = spectrum.getCvParam();
                for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();)
                {
                   CVParam lCVParam = (CVParam) lCVParamIterator.next();
                   if (lCVParam.getAccession().equals("MS:1000511"))
                   {
                       mslevel = lCVParam.getValue().trim();
                   }
                }

                double rt = 0.0;
                List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();)
                {
                   CVParam lCVParam = (CVParam) lCVParamIterator.next();
                   if (lCVParam.getAccession().equals("MS:1000016"))
                   {
                       rt = Double.parseDouble(lCVParam.getValue().trim());
                   }
                }

                //... Setting a more descriptive name for each node ...//
                DecimalFormat df = new DecimalFormat("#.0000");
                long spectrumIndex = spectrum.getIndex().longValue();

                spectrumIndex++;

                String nodeName = "Scan: " + spectrumIndex + " @ " + df.format(rt) + " mins - MS" + mslevel;

                //... Determining precursor list if exists (Add MSn as subnode) ...//
                PrecursorList plist = spectrum.getPrecursorList();
                if (plist != null)
                {
                    if (plist.getCount().intValue() == 1)
                    {                    
                        //ms2Node = new DefaultMutableTreeNode(nodeName);
                        //ms1Node.add(ms2Node);
                    }
                }
                else
                {
                   //ms1Node = new DefaultMutableTreeNode(nodeName);
                   //sampleNode.add(ms1Node);
                   iJ++;
                }
                iK++;
            }
            int mid= xmlFile.getName().lastIndexOf(".");
            String ext="";
            ext=xmlFile.getName().substring(mid+1,xmlFile.getName().length());             
            model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(),
                                                                      xmlFile.getPath(),
                                                                      ext,
                                                                      unmarshaller.getMzMLVersion(),
                                                                      spectra});            
    }
    //... Writing the XTrackerConfigFile module ...//
    private void writeXTrackerConfigFiles() 
    {       
        //... xTracker consists of 5 different plugins (read more on www.x-tracker.info) ...//
        writeXTrackerMain(jcbTechnique.getSelectedItem().toString());
        writeXTrackerRaw(jcbTechnique.getSelectedItem().toString());
        writeXTrackerIdent(jcbTechnique.getSelectedItem().toString());
        writeXTrackerPeakSel(jcbTechnique.getSelectedItem().toString());
        writeXTrackerQuantSel(jcbTechnique.getSelectedItem().toString());
        writeXTrackerOutput(jcbTechnique.getSelectedItem().toString());
        System.exit(0);
    }
    //... Write xTrackerMain based on the technique ...//
    private void writeXTrackerMain(String sExperiment) 
    {
        String sFileName = sWorkspace + "\\" + sExperiment + "_Conf.xtc";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();
            out.write("    This XML file specifies the set of plugins and the corresponding parameters used for this pipeline in X-Tracker.");
            out.newLine();
            out.write("    For each plugin, if needed, parameters can be specified as xml files.");
            out.newLine();
            out.write("-->");
            out.newLine();
            out.write("<xTrackerPipeline xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\xtracker.xsd\">");
            out.newLine();
            out.write("    <!--");            
            out.newLine();
            out.write("    The different tags MUST BE:");            
            out.newLine();
            out.write("        rawdata_loadplugin");            
            out.newLine();
            out.write("        identdata_loadplugin");
            out.newLine();
            out.write("        peakselplugin");
            out.newLine();
            out.write("        quantplugin");
            out.newLine();
            out.write("        outplugin");
            out.newLine();
            out.write("    and each one of them has to be present EXACTLY once within the whole xml file.");
            out.newLine();
            out.write("    The attribute \"filename\" will point to the .jar plugin (which needs to be placed in the \"Plugins\" folder)");
            out.newLine();
            out.write("    and the configuration parameter file needs to be located within the tags");
            out.newLine();
            out.write("        (e.g. <peakselplugin filename=\"plugin2.jar\">15n14n.xml</peakselplugin>)");
            out.newLine();
            out.write(" -->");
            out.newLine();
            
            //... Based on the technique, select the plugins that are available to perform the quantitation ...//
            String[] sPipeline;
            sPipeline = new String[5];            
            sPipeline = getPlugins(sExperiment);
            out.write("    <rawdata_loadplugin filename=\"" + sPipeline[0] + ".jar\">" + sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_LoadRawDataParams.xtp</rawdata_loadplugin>");
            out.newLine();
            out.write("    <identdata_loadplugin filename=\"" + sPipeline[1] + ".jar\">" + sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_LoadIdentDataParams.xtp</identdata_loadplugin>");
            out.newLine();
            out.write("    <peakselplugin filename=\"" + sPipeline[2] + ".jar\">" + sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_PeakSelParams.xtp</peakselplugin>");
            out.newLine();
            out.write("    <quantplugin filename=\"" + sPipeline[3] + ".jar\">" + sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_QuantParams.xtp</quantplugin>");
            out.newLine();
            out.write("    <outplugin filename=\"" + sPipeline[4] + ".jar\">" + sWorkspace + "\\" + jcbTechnique.getSelectedItem().toString() + "_OutputParams.xtp</outplugin>");
            out.newLine();
            
            out.write("</xTrackerPipeline>");
            out.newLine();            
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    //... This method gets the plugins based on the selected pipeline ...//
    private String[] getPlugins(String sExperiment)
    {
        final List<List<String>> alPlugins = new ArrayList<List<String>>();
        String[] sPipeline;
        sPipeline = new String[5];
        
        //... Read files using SAX (Creates an Array of ArrayList) ...//
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() 
            {
                boolean bpipelineTechnique = false;
                boolean bpipelineLoadRawFiles = false;
                boolean bpipelineLoadIdentFiles = false;
                boolean bpipelinePeakSelection = false;
                boolean bpipelineQuantitation = false;
                boolean bpipelineOutput = false;                    
                boolean bWorkspace = false;

                String spipelineTechnique = "";
                String spipelineLoadRawFiles = "";
                String spipelineLoadIdentFiles = "";
                String spipelinePeakSelection = "";
                String spipelineQuantitation = "";
                String spipelineOutput = "";
                String sWorkspace = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
                    if (qName.equalsIgnoreCase("workspace")) {
                        bWorkspace = true;
                    }
                    if (qName.equalsIgnoreCase("pipelineTechnique")) {
                        bpipelineTechnique = true;
                    }
                    if (qName.equalsIgnoreCase("pipelineLoadRawFiles")) {
                        bpipelineLoadRawFiles = true;
                    }
                    if (qName.equalsIgnoreCase("pipelineLoadIdentFiles")) {
                        bpipelineLoadIdentFiles = true;
                    }
                    if (qName.equalsIgnoreCase("pipelinePeakSelection")) {
                        bpipelinePeakSelection = true;
                    }
                    if (qName.equalsIgnoreCase("pipelineQuantitation")) {
                        bpipelineQuantitation = true;
                    }
                    if (qName.equalsIgnoreCase("pipelineOutput")) {
                        bpipelineOutput = true;
                    }
                }
                @Override
                public void characters(char ch[], int start, int length)
                throws SAXException {
                    if (bWorkspace) {
                        bWorkspace = false;
                        sWorkspace = new String(ch, start, length);
                    }                        
                    if (bpipelineTechnique) {
                        bpipelineTechnique = false;
                        spipelineTechnique = new String(ch, start, length);
                    }
                    if (bpipelineLoadRawFiles) {
                        bpipelineLoadRawFiles = false;
                        spipelineLoadRawFiles = new String(ch, start, length);
                    }
                    if (bpipelineLoadIdentFiles) {
                        bpipelineLoadIdentFiles = false;
                        spipelineLoadIdentFiles = new String(ch, start, length);
                    }
                    if (bpipelinePeakSelection) {
                        bpipelinePeakSelection = false;
                        spipelinePeakSelection = new String(ch, start, length);
                    }
                    if (bpipelineQuantitation) {
                        bpipelineQuantitation = false;
                        spipelineQuantitation = new String(ch, start, length);
                    }
                    if (bpipelineOutput) {
                        bpipelineOutput = false;
                        spipelineOutput = new String(ch, start, length);
                        alPlugins.add(Arrays.asList(spipelineTechnique, spipelineLoadRawFiles, spipelineLoadIdentFiles, spipelinePeakSelection, spipelineQuantitation, spipelineOutput));        
                    }
                }
            };
            saxParser.parse("config.xml", handler);

        } catch (Exception e) {
          e.printStackTrace();
        }
        
        //... Using the array list we need to find the pipeline and corresponding plugins ...//        
        for (int iI=0; iI < alPlugins.size(); iI++)
        {
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[0].toString().equals(sExperiment))
            {
                sPipeline[0] = arrayOfStrings[1].toString();
                sPipeline[1] = arrayOfStrings[2].toString();
                sPipeline[2] = arrayOfStrings[3].toString();
                sPipeline[3] = arrayOfStrings[4].toString();
                sPipeline[4] = arrayOfStrings[5].toString();
                break;
            }
        }
        return sPipeline;
    }
    //... Write the raw files selected for quantitation ...//
    private void writeXTrackerRaw(String sExperiment) 
    {
        String sFileName = sWorkspace + "\\" + sExperiment + "_LoadRawDataParams.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();            
            out.write("    This XML file specifies all raw files needed for the analysis.");
            out.newLine();
            out.write("-->");
            out.newLine();
 
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\loadRawMzML.xsd\">");
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
    private void writeXTrackerIdent(String sExperiment) 
    {
        String sFileName = sWorkspace + "\\" + sExperiment + "_LoadIdentDataParams.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!-- ");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();
            out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
            out.newLine();
            out.write("    Also, modifications and mass shifts are specified here in case mascot does not report fixed modification mass shifts.");
            out.newLine();
            out.write("    Finally, the minimum threshold use for the search engine is specified in the pop_score_threshold tag. ");
            out.newLine();
            out.write("-->");
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\loadMascotIdent.xsd\">");
            out.newLine();
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
            out.write("    <pep_score_threshold>20</pep_score_threshold>");
            out.newLine();
            out.write("</param>");
            out.newLine();
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    
    private void writeXTrackerPeakSel(String sExperiment) 
    {        
        String sFileName = sWorkspace + "\\" + sExperiment + "_PeakSelParams.xtp";        
        if (sExperiment.equals("SILAC"))
        {            
            try{
                FileWriter fstream = new FileWriter(sFileName);
                BufferedWriter out = new BufferedWriter(fstream);            
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.newLine();
                out.write("<!-- ");
                out.newLine();                
                out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
                out.newLine();
                out.write("    This plugin allows the specification of the parameters used for the SILAC pipeline. Here, we specify the mass type, ");
                out.newLine();
                out.write("    incorporation rate, retention time window, m/z tolerance and the shifts for the C-terminus, N-terminus and amino acid shifts.");
                out.newLine();                
                out.write("-->");
                out.newLine();            
                out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\metLabelingPeakSel.xsd\">");
                out.newLine();
                out.write("    <!-- Specifies the mass values to use (it can be either \"monoisotopic\" or \"average\") -->");
                out.newLine();
                out.write("    <mass_type>monoisotopic</mass_type>");
                out.newLine();
                out.newLine();
                out.write("   <!-- Specifies the incorporation rate, normalized to 1 (i.e. 98.5% -> 0.985) -->");
                out.newLine();                
                out.write("   <incorporation_rate>1.000</incorporation_rate>");
                out.newLine();
                out.newLine();                
                out.write("   <!-- Specifies the retention time mass window (in seconds) to consider while trying to match peaks -->");
                out.newLine();
                out.write("   <RT_window>10</RT_window>");
                out.newLine();
                out.newLine();                
                out.write("   <!-- Specifies the M/Z tolerance +/- (in Daltons) to match a MS peak with the theoretical computed from the parent ion Mz of a peptide +/- its mass shift -->	");
                out.newLine();
                out.write("   <mz_tolerance>0.1</mz_tolerance>");
                out.newLine();
                out.newLine();                
                out.write("   <!-- Specifies the mass shift in Daltons for the C terminus -->");
                out.newLine();
                out.write("   <C_term>0</C_term>");
                out.newLine();
                out.newLine();                
                out.write("   <!-- Specifies the mass shift in Daltons for the N terminus -->");
                out.newLine();
                out.write("    <N_term>0</N_term>");
                out.newLine();
                out.newLine();                
                out.write("    <!-- ");
                out.newLine();
                out.write("    Specifies the mass shift in Daltons for each amino acid. All 20 aminoacids will have to be specified");
                out.newLine();
                out.write("    otherwise 0 Daltons shift will be assumed!");
                out.newLine();
                out.write("    -->");
                out.newLine();
                out.write("    <aminoShifts>");
                out.newLine();
                out.write("        <aminoacid value=\"A\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"R\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"N\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"D\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"C\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"E\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"Q\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"G\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"H\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"I\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"L\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"K\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"M\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"F\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"P\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"S\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"T\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"W\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"Y\">0.0000</aminoacid>");               
                out.newLine();
                out.write("        <aminoacid value=\"V\">0.0000</aminoacid>");               
                out.newLine();
                out.write("    </aminoShifts>");
                out.newLine();
                out.write("</param>");
                out.newLine();
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }
        }
        else if (sExperiment.equals("iTRAQ"))
        {
            try{
                FileWriter fstream = new FileWriter(sFileName);
                BufferedWriter out = new BufferedWriter(fstream);            
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.newLine();
                out.write("<!-- ");
                out.newLine();
                out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
                out.newLine();                
                out.write("    This plugin allows the specification of the parameters used for the iTRAQ pipeline.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<iTraq xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\iTraqNplexPeakSel.xsd\">");
                out.newLine();
                out.write("<settings>");
                out.newLine();
                out.write("    <mzLowerThreshold>0.05</mzLowerThreshold>");
                out.newLine();
                out.write("    <mzUpperThreshold>0.05</mzUpperThreshold>");
                out.newLine();
                out.write("</settings>");
                out.newLine();                
                out.write("<reporterIons>");
                out.newLine();
                out.write("    <reporter label=\"Label 114\" mz=\"114.11123\" />");
                out.newLine();
                out.write("    <reporter label=\"Label 115\" mz=\"115.10826\" />");
                out.newLine();
                out.write("    <reporter label=\"Label 116\" mz=\"116.11162\" />");
                out.newLine();
                out.write("    <reporter label=\"Label 117\" mz=\"117.11497\" />");
                out.newLine();
                out.write("</reporterIons>");
                out.newLine();
                out.write("</iTraq>");
                out.newLine();
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }   
        }        
    }
    private void writeXTrackerQuantSel(String sExperiment) 
    {       
        String sFileName = sWorkspace + "\\" + sExperiment + "_QuantParams.xtp";        
        if (sExperiment.equals("SILAC"))
        {            
            
        }
        else if (sExperiment.equals("iTRAQ"))
        {            
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
                out.write("<iTraq xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\iTraqNplexQuant.xsd\">");
                out.newLine();
                out.write("    <settings>");
                out.newLine();
                out.write("    <NumcorrFactors>4</NumcorrFactors>");
                out.newLine();
                out.write("    <NumReporterIons>4</NumReporterIons>");
                out.newLine();
                out.write("    <IntegrationMethod>SumIntensities</IntegrationMethod>");
                out.newLine();                
                out.write("    </settings>");
                out.newLine();                
                out.write("    <reporterIons>");
                out.newLine();
                out.write("        <reporter label=\"Label 114\" mz=\"114.11123\">");
                out.newLine();
                out.write("        <correctionFactors>");
                out.newLine();
                out.write("            <factor deltaMass=\"-2\">0</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"-1\">1.0</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+1\">5.9</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+2\">0.2</factor>");
                out.newLine();
                out.write("        </correctionFactors>");
                out.newLine();
                out.write("        </reporter>");
                out.newLine();
                out.write("        <reporter label=\"Label 115\" mz=\"115.10826\">");
                out.newLine();
                out.write("        <correctionFactors>");
                out.newLine();
                out.write("            <factor deltaMass=\"-2\">0</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"-1\">2</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+1\">5.6</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+2\">0.1</factor>");
                out.newLine();
                out.write("        </correctionFactors>");
                out.newLine();
                out.write("        </reporter>");
                out.newLine();
                out.write("        <reporter label=\"Label 116\" mz=\"116.11162\">");
                out.newLine();
                out.write("        <correctionFactors>");
                out.newLine();
                out.write("            <factor deltaMass=\"-2\">0</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"-1\">3</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+1\">4.5</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+2\">0.1</factor>");
                out.newLine();
                out.write("        </correctionFactors>");
                out.newLine();
                out.write("        </reporter>");
                out.newLine();
                out.write("        <reporter label=\"Label 117\" mz=\"117.11497\">");
                out.newLine();
                out.write("        <correctionFactors>");
                out.newLine();
                out.write("            <factor deltaMass=\"-2\">0.1</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"-1\">4</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+1\">3.5</factor>");
                out.newLine();
                out.write("            <factor deltaMass=\"+2\">0</factor>");
                out.newLine();
                out.write("        </correctionFactors>");
                out.newLine();
                out.write("        </reporter>");
                out.newLine();
                out.write("    </reporterIons>");
                out.newLine();
                out.write("</iTraq>");
                out.newLine();
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }   
        }        
    }
    private void writeXTrackerOutput(String sExperiment) 
    {
        String sFileName = sWorkspace + "\\" + sExperiment + "_OutputParams.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
            out.newLine();                 
            out.write("    This plugin allows the specification of the output format. In this file we can set up whether a normalisation process will be applied (yes/no) ...//");
            out.newLine();
	    out.write("-->");
            out.newLine();       
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\displayTable.xsd\">");
            out.newLine();
            out.write("    <normalisation>no</normalisation>");
            out.newLine();
            out.write("</param>");
            out.newLine();
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton jbCopy;
    private javax.swing.JButton jbCut;
    private javax.swing.JButton jbGenerateTemplate;
    private javax.swing.JButton jbImportFile;
    private javax.swing.JButton jbNewProject;
    private javax.swing.JButton jbOpenProject;
    private javax.swing.JButton jbPaste;
    private javax.swing.JButton jbRunQuantAnalysis;
    private javax.swing.JButton jbSaveProject;
    private javax.swing.JButton jbShow2D;
    private javax.swing.JButton jbShowChromatogram;
    private javax.swing.JButton jbShowIsotopeDistrib;
    private javax.swing.JComboBox jcbTechnique;
    private javax.swing.JDesktopPane jdp2D;
    private javax.swing.JDesktopPane jdpMS;
    private javax.swing.JDesktopPane jdpTIC;
    private javax.swing.JLabel jlFiles;
    private javax.swing.JLabel jlIdentFiles;
    private javax.swing.JLabel jlIdentFilesStatus;
    private javax.swing.JLabel jlPeptide;
    private javax.swing.JLabel jlProperties;
    private javax.swing.JLabel jlProtein;
    private javax.swing.JLabel jlQuantFiles;
    private javax.swing.JLabel jlQuantFilesStatus;
    private javax.swing.JLabel jlRT;
    private javax.swing.JLabel jlRawFiles;
    private javax.swing.JLabel jlRawFilesStatus;
    private javax.swing.JLabel jlScan;
    private javax.swing.JLabel jlSearchMascotXML;
    private javax.swing.JLabel jlSearchMzML;
    private javax.swing.JLabel jlTechnique;
    private javax.swing.JLabel jlViewer;
    private javax.swing.JLabel jlVisualisationOptions;
    private javax.swing.JMenuItem jm2DView;
    private javax.swing.JMenuItem jm3DView;
    private javax.swing.JMenuItem jmAbout;
    private javax.swing.JMenu jmAnalyze;
    private javax.swing.JMenuItem jmCloseProject;
    private javax.swing.JMenu jmConverters;
    private javax.swing.JMenuItem jmCopy;
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
    private javax.swing.JMenuBar jmMain;
    private javax.swing.JMenuItem jmMascotXMLView;
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
    private javax.swing.JMenu jmStatistics;
    private javax.swing.JMenuItem jmSubmitPRIDE;
    private javax.swing.JMenuItem jmTIC;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenu jmView;
    private javax.swing.JMenu jmWindow;
    private javax.swing.JPanel jp2D;
    private javax.swing.JPanel jp3D;
    private javax.swing.JPanel jpLeftMenuBottom;
    private javax.swing.JPanel jpLeftMenuTop;
    private javax.swing.JPanel jpLeftPanelView;
    private javax.swing.JPanel jpLeftViewer;
    private javax.swing.JPanel jpLeftViewerBottom;
    private javax.swing.JPanel jpLeftViewerDetails;
    private javax.swing.JPanel jpMainPanelView;
    private javax.swing.JPanel jpMascotXML;
    private javax.swing.JPanel jpMascotXMLMenu;
    private javax.swing.JPanel jpMzML;
    private javax.swing.JPanel jpMzMLMenu;
    private javax.swing.JPanel jpMzQuantML;
    private javax.swing.JPanel jpProjectDetails;
    private javax.swing.JPanel jpProjectHeader;
    private javax.swing.JPanel jpProjectStatus;
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpPropertiesBox;
    private javax.swing.JPanel jpPropetiesTab;
    private javax.swing.JPanel jpQuantFiles;
    private javax.swing.JPanel jpRawDataValues;
    private javax.swing.JPanel jpRawDataValuesMenu;
    private javax.swing.JPanel jpTIC;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpViewerAndProperties;
    private javax.swing.JScrollPane jspIdentFiles;
    private javax.swing.JSplitPane jspLeftMenuBottom;
    private javax.swing.JSplitPane jspLeftPanelView;
    private javax.swing.JSplitPane jspLeftViewer;
    private javax.swing.JSplitPane jspLeftViewerDetails;
    private javax.swing.JPanel jspLeftViewerHeader;
    private javax.swing.JScrollPane jspLog;
    private javax.swing.JSplitPane jspMainPanelView;
    private javax.swing.JSplitPane jspMascotXML;
    private javax.swing.JScrollPane jspMascotXMLDetail;
    private javax.swing.JScrollPane jspMascotXMLHeader;
    private javax.swing.JSplitPane jspMascotXMLSubDetail;
    private javax.swing.JSplitPane jspMzML;
    private javax.swing.JSplitPane jspMzMLDetail;
    private javax.swing.JScrollPane jspMzMLHeader;
    private javax.swing.JScrollPane jspMzMLSubDetail;
    private javax.swing.JSplitPane jspMzQuantML;
    private javax.swing.JScrollPane jspMzQuantMLHeader;
    private javax.swing.JScrollPane jspMzQuantMLView;
    private javax.swing.JSplitPane jspProjectDetails;
    private javax.swing.JSplitPane jspProperties;
    private javax.swing.JScrollPane jspQuantFiles;
    private javax.swing.JScrollPane jspRawData;
    private javax.swing.JSplitPane jspRawDataValues;
    private javax.swing.JScrollPane jspRawFiles;
    private javax.swing.JSplitPane jspViewerAndProperties;
    private javax.swing.JTable jtIdentFiles;
    private javax.swing.JTable jtMascotXMLView;
    private javax.swing.JTable jtMzML;
    private javax.swing.JTable jtMzQuantMLView;
    private javax.swing.JTextField jtPeptide;
    private javax.swing.JTextField jtProtein;
    private javax.swing.JTable jtQuantFiles;
    private javax.swing.JTextField jtRT;
    private javax.swing.JTable jtRawData;
    private javax.swing.JTable jtRawFiles;
    private javax.swing.JTextField jtScan;
    private javax.swing.JTable jtTemplate;
    private javax.swing.JTable jtTemplate2;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextArea jtaMascotXML;
    private javax.swing.JTextArea jtaMzML;
    private javax.swing.JTextArea jtaMzQuantML;
    private javax.swing.JTabbedPane jtpIdentFiles;
    private javax.swing.JTabbedPane jtpLog;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpQuantFiles;
    private javax.swing.JTabbedPane jtpRawFiles;
    private javax.swing.JTabbedPane jtpViewer;
    // End of variables declaration//GEN-END:variables
}
