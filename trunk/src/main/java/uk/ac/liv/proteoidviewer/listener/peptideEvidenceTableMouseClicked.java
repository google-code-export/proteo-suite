package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;

public class peptideEvidenceTableMouseClicked implements MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final ProteinDBView proteinDBView;

	public peptideEvidenceTableMouseClicked(ProteoIDViewer proteoIDViewer, ProteinDBView proteinDBView) {
		this.proteoIDViewer = proteoIDViewer;
		this.proteinDBView = proteinDBView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable spectrumSummaryEvidence = (JTable) e.getSource();
		int row = spectrumSummaryEvidence.getSelectedRow();
		if (row != -1) {
			// row = peptideEvidenceTable.convertRowIndexToModel(row);
			String db_ref = (String) spectrumSummaryEvidence.getValueAt(row, 6);

			int rowCount = proteinDBView.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				if (db_ref.equals((String) proteinDBView.getTable().getValueAt(i, 0))) {

					proteinDBView.getTable().setRowSelectionInterval(i, i);
				}
			}
		}
		if (!proteoIDViewer.isFourthTabLoaded) {
			proteinDBView.loadDBSequenceTable(proteoIDViewer.getMzIdentMLUnmarshaller());
			proteoIDViewer.isFourthTabLoaded = true;
		}
		proteoIDViewer.setSelectedIndex(3);		
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
