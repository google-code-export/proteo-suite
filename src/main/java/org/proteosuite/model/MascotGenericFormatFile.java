/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.model;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.swing.SwingWorker;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.impl.CvParam;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;

public class MascotGenericFormatFile extends RawDataFile implements Comparable {

    private static final String RETENTION_TIME_PARAM = "MS:1000894";
    private boolean cacheSpectra = false;
    private MgfFile mgf = null;

    public MascotGenericFormatFile(File file) {
        this(file, false);
    }    
    
    public MascotGenericFormatFile(File file, boolean cacheSpectra) {
        super(file);
        this.cacheSpectra = cacheSpectra;
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
        this.msLevelPresence = new boolean[]{true, true};
        return new boolean[]{true, true};
    }

    @Override
    protected void initiateLoading() {
        ExecutorService executor = AnalyseData.getInstance().getGenericExecutor();

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

                    AnalyseData.getInstance().getInspectModel()
                            .addRawDataFile(MascotGenericFormatFile.this);
                    InspectTab.getInstance().refreshComboBox();
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

    @Override
    public int compareTo(Object that) {
        if (this.getFile().length() > ((MascotGenericFormatFile) that).getFile().length()) {
            return -1;
        } else if (this.getFile().length() < ((MascotGenericFormatFile) that).getFile().length()) {
            return 1;
        }

        return 0;
    }

    @Override
    public Iterator<Spectrum> iterator() {
        return new Iterator<Spectrum>() {
            private final Map<String, Spectrum> cachedSpectra = new LinkedHashMap<>();
            private final Map<Integer, List<Feature>> retentionTimePrecursorMap = new TreeMap<>();
            private Iterator<uk.ac.ebi.pride.tools.jmzreader.model.Spectrum> spectrumIterator = mgf.getSpectrumIterator();
            private Iterator<Entry<String, Spectrum>> cachedSpectrumIterator = null;
            private Iterator<Entry<Integer, List<Feature>>> reconstitutedPrecursorsIterator = null;
            private final boolean usingCache = determineIfUsingCache();
            private int index = 0;
            private int precursorIndex = 0;

            private boolean determineIfUsingCache() {
                if (cacheSpectra && cachingComplete) {
                    cachedSpectrumIterator = MascotGenericFormatFile.this.cachedSpectra.entrySet().iterator();
                    return true;
                } else {
                    spectrumIterator = mgf.getSpectrumIterator();
                    return false;
                }
            }

            @Override
            public boolean hasNext() {
                if (usingCache) {
                    return cachedSpectrumIterator.hasNext();
                } else {
                    if (spectrumIterator.hasNext()) {
                        return true;
                    } else {
                        if (reconstitutedPrecursorsIterator == null) {
                            reconstitutedPrecursorsIterator = retentionTimePrecursorMap.entrySet().iterator();
                        }

                        if (reconstitutedPrecursorsIterator.hasNext()) {
                            return true;
                        } else {
                            MascotGenericFormatFile.this.cachedSpectra = this.cachedSpectra;
                            MascotGenericFormatFile.this.cachingComplete = true;
                            return false;
                        }
                    }
                }
            }

            @Override
            public Spectrum next() {
                if (usingCache) {
                    return cachedSpectrumIterator.next().getValue();
                }

                Spectrum localSpectrum = null;

                if (spectrumIterator.hasNext()) {
                    uk.ac.ebi.pride.tools.jmzreader.model.Spectrum spectrum = spectrumIterator.next();
                    Feature precursor = new Feature(spectrum.getPrecursorMZ(), spectrum.getPrecursorIntensity(), spectrum.getPrecursorCharge());
                    localSpectrum = new FragmentSpectrum(precursor);

                    Map<Double, Double> peakList = spectrum.getPeakList();

                    if (peakList != null) {
                        for (Entry<Double, Double> entry : spectrum.getPeakList().entrySet()) {
                            localSpectrum.add(new MzIntensityPair(entry.getKey(), entry.getValue()));
                        }
                    }

                    localSpectrum.setSpectrumID(spectrum.getId());                   
                    localSpectrum.setSpectrumIndex(index++);
                   

                    double retentionTime = Double.parseDouble(getValueForAccession(RETENTION_TIME_PARAM, spectrum.getAdditional().getCvParams()));
                    localSpectrum.setRetentionTimeInSeconds(retentionTime);
                    if (retentionTimePrecursorMap.containsKey((int)retentionTime)) {
                        retentionTimePrecursorMap.get((int)retentionTime).add(precursor);
                    } else {
                        retentionTimePrecursorMap.put((int)retentionTime, new LinkedList<>(Arrays.asList(precursor)));
                    }
                } else {
                    Entry<Integer, List<Feature>> precursorSpectrum = reconstitutedPrecursorsIterator.next();
                    localSpectrum = new PrecursorSpectrum();
                    for (MzIntensityPair precursor : precursorSpectrum.getValue()) {
                        localSpectrum.add(precursor);
                    }

                    localSpectrum.setRetentionTimeInSeconds(precursorSpectrum.getKey());
                    
                    localSpectrum.setSpectrumIndex(index++);
                    localSpectrum.setSpectrumID("prec_" + precursorIndex++);
                }

                if (cacheSpectra) {
                    cachedSpectra.put(localSpectrum.getSpectrumID(), localSpectrum);
                }

                return localSpectrum;
            }
        };
    }

    private static String getValueForAccession(String accession, List<CvParam> params) {
        for (CvParam param : params) {
            if (param.getAccession().equals(accession)) {
                return param.getValue().trim();
            }
        }

        return null;
    }
}
