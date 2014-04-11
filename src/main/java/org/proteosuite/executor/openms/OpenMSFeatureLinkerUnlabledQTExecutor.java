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
public class OpenMSFeatureLinkerUnlabledQTExecutor extends OpenMSAbstractExecutor
		implements OpenMSExecutor {

	private static final String INI_TEMPLATE = "<XML_TEMPLATE>";

	public OpenMSFeatureLinkerUnlabledQTExecutor() {
		super("FeatureLinkerUnlabledQT", null);
	}

	@Override
	protected boolean writeConfig(String configPath) {
		Map<String, Map<String, String>> config = getConfig();
		// TODO: Implement me
		return false;
	}

}
