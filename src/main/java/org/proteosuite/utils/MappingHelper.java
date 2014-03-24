package org.proteosuite.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.xml.bind.JAXBException;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.Task;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;
import uk.ac.liv.mzqlib.idmapper.MzqMzIdMapper;
import uk.ac.liv.mzqlib.idmapper.MzqMzIdMapperFactory;

/**
 *
 * @author SPerkins
 */
public class MappingHelper {    

    private static String outputFile;
    
    private MappingHelper() {}

    public static void map(final QuantDataFile quantData) {

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingProcessing();
                AnalyseData.getInstance().getTasksModel().set(new Task(quantData.getFileName(), "Mapping Identifications"));
                TasksTab.getInstance().refreshFromTasksModel();
                outputFile = quantData.getAbsoluteFileName().replace(".mzq", "_mapped.mzq");
                Map<String, String> rawToMzidMap = new HashMap<String, String>();
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

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();                  
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setMappingDone();
                    
                    AnalyseData.getInstance().getTasksModel().set(new Task(quantData.getFileName(), "Mapping Identifications", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                    
                    ProteinInferenceHelper.infer(outputFile, "Label-free", ProteinInferenceHelper.LCMS_FEATURE_INTENSITY, "sum");                    
                    
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(MappingHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        AnalyseData.getInstance().getExecutor().submit(worker);
    }
}
