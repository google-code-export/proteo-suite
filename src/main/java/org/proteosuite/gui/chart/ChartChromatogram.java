package org.proteosuite.gui.chart;

import java.awt.Dimension;
import java.util.List;
import java.util.Set;

import org.proteosuite.ProteoSuiteView;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

import com.compomics.util.gui.spectrum.ChromatogramPanel;

public class ChartChromatogram {


	/**
	 * Display total ion chromatogram
	 * 
	 * @param iIndex
	 *            - Index to the aMzMLUnmarshaller list
	 * @param sTitle
	 *            - Window title
	 * @return 
	 * @return void
	 */
	public static ChromatogramPanel getChromatogram(MzMLUnmarshaller unmarshaller)
	{
		try {
			// Check if mzML contains MS1 data
			Set<String> chromats = unmarshaller.getChromatogramIDs();
			if (chromats.isEmpty()) {
				System.out.println(ProteoSuiteView.SYS_UTILS.getTime()
						+ " - This mzML file doesn't contain MS2 raw data.");
				return null;
			}
			Chromatogram chromatogram = unmarshaller.getChromatogramById("TIC");
			Number[] rtNumbers = null;
			Number[] intenNumbers = null;
			List<BinaryDataArray> bdal = chromatogram.getBinaryDataArrayList()
					.getBinaryDataArray();

			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000595")) {
						rtNumbers = bda.getBinaryDataAsNumberArray();
					}
					if (cvp.getAccession().equals("MS:1000515")) {
						intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
			}
			// Converting numbers to doubles
			double[] rt = new double[rtNumbers.length];
			for (int iI = 0; iI < rtNumbers.length; iI++) {
				rt[iI] = rtNumbers[iI].doubleValue();
			}
			double[] intensities = new double[intenNumbers.length];
			for (int iI = 0; iI < intenNumbers.length; iI++) {
				intensities[iI] = intenNumbers[iI].doubleValue();
			}
			
			// Class chromatogram from compomics.org
			ChromatogramPanel chromatogramPanel = new ChromatogramPanel(rt, intensities, "RT (mins)", "Intensity (counts)");
			chromatogramPanel.setSize(new Dimension(600, 400));
			chromatogramPanel.setPreferredSize(new Dimension(600, 400));
			
			return chromatogramPanel;
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
		return null;
	}
}
