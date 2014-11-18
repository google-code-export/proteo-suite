package org.proteosuite.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;
import uk.ac.liv.mzqlib.idmapper.MzqMzIdMapper;
import uk.ac.liv.mzqlib.idmapper.MzqMzIdMapperFactory;

/**
 *
 * @author SPerkins
 */
public class MappingHelper {   
    private MappingHelper() {}
    public static void map(final QuantDataFile quantData) {     
        
        final BackgroundTask task = new BackgroundTask(quantData, "Mapping Identifications");
        
        task.addAsynchronousProcessingAction((ProteoSuiteAction<Object, BackgroundTaskSubject>) (BackgroundTaskSubject ignored) -> {
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingProcessing();
            String outputFile = quantData.getAbsoluteFileName().replace(".mzq", "_mapped.mzq");
            
            Map<String, String> rawToMzidMap = new HashMap<>();
            for (int i = 0; i < AnalyseData.getInstance().getRawDataCount(); i++) {
                RawDataFile rawData = AnalyseData.getInstance().getRawDataFile(i);
                IdentDataFile identData = rawData.getIdentificationDataFile();
                if (identData != null) {
                    rawToMzidMap.put(rawData.getAbsoluteFileName(), identData.getAbsoluteFileName());
                }
            }
            
            try {
                MzQuantMLUnmarshaller umarsh = new MzQuantMLUnmarshaller(quantData.getFile());
                MzqMzIdMapper mapper = MzqMzIdMapperFactory.getInstance().buildMzqMzIdMapper(umarsh, rawToMzidMap);
                mapper.createMappedFile(outputFile);
            } catch (JAXBException ex) {
                
                System.out.println(ex.getLocalizedMessage());
            }
            
            return outputFile;
        });
        
        task.addCompletionAction((ProteoSuiteAction<Object, BackgroundTaskSubject>) (BackgroundTaskSubject argument) -> {
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingDone();
            String outputFile = task.getResultOfClass(String.class);
            
            NormalisationHelper.normalise(new MzQuantMLFile(new File(outputFile)));
            return null;
        });       
        
        BackgroundTaskManager.getInstance().getTasksOfType("Create Identifications").stream().map(p -> p.getTaskLatch()).forEach((latch) -> {
            task.addProcessingCondition(latch);
        });

        BackgroundTaskManager.getInstance().submit(task);        
    }
}