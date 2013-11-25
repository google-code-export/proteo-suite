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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.utils.ProgressBarDialog;
//import uk.ac.liv.progenmzquantmlconvertor.ProgenMzquantmlConvertor;

/**
 * Plugin to convert MaxQuant files to mzQuantML
 * @author fgonzalez
 * @param jframe Parent Frame
 * @param sPath Working directory
 */
public class Prog2MZQView extends JPanel {

    private JFrame jfFrame;
    private String sWorkspace = "";
            
    /** Creates a new MzML2MGF form */
    public Prog2MZQView(JFrame jframe, String sWorkspace) {
        initComponents();
        this.jfFrame = jframe;
        this.sWorkspace = sWorkspace;
    }    


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbConvert = new JButton();
        jlInstructions1 = new JLabel();
        jlInstructions2 = new JLabel();
        jlProteinList = new JLabel();
        jtProteinList = new JTextField();
        jtFeatureList = new JTextField();
        jlFeatureList = new JLabel();
        jbBrowseProtein = new JButton();
        jbBrowseFeature = new JButton();
        jlIdentificationFile = new JLabel();
        jtIdentificationFile = new JTextField();
        jbBrowseIdentification = new JButton();

        setMaximumSize(new java.awt.Dimension(500, 180));
        setMinimumSize(new java.awt.Dimension(500, 180));
        setPreferredSize(new java.awt.Dimension(500, 180));

        jbConvert.setText("Convert");
        jbConvert.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbConvertMouseClicked(evt);
            }
        });

        jlInstructions1.setText("Select the folder which contains the Progenesis output files that will be converted into mzQuantML.");

        jlInstructions2.setText("The output file will contain peptide, protein and proteingroup layers.");

        jlProteinList.setText("Protein List File:");

        jlFeatureList.setText("Feature List File:");

        jbBrowseProtein.setText("Browse");
        jbBrowseProtein.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbBrowseProteinMouseClicked(evt);
            }
        });

        jbBrowseFeature.setText("Browse");
        jbBrowseFeature.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbBrowseFeatureMouseClicked(evt);
            }
        });

        jlIdentificationFile.setText("Identification File:");

        jbBrowseIdentification.setText("Browse");
        jbBrowseIdentification.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jbBrowseIdentificationMouseClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jlInstructions1)
                            .addComponent(jlInstructions2))
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jlProteinList)
                            .addComponent(jlFeatureList)
                            .addComponent(jlIdentificationFile))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jtProteinList, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jtFeatureList, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jtIdentificationFile, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jbBrowseProtein, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBrowseFeature, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBrowseIdentification, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                        .addComponent(jbConvert)
                        .addGap(99, 99, 99))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlInstructions1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlInstructions2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jtProteinList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlProteinList))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jtFeatureList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlFeatureList))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jtIdentificationFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

    private void jbConvertMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbConvertMouseClicked
        //... Convert progrenesis files into mzq ...//               
        
        if (jtProteinList.getText().isEmpty() && jtFeatureList.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one list csv file");
        } else if ((((!jtFeatureList.getText().isEmpty()) && !jtFeatureList.getText().endsWith(".csv"))) || 
                (((!jtProteinList.getText().isEmpty()) && !jtProteinList.getText().endsWith(".csv")))) {
            JOptionPane.showMessageDialog(this, "The selected list file(s) is/are not in csv format. Please select the right format.");
        } else {
            JFileChooser fileChooser = new JFileChooser(sWorkspace);
            fileChooser.setDialogTitle("Save the MzQuantML file");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("MzQuantML (*.mzq)", "mzq");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showSaveDialog(this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //File file = fileChooser.getSelectedFile();
                //final String outputFn = file.getAbsolutePath().endsWith("mzq") ? file.getAbsolutePath() : file.getAbsolutePath() + ".mzq";
                //final String flFn = jtFeatureList.getText();
                //final String plFn = jtProteinList.getText();
                //final String idFn = jtIdentificationFile.getText();

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
                        //String sPath = "";
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

    private void jbBrowseProteinMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbBrowseProteinMouseClicked
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

    private void jbBrowseFeatureMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbBrowseFeatureMouseClicked
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

    private void jbBrowseIdentificationMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jbBrowseIdentificationMouseClicked
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
    private JButton jbBrowseFeature;
    private JButton jbBrowseIdentification;
    private JButton jbBrowseProtein;
    private JButton jbConvert;
    private JLabel jlFeatureList;
    private JLabel jlIdentificationFile;
    private JLabel jlInstructions1;
    private JLabel jlInstructions2;
    private JLabel jlProteinList;
    private JTextField jtFeatureList;
    private JTextField jtIdentificationFile;
    private JTextField jtProteinList;
    // End of variables declaration//GEN-END:variables
}
