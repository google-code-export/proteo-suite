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
import java.util.Iterator;
import java.util.List;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
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
    
    public void stampStart() {
        compressionResult.setStart(System.nanoTime());
    }
    
    public void stampStop() {
        compressionResult.setStop(System.nanoTime());        
    }
    
    public static void staticDecompress(File xmlFile, String outputDirectory) {
    
    }
    
    public DecompressionResult decompress(File xmlFile, String outputDirectory) {
        return decompressionResult;
    }
    
    public CompressionResult compress(File xmlFile, String outputDirectory) throws IOException {
        compressionResult.setNonCompressedSize(xmlFile.length());
        stampStart();
        doCompression(xmlFile, outputDirectory);
        stampStop();
        compressionResult.setCompressedSize(new File(outputDirectory + xmlFile.getName().replace(".mzML", getCompressorSpecificSuffix() + ".mzML")).length());
        return compressionResult;
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
    
    protected void doCompression(File xmlFile, String sPath) throws IOException {
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
            
            BinaryDataArrayList binaryListForNewData = new BinaryDataArrayList();
            compressBinaryDataArrayList(spectrum.getBinaryDataArrayList(), binaryListForNewData);          
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
        Writer writer = new FileWriter(sPath + "\\" + xmlFile.getName().replace(".mzML", getCompressorSpecificSuffix() + ".mzML"));
        marshaller.marshall(mzml, writer);
        writer.close();
    }
    
    protected void doDecompression(File xmlFile, String outputDirectory) {
    
    }
    
    protected abstract void compressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination);
    protected abstract void decompressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination);
    public abstract String getCompressorSpecificSuffix();
}
