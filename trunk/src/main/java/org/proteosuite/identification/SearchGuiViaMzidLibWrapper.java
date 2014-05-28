package org.proteosuite.identification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
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
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MascotGenericFormatFile;
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

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParamters, boolean doProteoGrouper, boolean doEmPAI) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix) {
        this.genomeAnnotation = true;
        this.rawData = inputSpectra;

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

        if (prefix != null && !prefix.isEmpty()) {
            commandList.add("-prefix");
            commandList.add(prefix);
        }

        if (!searchParameters.isEmpty()) {
            commandList.add("-searchParameters");
            StringBuilder parametersBuilder = new StringBuilder();
            parametersBuilder.append("\"");
            Iterator<Entry<String, String>> iterator = searchParameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> searchEntry = iterator.next();
                if (searchEntry.getKey().toUpperCase().contains("_MODS")) {
                    parametersBuilder.append("-").append(searchEntry.getKey()).append(StringUtils.space()).append("\\\"").append(searchEntry.getValue()).append("\\\"");
                } else {
                    parametersBuilder.append("-").append(searchEntry.getKey()).append(StringUtils.space()).append(searchEntry.getValue());
                }

                if (iterator.hasNext()) {
                    parametersBuilder.append(StringUtils.space());
                }
            }

            parametersBuilder.append("\"");

            commandList.add(parametersBuilder.toString());
        }

        commandList.add("-compress");
        commandList.add("false");
    }

    public void compute() {
        if (this.genomeAnnotation) {
            this.computeGenomeAnnotation();
        }
    }

    private void computeGenomeAnnotation() {
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
                reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                processBufferedReader(reader);

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                processBufferedReader(reader);
                //data.getLogReaders().add(reader);
                //TasksTab.getInstance().refreshFromTasksModel();                
                return process.waitFor();
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane
                            .showConfirmDialog(
                                    AnalyseDynamicTab.getInstance(),
                                    "Your genome annotation run has finished and your result files are now available.\n"
                                    + "Please check the \"annotation_output\" folder where your raw data was situated.\n"
                                    + "Check the output folder for various CSV files and an annotated GFF file.",
                                    "Genome Annotation Completed", JOptionPane.PLAIN_MESSAGE,
                                    JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Done!");
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };

        data.getSearchGUIExecutor().submit(worker);
    }

    private void processBufferedReader(final BufferedReader reader) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println("Problem reading output/error stream: " + ex.getLocalizedMessage());
                }
            }
        };

        worker.execute();
    }

    public void printDebugInfo() {
        System.out.println("This JAR found is: " + getMzIdLibJar());
    }

    private static File getMzIdLibJar() {
//        return new File("c:\\mzidlib\\TestProcessBuilder-1.0-SNAPSHOT.jar");

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
