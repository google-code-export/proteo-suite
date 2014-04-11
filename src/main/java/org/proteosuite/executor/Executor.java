package org.proteosuite.executor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Calls an executable file and returns the output from StdOut/StdError
 * 
 * @author Andrew Collins
 * 
 */
public class Executor {
	private final String exePath;
	private String errorMessage;
	private String outputMessage;

	public Executor(String exePath) {
		this.exePath = exePath;
	}

	/**
	 * Calls the executable and returns true or false depending on occurrence of
	 * an error
	 * 
	 * @param command
	 *            A string containing command to pass to exe
	 * @return true if success, false if failure
	 */
	public boolean callExe(String[] args) {
		String[] exeArgs = new String[args.length+1];
		exeArgs[0] = exePath;
		System.arraycopy(args, 0, exeArgs, 1, args.length);
		
		ProcessBuilder processBuilder = new ProcessBuilder(exeArgs);
		Process process  = null;
		try {
			process = processBuilder.start();


			InputStream is = process.getInputStream();
			InputStream errIs = process.getErrorStream();
			int value;
			StringBuilder output = new StringBuilder();
			while ((value = is.read()) != -1) {
				output.append((char) value);
			}
			outputMessage = output.toString();

			StringBuilder error = new StringBuilder();
			while ((value = errIs.read()) != -1) {
				error.append((char) value);
			}
			errorMessage = error.toString();

			// destroy the process
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (process == null)
			return false;
		
		int exitValue = process.exitValue();
		
		if (exitValue == 0)
			return true;
		
		return false;
	}

	public boolean callExe() {
		return callExe(new String[0]);
		
	}

	/**
	 * Gets an error message if any occurred
	 * 
	 * @return error Message
	 */
	public String getError() {
		return errorMessage;
	}

	/**
	 * Gets any output generated
	 * 
	 * @return error Message
	 */
	public String getOutput() {
		return outputMessage;
	}
}
