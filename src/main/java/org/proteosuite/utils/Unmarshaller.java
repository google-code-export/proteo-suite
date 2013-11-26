package org.proteosuite.utils;

import java.io.File;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.proteosuite.ProteoSuiteView;

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
	public static void unmarshalMzMLFile(DefaultTableModel model, File xmlFile,
			String sGroup, List<MzMLUnmarshaller> aMzMLUnmarshaller) {
		// Unmarshall mzML file and add on the aMzMLUnmarshaller list
		MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
		aMzMLUnmarshaller.add(unmarshaller);

		// split name and file extension
		int mid = xmlFile.getName().lastIndexOf(".");
		String ext = "";
		ext = xmlFile.getName().substring(mid + 1, xmlFile.getName().length());
		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().toString().replace("\\", "/"), ext,
				unmarshaller.getMzMLVersion() });
		System.out.println(SYS_UTILS.getTime() + " - " + xmlFile.getName()
				+ " was unmarshalled successfully!");
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
	 * @return void
	 **/
	public static void unmarshalMzIDFile(DefaultTableModel model, File xmlFile,
			String sGroup, List<MzIdentMLUnmarshaller> aMzIDUnmarshaller) {
		// For each mzid file we must specify the corresponding mzML file
		MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(xmlFile);
		aMzIDUnmarshaller.add(unmarshaller);
		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().replace("\\", "/"), "mzid",
				unmarshaller.getMzIdentMLVersion().toString(), sGroup });
		System.out.println(SYS_UTILS.getTime() + " - " + xmlFile.getName()
				+ " was unmarshalled successfully!");
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
	public static boolean unmarshalMzQMLFile(DefaultTableModel model,
			File xmlFile, List<MzQuantMLUnmarshaller> aMzQUnmarshaller)
			throws Exception {
		// Unmarshall mzquantml file
		MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(xmlFile);
		aMzQUnmarshaller.add(unmarshaller);

		model.insertRow(model.getRowCount(), new String[] { xmlFile.getName(),
				xmlFile.getPath().replace("\\", "/"), "mzq",
				ProteoSuiteView.MZQUANT_VERSION });
		System.out.println(SYS_UTILS.getTime() + " - (Unmarshalling) "
				+ xmlFile.getName() + " was unmarshalled successfully!");
		return true;
	}
}
