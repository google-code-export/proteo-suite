package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;

public class peptideEvidenceTableMouseClicked implements MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final SpectrumSummary spectrumSummary;
	private final ProteinDBView proteinDBView;

	public peptideEvidenceTableMouseClicked(ProteoIDViewer proteoIDViewer, SpectrumSummary spectrumSummary, ProteinDBView proteinDBView) {
		this.proteoIDViewer = proteoIDViewer;
		this.spectrumSummary = spectrumSummary;
		this.proteinDBView = proteinDBView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = spectrumSummary.getEvidenceTable().getSelectedRow();
		if (row != -1) {
			// row = peptideEvidenceTable.convertRowIndexToModel(row);
			String db_ref = (String) spectrumSummary.getEvidenceTable().getValueAt(row, 6);

			int rowCount = proteinDBView.getTable().getModel().getRowCount();
			for (int i = 0; i < rowCount; i++) {
				if (db_ref.equals((String) proteinDBView.getTable().getValueAt(i, 0))) {

					proteinDBView.getTable().setRowSelectionInterval(i, i);
				}

			}
		}
		if (!proteoIDViewer.fourthTab) {
			proteinDBView.loadDBSequenceTable(proteoIDViewer.getMzIdentMLUnmarshaller());
			proteoIDViewer.fourthTab = true;
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
