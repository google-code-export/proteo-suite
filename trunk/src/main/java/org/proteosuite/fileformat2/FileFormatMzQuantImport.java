package org.proteosuite.fileformat2;

import java.io.File;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.utils.Unmarshaller;

import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

public class FileFormatMzQuantImport implements Runnable {

	private JTable jtQuantFiles;
	private File inputFile;
	private List<MzQuantMLUnmarshaller> aMzQUnmarshaller;

	public FileFormatMzQuantImport(File inputFile, JTable jtQuantFiles,
			List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {
		this.inputFile = inputFile;
		this.jtQuantFiles = jtQuantFiles;
		this.aMzQUnmarshaller = aMzQUnmarshaller;
	}

	@Override
	public void run() {
		// Fill JTable
		final DefaultTableModel model = (DefaultTableModel) jtQuantFiles
				.getModel();

		// Reading selected files
		boolean isOK = true;
		// Validate file extension (mixed files)
		if (inputFile.getName().toLowerCase().indexOf(".mzq") > 0) {
			File xmlFile = new File(inputFile.getPath());

			// Unmarshall data using jmzIdentML API
			//jtpLog.appendLog("Unmarshalling " + xmlFile.getName()
			//		+ " starts");

			try {
				isOK = true;
				aMzQUnmarshaller.add(Unmarshaller.unmarshalMzQMLFile(
						model, xmlFile));
				// Invalid mzq file
				if (!isOK)
					return;

				//jtpLog.appendLog("Unmarshalling ends");
			} catch (Exception e) {
				//jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid");
				//jtpLog.appendLog(e.getMessage());
				e.printStackTrace();
				isOK = false;
			}
		}

	}

}
