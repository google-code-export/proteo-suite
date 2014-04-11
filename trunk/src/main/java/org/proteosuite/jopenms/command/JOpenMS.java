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

import org.proteosuite.executor.Executor;
import org.proteosuite.executor.openms.OpenMSExecutor;
import org.proteosuite.executor.openms.OpenMSFactory;
import org.proteosuite.jopenms.OpenMSModule;
import org.proteosuite.jopenms.config.jaxb.AbstractITEM;
import org.proteosuite.jopenms.config.jaxb.ITEM;
import org.proteosuite.jopenms.config.jaxb.ITEMLIST;
import org.proteosuite.jopenms.config.jaxb.LISTITEM;
import org.proteosuite.jopenms.config.jaxb.NODE;
import org.proteosuite.jopenms.config.jaxb.PARAMETERS;
import org.proteosuite.jopenms.util.Utils;

/**
 * 
 * @author Da Qi
 * @institute University of Liverpool
 * @time 10-Feb-2014 16:14:54
 */
public class JOpenMS {

	private JOpenMS() {
	}

	public static void performOpenMSTask(String systemExecutableExtension,
			String openMSCommand, List<String> inputFiles,
			List<String> outputFiles) {
		
		//OpenMSExecutor openMSExecutor = OpenMSFactory.getExecutor(openMSCommand);
		//Map<String, Object> cfgMap = openMSExecutor.getDefaultConfig();
		
		
		OpenMSModule module = new OpenMSModule(openMSCommand,
				systemExecutableExtension);
		Map<String, Object> cfgMap = module.getCfgMap();

		setConfig(cfgMap, openMSCommand + "$1$in",
				String.join(" ", inputFiles));
		setConfig(cfgMap, openMSCommand + "$1$out",
				String.join(" ", outputFiles));

		File cfgFile = generateConfigFile(openMSCommand, module,
				cfgMap);

		String[] args = new String[2];
		args[0] = "-ini";
		args[1] = cfgFile.getAbsolutePath();

		Executor e = new Executor(openMSCommand);
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
		} catch (IOException | JAXBException ex) {
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

		if (!key.contains(OpenMSModule.SEPARATOR))
			return;

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
}
