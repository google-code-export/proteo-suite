package org.proteosuite.utils;

import java.io.File;

import javax.swing.table.DefaultTableModel;

import org.proteosuite.gui.ProteoSuite;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

public class Unmarshaller {
	private static final SystemUtils SYS_UTILS = new SystemUtils();

	/**
	 * Unmarshall mzML file
	 * 
	 * @param model
	 *            - table model
	 * @param xmlFile
	 *            - XML file to unmarshall
	 * @param sGroup
	 *            - Raw file group
	 * 
	 * @return void
	 */
	public static MzMLUnmarshaller unmarshalMzMLFile(DefaultTableModel model, File xmlFile,
			String sGroup) {
		// Unmarshall mzML file and add on the aMzMLUnmarshaller list
		MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);

		// split name and file extension
		int mid = xmlFile.getName().lastIndexOf(".");
		String ext = "";
		ext = xmlFile.getName().substring(mid + 1, xmlFile.getName().length());
		
		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().toString().replace("\\", "/"), ext,
				unmarshaller.getMzMLVersion() });
		
		System.out.println(SYS_UTILS.getTime() + " - " + xmlFile.getName()
				+ " was unmarshalled successfully!");
		
		return unmarshaller;
	}

	/**
	 * Unmarshall mzIdentML files
	 * 
	 * @param model
	 *            - Table model
	 * @param iIndex
	 *            - Index to the aMzIDUnmarshaller arraylist
	 * @param xmlFile
	 *            - File to unmarshall
	 * @param sGroup
	 *            - Raw data group
	 * @return 
	 * @return void
	 **/
	public static MzIdentMLUnmarshaller unmarshalMzIDFile(DefaultTableModel model, File xmlFile,
			String sGroup) {
		// For each mzid file we must specify the corresponding mzML file
		MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(xmlFile);
		
		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().replace("\\", "/"), "mzid",
				unmarshaller.getMzIdentMLVersion().toString(), sGroup });
		
		System.out.println(SYS_UTILS.getTime() + " - " + xmlFile.getName()
				+ " was unmarshalled successfully!");
		

		
		return unmarshaller;
	}

	/**
	 * Unmarshall mzQML files
	 * 
	 * @param model
	 *            - Table model
	 * @param xmlFile
	 *            - File to unmarshall
	 * @return boolean - Flag
	 **/
	public static MzQuantMLUnmarshaller unmarshalMzQMLFile(DefaultTableModel model,
			File xmlFile)
			throws Exception {
		// Unmarshall mzquantml file
		MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(xmlFile);

		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().replace("\\", "/"), "mzq",
				ProteoSuite.MZQUANT_VERSION});
		
		System.out.println(SYS_UTILS.getTime() + " - (Unmarshalling) "
				+ xmlFile.getName() + " was unmarshalled successfully!");
		
		return unmarshaller;
	}
}
