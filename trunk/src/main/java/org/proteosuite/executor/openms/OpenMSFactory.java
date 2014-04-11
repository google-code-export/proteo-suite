package org.proteosuite.executor.openms;


public class OpenMSFactory {

	public static OpenMSExecutor getExecutor(String executableName) {
		switch (executableName)
		{
		case "FeatureFinderCentroided":
			return new OpenMSFeatureFinderCentroidedExecutor();
		case "FeatureLinkedUnlabeledQT":
			return new OpenMSFeatureLinkerUnlabledQTExecutor();
		case "MapAlignerPoseClustering":
			return new OpenMSMapAlignerPoseClusteringExecutor();
			default:
				throw new IllegalArgumentException("Unknown OpenMS argument:" + executableName);
		
		}
	}

}
