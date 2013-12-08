package org.proteosuite.fileformat2;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileFormatMascotImport implements Runnable {
	private JTable jtIdentFiles;
	private File inputFile;

	public FileFormatMascotImport(File inputFile, JTable jtIdentFiles) {
		this.inputFile = inputFile;
		this.jtIdentFiles = jtIdentFiles;
	}

	@Override
	public void run() {
		// Fill JTable
		final DefaultTableModel model = (DefaultTableModel) jtIdentFiles
				.getModel();

		model.insertRow(
				model.getRowCount(),
				new String[] {
						inputFile.getName(),
						inputFile.getPath().toString()
								.replace("\\", "/"), "mascot_xml",
						"N/A", "" });

	}

}
