/*
 * --------------------------------------------------------------------------
 * MzML2MGFView.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to Convert mzML files to MGF
 * Developer:         fgonzalez
 * Created:           09 February 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.utils.MzML2MGF;
import org.proteosuite.utils.ProgressBarDialog;

/**
 * Form to capture all MzML files to be converted into MGF.
 * @author fgonzalez
 * @param jframe Parent Frame
 * @param sPath File to convert
 */
public class MzML2MGFView extends JPanel {

    private String sWorkspaceInput;
    private JFrame jfFrame;
            
    /** Creates a new MzML2MGF form */
    public MzML2MGFView(JFrame jframe, String sPath) {
        initComponents();
        this.jfFrame = jframe;
        this.jtWorkspace.setText(sPath);
    }    


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new JButton();
        jLabel1 = new JLabel();
        jtWorkspace = new JTextField();
        jbBrowse = new JButton();
        jbAdd = new JButton();
        jbRemove = new JButton();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        jtMzMLFiles = new JTable();
        jSeparator1 = new JSeparator();
        jlInstructions1 = new JLabel();
        jlInstructions2 = new JLabel();
        jlInstructions3 = new JLabel();

        setMaximumSize(new Dimension(490, 390));
        setMinimumSize(new Dimension(490, 390));
        setPreferredSize(new Dimension(490, 390));

        jbConvert.setText("Convert");
        jbConvert.setEnabled(false);
        jbConvert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jLabel1.setText("Output folder:");

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

        jlInstructions1.setText("Select your mzML files to be converted into Mascot Generic Format (MGF). Output files will include");

        jlInstructions2.setText("different parameters in the scan title (e.g. scan number, scan id and retention time).");

        jlInstructions3.setForeground(new Color(102, 102, 102));
        jlInstructions3.setText("Example: Scan:960, (rt:1407.5947), (id:controllerType=0 controllerNumber=1 scan=961)");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jlInstructions1)
                            .addComponent(jlInstructions2)
                            .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jlInstructions3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1, GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jtWorkspace, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jbBrowse, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(11, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAdd, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbRemove)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbConvert)
                        .addGap(253, 253, 253))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlInstructions1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInstructions2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlInstructions3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jtWorkspace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jbBrowse))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdd)
                    .addComponent(jbRemove)
                    .addComponent(jbConvert))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbBrowseMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbBrowseMouseClicked
        //... Selecting output folder ...//
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(jtWorkspace.getText()));
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
        chooser.setDialogTitle("Select the file(s) to convert");

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
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true, "MzML2MGF");
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    progressBarDialog.setTitle("Converting mzML file(s) into MGF");
                    progressBarDialog.setVisible(true);                    
                }
            }, "ProgressBarDialog");
            thread.start();

            //... Splitting job in threads ...//
            new Thread("MzML2MGF"){
                @Override
                public void run(){
                    //... Convert each file ...//
                    String sPath = "";
                    for (int iI = 0; iI <jtMzMLFiles.getRowCount(); iI++){
                        sPath = sWorkspaceInput+jtMzMLFiles.getValueAt(iI, 0).toString();                    
                        File xmlFile = new File(sPath);
                        progressBarDialog.setTitle("Converting file ("+(iI+1)+"/"+jtMzMLFiles.getRowCount()+") - " + xmlFile.getName());
                        progressBarDialog.setTaskName("This task may take up to several minutes (e.g. ~2mins for a 1.5GB file)");
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
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTabbedPane jTabbedPane1;
    private JButton jbAdd;
    private JButton jbBrowse;
    private JButton jbConvert;
    private JButton jbRemove;
    private JLabel jlInstructions1;
    private JLabel jlInstructions2;
    private JLabel jlInstructions3;
    private JTable jtMzMLFiles;
    private JTextField jtWorkspace;
    // End of variables declaration//GEN-END:variables
}
