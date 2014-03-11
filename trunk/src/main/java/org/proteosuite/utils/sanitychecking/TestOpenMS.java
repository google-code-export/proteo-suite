/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.utils.sanitychecking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.RawMzMLFile;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;

/**
 *
 * @author SPerkins
 */
public class TestOpenMS {

    private static String directory = "e:\\data\\label_free";
    private static String[] rawFiles = new String[]{"mam_042408o_CPTAC_study6_6B011.mzML", "mam_042408o_CPTAC_study6_6C008.mzML",
        "mam_042408o_CPTAC_study6_6D004.mzML", "mam_042408o_CPTAC_study6_6E004.mzML"};
    private static List<RawDataFile> dataFiles = new ArrayList<RawDataFile>();

    public static void main(String[] args) {
        for (String rawFile : rawFiles) {
            dataFiles.add(new RawMzMLFile(new File(directory + "\\" + rawFile)));
        }

        OpenMSLabelFreeWrapper openms = new OpenMSLabelFreeWrapper(dataFiles);
        openms.compute();
    }
}
