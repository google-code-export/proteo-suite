/*
 * --------------------------------------------------------------------------
 * QuantParamsView.java
 * --------------------------------------------------------------------------
 * Description:       Parameter settings for the Quantitation
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This form corresponds to the quantitation parameter settings. Users can customised the settings by changing the 
 * default values for each technique, e.g. SILAC, iTRAQ, 15N, etc.
 * @author fgonzalez
 * @param void
 */
public class QuantParamsView extends javax.swing.JPanel {

    //... Class constructor ...//
    public QuantParamsView(ArrayList alRawFiles) {
        //... Initialising components and values ...//        
        initComponents();
        initValues(alRawFiles);
        jtpTechniques.setEnabledAt(0, false);
        jtpTechniques.setSelectedIndex(1);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpTechniques = new javax.swing.JTabbedPane();
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
        jbDefaultValues = new javax.swing.JButton();
        jlIntegrationMethod = new javax.swing.JLabel();
        jcbIntegrationMethod = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtiTRAQParams = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbAdd = new javax.swing.JButton();
        jbRemove = new javax.swing.JButton();
        jlSearchScore = new javax.swing.JLabel();
        jtSearchScore = new javax.swing.JTextField();
        jbSave = new javax.swing.JToggleButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

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
                            .addContainerGap(308, Short.MAX_VALUE))
                        .addGroup(jpSILACLayout.createSequentialGroup()
                            .addComponent(jCheckBox1)
                            .addContainerGap(638, Short.MAX_VALUE))
                        .addGroup(jpSILACLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jToggleButton2)
                            .addContainerGap(488, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSILACLayout.createSequentialGroup()
                            .addGroup(jpSILACLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpSILACLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpSILACLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(22, 22, 22)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpTechniques.addTab("SILAC", jpSILAC);

        jliTraqMinMz.setText("m/z Range (Max):");

        jliTraqMaxMz.setText("m/z Range (Min):");

        jbDefaultValues.setText("Restore to default values");
        jbDefaultValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDefaultValuesActionPerformed(evt);
            }
        });

        jlIntegrationMethod.setText("Integration Method:");

        jcbIntegrationMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SumIntensities", "Area", "Highest" }));

        jtiTRAQParams.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Raw File", "Study Variable", "Label ID", "Label Name", "Value", "-2", "-1", "+1", "+2"
            }
        ));
        jScrollPane2.setViewportView(jtiTRAQParams);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel15.setText("Labels");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel16.setText("Correction Factors");

        jbAdd.setText("Add");
        jbAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddActionPerformed(evt);
            }
        });

        jbRemove.setText("Remove");
        jbRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveActionPerformed(evt);
            }
        });

        jlSearchScore.setText("Search Score:");

        javax.swing.GroupLayout jpiTraqLayout = new javax.swing.GroupLayout(jpiTraq);
        jpiTraq.setLayout(jpiTraqLayout);
        jpiTraqLayout.setHorizontalGroup(
            jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpiTraqLayout.createSequentialGroup()
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpiTraqLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                                .addGap(16, 16, 16)
                                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jbAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 327, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addGap(219, 219, 219))
                            .addComponent(jbDefaultValues)))
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jliTraqMaxMz)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jtiTraqMaxMz, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpiTraqLayout.createSequentialGroup()
                                .addComponent(jliTraqMinMz)
                                .addGap(18, 18, 18)
                                .addComponent(jtiTraqMinMz, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addComponent(jlIntegrationMethod)
                        .addGap(18, 18, 18)
                        .addComponent(jcbIntegrationMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jlSearchScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtSearchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpiTraqLayout.setVerticalGroup(
            jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpiTraqLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtiTraqMinMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jliTraqMinMz)
                    .addComponent(jlIntegrationMethod)
                    .addComponent(jcbIntegrationMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlSearchScore)
                    .addComponent(jtSearchScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jliTraqMaxMz)
                    .addComponent(jtiTraqMaxMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpiTraqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpiTraqLayout.createSequentialGroup()
                        .addComponent(jbAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbRemove)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbDefaultValues)
                .addGap(43, 43, 43))
        );

        jtpTechniques.addTab("iTRAQ/TMT", jpiTraq);

        jbSave.setText("Save");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jLabel13.setText("Note: All settings have been configured by default on the configQuant.xml file located under your ProteoSuite");

        jLabel14.setText(" installation folder. You can make a copy of this file in case you need it in the future.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtpTechniques, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtpTechniques, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addComponent(jbSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    //... Initialise default settings using the config.xml file ...//
    private void initValues(ArrayList alRawFiles)
    {
        //... Validate if config file exists ...//
        boolean exists = (new File("configQuant.xml")).exists();
        if (exists){
            readConfigFile(alRawFiles);
        }
        else
        {
            String sMessage = "The configQuant.xml file was not found, please make sure that this file exists \n";
            sMessage = sMessage + "under your installation folder. ProteoSuite will create a new configQuant.xml file \n";
            sMessage = sMessage + "for you, however you will have to set all parameters manually. You can still close \n";
            sMessage = sMessage + "this window, then copy the configQuant.xml file and restart the program. \n";
            JOptionPane.showMessageDialog(this, sMessage, "Warning", JOptionPane.INFORMATION_MESSAGE);   
        }
    }
    //... Read configuration settings ...//
    private void readConfigFile(ArrayList alRawFiles)
    {          
        //... Fill JTable ...//
        final DefaultTableModel model = new DefaultTableModel();
        jtiTRAQParams.setModel(model);
        model.addColumn("Raw File");
        model.addColumn("Study Variable");
        model.addColumn("Label ID");        
        model.addColumn("Label Name");
        model.addColumn("Value");
        model.addColumn("-2");
        model.addColumn("-1");
        model.addColumn("+1");
        model.addColumn("+2");
        
        //... Read files using XPath xml parser ...//
        try{
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); 
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse("configQuant.xml");
            XPath xpath = XPathFactory.newInstance().newXPath();
            System.out.println("Reading configQuant files ...");
            
            //... Reading mzRanges (min and max) ...//
            XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/minus");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtiTraqMinMz.setText(nodes.item(iI).getTextContent());
            }
            System.out.println("Reading mzRanges ...");
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/plus");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtiTraqMaxMz.setText(nodes.item(iI).getTextContent());
            }
            System.out.println("Reading SeachScore ...");
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtSearchScore.setText(nodes.item(iI).getTextContent());
            }

            //... Check if all raw files have been set up ...//
            System.out.println("Reading raw files ...");
            if (alRawFiles.size()<=0){
                JOptionPane.showMessageDialog(null, "In order to fill the iTRAQ/TMT labels, please select a raw file in the main window.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                for(Object obj:alRawFiles){
                    
                    System.out.println("Checking file ... "+obj.toString());
                    expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='" + obj.toString()  + "']");
                    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    System.out.println("Nodes..."+nodes.getLength());
                    if (nodes.getLength()<=0){
                        //... Insert new record based on the default iTRAQ settings ...//

                        //... Assay Parameters (Labels) ...//
                        expr = xpath.compile("/ProteoSuiteApplication/configSettings/defaultParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='default']/AssayParam");
                        nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                        System.out.println("Nodes in default parameters settings ..."+nodes.getLength());

                        String sRawFile=obj.toString(), sStudyVariable="", sAssayID="", sAssayName = "", sMzValue="";
                        String[] sCorrFactors = new String[4];

                        for (int iI = 0; iI < nodes.getLength(); iI++) {
                            Node node = nodes.item(iI);
                            if (node.getNodeType() == Node.ELEMENT_NODE){

                                Element element = (Element) node;
                                NodeList nodelist = element.getElementsByTagName("StudyVariable");
                                Element element1 = (Element) nodelist.item(0);
                                NodeList fstNm = element1.getChildNodes();                    
                                if (fstNm.getLength()<=0){
                                    sStudyVariable = "";
                                }
                                else{
                                    sStudyVariable = fstNm.item(0).getNodeValue();
                                }

                                Element element8 = (Element) node;
                                NodeList nodelist4 = element8.getElementsByTagName("AssayID");
                                Element element9 = (Element) nodelist4.item(0);
                                NodeList fstNm4 = element9.getChildNodes();
                                if (fstNm4.getLength()<=0){
                                    sAssayID = "";
                                }
                                else{
                                    sAssayID = fstNm4.item(0).getNodeValue();
                                }

                                Element element2 = (Element) node;
                                NodeList nodelist1 = element2.getElementsByTagName("AssayName");
                                Element element3 = (Element) nodelist1.item(0);
                                NodeList fstNm1 = element3.getChildNodes();                    
                                if (fstNm1.getLength()<=0){
                                    sAssayName = "";
                                }
                                else{
                                    sAssayName = fstNm1.item(0).getNodeValue();
                                }

                                Element element4 = (Element) node;
                                NodeList nodelist2 = element4.getElementsByTagName("mzValue");
                                Element element5 = (Element) nodelist2.item(0);
                                NodeList fstNm2 = element5.getChildNodes();                    
                                if (fstNm2.getLength()<=0){
                                    sMzValue = "";
                                }
                                else{
                                    sMzValue = fstNm2.item(0).getNodeValue();
                                }

                                Element element6 = (Element) node;
                                NodeList nodelist3 = element6.getElementsByTagName("factor");
                                for (int iJ = 0; iJ < nodelist3.getLength(); iJ++){
                                    Element element7 = (Element) nodelist3.item(iJ);
                                    NodeList fstNm3 = element7.getChildNodes();
                                    sCorrFactors[iJ] = fstNm3.item(0).getNodeValue();
                                }
                                model.insertRow(model.getRowCount(), new Object[]{
                                                                              sRawFile,
                                                                              sStudyVariable, 
                                                                              sAssayID,
                                                                              sAssayName, 
                                                                              sMzValue, 
                                                                              sCorrFactors[0],
                                                                              sCorrFactors[1],
                                                                              sCorrFactors[2],
                                                                              sCorrFactors[3]
                                });
                            }
                        }                    
                    }                
                    else{ //... Fill from file ...//
                        //... Insert new record based on the config iTRAQ settings ...//
                        System.out.println("Node in config parameters settings ..."+nodes.getLength());
                        
                        //... Assay Parameters (Labels) ...//
                        expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"+obj.toString()+"']/AssayParam");
                        nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                        System.out.println("Assays in node ..."+nodes.getLength());                        

                        String sRawFile=obj.toString(), sStudyVariable="", sAssayID="", sAssayName = "", sMzValue="";
                        String[] sCorrFactors = new String[4];

                        for (int iI = 0; iI < nodes.getLength(); iI++) {
                            Node node = nodes.item(iI);
                            if (node.getNodeType() == Node.ELEMENT_NODE){

                                Element element = (Element) node;
                                NodeList nodelist = element.getElementsByTagName("StudyVariable");
                                Element element1 = (Element) nodelist.item(0);
                                NodeList fstNm = element1.getChildNodes();                    
                                if (fstNm.getLength()<=0){
                                    sStudyVariable = "";
                                }
                                else{
                                    sStudyVariable = fstNm.item(0).getNodeValue();
                                }

                                Element element8 = (Element) node;
                                NodeList nodelist4 = element8.getElementsByTagName("AssayID");
                                Element element9 = (Element) nodelist4.item(0);
                                NodeList fstNm4 = element9.getChildNodes();
                                if (fstNm4.getLength()<=0){
                                    sAssayID = "";
                                }
                                else{
                                    sAssayID = fstNm4.item(0).getNodeValue();
                                }

                                Element element2 = (Element) node;
                                NodeList nodelist1 = element2.getElementsByTagName("AssayName");
                                Element element3 = (Element) nodelist1.item(0);
                                NodeList fstNm1 = element3.getChildNodes();                    
                                if (fstNm1.getLength()<=0){
                                    sAssayName = "";
                                }
                                else{
                                    sAssayName = fstNm1.item(0).getNodeValue();
                                }

                                Element element4 = (Element) node;
                                NodeList nodelist2 = element4.getElementsByTagName("mzValue");
                                Element element5 = (Element) nodelist2.item(0);
                                NodeList fstNm2 = element5.getChildNodes();                    
                                if (fstNm2.getLength()<=0){
                                    sMzValue = "";
                                }
                                else{
                                    sMzValue = fstNm2.item(0).getNodeValue();
                                }
                                
                                Element element6 = (Element) node;
                                NodeList nodelist3 = element6.getElementsByTagName("factor");
                                for (int iJ = 0; iJ < nodelist3.getLength(); iJ++){
                                    Element element7 = (Element) nodelist3.item(iJ);
                                    NodeList fstNm3 = element7.getChildNodes();
                                    sCorrFactors[iJ] = fstNm3.item(0).getNodeValue();
                                }
                                model.insertRow(model.getRowCount(), new Object[]{
                                                                              sRawFile,
                                                                              sStudyVariable, 
                                                                              sAssayID,
                                                                              sAssayName, 
                                                                              sMzValue, 
                                                                              sCorrFactors[0],
                                                                              sCorrFactors[1],
                                                                              sCorrFactors[2],
                                                                              sCorrFactors[3]
                                });
                            }
                        }                                 
                    }
                }                
            }           
        }
        catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        } catch ( XPathExpressionException  e) {
          e.printStackTrace();
        }
        System.out.println("Table elements"+jtiTRAQParams.getRowCount());
        //... Once everything is fill, then we update the values on the configQuant.xml file ...//
        saveConfigFile();
    }    
    //... Saving configuration settings ...//
    private void saveConfigFile()
    {     
        //... Save configuration File ...//
        try{
            FileWriter fstream = new FileWriter("configQuant.xml");
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<ProteoSuiteApplication xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"ProteoSuiteApplication.xsd\" >");
            out.newLine();
            out.write("    <configSettings>");
            out.newLine();
            out.write("		<quantParamSettings>");
            out.newLine();
            out.write("		    <techniques>");
            out.newLine();
            out.write("				<technique id=\"iTRAQ\">");
            out.newLine();
            out.write("					<mzRange>");
            out.newLine();
            out.write("						<minus>" + jtiTraqMinMz.getText() + "</minus>");
            out.newLine();
            out.write("						<plus>" + jtiTraqMaxMz.getText() + "</plus>");
            out.newLine();
            out.write("					</mzRange>");
            out.newLine();
            out.write("					<SearchScore>" + jtSearchScore.getText() + "</SearchScore>");
            out.newLine();
            out.write("					<IntegrationMethod>" + jcbIntegrationMethod.getSelectedItem().toString() + "</IntegrationMethod>");
            out.newLine();
            out.write("					<AssayParamList>");
            out.newLine();
            
            //... Write assay params ...//
            String sChain = "";
            for (int iI=0; iI<jtiTRAQParams.getRowCount(); iI++){
                if(!jtiTRAQParams.getValueAt(iI, 0).toString().equals(sChain)){                   
                    if(!sChain.equals("")){
                        out.write("						</RawFile>");
                        out.newLine();                                        
                    }
                    out.write("						<RawFile id=\""+ jtiTRAQParams.getValueAt(iI, 0).toString() +"\">");
                    out.newLine();
                }
                out.write("							<AssayParam>");
                out.newLine();                
                out.write("								<StudyVariable>" + jtiTRAQParams.getValueAt(iI, 1).toString() + "</StudyVariable>");
                out.newLine();                
                out.write("								<AssayID>" + jtiTRAQParams.getValueAt(iI, 2).toString() + "</AssayID>");                
                out.newLine();                
                out.write("								<AssayName>" + jtiTRAQParams.getValueAt(iI, 3).toString() + "</AssayName>");
                out.newLine();
                out.write("								<mzValue>" + jtiTRAQParams.getValueAt(iI, 4).toString() + "</mzValue>");
                out.newLine();
                out.write("								<CorrectionFactors>");
                out.newLine();        
                out.write("									<factor deltaMass=\"-2\">" + jtiTRAQParams.getValueAt(iI, 5).toString() + "</factor>");
                out.newLine();
                out.write("									<factor deltaMass=\"-1\">" + jtiTRAQParams.getValueAt(iI, 6).toString() + "</factor>");
                out.newLine();
                out.write("									<factor deltaMass=\"+1\">" + jtiTRAQParams.getValueAt(iI, 7).toString() + "</factor>");
                out.newLine();
                out.write("									<factor deltaMass=\"+2\">" + jtiTRAQParams.getValueAt(iI, 8).toString() + "</factor>");
                out.newLine();
                out.write("								</CorrectionFactors>");
                out.newLine();
                out.write("							</AssayParam>");
                out.newLine();                           
                sChain = jtiTRAQParams.getValueAt(iI, 0).toString();
            }
            if(jtiTRAQParams.getRowCount()>0){
                out.write("						</RawFile>");
                out.newLine();
            }            
            
            out.write("					</AssayParamList>");
            out.newLine();
            out.write("				</technique>");
            out.newLine();
            out.write("			</techniques>");
            out.newLine();
            out.write("		</quantParamSettings>");
            out.newLine();
            
            out.write("		<defaultParamSettings>");
            out.newLine();
            out.write("		    <techniques>");
            out.newLine();
            out.write("				<technique id=\"iTRAQ\">");
            out.newLine();
            out.write("					<mzRange>");
            out.newLine();
            out.write("						<minus>0.05</minus>");
            out.newLine();
            out.write("						<plus>0.05</plus>");
            out.newLine();
            out.write("					</mzRange>");
            out.newLine();
            out.write("					<SearchScore>20</SearchScore>");            
            out.newLine();
            out.write("					<IntegrationMethod>SumIntensities</IntegrationMethod>");
            out.newLine();
            out.write("					<AssayParamList>");
            out.newLine();
            out.write("						<RawFile id=\"default\">");
            out.newLine();                        
            out.write("							<AssayParam>");
            out.newLine();
            out.write("								<StudyVariable>GroupA</StudyVariable>");
            out.newLine();
            out.write("								<AssayID>114</AssayID>");
            out.newLine();
            out.write("								<AssayName>iTRAQ4plex-114 reporter fragment</AssayName>");
            out.newLine();
            out.write("								<mzValue>114.11123</mzValue>");
            out.newLine();
            out.write("								<CorrectionFactors>");
            out.newLine();
            out.write("									<factor deltaMass=\"-2\">0</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"-1\">1.0</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+1\">5.9</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+2\">0.2</factor>");
            out.newLine();
            out.write("								</CorrectionFactors>");
            out.newLine();
            out.write("							</AssayParam>");
            out.newLine();
            out.write("							<AssayParam>");       
            out.newLine();            
            out.write("								<StudyVariable>GroupA</StudyVariable>");            
            out.newLine();
            out.write("								<AssayID>115</AssayID>");                        
            out.newLine();            
            out.write("								<AssayName>iTRAQ4plex-115 reporter fragment</AssayName>");
            out.newLine();
            out.write("								<mzValue>115.10826</mzValue>");
            out.newLine();
            out.write("								<CorrectionFactors>");
            out.newLine();
            out.write("									<factor deltaMass=\"-2\">0</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"-1\">2</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+1\">5.6</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+2\">0.1</factor>");
            out.newLine();
            out.write("								</CorrectionFactors>");
            out.newLine();
            out.write("							</AssayParam>");
            out.newLine();
            out.write("							<AssayParam>");
            out.newLine();
            out.write("								<StudyVariable>GroupB</StudyVariable>");            
            out.newLine();
            out.write("								<AssayID>116</AssayID>");                        
            out.newLine();
            out.write("								<AssayName>iTRAQ4plex-116 reporter fragment</AssayName>");
            out.newLine();
            out.write("								<mzValue>116.11162</mzValue>");
            out.newLine();
            out.write("								<CorrectionFactors>");
            out.newLine();
            out.write("									<factor deltaMass=\"-2\">0</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"-1\">3</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+1\">4.5</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+2\">0.1</factor>");
            out.newLine();
            out.write("								</CorrectionFactors>");
            out.newLine();
            out.write("							</AssayParam>");
            out.newLine();
            out.write("							<AssayParam>");
            out.newLine();
            out.write("								<StudyVariable>GroupB</StudyVariable>");            
            out.newLine();
            out.write("								<AssayName>iTRAQ4plex-117, mTRAQ heavy, reporter fragment</AssayName>");
            out.newLine();
            out.write("								<AssayID>117</AssayID>");                        
            out.newLine();
            out.write("								<mzValue>117.11497</mzValue>");
            out.newLine();
            out.write("								<CorrectionFactors>");
            out.newLine();
            out.write("									<factor deltaMass=\"-2\">0.1</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"-1\">4</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+1\">3.5</factor>");
            out.newLine();
            out.write("									<factor deltaMass=\"+2\">0</factor>");
            out.newLine();
            out.write("								</CorrectionFactors>");
            out.newLine();
            out.write("							</AssayParam>");
            out.newLine();
            out.write("						</RawFile>");            
            out.newLine();
            out.write("					</AssayParamList>");
            out.newLine();
            out.write("				</technique>");
            out.newLine();
            out.write("			</techniques>");
            out.newLine();
            out.write("		</defaultParamSettings>");
            out.newLine();
            
            out.write("    </configSettings>");
            out.newLine();
            out.write("</ProteoSuiteApplication>");
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    private void jbDefaultValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDefaultValuesActionPerformed
        //... Loading default values ...//
        
        //... Fill JTable ...//
        final DefaultTableModel model = new DefaultTableModel();
        jtiTRAQParams.setModel(model);
        model.addColumn("Raw File");        
        model.addColumn("Study Variable");
        model.addColumn("Label ID");        
        model.addColumn("Label Name");
        model.addColumn("Value");
        model.addColumn("-2");
        model.addColumn("-1");
        model.addColumn("+1");
        model.addColumn("+2");
        
        //... Read files using XPath xml parser ...//
        try{
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); 
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse("configQuant.xml");
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            //... Reading mzRanges (min and max) ...//
            XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/defaultParamSettings/techniques/technique[@id='iTRAQ']/mzRange/minus");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtiTraqMinMz.setText(nodes.item(iI).getTextContent());
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/defaultParamSettings/techniques/technique[@id='iTRAQ']/mzRange/plus");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtiTraqMaxMz.setText(nodes.item(iI).getTextContent());
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/defaultParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                jtSearchScore.setText(nodes.item(iI).getTextContent());
            }
            
            //... Assay Parameters (Labels) ...//
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/defaultParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='default']/AssayParam");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
            String sRawFile="", sStudyVariable="", sAssayID = "", sAssayName = "", sMzValue="";
            String[] sCorrFactors = new String[4];            
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                Node node = nodes.item(iI);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    NodeList nodelist = element.getElementsByTagName("StudyVariable");
                    Element element1 = (Element) nodelist.item(0);
                    NodeList fstNm = element1.getChildNodes();                    
                    if (fstNm.getLength()<=0){
                        sStudyVariable = "";
                    }
                    else{
                        sStudyVariable = fstNm.item(0).getNodeValue();
                    }                    
                    
                    Element element8 = (Element) node;
                    NodeList nodelist4 = element8.getElementsByTagName("AssayID");
                    Element element9 = (Element) nodelist4.item(0);
                    NodeList fstNm4 = element9.getChildNodes();
                    if (fstNm4.getLength()<=0){
                        sAssayID = "";
                    }
                    else{
                        sAssayID = fstNm4.item(0).getNodeValue();
                    }
                    
                    Element element2 = (Element) node;
                    NodeList nodelist1 = element2.getElementsByTagName("AssayName");
                    Element element3 = (Element) nodelist1.item(0);
                    NodeList fstNm1 = element3.getChildNodes();                    
                    if (fstNm1.getLength()<=0){
                        sAssayName = "";
                    }
                    else{
                        sAssayName = fstNm1.item(0).getNodeValue();
                    }
                    
                    Element element4 = (Element) node;
                    NodeList nodelist2 = element4.getElementsByTagName("mzValue");
                    Element element5 = (Element) nodelist2.item(0);
                    NodeList fstNm2 = element5.getChildNodes();                    
                    if (fstNm2.getLength()<=0){
                        sMzValue = "";
                    }
                    else{
                        sMzValue = fstNm2.item(0).getNodeValue();
                    }
                    
                    Element element6 = (Element) node;
                    NodeList nodelist3 = element6.getElementsByTagName("factor");
                    for (int iJ = 0; iJ < nodelist3.getLength(); iJ++)
                    {
                        Element element7 = (Element) nodelist3.item(iJ);
                        NodeList fstNm3 = element7.getChildNodes();
                        sCorrFactors[iJ] = fstNm3.item(0).getNodeValue();
                    }
                    model.insertRow(model.getRowCount(), new Object[]{
                                                                  "default",
                                                                  sStudyVariable, 
                                                                  sAssayID,
                                                                  sAssayName, 
                                                                  sMzValue, 
                                                                  sCorrFactors[0],
                                                                  sCorrFactors[1],
                                                                  sCorrFactors[2],
                                                                  sCorrFactors[3]
                    });
                }
            }
        }
        catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        } catch ( XPathExpressionException  e) {
          e.printStackTrace();
        }
    }//GEN-LAST:event_jbDefaultValuesActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        //... Save changes ...//
        saveConfigFile();
        JOptionPane.showMessageDialog(this, "Changes updated successfully!", "ProteoSuite", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jbRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveActionPerformed
        //... Remove label ...//
        if (jtiTRAQParams.getSelectedRow()<0)
        {
            JOptionPane.showMessageDialog(this, "Please select the label to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            DefaultTableModel model = (DefaultTableModel) jtiTRAQParams.getModel();
            model.removeRow(jtiTRAQParams.getSelectedRow());
            jtiTRAQParams.setModel(model);
        }
    }//GEN-LAST:event_jbRemoveActionPerformed

    private void jbAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddActionPerformed
        //... Adding a new row ...//
        DefaultTableModel model = (DefaultTableModel) jtiTRAQParams.getModel();
        model.insertRow(model.getRowCount(), new Object[]{"", "", "", "", "", "", "", "", ""});
        jtiTRAQParams.setModel(model);
    }//GEN-LAST:event_jbAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbDefaultValues;
    private javax.swing.JButton jbRemove;
    private javax.swing.JToggleButton jbSave;
    private javax.swing.JComboBox jcbIntegrationMethod;
    private javax.swing.JLabel jlIntegrationMethod;
    private javax.swing.JLabel jlSearchScore;
    private javax.swing.JLabel jliTraqMaxMz;
    private javax.swing.JLabel jliTraqMinMz;
    private javax.swing.JPanel jpSILAC;
    private javax.swing.JPanel jpiTraq;
    private javax.swing.JTextField jtSearchScore;
    private javax.swing.JTable jtiTRAQParams;
    private javax.swing.JTextField jtiTraqMaxMz;
    private javax.swing.JTextField jtiTraqMinMz;
    private javax.swing.JTabbedPane jtpTechniques;
    // End of variables declaration//GEN-END:variables
}
