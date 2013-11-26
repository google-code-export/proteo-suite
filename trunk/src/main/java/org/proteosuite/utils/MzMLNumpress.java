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
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
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
 * This plugin allows the compression of MzML files by storing the mz and intensity values using Numpress compression.
 * @author fgonzalez
 * @author SPerkins
 */
public class MzMLNumpress extends MzMLCompressorBase implements MzMLCompressor {
    
    public MzMLNumpress() {
        super();
    }
    
    /**
     * Numpress compresses the given file.     *
     * @param xmlFile A string containing the file name.
     * @param outputDirectory A string for the path where the file is located.
     * @throws IOException Thrown if problem writing data.
     */
    public void compress(File xmlFile, final String outputDirectory) throws IOException {
        // Record the timing of the beginning of the compression.
        stampStart();
        
        // Set the original file size in the compression result.
        compressionResult.setNonCompressedSize(xmlFile.length());
        
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
                for (Iterator<CVParam> lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
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

            //... Transforming data into Binary ...//
            BinaryDataArray bdaW = new BinaryDataArray();
            BinaryDataArray bda2W = new BinaryDataArray();

            if (mzNumbers != null && mzNumbers.length > 0
                    && intenNumbers != null
                    && intenNumbers.length == mzNumbers.length) {
                CV cv = new CV();
                cv.setFullName("PSI-MS");
                cv.setId("MS");

                // Numpress code.               
                byte[] encodedMzNumbers = new byte[8 + (mzNumbers.length * 5)];
                int encodedBytes = MSNumpress.encodeLinear(doublesFromNumbers(mzNumbers), mzNumbers.length, encodedMzNumbers, 
                        MSNumpress.optimalLinearFixedPoint(doublesFromNumbers(mzNumbers), mzNumbers.length));
                
                // Set the numpress compressed binary - note: can't set jmzml to use zlib when using this method.
                bdaW.setBinary(Arrays.copyOf(encodedMzNumbers, encodedBytes));
                bdaW.setEncodedLength(mzNumbers.length);
                
                // Set a CVParam for the numpress linear compression.
                CVParam compressionParam = new BinaryDataArrayCVParam();
                compressionParam.setAccession("MS:1000574");
                compressionParam.setName("numpress linear compression");
                bdaW.getCvParam().add(compressionParam);

                CVParam cvParam1 = new BinaryDataArrayCVParam();
                cvParam1.setUnitCvRef("MS");
                cvParam1.setUnitName("m/z");
                cvParam1.setUnitAccession("MS:1000040");
                cvParam1.setName("m/z array");
                cvParam1.setAccession("MS:1000514");
                cvParam1.setCvRef("MS");
                bdaW.getCvParam().add(cvParam1);                
                
                byte[] encodedIntensityNumbers = new byte[(intenNumbers.length * 5)];
                encodedBytes = MSNumpress.encodePic(doublesFromNumbers(intenNumbers), intenNumbers.length, encodedIntensityNumbers);
                
                bda2W.setBinary(Arrays.copyOf(encodedIntensityNumbers, encodedBytes));
                bda2W.setEncodedLength(intenNumbers.length);
                
                bda2W.getCvParam().add(compressionParam);

                CVParam cvParam2 = new BinaryDataArrayCVParam();
                cvParam2.setUnitCvRef("MS");
                cvParam2.setUnitName("number of counts");
                cvParam2.setAccession("MS:1000131");
                cvParam2.setName("intensity array");
                cvParam2.setAccession("MS:1000515");
                cvParam2.setCvRef("MS");
                bda2W.getCvParam().add(cvParam2);                
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

        String sNumpress = xmlFile.getName();
        sNumpress = sNumpress.replace(".mzML", "_numpress.mzML");
        Writer writer = new FileWriter(outputDirectory + "\\" + sNumpress);
        marshaller.marshall(mzml, writer);
        writer.close();
        
        // Record the timing of the end of the compression.
        stampStop();
        
        compressionResult.setCompressedSize(new File(outputDirectory + "\\" + sNumpress).length());
    }
    
    /**
     * Numpress decompresses the given file.
     * @param xmlFile A string containing the file name.
     * @param outputDirectory A string for the path where the file is located.
     */
    public void decompress(File xmlFile, String outputDirectory) {
    
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
    public String getCompressorSpecificSuffix() {
        return "_numpress";
    }  

    public static byte[] bytesFromNumbers(Number[] numbers) {
        ByteBuffer buffer = null;
        if (numbers[0] instanceof Double) {
            buffer = ByteBuffer.allocate(numbers.length * 8);
            for (Number value : numbers) {
                buffer.putDouble((Double) value);
            }
        } else if (numbers[0] instanceof Float) {
            buffer = ByteBuffer.allocate(numbers.length * 4);
            for (Number value : numbers) {
                buffer.putDouble((Float) value);
            }
        }

        return buffer.array();
    }

    /**
     * Converts an array of Number objects into an array of double primitives.
     * @param numbers Number array.
     * @return Primitive double array.
     */
    public static double[] doublesFromNumbers(final Number[] numbers) {
        double[] doubles = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            doubles[i] = (Double) numbers[i];
        }

        return doubles;
    }
}
