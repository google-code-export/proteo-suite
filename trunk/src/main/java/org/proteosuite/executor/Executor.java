package org.proteosuite.executor;

/**
 * Calls an executable file and returns the output from StdOut/StdError
 * 
 * @author Andrew Collins
 * 
 */
public class Executor {

	private final String exePath;
	private String errorMessage = "";

	public Executor(String exePath) {
		this.exePath = exePath;
	}

	/**
	 * Calls the executable and returns true or false depending on occurence of
	 * an error
	 * 
	 * @param command
	 *            A string containing command to pass to exe
	 * @return true if success, false if failure
	 */
	public boolean callExe(String command) {
		// TODO: Implement me
		return false;
	}

	/**
	 * Gets an error message if any occurred
	 * 
	 * @return error Message
	 */
	public String getError() {
		return errorMessage;
	}
}
