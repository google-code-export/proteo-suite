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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import javax.xml.bind.JAXBException;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.utils.MappingHelper;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.Task;
import org.proteosuite.utils.CommandExecutor;
import org.proteosuite.utils.FileUtils;
import uk.ac.liv.mzqlib.consensusxml.convertor.ConsensusXMLProcessor;
import uk.ac.liv.mzqlib.consensusxml.convertor.ConsensusXMLProcessorFactory;

/**
 *
 * @author SPerkins
 */
public class OpenMSLabelFreeWrapper {

    private final ExecutorService executor;
    private final List<RawDataFile> rawDataFiles;
    private final CountDownLatch featureFinderCentroidedLatch;
    private final CountDownLatch mapAlignerPoseClusteringLatch;
    private final CountDownLatch featureLinkerUnlabeledQTLatch;
    private static String systemExecutableExtension;

    public static boolean checkIsInstalled() {
        if (CommandExecutor.outputContains("FeatureFinderCentroided", "No options given. Aborting!")) {
            return true;
        }
        
        if (CommandExecutor.outputContains("FeatureFinderCentroided.exe", "No options given. Aborting!")) {
            return true;
        }      
        
        return false;
    }

    public OpenMSLabelFreeWrapper(List<RawDataFile> rawDataFiles) {
        String operatingSystemType = System.getProperty("os.name");

        if (operatingSystemType.startsWith("Windows")) {
            systemExecutableExtension = ".exe";
            if (System.getenv("ProgramFiles(x86)") != null) {

            } else {

            }
        } else {
            systemExecutableExtension = "";
            String linuxVersion = org.proteosuite.jopenms.util.Utils.getLinuxVersion();
            if (linuxVersion.contains("x86_64") || linuxVersion.contains("ia64")) {

            } else {

            }
        }

        this.executor = AnalyseData.getInstance().getExecutor();
        this.rawDataFiles = rawDataFiles;
        featureFinderCentroidedLatch = new CountDownLatch(rawDataFiles.size());
        mapAlignerPoseClusteringLatch = new CountDownLatch(1);
        featureLinkerUnlabeledQTLatch = new CountDownLatch(1);
    }

    public void compute() {
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationProcessing();

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

        String unlabeledOutputFile = rawDataFiles.get(0).getFile().getParent() + "\\unlabeled_result_FLUQT.consensusXML";
        String mzQuantMLFile = unlabeledOutputFile.replace(".consensusXML", ".mzq");

        doMapAlignerPoseClustering(featureFinderCentroidedFiles, mapAlignerPoseClusteringFiles, featureFinderCentroidedLatch, mapAlignerPoseClusteringLatch);

        doFeatureLinkerUnlabeledQT(mapAlignerPoseClusteringFiles, unlabeledOutputFile, mapAlignerPoseClusteringLatch, featureLinkerUnlabeledQTLatch);

        doConsensusXMLToMzQuantMLConversion(unlabeledOutputFile, mzQuantMLFile, featureLinkerUnlabeledQTLatch);
    }

    private void doFeatureFinderCentroided(final RawDataFile inputDataFile, final String outputFile, final CountDownLatch featureFinderCentroidedLatch, final int executionDelay) {
        AnalyseData.getInstance().getTasksModel().set(new Task(inputDataFile.getFileName(), "Finding Features"));
        TasksTab.getInstance().refreshFromTasksModel();

        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    Thread.sleep(executionDelay * 1000);
                    JOpenMS.performOpenMSTask(systemExecutableExtension, "FeatureFinderCentroided", Arrays.asList(inputDataFile.getAbsoluteFileName()), Arrays.asList(outputFile));

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
        AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFiles.get(0)), "Aligning Features"));
        TasksTab.getInstance().refreshFromTasksModel();
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    featureFinderCentroidedLatch.await();
                    JOpenMS.performOpenMSTask(systemExecutableExtension, "MapAlignerPoseClustering", inputFiles, outputFiles);
                    mapAlignerPoseClusteringLatch.countDown();
                } catch (InterruptedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFiles.get(0)), "Aligning Features", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };

        executor.submit(worker);
    }

    private void doFeatureLinkerUnlabeledQT(final List<String> inputFiles, final String outputFile, final CountDownLatch mapAlignerPoseClusteringLatch, final CountDownLatch featureLinkerUnlabeledQTLatch) {
        AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFiles.get(0)), "Linking Features"));
        TasksTab.getInstance().refreshFromTasksModel();
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                try {
                    mapAlignerPoseClusteringLatch.await();
                    JOpenMS.performOpenMSTask(systemExecutableExtension, "FeatureLinkerUnlabeledQT", inputFiles, Arrays.asList(outputFile));
                } catch (InterruptedException ex) {

                }

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getTasksModel().set(new Task(FileUtils.getFileNameFromFullPath(inputFiles.get(0)), "Linking Features", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();

                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationDone();

                    featureLinkerUnlabeledQTLatch.countDown();

                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };

        executor.submit(worker);
    }

    private void doConsensusXMLToMzQuantMLConversion(final String consensusXMLFile, final String mzqFile, final CountDownLatch featureLinkerUnlabeledQTLatch) {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() {
                
                try {
                    featureLinkerUnlabeledQTLatch.await();
                    try {
                        ConsensusXMLProcessor conProc = ConsensusXMLProcessorFactory.getInstance().buildConsensusXMLProcessor(new File(consensusXMLFile));
                        conProc.convert(mzqFile);
                    } catch (IOException | JAXBException ex) {                        
                        System.out.println(ex.getLocalizedMessage());
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }               

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    QuantDataFile quantFile = new MzQuantMLFile(new File(mzqFile));                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setQuantitationDone();
                    MappingHelper.map(quantFile);
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };
        
        executor.submit(worker);
    }
}
