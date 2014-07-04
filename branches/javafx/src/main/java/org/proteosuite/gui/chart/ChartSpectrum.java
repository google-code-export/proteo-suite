package org.proteosuite.gui.chart;

import com.compomics.util.gui.spectrum.SpectrumPanel;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.proteosuite.model.Feature;
import org.proteosuite.model.FragmentSpectrum;
import org.proteosuite.model.MzIntensityPair;
import org.proteosuite.model.PrecursorSpectrum;
import org.proteosuite.model.RawDataFile;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

public class ChartSpectrum extends AbstractChart {

    public static JPanel getSpectrum(RawDataFile rawDataFile, String spectrumID) {
        org.proteosuite.model.Spectrum spectrum = rawDataFile.getSpectrumByID(spectrumID);
        if (spectrum == null) {
            return null;
        }

        float precursorMz = 0;
        int precursorCharge = 0;
        if (spectrum instanceof FragmentSpectrum) {
            Feature precursor = ((FragmentSpectrum) spectrum).getPrecursor();
            precursorMz = (float) precursor.getMz();
            precursorCharge = precursor.getCharge();
        }

        double[] massCharges = new double[spectrum.size()];
        double[] intensities = new double[spectrum.size()];

        int i = 0;
        for (MzIntensityPair pair : spectrum) {
            massCharges[i] = pair.getMz();
            intensities[i] = pair.getIntensity();
            i++;
        }

        JPanel spectrumPanel = new SpectrumPanel(massCharges, intensities, precursorMz,
                Integer.toString(precursorCharge), spectrumID, 50, true, true, true,
                spectrum instanceof PrecursorSpectrum ? 1 : 2);
        spectrumPanel.setSize(new Dimension(600, 400));
        spectrumPanel.setPreferredSize(new Dimension(600, 400));

        return spectrumPanel;
    }

    public static JPanel getSpectrum(MzMLUnmarshaller unmarshaller, String sID) {
        Spectrum spectrum = null;
        try {
            spectrum = unmarshaller.getSpectrumById(sID);
        } catch (MzMLUnmarshallerException ume) {
            System.out.println(ume.getMessage());
        }

        if (spectrum == null) {
            return null;
        }

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

        double[] massCharges = null;
        double[] intensities = null;

        // Reading mz and intensity values
        for (BinaryDataArray binaryData : spectrum.getBinaryDataArrayList()
                .getBinaryDataArray()) {
            if (binaryData.getBinary() == null) {
                continue;
            }

            if (massCharges == null) {
                massCharges = getDouble(getMz(binaryData));
            }

            if (intensities == null) {
                intensities = getDouble(getIntensity(binaryData));
            }
        }

        // Call the spectrum panel from compomics.org
        JPanel spectrumPanel = null;
        if (massCharges.length != 0 && intensities.length != 0) {
            spectrumPanel = new SpectrumPanel(massCharges, intensities, parIonMz,
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
