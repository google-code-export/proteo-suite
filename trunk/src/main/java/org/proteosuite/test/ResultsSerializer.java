package org.proteosuite.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.proteosuite.test.IPC;
import org.proteosuite.test.IPC.Options;
import org.proteosuite.test.IPC.Results;
import org.proteosuite.test.ViewChartGUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author D3Y241
 */
public class ResultsSerializer {

    /**
     * @param args the command line arguments
     */
    final public static File myDocIPC = new File(System.getenv("USERPROFILE") + "\\My Documents\\IPC");
    final public static File shareIPC = new File("\\\\pnl\\projects\\msshare\\MichaelCusack\\IPC\\results");
    final public static double defaultRequiredSumP = .99999;
    File resultsDir;
//    IPC ipc;

    public ResultsSerializer(File resultsDir) {
        this.resultsDir = resultsDir;
        //this.ipc = new IPC();
    }

    public File getResultsDir() {
        return resultsDir;
    }

    public File writeResults(Results results) {
        if (results == null) {
            return null;
        }
        File file = getFile(results.getOptions());
        try {
            System.out.println("Writing results to " + file.getCanonicalPath());
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(results);
            oos.close();
            return file;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public Results readResults(Options options) {

        File f = getFile(options);
        //System.out.println("Trying to read results from " + f.toASCIIString() + "... " + f.exists());
        Results results = null;

        InputStream is = getInputStream(options);
        if (is != null) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(is);
                results = (Results) ois.readObject();
                ois.close();
            } catch (InvalidClassException ice) {
                System.err.println("Class changed..." + ice.getMessage());

                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ioe) {
                    }
                }
                boolean deleted = f.delete();

                System.err.println("\tTrying to delete..." + deleted);
            } catch (IOException e) {
                System.err.println("Trouble reading in object..." + f.getAbsolutePath());
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ioe) {
                    }
                }
                e.printStackTrace();
                System.err.println("\tTrying to delete..." + f.delete());
                f.deleteOnExit();
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Trouble making object..." + f.getAbsolutePath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (results != null) {
            results.resetOptions(options);
        }
        return results;
    }

    public File getFile(Options options) {
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }
        return new File(resultsDir, getFileName(options));
    }

    public InputStream getInputStream(Options options) {
        String filename = getFileName(options);

        InputStream is = null;

        is = getClass().getResourceAsStream(filename);

        if (is != null) {
            return is;
        }

        File file = new File(resultsDir, filename);
        if (file.exists()) {
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException fnfe) {
            }
        }

        return is;
    }

    public static String getFileName(Options options) {
        StringBuilder filename = new StringBuilder();
        filename.append(options.getFormula(false));
        filename.append(".");
        filename.append(options.getCharge());
        filename.append(".");
        filename.append(options.hashCode());
        return filename.toString();
    }

    public static File getIPCTempDir() {
        File cacheDirectoy = new File(System.getProperty("java.io.tmpdir"), "IPC");
        cacheDirectoy.mkdir();
        return cacheDirectoy;
    }

    public static Results calcResults(IPCSet ipcset) {
        return calcResults(ipcset, null);
    }

    public static Results calcResults(IPCSet ipcset, ViewChartGUI guiToUpdate) {
        return calcResults(ipcset.ipc, ipcset.rs, ipcset.options, guiToUpdate, true, defaultRequiredSumP);

    }

    public static Results calcResults(IPC ipc, ResultsSerializer rs, Options ipcOptions) {
        return calcResults(ipc, rs, ipcOptions, null, true, defaultRequiredSumP);
    }

    public static Results calcResults(IPC ipc, ResultsSerializer rs, Options ipcOptions, ViewChartGUI guiToUpdate, boolean silent, double requiredSumP) {
        Results ipcResults = null;
        long peaksToCalc = 32;
        do {
            ipcOptions.setFastCalc(peaksToCalc);
            if (!silent) {
                System.out.print("\t" + peaksToCalc);
                System.out.print("\t" + ipcOptions.hashCode());
            }
            if (ipcOptions.isBreakProcess()) {
                break;
            }

            ipcResults = ipc.execute(ipcOptions);

            if (ipcResults == null && !ipcOptions.isBreakProcess()) {
                System.err.println("SOMETHING CRASHED");
            }

            if (ipcResults != null && !ipcOptions.isBreakProcess()) {
                if (guiToUpdate != null) {
                    String percent = IPC.roundToDigits(ipcResults.getSumP() * 100, 2) + "% of Isotopic Area Calculated";
                    guiToUpdate.setCurrentResults(ipcResults, "Processing..." + percent);
                }
                peaksToCalc *= 8;
                if (!silent) {
                    System.out.println("\t" + ipcResults.getSumP());
                }
            } else {
                if (!silent) {
                    System.out.println();
                }
            }

        } while (!ipcOptions.isBreakProcess() && ipcResults != null && ipcResults.getSumP() < requiredSumP);
        rs.writeResults(ipcResults);

        return ipcResults;

    }

    public static Results getResults(IPCSet ipcset) {
        Results result = getResults(ipcset, null);

        return result;
    }

    public static Results getResults(IPCSet ipcset, ViewChartGUI guiToUpdate) {
        Results results = getResults(ipcset.ipc, ipcset.rs, ipcset.options, guiToUpdate);

        return results;

    }

    public static Results getResults(IPC ipc, ResultsSerializer rs, Options ipcOptions) {
        return getResults(ipc, rs, ipcOptions, null);
    }

    public static Results getResults(IPC ipc, ResultsSerializer rs, Options ipcOptions, ViewChartGUI guiToUpdate) {

        Results ipcResults = rs.readResults(ipcOptions);
        if (ipcResults == null) {
            ipcResults = calcResults(ipc, rs, ipcOptions, guiToUpdate, guiToUpdate == null, defaultRequiredSumP);
        }


        return ipcResults;
    }

    public static class IPCSet {

        final public IPC ipc;
        final public Options options;
        final public ResultsSerializer rs;

        public IPCSet(IPC ipc, Options options, ResultsSerializer rs) {
            this.ipc = ipc;
            this.options = options;
            this.rs = rs;
        }

        public Results getResults() {
            return ResultsSerializer.getResults(this);
        }

        public Results calcResults() {
            return ResultsSerializer.calcResults(this);
        }
    }
}
