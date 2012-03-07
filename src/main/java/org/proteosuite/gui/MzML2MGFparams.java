/*
 * --------------------------------------------------------------------------
 * MzML2MGFparams.java
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

import java.awt.Cursor;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.utils.MzML2MGF;

/**
 *
 * @author faviel
 */
public class MzML2MGFparams extends javax.swing.JPanel {

    private String sWorkspaceInput;
            
    /** Creates new form MzML2MGF */
    public MzML2MGFparams(String sPath) {
        initComponents();
        this.jTextField1.setText(sPath);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtMzMLFiles = new javax.swing.JTable();
        jbConvert = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jbBrowse = new javax.swing.JButton();
        jbImport = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(413, 300));
        setMinimumSize(new java.awt.Dimension(413, 300));

        jtMzMLFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Size", "Status"
            }
        ));
        jScrollPane1.setViewportView(jtMzMLFiles);

        jbConvert.setText("Convert");
        jbConvert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jLabel1.setText("Output folder:");

        jbBrowse.setText("...");
        jbBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseMouseClicked(evt);
            }
        });

        jbImport.setText("Add");
        jbImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbImportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbConvert))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbImport, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(jbBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbImport)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbConvert)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbBrowseMouseClicked
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
    }//GEN-LAST:event_jbBrowseMouseClicked

    private void jbImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbImportMouseClicked
        JFileChooser chooser = new JFileChooser(this.jTextField1.getText());
        chooser.setDialogTitle("Select the file(s) to analyze");

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
                model.addColumn("Size");
                model.addColumn("Status");                
                    
                if (aFiles[0].getName().indexOf(".mzML") > 0) 
                {
                    for (int iI = 0; iI < aFiles.length; iI++)
                    {
                        model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), aFiles[iI].getTotalSpace(), ""}); 
                    }
                }
            }
        }        
    }//GEN-LAST:event_jbImportMouseClicked

    private void jbConvertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked
        String sPath = "";
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int iI = 0; iI <jtMzMLFiles.getRowCount(); iI++)
        {
            sPath = sWorkspaceInput+jtMzMLFiles.getValueAt(iI, 0).toString();
            File xmlFile = new File(sPath);
            MzML2MGF convert = new MzML2MGF(xmlFile, this.jTextField1.getText());
            jtMzMLFiles.setValueAt("Done", iI, 2);
        }
        setCursor(null);
    }//GEN-LAST:event_jbConvertMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbConvert;
    private javax.swing.JButton jbImport;
    private javax.swing.JTable jtMzMLFiles;
    // End of variables declaration//GEN-END:variables
}
