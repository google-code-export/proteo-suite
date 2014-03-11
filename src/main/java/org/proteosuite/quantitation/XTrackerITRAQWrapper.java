/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.quantitation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.proteosuite.ProteoSuiteView;
import static org.proteosuite.ProteoSuiteView.MZQ_XSD;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.PluginManager;
import org.proteosuite.utils.SystemUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.cranfield.xTracker.xTracker;

/**
 *
 * @author SPerkins
 */
public class XTrackerITRAQWrapper {
    private List<RawDataFile> rawData;
    private static ExecutorService executor;
    private static SystemUtils sysUtils = new SystemUtils();
    public XTrackerITRAQWrapper(List<RawDataFile> rawData) {
        this.rawData = rawData;
        this.executor = AnalyseData.getInstance().getExecutor();
    }
    
    public void compute() {
        SwingWorker<Void, Void> iTRAQWorker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
                generateFiles("iTRAQ");       
                xTracker xtracker = new xTracker(rawData.get(0).getAbsoluteFileName(), rawData.get(0).getFile().getParent());               
                return null;
            }
        };
        
        executor.submit(iTRAQWorker);       
    }
    
     /**
     * Generate files (Quant file and xtracker files)
     *
     * @param jtIdentFiles
     * @param jtQuantFiles
     * @param void
     * @return true/false
     */
    private boolean generateFiles(String technique) {
        // Check project name
        String sFile = ProteoSuiteView.sProjectName;
        System.out.println(sysUtils.getTime()
                + " - Generating files for the pipeline ...");
        
        if (sFile.equals("New")) {
            sFile = "test.mzq"; 
            
            ProteoSuiteView.sProjectName = sFile;
        }

        // Generate mzq file
        System.out.println(sysUtils.getTime() + " - Generating mzq file ...");
        
        boolean isOK = QuantUtils.writeMzQuantML(technique, sFile, rawData);
        if (!isOK) {
            return false;
        }

        // Unmarshall mzquantml file
        Validator validator = XMLparser.getValidator(MZQ_XSD);
        boolean validFlag = XMLparser.validate(validator, rawData.get(0).getFile().getParent().replace("\\", "/") + "/" + ProteoSuiteView.sProjectName);
        System.out.println(sysUtils.getTime() + " - Validating mzQuantML ...");        
        if (!validFlag) {
            System.out.println("Invalid mzQuantML file!");        
        } else {
            // Modify the mzQuantML structure according to the experiment            
            writeXTrackerFiles(technique);
        }

        return true;
    }
    
    /**
     * Write xTrackerMain based on the technique
     *     
     */
    public void writeXTrackerFiles(String technique) {

		// ... Based on the technique, select the plugins that are available to
        // perform the quantitation ...//
        String[] sPipeline = PluginManager.getPlugins(technique, rawData.get(0).getFormat(), "mzIdentML", "mzQuantML");

		// ... xTracker consists of 4 main plugins (read more on
        // www.x-tracker.info) ...//
        writeXTrackerIdent(technique, sPipeline[0]);
        writeXTrackerRaw(sPipeline[1]);
        writeXTrackerQuant(sPipeline[2]);
        writeXTrackerOutput(sPipeline[3], ProteoSuiteView.sProjectName);
    }
    
    /**
     * Write the xTracker identification plugin
     *
     * @param sExperiment - Type of experiment (e.g. emPAI, etc.)
     * @param sPlugin - Plugin (e.g. loadRawMzML111, etc.)
     * @return void
	 *
     */
    private void writeXTrackerIdent(String technique, String plugin) {
        String sFileName = rawData.get(0).getFile().getParent() + "\\xTracker_" + plugin + ".xtp";
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
            if (plugin.equals("loadMascotIdent")) {
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
                for (RawDataFile dataFile : rawData) {
                    IdentDataFile identFile = dataFile.getIdentificationDataFile();
                    if (identFile != null) {
                        out.write("        <datafile identification_file=\""
                            + identFile.getAbsoluteFileName() + "\">" + dataFile.getAbsoluteFileName()
                            + "</datafile>");
                        out.newLine();
                    }
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

                    if (technique.equals("iTRAQ")) {
                        expr = xpath
                                .compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
                    } else if (technique.equals("emPAI")) {
                        expr = xpath
                                .compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/SearchScore");
                    }
                    
                    NodeList nodes = (NodeList) expr.evaluate(doc,
                            XPathConstants.NODESET);
                    for (int iI = 0; iI < nodes.getLength(); iI++) {
                        SearchScore = nodes.item(iI).getTextContent();
                    }
                    
                } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
                    e.printStackTrace();
                }
                
                out.write("    <pep_score_threshold>" + SearchScore
                        + "</pep_score_threshold>");
                out.newLine();
                out.write("</param> ");
            }
            
            if (plugin.equals("loadMzIdentML")) {
                out.write("<!-- ");
                out.newLine();
                out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<SpectralIdentificationList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/loadMzIdentML.xsd\">");
                out.newLine();
                for (RawDataFile dataFile : rawData) {
                    IdentDataFile identFile = dataFile.getIdentificationDataFile();
                    if (identFile != null) {
                        out.write("    <SpectralIdentificationPair spectra=\""
                            + dataFile.getAbsoluteFileName() + "\" identification=\""
                            + identFile.getAbsoluteFileName() + "\" />");
                    out.newLine();
                    }                    
                }               
                
                out.write("</SpectralIdentificationList>");
                out.newLine();
            }
            
            out.close();
        } catch (IOException | DOMException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Write the xTracker load raw data plugin
     *
     * @param sPlugin - Plugin (e.g. loadMzIdentML, etc.)
     * @return void
	 *
     */
    private void writeXTrackerRaw(String sPlugin) {
        String sFileName = rawData.get(0).getFile().getParent() + "\\xTracker_" + sPlugin + ".xtp";
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
            for (RawDataFile dataFile : rawData) {
                out.write("    <datafile>"
                        + dataFile.getAbsoluteFileName()
                        + "</datafile>");
                out.newLine();
            }           
            
            out.write("</param>");
            out.newLine();
            out.close();
        } catch (IOException io) {
            System.err.println("Error: " + io.getMessage());
        }
    }

    /**
     * Write the xTracker quantitation plugin
     *
     * @param sPlugin - Plugin (e.g. iTraqQuantitation, etc.)
     * @return void
	 *
     */
    private void writeXTrackerQuant(String plugin) {
        System.out.println("Quant=" + plugin);
        String sFileName = rawData.get(0).getFile().getParent() + "\\xTracker_" + plugin + ".xtp";

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
                                + rawData.get(0).getFileName());//jtRawFiles.getValueAt(iJ, 0) + "']");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                // ... Assay Parameters (Labels) ...//
                expr = xpath
                        .compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
                                + rawData.get(0).getFileName()
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
        if (plugin.equals("SILAC")) {

        } else if (plugin.equals("iTraqQuantitation")) {
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
            } catch (IOException io) {
                System.err.println("Error: " + io.getMessage());
            }
        } else if (plugin.startsWith("emPAI")) {

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
     *
     * @param sPlugin - Plugin type (e.g. outputMZQ)
     * @return void
	 *
     */
    private void writeXTrackerOutput(String sPlugin, String sProjectName) {
        String directory = rawData.get(0).getFile().getParent();
        String sFileName = directory.replace("\\", "/") + "/xTracker_"
                + sPlugin + ".xtp";        
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
                        + directory.replace("\\", "/") + "/"
                        + sProjectName.replace(".mzq", ".csv")
                        + "</outputFilename>");
                out.newLine();
                out.write("</output>");
                out.newLine();                
            }
            if (sPlugin.equals("outputMZQ")) {
                out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
                out.newLine();
                String sTemp = sProjectName.replace(".mzq", "");
                out.write("    <outputFilename>"
                        + directory.replace("\\", "/") + "/" + sTemp
                        + ".mzq</outputFilename>");
                out.newLine();
                out.write("</output>");
                out.newLine();                
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }       
    }
}
