/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddSearchResultsFrame.java
 *
 * Created on Apr 27, 2009, 11:48:52 PM
 */
package org.proteosuite.external;

import org.proteosuite.external.ViewChartGUI;
import org.proteosuite.external.IPC.Options;
import org.proteosuite.external.IPC.Results;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.ui.ExtensionFileFilter;

/**
 *
 * @author mpcusack
 */
public class ReadSearchResultsDialog extends JDialog {

    private DefaultTableModel dataModel;
    private Map<String, String> sequences = new HashMap<String, String>();
    ViewChartGUI parent;

    /**
     *
     * @param parent
     */
    public ReadSearchResultsDialog(ViewChartGUI parent) {
        super(parent, "Manage Search Results", false);
        this.parent = parent;
        initComponents();
        initComponents2();
    }

    private static DefaultTableModel makeTableModel() {
        return new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Process (Click to Toggle All)", "Peptide", "Charge"
                }) {

            Class<?>[] types = new Class[]{
                java.lang.Boolean.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    private void initComponents2() {
        setColmSpinnerEditors();
        setSize(new Double(this.getSize().width * 1.5).intValue(), this.getSize().height);
        setPanelsEnabled();
        //setAllGuiFieldsEnabled(false);

        ChangeListener setPanelsListener = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                setPanelsEnabled();
            }
        };
        stEndRB.addChangeListener(setPanelsListener);
        pepSeqRB.addChangeListener(setPanelsListener);


        headerSpin.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                pullHeaders();
                if (getNumToSkip() < getHeaderRow()) {
                    numToSkipSpin.setValue(getHeaderRow());
                }
            }
        });
        dataTable.getTableHeader().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int column = dataTable.convertColumnIndexToModel(dataTable.getColumnModel().getColumnIndexAtX(e.getX()));
                if (column == 0) {
                    for (int i = 0; i < dataModel.getRowCount(); i++) {
                        boolean selected = (Boolean) dataModel.getValueAt(i, column);
                        dataModel.setValueAt(!selected, i, column);
                    }
                }
            }
        });
        dataTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    Point p = new Point(e.getX(), e.getY());
                    int row = dataTable.rowAtPoint(p);
                    dataTable.getSelectionModel().setSelectionInterval(row, row);


                    if (row >= 0 && row < dataTable.getRowCount()) {

                        JPopupMenu contextMenu = createContextMenu(row);

                        if (contextMenu != null && contextMenu.getComponentCount() > 0) {
                            contextMenu.show(dataTable, p.x, p.y);
                        }
                    }
                }
            }
        });
    }

    private JPopupMenu createContextMenu(final int rowIndex) {
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem computeItem = new JMenuItem();
        computeItem.setText("Compute");
        computeItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //System.out.println("Compute: " + dataModel.getValuFeAt(rowIndex, 1) + " " + dataModel.getValueAt(rowIndex, 2));
                if (parent != null) {
                    parent.setPeptide((String) dataModel.getValueAt(rowIndex, 1),
                            (Integer) dataModel.getValueAt(rowIndex, 2));
                }

            }
        });
        contextMenu.add(computeItem);


        return contextMenu;
    }

    private void setColmSpinnerEditors() {
        colmSpinners = new JSpinner[]{
                    startSpin,
                    endSpin,
                    accSpin,
                    chargeColmSpin,
                    pepColmSpin
                };
        for (JSpinner spin : colmSpinners) {
            spin.setEditor(new ColumnSpinnerEditor(this, spin));
        }
        headerSpin.setEditor(new ColumnSpinnerEditor(this, headerSpin, false));
        numToSkipSpin.setEditor(new ColumnSpinnerEditor(this, numToSkipSpin, false));
    }

    private void clearAllGuiFields() {
        accSpin.setValue(1);
        startSpin.setValue(2);
        endSpin.setValue(3);


        pepColmSpin.setValue(1);
        chargeColmSpin.setValue(2);


        headerSpin.setValue(0);
        numToSkipSpin.setValue(0);

        fileTF.setText("");
        fastaTF.setText("");

        buttonGroup.clearSelection();

        clearRows();
    }

    private void clearRows() {
        while (dataModel.getRowCount() > 0) {
            dataModel.removeRow(0);
        }
    }
    private String[] headers = null;

    int getHeaderRow() {
        return (Integer) headerSpin.getValue();
    }

    int getPepColm() {
        return (Integer) pepColmSpin.getValue();
    }

    int getChargeColm() {
        return (Integer) chargeColmSpin.getValue();
    }

    int getEndColm() {
        return (Integer) endSpin.getValue();
    }

    int getStartColm() {
        return (Integer) startSpin.getValue();
    }

    int getAccColm() {
        return (Integer) accSpin.getValue();
    }

    String getFileName() {
        return fileTF.getText();
    }

    String getFastaFileName() {
        return fastaTF.getText();
    }

    int getNumToSkip() {
        return (Integer) numToSkipSpin.getValue();
    }

    void pullHeaders() {
        //System.out.println("Trying to get headers...");

        if (getHeaderRow() < 1) {
            return;
        }
        File file = new File(getFileName());
        if (!file.exists()) {
            return;
        }
        //System.out.println("All's good");
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(file));
            for (int i = 1; i < getHeaderRow(); i++) {
                fileIn.readLine();
            }

            setHeaders(fileIn.readLine().split("\t"));
            fileIn.close();
        } catch (IOException ioe) {
        }
    }

    private void setPanelsEnabled() {
        setStEndEnabled();
        setPepSeqEnabled();
    }

    private void setStEndEnabled() {
        boolean enabled = stEndRB.isSelected();
        //System.out.println("setStEndEnabled:" + enabled);
        fastaL.setEnabled(enabled);
        fastaBrowseBut.setEnabled(enabled);
        accL.setEnabled(enabled);
        accSpin.setEnabled(enabled);
        startL.setEnabled(enabled);
        startSpin.setEnabled(enabled);
        endL.setEnabled(enabled);
        endSpin.setEnabled(enabled);
    }

    private void setPepSeqEnabled() {

        boolean enabled = pepSeqRB.isSelected();
        //System.out.println("setPepSeqEnabled:" + enabled);
        pepColmL.setEnabled(enabled);
        pepColmSpin.setEnabled(enabled);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup = new ButtonGroup();
        numToSkipL = new JLabel();
        numToSkipSpin = new JSpinner();
        headerL = new JLabel();
        headerSpin = new JSpinner();
        colmsPanel = new JPanel();
        colmsSep = new JSeparator();
        startL = new JLabel();
        endL = new JLabel();
        startSpin = new JSpinner();
        endSpin = new JSpinner();
        pepColmL = new JLabel();
        accL = new JLabel();
        accSpin = new JSpinner();
        pepColmSpin = new JSpinner();
        stEndRB = new JRadioButton();
        pepSeqRB = new JRadioButton();
        fastaFilePanel = new JPanel();
        fastaTF = new JTextField();
        fastaBrowseBut = new JButton();
        fastaL = new JLabel();
        loadBut = new JButton();
        saveClearPanel = new JPanel();
        saveBut = new JButton();
        clearBut = new JButton();
        dataScrollPanel = new JScrollPane();
        dataModel = makeTableModel();
        dataTable = new JTable();
        nameFilePanel = new JPanel();
        fileL = new JLabel();
        fileTF = new JTextField();
        fileBrowseBut = new JButton();
        chargeColmL = new JLabel();
        chargeColmSpin = new JSpinner();
        outFilePanel = new JPanel();
        outFileL = new JLabel();
        outFileTF = new JTextField();
        outFileBrowseBut = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Read Peptides From File");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        numToSkipL.setText("# of Top Rows to Skip:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        getContentPane().add(numToSkipL, gridBagConstraints);

        numToSkipSpin.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        getContentPane().add(numToSkipSpin, gridBagConstraints);

        headerL.setText("Header Row:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        getContentPane().add(headerL, gridBagConstraints);

        headerSpin.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 10);
        getContentPane().add(headerSpin, gridBagConstraints);

        colmsPanel.setBorder(BorderFactory.createEtchedBorder());
        colmsPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        colmsPanel.add(colmsSep, gridBagConstraints);

        startL.setText("Start Column:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        colmsPanel.add(startL, gridBagConstraints);

        endL.setText("End Column:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 0);
        colmsPanel.add(endL, gridBagConstraints);

        startSpin.setModel(new SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(1), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        colmsPanel.add(startSpin, gridBagConstraints);

        endSpin.setModel(new SpinnerNumberModel(Integer.valueOf(4), Integer.valueOf(1), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 5);
        colmsPanel.add(endSpin, gridBagConstraints);

        pepColmL.setText("Peptide Column:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 0);
        colmsPanel.add(pepColmL, gridBagConstraints);

        accL.setText("Accession Column:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        colmsPanel.add(accL, gridBagConstraints);

        accSpin.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        colmsPanel.add(accSpin, gridBagConstraints);

        pepColmSpin.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 5);
        colmsPanel.add(pepColmSpin, gridBagConstraints);

        buttonGroup.add(stEndRB);
        stEndRB.setText("Start/End Numbers (Requires FASTA)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        colmsPanel.add(stEndRB, gridBagConstraints);

        buttonGroup.add(pepSeqRB);
        pepSeqRB.setText("Peptide Sequence");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        colmsPanel.add(pepSeqRB, gridBagConstraints);

        fastaFilePanel.setLayout(new java.awt.GridBagLayout());

        fastaTF.setEditable(false);
        fileTF.setBackground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        fastaFilePanel.add(fastaTF, gridBagConstraints);

        fastaBrowseBut.setText("Browse");
        fastaBrowseBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fastaBrowseButActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        fastaFilePanel.add(fastaBrowseBut, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        colmsPanel.add(fastaFilePanel, gridBagConstraints);

        fastaL.setText("FASTA DB File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        colmsPanel.add(fastaL, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(colmsPanel, gridBagConstraints);

        loadBut.setIcon(new ImageIcon(getClass().getResource("/ipc/gui/icons/View-refresh.png"))); // NOI18N
        loadBut.setText("(Re)Load File");
        loadBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(loadBut, gridBagConstraints);

        saveClearPanel.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        saveBut.setText("Save");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });
        saveClearPanel.add(saveBut);

        clearBut.setText("Clear");
        clearBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButActionPerformed(evt);
            }
        });
        saveClearPanel.add(clearBut);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        getContentPane().add(saveClearPanel, gridBagConstraints);

        dataTable.setModel(dataModel);
        dataScrollPanel.setViewportView(dataTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(dataScrollPanel, gridBagConstraints);

        nameFilePanel.setLayout(new java.awt.GridBagLayout());

        fileL.setText("File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        nameFilePanel.add(fileL, gridBagConstraints);

        fileTF.setEditable(false);
        fileTF.setBackground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        nameFilePanel.add(fileTF, gridBagConstraints);

        fileBrowseBut.setText("Browse");
        fileBrowseBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileBrowseButActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        nameFilePanel.add(fileBrowseBut, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 5, 10);
        getContentPane().add(nameFilePanel, gridBagConstraints);

        chargeColmL.setText("Charge Column:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        getContentPane().add(chargeColmL, gridBagConstraints);

        chargeColmSpin.setModel(new SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(1), null, Integer.valueOf(1)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 10);
        getContentPane().add(chargeColmSpin, gridBagConstraints);

        outFilePanel.setLayout(new java.awt.GridBagLayout());

        outFileL.setText("Output File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        outFilePanel.add(outFileL, gridBagConstraints);

        outFileTF.setEditable(false);
        fileTF.setBackground(Color.white);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        outFilePanel.add(outFileTF, gridBagConstraints);

        outFileBrowseBut.setText("Browse");
        outFileBrowseBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outFileBrowseButActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        outFilePanel.add(outFileBrowseBut, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        getContentPane().add(outFilePanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileBrowseButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileBrowseButActionPerformed
        JFileChooser fc = new JFileChooser(new File(getFileName()));
        fc.setDialogTitle("Select List of Peptides");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileTF.setText(fc.getSelectedFile().getAbsolutePath());
            pullHeaders();

            if (fc.getSelectedFile().getName().endsWith("_fht.txt")) {
                headerSpin.setValue(1);
                pepSeqRB.setSelected(true);
                pepColmSpin.setValue(11);
                chargeColmSpin.setValue(4);
            }
        }
    }//GEN-LAST:event_fileBrowseButActionPerformed

    private void readFasta() {

        File file = new File(getFastaFileName());
        if (getFastaFileName().equals("") || !file.exists()) {
            throw new RuntimeException("Can't find file '" + getFileName() + "'.");
        }

        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(file));
            String line;

            String acc = null;
            StringBuilder seq = null;
            while ((line = fileIn.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (acc != null && seq != null) {
                        sequences.put(acc, seq.toString());
                    }
                    acc = line.substring(1, line.indexOf(' ')).trim();
                    seq = new StringBuilder();
                } else if (seq != null) {
                    seq.append(line.trim());
                }
            }
            if (acc != null && seq != null) {
                sequences.put(acc, seq.toString());
            }
            fileIn.close();
        } catch (Exception ioe) {
            throw new RuntimeException("Error Reading FASTA File.");
        }
    }

    private void loadButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButActionPerformed
        try {
            if (buttonGroup.getSelection() == null) {
                throw new RuntimeException("Please Select a Method for Reading the File.");
            }

            boolean useFasta = stEndRB.isSelected();
            boolean skipBadPeps = false;



            File file = new File(getFileName());
            if (getFileName().equals("") || !file.exists()) {
                throw new RuntimeException("Can't find file '" + getFileName() + "'.");
            }

            clearRows();
            //List<SearchResult> newSearchResults = new LinkedList<SearchResult>();
            //System.out.println("results list cleared: " + getSearchResultList().size());
            LineNumberReader fileIn = null;
            try {
                fileIn = new LineNumberReader(new FileReader(file));
                String line;
                while ((line = fileIn.readLine()) != null) {
                    if (fileIn.getLineNumber() <= getNumToSkip()) {
                        continue;
                    }
                    String[] colms = line.split("\t");

                    String pep;
                    if (useFasta) {
                        String acc = getColm(colms, getAccColm());
                        if (!sequences.containsKey(acc)) {
                            readFasta();
                        }
                        if (!sequences.containsKey(acc)) {
                            fileIn.close();
                            throw new RuntimeException("Can't find sequence for '" + acc + "'.");
                        }
                        String seq = sequences.get(acc);

                        int start = getColmAsInt(colms, getStartColm());
                        int end = getColmAsInt(colms, getEndColm());

                        pep = seq.substring(start - 1, end);
                    } else {
                        pep = getColm(colms, getPepColm());
                    }

                    int charge = getColmAsInt(colms, getChargeColm());

                    try {
                        pep = validPep(pep);

                    } catch (RuntimeException re) {
                        if (skipBadPeps) {
                            continue;
                        }

                        int response = JOptionPane.showOptionDialog(this,
                                re.getMessage(),
                                "Peptide Error",
                                -1,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                new String[]{"Ignore This Peptide", "Ignore This Peptide and All Like It", "Cancel Operation"},
                                null);

                        if (response == 0) {
                            continue;
                        } else if (response == 1) {
                            skipBadPeps = true;
                            continue;
                        } else if (response == 2) {
                            clearRows();
                            fileIn.close();
                            return;
                        }
                    }
                    addPeptide(pep, charge);




                }
                fileIn.close();
            } catch (IOException ioe) {
                throw new RuntimeException("IO Error Reading File");
            }
//        updateSearchResultList();

            //firePropertyChange("searchResultsItem.searchResultList", null, getSearchResultList());


        } catch (Exception re) {
            JOptionPane.showMessageDialog(this, re.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loadButActionPerformed
    final static String validPeptidePattern = "[ARNDCEQGHILKMFPSTWYV]+";

    private String validPep(String pep) {

        if (pep.matches(".\\..+\\..")) {
            pep = pep.substring(2, pep.length() - 3);
        }
        if (!pep.matches(validPeptidePattern)) {
            throw new RuntimeException("'" + pep + "' is not a valid peptide.");
        }
        return pep;
    }

    private static Integer getColmAsInt(String[] colms, int colmNum) {
        try {
            return new Integer(getColm(colms, colmNum));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String getColm(String[] colms, int colmNum) {
        if (colmNum > colms.length || colmNum < 1) {
            return "";
        }
        return colms[colmNum - 1];
    }

    private class Peptide {

        String peptide;
        int charge;

        public Peptide(String peptide, int charge) {
            this.peptide = peptide;
            this.charge = charge;
        }
    }

    List<Peptide> getRowsToProcess() {
        List<Peptide> rows = new LinkedList<Peptide>();

        for (Object o : dataModel.getDataVector()) {
            Vector v = (Vector) o;
            boolean process = (Boolean) v.get(0);

            if (process) {

                String peptide = (String) v.get(1);
                int charge = (Integer) v.get(2);
                rows.add(new Peptide(peptide, charge));
            }
        }

        return rows;
    }
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButActionPerformed
        if (outFileTF.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "You Must Select An Output File!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File outFile = new File(outFileTF.getText());
        if (outFile.exists()) {
            int response = JOptionPane.showConfirmDialog(this, "You have selected a file that already exists.\n\n" +
                    "Do you want to overwrite?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.NO_OPTION) {
                return;
            }
        }
        List<Peptide> rowsToProcess = getRowsToProcess();
        if (rowsToProcess.size() >= 5) {
            int response = JOptionPane.showConfirmDialog(this, "You have selected " + rowsToProcess.size() + " peptides to process.\n" +
                    "If these peptides are not in the cache this may take a long to process.\n" +
                    "While processing the program may appear unresponsive.\n\n" +
                    "Do you want to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.NO_OPTION) {
                return;
            }
        }
        boolean first = true;
        try {
            PrintWriter fileOut = new PrintWriter(outFile);
            for (Peptide p : rowsToProcess) {
                if (first) {
                    first = false;
                } else {
                    fileOut.println("----------------------------------------------------------------------");
                }
                fileOut.println("Peptide: " + p.peptide);
                fileOut.println("Charge: " + p.charge);

                Options ipcOptions = new Options();
                ipcOptions.setCharge(p.charge);
                ipcOptions.addPeptide(p.peptide);
                ipcOptions.setResolution(parent.getCurrentResolution());
                ipcOptions.setTabOutput(true);
                Results ipcResults = parent.getResults(ipcOptions, false);

                fileOut.println(ipcResults.toString().replaceAll("\n", ViewChartGUI.nl));
                fileOut.println();
            }
            fileOut.close();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, "There was an I/O writing the file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveButActionPerformed

    private void addPeptide(String pep, int charge) {
        addPeptide(pep, charge, true);
    }

    private void addPeptide(String pep, int charge, boolean checked) {
        dataModel.addRow(new Object[]{checked, pep, charge});
    }

    private void clearButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButActionPerformed
        clearAllGuiFields();
    }//GEN-LAST:event_clearButActionPerformed

    private void fastaBrowseButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fastaBrowseButActionPerformed
        JFileChooser fc = new JFileChooser(new File(getFastaFileName()));
        fc.setDialogTitle("Select FASTA Database");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new ExtensionFileFilter("FASTA File", "fasta"));
        fc.setMultiSelectionEnabled(false);
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            fastaTF.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_fastaBrowseButActionPerformed

    private void outFileBrowseButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outFileBrowseButActionPerformed
        JFileChooser fc = new JFileChooser(new File(getFastaFileName()));
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
            outFileTF.setText(path);
        }
    }//GEN-LAST:event_outFileBrowseButActionPerformed

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        String[] old = this.headers;
        this.headers = headers;
        firePropertyChange("headers", old, headers);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ReadSearchResultsDialog(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel accL;
    private JSpinner accSpin;
    private ButtonGroup buttonGroup;
    private JLabel chargeColmL;
    private JSpinner chargeColmSpin;
    private JButton clearBut;
    private JPanel colmsPanel;
    private JSeparator colmsSep;
    private JScrollPane dataScrollPanel;
    private JTable dataTable;
    private JLabel endL;
    private JSpinner endSpin;
    private JButton fastaBrowseBut;
    private JPanel fastaFilePanel;
    private JLabel fastaL;
    private JTextField fastaTF;
    private JButton fileBrowseBut;
    private JLabel fileL;
    private JTextField fileTF;
    private JLabel headerL;
    private JSpinner headerSpin;
    private JButton loadBut;
    private JPanel nameFilePanel;
    private JLabel numToSkipL;
    private JSpinner numToSkipSpin;
    private JButton outFileBrowseBut;
    private JLabel outFileL;
    private JPanel outFilePanel;
    private JTextField outFileTF;
    private JLabel pepColmL;
    private JSpinner pepColmSpin;
    private JRadioButton pepSeqRB;
    private JButton saveBut;
    private JPanel saveClearPanel;
    private JRadioButton stEndRB;
    private JLabel startL;
    private JSpinner startSpin;
    // End of variables declaration//GEN-END:variables
    private JSpinner[] colmSpinners;
}
