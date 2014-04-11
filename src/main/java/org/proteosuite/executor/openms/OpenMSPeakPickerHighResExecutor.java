package org.proteosuite.executor.openms;

import java.util.Map;

/**
 * A tool for peak detection in profile data. Executes the peak picking with
 * high_res algorithm.
 * 
 * @see http://ftp.mi.fu-berlin.de/OpenMS/release-documentation/html/
 *      TOPP_PeakPickerHiRes.html
 * @author Andrew Collins
 * 
 */
public class OpenMSPeakPickerHighResExecutor extends OpenMSAbstractExecutor
		implements OpenMSExecutor {

	private static final String INI_TEMPLATE = "<XML_TEMPLATE>";

	public OpenMSPeakPickerHighResExecutor(String exePath) {
		super("PeakPickerHighRes", exePath);
	}

	@Override
	protected boolean writeConfig(String configPath) {
		Map<String, Map<String, String>> config = getConfig();
		// TODO: Implement me
		return false;
	}
}
