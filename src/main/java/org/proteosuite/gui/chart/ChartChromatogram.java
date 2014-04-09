package org.proteosuite.gui.chart;

import com.compomics.util.gui.spectrum.ChromatogramPanel;

import java.awt.Dimension;

import org.proteosuite.model.RawMzMLFile;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

public class ChartChromatogram extends AbstractChart {

	/**
	 * Display total ion chromatogram
	 * 
	 * @param unmarshaller
	 * @return
	 */
	public static ChromatogramPanel getChromatogram(RawMzMLFile dataFile) {
		MzMLUnmarshaller unmarshaller = dataFile.getUnmarshaller();
		MzMLObjectIterator<Chromatogram> iterator = unmarshaller
				.unmarshalCollectionFromXpath(
						"/run/chromatogramList/chromatogram",
						Chromatogram.class);
		while (iterator.hasNext()) {
			Chromatogram chrom = iterator.next();

			// Check if mzML contains MS1 data
			if (chrom.getId().toUpperCase().equals("TIC")) {
				return createChromatogramPanel(chrom);
			}

			for (CVParam param : chrom.getCvParam()) {
				if (param.getName().toUpperCase()
						.equals("TOTAL ION CURRENT CHROMATOGRAM")) {
					return createChromatogramPanel(chrom);
				}
			}
		}

		return null;
	}

	private static ChromatogramPanel createChromatogramPanel(final Chromatogram chromatogram) {
		double[] retentionTimes = null;
		double[] intensities = null;

		for (BinaryDataArray binaryData : chromatogram.getBinaryDataArrayList()
				.getBinaryDataArray()) {
			if (retentionTimes == null)
				retentionTimes = getDouble(getRetentionTimes(binaryData));
			
			if (intensities == null)
				intensities = getDouble(getIntensity(binaryData));
		}

		// Class chromatogram from compomics.org
		ChromatogramPanel chromatogramPanel = new ChromatogramPanel(retentionTimes,
				intensities, "RT (mins)", "Intensity (counts)");
		chromatogramPanel.setSize(new Dimension(600, 400));
		chromatogramPanel.setPreferredSize(new Dimension(600, 400));

		return chromatogramPanel;
	}
}