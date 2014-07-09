

package org.proteosuite.identification;

import javax.swing.SwingWorker;
import org.proteosuite.gui.listener.ThresholdCompletionListener;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.DependingActionRegister;
import org.proteosuite.model.IdentDataFile;
import uk.ac.liv.mzidlib.ThresholdMzid;

/**
 *
 * @author SPerkins
 */
public class ThresholdWrapper {
    private static AnalyseData data = AnalyseData.getInstance();
    private final IdentDataFile dataFile;
    private final String thresholdOn;
    private final double thresholdValue;
    private final boolean higherValuesBetter;
    private final DependingActionRegister<ThresholdWrapper> actions;
    private String outputPath;
    public ThresholdWrapper(IdentDataFile dataFile, String thresholdOn, double thresholdValue, boolean higherValuesBetter) {
        this.dataFile = dataFile;
        this.thresholdOn = thresholdOn;
        this.thresholdValue = thresholdValue;
        this.higherValuesBetter = higherValuesBetter;
        actions = new DependingActionRegister<>(this);
    }
    
    public IdentDataFile getOriginalIdentData() {
        return this.dataFile;
    }
    
    public String getThresholdedOutputPath() {
        return this.outputPath;
    }
    
    public void doThresholding() {
       actions.add(new ThresholdCompletionListener());
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                outputPath = dataFile.getAbsoluteFileName().replace(".mzid", "_thresholded.mzid");
                new ThresholdMzid(dataFile.getAbsoluteFileName(), outputPath, true, thresholdOn, thresholdValue, !ThresholdWrapper.this.higherValuesBetter, false);
                return null;
            }
            
            @Override
            protected void done() {
                actions.fireDependingActions();
            }        
        };
        
        data.getGenericExecutor().submit(worker);
    }
}