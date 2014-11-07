/*
 * --------------------------------------------------------------------------
 * SystemUtils.java
 * --------------------------------------------------------------------------
 * Description:       System Utilities
 * Developer:         fgonzalez
 * Created:           27 February 2013
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * System utilities for ProteoSuite
 *
 * @author fgonzalez
 */
public class SystemUtils {

    private static final int MEGABYTE = 1024 * 1024;
    private static final Runtime runtime = Runtime.getRuntime();

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public long getUsedMemory() {
        return (runtime.totalMemory() - runtime.freeMemory()) / MEGABYTE;
    }

    public long getFreeMemory() {
        return runtime.freeMemory() / MEGABYTE;
    }

    public long getTotalMemory() {
        return runtime.totalMemory() / MEGABYTE;
    }

    public long getMaxMemory() {
        return runtime.maxMemory() / MEGABYTE;
    }

    public boolean CheckURL(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName)
                    .openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getAvailableProcessors() {
        return runtime.availableProcessors();
    }

    public String getRuntimeInfo() {
        return System.getProperty("java.version") + " " + System.getProperty("os.arch");
    }

    public static boolean deleteRecursive(File path) throws FileNotFoundException {
        if (!path.exists()) {
            throw new FileNotFoundException(path.getAbsolutePath());
        }

        boolean allDeleted = true;
        File[] children = path.listFiles();
        for (File child : children) {
            if (child.isFile()) {
                if (!child.delete()) {
                    allDeleted = false;
                }
            } else if (child.isDirectory()) {
                if (!deleteRecursive(child)) {
                    allDeleted = false;
                }
            }
        }
        
        return allDeleted ? path.delete() : false;       
    }
}