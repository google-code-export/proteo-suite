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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.proteosuite.executor.Executor;

/**
 * System utilities for ProteoSuite
 *
 * @author fgonzalez
 */
public class SystemUtils {

    private static final int MEGABYTE = 1024 * 1024;
    private static final Runtime runtime = Runtime.getRuntime();
    private static final Map<String, File> previousFindCommandResults = new HashMap<>();
    private static final Set<File> previousFindCommandDirectories = new HashSet<>();
    private static final Map<String, File> previousFindFileResults = new HashMap<>();    
    private static SystemType TYPE = null;

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
        }
        catch (IOException e) {
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

    public static File findExecutionCommand(String command) {
        return findExecutionCommand(command, Collections.emptyList());
    }

    public static File findExecutionCommand(String command, Iterable<File> locationHints) {
        // First let's see if we have seen this command before in this session.
        if (previousFindCommandResults.containsKey(command)) {
            return previousFindCommandResults.get(command);
        }

        // We haven't seen it before, so let's see if the command is on the system path.
        // We use the 'where' command for windows, and the 'which' command for linux.
        SystemType type = getSystemType();
        Executor exec = null;
        switch (type) {
            case WIN32:
            case WIN64:
                exec = new Executor(findFileName("where.exe", Arrays.asList(File.listRoots())));
                break;
            case LINUX32:
            case LINUX64:
                exec = new Executor(findFileName("which", Arrays.asList(File.listRoots())));
        }

        if (exec != null) {
            try {
                exec.callExe(new String[]{command});
            }
            catch (IOException ex) {
                Logger.getLogger(SystemUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            List<String> output = exec.getOutput();

            if (output != null) {
                for (String outputLine : output) {
                    if (!outputLine.startsWith("INFO") && !outputLine.startsWith("JINKIES")) {
                        File file = new File(outputLine.trim());
                        previousFindCommandResults.put(command, file);
                        previousFindCommandDirectories.add(file.getParentFile());
                        try {
                            return file.getCanonicalFile();
                        }
                        catch (IOException ex) {
                            Logger.getLogger(SystemUtils.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        // Command wasn't in the path, so let's check the location hints.
        File foundFile = findFileName(command, locationHints);
        if (foundFile != null) {
            previousFindCommandResults.put(command, foundFile);
            previousFindCommandDirectories.add(foundFile.getParentFile());
            return foundFile;
        }

        // Command wasn't in the hinted locations, so we have to be more inventive.
        foundFile = findFileName(command, previousFindCommandDirectories);
        if (foundFile != null) {
            previousFindCommandResults.put(command, foundFile);
            previousFindCommandDirectories.add(foundFile.getParentFile());
            return foundFile;
        }

        // Command wasn't in the most recent directories that other commands where found in.
        // Let's look through the file system.
        foundFile = findFileName(command, Arrays.asList(File.listRoots()));
        if (foundFile != null) {
            previousFindCommandResults.put(command, foundFile);
            previousFindCommandDirectories.add(foundFile.getParentFile());
            return foundFile;
        }

        // If we ever get to this point the command was nowhere to be found, so return null;
        return null;
    }

    private static File findFileName(String fileName, Iterable<File> directories) {
        return findFileName(fileName, directories, false);
    }

    private static File findFileName(String fileName, Iterable<File> directories, boolean skipSystemLocationCheck) {
        for (File directory : directories) {
            File foundFile = findFileName(fileName, directory, skipSystemLocationCheck);
            if (foundFile != null) {
                return foundFile;
            }
        }

        return null;
    }

    private static File findFileName(String fileName, File rootDirectory) {
        return findFileName(fileName, rootDirectory, false);
    }

    private static File findFileName(String fileName, File rootDirectory, boolean skipSystemLocationCheck) {
        if (previousFindFileResults.containsKey(fileName)) {
            return previousFindFileResults.get(fileName);
        }
        
        if (!skipSystemLocationCheck) {
            File systemLocationsFile = findFileName(fileName, Arrays.asList(new File[]{new File("c:\\Windows\\System32"), new File("//usr/bin")}), true);
            if (systemLocationsFile != null) {
                return systemLocationsFile;
            }
        }

        if (rootDirectory == null || !rootDirectory.exists() || !rootDirectory.isDirectory()) {
            return null;
        }

        File[] children = rootDirectory.listFiles();
        if (children == null) {
            return null;
        }

        for (File child : children) {
            if (child.isDirectory()) {
                File foundFile = findFileName(fileName, child, true);
                if (foundFile != null) {
                    return foundFile;
                }
            } else if (child.isFile()) {
                if (getSystemType().equals(SystemType.WIN32) || getSystemType().equals(SystemType.WIN64)) {
                    if (child.getName().toUpperCase().equals(fileName.toUpperCase())) {
                        previousFindFileResults.put(fileName, child);                        
                        return child;
                    }
                } else {
                    if (child.getName().equals(fileName)) {
                        previousFindFileResults.put(fileName, child);                        
                        return child;
                    }
                }
            }
        }

        return null;
    }

    public static SystemType getSystemType() {
        if (TYPE != null) {
            return TYPE;
        }

        String operatingSystemType = System.getProperty("os.name");
        if (operatingSystemType.startsWith("Windows")) {
            if (System.getenv("ProgramFiles(x86)") != null) {
                TYPE = SystemType.WIN64;
            } else {
                TYPE = SystemType.WIN32;
            }
        } else {
            Executor exec = new Executor(findFileName("uname.sh", Arrays.asList(File.listRoots())));
            try {
                exec.callExe(new String[]{"-a"});
            }
            catch (IOException ex) {
                Logger.getLogger(SystemUtils.class.getName()).log(Level.SEVERE, null, ex);
                TYPE = SystemType.LINUX32;
            }

            List<String> output = exec.getOutput();
            for (String outputLine : output) {
                if (outputLine.contains("x86_64")
                        || outputLine.contains("ia64")) {
                    TYPE = SystemType.LINUX64;
                } else {
                    TYPE = SystemType.LINUX32;
                }
            }
        }

        return TYPE;
    }

    public enum SystemType {

        WIN32, WIN64, LINUX32, LINUX64
    }
}
