/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.quantitation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.proteosuite.ProteoSuiteView;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.PluginManager;
import uk.ac.liv.jmzqml.model.mzqml.AbstractParam;
import uk.ac.liv.jmzqml.model.mzqml.AnalysisSummary;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.Cv;
import uk.ac.liv.jmzqml.model.mzqml.CvList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFiles;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.Label;
import uk.ac.liv.jmzqml.model.mzqml.ModParam;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLMarshaller;

/**
 *
 * @author SPerkins
 */
public class QuantUtils {

    private QuantUtils() {
    }

    public static String writeMzQuantML(String technique, String quantFileName,
            List<RawDataFile> rawData) {

        String directory = rawData.get(0).getFile().getParent().replace("\\", "/");        

        // ... Create object ...//
        MzQuantML qml = new MzQuantML();

        // ... Set version ...//
        String Version = "1.0.0";
        qml.setVersion(Version);
        Calendar rightNow = Calendar.getInstance();
        qml.setCreationDate(rightNow);
        qml.setId(quantFileName);

        // ----------------------//
        // ... CREATE CV LIST ...//
        // ----------------------//
        CvList cvs = new CvList();
        List<Cv> cvList = cvs.getCv();
        Cv cvPSI_MS = new Cv();
        cvPSI_MS.setId("PSI-MS");
        cvPSI_MS.setUri("http://psidev.cvs.sourceforge.net/viewvc/*checkout*/psidev/psi/psi-ms/mzML/controlledVocabulary/psi-ms.obo");
        cvPSI_MS.setFullName("Proteomics Standards Initiative Mass Spectrometry Vocabulary");
        cvPSI_MS.setVersion(ProteoSuiteView.PSI_MS_VERSION);

        Cv cvUO = new Cv();
        cvUO.setId("UO");
        cvUO.setUri("http://obo.cvs.sourceforge.net/*checkout*/obo/obo/ontology/phenotype/unit.obo");
        cvUO.setFullName("Unit Ontology");

        Cv cvPSI_MOD = new Cv();
        cvPSI_MOD.setId("PSI-MOD");
        cvPSI_MOD
                .setUri("http://psidev.cvs.sourceforge.net/psidev/psi/mod/data/PSI-MOD.obo");
        cvPSI_MOD
                .setFullName("Proteomics Standards Initiative Protein Modifications");
        cvPSI_MOD.setVersion(ProteoSuiteView.PSI_MOD_VERSION);

        Cv cvUNI_MOD = new Cv();
        cvUNI_MOD.setId("UNIMOD");
        cvUNI_MOD.setUri("http://www.unimod.org/obo/unimod.obo");
        cvUNI_MOD.setFullName("UNIMOD");

        cvList.add(cvPSI_MS);
        cvList.add(cvUO);
        cvList.add(cvPSI_MOD);
        cvList.add(cvUNI_MOD);
        qml.setCvList(cvs);

        // -------------------------------//
        // ... CREATE ANALYSIS SUMMARY ...//
        // -------------------------------//
        if (technique.contains("iTRAQ")) {

            AnalysisSummary as = new AnalysisSummary();
            List<CvParam> cvParamList = as.getCvParam();

                        // ParamList pl = new ParamList();
            // List<AbstractParam> cvParamList = pl.getParamGroup();
                        /*
             * CvParam cvp = new CvParam(); //cvp.setAccession("MS:1002010");
             * cvp.setAccession("MS:1001837"); cvp.setCv(cvPSI_MS);
             * //cvp.setName("TMT quantitation analysis");
             * cvp.setName("iTRAQ quantitation analysis"); cvParamList.add(cvp);
             */
            // id: MS:1001837
            // name: iTRAQ quantitation analysis
            CvParam cvp6 = new CvParam();
            cvp6.setAccession("MS:1002023");
            cvp6.setCv(cvPSI_MS);
            cvp6.setName("MS2 tag-based analysis");
            cvParamList.add(cvp6);

            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002024");
            cvp2.setCv(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("MS2 tag-based analysis feature level quantitation");
            cvParamList.add(cvp2);

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002025");
            cvp3.setCv(cvPSI_MS);
            cvp3.setValue("true");
            cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");
            cvParamList.add(cvp3);

            CvParam cvp4 = new CvParam();
            cvp4.setAccession("MS:1002026");
            cvp4.setCv(cvPSI_MS);
            cvp4.setValue("true");
            cvp4.setName("MS2 tag-based analysis protein level quantitation");
            cvParamList.add(cvp4);

            CvParam cvp5 = new CvParam();
            cvp5.setAccession("MS:1002027");
            cvp5.setCv(cvPSI_MS);
            cvp5.setValue("false");
            cvp5.setName("MS2 tag-based analysis protein group level quantitation");
            cvParamList.add(cvp5);

            qml.setAnalysisSummary(as);
        }
        if (technique.contains("TMT")) {

            CvParam cvp = new CvParam();
            cvp.setAccession("MS:1001837");
            cvp.setCv(cvPSI_MS);
            cvp.setName("TMT quantitation analysis");

            CvParam cvp6 = new CvParam();
            cvp6.setAccession("MS:1002023");
            cvp6.setCv(cvPSI_MS);
            cvp6.setName("MS2 tag-based analysis");

            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002024");
            cvp2.setCv(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("MS2 tag-based analysis feature level quantitation");

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002025");
            cvp3.setCv(cvPSI_MS);
            cvp3.setValue("true");
            cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

            CvParam cvp4 = new CvParam();
            cvp4.setAccession("MS:1002026");
            cvp4.setCv(cvPSI_MS);
            cvp4.setValue("true");
            cvp4.setName("MS2 tag-based analysis protein level quantitation");

            CvParam cvp5 = new CvParam();
            cvp5.setAccession("MS:1002027");
            cvp5.setCv(cvPSI_MS);
            cvp5.setValue("false");
            cvp5.setName("MS2 tag-based analysis protein group level quantitation");

            AnalysisSummary as = new AnalysisSummary();
            List<CvParam> cvParamList = as.getCvParam();

            cvParamList.add(cvp6);
            cvParamList.add(cvp2);
            cvParamList.add(cvp3);
            cvParamList.add(cvp4);
            cvParamList.add(cvp5);

            qml.setAnalysisSummary(as);
        }
        if (technique.contains("emPAI")) {

            CvParam cvp = new CvParam();
            cvp.setAccession("MS:1001836");
            cvp.setCv(cvPSI_MS);
            cvp.setName("spectral counting quantitation analysis");

            CvParam cvp1 = new CvParam();
            cvp1.setAccession("MS:1002015");
            cvp1.setCv(cvPSI_MS);
            cvp1.setValue("false");
            cvp1.setName("spectral count peptide level quantitation");

            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002016");
            cvp2.setCv(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("spectral count protein level quantitation");

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002017");
            cvp3.setCv(cvPSI_MS);
            cvp3.setValue("false");
            cvp3.setName("spectral count proteingroup level quantitation");

            AnalysisSummary as = new AnalysisSummary();
            List<CvParam> cvParamList = as.getCvParam();

            cvParamList.add(cvp);
            cvParamList.add(cvp1);
            cvParamList.add(cvp2);
            cvParamList.add(cvp3);
            qml.setAnalysisSummary(as);
        }

        // --------------------------//
        // ... CREATE INPUT FILES ...//
        // --------------------------//
        InputFiles inputFiles = new InputFiles();

        // --------------------------//
        // ... RAW FILES ...//
        // --------------------------//
        // ... Get raw files ...//
        List<RawFilesGroup> rawFilesGroupList = inputFiles.getRawFilesGroup();
        Map<String, List<String[]>> hmRawFiles = new HashMap<String, List<String[]>>();

        // Build up data structure for raw data files.
        for (RawDataFile dataFile : rawData) {
            String dataFileKey = dataFile.getFileName();
            if (!hmRawFiles.containsKey(dataFileKey)) {
                List<String[]> al = new ArrayList<String[]>();
                String[] sTemp = new String[2];
                sTemp[0] = dataFile.getFileName();
                sTemp[1] = dataFile.getAbsoluteFileName();
                al.add(sTemp);
                hmRawFiles.put(dataFileKey, al);
            } else {
                List<String[]> al2 = hmRawFiles.get(dataFileKey);
                String[] sTemp = new String[2];
                sTemp[0] = dataFile.getFileName();
                sTemp[1] = dataFile.getAbsoluteFileName();
                al2.add(sTemp);
                hmRawFiles.put(dataFileKey, al2);
            }
        }

        // ... Set raw files groups ...//
        int rawFileCounter = 0;
        for (Entry<String, List<String[]>> entry : hmRawFiles.entrySet()) {

            RawFilesGroup rawFilesGroup = new RawFilesGroup();
            List<RawFile> rawFilesList = rawFilesGroup.getRawFile();

            // String rgId = "raw_"+Integer.toString(iCounter+1);
            String rgId = rawData.get(rawFileCounter).getFileName();
            List<String[]> saGroups = entry.getValue();
            for (String[] slFiles : saGroups) {
                // ... Identify the corresponding raw files
                String rawFname = slFiles[0];
                String rawId = "r" + Integer.toString(rawFileCounter + 1);
                // ... Raw files ...//
                RawFile rawFile = new RawFile();
                rawFile.setName(rawFname);
                rawFile.setId(rawId);
                rawFile.setLocation(slFiles[1]);
                rawFilesList.add(rawFile);
                rawFileCounter++;
            }

            rawFilesGroup.setId(rgId);
            rawFilesGroupList.add(rawFilesGroup);
        }

        // -----------------------------//
        // ... IDENTIFICATION FILES ...//
        // -----------------------------//
        // ... Define those structures that will be further used (e.g. in
        // AssayList) ...//
        Map<String, String> idFileNameIdMap = new HashMap<String, String>();

        // ... Identification file containers ...//
        IdentificationFiles idFiles = inputFiles.getIdentificationFiles();
        if (idFiles == null) {
            idFiles = new IdentificationFiles();
        }
        List<IdentificationFile> idFilesList = idFiles.getIdentificationFile();

        // ... Select all raw files from grid ...//
        for (int iI = 0; iI < rawData.size(); iI++) {
            // ... Identification files ...//
            IdentDataFile identFile = rawData.get(iI).getIdentificationDataFile();
            String idFname = identFile.getFileName();
            String idId = "id_file" + Integer.toString(iI + 1);

            IdentificationFile idFile = new IdentificationFile();
            idFile.setName(idFname);
            idFile.setId(idId);
            idFile.setLocation(identFile.getAbsoluteFileName().replace("\\", "/"));
            idFilesList.add(idFile);
            idFileNameIdMap.put(idFname, idId); // ... Saving hashmap for
            // AssayList ...//
        }

        inputFiles.setIdentificationFiles(idFiles);
        qml.setInputFiles(inputFiles);

        // ---------------------------//
        // ... Create SoftwareList ...//
        // ---------------------------//
        SoftwareList softwareList = new SoftwareList();
        Software software = new Software();
        softwareList.getSoftware().add(software);
        software.setId("x-Tracker");
        software.setVersion("2.0-ALPHA");
        CvParam cvpSW = new CvParam();
        cvpSW.setAccession("MS:1002123");
        cvpSW.setCv(cvPSI_MS);
        cvpSW.setName("x-Tracker");
        software.getCvParam().add(cvpSW);

        Software software2 = new Software();
        softwareList.getSoftware().add(software2);
        software2.setId("ProteoSuite");
        software2.setVersion(ProteoSuiteView.PROTEOSUITE_VERSION);
        CvParam cvpSW2 = new CvParam();
        cvpSW2.setAccession("MS:1002124");
        cvpSW2.setCv(cvPSI_MS);
        cvpSW2.setName("ProteoSuite");
        software2.getCvParam().add(cvpSW2);
        qml.setSoftwareList(softwareList);

        // ---------------------------------//
        // ... Create DataProcessingList ...//
        // ---------------------------------//
        DataProcessingList dataProcessingList = new DataProcessingList();
        DataProcessing dataProcessing = new DataProcessing();
        dataProcessing.setId("DP1");
        dataProcessing.setSoftware(software);
        dataProcessing.setOrder(BigInteger.ONE);

        // ... Based on the technique, select the plugins that are available to
        // perform the quantitation ...//
        if (technique.contains("iTRAQ")) {
            String[] sPipeline = PluginManager.getPlugins(technique, rawData.get(0).getFormat(), rawData.get(0).getIdentificationDataFile().getFormat(),
                    "mzQuantML");

            // ... Processing methods ...//
            ProcessingMethod processingMethod = new ProcessingMethod();
            processingMethod.setOrder(BigInteger.ONE);
            List<AbstractParam> pmList = processingMethod.getParamGroup();
            UserParam up1 = new UserParam();
            up1.setName("Plugin type");
            up1.setValue("load identification");
            pmList.add(up1);
            UserParam up2 = new UserParam();
            up2.setName("Plugin name");
            up2.setValue(sPipeline[0]);
            pmList.add(up2);
            UserParam up3 = new UserParam();
            up3.setName("Plugin configuration file");
            up3.setValue(directory + "/xTracker_"
                    + sPipeline[0] + ".xtp");
            pmList.add(up3);
            dataProcessing.getProcessingMethod().add(processingMethod);

            ProcessingMethod processingMethod2 = new ProcessingMethod();
            processingMethod2.setOrder(BigInteger.valueOf(2));
            List<AbstractParam> pmList2 = processingMethod2.getParamGroup();
            UserParam up2_1 = new UserParam();
            up2_1.setName("Plugin type");
            up2_1.setValue("load raw spectra");
            pmList2.add(up2_1);
            UserParam up2_2 = new UserParam();
            up2_2.setName("Plugin name");
            up2_2.setValue(sPipeline[1]);
            pmList2.add(up2_2);
            UserParam up2_3 = new UserParam();
            up2_3.setName("Plugin configuration file");
            up2_3.setValue(directory + "/xTracker_"
                    + sPipeline[1] + ".xtp");
            pmList2.add(up2_3);
            dataProcessing.getProcessingMethod().add(processingMethod2);

            ProcessingMethod processingMethod3 = new ProcessingMethod();
            processingMethod3.setOrder(BigInteger.valueOf(3));
            List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
            UserParam up3_1 = new UserParam();
            up3_1.setName("Plugin type");
            up3_1.setValue("feature detection and quantitation");
            pmList3.add(up3_1);
            UserParam up3_2 = new UserParam();
            up3_2.setName("Plugin name");
            up3_2.setValue(sPipeline[2]);
            pmList3.add(up3_2);
            UserParam up3_3 = new UserParam();
            up3_3.setName("Plugin configuration file");
            up3_3.setValue(directory.replace("\\", "/") + "/xTracker_"
                    + sPipeline[2] + ".xtp");
            pmList3.add(up3_3);
            UserParam up3_4 = new UserParam();
            up3_4.setName("Feature to peptide inference method");
            up3_4.setValue("mean");
            pmList3.add(up3_4);
            UserParam up3_5 = new UserParam();
            up3_5.setName("Peptide to protein inference method");
            up3_5.setValue("weightedAverage");
            pmList3.add(up3_5);
            UserParam up3_6 = new UserParam();
            up3_6.setName("Assay to Study Variables inference method");
            up3_6.setValue("sum");
            pmList3.add(up3_6);
            UserParam up3_7 = new UserParam();
            up3_7.setName("Protein ratio calculation infer from peptide ratio");
            up3_7.setValue("true");
            pmList3.add(up3_7);
            dataProcessing.getProcessingMethod().add(processingMethod3);

            ProcessingMethod processingMethod4 = new ProcessingMethod();
            processingMethod4.setOrder(BigInteger.valueOf(4));
            List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
            UserParam up4_1 = new UserParam();
            up4_1.setName("Plugin type");
            up4_1.setValue("Output");
            pmList4.add(up4_1);
            UserParam up4_2 = new UserParam();
            up4_2.setName("Plugin name");
            up4_2.setValue(sPipeline[3]);
            pmList4.add(up4_2);
            UserParam up4_3 = new UserParam();
            up4_3.setName("Plugin configuration file");
            up4_3.setValue(directory.replace("\\", "/") + "/xTracker_"
                    + sPipeline[3] + ".xtp");
            pmList4.add(up4_3);
            dataProcessing.getProcessingMethod().add(processingMethod4);
        }
        if (technique.contains("emPAI")) {
            String[] sPipeline = PluginManager.getPlugins(technique, rawData.get(0).getFormat(), rawData.get(0).getIdentificationDataFile().getFormat(),
                    "mzQuantML");

            // ... Processing methods ...//
            ProcessingMethod processingMethod = new ProcessingMethod();
            processingMethod.setOrder(BigInteger.ONE);
            List<AbstractParam> pmList = processingMethod.getParamGroup();
            UserParam up1 = new UserParam();
            up1.setName("Plugin type");
            up1.setValue("load identification");
            pmList.add(up1);
            UserParam up2 = new UserParam();
            up2.setName("Plugin name");
            up2.setValue(sPipeline[0]);
            pmList.add(up2);
            UserParam up3 = new UserParam();
            up3.setName("Plugin configuration file");
            up3.setValue(directory.replace("\\", "/") + "/xTracker_"
                    + sPipeline[0] + ".xtp");
            pmList.add(up3);
            dataProcessing.getProcessingMethod().add(processingMethod);

            ProcessingMethod processingMethod3 = new ProcessingMethod();
            processingMethod3.setOrder(BigInteger.valueOf(3));
            List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
            UserParam up3_1 = new UserParam();
            up3_1.setName("Plugin type");
            up3_1.setValue("feature detection and quantitation");
            pmList3.add(up3_1);
            UserParam up3_2 = new UserParam();
            up3_2.setName("Plugin name");
            up3_2.setValue(sPipeline[2]);
            pmList3.add(up3_2);
            UserParam up3_3 = new UserParam();
            up3_3.setName("Plugin configuration file");
            up3_3.setValue(directory.replace("\\", "/") + "/xTracker_"
                    + sPipeline[2] + ".xtp");
            pmList3.add(up3_3);
            dataProcessing.getProcessingMethod().add(processingMethod3);

            ProcessingMethod processingMethod4 = new ProcessingMethod();
            processingMethod4.setOrder(BigInteger.valueOf(4));
            List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
            UserParam up4_1 = new UserParam();
            up4_1.setName("Plugin type");
            up4_1.setValue("Output");
            pmList4.add(up4_1);
            UserParam up4_2 = new UserParam();
            up4_2.setName("Plugin name");
            up4_2.setValue(sPipeline[3]);
            pmList4.add(up4_2);
            UserParam up4_3 = new UserParam();
            up4_3.setName("Plugin configuration file");
            up4_3.setValue(directory.replace("\\", "/") + "/xTracker_"
                    + sPipeline[3] + ".xtp");
            pmList4.add(up4_3);
            dataProcessing.getProcessingMethod().add(processingMethod4);
        }
        
        dataProcessingList.getDataProcessing().add(dataProcessing);
        qml.setDataProcessingList(dataProcessingList);

        // ------------------------//
        // ... Create AssayList ...//
        // ------------------------//
        AssayList assays = new AssayList();
        assays.setId("AssayList1");
        List<Assay> assayList = assays.getAssay();
        // This will be used in StudyVariableList
        Map<String, List<Assay>> studyVarAssayID = new HashMap<String, List<Assay>>();

        if (technique.contains("iTRAQ")) {            
            for (int rawDataFileIndex = 0; rawDataFileIndex < rawData.size(); rawDataFileIndex++) {
                RawDataFile dataFile = rawData.get(rawDataFileIndex);
                Map<String, String> conditions = dataFile.getConditions();

                for (Entry<String, String> condition : conditions.entrySet()) {
                    ITRAQReagent reagent = ITRAQReagent.getReagent(true, condition.getKey());
                    Assay assay = new Assay();
                    assay.setName(reagent.getId());
                    assay.setId("i" + (rawDataFileIndex + 1) + "_" + reagent.getId());

                    // Change me!
                    String sStudyVariable = condition.getValue();

                    List<Assay> al;
                    if (!studyVarAssayID
                            .containsKey(sStudyVariable)) {
                        al = new ArrayList<Assay>();
                    } else {
                        al = studyVarAssayID.get(sStudyVariable);
                    }

                    al.add(assay);

                    studyVarAssayID.put(sStudyVariable, al);

                    String sRawFile = dataFile.getFileName();

                    // ... Check the rawFileGroup associated to that
                    // assay ...//
                    int iK = 0;
                    for (RawFilesGroup rfg : rawFilesGroupList) {
                        String sKey = sRawFile;
                        RawFilesGroup rfGroup = rawFilesGroupList
                                .get(iK);
                        if (rfg.getId().equals(sKey)) {
                            assay.setRawFilesGroup(rfGroup);
                            break;
                        }
                        iK++;
                    }

                    Label label = new Label();
                    CvParam labelCvParam = new CvParam();
                    labelCvParam.setAccession("");
                    labelCvParam.setName(reagent.getName());
                    labelCvParam.setValue(String.valueOf(reagent.getMz()));
                    labelCvParam.setCv(cvPSI_MOD);
                    List<ModParam> modParams = label.getModification();
                    ModParam modParam = new ModParam();
                    modParam.setCvParam(labelCvParam);
                    modParam.setMassDelta(145.0f);
                    modParams.add(modParam);
                    assay.setLabel(label);
                    assayList.add(assay);
                }
            }
        }
        
        if (technique.contains("emPAI")) {
            // ... Check the rawFileGroup associated to that assay ...//
            int iK = 0;
            for (RawFilesGroup rfg : rawFilesGroupList) {
                Assay assay = new Assay();
                assay.setId("assay_" + Integer.toString(iK + 1));
                assay.setName("assay_" + Integer.toString(iK + 1));
                assay.setRawFilesGroup(rfg);
                CvParam labelCvParam = new CvParam();
                labelCvParam.setAccession("MS:1002038");
                labelCvParam.setName("unlabeled sample");
                labelCvParam.setCv(cvPSI_MS);
                Label label = new Label();
                List<ModParam> modParams = label.getModification();
                ModParam modParam = new ModParam();
                modParam.setCvParam(labelCvParam);
                modParam.setMassDelta(0.0f);
                modParams.add(modParam);
                assay.setLabel(label);
                assayList.add(assay);
                iK++;
            }
        }
        qml.setAssayList(assays);

        // --------------------------------//
        // ... Create StudyVariableList ...//
        // --------------------------------//
        StudyVariableList studyVariables = new StudyVariableList();
        List<StudyVariable> studyVariableList = studyVariables
                .getStudyVariable();

        if (technique.contains("iTRAQ")) {
            for (Entry<String, List<Assay>> pairs : studyVarAssayID.entrySet()) {
                String group = pairs.getKey();
                List<Assay> al = pairs.getValue();
                StudyVariable studyVariable = new StudyVariable();
                studyVariable.setName(group);
                studyVariable.setId("SV_" + group);
                List<String> assayRefList = studyVariable.getAssayRefs();
                for (Assay assay : al) {
                    assayRefList.add(assay.getId());
                }
                CvParam cvp9 = new CvParam();
                cvp9.setAccession("MS:1001807");
                cvp9.setCv(cvPSI_MS);
                cvp9.setValue("1");
                cvp9.setName("StudyVariable attribute");
                List<AbstractParam> paramList = studyVariable.getParamGroup();
                paramList.add(cvp9);
                studyVariableList.add(studyVariable);
            }
        }
        if (technique.contains("emPAI")) {
            StudyVariable studyVariable = new StudyVariable();
            studyVariable.setName("Group");
            studyVariable.setId("SV_group");
            List<String> assayRefList = studyVariable.getAssayRefs();
            assayRefList.add(assayList.get(0).getId());
            studyVariableList.add(studyVariable);
        }
        
        qml.setStudyVariableList(studyVariables);

        // ... Marshal mzQuantML object ...//
        MzQuantMLMarshaller marshaller = new MzQuantMLMarshaller(directory + "/" + quantFileName);
        marshaller.marshall(qml);     
        
        return directory + "/" + quantFileName;
    }    
}