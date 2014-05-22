package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;

public class psmRankValueActionPerformed implements ActionListener {

	private ProteoIDViewer proteoIDViewer;

	public psmRankValueActionPerformed(ProteoIDViewer proteoIDViewer) {
		this.proteoIDViewer = proteoIDViewer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		proteoIDViewer.loadPeptideTable();		
	}

}
