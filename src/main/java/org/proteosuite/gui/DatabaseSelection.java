
package org.proteosuite.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DatabaseSelection {
    public static JPanel getDatabaseSection(boolean genomeAnnotationMode, final JDialog searchSettingsDialog) {
        final JTextField jtDatabaseFile = new JTextField(50);
        JPanel databasePanel = new JPanel();

        JButton jbSetDatabaseFile = new JButton("...");
        jbSetDatabaseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setFastAClicked(jtDatabaseFile, searchSettingsDialog);
            }
        });

        if (genomeAnnotationMode) {
            JPanel fastaGffPanel = new JPanel();
            fastaGffPanel.setLayout(new GridBagLayout());
            
            GridBagConstraints fastaLabelConstraints = new GridBagConstraints();
            fastaLabelConstraints.gridx = 0;
            fastaLabelConstraints.gridy = 0;    
            fastaLabelConstraints.anchor = GridBagConstraints.WEST;
            JLabel fastALabel = new JLabel("FastA file");           
            fastaGffPanel.add(fastALabel, fastaLabelConstraints);
            
            GridBagConstraints gffLabelConstraints = new GridBagConstraints();
            gffLabelConstraints.gridx = 5;
            gffLabelConstraints.gridy = 0;         
            JLabel gffLabel = new JLabel("GFF3 file");
            
            fastaGffPanel.add(gffLabel, gffLabelConstraints);            
            
            GridBagConstraints fastATextConstraints = new GridBagConstraints();
            fastATextConstraints.gridx = 0;
            fastATextConstraints.gridy = 2;
            fastATextConstraints.gridwidth = 4;
            fastATextConstraints.gridheight = 2;
            fastaGffPanel.add(new JTextField(30), fastATextConstraints);
            
            GridBagConstraints fastAButtonConstraints = new GridBagConstraints();
            fastAButtonConstraints.gridx = 4;
            fastAButtonConstraints.gridy = 2;
            fastAButtonConstraints.gridwidth = 1;
            fastAButtonConstraints.gridheight = 2;            
            fastaGffPanel.add(new JButton("..."), fastAButtonConstraints);
            
            GridBagConstraints gffTextConstraints = new GridBagConstraints();
            gffTextConstraints.gridx = 5;
            gffTextConstraints.gridy = 2;
            gffTextConstraints.gridwidth = 4;
            gffTextConstraints.gridheight = 2;
            fastaGffPanel.add(new JTextField(30), gffTextConstraints);
            
            GridBagConstraints gffButtonConstraints = new GridBagConstraints();
            gffButtonConstraints.gridx = 9;
            gffButtonConstraints.gridy = 2;
            gffButtonConstraints.gridwidth = 1;
            gffButtonConstraints.gridheight = 2;
            fastaGffPanel.add(new JButton("..."), gffButtonConstraints);
            
            GridBagConstraints newPairingConstraints = new GridBagConstraints();
            newPairingConstraints.gridx = 0;
            newPairingConstraints.gridy = 4;
            newPairingConstraints.gridwidth = 1;
            newPairingConstraints.gridheight = 2;
            fastaGffPanel.add(new JButton("New FastA-GFF Pairing"), newPairingConstraints);
            
            GridBagConstraints deletePairingConstraints = new GridBagConstraints();
            deletePairingConstraints.gridx = 1;
            deletePairingConstraints.gridy = 4;
            deletePairingConstraints.gridwidth = 1;
            deletePairingConstraints.gridheight = 2;            
            fastaGffPanel.add(new JButton("Delete FastA-GFF Pairing"), deletePairingConstraints);
            
            databasePanel.add(fastaGffPanel);           
        } else {
            databasePanel.add(new JLabel("Database file:"));            
            databasePanel.add(jtDatabaseFile);
            databasePanel.add(jbSetDatabaseFile);
        }
        
        return databasePanel;       
    }
    
    private static void setFastAClicked(JTextField databaseFile, JDialog searchSettingsDialog) {
        // Adding files
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select the database file");
        chooser.setAcceptAllFileFilterUsed(false);

        // Applying file extension filters
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Fasta Files (*.fasta, *.fa)", "fasta", "fa"));

        // Disable multiple file selection
        chooser.setMultiSelectionEnabled(false);

        int returnVal = chooser.showOpenDialog(searchSettingsDialog);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }

        if (file.getName().indexOf(".fasta") == -1) {
            JOptionPane
                    .showMessageDialog(
                            searchSettingsDialog,
                            "Incorrect file extension. Please select a valid fasta file (*.fasta)",
                            "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        databaseFile.setText(file.getPath());
    }
    
    private static JPanel getRow(Component... components) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Component c : components) {
            row.add(c);
        }

        return row;
    }
}
