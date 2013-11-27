package org.proteosuite.fileformat;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.utils.PluginManager;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.ac.liv.jmzqml.MzQuantMLElement;
import uk.ac.liv.jmzqml.model.mzqml.AbstractParam;
import uk.ac.liv.jmzqml.model.mzqml.AnalysisSummary;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.Cv;
import uk.ac.liv.jmzqml.model.mzqml.CvList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.FeatureList;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFiles;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.Label;
import uk.ac.liv.jmzqml.model.mzqml.ModParam;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensus;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensusList;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.Protein;
import uk.ac.liv.jmzqml.model.mzqml.ProteinList;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLMarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class FileFormatMzQuantML extends Thread implements Runnable {
	private final JTabbedPane jtpProperties;
	private final int iIndexRef;
	private final SystemUtils sysutils;
	private final ProgressBarDialog progressBarDialog;
	private final JTable jtProteinQuant;
	private final JTable jtPeptideQuant;
	private final JTable jtFeatureQuant;
	private final String sFileRef;
	private final List<MzQuantMLUnmarshaller> aMzQUnmarshaller;
	private final JLabel jlFileNameMzQText;
	private final JEditorPane jepMZQView;
	private final JTextArea jtaLog;
	private final JTabbedPane jtpMzQuantMLDetail;
	
	/**
	 * 
	 * @param jtpProperties
	 * @param iIndexRef
	 * @param sysutils
	 * @param progressBarDialog
	 * @param jtProteinQuant
	 * @param jtPeptideQuant
	 * @param jtFeatureQuant
	 * @param sFileRef
	 * @param aMzQUnmarshaller
	 * @param jlFileNameMzQText
	 * @param jepMZQView
	 * @param jtaLog
	 * @param jtpMzQuantMLDetail
	 */
	public FileFormatMzQuantML(JTabbedPane jtpProperties, int iIndexRef, SystemUtils sysutils, ProgressBarDialog progressBarDialog, 
			JTable jtProteinQuant, JTable jtPeptideQuant, JTable jtFeatureQuant, String sFileRef, List<MzQuantMLUnmarshaller> aMzQUnmarshaller, 
			JLabel jlFileNameMzQText, JEditorPane jepMZQView, JTextArea jtaLog, JTabbedPane jtpMzQuantMLDetail) {
		super("MzQuantML Loader");
		
		this.jtpProperties = jtpProperties;
		this.iIndexRef = iIndexRef;
		this.sysutils = sysutils;
		this.progressBarDialog = progressBarDialog;
		this.jtProteinQuant = jtProteinQuant;
		this.jtPeptideQuant = jtPeptideQuant;
		this.jtFeatureQuant = jtFeatureQuant;
		this.sFileRef = sFileRef;
		this.aMzQUnmarshaller = aMzQUnmarshaller;
		this.jlFileNameMzQText = jlFileNameMzQText;
		this.jepMZQView = jepMZQView;
		this.jtaLog = jtaLog;
		this.jtpMzQuantMLDetail = jtpMzQuantMLDetail;
	}

	@Override
	public void run() {
		jtpProperties.setSelectedIndex(4);
		DefaultTableModel model = new DefaultTableModel();
		DefaultTableModel model2 = new DefaultTableModel();
		DefaultTableModel model3 = new DefaultTableModel();
		jtProteinQuant.setModel(model);
		jtPeptideQuant.setModel(model2);
		jtFeatureQuant.setModel(model3);
		model.addColumn("Protein");
		model2.addColumn("Peptide");
		model3.addColumn("FeatureList");

		// ARJ Update 21/11/2013 - we need a full file path,
		// this is only delivering a local file name at the
		// moment.
		// When importing mzQuantML files, full path is needed
		// MzQuantMLUnmarshaller unmarshaller = new
		// MzQuantMLUnmarshaller(jtQuantFiles.getValueAt(0,
		// iIndexRef).toString());
		MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(
				new File(sFileRef));
		MzQuantML mzq = unmarshaller
				.unmarshal(uk.ac.liv.jmzqml.model.mzqml.MzQuantML.class);

		System.out.println(sysutils.getTime()
				+ " - (Loading mzQuantMLView)");
		System.out.println(sysutils.getTime()
				+ " - MZQ elements=" + aMzQUnmarshaller.size());
		System.out.println(sysutils.getTime()
				+ " - Unmarshalling element " + iIndexRef
				+ " from the array");

		// ... File Name and Version ...//
		jlFileNameMzQText.setText(sFileRef);
		System.out.println(sysutils.getTime() + " - Loading "
				+ sFileRef);
		String sOutput = "";
		String sMessage = "";
		sOutput = "<b>mzQuantML Version:</b> <font color='red'>"
				+ mzq.getVersion() + "</font><br />";
		System.out.println(sysutils.getTime()
				+ " - mzQuantML Version: " + mzq.getVersion());

		sOutput = sOutput + "<b>Software List:</b><br />";

		SoftwareList softwareList = unmarshaller
				.unmarshal(MzQuantMLElement.SoftwareList);
		// SoftwareList softwareList = mzq.getSoftwareList();
		System.out.println(sysutils.getTime()
				+ " - Software List Size: "
				+ softwareList.getSoftware().size());
		if (softwareList != null) {
			for (Software software : softwareList.getSoftware()) {
				List<CvParam> cvlSoftware = software
						.getCvParam();
				for (CvParam cvp : cvlSoftware) {
					sOutput = sOutput + " - "
							+ cvp.getName().trim() + "<br />";
				}
			}
		}

		sOutput = sOutput + "<b>Data Processing:</b><br />";
		DataProcessingList dpList = unmarshaller
				.unmarshal(MzQuantMLElement.DataProcessingList);
		// DataProcessingList dpList =
		// mzq.getDataProcessingList();
		if (dpList != null) {
			for (DataProcessing dp : dpList.getDataProcessing()) {
				List<ProcessingMethod> dataProcessingMethodList = dp
						.getProcessingMethod();
				for (ProcessingMethod procMeth : dataProcessingMethodList) {
					List<UserParam> usrpProcMeth = procMeth
							.getUserParam();
					for (UserParam usrp : usrpProcMeth) {
						sOutput = sOutput + " - ["
								+ usrp.getName().trim() + " = "
								+ usrp.getValue().trim()
								+ "]<br />";
					}
				}
			}
		}

		jepMZQView.setText(sOutput);
		sOutput = "";

		// ============================//
		// ... Protein Quantitation ...//
		// ============================//
		// ... Based on the the assay list and study variables
		// we will include the different columns ...//
		// AssayList assayList =
		// unmarshaller.unmarshal(MzQuantMLElement.AssayList);
		AssayList assayList = mzq.getAssayList();
		List<Assay> listAssay = assayList.getAssay();
		int iAssays = 0;
		for (Assay assay : listAssay) {
			model.addColumn(assay.getName());
			iAssays++;
		}
		sMessage = sysutils.getTime() + " - Assays: " + iAssays;
		System.out.println(sMessage);
		sOutput = sOutput + sMessage + "\n";

		StudyVariableList studyList = unmarshaller
				.unmarshal(MzQuantMLElement.StudyVariableList);
		// StudyVariableList studyList =
		// mzq.getStudyVariableList();
		List<StudyVariable> listStudy = studyList
				.getStudyVariable();
		int iStudyVars = 0;
		for (StudyVariable study : listStudy) {
			model.addColumn(study.getName());
			iStudyVars++;
		}
		sMessage = sysutils.getTime() + " - Study Variables: "
				+ iStudyVars;
		System.out.println(sMessage);
		sOutput = sOutput + sMessage + "\n";

		// ... Fill rows ...//
		Map<String, List<String>> hmProtein = new HashMap<String, List<String>>();

		sMessage = sysutils.getTime()
				+ " - Reading Protein Quantitation ...";
		System.out.println(sMessage);
		sOutput = sOutput + sMessage + "\n";

		// ... Check if mzq file contains protein list ...//
		ProteinList proteinList = unmarshaller
				.unmarshal(MzQuantMLElement.ProteinList);
		// ProteinList proteinList = mzq.getProteinList();

		Map<String, Protein> mapIDToProt = new HashMap<String, Protein>();

		for (Protein protein : proteinList.getProtein()) {
			mapIDToProt.put(protein.getId(), protein);
		}

		if (proteinList != null) {
			// ... Getting DataMatrix from AssayQuantLayer ...//
			if (proteinList.getAssayQuantLayer().size() > 0) {
				List<Row> dataMatrix = proteinList
						.getAssayQuantLayer().get(0)
						.getDataMatrix().getRow();
				for (Row row : dataMatrix) {
					String protID = row.getObjectRef();
					List<String> values = row.getValue();
					List<String> al = values;
					hmProtein.put(mapIDToProt.get(protID)
							.getAccession(), al);
				}
			}
			// ... Getting DataMatrix from
			// StudyVariableQuantLayer ...//
			if (proteinList.getStudyVariableQuantLayer().size() > 0) {
				List<Row> dataMatrix2 = proteinList
						.getStudyVariableQuantLayer().get(0)
						.getDataMatrix().getRow();
				for (Row row : dataMatrix2) {
					// Protein prot = (Protein)
					// row.getObjectRef();
					String protID = row.getObjectRef();
					List<String> values = row.getValue();
					List<String> al = values;
					List<String> al2 = hmProtein.get(mapIDToProt
							.get(protID).getAccession());
					for (String obj : al) {
						al2.add(obj);
					}
					hmProtein.put(mapIDToProt.get(protID)
							.getAccession(), al2);
				}
			}
			for (Entry<String, List<String>> entry : hmProtein
					.entrySet()) {
				String[] aObj = new String[iAssays + iStudyVars
						+ 1];
				aObj[0] = entry.getKey();

				List<String> saParams = entry.getValue();
				Iterator<String> itr = saParams.iterator();
				int iI = 1;
				while (itr.hasNext()) {
					if (iI >= iAssays + iStudyVars + 1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Invalid file. The mzq file contains duplications in the DataMatrix on "
												+ "the AssayQuantLayer or StudyVariableQuantLayer",
										"Information",
										JOptionPane.INFORMATION_MESSAGE);
						break;
					} else {
						aObj[iI] = itr.next().toString();
						iI++;
					}
				}
				model.insertRow(model.getRowCount(), aObj);
			}
			// ... Tooltip for headers ...//
			jtProteinQuant.getTableHeader().setDefaultRenderer(
					new TableCellRenderer() {
						final TableCellRenderer defaultRenderer = jtProteinQuant
								.getTableHeader()
								.getDefaultRenderer();

						public Component getTableCellRendererComponent(
								JTable table, Object value,
								boolean isSelected,
								boolean hasFocus, int row,
								int column) {
							JComponent component = (JComponent) defaultRenderer
									.getTableCellRendererComponent(
											table, value,
											isSelected,
											hasFocus, row,
											column);
							component.setToolTipText(""
									+ jtProteinQuant
											.getColumnName(column));
							return component;
						}
					});
			jtProteinQuant.setAutoCreateRowSorter(true);

			// ============================//
			// ... Peptide Quantitation ...//
			// ============================//
			sMessage = sysutils.getTime()
					+ " - Reading Peptide Quantitation ... ";
			System.out.println(sMessage);
			sOutput = sOutput + sMessage + "\n";
			// ... Based on the the assay list and study
			// variables we will include the different columns
			// ...//
			for (Assay assay : listAssay) {
				model2.addColumn(assay.getName());
			}

			// ... Fill rows ...//

			// ... Getting DataMatrix from AssayQuantLayer ...//
			PeptideConsensusList pepConsList = unmarshaller
					.unmarshal(MzQuantMLElement.PeptideConsensusList);
			// PeptideConsensusList pepConsList =
			// mzq.getPeptideConsensusList().get(0); //Set to
			// get first list only TODO - needs a fix for
			// multiple lists

			Map<String, PeptideConsensus> mapIDToPep = new HashMap<String, PeptideConsensus>();

			for (PeptideConsensus pep : pepConsList
					.getPeptideConsensus()) {
				mapIDToPep.put(pep.getId(), pep);
			}

			if (pepConsList.getAssayQuantLayer().size() > 0) {
				List<Row> dataMatrix3 = pepConsList
						.getAssayQuantLayer().get(0)
						.getDataMatrix().getRow();
				for (Row row : dataMatrix3) {
					String[] aObj = new String[iAssays + 1];
					String pepID = row.getObjectRef();

					aObj[0] = pepID;
					List<String> values = row.getValue();
					List<String> al = values;
					Iterator<String> itr = al.iterator();
					int iI = 1;
					while (itr.hasNext()) {
						aObj[iI] = itr.next().toString();
						iI++;
					}
					model2.insertRow(model2.getRowCount(), aObj);
				}
			}
			// ... Tooltip for headers ...//
			jtPeptideQuant.getTableHeader().setDefaultRenderer(
					new TableCellRenderer() {
						final TableCellRenderer defaultRenderer = jtPeptideQuant
								.getTableHeader()
								.getDefaultRenderer();

						public Component getTableCellRendererComponent(
								JTable table, Object value,
								boolean isSelected,
								boolean hasFocus, int row,
								int column) {
							JComponent component = (JComponent) defaultRenderer
									.getTableCellRendererComponent(
											table, value,
											isSelected,
											hasFocus, row,
											column);
							component.setToolTipText(""
									+ jtPeptideQuant
											.getColumnName(column));
							return component;
						}
					});
			jtPeptideQuant.setAutoCreateRowSorter(true);

			// ============================//
			// ... Feature Quantitation ...//
			// ============================//
			sMessage = sysutils.getTime()
					+ " - Reading Feature Quantitation ... ";
			System.out.println(sMessage);
			sOutput = sOutput + sMessage + "\n";
			// ... Based on the the assay list and study
			// variables we will include the different columns
			// ...//

			/*
			 * ARJ 22/11/2013 - removed this code, since this
			 * isn't correct parsing of mzq files, even itraq,
			 * for simplicity just display mz, rt, charge for
			 * (Assay assay : listAssay) {
			 * model3.addColumn(assay.getName()); }
			 */

			model3.addColumn("m/z");
			model3.addColumn("rt (m)");
			model3.addColumn("charge");

			// ... Fill rows ...//

			// ... Getting DataMatrix from AssayQuantLayer ...//
			// FeatureList featureList =
			// unmarshaller.unmarshal(MzQuantMLElement.FeatureList);
			for (FeatureList featureList : mzq.getFeatureList()) {
				// String rawFile =
				// featureList.getRawFilesGroupRef();
				String featureListID = featureList.getId();

				for (Feature feature : featureList.getFeature()) {
					String mz = "" + feature.getMz();
					String charge = feature.getCharge(); // Needs
															// to
															// be
															// updated
															// in
															// jmzq
															// to
															// correct
															// type
					String rt = feature.getRt();

					Object[] aObj = { featureListID, mz, rt,
							charge };
					model3.insertRow(model3.getRowCount(), aObj);
				}
				/*
				 * Removed by ARJ - more complicated model is
				 * needed to load different types of mzq file if
				 * (
				 * featureList.getMS2AssayQuantLayer().size()>0)
				 * { List<Row> dataMatrix4 =
				 * featureList.getMS2AssayQuantLayer
				 * ().get(0).getDataMatrix().getRow(); for(Row
				 * row:dataMatrix4){ Object[] aObj = new
				 * Object[iAssays+2]; Feature feature =
				 * (Feature) row.getObjectRef();
				 * 
				 * aObj[0] = feature.getId(); aObj[1] =
				 * featureListID; List<String> values =
				 * row.getValue(); ArrayList al = (ArrayList)
				 * values; Iterator<String> itr = al.iterator();
				 * int iI = 1; while (itr.hasNext()) { aObj[iI]
				 * = itr.next().toString(); iI++; }
				 * model3.insertRow(model3.getRowCount(), aObj);
				 * } } else{
				 * 
				 * }
				 */
			}
			jtaLog.setText(sOutput);

			// ... Tooltip for headers ...//
			jtFeatureQuant.getTableHeader().setDefaultRenderer(
					new TableCellRenderer() {
						final TableCellRenderer defaultRenderer = jtFeatureQuant
								.getTableHeader()
								.getDefaultRenderer();

						public Component getTableCellRendererComponent(
								JTable table, Object value,
								boolean isSelected,
								boolean hasFocus, int row,
								int column) {
							JComponent component = (JComponent) defaultRenderer
									.getTableCellRendererComponent(
											table, value,
											isSelected,
											hasFocus, row,
											column);
							component.setToolTipText(""
									+ jtFeatureQuant
											.getColumnName(column));
							return component;
						}
					});
			jtFeatureQuant.setAutoCreateRowSorter(true);
		}
		jtpProperties.setSelectedIndex(4);
		jtpMzQuantMLDetail.setSelectedIndex(1);

		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	}
	


	/**
	 * Write mzQuantML file
	 * 
	 * @param sExperiment
	 *            - Type of experiment
	 * @param sFile
	 *            - File name
	 * @param jtIdentFiles
	 * @return true/false
	 */
	public static boolean writeMzQuantML(String sExperiment, String sFile,
			JTable jtRawFiles, JComboBox<String> jcbOutputFormat,
			JTable jtIdentFiles) {
		boolean ret = true;

		// ... Create object ...//
		MzQuantML qml = new MzQuantML();

		// ... Set version ...//
		String Version = "1.0.0";
		qml.setVersion(Version);
		Calendar rightNow = Calendar.getInstance();
		qml.setCreationDate(rightNow);
		qml.setId(sFile);

		// ----------------------//
		// ... CREATE CV LIST ...//
		// ----------------------//
		CvList cvs = new CvList();
		List<Cv> cvList = cvs.getCv();
		Cv cvPSI_MS = new Cv();
		cvPSI_MS.setId("PSI-MS");
		cvPSI_MS.setUri("http://psidev.cvs.sourceforge.net/viewvc/*checkout*/psidev/psi/psi-ms/mzML/controlledVocabulary/psi-ms.obo");
		cvPSI_MS.setFullName("Proteomics Standards Initiative Mass Spectrometry Vocabulary");
		cvPSI_MS.setVersion(ProteoSuiteView.PSI_MS_VERSION);

		Cv cvUO = new Cv();
		cvUO.setId("UO");
		cvUO.setUri("http://obo.cvs.sourceforge.net/*checkout*/obo/obo/ontology/phenotype/unit.obo");
		cvUO.setFullName("Unit Ontology");

		Cv cvPSI_MOD = new Cv();
		cvPSI_MOD.setId("PSI-MOD");
		cvPSI_MOD
				.setUri("http://psidev.cvs.sourceforge.net/psidev/psi/mod/data/PSI-MOD.obo");
		cvPSI_MOD
				.setFullName("Proteomics Standards Initiative Protein Modifications");
		cvPSI_MOD.setVersion(ProteoSuiteView.PSI_MOD_VERSION);

		Cv cvUNI_MOD = new Cv();
		cvUNI_MOD.setId("UNIMOD");
		cvUNI_MOD.setUri("http://www.unimod.org/obo/unimod.obo");
		cvUNI_MOD.setFullName("UNIMOD");

		cvList.add(cvPSI_MS);
		cvList.add(cvUO);
		cvList.add(cvPSI_MOD);
		cvList.add(cvUNI_MOD);
		qml.setCvList(cvs);

		// -------------------------------//
		// ... CREATE ANALYSIS SUMMARY ...//
		// -------------------------------//

		if (sExperiment.contains("iTRAQ")) {

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			// ParamList pl = new ParamList();
			// List<AbstractParam> cvParamList = pl.getParamGroup();
			/*
			 * CvParam cvp = new CvParam(); //cvp.setAccession("MS:1002010");
			 * cvp.setAccession("MS:1001837"); cvp.setCv(cvPSI_MS);
			 * //cvp.setName("TMT quantitation analysis");
			 * cvp.setName("iTRAQ quantitation analysis"); cvParamList.add(cvp);
			 */
			// id: MS:1001837
			// name: iTRAQ quantitation analysis

			CvParam cvp6 = new CvParam();
			cvp6.setAccession("MS:1002023");
			cvp6.setCv(cvPSI_MS);
			cvp6.setName("MS2 tag-based analysis");
			cvParamList.add(cvp6);

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002024");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("MS2 tag-based analysis feature level quantitation");
			cvParamList.add(cvp2);

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002025");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("true");
			cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");
			cvParamList.add(cvp3);

			CvParam cvp4 = new CvParam();
			cvp4.setAccession("MS:1002026");
			cvp4.setCv(cvPSI_MS);
			cvp4.setValue("true");
			cvp4.setName("MS2 tag-based analysis protein level quantitation");
			cvParamList.add(cvp4);

			CvParam cvp5 = new CvParam();
			cvp5.setAccession("MS:1002027");
			cvp5.setCv(cvPSI_MS);
			cvp5.setValue("false");
			cvp5.setName("MS2 tag-based analysis protein group level quantitation");
			cvParamList.add(cvp5);

			qml.setAnalysisSummary(as);
		}
		if (sExperiment.contains("TMT")) {

			CvParam cvp = new CvParam();
			cvp.setAccession("MS:1001837");
			cvp.setCv(cvPSI_MS);
			cvp.setName("TMT quantitation analysis");

			CvParam cvp6 = new CvParam();
			cvp6.setAccession("MS:1002023");
			cvp6.setCv(cvPSI_MS);
			cvp6.setName("MS2 tag-based analysis");

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002024");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("MS2 tag-based analysis feature level quantitation");

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002025");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("true");
			cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

			CvParam cvp4 = new CvParam();
			cvp4.setAccession("MS:1002026");
			cvp4.setCv(cvPSI_MS);
			cvp4.setValue("true");
			cvp4.setName("MS2 tag-based analysis protein level quantitation");

			CvParam cvp5 = new CvParam();
			cvp5.setAccession("MS:1002027");
			cvp5.setCv(cvPSI_MS);
			cvp5.setValue("false");
			cvp5.setName("MS2 tag-based analysis protein group level quantitation");

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			cvParamList.add(cvp6);
			cvParamList.add(cvp2);
			cvParamList.add(cvp3);
			cvParamList.add(cvp4);
			cvParamList.add(cvp5);

			qml.setAnalysisSummary(as);
		}
		if (sExperiment.contains("emPAI")) {

			CvParam cvp = new CvParam();
			cvp.setAccession("MS:1001836");
			cvp.setCv(cvPSI_MS);
			cvp.setName("spectral counting quantitation analysis");

			CvParam cvp1 = new CvParam();
			cvp1.setAccession("MS:1002015");
			cvp1.setCv(cvPSI_MS);
			cvp1.setValue("false");
			cvp1.setName("spectral count peptide level quantitation");

			CvParam cvp2 = new CvParam();
			cvp2.setAccession("MS:1002016");
			cvp2.setCv(cvPSI_MS);
			cvp2.setValue("true");
			cvp2.setName("spectral count protein level quantitation");

			CvParam cvp3 = new CvParam();
			cvp3.setAccession("MS:1002017");
			cvp3.setCv(cvPSI_MS);
			cvp3.setValue("false");
			cvp3.setName("spectral count proteingroup level quantitation");

			AnalysisSummary as = new AnalysisSummary();
			List<CvParam> cvParamList = as.getCvParam();

			cvParamList.add(cvp);
			cvParamList.add(cvp1);
			cvParamList.add(cvp2);
			cvParamList.add(cvp3);
			qml.setAnalysisSummary(as);
		}

		// --------------------------//
		// ... CREATE INPUT FILES ...//
		// --------------------------//
		InputFiles inputFiles = new InputFiles();

		// --------------------------//
		// ... RAW FILES ...//
		// --------------------------//

		// ... Get raw files ...//
		List<RawFilesGroup> rawFilesGroupList = inputFiles.getRawFilesGroup();
		Map<String, List<String[]>> hmRawFiles = new HashMap<String, List<String[]>>();

		boolean bExists = false;
		// ... Select all raw files from grid ...//
		for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
			// ... Check if group has been added previously ...//
			String sKey = jtRawFiles.getValueAt(iI, 0).toString();
			bExists = hmRawFiles.containsKey(sKey);
			if (bExists == false) {
				List<String[]> al = new ArrayList<String[]>();
				String[] sTemp = new String[2];
				sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
				sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
				al.add(sTemp);
				hmRawFiles.put(sKey, al);
			} else {
				List<String[]> al2 = hmRawFiles.get(jtRawFiles
						.getValueAt(iI, 4).toString());
				String[] sTemp = new String[2];
				sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
				sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
				al2.add(sTemp);
				hmRawFiles.put(sKey, al2);
			}
		}
		// ... Set raw files groups ...//
		int iCounter = 0;
		for (Entry<String, List<String[]>> entry : hmRawFiles.entrySet()) {

			RawFilesGroup rawFilesGroup = new RawFilesGroup();
			List<RawFile> rawFilesList = rawFilesGroup.getRawFile();

			// String rgId = "raw_"+Integer.toString(iCounter+1);
			String rgId = jtRawFiles.getValueAt(iCounter, 0).toString();
			List<String[]> saGroups = entry.getValue();
			for (String[] slFiles : saGroups) {
				// ... Identify the corresponding raw files
				String rawFname = slFiles[0];
				String rawId = "r" + Integer.toString(iCounter + 1);
				// ... Raw files ...//
				RawFile rawFile = new RawFile();
				rawFile.setName(rawFname);
				rawFile.setId(rawId);
				rawFile.setLocation(slFiles[1]);
				rawFilesList.add(rawFile);
				iCounter++;
			}
			rawFilesGroup.setId(rgId);
			rawFilesGroupList.add(rawFilesGroup);
		}

		// -----------------------------//
		// ... IDENTIFICATION FILES ...//
		// -----------------------------//

		// ... Define those structures that will be further used (e.g. in
		// AssayList) ...//
		Map<String, String> idFileNameIdMap = new HashMap<String, String>();

		// ... Identification file containers ...//
		IdentificationFiles idFiles = inputFiles.getIdentificationFiles();
		if (idFiles == null) {
			idFiles = new IdentificationFiles();
		}
		List<IdentificationFile> idFilesList = idFiles.getIdentificationFile();

		// ... Select all raw files from grid ...//
		for (int iI = 0; iI < jtIdentFiles.getRowCount(); iI++) {
			// ... Identification files ...//
			String idFname = jtIdentFiles.getValueAt(iI, 0).toString();
			String idId = "id_file" + Integer.toString(iI + 1);

			IdentificationFile idFile = new IdentificationFile();
			idFile.setName(idFname);
			idFile.setId(idId);
			idFile.setLocation(jtIdentFiles.getValueAt(iI, 1).toString()
					.replace("\\", "/"));
			idFilesList.add(idFile);
			idFileNameIdMap.put(idFname, idId); // ... Saving hashmap for
												// AssayList ...//
		}
		inputFiles.setIdentificationFiles(idFiles);
		qml.setInputFiles(inputFiles);

		// ---------------------------//
		// ... Create SoftwareList ...//
		// ---------------------------//
		SoftwareList softwareList = new SoftwareList();
		Software software = new Software();
		softwareList.getSoftware().add(software);
		software.setId("x-Tracker");
		software.setVersion("2.0-ALPHA");
		CvParam cvpSW = new CvParam();
		cvpSW.setAccession("MS:1002123");
		cvpSW.setCv(cvPSI_MS);
		cvpSW.setName("x-Tracker");
		software.getCvParam().add(cvpSW);

		Software software2 = new Software();
		softwareList.getSoftware().add(software2);
		software2.setId("ProteoSuite");
		software2.setVersion(ProteoSuiteView.sPS_Version);
		CvParam cvpSW2 = new CvParam();
		cvpSW2.setAccession("MS:1002124");
		cvpSW2.setCv(cvPSI_MS);
		cvpSW2.setName("ProteoSuite");
		software2.getCvParam().add(cvpSW2);
		qml.setSoftwareList(softwareList);

		// ---------------------------------//
		// ... Create DataProcessingList ...//
		// ---------------------------------//
		DataProcessingList dataProcessingList = new DataProcessingList();
		DataProcessing dataProcessing = new DataProcessing();
		dataProcessing.setId("DP1");
		dataProcessing.setSoftware(software);
		dataProcessing.setOrder(BigInteger.ONE);

		// ... Based on the technique, select the plugins that are available to
		// perform the quantitation ...//
		if (sExperiment.contains("iTRAQ")) {
			String[] sPipeline = PluginManager.getPlugins(sExperiment, jtRawFiles, jtIdentFiles,
					jcbOutputFormat.getSelectedItem().toString());

			// ... Processing methods ...//
			ProcessingMethod processingMethod = new ProcessingMethod();
			processingMethod.setOrder(BigInteger.ONE);
			List<AbstractParam> pmList = processingMethod.getParamGroup();
			UserParam up1 = new UserParam();
			up1.setName("Plugin type");
			up1.setValue("load identification");
			pmList.add(up1);
			UserParam up2 = new UserParam();
			up2.setName("Plugin name");
			up2.setValue(sPipeline[0]);
			pmList.add(up2);
			UserParam up3 = new UserParam();
			up3.setName("Plugin configuration file");
			up3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[0] + ".xtp");
			pmList.add(up3);
			dataProcessing.getProcessingMethod().add(processingMethod);

			ProcessingMethod processingMethod2 = new ProcessingMethod();
			processingMethod2.setOrder(BigInteger.valueOf(2));
			List<AbstractParam> pmList2 = processingMethod2.getParamGroup();
			UserParam up2_1 = new UserParam();
			up2_1.setName("Plugin type");
			up2_1.setValue("load raw spectra");
			pmList2.add(up2_1);
			UserParam up2_2 = new UserParam();
			up2_2.setName("Plugin name");
			up2_2.setValue(sPipeline[1]);
			pmList2.add(up2_2);
			UserParam up2_3 = new UserParam();
			up2_3.setName("Plugin configuration file");
			up2_3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[1] + ".xtp");
			pmList2.add(up2_3);
			dataProcessing.getProcessingMethod().add(processingMethod2);

			ProcessingMethod processingMethod3 = new ProcessingMethod();
			processingMethod3.setOrder(BigInteger.valueOf(3));
			List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
			UserParam up3_1 = new UserParam();
			up3_1.setName("Plugin type");
			up3_1.setValue("feature detection and quantitation");
			pmList3.add(up3_1);
			UserParam up3_2 = new UserParam();
			up3_2.setName("Plugin name");
			up3_2.setValue(sPipeline[2]);
			pmList3.add(up3_2);
			UserParam up3_3 = new UserParam();
			up3_3.setName("Plugin configuration file");
			up3_3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[2] + ".xtp");
			pmList3.add(up3_3);
			UserParam up3_4 = new UserParam();
			up3_4.setName("Feature to peptide inference method");
			up3_4.setValue("mean");
			pmList3.add(up3_4);
			UserParam up3_5 = new UserParam();
			up3_5.setName("Peptide to protein inference method");
			up3_5.setValue("weightedAverage");
			pmList3.add(up3_5);
			UserParam up3_6 = new UserParam();
			up3_6.setName("Assay to Study Variables inference method");
			up3_6.setValue("sum");
			pmList3.add(up3_6);
			UserParam up3_7 = new UserParam();
			up3_7.setName("Protein ratio calculation infer from peptide ratio");
			up3_7.setValue("true");
			pmList3.add(up3_7);
			dataProcessing.getProcessingMethod().add(processingMethod3);

			ProcessingMethod processingMethod4 = new ProcessingMethod();
			processingMethod4.setOrder(BigInteger.valueOf(4));
			List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
			UserParam up4_1 = new UserParam();
			up4_1.setName("Plugin type");
			up4_1.setValue("Output");
			pmList4.add(up4_1);
			UserParam up4_2 = new UserParam();
			up4_2.setName("Plugin name");
			up4_2.setValue(sPipeline[3]);
			pmList4.add(up4_2);
			UserParam up4_3 = new UserParam();
			up4_3.setName("Plugin configuration file");
			up4_3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[3] + ".xtp");
			pmList4.add(up4_3);
			dataProcessing.getProcessingMethod().add(processingMethod4);
		}
		if (sExperiment.contains("emPAI")) {
			String[] sPipeline = PluginManager.getPlugins(sExperiment, jtRawFiles, jtIdentFiles,
					jcbOutputFormat.getSelectedItem().toString());

			// ... Processing methods ...//
			ProcessingMethod processingMethod = new ProcessingMethod();
			processingMethod.setOrder(BigInteger.ONE);
			List<AbstractParam> pmList = processingMethod.getParamGroup();
			UserParam up1 = new UserParam();
			up1.setName("Plugin type");
			up1.setValue("load identification");
			pmList.add(up1);
			UserParam up2 = new UserParam();
			up2.setName("Plugin name");
			up2.setValue(sPipeline[0]);
			pmList.add(up2);
			UserParam up3 = new UserParam();
			up3.setName("Plugin configuration file");
			up3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[0] + ".xtp");
			pmList.add(up3);
			dataProcessing.getProcessingMethod().add(processingMethod);

			ProcessingMethod processingMethod3 = new ProcessingMethod();
			processingMethod3.setOrder(BigInteger.valueOf(3));
			List<AbstractParam> pmList3 = processingMethod3.getParamGroup();
			UserParam up3_1 = new UserParam();
			up3_1.setName("Plugin type");
			up3_1.setValue("feature detection and quantitation");
			pmList3.add(up3_1);
			UserParam up3_2 = new UserParam();
			up3_2.setName("Plugin name");
			up3_2.setValue(sPipeline[2]);
			pmList3.add(up3_2);
			UserParam up3_3 = new UserParam();
			up3_3.setName("Plugin configuration file");
			up3_3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[2] + ".xtp");
			pmList3.add(up3_3);
			dataProcessing.getProcessingMethod().add(processingMethod3);

			ProcessingMethod processingMethod4 = new ProcessingMethod();
			processingMethod4.setOrder(BigInteger.valueOf(4));
			List<AbstractParam> pmList4 = processingMethod4.getParamGroup();
			UserParam up4_1 = new UserParam();
			up4_1.setName("Plugin type");
			up4_1.setValue("Output");
			pmList4.add(up4_1);
			UserParam up4_2 = new UserParam();
			up4_2.setName("Plugin name");
			up4_2.setValue(sPipeline[3]);
			pmList4.add(up4_2);
			UserParam up4_3 = new UserParam();
			up4_3.setName("Plugin configuration file");
			up4_3.setValue(ProteoSuiteView.sWorkspace.replace("\\", "/") + "/xTracker_"
					+ sPipeline[3] + ".xtp");
			pmList4.add(up4_3);
			dataProcessing.getProcessingMethod().add(processingMethod4);
		}
		dataProcessingList.getDataProcessing().add(dataProcessing);
		qml.setDataProcessingList(dataProcessingList);

		// ------------------------//
		// ... Create AssayList ...//
		// ------------------------//
		AssayList assays = new AssayList();
		assays.setId("AssayList1");
		List<Assay> assayList = assays.getAssay();
		// This will be used in StudyVariableList
		Map<String, List<Assay>> studyVarAssayID = new HashMap<String, List<Assay>>();

		if (sExperiment.contains("iTRAQ")) {
			// ... Assay list will be retrieved from the parameters set up in
			// the configQuantML file ...//
			try {
				DocumentBuilderFactory domFactory = DocumentBuilderFactory
						.newInstance();
				domFactory.setNamespaceAware(true);
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				Document doc = builder.parse("configQuant.xml");
				XPath xpath = XPathFactory.newInstance().newXPath();

				// ... Check raw files ...//
				for (int iJ = 0; iJ < jtRawFiles.getRowCount(); iJ++) {
					XPathExpression expr = xpath
							.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
									+ jtRawFiles.getValueAt(iJ, 0) + "']");
					NodeList nodes = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					if (nodes.getLength() <= 0) {
						// TODO: move to exception
						JOptionPane
								.showMessageDialog(
										null,
										"Please specify the parameter settings for each raw file in Project->Set Quantitation Parameters->iTRAQ.",
										"Error",
										JOptionPane.INFORMATION_MESSAGE);
						return false;
					}
					// ... Validating files ...//
					expr = xpath
							.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='"
									+ jtRawFiles.getValueAt(iJ, 0)
									+ "']/AssayParam");
					nodes = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					boolean blnExists = false;
					for (int iI = 0; iI < nodes.getLength(); iI++) {
						Node node = nodes.item(iI);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element element = (Element) node;
							NodeList nodelist = element
									.getElementsByTagName("AssayID");
							Element element2 = (Element) nodelist.item(0);
							NodeList fstNm1 = element2.getChildNodes();
							String sAssayID = fstNm1.item(0).getNodeValue()
									.toString();

							Element element3 = (Element) node;
							NodeList nodelist2 = element3
									.getElementsByTagName("AssayName");
							Element element4 = (Element) nodelist2.item(0);
							NodeList fstNm2 = element4.getChildNodes();
							String sAssayName = fstNm2.item(0).getNodeValue()
									.toString();

							Element element5 = (Element) node;
							NodeList nodelist3 = element5
									.getElementsByTagName("mzValue");
							Element element6 = (Element) nodelist3.item(0);
							NodeList fstNm3 = element6.getChildNodes();
							String sMzValue = fstNm3.item(0).getNodeValue()
									.toString();

							Element element7 = (Element) node;
							NodeList nodelist4 = element7
									.getElementsByTagName("StudyVariable");
							Element element8 = (Element) nodelist4.item(0);
							NodeList fstNm4 = element8.getChildNodes();
							String sStudyVariable = fstNm4.item(0)
									.getNodeValue().toString();

							Assay assay = new Assay();
							String sRawFile = jtRawFiles.getValueAt(iJ, 0)
									.toString();
							String assName = sAssayID; // ... e.g. 114 ...//
							String assId = "i" + (iJ + 1) + "_" + sAssayID;
							assay.setId(assId);
							assay.setName(assName);

							// ... Create the study variable tree ...//
							blnExists = studyVarAssayID
									.containsKey(sStudyVariable);
							List<Assay> al;
							if (!blnExists)
								al = new ArrayList<Assay>();
							else
								al = studyVarAssayID.get(sStudyVariable);

							al.add(assay);
							studyVarAssayID.put(sStudyVariable, al);

							// ... Check the rawFileGroup associated to that
							// assay ...//
							int iK = 0;
							for (RawFilesGroup rfg : rawFilesGroupList) {
								String sKey = sRawFile;
								RawFilesGroup rfGroup = rawFilesGroupList
										.get(iK);
								if (rfg.getId().equals(sKey)) {
									assay.setRawFilesGroup(rfGroup);
									break;
								}
								iK++;
							}
							Label label = new Label();
							CvParam labelCvParam = new CvParam();
							labelCvParam.setAccession("");
							labelCvParam.setName(sAssayName);
							labelCvParam.setValue(sMzValue);
							labelCvParam.setCv(cvPSI_MOD);
							List<ModParam> modParams = label.getModification();
							ModParam modParam = new ModParam();
							modParam.setCvParam(labelCvParam);
							modParam.setMassDelta(145.0f);
							modParams.add(modParam);
							assay.setLabel(label);
							assayList.add(assay);
						}
					}
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		if (sExperiment.contains("emPAI")) {
			// ... Check the rawFileGroup associated to that assay ...//
			int iK = 0;
			for (RawFilesGroup rfg : rawFilesGroupList) {
				Assay assay = new Assay();
				assay.setId("assay_" + Integer.toString(iK + 1));
				assay.setName("assay_" + Integer.toString(iK + 1));
				assay.setRawFilesGroup(rfg);
				CvParam labelCvParam = new CvParam();
				labelCvParam.setAccession("MS:1002038");
				labelCvParam.setName("unlabeled sample");
				labelCvParam.setCv(cvPSI_MS);
				Label label = new Label();
				List<ModParam> modParams = label.getModification();
				ModParam modParam = new ModParam();
				modParam.setCvParam(labelCvParam);
				modParam.setMassDelta(0.0f);
				modParams.add(modParam);
				assay.setLabel(label);
				assayList.add(assay);
				iK++;
			}
		}
		qml.setAssayList(assays);

		// --------------------------------//
		// ... Create StudyVariableList ...//
		// --------------------------------//

		StudyVariableList studyVariables = new StudyVariableList();
		List<StudyVariable> studyVariableList = studyVariables
				.getStudyVariable();

		if (sExperiment.contains("iTRAQ")) {
			for (Entry<String, List<Assay>> pairs : studyVarAssayID.entrySet()) {
				String group = pairs.getKey();
				List<Assay> al = pairs.getValue();
				StudyVariable studyVariable = new StudyVariable();
				studyVariable.setName(group);
				studyVariable.setId("SV_" + group);
				List<String> assayRefList = studyVariable.getAssayRefs();
				for (Assay assay : al) {
					assayRefList.add(assay.getId());
				}
				CvParam cvp9 = new CvParam();
				cvp9.setAccession("MS:1001807");
				cvp9.setCv(cvPSI_MS);
				cvp9.setValue("1");
				cvp9.setName("StudyVariable attribute");
				List<AbstractParam> paramList = studyVariable.getParamGroup();
				paramList.add(cvp9);
				studyVariableList.add(studyVariable);
			}
		}
		if (sExperiment.contains("emPAI")) {
			StudyVariable studyVariable = new StudyVariable();
			studyVariable.setName("Group");
			studyVariable.setId("SV_group");
			List<String> assayRefList = studyVariable.getAssayRefs();
			assayRefList.add(assayList.get(0).getId());
			studyVariableList.add(studyVariable);
		}
		qml.setStudyVariableList(studyVariables);

		// ... Marshal mzQuantML object ...//
		MzQuantMLMarshaller marshaller = new MzQuantMLMarshaller(ProteoSuiteView.sWorkspace
				+ "/" + sFile);
		marshaller.marshall(qml);
		return ret;
	}
}