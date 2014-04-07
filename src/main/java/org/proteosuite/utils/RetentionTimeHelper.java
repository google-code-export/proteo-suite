package org.proteosuite.utils;

import uk.ac.liv.mzidlib.AddRetentionTimeToMzid;

/**
 * 
 * @author SPerkins
 */
public class RetentionTimeHelper {
	private RetentionTimeHelper() {
	}

	public static String fill(String rawFile, String mzidFile) {
		String ftFilledIn = mzidFile.replaceAll(".mzid", "_rt_corrected.mzid");
		new AddRetentionTimeToMzid(mzidFile, rawFile, ftFilledIn);
		return ftFilledIn;
	}
}
