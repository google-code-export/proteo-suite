package org.proteosuite.model;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class InspectModel {
    private final Map<String, ChromatogramPanel> cachedChromatograms = new HashMap<String, ChromatogramPanel>();
    private final Map<String, JPanel> cached2DViews = new HashMap<String, JPanel>();

    private final List<RawDataFile> rawData = new ArrayList<RawDataFile>();
    private final List<IdentDataFile> identData = new ArrayList<IdentDataFile>();
    private final List<QuantDataFile> quantData = new ArrayList<QuantDataFile>();

    public synchronized void addRawDataFile(RawDataFile rawDataFile) {
        rawData.add(rawDataFile);
    }

    public synchronized void removeRawDataFile(RawDataFile rawDataFile) {
        rawData.remove(rawDataFile);
    }

    public synchronized List<RawDataFile> getRawData() {
        return rawData;
    }

    public synchronized void addIdentDataFile(IdentDataFile identDataFile) {
        identData.add(identDataFile);
    }

    public synchronized List<IdentDataFile> getIdentData() {
        return identData;
    }

    public synchronized void addQuantDataFile(QuantDataFile quantDataFile) {
        quantData.add(quantDataFile);
    }
    
    public synchronized List<QuantDataFile> getQuantData() {
        return quantData;
    }
    
    public ChromatogramPanel getCachedChromatogramOrNull(String fileName) {
        return cachedChromatograms.get(fileName);
    }
    
    public void addCachedChromatogram(String fileName, ChromatogramPanel chromatogram) {
        cachedChromatograms.put(fileName, chromatogram);
    } 
    
    public JPanel getCached2DViewOrNull(String fileName) {
        return cached2DViews.get(fileName);
    }
    
    public void addCached2DView(String fileName, JPanel twoDView) {
        cached2DViews.put(fileName, twoDView);
    } 

    public boolean isRawDataFile(String fileName) {
        for (RawDataFile dataFile : rawData) {
            if (dataFile.getFileName().equals(fileName)) {
                return true;
            }
        }

        return false;
    }

    public RawDataFile getRawDataFile(String fileName) {
        for (RawDataFile dataFile : rawData) {
            if (dataFile.getFileName().equals(fileName)) {
                return dataFile;
            }
        }

        return null;
    }

    public boolean isIdentFile(String fileName) {
        for (IdentDataFile identFile : identData) {
            if (identFile.getFileName().equals(fileName)) {
                return true;
            }
        }

        return false;
    }

    public IdentDataFile getIdentDataFile(String fileName) {
        for (IdentDataFile identFile : identData) {
            if (identFile.getFileName().equals(fileName)) {
                return identFile;
            }
        }

        return null;
    }

    public boolean isQuantFile(String fileName) {
        for (QuantDataFile identFile : quantData) {
            if (identFile.getFileName().equals(fileName)) {
                return true;
            }
        }
        
        return false;
    }

	public QuantDataFile getQuantDataFile(String fileName) {
        for (QuantDataFile quantFile : quantData) {
            if (quantFile.getFileName().equals(fileName)) {
                return quantFile;
            }
        }

        return null;
	}
    
    public void clear() {
        rawData.clear();
        identData.clear();
        quantData.clear();
    }
}
