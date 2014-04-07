package org.proteosuite.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author SPerkins
 */
public class FileUtils {
	private static final Pattern fullPathPattern = Pattern
			.compile(".+[\\\\/]([^\\\\/]+)");

	private FileUtils() {
	}

	public static String getFileNameFromFullPath(String fullPath) {
		String fileName = null;
		Matcher matcher = fullPathPattern.matcher(fullPath);
		if (matcher.find()) {
			fileName = matcher.group(1);
		}

		return fileName;
	}
}
