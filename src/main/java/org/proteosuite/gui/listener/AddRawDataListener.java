package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.proteosuite.WorkSpace;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzMLFile;

/**
 * 
 * @author SPerkins
 */
public class AddRawDataListener implements ActionListener {
	private final RawDataAndMultiplexingStep step;

	public AddRawDataListener(RawDataAndMultiplexingStep step) {
		this.step = step;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		AnalyseData data = AnalyseData.getInstance();
		JFileChooser chooser = new JFileChooser(
				WorkSpace.sPreviousLocation);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileFilter(new FileNameExtensionFilter("mzML/MGF Data Files",
				"mzML", "mgf"));
		chooser.setAcceptAllFileFilterUsed(false);
		int returnVal = chooser.showOpenDialog(step);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File[] files = chooser.getSelectedFiles();
		if (!files[0].getParent().equals(WorkSpace.sPreviousLocation)) {
			WorkSpace.sPreviousLocation = files[0].getParent();
		}

		for (File f : files) {
                        if (f.getName().toUpperCase().endsWith("MGF")) {
                            data.addRawDataFile(new MascotGenericFormatFile(f));
                        } else if (f.getName().toUpperCase().endsWith("MZML")) {
                            data.addRawDataFile(new MzMLFile(f));
                        }			
		}

		AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
				.setRawDataProcessing();
		step.refreshFromData();
	}
}
