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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.params.BinaryDataArrayCVParam;

/**
 * This plugin allows the compression of MzML files by storing the mz and intensity values using Numpress compression.
 * @author fgonzalez
 * @author SPerkins
 */
public class MzMLNumpress extends MzMLCompressorBase implements MzMLCompressor {
    private final String LINEAR_COMPRESSION_ACCESSION = "MS:1002312";
    private final String PIC_COMPRESSION_ACCESSION = "MS:1002313";
    
    public MzMLNumpress() {
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
            if (numbers[i] instanceof Integer) {
                doubles[i] = (Integer) numbers[i];
            } else if (numbers[i] instanceof Float) {
                doubles[i] = (Float) numbers[i];
            } else if (numbers[i] instanceof Double) {
                doubles[i] = (Double) numbers[i];
            }           
        }

        return doubles;
    }
    
    @Override
    protected void compressBinaryDataArrayList(BinaryDataArrayList source, BinaryDataArrayList destination) {
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
                            break;
                        }
                    }
                    
                    if (cvp.getAccession().equals("MS:1000515")) {
                        if (bda.getEncodedLength() > 0) {
                            intenNumbers = bda.getBinaryDataAsNumberArray();
                            break;
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

                // Numpress code.
                byte[] encodedMzNumbers = new byte[8 + (mzNumbers.length * 5)];
                int encodedBytes = MSNumpress.encodeLinear(doublesFromNumbers(mzNumbers), mzNumbers.length, encodedMzNumbers, 
                        MSNumpress.optimalLinearFixedPoint(doublesFromNumbers(mzNumbers), mzNumbers.length));
                
                // Set the numpress compressed binary - note: can't set jmzml to use zlib when using this method.
                bdaW.setBinary(Arrays.copyOf(encodedMzNumbers, encodedBytes));
                bdaW.setEncodedLength(mzNumbers.length);
                
                // Set a CVParam for the numpress linear compression.
                CVParam linearCompressionParam = new BinaryDataArrayCVParam();
                linearCompressionParam.setAccession(LINEAR_COMPRESSION_ACCESSION);
                linearCompressionParam.setName("numpress linear compression");
                bdaW.getCvParam().add(linearCompressionParam);

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
                
                // Set a CVParam for the numpress pic compression.
                CVParam picCompressionParam = new BinaryDataArrayCVParam();
                picCompressionParam.setAccession(PIC_COMPRESSION_ACCESSION);
                picCompressionParam.setName("numpress pic compression");
                
                bda2W.getCvParam().add(picCompressionParam);

                CVParam cvParam2 = new BinaryDataArrayCVParam();
                cvParam2.setUnitCvRef("MS");
                cvParam2.setUnitName("number of counts");
                cvParam2.setAccession("MS:1000131");
                cvParam2.setName("intensity array");
                cvParam2.setAccession("MS:1000515");
                cvParam2.setCvRef("MS");
                bda2W.getCvParam().add(cvParam2);
                
                destination.getBinaryDataArray().add(bdaW);
                destination.getBinaryDataArray().add(bda2W);
                destination.setCount(2);
            } else {
                destination.setCount(0);
            }           
    }
    
    @Override
    protected void decompressBinaryDataArrayList(final BinaryDataArrayList source, BinaryDataArrayList destination) {
        for (BinaryDataArray dataArray : source.getBinaryDataArray()) {
            List<CVParam> paramList = dataArray.getCvParam();
            byte[] binary = dataArray.getBinary();
            double[] decodedDoubles = new double[dataArray.getEncodedLength()];
            BinaryDataArray outDataArray = new BinaryDataArray();
            for (int i = 0; i < paramList.size(); i++) {
                CVParam param = paramList.get(i);
                try {
                    if (param.getAccession().equals(LINEAR_COMPRESSION_ACCESSION)) {
                        int decodedDoubleCount = MSNumpress.decodeLinear(binary, binary.length, decodedDoubles);
                        if (decodedDoubleCount != dataArray.getEncodedLength()) {
                            throw new NumpressDecodeException();
                        }
                        
                        paramList.remove(i);
                        break;
                    } else if (param.getAccession().equals(PIC_COMPRESSION_ACCESSION)) {
                        int decodedDoubleCount = MSNumpress.decodePic(binary, binary.length, decodedDoubles);
                        if (decodedDoubleCount != dataArray.getEncodedLength()) {
                            throw new NumpressDecodeException();
                        }
                        
                        paramList.remove(i);
                        break;
                        
                    } else if (i == (paramList.size() - 1)) {
                        throw new NumpressDecodeException("Data is not numpress encoded.");
                    }                   
                } catch (NumpressDecodeException n) {
                    System.out.println("Problem decoding numpress data. Data has not been decoded.");                    
                }               
            } 
            
            outDataArray.getCvParam().addAll(paramList);
            outDataArray.set64BitFloatArrayAsBinaryData(decodedDoubles, true, getCV());
            outDataArray.setEncodedLength(decodedDoubles.length);
            destination.getBinaryDataArray().add(outDataArray);
        }
        
        destination.setCount(destination.getBinaryDataArray().size());
    }
    
    public static class NumpressDecodeException extends Exception {
        public NumpressDecodeException() {
            this("Data length decoded did not match data length decoded.");
        }
        
        public NumpressDecodeException(String message) {
            super(message);
        }
    }
}
