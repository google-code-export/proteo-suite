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
import java.awt.event.MouseEvent;

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
import javax.swing.JComponent;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.List;
import java.util.Iterator;

/* -------------------------------
 * EXTERNAL APIs
 * -------------------------------
 */

//-----------------//
//... jmzML API ...//
//-----------------//
import uk.ac.ebi.jmzml.*;
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
import uk.ac.liv.proteosuite.Node;
import uk.ac.liv.proteosuite.ProgMonitor;
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
        //Dimension screenSize = getFrame().getToolkit().getScreenSize();
        //System.out.println(screenSize);

        getFrame().setMinimumSize(new Dimension(1024, 780));
        //getFrame().setSize(screenSize);
        //getFrame().setPreferredSize(new Dimension(1024, 780));
        getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

        jSPTopDivision.setDividerLocation(200);        
        jTPDisplay.setSelectedIndex(0);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPMS = new javax.swing.JPanel();
        jPXIC = new javax.swing.JPanel();
        jP2D = new javax.swing.JPanel();
        jPD2D = new javax.swing.JDesktopPane();
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
        jPHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPHomeMouseExited(evt);
            }
        });

        jLLogo.setText(resourceMap.getString("jLLogo.text")); // NOI18N
        jLLogo.setName("jLLogo"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setForeground(resourceMap.getColor("jLabel1.foreground")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel5.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel6.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
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
                    .addComponent(jLabel1)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
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

        jPD2D.setBackground(resourceMap.getColor("jPD2D.background")); // NOI18N
        jPD2D.setName("jPD2D"); // NOI18N

        javax.swing.GroupLayout jP2DLayout = new javax.swing.GroupLayout(jP2D);
        jP2D.setLayout(jP2DLayout);
        jP2DLayout.setHorizontalGroup(
            jP2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPD2D, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
        );
        jP2DLayout.setVerticalGroup(
            jP2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPD2D, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
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
        jTMainTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTMainTreeMouseClicked(evt);
            }
        });
        jTMainTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTMainTreeValueChanged(evt);
            }
        });
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

    private void jPHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPHomeMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPHomeMouseEntered

    private void jPHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPHomeMouseExited
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPHomeMouseExited

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel3.setFont(font);
        jLabel3.setForeground(Color.red);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        jLabel3.setFont(font);
        jLabel3.setForeground(Color.black);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel5.setFont(font);
        jLabel5.setForeground(Color.red);
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        jLabel5.setFont(font);
        jLabel5.setForeground(Color.black);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel4.setFont(font);
        jLabel4.setForeground(Color.red);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        jLabel4.setFont(font);
        jLabel4.setForeground(Color.black);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel6.setFont(font);
        jLabel6.setForeground(Color.red);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        jPHome.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        jLabel6.setFont(font);
        jLabel6.setForeground(Color.black);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        jTPDisplay.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        jTPDisplay.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        jTPDisplay.setSelectedIndex(3);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        jTPDisplay.setSelectedIndex(4);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jTMainTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTMainTreeValueChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jTMainTreeValueChanged

    private void jTMainTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTMainTreeMouseClicked

        if (evt.getButton()== 3)
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jTMainTree.getLastSelectedPathComponent();
            int x = evt.getX();
            int y = evt.getY();

            if (node == null) return;

            Object nodeInfo = node.getUserObject();
            if (node.isLeaf()) {                
                
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("Show Spectrum");
                javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(proteosuite.ProteoSuiteApp.class).getContext().getActionMap(ProteoSuiteView.class, this);
                menuItem.setAction(actionMap.get("showSpectrum"));
                popup.add(menuItem);
                popup.show(jTMainTree, x, y);
            }
            else
            {
                
                JPopupMenu popup = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("Show 2D View");
                JMenuItem menuItem2 = new JMenuItem("Show Chromatogram");
                javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(proteosuite.ProteoSuiteApp.class).getContext().getActionMap(ProteoSuiteView.class, this);
                menuItem.setAction(actionMap.get("show2DPlot"));
                menuItem2.setAction(actionMap.get("showChromatogram"));
                popup.add(menuItem);
                popup.add(menuItem2);
                popup.show(jTMainTree, x, y);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTMainTreeMouseClicked
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

            progressBar.setVisible(true);
            progressBar.setValue(0);
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

                    //JComponent newContentPane = new ProgMonitor("Loading file " + aFiles[iI].getName());

                    System.out.println("File selected: " + aFiles[iI].getPath());
                    System.out.println("Name selected: " + aFiles[iI].getName());                    

                    File xmlFile = new File(aFiles[iI].getPath());

                    //... Unmarshall data using jzmzML API ...//
                    MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
                    time = System.currentTimeMillis();                    

                    //... FULL UNMARSHALL (Not used yet) MzML completeMzML = unmarshaller.unmarshall();

                    System.out.println("MzML Version: = " + unmarshaller.getMzMLVersion());
                    System.out.println("MzML ID: = " + unmarshaller.getMzMLId());
                    System.out.println("MzML Accession: = " + unmarshaller.getMzMLAccession());

                    CVList cvList = unmarshaller.unmarshalFromXpath("/cvList", CVList.class);
                    System.out.println("Number of defined CVs in this mzML file: " + cvList.getCount());

                    //... Reading CV params ...//
                    
                    //MzMLObjectIterator<CV> cvIterator = unmarshaller.unmarshalCollectionFromXpath("/cvList/cv", CV.class);
                    //while (cvIterator.hasNext())
                   // {
                   //     CV cv = cvIterator.next();

                   //     System.out.println("CV Id: " + cv.getId());
                   //     System.out.println("CV Name: " + cv.getFullName());
                   //     System.out.println("CV Version: " + cv.getVersion());
                   //     System.out.println("CV URI: " + cv.getURI());
                   // }

                    FileDescription fd = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);
                    System.out.println("Number of source files: " + fd.getSourceFileList().getCount());

                    System.out.println("Supported XPath:" + Constants.XML_INDEXED_XPATHS);                    

                    System.out.println("Number of spectrum elements: " + unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));

                    aSamples[iI] = new Sample(aFiles[iI].getName(), unmarshaller.getMzMLVersion(),
                            unmarshaller.getMzMLId(), unmarshaller.getMzMLAccession(), cvList.getCount(),
                            unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum"));

                    //... Populate the JTree with sample data ...//
                    DefaultMutableTreeNode aSampleNodes[] = new DefaultMutableTreeNode[aSamples.length];
                    aSampleNodes[iI] = new DefaultMutableTreeNode(aSamples[iI].getSam_name());
                    treeRootNode.add(aSampleNodes[iI]);

                    //... Populate the JTree with spectrum data ...//
                    SpectrumData aSpectrums[][] = new SpectrumData[aFiles.length][unmarshaller.getObjectCountForXpath("/run/spectrumList/spectrum")];
                    DefaultMutableTreeNode aSpectrumNodes[] = new DefaultMutableTreeNode[aSamples.length];

                    //... Reading spectrum data ...//
                    MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
                    int iJ = 0;
                    while (spectrumIterator.hasNext())
                    {
                        Spectrum spectrum = spectrumIterator.next();

                        //System.out.println("Spectrum ID: " + spectrum.getId());
                        aSpectrumNodes[0] = new DefaultMutableTreeNode(spectrum.getId());
                        aSampleNodes[iI].add(aSpectrumNodes[0]);
                        iJ++;

                        //... Reading Retention Times ...//
//                        MzMLObjectIterator<CVParam> cvpScanIterator = unmarshaller.unmarshalCollectionFromXpath("/indexedmzML/mzML/run/spectrumList/spectrum/scanList/scan/cvParam", CVParam.class);
//                        while (cvpScanIterator.hasNext())
//                        {
//                            CVParam cvpScan = cvpScanIterator.next();
//
//                            System.out.println("CVParam Accession: " + cvpScan.getAccession());
//                            System.out.println("CVParam Name: " + cvpScan.getName());
//                            System.out.println("CVParam Value: " + cvpScan.getValue());
//                            System.out.println("CVParam Unit Acc: " + cvpScan.getUnitAccession());
//                            System.out.println("CVParam Unit Name: " + cvpScan.getUnitName());
//
//                        }
                    }
                    System.out.println("*********************************" + "\n\n");
                    progressBar.setValue(iI*100/aFiles.length);
		}
                progressBar.setValue(100);

                //... Draw tree ...//
                jTMainTree.setModel(new DefaultTreeModel(treeRootNode));
                jTMainTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
                
                
                jTAOutput.setText(sOutput);

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
                
                //progressBar.setValue(0);
                //progressBar.setVisible(false);
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
    public void showMS() {
        jTPDisplay.setSelectedIndex(1);

        //...

        //RawDataFileImpl rawDataFileWriter;
        //rawDataFileWriter = (RawDataFileImpl) MZmineCore.createNewFile(null);
        //TwoDVisualizer visualizer = new TwoDVisualizer();
        //visualizer.show2DVisualizerSetupDialog(rawDataFileWriter, null, null);
    }
//--------------------------------------------------------------------------
    @Action
    public void show2DPlot() {

           DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jTMainTree.getLastSelectedPathComponent();
            if (node == null) return;

            Object nodeInfo = node.getUserObject();
//            double[] mz = null;
//            double[] intensities = null;
//            double[] art = null;

            double[] mz = new double[1000000];
            double[] intensities = new double[1000000];
            double[] art = new double[1000000];

            double rt = 0;
            int iCounter = 0;

            File xmlFile = new File("D:\\Data\\" + node.toString());
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);

            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);            
            while (spectrumIterator.hasNext())
            {
                Spectrum spectrumobj = spectrumIterator.next();
                String spectrumid = spectrumobj.getId();
                try
                {
                   Spectrum spectrum = unmarshaller.getSpectrumById(spectrumid);

                   //System.out.println("Spectrum : " + spectrum.getId());

    //               List<CVParam> specParam = spectrum.getCvParam();
    //               for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();)
    //               {
    //                   CVParam lCVParam = (CVParam) lCVParamIterator.next();
    //                   if (lCVParam.getAccession().equals("MS:1000504"))
    //                   {
    //                       //peakmz = Double.parseDouble(lCVParam.getValue().trim());
    //                       System.out.print("Peak m/z: " + lCVParam.getValue().trim() + "\t");
    //                   }
    //                   if (lCVParam.getAccession().equals("MS:1000505"))
    //                   {
    //                       //peakint = Double.parseDouble(lCVParam.getValue().trim());
    //                       System.out.print("Peak int: " + lCVParam.getValue().trim() + "\t");
    //                   }
    //               }

                   List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                   for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();)
                   {
                       CVParam lCVParam = (CVParam) lCVParamIterator.next();
                       if (lCVParam.getAccession().equals("MS:1000016"))
                       {

                           rt = Double.parseDouble(lCVParam.getValue().trim());
                           //System.out.print("Rt: " + rt + "\n");
                       }
                   }

                   //... Binary data ...//
                   List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

                   //... Reading mz Values ...//
                   BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
                   BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
                   Number[] mzNumbers = mzBinaryDataArray.getBinaryDataAsNumberArray();
                   Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
                   //mz = new double[mzNumbers.length];
                   //intensities = new double[intenNumbers.length];
                   //art = new double[intenNumbers.length];
                   for (int iI = 0; iI < mzNumbers.length; iI++)
                   {
                       mz[iCounter] = mzNumbers[iI].doubleValue();                                              
                       intensities[iCounter] = intenNumbers[iI].doubleValue();
                       art[iCounter] = rt;
                       //System.out.println("mz[" + iI + "] = " + mz[iI]);
                       //System.out.println("Int[" + iI + "] = " + intensities[iI]);
                       iCounter++;
                   }
                }
                catch (MzMLUnmarshallerException ume)
                {
                    System.out.println(ume.getMessage());
                }
            }
            TwoDPlot demo = new TwoDPlot(nodeInfo.toString(), mz, intensities, art);
            jPD2D.add(demo);
            demo.pack();
            demo.setVisible(true);
            System.out.println("Displaying " + nodeInfo.toString() + " as 2D");

            jTPDisplay.setSelectedIndex(3);
    }
    //--------------------------------------------------------------------------
    @Action
    public void showSpectrum() {


            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jTMainTree.getLastSelectedPathComponent();

            DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)
                               jTMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jTPDisplay.setSelectedIndex(1);

            Object nodeInfo = node.getUserObject();

            File xmlFile = new File("D:\\Data\\" + node2.getParent().toString());
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
            try
            {
               Spectrum spectrum = unmarshaller.getSpectrumById(nodeInfo.toString());

               List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

               //... Reading mz Values ...//
               BinaryDataArray mzBinaryDataArray = (BinaryDataArray) bdal.get(0);
               Number[] mzNumbers = mzBinaryDataArray.getBinaryDataAsNumberArray();
               double[] mz = new double[mzNumbers.length];
               for (int iI = 0; iI < mzNumbers.length; iI++)
               {
                   mz[iI] = mzNumbers[iI].doubleValue();
                   System.out.println("mz[" + iI + "] = " + mz[iI]);
               }

               //... Reading Intensities ...//
               BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
               Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
               double[] intensities = new double[intenNumbers.length];
               for (int iI = 0; iI < intenNumbers.length; iI++)
               {
                   intensities[iI] = intenNumbers[iI].doubleValue();
                   System.out.println("Int[" + iI + "] = " + intensities[iI]);
               }


               System.out.println("Spectrum" + spectrum.getId());
            }
            catch (MzMLUnmarshallerException ume)
            {
                System.out.println(ume.getMessage());
            }
            System.out.println("Displaying " + nodeInfo.toString() + " as Spectrum");
    }
    //--------------------------------------------------------------------------
    @Action
    public void showChromatogram() {


            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                               jTMainTree.getLastSelectedPathComponent();

            if (node == null) return;

            jTPDisplay.setSelectedIndex(2);

            Object nodeInfo = node.getUserObject();

            File xmlFile = new File("D:\\Data\\" + node.toString());
            System.out.println(xmlFile.toString());
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
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
                   System.out.println("mz[" + iI + "] = " + rt[iI]);
               }

               //... Reading Intensities ...//
               BinaryDataArray intenBinaryDataArray = (BinaryDataArray) bdal.get(1);
               Number[] intenNumbers = intenBinaryDataArray.getBinaryDataAsNumberArray();
               double[] intensities = new double[intenNumbers.length];
               for (int iI = 0; iI < intenNumbers.length; iI++)
               {
                   intensities[iI] = intenNumbers[iI].doubleValue();
                   System.out.println("Int[" + iI + "] = " + intensities[iI]);
               }

               System.out.println("Chromatogram" + chromatogram.getId());
            }
            catch (MzMLUnmarshallerException ume)
            {
                System.out.println(ume.getMessage());
            }
            System.out.println("Displaying " + nodeInfo.toString() + " as Chromatogram");
    }
//--------------------------------------------------------------------------
    @Action
    public void callUtility1() {

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
    private javax.swing.JDesktopPane jPD2D;
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