/**
 * --------------------------------------------------------------------------
 * DeltaConversion.java
 * --------------------------------------------------------------------------
 * Description: Utility to perform delta conversion Developer: SPerkins Created:
 * 26 November 2013 Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/ Project Website:
 * http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

/**
 * Class for converting back and forth from the delta number format.
 *
 * @author SPerkins
 */
public final class DeltaConversion {

    /**
     * Private constructor to prevent instantiation.
     */
    private DeltaConversion() {
    }

    /**
     * Converts double data to the delta format.
     * @param sourceData The original double data.
     * @return Delta double data.
     * @throws org.proteosuite.utils.DeltaConversion.DeltaSourceDataFormatException
     */
    public static double[] toDeltaDoubleFormat(final double[] sourceData) throws DeltaSourceDataFormatException {
        double[] deltaData = new double[sourceData.length];
        deltaData[0] = sourceData[0];
        for (int i = 1; i < sourceData.length; i++) {
            deltaData[i] = sourceData[i] - sourceData[i - 1];
            if (deltaData[i] < 0d) {
                throw new DeltaSourceDataFormatException();
            }
        }

        return deltaData;
    }

    /**
     * Converts double data from the delta format.
     * @param deltaData The delta double data.
     * @return Original double data.
     * @throws org.proteosuite.utils.DeltaConversion.DeltaEncodedDataFormatException
     */
    public static double[] fromDeltaDoubleFormat(final double[] deltaData) throws DeltaEncodedDataFormatException {
        double[] sourceData = new double[deltaData.length];
        sourceData[0] = deltaData[0];
        for (int i = 1; i < deltaData.length; i++) {
            if (deltaData[i] < 0d) {
                throw new DeltaEncodedDataFormatException();
            }
            
            sourceData[i] = sourceData[i - 1] + deltaData[i];
        }

        return sourceData;
    }

    /**
     * Converts Number data to the delta format, changing the original array.
     * @param sourceData Number array.
     * @throws org.proteosuite.utils.DeltaConversion.DeltaSourceDataFormatException 
     */
    public static void toDeltaNumberFormat(Number[] sourceData) throws DeltaSourceDataFormatException {
        for (int i = sourceData.length - 1; i > 0; i--) {
            if (sourceData[i] instanceof Double) {
                sourceData[i] = (Double) sourceData[i] - (Double) sourceData[i - 1];
                if ((Double) sourceData[i] < 0d) {
                    throw new DeltaSourceDataFormatException();
                }
            } else if (sourceData[i] instanceof Float) {
                if ((Float) sourceData[i] < 0f) {
                    throw new DeltaSourceDataFormatException();
                }

                sourceData[i] = (Float) sourceData[i] - (Float) sourceData[i - 1];
            }
        }
    }

    /**
     * Converts Number data from the delta format, changing the original array.
     * @param deltaData Number array.
     * @throws org.proteosuite.utils.DeltaConversion.DeltaEncodedDataFormatException 
     */
    public static void fromDeltaNumberFormat(Number[] deltaData) throws DeltaEncodedDataFormatException {
        for (int i = 1; i < deltaData.length; i++) {
            if (deltaData[i] instanceof Double) {
                if ((Double) deltaData[i] < 0d) {
                    throw new DeltaEncodedDataFormatException();
                }

                deltaData[i] = (Double) deltaData[i] + (Double) deltaData[i - 1];
            } else if (deltaData[i] instanceof Float) {
                if ((Float) deltaData[i] < 0f) {
                    throw new DeltaEncodedDataFormatException();
                }

                deltaData[i] = (Float) deltaData[i] + (Float) deltaData[i - 1];
            }
        }
    }

    public static class DeltaSourceDataFormatException extends Exception {
        private static final long serialVersionUID = 2001261542747200559L;

        public DeltaSourceDataFormatException() {
            super("Data is not in ascending order. The array may now be corrupt.");
        }
    }

    public static class DeltaEncodedDataFormatException extends Exception {

        public DeltaEncodedDataFormatException() {
            super("Delta data contains negative values. The array may now be corrupt.");
        }
    }
}
