/*
 * ----------------------------------------------------------------------------
 * IdentParamsView.java
 * ----------------------------------------------------------------------------
 * Description:       Parameter settings for the peptide/protein identification
 * Developer:         Faviel Gonzalez
 * Created:           03 April 2013
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org
 * ----------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.lang.ArrayUtils;
import org.proteosuite.utils.Unimod2MSGPlus;

/**
 *
 * @author faviel
 */
public class IdentParamsView extends javax.swing.JPanel {

    private String sWorkspace = "";    
    
    public IdentParamsView(String sWorkspace) {
        this.sWorkspace = sWorkspace;
        initComponents();
        initMods();
        initValues();
    }
    public void initMods(){
        Unimod2MSGPlus getMods = new Unimod2MSGPlus();
        List<ArrayList> modList = new ArrayList();
        DefaultListModel listModel = new DefaultListModel();

        modList = getMods.getAllMods();
        for (ArrayList al : modList) {
            listModel.addElement(al.get(0));
        }
        jlstUnimods.setModel(listModel);
    }
    public void initValues(){
        //... Getting total available processors for multithreading ...//
        int processors = Runtime.getRuntime().availableProcessors();
        if (processors <= 0){
            processors = 1;
        }else{
            processors--;
        }                        
        jcThreads.setSelectedIndex(processors);
        jcEnzyme.setSelectedIndex(1);
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgMonoAver = new javax.swing.ButtonGroup();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jlEnzyme = new javax.swing.JLabel();
        jcMSMSTol = new javax.swing.JComboBox();
        jtMSMSTol = new javax.swing.JTextField();
        jlMSMSTol = new javax.swing.JLabel();
        jcEnzyme = new javax.swing.JComboBox();
        jlDatabaseFile = new javax.swing.JLabel();
        jtDatabaseFile = new javax.swing.JTextField();
        jbAddDatabaseFile = new javax.swing.JButton();
        jlErrorRange = new javax.swing.JLabel();
        jchbSearchDecoy = new javax.swing.JCheckBox();
        jlFragMethod = new javax.swing.JLabel();
        jcFragMethod = new javax.swing.JComboBox();
        jlInstrument = new javax.swing.JLabel();
        jcInstrument = new javax.swing.JComboBox();
        jtErrorRange = new javax.swing.JTextField();
        jlProtocol = new javax.swing.JLabel();
        jcProtocol = new javax.swing.JComboBox();
        jlMaxMissedCleavage = new javax.swing.JLabel();
        jcMaxMissedCleavage = new javax.swing.JComboBox();
        jlMaxPepLen = new javax.swing.JLabel();
        jtMaxPepLen = new javax.swing.JTextField();
        jlMinPepLen = new javax.swing.JLabel();
        jtMinPepLen = new javax.swing.JTextField();
        jlMinCharge = new javax.swing.JLabel();
        jlMaxCharge = new javax.swing.JLabel();
        jtMinCharge = new javax.swing.JTextField();
        jtMaxCharge = new javax.swing.JTextField();
        jlMatches = new javax.swing.JLabel();
        jtMatches = new javax.swing.JTextField();
        jlOutput = new javax.swing.JLabel();
        jcOutput = new javax.swing.JComboBox();
        jlThreads = new javax.swing.JLabel();
        jcThreads = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jlFixedMods = new javax.swing.JLabel();
        jlVarMods = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlstVarMods = new javax.swing.JList();
        jbAddVarMods = new javax.swing.JButton();
        jbDelVarMods = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlstUnimods = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstFixedMods = new javax.swing.JList();
        jbAddFixedMods = new javax.swing.JButton();
        jbDelMods = new javax.swing.JButton();
        jlUniMod = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jLabel13.setText("jLabel13");

        setMaximumSize(new java.awt.Dimension(638, 585));
        setMinimumSize(new java.awt.Dimension(638, 585));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters:"));
        jPanel1.setMaximumSize(new java.awt.Dimension(700, 234));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 234));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 234));

        jlEnzyme.setText("Enzyme:");

        jcMSMSTol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ppm", "Da" }));

        jtMSMSTol.setText("20");
        jtMSMSTol.setToolTipText("Precursor Mass Tolerance (E.g. +/- 2.5Da, +/- 20ppm, etc.), Use commas for asymetric values (e.g. 0.5,2.5)");

        jlMSMSTol.setText("<html> MS/MS tol. &plusmn;</html>");

        jcEnzyme.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "unspecified cleavage", "Trypsin", "Chymotrypsin", "Lys-C", "Lys-N", "glutamyl endopeptidase", "Arg-C", "Asp-N", "alphaLP", "no cleavage" }));

        jlDatabaseFile.setText("Database file:");

        jtDatabaseFile.setToolTipText("Enter the path to the database file");

        jbAddDatabaseFile.setText("...");
        jbAddDatabaseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddDatabaseFileActionPerformed(evt);
            }
        });

        jlErrorRange.setText("Error Range:");

        jchbSearchDecoy.setText("Search Decoy");

        jlFragMethod.setText("Fragmentation Method:");

        jcFragMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default", "CID", "ETD", "HCD" }));

        jlInstrument.setText("Instrument:");

        jcInstrument.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Low-res LCQ/LTQ", "High-res LTQ", "TOF", "Q-Exactive" }));

        jtErrorRange.setText("0,1");
        jtErrorRange.setToolTipText("Range of allowed isotope peak errors (E.g. 0,1)");

        jlProtocol.setText("Protocol:");

        jcProtocol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default", "Phosphorylation", "iTRAQ", "iTRAQPhospo" }));

        jlMaxMissedCleavage.setText("Missed Claveages:");

        jcMaxMissedCleavage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "non-tryptic", "semi-tryptic", "fully-triptic peptides only" }));

        jlMaxPepLen.setText("Max Peptide Length:");

        jtMaxPepLen.setText("40");
        jtMaxPepLen.setToolTipText("Maximum peptide length to consider");

        jlMinPepLen.setText("Min Peptide Length:");

        jtMinPepLen.setText("6");
        jtMinPepLen.setToolTipText("Minimum peptide length to consider");

        jlMinCharge.setText("Min Charge:");

        jlMaxCharge.setText("Max Charge:");

        jtMinCharge.setText("2");
        jtMinCharge.setToolTipText("Minimum precursor charge to consider if charges are not specified in the spectrum file");

        jtMaxCharge.setText("3");
        jtMaxCharge.setToolTipText("Maximum precursor charge to consider if charges are not specified in the spectrum file");

        jlMatches.setText("Matches per spectra:");

        jtMatches.setToolTipText("Number of matches per spectrum to be reported");

        jlOutput.setText("Output:");

        jcOutput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Basic scores only", "Additional features" }));

        jlThreads.setText("Threads:");

        jcThreads.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        jcThreads.setToolTipText("Number of concurrent threads to be executed");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlDatabaseFile)
                        .addGap(17, 17, 17)
                        .addComponent(jtDatabaseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbAddDatabaseFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jtMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jcMSMSTol, 0, 83, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlEnzyme, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlErrorRange))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtErrorRange, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jcEnzyme, 0, 157, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlProtocol)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcProtocol, 0, 158, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlFragMethod)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcFragMethod, 0, 88, Short.MAX_VALUE))
                                    .addComponent(jchbSearchDecoy, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(42, 42, 42))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMaxPepLen)
                                    .addComponent(jlMinPepLen))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtMaxPepLen)
                                    .addComponent(jtMinPepLen, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMinCharge)
                                    .addComponent(jlMaxCharge))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtMinCharge, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                                    .addComponent(jtMaxCharge, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlMatches)
                                        .addGap(13, 13, 13)
                                        .addComponent(jtMatches, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlOutput)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jcOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlMaxMissedCleavage)
                            .addComponent(jlInstrument))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcInstrument, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jlThreads)
                                .addGap(18, 18, 18)
                                .addComponent(jcThreads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jcMaxMissedCleavage, javax.swing.GroupLayout.Alignment.TRAILING, 0, 127, Short.MAX_VALUE))))
                .addGap(74, 74, 74))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtDatabaseFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDatabaseFile)
                    .addComponent(jbAddDatabaseFile))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jchbSearchDecoy)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlErrorRange)
                    .addComponent(jlFragMethod)
                    .addComponent(jcFragMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlInstrument)
                    .addComponent(jtErrorRange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcInstrument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlEnzyme)
                        .addComponent(jcEnzyme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlProtocol))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlMaxMissedCleavage)
                        .addComponent(jcProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcMaxMissedCleavage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlMinPepLen)
                            .addComponent(jtMinPepLen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMinCharge)
                            .addComponent(jtMinCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMatches)
                            .addComponent(jtMatches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlMaxPepLen)
                            .addComponent(jtMaxPepLen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMaxCharge)
                            .addComponent(jtMaxCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlOutput)
                            .addComponent(jcOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcThreads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlThreads)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifications:"));

        jlFixedMods.setText("Fixed modifications:");

        jlVarMods.setText("Variable modifications:");

        jlstVarMods.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "--- none selected ---" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jlstVarMods);

        jbAddVarMods.setText(">");
        jbAddVarMods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddVarModsActionPerformed(evt);
            }
        });

        jbDelVarMods.setText("<");
        jbDelVarMods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDelVarModsActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jlstUnimods);

        jlstFixedMods.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "--- none selected ---" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jlstFixedMods);

        jbAddFixedMods.setText(">");
        jbAddFixedMods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddFixedModsActionPerformed(evt);
            }
        });

        jbDelMods.setText("<");
        jbDelMods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDelModsActionPerformed(evt);
            }
        });

        jlUniMod.setText("Unimod modifications:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlUniMod)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbDelVarMods)
                            .addComponent(jbAddVarMods))
                        .addComponent(jbDelMods))
                    .addComponent(jbAddFixedMods))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFixedMods, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(jlVarMods)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlUniMod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jlFixedMods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jbAddFixedMods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbDelMods)))
                        .addGap(18, 18, 18)
                        .addComponent(jlVarMods)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jbAddVarMods)
                                .addGap(18, 18, 18)
                                .addComponent(jbDelVarMods)
                                .addGap(32, 32, 32))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jButton6.setText("Save");

        jButton7.setText("Restore to default");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 541, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddDatabaseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddDatabaseFileActionPerformed
        //... Adding files ...//
        JFileChooser chooser = new JFileChooser(sWorkspace);
        chooser.setDialogTitle("Select the database file");        

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fasta Files (*.fasta)", "fasta");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Fasta Files (*.fa)", "fa");
        chooser.setFileFilter(filter2);
        chooser.setFileFilter(filter);
        
        //... Disable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);        
        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
	    if (file != null){
                if (file.getName().indexOf(".fasta") > 0) {
                    jtDatabaseFile.setText(file.getPath());
                }
                else{
                    JOptionPane.showMessageDialog(this, "Incorrect file extension. Please select a valid fasta file (*.fasta)", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }      
    }//GEN-LAST:event_jbAddDatabaseFileActionPerformed

    private void jbAddFixedModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddFixedModsActionPerformed
        //... Add Fixed Modifications ...//
        Object[] selectedFixedMods = jlstUnimods.getSelectedValues();
        ListModel fixedModsModel = jlstFixedMods.getModel();

        //... Copy elements from the list model ...//
        int oldSize = fixedModsModel.getSize();
        Object[] oldFixedMods = new Object[oldSize];
        for (int iI = 0; iI < oldSize; iI++) {
            oldFixedMods[iI] = fixedModsModel.getElementAt(iI);
        }
        
        //... Adding values ...//
        if (oldFixedMods[0].equals("--- none selected ---")) {
            DefaultListModel listModel = new DefaultListModel();
            if (selectedFixedMods.length > 0) {
                for (Object mod : selectedFixedMods) {
                    listModel.addElement(mod);
                }
                jlstFixedMods.setModel(listModel);
            }
        }
        else {
            DefaultListModel listModel = new DefaultListModel();
            Object[] allMods = ArrayUtils.addAll(oldFixedMods, selectedFixedMods);
            Arrays.sort(allMods);
            for (Object mod : allMods) {
                if (!listModel.contains(mod)) {
                    listModel.addElement(mod);
                }
            }
            jlstFixedMods.setModel(listModel);
        }
    }//GEN-LAST:event_jbAddFixedModsActionPerformed

    private void jbDelModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDelModsActionPerformed
        //... Remove Fixed Modifications ...//
        Object[] selectedFixedMods = jlstFixedMods.getSelectedValues();
        ListModel fixedModsModel = jlstFixedMods.getModel();

        //... Copy elements from the list model ...//
        int size = fixedModsModel.getSize();
        Object[] fixedMods = new Object[size];
        for (int iI = 0; iI < size; iI++) {
            fixedMods[iI] = fixedModsModel.getElementAt(iI);
        }

        List<Object> fixedModsList = new LinkedList(Arrays.asList(fixedMods));

        for (Object mod : selectedFixedMods) {
            fixedModsList.remove(mod);
        }
        if (fixedModsList.isEmpty()) {
            fixedModsList.add("--- none selected ---");
        }
        //... Set new list ...//
        DefaultListModel newFixedModsModel = new DefaultListModel();
        for (Object mod : fixedModsList) {
            newFixedModsModel.addElement(mod);
        }
        jlstFixedMods.setModel(newFixedModsModel);
    }//GEN-LAST:event_jbDelModsActionPerformed

    private void jbAddVarModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddVarModsActionPerformed
        //... Add Fixed Modifications ...//
        Object[] selectedVarMods = jlstUnimods.getSelectedValues();
        ListModel varModsModel = jlstVarMods.getModel();

        //... Copy elements from the list model ...//
        int oldSize = varModsModel.getSize();
        Object[] oldVarMods = new Object[oldSize];
        for (int iI = 0; iI < oldSize; iI++) {
            oldVarMods[iI] = varModsModel.getElementAt(iI);
        }
        
        //... Adding values ...//
        if (oldVarMods[0].equals("--- none selected ---")) {
            DefaultListModel listModel = new DefaultListModel();
            if (selectedVarMods.length > 0) {
                for (Object mod : selectedVarMods) {
                    listModel.addElement(mod);
                }
                jlstVarMods.setModel(listModel);
            }
        }
        else {
            DefaultListModel listModel = new DefaultListModel();
            Object[] allMods = ArrayUtils.addAll(oldVarMods, selectedVarMods);
            Arrays.sort(allMods);
            for (Object mod : allMods) {
                if (!listModel.contains(mod)) {
                    listModel.addElement(mod);
                }
            }
            jlstVarMods.setModel(listModel);
        }        
    }//GEN-LAST:event_jbAddVarModsActionPerformed

    private void jbDelVarModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDelVarModsActionPerformed
        //... Remove Fixed Modifications ...//
        Object[] selectedVarMods = jlstVarMods.getSelectedValues();
        ListModel varModsModel = jlstVarMods.getModel();

        //... Copy elements from the list model ...//
        int size = varModsModel.getSize();
        Object[] varMods = new Object[size];
        for (int iI = 0; iI < size; iI++) {
            varMods[iI] = varModsModel.getElementAt(iI);
        }

        List<Object> varModsList = new LinkedList(Arrays.asList(varMods));

        for (Object mod : selectedVarMods) {
            varModsList.remove(mod);
        }
        if (varModsList.isEmpty()) {
            varModsList.add("--- none selected ---");
        }
        //... Set new list ...//
        DefaultListModel newVarModsModel = new DefaultListModel();
        for (Object mod : varModsList) {
            newVarModsModel.addElement(mod);
        }
        jlstVarMods.setModel(newVarModsModel);        
    }//GEN-LAST:event_jbDelVarModsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgMonoAver;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbAddDatabaseFile;
    private javax.swing.JButton jbAddFixedMods;
    private javax.swing.JButton jbAddVarMods;
    private javax.swing.JButton jbDelMods;
    private javax.swing.JButton jbDelVarMods;
    private javax.swing.JComboBox jcEnzyme;
    private javax.swing.JComboBox jcFragMethod;
    private javax.swing.JComboBox jcInstrument;
    private javax.swing.JComboBox jcMSMSTol;
    private javax.swing.JComboBox jcMaxMissedCleavage;
    private javax.swing.JComboBox jcOutput;
    private javax.swing.JComboBox jcProtocol;
    private javax.swing.JComboBox jcThreads;
    private javax.swing.JCheckBox jchbSearchDecoy;
    private javax.swing.JLabel jlDatabaseFile;
    private javax.swing.JLabel jlEnzyme;
    private javax.swing.JLabel jlErrorRange;
    private javax.swing.JLabel jlFixedMods;
    private javax.swing.JLabel jlFragMethod;
    private javax.swing.JLabel jlInstrument;
    private javax.swing.JLabel jlMSMSTol;
    private javax.swing.JLabel jlMatches;
    private javax.swing.JLabel jlMaxCharge;
    private javax.swing.JLabel jlMaxMissedCleavage;
    private javax.swing.JLabel jlMaxPepLen;
    private javax.swing.JLabel jlMinCharge;
    private javax.swing.JLabel jlMinPepLen;
    private javax.swing.JLabel jlOutput;
    private javax.swing.JLabel jlProtocol;
    private javax.swing.JLabel jlThreads;
    private javax.swing.JLabel jlUniMod;
    private javax.swing.JLabel jlVarMods;
    private javax.swing.JList jlstFixedMods;
    private javax.swing.JList jlstUnimods;
    private javax.swing.JList jlstVarMods;
    private javax.swing.JTextField jtDatabaseFile;
    private javax.swing.JTextField jtErrorRange;
    private javax.swing.JTextField jtMSMSTol;
    private javax.swing.JTextField jtMatches;
    private javax.swing.JTextField jtMaxCharge;
    private javax.swing.JTextField jtMaxPepLen;
    private javax.swing.JTextField jtMinCharge;
    private javax.swing.JTextField jtMinPepLen;
    // End of variables declaration//GEN-END:variables
}
