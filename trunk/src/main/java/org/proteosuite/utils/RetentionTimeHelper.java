/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils;

//import uk.ac.liv.mzidlib.AddRetentionTimeToMzid;

/**
 *
 * @author SPerkins
 */
public class RetentionTimeHelper {
    private RetentionTimeHelper() {}
    public static String fill(String rawFile, String mzidFile) {
        String ftFilledIn = mzidFile.replaceAll(".mzid", "_rt_corrected.mzid");
        //new AddRetentionTimeToMzid(mzidFile, rawFile, ftFilledIn);
        return ftFilledIn;
    }
}
