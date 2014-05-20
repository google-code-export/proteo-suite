

package org.proteosuite.identification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.utils.FileFormatUtils;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {    
    private List<String> commandList;
    private BufferedReader reader;
    
    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParamters, boolean doProteoGrouper, boolean doEmPAI) {
        throw new UnsupportedOperationException("Not supported yet.");    
    }
    
    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters, String prefix) {
        // First thing we need to do is merge our MGF files together.
        MascotGenericFormatFile mgf;
        if (inputSpectra.size() > 1) {
            mgf = FileFormatUtils.merge(inputSpectra);
        } else {
            mgf = inputSpectra.iterator().next();
        }
        
        commandList = new LinkedList<>();
        commandList.add("java");
        commandList.add("memory");
        commandList.add("-jar");
        
        
        commandList.add("ProteoAnnotator");
        commandList.add("-inputGFF");
        commandList.add(geneModel[0]);
        if (geneModel[1] != null && !geneModel[1].isEmpty()) {
            commandList.add("-inputFasta");
            commandList.add(geneModel[1]);
        }
        
        if (mgf != null) {
            commandList.add("-spectrum_files");
            commandList.add(mgf.getAbsoluteFileName());
        }
        
        commandList.add("-outputFolder");
        commandList.add(mgf.getFile().getParentFile().getAbsolutePath() + File.pathSeparator + "annotation_output");
        
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
        
        if (prefix != null || !prefix.isEmpty()) {
            commandList.add("-prefix");
            commandList.add(prefix);
        }        
        
        if (!searchParameters.isEmpty()) {
            commandList.add("-searchParameters");
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
            
            commandList.add(parametersBuilder.toString());           
        }
        
        commandList.add("-compress");
        commandList.add("false");        
    }
    
    public BufferedReader getOutputReader() {
        return reader;
    }
    
    public void compute() {
        try {
            ProcessBuilder builder = new ProcessBuilder(commandList);
            builder.redirectErrorStream(true);
            final Process process = builder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            SwingWorker<Integer, Void> worker = new SwingWorker() {
                @Override
                protected Integer doInBackground() throws Exception {
                    return process.waitFor();
                }
                
                @Override
                protected void done() {
                    try {
                        get();
                        
                        // Do stuff when process has finished.
                    } catch (InterruptedException | ExecutionException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }                   
                }
            };      
            
            worker.execute();
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }       
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
