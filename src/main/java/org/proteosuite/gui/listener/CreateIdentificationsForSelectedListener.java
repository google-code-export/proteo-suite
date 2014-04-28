package org.proteosuite.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;

import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.identification.MSGFPlusWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 * 
 * @author SPerkins
 */
public class CreateIdentificationsForSelectedListener implements ActionListener {

	private CreateOrLoadIdentificationsStep step;

	public CreateIdentificationsForSelectedListener(
			CreateOrLoadIdentificationsStep step) {
		this.step = step;
	}

	@Override
	public void actionPerformed(ActionEvent event) {		
		AnalyseData data = AnalyseData.getInstance();

		// Get the identParamsView.
		final IdentParamsView identParamsExecute = step.getIdentParamsView();

		// Create a dialog for the view, while finding the parent JFrame.
		Component parent = step.getParent();
		while (!(parent instanceof JFrame)) {
			parent = parent.getParent();
		}

		// Add the view to the content pane.
		identParamsExecute.setVisible(true);

		// Check if we're okay to run identifications..
		boolean paramsSetOkay = identParamsExecute.getRun();
		
		if (!paramsSetOkay)
			return;

		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
				.setIdentificationsProcessing();

		// Get the selected raw files from the table to know which ones to run
		// ident for.
		int[] selectedRawFiles = step.getIdentificationsTable()
				.getSelectedRows();
		for (int fileIndex = 0; fileIndex < selectedRawFiles.length; fileIndex++) {
			
			Map<String, String> runParams = identParamsExecute.getParams();

			// Inject thread setting.
			runParams.put("-thread", String.valueOf(AnalyseData.MAX_THREADS));

			// Create an MSGFPlusWrapper from the view's params.
			MSGFPlusWrapper msgf = new MSGFPlusWrapper(runParams);

			// First get the spectrum file we want to run identifications for in
			// this run.
			RawDataFile rawDataFile = data
					.getRawDataFile(selectedRawFiles[fileIndex]);
                        rawDataFile.setIdentStatus("Creating...");
                        step.refreshFromData();

			// Set the spectrum file as the input to MSGF+
			msgf.setInputSpectrum(rawDataFile);

			msgf.performSearch(5);
		}
	}
}
