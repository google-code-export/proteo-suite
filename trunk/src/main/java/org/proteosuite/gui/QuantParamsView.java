/*
 * --------------------------------------------------------------------------
 * QuantParamsView.java
 * --------------------------------------------------------------------------
 * Description:       Parameter settings for the Quantitation
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

/**
 * This form corresponds to the quantitation parameter settings. Users can customised the settings by changing the 
 * default values for each technique, e.g. SILAC, iTRAQ, 15N, etc.
 * @author fgonzalez
 * @param void
 */
public class QuantParamsView extends javax.swing.JPanel {

    //... Class constructor ...//
    public QuantParamsView() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpSILAC = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jpiTraq = new javax.swing.JPanel();
        jliTraqMinMz = new javax.swing.JLabel();
        jtiTraqMinMz = new javax.swing.JTextField();
        jliTraqMaxMz = new javax.swing.JLabel();
        jtiTraqMaxMz = new javax.swing.JTextField();
        jlReporterIons = new javax.swing.JLabel();
        jlFourPlex = new javax.swing.JLabel();
        jlEightPlex = new javax.swing.JLabel();
        jlLabel114 = new javax.swing.JLabel();
        jlLabel115 = new javax.swing.JLabel();
        jlLabel116 = new javax.swing.JLabel();
        jlLabel117 = new javax.swing.JLabel();
        jtLabel114 = new javax.swing.JTextField();
        jtLabel115 = new javax.swing.JTextField();
        jtLabel116 = new javax.swing.JTextField();
        jtLabel117 = new javax.swing.JTextField();
        jlLabel113 = new javax.swing.JLabel();
        jtLabel113 = new javax.swing.JTextField();
        jlLabel118 = new javax.swing.JLabel();
        jtLabel118 = new javax.swing.JTextField();
        jlLabel119 = new javax.swing.JLabel();
        jtLabel119 = new javax.swing.JTextField();
        jlCorrectionFactors = new javax.swing.JLabel();
        jl2Neg4plex = new javax.swing.JLabel();
        jl1Neg4plex = new javax.swing.JLabel();
        jtCorr1Label114 = new javax.swing.JTextField();
        jl1Pos4plex = new javax.swing.JLabel();
        jlLabel121 = new javax.swing.JLabel();
        jtLabel121 = new javax.swing.JTextField();
        jl2Pos4plex = new javax.swing.JLabel();
        jtCorr2Label114 = new javax.swing.JTextField();
        jtCorr3Label114 = new javax.swing.JTextField();
        jtCorr4Label114 = new javax.swing.JTextField();
        jtCorr1Label115 = new javax.swing.JTextField();
        jtCorr2Label115 = new javax.swing.JTextField();
        jtCorr3Label115 = new javax.swing.JTextField();
        jtCorr4Label115 = new javax.swing.JTextField();
        jtCorr1Label116 = new javax.swing.JTextField();
        jtCorr2Label116 = new javax.swing.JTextField();
        jtCorr3Label116 = new javax.swing.JTextField();
        jtCorr4Label116 = new javax.swing.JTextField();
        jtCorr1Label117 = new javax.swing.JTextField();
        jtCorr2Label117 = new javax.swing.JTextField();
        jtCorr3Label117 = new javax.swing.JTextField();
        jtCorr4Label117 = new javax.swing.JTextField();
        jtCorr1Label113 = new javax.swing.JTextField();
        jtCorr2Label113 = new javax.swing.JTextField();
        jtCorr3Label113 = new javax.swing.JTextField();
        jtCorr4Label113 = new javax.swing.JTextField();
        jtCorr1Label118 = new javax.swing.JTextField();
        jtCorr2Label118 = new javax.swing.JTextField();
        jtCorr3Label118 = new javax.swing.JTextField();
        jtCorr4Label118 = new javax.swing.JTextField();
        jtCorr1Label119 = new javax.swing.JTextField();
        jtCorr2Label119 = new javax.swing.JTextField();
        jtCorr3Label119 = new javax.swing.JTextField();
        jtCorr4Label119 = new javax.swing.JTextField();
        jtCorr1Label121 = new javax.swing.JTextField();
        jtCorr2Label121 = new javax.swing.JTextField();
        jtCorr3Label121 = new javax.swing.JTextField();
        jtCorr4Label121 = new javax.swing.JTextField();
        jl2Neg8plex = new javax.swing.JLabel();
        jl1Neg8plex = new javax.swing.JLabel();
        jl1Pos8plex = new javax.swing.JLabel();
        jl2Pos8plex = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jbDefaultValues = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        jLabel6.setText("N-terminus shift:");

        jLabel5.setText("C-terminus shift:");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "R - 10.008269", "K - 8.014199" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel7.setText("Amino acid:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "R", "N", "D", "C", "E", "Q", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" }));

        jToggleButton2.setText("Add");

        jLabel10.setText("daltons");

        jLabel11.setText("daltons");

        jLabel1.setText("Mass type:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monoisotopic", "Average" }));

        jLabel2.setText("Incorporation rate:");

        jTextField1.setText("0.985");

        jLabel12.setText("(0-100%)");

        jLabel3.setText("RT window:");

        jTextField2.setText("10");

        jLabel8.setText("sec");

        jLabel4.setText("MZ tolerance (+/-) :");

        jTextField3.setText("0.1");

        jLabel9.setText("daltons");

        jCheckBox1.setText("Normalised");

        javax.swing.GroupLayout jpSILACLayout = new javax.swing.GroupLayout(jpSILAC);
        jpSILAC.setLayout(jpSILACLayout);
        jpSILACLayout.setHorizontalGroup(
            jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSILACLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSILACLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpSILACLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel8)
                            .addGap(45, 45, 45)
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(22, 22, 22)
                            .addComponent(jLabel9)
                            .addContainerGap(95, Short.MAX_VALUE))
                        .addGroup(jpSILACLayout.createSequentialGroup()
                            .addComponent(jCheckBox1)
                            .addContainerGap(425, Short.MAX_VALUE))
                        .addGroup(jpSILACLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jToggleButton2)
                            .addContainerGap(275, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSILACLayout.createSequentialGroup()
                            .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpSILACLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpSILACLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(22, 22, 22)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12))
                            .addGap(217, 217, 217)))))
        );
        jpSILACLayout.setVerticalGroup(
            jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSILACLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SILAC", jpSILAC);

        jliTraqMinMz.setText("Min m/z:");

        jtiTraqMinMz.setText("0.05");

        jliTraqMaxMz.setText("Max m/z:");

        jtiTraqMaxMz.setText("0.05");

        jlReporterIons.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlReporterIons.setText("Reporter Ions");

        jlFourPlex.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlFourPlex.setForeground(new java.awt.Color(102, 102, 102));
        jlFourPlex.setText("a) 4-plex");

        jlEightPlex.setBackground(new java.awt.Color(102, 102, 102));
        jlEightPlex.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlEightPlex.setForeground(new java.awt.Color(102, 102, 102));
        jlEightPlex.setText("b) 8-plex");

        jlLabel114.setText("Label 114:");

        jlLabel115.setText("Label 115:");

        jlLabel116.setText("Label 116:");

        jlLabel117.setText("Label 117:");

        jtLabel114.setText("114.11123");
        jtLabel114.setPreferredSize(new java.awt.Dimension(60, 20));

        jtLabel115.setText("115.10826");
        jtLabel115.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel115.setPreferredSize(new java.awt.Dimension(60, 20));

        jtLabel116.setText("116.11162");
        jtLabel116.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel116.setPreferredSize(new java.awt.Dimension(60, 20));

        jtLabel117.setText("117.11497");
        jtLabel117.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel117.setPreferredSize(new java.awt.Dimension(60, 20));

        jlLabel113.setText("Label 113:");

        jtLabel113.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel113.setPreferredSize(new java.awt.Dimension(60, 20));

        jlLabel118.setText("Label 118:");

        jtLabel118.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel118.setPreferredSize(new java.awt.Dimension(60, 20));

        jlLabel119.setText("Label 119:");

        jtLabel119.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel119.setPreferredSize(new java.awt.Dimension(60, 20));

        jlCorrectionFactors.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlCorrectionFactors.setText("Correction factors");

        jl2Neg4plex.setText("-2");

        jl1Neg4plex.setText("-1");

        jtCorr1Label114.setText("0");
        jtCorr1Label114.setMinimumSize(new java.awt.Dimension(40, 20));
        jtCorr1Label114.setPreferredSize(new java.awt.Dimension(40, 20));

        jl1Pos4plex.setText("+1");

        jlLabel121.setText("Label 121:");

        jtLabel121.setMinimumSize(new java.awt.Dimension(60, 20));
        jtLabel121.setPreferredSize(new java.awt.Dimension(60, 20));

        jl2Pos4plex.setText("+2");

        jtCorr2Label114.setText("1.0");
        jtCorr2Label114.setMinimumSize(new java.awt.Dimension(40, 20));
        jtCorr2Label114.setPreferredSize(new java.awt.Dimension(40, 20));

        jtCorr3Label114.setText("5.9");
        jtCorr3Label114.setMinimumSize(new java.awt.Dimension(40, 20));
        jtCorr3Label114.setPreferredSize(new java.awt.Dimension(40, 20));

        jtCorr4Label114.setText("0.2");
        jtCorr4Label114.setMinimumSize(new java.awt.Dimension(40, 20));
        jtCorr4Label114.setPreferredSize(new java.awt.Dimension(40, 20));

        jtCorr1Label115.setText("0");

        jtCorr2Label115.setText("2.0");

        jtCorr3Label115.setText("5.6");

        jtCorr4Label115.setText("0.1");

        jtCorr1Label116.setText("0");

        jtCorr2Label116.setText("3.0");

        jtCorr3Label116.setText("4.5");

        jtCorr4Label116.setText("0.1");

        jtCorr1Label117.setText("0.1");

        jtCorr2Label117.setText("4.0");

        jtCorr3Label117.setText("3.5");

        jtCorr4Label117.setText("0");

        jl2Neg8plex.setText("-2");

        jl1Neg8plex.setText("-1");

        jl1Pos8plex.setText("+1");

        jl2Pos8plex.setText("+2");

        jbDefaultValues.setText("Default values");
        jbDefaultValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDefaultValuesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpiTraqLayout = new javax.swing.GroupLayout(jpiTraq);
        jpiTraq.setLayout(jpiTraqLayout);
        jpiTraqLayout.setHorizontalGroup(
            jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpiTraqLayout.createSequentialGroup()
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jliTraqMinMz)
                        .addGap(18, 18, 18)
                        .addComponent(jtiTraqMinMz, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jliTraqMaxMz)
                        .addGap(18, 18, 18)
                        .addComponent(jtiTraqMaxMz, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jlReporterIons)
                        .addGap(191, 191, 191)
                        .addComponent(jlCorrectionFactors))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel113)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel118)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel119)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel121)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpiTraqLayout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jtCorr1Label121, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr2Label121, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr3Label121, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpiTraqLayout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jtCorr1Label119, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr2Label119, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr3Label119, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpiTraqLayout.createSequentialGroup()
                                    .addGap(100, 100, 100)
                                    .addComponent(jtCorr1Label118, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(14, 14, 14)
                                    .addComponent(jtCorr2Label118, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(14, 14, 14)
                                    .addComponent(jtCorr3Label118, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpiTraqLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpiTraqLayout.createSequentialGroup()
                                        .addComponent(jtCorr1Label113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr2Label113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jtCorr3Label113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpiTraqLayout.createSequentialGroup()
                                        .addComponent(jl2Neg8plex)
                                        .addGap(30, 30, 30)
                                        .addComponent(jl1Neg8plex)
                                        .addGap(30, 30, 30)
                                        .addComponent(jl1Pos8plex)))
                                .addGap(10, 10, 10)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl2Pos8plex)
                                    .addComponent(jtCorr4Label113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label121, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label118, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label119, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbDefaultValues)
                            .addComponent(jlEightPlex)))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel115)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jtCorr1Label115, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr2Label115, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr3Label115, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel116)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jtCorr1Label116, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr2Label116, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr3Label116, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel117)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jtCorr1Label117, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr2Label117, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jtCorr3Label117, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlLabel114)
                                .addGap(20, 20, 20)
                                .addComponent(jtLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtCorr1Label114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl2Neg4plex))
                                .addGap(14, 14, 14)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtCorr2Label114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl1Neg4plex))
                                .addGap(14, 14, 14)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl1Pos4plex)
                                    .addComponent(jtCorr3Label114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpiTraqLayout.createSequentialGroup()
                                .addComponent(jlFourPlex)
                                .addGap(325, 325, 325)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl2Pos4plex)
                                    .addComponent(jtCorr4Label114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label115, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label116, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCorr4Label117, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(388, 388, 388))
        );
        jpiTraqLayout.setVerticalGroup(
            jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpiTraqLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtiTraqMinMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jliTraqMinMz))
                    .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jliTraqMaxMz)
                        .addComponent(jtiTraqMaxMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlReporterIons)
                    .addComponent(jlCorrectionFactors))
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlFourPlex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jl2Neg4plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jl1Neg4plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jl1Pos4plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr1Label114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr2Label114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr3Label114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr1Label115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr2Label115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr3Label115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr1Label116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr2Label116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr3Label116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr1Label117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr2Label117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr3Label117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addComponent(jl2Pos4plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label114, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label115, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlEightPlex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jl2Neg8plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jl1Neg8plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jl1Pos8plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl2Pos8plex, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr1Label113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr2Label113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCorr3Label113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr1Label118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr2Label118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr3Label118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr1Label119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr2Label119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr3Label119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr1Label121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr2Label121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jtCorr3Label121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addComponent(jtCorr4Label113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtCorr4Label121, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jbDefaultValues)
                .addContainerGap())
        );

        jTabbedPane1.addTab("iTRAQ", jpiTraq);

        jToggleButton1.setText("Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbDefaultValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDefaultValuesActionPerformed
        //... Loading default values ...//
        jtiTraqMinMz.setText("0.05");
        jtiTraqMaxMz.setText("0.05");
        
        jtLabel114.setText("114.11123");
        jtLabel115.setText("115.10826");
        jtLabel116.setText("116.11162");
        jtLabel117.setText("117.11497");
        
        jtLabel113.setText("");
        jtLabel118.setText("");
        jtLabel119.setText("");
        jtLabel121.setText("");
        
        jtCorr1Label114.setText("0");
        jtCorr2Label114.setText("1.0");
        jtCorr3Label114.setText("5.9");
        jtCorr4Label114.setText("0.2");
        
        jtCorr1Label115.setText("0");
        jtCorr2Label115.setText("2.0");
        jtCorr3Label115.setText("5.6");
        jtCorr4Label115.setText("0.1");
        
        jtCorr1Label116.setText("0");
        jtCorr2Label116.setText("3.0");
        jtCorr3Label116.setText("4.5");
        jtCorr4Label116.setText("0.1");
        
        jtCorr1Label117.setText("0.1");
        jtCorr2Label117.setText("4.0");
        jtCorr3Label117.setText("3.5");
        jtCorr4Label117.setText("0");        
    }//GEN-LAST:event_jbDefaultValuesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JButton jbDefaultValues;
    private javax.swing.JLabel jl1Neg4plex;
    private javax.swing.JLabel jl1Neg8plex;
    private javax.swing.JLabel jl1Pos4plex;
    private javax.swing.JLabel jl1Pos8plex;
    private javax.swing.JLabel jl2Neg4plex;
    private javax.swing.JLabel jl2Neg8plex;
    private javax.swing.JLabel jl2Pos4plex;
    private javax.swing.JLabel jl2Pos8plex;
    private javax.swing.JLabel jlCorrectionFactors;
    private javax.swing.JLabel jlEightPlex;
    private javax.swing.JLabel jlFourPlex;
    private javax.swing.JLabel jlLabel113;
    private javax.swing.JLabel jlLabel114;
    private javax.swing.JLabel jlLabel115;
    private javax.swing.JLabel jlLabel116;
    private javax.swing.JLabel jlLabel117;
    private javax.swing.JLabel jlLabel118;
    private javax.swing.JLabel jlLabel119;
    private javax.swing.JLabel jlLabel121;
    private javax.swing.JLabel jlReporterIons;
    private javax.swing.JLabel jliTraqMaxMz;
    private javax.swing.JLabel jliTraqMinMz;
    private javax.swing.JPanel jpSILAC;
    private javax.swing.JPanel jpiTraq;
    private javax.swing.JTextField jtCorr1Label113;
    private javax.swing.JTextField jtCorr1Label114;
    private javax.swing.JTextField jtCorr1Label115;
    private javax.swing.JTextField jtCorr1Label116;
    private javax.swing.JTextField jtCorr1Label117;
    private javax.swing.JTextField jtCorr1Label118;
    private javax.swing.JTextField jtCorr1Label119;
    private javax.swing.JTextField jtCorr1Label121;
    private javax.swing.JTextField jtCorr2Label113;
    private javax.swing.JTextField jtCorr2Label114;
    private javax.swing.JTextField jtCorr2Label115;
    private javax.swing.JTextField jtCorr2Label116;
    private javax.swing.JTextField jtCorr2Label117;
    private javax.swing.JTextField jtCorr2Label118;
    private javax.swing.JTextField jtCorr2Label119;
    private javax.swing.JTextField jtCorr2Label121;
    private javax.swing.JTextField jtCorr3Label113;
    private javax.swing.JTextField jtCorr3Label114;
    private javax.swing.JTextField jtCorr3Label115;
    private javax.swing.JTextField jtCorr3Label116;
    private javax.swing.JTextField jtCorr3Label117;
    private javax.swing.JTextField jtCorr3Label118;
    private javax.swing.JTextField jtCorr3Label119;
    private javax.swing.JTextField jtCorr3Label121;
    private javax.swing.JTextField jtCorr4Label113;
    private javax.swing.JTextField jtCorr4Label114;
    private javax.swing.JTextField jtCorr4Label115;
    private javax.swing.JTextField jtCorr4Label116;
    private javax.swing.JTextField jtCorr4Label117;
    private javax.swing.JTextField jtCorr4Label118;
    private javax.swing.JTextField jtCorr4Label119;
    private javax.swing.JTextField jtCorr4Label121;
    private javax.swing.JTextField jtLabel113;
    private javax.swing.JTextField jtLabel114;
    private javax.swing.JTextField jtLabel115;
    private javax.swing.JTextField jtLabel116;
    private javax.swing.JTextField jtLabel117;
    private javax.swing.JTextField jtLabel118;
    private javax.swing.JTextField jtLabel119;
    private javax.swing.JTextField jtLabel121;
    private javax.swing.JTextField jtiTraqMaxMz;
    private javax.swing.JTextField jtiTraqMinMz;
    // End of variables declaration//GEN-END:variables
}
