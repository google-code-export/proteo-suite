
/*
 * ViewChartGUI.java
 *
 * Created on Aug 28, 2009, 1:05:04 PM
 */
package org.proteosuite.external;

import org.proteosuite.external.IPC;
import org.proteosuite.external.Peak;

import static org.proteosuite.external.IPC.*;

import org.proteosuite.external.Plot;
import org.proteosuite.external.Plot.FixedRange;
import org.proteosuite.external.Plot.RangeChangeListener;
import org.proteosuite.external.ResultsSerializer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ExtensionFileFilter;

/**
 *
 * @author D3Y241
 */
public class ViewChartGUI
        extends JFrame {

    static JFreeChart makeDefaultChart() {
        return null;

    }
    private Map<String, Integer> currentMods = new HashMap<String, Integer>();
    private Results currentResults = null;
    private String currentTitle = null;
    //private IsotopePatternStorage isp;
    private IPC ipc;
    private List<Future<Results>> futureResults = new LinkedList<Future<Results>>();
    private Options ipcOptionsInProcess = null;
    private GridBagLayout topPaneLayout = new GridBagLayout();
    private ResultsSerializer rs;
    private ExecutorService executor;
    private Properties props;
    private final static File propsFile = new File("properties.ini");
    final static String nl = System.getProperty("line.separator");
    private static String version;
    private Image icon = getToolkit().getImage(getClass().getResource("images/icon.png"));
    private ResultsDialog resultsDialog = null;
    private ChangeListener remakeChartChangeListener = new ChangeListener() {

        public void stateChanged(ChangeEvent e) {
            remakeChart();
        }
    };

    public ViewChartGUI(String sPeptide, String sCharge, String sResol) {
        loadProperties(sPeptide, sCharge, sResol);

        ipc = new IPC();

        initComponents();
        addListeners();
        //      props.

        rs = new ResultsSerializer(getCurrentCacheDir());
        //isp = new IsotopePatternStorage(IsotopePatternStorage.shareIPC);



        executor = Executors.newSingleThreadExecutor();

        double c13 = ipc.findElement(null, "C").last().getP();
        c13Spin.setValue(c13);        
        remakeChart();
        
    }

    private void loadProperties(String sPeptide, String sCharge, String sResol) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("svn-revision.txt")));
            version = reader.readLine();
        } catch (Exception ioe) {
            //ioe.printStackTrace();
        }

        props = new Properties();

        //Default properties
        props.setProperty("Resolution", sResol);
        props.setProperty("NormalProfile", "True");
        props.setProperty("Peptide", sPeptide);
        props.setProperty("Charge", sCharge);
        props.setProperty("CacheDir", ResultsSerializer.getIPCTempDir().getAbsolutePath());

//        if (propsFile.exists()) {
//            try {
//                FileReader reader = new FileReader(propsFile);
//                props.load(reader);
//                reader.close();
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            }
//            if (props.getProperty("Peptide").equals("")) {
//                props.setProperty("Peptide", "PEPTIDE");
//            }
//        }
    }

    private void close() {
//        if (props != null) {
//            try {
//                FileWriter writer = new FileWriter(propsFile);
//                props.store(writer, "IPC Properties");
//                writer.close();
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            }
//        }
        dispose();
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        peptideTF.getDocument().addDocumentListener(new DocumentListener() {

            void updatePeptide() {
                setCurrentPeptide(peptideTF.getText().trim());
            }

            public void insertUpdate(DocumentEvent e) {
                updatePeptide();
            }

            public void removeUpdate(DocumentEvent e) {
                updatePeptide();
            }

            public void changedUpdate(DocumentEvent e) {
                updatePeptide();
            }
        });
        chargeSpinner.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                setCurrentCharge((Integer) chargeSpinner.getValue());
            }
        });

        n15Spin.addChangeListener(remakeChartChangeListener);
        c13Spin.addChangeListener(remakeChartChangeListener);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        topPane = new JPanel();
        peptideTF = new JTextField();
        chargeSpinner = new JSpinner();
        addModificationL = new JLabel();
        n15Panel = new JPanel();
        n15L = new JLabel();
        n15ResetBut = new JButton();
        n15Spin = new JSpinner();
        n15Spin.setModel(new SpinnerNumberModel(ipc.findElement(null, "N").last().getP(), 0.0d, 1.0d, 0.001d));
        n15Spin.setEditor(new JSpinner.NumberEditor(n15Spin, "##0.000%"));
        c13L = new JLabel();
        c13ResetBut = new JButton();
        c13Spin = new JSpinner();
        n15Spin.setModel(new SpinnerNumberModel(ipc.findElement(null, "N").last().getP(), 0.0d, 1.0d, 0.001d));
        n15Spin.setEditor(new JSpinner.NumberEditor(n15Spin, "##0.000%"));
        bottomPane = new ChartPanel(makeDefaultChart(), true);
        ((ChartPanel)bottomPane).setMaximumDrawHeight(99999);
        ((ChartPanel)bottomPane).setMaximumDrawWidth(99999);
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        readFile = new JMenuItem();
        saveMenuItem = new JMenuItem();
        exitMI = new JMenuItem();
        editMenu = new JMenu();
        normalProfileCB = new JCheckBoxMenuItem();
        tableCheckBoxMenuItem = new JCheckBoxMenuItem();
        clearModsMenuItem = new JMenuItem();
        setResolutionMenuItem = new JMenuItem();
        setCacheLocationMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Isotope Pattern Calculator");
        getContentPane().setLayout(new GridBagLayout());

        topPane.setLayout(new GridBagLayout());
        topPane.setLayout(topPaneLayout);

        peptideTF.setText(getCurrentPeptide());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        topPane.add(peptideTF, gridBagConstraints);

        chargeSpinner.setModel(new SpinnerNumberModel(getCurrentCharge(), 1, 99, 1));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 5, 0, 9);
        topPane.add(chargeSpinner, gridBagConstraints);

        addModificationL.setForeground(new Color(0, 51, 255));
        addModificationL.setText("Add Modification...");
        addModificationL.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 51, 255)));
        addModificationL.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                addModificationLMouseClicked(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 10, 0, 10);
        topPane.add(addModificationL, gridBagConstraints);

        n15Panel.setLayout(new GridBagLayout());

        n15L.setText("N15 Percentage: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        n15Panel.add(n15L, gridBagConstraints);

        n15ResetBut.setText("Reset");
        n15ResetBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                n15ResetButActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        n15Panel.add(n15ResetBut, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        n15Panel.add(n15Spin, gridBagConstraints);

        c13L.setText("C13 Percentage: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        n15Panel.add(c13L, gridBagConstraints);

        c13ResetBut.setText("Reset");
        c13ResetBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                c13ResetButActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 5, 0, 0);
        n15Panel.add(c13ResetBut, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        n15Panel.add(c13Spin, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 10, 0, 10);
        topPane.add(n15Panel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        getContentPane().add(topPane, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(bottomPane, gridBagConstraints);

        fileMenu.setText("File");

        readFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        readFile.setText("Read Peptides From File");
        readFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                readFileActionPerformed(evt);
            }
        });
        fileMenu.add(readFile);

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save This Peptide to Text File");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        exitMI.setText("Exit");
        exitMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMIActionPerformed(evt);
            }
        });
        fileMenu.add(exitMI);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        editMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editMenuActionPerformed(evt);
            }
        });

        normalProfileCB.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
        normalProfileCB.setSelected(true);
        normalProfileCB.setText("Show Normal Peak Profile");
        normalProfileCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                normalProfileCBActionPerformed(evt);
            }
        });
        editMenu.add(normalProfileCB);

        tableCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
        tableCheckBoxMenuItem.setText("Show Table of Results");
        tableCheckBoxMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tableCheckBoxMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(tableCheckBoxMenuItem);

        clearModsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
        clearModsMenuItem.setText("Clear Modifications");
        clearModsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clearModsMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(clearModsMenuItem);

        setResolutionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
        setResolutionMenuItem.setText("Set Resolution");
        setResolutionMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setResolutionMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(setResolutionMenuItem);

        setCacheLocationMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
        setCacheLocationMenuItem.setText("Set Cache Location");
        setCacheLocationMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setCacheLocationMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(setCacheLocationMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    List<ModPanel> modPanels = new ArrayList<ModPanel>();
    private void addModificationLMouseClicked(MouseEvent evt) {//GEN-FIRST:event_addModificationLMouseClicked
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = modPanels.size() + 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new Insets(5, 10, 0, 10);

        final ModPanel newMod = new ModPanel(gridBagConstraints);

        newMod.removeBut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                int oldIndex = newMod.getIndex();
                //System.out.println("Trying to remove modPanel #" + oldIndex);
                topPane.remove(newMod);
                for (int i = oldIndex + 1; i
                        < modPanels.size(); i++) {
                    modPanels.get(i).decrementIndex();
                    topPaneLayout.setConstraints(modPanels.get(i), modPanels.get(i).getGbc());
                }

                modPanels.remove(newMod);
                topPane.revalidate();
                setCurrentMods(determineMods(modPanels, getCurrentPeptide()));

            }
        });

        newMod.addChangedListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setCurrentMods(determineMods(modPanels, getCurrentPeptide()));
            }
        });


        topPane.add(newMod, gridBagConstraints);
        topPane.revalidate();
        modPanels.add(newMod);




    }//GEN-LAST:event_addModificationLMouseClicked

    void clearMods() {
        List<ActionListener> als = new LinkedList<ActionListener>();
        for (ModPanel mp : modPanels) {
            for (ActionListener al : mp.removeBut.getActionListeners()) {
                als.add(al);
            }

        }
        for (ActionListener al : als) {
            al.actionPerformed(null);
        }

    }

    private void exitMIActionPerformed(ActionEvent evt) {//GEN-FIRST:event_exitMIActionPerformed
        close();
    }//GEN-LAST:event_exitMIActionPerformed

    private void editMenuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_editMenuActionPerformed
    }//GEN-LAST:event_editMenuActionPerformed

    private void normalProfileCBActionPerformed(ActionEvent evt) {//GEN-FIRST:event_normalProfileCBActionPerformed
        setCurrentNormalProfile(normalProfileCB.isSelected());

    }//GEN-LAST:event_normalProfileCBActionPerformed

    private void readFileActionPerformed(ActionEvent evt) {//GEN-FIRST:event_readFileActionPerformed
        ReadSearchResultsDialog srd = new ReadSearchResultsDialog(this);
        srd.setVisible(true);
    }//GEN-LAST:event_readFileActionPerformed

    private void clearModsMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_clearModsMenuItemActionPerformed
        clearMods();
    }//GEN-LAST:event_clearModsMenuItemActionPerformed

    private void setResolutionMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_setResolutionMenuItemActionPerformed
        JSpinner resSpin = new JSpinner(new SpinnerNumberModel(getCurrentResolution(), 1, 10000000, 1000));

        int response = JOptionPane.showOptionDialog(this,
                new Object[]{"Please Select Desired Resolution (FWHM)", resSpin},
                "Resolution",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);

        if (response == JOptionPane.OK_OPTION) {
            setCurrentResolution(((Number) resSpin.getValue()).longValue());


        }
    }//GEN-LAST:event_setResolutionMenuItemActionPerformed

    private void setCacheLocationMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_setCacheLocationMenuItemActionPerformed
        String path = getCurrentCacheDir().getAbsolutePath();
        try {
            path = getCurrentCacheDir().getCanonicalPath();
        } catch (IOException ioe) {
        }


        final JTextField pathField = new JTextField(45);
        final JCheckBox moveFiles = new JCheckBox("Move Existing Files To New Location?", true);

        pathField.setText(path);
        pathField.setEditable(false);
        JButton browseBut = new JButton("Browse");
        final ViewChartGUI gui = this;
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Cache Location");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        browseBut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fc.setCurrentDirectory(new File(pathField.getText()));

                if (fc.showOpenDialog(gui) == JFileChooser.APPROVE_OPTION) {
                    System.out.println(fc.getSelectedFile());
                    pathField.setText(fc.getSelectedFile().getAbsolutePath());
                }

            }
        });

        final JPanel panel = new JPanel();
        panel.add(pathField);
        panel.add(browseBut);

        panel.addHierarchyListener(new HierarchyListener() {

            public void hierarchyChanged(HierarchyEvent e) {
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window instanceof Dialog) {
                    Dialog dialog = (Dialog) window;
                    if (!dialog.isResizable()) {
                        dialog.setResizable(true);
                    }

                }
            }
        });

        int response = JOptionPane.showOptionDialog(this,
                new Object[]{"Please Select Desired Cache Location", panel, moveFiles},
                "Cache Location",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);

        if (response == JOptionPane.OK_OPTION) {
            File newTempDirectory = new File(pathField.getText());


            if (!newTempDirectory.equals(getCurrentCacheDir())) {
                if (moveFiles.isSelected()) {
                    for (File f : getCurrentCacheDir().listFiles()) {
                        f.renameTo(new File(newTempDirectory, f.getName()));
                    }
                }

                setCurrentCacheDir(newTempDirectory);
                rs = new ResultsSerializer(getCurrentCacheDir());
            }
        }
    }//GEN-LAST:event_setCacheLocationMenuItemActionPerformed

    private void saveMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (getCurrentResults() != null) {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File(getCurrentPeptide() + "." + getCurrentCharge() + ".txt"));
            fc.setDialogTitle("Select Output File");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setMultiSelectionEnabled(false);
            fc.setFileFilter(new ExtensionFileFilter("Text File", "txt"));
            int result = fc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".txt")) {
                    path += ".txt";
                }
                if (new File(path).exists()) {
                    int response = JOptionPane.showConfirmDialog(this, "You have selected a file that already exists.\n\n"
                            + "Do you want to overwrite?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                try {
                    PrintWriter fileOut = new PrintWriter(path);
                    fileOut.println("Peptide: " + getCurrentPeptide());
                    fileOut.println("Charge: " + getCurrentCharge());
                    fileOut.println(getCurrentResults().toString().replaceAll("\n", nl));
                    fileOut.println();
                    fileOut.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(this, "There was an I/O writing the file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        StringBuilder message = new StringBuilder();
        message.append("Isotopic Pattern Calculator\n\n");
        message.append("This program can be used to calculate exact isotopic masses and distributions.\n");
        message.append("Element data was retrieved from NIST (http://physics.nist.gov/PhysRefData/Compositions/) in August 2009.\n\n");

        message.append("Program written by Michael Cusack for the Department of Energy (PNNL, Richland, WA) in 2009\n");
        message.append("Copyright 2009, Battelle Memorial Institute. All Rights Reserved.\n\n");

        message.append("Email: michael.cusack@pnl.gov or mpcusack@trustus.net\n\n");

        message.append("Software is provided \"as is.\" Use at your own risk.\n\n");

        message.append("Version: 1.0 Revision ");
        message.append(version);
        JOptionPane.showMessageDialog(this, message.toString(), "About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon.getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void tableCheckBoxMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_tableCheckBoxMenuItemActionPerformed
        if (resultsDialog == null) {
            resultsDialog = new ResultsDialog(this);
        }
        resultsDialog.setVisible(tableCheckBoxMenuItem.isSelected());
    }//GEN-LAST:event_tableCheckBoxMenuItemActionPerformed

    private void n15ResetButActionPerformed(ActionEvent evt) {//GEN-FIRST:event_n15ResetButActionPerformed
        double n15 = ipc.findElement(null, "N").last().getP();
        n15Spin.setValue(n15);

    }//GEN-LAST:event_n15ResetButActionPerformed

    private void c13ResetButActionPerformed(ActionEvent evt) {//GEN-FIRST:event_c13ResetButActionPerformed
        double c13 = ipc.findElement(null, "C").last().getP();
        c13Spin.setValue(c13);
    }//GEN-LAST:event_c13ResetButActionPerformed
    RangeChangeListener chartRangeChangeListener = null;

    public ChartPanel getChartPanel() {
        return ((ChartPanel) bottomPane);
    }

    void setChart(JFreeChart chart) {

        getChartPanel().setChart(chart);

        FixedRange domain = (FixedRange) ((XYPlot) chart.getPlot()).getDomainAxis();
        if (chartRangeChangeListener != null) {
            getChartPanel().removeChartMouseListener(chartRangeChangeListener);
        }

        chartRangeChangeListener = new RangeChangeListener(getChartPanel(), domain);
        getChartPanel().addChartMouseListener(chartRangeChangeListener);
        resetPeakColors();

    }

    public void resetPeakColors() {
        XYDataset dataset = ((XYPlot) getChartPanel().getChart().getPlot()).getDataset();
        for (int seriesIndex = 0; seriesIndex < dataset.getSeriesCount(); seriesIndex++) {
            setPeakColor(seriesIndex, Plot.color);
        }
        if (resultsDialog != null && resultsDialog.isVisible()) {
            for (int row : resultsDialog.table.getSelectedRows()) {

                if (row >= 0 && row < resultsDialog.table.getRowCount()) {
                    row = resultsDialog.table.convertRowIndexToModel(row);
                    double mass = (Double) (resultsDialog.table.getModel().getValueAt(row, 1));
                    System.out.println("Setting " + mass + " black.");
                    setPeakColor(mass, Color.black);
                }
            }
        }
    }

    public void setPeakColor(double mass, Color color) {
        XYDataset dataset = ((XYPlot) getChartPanel().getChart().getPlot()).getDataset();
        for (int peakIndex = 0; peakIndex < dataset.getSeriesCount(); peakIndex++) {
            if (dataset.getSeriesKey(peakIndex).equals(mass)) {
                setPeakColor(peakIndex, color);
            }
        }
    }

    public void setPeakColor(int seriesIndex, Color color) {
        XYItemRenderer renderer = ((XYPlot) getChartPanel().getChart().getPlot()).getRenderer();
        renderer.setSeriesPaint(seriesIndex, color);
    }

    void setPeptide(String pep, int charge) {
        clearMods();
        setCurrentCharge(charge);
        setCurrentPeptide(pep);
    }

    static JFreeChart makeChart(Results results, String title, boolean useBars) {

        Plot plot = new Plot(results.getPeaks(), title, results.getOptions().getResolution(), useBars);
        return plot.makeChart();
    }

    JFreeChart makeChart(Results results, String title) {
        return makeChart(results, title, !getCurrentNormalProfile());
    }

    void remakeChart() {
        if (getCurrentPeptide().equals("") && getCurrentMods().isEmpty()) {
            return;
        }
        processIPC();

    }

    private static Map<String, Integer> determineMods(List<ModPanel> modPanels, String peptide) {
        Map<String, Integer> mods = new HashMap<String, Integer>();
        for (ModPanel mp : modPanels) {
            Map<String, Integer> mod = Options.parseChemFormula(mp.getMod());
            if (mp.getModType() == ModPanel.ModTypes.Peptide_Mod) {
                Options.addComponents(mod, mods);
            } else if (mp.getModType() == ModPanel.ModTypes.Residue_Mod) {
                char selectedAA = mp.getAA().getOneLetter();
                for (char AA : peptide.toCharArray()) {
                    if (AA == selectedAA) {
                        Options.addComponents(mod, mods);
                    }

                }
            }
        }
        return mods;
    }

    Results getResults(Options ipcOptions, boolean updateGUI) {
        return ResultsSerializer.getResults(ipc, rs, ipcOptions, (updateGUI ? this : null));
    }

    Map<String, TreeSet<Peak>> makeOverriddingElementsFromGUI() {
        Map<String, TreeSet<Peak>> elements = new HashMap<String, TreeSet<Peak>>();
        String[] elementsToOverride = {"N", "C"};
        JSpinner[] elementsToOverrideHeavySpinners = {n15Spin, c13Spin};
        for (int i = 0; i < elementsToOverrideHeavySpinners.length; i++) {
            String elementToOverride = elementsToOverride[i];
            JSpinner elementToOverrideHeavySpinner = elementsToOverrideHeavySpinners[i];


            try {
                TreeSet<Peak> elementToOverridePeaks = new TreeSet<Peak>();

                for (Peak p : ipc.findElement(null, elementToOverride)) {
                    elementToOverridePeaks.add(new Peak(p.getMass(), p.getP()));
                }

                double heavyP = ((Number) elementToOverrideHeavySpinner.getValue()).doubleValue();
                double lightP = 1.0 - heavyP;
                elementToOverridePeaks.first().setP(lightP);
                elementToOverridePeaks.last().setP(heavyP);

                elements.put(elementToOverride, elementToOverridePeaks);
                //IPC.printElement("Old" + elementToOverride, ipc.findElement(null, elementToOverride));
                //IPC.printElement("New" + elementToOverride, elementToOverridePeaks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return elements;
    }

    synchronized void processIPC() {
        if (ipcOptionsInProcess != null) {
            ipcOptionsInProcess.setBreakProcess(true);
        }

        final String peptide = getCurrentPeptide();
        final int charge = getCurrentCharge();
        final Map<String, Integer> mods = getCurrentMods();
        final long resolution = getCurrentResolution();

        final ViewChartGUI gui = this;



        Callable<Results> callable = new Callable<Results>() {

            public Results call() throws Exception {



                try {
                    if (ipcOptionsInProcess != null) {
                        ipcOptionsInProcess.setBreakProcess(true);
                    }
                    Options ipcOptions = new Options();
                    ipcOptionsInProcess = ipcOptions;

                    ipcOptions.setOverriddenElements(makeOverriddingElementsFromGUI());
                    ipcOptions.setResolution(resolution);
                    ipcOptions.addComponents(mods);
                    ipcOptions.addPeptide(peptide);
                    ipcOptions.setCharge(charge);
                    ipcOptions.setTabOutput(true);


                    //int peaksToCalc = (int) Math.pow(ipcOptions.getNumAtoms(), 1.5);
                    System.out.println(ipcOptions.getBinPeaks());


                    ipcOptions.setUseDigits(100);
                    ipcOptions.setPrintOutput(false);

                    Results ipcResults = getResults(ipcOptions, true);

                    if (ipcResults != null && !ipcOptions.isBreakProcess()) {
                        //worked!
                        System.out.println("");
                        //currentlyProcessing.remove(ipcOptions);
                        setCurrentResults(ipcResults, ipcOptions.getFormula());
                        return ipcResults;

                    }
                    if (ipcResults == null && !ipcOptions.isBreakProcess()) {

                        System.err.println("SOMETHING CRASHED, " + ipcOptions.isBreakProcess());
                    }

                } catch (RuntimeException re) {
                    JOptionPane.showMessageDialog(gui, re.getMessage(), "Error Creating Graph!", JOptionPane.ERROR_MESSAGE);
                    re.printStackTrace();
                }


                return null;

            }
        };
        Future<Results> futureResult = executor.submit(callable);
        futureResults.add(futureResult);



        boolean cancellationsSuccessful = true;
        for (Future<Results> old : futureResults) {
            if (futureResult != old) {
                cancellationsSuccessful = cancellationsSuccessful && old.cancel(false);
            }

        }
        if (!cancellationsSuccessful) {
            try {
                executor.awaitTermination(15, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ie) {
            }
        }

        for (Future<Results> old : futureResults) {
            if (futureResult != old) {
                old.cancel(true);
            }

        }



    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewChartGUI("ITITNDK", "2", "10000").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JMenuItem aboutMenuItem;
    private JLabel addModificationL;
    private JPanel bottomPane;
    private JLabel c13L;
    private JButton c13ResetBut;
    private JSpinner c13Spin;
    private JSpinner chargeSpinner;
    private JMenuItem clearModsMenuItem;
    private JMenu editMenu;
    private JMenuItem exitMI;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuBar menuBar;
    private JLabel n15L;
    private JPanel n15Panel;
    private JButton n15ResetBut;
    private JSpinner n15Spin;
    private JCheckBoxMenuItem normalProfileCB;
    private JTextField peptideTF;
    private JMenuItem readFile;
    private JMenuItem saveMenuItem;
    private JMenuItem setCacheLocationMenuItem;
    private JMenuItem setResolutionMenuItem;
    private JCheckBoxMenuItem tableCheckBoxMenuItem;
    private JPanel topPane;
    // End of variables declaration//GEN-END:variables

    public String getCurrentPeptide() {
        return props.getProperty("Peptide");
    }

    public void setCurrentPeptide(String peptide) {
        if (!peptide.equals(getCurrentPeptide())) {
            props.setProperty("Peptide", peptide);
            remakeChart();
            if (!peptideTF.getText().equals(peptide)) {

                peptideTF.setText(peptide);
            }

        }
    }

    public int getCurrentCharge() {
        return new Integer(props.getProperty("Charge"));
    }

    public void setCurrentCharge(int charge) {
        if (charge != getCurrentCharge()) {
            props.setProperty("Charge", String.valueOf(charge));
            remakeChart();
            if (!chargeSpinner.getValue().equals(charge)) {
                chargeSpinner.setValue(charge);
            }

        }
    }

    public Map<String, Integer> getCurrentMods() {
        return currentMods;
    }

    public void setCurrentMods(Map<String, Integer> mods) {
        if (!mods.equals(getCurrentMods())) {
            currentMods = mods;
            remakeChart();
        }
    }

    public Results getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(Results results, String title) {
        currentResults = results;
        currentTitle = title;
        setChart(makeChart(results, title));
        if (resultsDialog != null) {
            resultsDialog.updateTable();
        }
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public long getCurrentResolution() {
        return new Long(props.getProperty("Resolution"));
    }

    public void setCurrentResolution(long resolution) {
        if (resolution != getCurrentResolution()) {

            props.setProperty("Resolution", String.valueOf(resolution));
            remakeChart();
        }
    }

    public File getCurrentCacheDir() {
        return new File(props.getProperty("CacheDir"));
    }

    public void setCurrentCacheDir(File cacheDir) {
        props.setProperty("CacheDir", cacheDir.getAbsolutePath());
    }

    public boolean getCurrentNormalProfile() {
        return new Boolean(props.getProperty("NormalProfile"));
    }

    public void setCurrentNormalProfile(boolean normalProfile) {
        if (normalProfile != getCurrentNormalProfile()) {
            props.setProperty("NormalProfile", String.valueOf(normalProfile));
            if (getCurrentResults() != null) {
                setChart(makeChart(getCurrentResults(), getCurrentTitle()));
            }

        }
    }

    private class ResultsDialog
            extends JDialog {

        final ViewChartGUI parent;
        JTable table;
        JScrollPane scrollPane;
        NumberFormat percentFormat = NumberFormat.getPercentInstance();

        ResultsTableModel makeNewModel() {
            if (parent != null) {
                if (parent.getCurrentResults() != null) {
                    return new ResultsTableModel(parent.getCurrentResults());
                } else {
                    System.err.println("Current results are null");
                }
            } else {
                System.err.println("Parent is null");
            }

            return null;
        }

        private void updateTable() {
            try {
                table.setModel(makeNewModel());

                Dimension pSize = scrollPane.getPreferredSize();
                int height = Math.min(600, table.getPreferredSize().height + table.getTableHeader().getPreferredSize().height + 5);
                scrollPane.setPreferredSize(new Dimension(pSize.width, height));
                pack();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("!!!");
            }
        }

        public ResultsDialog(final ViewChartGUI parent) {
            super(parent, "Results Table", false);
            this.parent = parent;

            percentFormat.setMaximumFractionDigits(2);
            percentFormat.setMinimumFractionDigits(2);


            addComponentListener(new ComponentAdapter() {

                @Override
                public void componentShown(ComponentEvent e) {
                    if (!parent.tableCheckBoxMenuItem.isSelected()) {
                        parent.tableCheckBoxMenuItem.setSelected(true);
                    }
                    parent.resetPeakColors();
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    if (parent.tableCheckBoxMenuItem.isSelected()) {
                        parent.tableCheckBoxMenuItem.setSelected(false);
                    }
                    parent.resetPeakColors();
                }
            });



            //ResultsTableModel model = new ResultsTableModel(getCurrentResults());
            table = new JTable();
            table.setAutoCreateRowSorter(true);

            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        parent.resetPeakColors();
                    }
                }
            });

            table.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {

                String format(Object value) {
                    if (value instanceof Double) {
                        Double d = (Double) value;
                        if (d >= 0 && d <= 1.00001) {
                            return percentFormat.format(d);
                        }
                        return String.valueOf(d);
                    }
                    if (value == null) {
                        System.err.println("null value?");
                    }else{
                        System.err.println(value.getClass());
                    }
                    return "???";
                }

                @Override
                protected void setValue(Object value) {
                    String s = format(value);
                    setToolTipText(s);
                    setText(s);
                }

                @Override
                public int getHorizontalAlignment() {
                    return JLabel.RIGHT;
                }
            });
            //table.setPreferredSize(table.getMinimumSize());
            scrollPane = new JScrollPane(table);

            Panel pane = new Panel(
                    new BorderLayout());
            pane.add(scrollPane, BorderLayout.CENTER);

            setContentPane(pane);

            updateTable();
        }
    }

    private class ResultsTableModel
            implements TableModel {

        final private Peak[] peaks;
        final private int charge;

        public ResultsTableModel(Results ipcResults) {
            peaks = new Peak[ipcResults.getPeaks().size()];

            int index = 0;
            for (Peak peak : ipcResults.getPeaks()) {
                peaks[index++] = peak;
            }

            charge = ipcResults.getOptions().getCharge();
        }

        public void addTableModelListener(TableModelListener l) {
        }

        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex < 4) {
                return Double.class;
            } else {
                return null;
            }
        }

        public int getColumnCount() {
            return 4;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Mr";
                case 1:
                    return "M/Z";
                case 2:
                    return "Portion of Total";
                case 3:
                    return "Relative Intensity";
            }
            return null;
        }

        public int getRowCount() {
            return peaks.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex < getRowCount() && columnIndex < 4) {
                switch (columnIndex) {
                    case 0:
                        return IPC.toMr(peaks[rowIndex].getMass(), charge);
                    case 1:
                        return peaks[rowIndex].getMass();
                    case 2:
                        return peaks[rowIndex].getP();
                    case 3:
                        return peaks[rowIndex].getRelInt();
                }
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public void removeTableModelListener(TableModelListener l) {
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            return;
        }
    }
}
