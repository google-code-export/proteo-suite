package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;

public class ProteinDBView extends JPanel implements LazyLoading {
	private static final long serialVersionUID = 1L;
	private static final String[] dBSequenceTableHeaders = new String[] { "ID",
			"Accession", "Seq", "Protein Description" };

	private final JTable dBSequenceTable = new JTable();
	private final ProteoIDViewer proteoIDViewer;

	private boolean isLoaded = false;

	public ProteinDBView(ProteoIDViewer proteoIDViewer) {
		this.proteoIDViewer = proteoIDViewer;
		addComponentListener(new LazyLoadingComponentListener());
		// dBSequenceTable Table
		dBSequenceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dBSequenceTable.getTableHeader().setReorderingAllowed(false);

		setLayout(new BorderLayout());
		add(new JScrollPane(dBSequenceTable));
		setBorder(BorderFactory.createTitledBorder("DB Sequence"));
	}

	public int getRowCount() {
		return dBSequenceTable.getModel().getRowCount();
	}

	public JTable getTable() {
		return dBSequenceTable;
	}

	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	public void load() {
		MzIdentMLUnmarshaller mzIdentMLUnmarshaller = proteoIDViewer
				.getMzIdentMLUnmarshaller();
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
		isLoaded = true;
	}

	public void reset() {
		// dBSequence view dBSequenceTable
		// dBSequenceTable.setAutoCreateRowSorter(true);
		dBSequenceTable.setModel(new DefaultTableModel(new Object[][] {},
				dBSequenceTableHeaders) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		((DefaultTableModel) dBSequenceTable.getModel()).setRowCount(0);
	}
}
