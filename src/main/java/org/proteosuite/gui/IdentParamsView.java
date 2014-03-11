/*
 * ----------------------------------------------------------------------------
 * IdentParamsView.java
 * ----------------------------------------------------------------------------
 * Description:       Parameter settings for peptide/protein identification
 * Developer:         fgonzalez
 * Created:           03 April 2013
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org
 * ----------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.WorkSpace;
import org.proteosuite.utils.Unimod2MSGPlus;

/**
 * This forms allows to specify different parameters for the search
 * @param sWorskpace location 
 * @param sMode type of entry (edit|execute)
 * @author fgonzalez
 */
public class IdentParamsView extends JPanel {
	private static final WorkSpace workSpace = WorkSpace.getInstance();
 
    private String sMode = "";
    private String sRegex = "";
    private Map<String, String> hmParams = new HashMap<String, String>();
    private Map<String, String> hmUniModsFixed = new HashMap<String, String>();
    private Map<String, String> hmUniModsVar = new HashMap<String, String>();
    private boolean bRun = false;
    private boolean bProteinInference = false;
    private File modificationFile;
    
    public IdentParamsView(String sMode) {
        this.sMode = sMode;
        initComponents();
        initMods();
        initValues();
    }

    //... Initialise modifications from Unimod ...//
    public void initMods(){
        Unimod2MSGPlus getMods = new Unimod2MSGPlus();
        List<List<String>> modList = new ArrayList<List<String>>();
        DefaultListModel<String> listModel = new DefaultListModel<String>();

        modList = getMods.getAllMods();
        for (List<String> al : modList) {
            listModel.addElement(al.get(0));
            //... 0=Key, 1=Mass(E.g. 15.994915), 2=site(E.g. M), 3=position(E.g. N-term), 4=psiMod(E.g. Oxidation) ...//
            hmUniModsFixed.put(al.get(0).toString(), al.get(1).toString()+","+al.get(2).toString()+",fix,"+al.get(3).toString()+","+al.get(4).toString());
            hmUniModsVar.put(al.get(0).toString(), al.get(1).toString()+","+al.get(2).toString()+",opt,"+al.get(3).toString()+","+al.get(4).toString());            
        }
        jlstUnimods.setModel(listModel);
    }
    //... Initialise values ...//
    public void initValues(){
        //... Getting total available processors for multithreading ...//
        int processors = Runtime.getRuntime().availableProcessors();
        if (processors <= 0)
            processors = 1;
        else if (processors >= 8) // TODO: This needs fixing at the jcThreads level
        	processors = 7;
        else
            processors--;

        jcThreads.setSelectedIndex(processors);
        jcEnzyme.setSelectedIndex(1);
        jcMaxMissedCleavage.setSelectedIndex(2);
        if (sMode.equals("execute")){
            jbSetDefaults.setVisible(false);
            jbSave.setVisible(false);
            jbRun.setVisible(true);    
        }
        else{
            jbSetDefaults.setVisible(true);
            jbSave.setVisible(true);
            jbRun.setVisible(false);            
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgMonoAver = new ButtonGroup();
        jLabel13 = new JLabel();
        jPanel1 = new JPanel();
        jlEnzyme = new JLabel();
        jcMSMSTol = new JComboBox<String>();
        jtMSMSTol = new JTextField();
        jlMSMSTol = new JLabel();
        jcEnzyme = new JComboBox<String>();
        jlDatabaseFile = new JLabel();
        jtDatabaseFile = new JTextField();
        jbAddDatabaseFile = new JButton();
        jlErrorRange = new JLabel();
        jchbSearchDecoy = new JCheckBox();
        jlFragMethod = new JLabel();
        jcFragMethod = new JComboBox<String>();
        jlInstrument = new JLabel();
        jcInstrument = new JComboBox<String>();
        jtErrorRange = new JTextField();
        jlProtocol = new JLabel();
        jcProtocol = new JComboBox<String>();
        jlMaxMissedCleavage = new JLabel();
        jcMaxMissedCleavage = new JComboBox<String>();
        jlMaxPepLen = new JLabel();
        jtMaxPepLen = new JTextField();
        jlMinPepLen = new JLabel();
        jtMinPepLen = new JTextField();
        jlMinCharge = new JLabel();
        jlMaxCharge = new JLabel();
        jtMinCharge = new JTextField();
        jtMaxCharge = new JTextField();
        jlMatches = new JLabel();
        jtSpectraMatches = new JTextField();
        jlOutput = new JLabel();
        jcOutput = new JComboBox<String>();
        jlThreads = new JLabel();
        jcThreads = new JComboBox<String>();
        jPanel2 = new JPanel();
        jlFixedMods = new JLabel();
        jlVarMods = new JLabel();
        jScrollPane3 = new JScrollPane();
        jlstVarMods = new JList<String>();
        jbAddVarMods = new JButton();
        jbDelVarMods = new JButton();
        jScrollPane4 = new JScrollPane();
        jlstUnimods = new JList<String>();
        jScrollPane2 = new JScrollPane();
        jlstFixedMods = new JList<String>();
        jbAddFixedMods = new JButton();
        jbDelMods = new JButton();
        jlUniMod = new JLabel();
        jbSave = new JButton();
        jbSetDefaults = new JButton();
        jbRun = new JButton();
        jlCancel = new JLabel();
        jcbProteinInference = new JCheckBox();
        jtRegex = new JTextField();
        jlRegex = new JLabel();

        jLabel13.setText("jLabel13");

        setMaximumSize(new java.awt.Dimension(792, 648));
        setMinimumSize(new java.awt.Dimension(792, 648));

        jPanel1.setBorder(BorderFactory.createTitledBorder("Parameters:"));
        jPanel1.setMaximumSize(new java.awt.Dimension(700, 234));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 234));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 234));

        jlEnzyme.setText("Enzyme:");

        jcMSMSTol.setModel(new DefaultComboBoxModel<String>(new String[] { "ppm", "Da" }));

        jtMSMSTol.setText("20");
        jtMSMSTol.setToolTipText("Precursor Mass Tolerance (E.g. +/- 2.5Da, +/- 20ppm, etc.), Use commas for asymetric values (e.g. 0.5,2.5)");

        jlMSMSTol.setText("<html> MS/MS tol. &plusmn;</html>");

        jcEnzyme.setModel(new DefaultComboBoxModel<String>(new String[] { "unspecified cleavage", "Trypsin", "Chymotrypsin", "Lys-C", "Lys-N", "glutamyl endopeptidase", "Arg-C", "Asp-N", "alphaLP", "no cleavage" }));

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

        jcFragMethod.setModel(new DefaultComboBoxModel<String>(new String[] { "Default", "CID", "ETD", "HCD" }));

        jlInstrument.setText("Instrument:");

        jcInstrument.setModel(new DefaultComboBoxModel<String>(new String[] { "Low-res LCQ/LTQ", "High-res LTQ", "TOF", "Q-Exactive" }));

        jtErrorRange.setText("0,1");
        jtErrorRange.setToolTipText("Range of allowed isotope peak errors (E.g. 0,1)");

        jlProtocol.setText("Protocol:");

        jcProtocol.setModel(new DefaultComboBoxModel<String>(new String[] { "Default", "Phosphorylation", "iTRAQ", "iTRAQPhospo" }));

        jlMaxMissedCleavage.setText("Tryptic peptides:");

        jcMaxMissedCleavage.setModel(new DefaultComboBoxModel<String>(new String[] { "non-tryptic", "semi-tryptic", "fully-tryptic peptides only" }));

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

        jtSpectraMatches.setText("1");
        jtSpectraMatches.setToolTipText("Number of matches per spectrum to be reported");

        jlOutput.setText("Output:");

        jcOutput.setModel(new DefaultComboBoxModel<String>(new String[] { "Basic scores only", "Additional features" }));

        jlThreads.setText("Threads:");

        jcThreads.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        jcThreads.setToolTipText("Number of concurrent threads to be executed");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
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
                        .addContainerGap(114, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jtMSMSTol, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jcMSMSTol, 0, 99, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlEnzyme, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlErrorRange))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtErrorRange, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jcEnzyme, 0, 173, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlProtocol)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcProtocol, 0, 173, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlFragMethod)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcFragMethod, 0, 103, Short.MAX_VALUE))
                                    .addComponent(jchbSearchDecoy, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMaxMissedCleavage)
                                    .addComponent(jlInstrument))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jcInstrument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcMaxMissedCleavage, 0, 126, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMaxPepLen)
                                    .addComponent(jlMinPepLen))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtMaxPepLen)
                                    .addComponent(jtMinPepLen, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlMinCharge)
                                    .addComponent(jlMaxCharge))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtMaxCharge)
                                    .addComponent(jtMinCharge, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlMatches)
                                        .addGap(13, 13, 13)
                                        .addComponent(jtSpectraMatches, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jlOutput)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jcOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(132, 132, 132)
                                .addComponent(jlThreads)
                                .addGap(18, 18, 18)
                                .addComponent(jcThreads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(74, 74, 74))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtDatabaseFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDatabaseFile)
                    .addComponent(jbAddDatabaseFile))
                .addGap(18, 18, 18)
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
                            .addComponent(jtSpectraMatches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jlstVarMods.setModel(new AbstractListModel<String>() {
            String[] strings = { "Oxidation (M)" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
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

        jlstFixedMods.setModel(new AbstractListModel<String>() {
            String[] strings = { "Carbamidomethyl (C)" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
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
                    .addComponent(jbAddVarMods)
                    .addComponent(jbAddFixedMods)
                    .addComponent(jbDelMods)
                    .addComponent(jbDelVarMods))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFixedMods, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(jlVarMods)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlUniMod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jlFixedMods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jbAddFixedMods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbDelMods)))
                        .addGap(27, 27, 27)
                        .addComponent(jlVarMods)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jbAddVarMods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbDelVarMods))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(11, 11, 11))
        );

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jbSetDefaults.setText("Default settings");
        jbSetDefaults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSetDefaultsActionPerformed(evt);
            }
        });

        jbRun.setText("Run");
        jbRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRunActionPerformed(evt);
            }
        });

        jlCancel.setText("Press escape (ESC) to cancel");

        jcbProteinInference.setText("Include Protein Inference");

        jtRegex.setText("XXX");

        jlRegex.setText("Regex:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbProteinInference)
                        .addGap(34, 34, 34)
                        .addComponent(jlRegex)
                        .addGap(18, 18, 18)
                        .addComponent(jtRegex, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbSetDefaults)
                        .addGap(18, 18, 18)
                        .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                        .addComponent(jlCancel)
                        .addGap(18, 18, 18)
                        .addComponent(jbRun, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbProteinInference)
                    .addComponent(jlRegex)
                    .addComponent(jtRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSetDefaults)
                    .addComponent(jbRun)
                    .addComponent(jbSave)
                    .addComponent(jlCancel))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddDatabaseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddDatabaseFileActionPerformed
        //... Adding files ...//
        JFileChooser chooser = new JFileChooser(workSpace.getWorkSpace());
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
        List<String> selectedFixedMods = jlstUnimods.getSelectedValuesList();
        ListModel<String> fixedModsModel = jlstFixedMods.getModel();
        
        //... Adding values ...//
        if (fixedModsModel.getElementAt(0).equals("--- none selected ---")) {
            DefaultListModel<String> listModel = new DefaultListModel<String>();
            if (selectedFixedMods.size() > 0) {
                for (String mod : selectedFixedMods) {
                    listModel.addElement(mod);
                }
                jlstFixedMods.setModel(listModel);
            }
        }
        else {
            DefaultListModel<String> listModel = new DefaultListModel<String>();
            List<String> allMods = new ArrayList<String>(selectedFixedMods);
            for (int i = 0; i < fixedModsModel.getSize(); i++) {
            	allMods.add(fixedModsModel.getElementAt(i));
            }
            
            Collections.sort(allMods);
            for (String mod : allMods) {
                if (!listModel.contains(mod)) {
                    listModel.addElement(mod);
                }
            }
            jlstFixedMods.setModel(listModel);
        }
    }//GEN-LAST:event_jbAddFixedModsActionPerformed

    private void jbDelModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDelModsActionPerformed
        //... Remove Fixed Modifications ...//
        List<String> selectedFixedMods = jlstFixedMods.getSelectedValuesList();
        ListModel<String> fixedModsModel = jlstFixedMods.getModel();

        //... Copy elements from the list model ...//
        int size = fixedModsModel.getSize();
        String[] fixedMods = new String[size];
        for (int iI = 0; iI < size; iI++) {
            fixedMods[iI] = fixedModsModel.getElementAt(iI);
        }

        List<String> fixedModsList = new LinkedList<String>(Arrays.asList(fixedMods));

        for (Object mod : selectedFixedMods) {
            fixedModsList.remove(mod);
        }
        if (fixedModsList.isEmpty()) {
            fixedModsList.add("--- none selected ---");
        }
        //... Set new list ...//
        DefaultListModel<String> newFixedModsModel = new DefaultListModel<String>();
        for (String mod : fixedModsList) {
            newFixedModsModel.addElement(mod);
        }
        jlstFixedMods.setModel(newFixedModsModel);
    }//GEN-LAST:event_jbDelModsActionPerformed

    private void jbAddVarModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddVarModsActionPerformed
        //... Add Fixed Modifications ...//
        List<String> selectedVarMods = jlstUnimods.getSelectedValuesList();
        ListModel<String> varModsModel = jlstVarMods.getModel();
        
        //... Adding values ...//
        if (varModsModel.getElementAt(0).equals("--- none selected ---")) {
            DefaultListModel<String> listModel = new DefaultListModel<String>();
            if (selectedVarMods.size() > 0) {
                for (String mod : selectedVarMods) {
                    listModel.addElement(mod);
                }
                jlstVarMods.setModel(listModel);
            }
        }
        else {
            DefaultListModel<String> listModel = new DefaultListModel<String>();

            List<String> allMods = new ArrayList<String>(selectedVarMods);
            for (int i = 0; i < varModsModel.getSize(); i++) {
            	allMods.add(varModsModel.getElementAt(i));
            }
            
            Collections.sort(allMods);
            
            for (String mod : allMods) {
                if (!listModel.contains(mod)) {
                    listModel.addElement(mod);
                }
            }
            jlstVarMods.setModel(listModel);
        }        
    }//GEN-LAST:event_jbAddVarModsActionPerformed

    private void jbDelVarModsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDelVarModsActionPerformed
        //... Remove Fixed Modifications ...//
        List<String> selectedVarMods = jlstVarMods.getSelectedValuesList();
        ListModel<String> varModsModel = jlstVarMods.getModel();

        //... Copy elements from the list model ...//
        int size = varModsModel.getSize();
        String[] varMods = new String[size];
        for (int iI = 0; iI < size; iI++) {
            varMods[iI] = varModsModel.getElementAt(iI);
        }

        List<String> varModsList = new LinkedList<String>(Arrays.asList(varMods));

        for (String mod : selectedVarMods) {
            varModsList.remove(mod);
        }
        if (varModsList.isEmpty()) {
            varModsList.add("--- none selected ---");
        }
        //... Set new list ...//
        DefaultListModel<String> newVarModsModel = new DefaultListModel<String>();
        for (String mod : varModsList) {
            newVarModsModel.addElement(mod);
        }
        jlstVarMods.setModel(newVarModsModel);        
    }//GEN-LAST:event_jbDelVarModsActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jbRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRunActionPerformed
        //... Validate entries ...//
        if((jtDatabaseFile.getText().isEmpty())||(!jtDatabaseFile.getText().toLowerCase().endsWith(".fasta"))){
            JOptionPane.showMessageDialog(this, "Please select a valid fasta file (*.fasta)", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            if((jcbProteinInference.isSelected())&&(!jchbSearchDecoy.isSelected()&&
                                                   (!jtDatabaseFile.getText().toLowerCase().contains("rev")))){
                JOptionPane.showMessageDialog(this, "If you want to perform protein inference please select 'Search decoy' \n" + 
                                                    "from the options above OR use your own decoy database fasta file using the \n" + 
                                                    "prefix/suffix REV in the file name and provide the Regular expression (Regex), e.g. XXX, etc.", "Error", JOptionPane.ERROR_MESSAGE);              
            }else{
                //... Save current values ...//
                bRun = true;
                hmParams.clear();
                                                                                                //...     Spectrum File                 ...//
                hmParams.put("-d", jtDatabaseFile.getText());                                   //... 0 - Database File                 ...//
                                                                                                //...     Output File                   ...//
                hmParams.put("-t", jtMSMSTol.getText()+jcMSMSTol.getSelectedItem().toString()); //... 1 - Precursor Mass Tolerance      ...//
                hmParams.put("-ti", jtErrorRange.getText());                                    //... 2 - Isotope Error Range           ...//
                hmParams.put("-thread", jcThreads.getSelectedItem().toString());                //... 3 - Threads                       ...//
                if (jchbSearchDecoy.isSelected()){                                              //... 4 - TDA                           ...//
                    hmParams.put("-tda", "1");
                }else{
                    hmParams.put("-tda", "0");
                }
                hmParams.put("-m", Integer.toString(jcFragMethod.getSelectedIndex()));          //... 5 - Fragment Method               ...//
                hmParams.put("-inst", Integer.toString(jcInstrument.getSelectedIndex()));       //... 6 - Instrument ID                 ...//
                hmParams.put("-e", Integer.toString(jcEnzyme.getSelectedIndex()));              //... 7 - Enzyme                        ...//
                hmParams.put("-protocol", Integer.toString(jcProtocol.getSelectedIndex()));     //... 8 - Protocol ID                   ...//
                hmParams.put("-ntt", Integer.toString(jcMaxMissedCleavage.getSelectedIndex())); //... 9 - Number of tolerable termini   ...//

                //... 10 - Modification file name ...//            
                ListModel<String> fixedModsModel = jlstFixedMods.getModel();
                ListModel<String> varModsModel = jlstVarMods.getModel();
                int iSizeFixed = fixedModsModel.getSize();
                int iSizeVar = varModsModel.getSize();
                if(iSizeFixed>0||iSizeVar>0){
                    //... Write file ...//
                    FileWriter fstream;
                    try {
                        modificationFile = new File("Mods.txt");
                        fstream = new FileWriter(modificationFile);
                        
                        BufferedWriter out = new BufferedWriter(fstream);            
                        out.write("NumMods="+(iSizeFixed+iSizeVar));
                        out.newLine();
                        for (int iI = 0; iI < iSizeFixed; iI++) {
                            out.write(getValueUniModFixed(fixedModsModel.getElementAt(iI).toString()));
                            out.newLine();                        
                        }
                        for (int iI = 0; iI < iSizeVar; iI++) {
                            out.write(getValueUniModVar(varModsModel.getElementAt(iI).toString()));
                            out.newLine();
                        }                 
                        out.close();                                                               //... 10 - Modifications                ...//
                    } catch (IOException ex) {
                        Logger.getLogger(IdentParamsView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                hmParams.put("-minLength", jtMinPepLen.getText());                              //... 11 - Min peptide length           ...//
                hmParams.put("-maxLength", jtMaxPepLen.getText());                              //... 12 - Max peptide length           ...//
                hmParams.put("-minCharge", jtMinCharge.getText());                              //... 13 - Min charge                   ...//
                hmParams.put("-maxCharge", jtMaxCharge.getText());                              //... 14 - Max charge                   ...//
                hmParams.put("-n", jtSpectraMatches.getText());                                 //... 15 - Num matches per spectrum     ...//
                hmParams.put("-addFeatures", Integer.toString(jcOutput.getSelectedIndex()));    //... 16 - Additional features          ...//
                bProteinInference = jcbProteinInference.isSelected();
                sRegex = jtRegex.getText();
                Window win = SwingUtilities.getWindowAncestor(this);
                if (win != null) {
                    win.dispose();
                }
            }       
        }
    }//GEN-LAST:event_jbRunActionPerformed

    private void jbSetDefaultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSetDefaultsActionPerformed
        
    }//GEN-LAST:event_jbSetDefaultsActionPerformed
    public Map<String, String> getParams() {
        return hmParams;
    }
    
    public File getModificationFile() {
        return modificationFile;
    }
    
    public boolean getRun() {
        return bRun;
    }
    public void setRun(boolean bRun) {
        this.bRun = bRun;
    }    
    public String getRegex() {
        return sRegex;
    }        
    public boolean getProteinInference() {
        return bProteinInference;
    }    
    public String getValueUniModFixed(String sValue){        
        return hmUniModsFixed.get(sValue);
    }
    public String getValueUniModVar(String sValue){        
        return hmUniModsVar.get(sValue);
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup bgMonoAver;
    private JLabel jLabel13;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JButton jbAddDatabaseFile;
    private JButton jbAddFixedMods;
    private JButton jbAddVarMods;
    private JButton jbDelMods;
    private JButton jbDelVarMods;
    private JButton jbRun;
    private JButton jbSave;
    private JButton jbSetDefaults;
    private JComboBox<String> jcEnzyme;
    private JComboBox<String> jcFragMethod;
    private JComboBox<String> jcInstrument;
    private JComboBox<String> jcMSMSTol;
    private JComboBox<String> jcMaxMissedCleavage;
    private JComboBox<String> jcOutput;
    private JComboBox<String> jcProtocol;
    private JComboBox<String> jcThreads;
    private JCheckBox jcbProteinInference;
    private JCheckBox jchbSearchDecoy;
    private JLabel jlCancel;
    private JLabel jlDatabaseFile;
    private JLabel jlEnzyme;
    private JLabel jlErrorRange;
    private JLabel jlFixedMods;
    private JLabel jlFragMethod;
    private JLabel jlInstrument;
    private JLabel jlMSMSTol;
    private JLabel jlMatches;
    private JLabel jlMaxCharge;
    private JLabel jlMaxMissedCleavage;
    private JLabel jlMaxPepLen;
    private JLabel jlMinCharge;
    private JLabel jlMinPepLen;
    private JLabel jlOutput;
    private JLabel jlProtocol;
    private JLabel jlRegex;
    private JLabel jlThreads;
    private JLabel jlUniMod;
    private JLabel jlVarMods;
    private JList<String> jlstFixedMods;
    private JList<String> jlstUnimods;
    private JList<String> jlstVarMods;
    private JTextField jtDatabaseFile;
    private JTextField jtErrorRange;
    private JTextField jtMSMSTol;
    private JTextField jtMaxCharge;
    private JTextField jtMaxPepLen;
    private JTextField jtMinCharge;
    private JTextField jtMinPepLen;
    private JTextField jtRegex;
    private JTextField jtSpectraMatches;
    // End of variables declaration//GEN-END:variables
}
