package org.proteosuite.fileformat2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.swing.table.DefaultTableModel;


import org.proteosuite.utils.Unmarshaller;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class FileFormatMzIdImport implements Runnable {
	private File inputFile;	
	private DefaultTableModel model;
	private List<MzIdentMLUnmarshaller> aMzIDUnmarshaller;

	public FileFormatMzIdImport(File inputFile,
			List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			DefaultTableModel model) {

		this.inputFile = inputFile;
		
		this.model = model;
		this.aMzIDUnmarshaller = aMzIDUnmarshaller;
	}

	public void run() {
		File xmlFile = new File(inputFile.getPath());

		// Uncompress .gz files
		if (xmlFile.getName().toLowerCase().indexOf(".mzid.gz") > 0) {
			try {
				

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
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		// Unmarshall data using jmzIdentML API
		

		aMzIDUnmarshaller.add(Unmarshaller.unmarshalMzIDFile(model, xmlFile, ""));
		
	}
}