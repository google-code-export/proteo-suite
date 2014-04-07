package org.proteosuite.model;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CleanIdentificationsStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class MzIdentMLFile extends IdentDataFile {

    private MzIdentMLUnmarshaller unmarshaller;

    public MzIdentMLFile(File file) {
        super(file);
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
        return "";
    }

    @Override
    protected synchronized void computePSMStats() {
        SwingWorker<int[], Void> worker = new SwingWorker<int[], Void>() {
            @Override
            protected int[] doInBackground() {
                int psmPassingThreshold = 0;
                int psmNotPassingThreshold = 0;
                Set<String> peptidesPassingThreshold = new HashSet<String>();
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
                        }
                    }
                }

                return new int[]{psmPassingThreshold, psmNotPassingThreshold, peptidesPassingThreshold.size()};
            }

            @Override
            protected void done() {
                try {
                    int[] computationResult = get();
                    psmCountPassingThreshold = computationResult[0];
                    psmCountNotPassingThrehsold = computationResult[1];
                    peptideCountPassingThreshold = computationResult[2];                    
                    computedPSMStats = true;
                    
                    ((CleanIdentificationsStep)AnalyseDynamicTab.CLEAN_IDENTIFICATIONS_STEP).refreshFromData();
                    
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        };
        
        AnalyseData.getInstance().getExecutor().submit(worker);
    }

    @Override
    protected void initiateLoading() {
        AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Loading Identifications"));
        TasksTab.getInstance().refreshFromTasksModel();

        ExecutorService executor = AnalyseData.getInstance().getExecutor();
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
