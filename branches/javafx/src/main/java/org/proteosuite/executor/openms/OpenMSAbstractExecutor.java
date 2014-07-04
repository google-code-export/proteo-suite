package org.proteosuite.executor.openms;

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
import org.proteosuite.jopenms.OpenMSModule;
import org.proteosuite.jopenms.command.JOpenMS;
import org.proteosuite.jopenms.config.jaxb.AbstractITEM;
import org.proteosuite.jopenms.config.jaxb.ITEM;
import org.proteosuite.jopenms.config.jaxb.ITEMLIST;
import org.proteosuite.jopenms.config.jaxb.LISTITEM;
import org.proteosuite.jopenms.config.jaxb.NODE;
import org.proteosuite.jopenms.config.jaxb.PARAMETERS;

/**
 * Abstract class for all OpenMS executables. Responsible for generating config
 * via child and calling the executables via parent.
 * 
 * @author Andrew Collins
 * 
 */
public abstract class OpenMSAbstractExecutor extends Executor implements
		OpenMSExecutor {
	private String executable;
	private Map<String, Map<String, String>> config = new HashMap<String, Map<String, String>>();

	public OpenMSAbstractExecutor(String executableName, String exePath) {
		super(exePath);
		executable = executableName;
	}

	@Override
	public boolean callExe(Map<String, Map<String, String>> config, String inputPath,
			String outputPath) {
		// TODO: Implement me
		String configPath = ""; // TODO: Perhaps assign this to system temp?
		writeConfig(configPath);

		//boolean state = callExe("-in " + inputPath + " -out " + outputPath
		//		+ " -ini " + configPath);

		return false;
	}

	/**
	 * Method should write the specified config values to the given config file
	 * location
	 * 
	 * @param configPath
	 *            Config file location
	 * @return true if success, else false
	 * @note If we find writeConfig is not module specific, remove the need for
	 *       it to be abstracted to the child
	 */
	protected abstract boolean writeConfig(String configPath);

	@Override
	public String getLog() {
		// TODO: Implement me
		return "";
	}
	
	public Map<String, Map<String, String>> getConfig()
	{
		return config;
	}
	
	/**
	 * Set individual config keys to value
	 * @param key
	 * @param value
	 */
	protected void setConfig(String section, String key, String value)
	{
		if (!config.containsKey(section))
		{
			config.put(section, new HashMap<String, String>());
		}
		config.get(section).put(key, value);
		
	}
	
	public Map<String, Object> getDefaultConfig()
	{
		String systemExecutableExtension = ".exe";
		try {
			// create a default config file using "-write_ini <file>" argument
			File cfgFile = File.createTempFile(executable, ".ini");
			cfgFile.deleteOnExit();

			Executor exe = new Executor(executable + systemExecutableExtension);
			String[] args = new String[2];
			args[0] = "-write_ini";
			args[1] = cfgFile.getAbsolutePath();
			exe.callExe(args);

			// build a config map and options
			return initConfigMap(cfgFile);
		} catch (IOException ex) {
			Logger.getLogger(OpenMSModule.class.getName()).log(Level.SEVERE,
					null, ex);
			System.out.println(ex.getLocalizedMessage());
		}
		return null;
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
			Unmarshaller unmarsh = context.createUnmarshaller();

			Marshaller marsh = context.createMarshaller();
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
