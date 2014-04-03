package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.analyse.DefineConditionsStep;
import org.proteosuite.gui.analyse.ITRAQStep;
import org.proteosuite.gui.analyse.LabelFreeStep;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.tables.DefineConditionsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 * 
 * @author SPerkins
 */
public class ContinueButtonListener implements ActionListener {
	private JPanel panel;

	public ContinueButtonListener(JPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		AnalyseDynamicTab parent = (AnalyseDynamicTab) panel.getParent();
		AnalyseData data = AnalyseData.getInstance();

		if (panel instanceof RawDataAndMultiplexingStep) {
			String selectedMultiplexing = (String) ((RawDataAndMultiplexingStep) panel)
					.getMultiplexingBox().getSelectedItem();
			boolean multiplexChange = !selectedMultiplexing.equals(data
					.getMultiplexing());
			data.setMultiplexing((String) ((RawDataAndMultiplexingStep) panel)
					.getMultiplexingBox().getSelectedItem());
			for (int i = 0; i < data.getRawDataCount(); i++) {
				RawDataFile file = data.getRawDataFile(i);
				if (multiplexChange) {
					file.resetAssay();
				}

				if (data.getMultiplexing().equals("iTRAQ 4-plex")) {
					file.setAssays(new String[] { "114", "115", "116", "117" });
				} else if (data.getMultiplexing().equals("None (label-free)")) {
					file.setAssays(new String[] { "" });
				}
			}

			((DefineConditionsStep) AnalyseDynamicTab.DEFINE_CONDITIONS_STEP)
					.refreshFromData();

			parent.moveToStep(AnalyseDynamicTab.DEFINE_CONDITIONS_STEP);

		} else if (panel instanceof DefineConditionsStep) {
			defineConditionsStep();

		} else if (panel instanceof CreateOrLoadIdentificationsStep) {
			switch (data.getMultiplexing()) {
			case "None (label-free)":
				((LabelFreeStep) AnalyseDynamicTab.LABEL_FREE_STEP)
						.refreshFromData();
				parent.moveToStep(AnalyseDynamicTab.LABEL_FREE_STEP);
				break;
			case "iTRAQ 4-plex":
				((ITRAQStep) AnalyseDynamicTab.ITRAQ_STEP).refreshFromData();
				parent.moveToStep(AnalyseDynamicTab.ITRAQ_STEP);
				break;
			case "iTRAQ 8-plex":
				break;
			}
		}
	}

	private void defineConditionsStep() {
		DefineConditionsTable conditionsTable = ((DefineConditionsStep) panel)
				.getConditionsTable();
		if (!conditionsTable.isConditionsValid()) {
			JOptionPane
					.showConfirmDialog(
							panel,
							"You have not set a condition for all multiplex assays.\nPlease correct this before moving to the next stage.",
							"Missing Conditions", JOptionPane.PLAIN_MESSAGE,
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		AnalyseData data = AnalyseData.getInstance();
		AnalyseDynamicTab parent = (AnalyseDynamicTab) panel.getParent();

		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
				.setConditionsProcessing();

		for (int i = 0; i < conditionsTable.getRowCount(); i++) {
			String condition = (String) conditionsTable.getModel().getValueAt(
					i, 0);

			String fileName = (String) conditionsTable.getModel().getValueAt(i,
					1);
			String assay = (String) conditionsTable.getModel().getValueAt(i, 2);

			for (int j = 0; j < data.getRawDataCount(); j++) {
				RawDataFile dataFile = data.getRawDataFile(j);
				if (dataFile.getFileName().equals(fileName)) {
					if (dataFile.getConditions().containsKey(assay)) {
						dataFile.getConditions().put(assay, condition);
					}
				}
			}
		}

		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
				.setConditionsDone();

		((CreateOrLoadIdentificationsStep) AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)
				.refreshFromData();
		parent.moveToStep(AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP);
	}
}