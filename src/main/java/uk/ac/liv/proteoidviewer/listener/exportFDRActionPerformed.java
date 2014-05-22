package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.util.CsvFileFilter;

public class exportFDRActionPerformed implements ActionListener {

	private ProteoIDViewer proteoIDViewer;

	public exportFDRActionPerformed(ProteoIDViewer proteoIDViewer) {
		this.proteoIDViewer = proteoIDViewer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new CsvFileFilter());
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle("Export FDR");

		int returnVal = chooser.showSaveDialog(proteoIDViewer);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File selectedFile = chooser.getSelectedFile();

		if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
			selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
		}

		while (selectedFile.exists()) {
			int option = JOptionPane.showConfirmDialog(proteoIDViewer,
					"The file " + chooser.getSelectedFile().getName()
							+ " already exists. Replace file?",
					"Replace File?", JOptionPane.YES_NO_CANCEL_OPTION);

			if (option == JOptionPane.NO_OPTION) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new CsvFileFilter());
				chooser.setMultiSelectionEnabled(false);
				chooser.setDialogTitle("Export FDR");

				returnVal = chooser.showSaveDialog(proteoIDViewer);

				if (returnVal == JFileChooser.CANCEL_OPTION) {
					return;
				} else {
					selectedFile = chooser.getSelectedFile();

					if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
						selectedFile = new File(selectedFile.getAbsolutePath()
								+ ".csv");
					}
				}
			} else { // YES option
				break;
			}
		}

		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		try {

			selectedFile = chooser.getSelectedFile();

			if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
			}

			if (selectedFile.exists()) {
				selectedFile.delete();
			}

			selectedFile.createNewFile();
			FileWriter f = new FileWriter(selectedFile);
			String outStrHead = "sorted_spectrumResult.get(i)\tsorted_peptideNames.get(i) \t sorted_decoyOrNot.get(i) \t  sorted_evalues.get(i).toString() \t + sorted_scores.get(i).toString() \t estimated_simpleFDR.get(i) \t estimated_qvalue.get(i) \t estimated_fdrscore.get(i) \n";
			f.write(outStrHead);
			for (int i = 0; i < proteoIDViewer.falseDiscoveryRate
					.getSorted_evalues().size(); i++) {

				String outStr = proteoIDViewer.falseDiscoveryRate
						.getSorted_spectrumResult().get(i)
						+ "\t"
						+ proteoIDViewer.falseDiscoveryRate
								.getSorted_peptideNames().get(i)
						+ "\t"
						+ proteoIDViewer.falseDiscoveryRate
								.getSorted_decoyOrNot().get(i)
						+ "\t"
						// + sorted_evalues.get(i).toString() + "\t" +
						// sorted_scores.get(i).toString() + "\t"
						+ proteoIDViewer.falseDiscoveryRate
								.getSorted_simpleFDR().get(i)
						+ "\t"
						+ proteoIDViewer.falseDiscoveryRate.getSorted_qValues()
								.get(i)
						+ "\t"
						+ proteoIDViewer.falseDiscoveryRate
								.getSorted_estimatedFDR().get(i) + "\n";

				f.write(outStr);
			}
			f.close();

		} catch (IOException ex) {
			JOptionPane
					.showMessageDialog(
							proteoIDViewer,
							"An error occured when exporting the spectra file details.",
							"Error Exporting", JOptionPane.ERROR_MESSAGE);
		}

		proteoIDViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}
}
