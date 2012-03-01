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
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.proteosuite.data.Sample;
import org.proteosuite.data.SpectrumData;
import org.proteosuite.utils.TwoDPlot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVList;
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

        
/**
 * @author faviel
 */
public class ProteoSuiteView extends JFrame {

    //... Defining a list of unmarshaller objects (all files from one experiment)  ...//
    private ArrayList<MzMLUnmarshaller> alUnmarshaller = null;
    private String sProjectName;
    private String sWorkspace;
    private boolean bProjectChanges;
    
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
        this.setTitle("ProteoSuite 0.1.1 (Beta Version) - Software for the Analysis of Quantitative Proteomics Data  <Project: " + this.sProjectName +  ">");
        
        //... Setting icons ...//        
        Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        this.setIconImage(iconApp);
        
        //... Main Menu icons ...//
        Icon newIcon = new ImageIcon(getClass().getClassLoader().getResource("images/new.gif"));
        Icon importIcon = new ImageIcon(getClass().getClassLoader().getResource("images/import.gif"));
        Icon openIcon = new ImageIcon(getClass().getClassLoader().getResource("images/open.gif"));
        Icon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("images/save.gif"));
        Icon printIcon = new ImageIcon(getClass().getClassLoader().getResource("images/print.gif"));
        
        jmNewProject.setIcon(newIcon);
        jmImportFile.setIcon(importIcon);
        jmOpenProject.setIcon(openIcon);
        jmSaveProject.setIcon(saveIcon);
        jmPrint.setIcon(printIcon);

        //... Object Tree icons ...//
        Icon leafIcon = new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif"));
        Icon openleafIcon = new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif"));
        Icon closeleafIcon = new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif"));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)jtMainTree.getCellRenderer();
        renderer.setLeafIcon(leafIcon);
        renderer.setClosedIcon(closeleafIcon);
        renderer.setOpenIcon(openleafIcon);
        
        //... Setting Window Height and Width ...//
        this.setMinimumSize(new Dimension(1024, 780));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //... Setting dividers ...//
        jspLeft.setDividerLocation(220);
        jspCenter.setDividerLocation(800);
        jspViews.setDividerLocation(550);
        jspMzML.setDividerLocation(100);
        jtpViews.setSelectedIndex(0);
        
        //... Configuring exit events...//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        jToolBar2 = new javax.swing.JToolBar();
        jToolBar3 = new javax.swing.JToolBar();
        jpbStatus = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jpMain = new javax.swing.JPanel();
        jspLeft = new javax.swing.JSplitPane();
        jspTree = new javax.swing.JScrollPane();
        jtMainTree = new javax.swing.JTree();
        jspCenter = new javax.swing.JSplitPane();
        jpProperties = new javax.swing.JPanel();
        jtpProperties = new javax.swing.JTabbedPane();
        jpMzML = new javax.swing.JPanel();
        jspMzML = new javax.swing.JSplitPane();
        jspMzMLHeader = new javax.swing.JScrollPane();
        jtaMzML = new javax.swing.JTextArea();
        jspMzMLDetail = new javax.swing.JScrollPane();
        jtMzML = new javax.swing.JTable();
        jpMzQuantML = new javax.swing.JPanel();
        jspViews = new javax.swing.JSplitPane();
        jtpViews = new javax.swing.JTabbedPane();
        jpMS = new javax.swing.JPanel();
        jpXIC = new javax.swing.JPanel();
        jp2D = new javax.swing.JPanel();
        jp3D = new javax.swing.JPanel();
        jspGrid = new javax.swing.JScrollPane();
        jtGrid = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
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
        jmView = new javax.swing.JMenu();
        jmAnalyze = new javax.swing.JMenu();
        jmSetIdentParams = new javax.swing.JMenuItem();
        jmSetQuantParams = new javax.swing.JMenuItem();
        jmStatistics = new javax.swing.JMenu();
        jmVisualization = new javax.swing.JMenu();
        jmUtilites = new javax.swing.JMenu();
        jmDatabases = new javax.swing.JMenu();
        jmTools = new javax.swing.JMenu();
        jmWindow = new javax.swing.JMenu();
        jmHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpToolBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jpToolBar.setPreferredSize(new java.awt.Dimension(933, 32));

        jToolBar1.setRollover(true);

        jToolBar2.setRollover(true);

        jToolBar3.setRollover(true);

        jLabel1.setText("Task monitor:");

        javax.swing.GroupLayout jpToolBarLayout = new javax.swing.GroupLayout(jpToolBar);
        jpToolBar.setLayout(jpToolBarLayout);
        jpToolBarLayout.setHorizontalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpToolBarLayout.setVerticalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addGroup(jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jpbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpToolBarLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspTree.setMinimumSize(new java.awt.Dimension(200, 300));
        jspTree.setPreferredSize(new java.awt.Dimension(200, 300));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Project -");
        jtMainTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jtMainTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMainTreeMouseClicked(evt);
            }
        });
        jspTree.setViewportView(jtMainTree);

        jspLeft.setLeftComponent(jspTree);

        jspMzML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtaMzML.setColumns(20);
        jtaMzML.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jtaMzML.setRows(5);
        jspMzMLHeader.setViewportView(jtaMzML);

        jspMzML.setTopComponent(jspMzMLHeader);

        jtMzML.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "ID", "MS", "Base peak m/z", "Base peak int", "Total ion curr", "Lowest obs m/z", "Highest obs m/z", "Ret Time", "Scan Win Min", "Scan Win Max"
            }
        ));
        jspMzMLDetail.setViewportView(jtMzML);

        jspMzML.setRightComponent(jspMzMLDetail);

        javax.swing.GroupLayout jpMzMLLayout = new javax.swing.GroupLayout(jpMzML);
        jpMzML.setLayout(jpMzMLLayout);
        jpMzMLLayout.setHorizontalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        jpMzMLLayout.setVerticalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzML Data", jpMzML);

        javax.swing.GroupLayout jpMzQuantMLLayout = new javax.swing.GroupLayout(jpMzQuantML);
        jpMzQuantML.setLayout(jpMzQuantMLLayout);
        jpMzQuantMLLayout.setHorizontalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
        );
        jpMzQuantMLLayout.setVerticalGroup(
            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzQuantML", jpMzQuantML);

        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
        jpProperties.setLayout(jpPropertiesLayout);
        jpPropertiesLayout.setHorizontalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
        );
        jpPropertiesLayout.setVerticalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );

        jspCenter.setRightComponent(jpProperties);

        jspViews.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jpMS.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpMSLayout = new javax.swing.GroupLayout(jpMS);
        jpMS.setLayout(jpMSLayout);
        jpMSLayout.setHorizontalGroup(
            jpMSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        jpMSLayout.setVerticalGroup(
            jpMSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtpViews.addTab("Spectrum", jpMS);

        jpXIC.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpXICLayout = new javax.swing.GroupLayout(jpXIC);
        jpXIC.setLayout(jpXICLayout);
        jpXICLayout.setHorizontalGroup(
            jpXICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        jpXICLayout.setVerticalGroup(
            jpXICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtpViews.addTab("XIC", jpXIC);

        jp2D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp2DLayout = new javax.swing.GroupLayout(jp2D);
        jp2D.setLayout(jp2DLayout);
        jp2DLayout.setHorizontalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        jp2DLayout.setVerticalGroup(
            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtpViews.addTab("2D View", jp2D);

        jp3D.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jp3DLayout = new javax.swing.GroupLayout(jp3D);
        jp3D.setLayout(jp3DLayout);
        jp3DLayout.setHorizontalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );
        jp3DLayout.setVerticalGroup(
            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtpViews.addTab("3D View", jp3D);

        jtpViews.setSelectedIndex(3);

        jspViews.setTopComponent(jtpViews);

        jtGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Version", "ID", "Accession", "CVs", "Spectrums"
            }
        ));
        jtGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtGridMouseClicked(evt);
            }
        });
        jspGrid.setViewportView(jtGrid);

        jspViews.setRightComponent(jspGrid);

        jspCenter.setLeftComponent(jspViews);

        jspLeft.setRightComponent(jspCenter);

        javax.swing.GroupLayout jpMainLayout = new javax.swing.GroupLayout(jpMain);
        jpMain.setLayout(jpMainLayout);
        jpMainLayout.setHorizontalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        jpMainLayout.setVerticalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
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

        jMenuBar1.add(jmFile);

        jmEdit.setText("Edit");
        jMenuBar1.add(jmEdit);

        jmView.setText("View");
        jMenuBar1.add(jmView);

        jmAnalyze.setText("Analyze");

        jmSetIdentParams.setText("Set Ident Params");
        jmSetIdentParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSetIdentParamsActionPerformed(evt);
            }
        });
        jmAnalyze.add(jmSetIdentParams);

        jmSetQuantParams.setText("Set Quant Params");
        jmSetQuantParams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSetQuantParamsActionPerformed(evt);
            }
        });
        jmAnalyze.add(jmSetQuantParams);

        jMenuBar1.add(jmAnalyze);

        jmStatistics.setText("Statistics");
        jMenuBar1.add(jmStatistics);

        jmVisualization.setText("Visualization");
        jMenuBar1.add(jmVisualization);

        jmUtilites.setText("Utilities");
        jMenuBar1.add(jmUtilites);

        jmDatabases.setText("Databases");
        jMenuBar1.add(jmDatabases);

        jmTools.setText("Tools");
        jMenuBar1.add(jmTools);

        jmWindow.setText("Window");
        jMenuBar1.add(jmWindow);

        jmHelp.setText("Help");
        jMenuBar1.add(jmHelp);

        setJMenuBar(jMenuBar1);

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
                .addComponent(jpToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        if (this.bProjectChanges)
        {
            if (this.sProjectName.equals("None"))
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
                
        //... Defining tree nodes (root=Project, sampleNode=Files, ms1Node=LC-MS, ms2Node=LC-MS/MS ...//
        System.out.println("Project Changes=" + bProjectChanges);
        DefaultMutableTreeNode rootNode = null;
        DefaultMutableTreeNode sampleNode = null;
        DefaultMutableTreeNode ms1Node = null;
        DefaultMutableTreeNode ms2Node = null;

        //... Selecting file(s) ...//
        JFileChooser chooser = new JFileChooser("user.home");
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mzML Files (*.mzML)", "mzML");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("mzIdentML Files (*.mzid)", "mzid");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq)", "mzq");

        FileNameExtensionFilter filter4 = new FileNameExtensionFilter("mzML Compressed Files (*.mzML.gz)", "gz");
        FileNameExtensionFilter filter5 = new FileNameExtensionFilter("mzIdentML Compressed Files (*.mzid.gz)", "gz");
        FileNameExtensionFilter filter6 = new FileNameExtensionFilter("mzQuantML Compressed Files (*.mzq.gz)", "gz");

        //... Filers must be in descending order ...//
        chooser.setFileFilter(filter6);
        chooser.setFileFilter(filter5);
        chooser.setFileFilter(filter4);
        chooser.setFileFilter(filter3);
        chooser.setFileFilter(filter2);
        chooser.setFileFilter(filter);

        //... Enable multiple file selection ...//
        chooser.setMultiSelectionEnabled(true);

        //... Setting default directory ...//
        chooser.setCurrentDirectory(new java.io.File("D:/Data")); //... If not found it goes to Home, exception handle not needed ...//

        //... Customising icons for jtree ...//
        Icon rootIcon = new ImageIcon(getClass().getClassLoader().getResource("images/root.gif"));
        Icon sampleIcon = new ImageIcon(getClass().getClassLoader().getResource("images/file.gif"));
        Icon ms1Icon = new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif"));
        Icon ms2Icon = new ImageIcon(getClass().getClassLoader().getResource("images/scan.gif"));
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(null);

        //... Displaying selected data ...//
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {

            this.bProjectChanges = true;
            
            //... A common experiment will have around 6-20 files ....//
            File [] aFiles = chooser.getSelectedFiles();

            rootNode = new DefaultMutableTreeNode("Project - " + this.sProjectName); //... This will contain the name of the project once we set it up ..//            

	    if (aFiles != null && aFiles.length > 0)
            {
                //... Code to be inserted here ...//
                if (aFiles[0].getName().indexOf(".mzML") > 0) 
                {
                    //... Setting progress bar ...// (To do: This needs to be set up in a thread)
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //jpbStatus.setStringPainted(true);                    

                    Sample aSamples[] = new Sample[aFiles.length];

                    //... Removing initial jTree default configuration ...//
                    jtMainTree.removeAll();       
                    alUnmarshaller = new ArrayList<MzMLUnmarshaller>();

                    //... Reading selected files ...//
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        File xmlFile = new File(aFiles[iI].getPath());                        
//                        jpbStatus.setValue(0);
                        
                        //jLabel2.setText("Processing file (" + (iI+1) + " of " + aFiles.length + ")");
                        //try{Thread.sleep(500);} 
                        //catch (InterruptedException err){}                        
                        //new Thread(new threadProgBar(aFiles[iI].getName())).start();                         

                        //... Unmarshall data using jzmzML API ...//
                        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
                        alUnmarshaller.add(unmarshaller);

                        //... Creating object for samples ...//
                        CVList cvList = unmarshaller.unmarshalFromXpath("/cvList", CVList.class);                    
                        aSamples[iI] = new Sample(unmarshaller.getMzMLId(),
                                                    aFiles[iI].getName(),
                                                    unmarshaller.getMzMLVersion(),
                                                    unmarshaller.getMzMLAccession(), cvList.getCount(),
                                                    unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));

                        //... Populate the JTree with samples ...//
                        sampleNode = new DefaultMutableTreeNode(aSamples[iI].getName());
                        rootNode.add(sampleNode);

                        //... Populate the JTree with spectrums ...//                    
                        int spectrums = unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum");

                        //... Reading spectrum data ...//
                        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
                        int iJ = 0; //... For Spectrum Counter ...//
                        int iK = 0; //... For Spectrum Counter ...//
                        int iPercentage = 0;
                        SpectrumData[][] aSpectrums = new SpectrumData[aFiles.length][spectrums]; //... To store MS1 and MS2 spectrums ...//
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
                                    aSpectrums[iI][iK] = new SpectrumData(spectrum.getId(), nodeName, mslevel, rt);
                                    ms2Node = new DefaultMutableTreeNode(nodeName);
                                    ms1Node.add(ms2Node);
                                }
                            }
                            else
                            {
                               aSpectrums[iI][iK] = new SpectrumData(spectrum.getId(), nodeName, mslevel, rt);
                               ms1Node = new DefaultMutableTreeNode(nodeName);
                               sampleNode.add(ms1Node);
                               iJ++;
                            }
                            iPercentage = Math.round(iK/spectrums*100);
                            if (iPercentage%2 == 0)
                            {
                                //jpbStatus.setValue(iPercentage);
                            }
                            iK++;
                        }
//                        jpbStatus.setValue(100);                                            
//                        jpbStatus.repaint();                        
                    } //... For files ...//                    
                    
                    //... Draw tree ...//
                    jtMainTree.setModel(new DefaultTreeModel(rootNode));
                    jtMainTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);                                

                    //... Fill JTable ...//
                    DefaultTableModel model = new DefaultTableModel();
                    jtGrid.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Version");                
                    model.addColumn("Accession");
                    model.addColumn("CVs");
                    model.addColumn("Spectrums");
                    for(int iI=0; iI<aSamples.length; iI++)
                    {
                        model.insertRow(model.getRowCount(), new Object[]{aSamples[iI].getId(),
                                                                          aSamples[iI].getName(),
                                                                          aSamples[iI].getVersion(),                                                                      
                                                                          aSamples[iI].getAccession(),
                                                                          aSamples[iI].getCVs(),
                                                                          aSamples[iI].getSpectrums()});
                    }
                    setCursor(null); //... Turn off the wait cursor ...//                    
//                    try{Thread.sleep(500);} 
//                    catch (InterruptedException err){}
//                    jpbStatus.setValue(0);                                        
//                    jpbStatus.repaint();                     
//                    jLabel2.setText("");
                    
                } //... From reading mzML files ...//
                
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
        if (this.bProjectChanges)
        {
            if (this.sProjectName.equals("None"))
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save the current project?", "Open Project", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) 
                {
                    System.out.println("Yes, I want to save all changes.");
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
                        System.out.println("No, I don't want to save any changes.");
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
        // TODO add your handling code here:
        jpbStatus.setIndeterminate(false);
    }//GEN-LAST:event_jmOpenRecentProjectActionPerformed

    private void jtMainTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMainTreeMouseClicked
       //... Validate right click on tree nodes ...//
        if (evt.getButton()== 3)
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)jtMainTree.getLastSelectedPathComponent();
            int x = evt.getX();
            int y = evt.getY();

            if (node == null) return; 

            //... Getting object values ...//
            Object nodeInfo = node.getUserObject();

            //... Check if node is a spectrum or file ...//
            if (nodeInfo.toString().indexOf("Scan:") >= 0)   //... Scan files can be visualized as spectrums ...//
            {                
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("Show Spectrum");
                menuItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        showSpectrum();
                    }
                });
                popup.add(menuItem);
                popup.show(jtMainTree, x, y);                
            }
            else
            {
                if (nodeInfo.toString().indexOf(".mzML") >= 0)  //... mzML files can be visualized in Chromatograms and 2D ...//
                {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem menuItem = new JMenuItem("Show 2D View");
                    JMenuItem menuItem2 = new JMenuItem("Show Chromatogram");
                    menuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            show2DPlot();
                        }
                    });
                    popup.add(menuItem);
                    menuItem2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            showChromatogram();
                        }
                    });
                    popup.add(menuItem2);
                    popup.show(jtMainTree, x, y);
                }
            }
        }         
        else
        {
            //... Validate click on spectrums ...//
            if (evt.getButton()== 1)
            {
                 showSpectrumData();
            }
        }        
    }//GEN-LAST:event_jtMainTreeMouseClicked

    private void jmSaveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectActionPerformed
        
        if (this.bProjectChanges)
        {
            writeConfigFile(sProjectName);
        }        
    }//GEN-LAST:event_jmSaveProjectActionPerformed

    private void jmSaveProjectAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectAsActionPerformed
        
        saveProject();
    }//GEN-LAST:event_jmSaveProjectAsActionPerformed

    private void jmCloseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCloseProjectActionPerformed
        //... Validate modification options ...//
        if (this.bProjectChanges)
        {
            if (this.sProjectName.equals("None"))
            {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to save this project?", "Closing project", JOptionPane.YES_NO_CANCEL_OPTION);
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

    private void jtGridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtGridMouseClicked
        if (evt.getButton()== 1)
        {
            loadMzMLView();
        }
    }//GEN-LAST:event_jtGridMouseClicked

    private void jmSetIdentParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSetIdentParamsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmSetIdentParamsActionPerformed

    private void jmSetQuantParamsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSetQuantParamsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmSetQuantParamsActionPerformed
    
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
        
    private void showSpectrum() {                                             
        
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jtpViews.setSelectedIndex(0);            
            String nodeName = node.getUserObject().toString();
            int iPos = nodeName.indexOf("@");

            nodeName = "controllerType=0 controllerNumber=1 scan=" + nodeName.substring(5,iPos-1).trim();           
            
            //... Get index from spectrums ...//
            int iIndex = 0;
            MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);            
            try
            {
               Spectrum spectrum = unmarshaller.getSpectrumById(nodeName);
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

               JPanel specpanel = new SpectrumPanel(mz, intensities, 0.0, "", nodeName.substring(0,10), 50, false, true, true, true, 1);
               JInternalFrame internal = new JInternalFrame("MS spectrum <" + nodeName + ">");
               specpanel.setSize(new java.awt.Dimension(600, 400));
               specpanel.setPreferredSize(new java.awt.Dimension(600, 400));
               Icon icon = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif"));
               internal.setFrameIcon(icon);
               internal.setResizable(true);
               internal.setMaximizable(true);
               internal.setClosable(true);
               internal.setIconifiable(true);

               //internal.add(specpanel);
               internal.pack();
               internal.setVisible(true);
               jpMS.add(internal);
            }
            catch (MzMLUnmarshallerException ume)
            {
                System.out.println(ume.getMessage());
            }
    }     
    private void showSpectrumData() {                                             
        
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jtpViews.setSelectedIndex(0);
            String nodeName = node.getUserObject().toString();

            nodeName = "controllerType=0 controllerNumber=1 scan=" + nodeName.toString();           
            System.out.println(nodeName);
            
    }         
    private void showChromatogram() {                                             
        
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jtpViews.setSelectedIndex(1);
            Object nodeInfo = node.getUserObject();

            //... Get index from spectrums ...//
            int iIndex = 0;
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

               JInternalFrame internal = new JInternalFrame("Chromatogram <" + nodeInfo.toString() + ">");
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
               jpXIC.add(internal);
            }
            catch (MzMLUnmarshallerException ume)
            {
                System.out.println(ume.getMessage());
            }
    }         
    private void show2DPlot() {                                             
       
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();
            if (node == null) return;

            //... Getting values from nodes ...//
            Object nodeInfo = node.getUserObject();

            double[] mz = new double[1000000];
            double[] intensities = new double[1000000];
            double[] art = new double[1000000];

            double rt = 0;
            int iCounter = 0;

            //... Get index from spectrums ...//
            int iIndex = 0;
            MzMLUnmarshaller unmarshaller = alUnmarshaller.get(iIndex);            

            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            int iK = 0;
            while ((spectrumIterator.hasNext())&&(iK < 500))
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
            TwoDPlot demo2 = new TwoDPlot(nodeInfo.toString(), mz, intensities, art);
            jp2D.add(demo2);            
            demo2.pack();
            demo2.setVisible(true);
            jtpViews.setSelectedIndex(2);
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
        chooser.setCurrentDirectory(new java.io.File("D:/Data")); //... If not found it goes to Home, exception handle not needed ...//        
        
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
            //... Write all files ...//
            for (int iI=0; iI<jtGrid.getRowCount(); iI++)
            {
                out.write("         <rawFile ");
                out.write(" id=\"" + jtGrid.getValueAt(iI, 0) + "\"");
                out.write(" name=\"" + jtGrid.getValueAt(iI, 1) + "\"");
                out.write(" version=\"" + jtGrid.getValueAt(iI, 2) + "\"");
                out.write(" accession=\"" + jtGrid.getValueAt(iI, 3) + "\"");
                out.write(" cvs=\"" + jtGrid.getValueAt(iI, 4) + "\"");
                out.write(" spectrums=\"" + jtGrid.getValueAt(iI, 5) + "\" >");
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
        model.addColumn("Index");
        model.addColumn("ID");
        model.addColumn("MS");
        model.addColumn("Base peak m/z");
        model.addColumn("Base peak int");
        model.addColumn("Total ion current");
        model.addColumn("Lowest obs m/z");
        model.addColumn("Highest obs m/z");
        model.addColumn("Ret Time");
        model.addColumn("Scan Win Min");
        model.addColumn("Scan Win Max");     
        String sOutput="";
        String sVar="";
        
        MzMLUnmarshaller unmarshaller = alUnmarshaller.get(jtGrid.getSelectedRow());
        
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
        String basePeakMZ = "";
        String basePeakInt = "";
        String totalIonCurrent = "";
        String lowestObsMZ = "";
        String highestObsMZ = "";
        String unitRT = "";
        float rt = 0;
        String scanWinMin = "";
        String scanWinMax = "";
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
                   basePeakMZ = lCVParam.getValue().trim();
               }
               if (lCVParam.getAccession().equals("MS:1000505")) 
               {
                   basePeakInt = lCVParam.getValue().trim();
               }
               if (lCVParam.getAccession().equals("MS:1000285")) 
               {
                   totalIonCurrent = lCVParam.getValue().trim();
               }
               if (lCVParam.getAccession().equals("MS:1000528")) 
               {
                   lowestObsMZ = lCVParam.getValue().trim();
               }
               if (lCVParam.getAccession().equals("MS:1000527")) 
               {
                   highestObsMZ = lCVParam.getValue().trim();
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
            
            model.insertRow(model.getRowCount(), new Object[]{spectrum.getIndex().toString(), 
                spectrum.getId().toString(),
                msLevel,
                basePeakMZ,
                basePeakInt,
                totalIonCurrent,
                lowestObsMZ,
                highestObsMZ,
                String.format("%.4f", rt),
                scanWinMin,
                scanWinMax
                });
        }
        
    }
    private void initProjectValues() 
    {
        DefaultMutableTreeNode rootNode = null;
        
        this.sProjectName = "None";
        this.bProjectChanges = false;
        this.sWorkspace = "";
        
        jtMainTree.removeAll();
        rootNode = new DefaultMutableTreeNode("Project - " + this.sProjectName);        
        jtMainTree.setModel(new DefaultTreeModel(rootNode));
        jtMainTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
                    
        DefaultTableModel model = new DefaultTableModel();
        jtGrid.setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Version");                
        model.addColumn("Accession");
        model.addColumn("CVs");
        model.addColumn("Spectrums");        
        
        System.out.println("Cleaning variables");
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
        chooser.setCurrentDirectory(new java.io.File("D:/Data")); //... If not found it goes to Home, exception handle not needed ...//
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);        
        
        //... Write file ...//
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            sFileName = file.toString();
            if (file.exists())
            {
                    readConfigFile(sFileName);
                    //... Unmarshall ...//
                    alUnmarshaller = new ArrayList<MzMLUnmarshaller>();
                    
                    for (int iI=0; iI<jtGrid.getRowCount(); iI++)
                    {                    
                        File xmlFile = new File("D:\\Data\\"+jtGrid.getValueAt(iI, 1).toString());

                        //... Unmarshall data using jzmzML API ...//
                        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
                        alUnmarshaller.add(unmarshaller);
                    }
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
            this.bProjectChanges = false;

            //... Fill JTable ...//
            DefaultTableModel model = new DefaultTableModel();
            jtGrid.setModel(model);
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Version");                
            model.addColumn("Accession");
            model.addColumn("CVs");
            model.addColumn("Spectrums");


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
                                                                      rawFileElement.getAttribute("spectrums").toString()});
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JMenu jmAnalyze;
    private javax.swing.JMenuItem jmCloseProject;
    private javax.swing.JMenu jmDatabases;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenuItem jmExit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenuItem jmImportFile;
    private javax.swing.JMenuItem jmNewProject;
    private javax.swing.JMenuItem jmOpenProject;
    private javax.swing.JMenuItem jmOpenRecentProject;
    private javax.swing.JMenuItem jmPrint;
    private javax.swing.JMenuItem jmSaveProject;
    private javax.swing.JMenuItem jmSaveProjectAs;
    private javax.swing.JMenuItem jmSetIdentParams;
    private javax.swing.JMenuItem jmSetQuantParams;
    private javax.swing.JMenu jmStatistics;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenu jmUtilites;
    private javax.swing.JMenu jmView;
    private javax.swing.JMenu jmVisualization;
    private javax.swing.JMenu jmWindow;
    private javax.swing.JPanel jp2D;
    private javax.swing.JPanel jp3D;
    private javax.swing.JPanel jpMS;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpMzML;
    private javax.swing.JPanel jpMzQuantML;
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpXIC;
    private javax.swing.JProgressBar jpbStatus;
    private javax.swing.JSplitPane jspCenter;
    private javax.swing.JScrollPane jspGrid;
    private javax.swing.JSplitPane jspLeft;
    private javax.swing.JSplitPane jspMzML;
    private javax.swing.JScrollPane jspMzMLDetail;
    private javax.swing.JScrollPane jspMzMLHeader;
    private javax.swing.JScrollPane jspTree;
    private javax.swing.JSplitPane jspViews;
    private javax.swing.JTable jtGrid;
    private javax.swing.JTree jtMainTree;
    private javax.swing.JTable jtMzML;
    private javax.swing.JTextArea jtaMzML;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpViews;
    // End of variables declaration//GEN-END:variables
}
