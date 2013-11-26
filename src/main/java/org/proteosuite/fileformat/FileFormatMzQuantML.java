package org.proteosuite.fileformat;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;

import uk.ac.liv.jmzqml.MzQuantMLElement;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.FeatureList;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensus;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensusList;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.Protein;
import uk.ac.liv.jmzqml.model.mzqml.ProteinList;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

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
}
