package org.proteosuite.utils;

public final class PrimitiveUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private PrimitiveUtils() {
    }

    /**
     * Round a number to n decimals
     *
     * @param fValue Value
     * @param iDecimals Number of decimals
     * @return Number rounded
     */
    public static float round(float fValue, int iDecimals) {
        float p = (float) Math.pow(10, iDecimals);
        fValue *= p;
        float tmp = Math.round(fValue);
        return (float) tmp / p;
    }

    /**
     * Truncate a number to n decimals
     *
     * @param fValue Value
     * @param iDecimals Number of decimals
     * @return Number truncated
     */
    public static double truncate(double fValue, int iDecimals) {
        double multiplier = Math.pow(10, iDecimals);

        return Math.floor(multiplier * fValue) / multiplier;
    }

    public static boolean isDouble(String candidate) {
        try {
            double validatedDouble = Double.parseDouble(candidate);
        } catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isInteger(String candidate) {
        try {
            int validatedInt = Integer.parseInt(candidate);
        } catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    } 
}
