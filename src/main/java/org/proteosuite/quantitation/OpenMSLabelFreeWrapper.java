package org.proteosuite.quantitation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.executor.Executor;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.MappingHelper;
import uk.ac.liv.mzqlib.consensusxml.convertor.ConsensusXMLProcessor;
import uk.ac.liv.mzqlib.consensusxml.convertor.ConsensusXMLProcessorFactory;

/**
 *
 * @author SPerkins
 */
public class OpenMSLabelFreeWrapper {

    private final List<RawDataFile> rawDataFiles;
    private final CountDownLatch featureFinderCentroidedLatch;
    private final CountDownLatch mapAlignerPoseClusteringLatch;
    private final CountDownLatch featureLinkerUnlabeledQTLatch;
    private static String systemExecutableExtension;

    public static boolean checkIsInstalled() {
        Executor exec = new Executor("FeatureFinderCentroided");
        exec.callExe();
        String output = exec.getOutput();

        if (output != null && output.contains("No options given. Aborting!")) {
            return true;
        }

        exec = new Executor("FeatureFinderCentroided.exe");
        exec.callExe();
        output = exec.getOutput();
        if (output != null && output.contains("No options given. Aborting!")) {
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
            String linuxVersion = org.proteosuite.jopenms.util.Utils
                    .getLinuxVersion();
            if (linuxVersion.contains("x86_64")
                    || linuxVersion.contains("ia64")) {

            } else {

            }
        }

        this.rawDataFiles = rawDataFiles;
        featureFinderCentroidedLatch = new CountDownLatch(rawDataFiles.size());
        mapAlignerPoseClusteringLatch = new CountDownLatch(1);
        featureLinkerUnlabeledQTLatch = new CountDownLatch(1);
    }

    public void compute() {
        AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                .setQuantitationProcessing();

        List<String> featureFinderCentroidedFiles = new ArrayList<>();
        List<String> mapAlignerPoseClusteringFiles = new ArrayList<>();
        int featureFinderExecutionDelay = 0;
        for (RawDataFile dataFile : rawDataFiles) {
            String featureFinderCentroidedFile = dataFile.getAbsoluteFileName()
                    .replaceAll("\\." + dataFile.getFormat() + '$',
                            "_FFC.featureXML");
            String mapAlignerPostClusteringFile = featureFinderCentroidedFile
                    .replaceAll("\\." + "featureXML" + '$', "_MAPC.featureXML");
            doFeatureFinderCentroided(dataFile, featureFinderCentroidedFile,
                    featureFinderCentroidedLatch, featureFinderExecutionDelay);
            featureFinderCentroidedFiles.add(featureFinderCentroidedFile);
            mapAlignerPoseClusteringFiles.add(mapAlignerPostClusteringFile);
            featureFinderExecutionDelay += 5;
        }

        String unlabeledOutputFile = rawDataFiles.get(0).getFile().getParent()
                + "\\unlabeled_result_FLUQT.consensusXML";
        String mzQuantMLFile = unlabeledOutputFile.replace(".consensusXML",
                ".mzq");

        doMapAlignerPoseClustering(featureFinderCentroidedFiles,
                mapAlignerPoseClusteringFiles, featureFinderCentroidedLatch,
                mapAlignerPoseClusteringLatch);

        doFeatureLinkerUnlabeledQT(mapAlignerPoseClusteringFiles,
                unlabeledOutputFile, mapAlignerPoseClusteringLatch,
                featureLinkerUnlabeledQTLatch);

        doConsensusXMLToMzQuantMLConversion(unlabeledOutputFile, mzQuantMLFile,
                featureLinkerUnlabeledQTLatch);
    }

    private void doFeatureFinderCentroided(final RawDataFile inputDataFile,
            final String outputFile,
            final CountDownLatch featureFinderCentroidedLatch,
            final int executionDelay) {

        BackgroundTask task = new BackgroundTask(inputDataFile, "Finding Features");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                try {
                    Thread.sleep(executionDelay * 1000);
                    JOpenMS.performOpenMSTask(systemExecutableExtension,
                            "FeatureFinderCentroided",
                            Arrays.asList(inputDataFile.getAbsoluteFileName()),
                            Arrays.asList(outputFile));
                } catch (InterruptedException ex) {
                    Logger.getLogger(OpenMSLabelFreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                featureFinderCentroidedLatch.countDown();
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doMapAlignerPoseClustering(final List<String> inputFiles,
            final List<String> outputFiles,
            final CountDownLatch featureFinderCentroidedLatch,
            final CountDownLatch mapAlignerPoseClusteringLatch) {

        BackgroundTaskSubject subject = new BackgroundTaskSubject() {
            private final File file = new File(inputFiles.get(0));

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask task = new BackgroundTask(subject, "Aligning Features");

        task.addProcessingCondition(featureFinderCentroidedLatch);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                JOpenMS.performOpenMSTask(systemExecutableExtension,
                        "MapAlignerPoseClustering", inputFiles, outputFiles);

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                mapAlignerPoseClusteringLatch.countDown();
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doFeatureLinkerUnlabeledQT(final List<String> inputFiles,
            final String outputFile,
            final CountDownLatch mapAlignerPoseClusteringLatch,
            final CountDownLatch featureLinkerUnlabeledQTLatch) {

        BackgroundTaskSubject subject = new BackgroundTaskSubject() {
            private final File file = new File(inputFiles.get(0));

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask task = new BackgroundTask(subject, "Linking Features");

        task.addProcessingCondition(mapAlignerPoseClusteringLatch);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                JOpenMS.performOpenMSTask(systemExecutableExtension,
                        "FeatureLinkerUnlabeledQT", inputFiles,
                        Arrays.asList(outputFile));

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, Void>() {

            @Override
            public Void act(Void argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setQuantitationDone();

                featureLinkerUnlabeledQTLatch.countDown();
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doConsensusXMLToMzQuantMLConversion(
            final String consensusXMLFile, final String mzqFile,
            final CountDownLatch featureLinkerUnlabeledQTLatch) {

        BackgroundTaskSubject subject = new BackgroundTaskSubject() {
            private final File file = new File(consensusXMLFile);

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask task = new BackgroundTask(subject, "Converting to mzQuantML");
        task.addProcessingCondition(featureLinkerUnlabeledQTLatch);
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                try {                    
                    ConsensusXMLProcessor conProc = ConsensusXMLProcessorFactory
                            .getInstance().buildConsensusXMLProcessor(
                                    new File(consensusXMLFile));
                    conProc.convert(mzqFile);
                } catch (IOException | JAXBException ex) {
                    Logger.getLogger(OpenMSLabelFreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
                }

                return null;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, Void>() {
            @Override
            public Void act(Void argument) {
                QuantDataFile quantFile = new MzQuantMLFile(new File(
                        mzqFile));
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setQuantitationDone();
                MappingHelper.map(quantFile);
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
