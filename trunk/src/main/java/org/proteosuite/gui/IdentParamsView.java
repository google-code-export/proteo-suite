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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.WorkSpace;
import org.proteosuite.utils.Unimod2MSGPlus;

/**
 * This forms allows to specify different parameters for the search
 * 
 * @param sWorskpace
 *            location
 * @param sMode
 *            type of entry (edit|execute)
 * @author fgonzalez
 */
public class IdentParamsView extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final WorkSpace workSpace = WorkSpace.getInstance();
	private static final String NO_MODS_SELECTED = "--- none selected ---";

	private String sRegex = "";
	private Map<String, String> hmParams = new HashMap<String, String>();
	private Map<String, String> hmUniModsFixed = new HashMap<String, String>();
	private Map<String, String> hmUniModsVar = new HashMap<String, String>();
	private boolean bRun = false;
	private File modificationFile;

	JComboBox<String> jcFragMethod = new JComboBox<String>(new String[] {
			"Default", "CID", "ETD", "HCD" });

	JComboBox<String> jcInstrument = new JComboBox<String>(new String[] {
			"Low-res LCQ/LTQ", "High-res LTQ", "TOF", "Q-Exactive" });

	JComboBox<String> jcMSMSTol = new JComboBox<String>(new String[] { "ppm",
			"Da" });

	JComboBox<String> jcMaxMissedCleavage = new JComboBox<String>(new String[] {
			"non-tryptic", "semi-tryptic", "fully-tryptic peptides only" });

	JComboBox<String> jcOutput = new JComboBox<String>(new String[] {
			"Basic scores only", "Additional features" });

	JComboBox<String> jcProtocol = new JComboBox<String>(new String[] {
			"Default", "Phosphorylation", "iTRAQ", "iTRAQPhospo" });

	JList<String> jlstFixedMods = new JList<String>(
			new DefaultListModel<String>());

	JList<String> jlstUnimods = new JList<String>(
			new DefaultListModel<String>());

	JList<String> jlstVarMods = new JList<String>(
			new DefaultListModel<String>());

	JTextField jtDatabaseFile = new JTextField(50);
	JTextField jtErrorRange = new JTextField("0,1");
	JTextField jtMSMSTol = new JTextField("20");
	JTextField jtMaxCharge = new JTextField("3");
	JTextField jtMaxPepLen = new JTextField("40");
	JTextField jtMinCharge = new JTextField("2");
	JTextField jtMinPepLen = new JTextField("6");
	JTextField jtRegex = new JTextField("XXX");
	JTextField jtSpectraMatches = new JTextField("1");

	public IdentParamsView(Window owner, String sMode) {
		super(owner, "Set Identifications Parameters", Dialog.ModalityType.APPLICATION_MODAL);

		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
				"images/icon.gif")).getImage());
		
		Unimod2MSGPlus getMods = new Unimod2MSGPlus();
		DefaultListModel<String> listModel = (DefaultListModel<String>) jlstUnimods
				.getModel();

		for (List<String> al : getMods.getAllMods()) {
			listModel.addElement(al.get(0));
			// 0=Key, 1=Mass(E.g. 15.994915), 2=site(E.g. M),
			// 3=position(E.g. N-term), 4=psiMod(E.g. Oxidation)
			hmUniModsFixed.put(al.get(0), al.get(1) + "," + al.get(2) + ",fix,"
					+ al.get(3) + "," + al.get(4));
			hmUniModsVar.put(al.get(0), al.get(1) + "," + al.get(2) + ",opt,"
					+ al.get(3) + "," + al.get(4));
		}

		((DefaultListModel<String>) jlstVarMods.getModel())
				.addElement("Oxidation (M)");
		((DefaultListModel<String>) jlstFixedMods.getModel())
				.addElement("Carbamidomethyl (C)");

		// Getting total available processors for multithreading
		jcMaxMissedCleavage.setSelectedIndex(2);

		jtMSMSTol
				.setToolTipText("Precursor Mass Tolerance (E.g. +/- 2.5Da, +/- 20ppm, etc.), Use commas for asymetric values (e.g. 0.5,2.5)");
		jtDatabaseFile.setToolTipText("Enter the path to the database file");
		jtErrorRange
				.setToolTipText("Range of allowed isotope peak errors (E.g. 0,1)");

		jtMaxPepLen.setToolTipText("Maximum peptide length to consider");
		jtMinPepLen.setToolTipText("Minimum peptide length to consider");
		jtMinCharge
				.setToolTipText("Minimum precursor charge to consider if charges are not specified in the spectrum file");
		jtMaxCharge
				.setToolTipText("Maximum precursor charge to consider if charges are not specified in the spectrum file");
		jtSpectraMatches
				.setToolTipText("Number of matches per spectrum to be reported");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setRun(false);
				dispose();
			}
		});
		
		getContentPane().add(createInterface(sMode));
		pack();
	}

	private Component createInterface(String sMode) {
		JPanel content = new JPanel();
		final JComboBox<String> jcEnzyme = new JComboBox<String>(new String[] {
				"unspecified cleavage", "Trypsin", "Chymotrypsin", "Lys-C",
				"Lys-N", "glutamyl endopeptidase", "Arg-C", "Asp-N", "alphaLP",
				"no cleavage" });
		jcEnzyme.setSelectedIndex(1);

		JButton jbSetDefaults = new JButton("Default settings");
		JButton jbSave = new JButton("Save");
		JButton run = new JButton("Run");
		if (sMode.equals("execute")) {
			jbSetDefaults.setVisible(false);
			jbSave.setVisible(false);
			run.setVisible(true);
		} else {
			jbSetDefaults.setVisible(true);
			jbSave.setVisible(true);
			run.setVisible(false);
		}

		final JCheckBox jchbSearchDecoy = new JCheckBox("Search Decoy");

		setMaximumSize(new Dimension(792, 648));
		setMinimumSize(new Dimension(792, 648));

		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jbRunActionPerformed(jtDatabaseFile.getText(),
						jlstFixedMods.getModel(), jlstVarMods.getModel(),
						jcFragMethod.getSelectedIndex(),
						jcInstrument.getSelectedIndex(),
						jcEnzyme.getSelectedIndex(),
						jcProtocol.getSelectedIndex(),
						jcMaxMissedCleavage.getSelectedIndex(),
						jchbSearchDecoy.isSelected());
			}
		});

		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.add(getParametersPanel(jcEnzyme, jchbSearchDecoy));
		content.add(getModificationsPanel());
		content.add(getRow(new JLabel("Regex:"), jtRegex, jbSetDefaults,
				jbSetDefaults, new JLabel("Press escape (ESC) to cancel"), run,
				jbSave));

		return content;
	}

	private void addMods(List<String> selectedMods,
			DefaultListModel<String> modsModel) {
		// Adding values
		if (selectedMods.isEmpty())
			return;

		if (modsModel.getElementAt(0).equals(NO_MODS_SELECTED))
			modsModel.removeAllElements();

		SortedSet<String> allMods = new TreeSet<String>(selectedMods);
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
		jPanel.add(getUniModPanel());
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
			public void actionPerformed(ActionEvent evt) {
				addMods(jlstUnimods.getSelectedValuesList(),
						(DefaultListModel<String>) jlstVarMods.getModel());
			}
		});

		JButton removeMods = new JButton("<");
		removeMods.addActionListener(new ActionListener() {
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

	private JPanel getParametersPanel(JComboBox<String> jcEnzyme,
			JCheckBox jchbSearchDecoy) {

		JPanel jPanel = new JPanel();
		jPanel.setBorder(BorderFactory.createTitledBorder("Parameters:"));

		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		// Database
		JButton jbAddDatabaseFile = new JButton("...");
		jbAddDatabaseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jbAddDatabaseFileActionPerformed(jtDatabaseFile);
			}
		});
		jPanel.add(getRow(new JLabel("Database file:"), jtDatabaseFile,
				jbAddDatabaseFile));

		// MS/MS Tolerance
		jPanel.add(getRow(new JLabel("<html>MS/MS tol. &plusmn;</html>"),
				jtMSMSTol, jcMSMSTol, jchbSearchDecoy));

		// Error Range
		jPanel.add(getRow(new JLabel("Error Range:"), jtErrorRange, new JLabel(
				"Fragmentation Method:"), jcFragMethod, new JLabel(
				"Instrument:"), jcInstrument));

		// Enzyme
		jPanel.add(getRow(new JLabel("Enzyme:"), jcEnzyme, new JLabel(
				"Protocol:"), jcProtocol, new JLabel("Tryptic peptides:"),
				jcMaxMissedCleavage));

		// Min Peptide Length
		jPanel.add(getRow(new JLabel("Min Peptide Length:"), jtMinPepLen,
				new JLabel("Min Charge:"), jtMinCharge, new JLabel(
						"Matches per spectra:"), jtSpectraMatches));

		// Min Peptide Length
		jPanel.add(getRow(new JLabel("Max Peptide Length:"), jtMaxPepLen,
				new JLabel("Max Charge:"), jtMaxCharge, new JLabel("Output:"),
				jcOutput));

		return jPanel;
	}

	public Map<String, String> getParams() {
		return hmParams;
	}

	public String getRegex() {
		return sRegex;
	}

	private JPanel getRow(Component... components) {
		JPanel row = new JPanel();
		row.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (Component c : components) {
			row.add(c);
		}

		return row;
	}

	public boolean getRun() {
		return bRun;
	}

	private JPanel getUniModPanel() {
		JPanel uniMod = new JPanel();
		uniMod.setLayout(new BorderLayout());

		uniMod.add(new JLabel("Unimod modifications:"), BorderLayout.PAGE_START);
		uniMod.add(new JScrollPane(jlstUnimods), BorderLayout.CENTER);

		return uniMod;
	}

	private String getValueUniModFixed(String sValue) {
		return hmUniModsFixed.get(sValue);
	}

	private String getValueUniModVar(String sValue) {
		return hmUniModsVar.get(sValue);
	}

	private void jbAddDatabaseFileActionPerformed(JTextField databaseFile) {
		// Adding files
		JFileChooser chooser = new JFileChooser(workSpace.getWorkSpace());
		chooser.setDialogTitle("Select the database file");
		chooser.setAcceptAllFileFilterUsed(false);

		// Applying file extension filters
		chooser.setFileFilter(new FileNameExtensionFilter(
				"Fasta Files (*.fasta, *.fa)", "fasta", "fa"));

		// Disable multiple file selection
		chooser.setMultiSelectionEnabled(false);

		int returnVal = chooser.showOpenDialog(this);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File file = chooser.getSelectedFile();
		if (file == null)
			return;

		if (file.getName().indexOf(".fasta") == -1) {
			JOptionPane
					.showMessageDialog(
							this,
							"Incorrect file extension. Please select a valid fasta file (*.fasta)",
							"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		databaseFile.setText(file.getPath());
	}

	private void jbRunActionPerformed(String databaseFile,
			ListModel<String> fixedModsModel, ListModel<String> varModsModel,
			int fragMethod, int instrument, int enzyme, int protocol,
			int maxMissedCleavages, boolean searchDecoy) {
		// Validate entries
		if ((databaseFile.isEmpty())
				|| (!databaseFile.toLowerCase().endsWith(".fasta"))) {
			JOptionPane.showMessageDialog(this,
					"Please select a valid fasta file (*.fasta)", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Save current values
		bRun = true;
		hmParams.clear();
		// Spectrum File
		// 0 - Database File Output File
		hmParams.put("-d", databaseFile);
		// 1 - Precursor Mass Tolerance
		hmParams.put("-t", jtMSMSTol.getText()
				+ jcMSMSTol.getSelectedItem().toString());
		// 2 - Isotope Error Range
		hmParams.put("-ti", jtErrorRange.getText());
		// 4 - TDA
		if (searchDecoy)
			hmParams.put("-tda", "1");
		else
			hmParams.put("-tda", "0");
		// 5 - Fragment Method
		hmParams.put("-m", Integer.toString(fragMethod));
		// 6 - Instrument ID
		hmParams.put("-inst", Integer.toString(instrument));
		// 7 - Enzyme
		hmParams.put("-e", Integer.toString(enzyme));
		// 8 - Protocol ID
		hmParams.put("-protocol", Integer.toString(protocol));
		// 9 - Number of tolerable termini
		hmParams.put("-ntt", Integer.toString(maxMissedCleavages));

		// 10 - Modification file name
		int iSizeFixed = fixedModsModel.getSize();
		int iSizeVar = varModsModel.getSize();
		if (iSizeFixed > 0 || iSizeVar > 0) {
			// Write file
			FileWriter fileStream = null;
			BufferedWriter out = null;
			modificationFile = new File("Mods.txt");
			try {
				fileStream = new FileWriter(modificationFile);

				out = new BufferedWriter(fileStream);
				out.write("NumMods=" + (iSizeFixed + iSizeVar));
				out.newLine();
				for (int i = 0; i < iSizeFixed; i++) {
					out.write(getValueUniModFixed(fixedModsModel
							.getElementAt(i)));
					out.newLine();
				}
				for (int i = 0; i < iSizeVar; i++) {
					out.write(getValueUniModVar(varModsModel.getElementAt(i)));
					out.newLine();
				}
			} catch (IOException ex) {
				Logger.getLogger(IdentParamsView.class.getName()).log(
						Level.SEVERE, null, ex);
			} finally {
				try {
					if (out != null)
						out.close();
					if (fileStream != null)
						fileStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 11 - Min peptide length
		hmParams.put("-minLength", jtMinPepLen.getText());
		// 12 - Max peptide length
		hmParams.put("-maxLength", jtMaxPepLen.getText());
		// 13 - Min charge
		hmParams.put("-minCharge", jtMinCharge.getText());
		// 14 - Max charge
		hmParams.put("-maxCharge", jtMaxCharge.getText());
		// 15 - Num matches per spectrum
		hmParams.put("-n", jtSpectraMatches.getText());
		// 16 - Additional features
		hmParams.put("-addFeatures",
				Integer.toString(jcOutput.getSelectedIndex()));
		sRegex = jtRegex.getText();

		dispose();
	}

	private void removeMods(JList<String> modList) {
		// Remove Modifications
		DefaultListModel<String> modsModel = (DefaultListModel<String>) modList
				.getModel();

		for (String mod : modList.getSelectedValuesList()) {
			modsModel.removeElement(mod);
		}

		if (modsModel.isEmpty())
			modsModel.addElement(NO_MODS_SELECTED);
	}

	public void setRun(boolean bRun) {
		this.bRun = bRun;
	}
}