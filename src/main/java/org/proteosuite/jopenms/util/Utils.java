package org.proteosuite.jopenms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * General utilities class
 * 
 * @author Da Qi
 * 
 */
public class Utils {

	public static String nameDecode(String original) {
		return original.replace("_", "-");
	}

	public static String join(List<String> stringArray) {
		StringBuilder builder = new StringBuilder();

		for (String value : stringArray) {
			builder.append(value);
			builder.append(" ");
		}

		return builder.toString().trim();
	}

	public static String getLinuxVersion() {		
		List<String> versionOutput = getCommandOutput("uname -a");
		if (versionOutput.size() > 0) {
			return versionOutput.get(0);
		} else {
			return "";
		}
	}

	// TODO: use Executor.callExe()
	private static List<String> getCommandOutput(String command) {
		List<String> output = new ArrayList<String>();
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(command);
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = b.readLine()) != null) {
				output.add(line);
			}
		} catch (InterruptedException | IOException ex) {

		}

		return output;

	}

}
