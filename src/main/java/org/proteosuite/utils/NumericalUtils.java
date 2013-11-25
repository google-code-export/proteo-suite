package org.proteosuite.utils;

public class NumericalUtils {

	public static Number[] decodeMzDeltas(Number[] mzNumbers) {

		// ... Storing normal values ...//
		if (mzNumbers == null)
			return mzNumbers;
		
		double previous = 0.0d;
		for (int iI = 0; iI < mzNumbers.length; iI++) {
			previous = mzNumbers[iI].doubleValue() + previous;
			mzNumbers[iI] = previous;
		}
		
		return mzNumbers;
	}

	/*---------------------------------------
	 * Round a number to n decimals
	 * @param fValue - Value
	 * @param iDecimals - Number of decimals
	 * @return Number rounded
	 ----------------------------------------*/
	public static float Round(float fValue, int iDecimals) {
		float p = (float) Math.pow(10, iDecimals);
		fValue *= p;
		float tmp = Math.round(fValue);
		return (float) tmp / p;
	}

	/*---------------------------------------
	 * Truncate a number to n decimals
	 * @param fValue - Value
	 * @param iDecimals - Number of decimals
	 * @return Number truncated
	 ----------------------------------------*/
	public static double Truncate(double fValue, int iDecimals) {
		double multiplier = Math.pow(10, iDecimals);
		
		return Math.floor(multiplier * fValue) / multiplier;
	}
}
