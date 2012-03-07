/*
 * --------------------------------------------------------------------------
 * Configparams.java
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author faviel
 */
public class Configparams extends javax.swing.JPanel {

    public Configparams() {
        initComponents();
        initValues();
    }
   
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
    private void readConfigFile()
    {  
        try{            
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("config.xml"));

            doc.getDocumentElement().normalize();
            NodeList projectList = doc.getElementsByTagName("ProteoSuiteApp");                
            Node projectNode = projectList.item(0);
            Element projectElement = (Element)projectNode;
            jTextField1.setText(projectElement.getAttribute("workspace").toString());            

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
    private void saveConfigFile()
    {     
        try{
            FileWriter fstream = new FileWriter("config.xml");
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<ProteoSuiteApp xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
            out.write("xsi:schemaLocation=\"ProteoSuiteApp.xsd\" workspace=\"" + this.jTextField1.getText() + "\">");
            out.newLine();
            out.write(" <configSettings>");
            out.newLine();
            out.write("     <generalSettings>");            
            out.newLine();
            out.write("     </generalSettings>");
            out.newLine();
            out.write(" </configSettings>");
            out.newLine();            
            out.write("</ProteoSuiteApp>");
            out.close();
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jbBrowse = new javax.swing.JButton();
        jbSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(580, 370));
        setMinimumSize(new java.awt.Dimension(580, 370));

        jLabel1.setText("Workspace:");

        jbBrowse.setText("...");
        jbBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpGeneralLayout = new javax.swing.GroupLayout(jpGeneral);
        jpGeneral.setLayout(jpGeneralLayout);
        jpGeneralLayout.setHorizontalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbBrowse)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jpGeneralLayout.setVerticalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jtpOptions.addTab("General", jpGeneral);

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jLabel2.setText("If you do not want to apply changes press ESC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jtpOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSave)
                    .addComponent(jLabel2)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(this.jTextField1.getText()));
        chooser.setDialogTitle("Select another output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        //... Retrieving selection from user ...//
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File selected = chooser.getSelectedFile();
            this.jTextField1.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_jbBrowseActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        saveConfigFile();
    }//GEN-LAST:event_jbSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbSave;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JTabbedPane jtpOptions;
    // End of variables declaration//GEN-END:variables
}

