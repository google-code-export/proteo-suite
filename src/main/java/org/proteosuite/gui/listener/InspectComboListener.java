package org.proteosuite.gui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTable;

import org.proteosuite.gui.inspect.InspectQuant;
import org.proteosuite.gui.inspect.InspectRaw;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tables.JTableFeatureQuant;
import org.proteosuite.gui.tables.JTablePeptideQuant;
import org.proteosuite.gui.tables.JTableProteinQuant;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.RawMzMLFile;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;

/**
 * 
 * @author SPerkins
 */
public class InspectComboListener implements ItemListener {
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() != ItemEvent.SELECTED)
			return;

		// Populate the table.
		String fileChosen = (String) e.getItem();
		if (inspectModel.isRawDataFile(fileChosen))
			openRawFile(fileChosen);
		else if (inspectModel.isIdentFile(fileChosen))
			openIdentFile(fileChosen);
		else if (inspectModel.isQuantFile(fileChosen))
			openQuantFile(fileChosen);
	}

	private void openQuantFile(String fileChosen) {
		InspectTab.getInstance().setInspectType(InspectTab.PANEL_QUANT);
		InspectQuant quantPanel = (InspectQuant) InspectTab.getInstance()
				.getContentPanel();

		MzQuantMLFile dataFile = (MzQuantMLFile) inspectModel
				.getQuantDataFile(fileChosen);

		JTablePeptideQuant peptideQuant = new JTablePeptideQuant();
		peptideQuant.showData(dataFile);

		JTable jTable = quantPanel.getPeptideTable();
		jTable.removeAll();
		jTable.setModel(peptideQuant.getModel());

		JTableProteinQuant proteinQuant = new JTableProteinQuant();
		proteinQuant.showData(dataFile);

		jTable = quantPanel.getProteinTable();
		jTable.removeAll();
		jTable.setModel(proteinQuant.getModel());

		JTableFeatureQuant featureQuant = new JTableFeatureQuant();
		featureQuant.showData(dataFile);

		jTable = quantPanel.getFeatureTable();
		jTable.removeAll();
		jTable.setModel(featureQuant.getModel());
	}

	private void openRawFile(String fileChosen) {
		InspectTab.getInstance().setInspectType(InspectTab.PANEL_RAW);

		RawMzMLFile dataFile = (RawMzMLFile) inspectModel
				.getRawDataFile(fileChosen);

		InspectRaw rawPanel = (InspectRaw) InspectTab.getInstance()
				.getContentPanel();
		rawPanel.setData(dataFile);
	}

	private void openIdentFile(String fileChosen) {
		InspectTab.getInstance().setInspectType(InspectTab.PANEL_IDENT);
		MzIdentMLFile identDataFile = (MzIdentMLFile) inspectModel.getIdentDataFile(fileChosen);

		ProteoIDViewer proteoIDViewer = (ProteoIDViewer) InspectTab.getInstance()
				.getContentPanel();
		proteoIDViewer.setMzIdentMLFile(identDataFile);
	}
}
