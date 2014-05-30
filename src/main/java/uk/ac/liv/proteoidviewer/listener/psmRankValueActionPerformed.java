package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;

public class psmRankValueActionPerformed implements ActionListener {

	private final ProteoIDViewer proteoIDViewer;
	private final PeptideSummary peptideSummary;

	public psmRankValueActionPerformed(ProteoIDViewer proteoIDViewer, PeptideSummary peptideSummary) {
		this.proteoIDViewer = proteoIDViewer;
		this.peptideSummary = peptideSummary;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		peptideSummary.loadPeptideTable(proteoIDViewer.mzIdentMLUnmarshaller);		
	}

}
