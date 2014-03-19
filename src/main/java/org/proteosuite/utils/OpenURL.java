/*
 * --------------------------------------------------------------------------
 * OpenURL.java
 * --------------------------------------------------------------------------
 * Description:       Class for opening an URL on the browser
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

/**
 * This class allows to open a URL on any browser.
 * 
 * @author fgonzalez
 * @param url
 *            URL
 */
public class OpenURL {
	private static final String[] BROWSERS = { "firefox", "mozilla", "konqueror",
			"opera", "links", "lynx" };
	/**
	 * Opens a URL in the users browser
	 * 
	 * @param url
	 *            to open
	 */
	public static void open(String url) {
		final String operatingSystem = System.getProperty("os.name").toLowerCase();
		final Runtime runTime = Runtime.getRuntime();

		// Validate browser settings
		try {
			if (operatingSystem.indexOf("win") != -1)
				runTime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			else if (operatingSystem.indexOf("mac") != -1)
				runTime.exec("open " + url);
			else if (operatingSystem.indexOf("nix") != -1 || operatingSystem.indexOf("nux") != -1) {

				StringBuilder cmd = new StringBuilder();
				for (int i = 0; i < BROWSERS.length; i++)
					cmd.append((i == 0 ? "" : " || ") + BROWSERS[i] + " \""
							+ url + "\" ");
				runTime.exec(new String[] { "sh", "-c", cmd.toString() });
			} else
				return;
		} catch (Exception e) {
			return;
		}
	}
}
