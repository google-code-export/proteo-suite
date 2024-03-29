package org.proteosuite.quantitation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.validation.Validator;

import org.proteosuite.actions.ProteoSuiteAction;
import static org.proteosuite.gui.ProteoSuite.MZQ_XSD;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.PluginManager;
import org.proteosuite.utils.ProteinInferenceHelper;
import org.proteosuite.utils.SystemUtils;
import org.w3c.dom.DOMException;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.cranfield.xTracker.xTracker;

/**
 *
 * @author SPerkins
 */
public class XTrackerITRAQWrapper {

    private List<RawDataFile> rawData;    
    private static boolean fourPlex = true;
    private static SystemUtils sysUtils = new SystemUtils();
    private String outputPath = null;
    private String defaultProjectName = "DEFAULT_PROJECT";

    public XTrackerITRAQWrapper(List<RawDataFile> rawData) {
        this.rawData = rawData;        
        fourPlex = !AnalyseData.getInstance().getMultiplexing().equals("iTRAQ 8-plex");
    }

    public void compute() {
        final BackgroundTask task = new BackgroundTask(rawData.iterator().next(), "Quantitating iTRAQ Data");
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationProcessing();
                generateFiles();
                new xTracker(outputPath, rawData.get(0).getFile().getParent());                
                return ProteoSuiteActionResult.emptyResult();
            }
        });
        
        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationDone();
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingDone();
                    
                    // For now, skip all the mzq post-processing steps, and jst load the file.
                    //ProteinInferenceHelper.infer(outputPath, fourPlex ? "iTRAQ 4-plex" : "iTRAQ 8-plex", ProteinInferenceHelper.REPORTER_ION_INTENSITY, "sum");
                    
                    AnalyseData
                        .getInstance()
                        .getInspectModel()
                        .addQuantDataFile(
                                new MzQuantMLFile(new File(outputPath)));
                    return ProteoSuiteActionResult.emptyResult();
            }
        });        

        BackgroundTaskManager.getInstance().submit(task);
    }

    /**
     * Generate files (Quant file and xtracker files)
     *
     * @param jtIdentFiles
     * @param jtQuantFiles
     * @param void
     * @return true/false
     */
    private boolean generateFiles() {
        // Check project name
        String sFile = defaultProjectName;
        System.out.println(sysUtils.getTime()
                + " - Generating files for the pipeline ...");

        if (sFile.equals("New") || sFile.equals("")) {
            sFile = "output.mzq";

            defaultProjectName = sFile;
        }

        // Generate mzq file
        System.out.println(sysUtils.getTime() + " - Generating mzq file ...");

        outputPath = QuantUtils.writeMzQuantML("iTRAQ", fourPlex ? "4-plex" : "8-plex", sFile, rawData);

        // Unmarshall mzquantml file        
        Validator validator = XMLparser.getValidator(MZQ_XSD);
        boolean validFlag = XMLparser.validate(validator, rawData.get(0).getFile().getParent().replace("\\", "/") + "/" + defaultProjectName);
        System.out.println(sysUtils.getTime() + " - Validating mzQuantML ...");
        if (!validFlag) {
            System.out.println("Invalid mzQuantML file!");
        } else {
            // Modify the mzQuantML structure according to the experiment            
            writeXTrackerFiles("iTRAQ");
        }

        return true;
    }

    /**
     * Write xTrackerMain based on the technique
     *
     * @param technique The technique used.
     */
    public void writeXTrackerFiles(String technique) {

        // Based on the technique, select the plugins that are available to
        // perform the quantitation
        String[] sPipeline = PluginManager.getPlugins(technique, rawData.get(0).getFormat(), "mzIdentML", "mzQuantML");

        // xTracker consists of 4 main plugins (read more on
        // www.x-tracker.info)
        writeXTrackerIdent(technique, sPipeline[0]);
        writeXTrackerRaw(sPipeline[1]);
        writeXTrackerQuant(sPipeline[2]);
        writeXTrackerOutput(sPipeline[3], defaultProjectName);
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

                // Read files using XPath xml parser
                String searchScore = String.valueOf(ITRAQTechnique.SEARCH_SCORE);                        

                out.write("    <pep_score_threshold>" + searchScore
                        + "</pep_score_threshold>");
                out.newLine();
                out.write("</param> ");
            } else if (plugin.equals("loadMzIdentML")) {
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

        // Read files using XPath xml parser
        String mzRangeMin = "", mzRangeMax = "", integrationMethod = "";
        String minPepRange = "", maxPepRange = "", searchScore = "", enzyme = "";
        List<String> alFastaFiles = new ArrayList<String>();
        List<List<String>> twoDim = new ArrayList<List<String>>();
        mzRangeMin = String.valueOf(ITRAQTechnique.MZ_RANGE_MINUS);
        mzRangeMax = String.valueOf(ITRAQTechnique.MZ_RANGE_PLUS);
        integrationMethod = ITRAQTechnique.INTEGRATION_METHOD;
        for (int loopIterator = 0; loopIterator < 1; loopIterator++) { 
        	// This has been set up to only
            // one iteration as x-Tracker
            // cannot cope with multiple
            // configurations

            RawDataFile dataFile = rawData.get(loopIterator);
            for (Entry<String, String> assay : dataFile.getConditions().entrySet()) {                
                ITRAQReagent reagent = ITRAQReagent.getReagent(fourPlex, assay.getKey());
                String assayName = reagent.getName();
                String mzValue = String.valueOf(reagent.getReporterMz());
                double[] correctionFactorsDoubles = reagent.getCorrectionFactors();
                String[] correctionFactors = new String[correctionFactorsDoubles.length];
                for (int i = 0; i < correctionFactors.length; i++) {
                    correctionFactors[i] = String.valueOf(correctionFactorsDoubles[i]);
                }
                
                twoDim.add(Arrays.asList(assayName, mzValue, correctionFactors[0], correctionFactors[1], correctionFactors[2], correctionFactors[3]));
            }
        }

        // Depending on which plugin was selected we must write the
        // corresponding x-Tracker file
        if (plugin.equals("iTraqQuantitation")) {
            // Write configuration file
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

                for (List<String> twoDimValue : twoDim) {
                    out.write("		<AssayParam>");
                    out.newLine();
                    
                    out.write("			<AssayName>" + twoDimValue.get(0)
                            + "</AssayName>");
                    out.newLine();
                    out.write("			<mzValue>" + twoDimValue.get(1)
                            + "</mzValue>");
                    out.newLine();
                    out.write("			<CorrectionFactors>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"-2\">" + twoDimValue.get(2)
                            + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"-1\">" + twoDimValue.get(3)
                            + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+1\">" + twoDimValue.get(4)
                            + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+2\">" + twoDimValue.get(5)
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

            // Write configuration file
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
                for (String alFastaFile : alFastaFiles) {
                    out.write("        <fastaFile>" + alFastaFile + "</fastaFile>");
                    out.newLine();
                }
                out.write("    </fastaFiles>");
                out.newLine();
                out.write("    <enzyme>" + enzyme + "</enzyme>");
                out.newLine();
                out.write("</emPaiQuantitation>");
                out.newLine();
                out.close();
            } catch (IOException e) {
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
            
            out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
            out.newLine();
            out.write("<    outputFilename>");
            out.write(directory.replace("\\", "/") + "/");
            
            switch (sPlugin) {
                case "outputCSV":
                    out.write(sProjectName.replace(".mzq", ".csv"));
                    break;
                case "outputMZQ":            
                    out.write(sProjectName);
                    break;
            }

            out.write("</outputFilename>");
            out.newLine();
            out.write("</output>");
            out.newLine();
            
            out.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}