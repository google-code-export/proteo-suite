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

		setConfig("section_one", "mz_tolerance", "0.03");
		setConfig("section_one", "min_spectra", "10");
		setConfig("section_one", "max_missing", "1");
		setConfig("section_one", "slope_bound", "0.1");		
	}

	@Override
	protected boolean writeConfig(String configPath) {
		Map<String, Map<String, String>> config = getConfig();
		
		// TODO: Implement me
		return false;
	}

}
