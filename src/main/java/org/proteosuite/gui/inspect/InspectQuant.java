package org.proteosuite.gui.inspect;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class InspectQuant extends JTabbedPane {
	private final JTable peptideTable = new JTable();
	private final JTable proteinTable = new JTable();
	private final JTable featureTable = new JTable();

	public InspectQuant() {
		setTabPlacement(JTabbedPane.BOTTOM);
		add("Peptides", new JScrollPane(peptideTable));
		add("Proteins", new JScrollPane(proteinTable));
		add("Features", new JScrollPane(featureTable));

		peptideTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		peptideTable.setAutoCreateRowSorter(true);

		proteinTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		proteinTable.setAutoCreateRowSorter(true);

		featureTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		featureTable.setAutoCreateRowSorter(true);
	}

	public JTable getPeptideTable() {
		return peptideTable;
	}

	public JTable getProteinTable() {
		return proteinTable;
	}

	public JTable getFeatureTable() {
		return featureTable;
	}
}
