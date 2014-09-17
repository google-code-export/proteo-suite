package org.proteosuite.identification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.Log;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {

    private boolean genomeAnnotation = false;
    private List<String> commandList; 
    
    private Set<MascotGenericFormatFile> rawData;
    private final AnalyseData data = AnalyseData.getInstance();
    private String prefix = null;

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix, String peptideLevelThresholding, String proteinLevelThresholding) {
        this.genomeAnnotation = true;
        this.rawData = inputSpectra;
        this.prefix = prefix;

        commandList = new LinkedList<>();
        commandList.add("java");
        commandList.add("-Xmx5G");
        commandList.add("-jar");

        String jarLocation = getMzIdLibJar().getAbsolutePath();
        //commandList.add(jarLocation.contains(" ") ? "\"" + jarLocation + "\"" : jarLocation);
        commandList.add(jarLocation);
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

    private void computeGenomeAnnotation() {

        BackgroundTask task = new BackgroundTask(rawData.iterator().next(), "Run Genome Annotation");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Integer act(BackgroundTaskSubject ignored) {
                try {
                    MascotGenericFormatFile mgf = rawData.iterator().next();
                    if (rawData.size() > 1) {
                        commandList.add("-spectrum_files");
                        commandList.add(mgf.getFile().getParent());
                    } else {
                        commandList.add("-spectrum_files");
                        commandList.add(mgf.getAbsoluteFileName());
                    }

                    commandList.add("-outputFolder");
                    commandList.add(mgf.getFile().getParentFile().getAbsolutePath() + File.separator + "annotation_output");                    
                    System.out.println("Execution String: " + StringUtils.join(" ", commandList));                   
                    
                    ProcessBuilder builder = new ProcessBuilder(commandList);
                    Process proc = builder.start();                   
                    
                    Log log = new Log();
                    log.setErrorOutput(new BufferedReader(new InputStreamReader(proc.getErrorStream())));
                    log.setStandardOutput(new BufferedReader(new InputStreamReader(proc.getInputStream())));
                    data.getLogs().add(log);                   
                    return proc.waitFor();
                } catch (IOException | InterruptedException ex) {
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
                
                String outputMzid = rawData.iterator().next().getFile().getParentFile().getAbsolutePath()
                        + File.separator + "annotation_output" + File.separator
                        + prefix
                        + "combined_fdr_peptide_threshold_mappedGff2_proteoGrouper_fdr_Threshold.mzid";

                File mzidFile = new File(outputMzid);
                if (mzidFile.exists()) {
                    data.getInspectModel().addIdentDataFile(new MzIdentMLFile(mzidFile, null));
                }

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

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                System.out.println("Genome annotation done.");
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    public void printDebugInfo() {
        System.out.println("This JAR found is: \"" + getMzIdLibJar() + "\"");
    }

    private static File getMzIdLibJar() {
        try {
            File thisClassFile = new File(URLDecoder.decode(SearchGuiViaMzidLibWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
            if (thisClassFile.getAbsolutePath().endsWith("classes")) {
                return new File("c:\\mzidlib\\mzidentml-lib-1.6.11-SNAPSHOT.jar");
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
