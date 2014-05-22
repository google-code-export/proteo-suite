package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.ac.liv.mzidlib.MzIdentMLToCSV;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.util.CsvFileFilter;

public class jMenuItem2ActionPerformed implements ActionListener {

	private final ProteoIDViewer proteoIDViewer;

	public jMenuItem2ActionPerformed(ProteoIDViewer proteoIDViewer) {
		this.proteoIDViewer = proteoIDViewer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MzIdentMLToCSV mzidToCsv = new MzIdentMLToCSV();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new CsvFileFilter());
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle("Export Protein Groups");

		File selectedFile;

		int returnVal = chooser.showSaveDialog(proteoIDViewer);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			selectedFile = chooser.getSelectedFile();

			if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
			}

			while (selectedFile.exists()) {
				int option = JOptionPane.showConfirmDialog(proteoIDViewer, "The  file "
						+ chooser.getSelectedFile().getName()
						+ " already exists. Replace file?", "Replace File?",
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (option == JOptionPane.NO_OPTION) {
					chooser = new JFileChooser();
					chooser.setFileFilter(new CsvFileFilter());
					chooser.setMultiSelectionEnabled(false);
					chooser.setDialogTitle("Export Protein Groups");

					returnVal = chooser.showSaveDialog(proteoIDViewer);

					if (returnVal == JFileChooser.CANCEL_OPTION) {
						return;
					} else {
						selectedFile = chooser.getSelectedFile();

						if (!selectedFile.getName().toLowerCase()
								.endsWith(".csv")) {
							selectedFile = new File(
									selectedFile.getAbsolutePath() + ".csv");
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
					selectedFile = new File(selectedFile.getAbsolutePath()
							+ ".csv");
				}

				if (selectedFile.exists()) {
					selectedFile.delete();
				}

				mzidToCsv.useMzIdentMLToCSV(proteoIDViewer.mzIdentMLUnmarshaller,
						selectedFile.getPath(), "exportProteinGroups", false);

			} catch (Exception ex) {
				JOptionPane
						.showMessageDialog(
								proteoIDViewer,
								"An error occured when exporting the spectra file details.",
								"Error Exporting", JOptionPane.ERROR_MESSAGE);
			}

			proteoIDViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
