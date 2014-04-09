package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.RawMzMLFile;

public class OpenListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser openFile = new JFileChooser();
		openFile.setAcceptAllFileFilterUsed(false);
		openFile.setFileFilter(new FileNameExtensionFilter("MzQuantML (*.mzq)",
				"mzq"));
		openFile.setFileFilter(new FileNameExtensionFilter("MzML (*.mzML)",
				"mzml"));
		int result = openFile.showOpenDialog(null);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		File file = openFile.getSelectedFile();

		AnalyseData data = AnalyseData.getInstance();
		InspectModel model = AnalyseData.getInstance().getInspectModel();

		String name = file.getName();
		String[] tmp = name.split(Pattern.quote("."));
		String extension = tmp[tmp.length - 1];
		if (extension.equalsIgnoreCase("mzq")) {
			MzQuantMLFile quantDataFile = new MzQuantMLFile(file);
			model.addQuantDataFile(quantDataFile);
		} else if (extension.equalsIgnoreCase("mzml")) {
			RawMzMLFile rawDataFile = new RawMzMLFile(file);
			data.addRawDataFile(rawDataFile);

			AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
					.setRawDataProcessing();
			((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
					.refreshFromData();
		}
		else
		{
			System.out.println("Unknown File type");
		}
	}
}