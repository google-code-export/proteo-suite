package org.proteosuite.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzMLFile;
import static org.proteosuite.utils.StringUtils.emptyString;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.SourceFile;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

public class FileFormatUtils {

    private static final Pattern terminatingZeroPattern = Pattern.compile("[.]{0,1}[0]+$");

    public static boolean mzMLToMGF(MzMLFile input, String output) {
        return mzMLToMGF(input.getUnmarshaller(), output);
    }

    public static MascotGenericFormatFile merge(Set<MascotGenericFormatFile> setToMerge, long mergeLimit) {
        Set<MascotGenericFormatFile> mergeableSet = new HashSet<>();
        for (MascotGenericFormatFile thisFile : setToMerge) {
            if (thisFile.getFile().length() <= mergeLimit) {
                mergeableSet.add(thisFile);
                mergeLimit -= thisFile.getFile().length();
            } else {
                throw new MgfOverMergeLimitException(thisFile.getFile().length() - mergeLimit);
            }
        }

        return merge(mergeableSet);
    }

    public static MascotGenericFormatFile merge(Set<MascotGenericFormatFile> setToMerge) {
        Set<String> setToMergeAsStrings = new HashSet<>();
        String outputFile = null;
        for (MascotGenericFormatFile file : setToMerge) {
            setToMergeAsStrings.add(file.getAbsoluteFileName());
            if (outputFile == null) {
                outputFile = file.getAbsoluteFileName().replaceFirst(".mgf", "_merge.mgf");
            }
        }

        if (mergeMGF(setToMergeAsStrings, outputFile)) {
            return new MascotGenericFormatFile(new File(outputFile));
        }

        return null;
    }

    public static boolean mergeMGF(Set<String> setToMerge, String outputPath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (String mgfFile : setToMerge) {
                try (BufferedReader reader = new BufferedReader(new FileReader(mgfFile))) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception merging MGF files.");
            return false;
        }

        return true;
    }

    public static boolean mzMLToMGF(MzMLUnmarshaller input, String output) {        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            String originalRawFileName = null;
            MzMLObjectIterator<SourceFile> sourceFiles = input.unmarshalCollectionFromXpath("/fileDescription/sourceFileList/sourceFile", SourceFile.class);
            while (sourceFiles.hasNext()) {
                SourceFile sourceFile = sourceFiles.next();
                originalRawFileName = sourceFile.getName();
                if (originalRawFileName != null) {
                    break;
                }
            }

            String mzmlId = input.getMzMLId();

            System.out.println("Unmarshalling finished!");

            int invalidCount = 0;

            //... Reading entire spectra ...//
            MzMLObjectIterator<Spectrum> spectrumIterator = input.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            while (spectrumIterator.hasNext()) {
                //... Reading a individual spectrum ...//
                Spectrum spectrum = spectrumIterator.next();

                //... Reading CvParam to identify the MS level (1, 2) ...//
                String msLevel = emptyString();
                List<CVParam> spectrumParams = spectrum.getCvParam();
                for (CVParam spectrumParam : spectrumParams) {
                    if (spectrumParam.getAccession().equals("MS:1000511")) {
                        msLevel = spectrumParam.getValue().trim();
                    }
                }

                //... Getting Retention Time (rt) ...//
                float rt = 0;
                String rtUnit = emptyString();
                List<CVParam> scanParams = spectrum.getScanList().getScan().get(0).getCvParam();

                for (CVParam scanParam : scanParams) {
                    if (scanParam.getAccession().equals("MS:1000016")) {
                        rtUnit = scanParam.getUnitAccession().trim();
                        if (rtUnit.equals("UO:0000031")) {
                            rt = Float.parseFloat(scanParam.getValue().trim()) * 60;
                        } else {
                            rt = Float.parseFloat(scanParam.getValue().trim());
                        }
                    }
                }

                if (msLevel.toUpperCase().contains("2")) {
                    PrecursorList precursorList = spectrum.getPrecursorList(); //... Get precursor ion ...//
                    if (precursorList != null) {
                        if (precursorList.getCount() != null && precursorList.getCount() == 1) {
                            List<CVParam> scanPrecursorParams = precursorList.getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam();
                            float parentIonMz = 0;
                            float parentIntensity = 0;
                            int parentCharge = 0;

                            //... Detect parent ion m/z and charge ...//
                            for (CVParam scanPrecursorParam : scanPrecursorParams) {
                                switch (scanPrecursorParam.getAccession()) {
                                    case "MS:1000744":
                                        parentIonMz = Float.parseFloat(scanPrecursorParam.getValue().trim());
                                        break;
                                    case "MS:1000041":
                                        parentCharge = Integer.parseInt(scanPrecursorParam.getValue().trim());
                                        break;
                                    case "MS:1000042":
                                        parentIntensity = Float.parseFloat(scanPrecursorParam.getValue().trim());
                                        break;
                                }
                            }

                            //... Binary data ...//
                            List<BinaryDataArray> binaryDataArrays = spectrum.getBinaryDataArrayList().getBinaryDataArray();

                            //... Reading mz Values (Peaks) ...//
                            Number[] mzNumbers = null;
                            Number[] intenNumbers = null;

                            //... Reading mz and intensity values ...//
                            for (BinaryDataArray dataArray : binaryDataArrays) {
                                List<CVParam> dataArrayParams = dataArray.getCvParam();
                                for (CVParam dataArrayParam : dataArrayParams) {
                                    if (dataArray.getEncodedLength() > 0) {
                                        switch (dataArrayParam.getAccession()) {
                                            case "MS:1000514":
                                                mzNumbers = dataArray.getBinaryDataAsNumberArray();
                                                break;
                                            case "MS:1000515":
                                                intenNumbers = dataArray.getBinaryDataAsNumberArray();
                                                break;
                                        }
                                    }
                                }
                            }

                            if (parentCharge == 0) //... In case it is not specified ...//
                            {
                                parentCharge = 1;
                            }

                            writer.write("BEGIN IONS");
                            writer.write("\n");

                            writer.write("TITLE=" + mzmlId + '.' + (spectrum.getIndex() + 1) + '.' + (spectrum.getIndex() + 1) + '.' + parentCharge);
                            writer.write(" File:\"" + originalRawFileName + "\", NativeID:\"" + spectrum.getId() + "\"");
                            writer.write("\n");

                            writer.write("RTINSECONDS=" + terminatingZeroPattern.matcher(String.format("%.4f", rt)).replaceFirst(StringUtils.emptyString()));
                            writer.write("\n");

                            writer.write("PEPMASS=" + terminatingZeroPattern.matcher(String.format("%.12f", parentIonMz)).replaceFirst(StringUtils.emptyString())
                                    + " " + terminatingZeroPattern.matcher(String.format("%.10f", parentIntensity)).replaceFirst(StringUtils.emptyString()));
                            writer.write("\n");

                            writer.write("CHARGE=" + parentCharge + "+");
                            writer.write("\n");

                            if (mzNumbers != null && intenNumbers != null && mzNumbers.length == intenNumbers.length) {
                                double mz;
                                double intensity;

                                for (int i = 0; i < mzNumbers.length; i++) {
                                    mz = mzNumbers[i].doubleValue();
                                    intensity = intenNumbers[i].doubleValue();
                                    if (intensity > 0.0001) {
                                        String formattedMz = terminatingZeroPattern.matcher(String.format("%.7f", mz)).replaceFirst(StringUtils.emptyString());
                                        if (formattedMz.length() > 11) {
                                            int overhang = formattedMz.length() - 11;
                                            formattedMz = terminatingZeroPattern.matcher(String.format("%." + (7 - overhang) + "f", mz)).replaceFirst(StringUtils.emptyString());
                                        }

                                        writer.write(formattedMz + " " + terminatingZeroPattern.matcher(String.format("%.10f", intensity)).replaceFirst(StringUtils.emptyString()));
                                        writer.write("\n");
                                    }
                                }
                            } else {
                                invalidCount++;
                                if (mzNumbers == null) {
                                    System.out.println("mzNumbers is null for spectra " + spectrum.getIndex());
                                }

                                if (intenNumbers == null) {
                                    System.out.println("intenNumbers is null for spectra " + spectrum.getIndex());
                                }

                                if (mzNumbers != null && intenNumbers != null) {
                                    System.out.println("Length mismatch for spectra " + spectrum.getIndex() + ": " + mzNumbers.length + "\t" + intenNumbers.length);
                                }

                            }

                            writer.write("END IONS");
                            writer.write("\n");
                        }
                    }  //... If precursor ion
                } //... If MS2
            }   //... While

            System.out.println(invalidCount);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean mzMLToMGF(File input, String output) {
        try {
            FileWriter outputStream = new FileWriter(output);
            BufferedWriter writer = new BufferedWriter(outputStream);

            //... Unmarshall data using jzmzML API ...//
            System.out.println("Unmarshalling File: " + input.getName());

            MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(input);

            mzMLToMGF(unmarshaller, output);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Get molecular composition from a given peptide
     *
     * @param sPeptide Peptide sequence
     * @return Molecular composition
     */
    public static String getResidueComposition(String sPeptide) {
        /*
         * -------------------------------------------------------------- Name
         * 3-Sym 1-Sym Mono Avg Residues
         * --------------------------------------------------------------
         * Alanine Ala A 71.03711 71.08 C3H5NO Arginine Arg R 156.10111 156.2
         * C6H12N4O Asparagine Asn N 114.04293 114.1 C4H6N2O2 Aspartic Acid Asp
         * D 115.02694 115.1 C4H5NO3 Cysteine Cys C 103.00919 103.1 C3H5NOS
         * Glutamic Acid Glu E 129.04259 129.1 C5H7NO3 Glutamine Gln Q 128.05858
         * 128.1 C5H8N2O2 Glycine Gly G 57.02146 57.05 C2H3NO Histidine His H
         * 137.05891 137.1 C6H7N3O Isoleucine Ile I 113.08406 113.2 C6H11NO
         * Leucine Leu L 113.08406 113.2 C6H11NO Lysine Lys K 128.09496 128.2
         * C6H12N2O Methionine Met M 131.04049 131.2 C5H9NOS Phenyalanine Phe F
         * 147.06841 147.2 C9H9NO Proline Pro P 97.05276 97.12 C5H7NO Serine Ser
         * S 87.03203 87.08 C3H5NO2 Threonine Thr T 101.04768 101.1 C4H7NO2
         * Tryptophan Trp W 186.07931 186.2 C11H10N2O Tyrosine Tyr Y 163.06333
         * 163.2 C9H9NO2 Valine Val V 99.06841 99.13 C5H9NO
         * --------------------------------------------------------------
         */

        // Water molecule (H2O) + H1
        int iCarb = 0, iHydro = 3, iNitro = 0, iOxy = 1, iSulf = 0;
        String sPeptideRet = "";
        for (int iI = 0; iI < sPeptide.length(); iI++) {
            switch (sPeptide.toUpperCase().charAt(iI)) {
                case 'A':
                    iCarb += 3;
                    iHydro += 5;
                    iNitro++;
                    iOxy++;
                    break;
                case 'R':
                    iCarb += 6;
                    iHydro += 12;
                    iNitro += 4;
                    iOxy++;
                    break;
                case 'N':
                    iCarb += 4;
                    iHydro += 6;
                    iNitro += 2;
                    iOxy += 2;
                    break;
                case 'D':
                    iCarb += 4;
                    iHydro += 5;
                    iNitro++;
                    iOxy += 3;
                    break;
                case 'C':
                    iCarb += 3;
                    iHydro += 5;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                case 'E':
                    iCarb += 5;
                    iHydro += 7;
                    iNitro++;
                    iOxy += 3;
                    break;
                case 'Q':
                    iCarb += 5;
                    iHydro += 8;
                    iNitro += 2;
                    iOxy += 2;
                    break;
                case 'G':
                    iCarb += 2;
                    iHydro += 3;
                    iNitro++;
                    iOxy++;
                    break;
                case 'H':
                    iCarb += 6;
                    iHydro += 7;
                    iNitro += 3;
                    iOxy++;
                    break;
                case 'I':
                    iCarb += 6;
                    iHydro += 11;
                    iNitro++;
                    iOxy++;
                    break;
                case 'L':
                    iCarb += 6;
                    iHydro += 11;
                    iNitro++;
                    iOxy++;
                    break;
                case 'K':
                    iCarb += 6;
                    iHydro += 12;
                    iNitro += 2;
                    iOxy++;
                    break;
                case 'M':
                    iCarb += 5;
                    iHydro += 9;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                case 'F':
                    iCarb += 9;
                    iHydro += 9;
                    iNitro++;
                    iOxy++;
                    break;
                case 'P':
                    iCarb += 5;
                    iHydro += 7;
                    iNitro++;
                    iOxy++;
                    break;
                case 'S':
                    iCarb += 3;
                    iHydro += 5;
                    iNitro++;
                    iOxy += 2;
                    break;
                case 'T':
                    iCarb += 4;
                    iHydro += 7;
                    iNitro++;
                    iOxy += 2;
                    break;
                case 'W':
                    iCarb += 11;
                    iHydro += 10;
                    iNitro += 2;
                    iOxy++;
                    break;
                case 'Y':
                    iCarb += 9;
                    iHydro += 9;
                    iNitro++;
                    iOxy += 2;
                    break;
                case 'V':
                    iCarb += 5;
                    iHydro += 9;
                    iNitro++;
                    iOxy++;
                    break;
            }
        }
        if (iCarb > 0) {
            sPeptideRet = "C" + iCarb;
        }
        if (iHydro > 0) {
            sPeptideRet = sPeptideRet + "H" + iHydro;
        }
        if (iNitro > 0) {
            sPeptideRet = sPeptideRet + "N" + iNitro;
        }
        if (iOxy > 0) {
            sPeptideRet = sPeptideRet + "O" + iOxy;
        }
        if (iSulf > 0) {
            sPeptideRet = sPeptideRet + "S" + iSulf;
        }
        return sPeptideRet;
    }

    private static class MgfOverMergeLimitException extends RuntimeException {

        public MgfOverMergeLimitException(long overBy) {
            super("MGF data supplied is over the requested limit. Over by: " + overBy);
        }
    }
}
