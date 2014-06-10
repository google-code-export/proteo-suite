package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;

public class psmRankValueActionPerformed implements ActionListener {

	private final PeptideSummary peptideSummary;

	public psmRankValueActionPerformed(PeptideSummary peptideSummary) {
		this.peptideSummary = peptideSummary;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//peptideSummary.load();		
	}

}
