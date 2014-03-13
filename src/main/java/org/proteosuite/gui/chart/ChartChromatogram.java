package org.proteosuite.gui.chart;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import java.awt.Dimension;
import java.util.List;
import org.proteosuite.ProteoSuiteView;
import org.proteosuite.model.RawMzMLFile;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

public class ChartChromatogram {

    /**
     * Display total ion chromatogram
     *
     * @param unmarshaller
     * @return
     */
    public static ChromatogramPanel getChromatogram(RawMzMLFile dataFile) {
        MzMLUnmarshaller unmarshaller = dataFile.getUnmarshaller();
        MzMLObjectIterator<Chromatogram> iterator = unmarshaller.unmarshalCollectionFromXpath("/run/chromatogramList/chromatogram", Chromatogram.class);
        while (iterator.hasNext()) {
            Chromatogram chrom = iterator.next();
            
            // Check if mzML contains MS1 data
            if (chrom.getId().toUpperCase().equals("TIC")) {
                return createChromatogramPanel(chrom);
            }
            
            for (CVParam param : chrom.getCvParam()) {
                if (param.getName().toUpperCase().equals("TOTAL ION CURRENT CHROMATOGRAM")) {
                    return createChromatogramPanel(chrom);
                }
            }
        }

        System.out.println(ProteoSuiteView.SYS_UTILS.getTime()
                + " - This mzML file doesn't contain MS2 raw data.");
            
        return null;
    }

    private static ChromatogramPanel createChromatogramPanel(Chromatogram chrom) {
        Number[] rtNumbers = null;
        Number[] intenNumbers = null;
        List<BinaryDataArray> bdal = chrom.getBinaryDataArrayList()
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
    }
}
