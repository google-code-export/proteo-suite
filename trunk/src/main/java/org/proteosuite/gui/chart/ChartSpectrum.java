package org.proteosuite.gui.chart;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

import com.compomics.util.gui.spectrum.SpectrumPanel;

public class ChartSpectrum extends AbstractChart {

	/**
	 * Display MS spectrum
	 * 
	 * @param iIndex
	 *            - Index to the aMzMLUnmarshaller list
	 * @param sID
	 *            - spectrum ID
	 * @return
	 * @return void
	 */
	public static JPanel getSpectrum(MzMLUnmarshaller unmarshaller, String sID) {
		Spectrum spectrum = null;
		try {
			spectrum = unmarshaller.getSpectrumById(sID);
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}

		if (spectrum == null)
			return null;

		// Reading CvParam to identify the MS level (1, 2)
		byte msLevel = getMSLevel(spectrum.getCvParam());

		float parIonMz = 0;
		int parCharge = 0;
		if (msLevel == 2) {
			// Get precursor ion
			PrecursorList plist = spectrum.getPrecursorList();
			if (plist == null || plist.getCount().intValue() != 1) {
				return null;
			}

			// Detect parent ion m/z and charge
			for (CVParam lCVParam : plist.getPrecursor().get(0)
					.getSelectedIonList().getSelectedIon().get(0).getCvParam()) {

				if (lCVParam.getAccession().equals("MS:1000744")) {
					parIonMz = Float.parseFloat(lCVParam.getValue().trim());
				}
				if (lCVParam.getAccession().equals("MS:1000041")) {
					parCharge = Integer.parseInt(lCVParam.getValue().trim());
				}
			}
		}

		Number[] mzNumbers = new Double[0];
		Number[] intenNumbers = new Double[0];

		// Reading mz Values
		List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
				.getBinaryDataArray();

		// Reading mz and intensity values
		for (BinaryDataArray bda : bdal) {
			if (bda.getBinary() == null)
				continue;

			List<CVParam> cvpList = bda.getCvParam();
			for (CVParam cvp : cvpList) {
				if (cvp.getAccession().equals("MS:1000514")) {
					mzNumbers = bda.getBinaryDataAsNumberArray();
					break;
				} else if (cvp.getAccession().equals("MS:1000515")) {
					intenNumbers = bda.getBinaryDataAsNumberArray();
					break;
				}
			}
		}

		double[] mz = new double[mzNumbers.length];
		for (int i = 0; i < mzNumbers.length; i++) {
			mz[i] = mzNumbers[i].doubleValue();
		}

		double[] intensities = new double[intenNumbers.length];
		for (int i = 0; i < intenNumbers.length; i++) {
			intensities[i] = intenNumbers[i].doubleValue();
		}

		// Call the spectrum panel from compomics.org

		JPanel spectrumPanel = null;
		if (mz.length != 0 && intensities.length != 0) {
			spectrumPanel = new SpectrumPanel(mz, intensities, parIonMz,
					Integer.toString(parCharge), sID, 50, true, true, true,
					msLevel);
			spectrumPanel.setSize(new Dimension(600, 400));
			spectrumPanel.setPreferredSize(new Dimension(600, 400));
		} else {
			spectrumPanel = new JPanel();
		}

		return spectrumPanel;
	}
}
