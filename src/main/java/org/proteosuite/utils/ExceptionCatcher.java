package org.proteosuite.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExceptionCatcher {
	private static final String UPDATE_CHECK_URL = "http://www.proteosuite.org/service/crash.php?%s";

	/**
	 * Contacts update check service and returns location of latest version
	 * 
	 * @param version
	 *            current version of ProteoSuite
	 * @return URL to get latest version, null if current version is latest
	 * @throws IOException
	 */
	public static void reportException(Exception exception) {
		String queryString = "exc=" + exception.getClass().toString();
		queryString += "&msg=" + exception.getMessage();
		for (StackTraceElement element : exception.getStackTrace()) {
			queryString += "&trace[]=" + element.toString();
		}

		queryString = queryString.replace(" ", "%20");

		String query = String.format(UPDATE_CHECK_URL, queryString);
		try {
			URL url = new URL(query);

			InputStream urlStream = url.openStream();
			while (true) {
				int read = urlStream.read();
				if (read == -1)
					break;
			}
			urlStream.close();

		} catch (Exception ignore) {
			// Most likely no Internet connection, do nothing!
			// Though we lost debug info :(
		}
	}
}