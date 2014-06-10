package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.proteosuite.WorkSpace;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.MzMLFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.RawDataFile;

public class OpenListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser openFile = new JFileChooser(WorkSpace.sPreviousLocation);
		openFile.setAcceptAllFileFilterUsed(false);
		openFile.setFileFilter(new FileNameExtensionFilter("MzQuantML (*.mzQ)",
				"mzq"));
		openFile.setFileFilter(new FileNameExtensionFilter("MzML (*.mzML)",
				"mzml"));
		openFile.setFileFilter(new FileNameExtensionFilter("MGF (*.mgf)",
				"mgf"));
		openFile.setFileFilter(new FileNameExtensionFilter(
				"MzIdentML (*.mzId)", "mzid"));
		int result = openFile.showOpenDialog(null);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		File file = openFile.getSelectedFile();
                if (!file.getParent().equals(WorkSpace.sPreviousLocation)) {
                        WorkSpace.sPreviousLocation = file.getParent();
                }                

		AnalyseData data = AnalyseData.getInstance();
		InspectModel model = AnalyseData.getInstance().getInspectModel();

		String name = file.getName();
		String[] tmp = name.split(Pattern.quote("."));
		String extension = tmp[tmp.length - 1];
		if (extension.equalsIgnoreCase("mzq")) {
			MzQuantMLFile quantDataFile = new MzQuantMLFile(file);
			model.addQuantDataFile(quantDataFile);
		} else if (extension.equalsIgnoreCase("mzid")) {
			// TODO: We possibly should poll the user here for an mzML file.
			Object[] selectionValues = new Object[model.getRawData().size() + 1];
			int i = 0;
			selectionValues[i++] = "None";
			for (RawDataFile rawDataFile : model.getRawData())
			{
				selectionValues[i++] = rawDataFile.getFileName();
			}
			String s = (String)JOptionPane.showInputDialog(
			                    null,
			                    "Please select the spectrum file",
			                    "Parent Spectrum File",
			                    JOptionPane.QUESTION_MESSAGE,
			                    null,
			                    selectionValues,
			                    "None");

			RawDataFile parent = null;
			if (s != null && !s.equals("None"))
			{
				parent = model.getRawDataFile(s);
			}
			
			MzIdentMLFile identDataFile = new MzIdentMLFile(file, parent);
			model.addIdentDataFile(identDataFile);
		} else if (extension.equalsIgnoreCase("mzml")) {
			MzMLFile rawDataFile = new MzMLFile(file);
			data.addRawDataFile(rawDataFile);

			AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
					.setRawDataProcessing();
			((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
					.refreshFromData();
		} else if (extension.equalsIgnoreCase("mgf")) {
            data.addRawDataFile(new MascotGenericFormatFile(file));
			AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
				.setRawDataProcessing();
			((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
					.refreshFromData();
		} else {
			System.out.println("Unknown File type");
		}
	}
}