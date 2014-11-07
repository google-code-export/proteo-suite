package org.proteosuite.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.BackgroundTaskSubject;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.QuantDataFile;
import uk.ac.liv.mzqlib.stats.MzqQLAnova;

/**
 *
 * @author SPerkins
 */
public class AnovaHelper {

    private AnovaHelper() {
    }

    public static void anova(final QuantDataFile quantData) {
        final BackgroundTask task = new BackgroundTask(quantData, "Calculating ANOVA Values");
        task.setInvisibility(true);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Object act(BackgroundTaskSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setAnovaProcessing();
                String outputFile = quantData.getAbsoluteFileName().replace(".mzq", "_anova.mzq");

                MzqQLAnova anova = new MzqQLAnova(quantData.getAbsoluteFileName(), "ProteinGroup", getGroupedAssays(), "MS:1002518");
                try {
                    anova.writeMzQuantMLFile(outputFile);
                } catch (JAXBException ex) {
                    System.out.println("Error writing ANOVA values to mzq file.");
                    Logger.getLogger(AnovaHelper.class.getName()).log(Level.SEVERE, null, ex);
                }

                return outputFile;
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Object act(BackgroundTaskSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setNormalisationDone();
                String outputFile = task.getResultOfClass(String.class);
                
                AnalyseData.getInstance().getInspectModel().addQuantDataFile(new MzQuantMLFile(new File(outputFile)));

                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private static List<List<String>> getGroupedAssays() {
        AnalyseData data = AnalyseData.getInstance();
        Map<String, List<String>> conditionToAssays = new HashMap<>();
        for (int i = 0; i < data.getRawDataCount(); i++) {
            String thisCondition = data.getRawDataFile(0).getConditions().get("");
            if (conditionToAssays.containsKey(thisCondition)) {
                conditionToAssays.get(thisCondition).add("ass_" + i);
            } else {
                conditionToAssays.put(thisCondition, new ArrayList<>(Arrays.asList("ass_" + i)));
            }
        }

        return new ArrayList<>(conditionToAssays.values());
    }
}