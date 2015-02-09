package org.proteosuite.jopenms.command;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.proteosuite.config.Config;
import org.proteosuite.config.GlobalConfig;
import org.proteosuite.executor.Executor;
import org.proteosuite.jopenms.OpenMSModule;
import org.proteosuite.jopenms.config.jaxb.AbstractITEM;
import org.proteosuite.jopenms.config.jaxb.ITEM;
import org.proteosuite.jopenms.config.jaxb.ITEMLIST;
import org.proteosuite.jopenms.config.jaxb.LISTITEM;
import org.proteosuite.jopenms.config.jaxb.NODE;
import org.proteosuite.jopenms.config.jaxb.PARAMETERS;
import org.proteosuite.jopenms.util.Utils;
import org.proteosuite.utils.StringUtils;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 10-Feb-2014 16:14:54
 */
public class JOpenMS {

    private static final GlobalConfig config = Config.getInstance().getGlobalConfig();

    private JOpenMS() {
    }

    public static void performOpenMSTask(File executable, List<String> inputFiles,
            List<String> outputFiles) throws IOException {

		//OpenMSExecutor openMSExecutor = OpenMSFactory.getExecutor(openMSCommand);
        //Map<String, Object> cfgMap = openMSExecutor.getDefaultConfig();
        String executableTrimmed = executable.getName().replaceFirst("\\.[Ee][Xx][Ee]", "");
        OpenMSModule module = new OpenMSModule(executable);
        Map<String, Object> cfgMap = module.getCfgMap();

        readProteosuiteConfigIntoMap(cfgMap, executableTrimmed);

        setConfig(cfgMap, executableTrimmed + "$1$in",
                StringUtils.join(" ", inputFiles));
        setConfig(cfgMap, executableTrimmed + "$1$out",
                StringUtils.join(" ", outputFiles));

        File cfgFile = generateConfigFile(executableTrimmed, module,
                cfgMap);

        String[] args = new String[2];
        args[0] = "-ini";
        args[1] = cfgFile.getAbsolutePath();

        Executor e = new Executor(executable);
        e.callExe(args);
        System.out.println(e.getOutput());
        System.out.println(e.getError());

        cfgFile.delete();
    }

    private static File generateConfigFile(String command, OpenMSModule module,
            Map<String, Object> cfgMap) {
        File newCfgFile = null;
        try {
            File dir = new File(System.getProperty("user.dir"));
            newCfgFile = File.createTempFile(command, ".ini", dir);
            writeConfigFile(module.getUnmarshaller(), module.getMarshaller(),
                    newCfgFile, cfgMap);
        }
        catch (IOException | JAXBException ex) {
            Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null,
                    ex);
            System.out.println(ex.getLocalizedMessage());
        }

        return newCfgFile;

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

        if (!key.contains(OpenMSModule.SEPARATOR)) {
            return;
        }

        String[] keys = key.split("\\" + OpenMSModule.SEPARATOR, 2);
        if (!keys[1].contains(OpenMSModule.SEPARATOR)) {
            Map<String, Object> subMap = (Map<String, Object>) cfMap
                    .get(keys[0]);
            if (subMap == null) {
                subMap = (Map<String, Object>) cfMap.get(Utils
                        .nameDecode(keys[0]));
            }

            if (subMap != null) {
                String oldValue = (String) subMap.get(keys[1]);

				// when the default value contains "::", it indicate that
                // this is ITEMLIST
                if (oldValue.contains("::")) {
					// replace the user input space separating by "::"
                    // separating
                    subMap.put(keys[1], newValue.replace(" ", "::"));
                } else {
                    subMap.put(keys[1], newValue);
                }
            } else {
                System.out.println("Can't not set value \"" + newValue
                        + "\". Run with the other settings.");
            }
        } else {
            Map<String, Object> subMap = (Map<String, Object>) cfMap
                    .get(keys[0]);
            if (subMap == null) {
                subMap = (Map<String, Object>) cfMap.get(Utils
                        .nameDecode(keys[0]));
            }
            if (subMap != null) {
                setConfig(subMap, keys[1], newValue);
            } else {
                System.out.println("Can't not set value \"" + newValue
                        + "\". Run with the other settings.");
            }
        }
    }

    private static boolean writeConfigFile(Unmarshaller um, Marshaller m,
            File output, Map<String, Object> cfMap) throws JAXBException {

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
                    System.out.println(item.getName()
                            + " in configMap is not a leaf but a node!\n");
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
        for (NODE node : parameters.getNODE()) {
            if (cfMap.get(node.getName()) instanceof Map<?, ?>) {
                Map<String, Object> subCfMap = (Map<String, Object>) cfMap
                        .get(node.getName());
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
                    System.out.println(item.getName()
                            + " in configMap is not a leaf but a node!\n");
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
                    Map<String, Object> subCfMap = (Map<String, Object>) cfMap
                            .get(subNode.getName());
                    ret = writeNode(subNode, subCfMap);
                }
            }
        }
        return ret;
    }

    private static void readProteosuiteConfigIntoMap(Map<String, Object> map, String executableName) {
        switch (executableName) {
            case "FeatureFinderCentroided":
                setConfig(map, "FeatureFinderCentroided$1$algorithm$mass_trace$mz_tolerance", String.valueOf(config.getFeatureFinderMassTraceMzTolerance()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$mass_trace$min_spectra", String.valueOf(config.getFeatureFinderMassTraceMinSpectra()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$mass_trace$max_missing", String.valueOf(config.getFeatureFinderMassTraceMaxMissing()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$mass_trace$slope_bound", String.valueOf(config.getFeatureFinderMassTraceSlopeBound()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$isotopic_pattern$charge_low", String.valueOf(config.getFeatureFinderIsotopicPatternChargeLow()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$isotopic_pattern$charge_high", String.valueOf(config.getFeatureFinderIsotopicPatternChargeHigh()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$isotopic_pattern$mz_tolerance", String.valueOf(config.getFeatureFinderIsotopicPatternMzTolerance()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$seed$min_score", String.valueOf(config.getFeatureFinderSeedMinScore()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$feature$min_score", String.valueOf(config.getFeatureFinderFeatureMinScore()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$feature$min_isotope_fit", String.valueOf(config.getFeatureFinderFeatureMinIsotopeFit()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$feature$min_trace_score", String.valueOf(config.getFeatureFinderFeatureMinTraceScore()));
                setConfig(map, "FeatureFinderCentroided$1$algorithm$feature$min_rt_span", String.valueOf(config.getFeatureFinderFeatureMaxRtSpan()));
                break;
            case "IDMapper":
                break;
            case "MapAlignerIdentification":
                setConfig(map, "MapAlignerIdentification$1$algorithm$min_run_occur", String.valueOf(config.getIdentAlignerMinRuns()));
                setConfig(map, "MapAlignerIdentification$1$algorithm$max_rt_shift", String.valueOf(config.getIdentAlignerMaxRtShift()));
                break;
            case "MapAlignerPoseClustering":

                break;
            case "FeatureLinkerUnlabeledQT":
                setConfig(map, "FeatureLinkerUnlabeledQT$1$algorithm$use_identifications", String.valueOf(config.getFeatureLinkerUseIdentifications()));
                setConfig(map, "FeatureLinkerUnlabeledQT$1$algorithm$distance_RT$max_difference", String.valueOf(config.getFeatureLinkerDistanceRtMaxDifference()));
                setConfig(map, "FeatureLinkerUnlabeledQT$1$algorithm$distance_MZ$max_difference", String.valueOf(config.getFeatureLinkerDistanceMzMaxDifference()));
                break;
        }
    }
}
