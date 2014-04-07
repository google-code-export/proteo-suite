package org.proteosuite.fileformat;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.bind.JAXBException;

import org.proteosuite.ProteoSuite;
import org.proteosuite.utils.DecoyDetection;
import org.proteosuite.utils.FileFormatUtils;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisData;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftware;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class FileFormatMzIdentML implements Runnable {
	private final JTabbedPane jtpProperties;
	private final JLabel jlFileNameMzIDText;
	private final String filename;
	private final JTable jtMzId;
	private final JComboBox<String> jcbPSM;
	private final JTable jtMzIDProtGroup;
	private final JEditorPane jepMzIDView;
	
	private final MzIdentMLUnmarshaller unmarshaller;
	
	public FileFormatMzIdentML(JTabbedPane jtpProperties, JLabel jlFileNameMzIDText, String filename, 
			JTable jtMzId, 
			JComboBox<String> jcbPSM, JTable jtMzIDProtGroup, JEditorPane jepMzIDView,
			MzIdentMLUnmarshaller unmarshaller) {
		this.jtpProperties = jtpProperties;
		this.jlFileNameMzIDText = jlFileNameMzIDText;
		this.filename = filename;
		this.jtMzId = jtMzId;
		this.jcbPSM = jcbPSM;
		this.jtMzIDProtGroup = jtMzIDProtGroup;
		this.jepMzIDView = jepMzIDView;
		this.unmarshaller = unmarshaller;
	}


	/**
	 * Load MzIdentML viewer
	 * @param iIndex - Index to the aMzIDUnmarshaller list 
	 * @param sFileName - File name
	 * @return void
	 */
	public void run() {
		String sOutput = "";
		jtpProperties.setSelectedIndex(2);
		// ... File Name and Version ...//
		jlFileNameMzIDText.setText(filename);

		System.out.println("Unmarshalling mzid file: " + filename);

		sOutput = "<b>mzIdentML Version:</b> <font color='red'>"
				+ unmarshaller.getMzIdentMLVersion() + "</font><br />";

		// ... File Content ...//
		sOutput = sOutput + "<b>Analysis Software:</b><br />";
		String sSoftwareID = "Default";
		HashMap<String, AnalysisSoftware> analysisSoftwareHashMap = new HashMap<String, AnalysisSoftware>();

		Iterator<AnalysisSoftware> iterAnalysisSoftware = unmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.AnalysisSoftware);
		while (iterAnalysisSoftware.hasNext()) {
			AnalysisSoftware analysisSoftware = iterAnalysisSoftware
					.next();
			analysisSoftwareHashMap.put(analysisSoftware.getId(),
					analysisSoftware);
			sOutput = sOutput + " - "
					+ analysisSoftware.getName().trim() + "<br />";
			sSoftwareID = analysisSoftware.getVersion();
		}
		List<Class<?>> alClasses = new ArrayList<Class<?>>();
		alClasses.add(Integer.class);
		alClasses.add(String.class);
		alClasses.add(String.class);
		alClasses.add(String.class);
		alClasses.add(Double.class);
		alClasses.add(Double.class);
		alClasses.add(Integer.class);
		alClasses.add(Integer.class);
		alClasses.add(String.class);
		alClasses.add(String.class);

		// ... Based on the number of CV params from a given search
		// engine we define the table model ...//
		List<String> alScoreName = new ArrayList<String>();
		List<String> alScoreAccession = new ArrayList<String>();
		AnalysisData analysisData = unmarshaller
				.unmarshal(MzIdentMLElement.AnalysisData);
		List<SpectrumIdentificationList> silList = analysisData
				.getSpectrumIdentificationList();
		init: for (SpectrumIdentificationList sil : silList) {
			List<SpectrumIdentificationResult> sirList = sil
					.getSpectrumIdentificationResult();
			for (SpectrumIdentificationResult sir : sirList) {
				SpectrumIdentificationItem selected = null;
				List<SpectrumIdentificationItem> siiList = sir
						.getSpectrumIdentificationItem();
				for (SpectrumIdentificationItem sii : siiList) {
					selected = sii;
					Peptide peptide = selected.getPeptide();
					if (peptide != null) {
						List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvpList = sii
								.getCvParam();
						if (cvpList != null) {
							for (uk.ac.ebi.jmzidml.model.mzidml.CvParam cvp : cvpList) {
								if (cvp.getValue() != null) {
									alScoreName.add(cvp.getName()
											.toString());
									alScoreAccession.add(cvp
											.getAccession().toString());
									alClasses.add(Double.class);
								}
							}
							break init;
						}
					}
				}
			}
		}
		// ... Determine columns and format type ...//
		final Class<?>[] types = new Class[alClasses.size()];
		for (int iI = 0; iI < alClasses.size(); iI++) {
			types[iI] = alClasses.get(iI);
		}
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};

		model.addColumn("Index");
		model.addColumn("Protein");
		model.addColumn("Peptide");
		model.addColumn("Composition");
		model.addColumn("Exp m/z");
		model.addColumn("Calc m/z");
		model.addColumn("Charge");
		model.addColumn("Rank");
		model.addColumn("Spectrum ID");
		model.addColumn("PassThreshold");

		System.out.println("TotalScores=" + alScoreName.size());
		for (int iSize = 0; iSize < alScoreName.size(); iSize++) {
			// System.out.println("alScoreName:" +
			// alScoreName.get(iSize).toString());
			model.addColumn(alScoreName.get(iSize).toString());
		}
		jtMzId.setModel(model);

		int iCount = 0;
		String sAccesion = "";
		List<String> alScoreValues = new ArrayList<String>();

		// Populating protein and peptide identifications
		for (SpectrumIdentificationList sil : silList) {
			List<SpectrumIdentificationResult> sirList = sil
					.getSpectrumIdentificationResult();
			for (SpectrumIdentificationResult sir : sirList) {
				SpectrumIdentificationItem selected = null;
				List<SpectrumIdentificationItem> siiList = sir
						.getSpectrumIdentificationItem();
				for (SpectrumIdentificationItem sii : siiList) {
					selected = sii;
					iCount++;
					Peptide peptide = selected.getPeptide();
					// System.out.println("Peptide="+peptide.getPeptideSequence());
					if (peptide != null) {
						List<PeptideEvidenceRef> pepRefList = selected
								.getPeptideEvidenceRef();
						sAccesion = "";
						// System.out.println("Peptide evidence Refs="+pepRefList.size());
						if (pepRefList.size() > 0) {
							for (PeptideEvidenceRef per : pepRefList) {
								// System.out.println("Here is the problem");
								// System.out.println("per="+per.getPeptideEvidenceRef());
								PeptideEvidence pe;
								try {
									pe = unmarshaller
											.unmarshal(
													PeptideEvidence.class,
													per.getPeptideEvidenceRef());
									// System.out.println("pe="+pe.getDBSequenceRef());
									DBSequence dbs = unmarshaller
											.unmarshal(
													DBSequence.class,
													pe.getDBSequenceRef());
									// System.out.println("dbs="+dbs.getAccession());
									sAccesion = sAccesion
											+ dbs.getAccession()
													.replace("|", "-")
											+ ";";
								} catch (JAXBException ex) {
									Logger.getLogger(
											ProteoSuite.class
													.getName()).log(
											Level.SEVERE, null, ex);
								}
							}
						} else {
							sAccesion = "N/A";
						}
						alScoreValues.clear();
						List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvpList = sii
								.getCvParam();
						if (cvpList != null) {
							for (uk.ac.ebi.jmzidml.model.mzidml.CvParam cvp : cvpList) {
								if (cvp.getValue() != null) {
									alScoreValues.add(cvp.getValue());
								}
							}
						}

						List<Object> alValues = new ArrayList<Object>();
						alValues.add(iCount);
						alValues.add(sAccesion);
						alValues.add(peptide.getPeptideSequence());
						alValues.add(FileFormatUtils.getResidueComposition(peptide
								.getPeptideSequence()));
						alValues.add(sii.getExperimentalMassToCharge());
						alValues.add(sii.getCalculatedMassToCharge());
						alValues.add(sii.getChargeState());
						alValues.add(sii.getRank());
						alValues.add(sir.getSpectrumID().toString());
						alValues.add(String.valueOf(sii
								.isPassThreshold()));

						for (int iI = 0; iI < alScoreValues.size(); iI++) {
							alValues.add(Double
									.parseDouble(alScoreValues.get(iI)
											.toString()));
						}

						Object[] objects = new Object[alValues.size()];
						for (int iObjects = 0; iObjects < alValues
								.size(); iObjects++) {
							objects[iObjects] = alValues.get(iObjects);
						}

						if (jcbPSM.getSelectedItem().toString()
								.equals("All")) {
							model.insertRow(model.getRowCount(),
									objects);
						} else {
							if (sii.getRank() <= (jcbPSM
									.getSelectedIndex() + 1)) {
								model.insertRow(model.getRowCount(),
										objects);
							}
						}
					}
				}
			}
		}
		jtMzId.getTableHeader().setDefaultRenderer(
				new TableCellRenderer() {
					final TableCellRenderer defaultRenderer = jtMzId
							.getTableHeader().getDefaultRenderer();

					public Component getTableCellRendererComponent(
							JTable table, Object value,
							boolean isSelected, boolean hasFocus,
							int row, int column) {
						JComponent component = (JComponent) defaultRenderer
								.getTableCellRendererComponent(table,
										value, isSelected, hasFocus,
										row, column);
						component.setToolTipText(""
								+ jtMzId.getColumnName(column));
						return component;
					}
				});
		jtMzId.setAutoCreateRowSorter(true);

		// ... Filling protein groups ...//
		while (((DefaultTableModel) jtMzIDProtGroup.getModel())
				.getRowCount() > 0) {
			((DefaultTableModel) jtMzIDProtGroup.getModel())
					.removeRow(0);
		}
		Iterator<ProteinAmbiguityGroup> iterProteinAmbiguityGroup = unmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.ProteinAmbiguityGroup);
		List<ProteinDetectionHypothesis> proteinDetectionHypothesisList;
		String protein_accessions = "";
		while (iterProteinAmbiguityGroup.hasNext()) {
			ProteinAmbiguityGroup proteinAmbiguityGroup = iterProteinAmbiguityGroup
					.next();

			protein_accessions = "";
			proteinDetectionHypothesisList = proteinAmbiguityGroup
					.getProteinDetectionHypothesis();
			boolean representativeProtein = false;
			String representativeProteinAccession = "";
			boolean isDecoy = false;
			String score = " ";
			String number_peptide = " ";
			boolean isPassThreshold = false;
			if (proteinDetectionHypothesisList.size() > 0) {
				for (int j = 0; j < proteinDetectionHypothesisList
						.size(); j++) {
					ProteinDetectionHypothesis proteinDetectionHypothesis = proteinDetectionHypothesisList
							.get(j);
					try {
						DBSequence dBSequence = unmarshaller.unmarshal(
								DBSequence.class,
								proteinDetectionHypothesis
										.getDBSequenceRef());
						if (dBSequence.getAccession() != null) {
							protein_accessions = protein_accessions
									+ dBSequence.getAccession() + ";";
						}
						List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvParamList = proteinDetectionHypothesis
								.getCvParam();
						for (int i = 0; i < cvParamList.size(); i++) {
							uk.ac.ebi.jmzidml.model.mzidml.CvParam cvParam = cvParamList
									.get(i);
							if (cvParam.getName().equals(
									"anchor protein")) {
								representativeProtein = true;
								representativeProteinAccession = dBSequence
										.getAccession();
								isDecoy = DecoyDetection.checkIfProteinDetectionHypothesisIsDecoy(
										unmarshaller,
										proteinDetectionHypothesis);
								if (proteinDetectionHypothesis
										.getPeptideHypothesis() != null) {
									number_peptide = String
											.valueOf(proteinDetectionHypothesis
													.getPeptideHypothesis()
													.size());
								}

								isPassThreshold = proteinDetectionHypothesis
										.isPassThreshold();
							}
							if (cvParam.getName().contains("score")) {
								score = cvParam.getValue();
							}
						}
					} catch (JAXBException ex) {
						Logger.getLogger(
								ProteoSuite.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
			}
			protein_accessions = protein_accessions.substring(0,
					protein_accessions.length() - 1);
			if (representativeProtein) {
				((DefaultTableModel) jtMzIDProtGroup.getModel())
						.addRow(new Object[] {
								proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions,
								representativeProteinAccession,
								Double.valueOf(score).doubleValue(),
								" ", Integer.valueOf(number_peptide),
								String.valueOf(isDecoy),
								String.valueOf(isPassThreshold) });
			} else {
				((DefaultTableModel) jtMzIDProtGroup.getModel())
						.addRow(new Object[] {
								proteinAmbiguityGroup.getId(),
								proteinAmbiguityGroup.getName(),
								protein_accessions, " ", " ", " ", " ",
								" ", " " });
			}
		}

		jepMzIDView.setText(sOutput);
	}
}
