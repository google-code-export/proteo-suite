/*
 * --------------------------------------------------------------------------
 * MzML2MGF.java
 * --------------------------------------------------------------------------
 * Description:       Plugin to Convert mzML files to MGF
 * Developer:         Faviel Gonzalez
 * Created:           09 February 2012
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * This plugin allows the conversion of MzML files to MGF, considering retention time in the scan title.
 * @param xmlFile a string containing the file name
 * @param sPath a string for the path where the file is located 
 * @author faviel
 */
public class MzML2MGF {
        
    private ArrayList<MzMLUnmarshaller> iUnmarshaller = null;    
    private SystemUtils SysUtils = new SystemUtils();
        
    public MzML2MGF(File xmlFile, String sPath) {        

        String paramFile = xmlFile.getName();
        paramFile = sPath + "/" + paramFile.replace(".mzML", ".mgf");
        String sOut = "";
        try{            
            FileWriter fstream = new FileWriter(paramFile);
            BufferedWriter out = new BufferedWriter(fstream);    

            //... Unmarshall data using jzmzML API ...//
            System.out.println(SysUtils.getTime()+" - Unmarshalling File: "+xmlFile.getName());
            iUnmarshaller = new ArrayList<MzMLUnmarshaller>();
            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
            iUnmarshaller.add(unmarshaller);
            System.out.println(SysUtils.getTime()+" - Unmarshalling fiinished!");

            //... Reading entire spectra ...//
            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            while (spectrumIterator.hasNext()){
                //... Reading a individual spectrum ...//
                Spectrum spectrum = spectrumIterator.next();                

                //... Reading CvParam to identify the MS level (1, 2) ...//
                String mslevel = "";
                List<CVParam> specParam = spectrum.getCvParam();
                for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    if (lCVParam.getAccession().equals("MS:1000511")){
                        mslevel = lCVParam.getValue().trim();
                    }
                }
                
                //... Getting Retention Time (rt) ...//
                float rt = 0;
                String unitRT = "";
                List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();){
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    if (lCVParam.getAccession().equals("MS:1000016")){
                        unitRT = lCVParam.getUnitAccession().trim();
                        if (unitRT.equals("UO:0000031")){    
                            rt = Float.parseFloat(lCVParam.getValue().trim()) * 60;
                        }
                        else{
                            rt = Float.parseFloat(lCVParam.getValue().trim());
                        }
                    }                        
                }            
                
                if (mslevel.toString().indexOf("2") >= 0){
                    PrecursorList plist = spectrum.getPrecursorList(); //... Get precursor ion ...//
                    if (plist != null){
                        if (plist.getCount().intValue() == 1){
                            List<CVParam> scanPrecParam = plist.getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam();
                            float parIonMz =0;
                            float peakIntensity =0;
                            int parCharge = 0;

                            //... Detect parent ion m/z and charge ...//
                            for (Iterator lCVParamIterator = scanPrecParam.iterator(); lCVParamIterator.hasNext();){
                                CVParam lCVParam = (CVParam) lCVParamIterator.next();
                                if (lCVParam.getAccession().equals("MS:1000744")){
                                    parIonMz = Float.parseFloat(lCVParam.getValue().trim());
                                }
                                if (lCVParam.getAccession().equals("MS:1000041")){
                                    parCharge = Integer.parseInt(lCVParam.getValue().trim());
                                }
                                if (lCVParam.getAccession().equals("MS:1000042")){
                                    peakIntensity = Float.parseFloat(lCVParam.getValue().trim());
                                }                            
                            }

                            //... Binary data ...//
                            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

                            //... Reading mz Values (Peaks) ...//
                            Number[] mzNumbers = null;
                            Number[] intenNumbers = null;                            

                            //... Reading mz and intensity values ...//
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
                            if (mzNumbers!=null){
                                int iJ=0;
                                float mzVal;
                                float intVal;
                                if (parCharge == 0) //... In case it is not specified ...//
                                {
                                    parCharge = 1;
                                }                            
                                out.write("BEGIN IONS");
                                out.newLine();

                                sOut = "TITLE=Scan:" + spectrum.getIndex() + ", (rt:" + rt + "), (id:" + spectrum.getId() + ")";
                                out.write(sOut);
                                out.newLine();
                                System.out.println(SysUtils.getTime()+" - Scan="+sOut);

                                sOut = "PEPMASS=" + String.format("%.4f", parIonMz) + " " + String.format("%.4f", peakIntensity);
                                out.write(sOut);
                                out.newLine();

                                sOut = "CHARGE=" + parCharge + "+";
                                out.write(sOut);
                                out.newLine();

                                sOut = "RTINSECONDS=" + String.format("%.4f", rt);
                                out.write(sOut);
                                out.newLine();

                                sOut = "SCANS=" + spectrum.getIndex();
                                out.write(sOut);
                                out.newLine();

                                while (iJ < mzNumbers.length){ 
                                    mzVal = mzNumbers[iJ].floatValue();
                                    intVal = intenNumbers[iJ].floatValue();
                                    if (intVal > 0.0001){
                                        sOut = String.format("%.4f", mzVal) + " " + String.format("%.4f", intVal);
                                        out.write(sOut);
                                        out.newLine();
                                    }
                                    iJ++;
                                }           
                                out.write("END IONS");
                                out.newLine();   
                            }
                        }
                    }  //... If precursor ion
                } //... If MS2    
            }   //... While
            out.close();                
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(0);                                
        }                
        return;
    }
}

