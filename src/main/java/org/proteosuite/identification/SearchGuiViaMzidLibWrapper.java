package org.proteosuite.identification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.Actions;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.model.TaskStreams;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.MzMLFile;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.FileFormatUtils;
import org.proteosuite.utils.StringUtils;
import uk.ac.liv.mzidlib.AddRetentionTimeToMzid;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {

    private static final BackgroundTaskManager taskManager = BackgroundTaskManager.getInstance();
    private static final AnalyseData data = AnalyseData.getInstance();
    private static final String jarLocation = getMzIdLibJar().getAbsolutePath();
    private final boolean genomeAnnotation;
    private final Set<RawDataFile> rawData;
    private String prefix = null;
    private final List<String> commandList;

    {
        commandList = new LinkedList<>();
        commandList.add("java");
        commandList.add("-Xmx10G");
        commandList.add("-jar");
        commandList.add(jarLocation);
    }

    public SearchGuiViaMzidLibWrapper(Set<RawDataFile> inputSpectra, String databasePath, Map<String, String> searchParameters, String peptideLevelThresholding, String proteinLevelThresholding) throws ProteoSuiteException {
        this.genomeAnnotation = false;
        this.rawData = inputSpectra;
        commandList.add("GenericSearch");
        if (databasePath != null && !databasePath.isEmpty()) {
            commandList.add("-inputFasta");
            commandList.add(databasePath);
        }

        if (!searchParameters.isEmpty()) {
            commandList.add("-searchParameters");
            File searchParameterFile = createSearchParameterFile(searchParameters);
            commandList.add(searchParameterFile.getAbsolutePath());
        }

        commandList.add("-peptideThreshValue");
        commandList.add(peptideLevelThresholding);

        commandList.add("-proteinThreshValue");
        commandList.add(proteinLevelThresholding);

    }

    public SearchGuiViaMzidLibWrapper(Set<RawDataFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix, String peptideLevelThresholding, String proteinLevelThresholding) throws ProteoSuiteException {
        this.genomeAnnotation = true;
        this.rawData = inputSpectra;
        this.prefix = prefix;

        commandList.add("ProteoAnnotator");
        commandList.add("-inputGFF");
        commandList.add(geneModel[0]);
        if (geneModel[1] != null && !geneModel[1].isEmpty()) {
            commandList.add("-inputFasta");
            commandList.add(geneModel[1]);
        }

        if (!otherModels.isEmpty()) {
            commandList.add("-inputPredicted");
            StringBuilder otherModelBuilder = new StringBuilder();
            //otherModelBuilder.append("\"");
            Iterator<Entry<String, String>> iterator = otherModels.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> model = iterator.next();
                otherModelBuilder.append("-").append(model.getKey());
                otherModelBuilder.append(";");
                if (model.getValue() != null && !model.getValue().isEmpty()) {
                    otherModelBuilder.append(model.getValue());
                }

                if (iterator.hasNext()) {
                    otherModelBuilder.append("##");
                }
            }

            //otherModelBuilder.append("\"");
            commandList.add(otherModelBuilder.toString());
        }

        if (this.prefix != null && !this.prefix.isEmpty()) {
            commandList.add("-prefix");
            commandList.add(this.prefix);
        }

        if (!searchParameters.isEmpty()) {
            commandList.add("-searchParameters");
            File searchParameterFile = createSearchParameterFile(searchParameters);
            commandList.add(searchParameterFile.getAbsolutePath());
        }

        commandList.add("-peptideThreshValue");
        commandList.add(peptideLevelThresholding);

        commandList.add("-proteinThreshValue");
        commandList.add(proteinLevelThresholding);

        commandList.add("-compress");
        commandList.add("false");
    }

    public void compute() {
        if (this.genomeAnnotation) {
            this.computeGenomeAnnotation();
        } else {
            this.computeGenericSearch();
        }
    }

    private File createSearchParameterFile(Map<String, String> searchParameters) throws ProteoSuiteException {
        StringBuilder parametersBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = searchParameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> searchEntry = iterator.next();
            if (searchEntry.getKey().toUpperCase().contains("_MODS")) {
                parametersBuilder.append("-").append(searchEntry.getKey()).append(StringUtils.space()).append("\"").append(searchEntry.getValue()).append("\"");
            } else {
                parametersBuilder.append("-").append(searchEntry.getKey()).append(StringUtils.space()).append(searchEntry.getValue());
            }

            if (iterator.hasNext()) {
                parametersBuilder.append(StringUtils.space());
            }
        }

        File temporaryFile = null;
        try {
            temporaryFile = File.createTempFile("proteosuite_searchGUI_settings_", null);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile))) {
                writer.write(parametersBuilder.toString() + "\n");
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            temporaryFile = null;
            throw new ProteoSuiteException("Problem writing out search settings file for mzidLib.", ex);
        }

        return temporaryFile;
    }

    private void computeGenericSearch() {
        Iterator<RawDataFile> iterator = rawData.iterator();
        final RawDataFile primaryFile = iterator.next();
        BackgroundTask<RawDataFile> task = new BackgroundTask<>(primaryFile, "Create Identifications");
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, RawDataFile>() {
            @Override
            public ProteoSuiteActionResult<File> act(RawDataFile argument) {
                if (rawData.size() > 1) {
                    File mgfFile = null;
                    for (RawDataFile rawDataFile : rawData) {
                        if (rawDataFile instanceof MzMLFile) {
                            File file = new File(rawDataFile.getAbsoluteFileName().replaceAll("\\.mzML$", ".mgf").replace("\\.mzml$", ".mgf"));
                            boolean successfulConversion = FileFormatUtils.mzMLToMGF(((MzMLFile) rawDataFile).getUnmarshaller(), file.getAbsolutePath());
                            if (successfulConversion && file.exists()) {
                                rawDataFile.setPeakListFile(file);
                                mgfFile = file;
                            }
                        } else {
                            mgfFile = rawDataFile.getFile();
                            rawDataFile.setPeakListFile(rawDataFile.getFile());
                        }
                    }

                    if (mgfFile != null) {
                        commandList.add("-spectrum_files");
                        commandList.add(mgfFile.getParent());
                    }
                } else {
                    if (primaryFile instanceof MzMLFile) {
                        File file = new File(primaryFile.getAbsoluteFileName().replaceAll("\\.mzML$", ".mgf").replace("\\.mzml$", ".mgf"));
                        boolean successfulConversion = FileFormatUtils.mzMLToMGF(((MzMLFile) primaryFile).getUnmarshaller(), file.getAbsolutePath());                       
                        if (successfulConversion && file.exists()) {
                            primaryFile.setPeakListFile(file);
                            commandList.add("-spectrum_files");
                            commandList.add(file.getAbsolutePath());
                        }
                    } else {
                        commandList.add("-spectrum_files");
                        primaryFile.setPeakListFile(primaryFile.getFile());
                        commandList.add(primaryFile.getAbsoluteFileName());
                    }
                }

                commandList.add("-outputFolder");
                if (primaryFile.getFileName().toUpperCase().endsWith("MGF")) {
                    commandList.add(primaryFile.getFile().getParentFile().getAbsolutePath() + File.separator + primaryFile.getFileName().replaceAll("\\.[Mm][Gg][Ff]$", "_ident"));
                } else if (primaryFile.getFileName().toUpperCase().endsWith("MZML")) {
                    commandList.add(primaryFile.getFile().getParentFile().getAbsolutePath() + File.separator + primaryFile.getFileName().replaceAll("\\.[Mm][Zz][Mm][Ll]$", "_ident"));
                }

                String outputMzid = null;
                if (primaryFile.getFileName().toUpperCase().endsWith("MGF")) {
                    outputMzid = primaryFile.getFile().getParentFile().getAbsolutePath()
                            + File.separator + primaryFile.getFileName().replaceFirst("\\.[Mm][Gg][Ff]$", "_ident") + File.separator
                            + "combined_fdr_peptide_threshold_proteoGrouper_fdr_Threshold.mzid";
                } else if (primaryFile.getFileName().toUpperCase().endsWith("MZML")) {
                    outputMzid = primaryFile.getFile().getParentFile().getAbsolutePath()
                            + File.separator + primaryFile.getFileName().replaceFirst("\\.[Mm][Zz][Mm][Ll]$", "_ident") + File.separator
                            + "combined_fdr_peptide_threshold_proteoGrouper_fdr_Threshold.mzid";
                }

                if (outputMzid == null) {
                    return new ProteoSuiteActionResult(null, new ProteoSuiteException("Could not generate output mzid file (path)."));                    
                }

                File mzidNonRtFile = new File(outputMzid);

                System.out.println("Execution String: " + StringUtils.join(" ", commandList));               

                try {
                    Process proc = getProcess();
                    TaskStreams streams = getStreams(proc);
                    task.setStreams(streams);
                    int returnVal = awaitProcessTermination(proc);
                    if (returnVal != 0) {
                        return new ProteoSuiteActionResult(new ProteoSuiteException("Generic identification search produced exceptional return value."));
                    }

                    // If we are in 'folder' mode, then don't add retention times - it is impossible at this stage.
                    if (rawData.size() > 1) {
                        return new ProteoSuiteActionResult(mzidNonRtFile);                        
                    }

                    // Need to add in retention times now.
                    if (mzidNonRtFile.exists()) {
                        File mzidRtFile = new File(mzidNonRtFile.getCanonicalPath() + ".tmp");
                        Object deadStore = new AddRetentionTimeToMzid(mzidNonRtFile.getCanonicalPath(), primaryFile.getPeakListFile().getCanonicalPath(), mzidRtFile.getCanonicalPath());

                        // If the mzid file exists with retention times, delete the original and rename it to the original name.
                        // Even if the delete fails, the file should be overwritten.
                        if (mzidRtFile.exists()) {
                            File mzidFile = new File(mzidNonRtFile.getCanonicalPath());
                            mzidNonRtFile.delete();
                            Files.move(mzidRtFile.toPath(), mzidFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            mzidRtFile.delete();
                            return new ProteoSuiteActionResult(mzidFile);
                        }
                    }
                }
                catch (IOException | InterruptedException ex) {                
                    Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    return new ProteoSuiteActionResult(new ProteoSuiteException("Error running mzidLib/searchGUI for identifications.", ex));
                }

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, RawDataFile>() {
            @Override
            public ProteoSuiteActionResult act(RawDataFile argument) {
                File mzidFile = task.getResultOfClass(File.class);
                if (mzidFile != null && mzidFile.exists()) {
                    // Let's copy this file to the main directory.
                    File finalFile = new File(mzidFile.getParentFile().getParentFile().getAbsolutePath() + File.separator
                            + mzidFile.getParentFile().getName().replaceFirst("_ident", "") + ".mzid");
                    try {
                        // Copy instead of move and do not delete folder, while we need the untouched folder.
                        Files.copy(mzidFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        //Files.move(mzidFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        //SystemUtils.deleteRecursive(mzidFile.getParentFile());
                    }
                    catch (IOException ex) {
                        Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        finalFile = mzidFile;
                        return new ProteoSuiteActionResult(new ProteoSuiteException("Error copying mzid file to the main data directory.", ex));
                    }
                    
                    MzIdentMLFile mzid = new MzIdentMLFile(finalFile, primaryFile);
                    primaryFile.setIdentificationDataFile(mzid);

                    data.getInspectModel().addIdentDataFile(mzid);
                }

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, RawDataFile>() {
            @Override
            public ProteoSuiteActionResult act(RawDataFile argument) {
                System.out.println("Identification search done.");
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        while (iterator.hasNext()) {
            RawDataFile dataFile = iterator.next();
            BackgroundTask slaveTask = new BackgroundTask(dataFile, "Create Identifications");
            slaveTask.setSlaveStatus(true);
            slaveTask.addAsynchronousProcessingAction(Actions.latchedAction(task.getTaskLatch()));

            taskManager.submit(slaveTask);
        }

        taskManager.submit(task);
    }

    private void computeGenomeAnnotation() {

        Iterator<RawDataFile> iterator = rawData.iterator();
        final RawDataFile primaryFile = iterator.next();
        BackgroundTask task = new BackgroundTask(primaryFile, "Run Genome Annotation");
        final CountDownLatch masterTaskLatch = new CountDownLatch(1);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult<Integer> act(ProteoSuiteActionSubject ignored) {
                if (rawData.size() > 1) {
                    File mgfFile = null;
                    for (RawDataFile rawDataFile : rawData) {
                        if (rawDataFile instanceof MzMLFile) {
                            File file = new File(rawDataFile.getAbsoluteFileName().replaceAll("\\.mzML$", ".mgf").replace("\\.mzml$", ".mgf"));
                            boolean successfulConversion = FileFormatUtils.mzMLToMGF(((MzMLFile) rawDataFile).getUnmarshaller(), file.getAbsolutePath());
                            if (successfulConversion && file.exists()) {
                                mgfFile = file;
                            }
                        } else {
                            mgfFile = rawDataFile.getFile();
                        }
                    }

                    if (mgfFile != null) {
                        commandList.add("-spectrum_files");
                        commandList.add(mgfFile.getParent());
                    }
                } else {
                    commandList.add("-spectrum_files");
                    if (primaryFile instanceof MzMLFile) {
                        File file = new File(primaryFile.getAbsoluteFileName().replaceAll("\\.mzML$", ".mgf").replace("\\.mzml$", ".mgf"));
                        boolean successfulConversion = FileFormatUtils.mzMLToMGF(((MzMLFile) primaryFile).getUnmarshaller(), file.getAbsolutePath());
                        if (successfulConversion && file.exists()) {
                            commandList.add("-spectrum_files");
                            commandList.add(file.getAbsolutePath());
                        }
                    } else {
                        commandList.add("-spectrum_files");
                        commandList.add(primaryFile.getAbsoluteFileName());
                    }
                }

                commandList.add("-outputFolder");
                commandList.add(primaryFile.getFile().getParentFile().getAbsolutePath() + File.separator + "annotation_output");
                System.out.println("Execution String: " + StringUtils.join(" ", commandList));

                try {
                    Process proc = getProcess();
                    TaskStreams streams = getStreams(proc);
                    task.setStreams(streams);
                    return new ProteoSuiteActionResult(awaitProcessTermination(proc));
                }
                catch (InterruptedException | IOException ex) {
                    Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    return new ProteoSuiteActionResult(new ProteoSuiteException("Error running mzidLib/searchGUI for genome annotation.", ex));
                }                
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setIdentificationsDone();

                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setResultsDone();

                String outputMzid = primaryFile.getFile().getParentFile().getAbsolutePath()
                        + File.separator + "annotation_output" + File.separator
                        + prefix
                        + "combined_fdr_peptide_threshold_mappedGff2_proteoGrouper_fdr_Threshold.mzid";

                File mzidFile = new File(outputMzid);
                if (mzidFile.exists()) {
                    data.getInspectModel().addIdentDataFile(new MzIdentMLFile(mzidFile, null));
                }

                masterTaskLatch.countDown();

                JOptionPane
                        .showConfirmDialog(
                                AnalyseDynamicTab.getInstance(),
                                "Your genome annotation run has finished and your result files are now available.\n"
                                + "Please check the \"annotation_output\" folder where your raw data was situated.\n"
                                + "The mzID output file from the pipeline is currently loading in the background and should be available for viewing in the Inspect tab soon.\n"
                                + "Check the output folder for various CSV files and multiple annotated GFF files.\n"
                                + "Also check the \"ProteoAnnotator.txt\" file for any error messages and a log of the run.",
                                "Genome Annotation Completed", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.INFORMATION_MESSAGE);

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction((ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>) (ProteoSuiteActionSubject argument) -> {
            System.out.println("Genome annotation done.");
            return ProteoSuiteActionResult.emptyResult();
        });

        while (iterator.hasNext()) {
            RawDataFile dataFile = iterator.next();
            BackgroundTask slaveTask = new BackgroundTask(dataFile, "Run Genome Annotation");
            slaveTask.setSlaveStatus(true);
            slaveTask.addAsynchronousProcessingAction(Actions.latchedAction(masterTaskLatch));
            taskManager.submit(slaveTask);
        }

        taskManager.submit(task);
    }

    public void printDebugInfo() {
        System.out.println("This JAR found is: \"" + getMzIdLibJar() + "\"");
    }

    private Process getProcess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder(commandList);
        Process proc = builder.start();
        return proc;
    }

    private TaskStreams getStreams(Process proc) {
        TaskStreams streams = new TaskStreams();
        streams.setErrorStream(proc.getErrorStream());
        streams.setOutputStream(proc.getInputStream());
        return streams;
    }

    private int awaitProcessTermination(Process proc) throws IOException, InterruptedException {
        return proc.waitFor();
    }

    private static File getMzIdLibJar() {
        try {
            File thisClassFile = new File(URLDecoder.decode(SearchGuiViaMzidLibWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
            if (thisClassFile.getAbsolutePath().endsWith("classes")) {
                return new File("c:\\mzidlib\\mzidentml-lib-1.6.11.jar");
            }

            File mzidlibFolder = new File(thisClassFile.getParent() + File.separator + "mzidlib");
            System.out.println("Folder is :" + mzidlibFolder);

            File[] potentialJars = mzidlibFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File directory, String fileName) {
                    return fileName.endsWith(".jar");
                }
            });

            return potentialJars[0];
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 not supported?!");
        }
    }
}