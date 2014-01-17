package org.proteosuite.executor.openms;

import java.util.Map;

/**
 * Interface for all OpenMS executables
 * 
 * @author Andrew Collins
 * 
 */
public interface OpenMSExecutor {
	/**
	 * Call the executable file using the config values, input file and output
	 * file
	 * 
	 * @param config
	 *            Config values as a Key => value list.
	 * @param inputPath
	 *            Location of input file
	 * @param outputPath
	 *            Location of output file
	 * @return true if success, else false
	 */
	public boolean callExe(Map<String, Map<String, String>> config, String inputPath,
			String outputPath);

	/**
	 * If callExe returns false, this should contain the error that was
	 * generated
	 * 
	 * @return error
	 */
	public String getError();

	/**
	 * Contents of log file
	 * 
	 * @return log
	 */
	public String getLog();
	
	/**
	 * Returns the current config values either defaults or set via callExe
	 * @return config values
	 */
	public Map<String, Map<String, String>> getConfig();
}
