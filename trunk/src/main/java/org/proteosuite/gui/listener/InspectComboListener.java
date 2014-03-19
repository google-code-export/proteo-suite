package org.proteosuite.gui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tables.JTableMzML;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.RawMzMLFile;

/**
 * 
 * @author SPerkins
 */
public class InspectComboListener implements ItemListener {
	private static String path = "C:" + File.separatorChar
			+ "ProtoIDViewer-1.4" + File.separatorChar + "ProteoIDViewer.jar";
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() != ItemEvent.SELECTED)
			return;

		// Populate the table.
		String fileChosen = (String) e.getItem();
		if (inspectModel.isRawDataFile(fileChosen)) {
			RawDataFile dataFile = inspectModel.getRawDataFile(fileChosen);
			JTableMzML rawData = new JTableMzML();
			rawData.showData((RawMzMLFile) dataFile);

			JTable jTable = InspectTab.getInstance().getTablePanel();
			jTable.removeAll();
			jTable.setModel(rawData.getModel());

			// InspectTab.getInstance().getTablePanel().setTable(rawData);
			rawData.getSelectionModel().addListSelectionListener(
					InspectTab.getInstance());
			InspectTab
					.getInstance()
					.getChartPanel()
					.setChromatogram(
							ChartChromatogram
									.getChromatogram((RawMzMLFile) dataFile));
			InspectTab.getInstance().getChartPanel()
					.set2D(ChartPlot2D.get2DPlot((RawMzMLFile) dataFile));
		} else if (inspectModel.isIdentFile(fileChosen)) {
			openIdentFile(fileChosen);
		} else if (inspectModel.isQuantFile(fileChosen)) {

		}
	}

	private void openIdentFile(String fileChosen) {
		int response = JOptionPane.showConfirmDialog(null,
				"Open in ProteoIDViewer?", "View MzIdentML",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (response == JOptionPane.NO_OPTION)
			return;

		File f = new File(path);
		while (!f.exists()) {
			response = JOptionPane.showConfirmDialog(null,
					"ProteoIDViewer not found. Would you like to locate it?",
					"ProteoIDViewer not found", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE);
			if (response == JOptionPane.CANCEL_OPTION)
				return;

			JFileChooser search = new JFileChooser();
			search.setAcceptAllFileFilterUsed(false);
			search.addChoosableFileFilter(new FileNameExtensionFilter("ProteoIDViewer.jar", "jar"));
			search.showOpenDialog(null);

			f = search.getSelectedFile();
		}

		path = f.getAbsolutePath();
		String fileName = inspectModel.getIdentDataFile(fileChosen)
				.getAbsoluteFileName();

		try {
			Process proc = Runtime.getRuntime().exec(
					"java -jar " + path + " " + fileName);

			Thread.sleep(5_000);
			// If it died in less than 5 seconds. Something went wrong!
			if (!proc.isAlive()) {
				InputStream err = proc.getErrorStream();
				String error = "";
				while (true) {
					int data = err.read();
					if (data == -1)
						break;

					error += (char) data;
				}
				if (error.length() > 0)
					JOptionPane.showMessageDialog(null, error, "Error",
							JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
