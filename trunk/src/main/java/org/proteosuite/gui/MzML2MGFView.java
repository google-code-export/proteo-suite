/*
 * --------------------------------------------------------------------------
 * MzML2MGFView.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to Convert mzML files to MGF
 * Developer:         Faviel Gonzalez
 * Created:           09 February 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.utils.MzML2MGF;
import org.proteosuite.utils.ProgressBarDialog;

/**
 * Form to capture all MzML files to be converted into MGF.
 * @author faviel
 * @param jframe Parent Frame
 * @param sPath File to convert
 */
public class MzML2MGFView extends javax.swing.JPanel {

    private String sWorkspaceInput;
    private JFrame jfFrame;
            
    /** Creates a new MzML2MGF form */
    public MzML2MGFView(JFrame jframe, String sPath) {
        initComponents();
        this.jfFrame = jframe;
        this.jtWorkspace.setText(sPath);
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtWorkspace = new javax.swing.JTextField();
        jbBrowse = new javax.swing.JButton();
        jbAdd = new javax.swing.JButton();
        jbRemove = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtMzMLFiles = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jlInstructions1 = new javax.swing.JLabel();
        jlInstructions2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(490, 350));
        setMinimumSize(new java.awt.Dimension(490, 350));
        setPreferredSize(new java.awt.Dimension(490, 350));

        jbConvert.setText("Convert");
        jbConvert.setEnabled(false);
        jbConvert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jLabel1.setText("Output folder:");

        jbBrowse.setText("Browse");
        jbBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseMouseClicked(evt);
            }
        });

        jbAdd.setText("Add");
        jbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAddMouseClicked(evt);
            }
        });

        jbRemove.setText("Remove");
        jbRemove.setEnabled(false);
        jbRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbRemoveMouseClicked(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jtMzMLFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Size (MB)", "Status"
            }
        ));
        jScrollPane1.setViewportView(jtMzMLFiles);

        jTabbedPane1.addTab("MzML Files", jScrollPane1);

        jlInstructions1.setText("Select your mzML files to convert them into Mascot Generic Format (MGF). Output files include the");

        jlInstructions2.setText("specification of retention times in the scan title.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbConvert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbRemove)))
                    .addComponent(jlInstructions1)
                    .addComponent(jlInstructions2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlInstructions1)
                .addGap(3, 3, 3)
                .addComponent(jlInstructions2)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jbAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbConvert))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(286, 286, 286))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbBrowseMouseClicked
        //... Selecting output folder ...//
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(this.jtWorkspace.getText()));
        chooser.setDialogTitle("Select output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        //... Retrieving selection from user ...//
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File selected = chooser.getSelectedFile();
            this.jtWorkspace.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_jbBrowseMouseClicked

    private void jbAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAddMouseClicked
        //... Adding files ...//
        JFileChooser chooser = new JFileChooser(this.jtWorkspace.getText());
        chooser.setDialogTitle("Select the file(s) to convert");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mzML Files (*.mzML)", "mzML");
        chooser.setFileFilter(filter);
        
        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(true);        
        
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            //... A common experiment may have around 4-20 mzML files ....//
            File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0)
            {
                //... Fill JTable ...//
                DefaultTableModel model = new DefaultTableModel();
                jtMzMLFiles.setModel(model);
                String sName = "";
                sWorkspaceInput = aFiles[0].getAbsolutePath();
                sName = aFiles[0].getName();
                sWorkspaceInput = sWorkspaceInput.replace(sName, "");
                model.addColumn("Name");
                model.addColumn("Size (MB)");
                model.addColumn("Status");     
                float fValue = 0f;
                    
                if (aFiles[0].getName().indexOf(".mzML") > 0) 
                {
                    jbRemove.setEnabled(true);
                    jbConvert.setEnabled(true);
                
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        fValue = (float) aFiles[iI].length()/(1024*1024);
                        model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), String.format("%.2f", fValue), ""}); 
                    }
                }
            }
        }        
    }//GEN-LAST:event_jbAddMouseClicked

    private void jbConvertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked
        //... Validate selection ...//        
        if (jtMzMLFiles.getRowCount()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select at least one mzML file", "Error", JOptionPane.ERROR_MESSAGE);
        }   
        else    //... Convert selected mzML files into MGF format ...//
        {
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true);
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    progressBarDialog.setTitle("Converting mzML file(s) into MGF");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();

            //... Splitting job in threads ...//
            new Thread("LoadingThread"){
                @Override
                public void run(){
                    //... Convert each file ...//
                    String sPath = "";
                    for (int iI = 0; iI <jtMzMLFiles.getRowCount(); iI++)
                    {
                        sPath = sWorkspaceInput+jtMzMLFiles.getValueAt(iI, 0).toString();                    
                        File xmlFile = new File(sPath);
                        progressBarDialog.setTitle("Converting " + xmlFile.getName() + " into MGF");
                        progressBarDialog.repaint();
                        MzML2MGF convert = new MzML2MGF(xmlFile, jtWorkspace.getText());
                        jtMzMLFiles.setValueAt("Done", iI, 2);
                    }
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();        
                }
            }.start();                                   
        }
    }//GEN-LAST:event_jbConvertMouseClicked

    private void jbRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRemoveMouseClicked
        if (jtMzMLFiles.getSelectedRow()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select the file to remove", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            DefaultTableModel model = (DefaultTableModel) jtMzMLFiles.getModel();
            model.removeRow(jtMzMLFiles.getSelectedRow());
            jtMzMLFiles.setModel(model);
        }
    }//GEN-LAST:event_jbRemoveMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbConvert;
    private javax.swing.JButton jbRemove;
    private javax.swing.JLabel jlInstructions1;
    private javax.swing.JLabel jlInstructions2;
    private javax.swing.JTable jtMzMLFiles;
    private javax.swing.JTextField jtWorkspace;
    // End of variables declaration//GEN-END:variables
}
