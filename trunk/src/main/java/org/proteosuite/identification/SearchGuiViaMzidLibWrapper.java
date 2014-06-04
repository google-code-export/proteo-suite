package org.proteosuite.identification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.Log;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.Task;
import org.proteosuite.utils.FileFormatUtils;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {

    private boolean genomeAnnotation = false;
    private List<String> commandList;
    private BufferedReader reader;
    private Set<MascotGenericFormatFile> rawData;
    private AnalyseData data = AnalyseData.getInstance();
    private String prefix = null;

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParamters, boolean doProteoGrouper, boolean doEmPAI) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix) {
        this.genomeAnnotation = true;
        this.rawData = inputSpectra;
        this.prefix = prefix;

        commandList = new LinkedList<>();
        commandList.add("java");
        commandList.add("-Xmx5G");
        commandList.add("-jar");

        commandList.add(getMzIdLibJar().getAbsolutePath());

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
            otherModelBuilder.append("\"");
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

            otherModelBuilder.append("\"");
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile));
            writer.write(parametersBuilder.toString() + "\n");
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            temporaryFile = null;
        }

        return temporaryFile;
    }

    private void computeGenomeAnnotation() {
        data.getTasksModel()
                .set(new Task(rawData.iterator().next().getFileName(),
                                "Run Genome Annotation"));
        TasksTab.getInstance().refreshFromTasksModel();

        SwingWorker<Integer, Void> worker = new SwingWorker() {
            @Override
            protected Integer doInBackground() throws Exception {
                MascotGenericFormatFile mgf;
                if (rawData.size() > 1) {
                    mgf = FileFormatUtils.merge(new TreeSet<>(rawData), 1024 * 1024 * 1024);
                } else {
                    mgf = rawData.iterator().next();
                }

                if (mgf != null) {
                    commandList.add("-spectrum_files");
                    commandList.add(mgf.getAbsoluteFileName());
                }

                commandList.add("-outputFolder");
                commandList.add(mgf.getFile().getParentFile().getAbsolutePath() + File.separator + "annotation_output");
                String executionString = StringUtils.join(" ", commandList);
                System.out.println("Execution String: " + executionString);
                Process process = Runtime.getRuntime().exec(executionString);
                Log log = new Log();
                log.setErrorOutput(new BufferedReader(new InputStreamReader(process.getErrorStream())));
                log.setStandardOutput(new BufferedReader(new InputStreamReader(process.getInputStream())));
                data.getLogs().add(log);               
                
                TasksTab.getInstance().refreshFromTasksModel();
                return process.waitFor();
            }

            @Override
            protected void done() {
                try {
                    get();
                    data.getTasksModel().set(
                            new Task(rawData.iterator().next().getFileName(),
                                    "Run Genome Annotation", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();

                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                            .setIdentificationsDone();
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                            .setResultsDone();
                    
                    JOptionPane
                            .showConfirmDialog(
                                    AnalyseDynamicTab.getInstance(),
                                    "Your genome annotation run has finished and your result files are now available.\n"
                                    + "Please check the \"annotation_output\" folder where your raw data was situated.\n"
                                    + "Check the output folder for various CSV files and an annotated GFF file.\n"
                                    + "Also check the \"ProteoAnnotator.txt\" file for any error messages and a log of the run.",
                                    "Genome Annotation Completed", JOptionPane.PLAIN_MESSAGE,
                                    JOptionPane.INFORMATION_MESSAGE);
                    
                    String outputMzid = rawData.iterator().next().getFile().getParentFile().getAbsolutePath()
                            + File.separator + "annotation_output" + File.separator
                            + prefix
                            + rawData.iterator().next().getFileName().replaceAll(".[Mm][Gg][Ff]", StringUtils.emptyString())
                            + "_fdr_peptide_threshold_mappedGff2_proteoGrouper_nonA_Threshold_FDR_Threshold.mzid";
                    
                    File mzidFile = new File(outputMzid);
                    if (mzidFile.exists()) {
                        data.getInspectModel().addIdentDataFile(new MzIdentMLFile(mzidFile, null));
                    }
                    
                    System.out.println("Done!");
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };

        data.getSearchGUIExecutor().submit(worker);
    }  

    public void printDebugInfo() {
        System.out.println("This JAR found is: " + getMzIdLibJar());
    }

    private static File getMzIdLibJar() {
        File thisClassFile = new File(SearchGuiViaMzidLibWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (thisClassFile.getAbsolutePath().endsWith("classes")) {
            return new File("c:\\mzidlib\\mzidentml-lib-1.6.10-SNAPSHOT.jar");
        }

        File mzidlibFolder = new File(thisClassFile.getParent() + File.separator + "mzidlib");

        File[] potentialJars = mzidlibFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".jar");
            }
        });

        return potentialJars[0];
    }
}
