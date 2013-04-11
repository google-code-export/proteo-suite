/*
 * --------------------------------------------------------------------------
 * SystemUtils.java
 * --------------------------------------------------------------------------
 * Description:       System Utilities
 * Developer:         Faviel Gonzalez
 * Created:           27 February 2013
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * System utilities for ProteoSuite
 * @author faviel
 */
public class SystemUtils {
    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void CheckMemory(String sTitle) {         
        int mb = 1024*1024;        
        Runtime runtime = Runtime.getRuntime();         
        System.out.println("Memory check at " + sTitle + " ... ");         
        System.out.println("Used Memory:" + ((runtime.totalMemory() - runtime.freeMemory()) / mb) + " MB");
        System.out.println("Free Memory:" + (runtime.freeMemory() / mb) + " MB");
        System.out.println("Total Memory:" + (runtime.totalMemory() / mb) + " MB");
        System.out.println("Max Memory:" + (runtime.maxMemory() / mb) + " MB");
    }    
    public boolean CheckURL(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
}
