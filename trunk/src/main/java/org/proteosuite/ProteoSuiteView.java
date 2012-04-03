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
import java.applet.AppletContext;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.proteosuite.gui.*;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.TwoDPlot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
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
        this.setTitle("ProteoSuite 0.2.0 (Beta Version) - <Project: " + this.sProjectName +  ">  http://www.proteosuite.org");
        
        //... Setting icons ...//        
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        this.setIconImage(iconApp);
        
        //... Main Menu icons ...//
        Icon newIcon = new ImageIcon(getClass().getClassLoader().getResource("images/new.gif"));
        Icon importIcon = new ImageIcon(getClass().getClassLoader().getResource("images/import.gif"));
        Icon openIcon = new ImageIcon(getClass().getClassLoader().getResource("images/open.gif"));
        Icon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("images/save.gif"));
        Icon printIcon = new ImageIcon(getClass().getClassLoader().getResource("images/print.gif"));
        
        Icon cutIcon = new ImageIcon(getClass().getClassLoader().getResource("images/cut.gif"));
        Icon copyIcon = new ImageIcon(getClass().getClassLoader().getResource("images/copy.gif"));
        Icon pasteIcon = new ImageIcon(getClass().getClassLoader().getResource("images/paste.gif"));
        
        Icon spectrumIcon = new ImageIcon(getClass().getClassLoader().getResource("images/spectrum.gif"));
        Icon twoDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/twod.gif"));
        
        jmNewProject.setIcon(newIcon);
        jbNewProject.setIcon(newIcon);
        jmImportFile.setIcon(importIcon);
        jbImportFile.setIcon(importIcon);
        jmOpenProject.setIcon(openIcon);
        jbOpenProject.setIcon(openIcon);
        jmSaveProject.setIcon(saveIcon);
        jbSaveProject.setIcon(saveIcon);
        jmPrint.setIcon(printIcon);
        
        jmCut.setIcon(cutIcon);
        jbCut.setIcon(cutIcon);
        jmCopy.setIcon(copyIcon);
        jbCopy.setIcon(copyIcon);
        jmPaste.setIcon(pasteIcon);
        jbPaste.setIcon(pasteIcon);
        
        jbShowChromatogram.setIcon(spectrumIcon);
        jbShow2D.setIcon(twoDIcon);
        
        //... Setting Window Height and Width ...//
        this.setMinimumSize(new Dimension(1024, 780));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //... Setting dividers ...//
        jspMainPanelView.setDividerLocation(250); //... Left Menu ...//
        jspLeftPanelView.setDividerLocation(200);
        jspLeftMenuBottom.setDividerLocation(200);
        jspRightPanelView.setDividerLocation(650); //... Graphs X-axis ...//
        jspViewer.setDividerLocation(460); //... Graphs Y-axis ...//
        jspMzML.setDividerLocation(100); //... MzML data ...//
        jtpViewer.setSelectedIndex(0);
        
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
        jpbStatus = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jToolBar3 = new javax.swing.JToolBar();
        jpMain = new javax.swing.JPanel();
        jspMainPanelView = new javax.swing.JSplitPane();
        jspRightPanelView = new javax.swing.JSplitPane();
        jpProperties = new javax.swing.JPanel();
        jtpProperties = new javax.swing.JTabbedPane();
        jpMzML = new javax.swing.JPanel();
        jspMzML = new javax.swing.JSplitPane();
        jspMzMLHeader = new javax.swing.JScrollPane();
        jtaMzML = new javax.swing.JTextArea();
        jspMzMLDetail = new javax.swing.JSplitPane();
        jspMzMLSubDetail = new javax.swing.JScrollPane();
        jtMzML = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        jbShowChromatogram = new javax.swing.JButton();
        jbShow2D = new javax.swing.JButton();
        jpMzQuantML = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jspViewer = new javax.swing.JSplitPane();
        jtpViewer = new javax.swing.JTabbedPane();
        jdpMS = new javax.swing.JDesktopPane();
        jpTIC = new javax.swing.JPanel();
        jp2D = new javax.swing.JPanel();
        jp3D = new javax.swing.JPanel();
        jpViewer = new javax.swing.JPanel();
        jtpLog = new javax.swing.JTabbedPane();
        jspLog = new javax.swing.JScrollPane();
        jtaLog = new javax.swing.JTextArea();
        jpRawData = new javax.swing.JPanel();
        jspRawData = new javax.swing.JScrollPane();
        jtRawData = new javax.swing.JTable();
        jspLeftPanelView = new javax.swing.JSplitPane();
        jspLeftMenuBottom = new javax.swing.JSplitPane();
        jtpIdentFiles = new javax.swing.JTabbedPane();
        jspIdentFiles = new javax.swing.JScrollPane();
        jtIdentFiles = new javax.swing.JTable();
        jtpQuantFiles = new javax.swing.JTabbedPane();
        jspQuantFiles = new javax.swing.JScrollPane();
        jtQuantFiles = new javax.swing.JTable();
        jtpRawFiles = new javax.swing.JTabbedPane();
        jspRawFiles = new javax.swing.JScrollPane();
        jtRawFiles = new javax.swing.JTable();
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
        jmViewSpectrum = new javax.swing.JMenuItem();
        jmViewXIC = new javax.swing.JMenuItem();
        jmView2D = new javax.swing.JMenuItem();
        jmView3D = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jmViewMzML = new javax.swing.JMenuItem();
        jmViewMzQuantML = new javax.swing.JMenuItem();
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

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

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

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

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

        jLabel1.setText("Task monitor:");

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setMaximumSize(new java.awt.Dimension(34, 9));
        jToolBar3.setMinimumSize(new java.awt.Dimension(34, 9));

        javax.swing.GroupLayout jpToolBarLayout = new javax.swing.GroupLayout(jpToolBar);
        jpToolBar.setLayout(jpToolBarLayout);
        jpToolBarLayout.setHorizontalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 352, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpToolBarLayout.setVerticalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addGap(11, 11, 11))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpToolBarLayout.createSequentialGroup()
                .addGroup(jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jpbStatus, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpMain.setBackground(new java.awt.Color(255, 255, 255));

        jspMainPanelView.setBackground(new java.awt.Color(255, 255, 255));
        jspMainPanelView.setDividerLocation(280);
        jspMainPanelView.setDividerSize(2);

        jspRightPanelView.setBackground(new java.awt.Color(255, 255, 255));
        jspRightPanelView.setDividerLocation(500);
        jspRightPanelView.setDividerSize(2);

        jpProperties.setBackground(new java.awt.Color(255, 255, 255));

        jtpProperties.setBackground(new java.awt.Color(255, 255, 255));

        jspMzML.setBackground(new java.awt.Color(255, 255, 255));
        jspMzML.setDividerSize(2);
        jspMzML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzML.setColumns(20);
        jtaMzML.setFont(new java.awt.Font("Tahoma", 0, 11));
        jtaMzML.setRows(5);
        jspMzMLHeader.setViewportView(jtaMzML);

        jspMzML.setLeftComponent(jspMzMLHeader);

        jspMzMLDetail.setDividerLocation(35);
        jspMzMLDetail.setDividerSize(2);
        jspMzMLDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtMzML.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Index", "ID", "MS", "Base peak m/z", "Base peak int", "Total ion curr", "Lowest obs m/z", "Highest obs m/z", "Ret Time (sec)", "Scan Win Min", "Scan Win Max"
            }
        ));
        jtMzML.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMzMLMouseClicked(evt);
            }
        });
        jspMzMLSubDetail.setViewportView(jtMzML);

        jspMzMLDetail.setRightComponent(jspMzMLSubDetail);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);
        jToolBar4.setMaximumSize(new java.awt.Dimension(100, 27));
        jToolBar4.setMinimumSize(new java.awt.Dimension(100, 27));
        jToolBar4.setPreferredSize(new java.awt.Dimension(100, 27));
        jToolBar4.setRequestFocusEnabled(false);

        jbShowChromatogram.setToolTipText("Show Chromatogram");
        jbShowChromatogram.setMaximumSize(new java.awt.Dimension(35, 24));
        jbShowChromatogram.setMinimumSize(new java.awt.Dimension(35, 24));
        jbShowChromatogram.setPreferredSize(new java.awt.Dimension(35, 24));
        jbShowChromatogram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbShowChromatogramMouseClicked(evt);
            }
        });
        jToolBar4.add(jbShowChromatogram);

        jbShow2D.setToolTipText("Show 2D Plot");
        jbShow2D.setMaximumSize(new java.awt.Dimension(35, 24));
        jbShow2D.setMinimumSize(new java.awt.Dimension(35, 24));
        jbShow2D.setPreferredSize(new java.awt.Dimension(35, 24));
        jbShow2D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbShow2DMouseClicked(evt);
            }
        });
        jToolBar4.add(jbShow2D);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(114, 114, 114))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jspMzMLDetail.setLeftComponent(jPanel1);

        jspMzML.setBottomComponent(jspMzMLDetail);

        javax.swing.GroupLayout jpMzMLLayout = new javax.swing.GroupLayout(jpMzML);
        jpMzML.setLayout(jpMzMLLayout);
        jpMzMLLayout.setHorizontalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );
        jpMzMLLayout.setVerticalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzML View", jpMzML);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jSplitPane1.setTopComponent(jScrollPane1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Column 1", "Column 2", "Column 3", "Column 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jpMzQuantMLLayout = new javax.swing.GroupLayout(jpMzQuantML);
        jpMzQuantML.setLayout(jpMzQuantMLLayout);
        jpMzQuantMLLayout.setHorizontalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );
        jpMzQuantMLLayout.setVerticalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzQuantML View", jpMzQuantML);

        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
        jpProperties.setLayout(jpPropertiesLayout);
        jpPropertiesLayout.setHorizontalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties)
        );
        jpPropertiesLayout.setVerticalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties)
        );

        jspRightPanelView.setRightComponent(jpProperties);

        jspViewer.setBackground(new java.awt.Color(255, 255, 255));
        jspViewer.setDividerLocation(400);
        jspViewer.setDividerSize(2);
        jspViewer.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jdpMS.setBackground(new java.awt.Color(255, 255, 255));
        jtpViewer.addTab("Spectrum", jdpMS);

        jpTIC.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpTICLayout = new javax.swing.GroupLayout(jpTIC);
        jpTIC.setLayout(jpTICLayout);
        jpTICLayout.setHorizontalGroup(
            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        jpTICLayout.setVerticalGroup(
            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        jtpViewer.addTab("TIC", jpTIC);

        jp2D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp2DLayout = new javax.swing.GroupLayout(jp2D);
        jp2D.setLayout(jp2DLayout);
        jp2DLayout.setHorizontalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        jp2DLayout.setVerticalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        jtpViewer.addTab("2D View", jp2D);

        jp3D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp3DLayout = new javax.swing.GroupLayout(jp3D);
        jp3D.setLayout(jp3DLayout);
        jp3DLayout.setHorizontalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        jp3DLayout.setVerticalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        jtpViewer.addTab("3D View", jp3D);

        jspViewer.setTopComponent(jtpViewer);

        jpViewer.setBackground(new java.awt.Color(255, 255, 255));

        jtaLog.setColumns(20);
        jtaLog.setRows(5);
        jspLog.setViewportView(jtaLog);

        jtpLog.addTab("Log", jspLog);

        jtRawData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "m/z", "Intensity"
            }
        ));
        jspRawData.setViewportView(jtRawData);

        javax.swing.GroupLayout jpRawDataLayout = new javax.swing.GroupLayout(jpRawData);
        jpRawData.setLayout(jpRawDataLayout);
        jpRawDataLayout.setHorizontalGroup(
            jpRawDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspRawData, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );
        jpRawDataLayout.setVerticalGroup(
            jpRawDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpRawDataLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(jspRawData, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtpLog.addTab("Raw Data", jpRawData);

        javax.swing.GroupLayout jpViewerLayout = new javax.swing.GroupLayout(jpViewer);
        jpViewer.setLayout(jpViewerLayout);
        jpViewerLayout.setHorizontalGroup(
            jpViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog)
        );
        jpViewerLayout.setVerticalGroup(
            jpViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpLog, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        jspViewer.setRightComponent(jpViewer);

        jspRightPanelView.setLeftComponent(jspViewer);

        jspMainPanelView.setRightComponent(jspRightPanelView);

        jspLeftPanelView.setBackground(new java.awt.Color(255, 255, 255));
        jspLeftPanelView.setDividerLocation(150);
        jspLeftPanelView.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jspLeftMenuBottom.setBackground(new java.awt.Color(255, 255, 255));
        jspLeftMenuBottom.setDividerLocation(150);
        jspLeftMenuBottom.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtpIdentFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtIdentFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Version"
            }
        ));
        jspIdentFiles.setViewportView(jtIdentFiles);

        jtpIdentFiles.addTab("Identification Files", jspIdentFiles);

        jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

        jtpQuantFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtQuantFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Version"
            }
        ));
        jspQuantFiles.setViewportView(jtQuantFiles);

        jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);

        jspLeftMenuBottom.setRightComponent(jtpQuantFiles);

        jspLeftPanelView.setBottomComponent(jspLeftMenuBottom);

        jtpRawFiles.setBackground(new java.awt.Color(255, 255, 255));

        jspRawFiles.setBackground(new java.awt.Color(255, 255, 255));

        jtRawFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Version", "Spectra"
            }
        ));
        jtRawFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtRawFilesMouseClicked(evt);
            }
        });
        jspRawFiles.setViewportView(jtRawFiles);

        jtpRawFiles.addTab("Raw Files", jspRawFiles);

        jspLeftPanelView.setLeftComponent(jtpRawFiles);

        jspMainPanelView.setLeftComponent(jspLeftPanelView);

        javax.swing.GroupLayout jpMainLayout = new javax.swing.GroupLayout(jpMain);
        jpMain.setLayout(jpMainLayout);
        jpMainLayout.setHorizontalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        jpMainLayout.setVerticalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
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

        jmViewSpectrum.setText("Spectrum");
        jmViewSpectrum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmViewSpectrumActionPerformed(evt);
            }
        });
        jmView.add(jmViewSpectrum);

        jmViewXIC.setText("XIC");
        jmViewXIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmViewXICActionPerformed(evt);
            }
        });
        jmView.add(jmViewXIC);

        jmView2D.setText("2D View");
        jmView2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmView2DActionPerformed(evt);
            }
        });
        jmView.add(jmView2D);

        jmView3D.setText("3D View");
        jmView3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmView3DActionPerformed(evt);
            }
        });
        jmView.add(jmView3D);
        jmView.add(jSeparator7);

        jmViewMzML.setText("mzML Data");
        jmViewMzML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmViewMzMLActionPerformed(evt);
            }
        });
        jmView.add(jmViewMzML);

        jmViewMzQuantML.setText("mzQuantML Data");
        jmViewMzQuantML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmViewMzQuantMLActionPerformed(evt);
            }
        });
        jmView.add(jmViewMzQuantML);

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
            .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
        
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
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mzML Files (*.mzML)", "mzML");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("mzIdentML Files (*.mzid)", "mzid");
        FileNameExtensionFilter filter2a = new FileNameExtensionFilter("Mascot XML Files (*.xml)", "xml");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq)", "mzq");

        //... Filters must be in descending order ...//
        chooser.setFileFilter(filter3);
        chooser.setFileFilter(filter2);
        chooser.setFileFilter(filter2a);
        chooser.setFileFilter(filter);

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
            File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0)
            {
                //... Code to be inserted here ...//
                if (aFiles[0].getName().indexOf(".mzML") > 0) 
                {
                    //... Setting progress bar ...// (To do: This needs to be set up in a thread)
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    
                    //... Fill JTable ...//
                    DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Version");
                    model.addColumn("Spectra");
    
                    alUnmarshaller = new ArrayList<MzMLUnmarshaller>();

                    //... Reading selected files ...//
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        File xmlFile = new File(aFiles[iI].getPath());                        

                        //... Unmarshall data using jzmzML API ...//
                        unmarshalMzMLFile(model, iI, xmlFile);
                    } //... For files ...//                    
                    
                    setCursor(null); //... Turn off the wait cursor ...//                                        
                } //... From reading mzML files ...//

                if (aFiles[0].getName().indexOf(".xml") >0) 
                {
                    //... Setting progress bar ...// (To do: This needs to be set up in a thread)
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    
                    //... Fill JTable ...//
                    DefaultTableModel model = new DefaultTableModel();
                    jtIdentFiles.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Version");

                    //... Reading selected files ...//
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        File xmlFile = new File(aFiles[iI].getPath());                        

                        //... Unmarshall data using jzmzML API ...//
                        loadIdentFiles(model, aFiles[iI].getName(), aFiles[iI].getName(), "Mascot XML File", iI);
                    } //... For files ...//                    
                    
                    setCursor(null); //... Turn off the wait cursor ...//                    
                }                
                
                if (aFiles[0].getName().indexOf(".mzq") >0) 
                {
                    JOptionPane.showMessageDialog(this, "The module for .mzq files is under development.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
                }
                if (aFiles[0].getName().indexOf(".mzid") >0) 
                {   
                    JOptionPane.showMessageDialog(this, "The module for .mzid files is under development.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
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
        
        jpbStatus.setIndeterminate(false);
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

    private void jmRunQuantAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRunQuantAnalysisActionPerformed
        
        //... Validate if we got some raw and identification files ...//
        if (jtRawFiles.getRowCount()<=0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one raw file", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (jtIdentFiles.getRowCount()<=0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one identification file", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);            
        }
        else
        {
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    progressBarDialog.setTitle("Running xTracker Core");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();
            new Thread("LoadingThread"){
                @Override
                public void run(){
                    xTracker run = new xTracker("D:\\Data\\SILAC_Conf.xtc");
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();        
                }
            }.start();                    
        }       
    }//GEN-LAST:event_jmRunQuantAnalysisActionPerformed

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

    private void jmViewSpectrumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmViewSpectrumActionPerformed
        
       jtpViewer.setSelectedIndex(0);
    }//GEN-LAST:event_jmViewSpectrumActionPerformed

    private void jmViewXICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmViewXICActionPerformed
        
        jtpViewer.setSelectedIndex(1);
    }//GEN-LAST:event_jmViewXICActionPerformed

    private void jmView2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmView2DActionPerformed
        
        jtpViewer.setSelectedIndex(2);
    }//GEN-LAST:event_jmView2DActionPerformed

    private void jmView3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmView3DActionPerformed
        
        jtpViewer.setSelectedIndex(3);
    }//GEN-LAST:event_jmView3DActionPerformed

    private void jmViewMzMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmViewMzMLActionPerformed
        
        jtpProperties.setSelectedIndex(0);
    }//GEN-LAST:event_jmViewMzMLActionPerformed

    private void jmViewMzQuantMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmViewMzQuantMLActionPerformed
        
        jtpProperties.setSelectedIndex(1);
    }//GEN-LAST:event_jmViewMzQuantMLActionPerformed

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
        
        if (evt.getButton() == 1) {
            loadMzMLView();         
        }
    }//GEN-LAST:event_jtRawFilesMouseClicked

    private void jmMzML2MGFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzML2MGFActionPerformed
        
        //... Load MzML2MGF GUI ...//
        MzML2MGFparams winParams = new MzML2MGFparams(this.sWorkspace);
        final JFrame jfWinParams = new JFrame("Convert mzML files to MGF");
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
        Configparams panelParams = new Configparams();
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
        this.showChromatogram(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
    }//GEN-LAST:event_jbShowChromatogramMouseClicked

    private void jbShow2DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbShow2DMouseClicked
        this.show2DPlot(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
    }//GEN-LAST:event_jbShow2DMouseClicked

    private void jmSubmitPRIDEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSubmitPRIDEActionPerformed
        // writeXTrackerConfigFiles();
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

    private void jmHelpContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHelpContentActionPerformed
	String url = "http://www.proteosuite.org/?q=tutorials";
	String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
	try{ 
	    if (os.indexOf( "win" ) >= 0) {
	        rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
 
	    } else if (os.indexOf( "mac" ) >= 0) {
	        rt.exec( "open " + url);
            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
	        String[] browsers = {"firefox","mozilla","konqueror","opera","links","lynx"};
 
	        StringBuilder cmd = new StringBuilder();
	        for (int i=0; i<browsers.length; i++)
	            cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
	        rt.exec(new String[] { "sh", "-c", cmd.toString() });
           } else {
                return;
           }
       }catch (Exception e){
	    return;
       }
    }//GEN-LAST:event_jmHelpContentActionPerformed
    
    public class threadProgBar implements Runnable{
        
        private String sFile="";
        public threadProgBar(String sFile){
            this.sFile = sFile;
        }        
        public void run(){            
            jpbStatus.setValue(0);            
            jpbStatus.setStringPainted(true);            
            for (int i=0; i<=100; i++){ //Progressively increment variable i
                jpbStatus.setString(i+"%");
                jpbStatus.setValue(i); //Set value
                //jpbStatus.repaint(); 
                try{Thread.sleep(50);} //Sleep 50 milliseconds
                catch (InterruptedException err)
                {}
            }
            jpbStatus.setString("Completed");
            try{Thread.sleep(500);} 
            catch (InterruptedException err)
            {}
            jpbStatus.setValue(0);            
            jpbStatus.setStringPainted(false);                        
            //jpbStatus.repaint();
        }
    }
        
    private void showSpectrum(int iIndex, String sID) {                                             
                  
        jtpViewer.setSelectedIndex(0);
        
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
           
           for (int iI = 0; iI < mzNumbers.length; iI++)
           {
               if (intenNumbers[iI].doubleValue() > 0)
               {
                    model.insertRow(model.getRowCount(), new Object[]{
                        iI,
                        String.format("%.4f", mzNumbers[iI].doubleValue()),
                        String.format("%.1f", intenNumbers[iI].doubleValue())
                        });
               }
           }                  
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
               ChromatogramPanel panel = new ChromatogramPanel(rt, intensities, "RT (mins)", "Intensity (counts)");
               JInternalFrame internal = new JInternalFrame("Chromatogram <" + sTitle + ">");
               panel.setSize(new java.awt.Dimension(600, 400));
               panel.setPreferredSize(new java.awt.Dimension(600, 400));
               panel.setMaxPadding(50);
               Icon icon = new ImageIcon(getClass().getResource("/images/icon.gif"));
               internal.setFrameIcon(icon);
               internal.setResizable(true);
               internal.setMaximizable(true);
               internal.setClosable(true);
               internal.setIconifiable(true);
               
               internal.add(panel);
               internal.pack();
               internal.setVisible(true);
               jpTIC.add(internal);
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
            int iK = 0;
            while ((spectrumIterator.hasNext()))
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
                out.write(" id=\"" + jtRawFiles.getValueAt(iI, 0) + "\"");
                out.write(" name=\"" + jtRawFiles.getValueAt(iI, 1) + "\"");
                out.write(" version=\"" + jtRawFiles.getValueAt(iI, 2) + "\"");
                out.write(" spectra=\"" + jtRawFiles.getValueAt(iI, 3) + "\" >");
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
        jtMzML.setModel(model);
        model.addColumn("");
        model.addColumn("Index");
        model.addColumn("ID");
        model.addColumn("MS");
        model.addColumn("Base peak m/z");
        model.addColumn("Base peak int");
        model.addColumn("Total ion current");
        model.addColumn("Lowest obs m/z");
        model.addColumn("Highest obs m/z");
        model.addColumn("Ret Time (sec)");
        model.addColumn("Scan Win Min");
        model.addColumn("Scan Win Max");     
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
     
        //... Reading spectrum data ...//
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        String msLevel = "";
        float basePeakMZ = 0;
        float basePeakInt = 0;
        float totalIonCurrent = 0;
        float lowestObsMZ = 0;
        float highestObsMZ = 0;
        String unitRT = "";
        float rt = 0;
        String scanWinMin = "";
        String scanWinMax = "";
        int iCount = 1;
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
               }
               if (lCVParam.getAccession().equals("MS:1000504")) 
               {
                   basePeakMZ = Float.parseFloat(lCVParam.getValue().trim());
               }
               if (lCVParam.getAccession().equals("MS:1000505")) 
               {
                   basePeakInt = Float.parseFloat(lCVParam.getValue().trim());
               }
               if (lCVParam.getAccession().equals("MS:1000285")) 
               {
                   totalIonCurrent = Float.parseFloat(lCVParam.getValue().trim());
               }
               if (lCVParam.getAccession().equals("MS:1000528")) 
               {
                   lowestObsMZ = Float.parseFloat(lCVParam.getValue().trim());
               }
               if (lCVParam.getAccession().equals("MS:1000527")) 
               {
                   highestObsMZ = Float.parseFloat(lCVParam.getValue().trim());
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
                        rt = Float.parseFloat(lCVParam.getValue().trim()) * 60;
                    }
                    else
                    {
                        rt = Float.parseFloat(lCVParam.getValue().trim());
                    }
                }
            }
            List<CVParam> scanWindowList = spectrum.getScanList().getScan().get(0).getScanWindowList().getScanWindow().get(0).getCvParam();
            for (Iterator lCVParamIterator = scanWindowList.iterator(); lCVParamIterator.hasNext();)
            {            
                CVParam lCVParam = (CVParam) lCVParamIterator.next();
                if (lCVParam.getAccession().equals("MS:1000501"))
                {
                    scanWinMin = lCVParam.getValue().trim();
                }
                if (lCVParam.getAccession().equals("MS:1000500"))
                {
                    scanWinMax = lCVParam.getValue().trim();
                }
            }            
            model.insertRow(model.getRowCount(), new Object[]{
                "",
                spectrum.getIndex().toString(), 
                spectrum.getId().toString(),
                msLevel,
                String.format("%.2f", basePeakMZ),
                String.format("%,.2f", basePeakInt),
                String.format("%,.2f", totalIonCurrent),
                String.format("%.2f", lowestObsMZ),
                String.format("%.2f", highestObsMZ),
                String.format("%.2f", rt),
                scanWinMin,
                scanWinMax
                });       
            iCount++;
        }
        this.jtMzML.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        
        for (int i = 0; i < jtMzML.getModel().getRowCount(); i++) {
           for (int j = 0; j < jtMzML.getModel().getColumnCount(); j++) {
              DefaultTableCellRenderer renderer =
                 (DefaultTableCellRenderer)jtMzML.getCellRenderer(i, j);
              renderer.setHorizontalAlignment(JTextField.RIGHT);
           } 
        }        
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

    /**
     * Initialise project variables (Clean build).
     *
     */    
    private void initProjectValues() 
    {
        //... Project settings ...//
        initSettings();                  
        initTables();
        initTextAreas();
        initViews();
    }
     /**
     * Initialise project settings (configuration file).
     *
     */    
    private void initSettings() 
    {
        //... Validate if config file exists ...//
        boolean exists = (new File("config.xml")).exists();
        if (exists)
        {
            try{            
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse (new File("config.xml"));

                doc.getDocumentElement().normalize();
                NodeList projectList = doc.getElementsByTagName("ProteoSuiteApp");                
                Node projectNode = projectList.item(0);
                Element projectElement = (Element)projectNode;
                this.sProjectName = "New";
                this.sWorkspace = projectElement.getAttribute("workspace").toString();
                this.bProjectModified = false;
                
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
    
    /**
     * Initialise table values.
     *
     */     
    private void initTables()
    {
        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        jtRawFiles.setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Version");
        model.addColumn("Spectra");
        
        jtMzML.setModel(model2);     
        model2.addColumn("Index");
        model2.addColumn("ID");
        model2.addColumn("MS");
        model2.addColumn("Base peak m/z");
        model2.addColumn("Base peak int");
        model2.addColumn("Total ion current");
        model2.addColumn("Lowest obs m/z");
        model2.addColumn("Highest obs m/z");
        model2.addColumn("Ret Time (sec)");
        model2.addColumn("Scan Win Min");
        model2.addColumn("Scan Win Max");             
    }
    private void initTextAreas()
    {
        jtaMzML.setText("");
    }
    private void initViews()
    {
        jdpMS.removeAll();
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
                    model.addColumn("Version");
                    model.addColumn("Spectra");                   
                    
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
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Version");                
            model.addColumn("Spectra");


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
                    
                    model.insertRow(model.getRowCount(), new Object[]{rawFileElement.getAttribute("id").toString(),
                                                                      rawFileElement.getAttribute("name").toString(),
                                                                      rawFileElement.getAttribute("version").toString(),
                                                                      rawFileElement.getAttribute("accession").toString(),
                                                                      rawFileElement.getAttribute("cvs").toString(),
                                                                      rawFileElement.getAttribute("spectra").toString()});
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
            model.insertRow(model.getRowCount(), new Object[]{unmarshaller.getMzMLId(),
                                                                      xmlFile.getName(),
                                                                      unmarshaller.getMzMLVersion(),
                                                                      spectra});            
    }
    private void loadIdentFiles(DefaultTableModel model, String sID, String sFileName, String sVersion, int iIndex)
    {
            model.insertRow(model.getRowCount(), new Object[]{sID, sFileName, sVersion});  
    }    
    private void writeXTrackerConfigFiles() 
    {
        String sExperiment = "SILAC";
        writeXTrackerMain(sExperiment);
        writeXTrackerRaw(sExperiment);
        writeXTrackerIdent(sExperiment);
        writeXTrackerPeakSel(sExperiment);
        writeXTrackerOutput(sExperiment);
        
        JOptionPane.showMessageDialog(this, "xTracker files generated.", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
    }
    private void writeXTrackerMain(String sExperiment) 
    {
        String sFileName = sExperiment + "_Conf.xtc";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write(" This XML file specifies the set of plugins used in this specific run of the pipeline X-Tracker together with  all the corresponding parameters.");
            out.newLine();
            out.write(" For each plugin, if needed, parameters can be specified in turn as xml files specifying particular inputs.");
            out.newLine();
            out.write("-->");
            out.newLine();
            out.write("<xTrackerPipeline xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\xtracker.xsd\">");
            out.newLine();
            out.write(" <!--");            
            out.newLine();
            out.write(" Specifies which Plugins to use as well as the parameters those plugins will work on.");
            out.newLine();
            out.write(" There different tags MUST BE:");            
            out.newLine();
            out.write("     rawdata_loadplugin");            
            out.newLine();
            out.write("     identdata_loadplugin");
            out.newLine();
            out.write("     peakselplugin");
            out.newLine();
            out.write("     quantplugin");
            out.newLine();
            out.write("     outplugin");
            out.newLine();
            out.write(" and each one of them has to be present EXACTLY once within the whole xml file.");
            out.newLine();
            out.write(" The attribute \"filename\" will point to the .jar plugin (which has to be put in the Plugins folder)");
            out.newLine();
            out.write(" and the parameter file is the content of the tag");
            out.newLine();
            out.write("     (e.g. <peakselplugin filename=\"plugin2.jar\">15n14n.xml</peakselplugin>).");
            out.newLine();
            out.write(" -->");
            out.newLine();
            out.write("<rawdata_loadplugin filename=\"loadRawMzML111.jar\">D:/Data/SILAC_LoadRawDataParams.xtp</rawdata_loadplugin>");
            out.newLine();
            out.write("<identdata_loadplugin filename=\"loadMascotIdent111.jar\">D:/Data/SILAC_LoadIdentDataParams.xtp</identdata_loadplugin>");
            out.newLine();
            out.write("<peakselplugin filename=\"metLabelingPeakSel.jar\">D:/Data/SILAC_PeakSel.xtp</peakselplugin>");
            out.newLine();
            out.write("<quantplugin filename=\"LcMsAreasSimpson.jar\"></quantplugin>");
            out.newLine();
            out.write("<outplugin filename=\"displayTable.jar\">D:/Data/SILAC_DisplayTableParam.xtp</outplugin>");
            out.newLine();
            out.write("</xTrackerPipeline>");
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    private void writeXTrackerRaw(String sExperiment) 
    {
        String sFileName = sExperiment + "_LoadRawDataParams.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\loadRawMzML.xsd\">");
            out.newLine();
            for (int iI=0; iI<jtRawFiles.getRowCount(); iI++)
            {
                out.write(" <datafile>D:/Data/SILACData5000/" + jtRawFiles.getValueAt(iI, 1).toString() + "</datafile>");
                out.newLine();
            }
            out.write("</param>");
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    private void writeXTrackerIdent(String sExperiment) 
    {
        String sFileName = sExperiment + "_LoadIdentDataParams.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!-- ");
            out.newLine();
            out.write("This is the Load_raw and load_identification files parameters file. ");
            out.newLine();
            out.write("It specifies a list of raw data files together with their identification files.");
            out.newLine();
            out.write("Also, modifications and mass shift induced by them are specified here since mascot does not report fixed modification mass shifts.");
            out.newLine();
            out.write("Finally it specifies the threshold on \"pep_delta\" field to accept identifications. ");
            out.newLine();
            out.write("-->");
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\loadMascotIdent.xsd\">");
            out.newLine();
            out.write(" <!--");
            out.newLine();
            out.write(" Specifies the rawdata file names and the corresponding identification names. Note that paths are relative to the xTracker folder.");
            out.newLine();
            out.write(" -->");
            out.newLine();
            out.write(" <inputFiles>");
            out.newLine();
            for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++)
            {
                out.write("     <datafile identification_file=\"D:/Data/SILACData5000/" + jtIdentFiles.getValueAt(iI, 1) + "\">D:/Data/SILACData200/" + jtRawFiles.getValueAt(iI, 1) + "</datafile>");
                out.newLine();   
            }           
            out.write(" </inputFiles>");
            out.newLine();
            out.write("<!-- Modifications (including amino-acid affected) and mass shifts considered in the search are reported below.");
            out.newLine();
            out.write(" NOTE THAT MONOISOTOPIC MASSES ARE USED IN THIS FILE!!!");
            out.newLine();
            out.write("-->");
            out.newLine();
            out.write("<modificationData>");
            out.newLine();
            out.write(" <modification delta=\"42.010559\">Acetyl (K)</modification>");
            out.newLine();
            out.write("</modificationData>");
            out.newLine();
            out.write("<pep_score_threshold>30</pep_score_threshold>");
            out.newLine();
            out.write("</params>");
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
        String sFileName = sExperiment + "_PeakSel.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<!-- ");
            out.newLine();
            out.write("This is the SILAC method parameters file. ");
            out.newLine();
            out.write("-->");
            out.newLine();            
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\metLabelingPeakSel.xsd\">");
            out.newLine();
            out.write(" <!-- Specifies the mass values to use (it can be either \"monoisotopic\" or \"average\") -->");
            out.newLine();
            out.write(" <mass_type>monoisotopic</mass_type>");
            out.newLine();
            out.write(" <!-- Specifies the incorporation rate, normalized to 1 (i.e. 98.5% -> 0.985) -->");
            out.newLine();
            out.write(" <incorporation_rate>0.985</incorporation_rate>");
            out.newLine();
            out.write(" <!-- Specifies the Retention time mass window (in seconds) to consider while trying to match peaks -->");
            out.newLine();
            out.write(" <RT_window>10</RT_window>");
            out.newLine();
            out.write(" <!-- Specifies the M/Z tolerance +/- (in Daltons) to match a MS peak with  the theoretical computed form the parent ion Mz of a peptide +/- its mass shift -->	");
            out.newLine();
            out.write(" <mz_tolerance>0.1</mz_tolerance>");
            out.newLine();
            out.write(" <!-- Specifies the mass shift in Daltons for the C terminus -->");
            out.newLine();
            out.write(" <C_term>0</C_term>");
            out.newLine();
            out.write(" <!-- Specifies the mass shift in Daltons for the N terminus -->");
            out.newLine();
            out.write(" <N_term>0</N_term>");
            out.newLine();
            out.write(" <!-- ");
            out.newLine();
            out.write(" Specifies the mass shift in Daltons for each aminoacid ALL 20 aminoacids will have to be specified");
            out.newLine();
            out.write(" otherwise 0 Daltons shift will be assumed!");
            out.newLine();
            out.write(" -->");
            out.newLine();
            out.write(" <aminoShifts>");
            out.newLine();
            out.write("     <aminoacid value=\"A\">1.0087</aminoacid>");
            out.newLine();
            out.write(" </aminoShifts>");
            out.newLine();
            out.write("</params>");
            out.newLine();
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    }
    private void writeXTrackerOutput(String sExperiment) 
    {
        String sFileName = sExperiment + "_DisplayTableParam.xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins\\displayTable.xsd\">");
            out.newLine();
            out.write(" <normalisation>yes</normalisation>");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton jbCopy;
    private javax.swing.JButton jbCut;
    private javax.swing.JButton jbImportFile;
    private javax.swing.JButton jbNewProject;
    private javax.swing.JButton jbOpenProject;
    private javax.swing.JButton jbPaste;
    private javax.swing.JButton jbSaveProject;
    private javax.swing.JButton jbShow2D;
    private javax.swing.JButton jbShowChromatogram;
    private javax.swing.JDesktopPane jdpMS;
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
    private javax.swing.JMenuBar jmMain;
    private javax.swing.JMenuItem jmMzML2MGF;
    private javax.swing.JMenuItem jmNewProject;
    private javax.swing.JMenuItem jmOpenProject;
    private javax.swing.JMenuItem jmOpenRecentProject;
    private javax.swing.JMenuItem jmOptions;
    private javax.swing.JMenuItem jmPaste;
    private javax.swing.JMenuItem jmPrint;
    private javax.swing.JMenu jmProject;
    private javax.swing.JMenuItem jmRunQuantAnalysis;
    private javax.swing.JMenuItem jmSaveProject;
    private javax.swing.JMenuItem jmSaveProjectAs;
    private javax.swing.JMenu jmStatistics;
    private javax.swing.JMenuItem jmSubmitPRIDE;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenu jmView;
    private javax.swing.JMenuItem jmView2D;
    private javax.swing.JMenuItem jmView3D;
    private javax.swing.JMenuItem jmViewMzML;
    private javax.swing.JMenuItem jmViewMzQuantML;
    private javax.swing.JMenuItem jmViewSpectrum;
    private javax.swing.JMenuItem jmViewXIC;
    private javax.swing.JMenu jmWindow;
    private javax.swing.JPanel jp2D;
    private javax.swing.JPanel jp3D;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpMzML;
    private javax.swing.JPanel jpMzQuantML;
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpRawData;
    private javax.swing.JPanel jpTIC;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpViewer;
    private javax.swing.JProgressBar jpbStatus;
    private javax.swing.JScrollPane jspIdentFiles;
    private javax.swing.JSplitPane jspLeftMenuBottom;
    private javax.swing.JSplitPane jspLeftPanelView;
    private javax.swing.JScrollPane jspLog;
    private javax.swing.JSplitPane jspMainPanelView;
    private javax.swing.JSplitPane jspMzML;
    private javax.swing.JSplitPane jspMzMLDetail;
    private javax.swing.JScrollPane jspMzMLHeader;
    private javax.swing.JScrollPane jspMzMLSubDetail;
    private javax.swing.JScrollPane jspQuantFiles;
    private javax.swing.JScrollPane jspRawData;
    private javax.swing.JScrollPane jspRawFiles;
    private javax.swing.JSplitPane jspRightPanelView;
    private javax.swing.JSplitPane jspViewer;
    private javax.swing.JTable jtIdentFiles;
    private javax.swing.JTable jtMzML;
    private javax.swing.JTable jtQuantFiles;
    private javax.swing.JTable jtRawData;
    private javax.swing.JTable jtRawFiles;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JTextArea jtaMzML;
    private javax.swing.JTabbedPane jtpIdentFiles;
    private javax.swing.JTabbedPane jtpLog;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpQuantFiles;
    private javax.swing.JTabbedPane jtpRawFiles;
    private javax.swing.JTabbedPane jtpViewer;
    // End of variables declaration//GEN-END:variables
}
