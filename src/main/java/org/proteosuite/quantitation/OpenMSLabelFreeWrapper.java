package org.proteosuite.quantitation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.executor.Executor;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.MappingHelper;
import org.proteosuite.utils.SystemUtils;
import org.proteosuite.utils.SystemUtils.SystemType;

/**
 *
 * @author SPerkins
 */
public class OpenMSLabelFreeWrapper {
    private static boolean IS_WINDOWS = false;
    private static final List<File> installedLocationHints = new ArrayList<>();
    static {
        installedLocationHints.add(new File("c:\\Program Files\\OpenMS-1.11\\bin"));
        installedLocationHints.add(new File("c:\\Program Files (x86)\\OpenMS-1.11\\bin"));
        installedLocationHints.add(new File("c:\\Program Files"));
        installedLocationHints.add(new File("c:\\Program Files (x86)"));
        installedLocationHints.add(new File("c:"));
    }
    
    private final List<RawDataFile> rawDataFiles;
    private final CountDownLatch featureFinderCentroidedLatch;
    private final CountDownLatch mapAlignerPoseClusteringLatch;
    private final CountDownLatch featureLinkerUnlabeledQTLatch;    

    public static boolean checkIsInstalled() {
        IS_WINDOWS = SystemUtils.getSystemType().equals(SystemType.WIN32) || SystemUtils.getSystemType().equals(SystemType.WIN64);
        File executable = null;
        if (IS_WINDOWS) {
            executable = SystemUtils.findExecutionCommand("FeatureFinderCentroided.exe", installedLocationHints);
        } else {
            executable = SystemUtils.findExecutionCommand("FeatureFinderCentroided", installedLocationHints);
        }
        
        return executable != null;
    }

    public OpenMSLabelFreeWrapper(List<RawDataFile> rawDataFiles) {
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

        BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(inputDataFile, "Finding Features");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                try {
                    Thread.sleep(executionDelay * 1000);
                    String command = "FeatureFinderCentroided" + (IS_WINDOWS ? ".exe" : "");
                    File executable = SystemUtils.findExecutionCommand(command, installedLocationHints);
//                    JOpenMS.performOpenMSTask(executable,
//                            Arrays.asList(inputDataFile.getAbsoluteFileName()),
//                            Arrays.asList(outputFile));
                } catch (InterruptedException ex) {
                    ProteoSuiteException pex = new ProteoSuiteException("Thread sleep error in openMS thread.", ex);
                    Logger.getLogger(OpenMSLabelFreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
                    return new ProteoSuiteActionResult(pex);
                }

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                featureFinderCentroidedLatch.countDown();
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doMapAlignerPoseClustering(final List<String> inputFiles,
            final List<String> outputFiles,
            final CountDownLatch featureFinderCentroidedLatch,
            final CountDownLatch mapAlignerPoseClusteringLatch) {

        ProteoSuiteActionSubject subject = new ProteoSuiteActionSubject() {
            private final File file = new File(inputFiles.get(0));

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(subject, "Aligning Features");

        task.addProcessingCondition(featureFinderCentroidedLatch);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                String command = "MapAlignerPoseClustering" + (IS_WINDOWS ? ".exe" : "");
                File executable = SystemUtils.findExecutionCommand(command, installedLocationHints);
//                JOpenMS.performOpenMSTask(executable, inputFiles, outputFiles);
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                mapAlignerPoseClusteringLatch.countDown();
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doFeatureLinkerUnlabeledQT(final List<String> inputFiles,
            final String outputFile,
            final CountDownLatch mapAlignerPoseClusteringLatch,
            final CountDownLatch featureLinkerUnlabeledQTLatch) {

        ProteoSuiteActionSubject subject = new ProteoSuiteActionSubject() {
            private final File file = new File(inputFiles.get(0));

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(subject, "Linking Features");

        task.addProcessingCondition(mapAlignerPoseClusteringLatch);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                String command = "FeatureLinkerUnlabeledQT" + (IS_WINDOWS ? ".exe" : "");
                File executable = SystemUtils.findExecutionCommand(command, installedLocationHints);
//                JOpenMS.performOpenMSTask(executable, inputFiles,
//                        Arrays.asList(outputFile));

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {

            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setQuantitationDone();

                featureLinkerUnlabeledQTLatch.countDown();
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private void doConsensusXMLToMzQuantMLConversion(
            final String consensusXMLFile, final String mzqFile,
            final CountDownLatch featureLinkerUnlabeledQTLatch) {

        ProteoSuiteActionSubject subject = new ProteoSuiteActionSubject() {
            private final File file = new File(consensusXMLFile);

            @Override
            public String getSubjectName() {
                return file.getName();
            }
        };

        BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(subject, "Converting to mzQuantML");
        task.addProcessingCondition(featureLinkerUnlabeledQTLatch);
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
//                try {                    
//                    ConsensusXMLProcessor conProc = ConsensusXMLProcessorFactory
//                            .getInstance().buildConsensusXMLProcessor(
//                                    new File(consensusXMLFile));
//                    conProc.convert(mzqFile);
//                } catch (IOException | JAXBException ex) {
//                    Logger.getLogger(OpenMSLabelFreeWrapper.class.getName()).log(Level.SEVERE, null, ex);
//                }

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                QuantDataFile quantFile = new MzQuantMLFile(new File(
                        mzqFile));
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setQuantitationDone();
                MappingHelper.map(quantFile);
                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }
}
