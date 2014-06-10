package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;
import uk.ac.liv.proteoidviewer.listener.proteinAmbiguityGroupTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.proteinDetectionHypothesisTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemProteinTableeMouseClicked;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

public class ProteinView extends JPanel implements LazyLoading {
	private static final String[] spectrumIdentificationItemProteinViewTableHeaders = new String[] {
			"Peptide Sequence", "SII", "Name", "Score", "Expectation value",
			"passThreshold" };
	private static final String[] proteinDetectionHypothesisTableHeaders = new String[] {
			"ID", "Accession", "Scores", "P-values", "Number of peptides",
			"Is Decoy", "passThreshold" };
	private static final String[] proteinAmbiguityGroupTableHeaders = new String[] {
			"ID", "Name", "Protein Accessions", "Representative Protein",
			"Scores", "P-values", "Number of peptides", "Is Decoy",
			"passThreshold" };

	private static final long serialVersionUID = 1L;

	private final JTable spectrumIdentificationItemProteinViewTable = new JTable();
	private final JTable proteinDetectionHypothesisTable = new JTable();
	private final JTable proteinAmbiguityGroupTable = new JTable();

	private final JTextArea proteinDescription = new JTextArea();
	private final JTextArea proteinSequence = new JTextArea();
	private final JLabel scientificNameValue = new JLabel();

	private final ProteoIDViewer proteoIDViewer;
	private final GlobalStatisticsPanel globalStatisticsPanel;

	private boolean isLoaded = false;

	public ProteinView(ProteoIDViewer proteoIDViewer,
			SpectrumSummary spectrumSummary,
			GlobalStatisticsPanel globalStatisticsPanel) {
		this.proteoIDViewer = proteoIDViewer;
		this.globalStatisticsPanel = globalStatisticsPanel;
		addComponentListener(new LazyLoadingComponentListener());

		createTables(proteoIDViewer, spectrumSummary);
		proteinDescription.setEditable(false);
		proteinSequence.setEditable(false);

		final JPanel jProteinDescriptionPanel = new JPanel(new BorderLayout());
		jProteinDescriptionPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Description"));
		jProteinDescriptionPanel.add(new JScrollPane(proteinDescription),
				BorderLayout.CENTER);

		final JPanel jProteinSequencePanel = new JPanel(new BorderLayout());
		jProteinSequencePanel.setBorder(BorderFactory
				.createTitledBorder("Protein Sequence"));
		jProteinSequencePanel.add(new JScrollPane(proteinSequence),
				BorderLayout.CENTER);

		final JPanel jProteinInfoPanel = new JPanel(new GridLayout(5, 1));
		jProteinInfoPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Info"));
		jProteinInfoPanel.setInheritsPopupMenu(true);
		JPanel scientificNamePanel = new JPanel(new FlowLayout());
		scientificNamePanel.add(new JLabel("Scientific name:"));
		scientificNamePanel.add(scientificNameValue);
		jProteinInfoPanel.add(scientificNamePanel);
		jProteinInfoPanel.add(jProteinDescriptionPanel);
		jProteinInfoPanel.add(jProteinSequencePanel);

		final JPanel jSpectrumIdentificationItemProteinPanel = new JPanel();
		jSpectrumIdentificationItemProteinPanel.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemProteinPanel
				.setToolTipText("Protein Detection Hypothesis");
		jSpectrumIdentificationItemProteinPanel.setPreferredSize(new Dimension(
				772, 150));
		jSpectrumIdentificationItemProteinPanel.getAccessibleContext()
				.setAccessibleDescription("Spectrum Identification Item");
		jSpectrumIdentificationItemProteinPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationItemProteinPanel.add(new JScrollPane(
				spectrumIdentificationItemProteinViewTable));
		jProteinInfoPanel.add(jSpectrumIdentificationItemProteinPanel);

		JPanel leftPanel = new JPanel(new GridLayout(2, 1));

		final JPanel jProteinAmbiguityGroupPanel = new JPanel();
		jProteinAmbiguityGroupPanel.setBorder(BorderFactory
				.createTitledBorder("Protein Group"));
		jProteinAmbiguityGroupPanel
				.setToolTipText("groups of proteins sharing some or all of the same peptides");
		jProteinAmbiguityGroupPanel.setPreferredSize(new Dimension(772, 150));
		jProteinAmbiguityGroupPanel.setLayout(new BorderLayout());
		jProteinAmbiguityGroupPanel.add(new JScrollPane(
				proteinAmbiguityGroupTable));

		leftPanel.add(jProteinAmbiguityGroupPanel);

		final JPanel jProteinDetectionHypothesisPanel = new JPanel();
		jProteinDetectionHypothesisPanel.setBorder(BorderFactory
				.createTitledBorder("Protein"));
		jProteinDetectionHypothesisPanel
				.setToolTipText("proteins inferred based on a set of peptide spectrum matches");
		jProteinDetectionHypothesisPanel.setPreferredSize(new Dimension(772,
				150));
		jProteinDetectionHypothesisPanel.setLayout(new BorderLayout());
		jProteinDetectionHypothesisPanel.add(new JScrollPane(
				proteinDetectionHypothesisTable));
		leftPanel.add(jProteinDetectionHypothesisPanel);

		setLayout(new GridLayout(1, 2));
		setToolTipText("Protein View");
		setName("Protein View"); // NOI18N
		setPreferredSize(new Dimension(889, 939));
		add(leftPanel);
		add(jProteinInfoPanel);
		getAccessibleContext().setAccessibleName("Protein View");
	}

	public boolean checkIfProteinDetectionHypothesisIsDecoy(
			ProteinDetectionHypothesis proteinDetectionHypothesis,
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
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
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return result;
	}

	private void createTables(ProteoIDViewer proteoIDViewer,
			SpectrumSummary spectrumSummary) {
		spectrumIdentificationItemProteinViewTable.setAutoCreateRowSorter(true);
		proteinDetectionHypothesisTable.setAutoCreateRowSorter(true);
		proteinAmbiguityGroupTable.setAutoCreateRowSorter(true);

		spectrumIdentificationItemProteinViewTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		proteinDetectionHypothesisTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		proteinAmbiguityGroupTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		spectrumIdentificationItemProteinViewTable.getTableHeader()
				.setReorderingAllowed(false);
		proteinDetectionHypothesisTable.getTableHeader().setReorderingAllowed(
				false);
		proteinAmbiguityGroupTable.getTableHeader().setReorderingAllowed(false);

		spectrumIdentificationItemProteinViewTable
				.addMouseListener(new spectrumIdentificationItemProteinTableeMouseClicked(
						proteoIDViewer, spectrumSummary));
		proteinDetectionHypothesisTable
				.addMouseListener(new proteinDetectionHypothesisTableMouseClicked(
						proteoIDViewer, proteinDetectionHypothesisTable, this,
						scientificNameValue, proteinDescription,
						proteinSequence));
		proteinAmbiguityGroupTable
				.addMouseListener(new proteinAmbiguityGroupTableMouseClicked(
						proteoIDViewer, proteinSequence, scientificNameValue,
						proteinAmbiguityGroupTable, this));
	}

	public JTable getAmbiguityGroupTable() {
		return proteinAmbiguityGroupTable;
	}

	public JTable getDetectionHypothesisTable() {
		return proteinDetectionHypothesisTable;
	}

	public JTable getIdentificationItemTable() {
		return spectrumIdentificationItemProteinViewTable;
	}

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	public void load() {
		MzIdentMLUnmarshaller mzIdentMLUnmarshaller = proteoIDViewer
				.getMzIdentMLUnmarshaller();
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String protein_accessions = "";
		globalStatisticsPanel.getPDHListPassThreshold().clear();

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
							globalStatisticsPanel.getPDHListPassThreshold()
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
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(
										proteinDetectionHypothesis,
										mzIdentMLUnmarshaller);
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
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(
										proteinDetectionHypothesis,
										mzIdentMLUnmarshaller);
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
								isDecoy = checkIfProteinDetectionHypothesisIsDecoy(
										proteinDetectionHypothesis,
										mzIdentMLUnmarshaller);
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
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			}
			protein_accessions = protein_accessions.substring(0,
					protein_accessions.length() - 1);
			if (mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith("1.1.")
					&& anchorProtein) {
				((DefaultTableModel) getAmbiguityGroupTable().getModel())
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
				((DefaultTableModel) getAmbiguityGroupTable().getModel())
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
				((DefaultTableModel) getAmbiguityGroupTable().getModel())
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
				((DefaultTableModel) getAmbiguityGroupTable().getModel())
						.addRow(new Object[] { proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions, " ", " ", " ", " ", " ",
								" " });
			}
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		isLoaded = true;
	}

	public void reset() {
		spectrumIdentificationItemProteinViewTable
				.setModel(new DefaultTableModel(new Object[][] {},
						spectrumIdentificationItemProteinViewTableHeaders) {
					private static final long serialVersionUID = 1L;
				});
		((DefaultTableModel) spectrumIdentificationItemProteinViewTable
				.getModel()).setRowCount(0);

		// spectrumIdentificationItemProteinViewTable.setAutoCreateRowSorter(true);

		proteinDetectionHypothesisTable.setModel(new DefaultTableModel(
				new Object[][] {}, proteinDetectionHypothesisTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		((DefaultTableModel) proteinDetectionHypothesisTable.getModel())
				.setRowCount(0);
		// proteinDetectionHypothesisTable.setAutoCreateRowSorter(true);

		proteinAmbiguityGroupTable.setModel(new DefaultTableModel(
				new Object[][] {}, proteinAmbiguityGroupTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		((DefaultTableModel) proteinAmbiguityGroupTable.getModel())
				.setRowCount(0);
		// proteinAmbiguityGroupTable.setAutoCreateRowSorter(true);

		proteinDescription.setText("");
		proteinSequence.setText("");
	}
}
