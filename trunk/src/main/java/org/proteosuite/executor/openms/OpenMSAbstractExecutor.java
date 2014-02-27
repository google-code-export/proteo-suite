package org.proteosuite.executor.openms;

import java.util.HashMap;
import java.util.Map;
import org.proteosuite.executor.Executor;

/**
 * Abstract class for all OpenMS executables. Responsible for generating config
 * via child and calling the executables via parent.
 * 
 * @author Andrew Collins
 * 
 */
public abstract class OpenMSAbstractExecutor extends Executor implements
		OpenMSExecutor {
	private Map<String, Map<String, String>> config = new HashMap<String, Map<String, String>>();	

	public OpenMSAbstractExecutor(String exePath) {
		super(exePath);
	}

	@Override
	public boolean callExe(Map<String, Map<String, String>> config, String inputPath,
			String outputPath) {
		// TODO: Implement me
		String configPath = ""; // TODO: Perhaps assign this to system temp?
		writeConfig(configPath);

		boolean state = callExe("-in " + inputPath + " -out " + outputPath
				+ " -ini " + configPath);

		return state;
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
}
