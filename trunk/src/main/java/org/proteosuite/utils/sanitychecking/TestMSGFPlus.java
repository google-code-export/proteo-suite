package org.proteosuite.utils.sanitychecking;

import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.identification.MSGFPlusWrapper;

/**
 * 
 * @author SPerkins
 */
public class TestMSGFPlus {
	public static void main(String[] args) {
		String directory = "e:\\data\\label_free";
		String[] rawFiles = new String[] {
				"mam_042408o_CPTAC_study6_6B011.mzML",
				"mam_042408o_CPTAC_study6_6C008.mzML",
				"mam_042408o_CPTAC_study6_6D004.mzML",
				"mam_042408o_CPTAC_study6_6E004.mzML" };

		final IdentParamsView identParamsExecute = new IdentParamsView(null,
				false);

		// Add the view to the content pane.
		identParamsExecute.setVisible(true);

		// Check if we're okay to run identifications..
		boolean paramsSetOkay = identParamsExecute.hasRunSuccessfully();

		int executionDelay = 5;

		for (String rawFile : rawFiles) {

			// Create an MSGFPlusWrapper from the view's params.
			MSGFPlusWrapper msgf = new MSGFPlusWrapper(
					identParamsExecute.getSearchGUIParameterSet());

			// First get the spectrum file we want to run identifications for in
			// this run.

			// Set the spectrum file as the input to MSGF+
			msgf.setInputSpectrumString(directory + "\\" + rawFile);

			msgf.setModificationFileName(identParamsExecute
					.getModificationFile().getAbsolutePath());

			msgf.performSearch(executionDelay);
			executionDelay += 5;
		}
	}
}
