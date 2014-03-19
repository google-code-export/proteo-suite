/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.jopenms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.cli.*;
import org.proteosuite.jopenms.command.*;
import org.proteosuite.jopenms.config.jaxb.*;
import org.proteosuite.jopenms.util.Utils;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 21-Feb-2014 09:43:43
 */
public class OpenMSModule {

    private static Options options;
    private static Map<String, Object> cfgMap;
    private static OpenMSExecutable openMSExe;
    private String desc;
    private static File cfgFile;
    private static Unmarshaller unmarsh;
    private static Marshaller marsh;
    public static final String SEPARATOR = "$";
    private String exeLocation = null;

    public OpenMSModule(String exeLocation, OpenMSExecutable omse) {
        this.exeLocation = exeLocation;
    }
    
    public OpenMSModule(OpenMSExecutable omse) {
        try {
            openMSExe = omse;

            // create a default config file using "-write_ini <file>" argument
            String fn = openMSExe.getName() + new Random().toString();
            cfgFile = File.createTempFile(fn, ".ini");

            Runtime rt = Runtime.getRuntime();
            Process p;
            if (exeLocation == null) {
                p = rt.exec(omse.getName() + ".exe" + " -write_ini " + cfgFile.getAbsolutePath());
            } else {
                p = rt.exec(exeLocation + omse.getName() + ".exe" + " -write_ini " + cfgFile.getAbsolutePath());
            }            

            InputStream is = p.getInputStream();
            int value;
            while ((value = is.read()) != -1) {
                System.out.print((char) value);
            }

            InputStream errIs = p.getErrorStream();
            while ((value = errIs.read()) != -1) {
                System.out.print((char) value);
            }

            p.destroy();

            // build a config map and optoins
            options = new Options();
            cfgMap = initConfigMap(cfgFile);
        }
        catch (IOException ex) {
            Logger.getLogger(OpenMSModule.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
            //cfgFile.deleteOnExit();
        }
        finally {
            cfgFile.deleteOnExit();
        }
    }

    public void printHelp() {
        if (options != null && !options.getOptions().isEmpty()) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(openMSExe.getName() + " -- " + desc, options);
        }
        else {
            System.out.println("No option for this module, please double check the name!");
        }
    }

    public void destroy() {
        cfgFile.deleteOnExit();
    }

    public Options getOptions() {
        return options;
    }

    public Map<String, Object> getCfgMap() {
        return cfgMap;
    }

    public File getCfgFile() {
        return cfgFile;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarsh;
    }

    public Marshaller getMarshaller() {
        return marsh;
    }

    public String getModuleDescription() {
        return desc;
    }

    /**
     * Build a map structure according to the INI file. The INI file comply to Param_1_6_2.xsd.
     *
     * @param ini the INI file.
     *
     * @return a map version of INI file.
     */
    private Map<String, Object> initConfigMap(File ini) {

        Map<String, Object> configMap = new HashMap<String, Object>();

        configMap.put("iniFile", ini); // store the input file name with key "inputFileName"

        //URL fileURL = JOpenMS.class.getClassLoader().getResource(ini);
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{PARAMETERS.class});
            unmarsh = context.createUnmarshaller();

            marsh = context.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // initial map

            // root node
            PARAMETERS parameters = (PARAMETERS) unmarsh.unmarshal(ini);

            List<AbstractITEM> items = parameters.getITEMS();
            if (items != null) {
                for (Object i : items) {
                    if (i instanceof ITEM) {
                        ITEM item = (ITEM) i;

                        if (configMap.get(item.getName()) == null) {
                            configMap.put(item.getName(), item.getValue());

                            //options                           
                            Option opt = OptionBuilder.withArgName(item.getType().value())
                                    .hasArg() //item single argument
                                    .isRequired(item.isRequired())
                                    .withDescription(item.getDescription())
                                    .create(item.getName());
                            options.addOption(opt);
                        }
                    }
                    else if (i instanceof ITEMLIST) {
                        ITEMLIST itlst = (ITEMLIST) i;
                        String lstName = itlst.getName();
                        String lstValue = getItemListValue(itlst);
                        if (configMap.get(lstName) == null) {
                            configMap.put(lstName, lstValue);
                        }

                        //options
                        Option opt = OptionBuilder.withArgName(itlst.getType().value())
                                .hasArgs() //itemlist many arguments
                                .isRequired(itlst.isRequired())
                                .withDescription(itlst.getDescription())
                                .create(itlst.getName());
                        options.addOption(opt);
                    }
                }
            }

            List<NODE> nodes = parameters.getNODE();
            if (nodes != null) {
                for (NODE node : nodes) {

                    if (node.getName().equalsIgnoreCase(openMSExe.getName())) {
                        desc = node.getDescription(); // store description
                    }
                    putInConfigMap(configMap, node.getName(), node.getITEMOrITEMLISTOrNODE());

                    // options                                   
                    String leftString = node.getName();
                    addOption(node, leftString);
                }
            }
        }
        catch (JAXBException ex) {
            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return configMap;
    }

    private void addOption(NODE node, String leftString) {
        List<Object> objs = node.getITEMOrITEMLISTOrNODE();

        if (objs != null) {
            for (Object obj : objs) {
                if (obj instanceof ITEM) {
                    ITEM item = (ITEM) obj;

                    String optName = "";
                    if (leftString.isEmpty()) {
                        optName = item.getName();
                    }
                    else {
                        optName = leftString + SEPARATOR + item.getName();
                    }
                    Option opt = OptionBuilder.withArgName(item.getType().value())
                            .hasArg() //item single argument
                            .isRequired(item.isRequired())
                            .withDescription(item.getDescription())
                            .create(optName);
                    options.addOption(opt);
                }
                else if (obj instanceof ITEMLIST) {
                    ITEMLIST itlst = (ITEMLIST) obj;

                    String optName = "";
                    if (leftString.isEmpty()) {
                        optName = itlst.getName();
                    }
                    else {
                        optName = leftString + SEPARATOR + itlst.getName();

                    }
                    Option opt = OptionBuilder.withArgName(itlst.getType().value())
                            .hasArgs() //itemlist many arguments
                            .isRequired(itlst.isRequired())
                            .withDescription(itlst.getDescription())
                            .create(optName);
                    options.addOption(opt);
                }
                else if (obj instanceof NODE) {
                    NODE subNode = (NODE) obj;
                    String tempString = leftString + SEPARATOR + Utils.nameEncode(subNode.getName());

                    addOption(subNode, tempString);
                }
            }
        }

    }

    private boolean putInConfigMap(Map<String, Object> m, String nodeName,
                                   List<Object> objs) {
        boolean ret = true;

        if (objs != null) {
            for (Object obj : objs) {
                if (obj instanceof ITEM) {
                    ITEM item = (ITEM) obj;
                    Map<String, Object> subMap;
                    Object value = m.get(nodeName);

                    if (value == null) {
                        subMap = new HashMap<String, Object>();
                        m.put(nodeName, subMap);
                        subMap.put(item.getName(), item.getValue());
                        //subMap.put(item.getName(), item);
                    }
                    else if (value instanceof Map) {
                        subMap = (Map<String, Object>) value;
                        subMap.put(item.getName(), item.getValue());
                        //subMap.put(item.getName(), item);
                    }
                    else if (!(value instanceof String)) {
                        System.out.println("Unexpected type: " + value.getClass().getName() + "!\n");
                        ret = false;
                    }
                }
                else if (obj instanceof ITEMLIST) {
                    ITEMLIST itlst = (ITEMLIST) obj;
                    Map<String, Object> subMap;
                    Object value = m.get(nodeName);

                    if (value == null) {
                        subMap = new HashMap<String, Object>();
                        m.put(nodeName, subMap);
                        subMap.put(itlst.getName(), getItemListValue(itlst));
                        //subMap.put(item.getName(), item);
                    }
                    else if (value instanceof Map) {
                        subMap = (Map<String, Object>) value;
                        subMap.put(itlst.getName(), getItemListValue(itlst));
                        //subMap.put(item.getName(), item);    
                    }
                }
                else if (obj instanceof NODE) {
                    NODE node = (NODE) obj;
                    if (node.getName().equalsIgnoreCase(openMSExe.getName())) {
                        desc = node.getDescription(); // store description
                    }
                    Map<String, Object> subMap;
                    Object value = m.get(nodeName);

                    if (value == null) {
                        subMap = new HashMap<String, Object>();
                        m.put(nodeName, subMap);

                        // Param_1_6_2.xsd
                        ret = putInConfigMap(subMap, node.getName(), node.getITEMOrITEMLISTOrNODE());
                    }
                    else if (value instanceof Map) {
                        subMap = (Map<String, Object>) value;
                        // Param_1_6_2.xsd
                        ret = putInConfigMap(subMap, node.getName(), node.getITEMOrITEMLISTOrNODE());
                    }
                }
            }
        }

        return ret;
    }

    private String getItemListValue(ITEMLIST itlst) {
        String lstValue = "::";
        for (LISTITEM lstitem : itlst.getLISTITEM()) {
            lstValue = lstValue + lstitem.getValue();
        }
        return lstValue;
    }

}
