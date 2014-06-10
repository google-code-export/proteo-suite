package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;
import uk.ac.liv.proteoidviewer.listener.peptideEvidenceTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationResultTableMouseClicked;

public class SpectrumSummary extends JSplitPane implements LazyLoading {
	private static final String[] fragmentationTableHeaders = { "M/Z",
			"Intensity", "M Error", "Ion Type", "Charge" };

	private static final String[] peptideEvidenceTableHeaders = { "Start",
			"End", "Pre", "Post", "IsDecoy", "Peptide Sequence",
			"dBSequence_ref" };

	private static final long serialVersionUID = 1L;

	private final JTable spectrumIdentificationResultTable = new JTable();
	private final JTable fragmentationTable = new JTable();
	private final JTable peptideEvidenceTable = new JTable();
	private final JTable spectrumIdentificationItemTable = new JTable();
	private final JPanel jExperimentalFilterPanel = new JPanel();
	private final JPanel jGraph = new JPanel();
	
	public List<SpectrumIdentificationItem> spectrumIdentificationItemListForSpecificResult;
	private final ProteoIDViewer proteoIDViewer;

	public SpectrumSummary(ProteoIDViewer proteoIDViewer,
			ProteinDBView proteinDBView) {
		addComponentListener(new LazyLoadingComponentListener());
		this.proteoIDViewer = proteoIDViewer;
		createTables(proteoIDViewer, proteinDBView);
		createSpectrumSummary();
	}

	private void createTables(ProteoIDViewer proteoIDViewer,
			ProteinDBView proteinDBView) {
		spectrumIdentificationResultTable.setAutoCreateRowSorter(true);
		fragmentationTable.setAutoCreateRowSorter(true);
		peptideEvidenceTable.setAutoCreateRowSorter(true);
		spectrumIdentificationItemTable.setAutoCreateRowSorter(true);

		spectrumIdentificationResultTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fragmentationTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		peptideEvidenceTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationItemTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		fragmentationTable.getTableHeader().setReorderingAllowed(false);
		peptideEvidenceTable.getTableHeader().setReorderingAllowed(false);
		spectrumIdentificationResultTable.getTableHeader()
				.setReorderingAllowed(false);
		spectrumIdentificationItemTable.getTableHeader().setReorderingAllowed(
				false);

		spectrumIdentificationResultTable
				.addMouseListener(new spectrumIdentificationResultTableMouseClicked(
						proteoIDViewer, SpectrumSummary.this));
		peptideEvidenceTable
				.addMouseListener(new peptideEvidenceTableMouseClicked(
						proteoIDViewer, proteinDBView));
		spectrumIdentificationItemTable
				.addMouseListener(new spectrumIdentificationItemTableMouseClicked(
						proteoIDViewer, SpectrumSummary.this));

		spectrumIdentificationResultTable
				.setToolTipText("this corresponds to Spectrum Identification Result in mzIdentML");
	}

	private void createSpectrumSummary() {
		final JPanel jFragmentationPanel = new JPanel();
		jFragmentationPanel.setLayout(new BorderLayout());
		jFragmentationPanel.add(new JScrollPane(fragmentationTable));
		jFragmentationPanel.setBorder(BorderFactory
				.createTitledBorder("Fragmentation"));
		jFragmentationPanel.setAutoscrolls(true);
		jFragmentationPanel.setMinimumSize(new Dimension(0, 0));
		jFragmentationPanel.setPreferredSize(new Dimension(383, 447));

		jGraph.setBorder(BorderFactory.createTitledBorder("Graph"));
		jGraph.setLayout(new BoxLayout(jGraph, BoxLayout.LINE_AXIS));

		jExperimentalFilterPanel.setBorder(BorderFactory
				.createTitledBorder("Experimental Filtering"));

		final JPanel jSpectrumIdentificationResultPanel = new JPanel();
		jSpectrumIdentificationResultPanel.getAccessibleContext()
				.setAccessibleDescription("Spectrum Identification Result");
		jSpectrumIdentificationResultPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationResultPanel.add(new JScrollPane(
				spectrumIdentificationResultTable));
		jSpectrumIdentificationResultPanel.setBorder(BorderFactory
				.createTitledBorder("Spectrum List"));
		jSpectrumIdentificationResultPanel
				.setToolTipText("Protein Ambiguity Group");
		jSpectrumIdentificationResultPanel.setMinimumSize(new Dimension(404,
				569));

		final JPanel jSpectrumIdentificationItemPanel = new JPanel();
		jSpectrumIdentificationItemPanel.setLayout(new BorderLayout());
		jSpectrumIdentificationItemPanel.add(new JScrollPane(
				spectrumIdentificationItemTable));
		jSpectrumIdentificationItemPanel.setBorder(BorderFactory
				.createTitledBorder("Peptide-Spectrum matches"));
		jSpectrumIdentificationItemPanel
				.setToolTipText("Spectrum Identification Item");
		jSpectrumIdentificationItemPanel.setAutoscrolls(true);

		final JPanel jPeptideEvidencePanel = new JPanel();
		jPeptideEvidencePanel.setLayout(new BorderLayout());
		jPeptideEvidencePanel.add(new JScrollPane(peptideEvidenceTable));
		jPeptideEvidencePanel.setBorder(BorderFactory
				.createTitledBorder("Peptide Evidence"));
		jPeptideEvidencePanel.setToolTipText("Peptide Evidence");
		jPeptideEvidencePanel.setAutoscrolls(true);

		final JPanel leftPanel = new JPanel(new GridLayout(3, 1));
		leftPanel.add(jSpectrumIdentificationResultPanel);
		leftPanel.add(jSpectrumIdentificationItemPanel);
		leftPanel.add(jPeptideEvidencePanel);

		final JPanel spectrumPanel = new JPanel(new GridLayout(3, 1));
		spectrumPanel.setBorder(BorderFactory.createTitledBorder("Spectrum"));
		spectrumPanel.setAutoscrolls(true);
		spectrumPanel.add(jGraph);
		spectrumPanel.add(jExperimentalFilterPanel);
		spectrumPanel.add(jFragmentationPanel);

		setBorder(null);
		setDividerLocation(500);
		setRightComponent(spectrumPanel);
		setLeftComponent(leftPanel);
	}

	public JTable getFragmentationTable() {
		return fragmentationTable;
	}

	public JPanel getGraph() {
		return jGraph;
	}

	public JTable getIdentificationItemTable() {
		return spectrumIdentificationItemTable;
	}

	public JTable getIdentificationResultTable() {
		return spectrumIdentificationResultTable;
	}

	public void reset(String[] sir,
			int spectrumIdentificationResultCvParamLengs,
			String[] spectrumIdentificationItemTableHeaders) {
		String[] spectrumIdentificationResultTableHeaders = new String[spectrumIdentificationResultCvParamLengs + 2];
		spectrumIdentificationResultTableHeaders[0] = "ID";
		spectrumIdentificationResultTableHeaders[1] = "Spectrum ID";

		if (sir != null) {
			for (int i = 0; i < sir.length; i++) {
				String string = sir[i];
				spectrumIdentificationResultTableHeaders[2 + i] = string;

			}
		}

		spectrumIdentificationResultTable.setModel(new DefaultTableModel(
				new Object[][] {}, spectrumIdentificationResultTableHeaders) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		// spectrumIdentificationResultTable.setAutoCreateRowSorter(true);

		spectrumIdentificationResultTable.removeAll();

		fragmentationTable.setModel(new DefaultTableModel(new Object[][] {},
				fragmentationTableHeaders) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		fragmentationTable.removeAll();
		// fragmentationTable.setAutoCreateRowSorter(true);

		peptideEvidenceTable.setModel(new DefaultTableModel(new Object[][] {},
				peptideEvidenceTableHeaders) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		peptideEvidenceTable.removeAll();

		spectrumIdentificationItemTable.setModel(new DefaultTableModel(
				new Object[][] {}, spectrumIdentificationItemTableHeaders) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		spectrumIdentificationItemTable.removeAll();
		// spectrumIdentificationItemTable.setAutoCreateRowSorter(true);
		// peptideEvidenceTable.setAutoCreateRowSorter(true);

		jExperimentalFilterPanel.removeAll();

		jGraph.removeAll();

		jGraph.validate();
		jGraph.repaint();
	}

	public void removeAllEvidence() {
		((DefaultTableModel) peptideEvidenceTable.getModel()).setNumRows(0);
	}

	public void addEvidence(Object[] objects) {
		((DefaultTableModel) peptideEvidenceTable.getModel()).addRow(objects);
	}

	public void removeAllFragmentation() {
		((DefaultTableModel) fragmentationTable.getModel()).setNumRows(0);

	}

	public void removeAllIdentificationItems() {
		((DefaultTableModel) spectrumIdentificationItemTable.getModel())
				.setNumRows(0);
	}

	@Override
	public void load() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Iterator<SpectrumIdentificationResult> iterSpectrumIdentificationResult = proteoIDViewer
				.getMzIdentMLUnmarshaller()
				.unmarshalCollectionFromXpath(
						MzIdentMLElement.SpectrumIdentificationResult);

		DefaultTableModel spectrumIdentificationResultTableModel = (DefaultTableModel) getIdentificationResultTable().getModel();
		spectrumIdentificationResultTableModel.setRowCount(0);
		getIdentificationResultTable().getTableHeader()
				.setEnabled(false);
		while (iterSpectrumIdentificationResult.hasNext()) {
			SpectrumIdentificationResult spectrumIdentificationResult = iterSpectrumIdentificationResult
					.next();

			spectrumIdentificationResultTableModel.addRow(new String[] {
					spectrumIdentificationResult.getId(),
					spectrumIdentificationResult.getSpectrumID() });

			List<CvParam> cvParamListspectrumIdentificationResult = spectrumIdentificationResult
					.getCvParam();

			for (int s = 0; s < cvParamListspectrumIdentificationResult
					.size(); s++) {
				CvParam cvParam = cvParamListspectrumIdentificationResult
						.get(s);
				String name = cvParam.getName();
				for (int j = 0; j < spectrumIdentificationResultTableModel
						.getColumnCount(); j++) {
					if (!spectrumIdentificationResultTableModel
							.getColumnName(j).equals(name))
						continue;
					spectrumIdentificationResultTableModel.setValueAt(
							cvParam.getValue(),
							spectrumIdentificationResultTableModel
									.getRowCount() - 1, j);

				}
			}
		}
		getIdentificationResultTable().getTableHeader()
				.setEnabled(true);

		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
