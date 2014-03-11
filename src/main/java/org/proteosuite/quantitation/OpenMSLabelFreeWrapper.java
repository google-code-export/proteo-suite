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
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class OpenMSLabelFreeWrapper {
    private ExecutorService executor;
    private List<RawDataFile> rawDataFiles;
    private CountDownLatch featureFinderCentroidedLatch;
    private CountDownLatch mapAlignerPoseClusteringLatch;

    public OpenMSLabelFreeWrapper(List<RawDataFile> rawDataFiles) {
        this.executor = AnalyseData.getInstance().getExecutor();
        this.rawDataFiles = rawDataFiles;
        featureFinderCentroidedLatch = new CountDownLatch(rawDataFiles.size());
        mapAlignerPoseClusteringLatch = new CountDownLatch(1);
    }

    public void compute() {
        List<String> featureFinderCentroidedFiles = new ArrayList<String>();  
        List<String> mapAlignerPoseClusteringFiles = new ArrayList<String>();
        for (RawDataFile dataFile : rawDataFiles) {
            String featureFinderCentroidedFile = dataFile.getAbsoluteFileName().replaceAll("\\." + dataFile.getFormat() + '$', "_FFC.featureXML");
            String mapAlignerPostClusteringFile = featureFinderCentroidedFile.replaceAll("\\." + "featureXML" + '$', "_MAPC.featureXML");
            doFeatureFinderCentroided(dataFile, featureFinderCentroidedFile, featureFinderCentroidedLatch);
            featureFinderCentroidedFiles.add(featureFinderCentroidedFile);
            mapAlignerPoseClusteringFiles.add(mapAlignerPostClusteringFile);            
        }
        
        String unlabledOutputFile = rawDataFiles.get(0).getFile().getParent() + "\\unlabeled_result_FLUQT.consensusXML";
        
        doMapAlignerPoseClustering(featureFinderCentroidedFiles, mapAlignerPoseClusteringFiles, featureFinderCentroidedLatch, mapAlignerPoseClusteringLatch);        
        
        doFeatureLinkerUnlabeledQT(mapAlignerPoseClusteringFiles, unlabledOutputFile, mapAlignerPoseClusteringLatch);
    }

    private void doFeatureFinderCentroided(final RawDataFile inputDataFile, final String outputFile, final CountDownLatch featureFinderCentroidedLatch) {
        
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            public Void doInBackground() {
                try {
                JOpenMS.performOpenMSTask("FeatureFinderCentroided", Arrays.asList(inputDataFile.getAbsoluteFileName()), Arrays.asList(outputFile));
                
                featureFinderCentroidedLatch.countDown();} catch (NullPointerException n) {
                    n.printStackTrace();
                }
                return null;
            }
            
            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                } catch (ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };
        
        executor.submit(worker);
    }

    private void doMapAlignerPoseClustering(final List<String> inputFiles, final List<String> outputFiles, final CountDownLatch featureFinderCentroidedLatch, final CountDownLatch mapAlignerPoseClusteringLatch) {

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
        };
        
        executor.submit(worker);
    }

    private void doFeatureLinkerUnlabeledQT(final List<String> inputFiles, final String outputFile, final CountDownLatch mapAlignerPoseClusteringLatch) {

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
        };
        
        executor.submit(worker);
    }
}
