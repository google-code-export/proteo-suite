package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PluginManager {


	/**
	 * This method gets the plugins based on the selected pipeline
	 * 
	 * @param sExperiment
	 *            - Pipeline type
	 * @return Returns an array with the different plugins
	 **/
	public static String[] getPlugins(String sExperiment, JTable jtRawFiles,
			JTable jtIdentFiles, String selectedOutputFormat) {
		final List<List<String>> alPlugins = new ArrayList<List<String>>();
		String[] sPipeline;
		sPipeline = new String[5];

		// ... Read files using XML parser (Creates an Array of ArrayList) ...//
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File("config.xml"));

			NodeList nodeList = document
					.getElementsByTagName("pluginLoadIdentFiles");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginLoadRawFiles");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginQuantitation");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
			nodeList = document.getElementsByTagName("pluginOutput");
			for (int x = 0, size = nodeList.getLength(); x < size; x++) {
				alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
						.getNamedItem("type").getNodeValue(), nodeList.item(x)
						.getAttributes().getNamedItem("id").getNodeValue(),
						nodeList.item(x).getAttributes().getNamedItem("value")
								.getNodeValue(), nodeList.item(x)
								.getAttributes().getNamedItem("desc")
								.getNodeValue()));
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Using the array list we need to find the pipeline and
		// corresponding plugins

		// Find ident file plugin
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(jtIdentFiles.getValueAt(0, 2).toString()
							.toLowerCase())) {
				sPipeline[0] = arrayOfStrings[2].toString();
				break;
			}
		}
		// Find raw file plugin
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(jtRawFiles.getValueAt(0, 2).toString()
							.toLowerCase())) {
				sPipeline[1] = arrayOfStrings[2].toString();
				break;
			}
		}
		// Find quant file plugin
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1].toString().toLowerCase()
					.equals(sExperiment.toLowerCase())) {
				sPipeline[2] = arrayOfStrings[2].toString();
				break;
			}
		}
		// Find output file plugin
		for (int iI = 0; iI < alPlugins.size(); iI++) {
			List<String> sublist = alPlugins.get(iI);
			String[] arrayOfStrings = (String[]) sublist.toArray();
			if (arrayOfStrings[1]
					.toString()
					.toLowerCase()
					.equals(selectedOutputFormat.toLowerCase())) {
				sPipeline[3] = arrayOfStrings[2].toString();
				break;
			}
		}
		return sPipeline;
	}
}
