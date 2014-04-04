package org.proteosuite.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UpdateCheck {
	private static final String UPDATE_CHECK_URL = "http://www.proteosuite.org/service/update.php?%s";
	private static final String NO_NEW_VERSION = "FALSE";

	/**
	 * Contacts update check service and returns location of latest version
	 * 
	 * @param version
	 *            current version of ProteoSuite
	 * @return URL to get latest version, null if current version is latest
	 * @throws IOException
	 */
	public static String hasUpdate(String version) throws IOException {			
		String currentVersionInfo = "jvendor=" + System.getProperty("java.vendor");
		currentVersionInfo += "&jversion=" + System.getProperty("java.version");
		currentVersionInfo += "&osarch=" + System.getProperty("os.arch");
		currentVersionInfo += "&osname=" + System.getProperty("os.name");
		currentVersionInfo += "&osversion=" + System.getProperty("os.version");
		currentVersionInfo += "&psversion=" + version.replace(' ', '-');
		currentVersionInfo += "&sysproc=" + Runtime.getRuntime().availableProcessors();
		currentVersionInfo += "&sysmem=" + Runtime.getRuntime().maxMemory();		
		
		
		currentVersionInfo = currentVersionInfo.replace(" ", "%20");
		
		String query = String.format(UPDATE_CHECK_URL, currentVersionInfo);
		URL url = new URL(query);
		
		InputStream moo = url.openStream();
		StringBuilder str = new StringBuilder();
		while (true)
		{
			int read = moo.read();
			if (read == -1)
				break;
			
			str.append((char) read);
		}
		String newVersion = str.toString();
		
		if (newVersion.equalsIgnoreCase(NO_NEW_VERSION))
			return null;
		
		return newVersion;
	}
}