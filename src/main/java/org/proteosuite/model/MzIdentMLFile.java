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
import org.proteosuite.actions.IdentFilePostLoadAction;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.utils.NumericalUtils;
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
    public synchronized void computePSMStats() {
        final BackgroundTask task = new BackgroundTask(this, "Compute PSM Stats");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<String[], BackgroundTaskSubject>() {
            @Override
            public String[] act(BackgroundTaskSubject ignored) {
                SpectrumIdentificationProtocol protocol = unmarshaller.unmarshal(MzIdentMLElement.SpectrumIdentificationProtocol);
                List<CvParam> thresholdingParams = protocol.getThreshold().getCvParam();
                List<String> thresholdTerms = new ArrayList<>();
                for (CvParam param : thresholdingParams) {
                    if (param.getValue() == null || param.getValue().isEmpty()) {
                        thresholdTerms.add(param.getName());
                    } else {
                    thresholdTerms.add(param.getName() + "(" + param.getValue() + ")");}
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
                                if (NumericalUtils.isDouble(param.getValue())) {
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
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                String[] computationResult = task.getResultOfClass(String[].class);
                if (computationResult == null || computationResult[0] == null) {
                    System.out.println("PSM Stats Calculator Did Not Run Properly");
                }
                
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
                
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);       
    }

    @Override
    protected void initiateLoading() {
        final BackgroundTask task = new BackgroundTask(this, "Loading Identifications");

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<MzIdentMLUnmarshaller, BackgroundTaskSubject>() {
            @Override
            public MzIdentMLUnmarshaller act(BackgroundTaskSubject argument) {
                if (MzIdentMLFile.this.getParent() != null) {
                    MzIdentMLFile.this.getParent().setIdentStatus("Loading...");
                    ((CreateOrLoadIdentificationsStep) (AnalyseDynamicTab.CREATE_OR_LOAD_IDENTIFICATIONS_STEP)).refreshFromData();
                }

                return new MzIdentMLUnmarshaller(file);
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                unmarshaller = task.getResultOfClass(MzIdentMLUnmarshaller.class);
                return null;
            }
        });

        task.addCompletionAction(new IdentFilePostLoadAction());

        BackgroundTaskManager.getInstance().submit(task);
    }

    public MzIdentMLUnmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    @Override
    public String getSubjectName() {
        return this.getFileName();
    }
}
