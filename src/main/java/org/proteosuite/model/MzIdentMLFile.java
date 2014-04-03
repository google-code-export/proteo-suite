package org.proteosuite.model;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
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
    protected void initiateLoading() {
        AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Loading Identifications"));
        TasksTab.getInstance().refreshFromTasksModel();
        
        ExecutorService executor = AnalyseData.getInstance().getExecutor();
        SwingWorker<Void, Void> mzIdentMLWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {                
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    AnalyseData.getInstance().getInspectModel().addIdentDataFile(MzIdentMLFile.this);
                    InspectTab.getInstance().refreshComboBox();
                    
                    AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Loading Identifications", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateIdentificationsStatus();
                    
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