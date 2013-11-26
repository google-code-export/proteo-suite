package org.proteosuite.utils;

public class BinaryUtils {

	/**
	 * Assign mz values in a resampled array using linear interpolation
	 * @param array - bidimensional array that contains m/z and int values
	 * @param mzArray - m/z values
	 * @param intArrray2 - intensity values
	 * @param mzWindow - m/z window
	 * @param debug - true/false
	 * @return array with values assigned
	 **/
	public static float[][] binArray(float[][] array, Number[] mzArray,
			Number[] intArray, int mzWindow, boolean debug) {
		// ... The dimension of the array has to be same length as the one
		// provided in the resampling array ...//
		float[][] arrayret = null;
		arrayret = new float[array.length][array[0].length];

		// ... Convert bidimensional array into one dimension (m/z values for
		// binary search) ...//
		Number[] onedimension = new Number[array[0].length];
		for (int iI = 0; iI < array[0].length; iI++) {
			onedimension[iI] = array[0][iI];
		}
		arrayret = array; // ... Contains initialised locations (with 0 values)
							// ...//
		// ... Clean intensity values ...//
		for (int iI = 0; iI < arrayret[0].length; iI++) {
			arrayret[1][iI] = 0.0f;
		}

		int iLocation = 0; // ... Location in the array ...//
		float fMzRef = 0.0f, fX1 = 0.0f, fX2 = 0.0f, fDx1 = 0.0f, fDx2 = 0.0f, fDx1x2 = 0.0f, fValX1 = 0.0f, fValX2 = 0.0f;
		for (int iI = 0; iI < intArray.length; iI++) {
			// ... Perform binary search to find the location of the m/z value
			// in the resampling array ..//
			if (intArray[iI].floatValue() > 0
					&& mzArray[iI].floatValue() < mzWindow) { // ... Only data
																// with
																// intensities
																// higher than 0
																// and within
																// the m/z
																// window ...//
				iLocation = binarySearch(onedimension, 0, onedimension.length,
						mzArray[iI].floatValue(), false);
				// ... Validate indexes (first and last position) ...//
				if (iLocation > 0 && iLocation < onedimension.length) {
					// ... Perform linear interpolation ...//

					// ... Get m/z value ...//
					fMzRef = mzArray[iI].floatValue();
					System.out.println("Look for m/z=" + fMzRef + "\tInt="
							+ intArray[iI].floatValue());
					if (fMzRef < onedimension[iLocation].floatValue()) {
						fX1 = onedimension[iLocation - 1].floatValue();
						fX2 = onedimension[iLocation].floatValue();
						System.out.println("iLocation-1=" + fX1);
						System.out.println("iLocation=" + iLocation + "\tMz="
								+ fX2);
					} else {
						fX1 = onedimension[iLocation].floatValue();
						fX2 = onedimension[iLocation + 1].floatValue();
						System.out.println("iLocation=" + iLocation + "\tMz="
								+ fX1);
						System.out.println("iLocation+1=" + fX2);
					}
					fDx1 = Math.abs(fX1 - fMzRef);
					System.out.println("fDx1=" + fDx1);
					fDx2 = Math.abs(fX2 - fMzRef);
					System.out.println("fDx2=" + fDx2);
					fDx1x2 = Math.abs(fX1 - fX2); // ... 100 % ...//
					System.out.println("fDx1x2=" + fDx1x2);
					fValX1 = Math.abs(1 - fDx1 / fDx1x2);
					System.out.println("fValX1=" + fValX1);
					fValX2 = Math.abs(1 - fDx2 / fDx1x2);
					System.out.println("fValX2=" + fValX2);
					if (fMzRef < onedimension[iLocation].floatValue()) {
						arrayret[1][iLocation - 1] = arrayret[1][iLocation - 1]
								+ intArray[iI].floatValue() * fValX1;
						arrayret[1][iLocation] = arrayret[1][iLocation]
								+ intArray[iI].floatValue() * fValX2;
						System.out.println(arrayret[1][iLocation - 1]
								+ " goes to index = " + (iLocation - 1));
						System.out.println(+arrayret[1][iLocation]
								+ " goes to index = " + (iLocation));
					} else {
						arrayret[1][iLocation] = arrayret[1][iLocation]
								+ intArray[iI].floatValue() * fValX1;
						arrayret[1][iLocation + 1] = arrayret[1][iLocation + 1]
								+ intArray[iI].floatValue() * fValX2;
						System.out.println(arrayret[1][iLocation]
								+ " goes to index = " + (iLocation));
						System.out.println(arrayret[1][iLocation + 1]
								+ " goes to index = " + (iLocation + 1));
					}
				}
			}
		}
		if (debug) {
			System.out.println("Print binned array...");
			for (int iD = 0; iD < arrayret[0].length; iD++) {
				System.out.println("mz=" + arrayret[0][iD] + "\tint="
						+ arrayret[1][iD]);
			}
		}
		return arrayret;
	}

	/**
	 * Binary search
	 * @param nArray - Array
	 * @param iLowerBound - From position
	 * @param iUpperBound - To position
	 * @param fKey - Value to look for
	 * @param debug - debug mode
	 * @return position in the array 
	 **/
	public static int binarySearch(Number[] nArray, int iLowerBound, int iUpperBound,
			float fKey, boolean debug) {
		int iPos;
		int iCompCount = 1;

		iPos = (iLowerBound + iUpperBound) / 2;
		while ((NumericalUtils.Round(nArray[iPos].floatValue(), 4) != NumericalUtils.Round(fKey, 4))
				&& (iLowerBound <= iUpperBound)) {
			iCompCount++;
			if (NumericalUtils.Round(nArray[iPos].floatValue(), 4) > NumericalUtils.Round(fKey, 4)) {
				iUpperBound = iPos - 1;
			} else {
				iLowerBound = iPos + 1;
			}
			iPos = (iLowerBound + iUpperBound) / 2;
		}
		if (debug) {
			System.out.println("Searching for m/z = " + NumericalUtils.Round(fKey, 4)
					+ " in array");
			if (iLowerBound <= iUpperBound) {
				System.out.println("The number was found in array at position "
						+ iPos);
				System.out.println("The binary search found the number after "
						+ iCompCount + " comparisons.");
			} else {
				System.out
						.println("Sorry, the number is not in this array. The binary search made "
								+ iCompCount + " comparisons.");
				System.out.println("The closest indexes were iLowerBound="
						+ iLowerBound + " and iUpperBound=" + iUpperBound);
				iPos = iLowerBound;
				if (Math.abs(fKey - nArray[iPos].floatValue()) > Math.abs(fKey
						- nArray[iPos - 1].floatValue())) {
					iPos = iPos - 1;
				}
				System.out.println("The pointer was set up to Index=" + iPos);
			}
		}
		return iPos;
	}
}