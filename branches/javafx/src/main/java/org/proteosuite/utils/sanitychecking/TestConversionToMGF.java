/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.sanitychecking;

import java.io.File;
import org.proteosuite.utils.FileFormatUtils;

/**
 *
 * @author SPerkins
 */
public class TestConversionToMGF {
    public static void main(String[] args) {
        boolean success = FileFormatUtils.mzMLToMGF(new File("e:\\data\\puf3\\20130123_JC.1.mzML"), "e:\\data\\puf3\\20130123_JC.1_from_proteosuite.mgf");
        System.out.println(success ? "Successful conversion!" : "Failed conversion");
    }
}
