/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.jopenms.util;

import java.util.Collection;
import java.util.List;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * General utilities class
 *
 * @author Da Qi
 *
 */
public class Utils {


    /**
     * Change the String to contain all the character that isJavaIdentifierPart. Replace '-' to '_'.
     *
     * @param original original String
     *
     * @return the changed String
     */
    public static String nameEncode(String original) {
        return original.replace("-", "_");
    }

    public static String nameDecode(String original) {
        return original.replace("_", "-");
    }

    public static Options combineOptions(Options optionsA, Options optionsB) {
        Options options = new Options();
        if (optionsA == null && optionsB == null) {
            return null;
        }
        else if (optionsA == null) {
            return optionsB;
        }
        else if (optionsB == null) {
            return optionsA;
        }
        else {
            Collection<Option> a = optionsA.getOptions();
            for (Option opt : a) {
                options.addOption(opt);
            }
            Collection<Option> b = optionsB.getOptions();
            for (Option opt : b) {
                options.addOption(opt);
            }
        }

        return options;
    }
    
    public static String join(List<String>stringArray) {
        StringBuilder builder = new StringBuilder();
        for (String value : stringArray) {
            builder.append(value);
            builder.append(" ");
        }
        
        return builder.toString().trim();
    }

}
