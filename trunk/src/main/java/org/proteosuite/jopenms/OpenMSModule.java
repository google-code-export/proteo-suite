package org.proteosuite.jopenms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.proteosuite.executor.Executor;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.jopenms.config.jaxb.AbstractITEM;
import org.proteosuite.jopenms.config.jaxb.ITEM;
import org.proteosuite.jopenms.config.jaxb.ITEMLIST;
import org.proteosuite.jopenms.config.jaxb.LISTITEM;
import org.proteosuite.jopenms.config.jaxb.NODE;
import org.proteosuite.jopenms.config.jaxb.PARAMETERS;

/**
 * 
 * @author Da Qi
 * @institute University of Liverpool
 * @time 21-Feb-2014 09:43:43
 */
public class OpenMSModule {

	// private static Options options;
	private static Map<String, Object> cfgMap;
	private static OpenMSExecutable openMSExe;
	private String desc;
	private static File cfgFile;
	private static Unmarshaller unmarsh;
	private static Marshaller marsh;
	public static final String SEPARATOR = "$";

	public OpenMSModule(OpenMSExecutable omse, String systemExecutableExtension) {
		try {
			openMSExe = omse;
			
			// create a default config file using "-write_ini <file>" argument
			cfgFile = File.createTempFile(openMSExe.getName(), ".ini");

			Executor exe = new Executor(openMSExe.getName() + systemExecutableExtension);
			String[] args = new String[2];
			args[0] = "-write_ini";
			args[1] = cfgFile.getAbsolutePath();
			exe.callExe(args);

			// build a config map and options
			cfgMap = initConfigMap(cfgFile);
		} catch (IOException ex) {
			Logger.getLogger(OpenMSModule.class.getName()).log(Level.SEVERE,
					null, ex);
			System.out.println(ex.getLocalizedMessage());
		} finally {
			cfgFile.deleteOnExit();
		}
	}

	public Map<String, Object> getCfgMap() {
		return cfgMap;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarsh;
	}

	public Marshaller getMarshaller() {
		return marsh;
	}

	/**
	 * Build a map structure according to the INI file. The INI file comply to
	 * Param_1_6_2.xsd.
	 * 
	 * @param ini
	 *            the INI file.
	 * 
	 * @return a map version of INI file.
	 */
	private Map<String, Object> initConfigMap(File ini) {

		Map<String, Object> configMap = new HashMap<String, Object>();

		configMap.put("iniFile", ini); // store the input file name with key
										// "inputFileName"

		// URL fileURL = JOpenMS.class.getClassLoader().getResource(ini);
		try {
			JAXBContext context = JAXBContext
					.newInstance(new Class[] { PARAMETERS.class });
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
						}
					} else if (i instanceof ITEMLIST) {
						ITEMLIST itlst = (ITEMLIST) i;
						String lstName = itlst.getName();
						String lstValue = getItemListValue(itlst);
						if (configMap.get(lstName) == null) {
							configMap.put(lstName, lstValue);
						}
					}
				}
			}

			List<NODE> nodes = parameters.getNODE();
			if (nodes != null) {
				for (NODE node : nodes) {
					if (node.getName().equalsIgnoreCase(openMSExe.getName())) {
						desc = node.getDescription(); // store description
					}
					putInConfigMap(configMap, node.getName(),
							node.getITEMOrITEMLISTOrNODE());
				}
			}
		} catch (JAXBException ex) {
			Logger.getLogger(JOpenMS.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return configMap;
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
						// subMap.put(item.getName(), item);
					} else if (value instanceof Map) {
						subMap = (Map<String, Object>) value;
						subMap.put(item.getName(), item.getValue());
						// subMap.put(item.getName(), item);
					} else if (!(value instanceof String)) {
						System.out.println("Unexpected type: "
								+ value.getClass().getName() + "!\n");
						ret = false;
					}
				} else if (obj instanceof ITEMLIST) {
					ITEMLIST itlst = (ITEMLIST) obj;
					Map<String, Object> subMap;
					Object value = m.get(nodeName);

					if (value == null) {
						subMap = new HashMap<String, Object>();
						m.put(nodeName, subMap);
						subMap.put(itlst.getName(), getItemListValue(itlst));
						// subMap.put(item.getName(), item);
					} else if (value instanceof Map) {
						subMap = (Map<String, Object>) value;
						subMap.put(itlst.getName(), getItemListValue(itlst));
						// subMap.put(item.getName(), item);
					}
				} else if (obj instanceof NODE) {
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
						ret = putInConfigMap(subMap, node.getName(),
								node.getITEMOrITEMLISTOrNODE());
					} else if (value instanceof Map) {
						subMap = (Map<String, Object>) value;
						// Param_1_6_2.xsd
						ret = putInConfigMap(subMap, node.getName(),
								node.getITEMOrITEMLISTOrNODE());
					}
				}
			}
		}

		return ret;
	}

	private String getItemListValue(ITEMLIST itlst) {
		String lstValue = "::";
		for (LISTITEM lstitem : itlst.getLISTITEM()) {
			lstValue += lstitem.getValue();
		}
		return lstValue;
	}
}
