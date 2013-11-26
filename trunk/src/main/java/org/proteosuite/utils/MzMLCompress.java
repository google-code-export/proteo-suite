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
package org.proteosuite.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray.Precision;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
import uk.ac.ebi.jmzml.model.mzml.CV;
import uk.ac.ebi.jmzml.model.mzml.CVList;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.ChromatogramList;
import uk.ac.ebi.jmzml.model.mzml.FileDescription;
import uk.ac.ebi.jmzml.model.mzml.InstrumentConfigurationList;
import uk.ac.ebi.jmzml.model.mzml.MzML;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroupList;
import uk.ac.ebi.jmzml.model.mzml.Run;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.model.mzml.SpectrumList;
import uk.ac.ebi.jmzml.model.mzml.params.BinaryDataArrayCVParam;
import uk.ac.ebi.jmzml.xml.io.MzMLMarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * This plugin allows the compression of MzML files by storing deltas on m/z
 * values and removing zeros on intensity values.
 *
 * @author fgonzalez
 */
public class MzMLCompress extends MzMLCompressorBase implements MzMLCompressor {

    public MzMLCompress() {
        super();        
    }
    
    /**
     * Compresses the mzML file.     *
     * @param xmlFile a string containing the file name.
     * @param sPath a string for the path where the file is located.
     * @param bZeros Whether or not to strip out zero-intensity pairs.
     * @author fgonzalez
     * @throws IOException
     */
    //public MzMLCompress(File xmlFile, String sPath, boolean bZeros) throws IOException {
    //    staticCompress(xmlFile, sPath, bZeros);
    //}

    public String getCompressorName() {
        return this.getClass().getName();
    }
    
    /**
     * Gets the file suffix for the compressor.
     * @return Compressor file suffix.
     */
    public String getCompressorSpecificSuffix() {
        return "_compress";
    }
    
    public void compress(File xmlFile, String sPath) throws IOException {
        compressionResult.setNonCompressedSize(xmlFile.length());
        stampStart();
        staticCompress(xmlFile, sPath, true);
        stampStop();
        compressionResult.setCompressedSize(new File(sPath + xmlFile.getName().replace(".mzML", getCompressorSpecificSuffix() + ".mzML")).length());
    }
    
    public void compress(File xmlFile, String sPath, boolean bZeros) throws IOException {
        staticCompress(xmlFile, sPath, bZeros);
    }
    
    public void decompress(File xmlFile, String sPath) {
        
    }
    
    public static void staticCompress(File xmlFile, String sPath, boolean bZeros) throws IOException {        
        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
        //... CV list ...//                
        System.out.println("- Reading /cvList");
        CVList cvl = unmarshaller.unmarshalFromXpath("/cvList", CVList.class);

        //... File Description ...//
        System.out.println("- Reading /fileDescription");
        FileDescription fdList = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);

        //... Referenceable Param Group ...//
        System.out.println("- Reading /referenceableParamGroupList");
        ReferenceableParamGroupList refList = unmarshaller.unmarshalFromXpath("/referenceableParamGroupList", ReferenceableParamGroupList.class);

        //... Software List ...//
        System.out.println("- Reading /softwareList");
        uk.ac.ebi.jmzml.model.mzml.SoftwareList softList = unmarshaller.unmarshalFromXpath("/softwareList", uk.ac.ebi.jmzml.model.mzml.SoftwareList.class);

        //... InstrumentConfiguration ...//
        System.out.println("- Reading /instrumentConfigurationList");
        InstrumentConfigurationList instConfList = unmarshaller.unmarshalFromXpath("/instrumentConfigurationList", InstrumentConfigurationList.class);

        //... Data Processing List ...//
        System.out.println("- Reading /dataProcessingList");
        uk.ac.ebi.jmzml.model.mzml.DataProcessingList dataProcList = unmarshaller.unmarshalFromXpath("/dataProcessingList", uk.ac.ebi.jmzml.model.mzml.DataProcessingList.class);

        //... Runs ...//
        Run runFileW = new Run();
        Calendar date = Calendar.getInstance();
        runFileW.setStartTimeStamp(date);
        runFileW.setDefaultSourceFileRef("RAW1");
        runFileW.setDefaultInstrumentConfigurationRef("IC1");
        runFileW.setId(xmlFile.getName());

        SpectrumList specListW = new SpectrumList();
        specListW.setDefaultDataProcessingRef("pwiz_Reader_conversion");

        //... Spectra ...//
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        int iSpectrum = 0;
        while (spectrumIterator.hasNext()) {
            Spectrum spectrum = spectrumIterator.next();

                //System.out.println(spectrum.getId());
            //... Write spectrum ...//
            //... Attributes ...//
            Spectrum spectrumW = new Spectrum();
            spectrumW.setDefaultArrayLength(spectrum.getDefaultArrayLength());
            spectrumW.setIndex(spectrum.getIndex());
            spectrumW.setId(spectrum.getId());

            //... cvParams ...//
            List<CVParam> specParam = spectrum.getCvParam();
            for (Iterator<CVParam> lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();) {
                CVParam lCVParam = lCVParamIterator.next();
                spectrumW.getCvParam().add(lCVParam);
            }

            //... Scan List ...//
            spectrumW.setScanList(spectrum.getScanList());

            //... Precursor List ...//
            spectrumW.setPrecursorList(spectrum.getPrecursorList());

                //... Binary Data Array ...//
            //... Reading m/z and intensity values ...//
            Number[] mzNumbers = null;
            Number[] intenNumbers = null;

            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
            for (BinaryDataArray bda : bdal) {
                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp : cvpList) {
                    if (cvp.getAccession().equals("MS:1000514")) {
                        if (bda.getEncodedLength() > 0) {
                            mzNumbers = bda.getBinaryDataAsNumberArray();
                        }
                    }
                    if (cvp.getAccession().equals("MS:1000515")) {
                        if (bda.getEncodedLength() > 0) {
                            intenNumbers = bda.getBinaryDataAsNumberArray();
                        }
                    }
                }
            }

            //... Storing delta values ...//
            if (mzNumbers != null && intenNumbers != null) {
                if (bZeros) {
                    // Remove mz values associated intensity value when intensity value is not above zero.
                    SpectrumTrimmer trimmer = new SpectrumTrimmer(mzNumbers, intenNumbers);
                    mzNumbers = trimmer.getMzValues();
                    intenNumbers = trimmer.getIntensityValues();
                }

                // Convert MZ values to delta values.
                DeltaConversion.toDeltaNumberFormat(mzNumbers);
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

                if (bZeros) {
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
                if (bZeros) {
                    bda2W.getCvParam().add(zeroesRemovedParam);
                }

                bda2W.setEncodedLength(bda2W.getArrayLength());
            }

            BinaryDataArrayList bdalstW = new BinaryDataArrayList();
            bdalstW.getBinaryDataArray().add(bdaW);
            bdalstW.getBinaryDataArray().add(bda2W);
            bdalstW.setCount(2);
            spectrumW.setBinaryDataArrayList(bdalstW);
            specListW.getSpectrum().add(spectrumW);

            iSpectrum++;
        }

        specListW.setCount(iSpectrum);

        System.out.println("- Writing spectrumList");
        runFileW.setSpectrumList(specListW);

        System.out.println("- Writing /run/chromatogramList");
        ChromatogramList chromat = unmarshaller.unmarshalFromXpath("/run/chromatogramList", ChromatogramList.class);
        runFileW.setChromatogramList(chromat);

        MzML mzml = new MzML();
        mzml.setVersion("1.1.0");
        mzml.setId(xmlFile.getName());
        mzml.setCvList(cvl);
        mzml.setFileDescription(fdList);
        mzml.setReferenceableParamGroupList(refList);
        mzml.setSoftwareList(softList);
        mzml.setInstrumentConfigurationList(instConfList);
        mzml.setDataProcessingList(dataProcList);
        mzml.setRun(runFileW);
        MzMLMarshaller marshaller = new MzMLMarshaller();
        Writer writer = new FileWriter(sPath + "\\" + xmlFile.getName().replace(".mzML", "_compress.mzML"));
        marshaller.marshall(mzml, writer);
        writer.close();
    }
    
    /**
     * Checks whether the supplied Number array is an array of integers.     *
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
