package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
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
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.listener.peptideEvidenceTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationItemTableMouseClicked;
import uk.ac.liv.proteoidviewer.listener.spectrumIdentificationResultTableMouseClicked;

public class SpectrumSummary extends JSplitPane {
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


	public SpectrumSummary(ProteoIDViewer proteoIDViewer, ProteinDBView proteinDBView) {

		// spectrum tab
		// spectrum Identification Result Table
		spectrumIdentificationResultTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationResultTable.addMouseListener(new spectrumIdentificationResultTableMouseClicked(proteoIDViewer, SpectrumSummary.this));		
		
		spectrumIdentificationResultTable.getTableHeader()
				.setReorderingAllowed(false);

		spectrumIdentificationResultTable
				.setToolTipText("this corresponds to Spectrum Identification Result in mzIdentML");

		// fragmentation Table
		fragmentationTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fragmentationTable.getTableHeader().setReorderingAllowed(false);

		// peptide Evidence Table
		peptideEvidenceTable.getTableHeader().setReorderingAllowed(false);
		peptideEvidenceTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		peptideEvidenceTable
				.addMouseListener(new peptideEvidenceTableMouseClicked(proteoIDViewer,
						this, proteinDBView));


		// spectrum Identification Item Table
		spectrumIdentificationItemTable.getTableHeader().setReorderingAllowed(
				false);
		spectrumIdentificationItemTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spectrumIdentificationItemTable.addMouseListener(new spectrumIdentificationItemTableMouseClicked(proteoIDViewer, SpectrumSummary.this));
		
		createSpectrumSummary();
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

	public void loadSpectrumIdentificationResultTable(
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
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

	public void reset(String[] sir, int spectrumIdentificationResultCvParamLengs, String[] spectrumIdentificationItemTableHeaders) {
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
		});
		// spectrumIdentificationResultTable.setAutoCreateRowSorter(true);

		spectrumIdentificationResultTable.removeAll();


		fragmentationTable.setModel(new DefaultTableModel(new Object[][] {},
				fragmentationTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		fragmentationTable.removeAll();
		// fragmentationTable.setAutoCreateRowSorter(true);
		


		peptideEvidenceTable.setModel(new DefaultTableModel(new Object[][] {},
				peptideEvidenceTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		peptideEvidenceTable.removeAll();


		spectrumIdentificationItemTable.setModel(new DefaultTableModel(
				new Object[][] {}, spectrumIdentificationItemTableHeaders) {
			private static final long serialVersionUID = 1L;
		});
		spectrumIdentificationItemTable.removeAll();
		// spectrumIdentificationItemTable.setAutoCreateRowSorter(true);
		// peptideEvidenceTable.setAutoCreateRowSorter(true);
		
		jExperimentalFilterPanel.removeAll();

		jGraph.removeAll();

		jGraph.validate();
		jGraph.repaint();
	}

	@Deprecated
	public JTable getTable() {
		return spectrumIdentificationResultTable;
	}

	@Deprecated
	public JTable getIdentificationTable() {
		return spectrumIdentificationResultTable;
	}
	
	public JTable getIdentificationResultTable() {
		return spectrumIdentificationResultTable;
	}
	
	public JTable getIdentificationItemTable()
	{
		return spectrumIdentificationItemTable;
	}

	public JTable getFragmentationTable() {
		return this.fragmentationTable;
	}

	public JTable getEvidenceTable() {
		return peptideEvidenceTable;
	}

	public JPanel getGraph() {
		return jGraph;
	}
}
