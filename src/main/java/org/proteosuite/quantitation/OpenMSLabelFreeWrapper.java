/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.quantitation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.Task;

/**
 *
 * @author SPerkins
 */
public class OpenMSLabelFreeWrapper {
    private ExecutorService executor;
    private List<RawDataFile> rawDataFiles;
    private CountDownLatch featureFinderCentroidedLatch;
    private CountDownLatch mapAlignerPoseClusteringLatch;
    private static String exeLocation;

    public OpenMSLabelFreeWrapper(List<RawDataFile> rawDataFiles) {
        String operatingSystemType = System.getProperty("os.name");
        if (operatingSystemType.startsWith("Windows")) {
            if (System.getenv("ProgramFiles(x86)") != null) {
                exeLocation = getClass().getClassLoader().getResource("lib/openms/win64").getFile();
            } else {
                exeLocation = getClass().getClassLoader().getResource("lib/openms/win32").getFile();
            }
        } else {
            String linuxVersion = org.proteosuite.jopenms.util.Utils.getLinuxVersion();
            if (linuxVersion.contains("x86_64") || linuxVersion.contains("ia64")) {
                exeLocation = getClass().getClassLoader().getResource("lib/openms/linux64").getFile();
            } else {
                exeLocation = getClass().getClassLoader().getResource("lib/openms/linux32").getFile();
            }
        }
        
        this.executor = AnalyseData.getInstance().getExecutor();
        this.rawDataFiles = rawDataFiles;
        featureFinderCentroidedLatch = new CountDownLatch(rawDataFiles.size());
        mapAlignerPoseClusteringLatch = new CountDownLatch(1);
    }

    public void compute() {
        List<String> featureFinderCentroidedFiles = new ArrayList<String>();  
        List<String> mapAlignerPoseClusteringFiles = new ArrayList<String>();
        int featureFinderExecutionDelay = 0;
        for (RawDataFile dataFile : rawDataFiles) {
            String featureFinderCentroidedFile = dataFile.getAbsoluteFileName().replaceAll("\\." + dataFile.getFormat() + '$', "_FFC.featureXML");
            String mapAlignerPostClusteringFile = featureFinderCentroidedFile.replaceAll("\\." + "featureXML" + '$', "_MAPC.featureXML");
            doFeatureFinderCentroided(dataFile, featureFinderCentroidedFile, featureFinderCentroidedLatch, featureFinderExecutionDelay);
            featureFinderCentroidedFiles.add(featureFinderCentroidedFile);
            mapAlignerPoseClusteringFiles.add(mapAlignerPostClusteringFile);            
            featureFinderExecutionDelay += 5;
        }
        
        String unlabledOutputFile = rawDataFiles.get(0).getFile().getParent() + "\\unlabeled_result_FLUQT.consensusXML";
        
        doMapAlignerPoseClustering(featureFinderCentroidedFiles, mapAlignerPoseClusteringFiles, featureFinderCentroidedLatch, mapAlignerPoseClusteringLatch);        
        
        doFeatureLinkerUnlabeledQT(mapAlignerPoseClusteringFiles, unlabledOutputFile, mapAlignerPoseClusteringLatch);
    }

    private void doFeatureFinderCentroided(final RawDataFile inputDataFile, final String outputFile, final CountDownLatch featureFinderCentroidedLatch, final int executionDelay) {
        AnalyseData.getInstance().getTasksModel().set(new Task(inputDataFile.getFileName(), "Finding Features"));
        TasksTab.getInstance().refreshFromTasksModel();
        
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground(){
                try {
                    Thread.sleep(executionDelay * 1000);
                    JOpenMS.performOpenMSTask("FeatureFinderCentroided", Arrays.asList(inputDataFile.getAbsoluteFileName()), Arrays.asList(outputFile));
                
                    featureFinderCentroidedLatch.countDown();
                } catch (NullPointerException | InterruptedException n) {
                    n.printStackTrace();
                }
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getTasksModel().set(new Task(inputDataFile.getFileName(), "Finding Features", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };
        
        executor.submit(worker);
    }

    private void doMapAlignerPoseClustering(final List<String> inputFiles, final List<String> outputFiles, final CountDownLatch featureFinderCentroidedLatch, final CountDownLatch mapAlignerPoseClusteringLatch) {
        AnalyseData.getInstance().getTasksModel().set(new Task(inputFiles.get(0), "Aligning Features"));
        TasksTab.getInstance().refreshFromTasksModel();
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    featureFinderCentroidedLatch.await();
                    JOpenMS.performOpenMSTask("MapAlignerPoseClustering", inputFiles, outputFiles);                    
                    mapAlignerPoseClusteringLatch.countDown();                    
                } catch (InterruptedException ex) {

                }

                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getTasksModel().set(new Task(inputFiles.get(0), "Aligning Features", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }                
            }
        };
        
        executor.submit(worker);
    }

    private void doFeatureLinkerUnlabeledQT(final List<String> inputFiles, final String outputFile, final CountDownLatch mapAlignerPoseClusteringLatch) {
        AnalyseData.getInstance().getTasksModel().set(new Task(inputFiles.get(0), "Linking Features"));
        TasksTab.getInstance().refreshFromTasksModel();
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                mapAlignerPoseClusteringLatch.await();
                JOpenMS.performOpenMSTask("FeatureLinkerUnlabeledQT", inputFiles, Arrays.asList(outputFile));
                } catch (InterruptedException ex) {
                
                }
                
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getTasksModel().set(new Task(inputFiles.get(0), "Linking Features", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };
        
        executor.submit(worker);
    }
}
