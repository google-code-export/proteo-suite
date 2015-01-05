package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.ProteoSuiteActionResult;
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
        
        final BackgroundTask<QuantDataFile> task = new BackgroundTask<>(quantData, "Mapping Identifications");
        
        task.addAsynchronousProcessingAction((ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>) (QuantDataFile ignored) -> {
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingProcessing();
            File outputFile = new File(quantData.getAbsoluteFileName().replaceFirst("\\.[Mm][Zz][Qq]$", "_mapped.mzq"));            
            
            Map<File, File> rawToMzidMap = new HashMap<>();
            for (int i = 0; i < AnalyseData.getInstance().getRawDataCount(); i++) {
                RawDataFile rawData = AnalyseData.getInstance().getRawDataFile(i);
                IdentDataFile identData = rawData.getIdentificationDataFile();
                if (identData != null) {
                    rawToMzidMap.put(rawData.getFile(), identData.getFile());
                }
            }
            
            try {
                MzQuantMLUnmarshaller umarsh = new MzQuantMLUnmarshaller(quantData.getFile());
                MzqMzIdMapper mapper = MzqMzIdMapperFactory.getInstance().buildMzqMzIdMapper(umarsh, rawToMzidMap);
                mapper.createMappedFile(outputFile);
            } catch (IOException | JAXBException ex) {                
                System.out.println(ex.getLocalizedMessage());
                return new ProteoSuiteActionResult(new ProteoSuiteException("Error mapping mzid files to mzq file.", ex));
            }            
            
            return new ProteoSuiteActionResult<>(outputFile);            
        });
        
        task.addCompletionAction((ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>) (QuantDataFile argument) -> {
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingDone();
            File outputFile = task.getResultOfClass(File.class);
            
            NormalisationHelper.normalise(new MzQuantMLFile(outputFile));
            return ProteoSuiteActionResult.emptyResult();
        });       
        
        BackgroundTaskManager.getInstance().getTasksOfType("Create Identifications").stream().map(p -> p.getTaskLatch()).forEach((latch) -> {
            task.addProcessingCondition(latch);
        });

        BackgroundTaskManager.getInstance().submit(task);        
    }
}