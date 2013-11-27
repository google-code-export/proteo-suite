package org.proteosuite.gui.chart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.proteosuite.utils.DeltaConversion;
import org.proteosuite.utils.DeltaConversion.DeltaEncodedDataFormatException;
import org.proteosuite.utils.NumericalUtils;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

import com.compomics.util.gui.spectrum.SpectrumPanel;

public class ChartSpectrum {

	/**
	 * Display MS spectrum
	 * 
	 * @param iIndex
	 *            - Index to the aMzMLUnmarshaller list
	 * @param sID
	 *            - spectrum ID
	 * @return void
	 */
	public static void getSpectrum(MzMLUnmarshaller unmarshaller, String sID,
			JDesktopPane jdpMS, JInternalFrame jifViewSpectrum) {
		try {
			Spectrum spectrum = unmarshaller.getSpectrumById(sID);

			// ... Reading CvParam to identify the MS level (1, 2) ...//
			String mslevel = "";
			for (CVParam lCVParam : spectrum.getCvParam()) {
				if (lCVParam.getAccession().equals("MS:1000511")) {
					mslevel = lCVParam.getValue().trim();
				}
			}
			float parIonMz = 0;
			int parCharge = 0;
			if (mslevel.toString().indexOf("2") >= 0) {
				// Get precursor ion
				PrecursorList plist = spectrum.getPrecursorList();
				if (plist == null || plist.getCount().intValue() != 1)
					return;

				// Detect parent ion m/z and charge
				for (CVParam lCVParam : plist.getPrecursor().get(0)
						.getSelectedIonList().getSelectedIon().get(0)
						.getCvParam()) {
					if (lCVParam.getAccession().equals("MS:1000744")) {
						parIonMz = Float.parseFloat(lCVParam.getValue().trim());
					}
					if (lCVParam.getAccession().equals("MS:1000041")) {
						parCharge = Integer
								.parseInt(lCVParam.getValue().trim());
					}
				}
			}

			Number[] mzNumbers = null;
			Number[] intenNumbers = null;

			boolean bCompressed = false;
			// Reading mz Values
			List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
					.getBinaryDataArray();
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000000")) {
						bCompressed = true;
					}
				}
			}

			// Reading mz and intensity values
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000514")) {
						mzNumbers = bda.getBinaryDataAsNumberArray();
						if (bCompressed) {
							try {
                                DeltaConversion.fromDeltaNumberFormat(mzNumbers);
                            } catch (DeltaEncodedDataFormatException dex) {
                                System.out.println("Problem converting back from delta m/z format: " + dex.getLocalizedMessage());
                                return;
                            }
						}
					}
					if (cvp.getAccession().equals("MS:1000515")) {
						intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
			}

			double[] mz = new double[mzNumbers.length];
			for (int iI = 0; iI < mzNumbers.length; iI++) {
				mz[iI] = mzNumbers[iI].doubleValue();
			}
			double[] intensities = new double[intenNumbers.length];
			for (int iI = 0; iI < intenNumbers.length; iI++) {
				intensities[iI] = intenNumbers[iI].doubleValue();
			}
			// Call the spectrum panel from compomics.org
			jdpMS.removeAll();

			JPanel specpanel = new SpectrumPanel(mz, intensities, parIonMz,
					Integer.toString(parCharge), sID, 50, false, true, true,
					true, Integer.parseInt(mslevel));
			specpanel.setSize(new Dimension(600, 400));
			specpanel.setPreferredSize(new Dimension(600, 400));
			
			
			jifViewSpectrum.setTitle("MS spectrum <ScanID: " + sID + ">");
			jifViewSpectrum.add(specpanel, BorderLayout.CENTER);
			jifViewSpectrum.setSize(jdpMS.getSize());
			
			jdpMS.add(jifViewSpectrum, BorderLayout.CENTER);
			jdpMS.revalidate();
			jdpMS.repaint();
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
	}
}
