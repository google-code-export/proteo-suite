package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;

public class peptideEvidenceTableMouseClicked implements MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final JTable peptideEvidenceTable;
	private final JTable dBSequenceTable;
	private final JTabbedPane mainTabbedPane;

	public peptideEvidenceTableMouseClicked(ProteoIDViewer proteoIDViewer, JTable peptideEvidenceTable, JTable dBSequenceTable, JTabbedPane mainTabbedPane) {
		this.proteoIDViewer = proteoIDViewer;
		this.peptideEvidenceTable = peptideEvidenceTable;
		this.dBSequenceTable = dBSequenceTable;
		this.mainTabbedPane = mainTabbedPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = peptideEvidenceTable.getSelectedRow();
		if (row != -1) {
			// row = peptideEvidenceTable.convertRowIndexToModel(row);
			String db_ref = (String) peptideEvidenceTable.getValueAt(row, 6);

			int rowCount = dBSequenceTable.getModel().getRowCount();
			for (int i = 0; i < rowCount; i++) {
				if (db_ref.equals((String) dBSequenceTable.getValueAt(i, 0))) {

					dBSequenceTable.setRowSelectionInterval(i, i);
				}

			}
		}
		if (!proteoIDViewer.fourthTab) {
			proteoIDViewer.loadDBSequenceTable();
			proteoIDViewer.fourthTab = true;
		}
		mainTabbedPane.setSelectedIndex(3);		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
