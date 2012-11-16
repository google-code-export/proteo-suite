/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

/**
 *
 * @author faviel
 */
public class XIC {
    
    public XIC(String xmlFile, double rtStarts, double rtEnds, double mzStarts, double mzEnds) {
        //... The index corresponds to files that have been unmarshalled ...//

        File file = new File(xmlFile);
        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(file);
        
        //... Iterate in the rt dimension ...//
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        while (spectrumIterator.hasNext()){
            Spectrum spectrum = spectrumIterator.next();            
            
            //... Identify MS1 data ...//
            String mslevel = "";                        
            List<CVParam> specParam = spectrum.getCvParam();       
            for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                CVParam lCVParam = (CVParam) lCVParamIterator.next();
                if (lCVParam.getAccession().equals("MS:1000511")){
                    mslevel = lCVParam.getValue().trim();
                }
            }            
            if (mslevel.equals("1")){
                //... Get rt from spectrum ...//
                double rt = 0.0;
                String unitRT = "";
                List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();){
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    if (lCVParam.getAccession().equals("MS:1000016")){
                        unitRT = lCVParam.getUnitAccession().trim();
                        if (unitRT.equals("UO:0000031")) //... Validating rt unit (mins or secs) ...//
                        {    
                            rt = Float.parseFloat(lCVParam.getValue().trim()) * 60;
                        }
                        else{
                            rt = Float.parseFloat(lCVParam.getValue().trim());
                        }
                    }    
                }
                //... Get XIC across intervals ...//
                if (rt>=rtStarts&&rt<=rtEnds){
                    Number[] mzNumbers = null;
                    Number[] intenNumbers = null;
                    //... Reading mz Values ...//
                    List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
                    for (BinaryDataArray bda:bdal){
                        List<CVParam> cvpList = bda.getCvParam();
                        for (CVParam cvp:cvpList){                            
                            if(cvp.getAccession().equals("MS:1000514")){
                                mzNumbers = bda.getBinaryDataAsNumberArray();                                
                            }
                            if(cvp.getAccession().equals("MS:1000515")){
                                intenNumbers = bda.getBinaryDataAsNumberArray();                                
                            }
                        }
                    }                        
                    //... Generating XIC ...//
                    double intensXIC = 0.0;
                    if(mzNumbers!=null){
                        for (int iI=0; iI<mzNumbers.length;iI++){
                            if(mzNumbers[iI].doubleValue()>=mzStarts&&mzNumbers[iI].doubleValue()<=mzEnds){
                                intensXIC+=intenNumbers[iI].doubleValue();                                    
                            }
                        }
                        System.out.println("RT="+rt+"\tInt="+intensXIC);
                    }
                }
            }
        }        
    }
}
