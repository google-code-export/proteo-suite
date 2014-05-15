/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.sanitychecking;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;

/**
 *
 * @author SPerkins
 */
public class CompareMGF {
    public static void main(String[] args) {
        try {
            String file1 = "e:\\data\\puf3\\20130123_JC.1_from_proteosuite.mgf";
            String file2 = "e:\\data\\puf3\\20130123_JC.1.mgf";
            
            MgfFile mgf1 = new MgfFile(new File(file1));
            MgfFile mgf2 = new MgfFile(new File(file2));
            
            System.out.println(mgf1.getSpectraCount() + "\t" + mgf2.getSpectraCount());
            
            
        } catch (JMzReaderException ex) {
            Logger.getLogger(CompareMGF.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    
    }
}
