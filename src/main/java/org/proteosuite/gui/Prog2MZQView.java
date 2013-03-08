/*
 * --------------------------------------------------------------------------
 * MaxQ2MZQView.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to Convert MaxQuant files to mzQuantML
 * Developer:         Faviel Gonzalez
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
import javax.swing.table.DefaultTableModel;
import javax.xml.datatype.DatatypeConfigurationException;
import org.proteosuite.utils.MzML2MGF;
import org.proteosuite.utils.ProgressBarDialog;
import uk.ac.liv.maxquantmzquantmlconvertor.MaxquantMzquantmlConvertor;

/**
 * Form to capture all MzML files to be converted into MGF.
 * @author faviel
 * @param jframe Parent Frame
 * @param sPath File to convert
 */
public class Prog2MZQView extends javax.swing.JPanel {

    private JFrame jfFrame;
            
    /** Creates a new MzML2MGF form */
    public Prog2MZQView(JFrame jframe, String sPath) {
        initComponents();
        this.jfFrame = jframe;
        this.jtWorkspace.setText(sPath);
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new javax.swing.JButton();
        jlOutput = new javax.swing.JLabel();
        jtWorkspace = new javax.swing.JTextField();
        jbBrowse = new javax.swing.JButton();
        jlInstructions1 = new javax.swing.JLabel();
        jlInstructions2 = new javax.swing.JLabel();
        jlProteinList = new javax.swing.JLabel();
        jtProteinList = new javax.swing.JTextField();
        jtFeatureList = new javax.swing.JTextField();
        jlFeatureList = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(490, 200));
        setMinimumSize(new java.awt.Dimension(490, 200));
        setPreferredSize(new java.awt.Dimension(490, 200));

        jbConvert.setText("Convert");
        jbConvert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jlOutput.setText("Output:");

        jbBrowse.setText("Browse");
        jbBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbBrowseMouseClicked(evt);
            }
        });

        jlInstructions1.setText("Select the folder which contains the Progenesis output files that will be converted into mzQuantML.");

        jlInstructions2.setText("The output file will contain peptide, protein and proteingroup layers.");

        jlProteinList.setText("Protein List:");

        jlFeatureList.setText("Feature List:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlInstructions1)
                    .addComponent(jlInstructions2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jbConvert)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlOutput)
                                .addComponent(jlProteinList)
                                .addComponent(jlFeatureList))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jtProteinList)
                                .addComponent(jtWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                                .addComponent(jtFeatureList))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(jlOutput)
                    .addComponent(jtWorkspace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlProteinList)
                    .addComponent(jtProteinList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtFeatureList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlFeatureList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jbConvert)
                .addContainerGap())
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

        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this.jfFrame, true, "Prog2MZQ");
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                progressBarDialog.setTitle("Converting MaxQuant file(s) into mzQuantML");
                progressBarDialog.setVisible(true);                    
            }
        }, "ProgressBarDialog");
        thread.start();

        //... Splitting job in threads ...//
        new Thread("Prog2MZQ"){
            @Override
            public void run(){
                //... Convert each file ...//
                String sPath = "";
                progressBarDialog.setTitle("Converting folder " + jtWorkspace.getText());
                progressBarDialog.setTaskName("Creating mzq file ...");
                progressBarDialog.repaint();
                try {
                    MaxquantMzquantmlConvertor convert = new MaxquantMzquantmlConvertor(jtWorkspace.getText(), jtProteinList.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Prog2MZQView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(Prog2MZQView.class.getName()).log(Level.SEVERE, null, ex);
                }
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();
    }//GEN-LAST:event_jbConvertMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbBrowse;
    private javax.swing.JButton jbConvert;
    private javax.swing.JLabel jlFeatureList;
    private javax.swing.JLabel jlInstructions1;
    private javax.swing.JLabel jlInstructions2;
    private javax.swing.JLabel jlOutput;
    private javax.swing.JLabel jlProteinList;
    private javax.swing.JTextField jtFeatureList;
    private javax.swing.JTextField jtProteinList;
    private javax.swing.JTextField jtWorkspace;
    // End of variables declaration//GEN-END:variables
}
