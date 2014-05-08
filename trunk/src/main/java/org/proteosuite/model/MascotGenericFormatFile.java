/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;

public class MascotGenericFormatFile extends RawDataFile {
    private MgfFile mgf = null;
    
    public MascotGenericFormatFile(File file) {
        super(file);
    }

    @Override
    public boolean isLoaded() {
        return mgf != null;
    }

    @Override
    public String getFormat() {
        return "mgf";
    }

    @Override
    public int getSpectraCount() {
        return mgf == null ? 0 : mgf.getSpectraCount();
    }

    @Override
    public boolean[] getPeakPicking() {
        this.msLevelPresence = new boolean[] {true, true};
        return new boolean[] {true, true};
    }

    @Override
    protected void initiateLoading() {
        ExecutorService executor = AnalyseData.getInstance().getExecutor();
        
        SwingWorker<MgfFile, Void> mgfWorker = new SwingWorker<MgfFile, Void>() {
            @Override
            protected MgfFile doInBackground() {
                try {
                    return new MgfFile(file);
                } catch (JMzReaderException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                
                return null;
            }

            @Override
            protected void done() {
                try {
                    mgf = get();
                    if (mgf == null) {
                        throw new RuntimeException("MGF file not read in correctly.");
                    }
                    
                    RawDataAndMultiplexingStep.getInstance().refreshFromData();
                    
                    //AnalyseData.getInstance().getInspectModel()
                    //        .addRawDataFile(MascotGenericFormatFile.this);
                    //InspectTab.getInstance().refreshComboBox();
                    AnalyseData
                            .getInstance()
                            .getTasksModel()
                            .set(new Task(file.getName(), "Load Raw Data",
                                            "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();

                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                            .checkAndUpdateRawDataStatus();

                    System.out.println("Done loading MGF file.");
                } catch (InterruptedException ex) {
                    System.out
                            .println("Interrupted exception loading MGF file: "
                                    + ex.getLocalizedMessage());
                } catch (ExecutionException ex) {
                    System.out
                            .println("Execution exception loading MGF file: "
                                    + ex.getLocalizedMessage());
                }
            }
        };

        executor.submit(mgfWorker);       
    }    
}
