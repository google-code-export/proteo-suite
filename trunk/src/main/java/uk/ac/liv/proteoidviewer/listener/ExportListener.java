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

/**
 * 
 * @author Andrew Collins
 */
public class ExportListener implements ActionListener {
	public static final byte EXPORT_TYPE_PROTEIN = 0;
	public static final byte EXPORT_TYPE_PROTEIN_GROUP = 1;
	public static final byte EXPORT_TYPE_PSM = 2;

	private final ProteoIDViewer proteoIDViewer;
	private final String title;
	private final String option;
	
	public ExportListener(ProteoIDViewer proteoIDViewer, byte exportType)
	{
		this.proteoIDViewer = proteoIDViewer;
		switch (exportType)
		{
		case EXPORT_TYPE_PROTEIN:
			this.title = "Export Proteins Only";
			this.option = "exportProteinsOnly";
			break;
		case EXPORT_TYPE_PROTEIN_GROUP:
			this.title = "Export Protein Groups";
			this.option = "exportProteinGroups";
			break;
		case EXPORT_TYPE_PSM:
			this.title = "Export PSMs";
			this.option = "exportPSMs";
			break;
			default:
				throw new IllegalArgumentException("Unknown export type " + exportType);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new CsvFileFilter());
		chooser.setMultiSelectionEnabled(false);
		chooser.setDialogTitle(title);
		MzIdentMLToCSV mzidToCsv = new MzIdentMLToCSV();

		int returnVal = chooser.showSaveDialog(proteoIDViewer);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		File selectedFile = chooser.getSelectedFile();

		if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
			selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
		}

		while (selectedFile.exists()) {
			int option = JOptionPane.showConfirmDialog(proteoIDViewer,
					"The  file " + chooser.getSelectedFile().getName()
							+ " already exists. Replace file?",
					"Replace File?", JOptionPane.YES_NO_CANCEL_OPTION);

			if (option == JOptionPane.YES_OPTION)
				break;

			chooser = new JFileChooser();
			chooser.setFileFilter(new CsvFileFilter());
			chooser.setMultiSelectionEnabled(false);
			chooser.setDialogTitle(title);

			returnVal = chooser.showSaveDialog(proteoIDViewer);

			if (returnVal == JFileChooser.CANCEL_OPTION)
				return;

			selectedFile = chooser.getSelectedFile();

			if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
				selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
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

			mzidToCsv.useMzIdentMLToCSV(proteoIDViewer.mzIdentMLUnmarshaller,
					selectedFile.getPath(), option, false);

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