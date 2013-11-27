/*
 * --------------------------------------------------------------------------
 * MzMLCompress.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to compress mzML files 
 * Developer:         fgonzalez
 * Created:           12 April 2013
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils.compression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.proteosuite.utils.compression.DeltaConversion.DeltaSourceDataFormatException;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray.Precision;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
import uk.ac.ebi.jmzml.model.mzml.CV;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.params.BinaryDataArrayCVParam;

/**
 * This plugin allows the compression of MzML files by storing deltas on m/z
 * values and removing zeros on intensity values.
 *
 * @author SPerkins
 * @author fgonzalez
 */
public class MzMLCompress extends MzMLCompressorBase implements MzMLCompressor {
    private boolean trimZeros = false;

    public MzMLCompress() {
        super();
    }
    
    /** Gets the name of the MzML compressor.
     * @return Compressor name.
     */   
    public String getCompressorName() {
        return this.getClass().getName();
    }

    /**
     * Gets the file suffix for the compressor.
     * @return Compressor file suffix.
     */
    @Override
    public final String getCompressorSpecificSuffix() {
        return "_compress";
    }    

    public static void staticCompress(File xmlFile, String sPath, boolean bZeros) throws IOException {
        MzMLCompress compressor = new MzMLCompress();
        compressor.trimZeros = bZeros;
        compressor.compress(xmlFile, sPath);
    }   

    @Override
    protected final void compressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination) {
            //... Binary Data Array ...//
        //... Reading m/z and intensity values ...//
        Number[] mzNumbers = null;
        Number[] intenNumbers = null;
        for (BinaryDataArray bda : source.getBinaryDataArray()) {
            List<CVParam> cvpList = bda.getCvParam();
            for (CVParam cvp : cvpList) {
                if (cvp.getAccession().equals("MS:1000514")) {
                    if (bda.getEncodedLength() > 0) {
                        mzNumbers = bda.getBinaryDataAsNumberArray();
                    }
                } else if (cvp.getAccession().equals("MS:1000515")) {
                    if (bda.getEncodedLength() > 0) {
                        intenNumbers = bda.getBinaryDataAsNumberArray();
                    }
                }
            }
        }

        //... Storing delta values ...//
        if (mzNumbers != null && intenNumbers != null) {
            if (trimZeros) {
                // Remove mz values associated intensity value when intensity value is not above zero.
                SpectrumTrimmer trimmer = new SpectrumTrimmer(mzNumbers, intenNumbers);
                mzNumbers = trimmer.getMzValues();
                intenNumbers = trimmer.getIntensityValues();
            }

            // Convert MZ values to delta values.
            try {
                DeltaConversion.toDeltaNumberFormat(mzNumbers);
            } catch (DeltaSourceDataFormatException d) {
                System.out.println("Terminating compression due to issue with MZ data: " + d.getLocalizedMessage());
                return;
            }
        }

        //... Transforming data into Binary ...//
        BinaryDataArray bdaW = new BinaryDataArray();
        BinaryDataArray bda2W = new BinaryDataArray();

        if (mzNumbers != null && mzNumbers.length > 0 && intenNumbers != null && intenNumbers.length == mzNumbers.length) {
            CV cv = new CV();
            cv.setFullName("PSI-MS");
            cv.setId("MS");

            if (mzNumbers[0] instanceof Double) {
                bdaW.setNumberArrayAsBinaryData(mzNumbers, Precision.FLOAT64BIT, true, cv);
            } else if (mzNumbers[0] instanceof Float) {
                bdaW.setNumberArrayAsBinaryData(mzNumbers, Precision.FLOAT32BIT, true, cv);
            }

            CVParam cvParam1 = new BinaryDataArrayCVParam();
            cvParam1.setUnitCvRef("MS");
            cvParam1.setUnitName("m/z");
            cvParam1.setUnitAccession("MS:1000040");
            cvParam1.setName("m/z array");
            cvParam1.setAccession("MS:1000514");
            cvParam1.setCvRef("MS");
            bdaW.getCvParam().add(cvParam1);

            CVParam cvParam1_2 = new BinaryDataArrayCVParam();
            cvParam1_2.setAccession("MS:1000000");
            cvParam1_2.setName("deltas_zeros");
            cvParam1_2.setCvRef("MS");
            bdaW.getCvParam().add(cvParam1_2);
            bdaW.setEncodedLength(bdaW.getArrayLength());

            CVParam zeroesRemovedParam = null;

            if (trimZeros) {
                zeroesRemovedParam = new BinaryDataArrayCVParam();
                zeroesRemovedParam.setAccession("MS:1000001");
                zeroesRemovedParam.setName("zero_intensity_removed_values");
                zeroesRemovedParam.setCvRef("MS");
                bdaW.getCvParam().add(zeroesRemovedParam);
            }

                     // Set intensity array as array of integers if all values appear to be integers.
            // Otherwise set as array of doubles or floats.
            if (isArrayOfIntegers(intenNumbers)) {
                bda2W.setNumberArrayAsBinaryData(intenNumbers, Precision.INT32BIT, true, cv);
            } else if (intenNumbers[0] instanceof Double) {
                bda2W.setNumberArrayAsBinaryData(intenNumbers, Precision.FLOAT64BIT, true, cv);
            } else if (intenNumbers[0] instanceof Float) {
                bda2W.setNumberArrayAsBinaryData(intenNumbers, Precision.FLOAT32BIT, true, cv);
            }

            CVParam cvParam2 = new BinaryDataArrayCVParam();
            cvParam2.setUnitCvRef("MS");
            cvParam2.setUnitName("number of counts");
            cvParam2.setAccession("MS:1000131");
            cvParam2.setName("intensity array");
            cvParam2.setAccession("MS:1000515");
            cvParam2.setCvRef("MS");
            bda2W.getCvParam().add(cvParam2);
            bda2W.getCvParam().add(cvParam1_2);
            if (trimZeros) {
                bda2W.getCvParam().add(zeroesRemovedParam);
            }

            bda2W.setEncodedLength(bda2W.getArrayLength());
        }

        destination.getBinaryDataArray().add(bdaW);
        destination.getBinaryDataArray().add(bda2W);
        destination.setCount(2);

    }
    
    @Override
    protected void decompressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination) {
        
    }

    /**
     * Checks whether the supplied Number array is an array of integers.
     * @param numbers Number array.
     * @return Whether the Number array is all integers.
     */
    private static boolean isArrayOfIntegers(final Number[] numbers) {
        if (numbers[0] instanceof Double) {
            for (Number number : numbers) {
                if (!(((Double) number % 1) == 0)) {
                    return false;
                }
            }
        } else if (numbers[0] instanceof Float) {
            for (Number number : numbers) {
                if (!(((Float) number % 1) == 0)) {
                    return false;
                }
            }
        } else if (numbers[0] instanceof Integer) {
            return true;
        } else {
            return false;
        }

        return true;
    }

    private static int removeZeroesAndPerformDeltaConversion(Number[] mzNumbers, Number[] intenNumbers, final boolean bZeros) {
        int mzCount = 0;
        double[] mz = null;
        double[] nonzerosMz = null;
        double[] nonzerosIntens = null;
        double[] intensities = null;

        //... Storing delta values ...//
        if (mzNumbers != null) {
            mz = new double[mzNumbers.length];
            intensities = new double[intenNumbers.length];
            mzCount = mzNumbers.length;

            //... Copy Numbers into doubles ...//
            for (int iI = 0; iI < mzNumbers.length; iI++) {
                mz[iI] = mzNumbers[iI].doubleValue();
                intensities[iI] = intenNumbers[iI].doubleValue();
            }
            //... Removing zero values ...//
            ArrayList<Double> zeroMz = new ArrayList<Double>();
            ArrayList<Double> zeroIntens = new ArrayList<Double>();
            double last = 0.0d;
            for (int iI = 0; iI < mzNumbers.length; iI++) {
                if (bZeros) {
                    if (intensities[iI] > 0) {
                        zeroMz.add(mz[iI] - last);    //... Storing Deltas ...//
                        zeroIntens.add(intensities[iI]);
                        last = mz[iI];
                    }
                } else {
                    zeroMz.add(mz[iI] - last);    //... Storing Deltas ...//
                    zeroIntens.add(intensities[iI]);
                    last = mz[iI];
                }
            }
            nonzerosMz = new double[zeroMz.size()];
            nonzerosIntens = new double[zeroIntens.size()];
            int iI = 0;
            for (Double d : zeroMz) {
                nonzerosMz[iI++] = d.doubleValue();
            }
            iI = 0;
            for (Double d : zeroIntens) {
                nonzerosIntens[iI++] = d.doubleValue();
            }
        }

        return mzCount;
    }
}
