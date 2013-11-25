/*
 * --------------------------------------------------------------------------
 * MzMLCompressView.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to compress mzML files
 * Developer:         fgonzalez
 * Created:           12 April 2013
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.utils.MzMLCompress;
import org.proteosuite.utils.ProgressBarDialog;

/**
 * Form to capture all MzML files to be compressed.
 * @author fgonzalez
 * @param jframe Parent Frame
 * @param sPath File to convert
 */
public class MzMLCompressView extends JPanel {

    private String sWorkspaceInput;
    private JFrame jfFrame;
            
    /** Creates a new MzML2MGF form */
    public MzMLCompressView(JFrame jframe, String sPath) {
        initComponents();
        this.jfFrame = jframe;
        this.jtWorkspace.setText(sPath);
    }    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new JButton();
        jlOutputFolder = new JLabel();
        jtWorkspace = new JTextField();
        jbBrowse = new JButton();
        jbAdd = new JButton();
        jbRemove = new JButton();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        jtMzMLFiles = new JTable();
        jSeparator1 = new JSeparator();
        jlInstructions = new JLabel();
        jlInstructions2 = new JLabel();
        jcbZeros = new JCheckBox();

        setMaximumSize(new java.awt.Dimension(490, 390));
        setMinimumSize(new java.awt.Dimension(490, 390));
        setPreferredSize(new java.awt.Dimension(490, 390));

        jbConvert.setText("Convert");
        jbConvert.setEnabled(false);
        jbConvert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jlOutputFolder.setText("Output folder:");

        jbBrowse.setText("Browse");
        jbBrowse.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbBrowseMouseClicked(evt);
            }
        });

        jbAdd.setText("Add");
        jbAdd.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbAddMouseClicked(evt);
            }
        });

        jbRemove.setText("Remove");
        jbRemove.setEnabled(false);
        jbRemove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbRemoveMouseClicked(evt);
            }
        });

        jTabbedPane1.setBackground(new Color(255, 255, 255));

        jScrollPane1.setBackground(new Color(255, 255, 255));

        jtMzMLFiles.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Size (MB)", "Status"
            }
        ));
        jScrollPane1.setViewportView(jtMzMLFiles);

        jTabbedPane1.addTab("MzML Files", jScrollPane1);

        jlInstructions.setText("Select your mzML files to be compressed. Compression is made by removing zeros on intensity");

        jlInstructions2.setText("values and storing deltas on m/z");

        jcbZeros.setSelected(true);
        jcbZeros.setText("Remove zeros");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbZeros)
                        .addContainerGap())
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jlInstructions)
                                .addComponent(jlInstructions2)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator1, GroupLayout.Alignment.LEADING)
                                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jlOutputFolder)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtWorkspace, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbBrowse, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))))
                            .addContainerGap(11, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jbAdd, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jbRemove)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                    .addComponent(jbConvert)
                                    .addGap(123, 123, 123)))
                            .addGap(107, 107, 107)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlInstructions)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInstructions2)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jtWorkspace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlOutputFolder))
                    .addComponent(jbBrowse))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbZeros)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdd)
                    .addComponent(jbRemove)
                    .addComponent(jbConvert))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbBrowseMouseClicked
        //... Selecting output folder ...//
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(jtWorkspace.getText()));
        chooser.setDialogTitle("Select output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        //... Retrieving selection from user ...//
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File selected = chooser.getSelectedFile();
            jtWorkspace.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_jbBrowseMouseClicked

    private void jbAddMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbAddMouseClicked
        //... Adding files ...//
        JFileChooser chooser = new JFileChooser(jtWorkspace.getText());
        chooser.setDialogTitle("Select the file(s) to compress");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mzML Files (*.mzML)", "mzML");
        chooser.setFileFilter(filter);
        
        //... Enable multiple file selection ...//
        chooser.setMultiSelectionEnabled(true);        
        
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            //... A common experiment may have around 4-20 mzML files ....//
            File [] aFiles = chooser.getSelectedFiles();

	    if (aFiles != null && aFiles.length > 0){
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
                    
                if (aFiles[0].getName().indexOf(".mzML") > 0) {
                    jbRemove.setEnabled(true);
                    jbConvert.setEnabled(true);
                
                    for (int iI = 0; iI < aFiles.length; iI++){
                        fValue = (float) aFiles[iI].length()/(1024*1024);
                        model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), String.format("%.2f", fValue), ""}); 
                    }
                }
            }
        }        
    }//GEN-LAST:event_jbAddMouseClicked

    private void jbConvertMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked
        //... Validate selection ...//        
        if (jtMzMLFiles.getRowCount()<0){
            JOptionPane.showMessageDialog(this, "Please select at least one mzML file", "Error", JOptionPane.ERROR_MESSAGE);
        }   
        else    //... Convert selected mzML files into MGF format ...//
        {
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true, "MzMLCompress");
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    progressBarDialog.setTitle("Converting mzML file(s) into MGF");
                    progressBarDialog.setVisible(true);                    
                }
            }, "ProgressBarDialog");
            thread.start();

            //... Splitting job in threads ...//
            new Thread("mzMLCompress"){
                @Override
                public void run(){
                    //... Convert each file ...//
                    String sPath = "";
                    for (int iI = 0; iI <jtMzMLFiles.getRowCount(); iI++){
                        sPath = sWorkspaceInput+jtMzMLFiles.getValueAt(iI, 0).toString();                    
                        File xmlFile = new File(sPath);
                        progressBarDialog.setTitle("Compressing file ("+(iI+1)+"/"+jtMzMLFiles.getRowCount()+") - " + xmlFile.getName());
                        progressBarDialog.setTaskName("This task may take up to several minutes (e.g. ~2mins for a 1.5GB file)");
                        progressBarDialog.repaint();
                        try {
                            MzMLCompress compress = new MzMLCompress(xmlFile, jtWorkspace.getText(), jcbZeros.isSelected());
                        } catch (IOException ex) {
                            Logger.getLogger(MzMLCompressView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jtMzMLFiles.setValueAt("Done", iI, 2);
                    }
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();        
                }
            }.start();                                   
        }
    }//GEN-LAST:event_jbConvertMouseClicked

    private void jbRemoveMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbRemoveMouseClicked
        if (jtMzMLFiles.getSelectedRow()<0){
            JOptionPane.showMessageDialog(this, "Please select the file to remove", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            DefaultTableModel model = (DefaultTableModel) jtMzMLFiles.getModel();
            model.removeRow(jtMzMLFiles.getSelectedRow());
            jtMzMLFiles.setModel(model);
        }
    }//GEN-LAST:event_jbRemoveMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTabbedPane jTabbedPane1;
    private JButton jbAdd;
    private JButton jbBrowse;
    private JButton jbConvert;
    private JButton jbRemove;
    private JCheckBox jcbZeros;
    private JLabel jlInstructions;
    private JLabel jlInstructions2;
    private JLabel jlOutputFolder;
    private JTable jtMzMLFiles;
    private JTextField jtWorkspace;
    // End of variables declaration//GEN-END:variables
}
