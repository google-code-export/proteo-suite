/*
 * --------------------------------------------------------------------------
 * MaxQ2MZQView.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to Convert MaxQuant files to mzQuantML
 * Developer:         fgonzalez
 * Created:           27 February 2012
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.datatype.DatatypeConfigurationException;
import org.proteosuite.utils.ProgressBarDialog;
import uk.ac.liv.maxquantmzquantmlconvertor.MaxquantMzquantmlConvertor;

/**
 * Form to capture all MzML files to be converted into MGF.
 * @author fgonzalez
 * @param jframe Parent Frame
 * @param sPath File to convert
 */
public class MaxQ2MZQView extends javax.swing.JPanel {

    private JFrame jfFrame;
            
    /** Creates a new MzML2MGF form */
    public MaxQ2MZQView(JFrame jframe, String sPath) {
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
        jlInstructions1 = new javax.swing.JLabel();
        jlInstructions2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(500, 150));
        setMinimumSize(new java.awt.Dimension(500, 150));
        setPreferredSize(new java.awt.Dimension(500, 150));

        jbConvert.setText("Convert");
        jbConvert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jLabel1.setText("Maxquant folder:");

        jbBrowse.setText("Browse");
        jbBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseMouseClicked(evt);
            }
        });

        jlInstructions1.setText("Select the folder which contains the MaxQuant output files that will be converted into mzQuantML.");

        jlInstructions2.setText("The output file will contain peptide, protein and proteingroup layers.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlInstructions1)
                    .addComponent(jlInstructions2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbConvert)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlInstructions1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInstructions2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbConvert)
                .addContainerGap(37, Short.MAX_VALUE))
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
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File selected = chooser.getSelectedFile();
            this.jtWorkspace.setText(selected.getAbsolutePath());
        }
    }//GEN-LAST:event_jbBrowseMouseClicked

    private void jbConvertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked

        //... Converting MaxQuant File to mzq ...//
        if (jtWorkspace.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select the output folder");
        }else{
            //... Check all files neccessary for conversion ...//
            File path = new File(jtWorkspace.getText());
            String[] fileList = path.list();
            String[] requireList = {"evidence.txt", "peptides.txt", "proteinGroups.txt", "experimentalDesignTemplate.txt", "parameters.txt"}; 
            
            boolean contain = true;
            List<String> aList = Arrays.asList(fileList);
            for (int iI = 0; iI < requireList.length; iI++) {
                if (!aList.contains(requireList[iI])) {
                    contain = false;
                    break;
                }
            } 
            if(contain){
                JFileChooser fileChooser = new javax.swing.JFileChooser(jtWorkspace.getText());
                fileChooser.setDialogTitle("Save the MzQuantML file");

                //... Applying file extension filters ...//
                FileNameExtensionFilter filter = new FileNameExtensionFilter("MzQuantML (*.mzq)", "mzq");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showSaveDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    final String outputFn = file.getAbsolutePath().endsWith("mzq") ? file.getName().replace("mzq", "") : file.getName();

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true, "MaxQ2MZQ");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            progressBarDialog.setTitle("Converting MaxQuant file(s) into mzQuantML");
                            progressBarDialog.setVisible(true);                    
                        }
                    }, "ProgressBarDialog");
                    thread.start();

                    //... Splitting job in threads ...//
                    new Thread("MaxQ2MZQ"){
                        @Override
                        public void run(){
                            //... Convert each file ...//
                            String sPath = "";
                            progressBarDialog.setTitle("Converting folder " + jtWorkspace.getText());
                            progressBarDialog.setTaskName("Creating mzq file ...");
                            progressBarDialog.repaint();
                            try {
                                MaxquantMzquantmlConvertor convert = new MaxquantMzquantmlConvertor(jtWorkspace.getText(), outputFn);
                            } catch (IOException ex) {
                                Logger.getLogger(MaxQ2MZQView.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DatatypeConfigurationException ex) {
                                Logger.getLogger(MaxQ2MZQView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();  
                            JOptionPane.showMessageDialog(MaxQ2MZQView.this, "Your file was created successfully! ", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }.start();                                                
                }                 
            }             
            else{
                JOptionPane.showMessageDialog(this, "The directory doesnt contain all maxquant files. Please verify the path.", "Error", JOptionPane.ERROR_MESSAGE);
            }           
        }                    
    }//GEN-LAST:event_jbConvertMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbConvert;
    private javax.swing.JLabel jlInstructions1;
    private javax.swing.JLabel jlInstructions2;
    private javax.swing.JTextField jtWorkspace;
    // End of variables declaration//GEN-END:variables
}
