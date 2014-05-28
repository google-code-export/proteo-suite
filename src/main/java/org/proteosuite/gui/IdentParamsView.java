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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import org.proteosuite.WorkSpace;
import org.proteosuite.utils.NumericalUtils;
import org.proteosuite.utils.StringUtils;

/**
 * This forms allows to specify different parameters for the search.
 *
 * @author fgonzalez
 */
public class IdentParamsView extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final Pattern possibleModsFilePattern = Pattern.compile("(.+)\\(.+\\)");
    private static final WorkSpace workSpace = WorkSpace.getInstance();
    private static final String NO_MODS_SELECTED = "--- none selected ---";
    private static Set<String> possibleMods = null;

    private boolean genomeAnnotationMode = false;

    private boolean hasRunSuccessfully = false;
    private File modificationFile;

    private final JComboBox<String> jcPrecursorToleranceUnits = new JComboBox<>(new String[]{"ppm",
        "Da"});

    private final JComboBox<String> jcFragmentToleranceUnits = new JComboBox<>(new String[]{"ppm",
        "Da"});

    private final JComboBox<Integer> jcMaxMissedCleavages = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5,});  

    private final JList<String> jlstFixedMods = new JList<>(
            new DefaultListModel<String>());

    private final JList<String> jlstUnimods = new JList<>(
            new DefaultListModel<String>());

    private final JList<String> jlstVarMods = new JList<>(
            new DefaultListModel<String>());

    private final JTextField jtDatabaseFile = new JTextField(50);
    
    private final JTextField jtPrefix = new JTextField(10);
    
    private JTextField jtMSTolerance;
    private JTextField jtMSMSTolerance;
    private JComboBox<Integer> jcMinCharge;
    private JComboBox<Integer> jcMaxCharge;    
 
    private JComboBox<Character> jcFragmentIonTypesABC;
    private JComboBox<Character> jcFragmentIonTypesXYZ;
    private JCheckBox jcDoProteoGrouper;
    private JCheckBox jcDoEmPAI;
    private JComboBox<String> jcEnzyme;

    private DatabasePanel databasePanel;

    public IdentParamsView(Window owner, boolean genomeAnnotationMode) {
        super(owner, "Set Identifications Parameters", Dialog.ModalityType.APPLICATION_MODAL);

        this.genomeAnnotationMode = genomeAnnotationMode;

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
                "images/icon.gif")).getImage());

        if (possibleMods == null) {
            readInPossibleMods();
        }

        DefaultListModel<String> listModel = (DefaultListModel<String>) jlstUnimods
                .getModel();

        for (String possibleMod : possibleMods) {
            listModel.addElement(possibleMod);
        }

        ((DefaultListModel<String>) jlstVarMods.getModel())
                .addElement("oxidation of m");
        ((DefaultListModel<String>) jlstFixedMods.getModel())
                .addElement("carbamidomethyl c");

        // Getting total available processors for multithreading
        jcMaxMissedCleavages.setSelectedIndex(1);

        jtDatabaseFile.setToolTipText("Enter the path to the database file");        

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                hasRunSuccessfully = false;
                dispose();
            }
        });

        getContentPane().add(createInterface());
        pack();
    }

    public static void readInPossibleMods() {
        possibleMods = new TreeSet<>();
        Class thisClass = IdentParamsView.class;
        ClassLoader thisClassLoader = thisClass.getClassLoader();
        InputStream thisResource = thisClassLoader.getResourceAsStream("config/possibleMods.txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(thisResource));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Matcher m = possibleModsFilePattern.matcher(line);
                if (m.matches()) {
                    possibleMods.add(m.group(1));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Component createInterface() {
        JPanel content = new JPanel();

        JButton run = new JButton("Run");

        run.setVisible(true);

        

        setMaximumSize(new Dimension(792, 648));
        setMinimumSize(new Dimension(792, 648));

        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jbRunActionPerformed(jtDatabaseFile.getText(),
                        jlstFixedMods.getModel(), jlstVarMods.getModel(),
                        jcMaxMissedCleavages.getSelectedIndex());
            }
        });

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(getDatabasePanel());
        content.add(getParametersPanel());
        content.add(getModificationsPanel());
        content.add(getRow(run));

        return content;
    }

    private void addMods(List<String> selectedMods,
            DefaultListModel<String> modsModel) {
        // Adding values
        if (selectedMods.isEmpty()) {
            return;
        }

        if (modsModel.getElementAt(0).equals(NO_MODS_SELECTED)) {
            modsModel.removeAllElements();
        }

        SortedSet<String> allMods = new TreeSet<>(selectedMods);
        for (int i = 0; i < modsModel.getSize(); i++) {
            allMods.add(modsModel.getElementAt(i));
        }

        modsModel.removeAllElements();
        for (String mod : allMods) {
            modsModel.addElement(mod);
        }
    }

    public File getModificationFile() {
        return modificationFile;
    }

    private JPanel getModificationsPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createTitledBorder("Modifications:"));

        jPanel.setLayout(new GridLayout(1, 2));
        jPanel.add(getPossibleModsPanel());
        jPanel.add(getModsPanel());

        return jPanel;
    }

    private JPanel getModsPanel() {
        JPanel modsPanel = new JPanel();
        modsPanel.setLayout(new GridLayout(2, 1));

        modsPanel.add(getModsPanel("Fixed Modifications:", jlstUnimods,
                jlstFixedMods));
        modsPanel.add(getModsPanel("Variable Modifications:", jlstUnimods,
                jlstVarMods));

        return modsPanel;
    }

    private JPanel getModsPanel(String title, final JList<String> jlstUnimods,
            final JList<String> jlstVarMods) {
        JButton addMods = new JButton(">");
        addMods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                addMods(jlstUnimods.getSelectedValuesList(),
                        (DefaultListModel<String>) jlstVarMods.getModel());
            }
        });

        JButton removeMods = new JButton("<");
        removeMods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeMods(jlstVarMods);
            }
        });

        JPanel modsPanel = new JPanel();
        modsPanel.setLayout(new BorderLayout());
        modsPanel.add(new JLabel(title), BorderLayout.PAGE_START);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(addMods);
        buttonPanel.add(removeMods);
        modsPanel.add(buttonPanel, BorderLayout.LINE_START);

        modsPanel.add(new JScrollPane(jlstVarMods), BorderLayout.CENTER);

        return modsPanel;
    }

    private JPanel getDatabasePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Databases:"));
        this.databasePanel = new DatabasePanel(genomeAnnotationMode, this);
        panel.add(this.databasePanel);

        return panel;
    }

    private JPanel getParametersPanel() {

        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createTitledBorder("Parameters:"));

        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        this.jcEnzyme = new JComboBox<>(new String[]{
            "Arg-C", "Arg-N", "Asp-N (DE)", "Asp-N + Glu-C",
            "CNBr", "Chymotrypsin (FYWL)", "Chymotrypsin, no P rule (FYWL)", "Formic Acid", "Glu-C",
            "Glu-C (DE)", "Lys-C", "Lys-C, no P rule", "Lys-N (K)", "No Enzyme", "Pepsin A",
            "Semi-Chyomotrypsin (FYWL)", "Semi-Glu-C", "Semi-Tryptic", "Thermolysin, no P rule",
            "Top-Down", "Trypsin", "Trypsin + CNBr", "Trypsin + Chymotrypsin (FYWLKR)", "Trypsin, no P rule",
            "Whole Protein"});
        jcEnzyme.setSelectedIndex(20);

        this.jcFragmentIonTypesXYZ = new JComboBox<>(new Character[]{'x', 'y', 'z'});
        this.jcFragmentIonTypesXYZ.setSelectedIndex(1);
        this.jcFragmentIonTypesABC = new JComboBox<>(new Character[]{'a', 'b', 'c'});
        this.jcFragmentIonTypesABC.setSelectedIndex(1);
        this.jtMSMSTolerance = new JTextField("5");
        this.jtMSTolerance = new JTextField("10");
        this.jcMinCharge = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        this.jcMinCharge.setSelectedIndex(1);
        this.jcMaxCharge = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        this.jcMaxCharge.setSelectedIndex(3);
        this.jcDoProteoGrouper = new JCheckBox("Group Proteins?");
        this.jcDoEmPAI = new JCheckBox("Calculate emPAI?");
        jtMSTolerance
                .setToolTipText("Precursor Mass Tolerance (E.g. +/- 2.5Da, +/- 20ppm)");

        // Tolerances
        jPanel.add(getRow(new JLabel("<html>Precursor MS tolerance &plusmn;</html>"), jtMSTolerance, jcPrecursorToleranceUnits));

        jPanel.add(getRow(new JLabel("<html>Fragment MS/MS tolerance &plusmn;</html>"),
                jtMSMSTolerance, jcFragmentToleranceUnits));

        jPanel.add(getRow(new JLabel("Fragment Ion Types:"), jcFragmentIonTypesABC, jcFragmentIonTypesXYZ));

        // Enzyme
        jPanel.add(getRow(new JLabel("Enzyme:"), jcEnzyme, new JLabel("Maximum missed cleavages:"),
                jcMaxMissedCleavages));

        jPanel.add(getRow(new JLabel("Precursor Charge Range:"), jcMinCharge, new JLabel("-"), jcMaxCharge));
        
        jPanel.add(getRow(new JLabel("Output File Prefix:"), jtPrefix));

        if (!this.genomeAnnotationMode) {
            jPanel.add(getRow(jcDoProteoGrouper, jcDoEmPAI));
        }

        return jPanel;
    }

    private JPanel getRow(Component... components) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Component c : components) {
            row.add(c);
        }

        return row;
    }

    public boolean hasRunSuccessfully() {
        return hasRunSuccessfully;
    }

    private JPanel getPossibleModsPanel() {
        JPanel uniMod = new JPanel();
        uniMod.setLayout(new BorderLayout());

        uniMod.add(new JLabel("Possible Modifications:"), BorderLayout.PAGE_START);
        uniMod.add(new JScrollPane(jlstUnimods), BorderLayout.CENTER);

        return uniMod;
    }

    private void jbRunActionPerformed(String databaseFile,
            ListModel<String> fixedModsModel, ListModel<String> varModsModel,
            int maxMissedCleavages) {

        // Validate entries
        Set<String> validationErrors = validateFields();
        if (validationErrors.size() > 0) {
            JOptionPane.showMessageDialog(this,
                    StringUtils.join("\n", validationErrors), "Invalid Parameters",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        hasRunSuccessfully = true;
        dispose();
    }
    
    public String getOutputFilePrefix() {
        return jtPrefix.getText();
    }

    public Map<String, String> getSearchGUIParameterSet() {
        Map<String, String> parameterSet = new HashMap<>();
        parameterSet.put("prec_ppm", ((String) jcPrecursorToleranceUnits.getSelectedItem()).equals("ppm") ? "1" : "2");
        parameterSet.put("prec_tol", jtMSTolerance.getText());
        parameterSet.put("frag_ppm", ((String) jcFragmentToleranceUnits.getSelectedItem()).equals("ppm") ? "1" : "2");
        parameterSet.put("frag_tol", jtMSMSTolerance.getText());
        parameterSet.put("enzyme", (String) jcEnzyme.getSelectedItem());
        parameterSet.put("min_charge", String.valueOf((Integer)jcMinCharge.getSelectedItem()));
        parameterSet.put("max_charge", String.valueOf((Integer)jcMaxCharge.getSelectedItem()));
        parameterSet.put("mc", (String) String.valueOf((Integer)jcMaxMissedCleavages.getSelectedItem()));
        parameterSet.put("fi", String.valueOf(jcFragmentIonTypesABC.getSelectedItem()));
        parameterSet.put("ri", String.valueOf(jcFragmentIonTypesXYZ.getSelectedItem()));
        parameterSet.put("fixed_mods", StringUtils.join(", ", jlstFixedMods.getModel()));
        parameterSet.put("variable_mods", StringUtils.join(", ", jlstVarMods.getModel()));

        return parameterSet;
    }

    public String getSingleDatabasePath() {
        return databasePanel.getSingleDatabasePath();
    }

    public Map<String, String> getOtherGeneModels() {
        return databasePanel.getOtherGeneModels();
    }

    public String[] getGeneModel() {
        return databasePanel.getGeneModel();
    }

    public boolean isRequestingProteoGrouper() {
        return jcDoProteoGrouper.isSelected();
    }

    public boolean isRequestingEmPAI() {
        return jcDoEmPAI.isSelected();
    }

    private Set<String> validateFields() {
        Set<String> validationErrors = new HashSet<>();

        if (this.genomeAnnotationMode) {
            String[] geneModels = this.getGeneModel();
            if (geneModels[0] == null || geneModels[0].isEmpty()) {
                validationErrors.add("GFF file is mandatory. FastA is optional. Please amend.");
            } else {
                if (!geneModels[0].toUpperCase().endsWith(".GFF")) {
                    validationErrors.add("GFF file has not been correctly set. Please amend.");
                }
                
                if (geneModels[1] != null && !geneModels[1].isEmpty() && !geneModels[1].toUpperCase().endsWith(".FASTA")) {
                    validationErrors.add("FastA file has not been correctly set. Please amend.");
                }
            }
            
            Map<String, String> otherModels = this.getOtherGeneModels();
            for (Entry<String, String> otherModel : otherModels.entrySet()) {
                if (otherModel.getKey() == null || otherModel.getKey().isEmpty()) {
                    validationErrors.add("GFF file is mandatory for other gene models. FastA is optional. Please amend.");
                }
            }
        } else {
            if (jtDatabaseFile.getText().isEmpty() || (!jtDatabaseFile.getText().toUpperCase().endsWith(".FASTA"))) {
                validationErrors.add("FastA file has not been correctly set. Please amend.");
            }
        }

        if (!NumericalUtils.isDouble(jtMSTolerance.getText())) {
            validationErrors.add("Precursor MS tolerance is not a valid number. Please amend.");
        }

        if (!NumericalUtils.isDouble(jtMSMSTolerance.getText())) {
            validationErrors.add("Fragment MS/MS tolerance is not a valid number. Please amend.");
        }

        if ((Integer) jcMinCharge.getSelectedItem() > (Integer) jcMaxCharge.getSelectedItem()) {
            validationErrors.add("Minimum charge is higher than maximum charge. Please amend.");
        }

        return validationErrors;
    }

    private void removeMods(JList<String> modList) {
        // Remove Modifications
        DefaultListModel<String> modsModel = (DefaultListModel<String>) modList
                .getModel();

        for (String mod : modList.getSelectedValuesList()) {
            modsModel.removeElement(mod);
        }

        if (modsModel.isEmpty()) {
            modsModel.addElement(NO_MODS_SELECTED);
        }
    }

}
