package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;

public class PsmRankValueActionPerformed implements ActionListener {

	private final PeptideSummary peptideSummary;

	public PsmRankValueActionPerformed(PeptideSummary peptideSummary) {
		this.peptideSummary = peptideSummary;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//peptideSummary.load();		
	}

}
