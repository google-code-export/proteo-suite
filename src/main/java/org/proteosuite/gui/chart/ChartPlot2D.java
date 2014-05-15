package org.proteosuite.gui.chart;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import org.proteosuite.utils.TwoDPlot;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class ChartPlot2D extends AbstractChart {

	/**
	 * Displays the MS1 raw data as 2D plots
	 * 
	 * @param unmarshaller
	 * @param lowIntensity 
	 * @param msLevel 
	 * @return
	 */
	public static JPanel get2DPlot(MzMLUnmarshaller unmarshaller, byte msLevelThreshold, double lowIntensityThreshold) {

		// Check if mzML contains MS1 data
		if (unmarshaller.getChromatogramIDs().isEmpty())
			return null;

		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);

		Set<Point2D.Float> points = new HashSet<Point2D.Float>();
		while (spectrumIterator.hasNext()) {
			Spectrum spectrum = spectrumIterator.next();

			// Identify MS1 data
			byte msLevel = getMSLevel(spectrum.getCvParam());

			if (msLevel != msLevelThreshold)
				continue;

			float rt = getRetentionTime(spectrum.getScanList().getScan().get(0)
					.getCvParam());

			float[] massCharges = null;
			double[] intensities = null;

			// Reading mz Values
			for (BinaryDataArray binaryData : spectrum.getBinaryDataArrayList()
					.getBinaryDataArray()) {
				if (massCharges == null)
					massCharges = getFloat(getMz(binaryData));

				if (intensities == null)
					intensities = getDouble(getIntensity(binaryData));
			}

			addPoints(points, rt, massCharges, intensities, lowIntensityThreshold);
		}

		float[][] data = convertPoints(points);

		return TwoDPlot.getTwoDPlot(data);
	}

	private static float[][] convertPoints(Set<Float> points) {
		int i = 0;
		float[][] data = new float[2][points.size()];
		for (Point2D.Float point : points) {
			data[0][i] = point.x;
			data[1][i] = point.y;
			i++;
		}

		return data;
	}

	private static void addPoints(final Set<Float> points, float rt,
			float[] massCharges, double[] intensities, double lowIntensityThreshold) {
		for (int i = 0; i < massCharges.length; i++) {
			// Removing low values
			if (intensities[i] <= lowIntensityThreshold)
				continue;

			points.add(new Point2D.Float(massCharges[i], rt));
		}
	}
}