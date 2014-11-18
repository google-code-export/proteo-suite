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
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.IntWrapper;
import org.proteosuite.model.TaskStreams;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {

    private static final BackgroundTaskManager taskManager = BackgroundTaskManager.getInstance();
    private static final AnalyseData data = AnalyseData.getInstance();
    private static final String jarLocation = getMzIdLibJar().getAbsolutePath();
    private final boolean genomeAnnotation;
    private final Set<MascotGenericFormatFile> rawData;
    private String prefix = null;
    private final List<String> commandList;

    {
        commandList = new LinkedList<>();
        commandList.add("java");
        commandList.add("-Xmx5G");
        commandList.add("-jar");
        commandList.add(jarLocation);
    }

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParameters, String peptideLevelThresholding, String proteinLevelThresholding) {
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

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix, String peptideLevelThresholding, String proteinLevelThresholding) {
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

    private File createSearchParameterFile(Map<String, String> searchParameters) {
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
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            temporaryFile = null;
        }

        return temporaryFile;
    }

    private void computeGenericSearch() {
        Iterator<MascotGenericFormatFile> iterator = rawData.iterator();
        final MascotGenericFormatFile primaryFile = iterator.next();
        BackgroundTask task = new BackgroundTask(primaryFile, "Create Identifications");
        final CountDownLatch masterTaskLatch = new CountDownLatch(1);
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Integer act(BackgroundTaskSubject argument) {

                if (rawData.size() > 1) {
                    commandList.add("-spectrum_files");
                    commandList.add(primaryFile.getFile().getParent());
                } else {

                    commandList.add("-spectrum_files");
                    commandList.add(primaryFile.getAbsoluteFileName());
                }                
                

                commandList.add("-outputFolder");
                commandList.add(primaryFile.getFile().getParentFile().getAbsolutePath() + File.separator + primaryFile.getFileName().replace(".mgf", "_ident"));
                System.out.println("Execution String: " + StringUtils.join(" ", commandList));

                try {
                    IntWrapper processVal = new IntWrapper(-1);
                    TaskStreams streams = execAndProcess(processVal);
                    task.setStreams(streams);
                    return processVal.get();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }

        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {                

                String outputMzid = primaryFile.getFile().getParentFile().getAbsolutePath()
                        + File.separator + primaryFile.getFileName().replace(".mgf", "_ident") + File.separator
                        + "combined_fdr_peptide_threshold_proteoGrouper_fdr_Threshold.mzid";

                File mzidFile = new File(outputMzid);
                if (mzidFile.exists()) {
                    // Let's copy this file to the main directory.
                    File finalFile = new File(mzidFile.getParentFile().getParentFile().getAbsolutePath() + File.separator + 
                            mzidFile.getParentFile().getName() + ".mzid");
                    try {
                        Files.move(mzidFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        //SystemUtils.deleteRecursive(mzidFile.getParentFile());
                    } catch (IOException ex) {
                        Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        finalFile = mzidFile;
                    }
                    
                    data.getInspectModel().addIdentDataFile(new MzIdentMLFile(finalFile, primaryFile.selfOrParent()));  
                    
                    masterTaskLatch.countDown();
                }

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                System.out.println("Identification search done.");
                return null;
            }
        });
        
        while (iterator.hasNext()) {
            MascotGenericFormatFile dataFile = iterator.next();
            BackgroundTask slaveTask = new BackgroundTask(dataFile, "Create Identifications");
            slaveTask.setSlaveStatus(true);
            slaveTask.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
                @Override
                public Object act(BackgroundTaskSubject argument) {
                    try {
                        masterTaskLatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return null;
                }
            });

            BackgroundTaskManager.getInstance().submit(slaveTask);
        }

        taskManager.submit(task);
    }

    private void computeGenomeAnnotation() {

        Iterator<MascotGenericFormatFile> iterator = rawData.iterator();
        final MascotGenericFormatFile primaryFile = iterator.next();
        BackgroundTask task = new BackgroundTask(primaryFile, "Run Genome Annotation");
        final CountDownLatch masterTaskLatch = new CountDownLatch(1);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Integer act(BackgroundTaskSubject ignored) {                
                if (rawData.size() > 1) {
                    commandList.add("-spectrum_files");
                    commandList.add(primaryFile.getFile().getParent());
                } else {

                    commandList.add("-spectrum_files");
                    commandList.add(primaryFile.getAbsoluteFileName());
                }

                commandList.add("-outputFolder");
                commandList.add(primaryFile.getFile().getParentFile().getAbsolutePath() + File.separator + "annotation_output");
                System.out.println("Execution String: " + StringUtils.join(" ", commandList));

                try {
                    IntWrapper processVal = new IntWrapper(-1);                   
                    TaskStreams streams = execAndProcess(processVal);
                    task.setStreams(streams);
                    return processVal.get();
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
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

                return null;
            }
        });

        task.addCompletionAction((ProteoSuiteAction<Object, BackgroundTaskSubject>) (BackgroundTaskSubject argument) -> {
            System.out.println("Genome annotation done.");
            return null;
        });

        while (iterator.hasNext()) {
            MascotGenericFormatFile dataFile = iterator.next();
            BackgroundTask slaveTask = new BackgroundTask(dataFile, "Run Genome Annotation");
            slaveTask.setSlaveStatus(true);
            slaveTask.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
                @Override
                public Object act(BackgroundTaskSubject argument) {
                    try {
                        masterTaskLatch.await();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SearchGuiViaMzidLibWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return null;
                }
            });

            BackgroundTaskManager.getInstance().submit(slaveTask);
        }

        taskManager.submit(task);
    }

    public void printDebugInfo() {
        System.out.println("This JAR found is: \"" + getMzIdLibJar() + "\"");
    }

    private TaskStreams execAndProcess(IntWrapper processVal) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(commandList);
        Process proc = builder.start();
        TaskStreams streams = new TaskStreams();
        streams.setErrorStream(proc.getErrorStream());
        streams.setOutputStream(proc.getInputStream());        
        processVal.set(proc.waitFor());
        return streams;
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
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 not supported?!");
        }
    }
}