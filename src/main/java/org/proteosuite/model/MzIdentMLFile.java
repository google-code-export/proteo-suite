/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.model;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
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
