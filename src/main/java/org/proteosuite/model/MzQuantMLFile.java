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
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class MzQuantMLFile extends QuantDataFile {
    private MzQuantMLUnmarshaller unmarshaller = null;
    public MzQuantMLFile(File file) {
        super(file);
    }
    
    @Override
    public boolean isLoaded() {
        if (unmarshaller != null) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void initiateLoading() {
        AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Load Quantitation Data"));
        TasksTab.getInstance().refreshFromTasksModel();
        
        ExecutorService executor = AnalyseData.getInstance().getExecutor();
        
        SwingWorker<MzQuantMLUnmarshaller, Void> mzMLWorker = new SwingWorker<MzQuantMLUnmarshaller, Void>() {
            @Override
            protected MzQuantMLUnmarshaller doInBackground() {
                return new MzQuantMLUnmarshaller(file);
            }

            @Override
            protected void done() {
                try {
                    unmarshaller = get();
                    AnalyseData.getInstance().getInspectModel().addQuantDataFile(MzQuantMLFile.this);
                    InspectTab.getInstance().refreshComboBox();
                    AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Load Quantitation Data", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateRawDataStatus();
                    
                    System.out.println("Done loading mzQuantML file.");
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted exception loading mzQuantML file: " + ex.getLocalizedMessage());
                } catch (ExecutionException ex) {
                    System.out.println("Execution exception loading mzQuantML file: " + ex.getLocalizedMessage());
                }
            }
        };

        executor.submit(mzMLWorker);
    }
}
