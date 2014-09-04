package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.gui.renderer.PeptideCellRenderer;
import org.proteosuite.model.Modification;
import org.proteosuite.model.Peptide;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;
import uk.ac.liv.proteoidviewer.listener.PsmRankValueActionPerformed;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemTablePeptideViewMouseClicked;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

public class PeptideSummary extends JSplitPane implements LazyLoading {

	private static final String[] peptideEvidenceTableHeaders = { "Start",
			"End", "Pre", "Post", "IsDecoy", "Peptide Sequence",
			"dBSequence_ref" };
	private static final String[] fragmentationTableHeaders = { "M/Z",
			"Intensity", "M Error", "Ion Type", "Charge" };

	private static final long serialVersionUID = 1L;
	private final ProteoIDViewer proteoIDViewer;

	private final List<String> filterListIon1 = new ArrayList<>();
	private final List<String> filterListCharge1 = new ArrayList<>();

	private final JTable peptideEvidenceTablePeptideView = new JTable();
	private final JTable fragmentationTablePeptideView = new JTable();
	private final JTable spectrumIdentificationItemTablePeptideView = new JTable();
	private final JComboBox<String> psmRankValue = new JComboBox<>(
			new DefaultComboBoxModel<String>(new String[] { "<=1", "<=2",
					"<=3", "All" }));
	private final JPanel jExperimentalFilterPanel1 = new JPanel();
	private final JPanel jGraph1 = new JPanel();
	private final Map<String, String> siiSirMap = new HashMap<>();

	private boolean isLoaded = false;
	
	public PeptideSummary(ProteoIDViewer proteoIDViewer) {
		addComponentListener(new LazyLoadingComponentListener());
		this.proteoIDViewer = proteoIDViewer;

		// peptide Evidence Table
		peptideEvidenceTablePeptideView.getTableHeader().setReorderingAllowed(
				false);
		peptideEvidenceTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// fragmentation Table
		fragmentationTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fragmentationTablePeptideView.getTableHeader().setReorderingAllowed(
				false);

		spectrumIdentificationItemTablePeptideView.getTableHeader()
				.setReorderingAllowed(false);
		spectrumIdentificationItemTablePeptideView
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationItemTablePeptideView
				.addMouseListener(new spectrumIdentificationItemTablePeptideViewMouseClicked(
						proteoIDViewer, this, filterListIon1,
						filterListCharge1, jGraph1, siiSirMap));
		
		spectrumIdentificationItemTablePeptideView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		spectrumIdentificationItemTablePeptideView.setDefaultRenderer(Object.class, new PeptideCellRenderer());

		createPanel();
	}

	private void createPanel() {
		final JPanel jFragmentationPanel1 = new JPanel();
		jFragmentationPanel1.setLayout(new BorderLayout());
		jFragmentationPanel1
				.add(new JScrollPane(fragmentationTablePeptideView));
		jFragmentationPanel1.setBorder(BorderFactory
				.createTitledBorder("Fragmentation"));
		jFragmentationPanel1.setAutoscrolls(true);
		jFragmentationPanel1.setPreferredSize(new Dimension(383, 447));

		jGraph1.setBorder(BorderFactory.createTitledBorder("Graph"));

		jExperimentalFilterPanel1.setBorder(BorderFactory
				.createTitledBorder("Experimental Filtering"));

		psmRankValue.addActionListener(new PsmRankValueActionPerformed(
				this));

		final JPanel jSpectrumIdentificationItemPanel1 = new JPanel();
		jSpectrumIdentificationItemPanel1.setLayout(new BorderLayout());
		jSpectrumIdentificationItemPanel1.add(new JScrollPane(
				spectrumIdentificationItemTablePeptideView));
		jSpectrumIdentificationItemPanel1.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemPanel1
				.setToolTipText("Spectrum Identification Item");
		jSpectrumIdentificationItemPanel1.setAutoscrolls(true);

		final JPanel jPeptideEvidencePanel1 = new JPanel();
		jPeptideEvidencePanel1.setLayout(new BorderLayout());
		jPeptideEvidencePanel1.add(new JScrollPane(
				peptideEvidenceTablePeptideView));
		jPeptideEvidencePanel1.setBorder(BorderFactory
				.createTitledBorder("Peptide Evidence"));
		jPeptideEvidencePanel1.setToolTipText("Peptide Evidence");
		jPeptideEvidencePanel1.setAutoscrolls(true);

		final JPanel SpectrumPanel = new JPanel(new GridLayout(3, 1));
		SpectrumPanel.setBorder(BorderFactory.createTitledBorder("Spectrum"));
		SpectrumPanel.setAutoscrolls(true);
		SpectrumPanel.setPreferredSize(new Dimension(362, 569));
		SpectrumPanel.add(jGraph1);
		SpectrumPanel.add(jExperimentalFilterPanel1);
		SpectrumPanel.add(jFragmentationPanel1);

		final JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel psmPanel = new JPanel(new FlowLayout());
		
		psmPanel.add(new JLabel("Peptide-Spectrum matches with Rank: "));
		psmPanel.add(psmRankValue);
		leftPanel.add(psmPanel, BorderLayout.PAGE_START);
		
		JPanel tablePanel = new JPanel(new GridLayout(2, 1));
		tablePanel.add(jSpectrumIdentificationItemPanel1);
		tablePanel.add(jPeptideEvidencePanel1);
		leftPanel.add(tablePanel, BorderLayout.CENTER);

		setBorder(null);
		setDividerLocation(500);
		setRightComponent(SpectrumPanel);
		setLeftComponent(leftPanel);
	}

	public JTable getEvidenceTable() {
		return peptideEvidenceTablePeptideView;
	}

	public JTable getFragmentationTable() {
		return fragmentationTablePeptideView;
	}

	public JTable getIdentificationTable() {
		return spectrumIdentificationItemTablePeptideView;
	}

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	public void load() {
		int filterRank = 10;
		if (psmRankValue.getSelectedIndex() == 0) {
			filterRank = 1;
		} else if (psmRankValue.getSelectedIndex() == 1) {
			filterRank = 2;
		} else if (psmRankValue.getSelectedIndex() == 2) {
			filterRank = 3;
		}
		
		MzIdentMLUnmarshaller mzIdentMLUnmarshaller = proteoIDViewer.getMzIdentMLUnmarshaller();
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		((DefaultTableModel) spectrumIdentificationItemTablePeptideView
				.getModel()).setNumRows(0);
		siiSirMap.clear();

		Iterator<uk.ac.ebi.jmzidml.model.mzidml.Peptide> iterPeptide = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.Peptide);
		Map<String, uk.ac.ebi.jmzidml.model.mzidml.Peptide> pepMap = new HashMap<>();

		while (iterPeptide.hasNext()) {
			uk.ac.ebi.jmzidml.model.mzidml.Peptide pep = iterPeptide.next();
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

					if (spectrumIdentificationItem.getRank() > filterRank)
						continue;

					// Peptide peptide =
					// mzIdentMLUnmarshaller.unmarshal(Peptide.class,
					// spectrumIdentificationItem.getPeptideRef());
					uk.ac.ebi.jmzidml.model.mzidml.Peptide peptide = pepMap.get(spectrumIdentificationItem
							.getPeptideRef());
					// Peptide peptide =
					// spectrumIdentificationItem.getPeptide();

					if (peptide == null)
						continue;

					boolean isDecoy = proteoIDViewer
							.checkIfSpectrumIdentificationItemIsDecoy(
									spectrumIdentificationItem,
									mzIdentMLUnmarshaller);
					
					List<uk.ac.ebi.jmzidml.model.mzidml.Modification> modificationList = peptide
							.getModification();
					
					Modification[] mods = new Modification[modificationList.size()];
					
					
					String[] residues = null;
					Integer location = -1;
					String modificationName = null;
					CvParam modificationCvParam;
					int modCount = 0;
					if (modificationList.size() > 0) {
						uk.ac.ebi.jmzidml.model.mzidml.Modification modification = modificationList.get(0);
						location = modification.getLocation();
						if (modification.getResidues().size() > 0) {
							residues = new String[modification.getResidues().size()];
							residues = modification.getResidues().toArray(residues);
						}
						List<CvParam> modificationCvParamList = modification
								.getCvParam();
						if (modificationCvParamList.size() > 0) {
							modificationCvParam = modificationCvParamList
									.get(0);
							modificationName = modificationCvParam
									.getName();
						}
						mods[modCount++] = new Modification(location, residues, modificationName);
					}
					
					Peptide pepMod = new Peptide(peptide.getPeptideSequence(), mods);
					
					double calculatedMassToCharge = 0;
					if (spectrumIdentificationItem
							.getCalculatedMassToCharge() != null) {
						calculatedMassToCharge = spectrumIdentificationItem
								.getCalculatedMassToCharge();
					}

					((DefaultTableModel) spectrumIdentificationItemTablePeptideView
							.getModel())
							.addRow(new Object[] {
									spectrumIdentificationItem.getId(),
									pepMod,
									IdViewerUtils
											.roundTwoDecimals(calculatedMassToCharge),
									IdViewerUtils
											.roundTwoDecimals(spectrumIdentificationItem
													.getExperimentalMassToCharge()),
									spectrumIdentificationItem
											.getRank(),
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
											7 + s);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		isLoaded = true;
	}

	public void removeAllFragmentation() {
		((DefaultTableModel) fragmentationTablePeptideView.getModel())
		.setNumRows(0);
		
	}

	public void reset(String[] spectrumIdentificationItemTableHeaders) {
		peptideEvidenceTablePeptideView.setModel(new DefaultTableModel(
				new Object[][] {}, peptideEvidenceTableHeaders) {
			private static final long serialVersionUID = 1L;


			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
		});
		peptideEvidenceTablePeptideView.removeAll();
		// peptideEvidenceTablePeptideView.setAutoCreateRowSorter(true);

		fragmentationTablePeptideView.setModel(new DefaultTableModel(
				new Object[][] {}, fragmentationTableHeaders) {
			private static final long serialVersionUID = 1L;


			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
		});
		fragmentationTablePeptideView.removeAll();

		spectrumIdentificationItemTablePeptideView
				.setModel(new DefaultTableModel(new Object[][] {},
						spectrumIdentificationItemTableHeaders) {
					private static final long serialVersionUID = 1L;
					


					@Override
		            public boolean isCellEditable(int row, int col) {
		                return false;
		            }
				});
		//spectrumIdentificationItemTablePeptideView.removeAll();
		// spectrumIdentificationItemTablePeptideView.setAutoCreateRowSorter(true);

		jExperimentalFilterPanel1.removeAll();

		jGraph1.removeAll();

		jGraph1.validate();
		jGraph1.repaint();
	}
}
