/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingWorker;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class RawMzMLFile extends RawDataFile {
    private MzMLUnmarshaller unmarshaller;
    private Pattern spectrumListPattern = Pattern.compile("<spectrumList([^<]+)>");
    private Pattern spectrumCountPattern = Pattern.compile("count=\"(\\d+)\"");
    public RawMzMLFile(File file) {
        super(file);
    }
    
    public MzMLUnmarshaller getUnmarshaller() {
        return unmarshaller;
    }
    
    @Override
    public String getFormat() {
        return "mzML";
    }

    @Override
    public int getSpectraCount() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String fileLine ="";
            while((fileLine = reader.readLine())!=null) {
                Matcher specListMatcher = spectrumListPattern.matcher(fileLine);                
                if (specListMatcher.find()) {
                    String[] specListAttributes = specListMatcher.group(1).trim().split(" ");
                    for (String attribute : specListAttributes) {
                        Matcher specCountMatcher = spectrumCountPattern.matcher(attribute);
                        if (specCountMatcher.matches()) {
                            reader.close();
                            return Integer.parseInt(specCountMatcher.group(1));
                        }
                    }                    
                }
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException io) {
        
        }        
        
        return 0;
    }

    @Override
    protected void initiateLoading() {
        AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Load Raw Data"));
        TasksTab.getInstance().refreshFromTasksModel();
        
        ExecutorService executor = AnalyseData.getInstance().getExecutor();        
        SwingWorker<MzMLUnmarshaller, Void> mzMLWorker = new SwingWorker<MzMLUnmarshaller, Void>() {
            @Override
            protected MzMLUnmarshaller doInBackground() {
                return new MzMLUnmarshaller(file);
            }
            
            @Override
            protected void done() {
                try {
                    unmarshaller = get();
                    AnalyseData.getInstance().getInspectModel().addRawDataFile(RawMzMLFile.this);
                    InspectTab.getInstance().refreshComboBox();
                    AnalyseData.getInstance().getTasksModel().set(new Task(file.getName(), "Load Raw Data", "Complete"));
                    TasksTab.getInstance().refreshFromTasksModel();
                    System.out.println("Done loading mzML file.");
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted exception loading mzML file: " + ex.getLocalizedMessage());
                } catch (ExecutionException ex) {
                    System.out.println("Execution exception loading mzML file: " + ex.getLocalizedMessage());
                }
            }
        };        
        
        executor.submit(mzMLWorker);        
    }   
}
