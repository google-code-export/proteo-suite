package org.proteosuite.gui.chart;

import java.awt.geom.Point2D;
import java.util.HashSet;
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
	private static final double LOW_INTENSITY = 10;

	/**
	 * Displays the MS1 raw data as 2D plots
	 * @param unmarshaller
	 * @param sTitle
	 * @return
	 */
	public static JPanel get2DPlot(MzMLUnmarshaller unmarshaller) {
		Set<Point2D.Float> points = new HashSet<Point2D.Float>();

		// Check if mzML contains MS1 data
		if (unmarshaller.getChromatogramIDs().isEmpty()) {
			System.out.println(ProteoSuiteView.SYS_UTILS.getTime()
					+ " - This mzML file doesn't contain MS2 raw data.");
			return null;
		}
		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);

		while (spectrumIterator.hasNext()) {
			float rt = 0;
			Spectrum spectrumobj = spectrumIterator.next();

			// Identify MS1 data
			String msLevel = "";
			for (CVParam lCVParam : spectrumobj.getCvParam()) {
				if (lCVParam.getAccession().equals("MS:1000511"))
					msLevel = lCVParam.getValue().trim();
			}
			if (!msLevel.equals("1"))
				continue;

			try {
				Spectrum spectrum = unmarshaller.getSpectrumById(spectrumobj.getId());
				for (CVParam lCVParam : spectrum.getScanList().getScan().get(0)
						.getCvParam()) {
					if (!lCVParam.getAccession().equals("MS:1000016"))
						continue;
					
					// Get RT
					rt = Float.parseFloat(lCVParam.getValue().trim());

					if (lCVParam.getUnitAccession().trim().equals("UO:0000031"))
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
				int i = 0;
				while (i < mzNumbers.length) {
					// Removing zero values
					if (intenNumbers[i].doubleValue() <= LOW_INTENSITY)
					{
						i++;
						continue;
					}
					
					points.add(new Point2D.Float(mzNumbers[i].floatValue(), rt));
					
					i++;
				}
			} catch (MzMLUnmarshallerException ume) {
				System.out.println(ume.getMessage());
			}
		}
		System.out.println(ProteoSuiteView.SYS_UTILS.getTime() + " - 2D view holding "
				+ points.size() + " elements.");
		
		int i = 0;
    	float[][] data = new float[2][points.size()];
		for (Point2D.Float point : points)
		{
            data[0][i] = point.x;
            data[1][i] = point.y;
            i++;
		}
		
		return TwoDPlot.getTwoDPlot(data);
	}

	public static JPanel get2DPlot(RawMzMLFile dataFile) {		
		return get2DPlot(dataFile.getUnmarshaller());
	}
}
