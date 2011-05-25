/*
 * -----------------------------------------------------------------
 * ProteoSuiteView.java
 * -----------------------------------------------------------------
 * Description:       GUI for Proteomics Quantitative Analysis
 * Developer:         Faviel Gonzalez
 * Notes:             GUI generated using NetBeans IDE 6.9.1
 * Created:           08 February 2011
 * -----------------------------------------------------------------
 */
package proteosuite;

/* -------------------------------
 * NET BEANS DEFAULT LIBRARIES
 * -------------------------------
 */
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

/* -------------------------------
 * PROTEOSUITE GUI LIBRARIES
 * -------------------------------
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import javax.swing.Timer;

/* -------------------------------
 * EXTERNAL APIs
 * -------------------------------
 */

//-----------------//
//... jmzML API ...//
//-----------------//
import uk.ac.ebi.jmzml.*;
import uk.ac.ebi.jmzml.gui.*;
//import uk.ac.ebi.jmzml.gui.model.*;
import uk.ac.ebi.jmzml.model.mzml.*;
//import uk.ac.ebi.jmzml.model.mzml.interfaces.*;
//import uk.ac.ebi.jmzml.model.mzml.params.*;
//import uk.ac.ebi.jmzml.model.mzml.utilities.*;
import uk.ac.ebi.jmzml.xml.*;
import uk.ac.ebi.jmzml.xml.io.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.adapters.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.marshaller.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.marshaller.listeners.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.unmarshaller.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.unmarshaller.cache.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.unmarshaller.cache.filters.*;
//import uk.ac.ebi.jmzml.model.xml.jaxb.unmarshaller.cache.listeners.*;
//import uk.ac.ebi.jmzml.model.xml.xxindex.*;

//------------------//
//... MZmine API ...//
//------------------//
// Examples of mzmine API (will not be used on the sw)
import net.sf.mzmine.modules.visualization.twod.*;
import net.sf.mzmine.data.RawDataFile;
import net.sf.mzmine.project.impl.RawDataFileImpl;
import net.sf.mzmine.modules.visualization.twod.TwoDVisualizer;
import net.sf.mzmine.main.MZmineCore;

/* -------------------------------
 * PROTEOSUITE CLASSES
 * -------------------------------
 * Examples of classes to draw the 2D plot chart 
 */
import uk.ac.liv.proteosuite.Sample;
import uk.ac.liv.proteosuite.SpectrumData;
import uk.ac.liv.proteosuite.PieChart;
import uk.ac.liv.proteosuite.TwoDPlot;
//--------------------------------------------------------------------------
/**
 * MAIN FORM
 */
//--------------------------------------------------------------------------
public class ProteoSuiteView extends FrameView {

    public ProteoSuiteView(SingleFrameApplication app) {
        super(app);

        //... Initializing components using NetBeans ...//
        initComponents();     
        
        //... Setting window defaults ...//
        getFrame().setTitle("ProteoSuite 0.1.0 - Software for Quantitative Proteomics (Beta Version)");        

        //... Setting icons ...//
        Image iconApp = new ImageIcon(this.getClass().getResource("/images/icon.gif")).getImage();
        getFrame().setIconImage(iconApp);
        
        //... Starting logo ...//
        Icon logoIcon = new ImageIcon(getClass().getResource("/images/logo.gif"));
        jLLogo.setIcon(logoIcon);

        //... Main Menu ...//
        Icon newIcon = new ImageIcon(getClass().getResource("/images/new.gif"));
        Icon importIcon = new ImageIcon(getClass().getResource("/images/import.gif"));
        Icon openIcon = new ImageIcon(getClass().getResource("/images/open.gif"));
        Icon saveIcon = new ImageIcon(getClass().getResource("/images/save.gif"));
        Icon printIcon = new ImageIcon(getClass().getResource("/images/print.gif"));
        Icon helpIcon = new ImageIcon(getClass().getResource("/images/help.gif"));
        jMNewProject.setIcon(newIcon);
        jMImportFile.setIcon(importIcon);
        jMOpenProject.setIcon(openIcon);
        jMSaveProject.setIcon(saveIcon);
        jMPrint.setIcon(printIcon);
        jMHelpContent.setIcon(helpIcon);

        //... Object Tree ...//
        Icon leafIcon = new ImageIcon(getClass().getResource("/images/scan.gif"));
        Icon openleafIcon = new ImageIcon(getClass().getResource("/images/file.gif"));
        Icon closeleafIcon = new ImageIcon(getClass().getResource("/images/file.gif"));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)jTMainTree.getCellRenderer();
        renderer.setLeafIcon(leafIcon);
        renderer.setClosedIcon(closeleafIcon);
        renderer.setOpenIcon(openleafIcon);
        
        //... Setting status bar (message timeout, idle icon and busy animation, etc) ...//
        ResourceMap resourceMap = getResourceMap();
        
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        //... Connecting action tasks to status bar via TaskMonitor ...//
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        //... Setting Window Height and Width ...//
        getFrame().setMinimumSize(new Dimension(1024, 780));
        getFrame().setPreferredSize(new Dimension(1024, 780));
        getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        jSPTopDivision.setDividerLocation(200);
        getFrame().pack();
    }
//--------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPToolbar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        jToolBar3 = new javax.swing.JToolBar();
        jPStatusBar = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        jLStatus = new javax.swing.JLabel();
        jSPMainDivision = new javax.swing.JSplitPane();
        jSPTopDivision = new javax.swing.JSplitPane();
        jTPDisplay = new javax.swing.JTabbedPane();
        jPHome = new javax.swing.JPanel();
        jLLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPMS = new javax.swing.JPanel();
        jPXIC = new javax.swing.JPanel();
        jP2D = new javax.swing.JPanel();
        jP3D = new javax.swing.JPanel();
        jSPTree = new javax.swing.JScrollPane();
        jTMainTree = new javax.swing.JTree();
        jTPResults = new javax.swing.JTabbedPane();
        jPOutput = new javax.swing.JPanel();
        jSPOutput = new javax.swing.JScrollPane();
        jTAOutput = new javax.swing.JTextArea();
        jSPSampleData = new javax.swing.JScrollPane();
        jTSampleData = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMNewProject = new javax.swing.JMenuItem();
        jMImportFile = new javax.swing.JMenuItem();
        jMSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMOpenProject = new javax.swing.JMenuItem();
        jMOpenProjectAs = new javax.swing.JMenu();
        jMSaveProject = new javax.swing.JMenuItem();
        jMSaveProjectAs = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMPrint = new javax.swing.JMenuItem();
        jMSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        analyzeMenu = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        statsMenu = new javax.swing.JMenu();
        visualMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        utilMenu = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        dbMenu = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        winMenu = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        jMHelpContent = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainPanel.setMaximumSize(new java.awt.Dimension(30000, 30000));
        mainPanel.setMinimumSize(new java.awt.Dimension(1024, 740));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1024, 740));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(proteosuite.ProteoSuiteApp.class).getContext().getResourceMap(ProteoSuiteView.class);
        jPToolbar.setBackground(resourceMap.getColor("jPToolbar.background")); // NOI18N
        jPToolbar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPToolbar.setToolTipText(resourceMap.getString("jPToolbar.toolTipText")); // NOI18N
        jPToolbar.setAlignmentX(0.1F);
        jPToolbar.setAlignmentY(0.1F);
        jPToolbar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPToolbar.setMinimumSize(new java.awt.Dimension(1024, 30));
        jPToolbar.setName("jPToolbar"); // NOI18N
        jPToolbar.setPreferredSize(new java.awt.Dimension(1024, 30));

        jToolBar1.setBackground(resourceMap.getColor("jToolBar1.background")); // NOI18N
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        jToolBar2.setBackground(resourceMap.getColor("jToolBar2.background")); // NOI18N
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jToolBar3.setBackground(resourceMap.getColor("jToolBar3.background")); // NOI18N
        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        javax.swing.GroupLayout jPToolbarLayout = new javax.swing.GroupLayout(jPToolbar);
        jPToolbar.setLayout(jPToolbarLayout);
        jPToolbarLayout.setHorizontalGroup(
            jPToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPToolbarLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPToolbarLayout.setVerticalGroup(
            jPToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPToolbarLayout.createSequentialGroup()
                .addGroup(jPToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPStatusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPStatusBar.setMinimumSize(new java.awt.Dimension(1025, 25));
        jPStatusBar.setName("jPStatusBar"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N
        progressBar.setPreferredSize(new java.awt.Dimension(146, 21));

        statusMessageLabel.setText(resourceMap.getString("statusMessageLabel.text")); // NOI18N
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setText(resourceMap.getString("statusAnimationLabel.text")); // NOI18N
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        jLStatus.setText(resourceMap.getString("jLStatus.text")); // NOI18N
        jLStatus.setName("jLStatus"); // NOI18N

        javax.swing.GroupLayout jPStatusBarLayout = new javax.swing.GroupLayout(jPStatusBar);
        jPStatusBar.setLayout(jPStatusBarLayout);
        jPStatusBarLayout.setHorizontalGroup(
            jPStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPStatusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 803, Short.MAX_VALUE)
                .addComponent(statusMessageLabel)
                .addGap(40, 40, 40)
                .addComponent(statusAnimationLabel)
                .addGap(32, 32, 32)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPStatusBarLayout.setVerticalGroup(
            jPStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(statusMessageLabel)
                .addComponent(statusAnimationLabel)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLStatus))
        );

        jSPMainDivision.setBorder(null);
        jSPMainDivision.setDividerLocation(580);
        jSPMainDivision.setDividerSize(1);
        jSPMainDivision.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSPMainDivision.setName("jSPMainDivision"); // NOI18N

        jSPTopDivision.setBorder(null);
        jSPTopDivision.setDividerLocation(200);
        jSPTopDivision.setDividerSize(1);
        jSPTopDivision.setName("jSPTopDivision"); // NOI18N

        jTPDisplay.setName("jTPDisplay"); // NOI18N

        jPHome.setBackground(resourceMap.getColor("jPHome.background")); // NOI18N
        jPHome.setName("jPHome"); // NOI18N

        jLLogo.setText(resourceMap.getString("jLLogo.text")); // NOI18N
        jLLogo.setName("jLLogo"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        javax.swing.GroupLayout jPHomeLayout = new javax.swing.GroupLayout(jPHome);
        jPHome.setLayout(jPHomeLayout);
        jPHomeLayout.setHorizontalGroup(
            jPHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHomeLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPHomeLayout.createSequentialGroup()
                        .addGroup(jPHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPHomeLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel4)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel5)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel6))
                    .addComponent(jLabel7))
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPHomeLayout.createSequentialGroup()
                .addContainerGap(538, Short.MAX_VALUE)
                .addComponent(jLLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPHomeLayout.setVerticalGroup(
            jPHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHomeLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(84, 84, 84)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jLLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTPDisplay.addTab(resourceMap.getString("jPHome.TabConstraints.tabTitle"), jPHome); // NOI18N

        jPMS.setBackground(resourceMap.getColor("jPMS.background")); // NOI18N
        jPMS.setName("jPMS"); // NOI18N

        javax.swing.GroupLayout jPMSLayout = new javax.swing.GroupLayout(jPMS);
        jPMS.setLayout(jPMSLayout);
        jPMSLayout.setHorizontalGroup(
            jPMSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        jPMSLayout.setVerticalGroup(
            jPMSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        jTPDisplay.addTab(resourceMap.getString("jPMS.TabConstraints.tabTitle"), jPMS); // NOI18N

        jPXIC.setBackground(resourceMap.getColor("jPXIC.background")); // NOI18N
        jPXIC.setName("jPXIC"); // NOI18N

        javax.swing.GroupLayout jPXICLayout = new javax.swing.GroupLayout(jPXIC);
        jPXIC.setLayout(jPXICLayout);
        jPXICLayout.setHorizontalGroup(
            jPXICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        jPXICLayout.setVerticalGroup(
            jPXICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        jTPDisplay.addTab(resourceMap.getString("jPXIC.TabConstraints.tabTitle"), jPXIC); // NOI18N

        jP2D.setBackground(resourceMap.getColor("jP2D.background")); // NOI18N
        jP2D.setName("jP2D"); // NOI18N

        javax.swing.GroupLayout jP2DLayout = new javax.swing.GroupLayout(jP2D);
        jP2D.setLayout(jP2DLayout);
        jP2DLayout.setHorizontalGroup(
            jP2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        jP2DLayout.setVerticalGroup(
            jP2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        jTPDisplay.addTab(resourceMap.getString("jP2D.TabConstraints.tabTitle"), jP2D); // NOI18N

        jP3D.setBackground(resourceMap.getColor("jP3D.background")); // NOI18N
        jP3D.setName("jP3D"); // NOI18N

        javax.swing.GroupLayout jP3DLayout = new javax.swing.GroupLayout(jP3D);
        jP3D.setLayout(jP3DLayout);
        jP3DLayout.setHorizontalGroup(
            jP3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        jP3DLayout.setVerticalGroup(
            jP3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        jTPDisplay.addTab(resourceMap.getString("jP3D.TabConstraints.tabTitle"), jP3D); // NOI18N

        jSPTopDivision.setRightComponent(jTPDisplay);

        jSPTree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSPTree.setName("jSPTree"); // NOI18N

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("New Project");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("...");
        treeNode1.add(treeNode2);
        jTMainTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTMainTree.setName("jTMainTree"); // NOI18N
        jSPTree.setViewportView(jTMainTree);

        jSPTopDivision.setLeftComponent(jSPTree);

        jSPMainDivision.setTopComponent(jSPTopDivision);

        jTPResults.setBackground(resourceMap.getColor("jTPResults.background")); // NOI18N
        jTPResults.setName("jTPResults"); // NOI18N

        jPOutput.setBackground(resourceMap.getColor("jPOutput.background")); // NOI18N
        jPOutput.setName("jPOutput"); // NOI18N

        jSPOutput.setBorder(null);
        jSPOutput.setName("jSPOutput"); // NOI18N

        jTAOutput.setBackground(resourceMap.getColor("jTAOutput.background")); // NOI18N
        jTAOutput.setColumns(20);
        jTAOutput.setFont(resourceMap.getFont("jTAOutput.font")); // NOI18N
        jTAOutput.setRows(5);
        jTAOutput.setBorder(null);
        jTAOutput.setName("jTAOutput"); // NOI18N
        jSPOutput.setViewportView(jTAOutput);

        javax.swing.GroupLayout jPOutputLayout = new javax.swing.GroupLayout(jPOutput);
        jPOutput.setLayout(jPOutputLayout);
        jPOutputLayout.setHorizontalGroup(
            jPOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSPOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
        );
        jPOutputLayout.setVerticalGroup(
            jPOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSPOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
        );

        jTPResults.addTab(resourceMap.getString("jPOutput.TabConstraints.tabTitle"), jPOutput); // NOI18N

        jSPSampleData.setName("jSPSampleData"); // NOI18N

        jTSampleData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Version", "ID", "Accession", "CVs", "Spectrums"
            }
        ));
        jTSampleData.setName("jTSampleData"); // NOI18N
        jSPSampleData.setViewportView(jTSampleData);

        jTPResults.addTab(resourceMap.getString("jSPSampleData.TabConstraints.tabTitle"), jSPSampleData); // NOI18N

        jSPMainDivision.setRightComponent(jTPResults);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSPMainDivision, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                    .addComponent(jPToolbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                    .addComponent(jPStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPMainDivision, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.setMinimumSize(new java.awt.Dimension(386, 21));
        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(proteosuite.ProteoSuiteApp.class).getContext().getActionMap(ProteoSuiteView.class, this);
        jMNewProject.setAction(actionMap.get("newProject")); // NOI18N
        jMNewProject.setText(resourceMap.getString("jMNewProject.text")); // NOI18N
        jMNewProject.setName("jMNewProject"); // NOI18N
        fileMenu.add(jMNewProject);

        jMImportFile.setAction(actionMap.get("importFile")); // NOI18N
        jMImportFile.setText(resourceMap.getString("jMImportFile.text")); // NOI18N
        jMImportFile.setName("jMImportFile"); // NOI18N
        fileMenu.add(jMImportFile);

        jMSeparator1.setName("jMSeparator1"); // NOI18N
        fileMenu.add(jMSeparator1);

        jMOpenProject.setAction(actionMap.get("openProject")); // NOI18N
        jMOpenProject.setText(resourceMap.getString("jMOpenProject.text")); // NOI18N
        jMOpenProject.setName("jMOpenProject"); // NOI18N
        fileMenu.add(jMOpenProject);

        jMOpenProjectAs.setText(resourceMap.getString("jMOpenProjectAs.text")); // NOI18N
        jMOpenProjectAs.setName("jMOpenProjectAs"); // NOI18N
        fileMenu.add(jMOpenProjectAs);

        jMSaveProject.setAction(actionMap.get("saveProject")); // NOI18N
        jMSaveProject.setText(resourceMap.getString("jMSaveProject.text")); // NOI18N
        jMSaveProject.setName("jMSaveProject"); // NOI18N
        fileMenu.add(jMSaveProject);

        jMSaveProjectAs.setAction(actionMap.get("saveProjectAs")); // NOI18N
        jMSaveProjectAs.setText(resourceMap.getString("jMSaveProjectAs.text")); // NOI18N
        jMSaveProjectAs.setName("jMSaveProjectAs"); // NOI18N
        fileMenu.add(jMSaveProjectAs);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        jMPrint.setAction(actionMap.get("printResults")); // NOI18N
        jMPrint.setText(resourceMap.getString("jMPrint.text")); // NOI18N
        jMPrint.setName("jMPrint"); // NOI18N
        fileMenu.add(jMPrint);

        jMSeparator2.setName("jMSeparator2"); // NOI18N
        fileMenu.add(jMSeparator2);

        exitMenuItem.setAction(actionMap.get("exitApplication")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N

        jMenuItem6.setAction(actionMap.get("undoProcess")); // NOI18N
        jMenuItem6.setText(resourceMap.getString("jMenuItem6.text")); // NOI18N
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        editMenu.add(jMenuItem6);

        jMenuItem7.setAction(actionMap.get("redoProcess")); // NOI18N
        jMenuItem7.setText(resourceMap.getString("jMenuItem7.text")); // NOI18N
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        editMenu.add(jMenuItem7);

        jSeparator4.setName("jSeparator4"); // NOI18N
        editMenu.add(jSeparator4);

        jMenuItem8.setText(resourceMap.getString("jMenuItem8.text")); // NOI18N
        jMenuItem8.setName("jMenuItem8"); // NOI18N
        editMenu.add(jMenuItem8);

        jMenuItem9.setText(resourceMap.getString("jMenuItem9.text")); // NOI18N
        jMenuItem9.setName("jMenuItem9"); // NOI18N
        editMenu.add(jMenuItem9);

        jMenuItem10.setAction(actionMap.get("copyObject")); // NOI18N
        jMenuItem10.setText(resourceMap.getString("jMenuItem10.text")); // NOI18N
        editMenu.add(jMenuItem10);

        jMenuItem11.setAction(actionMap.get("cutObject")); // NOI18N
        jMenuItem11.setText(resourceMap.getString("jMenuItem11.text")); // NOI18N
        jMenuItem11.setName("jMenuItem11"); // NOI18N
        editMenu.add(jMenuItem11);

        jMenuItem12.setAction(actionMap.get("pasteObject")); // NOI18N
        jMenuItem12.setText(resourceMap.getString("jMenuItem12.text")); // NOI18N
        jMenuItem12.setName("jMenuItem12"); // NOI18N
        editMenu.add(jMenuItem12);

        jSeparator5.setName("jSeparator5"); // NOI18N
        editMenu.add(jSeparator5);

        jMenuItem13.setAction(actionMap.get("selectAll")); // NOI18N
        editMenu.add(jMenuItem13);

        menuBar.add(editMenu);

        viewMenu.setText(resourceMap.getString("viewMenu.text")); // NOI18N
        viewMenu.setName("viewMenu"); // NOI18N

        jMenu9.setText(resourceMap.getString("jMenu9.text")); // NOI18N
        jMenu9.setName("jMenu9"); // NOI18N

        jMenuItem14.setText(resourceMap.getString("jMenuItem14.text")); // NOI18N
        jMenuItem14.setName("jMenuItem14"); // NOI18N
        jMenu9.add(jMenuItem14);

        jMenuItem16.setText(resourceMap.getString("jMenuItem16.text")); // NOI18N
        jMenuItem16.setName("jMenuItem16"); // NOI18N
        jMenu9.add(jMenuItem16);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenuItem15.setText(resourceMap.getString("jMenuItem15.text")); // NOI18N
        jMenuItem15.setName("jMenuItem15"); // NOI18N
        jMenu2.add(jMenuItem15);

        jMenu9.add(jMenu2);

        viewMenu.add(jMenu9);

        menuBar.add(viewMenu);

        analyzeMenu.setText(resourceMap.getString("analyzeMenu.text")); // NOI18N
        analyzeMenu.setName("analyzeMenu"); // NOI18N

        jMenuItem20.setText(resourceMap.getString("jMenuItem20.text")); // NOI18N
        jMenuItem20.setName("jMenuItem20"); // NOI18N
        analyzeMenu.add(jMenuItem20);

        jMenuItem21.setText(resourceMap.getString("jMenuItem21.text")); // NOI18N
        jMenuItem21.setName("jMenuItem21"); // NOI18N
        analyzeMenu.add(jMenuItem21);

        menuBar.add(analyzeMenu);

        statsMenu.setText(resourceMap.getString("statsMenu.text")); // NOI18N
        statsMenu.setName("statsMenu"); // NOI18N
        menuBar.add(statsMenu);

        visualMenu.setText(resourceMap.getString("visualMenu.text")); // NOI18N
        visualMenu.setName("visualMenu"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        visualMenu.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        visualMenu.add(jMenuItem2);

        jMenuItem3.setAction(actionMap.get("show2DPlot")); // NOI18N
        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        visualMenu.add(jMenuItem3);

        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        visualMenu.add(jMenuItem4);

        menuBar.add(visualMenu);

        utilMenu.setText(resourceMap.getString("utilMenu.text")); // NOI18N
        utilMenu.setName("utilMenu"); // NOI18N

        jMenuItem5.setAction(actionMap.get("callUtility1")); // NOI18N
        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        utilMenu.add(jMenuItem5);

        menuBar.add(utilMenu);

        dbMenu.setText(resourceMap.getString("dbMenu.text")); // NOI18N
        dbMenu.setName("dbMenu"); // NOI18N

        jMenuItem22.setText(resourceMap.getString("jMenuItem22.text")); // NOI18N
        jMenuItem22.setName("jMenuItem22"); // NOI18N
        dbMenu.add(jMenuItem22);

        jMenuItem23.setText(resourceMap.getString("jMenuItem23.text")); // NOI18N
        jMenuItem23.setName("jMenuItem23"); // NOI18N
        dbMenu.add(jMenuItem23);

        menuBar.add(dbMenu);

        toolsMenu.setText(resourceMap.getString("toolsMenu.text")); // NOI18N

        jMenuItem17.setText(resourceMap.getString("jMenuItem17.text")); // NOI18N
        jMenuItem17.setName("jMenuItem17"); // NOI18N
        toolsMenu.add(jMenuItem17);

        menuBar.add(toolsMenu);

        winMenu.setText(resourceMap.getString("winMenu.text")); // NOI18N
        winMenu.setName("winMenu"); // NOI18N

        jMenuItem18.setText(resourceMap.getString("jMenuItem18.text")); // NOI18N
        jMenuItem18.setName("jMenuItem18"); // NOI18N
        winMenu.add(jMenuItem18);

        jMenuItem19.setText(resourceMap.getString("jMenuItem19.text")); // NOI18N
        jMenuItem19.setName("jMenuItem19"); // NOI18N
        winMenu.add(jMenuItem19);

        menuBar.add(winMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N

        jMHelpContent.setText(resourceMap.getString("jMHelpContent.text")); // NOI18N
        jMHelpContent.setName("jMHelpContent"); // NOI18N
        helpMenu.add(jMHelpContent);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setToolBar(jToolBar1);
    }// </editor-fold>//GEN-END:initComponents
//--------------------------------------------------------------------------
    @Action
    public void newProject() {           

    }
//--------------------------------------------------------------------------
    @Action
    public void importFile() {

        String sOutput = ""; 
        long time;

        //... Select file(s) ...//
        JFileChooser chooser = new JFileChooser("user.home");
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mzML Files (*.mzML)", "mzML");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("mzIdentML Files (*.mzid)", "mzIdentML");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzQuantML)", "mzQuantML");
        chooser.setFileFilter(filter3);
        chooser.setFileFilter(filter2);
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new java.io.File("D:/Data"));

        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(null);

        //... Displaying selected data ...//
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0)
            {
                jLStatus.setText("Loading files, please wait...");
                Sample aSamples[] = new Sample[aFiles.length];

                //... Removing initial jTree default configuration ...//
                jTMainTree.removeAll();

                //... Root Node ...//
                DefaultMutableTreeNode treeRootNode = new DefaultMutableTreeNode("Project - ");

                time = System.currentTimeMillis();

                //... Reading selected files ...//
                for (int iI = 0; iI < aFiles.length; iI++)
                {
                    System.out.println("File selected: " + aFiles[iI].getPath());
                    System.out.println("Name selected: " + aFiles[iI].getName());

                    //... Unmarshall data using jzmzML API ...//
                    System.out.println("Before xmlFile " + time);

                    File xmlFile = new File(aFiles[iI].getPath());
                    System.out.println("After xmlFile " + (System.currentTimeMillis() - time) + "ms");

                    MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
                    time = System.currentTimeMillis();
                    System.out.println("After MzMLUnmarshaller " + (System.currentTimeMillis() - time) + "ms");

                    MzML completeMzML = unmarshaller.unmarshall();
                    System.out.println("After completeMzML " + (System.currentTimeMillis() - time) + "ms");

                    System.out.println("MzML Version: = " + unmarshaller.getMzMLVersion());
                    System.out.println("After MzML Version " + (System.currentTimeMillis() - time) + "ms");
                    System.out.println("MzML ID: = " + unmarshaller.getMzMLId());
                    System.out.println("After MzML ID " + (System.currentTimeMillis() - time) + "ms");

                    System.out.println("MzML Accession: = " + unmarshaller.getMzMLAccession());
                    System.out.println("After MzML Accession " + (System.currentTimeMillis() - time) + "ms");

                    CVList cvList = unmarshaller.unmarshalFromXpath("/cvList", CVList.class);
                    System.out.println("Number of defined CVs in this mzML: " + cvList.getCount());
                    System.out.println("After CVList " + (System.currentTimeMillis() - time) + "ms");

                    FileDescription fd = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);
                    System.out.println("After FileDescription " + (System.currentTimeMillis() - time) + "ms");
                    System.out.println("Number of source files: " + fd.getSourceFileList().getCount());

                    System.out.println("Supported XPath:" + Constants.XML_INDEXED_XPATHS);

                    System.out.println("Number of spectrum elements: " + unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));
                    System.out.println("After Number of spectrum elements " + (System.currentTimeMillis() - time) + "ms");

                    aSamples[iI] = new Sample(aFiles[iI].getName(), unmarshaller.getMzMLVersion(),
                            unmarshaller.getMzMLId(), unmarshaller.getMzMLAccession(), cvList.getCount(),
                            unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));
                    System.out.println("After Creating the array of samples " + (System.currentTimeMillis() - time) + "ms");

                    //... Populate the JTree with sample data ...//
                    DefaultMutableTreeNode aSampleNodes[] = new DefaultMutableTreeNode[aSamples.length];
                    aSampleNodes[iI] = new DefaultMutableTreeNode(aSamples[iI].getSam_name());
                    treeRootNode.add(aSampleNodes[iI]);
                    System.out.println("After Populating jtree " + (System.currentTimeMillis() - time) + "ms");

                    //... Populate the JTree with spectrum data ...//
                    SpectrumData aSpectrums[][] = new SpectrumData[aFiles.length][unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum")];
                    DefaultMutableTreeNode aSpectrumNodes[] = new DefaultMutableTreeNode[aSamples.length];
                    System.out.println("After Creating the array of spectrum data " + (System.currentTimeMillis() - time) + "ms");

                    //... Reading spectrum data ...//
                    MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
                    System.out.println("After reading spectrum data " + (System.currentTimeMillis() - time) + "ms");
                    int iJ = 0;
                    while (spectrumIterator.hasNext())
                    {
                        Spectrum spectrum = spectrumIterator.next();
                        System.out.println("After reading each spectrum: " + iJ + " " + (System.currentTimeMillis() - time) + " ms");

                        System.out.println("Spectrum ID: " + spectrum.getId());
                        aSpectrums[iI][iJ] = new SpectrumData(spectrum.getId());

                        System.out.println("Spectrum Name: " + aSpectrums[iI][iJ].getSpec_name());
                        aSpectrumNodes[0] = new DefaultMutableTreeNode(aSpectrums[iI][iJ].getSpec_name());
                        aSampleNodes[iI].add(aSpectrumNodes[0]);
                        iJ++;
                    }
                    //MzMLObjectIterator<BinaryDataArray> binaryIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum/binaryDataArrayList/binaryDataArray", BinaryDataArray.class);
                    MzMLObjectIterator<BinaryDataArray> binaryIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum/binaryDataArrayList/binaryDataArray/binary", BinaryDataArray.class);
                    int iK = 0;
                    while (binaryIterator.hasNext())
                    {
                        BinaryDataArray binaryDataArray = binaryIterator.next();
                        System.out.println("Hello");
                        iK++;
                    }

                    System.out.println("*********************************" + "\n\n");
		}

                //... Draw tree ...//
                jTMainTree.setModel(new DefaultTreeModel(treeRootNode));
                jTAOutput.setText(sOutput);
                System.out.println("After drawing the tree " + (System.currentTimeMillis() - time) + "ms");

                //... Fill JTable ...//
                DefaultTableModel model = new DefaultTableModel();
                jTSampleData.setModel(model);
                model.addColumn("Name");
                model.addColumn("Version");
                model.addColumn("ID");
                model.addColumn("Accession");
                model.addColumn("CVs");
                model.addColumn("Spectrums");
                for(int iI=0; iI<aSamples.length; iI++)
                {
                    model.insertRow(model.getRowCount(), new Object[]{aSamples[iI].getSam_name(),
                                                                      aSamples[iI].getSam_version(),
                                                                      aSamples[iI].getSam_id(),
                                                                      aSamples[iI].getSam_accession(),
                                                                      aSamples[iI].getSam_cvs(),
                                                                      aSamples[iI].getSam_spectrums()});
                }
                jLStatus.setText(aSamples.length + " file(s) loaded");
                jTPResults.setSelectedIndex(1);
	    }
        }
    }
    //--------------------------------------------------------------------------
    @Action
    public void openProject() {

    }
//--------------------------------------------------------------------------
    @Action
    public void saveProject() {

    }
//--------------------------------------------------------------------------
    @Action
    public void saveProjectAs() {

    }
//--------------------------------------------------------------------------
    @Action
    public void printResults() {

    }
//--------------------------------------------------------------------------
    @Action
    public void exitApplication() {
        int exit = JOptionPane.showConfirmDialog(null, "Do you want to exit?");

        if (exit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
//--------------------------------------------------------------------------
    @Action
    public void undoProcess() {

    }
//--------------------------------------------------------------------------
    @Action
    public void redoProcess() {
    }
//--------------------------------------------------------------------------
    @Action
    public void copyObject() {
    }
//--------------------------------------------------------------------------
    @Action
    public void cutObject() {
    }
//--------------------------------------------------------------------------
    @Action
    public void pasteObject() {
    }
//--------------------------------------------------------------------------
    @Action
    public void selectAll() {

    }
//--------------------------------------------------------------------------
    @Action
    public void show2DPlot() {
        TwoDPlot demo = new TwoDPlot("2D Plot");

        jP2D.add(demo);

        demo.pack();
        demo.setVisible(true);
        jTPDisplay.setSelectedIndex(3);

        //...
        
        //RawDataFileImpl rawDataFileWriter;
        //rawDataFileWriter = (RawDataFileImpl) MZmineCore.createNewFile(null);
        //TwoDVisualizer visualizer = new TwoDVisualizer();
        //visualizer.show2DVisualizerSetupDialog(rawDataFileWriter, null, null);
    }
//--------------------------------------------------------------------------
    @Action
    public void callUtility1() {

           JmzMLViewer jmzMLViewer = null;
           jmzMLViewer = new JmzMLViewer(null);

           int iWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
           int iHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

           jmzMLViewer.setBounds(50, 50, iWidth-250, iHeight-250);
           jmzMLViewer.setLocationRelativeTo(null);
           jmzMLViewer.setVisible(true);
           jmzMLViewer.setAlwaysOnTop(true);

            //new Thread(){
            //    public void run()
             //   {
                    //MZmineCore.main(new String[]{});
             //       System.out.println("Hello");
             //   }
            //}.start();
           
    }
//--------------------------------------------------------------------------
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ProteoSuiteApp.getApplication().getMainFrame();
            aboutBox = new ProteoSuiteAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ProteoSuiteApp.getApplication().show(aboutBox);
    }
//--------------------------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu analyzeMenu;
    private javax.swing.JMenu dbMenu;
    private javax.swing.JMenu editMenu;
    private javax.swing.JLabel jLLogo;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMHelpContent;
    private javax.swing.JMenuItem jMImportFile;
    private javax.swing.JMenuItem jMNewProject;
    private javax.swing.JMenuItem jMOpenProject;
    private javax.swing.JMenu jMOpenProjectAs;
    private javax.swing.JMenuItem jMPrint;
    private javax.swing.JMenuItem jMSaveProject;
    private javax.swing.JMenuItem jMSaveProjectAs;
    private javax.swing.JPopupMenu.Separator jMSeparator1;
    private javax.swing.JPopupMenu.Separator jMSeparator2;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jP2D;
    private javax.swing.JPanel jP3D;
    private javax.swing.JPanel jPHome;
    private javax.swing.JPanel jPMS;
    private javax.swing.JPanel jPOutput;
    private javax.swing.JPanel jPStatusBar;
    private javax.swing.JPanel jPToolbar;
    private javax.swing.JPanel jPXIC;
    private javax.swing.JSplitPane jSPMainDivision;
    private javax.swing.JScrollPane jSPOutput;
    private javax.swing.JScrollPane jSPSampleData;
    private javax.swing.JSplitPane jSPTopDivision;
    private javax.swing.JScrollPane jSPTree;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTextArea jTAOutput;
    private javax.swing.JTree jTMainTree;
    private javax.swing.JTabbedPane jTPDisplay;
    private javax.swing.JTabbedPane jTPResults;
    private javax.swing.JTable jTSampleData;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenu statsMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JMenu utilMenu;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JMenu visualMenu;
    private javax.swing.JMenu winMenu;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
