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
import org.proteosuite.gui.inspect.InspectQuant;
import org.proteosuite.gui.inspect.InspectRaw;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tables.JTableFeatureQuant;
import org.proteosuite.gui.tables.JTableMzML;
import org.proteosuite.gui.tables.JTablePeptideQuant;
import org.proteosuite.gui.tables.JTableProteinQuant;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MzQuantMLFile;
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
		JTableMzML rawData = new JTableMzML();
		rawData.showData(dataFile);

		InspectRaw rawPanel = (InspectRaw) InspectTab.getInstance()
				.getContentPanel();

		JTable jTable = rawPanel.getTablePanel();
		jTable.removeAll();
		jTable.setModel(rawData.getModel());

		rawData.getSelectionModel().addListSelectionListener(rawPanel);
		rawPanel.getChartPanel().setChromatogram(
				ChartChromatogram.getChromatogram(dataFile));
		rawPanel.getChartPanel().set2D(ChartPlot2D.get2DPlot(dataFile));
	}

	private void openIdentFile(String fileChosen) {
		InspectTab.getInstance().setInspectType(InspectTab.PANEL_IDENT);

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
			search.addChoosableFileFilter(new FileNameExtensionFilter(
					"ProteoIDViewer.jar", "jar"));
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
			try {
				if (proc.exitValue() != 0) {
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
			} catch (IllegalThreadStateException e) {
				// Ran fine!
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
