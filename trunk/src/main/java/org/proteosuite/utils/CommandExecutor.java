package org.proteosuite.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author SPerkins
 */
public class CommandExecutor {
    public static boolean outputContains(String command, String searchString) {
        Runtime rt = Runtime.getRuntime();
        StringBuilder output = new StringBuilder();
        try {
            Process p = rt.exec(command);
            InputStream inStream = p.getInputStream();
            int value;
            while ((value = inStream.read()) != -1) {
                output.append((char) value);                     
            }
            
            if (output.toString().contains(searchString)) {
                return true;
            }
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }        
        
        return false;
    }
}
