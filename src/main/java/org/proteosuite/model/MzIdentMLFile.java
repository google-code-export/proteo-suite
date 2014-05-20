package org.proteosuite.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.utils.StringUtils;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationProtocol;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class MzIdentMLFile extends IdentDataFile {

    private MzIdentMLUnmarshaller unmarshaller;

    public MzIdentMLFile(File file, RawDataFile parent) {
        super(file, parent);
    }

    @Override
    public String getFormat() {
        return "mzIdentML";
    }

    @Override
    public boolean isLoaded() {
        if (file == null) {
            return false;
        }

        return true;
    }

    @Override
    public int getPSMCountPassingThreshold() {
        return this.psmCountPassingThreshold;
    }

    @Override
    public int getPSMCountNotPassingThreshold() {
        return this.psmCountNotPassingThrehsold;
    }

    @Override
    public int getPeptideCountPassingThreshold() {
        return this.peptideCountPassingThreshold;
    }

    @Override
    public String getThresholdingUsed() {
        return this.thresholdingUsed;
    }
    
    @Override
    public Map<String, String> getThresholdables() {
        return this.thresholdables;
    }

    @Override
    protected synchronized void computePSMStats() {
        SwingWorker<String[], Void> worker = new SwingWorker<String[], Void>() {
            @Override
            protected String[] doInBackground() {                
                SpectrumIdentificationProtocol protocol = unmarshaller.unmarshal(MzIdentMLElement.SpectrumIdentificationProtocol);
                List<CvParam> thresholdingParams = protocol.getThreshold().getCvParam();
                List<String> thresholdTerms = new ArrayList<>();
                for (CvParam param : thresholdingParams) {
                    thresholdTerms.add(param.getName());
                }
                
                String thresholding = StringUtils.join(", ", thresholdTerms);
                
                int psmPassingThreshold = 0;
                int psmNotPassingThreshold = 0;
                Map<String, String> thresholdTypes = new HashMap<>();
                Set<String> peptidesPassingThreshold = new HashSet<>();
                if (unmarshaller != null) {
                    Iterator<SpectrumIdentificationResult> spectrumIdentificationResultIterator = unmarshaller.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationResult);
                    while (spectrumIdentificationResultIterator.hasNext()) {
                        SpectrumIdentificationResult result = spectrumIdentificationResultIterator.next();
                        for (SpectrumIdentificationItem item : result.getSpectrumIdentificationItem()) {
                            if (item.isPassThreshold()) {
                                psmPassingThreshold++;
                                peptidesPassingThreshold.add(item.getPeptideRef());
                            } else {
                                psmNotPassingThreshold++;
                            }
                            
                            for (CvParam param : item.getCvParam()) {
                                if (param.getName().toUpperCase().contains("SCORE") || param.getName().toUpperCase().contains("VALUE")) {
                                    thresholdTypes.put(param.getName(), param.getAccession());
                                }
                            }
                        }
                    }
                }
                
                // Need to pack threshold types as a string.
                StringBuilder thresholdsBuilder = new StringBuilder();
                for (Entry<String, String> entry : thresholdTypes.entrySet()) {
                    thresholdsBuilder.append(entry.getKey());
                    thresholdsBuilder.append(",");
                    thresholdsBuilder.append(entry.getValue());
                    thresholdsBuilder.append(";");
                }                

                return new String[]{String.valueOf(psmPassingThreshold), String.valueOf(psmNotPassingThreshold),
                    String.valueOf(peptidesPassingThreshold.size()), thresholding, thresholdsBuilder.toString()};
            }

            @Override
            protected void done() {
                try {
                    String[] computationResult = get();
                    psmCountPassingThreshold = Integer.parseInt(computationResult[0]);
                    psmCountNotPassingThrehsold = Integer.parseInt(computationResult[1]);
                    peptideCountPassingThreshold = Integer.parseInt(computationResult[2]);
                    thresholdingUsed = computationResult[3];
                    String thresholdablesPacked = computationResult[4];
                    String[] thresholdablesEntriesUnpacked = thresholdablesPacked.split(";");
                    for (String thresholdableEntry : thresholdablesEntriesUnpacked) {
                        String[] thresholdableEntryUnpacked = thresholdableEntry.split(",");
                        thresholdables.put(thresholdableEntryUnpacked[0], thresholdableEntryUnpacked[1]);
                    }
                    
                    computedPSMStats = true;

                    ((CleanIdentificationsStep) AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP).refreshFromData();

                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };

        AnalyseData.getInstance().getGenericExecutor().submit(worker);
    }

    @Override
    protected void initiateLoading() {
        AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Loading Identifications"));
        TasksTab.getInstance().refreshFromTasksModel();

        MzIdentMLFile.this.getParent().setIdentStatus("Loading...");
        ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();

        ExecutorService executor = AnalyseData.getInstance().getGenericExecutor();
        SwingWorker<MzIdentMLUnmarshaller, Void> mzIdentMLWorker = new SwingWorker<MzIdentMLUnmarshaller, Void>() {
            @Override
            protected MzIdentMLUnmarshaller doInBackground() {
                return new MzIdentMLUnmarshaller(file);
            }

            @Override
            protected void done() {
                try {
                    unmarshaller = get();
                    AnalyseData.getInstance().getInspectModel().addIdentDataFile(MzIdentMLFile.this);
                    InspectTab.getInstance().refreshComboBox();

                    AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Loading Identifications", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();

                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateIdentificationsStatus();

                    MzIdentMLFile.this.getParent().setIdentStatus("Done");
                    ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();

                    computePSMStats();

                    System.out.println("Done loading mzIdentML file.");
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted exception: " + ex.getLocalizedMessage());
                } catch (ExecutionException ex) {
                    System.out.println("Execution exception: " + ex.getLocalizedMessage());
                }
            }
        };

        executor.submit(mzIdentMLWorker);
    }
}
