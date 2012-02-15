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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import org.proteosuite.data.Sample;
import org.proteosuite.data.SpectrumData;
import org.proteosuite.utils.TwoDPlot;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVList;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

        
/**
 *
 * @author faviel
 */
public class ProteoSuiteView extends javax.swing.JFrame {

    //... Defining list of unmarshaller objects ...//
    private ArrayList<MzMLUnmarshaller> iUnmarshaller = null;
    
    /** Creates new form ProteoSuiteView */
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
        
        initComponents();
        
        //... Window default settings ...//
        this.setTitle("ProteoSuite 0.1.0 - Suite for the Analysis of Quantitative Proteomics Data (Beta Version)");        
        
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

        jspLeft.setDividerLocation(200);
        jspCenter.setDividerLocation(600);
        jspViews.setDividerLocation(600);
        jtpViews.setSelectedIndex(0);
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

        jpMain = new javax.swing.JPanel();
        jspLeft = new javax.swing.JSplitPane();
        jspCenter = new javax.swing.JSplitPane();
        jpProperties = new javax.swing.JPanel();
        jtpProperties = new javax.swing.JTabbedPane();
        jpMzML = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jspViews = new javax.swing.JSplitPane();
        jtpViews = new javax.swing.JTabbedPane();
        jpMS = new javax.swing.JPanel();
        jpXIC = new javax.swing.JPanel();
        jp2D = new javax.swing.JPanel();
        jp3D = new javax.swing.JPanel();
        jspGrid = new javax.swing.JScrollPane();
        jtGrid = new javax.swing.JTable();
        jspTree = new javax.swing.JScrollPane();
        jtMainTree = new javax.swing.JTree();
        jpStatusBar = new javax.swing.JPanel();
        jpToolBar = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmNewProject = new javax.swing.JMenuItem();
        jmImportFile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmOpenProject = new javax.swing.JMenuItem();
        jmOpenRecentProject = new javax.swing.JMenuItem();
        jmSaveProject = new javax.swing.JMenuItem();
        jmSaveProjectAs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmPrint = new javax.swing.JMenuItem();
        jmExit = new javax.swing.JMenuItem();
        jmEdit = new javax.swing.JMenu();
        jmView = new javax.swing.JMenu();
        jmAnalyze = new javax.swing.JMenu();
        jmStatistics = new javax.swing.JMenu();
        jmVisualization = new javax.swing.JMenu();
        jmUtilites = new javax.swing.JMenu();
        jmDatabases = new javax.swing.JMenu();
        jmTools = new javax.swing.JMenu();
        jmWindow = new javax.swing.JMenu();
        jmHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jSplitPane1.setTopComponent(jScrollPane1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "ID", "MS", "Base peak m/z", "Base peak int", "Total ion curr", "Lowest obs m/z", "Highest obs m/z", "Ret Time", "Scan Win Min", "Scan Win Max"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jpMzMLLayout = new javax.swing.GroupLayout(jpMzML);
        jpMzML.setLayout(jpMzMLLayout);
        jpMzMLLayout.setHorizontalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );
        jpMzMLLayout.setVerticalGroup(
            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzML Data", jpMzML);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 806, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 565, Short.MAX_VALUE)
        );

        jtpProperties.addTab("mzQuantML", jPanel1);

        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
        jpProperties.setLayout(jpPropertiesLayout);
        jpPropertiesLayout.setHorizontalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
        );
        jpPropertiesLayout.setVerticalGroup(
            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
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
        jspGrid.setViewportView(jtGrid);

        jspViews.setRightComponent(jspGrid);

        jspCenter.setLeftComponent(jspViews);

        jspLeft.setRightComponent(jspCenter);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jtMainTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jtMainTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMainTreeMouseClicked(evt);
            }
        });
        jspTree.setViewportView(jtMainTree);

        jspLeft.setLeftComponent(jspTree);

        javax.swing.GroupLayout jpMainLayout = new javax.swing.GroupLayout(jpMain);
        jpMain.setLayout(jpMainLayout);
        jpMainLayout.setHorizontalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
        );
        jpMainLayout.setVerticalGroup(
            jpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
        );

        jpStatusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpStatusBarLayout = new javax.swing.GroupLayout(jpStatusBar);
        jpStatusBar.setLayout(jpStatusBarLayout);
        jpStatusBarLayout.setHorizontalGroup(
            jpStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        jpStatusBarLayout.setVerticalGroup(
            jpStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jpToolBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jpToolBarLayout = new javax.swing.GroupLayout(jpToolBar);
        jpToolBar.setLayout(jpToolBarLayout);
        jpToolBarLayout.setHorizontalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        jpToolBarLayout.setVerticalGroup(
            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
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
        jmImportFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmImportFileMouseClicked(evt);
            }
        });
        jmImportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmImportFileActionPerformed(evt);
            }
        });
        jmFile.add(jmImportFile);
        jmFile.add(jSeparator1);

        jmOpenProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jmOpenProject.setText("Open Project");
        jmFile.add(jmOpenProject);

        jmOpenRecentProject.setText("Open Recent Project");
        jmFile.add(jmOpenRecentProject);

        jmSaveProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jmSaveProject.setText("Save Project");
        jmFile.add(jmSaveProject);

        jmSaveProjectAs.setText("Save Project As");
        jmFile.add(jmSaveProjectAs);
        jmFile.add(jSeparator2);

        jmPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jmPrint.setText("Print");
        jmFile.add(jmPrint);

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
            .addComponent(jpStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
        // TODO add your handling code here:
        int exit = JOptionPane.showConfirmDialog(null, "Do you want to exit?");

        if (exit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }        
        
    }//GEN-LAST:event_jmExitActionPerformed

    private void jmNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNewProjectActionPerformed
        // TODO add your handling code here:
        int exit = JOptionPane.showConfirmDialog(null, "Create a new project?");

        if (exit == JOptionPane.YES_OPTION) {
            //... We need to dump all objects we have in memory ...//

            //... Removing initial jTree default configuration ...//
            //jTMainTree.removeAll();

        }        
    }//GEN-LAST:event_jmNewProjectActionPerformed
    private void show2DPlot(java.awt.event.ActionEvent evt) {                                             
       
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
            MzMLUnmarshaller unmarshaller = iUnmarshaller.get(iIndex);            

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
    private void showSpectrum(java.awt.event.ActionEvent evt) {                                             
        
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jtpViews.setSelectedIndex(0);            
            String nodeName = node.getUserObject().toString();
            int iPos = nodeName.indexOf("@");

            nodeName = "controllerType=0 controllerNumber=1 scan=" + nodeName.substring(5,iPos-1).trim();           
            System.out.println(nodeName);
            
            //... Get index from spectrums ...//
            int iIndex = 0;
            MzMLUnmarshaller unmarshaller = iUnmarshaller.get(iIndex);            
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
    private void showChromatogram(java.awt.event.ActionEvent evt) {                                             
        
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jtMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jtpViews.setSelectedIndex(1);
            Object nodeInfo = node.getUserObject();

            //... Get index from spectrums ...//
            int iIndex = 0;
            MzMLUnmarshaller unmarshaller = iUnmarshaller.get(iIndex);
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
    
    private void jmImportFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmImportFileMouseClicked
        
        

    }//GEN-LAST:event_jmImportFileMouseClicked

    private void jmImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmImportFileActionPerformed
        
        //... Defining tree nodes (root=directory, sampleNode=file, ms1Node=LC-MS, ms2Node=LC-MS/MS ...//
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
        chooser.setCurrentDirectory(new java.io.File("D:/Data")); //... If not found it goes to Home, not need for exception ...//

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
            
            File [] aFiles = chooser.getSelectedFiles();

            rootNode = new DefaultMutableTreeNode("Project - "); //... This will contain the name of the project once we set it up ..//            

	    if (aFiles != null && aFiles.length > 0)
            {
                //... Code to be inserted here ...//
                if (aFiles[0].getName().indexOf(".mzML") >0) 
                {
                    //.... VALIDATING FILE ...//


                    //... Setting progress bar ...// (To do: This needs to be set up in a thread)
                    //********************************************************
                    final JFrame frameProgBar = new JFrame("Processing files ");
                    final JPanel panel = new JPanel();
                    Image iconApp = new ImageIcon(this.getClass().getClassLoader().getResource("images/icon.gif")).getImage();
                    Icon procIcon = new ImageIcon(getClass().getClassLoader().getResource("images/processing.gif"));
                    JLabel lprocess = new JLabel("Processing file... ");
                    lprocess.setIcon(procIcon);
                    panel.add(lprocess);
                    panel.setBackground(Color.white);
                    panel.setPreferredSize(new java.awt.Dimension(300, 50));
                    frameProgBar.setContentPane(panel);
                    frameProgBar.setIconImage(iconApp);
                    frameProgBar.setResizable(false);
                    frameProgBar.pack();


                    Sample aSamples[] = new Sample[aFiles.length];

                    //... Removing initial jTree default configuration ...//
                    jtMainTree.removeAll();                

                    //... Reading selected files ...//
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        frameProgBar.setTitle("Processing file: <" + aFiles[iI].getName() + ">");
                        File xmlFile = new File(aFiles[iI].getPath());

                        //... Unmarshall data using jzmzML API ...//
                        iUnmarshaller = new ArrayList<MzMLUnmarshaller>();
                        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
                        iUnmarshaller.add(unmarshaller);                    

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
                            iK++;
                        }                    
                    }

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

                    new Thread("Closing")
                    {
                        @Override
                        public void run()
                        {
                            frameProgBar.setVisible(false);
                            frameProgBar.dispose();
                        }
                    }.start();
                } //... From read mzML
                
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
                        showSpectrum(evt);
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
                            show2DPlot(evt);
                        }
                    });
                    popup.add(menuItem);
                    menuItem2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            showChromatogram(evt);
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProteoSuiteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProteoSuiteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProteoSuiteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProteoSuiteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ProteoSuiteView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenu jmAnalyze;
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
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpStatusBar;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpXIC;
    private javax.swing.JSplitPane jspCenter;
    private javax.swing.JScrollPane jspGrid;
    private javax.swing.JSplitPane jspLeft;
    private javax.swing.JScrollPane jspTree;
    private javax.swing.JSplitPane jspViews;
    private javax.swing.JTable jtGrid;
    private javax.swing.JTree jtMainTree;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpViews;
    // End of variables declaration//GEN-END:variables
}
