package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.ProteoSuiteActionResult;
import uk.ac.liv.mzqlib.stats.MzqQLAnova;

/**
 *
 * @author SPerkins
 */
public class AnovaHelper {

    private AnovaHelper() {
    }

    public static void anova(final File quantDataFile) {
        final BackgroundTask<ProteoSuiteActionSubject> task = new BackgroundTask<>(new ProteoSuiteActionSubject() {
            @Override
            public String getSubjectName() {
                return quantDataFile.getName();
            }
        }, "Calculating ANOVA Values");
        task.setInvisibility(true);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult<String> act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setAnovaProcessing();
                File outputFile = null;
                try {
                    outputFile = new File(quantDataFile.getCanonicalPath().replaceFirst("\\.[Mm][Zz][Qq]$", "_anova.mzq"));
                } catch (IOException ex) {
                    if (outputFile == null) {
                        ProteoSuiteException pex = new ProteoSuiteException("Unable to generate output file for AnovaHelper.", ex);
                        return new ProteoSuiteActionResult(pex);
                    }
                }
                                

                MzqQLAnova anova = new MzqQLAnova(quantDataFile.getAbsolutePath(), "ProteinGroup", getGroupedAssays(), "MS:1002518");
                try {
                    anova.writeMzQuantMLFile(outputFile.getAbsolutePath());
                } catch (JAXBException ex) {                    
                    Logger.getLogger(AnovaHelper.class.getName()).log(Level.SEVERE, null, ex);
                    return new ProteoSuiteActionResult(new ProteoSuiteException("Error writing ANOVA values to mzq file.", ex));
                }

                return new ProteoSuiteActionResult(outputFile);
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
            @Override
            public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setAnovaDone();
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setResultsDone();
                File outputFile = task.getResultOfClass(File.class);
                
                AnalyseData.getInstance().getInspectModel().addQuantDataFile(new MzQuantMLFile(outputFile));

                return ProteoSuiteActionResult.emptyResult();
            }
        });

        BackgroundTaskManager.getInstance().submit(task);
    }

    private static List<List<String>> getGroupedAssays() {
        AnalyseData data = AnalyseData.getInstance();
        Map<String, List<String>> conditionToAssays = new HashMap<>();
        for (int i = 0; i < data.getRawDataCount(); i++) {
            String thisCondition = data.getRawDataFile(i).getConditions().get("");
            if (conditionToAssays.containsKey(thisCondition)) {
                conditionToAssays.get(thisCondition).add("ass_" + i);
            } else {
                conditionToAssays.put(thisCondition, new ArrayList<>(Arrays.asList("ass_" + i)));
            }
        }

        return new ArrayList<>(conditionToAssays.values());
    }
}