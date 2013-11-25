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
 * This plugin allows the compression of MzML files by storing deltas on 
 * m/z values and removing zeros on intensity values
 * @param xmlFile a string containing the file name
 * @param sPath a string for the path where the file is located 
 * @author fgonzalez
 */
public class MzMLNumpress {
    public MzMLNumpress(File xmlFile, String sPath, boolean bZeros) throws IOException{
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
            while (spectrumIterator.hasNext()){
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
                double[] mz = null;
                double[] nonzerosMz = null;            
                int mzCount = 0;

                Number[] intenNumbers = null;
                double[] intensities = null;
                double[] nonzerosIntens = null;            

                List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
                for (BinaryDataArray bda:bdal){
                    List<CVParam> cvpList = bda.getCvParam();
                    for (CVParam cvp:cvpList){
                        if(cvp.getAccession().equals("MS:1000514")){
                            if (bda.getEncodedLength()>0){
                                mzNumbers = bda.getBinaryDataAsNumberArray();
                            }
                        }
                        if(cvp.getAccession().equals("MS:1000515")){
                            if (bda.getEncodedLength()>0){
                                intenNumbers = bda.getBinaryDataAsNumberArray();
                            }
                        }
                    }
                }

                //... Storing delta values ...//
                if (mzNumbers != null){
                    mz = new double[mzNumbers.length];
                    intensities = new double[intenNumbers.length];
                    mzCount = mzNumbers.length;

                    //... Copy Numbers into doubles ...//
                    for (int iI = 0; iI < mzNumbers.length; iI++){
                        mz[iI] = mzNumbers[iI].doubleValue(); 
                        intensities[iI] = intenNumbers[iI].doubleValue();
                    }
                    //... Removing zero values ...//
                    List<Double> zeroMz = new ArrayList<Double>();
                    List<Double> zeroIntens = new ArrayList<Double>();
                    double last=0.0d;
                    for (int iI = 0; iI < mzNumbers.length; iI++){  
                        if(bZeros){
                            if (intensities[iI]>0){
                                zeroMz.add(mz[iI]-last);    //... Storing Deltas ...//
                                zeroIntens.add(intensities[iI]);
                                last=mz[iI];
                            }
                        }else{
                            zeroMz.add(mz[iI]-last);    //... Storing Deltas ...//
                            zeroIntens.add(intensities[iI]);
                            last=mz[iI];                            
                        }
                    }
                    nonzerosMz = new double[zeroMz.size()];
                    nonzerosIntens = new double[zeroIntens.size()];
                    int iI=0;
                    for(Double d:zeroMz){
                        nonzerosMz[iI++] = d.doubleValue();
                    }
                    iI=0;
                    for(Double d:zeroIntens){
                        nonzerosIntens[iI++] = d.doubleValue();
                    }
                }

                //... Transforming data into Binary ...//
                BinaryDataArray bdaW = new BinaryDataArray();
                BinaryDataArray bda2W = new BinaryDataArray();

                if (mzCount>0){
                    CV cv = new CV();
                    cv.setFullName("PSI-MS");
                    cv.setId("MS");
                    bdaW.set64BitFloatArrayAsBinaryData(nonzerosMz, true, cv);
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

                    bda2W.set64BitFloatArrayAsBinaryData(nonzerosIntens, true, cv);
                    CVParam cvParam2 = new BinaryDataArrayCVParam();
                    cvParam2.setUnitCvRef("MS");
                    cvParam2.setUnitName("number of counts");
                    cvParam2.setAccession("MS:1000131");
                    cvParam2.setName("intensity array");
                    cvParam2.setAccession("MS:1000515");
                    cvParam2.setCvRef("MS");
                    bda2W.getCvParam().add(cvParam2);
                    bda2W.getCvParam().add(cvParam1_2);                            
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
            
            String sNumpress = xmlFile.getName();
            sNumpress = sNumpress.replace(".mzML", "_compress.mzML");
            Writer writer = new FileWriter(sPath+"\\"+sNumpress);
            marshaller.marshall(mzml, writer);
            writer.close();     
    }
}
