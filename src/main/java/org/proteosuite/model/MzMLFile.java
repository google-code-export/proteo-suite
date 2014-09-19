package org.proteosuite.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.actions.RawFilePostLoadAction;
import org.proteosuite.utils.FileFormatUtils;
import org.proteosuite.utils.NumericalUtils;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class MzMLFile extends RawDataFile {

    private MzMLUnmarshaller unmarshaller = null;
    private boolean cacheSpectra = false;
    private static final Pattern spectrumListPattern = Pattern
            .compile("<spectrumList([^<]+)>");
    private final Pattern spectrumCountPattern = Pattern.compile("count=\"(\\d+)\"");
    private final Pattern spectrumStartPattern = Pattern.compile("<spectrum [^<]+>");
    private final Pattern spectrumEndPattern = Pattern.compile("</spectrum>");
    private final Pattern msLevelParamPattern = Pattern
            .compile("<cvParam[^<]+accession=\"MS:1000511\"[^<]+value=\"(\\d)\"");
    private final Pattern centroidedPattern = Pattern
            .compile("<cvParam[^<]+accession=\"MS:1000127\"");
    private static final String MS_LEVEL_PARAM = "MS:1000511";
    private static final String BASE_PEAK_MZ_PARAM = "MS:1000504";
    private static final String BASE_PEAK_INTENSITY_PARAM = "MS:1000505";
    private static final String PRECURSOR_MZ_PARAM = "MS:1000744";
    private static final String PRECURSOR_CHARGE_PARAM = "MS:1000041";
    private static final String BINARY_MZ_PARAM = "MS:1000514";
    private static final String BINARY_INTENSITY_PARAM = "MS:1000515";
    private static final String RETENTION_TIME_PARAM = "MS:1000016";
    private static final String RETENTION_TIME_MINUTE_PARAM = "UO:0000031";

    public MzMLFile(File file) {
        this(file, false);
    }

    public MzMLFile(File file, boolean cacheSpectra) {
        super(file);
        this.cacheSpectra = cacheSpectra;
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
        return unmarshaller != null;
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
                    String[] specListAttributes = specListMatcher.group(1)
                            .trim().split(" ");
                    for (String attribute : specListAttributes) {
                        Matcher specCountMatcher = spectrumCountPattern
                                .matcher(attribute);
                        if (specCountMatcher.matches()) {
                            reader.close();
                            spectraCount = Integer.parseInt(specCountMatcher
                                    .group(1));
                            spectraCountChecked = true;
                            return spectraCount;
                        }
                    }
                }
            }
            reader.close();
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
                Matcher spectrumMatcher = spectrumStartPattern
                        .matcher(fileLine);
                if (!spectrumMatcher.find()) {
                    continue;
                }

                spectraSeen++;
                outer:
                while ((fileLine = reader.readLine()) != null) {
                    Matcher spectrumEndMatcher = spectrumEndPattern
                            .matcher(fileLine);
                    if (spectrumEndMatcher.find()) {
                        continue;
                    }

                    Matcher msLevelParamMatcher = msLevelParamPattern
                            .matcher(fileLine);

                    if (!msLevelParamMatcher.find()) {
                        continue;
                    }

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
                        Matcher spectrumEndMatcher2 = spectrumEndPattern
                                .matcher(fileLine);
                        if (!spectrumEndMatcher2.find()) {
                            Matcher centroidedMatcher = centroidedPattern
                                    .matcher(fileLine);
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
                            if ((checkedSpectrum[0] && checkedSpectrum[1])
                                    || spectraSeen > 1000) {
                                reader.close();
                                this.peakPickingChecked = true;
                                this.msLevelPresence = checkedSpectrum;
                                return this.peakPicking;
                            }

                            break outer;
                        }
                    }
                }
            }

            reader.close();
        } catch (IOException ex) {

        }

        this.peakPickingChecked = true;
        return peakPicking;
    }

    @Override
    protected void initiateLoading() {        

        final BackgroundTask task = new BackgroundTask(this, "Load Raw Data");        

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<MzMLUnmarshaller, BackgroundTaskSubject>() {
            @Override
            public MzMLUnmarshaller act(BackgroundTaskSubject ignored) {
                return new MzMLUnmarshaller(file);
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<Void,BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject ignored) {
                unmarshaller = task.getResultOfClass(MzMLUnmarshaller.class);
                if (unmarshaller == null) {
                    throw new RuntimeException("mzML file not read in correctly.");
                }
                
                return null;
            }
        });
        
        task.addCompletionAction(new RawFilePostLoadAction());
        task.addCompletionAction(new ProteoSuiteAction<Void, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject ignored) {
                System.out.println("Done loading mzML file.");
                return null;
            }
        });

        BackgroundTaskManager.getInstance().submit(task);       
    }

    public MascotGenericFormatFile getAsMGF() {
        if (this.isLoaded()) {
            String output = this.getAbsoluteFileName().replace("\\.mzML$", ".mgf").replace("\\.mzml$", ".mgf");
            boolean successfulConversion = FileFormatUtils.mzMLToMGF(unmarshaller, output);
            if (successfulConversion) {
                return new MascotGenericFormatFile(new File(output));
            }
        }

        return null;
    }

    @Override
    public Iterator<Spectrum> iterator() {
        return new Iterator<Spectrum>() {
            private final Map<String, Spectrum> cachedSpectra = new LinkedHashMap<>();
            private MzMLObjectIterator<uk.ac.ebi.jmzml.model.mzml.Spectrum> spectrumIterator = null;
            private Iterator<Entry<String, Spectrum>> cachedSpectrumIterator = null;
            private final boolean usingCache = determineIfUsingCache();

            private boolean determineIfUsingCache() {
                if (cacheSpectra && cachingComplete) {
                    cachedSpectrumIterator = MzMLFile.this.cachedSpectra.entrySet().iterator();
                    return true;
                } else {
                    spectrumIterator = unmarshaller
                            .unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
                                    uk.ac.ebi.jmzml.model.mzml.Spectrum.class);

                    return false;
                }
            }

            @Override
            public boolean hasNext() {
                if (usingCache) {
                    return cachedSpectrumIterator.hasNext();
                } else {
                    if (!spectrumIterator.hasNext()) {
                        MzMLFile.this.cachedSpectra = this.cachedSpectra;
                        MzMLFile.this.cachingComplete = true;
                        return false;
                    }

                    return true;
                }
            }

            @Override
            public Spectrum next() {
                if (usingCache) {
                    return cachedSpectrumIterator.next().getValue();
                }

                uk.ac.ebi.jmzml.model.mzml.Spectrum spectrum = spectrumIterator.next();
                Spectrum localSpectrum = null;
                if (Integer.parseInt(getValueForAccession(MS_LEVEL_PARAM, spectrum.getCvParam())) == 1) {
                    localSpectrum = new PrecursorSpectrum();
                } else {
                    List<CVParam> precursorParams = spectrum.getPrecursorList().getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam();
                    double precursorMz = Double.parseDouble(getValueForAccession(PRECURSOR_MZ_PARAM, precursorParams));
                    String precursorChargeString = getValueForAccession(PRECURSOR_CHARGE_PARAM, precursorParams);
                    int precursorCharge = 0;
                    if (precursorChargeString != null && NumericalUtils.isInteger(precursorChargeString)) {
                        precursorCharge = Integer.parseInt(getValueForAccession(PRECURSOR_CHARGE_PARAM, precursorParams));
                    }

                    localSpectrum = new FragmentSpectrum(precursorMz, 0.0, precursorCharge);
                }

                localSpectrum.setSpectrumIndex(spectrum.getIndex());
                localSpectrum.setSpectrumID(spectrum.getId());

                double basePeakMz = Double.parseDouble(getValueForAccession(BASE_PEAK_MZ_PARAM, spectrum.getCvParam()));
                double basePeakIntensity = Double.parseDouble(getValueForAccession(BASE_PEAK_INTENSITY_PARAM, spectrum.getCvParam()));
                localSpectrum.setBasePeak(new MzIntensityPair(basePeakMz, basePeakIntensity));

                double retentionTime = Double.parseDouble(getValueForAccession(RETENTION_TIME_PARAM, spectrum.getScanList().getScan().get(0).getCvParam()));
                if (containsUnitParamAccession(RETENTION_TIME_PARAM, RETENTION_TIME_MINUTE_PARAM, spectrum.getScanList().getScan().get(0).getCvParam())) {
                    localSpectrum.setRetentionTimeInMinutes(retentionTime);
                } else {
                    localSpectrum.setRetentionTimeInSeconds(retentionTime);
                }

                Number[] mzValues = getBinaryDataArrayForAccession(BINARY_MZ_PARAM, spectrum.getBinaryDataArrayList().getBinaryDataArray());
                Number[] intensityValues = getBinaryDataArrayForAccession(BINARY_INTENSITY_PARAM, spectrum.getBinaryDataArrayList().getBinaryDataArray());
                
                spectrum = null;

                if (mzValues != null && intensityValues != null && mzValues.length == intensityValues.length) {
                    for (int i = 0; i < mzValues.length; i++) {
                        double mzValue = 0.0;
                        double intensityValue = 0.0;
                        if (mzValues[i] != null) {
                            if (mzValues[i] instanceof Double) {
                                mzValue = (Double) mzValues[i];
                            } else {
                                mzValue = (Float) mzValues[i];
                            }
                        }

                        if (intensityValues[i] != null) {
                            if (intensityValues[i] instanceof Double) {
                                intensityValue = (Double) intensityValues[i];
                            } else if (intensityValues[i] instanceof Float) {
                                intensityValue = (Float) intensityValues[i];
                            } else if (intensityValues[i] instanceof Integer) {
                                intensityValue = (Integer) intensityValues[i];
                            } else if (intensityValues[i] instanceof Long) {
                                intensityValue = (Long) intensityValues[i];
                            }
                        }

                        if (mzValue != 0.0) {
                            localSpectrum.add(new MzIntensityPair(mzValue, intensityValue));
                        }
                    }
                }

                if (cacheSpectra) {
                    cachedSpectra.put(localSpectrum.getSpectrumID(), localSpectrum);
                }

                return localSpectrum;
            }
        };
    }

    private static boolean containsParamAccession(String accession, List<CVParam> params) {
        for (CVParam param : params) {
            if (param.getAccession().equals(accession)) {
                return true;
            }
        }

        return false;
    }

    private static boolean containsUnitParamAccession(String accession, String unitAccession, List<CVParam> params) {
        for (CVParam param : params) {
            if (param.getAccession().equals(accession)) {
                if (param.getUnitAccession().equals(unitAccession)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String getValueForAccession(String accession, List<CVParam> params) {
        for (CVParam param : params) {
            if (param.getAccession().equals(accession)) {
                return param.getValue().trim();
            }
        }

        return null;
    }

    private static Number[] getBinaryDataArrayForAccession(String accession, List<BinaryDataArray> dataArrays) {
        for (BinaryDataArray dataArray : dataArrays) {
            if (containsParamAccession(accession, dataArray.getCvParam())) {
                return dataArray.getBinaryDataAsNumberArray();
            }
        }

        return null;
    }

    @Override
    public String getSubjectName() {
        return this.getFileName();
    }
}