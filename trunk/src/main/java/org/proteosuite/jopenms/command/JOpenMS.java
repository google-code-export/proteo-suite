/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.jopenms.command;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.*;
import org.apache.commons.cli.*;
import org.proteosuite.jopenms.OpenMSExecutable;
import org.proteosuite.jopenms.OpenMSModule;
import org.proteosuite.jopenms.config.jaxb.*;
import org.proteosuite.jopenms.util.ExtendedPosixParser;
import org.proteosuite.jopenms.util.Utils;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 10-Feb-2014 16:14:54
 */
public class JOpenMS {

    public JOpenMS() {
    }

    public static void main(String[] args) {

        OpenMSModule omsm = null;
        try {
            // Definite command line
            CommandLineParser parser = new ExtendedPosixParser(true); // ignore the unrecognized options
            Options options = new Options();


            String helpOpt = "help";
            options.addOption("h", helpOpt, false, "print help message.");

            String cmdListOpt = "list";
            options.addOption("l", cmdListOpt, false, "list all supported OpenMS modules.");

            String cmdOpt = "command";

            options.addOption(cmdOpt, true, "print help message for specific OpenMS module.");

            /*
             *
             */

            CommandLine line = parser.parse(options, args);
            // no argument or "-h" or "--help" argument.
            // overrides any other legal argument
            if (line.getOptions().length == 0 || line.hasOption(helpOpt)) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("jOpenMS -- A Java wrapper to run OpenMS. An installation of OpenMS is a MUST to run this jar. java -jar jOpenMS-version", options, true);
            }
            // "-l" or "-list" argument
            else if (line.hasOption(cmdListOpt)) {

                System.out.println("\nPreparing the help messages, it could take up to one minute ... ...");

                Options cmdOptions = new Options();

                for (OpenMSExecutable omse : OpenMSExecutable.values()) {

                    omsm = new OpenMSModule(omse);

                    cmdOptions.addOption(OptionBuilder
                            .withDescription(omsm.getModuleDescription())
                            .create(omse.getName()));

                    omsm.destroy();
                }

                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("\tList of OpenMS modules.\nExample: java -jar jOpenMS-version [-command FeatureFinderCentroided [blank to display all parameters||-Path$To$Parameter value]].", cmdOptions);
            }
            // "-command" argument
            else if (line.hasOption(cmdOpt)) {
                String omseName = line.getOptionValue(cmdOpt);
                try {
                    OpenMSExecutable omse = OpenMSExecutable.valueOf(omseName);

                    omsm = new OpenMSModule(omse);

                    Options omsmOptions = omsm.getOptions();

                    Options combOptions = Utils.combineOptions(options, omsmOptions);
                    if (line.getArgList().isEmpty()) {
                        omsm.printHelp(); // print specific help message for one OpenMS module
                    }
                    else {
                        try {
                            CommandLineParser combParser = new PosixParser();
                            CommandLine combLine = combParser.parse(combOptions, args);

                            // write a new config file
                            Map newCfgMap = new HashMap(omsm.getCfgMap());
                            Option[] inputOpts = combLine.getOptions();
                            for (Option opt : inputOpts) {
                                if (!opt.getOpt().equalsIgnoreCase("command")) {
                                    //transform the value list to single String to fit setConfig() function
                                    String newValue = "";
                                    List<String> newValueList = opt.getValuesList();
                                    for (String v : newValueList) {
                                        newValue = newValue + v + " ";
                                    }
                                    newValue = newValue.trim();

                                    //
                                    setConfig(newCfgMap, opt.getOpt(), newValue);
                                }
                            }

                            File dir = new File(System.getProperty("user.dir"));
                            File newCfgFile = File.createTempFile(combLine.getOptionValue(cmdOpt) + System.currentTimeMillis(), ".ini", dir);
                            String fileName = newCfgFile.getAbsolutePath();

                            writeConfigFile(omsm.getUnmarshaller(), omsm.getMarshaller(), newCfgFile, newCfgMap);

                            String path = combLine.getOptionValue(cmdOpt) + " -ini " + fileName;
                            Runtime rt = Runtime.getRuntime();
                            Process p = rt.exec(path);

                            InputStream is = p.getInputStream();
                            int value;
                            while ((value = is.read()) != -1) {
                                System.out.print((char) value);
                            }

                            InputStream errIs = p.getErrorStream();
                            while ((value = errIs.read()) != -1) {
                                System.out.print((char) value);
                            }

                            // destroy the process
                            p.destroy();

                            // delete the temporate config file
                            newCfgFile.deleteOnExit();
                        }
                        catch (ParseException pex) {
                            System.out.println(pex.getMessage());
                        }
                    }
                    //omsm.destroy();
                }
                catch (IOException ex) {
                    Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getLocalizedMessage());
                    //omsm.destroy();
                }
                catch (JAXBException ex) {
                    Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                    //omsm.destroy();
                }
                catch (IllegalArgumentException irex) {
                    System.out.println("Error:\tCommand <" + omseName + "> is not a supported OpenMS module.\n\tPlease use \"-l\" or \"--list\" option to check all supported OpenMS modules.");
                    //System.out.println(irex.getLocalizedMessage());
                    //omsm.destroy();
                }
                finally {
                    omsm.destroy();
                }
            }

        }
//        catch (IOException ex) {
//            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        catch (IOException ex) {
//            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println(ex.getMessage());
//            omsm.destroy();
//        }
        catch (ParseException ex) {
            System.out.println(ex.getMessage());
            omsm.destroy();
        }
    }

    /**
     * This method sets the value for the specific key into the cfMap
     *
     * @param cfMap
     * @param key
     * @param newValue
     */
    private static void setConfig(Map cfMap, String key,
                                  String newValue) {

        if (key.contains(OpenMSModule.SEPARATOR)) {
            String[] keys = key.split("\\" + OpenMSModule.SEPARATOR, 2);
            if (!keys[1].contains(OpenMSModule.SEPARATOR)) {
                Map subMap = (Map) cfMap.get(keys[0]);
                if (subMap == null) {
                    subMap = (Map) cfMap.get(Utils.nameDecode(keys[0]));
                }

                if (subMap != null) {
                    String oldValue = (String) subMap.get(keys[1]);

                    // when the default value contains "::", it indicate that this is ITEMLIST
                    if (oldValue.contains("::")) {
                        // replace the user input space separating by "::" separating 
                        subMap.put(keys[1], newValue.replace(" ", "::"));
                    }
                    else {
                        subMap.put(keys[1], newValue);
                    }
                }
                else {
                    System.out.println("Can't not set value \"" + newValue + "\". Run with the other settings.");
                }
            }
            else {
                Map subMap = (Map) cfMap.get(keys[0]);
                if (subMap == null) {
                    subMap = (Map) cfMap.get(Utils.nameDecode(keys[0]));
                }
                if (subMap != null) {
                    setConfig(subMap, keys[1], newValue);
                }
                else {
                    System.out.println("Can't not set value \"" + newValue + "\". Run with the other settings.");
                }
            }
        }
    }

    private static boolean writeConfigFile(Unmarshaller um, Marshaller m,
                                           File output,
                                           Map<String, Object> cfMap)
            throws JAXBException {

        boolean ret = true;
        File input = (File) cfMap.get("iniFile");
        PARAMETERS parameters = (PARAMETERS) um.unmarshal(input);

        // populates the value for each item;
        List<AbstractITEM> itemObjects = parameters.getITEMS();

        for (Object itemObject : itemObjects) {
            if (itemObject instanceof ITEM) {
                ITEM item = (ITEM) itemObject;
                String itemName = item.getName();
                Object value = cfMap.get(itemName);
                if (value instanceof String) {
                    String newValue = (String) value;
                    item.setValue(newValue);
                }
                else {
                    System.out.println(item.getName() + " in configMap is not a leaf but a node!\n");
                    ret = false;
                }
            }
            else if (itemObject instanceof ITEMLIST) {
                ITEMLIST itlst = (ITEMLIST) itemObject;
                String lstName = itlst.getName();
                Object value = cfMap.get(lstName);
                if (value instanceof String) {
                    String[] values = ((String) value).split("::");
                    for (String v : values) {
                        LISTITEM lstitem = new LISTITEM();
                        lstitem.setValue(v);
                        itlst.getLISTITEM().add(lstitem);
                    }
                }
            }
        }
        // populates the value for items in each node using recursive call
        List<NODE> nodes = parameters.getNODE();
        for (NODE node : nodes) {
            if (cfMap.get(node.getName()) instanceof Map) {
                Map subCfMap = (Map) cfMap.get(node.getName());
                ret = writeNode(node, subCfMap);
            }
        }
        m.marshal(parameters, output);
        return ret;
    }

    private static boolean writeNode(NODE node, Map<String, Object> cfMap) {
        boolean ret = true;

        List<Object> itemsOrNodes = node.getITEMOrITEMLISTOrNODE();

        for (Object obj : itemsOrNodes) {
            if (obj instanceof ITEM) {
                ITEM item = (ITEM) obj;
                String itemName = item.getName();
                Object value = cfMap.get(itemName);
                if (value instanceof String) {
                    String newValue = (String) value;
                    item.setValue(newValue);
                }
                else {
                    System.out.println(item.getName() + " in configMap is not a leaf but a node!\n");
                    ret = false;
                }
            }
            else if (obj instanceof ITEMLIST) {
                ITEMLIST itlst = (ITEMLIST) obj;
                String lstName = itlst.getName();
                Object value = cfMap.get(lstName);
                if (value instanceof String) {
                    String[] values = ((String) value).split("::");
                    for (String v : values) {
                        LISTITEM lstitem = new LISTITEM();
                        lstitem.setValue(v);
                        itlst.getLISTITEM().add(lstitem);
                    }
                }
            }
            else if (obj instanceof NODE) {
                NODE subNode = (NODE) obj;
                if (cfMap.get(subNode.getName()) instanceof Map) {
                    Map subCfMap = (Map) cfMap.get(subNode.getName());
                    ret = writeNode(subNode, subCfMap);
                }
            }
        }
        return ret;
    }

}
