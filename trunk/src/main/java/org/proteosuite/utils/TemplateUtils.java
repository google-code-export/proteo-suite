package org.proteosuite.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.data.psTemplate;
import org.proteosuite.data.psTemplateQuant;
import org.proteosuite.external.IPC;
import org.proteosuite.external.IPC.Options;
import org.proteosuite.external.IPC.Results;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

public class TemplateUtils {

	/**
	 * Generate template for Label Free Analysis
	 * 
	 * @param scanIndex1
	 *            - Scan number e.g. 1671
	 * @param scanIndex2
	 *            - Scan number e.g. 1672
	 * @param mzStarts
	 *            - m/z starts from
	 * @param mzEnds
	 *            - m/z ends at
	 * @param resolution
	 *            - peaks left-right
	 * @return - void
	 */
	public void generateTemplate(int scanIndex1, int scanIndex2,
			double mzStarts, double mzEnds, int peaks, JTabbedPane jtpLog,
			JTable jtTemplate1, JTable jtTemplate2, JTable jtMascotXMLView,
			List<MzMLUnmarshaller> aMzMLUnmarshaller, JTable jTable1,
			JTable jTable2) {
		int iFileIndex = 0; // ... Index to mzML raw data ...//
		DefaultTableModel model = new DefaultTableModel();
		DefaultTableModel model2 = new DefaultTableModel();
		jtTemplate1.setModel(model);
		jtTemplate2.setModel(model2);
		model.addColumn("m/z Index");
		model.addColumn("Scan Index");
		model.addColumn("Quant");
		model.addColumn("Template2 Index");
		model2.addColumn("Template Index");
		model2.addColumn("x");
		model2.addColumn("y");
		model2.addColumn("i");

		System.out.println("Starting label free plugin...");

		// ... 1) Select MS/MS identifications that are specified in the
		// parameter window (e.g. From scan 1671 to 1672) ...//
		Map<String, List<String>> hmPeptides = new HashMap<String, List<String>>();
		int iCountPeptides = 0; // ... Peptide counter ...//
		boolean blnExists = false;
		for (int iI = 0; iI < jtMascotXMLView.getRowCount(); iI++) {
			// ... Range based on scan index window ...//
			if ((scanIndex1 <= Integer.parseInt(jtMascotXMLView.getValueAt(iI,
					8).toString()))
					&& (scanIndex2 >= Integer.parseInt(jtMascotXMLView
							.getValueAt(iI, 8).toString()))) {
				List<String> al = new ArrayList<String>();
				al.add(Integer.toString(iI)); // ... Index in the grid ...//
				al.add(jtMascotXMLView.getValueAt(iI, 3).toString()); // ... 2)
																		// Peptide
																		// molecular
																		// composition
																		// which
																		// was
																		// calculated
																		// previously
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 8).toString()); // ...
																		// Scan
																		// index
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 9).toString()); // ...
																		// Scan
																		// ID
																		// (jmzml
																		// API
																		// only
																		// supports
																		// getElementBy(scanID)
																		// ...//
				al.add(jtMascotXMLView.getValueAt(iI, 10).toString()); // ...
																		// Retention
																		// time
																		// ...//
				// ... Check if peptide has been added previously ...//
				blnExists = hmPeptides.containsKey(jtMascotXMLView.getValueAt(
						iI, 2).toString());
				if (blnExists == false) {
					hmPeptides.put(
							jtMascotXMLView.getValueAt(iI, 2).toString(), al); // ...
																				// This
																				// eliminates
																				// peptides
																				// which
																				// have
																				// been
																				// identified
																				// more
																				// than
																				// once
																				// (e.g.
																				// for
																				// different
																				// proteins)
																				// ...//
					iCountPeptides++; // ... Total peptides (identifications) in
										// the scan range ...//
				}
			}
		}
		if (iCountPeptides > 0) {
			System.out.println("Peptides in range = " + iCountPeptides);

			// ... Determine the length (resolution) of the resampling array.
			// ...//
			Map<String, float[][]> hmResamplingArray = new HashMap<String, float[][]>();
			int mzWindow = (int) (mzEnds - mzStarts);
			System.out.println("mzWindow=" + mzWindow);
			float resamplingFactor = 1.0f / 120.0f; // ... e.g. 0.008333334
													// Daltons ...//
			System.out.println("Resampling factor=" + resamplingFactor);
			float lengthArray = NumericalUtils.round(mzWindow
					/ resamplingFactor, 1);
			System.out.println("length=" + lengthArray);
			final int MZ_SIZE = (int) lengthArray;
			System.out.println("SIZE=" + MZ_SIZE);
			float resolution = (float) NumericalUtils.truncate(
					(NumericalUtils.round((float) (resamplingFactor), 8)), 8);
			System.out.println("resolution=" + resolution);
			float[][] resamplingArray = new float[2][MZ_SIZE];
			for (int iI = 0; iI < MZ_SIZE; iI++) {
				resamplingArray[0][iI] = (float) mzStarts + (resolution * iI);
				resamplingArray[1][iI] = 0;
			}
			Number[] mzSyntValues = new Number[MZ_SIZE];
			for (int iX = 0; iX < resamplingArray[0].length; iX++) {
				mzSyntValues[iX] = resamplingArray[0][iX];
			}

			final int PEAKS = peaks; // ... Number of peaks to retrieve (width
										// of the template, e.g. 4 left, 4
										// right) ...//
			final int NUMBER_PEAKS = PEAKS * 2 + 1; // ... Number of peaks for
													// each isotope ...//
			int[] aPeakIndexes = new int[PEAKS * 2 + 1];

			System.out.println("PEAKS_PARAM=" + PEAKS);
			System.out.println("TOTAL_PEAKS=" + NUMBER_PEAKS);

			// .. Define templates (A and C) ...//
			int iTempAIndex = 0; // ... Index for Template A ...//
			int iQuantTemplateIndex = 0; // ... Index for Template C ...//

			psTemplate[] templates = new psTemplate[iCountPeptides];
			psTemplateQuant[] quantTemplate = new psTemplateQuant[iCountPeptides
					* NUMBER_PEAKS];
			// psTemplateQuant2[] quantTemplate2 = new
			// psTemplateQuant2[iCountPeptides*NUMBER_PEAKS]; //... This will be
			// the final template once the resampling is done ...//

			// ... Initialize arrays via their constructors (TemplateA=templates
			// and TemplateC=templateQuant) ...//
			for (int iI = 0; iI < templates.length; iI++) {
				templates[iI] = new psTemplate();
			}
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				quantTemplate[iI] = new psTemplateQuant();
			}

			// ... 3) For each peptide, calculate the isotopic pattern
			// distribution ...//
			String sScanID = "";
			int iScanIndex = 0;
			for (Map.Entry<String, List<String>> entry : hmPeptides.entrySet()) {
				System.out
						.println("Calculating the Isotopic Patter Distribution (IPD) for peptide "
								+ entry.getKey());

				// ... Specify parameters ...//
				List<String> saParams = entry.getValue();
				Object saArray[] = saParams.toArray();
				for (String saParam : saParams) {
					// ... Show the parameters in the array
					// list (index, molecular composition,
					// scanNumber, scanID, rt) ...//
					System.out.println("Param = " + saParam + "; ");
				}
				// ... Using IPC to calculate the isotopic pattern distribution
				// ...//
				String[] args = {};
				String sCharge = jtMascotXMLView.getValueAt(
						Integer.parseInt(saArray[0].toString()), 6).toString();
				args = new String[] { "-a", entry.getKey().toString(), "-fc",
						"-z", sCharge, "-f", "1000", "-r", "10000" };
				if (args.length == 0) {
					System.exit(0);
				}
				IPC ipc = new IPC();
				Options options = Options.parseArgs(args);
				options.setPrintOutput(false);
				Results res = ipc.execute(options);
				Object[] objArray = res.getPeaks().toArray();
				float[] aMz = new float[objArray.length]; // ... There will be
															// six isotopes
															// estimated using
															// the IPC class
															// ...//
				float[] aRelIntens = new float[objArray.length];
				System.out.println("==== IPD ====");
				for (int iI = 0; iI < objArray.length; iI++) {
					aMz[iI] = Float.parseFloat(String.format("%.4f", res
							.getPeaks().first().getMass()));
					aRelIntens[iI] = Float.parseFloat(String.format("%.4f", res
							.getPeaks().first().getRelInt() * 100));
					System.out.println(aMz[iI] + "\t" + aRelIntens[iI]);
					res.getPeaks().pollFirst();
				}

				// ... 4) Generate templates for seaMass by getting the m/z
				// indexes for each isotope ...//
				MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller
						.get(iFileIndex);
				try {
					// ... 4.1) Get precursor ion (from 1671 would be 1666 on
					// the CTPAC example) ...//
					sScanID = saArray[3].toString();
					iScanIndex = Integer.parseInt(saArray[2].toString());
					Spectrum spectrum = unmarshaller.getSpectrumById(sScanID);
					PrecursorList plist = spectrum.getPrecursorList(); // ...
																		// Get
																		// precursor
																		// ion
																		// ...//
					if (plist != null) {
						if (plist.getCount().intValue() == 1) {

							// ... Perform a binary search to find the m/z index
							// closed to the m/z values estimated from the IPC
							// ...//
							int iPos = -1, iPosPrev = -1;
							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[0], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);

							// ... Generate psTemplate (index, {x, y, i}) ...//
							templates[iTempAIndex].setIndex(iTempAIndex);
							templates[iTempAIndex].setCoords(0, aRelIntens[0],
									0); // ... aRelIntens[0] contains the
										// highest value i.e. 100% ...//

							// ... Generate psTemplateQuant (mzIndex, scanIndex,
							// quant, temp2Index) ...//
							for (int iI = 0; iI < NUMBER_PEAKS; iI++) {
								quantTemplate[iI + iQuantTemplateIndex]
										.setTemplate(aPeakIndexes[iI], // ...
																		// mzIndex
																		// ...//
												plist.getPrecursor().get(0)
														.getSpectrumRef()
														.toString(), // ... scan
																		// ID
																		// ...//
												iScanIndex, // ... scanIndex
															// ...//
												1, // ... Initial quant value
													// ...//
												iTempAIndex); // ... Index to
																// template A
																// ...//
							}
							iQuantTemplateIndex += NUMBER_PEAKS; // ... Skip 9
																	// positions
																	// (2*4+1)
																	// ..../
							iPosPrev = iPos;

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[1], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[1], 1);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[2], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[2], 2);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[3], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[3], 3);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[4], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[4], 4);

							iPos = BinaryUtils.binarySearch(mzSyntValues, 0,
									mzSyntValues.length, aMz[5], true);
							aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
							templates[iTempAIndex].setCoords(iPos - iPosPrev,
									aRelIntens[5], 5);
							iTempAIndex++;
						}
					} // ... If precursor ion ...//
					jtpLog.setSelectedIndex(2);
				} catch (MzMLUnmarshallerException e) {
					System.out.println(e);
				}
			} // ... From For ...//

			// ... Perform normalisation ...//
			float fSum = 0.0f;
			float fNewValue = 0.0f;
			for (int iI = 0; iI < templates.length; iI++) {
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					fSum += Math.pow(templates[iI].getCoord(iJ)
							.getRelIntensity(), 2.0);
				}
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					fNewValue = templates[iI].getCoord(iJ).getRelIntensity()
							/ fSum;
					templates[iI].setCoords(templates[iI].getCoord(iJ).getX(),
							templates[iI].getCoord(iJ).getY(), fNewValue, iJ);
				}
			}
			System.out.println("Normalisation finished!");

			// ... Populate the Grids ...//
			System.out.println("Populating grids...");
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				model.insertRow(model.getRowCount(),
						new Object[] { quantTemplate[iI].getMzIndex(),
								quantTemplate[iI].getScanID(),
								quantTemplate[iI].getQuantIntensities(),
								quantTemplate[iI].getTemplate2Index() });
			}
			for (int iI = 0; iI < templates.length; iI++) {
				for (int iJ = 0; iJ < templates[iI].getCoords().length; iJ++) {
					model2.insertRow(model2.getRowCount(), new Object[] {
							templates[iI].getIndex(),
							templates[iI].getCoord(iJ).getX(),
							templates[iI].getCoord(iJ).getY(),
							templates[iI].getCoord(iJ).getRelIntensity() });
				}
			}

			// ... Generate the synthetic array which will be used by seaMass to
			// calculate the quantitation values ...//
			Map<String, float[][]> hmSyntheticArray = new HashMap<String, float[][]>();
			MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
			String sPrev = "", sNext = "";

			// ... Parse MS1 data ...//
			System.out.println("Parsing MS1 data ...");
			List<String[]> array = getMS1DataRefs(unmarshaller);
			String scanID = "";
			String[] triplets = new String[] { "", "", "" };

			System.out.println("Generate resampling array ...");
			for (int iI = 0; iI < quantTemplate.length; iI++) {
				scanID = quantTemplate[iI].getScanID();
				blnExists = hmSyntheticArray.containsKey(quantTemplate[iI]
						.getScanID());
				if (blnExists == false) {
					// ... Get Previous and Next MS1 scans ...//
					sPrev = getPrevScan(array, scanID);
					sNext = getNextScan(array, scanID);
					System.out.println("sPrev=" + sPrev);
					System.out.println("scanID=" + scanID);
					System.out.println("sNext=" + sNext);
					triplets[0] = sPrev;
					triplets[1] = scanID;
					triplets[2] = sNext;

					for (int iTrip = 0; iTrip < triplets.length; iTrip++) {
						// ... Go to the raw data and create an resampling array
						// ...//
						try {
							Spectrum spectrum = unmarshaller
									.getSpectrumById(triplets[iTrip]);
							List<BinaryDataArray> bdal = spectrum
									.getBinaryDataArrayList()
									.getBinaryDataArray();

							Number[] mzNumbers = null;
							Number[] intenNumbers = null;
							// ... Reading mz and intensity values ...//
							for (BinaryDataArray bda : bdal) {
								List<CVParam> cvpList = bda.getCvParam();
								for (CVParam cvp : cvpList) {
									if (cvp.getAccession().equals("MS:1000514")) {
										mzNumbers = bda
												.getBinaryDataAsNumberArray();
									}
									if (cvp.getAccession().equals("MS:1000515")) {
										intenNumbers = bda
												.getBinaryDataAsNumberArray();
									}
								}
							}
							// ... Create the hashmap for the resampling array
							// ...//
							blnExists = hmResamplingArray
									.containsKey(triplets[iTrip]);
							if (blnExists == false) {
								System.out
										.println("Performing resampling in scan = "
												+ triplets[iTrip]);
								float[][] resArray = BinaryUtils.binArray(
										resamplingArray, mzNumbers,
										intenNumbers, mzWindow, true);
								hmResamplingArray
										.put(triplets[iTrip], resArray);
								hmSyntheticArray.put(triplets[iTrip],
										resamplingArray); // ... Initalising
															// synthetic array
															// ...//
							}
						} catch (MzMLUnmarshallerException ume) {
							System.out.println(ume.getMessage());
						}
					}
				}
			}

			System.out.println("Creating mapping for scan indexes...");

			// ... Map keys for Scan IDs ...//
			String[] scanMap = new String[hmResamplingArray.size()];
			int iMapIndex = 0;
			for (Entry<String, float[][]> entry : hmResamplingArray.entrySet()) {
				scanMap[iMapIndex] = entry.getKey();
				iMapIndex++;
			}
			// ... Sort array ...//
			Arrays.sort(scanMap);

			// ... Populate Arrays ...//
			DefaultTableModel model3 = new DefaultTableModel();
			DefaultTableModel model4 = new DefaultTableModel();
			jTable1.setModel(model3);
			jTable2.setModel(model4);
			model3.addColumn("m/z Index");
			model3.addColumn("m/z Value");
			model4.addColumn("m/z Index");
			model4.addColumn("m/z Value");

			// ... Adding column headers ...//
			for (int iI = 0; iI < scanMap.length; iI++) {
				model3.addColumn(scanMap[iI]);
				model4.addColumn(scanMap[iI]);
			}

			System.out.println("Populating arrays  (inseting rows) ...");
			if (scanMap.length >= 0) {
				blnExists = hmResamplingArray.containsKey(scanMap[0]);
				if (blnExists == true) {
					float[][] temp = hmResamplingArray.get(scanMap[0]);
					for (int iI = 0; iI < temp[0].length; iI++) {
						model3.insertRow(model3.getRowCount(), new Object[] {
								iI, temp[0][iI], temp[1][iI] });
						model4.insertRow(model4.getRowCount(), new Object[] {
								iI, temp[0][iI], temp[1][iI] });
					}
				}
			}
			System.out
					.println("Populating arrays  (updating rows from hmSyntheticArray) ...");
			// ... Now it's time to load the rest of the columns ...//
			for (int iJ = 1; iJ < scanMap.length; iJ++) {
				blnExists = hmSyntheticArray.containsKey(scanMap[iJ]);
				if (blnExists == true) {
					float[][] temp2 = hmSyntheticArray.get(scanMap[iJ]);
					for (int iI = 0; iI < temp2[0].length; iI++) {
						model3.setValueAt(temp2[1][iI], iI, iJ + 2);
					}
				}
			}
			System.out
					.println("Populating arrays  (updating rows from hmResamplingArray) ...");
			// ... Now it's time to load the rest of the columns ...//
			for (int iJ = 1; iJ < scanMap.length; iJ++) {
				System.out.println("updating rows for hmsResamplingArray "
						+ scanMap[iJ]);
				blnExists = hmResamplingArray.containsKey(scanMap[iJ]);
				if (blnExists == true) {
					float[][] temp3 = hmResamplingArray.get(scanMap[iJ]);
					System.out.println("In_memory2=" + temp3[0][16] + "\tmz="
							+ temp3[1][16]);
					for (int iI = 0; iI < temp3[0].length; iI++) {
						model4.setValueAt(temp3[1][iI], iI, iJ + 2);
					}
				}
			}
			// //... Update synthetic array and quant templates ...//
			// System.out.println("Updating synthetic array and quant templates...");
			// int iTemp1Index = 0, iMzIndexResamplArray = 0;
			// float quantValue = 0.0f;
			// int iX=0, iY=0;
			// float fI=0.0f;
			// for(int iI=0; iI<quantTemplate.length; iI++){
			// scanID = quantTemplate[iI].getScanID();
			// blnExists =
			// hmSyntheticArray.containsKey(quantTemplate[iI].getScanID());
			// if (blnExists){
			// System.out.println("Updating scanID="+scanID);
			// //... Get previous and next scans (-1, 0 and +1 positions in rt)
			// ...//
			// sPrev = getPrevScan(array, scanID);
			// sNext = getNextScan(array, scanID);
			//
			// triplets[0] = sPrev;
			// triplets[1] = scanID;
			// triplets[2] = sNext;
			//
			// iTemp1Index = quantTemplate[iI].getTemplate2Index();
			// iMzIndexResamplArray = quantTemplate[iI].getMzIndex(); //... This
			// will be used to calculate -1, 0, +1 in m/z ...//
			// quantValue = quantTemplate[iI].getQuantIntensities();
			//
			// for(int iZ=0; iZ<templates[iTemp1Index].getCoords().length;
			// iZ++){ //... 54 positions (9 points x 6 isotopes) ...//
			// //... Update values using template A ...//
			// iX = templates[iI].getCoord(iZ).getX();
			// iY = templates[iI].getCoord(iZ).getY();
			// fI = templates[iI].getCoord(iZ).getRelIntensity();
			//
			// //... Get synthetic array for each scan ...//
			// float[][] temp = hmSyntheticArray.get(triplets[iY+1]);
			// temp[1][iTemp1Index+iX] = temp[1][iTemp1Index+iX] + (quantValue *
			// fI);
			// hmSyntheticArray.put(triplets[iY+1], temp);
			// }
			// }
			// }

		} // ... From if countPeptides ...//
	}


	/**
	 * Get neighbour peaks from a given mz value
	 * 
	 * @param array
	 *            - Array with MS data
	 * @param iIndex
	 *            - location in the array
	 * @param iOffset
	 *            - Number of peaks to cover
	 * @return array of integers with peak indexes
	 */
	private int[] getPeaks(Number[] nArray, int iIndex, int iOffset) {
		int[] aPeaks = new int[iOffset * 2 + 1];
		int iCount = 0;
		if ((iIndex - iOffset > 0) && (iIndex + iOffset < nArray.length)) {
			for (int iI = iIndex - iOffset; iI <= iIndex + iOffset; iI++) {
				aPeaks[iCount] = iI;
				iCount++;
				System.out.println("Index=" + iI + "\t"
						+ nArray[iI].floatValue());
			}
		}
		return aPeaks;
	}

	/**
	 * Get spectrum IDs and spectrum References across MS1 data
	 * 
	 * @param unmarshall
	 *            - Raw file
	 * @return - array of strings containing the spectrum IDs
	 */
	private List<String[]> getMS1DataRefs(MzMLUnmarshaller unmarshaller) {
		List<String[]> array = new ArrayList<String[]>();
		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);
		while (spectrumIterator.hasNext()) {
			Spectrum spectrum = spectrumIterator.next();

			// ... Identify MS1 data ...//
			String mslevel = "";
			for (CVParam lCVParam : spectrum.getCvParam()) {
				if (lCVParam.getAccession().equals("MS:1000511")) {
					mslevel = lCVParam.getValue().trim();
				}
			}
			if (mslevel.equals("1")) {
				array.add(new String[] { spectrum.getIndex().toString(),
						spectrum.getId() });
			}
		}
		// ... Sort by spectrum Index ...//
		Collections.sort(array, new Comparator<String[]>() {
			public int compare(String[] strings1, String[] strings2) {
				strings1[0] = String.format("%08d",
						Integer.parseInt(strings1[0]));
				strings2[0] = String.format("%08d",
						Integer.parseInt(strings2[0]));
				return strings1[0].compareTo(strings2[0]);
			}
		});
		return array;
	}

	/**
	 * Get previous MS1 scan
	 * 
	 * @param array
	 *            - Array with MS1 scan ids
	 * @param sScanID
	 *            - Scan ID reference
	 */
	private String getPrevScan(List<String[]> array, String sScanID) {
		String sPrev = "";
		// int iJ = 0;
		for (String[] value : array) {
			if (value[1].equals(sScanID)) {
				break;
			} else {
				sPrev = value[1];
			}
			// iJ++;
		}
		return sPrev;
	}

	/**
	 * Get next MS1 scan
	 * 
	 * @param array
	 *            - Array with MS1 scan ids
	 * @param sScanID
	 *            - Scan ID reference
	 */
	private String getNextScan(List<String[]> array, String sScanID) {
		String sNext = "";
		int iJ = 0;
		for (String[] value : array) {
			if (value[1].equals(sScanID)) {
				if (iJ < array.size()) {
					sNext = array.get(iJ + 1)[1];
					break;
				} else {
					sNext = value[1];
				}
			}
			iJ++;
		}
		return sNext;
	}
}
