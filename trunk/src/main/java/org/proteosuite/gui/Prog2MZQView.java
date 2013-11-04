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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.datatype.DatatypeConfigurationException;
import org.proteosuite.utils.ProgressBarDialog;
//import uk.ac.liv.progenmzquantmlconvertor.ProgenMzquantmlConvertor;

/**
 * Plugin to convert MaxQuant files to mzQuantML
 * @author fgonzalez
 * @param jframe Parent Frame
 * @param sPath Working directory
 */
public class Prog2MZQView extends javax.swing.JPanel {

    private JFrame jfFrame;
    private String sWorkspace = "";
            
    /** Creates a new MzML2MGF form */
    public Prog2MZQView(JFrame jframe, String sWorkspace) {
        initComponents();
        this.jfFrame = jframe;
        this.sWorkspace = sWorkspace;
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new javax.swing.JButton();
        jlInstructions1 = new javax.swing.JLabel();
        jlInstructions2 = new javax.swing.JLabel();
        jlProteinList = new javax.swing.JLabel();
        jtProteinList = new javax.swing.JTextField();
        jtFeatureList = new javax.swing.JTextField();
        jlFeatureList = new javax.swing.JLabel();
        jbBrowseProtein = new javax.swing.JButton();
        jbBrowseFeature = new javax.swing.JButton();
        jlIdentificationFile = new javax.swing.JLabel();
        jtIdentificationFile = new javax.swing.JTextField();
        jbBrowseIdentification = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 180));
        setMinimumSize(new java.awt.Dimension(500, 180));
        setPreferredSize(new java.awt.Dimension(500, 180));

        jbConvert.setText("Convert");
        jbConvert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jlInstructions1.setText("Select the folder which contains the Progenesis output files that will be converted into mzQuantML.");

        jlInstructions2.setText("The output file will contain peptide, protein and proteingroup layers.");

        jlProteinList.setText("Protein List File:");

        jlFeatureList.setText("Feature List File:");

        jbBrowseProtein.setText("Browse");
        jbBrowseProtein.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseProteinMouseClicked(evt);
            }
        });

        jbBrowseFeature.setText("Browse");
        jbBrowseFeature.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseFeatureMouseClicked(evt);
            }
        });

        jlIdentificationFile.setText("Identification File:");

        jbBrowseIdentification.setText("Browse");
        jbBrowseIdentification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseIdentificationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlInstructions1)
                            .addComponent(jlInstructions2))
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlProteinList)
                            .addComponent(jlFeatureList)
                            .addComponent(jlIdentificationFile))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtProteinList, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jtFeatureList, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jtIdentificationFile, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbBrowseProtein, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBrowseFeature, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBrowseIdentification, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                        .addComponent(jbConvert)
                        .addGap(99, 99, 99))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlInstructions1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlInstructions2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtProteinList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlProteinList))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtFeatureList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlFeatureList))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtIdentificationFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlIdentificationFile))
                        .addGap(12, 12, 12)
                        .addComponent(jbConvert))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jbBrowseIdentification))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jbBrowseProtein))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jbBrowseFeature)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbConvertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked
        //... Convert progrenesis files into mzq ...//               
        
        if (jtProteinList.getText().isEmpty() && jtFeatureList.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one list csv file");
        } else if ((((!jtFeatureList.getText().isEmpty()) && !jtFeatureList.getText().endsWith(".csv"))) || 
                (((!jtProteinList.getText().isEmpty()) && !jtProteinList.getText().endsWith(".csv")))) {
            JOptionPane.showMessageDialog(this, "The selected list file(s) is/are not in csv format. Please select the right format.");
        } else {
            JFileChooser fileChooser = new javax.swing.JFileChooser(sWorkspace);
            fileChooser.setDialogTitle("Save the MzQuantML file");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("MzQuantML (*.mzq)", "mzq");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showSaveDialog(this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                final String outputFn = file.getAbsolutePath().endsWith("mzq") ? file.getAbsolutePath() : file.getAbsolutePath() + ".mzq";
                final String flFn = jtFeatureList.getText();
                final String plFn = jtProteinList.getText();
                final String idFn = jtIdentificationFile.getText();

                //... Start process ...//
                final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true, "Prog2MZQ");
                final Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        progressBarDialog.setTitle("Converting Progenesis file(s) into mzQuantML");
                        progressBarDialog.setVisible(true);                    
                    }
                }, "ProgressBarDialog");
                thread.start();

                new Thread("Prog2MZQ"){
                    @Override
                    public void run(){
                        //... Convert each file ...//
                        String sPath = "";
                        progressBarDialog.setTitle("Converting files");
                        progressBarDialog.setTaskName("Creating mzq file ...");
                        progressBarDialog.repaint();
//                        try {
//                            String fileName = ProgenMzquantmlConvertor.createOutput(flFn, plFn, idFn, outputFn, false);
//                        } catch (IOException ex) {
//                            Logger.getLogger(Prog2MZQView.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (DatatypeConfigurationException ex) {
//                            Logger.getLogger(Prog2MZQView.class.getName()).log(Level.SEVERE, null, ex);
//                        }

                        progressBarDialog.setVisible(false);
                        progressBarDialog.dispose();
                        JOptionPane.showMessageDialog(Prog2MZQView.this, "Your file was saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }.start();                     
            }
        }               
    }//GEN-LAST:event_jbConvertMouseClicked

    private void jbBrowseProteinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbBrowseProteinMouseClicked
        //... Selecting protein list file ...//
        JFileChooser chooser = new JFileChooser(sWorkspace);        
        chooser.setDialogTitle("Select your protein list file");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Progenesis Protein List Files (*.csv)", "csv");        
        chooser.addChoosableFileFilter(filter1);

        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            jtProteinList.setText(file.getPath());
        }
    }//GEN-LAST:event_jbBrowseProteinMouseClicked

    private void jbBrowseFeatureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbBrowseFeatureMouseClicked
        //... Selecting feature list file ...//
        JFileChooser chooser = new JFileChooser(sWorkspace);        
        chooser.setDialogTitle("Select your feature list file");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Progenesis Feature List Files (*.csv)", "csv");        
        chooser.addChoosableFileFilter(filter1);

        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){            
            File file = chooser.getSelectedFile();
            jtFeatureList.setText(file.getPath());            
        }       
    }//GEN-LAST:event_jbBrowseFeatureMouseClicked

    private void jbBrowseIdentificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbBrowseIdentificationMouseClicked
        //... Selecting identification file ...//
        JFileChooser chooser = new JFileChooser(sWorkspace);        
        chooser.setDialogTitle("Select your identification file");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Progenesis Identification Files (*.csv)", "csv");        
        chooser.addChoosableFileFilter(filter1);

        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){            
            File file = chooser.getSelectedFile();
            jtIdentificationFile.setText(file.getPath());            
        }    
    }//GEN-LAST:event_jbBrowseIdentificationMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbBrowseFeature;
    private javax.swing.JButton jbBrowseIdentification;
    private javax.swing.JButton jbBrowseProtein;
    private javax.swing.JButton jbConvert;
    private javax.swing.JLabel jlFeatureList;
    private javax.swing.JLabel jlIdentificationFile;
    private javax.swing.JLabel jlInstructions1;
    private javax.swing.JLabel jlInstructions2;
    private javax.swing.JLabel jlProteinList;
    private javax.swing.JTextField jtFeatureList;
    private javax.swing.JTextField jtIdentificationFile;
    private javax.swing.JTextField jtProteinList;
    // End of variables declaration//GEN-END:variables
}
