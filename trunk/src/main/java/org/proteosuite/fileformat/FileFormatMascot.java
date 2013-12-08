package org.proteosuite.fileformat;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.proteosuite.utils.FileFormatUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileFormatMascot implements Runnable {

	/**
	 * Display Mascot XML files
	 * 
	 * @param sFileName File name
	 * @param sFilePath File location
	 * @return void
	 */
	public FileFormatMascot(String sFileName, String sFilePath,
			JTable jtMascotXMLView, JTabbedPane jtpProperties) {
		this.sFileName = sFileName;
		this.sFilePath = sFilePath;
		this.jtMascotXMLView = jtMascotXMLView;
		this.jtpProperties = jtpProperties;
	}

	private JTable jtMascotXMLView;
	private String sFilePath;
	private String sFileName;
	private JTabbedPane jtpProperties;

	@Override
	public void run() {
		DefaultTableModel model = new DefaultTableModel() {
			Class<?>[] types = new Class[] { Integer.class, String.class,
					String.class, String.class, Float.class, Float.class,
					Integer.class, Float.class, Integer.class, String.class,
					Float.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
		jtMascotXMLView.setModel(model);
		model.addColumn("Index");
		model.addColumn("Protein");
		model.addColumn("Peptide");
		model.addColumn("Composition");
		model.addColumn("Exp m/z");
		model.addColumn("Exp Mr");
		model.addColumn("Charge");
		model.addColumn("Score");
		model.addColumn("Scan");
		model.addColumn("Scan ID");
		model.addColumn("RT (sec)");

		jtpProperties.setSelectedIndex(3);
		JLabel jlFileNameMascotXMLText = new JLabel(sFileName);

		// Open mascot file and extract identifications
		File file = new File(sFilePath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			// Reading hits
			Node nodeLst = doc.getElementsByTagName("hits").item(0);
			NodeList hitsList = nodeLst.getChildNodes();

			// Initializing proteinId, peptide sequence and other variables
			Integer iIndex = 0;
			String proteinId = "";
			String peptideSeq = "";
			String peptideCompos = "";
			float hitScore = 0.0f;
			float retTime = 0.0f;
			String idRef = "";
			int scanNumber = 0;
			float parIonMz = 0.0f;
			float parIonMr = 0.0f;

			String[] modification = new String[6];
			int numMods = 1;
			boolean isVariableMod = false;
			int dbCharge = -1;
			String mascotPos = "";
			int iProteins = 0;
			int iPeptides = 0;
			String sChainToInsert = "";

			for (int iI = 0; iI < hitsList.getLength(); iI++) {
				// Identifying all protein hits from the search
				NodeList subHitsList = hitsList.item(iI).getChildNodes();
				for (int iJ = 0; iJ < subHitsList.getLength(); iJ++) {
					Node proteinItem = subHitsList.item(iJ);
					if (proteinItem.getNodeType() != Node.ELEMENT_NODE)
						continue;

					if (!proteinItem.getNodeName().equals("protein"))
						continue;

					// Reading the protein
					iProteins++;
					proteinId = proteinItem.getAttributes().item(0)
							.getTextContent().toString();
					NodeList subProteinList = proteinItem.getChildNodes();
					// Protein subnodes
					for (int iK = 0; iK < subProteinList.getLength(); iK++) {
						Node peptideItem = subProteinList.item(iK);
						if (peptideItem.getNodeType() != Node.ELEMENT_NODE)
							continue;
						if (!peptideItem.getNodeName().equals("peptide"))
							continue;

						// Reading peptides
						iPeptides++;
						NodeList peptideDataList = peptideItem.getChildNodes();
						// Peptide subnodes
						// Initializing variables
						for (int iA = 0; iA < 6; iA++) {
							// Cleaning array
							modification[iA] = null;
						}
						int iContMod = 0;
						isVariableMod = false;
						numMods = -1;
						dbCharge = -1;
						sChainToInsert = "";
						for (int iL = 0; iL < peptideDataList.getLength(); iL++) {
							Node peptideElem = peptideDataList.item(iL);
							if (peptideElem.getNodeType() != Node.ELEMENT_NODE)
								continue;

							if (peptideElem.getNodeName().equals("pep_seq")) {
								peptideSeq = peptideElem.getTextContent()
										.toString();
								// Calculate the residue
								// composition
								peptideCompos = FileFormatUtils
										.getResidueComposition(peptideSeq);
							} else if (peptideElem.getNodeName().equals(
									"pep_exp_mz")) {
								parIonMz = Double
										.valueOf(
												peptideElem.getTextContent()
														.toString())
										.floatValue();
							} else if (peptideElem.getNodeName().equals(
									"pep_exp_mr")) {
								parIonMr = Double
										.valueOf(
												peptideElem.getTextContent()
														.toString())
										.floatValue();
							} else if (peptideElem.getNodeName().equals(
									"pep_exp_z")) {
								dbCharge = Integer
										.valueOf(
												peptideElem.getTextContent()
														.toString()).intValue();
							} else if (peptideElem.getNodeName().equals(
									"pep_score")) {
								hitScore = Double
										.valueOf(
												peptideElem.getTextContent()
														.toString())
										.floatValue();
							} else if (hitScore >= 20) {
								// Verify if hits are over
								// minimum threshold
								if (peptideElem.getNodeName().equals(
										"pep_var_mod")) {
									// Modification variables
									String sModVariable = peptideElem
											.getTextContent().toString();

									String[] differentMods = new String[3];
									differentMods = sModVariable.split(";");
									// Check if different
									// modifications are
									// separated by colons
									if (differentMods[0].equals("")) {
										// Verify if has data
										isVariableMod = false;
									} else {
										for (int iDiffMod = 0; iDiffMod < differentMods.length; iDiffMod++) {
											// This code parses the number
											// of modifications (1, 2, n)
											// once we have identified the
											// different modif variables
											if (differentMods[iDiffMod]
													.startsWith(" ")) {
												differentMods[iDiffMod] = differentMods[iDiffMod]
														.substring(
																1,
																differentMods[iDiffMod]
																		.length());
											}
											if (differentMods[iDiffMod]
													.length() > 0) {
												String[] multipleMods = new String[3];

												// New array for multiple
												// modifications in
												// different modifications
												numMods = 1;
												if (differentMods[iDiffMod]
														.matches("^\\d+[\\w|\\D|\\S|\\s|\\W]*")) {
													multipleMods = differentMods[iDiffMod]
															.split(" ");
													numMods = Integer.valueOf(
															multipleMods[0])
															.intValue();
													sChainToInsert = differentMods[iDiffMod]
															.replace(numMods
																	+ " ", "");
													numMods = 1;
												} else {
													sChainToInsert = differentMods[iDiffMod];
													numMods = 1;
												}
												for (int iFound = 0; iFound < numMods; iFound++) {
													modification[iContMod] = sChainToInsert;
													iContMod++;
												}
											}
										}
										isVariableMod = true;
									}
								} else if (peptideElem.getNodeName().equals(
										"pep_var_mod_pos")
										&& isVariableMod) {
									mascotPos = peptideElem.getTextContent()
											.toString();
									mascotPos = mascotPos.replace(".", "");
								} else if (peptideElem.getNodeName().equals(
										"pep_scan_title")) {
									// Only in some cases
									// the rt is specified
									// in the scan title
									if (peptideElem.getTextContent().toString()
											.indexOf("rt:") > 0) {
										// Option 1, reading on scan title
										String myTmpString = peptideElem
												.getTextContent().toString();
										int ind = 0;
										int ind1 = 0;

										// Get retention time
										ind = myTmpString.indexOf("(rt:");
										if (ind > 0) {
											ind1 = myTmpString.indexOf(")");
											retTime = Double.valueOf(
													myTmpString.substring(
															ind + 4, ind1))
													.floatValue();
										} else {
											retTime = 0;
										}

										// Get ID ref
										ind = myTmpString.indexOf("(id:");
										if (ind > 0) {
											ind1 = myTmpString.lastIndexOf(")");
											idRef = myTmpString.substring(
													ind + 4, ind1);
										} else {
											idRef = "";
										}

										// Get scan number
										ind = myTmpString.indexOf("Scan:");
										if (ind > -1) {
											ind1 = myTmpString.indexOf(",");
											scanNumber = Double.valueOf(
													myTmpString.substring(
															ind + 5, ind1))
													.intValue();
										} else {
											scanNumber = 0;
										}
									} else {
										// Read mzML file or MGF (TO DO)
										retTime = 0;
									}
									model.insertRow(
											model.getRowCount(),
											new Object[] { iIndex, proteinId,
													peptideSeq, peptideCompos,
													parIonMz, parIonMr,
													dbCharge, hitScore,
													scanNumber, idRef, retTime });
									iIndex++;
								}
							}
						}
					}
				}
			}
			jtMascotXMLView.setAutoCreateRowSorter(true);
		} catch (Exception e) {
			System.out.println("LoadMascotIdent: Exception while reading "
					+ file + "\n" + e);
			System.exit(1);
		}		
	}
}
