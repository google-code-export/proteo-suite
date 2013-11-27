package org.proteosuite.fileformat;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.utils.ExcelExporter;

/**
 * 
 * @author Andrew collins
 */
public class FileFormatExcel {
	private void throwError(JTable jTable) {
		if (jTable.getRowCount() <= 0) {
			JOptionPane.showMessageDialog(null, "No data to export", "Error",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	private File ShowFileChooser() {
		JFileChooser chooser = new JFileChooser(
				ProteoSuiteView.sPreviousLocation);
		chooser.setDialogTitle("Please type the file name");

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Excel Files (*.xls)", "xls");
		chooser.addChoosableFileFilter(filter);

		if (ProteoSuiteView.sPreviousLocation == null
				|| ProteoSuiteView.sPreviousLocation.contains(""))
			chooser.setCurrentDirectory(new File(ProteoSuiteView.sWorkspace));
		else
			chooser.setCurrentDirectory(new File(
					ProteoSuiteView.sPreviousLocation));

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile();
		return null;
	}

	private String getFileLocation(File file) {
		int mid = 0;
		mid = file.getName().lastIndexOf(".xls");
		String fileLocation = "";
		if (mid <= 0) {
			fileLocation = file.getPath() + ".xls";
		} else {
			fileLocation = file.getPath();
		}
		fileLocation.replace("\\", "/");

		return fileLocation;
	}

	private boolean isWriteSafe(String fileLocation) {
		// Check if file exists
		boolean exists = new File(fileLocation).exists();
		boolean write = true;
		if (exists) {
			int n = JOptionPane
					.showConfirmDialog(
							null,
							"The file already exists in null directory, do you want to overwrite it?",
							"Information", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.NO_OPTION) {
				write = false;
			}
		}

		return write;
	}

	private void doWrite(JTable jTable, String fileLocation) {
		ExcelExporter exp = new ExcelExporter();
		exp.fillData(jTable, new File(fileLocation), true, 0);
		JOptionPane.showMessageDialog(null, "Data saved at " + fileLocation,
				"Information", JOptionPane.INFORMATION_MESSAGE);

	}

	public void export(final JTable jTable) {
		throwError(jTable);

		try {
			File file = ShowFileChooser();

			if (file == null)
				return;

			String fileLocation = getFileLocation(file);
			boolean write = isWriteSafe(fileLocation);

			if (write)
				doWrite(jTable, fileLocation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
