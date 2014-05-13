package org.proteosuite.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DatabasePanel extends JPanel {

    private boolean fullMode = false;
    private int otherModelsSize = 0;
    private final JDialog searchDialog;
    private static final Map<Component, GridBagConstraints> staticStartComponents = new HashMap<>();
    private final Map<Component, GridBagConstraints> dynamicComponents = new LinkedHashMap<>();
    private final Map<Component, GridBagConstraints> staticEndComponents = new HashMap<>();
    
    private JTextField databaseText;

    public DatabasePanel(boolean genomeAnnotationMode, final JDialog searchSettingsDialog) {
        this.fullMode = genomeAnnotationMode;
        this.searchDialog = searchSettingsDialog;
        buildInterface();
    }

    private void buildInterface() {
        if (this.fullMode) {
            rebuildFullModeInterface();
        } else {
            add(new JLabel("Database file:"));
            databaseText = new JTextField(50);
            add(databaseText);

            JButton databaseButton = new JButton("...");
            databaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    setFastAClicked(databaseText, DatabasePanel.this.searchDialog);
                }
            });

            add(databaseButton);
        }
    }

    private void buildStaticStartComponents() {
        // Static section, containing gene model fastA and GFF.       

        GridBagConstraints geneModelFastaLabelConstraints = new GridBagConstraints();
        geneModelFastaLabelConstraints.gridx = 0;
        geneModelFastaLabelConstraints.gridy = 0;
        geneModelFastaLabelConstraints.anchor = GridBagConstraints.WEST;
        JLabel geneModelFastaLabel = new JLabel("Gene Model FastA:");
        staticStartComponents.put(geneModelFastaLabel, geneModelFastaLabelConstraints);

        GridBagConstraints geneModelGffLabelConstraints = new GridBagConstraints();
        geneModelGffLabelConstraints.gridx = 5;
        geneModelGffLabelConstraints.gridy = 0;
        JLabel geneModelGffLabel = new JLabel("Gene Model GFF:");
        staticStartComponents.put(geneModelGffLabel, geneModelGffLabelConstraints);

        GridBagConstraints geneModelFastaTextConstraints = new GridBagConstraints();
        geneModelFastaTextConstraints.gridx = 0;
        geneModelFastaTextConstraints.gridy = 2;
        geneModelFastaTextConstraints.gridwidth = 4;
        staticStartComponents.put(new JTextField(30), geneModelFastaTextConstraints);

        GridBagConstraints geneModelFastaButtonConstraints = new GridBagConstraints();
        geneModelFastaButtonConstraints.gridx = 4;
        geneModelFastaButtonConstraints.gridy = 2;
        geneModelFastaButtonConstraints.gridwidth = 1;
        geneModelFastaButtonConstraints.gridheight = 2;
        JButton geneModelFastaButton = new JButton("...");
        staticStartComponents.put(geneModelFastaButton, geneModelFastaButtonConstraints);

        GridBagConstraints geneModelGffTextConstraints = new GridBagConstraints();
        geneModelGffTextConstraints.gridx = 5;
        geneModelGffTextConstraints.gridy = 2;
        geneModelGffTextConstraints.gridwidth = 4;
        staticStartComponents.put(new JTextField(30), geneModelGffTextConstraints);

        GridBagConstraints geneModelGffButtonConstraints = new GridBagConstraints();
        geneModelGffButtonConstraints.gridx = 9;
        geneModelGffButtonConstraints.gridy = 2;
        geneModelGffButtonConstraints.gridwidth = 1;
        geneModelGffButtonConstraints.gridheight = 2;
        JButton geneModelGffButton = new JButton("...");
        staticStartComponents.put(geneModelGffButton, geneModelGffButtonConstraints);
    }

    private int buildDynamicComponents() {
        Map<String, String> currentModel = getCurrentDynamicModel();

        dynamicComponents.clear();

        if (otherModelsSize == 0) {
            return 4;
        }

        GridBagConstraints otherFastaLabelConstraints = new GridBagConstraints();
        otherFastaLabelConstraints.gridx = 0;
        otherFastaLabelConstraints.gridy = 4;
        otherFastaLabelConstraints.anchor = GridBagConstraints.WEST;
        JLabel otherFastaLabel = new JLabel("Other FastA:");
        dynamicComponents.put(otherFastaLabel, otherFastaLabelConstraints);

        GridBagConstraints otherGffLabelConstraints = new GridBagConstraints();
        otherGffLabelConstraints.gridx = 5;
        otherGffLabelConstraints.gridy = 4;
        JLabel otherGffLabel = new JLabel("Other GFF3:");
        dynamicComponents.put(otherGffLabel, otherGffLabelConstraints);

        int yOffset = 6;
        
        Iterator<Entry<String, String>> iterator = currentModel.entrySet().iterator();
        for (int i = 0; i < otherModelsSize; i++) {
            String fasta = "", gff = "";
            if (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                fasta = entry.getKey();
                gff = entry.getValue();
            }
            
            GridBagConstraints otherFastaTextConstraints = new GridBagConstraints();
            otherFastaTextConstraints.gridx = 0;
            otherFastaTextConstraints.gridy = yOffset;
            otherFastaTextConstraints.gridwidth = 4;
            otherFastaTextConstraints.gridheight = 2;
            otherFastaTextConstraints.anchor = GridBagConstraints.WEST;
            JTextField otherFastaText = new JTextField(30);
            otherFastaText.setText(fasta);
            dynamicComponents.put(otherFastaText, otherFastaTextConstraints);

            GridBagConstraints otherFastaButtonConstraints = new GridBagConstraints();
            otherFastaButtonConstraints.gridx = 4;
            otherFastaButtonConstraints.gridy = yOffset;
            otherFastaButtonConstraints.gridwidth = 1;
            otherFastaButtonConstraints.gridheight = 2;
            otherFastaButtonConstraints.anchor = GridBagConstraints.WEST;
            JButton otherFastaButton = new JButton("...");
            dynamicComponents.put(otherFastaButton, otherFastaButtonConstraints);

            GridBagConstraints gffTextConstraints = new GridBagConstraints();
            gffTextConstraints.gridx = 5;
            gffTextConstraints.gridy = yOffset;
            gffTextConstraints.gridwidth = 4;
            gffTextConstraints.gridheight = 2;            
            JTextField otherGffText = new JTextField(30);
            otherGffText.setText(gff);
            dynamicComponents.put(otherGffText, gffTextConstraints);

            GridBagConstraints otherGffButtonConstraints = new GridBagConstraints();
            otherGffButtonConstraints.gridx = 9;
            otherGffButtonConstraints.gridy = yOffset;
            otherGffButtonConstraints.gridwidth = 1;
            otherGffButtonConstraints.gridheight = 2;
            JButton otherGffButton = new JButton("...");
            dynamicComponents.put(otherGffButton, otherGffButtonConstraints);
            
            yOffset += 2;
        }

        return yOffset;
    }
    
    private void buildStaticEndComponents(int yOffset) {
        GridBagConstraints newPairingConstraints = new GridBagConstraints();
        newPairingConstraints.gridx = 0;
        
        newPairingConstraints.gridy = yOffset;        
        newPairingConstraints.anchor = GridBagConstraints.WEST;       
        JButton newPairingButton = new JButton("New FastA-GFF Pairing");
        if (otherModelsSize == 5) {
            newPairingButton.setEnabled(false);
        }
        
        newPairingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otherModelsSize++;
                rebuildFullModeInterface();
            }
        });

        staticEndComponents.put(newPairingButton, newPairingConstraints);
        
        
        GridBagConstraints deletePairingConstraints = new GridBagConstraints();
        deletePairingConstraints.gridx = 2;
        deletePairingConstraints.gridy = yOffset;        
        
        JButton deletePairingButton = new JButton("Delete FastA-GFF Pairing");
        if (otherModelsSize == 0) {
            deletePairingButton.setEnabled(false);
        }
        
        deletePairingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otherModelsSize--;
                rebuildFullModeInterface();
            }
        });

        staticEndComponents.put(deletePairingButton, deletePairingConstraints);
    }

    public String[] getGeneModel() {
        String[] geneModelItems = new String[2];
        int index = 0;
        for (Component component : staticStartComponents.keySet()) {
            if (!(component instanceof JTextField)) {
                continue;
            }
            
            geneModelItems[index++] = ((JTextField) component).getText();            
        }     
        
        return geneModelItems;
    }
    
    private Map<String, String> getCurrentDynamicModel() {
        Map<String, String> currentModel = new LinkedHashMap<>();        
        
        String fasta = null, gff = null;
        boolean fastaFound = false;
        for (Component component : dynamicComponents.keySet()) {
            if (!(component instanceof JTextField)) {
                continue;
            }

            String fieldText = ((JTextField) component).getText();
            if (!fastaFound) {
                fasta = fieldText;
                fastaFound = true;
            } else {
                gff = fieldText;
                currentModel.put(fasta, gff);
                fastaFound = false;
            }
        }

        return currentModel;
    }
    
    
    
    public Map<String, String> getOtherGeneModels() {
        Map<String, String> currentGUIModel = this.getCurrentDynamicModel();
        Map<String, String> curatedModel = new HashMap<>();
        for (Entry<String, String> entry : currentGUIModel.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.isEmpty() || value.isEmpty()) {
                continue;
            }
            
            curatedModel.put(key, value);
        }
        
        return curatedModel;
    }

    private void rebuildFullModeInterface() {
        setLayout(new GridBagLayout());       
               
        staticEndComponents.clear();
        
        removeAll();

        buildStaticStartComponents();

        for (Entry<Component, GridBagConstraints> entry : staticStartComponents.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }

        int yOffset = buildDynamicComponents();
        
        for (Entry<Component, GridBagConstraints> entry : dynamicComponents.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
        
        buildStaticEndComponents(yOffset);
        
        for (Entry<Component, GridBagConstraints> entry : staticEndComponents.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
        
        revalidate();
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
