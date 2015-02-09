package org.proteosuite.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.proteosuite.config.Config;
import org.proteosuite.config.GlobalConfig;

public class DatabasePanel extends JPanel {

    private boolean fullMode = false;
    private int otherModelsSize = 0;
    private final JDialog searchDialog;
    private static final Map<Component, GridBagConstraints> staticStartComponents = new LinkedHashMap<>();
    private final Map<Component, GridBagConstraints> dynamicComponents = new LinkedHashMap<>();
    private final Map<Component, GridBagConstraints> staticEndComponents = new HashMap<>();
    private static final String fastATargetsOnlyTooltip = "FastA should contain target proteins only; no decoy proteins.";
    private static final String fastaTooltip = "FastA is not mandatory if GFF contains protein sequences.";
    private static final String gffTooltip = "GFF is always mandatory.";
    private static final int TEXTFIELD_SIZE = 32;
    private static final GlobalConfig config = Config.getInstance().getGlobalConfig();

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
            JLabel databaseLabel = new JLabel("Database file:");
            databaseLabel.setToolTipText(fastATargetsOnlyTooltip);
            add(databaseLabel);
            databaseText = new JTextField(50);
            databaseText.setToolTipText(fastATargetsOnlyTooltip);
            add(databaseText);

            JButton databaseButton = new JButton("...");
            databaseButton.setToolTipText(fastATargetsOnlyTooltip);
            databaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    setDatabaseButtonClicked(databaseText, DatabasePanel.this.searchDialog, true);
                }
            });

            add(databaseButton);
        }
    }

    private void buildStaticStartComponents() {
        // Static section, containing gene model fastA and GFF.  

        if (staticStartComponents.size() > 0) {
            return;
        }

        GridBagConstraints geneModelGffLabelConstraints = new GridBagConstraints();
        geneModelGffLabelConstraints.gridx = 0;
        geneModelGffLabelConstraints.gridy = 0;
        geneModelGffLabelConstraints.anchor = GridBagConstraints.WEST;
        JLabel geneModelGffLabel = new JLabel("Canonical Gene Model GFF:");
        geneModelGffLabel.setToolTipText(gffTooltip);
        staticStartComponents.put(geneModelGffLabel, geneModelGffLabelConstraints);

        GridBagConstraints geneModelFastaLabelConstraints = new GridBagConstraints();
        geneModelFastaLabelConstraints.gridx = 5;
        geneModelFastaLabelConstraints.gridy = 0;
        JLabel geneModelFastaLabel = new JLabel("Canonical Gene Model FastA:");
        geneModelFastaLabel.setToolTipText(fastaTooltip);
        staticStartComponents.put(geneModelFastaLabel, geneModelFastaLabelConstraints);

        GridBagConstraints geneModelGffTextConstraints = new GridBagConstraints();
        geneModelGffTextConstraints.gridx = 0;
        geneModelGffTextConstraints.gridy = 2;
        geneModelGffTextConstraints.gridwidth = 4;
        geneModelGffTextConstraints.anchor = GridBagConstraints.WEST;
        final JTextField geneModelGffTextField = new JTextField(TEXTFIELD_SIZE);
        staticStartComponents.put(geneModelGffTextField, geneModelGffTextConstraints);

        GridBagConstraints geneModelGffButtonConstraints = new GridBagConstraints();
        geneModelGffButtonConstraints.gridx = 4;
        geneModelGffButtonConstraints.gridy = 2;
        geneModelGffButtonConstraints.gridwidth = 1;
        geneModelGffButtonConstraints.gridheight = 2;
        geneModelGffButtonConstraints.anchor = GridBagConstraints.WEST;
        final JButton geneModelGffButton = new JButton("...");
        geneModelGffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDatabaseButtonClicked(geneModelGffTextField, DatabasePanel.this.searchDialog, false);
            }
        });

        staticStartComponents.put(geneModelGffButton, geneModelGffButtonConstraints);

        GridBagConstraints geneModelFastaTextConstraints = new GridBagConstraints();
        geneModelFastaTextConstraints.gridx = 5;
        geneModelFastaTextConstraints.gridy = 2;
        geneModelFastaTextConstraints.gridwidth = 4;
        geneModelFastaTextConstraints.anchor = GridBagConstraints.WEST;
        final JTextField geneModelFastaTextField = new JTextField(TEXTFIELD_SIZE);
        geneModelFastaTextField.setToolTipText(fastATargetsOnlyTooltip);
        staticStartComponents.put(geneModelFastaTextField, geneModelFastaTextConstraints);

        GridBagConstraints geneModelFastaButtonConstraints = new GridBagConstraints();
        geneModelFastaButtonConstraints.gridx = 9;
        geneModelFastaButtonConstraints.gridy = 2;
        geneModelFastaButtonConstraints.gridwidth = 1;
        geneModelFastaButtonConstraints.gridheight = 2;
        geneModelFastaButtonConstraints.anchor = GridBagConstraints.WEST;
        final JButton geneModelFastaButton = new JButton("...");
        geneModelFastaButton.setToolTipText(fastATargetsOnlyTooltip);
        geneModelFastaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDatabaseButtonClicked(geneModelFastaTextField, DatabasePanel.this.searchDialog, true);
            }
        });

        staticStartComponents.put(geneModelFastaButton, geneModelFastaButtonConstraints);
    }

    private int buildDynamicComponents() {
        Map<String, String> currentModel = getCurrentDynamicModel();

        dynamicComponents.clear();

        if (otherModelsSize == 0) {
            return 4;
        }

        GridBagConstraints otherGffLabelConstraints = new GridBagConstraints();
        otherGffLabelConstraints.gridx = 0;
        otherGffLabelConstraints.gridy = 4;
        otherGffLabelConstraints.anchor = GridBagConstraints.WEST;
        JLabel otherGffLabel = new JLabel("Predicted Gene Model GFF:");
        otherGffLabel.setToolTipText(gffTooltip);
        dynamicComponents.put(otherGffLabel, otherGffLabelConstraints);

        GridBagConstraints otherFastaLabelConstraints = new GridBagConstraints();
        otherFastaLabelConstraints.gridx = 5;
        otherFastaLabelConstraints.gridy = 4;
        otherFastaLabelConstraints.anchor = GridBagConstraints.WEST;
        JLabel otherFastaLabel = new JLabel("Predicted Gene Model FastA:");
        otherFastaLabel.setToolTipText(fastaTooltip);
        dynamicComponents.put(otherFastaLabel, otherFastaLabelConstraints);

        int yOffset = 6;

        Iterator<Entry<String, String>> iterator = currentModel.entrySet().iterator();
        for (int i = 0; i < otherModelsSize; i++) {
            String gff = "", fasta = "";
            if (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                gff = entry.getKey();
                fasta = entry.getValue();
            }

            GridBagConstraints otherGffTextConstraints = new GridBagConstraints();
            otherGffTextConstraints.gridx = 0;
            otherGffTextConstraints.gridy = yOffset;
            otherGffTextConstraints.gridwidth = 4;
            otherGffTextConstraints.gridheight = 2;
            otherGffTextConstraints.anchor = GridBagConstraints.WEST;
            final JTextField otherGffText = new JTextField(TEXTFIELD_SIZE);
            otherGffText.setText(gff);
            dynamicComponents.put(otherGffText, otherGffTextConstraints);

            GridBagConstraints otherGffButtonConstraints = new GridBagConstraints();
            otherGffButtonConstraints.gridx = 4;
            otherGffButtonConstraints.gridy = yOffset;
            otherGffButtonConstraints.gridwidth = 1;
            otherGffButtonConstraints.gridheight = 2;
            otherGffButtonConstraints.anchor = GridBagConstraints.WEST;
            final JButton otherGffButton = new JButton("...");
            otherGffButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setDatabaseButtonClicked(otherGffText, DatabasePanel.this.searchDialog, false);
                }
            });

            dynamicComponents.put(otherGffButton, otherGffButtonConstraints);

            GridBagConstraints fastaTextConstraints = new GridBagConstraints();
            fastaTextConstraints.gridx = 5;
            fastaTextConstraints.gridy = yOffset;
            fastaTextConstraints.gridwidth = 4;
            fastaTextConstraints.gridheight = 2;
            fastaTextConstraints.anchor = GridBagConstraints.WEST;
            final JTextField otherFastaText = new JTextField(TEXTFIELD_SIZE);
            otherFastaText.setText(fasta);
            otherFastaText.setToolTipText(fastATargetsOnlyTooltip);
            dynamicComponents.put(otherFastaText, fastaTextConstraints);

            GridBagConstraints otherFastaButtonConstraints = new GridBagConstraints();
            otherFastaButtonConstraints.gridx = 9;
            otherFastaButtonConstraints.gridy = yOffset;
            otherFastaButtonConstraints.gridwidth = 1;
            otherFastaButtonConstraints.gridheight = 2;
            otherFastaButtonConstraints.anchor = GridBagConstraints.WEST;
            final JButton otherFastaButton = new JButton("...");
            otherFastaButton.setToolTipText(fastATargetsOnlyTooltip);
            otherFastaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setDatabaseButtonClicked(otherFastaText, DatabasePanel.this.searchDialog, true);
                }
            });

            dynamicComponents.put(otherFastaButton, otherFastaButtonConstraints);

            yOffset += 2;
        }

        return yOffset;
    }

    private void buildStaticEndComponents(int yOffset) {

        staticEndComponents.clear();

        GridBagConstraints newPairingConstraints = new GridBagConstraints();
        newPairingConstraints.gridx = 0;
        newPairingConstraints.gridwidth = 2;
        newPairingConstraints.gridy = yOffset;
        newPairingConstraints.anchor = GridBagConstraints.WEST;
        JButton newPairingButton = new JButton("New Predicted Gene Model");
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
        deletePairingConstraints.gridwidth = 2;
        deletePairingConstraints.gridy = yOffset;
        deletePairingConstraints.anchor = GridBagConstraints.WEST;

        JButton deletePairingButton = new JButton("Delete Predicted Gene Model");
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

        String gff = null, fasta = null;
        boolean gffFound = false;
        for (Component component : dynamicComponents.keySet()) {
            if (!(component instanceof JTextField)) {
                continue;
            }

            String fieldText = ((JTextField) component).getText();
            if (!gffFound) {
                gff = fieldText;
                gffFound = true;
            } else {
                fasta = fieldText;
                currentModel.put(gff, fasta);
                gffFound = false;
            }
        }

        return currentModel;
    }

    public String getSingleDatabasePath() {
        return databaseText.getText();
    }

    public Map<String, String> getOtherGeneModels() {
        Map<String, String> currentGUIModel = this.getCurrentDynamicModel();
        Map<String, String> curatedModel = new HashMap<>();
        for (Entry<String, String> entry : currentGUIModel.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.isEmpty()) {
                continue;
            }

            curatedModel.put(key, value);
        }

        return curatedModel;
    }

    private void rebuildFullModeInterface() {
        setLayout(new GridBagLayout());

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

    private static void setDatabaseButtonClicked(JTextField databaseFile, JDialog searchSettingsDialog, boolean fastaDialog) {
        // Adding files
        JFileChooser chooser = new JFileChooser(config.getRememberedDirectory());
        if (fastaDialog) {
            chooser.setDialogTitle("Select the FastA database file:");
        } else {
            chooser.setDialogTitle("Select the GFF file:");
        }

        chooser.setAcceptAllFileFilterUsed(false);

        // Applying file extension filters
        if (fastaDialog) {
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "FastA Files (*.fasta, *.fa, *.fsa, *.aa)", "fasta", "fa", "fsa", "aa"));
        } else {
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "GFF Files (*.gff, *.gff3)", "gff", "gff3"));
        }

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

        if (!file.getParent().equals(config.getRememberedDirectory())) {
            config.setRememberedDirectory(file.getParent());
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
