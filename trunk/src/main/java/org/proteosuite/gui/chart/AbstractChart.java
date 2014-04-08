package org.proteosuite.gui.chart;

import java.util.List;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;

public class AbstractChart {
	private static final String SCAN_START_TIME = "MS:1000016";
	private static final String TIME_MINUTE = "UO:0000031";
	private static final String MS_LEVEL = "MS:1000511";
	private static final String MZ = "MS:1000514";
	private static final String ITENSITY = "MS:1000515";
	private static final String RETENTION_TIME = "MS:1000595";

	protected static float getRetentionTime(List<CVParam> cvParam) {
		for (CVParam lCVParam : cvParam) {
			if (!lCVParam.getAccession().equals(SCAN_START_TIME))
				continue;

			// Get RT
			float rt = Float.parseFloat(lCVParam.getValue().trim());

			if (lCVParam.getUnitAccession().trim().equals(TIME_MINUTE))
				rt *= 60;

			return rt;
		}

		return 0;
	}

	protected static byte getMSLevel(List<CVParam> cvParam) {
		for (CVParam lCVParam : cvParam) {
			if (lCVParam.getAccession().equals(MS_LEVEL))
				return Byte.parseByte(lCVParam.getValue().trim());
		}

		return -1;
	}

	protected static Number[] getMz(BinaryDataArray binaryData) {
		return get(binaryData, MZ);
	}

	protected static Number[] getIntensity(BinaryDataArray binaryData) {
		return get(binaryData, ITENSITY);
	}

	protected static Number[] getRetentionTimes(BinaryDataArray binaryData) {
		return get(binaryData, RETENTION_TIME);
	}
	
	protected static Number[] get(BinaryDataArray binaryData, String CVParam)
	{
		Number[] values = null;
		for (CVParam cvp : binaryData.getCvParam()) {
			if (!cvp.getAccession().equals(CVParam))
				continue;

			values = binaryData.getBinaryDataAsNumberArray();
			break;
		}

		return values;
	}
	
	protected static double[] getDouble(Number[] numbers) {
		if (numbers == null)
			return null;
		
		double[] doubles = new double[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			doubles[i] = numbers[i].doubleValue();
		}
		
		return doubles;
	}
	
	protected static float[] getFloat(Number[] numbers) {
		if (numbers == null)
			return null;
		
		float[] floats = new float[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			floats[i] = numbers[i].floatValue();
		}
		
		return floats;
	}
}
