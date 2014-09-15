package org.proteosuite.executor.openms;

import java.util.Map;

/**
 * Corrects retention time distortions between maps, using a pose clustering
 * approach.
 * 
 * @see: http://ftp.mi.fu-berlin.de/OpenMS/release-documentation/html/
 *       TOPP_MapAlignerPoseClustering.html
 * @author Andrew Collins
 * 
 */
public class OpenMSMapAlignerPoseClusteringExecutor extends
		OpenMSAbstractExecutor implements OpenMSExecutor {

	private static final String INI_TEMPLATE = "<XML_TEMPLATE>";

	public OpenMSMapAlignerPoseClusteringExecutor() {
		super("MapAlignerPoseClustering", null);
	}

	@Override
	protected boolean writeConfig(String configPath) {
		Map<String, Map<String, String>> config = getConfig();
		// TODO: Implement me
		return false;
	}

}