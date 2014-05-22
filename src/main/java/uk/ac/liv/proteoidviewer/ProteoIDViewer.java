/*
 * ProteoIDViewer.java
 *
 * Created on 15-Dec-2010, 10:34:37
 * 
 * Fawaz Ghali
 * 
 */
package uk.ac.liv.proteoidviewer;

import uk.ac.liv.proteoidviewer.listener.exportFDRActionPerformed;
import uk.ac.liv.proteoidviewer.listener.jMenuItem1ActionPerformed;
import uk.ac.liv.proteoidviewer.listener.jMenuItem2ActionPerformed;
import uk.ac.liv.proteoidviewer.listener.jMenuItem3ActionPerformed;
import uk.ac.liv.proteoidviewer.listener.mainTabbedPaneMouseClicked;
import uk.ac.liv.proteoidviewer.listener.manualCalculateActionPerformed;
import uk.ac.liv.proteoidviewer.listener.manualDecoyActionPerformed;
import uk.ac.liv.proteoidviewer.listener.openMenuItemActionPerformed;
import uk.ac.liv.proteoidviewer.listener.peptideEvidenceTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.proteinAmbiguityGroupTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.proteinDetectionHypothesisTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.psmRankValueActionPerformed;
import uk.ac.liv.proteoidviewer.listener.siiComboBoxActionPerformed;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemProteinTableeMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemTablePeptideViewMouseClicked;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;
import uk.ac.liv.proteoidviewer.util.XmlFilter;
import uk.ac.liv.proteoidviewer.util.OmssaFilter;
import uk.ac.liv.proteoidviewer.util.SourceFileFilter;

import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Fragmentation;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.util.MzIdentMLFilter;
import uk.ac.liv.proteoidviewer.util.ProgressBarDialog;

import com.compomics.util.gui.spectrum.SpectrumPanel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringEscapeUtils;

import uk.ac.liv.mzidlib.FalseDiscoveryRate;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;
import uk.ac.ebi.pride.tools.mzml_wrapper.MzMlWrapper;
import uk.ac.liv.mzidconverters.Tandem2mzid;

/**
 *
 * @author Fawaz Ghali
 */
public class ProteoIDViewer extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String[] peptideEvidenceTableHeaders = { "Start",
			"End", "Pre", "Post", "IsDecoy", "Peptide Sequence",
			"dBSequence_ref" };
	private static final String[] fragmentationTableHeaders = { "M/Z",
			"Intensity", "M Error", "Ion Type", "Charge" };
	private static final String[] dBSequenceTableHeaders = new String[] { "ID",
			"Accession", "Seq", "Protein Description" };
	private static final String[] proteinAmbiguityGroupTableHeaders = new String[] {
			"ID", "Name", "Protein Accessions", "Representative Protein",
			"Scores", "P-values", "Number of peptides", "Is Decoy",
			"passThreshold" };
	private static final String[] proteinDetectionHypothesisTableHeaders = new String[] {
			"ID", "Accession", "Scores", "P-values", "Number of peptides",
			"Is Decoy", "passThreshold" };
	private static final String[] spectrumIdentificationItemProteinViewTableHeaders = new String[] {
			"Peptide Sequence", "SII", "Name", "Score", "Expectation value",
			"passThreshold" };

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		if (args != null && args.length == 1) {
			ProteoIDViewer proteoIDViewer = new ProteoIDViewer();
			proteoIDViewer = new ProteoIDViewer();
			proteoIDViewer.setVisible(true);
			proteoIDViewer.openCLI(args[0]);
		} else {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					new ProteoIDViewer().setVisible(true);
				}
			});
		}
	}

	// GUI
	private final List<String> filterListIon = new ArrayList<>();
	private final List<String> filterListCharge = new ArrayList<>();
	private final List<String> filterListIon1 = new ArrayList<>();
	private final List<String> filterListCharge1 = new ArrayList<>();
	private final List<SpectrumAnnotation> peakAnnotation = new ArrayList<>();
	// FDR and Stats
	private final List<SpectrumIdentificationItem> sIIListPassThreshold = new ArrayList<>();
	private final Map<String, String> siiSirMap = new HashMap<>();
	private final Map<String, String> cvTermMap = new HashMap<>();
	// stats
	public FalseDiscoveryRate falseDiscoveryRate = null;
	public double falsePositiveSii;
	public double truePositiveSii;
	public double fdrSii;
	// Lists to store mzIdentML data
	private final List<ProteinDetectionHypothesis> pDHListPassThreshold = new ArrayList<>();
	private List<SpectrumIdentificationItem> spectrumIdentificationItemListForSpecificResult;
	public boolean secondTab;
	public boolean thirdTab;
	public boolean fourthTab;
	public boolean fifthTab;
	public boolean sixthTab;
	public JMzReader jmzreader = null;
	public File fileName = null;
	public String sourceFile = "";
	// maps to store mzIdentML data

	public MzIdentMLUnmarshaller mzIdentMLUnmarshaller = null;

	// GUI tables
	private final JTable proteinAmbiguityGroupTable = new JTable();
	private final JTable proteinDetectionHypothesisTable = new JTable();
	private final JTable spectrumIdentificationItemProteinViewTable = new JTable();
	private final JTable spectrumIdentificationResultTable = new JTable();
	private final JTable spectrumIdentificationItemTable = new JTable();
	private final JTable peptideEvidenceTable = new JTable();
	private final JTable fragmentationTable = new JTable();
	private final JTable spectrumIdentificationItemTablePeptideView = new JTable();
	private final JTable dBSequenceTable = new JTable();
	private final JTable fragmentationTablePeptideView = new JTable();
	private final JTable peptideEvidenceTablePeptideView = new JTable();
	private final JPanel dBSequencePanel = new JPanel();
	private final JPanel fdrPanel = new JPanel();
	private final JLabel fdrSiiValue = new JLabel();
	private final JFileChooser fileChooser = new JFileChooser();
	private final JLabel fpSiiValue = new JLabel();
	private final JLabel isDecoySiiFalseValue = new JLabel();
	private final JLabel isDecoySiiValue = new JLabel();
	private final JComboBox<String> jComboBox1 = new JComboBox<>();
	private final JComboBox<String> jComboBox2 = new JComboBox<>();
	private final JPanel jExperimentalFilterPanel = new JPanel();
	private final JPanel jExperimentalFilterPanel1 = new JPanel();
	private final JPanel jFragmentationPanel = new JPanel();
	private final JPanel jFragmentationPanel1 = new JPanel();
	private final JPanel jGraph = new JPanel();
	private final JPanel jGraph1 = new JPanel();
	private final JPanel jPeptideEvidencePanel = new JPanel();
	private final JPanel jPeptideEvidencePanel1 = new JPanel();
	private final JPanel jProteinAmbiguityGroupPanel = new JPanel();
	private final JEditorPane jProteinDescriptionEditorPane = new JEditorPane();
	private final JPanel jProteinDetectionHypothesisPanel = new JPanel();
	private final JTextPane jProteinSequenceTextPane = new JTextPane();
	private final JLabel jScientificNameValueLabel = new JLabel();
	private final JPanel jSpectrumIdentificationItemPanel = new JPanel();
	private final JPanel jSpectrumIdentificationItemPanel1 = new JPanel();
	private final JPanel jSpectrumIdentificationItemProteinPanel = new JPanel();
	private final JPanel jSpectrumIdentificationResultPanel = new JPanel();
	private final JTabbedPane mainTabbedPane = new JTabbedPane();
	private final JCheckBox manualDecoy = new JCheckBox();
	private final JTextField manualDecoyPrefixValue = new JTextField();
	private final JTextField manualDecoyRatioValue = new JTextField();
	private final JLabel percentIdentifiedSpectraLabelValue = new JLabel();
	private final JComboBox<String> psmRankValue = new JComboBox<>();
	private final JComboBox<String> siiComboBox = new JComboBox<>();
	private final JLabel totalPAGsLabelValue = new JLabel();
	private final JLabel totalPDHsaboveThresholdLabelValue = new JLabel();
	private final JLabel totalPeptidesaboveThresholdLabelValue = new JLabel();
	private final JLabel totalSIIaboveThresholdLabelValue = new JLabel();
	private final JLabel totalSIIaboveThresholdRankOneLabelValue = new JLabel();
	private final JLabel totalSIIbelowThresholdLabelValue = new JLabel();
	private final JLabel totalSIRLabelValue = new JLabel();
	private final JPanel tpEvaluePanel = new JPanel();
	private final JPanel tpQvaluePanel = new JPanel();
	private final JLabel tpSiiValue = new JLabel();

	/**
	 * Creates new form ProteoIDViewer
	 */
	public ProteoIDViewer() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ProteoIDViewer");
		setMinimumSize(new Dimension(900, 800));

		setJMenuBar(createMenuBar());
		
		
		// Swing init components
		initComponents();

		// protein tab
		// protein Ambiguity Group Table
		proteinAmbiguityGroupTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		proteinAmbiguityGroupTable
				.addMouseListener(new proteinAmbiguityGroupTableMouseClicked(
						this, jProteinSequenceTextPane,
						jScientificNameValueLabel, proteinAmbiguityGroupTable,
						spectrumIdentificationItemProteinViewTable,
						proteinDetectionHypothesisTable));
		jProteinAmbiguityGroupPanel.setLayout(new BorderLayout());
		jProteinAmbiguityGroupPanel.add(new JScrollPane(
				proteinAmbiguityGroupTable));
		proteinAmbiguityGroupTable.getTableHeader().setReorderingAllowed(false);
		// protein Detection Hypothesis Table
		proteinDetectionHypothesisTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		proteinDetectionHypothesisTable
				.addMouseListener(new proteinDetectionHypothesisTableMouseClicked(
						this, proteinDetectionHypothesisTable,
						spectrumIdentificationItemProteinViewTable,
						jScientificNameValueLabel,
						jProteinDescriptionEditorPane, jProteinSequenceTextPane));

		jProteinDetectionHypothesisPanel.setLayout(new BorderLayout());
		jProteinDetectionHypothesisPanel.add(new JScrollPane(
				proteinDetectionHypothesisTable));
		proteinDetectionHypothesisTable.getTableHeader().setReorderingAllowed(
				false);
		// spectrum Identification Item Protein View Table
		spectrumIdentificationItemProteinViewTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		spectrumIdentificationItemProteinViewTable
				.addMouseListener(new spectrumIdentificationItemProteinTableeMouseClicked(
						this, spectrumIdentificationItemProteinViewTable,
						spectrumIdentificationResultTable,
						spectrumIdentificationItemTable, mainTabbedPane));
		jSpectrumIdentificationItemProteinPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationItemProteinPanel.add(new JScrollPane(
				spectrumIdentificationItemProteinViewTable));
		spectrumIdentificationItemProteinViewTable.getTableHeader()
				.setReorderingAllowed(false);
		// spectrum tab
		// spectrum Identification Result Table
		spectrumIdentificationResultTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationResultTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				spectrumIdentificationResultTableMouseClicked();
			}
		});
		jSpectrumIdentificationResultPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationResultPanel.add(new JScrollPane(
				spectrumIdentificationResultTable));
		spectrumIdentificationResultTable.getTableHeader()
				.setReorderingAllowed(false);

		// spectrum Identification Item Table

		jSpectrumIdentificationItemPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationItemPanel.add(new JScrollPane(
				spectrumIdentificationItemTable));
		spectrumIdentificationItemTable.getTableHeader().setReorderingAllowed(
				false);
		spectrumIdentificationItemTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationItemTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				spectrumIdentificationItemTableMouseClicked(spectrumIdentificationItemTable.getSelectedRow());
			}
		});

		// peptide Evidence Table

		jPeptideEvidencePanel.setLayout(new BorderLayout());
		jPeptideEvidencePanel.add(new JScrollPane(peptideEvidenceTable));
		peptideEvidenceTable.getTableHeader().setReorderingAllowed(false);
		peptideEvidenceTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		peptideEvidenceTable
				.addMouseListener(new peptideEvidenceTableMouseClicked(this,
						peptideEvidenceTable, dBSequenceTable, mainTabbedPane));

		// fragmentation Table
		fragmentationTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jFragmentationPanel.setLayout(new BorderLayout());
		jFragmentationPanel.add(new JScrollPane(fragmentationTable));
		fragmentationTable.getTableHeader().setReorderingAllowed(false);

		// peptide view
		// spectrum Identification Item Table

		// spectrumIdentificationItemTablePeptideView.setAutoCreateRowSorter(true);
		JScrollPane jSpectrumIdentificationItemTablePeptideViewScrollPane = new JScrollPane(
				spectrumIdentificationItemTablePeptideView);
		jSpectrumIdentificationItemPanel1.setLayout(new BorderLayout());
		jSpectrumIdentificationItemPanel1
				.add(jSpectrumIdentificationItemTablePeptideViewScrollPane);
		spectrumIdentificationItemTablePeptideView.getTableHeader()
				.setReorderingAllowed(false);
		spectrumIdentificationItemTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationItemTablePeptideView
				.addMouseListener(new spectrumIdentificationItemTablePeptideViewMouseClicked(
						this, spectrumIdentificationItemTablePeptideView,
						fragmentationTablePeptideView,
						peptideEvidenceTablePeptideView, filterListIon1,
						filterListCharge1, jGraph1, siiSirMap));

		// peptide Evidence Table

		JScrollPane jPeptideEvidenceTableScrollPane1 = new JScrollPane(
				peptideEvidenceTablePeptideView);
		jPeptideEvidencePanel1.setLayout(new BorderLayout());
		jPeptideEvidencePanel1.add(jPeptideEvidenceTableScrollPane1);
		peptideEvidenceTablePeptideView.getTableHeader().setReorderingAllowed(
				false);
		peptideEvidenceTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// fragmentation Table
		fragmentationTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jFragmentationTablePeptideViewScrollPane = new JScrollPane(
				fragmentationTablePeptideView);
		jFragmentationPanel1.setLayout(new BorderLayout());
		jFragmentationPanel1.add(jFragmentationTablePeptideViewScrollPane);
		fragmentationTablePeptideView.getTableHeader().setReorderingAllowed(
				false);

		// dBSequenceTable Table
		dBSequenceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dBSequencePanel.setLayout(new BorderLayout());
		dBSequencePanel.add(new JScrollPane(dBSequenceTable));
		dBSequenceTable.getTableHeader().setReorderingAllowed(false);

		spectrumIdentificationResultTable
				.setToolTipText("this corresponds to Spectrum Identification Result in mzIdentML");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width, dim.height - 40);
		setLocationRelativeTo(getRootPane());

		fileChooser.addChoosableFileFilter(new OmssaFilter());
		fileChooser.addChoosableFileFilter(new XmlFilter());
		fileChooser.addChoosableFileFilter(new MzIdentMLFilter());
		repaint();
	}

	public boolean checkIfProteinDetectionHypothesisIsDecoy(
			ProteinDetectionHypothesis proteinDetectionHypothesis) {
		boolean result = false;
		List<PeptideHypothesis> PeptideHyposthesisList = proteinDetectionHypothesis
				.getPeptideHypothesis();
		for (int i = 0; i < PeptideHyposthesisList.size(); i++) {
			try {
				PeptideHypothesis peptideHypothesis = PeptideHyposthesisList
						.get(i);
				String peptidRef = peptideHypothesis.getPeptideEvidenceRef();
				PeptideEvidence peptiedEvidence = mzIdentMLUnmarshaller
						.unmarshal(PeptideEvidence.class, peptidRef);
				if (peptiedEvidence.isIsDecoy()) {
					result = true;
					break;
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
		return result;
	}

	private boolean checkIfSpectrumIdentificationItemIsDecoy(
			SpectrumIdentificationItem spectrumIdentificationItem) {
		boolean result = false;

		List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
				.getPeptideEvidenceRef();
		for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
			try {
				PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
						.get(i);
				PeptideEvidence peptiedEvidence = mzIdentMLUnmarshaller
						.unmarshal(PeptideEvidence.class,
								peptideEvidenceRef.getPeptideEvidenceRef());
				if (peptiedEvidence != null && peptiedEvidence.isIsDecoy()) {
					result = true;
					break;
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
		return result;
	}

	public void clearSummaryStats() {

		totalSIRLabelValue.setText("0");
		totalSIIaboveThresholdLabelValue.setText("0");
		totalSIIbelowThresholdLabelValue.setText("0");
		totalSIIaboveThresholdRankOneLabelValue.setText("0");
		percentIdentifiedSpectraLabelValue.setText("0");
		totalPeptidesaboveThresholdLabelValue.setText("0");
		totalPAGsLabelValue.setText("0");
		totalPDHsaboveThresholdLabelValue.setText("0");
		isDecoySiiValue.setText("0");
		isDecoySiiFalseValue.setText("0");

		fpSiiValue.setText("0");
		tpSiiValue.setText("0");
		fdrSiiValue.setText("0");
		siiComboBox.removeAllItems();

		fdrPanel.removeAll();
		fdrPanel.validate();
		fdrPanel.repaint();

		tpEvaluePanel.removeAll();
		tpEvaluePanel.validate();
		tpEvaluePanel.repaint();

		tpQvaluePanel.removeAll();
		tpQvaluePanel.validate();
		tpQvaluePanel.repaint();

	}

	public void createTables() {
		jComboBox1.removeAllItems();
		// dBSequence view dBSequenceTable
		// dBSequenceTable.setAutoCreateRowSorter(true);
		dBSequenceTable.setModel(new DefaultTableModel(new Object[][] {},
				dBSequenceTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		dBSequenceTable.removeAll();

		// protein view
		proteinAmbiguityGroupTable.setModel(new DefaultTableModel(
				new Object[][] {}, proteinAmbiguityGroupTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// proteinAmbiguityGroupTable.setAutoCreateRowSorter(true);
		proteinDetectionHypothesisTable.setModel(new DefaultTableModel(
				new Object[][] {}, proteinDetectionHypothesisTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// proteinDetectionHypothesisTable.setAutoCreateRowSorter(true);
		spectrumIdentificationItemProteinViewTable
				.setModel(new DefaultTableModel(new Object[][] {},
						spectrumIdentificationItemProteinViewTableHeaders) {
					private static final long serialVersionUID = 1L;
				});
		// spectrumIdentificationItemProteinViewTable.setAutoCreateRowSorter(true);
		jProteinSequenceTextPane.setText("");

		proteinAmbiguityGroupTable.removeAll();
		proteinDetectionHypothesisTable.removeAll();
		spectrumIdentificationItemProteinViewTable.removeAll();

		// spectrum view
		int spectrumIdentificationResultCvParamLengs = 0;
		int spectrumIdentificationItemCvParamLengs = 0;
		String[] sir = null;
		String[] sii = null;
		List<SpectrumIdentificationResult> spectrumIdentificationResultList = new ArrayList<>();

		Iterator<SpectrumIdentificationList> iterspectrumIdentificationList = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationList);
		while (iterspectrumIdentificationList.hasNext()) {
			SpectrumIdentificationList spectrumIdentificationList = iterspectrumIdentificationList
					.next();

			for (int j = 0; j < spectrumIdentificationList
					.getSpectrumIdentificationResult().size(); j++) {
				spectrumIdentificationResultList.add(spectrumIdentificationList
						.getSpectrumIdentificationResult().get(j));
			}
		}

		if (spectrumIdentificationResultList.size() > 0) {

			for (int i = 0; i < spectrumIdentificationResultList.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = spectrumIdentificationResultList
						.get(i);
				if (spectrumIdentificationResultCvParamLengs < spectrumIdentificationResult
						.getCvParam().size()) {
					spectrumIdentificationResultCvParamLengs = spectrumIdentificationResult
							.getCvParam().size();
					sir = new String[spectrumIdentificationResult.getCvParam()
							.size()];
					for (int j = 0; j < spectrumIdentificationResult
							.getCvParam().size(); j++) {
						sir[j] = spectrumIdentificationResult.getCvParam()
								.get(j).getName();
					}
				}
				List<SpectrumIdentificationItem> spectrumIdentificationItemList = spectrumIdentificationResult
						.getSpectrumIdentificationItem();
				for (int j = 0; j < spectrumIdentificationItemList.size(); j++) {
					SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemList
							.get(j);
					if (spectrumIdentificationItemCvParamLengs < spectrumIdentificationItem
							.getCvParam().size()) {
						spectrumIdentificationItemCvParamLengs = spectrumIdentificationItem
								.getCvParam().size();
					}
					sii = new String[spectrumIdentificationItem.getCvParam()
							.size()];
					for (int k = 0; k < spectrumIdentificationItem.getCvParam()
							.size(); k++) {
						sii[k] = spectrumIdentificationItem.getCvParam().get(k)
								.getName();

					}

				}
			}
		}

		String[] spectrumIdentificationResultTableHeaders = new String[spectrumIdentificationResultCvParamLengs + 2];
		spectrumIdentificationResultTableHeaders[0] = "ID";
		spectrumIdentificationResultTableHeaders[1] = "Spectrum ID";

		if (sir != null) {
			for (int i = 0; i < sir.length; i++) {
				String string = sir[i];
				spectrumIdentificationResultTableHeaders[2 + i] = string;

			}
		}

		String[] spectrumIdentificationItemTableHeaders = new String[spectrumIdentificationItemCvParamLengs + 8];
		spectrumIdentificationItemTableHeaders[0] = "ID";
		spectrumIdentificationItemTableHeaders[1] = "Peptide Sequence";
		spectrumIdentificationItemTableHeaders[2] = "Modification";
		spectrumIdentificationItemTableHeaders[3] = "Calculated MassToCharge";
		spectrumIdentificationItemTableHeaders[4] = "Experimental MassToCharge";
		spectrumIdentificationItemTableHeaders[5] = "Rank";
		spectrumIdentificationItemTableHeaders[6] = "Is Decoy";
		spectrumIdentificationItemTableHeaders[7] = "PassThreshold";
		if (sii != null) {
			for (int i = 0; i < sii.length; i++) {
				String string = sii[i];

				string = string.replaceAll("\\\\", "");

				spectrumIdentificationItemTableHeaders[8 + i] = string;
			}
		}

		// TODO: Disabled - Andrew
		/*
		 * / if (spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect")
		 * != null) {
		 * spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect"
		 * ).setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue") !=
		 * null) { spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue").
		 * setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value").setComparator(new
		 * DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt
		 * ("SEQUEST:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("SEQUEST:expectation value").setComparator(new
		 * DoubleComparator()); } /
		 */

		spectrumIdentificationResultTable.setModel(new DefaultTableModel(
				new Object[][] {}, spectrumIdentificationResultTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// spectrumIdentificationResultTable.setAutoCreateRowSorter(true);

		spectrumIdentificationResultTable.removeAll();

		spectrumIdentificationItemTable.setModel(new DefaultTableModel(
				new Object[][] {}, spectrumIdentificationItemTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// spectrumIdentificationItemTable.setAutoCreateRowSorter(true);

		peptideEvidenceTable.setModel(new DefaultTableModel(new Object[][] {},
				peptideEvidenceTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// peptideEvidenceTable.setAutoCreateRowSorter(true);

		fragmentationTable.setModel(new DefaultTableModel(new Object[][] {},
				fragmentationTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// fragmentationTable.setAutoCreateRowSorter(true);

		spectrumIdentificationItemTable.removeAll();
		peptideEvidenceTable.removeAll();
		fragmentationTable.removeAll();

		//
		spectrumIdentificationItemTablePeptideView
				.setModel(new DefaultTableModel(new Object[][] {},
						spectrumIdentificationItemTableHeaders) {
					private static final long serialVersionUID = 1L;
				});
		// spectrumIdentificationItemTablePeptideView.setAutoCreateRowSorter(true);

		peptideEvidenceTablePeptideView.setModel(new DefaultTableModel(
				new Object[][] {}, peptideEvidenceTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// peptideEvidenceTablePeptideView.setAutoCreateRowSorter(true);

		fragmentationTablePeptideView.setModel(new DefaultTableModel(
				new Object[][] {}, fragmentationTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		// peptideEvidenceTablePeptideView.setAutoCreateRowSorter(true);

		spectrumIdentificationItemTablePeptideView.removeAll();
		peptideEvidenceTablePeptideView.removeAll();
		fragmentationTablePeptideView.removeAll();
		jGraph.removeAll();
		jExperimentalFilterPanel.removeAll();

		jGraph.validate();
		jGraph.repaint();

		jGraph1.removeAll();
		jExperimentalFilterPanel1.removeAll();

		jGraph1.validate();
		jGraph1.repaint();

		jProteinDescriptionEditorPane.setText("");
	}

	private void initComponents() {
		fileChooser.setCurrentDirectory(null);
		fileChooser.addChoosableFileFilter(new MzIdentMLFilter());

		final JTextPane protocalTextPane = new JTextPane();
		mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainTabbedPane.setToolTipText("Global Statistics");
		mainTabbedPane.setPreferredSize(new Dimension(889, 939));
		mainTabbedPane.addMouseListener(new mainTabbedPaneMouseClicked(this,
				mainTabbedPane, siiComboBox, sIIListPassThreshold,
				protocalTextPane));

		jProteinAmbiguityGroupPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Group"));
		jProteinAmbiguityGroupPanel
				.setToolTipText("groups of proteins sharing some or all of the same peptides");
		jProteinAmbiguityGroupPanel.setPreferredSize(new Dimension(772, 150));

		GroupLayout jProteinAmbiguityGroupPanelLayout = new GroupLayout(
				jProteinAmbiguityGroupPanel);
		jProteinAmbiguityGroupPanel
				.setLayout(jProteinAmbiguityGroupPanelLayout);
		jProteinAmbiguityGroupPanelLayout
				.setHorizontalGroup(jProteinAmbiguityGroupPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 614, Short.MAX_VALUE));
		jProteinAmbiguityGroupPanelLayout
				.setVerticalGroup(jProteinAmbiguityGroupPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));

		jProteinDetectionHypothesisPanel.setBorder(BorderFactory
				.createTitledBorder("Protein"));
		jProteinDetectionHypothesisPanel
				.setToolTipText("proteins inferred based on a set of peptide spectrum matches");
		jProteinDetectionHypothesisPanel.setPreferredSize(new Dimension(772,
				150));

		GroupLayout jProteinDetectionHypothesisPanelLayout = new GroupLayout(
				jProteinDetectionHypothesisPanel);
		jProteinDetectionHypothesisPanel
				.setLayout(jProteinDetectionHypothesisPanelLayout);
		jProteinDetectionHypothesisPanelLayout
				.setHorizontalGroup(jProteinDetectionHypothesisPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 614, Short.MAX_VALUE));
		jProteinDetectionHypothesisPanelLayout
				.setVerticalGroup(jProteinDetectionHypothesisPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));

		final JPanel jProteinInfoPanel = new JPanel();
		jProteinInfoPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Info"));
		jProteinInfoPanel.setInheritsPopupMenu(true);

		final JPanel jProteinSequencePanel = new JPanel();
		jProteinSequencePanel.setBorder(BorderFactory
				.createTitledBorder("Protein Sequence"));

		jProteinSequenceTextPane.setContentType("text/html");
		jProteinSequenceTextPane.setText("");
		final JScrollPane jProteinSequenceScrollPane = new JScrollPane();
		jProteinSequenceScrollPane.setViewportView(jProteinSequenceTextPane);

		GroupLayout jProteinSequencePanelLayout = new GroupLayout(
				jProteinSequencePanel);
		jProteinSequencePanel.setLayout(jProteinSequencePanelLayout);
		jProteinSequencePanelLayout
				.setHorizontalGroup(jProteinSequencePanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jProteinSequenceScrollPane,
								GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE));
		jProteinSequencePanelLayout
				.setVerticalGroup(jProteinSequencePanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jProteinSequenceScrollPane,
								GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE));

		jSpectrumIdentificationItemProteinPanel.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemProteinPanel
				.setToolTipText("Protein Detection Hypothesis");
		jSpectrumIdentificationItemProteinPanel.setPreferredSize(new Dimension(
				772, 150));

		GroupLayout jSpectrumIdentificationItemProteinPanelLayout = new GroupLayout(
				jSpectrumIdentificationItemProteinPanel);
		jSpectrumIdentificationItemProteinPanel
				.setLayout(jSpectrumIdentificationItemProteinPanelLayout);
		jSpectrumIdentificationItemProteinPanelLayout
				.setHorizontalGroup(jSpectrumIdentificationItemProteinPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 740, Short.MAX_VALUE));
		jSpectrumIdentificationItemProteinPanelLayout
				.setVerticalGroup(jSpectrumIdentificationItemProteinPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 1134, Short.MAX_VALUE));

		final JLabel jScientificNameLabel = new JLabel("Scientific name:");

		final JPanel jProteinDescriptionPanel = new JPanel();
		jProteinDescriptionPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Description"));

		jProteinDescriptionEditorPane.setContentType("text/html");
		final JScrollPane jProteinDescriptionScrollPane = new JScrollPane();
		jProteinDescriptionScrollPane
				.setViewportView(jProteinDescriptionEditorPane);

		GroupLayout jProteinDescriptionPanelLayout = new GroupLayout(
				jProteinDescriptionPanel);
		jProteinDescriptionPanel.setLayout(jProteinDescriptionPanelLayout);
		jProteinDescriptionPanelLayout
				.setHorizontalGroup(jProteinDescriptionPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jProteinDescriptionScrollPane,
								GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE));
		jProteinDescriptionPanelLayout
				.setVerticalGroup(jProteinDescriptionPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jProteinDescriptionScrollPane,
								GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE));

		GroupLayout jProteinInfoPanelLayout = new GroupLayout(jProteinInfoPanel);
		jProteinInfoPanel.setLayout(jProteinInfoPanelLayout);
		jProteinInfoPanelLayout
				.setHorizontalGroup(jProteinInfoPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jProteinInfoPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jScientificNameLabel)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScientificNameValueLabel,
												GroupLayout.PREFERRED_SIZE,
												157, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(506, Short.MAX_VALUE))
						.addComponent(jProteinDescriptionPanel,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jProteinSequencePanel,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jSpectrumIdentificationItemProteinPanel,
								GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE));
		jProteinInfoPanelLayout
				.setVerticalGroup(jProteinInfoPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jProteinInfoPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jProteinInfoPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jScientificNameLabel)
														.addComponent(
																jScientificNameValueLabel,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addGap(11, 11, 11)
										.addComponent(jProteinDescriptionPanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jProteinSequencePanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jSpectrumIdentificationItemProteinPanel,
												GroupLayout.DEFAULT_SIZE, 1157,
												Short.MAX_VALUE)));

		jSpectrumIdentificationItemProteinPanel.getAccessibleContext()
				.setAccessibleDescription("Spectrum Identification Item");

		final JPanel proteinViewPanel = new JPanel();
		proteinViewPanel.setToolTipText("Protein View");
		proteinViewPanel.setName("Protein View"); // NOI18N
		proteinViewPanel.setPreferredSize(new Dimension(889, 939));
		GroupLayout proteinViewPanelLayout = new GroupLayout(proteinViewPanel);
		proteinViewPanel.setLayout(proteinViewPanelLayout);
		proteinViewPanelLayout
				.setHorizontalGroup(proteinViewPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								proteinViewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												proteinViewPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jProteinDetectionHypothesisPanel,
																GroupLayout.DEFAULT_SIZE,
																626,
																Short.MAX_VALUE)
														.addComponent(
																jProteinAmbiguityGroupPanel,
																GroupLayout.DEFAULT_SIZE,
																626,
																Short.MAX_VALUE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jProteinInfoPanel,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap()));
		proteinViewPanelLayout
				.setVerticalGroup(proteinViewPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								proteinViewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												proteinViewPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jProteinInfoPanel,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																proteinViewPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				jProteinAmbiguityGroupPanel,
																				GroupLayout.DEFAULT_SIZE,
																				802,
																				Short.MAX_VALUE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jProteinDetectionHypothesisPanel,
																				GroupLayout.DEFAULT_SIZE,
																				689,
																				Short.MAX_VALUE)))
										.addContainerGap()));

		mainTabbedPane.addTab("Protein View", null, proteinViewPanel,
				"Protein View");
		proteinViewPanel.getAccessibleContext().setAccessibleName(
				"Protein View");
		proteinViewPanel.getAccessibleContext().setAccessibleParent(
				mainTabbedPane);

		final JPanel spectrumViewPanel = new JPanel();
		spectrumViewPanel.setToolTipText("Spectrum View");
		spectrumViewPanel.setPreferredSize(new Dimension(889, 939));

		final JSplitPane jSplitPane1 = new JSplitPane();
		jSplitPane1.setBorder(null);
		jSplitPane1.setDividerLocation(500);

		final JPanel jSpectrumPanel = new JPanel();
		jSpectrumPanel.setBorder(BorderFactory.createTitledBorder("Spectrum"));
		jSpectrumPanel.setAutoscrolls(true);

		jFragmentationPanel.setBorder(BorderFactory
				.createTitledBorder("Fragmentation"));
		jFragmentationPanel.setAutoscrolls(true);
		jFragmentationPanel.setMinimumSize(new Dimension(0, 0));
		jFragmentationPanel.setPreferredSize(new Dimension(383, 447));

		GroupLayout jFragmentationPanelLayout = new GroupLayout(
				jFragmentationPanel);
		jFragmentationPanel.setLayout(jFragmentationPanelLayout);
		jFragmentationPanelLayout.setHorizontalGroup(jFragmentationPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						893, Short.MAX_VALUE));
		jFragmentationPanelLayout.setVerticalGroup(jFragmentationPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						213, Short.MAX_VALUE));

		jGraph.setBorder(BorderFactory.createTitledBorder("Graph"));

		GroupLayout jGraphLayout = new GroupLayout(jGraph);
		jGraph.setLayout(jGraphLayout);
		jGraphLayout.setHorizontalGroup(jGraphLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jGraphLayout
				.setVerticalGroup(jGraphLayout.createParallelGroup(
						GroupLayout.Alignment.LEADING).addGap(0, 1156,
						Short.MAX_VALUE));

		jExperimentalFilterPanel.setBorder(BorderFactory
				.createTitledBorder("Experimental Filtering"));

		GroupLayout jExperimentalFilterPanelLayout = new GroupLayout(
				jExperimentalFilterPanel);
		jExperimentalFilterPanel.setLayout(jExperimentalFilterPanelLayout);
		jExperimentalFilterPanelLayout
				.setHorizontalGroup(jExperimentalFilterPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jExperimentalFilterPanelLayout
				.setVerticalGroup(jExperimentalFilterPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 17, Short.MAX_VALUE));

		GroupLayout jSpectrumPanelLayout = new GroupLayout(jSpectrumPanel);
		jSpectrumPanel.setLayout(jSpectrumPanelLayout);
		jSpectrumPanelLayout.setHorizontalGroup(jSpectrumPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jGraph, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jFragmentationPanel, GroupLayout.DEFAULT_SIZE,
						905, Short.MAX_VALUE)
				.addComponent(jExperimentalFilterPanel,
						GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jSpectrumPanelLayout.setVerticalGroup(jSpectrumPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jSpectrumPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jGraph, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGap(24, 24, 24)
								.addComponent(jExperimentalFilterPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jFragmentationPanel,
										GroupLayout.PREFERRED_SIZE, 236,
										GroupLayout.PREFERRED_SIZE)));

		jSplitPane1.setRightComponent(jSpectrumPanel);

		jSpectrumIdentificationResultPanel.setBorder(BorderFactory
				.createTitledBorder("Spectrum List"));
		jSpectrumIdentificationResultPanel
				.setToolTipText("Protein Ambiguity Group");
		jSpectrumIdentificationResultPanel.setMinimumSize(new Dimension(404,
				569));

		GroupLayout jSpectrumIdentificationResultPanelLayout = new GroupLayout(
				jSpectrumIdentificationResultPanel);
		jSpectrumIdentificationResultPanel
				.setLayout(jSpectrumIdentificationResultPanelLayout);
		jSpectrumIdentificationResultPanelLayout
				.setHorizontalGroup(jSpectrumIdentificationResultPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jSpectrumIdentificationResultPanelLayout
				.setVerticalGroup(jSpectrumIdentificationResultPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));

		jSpectrumIdentificationItemPanel.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemPanel
				.setToolTipText("Spectrum Identification Item");
		jSpectrumIdentificationItemPanel.setAutoscrolls(true);

		GroupLayout jSpectrumIdentificationItemPanelLayout = new GroupLayout(
				jSpectrumIdentificationItemPanel);
		jSpectrumIdentificationItemPanel
				.setLayout(jSpectrumIdentificationItemPanelLayout);
		jSpectrumIdentificationItemPanelLayout
				.setHorizontalGroup(jSpectrumIdentificationItemPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jSpectrumIdentificationItemPanelLayout
				.setVerticalGroup(jSpectrumIdentificationItemPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 224, Short.MAX_VALUE));

		jPeptideEvidencePanel.setBorder(BorderFactory
				.createTitledBorder("Peptide Evidence"));
		jPeptideEvidencePanel.setToolTipText("Peptide Evidence");
		jPeptideEvidencePanel.setAutoscrolls(true);

		GroupLayout jPeptideEvidencePanelLayout = new GroupLayout(
				jPeptideEvidencePanel);
		jPeptideEvidencePanel.setLayout(jPeptideEvidencePanelLayout);
		jPeptideEvidencePanelLayout
				.setHorizontalGroup(jPeptideEvidencePanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 468, Short.MAX_VALUE));
		jPeptideEvidencePanelLayout
				.setVerticalGroup(jPeptideEvidencePanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 1029, Short.MAX_VALUE));

		final JPanel jPanel1 = new JPanel();
		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jPeptideEvidencePanel,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jSpectrumIdentificationResultPanel,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jSpectrumIdentificationItemPanel,
																GroupLayout.Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel1Layout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(
										jSpectrumIdentificationResultPanel,
										GroupLayout.PREFERRED_SIZE, 198,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSpectrumIdentificationItemPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPeptideEvidencePanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));

		jSpectrumIdentificationResultPanel.getAccessibleContext()
				.setAccessibleDescription("Spectrum Identification Result");

		jSplitPane1.setLeftComponent(jPanel1);

		GroupLayout spectrumViewPanelLayout = new GroupLayout(spectrumViewPanel);
		spectrumViewPanel.setLayout(spectrumViewPanelLayout);
		spectrumViewPanelLayout.setHorizontalGroup(spectrumViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jSplitPane1));
		spectrumViewPanelLayout.setVerticalGroup(spectrumViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jSplitPane1));

		mainTabbedPane.addTab("Spectrum Summary", null, spectrumViewPanel,
				"Spectrum Summary");
		spectrumViewPanel.getAccessibleContext().setAccessibleName(
				"Spectrum Summary");
		spectrumViewPanel.getAccessibleContext().setAccessibleDescription(
				"Spectrum Summary");
		spectrumViewPanel.getAccessibleContext().setAccessibleParent(
				mainTabbedPane);

		final JPanel peptideViewPanel = new JPanel();
		peptideViewPanel.setToolTipText("Peptide View");
		peptideViewPanel.setPreferredSize(new Dimension(889, 939));

		final JSplitPane jSplitPane2 = new JSplitPane();
		jSplitPane2.setBorder(null);
		jSplitPane2.setDividerLocation(500);

		final JPanel jSpectrumPanel1 = new JPanel();
		jSpectrumPanel1.setBorder(BorderFactory.createTitledBorder("Spectrum"));
		jSpectrumPanel1.setAutoscrolls(true);
		jSpectrumPanel1.setPreferredSize(new Dimension(362, 569));

		jFragmentationPanel1.setBorder(BorderFactory
				.createTitledBorder("Fragmentation"));
		jFragmentationPanel1.setAutoscrolls(true);
		jFragmentationPanel1.setPreferredSize(new Dimension(383, 447));

		GroupLayout jFragmentationPanel1Layout = new GroupLayout(
				jFragmentationPanel1);
		jFragmentationPanel1.setLayout(jFragmentationPanel1Layout);
		jFragmentationPanel1Layout
				.setHorizontalGroup(jFragmentationPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 893, Short.MAX_VALUE));
		jFragmentationPanel1Layout.setVerticalGroup(jFragmentationPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						227, Short.MAX_VALUE));

		jGraph1.setBorder(BorderFactory.createTitledBorder("Graph"));

		GroupLayout jGraph1Layout = new GroupLayout(jGraph1);
		jGraph1.setLayout(jGraph1Layout);
		jGraph1Layout.setHorizontalGroup(jGraph1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jGraph1Layout
				.setVerticalGroup(jGraph1Layout.createParallelGroup(
						GroupLayout.Alignment.LEADING).addGap(0, 1160,
						Short.MAX_VALUE));

		jExperimentalFilterPanel1.setBorder(BorderFactory
				.createTitledBorder("Experimental Filtering"));

		GroupLayout jExperimentalFilterPanel1Layout = new GroupLayout(
				jExperimentalFilterPanel1);
		jExperimentalFilterPanel1.setLayout(jExperimentalFilterPanel1Layout);
		jExperimentalFilterPanel1Layout
				.setHorizontalGroup(jExperimentalFilterPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 894, Short.MAX_VALUE));
		jExperimentalFilterPanel1Layout
				.setVerticalGroup(jExperimentalFilterPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 17, Short.MAX_VALUE));

		GroupLayout jSpectrumPanel1Layout = new GroupLayout(jSpectrumPanel1);
		jSpectrumPanel1.setLayout(jSpectrumPanel1Layout);
		jSpectrumPanel1Layout.setHorizontalGroup(jSpectrumPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jExperimentalFilterPanel1,
						GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jFragmentationPanel1, GroupLayout.DEFAULT_SIZE,
						905, Short.MAX_VALUE)
				.addComponent(jGraph1, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jSpectrumPanel1Layout.setVerticalGroup(jSpectrumPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						jSpectrumPanel1Layout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jGraph1,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jExperimentalFilterPanel1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jFragmentationPanel1,
										GroupLayout.PREFERRED_SIZE, 250,
										GroupLayout.PREFERRED_SIZE)));

		jSplitPane2.setRightComponent(jSpectrumPanel1);

		final JLabel psmRankLabel = new JLabel(
				"Peptide-Spectrum matches with Rank: ");

		psmRankValue.setModel(new DefaultComboBoxModel<String>(new String[] {
				"<=1", "<=2", "<=3", "All" }));
		psmRankValue.addActionListener(new psmRankValueActionPerformed(this));

		jSpectrumIdentificationItemPanel1.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemPanel1
				.setToolTipText("Spectrum Identification Item");
		jSpectrumIdentificationItemPanel1.setAutoscrolls(true);

		GroupLayout jSpectrumIdentificationItemPanel1Layout = new GroupLayout(
				jSpectrumIdentificationItemPanel1);
		jSpectrumIdentificationItemPanel1
				.setLayout(jSpectrumIdentificationItemPanel1Layout);
		jSpectrumIdentificationItemPanel1Layout
				.setHorizontalGroup(jSpectrumIdentificationItemPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jSpectrumIdentificationItemPanel1Layout
				.setVerticalGroup(jSpectrumIdentificationItemPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 303, Short.MAX_VALUE));

		jPeptideEvidencePanel1.setBorder(BorderFactory
				.createTitledBorder("Peptide Evidence"));
		jPeptideEvidencePanel1.setToolTipText("Peptide Evidence");
		jPeptideEvidencePanel1.setAutoscrolls(true);

		GroupLayout jPeptideEvidencePanel1Layout = new GroupLayout(
				jPeptideEvidencePanel1);
		jPeptideEvidencePanel1.setLayout(jPeptideEvidencePanel1Layout);
		jPeptideEvidencePanel1Layout
				.setHorizontalGroup(jPeptideEvidencePanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jPeptideEvidencePanel1Layout
				.setVerticalGroup(jPeptideEvidencePanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 1124, Short.MAX_VALUE));

		final JPanel jPanel2 = new JPanel();
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addComponent(
																				psmRankLabel)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				psmRankValue,
																				GroupLayout.PREFERRED_SIZE,
																				79,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(0,
																				212,
																				Short.MAX_VALUE))
														.addComponent(
																jSpectrumIdentificationItemPanel1,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPeptideEvidencePanel1,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGap(14, 14, 14)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																psmRankLabel)
														.addComponent(
																psmRankValue,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jSpectrumIdentificationItemPanel1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jPeptideEvidencePanel1,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jSplitPane2.setLeftComponent(jPanel2);

		GroupLayout peptideViewPanelLayout = new GroupLayout(peptideViewPanel);
		peptideViewPanel.setLayout(peptideViewPanelLayout);
		peptideViewPanelLayout.setHorizontalGroup(peptideViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jSplitPane2, GroupLayout.DEFAULT_SIZE, 1422,
						Short.MAX_VALUE));
		peptideViewPanelLayout.setVerticalGroup(peptideViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jSplitPane2));

		mainTabbedPane.addTab("Peptide Summary", null, peptideViewPanel,
				"Peptide Summary");

		final JPanel proteinDBViewPanel = new JPanel();
		proteinDBViewPanel.setPreferredSize(new Dimension(889, 939));

		dBSequencePanel.setBorder(BorderFactory
				.createTitledBorder("DB Sequence"));

		GroupLayout dBSequencePanelLayout = new GroupLayout(dBSequencePanel);
		dBSequencePanel.setLayout(dBSequencePanelLayout);
		dBSequencePanelLayout.setHorizontalGroup(dBSequencePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						1390, Short.MAX_VALUE));
		dBSequencePanelLayout.setVerticalGroup(dBSequencePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						1486, Short.MAX_VALUE));

		GroupLayout proteinDBViewPanelLayout = new GroupLayout(
				proteinDBViewPanel);
		proteinDBViewPanel.setLayout(proteinDBViewPanelLayout);
		proteinDBViewPanelLayout.setHorizontalGroup(proteinDBViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						proteinDBViewPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(dBSequencePanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));
		proteinDBViewPanelLayout.setVerticalGroup(proteinDBViewPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						proteinDBViewPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(dBSequencePanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));

		mainTabbedPane.addTab("Protein DB View", proteinDBViewPanel);
		proteinDBViewPanel.getAccessibleContext().setAccessibleParent(
				mainTabbedPane);

		final JPanel globalStatisticsPanel = new JPanel();
		globalStatisticsPanel.setPreferredSize(new Dimension(889, 939));

		final JPanel summaryPanel = new JPanel();
		summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

		final JLabel totalSIRLabel = new JLabel("Total SIR:");

		totalSIRLabelValue.setText("0");

		final JLabel totalSIILabel = new JLabel("Total PSM:");
		final JLabel totalSIIbelowThresholdLabel = new JLabel(
				"Total PSM below Threshold:");

		totalSIIbelowThresholdLabelValue.setText("0");

		final JLabel totalSIIaboveThresholdLabel = new JLabel(
				"Total PSM pass Threshold:");

		totalSIIaboveThresholdLabelValue.setText("0");

		final JLabel totalSIIaboveThresholdRankOneLabel = new JLabel(
				"Total PSM pass Threshold and where rank=1:");

		totalSIIaboveThresholdRankOneLabelValue.setText("0");

		final JLabel percentIdentifiedSpectraLabel = new JLabel(
				"Percent identified spectra:");

		percentIdentifiedSpectraLabelValue.setText("0");

		final JLabel totalPeptidesaboveThresholdLabel = new JLabel(
				"Total non-redundant peptides above Threshold:");

		totalPeptidesaboveThresholdLabelValue.setText("0");

		final JLabel totalPAGsLabel = new JLabel("Total PAGs:");
		final JLabel totalPDHsLabel = new JLabel("Total PDHs:");

		totalPAGsLabelValue.setText("0");

		final JLabel totalPDHsaboveThresholdLabel = new JLabel(
				"Total PDH above Threshold:");

		totalPDHsaboveThresholdLabelValue.setText("0");

		isDecoySiiValue.setText("0");

		isDecoySiiFalseValue.setText("0");

		final JLabel isDecoySii = new JLabel("PSM with Decoy = true:");
		final JLabel isDecoySiiFalse = new JLabel("PSM with Decoy = false:");
		final JLabel manualDecoyLabel = new JLabel("Manual Decoy:");
		final JLabel manualDecoyPrefix = new JLabel("Prefix");
		final JLabel manualDecoyRatio = new JLabel("Ratio");

		manualDecoyPrefixValue.setText("Rev_");
		manualDecoyPrefixValue.setEnabled(false);

		manualDecoyRatioValue.setText("1");
		manualDecoyRatioValue.setEnabled(false);

		manualDecoy.addActionListener(new manualDecoyActionPerformed(
				manualDecoy, manualDecoyPrefixValue, manualDecoyRatioValue));

		final JLabel fpSiiLabel = new JLabel("FP for PSM:");

		fpSiiValue.setText("0");

		final JLabel tpSiiLabel = new JLabel("TP for PSM:");

		tpSiiValue.setText("0");

		final JLabel fdrSiiLabel = new JLabel("FDR for PSM:");

		fdrSiiValue.setText("0");

		final JLabel fpProteinsLabel = new JLabel("FP for proteins:");
		final JLabel tpProteinsLabel = new JLabel("TP for proteins:");
		final JLabel fdrProteinsLabel = new JLabel("FDR for proteins:");

		final JLabel fpProteinsValue = new JLabel("0");
		final JLabel tpProteinsValue = new JLabel("0");
		final JLabel fdrProteinsValue = new JLabel("0");

		final JButton manualCalculate = new JButton("Calculate / Show graphs");
		manualCalculate.addActionListener(new manualCalculateActionPerformed(
				this, siiComboBox, manualDecoy, manualDecoyPrefixValue,
				isDecoySiiValue, isDecoySiiFalseValue, manualDecoyRatioValue,
				fpSiiValue, sIIListPassThreshold, tpSiiValue, fdrSiiValue,
				fdrPanel, jComboBox2, cvTermMap, jComboBox1, tpEvaluePanel,
				tpQvaluePanel));

		fdrPanel.setBorder(BorderFactory.createTitledBorder("FDR Graph"));
		fdrPanel.setPreferredSize(new Dimension(257, 255));

		GroupLayout fdrPanelLayout = new GroupLayout(fdrPanel);
		fdrPanel.setLayout(fdrPanelLayout);
		fdrPanelLayout.setHorizontalGroup(fdrPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		fdrPanelLayout.setVerticalGroup(fdrPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		tpEvaluePanel.setBorder(BorderFactory
				.createTitledBorder("TP vs FP vs E-value"));
		tpEvaluePanel.setPreferredSize(new Dimension(257, 255));

		GroupLayout tpEvaluePanelLayout = new GroupLayout(tpEvaluePanel);
		tpEvaluePanel.setLayout(tpEvaluePanelLayout);
		tpEvaluePanelLayout.setHorizontalGroup(tpEvaluePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						0, Short.MAX_VALUE));
		tpEvaluePanelLayout.setVerticalGroup(tpEvaluePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						917, Short.MAX_VALUE));

		tpQvaluePanel.setBorder(BorderFactory
				.createTitledBorder("TP vs Q-value"));
		tpQvaluePanel.setPreferredSize(new Dimension(257, 255));

		GroupLayout tpQvaluePanelLayout = new GroupLayout(tpQvaluePanel);
		tpQvaluePanel.setLayout(tpQvaluePanelLayout);
		tpQvaluePanelLayout.setHorizontalGroup(tpQvaluePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						0, Short.MAX_VALUE));
		tpQvaluePanelLayout.setVerticalGroup(tpQvaluePanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						918, Short.MAX_VALUE));

		final JSeparator jSeparator4 = new JSeparator();
		jSeparator4.setOrientation(SwingConstants.VERTICAL);

		siiComboBox.addActionListener(new siiComboBoxActionPerformed(this,
				siiComboBox, sIIListPassThreshold));

		final JLabel siiListLabel = new JLabel("SII List:");

		jComboBox2.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Better scores are lower", "Better scores are higher" }));

		GroupLayout summaryPanelLayout = new GroupLayout(summaryPanel);
		summaryPanel.setLayout(summaryPanelLayout);
		summaryPanelLayout
				.setHorizontalGroup(summaryPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								summaryPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												summaryPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																summaryPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				fdrPanel,
																				GroupLayout.DEFAULT_SIZE,
																				450,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				tpEvaluePanel,
																				GroupLayout.DEFAULT_SIZE,
																				450,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				tpQvaluePanel,
																				GroupLayout.DEFAULT_SIZE,
																				450,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																summaryPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								percentIdentifiedSpectraLabel)
																						.addComponent(
																								totalPeptidesaboveThresholdLabel)
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																totalPAGsLabel)
																														.addComponent(
																																totalPDHsLabel))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												totalPDHsaboveThresholdLabel)))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				408,
																				Short.MAX_VALUE)
																		.addComponent(
																				jSeparator4,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																isDecoySii)
																														.addComponent(
																																isDecoySiiFalse))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																isDecoySiiFalseValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																isDecoySiiValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)))
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																fpSiiLabel)
																														.addComponent(
																																tpSiiLabel)
																														.addComponent(
																																fdrSiiLabel)
																														.addComponent(
																																fpProteinsLabel)
																														.addComponent(
																																tpProteinsLabel)
																														.addComponent(
																																fdrProteinsLabel))
																										.addGap(40,
																												40,
																												40)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING,
																																false)
																														.addComponent(
																																fpSiiValue,
																																GroupLayout.PREFERRED_SIZE,
																																39,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																tpSiiValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																fpProteinsValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																tpProteinsValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																fdrProteinsValue,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)
																														.addComponent(
																																fdrSiiValue,
																																GroupLayout.PREFERRED_SIZE,
																																47,
																																GroupLayout.PREFERRED_SIZE))))
																		.addGap(552,
																				552,
																				552))
														.addGroup(
																summaryPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												manualDecoy)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												manualDecoyLabel)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												manualDecoyPrefix)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												manualDecoyPrefixValue,
																												GroupLayout.PREFERRED_SIZE,
																												51,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																totalSIRLabel)
																														.addComponent(
																																totalSIILabel)
																														.addComponent(
																																siiListLabel))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																siiComboBox,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																summaryPanelLayout
																																		.createSequentialGroup()
																																		.addGroup(
																																				summaryPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.LEADING)
																																						.addComponent(
																																								totalSIRLabelValue)
																																						.addComponent(
																																								totalSIIbelowThresholdLabel)
																																						.addComponent(
																																								totalSIIaboveThresholdLabel)
																																						.addComponent(
																																								totalSIIaboveThresholdRankOneLabel))
																																		.addGap(22,
																																				22,
																																				22)
																																		.addGroup(
																																				summaryPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.LEADING,
																																								false)
																																						.addComponent(
																																								totalSIIbelowThresholdLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								41,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								totalSIIaboveThresholdLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								totalSIIaboveThresholdRankOneLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								totalPeptidesaboveThresholdLabelValue,
																																								GroupLayout.PREFERRED_SIZE,
																																								27,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								percentIdentifiedSpectraLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								totalPDHsaboveThresholdLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								totalPAGsLabelValue,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)))
																														.addGroup(
																																summaryPanelLayout
																																		.createSequentialGroup()
																																		.addGap(150,
																																				150,
																																				150)
																																		.addComponent(
																																				manualDecoyRatio,
																																				GroupLayout.PREFERRED_SIZE,
																																				41,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				manualDecoyRatioValue,
																																				GroupLayout.PREFERRED_SIZE,
																																				51,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				jComboBox1,
																																				GroupLayout.PREFERRED_SIZE,
																																				155,
																																				GroupLayout.PREFERRED_SIZE)))))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jComboBox2,
																				GroupLayout.PREFERRED_SIZE,
																				177,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				manualCalculate,
																				GroupLayout.PREFERRED_SIZE,
																				162,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap()))));
		summaryPanelLayout
				.setVerticalGroup(summaryPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								summaryPanelLayout
										.createSequentialGroup()
										.addGroup(
												summaryPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																summaryPanelLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								summaryPanelLayout
																										.createSequentialGroup()
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																siiComboBox,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																siiListLabel))
																										.addGap(8,
																												8,
																												8)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																totalSIRLabel)
																														.addComponent(
																																totalSIRLabelValue))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																totalSIILabel)
																														.addComponent(
																																totalSIIbelowThresholdLabel)
																														.addComponent(
																																totalSIIbelowThresholdLabelValue))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																totalSIIaboveThresholdLabel)
																														.addComponent(
																																totalSIIaboveThresholdLabelValue))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																totalSIIaboveThresholdRankOneLabel)
																														.addComponent(
																																totalSIIaboveThresholdRankOneLabelValue))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.TRAILING)
																														.addGroup(
																																summaryPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				totalPAGsLabel)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addGroup(
																																				summaryPanelLayout
																																						.createParallelGroup(
																																								GroupLayout.Alignment.BASELINE)
																																						.addComponent(
																																								totalPDHsLabel)
																																						.addComponent(
																																								totalPDHsaboveThresholdLabel)))
																														.addGroup(
																																summaryPanelLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				totalPAGsLabelValue)
																																		.addPreferredGap(
																																				LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				totalPDHsaboveThresholdLabelValue)))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																percentIdentifiedSpectraLabelValue)
																														.addComponent(
																																percentIdentifiedSpectraLabel))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												summaryPanelLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																totalPeptidesaboveThresholdLabel)
																														.addComponent(
																																totalPeptidesaboveThresholdLabelValue)))
																						.addComponent(
																								jSeparator4,
																								GroupLayout.PREFERRED_SIZE,
																								237,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																summaryPanelLayout
																		.createSequentialGroup()
																		.addGap(22,
																				22,
																				22)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								isDecoySii)
																						.addComponent(
																								isDecoySiiValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								isDecoySiiFalse)
																						.addComponent(
																								isDecoySiiFalseValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fpSiiLabel)
																						.addComponent(
																								fpSiiValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								tpSiiLabel)
																						.addComponent(
																								tpSiiValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fdrSiiLabel)
																						.addComponent(
																								fdrSiiValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fpProteinsLabel)
																						.addComponent(
																								fpProteinsValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								tpProteinsLabel)
																						.addComponent(
																								tpProteinsValue))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				summaryPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fdrProteinsLabel)
																						.addComponent(
																								fdrProteinsValue))))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED,
												253, Short.MAX_VALUE)
										.addGroup(
												summaryPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addGroup(
																summaryPanelLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				manualDecoyPrefix)
																		.addComponent(
																				manualDecoyPrefixValue,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				manualDecoyLabel)
																		.addComponent(
																				manualDecoyRatio)
																		.addComponent(
																				manualDecoyRatioValue,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				manualCalculate)
																		.addComponent(
																				jComboBox1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jComboBox2,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																manualDecoy))
										.addGap(11, 11, 11)
										.addGroup(
												summaryPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																tpEvaluePanel,
																GroupLayout.DEFAULT_SIZE,
																940,
																Short.MAX_VALUE)
														.addComponent(
																tpQvaluePanel,
																GroupLayout.DEFAULT_SIZE,
																940,
																Short.MAX_VALUE)
														.addComponent(
																fdrPanel,
																GroupLayout.DEFAULT_SIZE,
																940,
																Short.MAX_VALUE))
										.addContainerGap()));

		GroupLayout globalStatisticsPanelLayout = new GroupLayout(
				globalStatisticsPanel);
		globalStatisticsPanel.setLayout(globalStatisticsPanelLayout);
		globalStatisticsPanelLayout
				.setHorizontalGroup(globalStatisticsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								globalStatisticsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(summaryPanel,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap()));
		globalStatisticsPanelLayout
				.setVerticalGroup(globalStatisticsPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								globalStatisticsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(summaryPanel,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap()));

		mainTabbedPane.addTab("Global Statistics", null, globalStatisticsPanel,
				"Global Statistics");
		globalStatisticsPanel.getAccessibleContext().setAccessibleName(
				"Global Statistics");
		globalStatisticsPanel.getAccessibleContext().setAccessibleDescription(
				"Global Statistics");
		globalStatisticsPanel.getAccessibleContext().setAccessibleParent(
				mainTabbedPane);


		protocalTextPane.setContentType("text/html");
		protocalTextPane.setEditable(false);

		mainTabbedPane.addTab("Protocols", createProtocolPanel(protocalTextPane));

		getContentPane().add(mainTabbedPane);
		getAccessibleContext().setAccessibleDescription("MzIdentML Viewer");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private JPanel createProtocolPanel(Component protocalTextPane) {		
		final JPanel protocolSummaryPanel = new JPanel(new BorderLayout());
		
		protocolSummaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
		protocolSummaryPanel.add(new JScrollPane(protocalTextPane), BorderLayout.CENTER);
		
		return protocolSummaryPanel;
	}

	private JMenuBar createMenuBar()
	{
		final JMenuBar jMenuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openMenuItem = new JMenuItem();
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		openMenuItem.setText("Open");
		openMenuItem.setToolTipText("Open");
		openMenuItem.addActionListener(new openMenuItemActionPerformed(this,
				fileChooser, mainTabbedPane, proteinAmbiguityGroupTable));
		fileMenu.add(openMenuItem);
		fileMenu.add(new Separator());
		
		jMenuBar.add(fileMenu);

		final JMenu exportMenu = new JMenu("Export");
		final JMenuItem jMenuItem1 = new JMenuItem("Export Proteins Only");
		final JMenuItem jMenuItem2 = new JMenuItem("Export Protein Groups");
		final JMenuItem jMenuItem3 = new JMenuItem("Export PSMs");

		jMenuItem1.addActionListener(new jMenuItem1ActionPerformed(this));
		exportMenu.add(jMenuItem1);

		jMenuItem2.addActionListener(new jMenuItem2ActionPerformed(this));
		exportMenu.add(jMenuItem2);

		jMenuItem3.addActionListener(new jMenuItem3ActionPerformed(this));

		exportMenu.add(jMenuItem3);
		exportMenu.add(new Separator());

		final JMenuItem exportFDR = new JMenuItem("Export FDR as CSV");
		exportFDR.addActionListener(new exportFDRActionPerformed(this));
		exportMenu.add(exportFDR);

		jMenuBar.add(exportMenu);

		return jMenuBar;
		
	}
	public void loadDBSequenceTable() {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Iterator<DBSequence> iterDBSequence = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.DBSequence);
		while (iterDBSequence.hasNext()) {
			DBSequence dBSequence = iterDBSequence.next();

			String cv = "";
			if (dBSequence.getCvParam() != null) {
				if (dBSequence.getCvParam().size() > 0) {
					cv = dBSequence.getCvParam().get(0).getValue();
				}
			}

			((DefaultTableModel) dBSequenceTable.getModel())
					.addRow(new String[] { dBSequence.getId(),
							dBSequence.getAccession(), dBSequence.getSeq(), cv });

		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadPeptideTable() {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		spectrumIdentificationItemTablePeptideView.removeAll();
		siiSirMap.clear();

		Iterator<Peptide> iterPeptide = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.Peptide);
		Map<String, Peptide> pepMap = new HashMap<>();

		while (iterPeptide.hasNext()) {
			Peptide pep = iterPeptide.next();
			pepMap.put(pep.getId(), pep);
		}

		Iterator<SpectrumIdentificationResult> iterSpectrumIdentificationResult = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationResult);

		while (iterSpectrumIdentificationResult.hasNext()) {
			try {
				SpectrumIdentificationResult spectrumIdentificationResult = iterSpectrumIdentificationResult
						.next();
				List<SpectrumIdentificationItem> spectrumIdentificationItemList = spectrumIdentificationResult
						.getSpectrumIdentificationItem();
				for (int i = 0; i < spectrumIdentificationItemList.size(); i++) {
					SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemList
							.get(i);
					siiSirMap.put(spectrumIdentificationItem.getId(),
							spectrumIdentificationResult.getId());

					boolean isDecoy = checkIfSpectrumIdentificationItemIsDecoy(spectrumIdentificationItem);

					// Peptide peptide =
					// mzIdentMLUnmarshaller.unmarshal(Peptide.class,
					// spectrumIdentificationItem.getPeptideRef());
					Peptide peptide = pepMap.get(spectrumIdentificationItem
							.getPeptideRef());
					// Peptide peptide =
					// spectrumIdentificationItem.getPeptide();

					if (peptide != null) {
						List<Modification> modificationList = peptide
								.getModification();
						Modification modification;
						String residues = null;
						Integer location = -1;
						String modificationName = null;
						CvParam modificationCvParam;
						String combine = null;
						if (modificationList.size() > 0) {
							modification = modificationList.get(0);
							location = modification.getLocation();
							if (modification.getResidues().size() > 0) {
								residues = modification.getResidues().get(0);
							}
							List<CvParam> modificationCvParamList = modification
									.getCvParam();
							if (modificationCvParamList.size() > 0) {
								modificationCvParam = modificationCvParamList
										.get(0);
								modificationName = modificationCvParam
										.getName();
							}
						}
						if (modificationName != null) {
							combine = modificationName;
						}
						if (residues != null) {
							combine += " on residues: " + residues;
						}
						if (location != null) {
							combine += " at location: " + location;
						}
						double calculatedMassToCharge = 0;
						if (spectrumIdentificationItem
								.getCalculatedMassToCharge() != null) {
							calculatedMassToCharge = spectrumIdentificationItem
									.getCalculatedMassToCharge();
						}
						int rank = 10;
						if (psmRankValue.getSelectedIndex() == 0) {
							rank = 1;
						} else if (psmRankValue.getSelectedIndex() == 1) {
							rank = 2;
						} else if (psmRankValue.getSelectedIndex() == 2) {
							rank = 3;
						}

						if (spectrumIdentificationItem.getRank() <= rank) {
							((DefaultTableModel) spectrumIdentificationItemTablePeptideView
									.getModel())
									.addRow(new Object[] {
											spectrumIdentificationItem.getId(),
											peptide.getPeptideSequence(),
											combine,
											IdViewerUtils
													.roundTwoDecimals(calculatedMassToCharge),
											IdViewerUtils
													.roundTwoDecimals(spectrumIdentificationItem
															.getExperimentalMassToCharge()),
											Integer.valueOf(spectrumIdentificationItem
													.getRank()),
											isDecoy,
											spectrumIdentificationItem
													.isPassThreshold() });

							List<CvParam> cvParamListspectrumIdentificationItem = spectrumIdentificationItem
									.getCvParam();

							for (int s = 0; s < cvParamListspectrumIdentificationItem
									.size(); s++) {
								CvParam cvParam = cvParamListspectrumIdentificationItem
										.get(s);
								String accession = cvParam.getAccession();

								if (cvParam.getName().equals(
										"peptide unique to one protein")) {
									((DefaultTableModel) spectrumIdentificationItemTablePeptideView
											.getModel())
											.setValueAt(1,
													spectrumIdentificationItemTablePeptideView
															.getModel()
															.getRowCount() - 1,
													8 + s);
								} else if (accession.equals("MS:1001330")
										|| accession.equals("MS:1001172")
										|| accession.equals("MS:1001159")
										|| accession.equals("MS:1001328")) {
									// ((DefaultTableModel)
									// spectrumIdentificationItemTablePeptideView.getModel()).setValueAt(roundScientificNumbers(Double.valueOf(cvParam.getValue()).doubleValue()),
									// ((DefaultTableModel)
									// spectrumIdentificationItemTablePeptideView.getModel()).getRowCount()
									// - 1, 8 + s);
									((DefaultTableModel) spectrumIdentificationItemTablePeptideView
											.getModel())
											.setValueAt(
													IdViewerUtils
															.roundThreeDecimals(Double
																	.parseDouble(cvParam
																			.getValue())),
													spectrumIdentificationItemTablePeptideView
															.getModel()
															.getRowCount() - 1,
													8 + s);
								} else {
									((DefaultTableModel) spectrumIdentificationItemTablePeptideView
											.getModel())
											.setValueAt(
													cvParam.getValue(),
													(spectrumIdentificationItemTablePeptideView
															.getModel())
															.getRowCount() - 1,
													8 + s);
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	public void loadProteinAmbiguityGroupTable() {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String protein_accessions = "";
		pDHListPassThreshold.clear();

		Iterator<ProteinAmbiguityGroup> iterProteinAmbiguityGroup = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.ProteinAmbiguityGroup);
		List<ProteinDetectionHypothesis> proteinDetectionHypothesisList;
		while (iterProteinAmbiguityGroup.hasNext()) {
			ProteinAmbiguityGroup proteinAmbiguityGroup = iterProteinAmbiguityGroup
					.next();

			protein_accessions = "";
			proteinDetectionHypothesisList = proteinAmbiguityGroup
					.getProteinDetectionHypothesis();
			boolean anchorProtein = false;
			String anchorProteinAccession = "";
			// mzid 1.2
			boolean leadProtein = false;
			String leadProteinAccession = "";
			boolean groupRepresentativeProtein = false;
			String groupRepresentativeProteinAccession = "";

			boolean isDecoy = false;
			String score = " ";
			String number_peptide = " ";
			boolean isPassThreshold = false;
			if (proteinDetectionHypothesisList.size() > 0) {
				for (int j = 0; j < proteinDetectionHypothesisList.size(); j++) {
					try {
						ProteinDetectionHypothesis proteinDetectionHypothesis = proteinDetectionHypothesisList
								.get(j);

						DBSequence dBSequence = mzIdentMLUnmarshaller
								.unmarshal(DBSequence.class,
										proteinDetectionHypothesis
												.getDBSequenceRef());

						if (dBSequence.getAccession() != null) {
							protein_accessions = protein_accessions
									+ dBSequence.getAccession() + ";";
						}

						if (proteinDetectionHypothesis.isPassThreshold()) {
							pDHListPassThreshold
									.add(proteinDetectionHypothesis);
						}

						List<CvParam> cvParamList = proteinDetectionHypothesis
								.getCvParam();
						for (int i = 0; i < cvParamList.size(); i++) {
							CvParam cvParam = cvParamList.get(i);
							if (mzIdentMLUnmarshaller.getMzIdentMLVersion()
									.startsWith("1.1.")
									&& cvParam.getName().equals(
											"anchor protein")) {
								anchorProtein = true;
								anchorProteinAccession = dBSequence
										.getAccession();
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(proteinDetectionHypothesis);
								if (proteinDetectionHypothesis
										.getPeptideHypothesis() != null) {
									number_peptide = String
											.valueOf(proteinDetectionHypothesis
													.getPeptideHypothesis()
													.size());
								}
								isPassThreshold = proteinDetectionHypothesis
										.isPassThreshold();
							} else if (mzIdentMLUnmarshaller
									.getMzIdentMLVersion().startsWith("1.2.")
									&& cvParam.getName().equals(
											"group representative")) {
								groupRepresentativeProtein = true;
								groupRepresentativeProteinAccession = dBSequence
										.getAccession();
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(proteinDetectionHypothesis);
								if (proteinDetectionHypothesis
										.getPeptideHypothesis() != null) {
									number_peptide = String
											.valueOf(proteinDetectionHypothesis
													.getPeptideHypothesis()
													.size());
								}
								isPassThreshold = proteinDetectionHypothesis
										.isPassThreshold();

							} else if (mzIdentMLUnmarshaller
									.getMzIdentMLVersion().startsWith("1.2.")
									&& cvParam.getName().equals(
											"leading protein")) {
								leadProtein = true;
								leadProteinAccession = dBSequence
										.getAccession();
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(proteinDetectionHypothesis);
								if (proteinDetectionHypothesis
										.getPeptideHypothesis() != null) {
									number_peptide = String
											.valueOf(proteinDetectionHypothesis
													.getPeptideHypothesis()
													.size());
								}
								isPassThreshold = proteinDetectionHypothesis
										.isPassThreshold();

							}
							if (cvParam.getName().contains("score")) {
								score = cvParam.getValue();
							}

						}
					} catch (JAXBException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}
			}
			protein_accessions = protein_accessions.substring(0,
					protein_accessions.length() - 1);
			if (mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith("1.1.")
					&& anchorProtein) {
				((DefaultTableModel) proteinAmbiguityGroupTable.getModel())
						.addRow(new Object[] {
								proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions,
								anchorProteinAccession,
								IdViewerUtils.roundTwoDecimals(Double.valueOf(
										score).doubleValue()), " ",
								Integer.valueOf(number_peptide),
								String.valueOf(isDecoy),
								String.valueOf(isPassThreshold) });
			} else if (mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith(
					"1.2.")
					&& groupRepresentativeProtein) {
				((DefaultTableModel) proteinAmbiguityGroupTable.getModel())
						.addRow(new Object[] {
								proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions,
								groupRepresentativeProteinAccession,
								IdViewerUtils.roundTwoDecimals(Double.valueOf(
										score).doubleValue()), " ",
								Integer.valueOf(number_peptide),
								String.valueOf(isDecoy),
								String.valueOf(isPassThreshold) });

			} else if (mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith(
					"1.2.")
					&& leadProtein) {
				((DefaultTableModel) proteinAmbiguityGroupTable.getModel())
						.addRow(new Object[] {
								proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions,
								leadProteinAccession,
								IdViewerUtils.roundTwoDecimals(Double.valueOf(
										score).doubleValue()), " ",
								Integer.valueOf(number_peptide),
								String.valueOf(isDecoy),
								String.valueOf(isPassThreshold) });

			} else {
				((DefaultTableModel) proteinAmbiguityGroupTable.getModel())
						.addRow(new Object[] { proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions, " ", " ", " ", " ", " ",
								" " });
			}

		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadSpectrumIdentificationList(
			List<SpectrumIdentificationItem> sIIListPassThreshold) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		List<Peptide> peptideListNonReduntant = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyTrue = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyFalse = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListPassThresholdRankOne = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListBelowThreshold = new ArrayList<>();
		sIIListPassThreshold.clear();
		try {
			List<SpectrumIdentificationResult> sirListTemp = mzIdentMLUnmarshaller
					.unmarshal(SpectrumIdentificationList.class,
							siiComboBox.getSelectedItem().toString())
					.getSpectrumIdentificationResult();
			List<SpectrumIdentificationItem> siiListTemp = new ArrayList<>();

			for (int i = 0; i < sirListTemp.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = sirListTemp
						.get(i);

				for (int j = 0; j < spectrumIdentificationResult
						.getSpectrumIdentificationItem().size(); j++) {
					siiListTemp.add(spectrumIdentificationResult
							.getSpectrumIdentificationItem().get(j));
				}
			}

			if (siiListTemp.size() > 0) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(0);
				List<CvParam> cvParamList = spectrumIdentificationItem
						.getCvParam();
				for (int i = 0; i < cvParamList.size(); i++) {
					CvParam cvParam = cvParamList.get(i);
					if (cvParam.getName() != null
							&& !cvParam.getName().equals("")) {
						boolean isthere = false;
						for (int j = 0; j < jComboBox1.getItemCount(); j++) {
							if (jComboBox1.getItemAt(j).equals(
									cvParam.getName())) {
								isthere = true;
								break;
							}
						}
						if (!isthere) {
							jComboBox1.addItem(cvParam.getName());
							cvTermMap.put(cvParam.getName(),
									cvParam.getAccession());
						}
					}
				}
			}

			for (int j = 0; j < siiListTemp.size(); j++) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(j);

				if (spectrumIdentificationItem.isPassThreshold()) {
					sIIListPassThreshold.add(spectrumIdentificationItem);
					String p_ref = spectrumIdentificationItem.getPeptideRef();

					// Update FG 18-03-2013 escape XML
					Peptide peptide = mzIdentMLUnmarshaller.unmarshal(
							Peptide.class, StringEscapeUtils.escapeXml(p_ref));

					if (!peptideListNonReduntant.contains(peptide)) {
						peptideListNonReduntant.add(peptide);
					}
				} else {
					sIIListBelowThreshold.add(spectrumIdentificationItem);
				}

				if (spectrumIdentificationItem.isPassThreshold()
						&& spectrumIdentificationItem.getRank() == 1) {
					sIIListPassThresholdRankOne.add(spectrumIdentificationItem);
				}

				boolean isdecoy = true;
				List<PeptideEvidenceRef> peRefLst = spectrumIdentificationItem
						.getPeptideEvidenceRef();
				for (int k = 0; k < peRefLst.size(); k++) {
					PeptideEvidenceRef peptideEvidenceRef = peRefLst.get(k);
					PeptideEvidence peptideEvidence = mzIdentMLUnmarshaller
							.unmarshal(PeptideEvidence.class,
									peptideEvidenceRef.getPeptideEvidenceRef());
					if (!peptideEvidence.isIsDecoy()) {
						sIIListIsDecoyFalse.add(spectrumIdentificationItem);
						isdecoy = false;
						break;
					}
				}
				if (isdecoy) {
					sIIListIsDecoyTrue.add(spectrumIdentificationItem);
				}
			}

			int sirSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.SpectrumIdentificationResult
							.getXpath());
			totalSIRLabelValue.setText("" + sirSize);

			if (sIIListPassThreshold != null) {
				totalSIIaboveThresholdLabelValue.setText(String
						.valueOf(sIIListPassThreshold.size()));
			}
			if (sIIListBelowThreshold != null) {
				totalSIIbelowThresholdLabelValue.setText(String
						.valueOf(sIIListBelowThreshold.size()));
			}
			if (sIIListPassThresholdRankOne != null) {
				totalSIIaboveThresholdRankOneLabelValue.setText(String
						.valueOf(sIIListPassThresholdRankOne.size()));
			}
			if (sIIListPassThresholdRankOne != null && sirSize > 0) {
				double percent = IdViewerUtils
						.roundTwoDecimals((float) sIIListPassThresholdRankOne
								.size() * 100 / sirSize);
				percentIdentifiedSpectraLabelValue.setText(String
						.valueOf(percent) + "%");
			}
			if (peptideListNonReduntant != null) {
				totalPeptidesaboveThresholdLabelValue.setText(String
						.valueOf(peptideListNonReduntant.size()));
			}
			int pagSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.ProteinAmbiguityGroup
							.getXpath());

			totalPAGsLabelValue.setText("" + pagSize);

			if (pDHListPassThreshold != null) {
				totalPDHsaboveThresholdLabelValue.setText(String
						.valueOf(pDHListPassThreshold.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				isDecoySiiValue.setText(String.valueOf(sIIListIsDecoyTrue
						.size()));
			}
			if (sIIListIsDecoyFalse != null) {
				isDecoySiiFalseValue.setText(String.valueOf(sIIListIsDecoyFalse
						.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				falsePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListIsDecoyTrue.size());
				fpSiiValue.setText(String.valueOf(falsePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				truePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListPassThreshold.size()
								- sIIListIsDecoyTrue.size());
				tpSiiValue.setText(String.valueOf(truePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				if (falsePositiveSii + truePositiveSii == 0) {
					fdrSiiValue.setText("0.0");
				} else {
					fdrSii = IdViewerUtils
							.roundThreeDecimals((falsePositiveSii)
									/ (falsePositiveSii + truePositiveSii));
					fdrSiiValue.setText(String.valueOf(fdrSii));
				}
			}
			// thresholdValue.setText(spectrumIdentificationProtocol.get(0).getThreshold().getCvParam().get(0).getValue());
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void loadSpectrumIdentificationResultTable() {
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Iterator<SpectrumIdentificationResult> iterSpectrumIdentificationResult = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationResult);
		while (iterSpectrumIdentificationResult.hasNext()) {

			SpectrumIdentificationResult spectrumIdentificationResult = iterSpectrumIdentificationResult
					.next();
			((DefaultTableModel) spectrumIdentificationResultTable.getModel())
					.addRow(new String[] {
							spectrumIdentificationResult.getId(),
							spectrumIdentificationResult.getSpectrumID() });
			List<CvParam> cvParamListspectrumIdentificationResult = spectrumIdentificationResult
					.getCvParam();

			for (int s = 0; s < cvParamListspectrumIdentificationResult.size(); s++) {
				CvParam cvParam = cvParamListspectrumIdentificationResult
						.get(s);
				String name = cvParam.getName();
				for (int j = 0; j < spectrumIdentificationResultTable
						.getModel().getColumnCount(); j++) {
					if (spectrumIdentificationResultTable.getModel()
							.getColumnName(j).equals(name)) {
						((DefaultTableModel) spectrumIdentificationResultTable
								.getModel())
								.setValueAt(
										cvParam.getValue(),
										((DefaultTableModel) spectrumIdentificationResultTable
												.getModel()).getRowCount() - 1,
										j);
					}
				}

			}

		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	private void openCLI(String filename) {
		ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");

		final File mzid_file = new File(filename);
		setTitle("ProteoIDViewer   -  " + mzid_file.getPath());
		thread.start();

		new Thread("LoadingThread") {

			@Override
			public void run() {
				try {
					if (mzid_file.getPath().endsWith(".gz")) {
						GZIPInputStream gin = new GZIPInputStream(
								new FileInputStream(mzid_file));
						File outFile = new File(mzid_file.getParent(), mzid_file
								.getName().replaceAll("\\.gz$", ""));
						FileOutputStream fos = new FileOutputStream(outFile);
						byte[] buf = new byte[100000];
						int len;
						while ((len = gin.read(buf)) > 0) {
							fos.write(buf, 0, len);
						}
						fos.close();
						gin.close();
					} else if (mzid_file.getPath().endsWith(".omx")) {
						File outFile = new File(fileChooser.getCurrentDirectory(),
								mzid_file.getName().replaceAll(".omx", ".mzid"));
						// TODO: Disabled - Andrew
						// new Omssa2mzid(mzid_file.getPath(),
						// outFile.getPath(), false);
					} else if (mzid_file.getPath().endsWith(".xml")) {
						File outFile =new File(fileChooser.getCurrentDirectory(),
								mzid_file.getName().replaceAll(".omx", ".mzid"));
						new Tandem2mzid(mzid_file.getPath(), outFile.getPath());
					}
					mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(
							mzid_file);
					fileName = mzid_file;

					if (!mzIdentMLUnmarshaller.getMzIdentMLVersion()
							.startsWith("1.1.")
							&& !mzIdentMLUnmarshaller.getMzIdentMLVersion()
									.startsWith("1.2.")) {
						progressBarDialog.setVisible(false);
						progressBarDialog.dispose();
						JOptionPane
								.showMessageDialog(
										null,
										"The file is not compatible with the Viewer: different mzIdentMl version",
										"mzIdentMl version",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					jmzreader = null;
					createTables();
					clearSummaryStats();
					mainTabbedPane.setSelectedIndex(0);
					secondTab = false;
					thirdTab = false;
					fourthTab = false;
					fifthTab = false;
					sixthTab = false;
					loadProteinAmbiguityGroupTable();

					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();

					String message = "Do you want to load spectrum source file?";

					int answer = JOptionPane.showConfirmDialog(null, message);
					if (answer == JOptionPane.YES_OPTION) {
						JFileChooser fc;
						// Create a file chooser
						fc = new JFileChooser();
						fc.setCurrentDirectory(fileChooser
								.getCurrentDirectory());

						fc.addChoosableFileFilter(new SourceFileFilter());
						int returnVal1 = fc.showOpenDialog(null);

						if (returnVal1 == JFileChooser.APPROVE_OPTION) {
							try {
								File file = fc.getSelectedFile();
								if (file.getAbsolutePath().toLowerCase()
										.endsWith("mgf")) {
									jmzreader = new MgfFile(file);
									sourceFile = "mgf";
									JOptionPane.showMessageDialog(null,
											file.getName() + " is loaded",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								} else if (file.getAbsolutePath().toLowerCase()
										.endsWith("mzml")) {
									jmzreader = new MzMlWrapper(file);
									sourceFile = "mzML";
									JOptionPane.showMessageDialog(null,
											file.getName() + " is loaded",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null,
											file.getName()
													+ " is not supported",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (JMzReaderException ex) {
								System.out.println(ex.getMessage());
							}
						}
					}

					if (proteinAmbiguityGroupTable.getRowCount() == 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"There is no protein view for this file",
										"Protein View",
										JOptionPane.INFORMATION_MESSAGE);
					}
					// loadSummaryStats();

				} catch (OutOfMemoryError error) {
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
					Runtime.getRuntime().gc();
					JOptionPane.showMessageDialog(null, "Out of Memory Error.",
							"Error", JOptionPane.ERROR_MESSAGE);

					System.exit(0);
				} catch (Exception ex) {
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
					System.out.println(ex.getMessage());
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					String msg = ex.getMessage();

					if (msg.equals("No entry found for ID: null and Class: class uk.ac.ebi.jmzidml.model.mzidml.DBSequence. Make sure the element you are looking for has an ID attribute and is id-mapped!")) {
						msg = "No dbSequence_ref provided from ProteinDetectionHypothesis, please report this error back to the mzid exporter";
					}
					JOptionPane.showMessageDialog(null, msg, "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}.start();
	}

	/**
	 * Creates spectrum Identification Item Table Mouse Clicked
	 */
	public void spectrumIdentificationItemTableMouseClicked(int row) {
		if (row == -1)
			return;

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		fragmentationTable.removeAll();
		peptideEvidenceTable.removeAll();

		// TODO: Disabled - Andrew
		// fragmentationTable.scrollRowToVisible(0);
		SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemListForSpecificResult
				.get(row);

		if (spectrumIdentificationItem != null) {
			List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
					.getPeptideEvidenceRef();
			if (peptideEvidenceRefList != null) {
				for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
					try {
						PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
								.get(i);

						PeptideEvidence peptideEvidence = mzIdentMLUnmarshaller
								.unmarshal(PeptideEvidence.class,
										peptideEvidenceRef
												.getPeptideEvidenceRef());

						((DefaultTableModel) peptideEvidenceTable.getModel())
								.addRow(new Object[] {
										peptideEvidence.getStart(),
										peptideEvidence.getEnd(),
										peptideEvidence.getPre(),
										peptideEvidence.getPost(),
										peptideEvidence.isIsDecoy(),
										peptideEvidence.getPeptideRef(),
										peptideEvidence.getDBSequenceRef()
								// "<html><a href=>"
								// +peptideEvidence.getDBSequenceRef()+"</a>"
								});
					} catch (JAXBException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
			}
		}

		Fragmentation fragmentation = spectrumIdentificationItem
				.getFragmentation();

		if (fragmentation != null) {
			List<IonType> ionTypeList = fragmentation.getIonType();
			if (ionTypeList != null) {
				for (int i = 0; i < ionTypeList.size(); i++) {
					IonType ionType = ionTypeList.get(i);
					CvParam cvParam = ionType.getCvParam();
					if (!filterListIon.contains(cvParam.getName())) {
						filterListIon.add(cvParam.getName());
					}
					if (!filterListCharge.contains(String.valueOf(ionType
							.getCharge()))) {
						filterListCharge
								.add(String.valueOf(ionType.getCharge()));
					}
					List<Float> m_mz = ionType.getFragmentArray().get(0)
							.getValues();
					List<Float> m_intensity = ionType.getFragmentArray().get(1)
							.getValues();
					List<Float> m_error = ionType.getFragmentArray().get(2)
							.getValues();
					String type = cvParam.getName();
					type = type.replaceAll("param: ", "");
					type = type.replaceAll(" ion", "");

					if (m_mz != null && !m_mz.isEmpty()) {
						for (int j = 0; j < m_mz.size(); j++) {
							((DefaultTableModel) fragmentationTable.getModel())
									.addRow(new Object[] { m_mz.get(j),
											m_intensity.get(j), m_error.get(j),
											type + ionType.getIndex().get(j),
											ionType.getCharge() });

						}
					}
				}
			}

			double[] mzValuesAsDouble = new double[fragmentationTable
					.getModel().getRowCount()];
			double[] intensityValuesAsDouble = new double[fragmentationTable
					.getModel().getRowCount()];
			double[] m_errorValuesAsDouble = new double[fragmentationTable
					.getModel().getRowCount()];
			peakAnnotation.clear();
			for (int k = 0; k < fragmentationTable.getModel().getRowCount(); k++) {
				mzValuesAsDouble[k] = (double) (fragmentationTable.getModel()
						.getValueAt(k, 0));

				intensityValuesAsDouble[k] = (double) (fragmentationTable
						.getModel().getValueAt(k, 1));
				m_errorValuesAsDouble[k] = (double) (fragmentationTable
						.getModel().getValueAt(k, 2));

				String type = (String) fragmentationTable.getModel()
						.getValueAt(k, 3);
				type = type.replaceFirst("frag:", "");
				type = type.replaceFirst("ion", "");
				type = type.replaceFirst("internal", "");

				peakAnnotation.add(new DefaultSpectrumAnnotation(
						mzValuesAsDouble[k], m_errorValuesAsDouble[k],
						Color.blue, type));
			}
			jGraph.removeAll();

			jGraph.validate();
			jGraph.repaint();
			if (jmzreader != null) {
				try {
					int row1 = spectrumIdentificationResultTable
							.getSelectedRow();
					String sir_id = (String) spectrumIdentificationResultTable
							.getModel().getValueAt(row1, 0);
					// System.out.println(sir_id);
					SpectrumIdentificationResult spectrumIdentificationResult = mzIdentMLUnmarshaller
							.unmarshal(SpectrumIdentificationResult.class,
									sir_id);
					Spectrum spectrum = null;
					String spectrumID = spectrumIdentificationResult
							.getSpectrumID();
					if (sourceFile.equals("mgf")) {
						String spectrumIndex = spectrumID.substring(6);
						Integer index1 = Integer.valueOf(spectrumIndex) + 1;
						spectrum = jmzreader.getSpectrumById(index1.toString());
					} else if (sourceFile.equals("mzML")) {
						spectrum = jmzreader.getSpectrumById(spectrumID);
					}

					List<Double> mzValues;
					if (spectrum.getPeakList() != null) {
						mzValues = new ArrayList<Double>(spectrum.getPeakList()
								.keySet());
					} else {
						mzValues = Collections.emptyList();
					}

					double[] mz = new double[mzValues.size()];
					double[] intensities = new double[mzValues.size()];

					int index = 0;
					for (double mzValue : mzValues) {
						mz[index] = mzValue;
						intensities[index] = spectrum.getPeakList()
								.get(mzValue);
						index++;
					}
					SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
							intensities,
							spectrumIdentificationItem
									.getExperimentalMassToCharge(),
							String.valueOf(spectrumIdentificationItem
									.getChargeState()),
							spectrumIdentificationItem.getName());
					spectrumPanel.setAnnotations(peakAnnotation);
					jGraph.setLayout(new BorderLayout());
					jGraph.setLayout(new BoxLayout(jGraph, BoxLayout.LINE_AXIS));
					jGraph.add(spectrumPanel);
					jGraph.validate();
					jGraph.repaint();
				} catch (JMzReaderException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (JAXBException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				}

			} else if (mzValuesAsDouble.length > 0) {
				SpectrumPanel spectrumPanel = new SpectrumPanel(
						mzValuesAsDouble, intensityValuesAsDouble,
						spectrumIdentificationItem
								.getExperimentalMassToCharge(),
						String.valueOf(spectrumIdentificationItem
								.getChargeState()),
						spectrumIdentificationItem.getName());

				spectrumPanel.setAnnotations(peakAnnotation);

				jGraph.setLayout(new BorderLayout());
				jGraph.setLayout(new BoxLayout(jGraph, BoxLayout.LINE_AXIS));
				jGraph.add(spectrumPanel);
				jGraph.validate();
				jGraph.repaint();
				this.repaint();
			}
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Creates spectrum Identification Result Table Mouse Clicked
	 */
	public void spectrumIdentificationResultTableMouseClicked() {

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int row = spectrumIdentificationResultTable.getSelectedRow();
		if (row != -1) {
			// row =
			// spectrumIdentificationResultTable.convertRowIndexToModel(row);
			try {
				spectrumIdentificationItemTable.removeAll();
				peptideEvidenceTable.removeAll();
				fragmentationTable.removeAll();
				jGraph.removeAll();

				jGraph.validate();
				jGraph.repaint();
				// TODO: Disabled - Andrew
				// spectrumIdentificationItemTable.scrollRowToVisible(0);
				String sir_id = (String) spectrumIdentificationResultTable
						.getModel().getValueAt(row, 0);
				SpectrumIdentificationResult spectrumIdentificationResult = mzIdentMLUnmarshaller
						.unmarshal(SpectrumIdentificationResult.class, sir_id);
				if (jmzreader != null) {

					Spectrum spectrum = null;
					String spectrumID = spectrumIdentificationResult
							.getSpectrumID();
					if (sourceFile.equals("mgf")) {
						String spectrumIndex = spectrumID.substring(6);
						Integer index1 = Integer.valueOf(spectrumIndex) + 1;
						spectrum = jmzreader.getSpectrumById(index1.toString());
					}
					if (sourceFile.equals("mzML")) {
						spectrum = jmzreader.getSpectrumById(spectrumID);
					}

					List<Double> mzValues;
					if (spectrum.getPeakList() != null) {
						mzValues = new ArrayList<Double>(spectrum.getPeakList()
								.keySet());
					} else {
						mzValues = Collections.emptyList();
					}

					double[] mz = new double[mzValues.size()];
					double[] intensities = new double[mzValues.size()];

					int index = 0;
					peakAnnotation.clear();
					for (double mzValue : mzValues) {
						mz[index] = mzValue;
						intensities[index] = spectrum.getPeakList()
								.get(mzValue);

						// peakAnnotation.add(
						// new DefaultSpectrumAnnotation(
						// mz[index],
						// intensities[index],
						// Color.blue,
						// ""));
						index++;
					}

					SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
							intensities, 0.0, "", "");
					spectrumPanel.setAnnotations(peakAnnotation);
					jGraph.setLayout(new BorderLayout());
					jGraph.setLayout(new BoxLayout(jGraph, BoxLayout.LINE_AXIS));
					jGraph.add(spectrumPanel);
					jGraph.validate();
					jGraph.repaint();
				}

				spectrumIdentificationItemListForSpecificResult = spectrumIdentificationResult
						.getSpectrumIdentificationItem();
				if (spectrumIdentificationItemListForSpecificResult.size() > 0) {

					for (int i = 0; i < spectrumIdentificationItemListForSpecificResult
							.size(); i++) {
						try {
							SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemListForSpecificResult
									.get(i);
							boolean isDecoy = checkIfSpectrumIdentificationItemIsDecoy(spectrumIdentificationItem);

							Peptide peptide = mzIdentMLUnmarshaller.unmarshal(
									Peptide.class,
									spectrumIdentificationItem.getPeptideRef());
							if (peptide != null) {
								List<Modification> modificationList = peptide
										.getModification();
								Modification modification;
								String residues = null;
								Integer location = null;
								String modificationName = null;
								CvParam modificationCvParam;
								String combine = null;
								if (modificationList.size() > 0) {
									modification = modificationList.get(0);
									location = modification.getLocation();
									if (modification.getResidues().size() > 0) {
										residues = modification.getResidues()
												.get(0);
									}
									List<CvParam> modificationCvParamList = modification
											.getCvParam();
									if (modificationCvParamList.size() > 0) {
										modificationCvParam = modificationCvParamList
												.get(0);
										modificationName = modificationCvParam
												.getName();
									}
								}
								if (modificationName != null) {
									combine = modificationName;
								}
								if (residues != null) {
									combine = combine + " on residues: "
											+ residues;
								}
								if (location != null) {
									combine = combine + " at location: "
											+ location;
								}
								double calculatedMassToCharge = 0;
								if (spectrumIdentificationItem
										.getCalculatedMassToCharge() != null) {
									calculatedMassToCharge = spectrumIdentificationItem
											.getCalculatedMassToCharge()
											.doubleValue();
								}
								((DefaultTableModel) spectrumIdentificationItemTable
										.getModel())
										.addRow(new Object[] {
												spectrumIdentificationItem
														.getId(),
												peptide.getPeptideSequence(),
												combine,
												IdViewerUtils
														.roundTwoDecimals(calculatedMassToCharge),
												IdViewerUtils
														.roundTwoDecimals(spectrumIdentificationItem
																.getExperimentalMassToCharge()),
												Integer.valueOf(spectrumIdentificationItem
														.getRank()),
												isDecoy,
												spectrumIdentificationItem
														.isPassThreshold() });

								List<CvParam> cvParamListspectrumIdentificationItem = spectrumIdentificationItem
										.getCvParam();

								for (int s = 0; s < cvParamListspectrumIdentificationItem
										.size(); s++) {
									CvParam cvParam = cvParamListspectrumIdentificationItem
											.get(s);
									String accession = cvParam.getAccession();

									if (cvParam.getName().equals(
											"peptide unique to one protein")) {
										((DefaultTableModel) spectrumIdentificationItemTable
												.getModel())
												.setValueAt(
														1,
														((DefaultTableModel) spectrumIdentificationItemTable
																.getModel())
																.getRowCount() - 1,
														8 + s);
									} else if (accession.equals("MS:1001330")
											|| accession.equals("MS:1001172")
											|| accession.equals("MS:1001159")
											|| accession.equals("MS:1001328")) {
										// ((DefaultTableModel)
										// spectrumIdentificationItemTable.getModel()).setValueAt(roundScientificNumbers(Double.valueOf(cvParam.getValue()).doubleValue()),
										// ((DefaultTableModel)
										// spectrumIdentificationItemTable.getModel()).getRowCount()
										// - 1, 8 + s);
										((DefaultTableModel) spectrumIdentificationItemTable
												.getModel())
												.setValueAt(
														cvParam.getValue(),
														((DefaultTableModel) spectrumIdentificationItemTable
																.getModel())
																.getRowCount() - 1,
														8 + s);
									} else {
										((DefaultTableModel) spectrumIdentificationItemTable
												.getModel())
												.setValueAt(
														cvParam.getValue(),
														((DefaultTableModel) spectrumIdentificationItemTable
																.getModel())
																.getRowCount() - 1,
														8 + s);
									}
								}

							}
						} catch (JAXBException ex) {
							Logger.getLogger(ProteoIDViewer.class.getName())
									.log(Level.SEVERE, null, ex);
						}
					}
				}
			} catch (JMzReaderException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}