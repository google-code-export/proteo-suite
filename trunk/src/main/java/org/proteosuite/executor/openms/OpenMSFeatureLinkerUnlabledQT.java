package org.proteosuite.executor.openms;

import java.util.Map;
/**
 * Groups corresponding features from multiple maps using a QT clustering
 * approach.
 * 
 * @see http://ftp.mi.fu-berlin.de/OpenMS/release-documentation/html/
 *      TOPP_FeatureLinkerUnlabeledQT.html
 * @author Andrew Collins
 * 
 */
public class OpenMSFeatureLinkerUnlabledQT extends OpenMSAbstractExecutor
		implements OpenMSExecutor {

	private static final String INI_TEMPLATE = "<XML_TEMPLATE>";

	public OpenMSFeatureLinkerUnlabledQT(String exePath) {
		super(exePath);
	}

	@Override
	protected boolean writeConfig(String configPath) {
		Map<String, Map<String, String>> config = getConfig();
		// TODO: Implement me
		return false;
	}

}
