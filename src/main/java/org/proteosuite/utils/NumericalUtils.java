package org.proteosuite.utils;

public final class NumericalUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private NumericalUtils() {
    }
    /*---------------------------------------
     * round a number to n decimals
     * @param fValue - Value
     * @param iDecimals - Number of decimals
     * @return Number rounded
     ----------------------------------------*/

    public static float round(float fValue, int iDecimals) {
        float p = (float) Math.pow(10, iDecimals);
        fValue *= p;
        float tmp = Math.round(fValue);
        return (float) tmp / p;
    }

    /*---------------------------------------
     * truncate a number to n decimals
     * @param fValue - Value
     * @param iDecimals - Number of decimals
     * @return Number truncated
     ----------------------------------------*/
    public static double truncate(double fValue, int iDecimals) {
        double multiplier = Math.pow(10, iDecimals);

        return Math.floor(multiplier * fValue) / multiplier;
    }
}
