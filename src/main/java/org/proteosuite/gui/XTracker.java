package org.proteosuite.gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.WorkSpace;
import org.proteosuite.utils.PluginManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Andrew Collins
 */
public class XTracker {
	private static final WorkSpace WORKSPACE = WorkSpace.getInstance();
	/**
	 * Write xTrackerMain based on the technique
	 * 
	 * @param sExperiment
	 *            - Pipeline type
	 * @param jtIdentFiles
	 * @param jtQuantFiles
	 * @return void
	 **/
	public void writeXTrackerFiles(String sExperiment, JTable jtRawFiles,
			JComboBox<String> jcbOutputFormat, JTable jtIdentFiles,
			JTable jtQuantFiles) {
		
		// ... Based on the technique, select the plugins that are available to
		// perform the quantitation ...//
		String[] sPipeline = PluginManager.getPlugins(sExperiment, jtRawFiles, jtIdentFiles, jcbOutputFormat.getSelectedItem().toString());

		// ... xTracker consists of 4 main plugins (read more on
		// www.x-tracker.info) ...//
		writeXTrackerIdent(sExperiment, sPipeline[0], WORKSPACE.getWorkSpace(), jtIdentFiles, jtRawFiles);
		writeXTrackerRaw(sPipeline[1], jtRawFiles, WORKSPACE.getWorkSpace());
		writeXTrackerQuant(sPipeline[2], jtRawFiles, WORKSPACE.getWorkSpace());
		writeXTrackerOutput(sPipeline[3], jtQuantFiles, WORKSPACE.getWorkSpace(), ProteoSuiteView.sProjectName);
	}
	
	/**
	 * Write the xTracker identification plugin
	 * @param sExperiment - Type of experiment (e.g. emPAI, etc.)
	 * @param sPlugin - Plugin (e.g. loadRawMzML111, etc.)
	 * @return void
	 **/
	private void writeXTrackerIdent(String sExperiment, String sPlugin, String sWorkspace, JTable jtIdentFiles, JTable jtRawFiles) {
		String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
		try {
			FileWriter fstream = new FileWriter(sFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.newLine();
			out.write("<!-- ");
			out.newLine();
			out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");
			out.newLine();
			out.write("-->");
			out.newLine();
			if (sPlugin.equals("loadMascotIdent")) {
				out.write("<!-- ");
				out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
				out.newLine();
				out.write("    Also, modifications and mass shifts are specified here in case mascot does not report fixed modification mass shifts.");
				out.newLine();
				out.write("    Finally, the minimum threshold use for the search engine is specified in the pop_score_threshold tag. ");
				out.newLine();
				out.write("-->");
				out.newLine();
				out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadMascotIdent.xsd\">");
				out.newLine();
				out.write("    <inputFiles>");
				out.newLine();
				for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
					String sRawFile = "";
					for (int iJ = 0; iJ < jtRawFiles.getRowCount(); iJ++) {
						if (jtRawFiles
								.getValueAt(iJ, 0)
								.toString()
								.equals(jtIdentFiles.getValueAt(iI, 4)
										.toString())) {
							sRawFile = jtRawFiles.getValueAt(iJ, 1).toString();
							break;
						}
					}
					out.write("        <datafile identification_file=\""
							+ jtIdentFiles.getValueAt(iI, 1) + "\">" + sRawFile
							+ "</datafile>");
					out.newLine();
				}
				out.write("    </inputFiles>");
				out.newLine();
				out.write("<!-- Modifications (including amino-acid affected) and mass shifts considered in the search are reported below.");
				out.newLine();
				out.write("    NOTE THAT MONOISOTOPIC MASSES ARE USED IN THIS FILE!!!");
				out.newLine();
				out.write("-->");
				out.newLine();
				out.write("    <modificationData>");
				out.newLine();
				out.write("        <modification delta=\"57.021469\">Carbamidomethyl (C)</modification>");
				out.newLine();
				out.write("        <modification delta=\"144.1020633\">iTRAQ4plex (K)</modification>");
				out.newLine();
				out.write("        <modification delta=\"144.102063\">iTRAQ4plex (N-term)</modification>");
				out.newLine();
				out.write("    </modificationData>");
				out.newLine();

				// ... Read files using XPath xml parser ...//
				String SearchScore = "";
				try {
					DocumentBuilderFactory domFactory = DocumentBuilderFactory
							.newInstance();
					domFactory.setNamespaceAware(true);
					DocumentBuilder builder = domFactory.newDocumentBuilder();
					Document doc = builder.parse("configQuant.xml");
					XPath xpath = XPathFactory.newInstance().newXPath();
					XPathExpression expr = null;

					if (sExperiment.equals("iTRAQ")) {
						expr = xpath
								.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
					} else if (sExperiment.equals("emPAI")) {
						expr = xpath
								.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/SearchScore");
					}
					NodeList nodes = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					for (int iI = 0; iI < nodes.getLength(); iI++) {
						SearchScore = nodes.item(iI).getTextContent();
					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				out.write("    <pep_score_threshold>" + SearchScore
						+ "</pep_score_threshold>");
				out.newLine();
				out.write("</param> ");
			}
			if (sPlugin.equals("loadMzIdentML")) {
				out.write("<!-- ");
				out.newLine();
				out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
				out.newLine();
				out.write("-->");
				out.newLine();
				out.write("<SpectralIdentificationList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/loadMzIdentML.xsd\">");
				out.newLine();
				for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
					String sRawFile = "";
					for (int iJ = 0; iJ < jtRawFiles.getRowCount(); iJ++) {
						if (jtRawFiles
								.getValueAt(iJ, 0)
								.toString()
								.equals(jtIdentFiles.getValueAt(iI, 4)
										.toString())) {
							sRawFile = jtRawFiles.getValueAt(iJ, 1).toString();
							break;
						}
					}
					out.write("    <SpectralIdentificationPair spectra=\""
							+ sRawFile + "\" identification=\""
							+ jtIdentFiles.getValueAt(iI, 1) + "\" />");
					out.newLine();
				}
				out.write("</SpectralIdentificationList>");
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Write the xTracker load raw data plugin
	 * @param sPlugin - Plugin (e.g. loadMzIdentML, etc.)
	 * @return void
	 **/
	private void writeXTrackerRaw(String sPlugin, JTable jtRawFiles, String sWorkspace) {
		String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
		try {
			FileWriter fstream = new FileWriter(sFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.newLine();
			out.write("<!--");
			out.newLine();
			out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");
			out.newLine();
			out.write("-->");
			out.newLine();
			out.write("<!-- ");
			out.write("    This XML file specifies a list of raw data files which will be used in the analysis.");
			out.newLine();
			out.write("-->");
			out.newLine();
			out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadRawMGF.xsd\">");
			out.newLine();
			for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
				out.write("    <datafile>"
						+ jtRawFiles.getValueAt(iI, 1).toString()
						+ "</datafile>");
				out.newLine();
			}
			out.write("</param>");
			out.newLine();
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Write the xTracker quantitation plugin
	 * @param sPlugin - Plugin (e.g. iTraqQuantitation, etc.)
	 * @return void
	 **/
	private void writeXTrackerQuant(String sPlugin, JTable jtRawFiles, String sWorkspace) {
		System.out.println("Quant=" + sPlugin);
		String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";

		// ... Read files using XPath xml parser ...//
		String mzRangeMin = "", mzRangeMax = "", integrationMethod = "";
		String minPepRange = "", maxPepRange = "", searchScore = "", enzyme = "";
		List<String> alFastaFiles = new ArrayList<String>();
		List<List<String>> twoDim = new ArrayList<List<String>>();
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse("configQuant.xml");
			XPath xpath = XPathFactory.newInstance().newXPath();

			// ------------------//
			// ... iTRAQ data ...//
			// ------------------//

			// ... Reading mzRanges (min and max) ...//
			XPathExpression expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/minus");
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				mzRangeMin = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/plus");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				mzRangeMax = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/IntegrationMethod");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				integrationMethod = nodes.item(iI).getTextContent();
			}

			for (int iJ = 0; iJ < 1; iJ++) { // ... This has been set up to only
												// one iteration as x-Tracker
												// cannot cope with multiple
												// configurations ...//
				expr = xpath
						.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
								+ jtRawFiles.getValueAt(iJ, 0) + "']");
				nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

				// ... Assay Parameters (Labels) ...//
				expr = xpath
						.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
								+ jtRawFiles.getValueAt(iJ, 0)
								+ "']/AssayParam");
				nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				for (int iI = 0; iI < nodes.getLength(); iI++) {
					Node node = nodes.item(iI);
					if (node.getNodeType() == Node.ELEMENT_NODE) {

						String sAssayName = "", sMzValue = "";
						String[] sCorrFactors = new String[4];
						Element element = (Element) node;
						NodeList nodelist = element
								.getElementsByTagName("AssayName");
						Element element1 = (Element) nodelist.item(0);
						NodeList fstNm = element1.getChildNodes();
						sAssayName = fstNm.item(0).getNodeValue();

						Element element2 = (Element) node;
						NodeList nodelist1 = element2
								.getElementsByTagName("mzValue");
						Element element3 = (Element) nodelist1.item(0);
						NodeList fstNm1 = element3.getChildNodes();
						sMzValue = fstNm1.item(0).getNodeValue();

						Element element4 = (Element) node;
						NodeList nodelist2 = element2
								.getElementsByTagName("factor");
						for (int iK = 0; iK < nodelist2.getLength(); iK++) {
							Element element5 = (Element) nodelist2.item(iK);
							NodeList fstNm2 = element5.getChildNodes();
							sCorrFactors[iK] = fstNm2.item(0).getNodeValue();
						}
						twoDim.add(Arrays.asList(sAssayName, sMzValue,
								sCorrFactors[0], sCorrFactors[1],
								sCorrFactors[2], sCorrFactors[3]));
					}
				}
			}
			// ------------------//
			// ... emPAI data ...//
			// ------------------//
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/minRange");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				minPepRange = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/maxRange");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				maxPepRange = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/SearchScore");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				searchScore = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/Enzyme");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				enzyme = nodes.item(iI).getTextContent();
			}
			expr = xpath
					.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/FastaFiles/File");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				alFastaFiles.add(nodes.item(iI).getTextContent());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		// ... Depending on which plugin was selected we must write the
		// corresponding x-Tracker file ...//
		if (sPlugin.equals("SILAC")) {

		} else if (sPlugin.equals("iTraqQuantitation")) {
			// ... Write configuration file ...//
			try {
				FileWriter fstream = new FileWriter(sFileName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.newLine();
				out.write("<!-- ");
				out.newLine();
				out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");
				out.newLine();
				out.write("    This plugin allows the specification of the parameters used for the quantitation process in iTRAQ. Here, we");
				out.newLine();
				out.write("    specify the quantitation method and the correction factors used for the reporter ions.");
				out.newLine();
				out.write("-->");
				out.newLine();
				out.write("<iTraqQuantitation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/iTraqQuantitation.xsd\">");
				out.newLine();
				out.write("	<AssayParamList>");
				out.newLine();

				for (int iI = 0; iI < twoDim.size(); iI++) {
					out.write("		<AssayParam>");
					out.newLine();
					List<String> lList = twoDim.get(iI);
					out.write("			<AssayName>" + lList.get(0).toString()
							+ "</AssayName>");
					out.newLine();
					out.write("			<mzValue>" + lList.get(1).toString()
							+ "</mzValue>");
					out.newLine();
					out.write("			<CorrectionFactors>");
					out.newLine();
					out.write("				<factor deltaMass=\"-2\">" + lList.get(2)
							+ "</factor>");
					out.newLine();
					out.write("				<factor deltaMass=\"-1\">" + lList.get(3)
							+ "</factor>");
					out.newLine();
					out.write("				<factor deltaMass=\"+1\">" + lList.get(4)
							+ "</factor>");
					out.newLine();
					out.write("				<factor deltaMass=\"+2\">" + lList.get(5)
							+ "</factor>");
					out.newLine();
					out.write("			</CorrectionFactors>");
					out.newLine();
					out.write("		</AssayParam>");
					out.newLine();
				}
				out.write("	</AssayParamList>");
				out.newLine();
				out.write("	<Setting>");
				out.newLine();
				out.write("		<mzRange>");
				out.newLine();
				out.write("    			<minus>" + mzRangeMin + "</minus>");
				out.newLine();
				out.write("    			<plus>" + mzRangeMax + "</plus>");
				out.newLine();
				out.write("		</mzRange>");
				out.newLine();
				out.write("		<IntegrationMethod>"
						+ integrationMethod
						+ "</IntegrationMethod><!--the method used to calculate the area of the peak-->");
				out.newLine();
				out.write("	</Setting>");
				out.newLine();
				out.write("</iTraqQuantitation>");
				out.newLine();
				out.close();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		} else if (sPlugin.startsWith("emPAI")) {

			// ... Write configuration file ...//
			try {
				FileWriter fstream = new FileWriter(sFileName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.newLine();
				out.write("<!-- ");
				out.newLine();
				out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");
				out.newLine();
				out.write("    This plugin allows the specification of the parameters used for the quantitation process in iTRAQ. Here, we");
				out.newLine();
				out.write("    specify the quantitation method and the correction factors used for the reporter ions.");
				out.newLine();
				out.write("-->");
				out.newLine();
				out.write("<emPaiQuantitation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/emPaiQuantitation.xsd\">");
				out.newLine();
				out.write("    <!--the molecular weight of observable peptide-->");
				out.newLine();
				out.write("    <peptideMwRange>");
				out.newLine();
				out.write("        <minimum>" + minPepRange + "</minimum>");
				out.newLine();
				out.write("        <maximum>" + maxPepRange + "</maximum>");
				out.newLine();
				out.write("    </peptideMwRange>");
				out.newLine();
				out.write("    <fastaFiles>");
				out.newLine();
				for (int iI = 0; iI < alFastaFiles.size(); iI++) {
					out.write("        <fastaFile>"
							+ alFastaFiles.get(iI).toString() + "</fastaFile>");
					out.newLine();
				}
				out.write("    </fastaFiles>");
				out.newLine();
				out.write("    <enzyme>" + enzyme + "</enzyme>");
				out.newLine();
				out.write("</emPaiQuantitation>");
				out.newLine();
				out.close();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Generate XTracker output plugin 
	 * @param sPlugin - Plugin type (e.g. outputMZQ)
	 * @return void
	 **/
	private void writeXTrackerOutput(String sPlugin, JTable jtQuantFiles, String sWorkspace, String sProjectName) {
		String sFileName = sWorkspace.replace("\\", "/") + "/xTracker_"
				+ sPlugin + ".xtp";
		String sType = "", sVersion = "";
		try {
			FileWriter fstream = new FileWriter(sFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.newLine();
			out.write("<!--");
			out.newLine();
			out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");
			out.newLine();
			out.write("    This plugin allows the specification of the output format. ");
			out.newLine();
			out.write("-->");
			out.newLine();
			if (sPlugin.equals("outputCSV")) {
				out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
				out.newLine();
				out.write("    <outputFilename>"
						+ sWorkspace.replace("\\", "/") + "/"
						+ sProjectName.replace(".mzq", ".csv")
						+ "</outputFilename>");
				out.newLine();
				out.write("</output>");
				out.newLine();
				sType = "CSV";
				sVersion = "N/A";
			}
			if (sPlugin.equals("outputMZQ")) {
				out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
				out.newLine();
				String sTemp = sProjectName.replace(".mzq", "");
				out.write("    <outputFilename>"
						+ sWorkspace.replace("\\", "/") + "/" + sTemp
						+ ".mzq</outputFilename>");
				out.newLine();
				out.write("</output>");
				out.newLine();
				sType = "MZQ";
				sVersion = ProteoSuiteView.MZQUANT_VERSION;
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		// ... Add file on the Quantitation Files tabsheet ...//
		final DefaultTableModel model = new DefaultTableModel();
		jtQuantFiles.setModel(model);
		model.addColumn("Name");
		model.addColumn("Path");
		model.addColumn("Type");
		model.addColumn("Version");
		if (sType.equals("MZQ")) {
			model.insertRow(
					model.getRowCount(),
					new String[] {
							sProjectName.replace(".mzq", "") + ".mzq",
							sWorkspace.replace("\\", "/") + "/"
									+ sProjectName.replace(".mzq", "") + ".mzq",
							sType.toLowerCase(), sVersion });
		}
		if (sType.equals("CSV")) {
			model.insertRow(
					model.getRowCount(),
					new String[] {
							sProjectName.replace(".mzq", ".csv"),
							sWorkspace.replace("\\", "/") + "/"
									+ sProjectName.replace(".mzq", ".csv"),
							sType.toLowerCase(), sVersion });
		}
	}
}
