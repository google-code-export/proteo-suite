package org.proteosuite.gui.chart;

import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.model.RawMzMLFile;
import org.proteosuite.utils.TwoDPlot;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

/**
 * 
 * @author Andrew Collins
 */
public class ChartPlot2D {

	/**
	 * Displays the MS1 raw data as 2D plots
	 * @param unmarshaller
	 * @param sTitle
	 * @return
	 */
	public static JPanel get2DPlot(MzMLUnmarshaller unmarshaller) {
		// CheckMemory chm = new
		// CheckMemory("Before allocating memory for 2D plot");
		// Display about 27 x 3 = 51 MB maximum for now
		float[] mz = new float[200_000];
		float[] art = new float[200_000];
		// CheckMemory chm2 = new
		// CheckMemory("After allocating memory for 2D plot");

		float rt = 0;
		int iCounter = 0;

		// Check if mzML contains MS1 data
		Set<String> chromats = unmarshaller.getChromatogramIDs();
		if (chromats.isEmpty()) {
			System.out.println(ProteoSuiteView.SYS_UTILS.getTime()
					+ " - This mzML file doesn't contain MS2 raw data.");
			return null;
		}
		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);
		int iK = 0;
		String unitRT = "";
		// Display first 100 scans for now
		while ((spectrumIterator.hasNext()) && (iK < 100)) {
			Spectrum spectrumobj = spectrumIterator.next();
			String spectrumid = spectrumobj.getId();

			// Identify MS1 data
			String mslevel = "";
			for (CVParam lCVParam : spectrumobj.getCvParam()) {
				if (lCVParam.getAccession().equals("MS:1000511"))
					mslevel = lCVParam.getValue().trim();
			}
			if (!mslevel.equals("1"))
				continue;

			try {
				Spectrum spectrum = unmarshaller.getSpectrumById(spectrumid);
				for (CVParam lCVParam : spectrum.getScanList().getScan().get(0)
						.getCvParam()) {
					if (!lCVParam.getAccession().equals("MS:1000016"))
						continue;
					
					// Get RT
					rt = Float.parseFloat(lCVParam.getValue().trim());
					unitRT = lCVParam.getUnitAccession().trim();

					if (unitRT.equals("UO:0000031"))
						rt *= 60;
				}
				Number[] mzNumbers = null;
				Number[] intenNumbers = null;
				// Reading mz Values
				List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
						.getBinaryDataArray();
				for (BinaryDataArray bda : bdal) {
					List<CVParam> cvpList = bda.getCvParam();
					for (CVParam cvp : cvpList) {
						if (cvp.getAccession().equals("MS:1000514"))
							mzNumbers = bda.getBinaryDataAsNumberArray();
						
						if (cvp.getAccession().equals("MS:1000515"))
							intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
				int iI = 0;
				while (iI < mzNumbers.length) {
					// Removing zero values
					if (intenNumbers[iI].doubleValue() <= 0.0)
					{
						iI += 10;
						continue;
					}
					
					if (iCounter >= 200_000) {
						iI += 10;
						continue;
					}
					
					mz[iCounter] = mzNumbers[iI].floatValue();
					art[iCounter] = rt;
					
					iCounter++;
					iI += 10;
				}
			} catch (MzMLUnmarshallerException ume) {
				System.out.println(ume.getMessage());
			}
			iK++;

		}
		System.out.println(ProteoSuiteView.SYS_UTILS.getTime() + " - 2D view holding "
				+ iCounter + " elements.");

		return TwoDPlot.getTwoDPlot(mz, art);
	}

	public static JPanel get2DPlot(RawMzMLFile dataFile) {		
		return get2DPlot(dataFile.getUnmarshaller());
	}
}
