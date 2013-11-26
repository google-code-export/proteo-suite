/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils;

/**
 *
 * @author SPerkins
 */
public class DeltaConversion {
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
    
    public static double[] fromDeltaDoubleFormat(double[] deltaData) {
        double[] sourceData = new double[deltaData.length];
        sourceData[0] = deltaData[0];
        for (int i = 1; i < deltaData.length; i++) {
            sourceData[i] = sourceData[i - 1] + deltaData[i];
        }
        
        return sourceData;
    }
    
    public static void toDeltaNumberFormat(Number[] sourceData) {
        for (int i = sourceData.length - 1; i > 0; i--) {
            if (sourceData[i] instanceof Double) {
                sourceData[i] = (Double)sourceData[i] - (Double)sourceData[i - 1];
            } else if (sourceData[i] instanceof Float) {
                sourceData[i] = (Float)sourceData[i] - (Float)sourceData[i - 1];
            }            
        }
    }
    
    public static class DeltaSourceDataFormatException extends Exception {

        public DeltaSourceDataFormatException() {
            super("Data is not in ascending order. The array may now be corrupt.");
        }
    }
}
