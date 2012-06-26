/*
 * --------------------------------------------------------------------------
 * ProjectParamsView.java
 * --------------------------------------------------------------------------
 * Description:       Capture of Configuration Parameters
 * Developer:         Faviel Gonzalez
 * Created:           07 March 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.proteosuite.utils.OpenURL;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This form corresponds to the main settings of the project. Users can customised their settings by changing the 
 * default values stored in the "config.xml" file located under the installation folder.
 * @author faviel
 * @param void
 */
public class ProjectParamsView extends javax.swing.JPanel {

    //... Class constructor ...//
    public ProjectParamsView() {
        initComponents();
        initValues();
    }    
    //... Initialise default settings using the config.xml file ...//
    private void initValues()
    {       
        //... Validate if config file exists ...//
        boolean exists = (new File("config.xml")).exists();
        if (exists)
        {
            readConfigFile();
        }
        else
        {
            String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
            sMessage = sMessage + "under your installation folder. ProteoSuite will create a new config.xml file \n";
            sMessage = sMessage + "for you, however you will have to set all parameters manually. You can still close \n";
            sMessage = sMessage + "this window, then copy the config.xml file and restart the program. \n";
            JOptionPane.showMessageDialog(this, sMessage, "Warning", JOptionPane.INFORMATION_MESSAGE);   
        }
    }
    //... Read configuration settings ...//
    private void readConfigFile()
    {  
            //... Fill JTable ...//
            final DefaultTableModel model = new DefaultTableModel();
            jtxTracker.setModel(model);
            model.addColumn("Technique");
            model.addColumn("Load Raw Files");
            model.addColumn("Load Ident Files");
            model.addColumn("Peak Selection");
            model.addColumn("Quantitation Method");
            model.addColumn("Output");
        
            //... Read files using SAX ...//
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
                            jtWorkspace.setText(sWorkspace);
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
                            model.insertRow(model.getRowCount(), new Object[]{spipelineTechnique, spipelineLoadRawFiles, spipelineLoadIdentFiles, 
                                spipelinePeakSelection, spipelineQuantitation, spipelineOutput});
                        }
                    }
                };
                saxParser.parse("config.xml", handler);

            } catch (Exception e) {
              e.printStackTrace();
            }
    }      
    //... Saving configuration settings ...//
    private void saveConfigFile()
    {     
        //... Save configuration File ...//
        try{
            FileWriter fstream = new FileWriter("config.xml");
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<ProteoSuiteApplication xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
            out.write("xsi:schemaLocation=\"ProteoSuiteApplication.xsd\" >");
            out.newLine();
            out.write(" <configSettings>");
            out.newLine();
            out.write("     <generalSettings>");            
            out.newLine();
            out.write("         <workspace>" + jtWorkspace.getText() + "</workspace>");
            out.newLine();
            out.write("     </generalSettings>");
            out.newLine();
            out.write("     <xTrackerSettings>");            
            out.newLine();
            //... Write xTracker plugins ...//
            for (int iI=0; iI<jtxTracker.getRowCount(); iI++)
            {
                out.write("         <pipeline>");
                out.newLine();
                out.write("             <pipelineTechnique>" + jtxTracker.getValueAt(iI, 0) + "</pipelineTechnique>");
                out.newLine();
                out.write("             <pipelineLoadRawFiles>" + jtxTracker.getValueAt(iI, 1) + "</pipelineLoadRawFiles>");
                out.newLine();
                out.write("             <pipelineLoadIdentFiles>" + jtxTracker.getValueAt(iI, 2) + "</pipelineLoadIdentFiles>");
                out.newLine();
                out.write("             <pipelinePeakSelection>" + jtxTracker.getValueAt(iI, 3) + "</pipelinePeakSelection>");
                out.newLine();
                out.write("             <pipelineQuantitation>" + jtxTracker.getValueAt(iI, 4) + "</pipelineQuantitation>");
                out.newLine();
                out.write("             <pipelineOutput>" + jtxTracker.getValueAt(iI, 5) + "</pipelineOutput>");
                out.newLine();
                out.write("         </pipeline>");
                out.newLine();
            }
            out.write("     </xTrackerSettings>");
            out.newLine();            
            out.write(" </configSettings>");
            out.newLine();            
            out.write("</ProteoSuiteApplication>");
            out.close();
            JOptionPane.showMessageDialog(this, "Changes updated successfully!", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpOptions = new javax.swing.JTabbedPane();
        jpGeneral = new javax.swing.JPanel();
        jlWorkspace = new javax.swing.JLabel();
        jtWorkspace = new javax.swing.JTextField();
        jbBrowse = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jpxTracker = new javax.swing.JPanel();
        jspxTracker = new javax.swing.JScrollPane();
        jtxTracker = new javax.swing.JTable();
        jbAdd = new javax.swing.JButton();
        jbRemove = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlURL = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jbSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(580, 370));
        setMinimumSize(new java.awt.Dimension(580, 370));

        jlWorkspace.setText("Workspace:");

        jbBrowse.setText("Browse");
        jbBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBrowseActionPerformed(evt);
            }
        });

        jLabel1.setText("Change general settings:");

        jLabel5.setText("Note: All settings have been configured by default on the config.xml file located under your ProteoSuite installation folder.");

        jLabel6.setText("You can make a copy of this file in case you need it in the future.");

        javax.swing.GroupLayout jpGeneralLayout = new javax.swing.GroupLayout(jpGeneral);
        jpGeneral.setLayout(jpGeneralLayout);
        jpGeneralLayout.setHorizontalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGeneralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpGeneralLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addGroup(jpGeneralLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jlWorkspace)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbBrowse))))
                    .addGroup(jpGeneralLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel6)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jpGeneralLayout.setVerticalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlWorkspace)
                    .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jtpOptions.addTab("General", jpGeneral);

        jtxTracker.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Technique", "Load Raw Files", "Load Ident Files", "Peak Selection", "Quantitation Method", "Output"
            }
        ));
        jspxTracker.setViewportView(jtxTracker);

        jbAdd.setText("Add");
        jbAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddActionPerformed(evt);
            }
        });

        jbRemove.setText("Remove");
        jbRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveActionPerformed(evt);
            }
        });

        jLabel3.setText("Modules for xTracker:");

        jLabel4.setText("Note: Do not modify these settings unless you are familiar with xTracker plugins. More information on: ");

        jlURL.setForeground(new java.awt.Color(0, 51, 153));
        jlURL.setText("www.x-tracker.info");
        jlURL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlURLMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpxTrackerLayout = new javax.swing.GroupLayout(jpxTracker);
        jpxTracker.setLayout(jpxTrackerLayout);
        jpxTrackerLayout.setHorizontalGroup(
            jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpxTrackerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpxTrackerLayout.createSequentialGroup()
                        .addComponent(jspxTracker, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbRemove)))
                    .addGroup(jpxTrackerLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlURL))
                    .addGroup(jpxTrackerLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2)))
                .addContainerGap())
        );
        jpxTrackerLayout.setVerticalGroup(
            jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpxTrackerLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpxTrackerLayout.createSequentialGroup()
                        .addComponent(jbAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemove))
                    .addComponent(jspxTracker, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpxTrackerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jlURL, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jtpOptions.addTab("xTracker Plugins", jpxTracker);

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jLabel2.setText("To discard any changes press ESC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpOptions)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jtpOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jbSave)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(jtWorkspace.getText()));
        chooser.setDialogTitle("Select another output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        //... Retrieving selection from user ...//
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File selected = chooser.getSelectedFile();
            jtWorkspace.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_jbBrowseActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        //... Save changes ...//
        saveConfigFile();        
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jbAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddActionPerformed
        //... Adding new row ...//
        DefaultTableModel model = (DefaultTableModel) jtxTracker.getModel();
        model.insertRow(model.getRowCount(), new Object[]{"", "", "", "", "", ""});
        jtxTracker.setModel(model);
    }//GEN-LAST:event_jbAddActionPerformed

    private void jlURLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlURLMouseClicked
	//... Call x-tracker website ...//
        OpenURL url = new OpenURL("http://www.x-tracker.info");
    }//GEN-LAST:event_jlURLMouseClicked

    private void jbRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveActionPerformed
        //... Remove pipeline ...//
        if (jtxTracker.getSelectedRow()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select the pipeline to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            DefaultTableModel model = (DefaultTableModel) jtxTracker.getModel();
            model.removeRow(jtxTracker.getSelectedRow());
            jtxTracker.setModel(model);
        }
                
    }//GEN-LAST:event_jbRemoveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbRemove;
    private javax.swing.JButton jbSave;
    private javax.swing.JLabel jlURL;
    private javax.swing.JLabel jlWorkspace;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JPanel jpxTracker;
    private javax.swing.JScrollPane jspxTracker;
    private javax.swing.JTextField jtWorkspace;
    private javax.swing.JTabbedPane jtpOptions;
    private javax.swing.JTable jtxTracker;
    // End of variables declaration//GEN-END:variables
}

