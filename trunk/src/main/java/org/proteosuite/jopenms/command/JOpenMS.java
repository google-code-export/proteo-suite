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

    private static final String COMMAND_OPTION = "command";
    private static final String LIST_OPTION = "list";
    private static final String HELP_OPTION = "help";

    private JOpenMS() {
    }

    public static void main(String[] args) {

        //OpenMSModule omsm = null;
        try {
            // Definite command line
            CommandLineParser parser = new ExtendedPosixParser(true); // ignore the unrecognized options
            Options jopenms_options = new Options();

            jopenms_options.addOption("h", HELP_OPTION, false, "print help message.");

            jopenms_options.addOption("l", LIST_OPTION, false, "list all supported OpenMS modules.");

            jopenms_options.addOption(COMMAND_OPTION, true, "print help message for specific OpenMS module.");

            CommandLine inputArguments = parser.parse(jopenms_options, args);
            // no argument or "-h" or "--help" argument.
            // overrides any other legal argument
            if (inputArguments.getOptions().length == 0 || inputArguments.hasOption(HELP_OPTION)) {
                processHelp(jopenms_options);
            } // "-l" or "-list" argument
            else if (inputArguments.hasOption(LIST_OPTION)) {
                processList();
            } // "-command" argument
            else if (inputArguments.hasOption(COMMAND_OPTION)) {
                processCommand(inputArguments, args, jopenms_options);
            }

        } //        catch (IOException ex) {
        //            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        //        catch (IOException ex) {
        //            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
        //            System.out.println(ex.getMessage());
        //            omsm.destroy();
        //        }
        catch (ParseException ex) {
            System.out.println(ex.getMessage());
            //omsm.destroy();
        }
    }

    private static void processList() {
        System.out.println("\nPreparing the help messages, it could take up to one minute ... ...");

        Options cmdOptions = new Options();

        for (OpenMSExecutable omse : OpenMSExecutable.values()) {

            OpenMSModule module = new OpenMSModule(omse, "");

            cmdOptions.addOption(OptionBuilder
                    .withDescription(module.getModuleDescription())
                    .create(omse.getName()));

            module.destroy();
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("\tList of OpenMS modules.\nExample: java -jar jOpenMS-version [-command FeatureFinderCentroided [blank to display all parameters||-Path$To$Parameter value]].", cmdOptions);
    }

    private static void processHelp(Options jopenms_options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jOpenMS -- A Java wrapper to run OpenMS. An installation of OpenMS is a MUST to run this jar. java -jar jOpenMS-version", jopenms_options, true);
    }

    private static void processCommand(CommandLine inputArguments, String[] javaArgs, Options jopenms_options) {
        String openMSCommand = inputArguments.getOptionValue(COMMAND_OPTION);
        OpenMSModule module = null;
        try {
            OpenMSExecutable openMSExecutable = OpenMSExecutable.valueOf(openMSCommand);

            module = new OpenMSModule(openMSExecutable, "");

            Options openMSOptions = module.getOptions();

            Options combOptions = Utils.combineOptions(jopenms_options, openMSOptions);
            if (inputArguments.getArgList().isEmpty()) {
                module.printHelp(); // print specific help message for one OpenMS module
            } else {
                try {
                    CommandLineParser combParser = new PosixParser();
                    CommandLine combLine = combParser.parse(combOptions, javaArgs);

                    // write a new config file
                    Map<String, Object> newCfgMap = new HashMap<String, Object>(module.getCfgMap());
                    Option[] inputOpts = combLine.getOptions();
                    for (Option opt : inputOpts) {
                        if (!opt.getOpt().equalsIgnoreCase(COMMAND_OPTION)) {
                            //transform the value list to single String to fit setConfig() function
                            String newValue = "";
                            List<String> newValueList = opt.getValuesList();
                            for (String v : newValueList) {
                                newValue = newValue + v + " ";
                            }
                            newValue = newValue.trim();

                            setConfig(newCfgMap, opt.getOpt(), newValue);
                        }
                    }

                    File cfgFile = generateConfigFile(openMSExecutable.getName(), module, newCfgMap);

                    performOpenMSTask(combLine.getOptionValue(COMMAND_OPTION), cfgFile);

                    // delete the temporate config file
                    cfgFile.delete();
                } catch (ParseException pex) {
                    System.out.println(pex.getMessage());
                }
            }
            //omsm.destroy();
        } catch (IllegalArgumentException irex) {
            System.out.println("Error:\tCommand <" + openMSCommand + "> is not a supported OpenMS module.\n\tPlease use \"-l\" or \"--list\" option to check all supported OpenMS modules.");
            //System.out.println(irex.getLocalizedMessage());
            //omsm.destroy();
        } finally {
            module.destroy();
        }
    }

    public static void performOpenMSTask(String systemExecutableExtension, String openMSCommand, List<String> inputFiles, List<String> outputFiles) {
        OpenMSExecutable openMSExecutable = OpenMSExecutable.valueOf(openMSCommand);
        OpenMSModule module = new OpenMSModule(openMSExecutable, systemExecutableExtension);
        Map<String, Object> cfgMap = new HashMap<String, Object>(module.getCfgMap());
        setConfig(cfgMap, openMSExecutable.getName() + "$1$in", Utils.join(inputFiles));
        setConfig(cfgMap, openMSExecutable.getName() + "$1$out", Utils.join(outputFiles));
        File cfgFile = generateConfigFile(openMSExecutable.getName(), module, cfgMap);       

        performOpenMSTask(openMSExecutable.getName(), cfgFile);
        cfgFile.delete();
    }

    private static File generateConfigFile(String command, OpenMSModule module, Map<String, Object> cfgMap) {
        File newCfgFile = null;
        try {
            File dir = new File(System.getProperty("user.dir"));
            newCfgFile = File.createTempFile(command + System.currentTimeMillis(), ".ini", dir);
            writeConfigFile(module.getUnmarshaller(), module.getMarshaller(), newCfgFile, cfgMap);
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }

        return newCfgFile;

    }

    private static void performOpenMSTask(String openMSCommand, File configFile) {
        String path = openMSCommand + " -ini " + configFile.getAbsolutePath();
        Runtime rt = Runtime.getRuntime();
        try {
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
        } catch (IOException io) {
            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, io);
            System.out.println(io.getLocalizedMessage());
        }
    }

    /**
     * This method sets the value for the specific key into the cfMap
     *
     * @param cfMap
     * @param key
     * @param newValue
     */
    private static void setConfig(Map<String, Object> cfMap, String key,
            String newValue) {

        if (key.contains(OpenMSModule.SEPARATOR)) {
            String[] keys = key.split("\\" + OpenMSModule.SEPARATOR, 2);
            if (!keys[1].contains(OpenMSModule.SEPARATOR)) {
                Map<String, Object> subMap = (Map<String, Object>) cfMap.get(keys[0]);
                if (subMap == null) {
                    subMap = (Map<String, Object>) cfMap.get(Utils.nameDecode(keys[0]));
                }

                if (subMap != null) {
                    String oldValue = (String) subMap.get(keys[1]);

                    // when the default value contains "::", it indicate that this is ITEMLIST
                    if (oldValue.contains("::")) {
                        // replace the user input space separating by "::" separating 
                        subMap.put(keys[1], newValue.replace(" ", "::"));
                    } else {
                        subMap.put(keys[1], newValue);
                    }
                } else {
                    System.out.println("Can't not set value \"" + newValue + "\". Run with the other settings.");
                }
            } else {
                Map<String, Object> subMap = (Map<String, Object>) cfMap.get(keys[0]);
                if (subMap == null) {
                    subMap = (Map<String, Object>) cfMap.get(Utils.nameDecode(keys[0]));
                }
                if (subMap != null) {
                    setConfig(subMap, keys[1], newValue);
                } else {
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
                } else {
                    System.out.println(item.getName() + " in configMap is not a leaf but a node!\n");
                    ret = false;
                }
            } else if (itemObject instanceof ITEMLIST) {
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
                Map<String, Object> subCfMap = (Map<String, Object>) cfMap.get(node.getName());
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
                } else {
                    System.out.println(item.getName() + " in configMap is not a leaf but a node!\n");
                    ret = false;
                }
            } else if (obj instanceof ITEMLIST) {
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
            } else if (obj instanceof NODE) {
                NODE subNode = (NODE) obj;
                if (cfMap.get(subNode.getName()) instanceof Map) {
                    Map<String, Object> subCfMap = (Map<String, Object>) cfMap.get(subNode.getName());
                    ret = writeNode(subNode, subCfMap);
                }
            }
        }
        return ret;
    }
}
