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
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class RawMzMLFile extends RawDataFile {

    private MzMLUnmarshaller unmarshaller = null;
    private Pattern spectrumListPattern = Pattern.compile("<spectrumList([^<]+)>");
    private Pattern spectrumCountPattern = Pattern.compile("count=\"(\\d+)\"");
    private Pattern spectrumStartPattern = Pattern.compile("<spectrum [^<]+>");
    private Pattern spectrumEndPattern = Pattern.compile("</spectrum>");
    private Pattern msLevelParamPattern = Pattern.compile("<cvParam[^<]+accession=\"MS:1000511\"[^<]+value=\"(\\d)\"");
    private Pattern centroidedPattern = Pattern.compile("<cvParam[^<]+accession=\"MS:1000127\"");

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
    public boolean isLoaded() {
        if (unmarshaller == null) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int getSpectraCount() {
        if (spectraCountChecked) {
            return spectraCount;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String fileLine = "";
            while ((fileLine = reader.readLine()) != null) {
                Matcher specListMatcher = spectrumListPattern.matcher(fileLine);
                if (specListMatcher.find()) {
                    String[] specListAttributes = specListMatcher.group(1).trim().split(" ");
                    for (String attribute : specListAttributes) {
                        Matcher specCountMatcher = spectrumCountPattern.matcher(attribute);
                        if (specCountMatcher.matches()) {
                            reader.close();
                            spectraCount = Integer.parseInt(specCountMatcher.group(1));
                            spectraCountChecked = true;
                            return spectraCount;
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException io) {

        }

        spectraCount = 0;
        spectraCountChecked = true;
        return spectraCount;
    }

    @Override
    public boolean[] getPeakPicking() {
        if (peakPickingChecked) {
            return this.peakPicking;
        }

        boolean[] checkedSpectrum = {false, false};

        // Needs extracting into more than one method.
        
        try {
            int spectraSeen = 0;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String fileLine = "";
            while ((fileLine = reader.readLine()) != null) {
                Matcher spectrumMatcher = spectrumStartPattern.matcher(fileLine);
                if (spectrumMatcher.find()) {
                    spectraSeen++;
                    outer:while ((fileLine = reader.readLine()) != null) {
                        Matcher spectrumEndMatcher = spectrumEndPattern.matcher(fileLine);
                        if (!spectrumEndMatcher.find()) {
                            Matcher msLevelParamMatcher = msLevelParamPattern.matcher(fileLine);
                            if (msLevelParamMatcher.find()) {
                                String msLevel = msLevelParamMatcher.group(1);
                                switch (msLevel) {
                                    case "1":
                                        checkedSpectrum[0] = true;
                                        break;
                                    case "2":
                                        checkedSpectrum[1] = true;
                                        break;
                                }

                                while ((fileLine = reader.readLine()) != null) {
                                    Matcher spectrumEndMatcher2 = spectrumEndPattern.matcher(fileLine);
                                    if (!spectrumEndMatcher2.find()) {
                                        Matcher centroidedMatcher = centroidedPattern.matcher(fileLine);
                                        if (centroidedMatcher.find()) {
                                            switch (msLevel) {
                                                case "1":
                                                    this.peakPicking[0] = true;
                                                    break;
                                                case "2":
                                                    this.peakPicking[1] = true;
                                                    break;
                                            }
                                        }
                                    } else {
                                        if ((checkedSpectrum[0] && checkedSpectrum[1]) || spectraSeen > 100) {
                                            reader.close();
                                            this.peakPickingChecked = true;
                                            return this.peakPicking;
                                        }
                                        
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            reader.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

        this.peakPickingChecked = true;
        return peakPicking;
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
                    
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().checkAndUpdateRawDataStatus();
                    
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
