package org.proteosuite.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SPerkins
 */
public class InspectModel {

    private final List<RawDataFile> rawData = new ArrayList<RawDataFile>();
    private final List<IdentDataFile> identData = new ArrayList<IdentDataFile>();
    private final List<QuantDataFile> quantData = new ArrayList<QuantDataFile>();

    public synchronized void addRawDataFile(RawDataFile rawDataFile) {
        rawData.add(rawDataFile);
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
