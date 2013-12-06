package org.proteosuite.fileformat2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.swing.table.DefaultTableModel;

import org.proteosuite.gui.TabbedLog;
import org.proteosuite.utils.Unmarshaller;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class FileFormatMzMLImport {
	private final TabbedLog jtpLog;
	private final File inputFile;
	private final List<MzMLUnmarshaller> aMzMLUnmarshaller;
	private final DefaultTableModel model;

	public FileFormatMzMLImport(final TabbedLog jtpLog, final File inputFile,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller, DefaultTableModel model) {
		this.jtpLog = jtpLog;
		this.inputFile = inputFile;
		this.aMzMLUnmarshaller = aMzMLUnmarshaller;
		this.model = model;
	}

	public void run() {
		File xmlFile = new File(inputFile.getPath());

		// Uncompress mzML.gz files
		if (xmlFile.getName().toLowerCase().indexOf(".mzml.gz") > 0) {
			try {
				jtpLog.appendLog("Uncompressing " + xmlFile.getName());

				GZIPInputStream gin = new GZIPInputStream(new FileInputStream(
						xmlFile));
				File outFile = new File(xmlFile.getParent(), xmlFile.getName()
						.replaceAll("\\.gz$", ""));
				FileOutputStream fos = new FileOutputStream(outFile);
				
				byte[] buf = new byte[100000];
				int len;
				while ((len = gin.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
				gin.close();
				fos.close();
				xmlFile = outFile;
				jtpLog.appendLog("Uncompressing ends");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		// Unmarshall data using jzmzML API
		jtpLog.appendLog("Unmarshalling " + xmlFile.getName() + " starts");

		aMzMLUnmarshaller.add(Unmarshaller.unmarshalMzMLFile(model, xmlFile, ""));
		jtpLog.appendLog("Unmarshalling ends");
	}
}
