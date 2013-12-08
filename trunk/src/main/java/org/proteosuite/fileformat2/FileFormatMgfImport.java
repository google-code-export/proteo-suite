package org.proteosuite.fileformat2;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileFormatMgfImport implements Runnable {

	private JTable jtRawFiles;
	private File inputFile;

	public FileFormatMgfImport(File inputFile, JTable jtRawFiles) {
		this.inputFile = inputFile;
		this.jtRawFiles = jtRawFiles;
	}

	@Override
	public void run() {
		// Fill JTable
		final DefaultTableModel model = (DefaultTableModel) jtRawFiles
				.getModel();

		model.insertRow(
				model.getRowCount(),
				new String[] {
						inputFile.getName(),
						inputFile.getPath().toString()
								.replace("\\", "/"), "MGF", "N/A" });
	}

}
