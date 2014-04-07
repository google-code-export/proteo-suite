package org.proteosuite.gui.chart;

import java.util.List;

import uk.ac.ebi.jmzml.model.mzml.CVParam;

public class AbstractChart {


	protected static float getRetentionTime(List<CVParam> cvParam) {
		for (CVParam lCVParam : cvParam) {
			if (!lCVParam.getAccession().equals("MS:1000016"))
				continue;

			// Get RT
			float rt = Float.parseFloat(lCVParam.getValue().trim());

			if (lCVParam.getUnitAccession().trim().equals("UO:0000031"))
				rt *= 60;
			
			return rt;
		}
		
		return 0;
	}

	protected static byte getMSLevel(List<CVParam> cvParam) {
		for (CVParam lCVParam : cvParam) {
			if (lCVParam.getAccession().equals("MS:1000511")) {
				return Byte.parseByte(lCVParam.getValue().trim());
			}
		}

		return -1;
	}
}
