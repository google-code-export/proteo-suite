package org.proteosuite.gui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawDataFile;

public class RawDataTableKeyListener implements KeyListener {
	private final RawDataAndMultiplexingStep step;

	public RawDataTableKeyListener(
			RawDataAndMultiplexingStep rawDataAndMultiplexingStep) {
		step = rawDataAndMultiplexingStep;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_DELETE)
			return;
		
		JTable t = (JTable) e.getSource();
		AnalyseData data = AnalyseData.getInstance();
		InspectModel model = data.getInspectModel();
		
		int result = JOptionPane
				.showConfirmDialog(
						step,
						"Are you sure you wish to delete the entries for the selected raw files?\nYour raw files will NOT be deleted, but any related data in Proteosuite will be lost.",
						"Delete Selected Files", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			int[] selectedRows = t.getSelectedRows();
			Arrays.sort(selectedRows);
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				RawDataFile rawDataFile = data.getRawDataFile(selectedRows[i]);
				data.deleteRawDataFile(selectedRows[i]);
				model.removeRawDataFile(rawDataFile);
			}

			step.refreshFromData();
			
			if (t.getRowCount() == 0)
				AnalyseData.getInstance().clear();
		}
        InspectTab.getInstance().refreshComboBox();
	}

}
