package org.proteosuite.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Calls an executable file and returns the output from StdOut/StdError
 * 
 * @author Andrew Collins
 * 
 */
public class Executor {
	private final File executable;
	private String errorMessage;
	private List<String> outputMessage = new ArrayList<>();        

	public Executor(File exe) {
		this.executable = exe;
	}

	/**
	 * Calls the executable and returns true or false depending on occurrence of
	 * an error
	 * 
	 * @param command
	 *            A string containing command to pass to exe
	 * @return true if success, false if failure
	 */
	public boolean callExe(String[] args) throws IOException {
		String[] exeArgs = new String[args.length+1];
		exeArgs[0] = executable.getCanonicalPath();
		System.arraycopy(args, 0, exeArgs, 1, args.length);
		
		ProcessBuilder processBuilder = new ProcessBuilder(exeArgs);
		Process process  = null;
		try {
			process = processBuilder.start();
                        InputStream is = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			InputStream errIs = process.getErrorStream();
			int value;
			
                        String line;
                        while ((line = reader.readLine()) != null) {
                            outputMessage.add(line);
                        }			

			StringBuilder error = new StringBuilder();
			while ((value = errIs.read()) != -1) {
				error.append((char) value);
			}
                        
			errorMessage = error.toString();

			// destroy the process
			process.destroy();
		} catch (IOException e) {
			return false;
		}	
		
		int exitValue = process.exitValue();
		
		return exitValue == 0;
	}

	public boolean callExe() throws IOException {
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
//	public String getOutput() {
//		return outputMessage;
//	}
        
        public List<String> getOutput() {
            return outputMessage;
        }
}
