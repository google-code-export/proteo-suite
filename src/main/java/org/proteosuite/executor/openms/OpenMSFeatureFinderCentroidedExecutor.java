package org.proteosuite.executor.openms;

import java.util.Map;

/**
 * The feature detection application for quantitation (centroided).
 * 
 * @see http://ftp.mi.fu-berlin.de/OpenMS/release-documentation/html/
 *      TOPP_FeatureFinderCentroided.html
 * @author Andrew Collins
 * 
 */
public class OpenMSFeatureFinderCentroidedExecutor extends
		OpenMSAbstractExecutor implements OpenMSExecutor {

	private static final String INI_TEMPLATE = "<XML_TEMPLATE>";

	public OpenMSFeatureFinderCentroidedExecutor(String exePath) {
		super(exePath);
	}

	@Override
	protected boolean writeConfig(Map<String, String> config, String configPath) {
		// TODO: Implement me
		return false;
	}

}
