/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.compression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
import uk.ac.ebi.jmzml.model.mzml.CV;
import uk.ac.ebi.jmzml.model.mzml.CVList;
import uk.ac.ebi.jmzml.model.mzml.ChromatogramList;
import uk.ac.ebi.jmzml.model.mzml.FileDescription;
import uk.ac.ebi.jmzml.model.mzml.InstrumentConfigurationList;
import uk.ac.ebi.jmzml.model.mzml.MzML;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroupList;
import uk.ac.ebi.jmzml.model.mzml.Run;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.model.mzml.SpectrumList;
import uk.ac.ebi.jmzml.xml.io.MzMLMarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public abstract class MzMLCompressorBase implements MzMLCompressor {
    protected CompressionResult compressionResult;
    private DecompressionResult decompressionResult;
    public MzMLCompressorBase() {
        compressionResult = new CompressionResult();
        decompressionResult = new DecompressionResult();
    }
    
    public void stampStart(boolean doingCompression) {
        if (doingCompression) {
            compressionResult.setStart(System.nanoTime());
        } else {
            decompressionResult.setStart(System.nanoTime());
        }
    }
    
    public void stampStop(boolean doingCompression) {
        if (doingCompression) {
            compressionResult.setStop(System.nanoTime());
        } else {
            decompressionResult.setStop(System.nanoTime());
        }
    }  
    
    public DecompressionResult decompress(File xmlFile, final String outputDirectory) throws IOException {
        decompressionResult.setCompressedSize(xmlFile.length());
        stampStart(false);
        doCompressionOrDecompression(xmlFile, outputDirectory, false);
        stampStop(false);
        decompressionResult.setDecompressedSize(new File(outputDirectory + xmlFile.getName().replace(getCompressorSpecificSuffix() + ".mzML", ".mzML")).length());
        return decompressionResult;
    }
    
    public CompressionResult compress(File xmlFile, final String outputDirectory) throws IOException {
        compressionResult.setNonCompressedSize(xmlFile.length());
        stampStart(true);
        doCompressionOrDecompression(xmlFile, outputDirectory, true);
        stampStop(true);
        compressionResult.setCompressedSize(new File(outputDirectory + xmlFile.getName().replace(".mzML", getCompressorSpecificSuffix() + ".mzML")).length());
        return compressionResult;
    }
    
    public CV getCV() {
        CV cv = new CV();
        cv.setFullName("PSI-MS");
        cv.setId("MS");
        return cv;
    }
    
    private static void copyNonSpectraData(MzMLUnmarshaller source, MzML destination) {
        //... CV list ...//                
        System.out.println("- Reading /cvList");
        CVList cvl = source.unmarshalFromXpath("/cvList", CVList.class);
        destination.setCvList(cvl);

        //... File Description ...//
        System.out.println("- Reading /fileDescription");
        FileDescription fdList = source.unmarshalFromXpath("/fileDescription", FileDescription.class);
        destination.setFileDescription(fdList);

        //... Referenceable Param Group ...//
        System.out.println("- Reading /referenceableParamGroupList");
        ReferenceableParamGroupList refList = source.unmarshalFromXpath("/referenceableParamGroupList", ReferenceableParamGroupList.class);
        destination.setReferenceableParamGroupList(refList);

        //... Software List ...//
        System.out.println("- Reading /softwareList");
        uk.ac.ebi.jmzml.model.mzml.SoftwareList softList = source.unmarshalFromXpath("/softwareList", uk.ac.ebi.jmzml.model.mzml.SoftwareList.class);
        destination.setSoftwareList(softList);

        //... InstrumentConfiguration ...//
        System.out.println("- Reading /instrumentConfigurationList");
        InstrumentConfigurationList instConfList = source.unmarshalFromXpath("/instrumentConfigurationList", InstrumentConfigurationList.class);
        destination.setInstrumentConfigurationList(instConfList);

        //... Data Processing List ...//
        System.out.println("- Reading /dataProcessingList");
        uk.ac.ebi.jmzml.model.mzml.DataProcessingList dataProcList = source.unmarshalFromXpath("/dataProcessingList", uk.ac.ebi.jmzml.model.mzml.DataProcessingList.class);
        destination.setDataProcessingList(dataProcList);
        
        
    }
    
    private static Run getNewRun() {
        Run run = new Run();        
        run.setStartTimeStamp(Calendar.getInstance());
        run.setDefaultSourceFileRef("RAW1");
        run.setDefaultInstrumentConfigurationRef("IC1");
        return run;
    }
    
    protected void doCompressionOrDecompression(File xmlFile, String outputDirectory, boolean doingCompression) throws IOException {
        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
        MzML mzml = new MzML();
        copyNonSpectraData(unmarshaller, mzml);

        //... Runs ...//
        Run runToWrite = getNewRun();
        runToWrite.setId(xmlFile.getName());

        SpectrumList specListW = new SpectrumList();
        specListW.setDefaultDataProcessingRef("pwiz_Reader_conversion");

        //... Spectra ...//
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        int iSpectrum = 0;
        while (spectrumIterator.hasNext()) {
            if (iSpectrum == 87) {
                System.out.println("Hello!");
            }
            
            Spectrum spectrum = spectrumIterator.next();

            //System.out.println(spectrum.getId());
            //... Write spectrum ...//
            //... Attributes ...//
            Spectrum spectrumW = new Spectrum();
            spectrumW.setDefaultArrayLength(spectrum.getDefaultArrayLength());
            spectrumW.setIndex(spectrum.getIndex());
            spectrumW.setId(spectrum.getId());

            //... cvParams ...//
            spectrumW.getCvParam().addAll(spectrum.getCvParam());

            //... Scan List ...//
            spectrumW.setScanList(spectrum.getScanList());

            //... Precursor List ...//
            spectrumW.setPrecursorList(spectrum.getPrecursorList());
            
            BinaryDataArrayList binaryListForNewData = new BinaryDataArrayList();
            if (doingCompression) {
                compressBinaryDataArrayList(spectrum.getBinaryDataArrayList(), binaryListForNewData);
            } else {
                System.out.println("Decoding spectrum " + iSpectrum);
                decompressBinaryDataArrayList(spectrum.getBinaryDataArrayList(), binaryListForNewData);
            }
            
            spectrumW.setBinaryDataArrayList(binaryListForNewData);
            specListW.getSpectrum().add(spectrumW);

            iSpectrum++;
        }

        specListW.setCount(iSpectrum);

        System.out.println("- Writing spectrumList");
        runToWrite.setSpectrumList(specListW);

        System.out.println("- Writing /run/chromatogramList");
        ChromatogramList chromat = unmarshaller.unmarshalFromXpath("/run/chromatogramList", ChromatogramList.class);
        runToWrite.setChromatogramList(chromat);

        mzml.setVersion("1.1.0");
        mzml.setId(xmlFile.getName());
        mzml.setRun(runToWrite);
        MzMLMarshaller marshaller = new MzMLMarshaller();
        Writer writer;
        if (doingCompression) {        
            writer = new FileWriter(outputDirectory + "\\" + xmlFile.getName().replace(".mzML", getCompressorSpecificSuffix() + ".mzML"));
        } else {
            writer = new FileWriter(outputDirectory + "\\" + xmlFile.getName().replace(getCompressorSpecificSuffix() + ".mzML", ".mzML"));
        }
        
        marshaller.marshall(mzml, writer);
        writer.close();
    }   
    
    protected abstract void compressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination);
    protected abstract void decompressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination);
    public abstract String getCompressorSpecificSuffix();
}
