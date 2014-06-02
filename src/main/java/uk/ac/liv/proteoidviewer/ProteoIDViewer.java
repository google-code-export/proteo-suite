/*
 * ProteoIDViewer.java
 *
 * Created on 15-Dec-2010, 10:34:37
 * 
 * Fawaz Ghali
 * 
 */
package uk.ac.liv.proteoidviewer;

import uk.ac.liv.proteoidviewer.listener.mainTabbedPaneMouseClicked;
import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;
import uk.ac.liv.proteoidviewer.tabs.ProtocolPanel;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.ebi.jmzidml.model.MzIdentMLObject;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringEscapeUtils;
import org.proteosuite.model.MzIdentMLFile;

import uk.ac.liv.mzidlib.FalseDiscoveryRate;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;
import uk.ac.ebi.pride.tools.mzml_wrapper.MzMlWrapper;

/**
 *
 * @author Fawaz Ghali
 */
public class ProteoIDViewer extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	// FDR and Stats
	// stats
	public FalseDiscoveryRate falseDiscoveryRate = null;
	public double falsePositiveSii;
	public double truePositiveSii;
	public double fdrSii;
	// Lists to store mzIdentML data
	public boolean secondTab;
	public boolean thirdTab;
	public boolean fourthTab;
	public boolean fifthTab;
	public boolean sixthTab;
	public JMzReader jmzreader = null;
	public File fileName = null;
	public String sourceFile = "";
	// maps to store mzIdentML data

	public MzIdentMLUnmarshaller mzIdentMLUnmarshaller = null;

	private final GlobalStatisticsPanel globalStatisticsPanel = new GlobalStatisticsPanel(
			this);
	private final PeptideSummary peptideSummary = new PeptideSummary(this);
	private final ProteinDBView proteinDBView = new ProteinDBView();
	private final ProtocolPanel protocolPanel = new ProtocolPanel();
	private final ProteinView proteinView;
	private final SpectrumSummary spectrumSummary;

	/**
	 * Creates new form ProteoIDViewer
	 */
	public ProteoIDViewer() {
		// Swing init components
		spectrumSummary = new SpectrumSummary(this, proteinDBView);
		proteinView = new ProteinView(this, spectrumSummary);

		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		setPreferredSize(new Dimension(889, 939));
		addMouseListener(new mainTabbedPaneMouseClicked(this, protocolPanel,
				proteinDBView, spectrumSummary, peptideSummary,
				globalStatisticsPanel));

		addTab("Protein View", null, proteinView, "Protein View");
		addTab("Spectrum Summary", null, spectrumSummary, "Spectrum Summary");
		addTab("Peptide Summary", peptideSummary);
		addTab("Protein DB View", proteinDBView);
		addTab("Global Statistics", null, globalStatisticsPanel,
				"Global Statistics");
		addTab("Protocols", protocolPanel);

		repaint();
	}

	public boolean checkIfSpectrumIdentificationItemIsDecoy(
			SpectrumIdentificationItem spectrumIdentificationItem,
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
		boolean result = false;

		List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
				.getPeptideEvidenceRef();
		for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
			try {
				PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
						.get(i);
				PeptideEvidence peptiedEvidence = mzIdentMLUnmarshaller
						.unmarshal(PeptideEvidence.class,
								peptideEvidenceRef.getPeptideEvidenceRef());
				if (peptiedEvidence != null && peptiedEvidence.isIsDecoy()) {
					result = true;
					break;
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
		return result;
	}

	public void clearSummaryStats() {
		globalStatisticsPanel.reset();
	}

	public void createTables() {
		globalStatisticsPanel.reset();
		proteinDBView.reset();

		// protein view
		proteinView.reset();

		// spectrum view
		int spectrumIdentificationResultCvParamLengs = 0;
		int spectrumIdentificationItemCvParamLengs = 0;
		String[] sir = null;
		String[] sii = null;
		List<SpectrumIdentificationResult> spectrumIdentificationResultList = new ArrayList<>();

		Iterator<SpectrumIdentificationList> iterspectrumIdentificationList = getMzIdentMLUnmarshaller()
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationList);
		while (iterspectrumIdentificationList.hasNext()) {
			SpectrumIdentificationList spectrumIdentificationList = iterspectrumIdentificationList
					.next();

			for (int j = 0; j < spectrumIdentificationList
					.getSpectrumIdentificationResult().size(); j++) {
				spectrumIdentificationResultList.add(spectrumIdentificationList
						.getSpectrumIdentificationResult().get(j));
			}
		}

		if (spectrumIdentificationResultList.size() > 0) {

			for (int i = 0; i < spectrumIdentificationResultList.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = spectrumIdentificationResultList
						.get(i);
				if (spectrumIdentificationResultCvParamLengs < spectrumIdentificationResult
						.getCvParam().size()) {
					spectrumIdentificationResultCvParamLengs = spectrumIdentificationResult
							.getCvParam().size();
					sir = new String[spectrumIdentificationResult.getCvParam()
							.size()];
					for (int j = 0; j < spectrumIdentificationResult
							.getCvParam().size(); j++) {
						sir[j] = spectrumIdentificationResult.getCvParam()
								.get(j).getName();
					}
				}
				List<SpectrumIdentificationItem> spectrumIdentificationItemList = spectrumIdentificationResult
						.getSpectrumIdentificationItem();
				for (int j = 0; j < spectrumIdentificationItemList.size(); j++) {
					SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemList
							.get(j);
					if (spectrumIdentificationItemCvParamLengs < spectrumIdentificationItem
							.getCvParam().size()) {
						spectrumIdentificationItemCvParamLengs = spectrumIdentificationItem
								.getCvParam().size();
					}
					sii = new String[spectrumIdentificationItem.getCvParam()
							.size()];
					for (int k = 0; k < spectrumIdentificationItem.getCvParam()
							.size(); k++) {
						sii[k] = spectrumIdentificationItem.getCvParam().get(k)
								.getName();

					}

				}
			}
		}

		// TODO: Disabled - Andrew
		/*
		 * / if (spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect")
		 * != null) {
		 * spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect"
		 * ).setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue") !=
		 * null) { spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue").
		 * setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value").setComparator(new
		 * DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt
		 * ("SEQUEST:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("SEQUEST:expectation value").setComparator(new
		 * DoubleComparator()); } /
		 */

		String[] spectrumIdentificationItemTableHeaders = new String[spectrumIdentificationItemCvParamLengs + 8];
		spectrumIdentificationItemTableHeaders[0] = "ID";
		spectrumIdentificationItemTableHeaders[1] = "Peptide Sequence";
		spectrumIdentificationItemTableHeaders[2] = "Modification";
		spectrumIdentificationItemTableHeaders[3] = "Calculated MassToCharge";
		spectrumIdentificationItemTableHeaders[4] = "Experimental MassToCharge";
		spectrumIdentificationItemTableHeaders[5] = "Rank";
		spectrumIdentificationItemTableHeaders[6] = "Is Decoy";
		spectrumIdentificationItemTableHeaders[7] = "PassThreshold";
		if (sii != null) {
			for (int i = 0; i < sii.length; i++) {
				String string = sii[i];

				string = string.replaceAll("\\\\", "");

				spectrumIdentificationItemTableHeaders[8 + i] = string;
			}
		}

		spectrumSummary.reset(sir, spectrumIdentificationResultCvParamLengs,
				spectrumIdentificationItemTableHeaders);

		//
		peptideSummary.reset(spectrumIdentificationItemTableHeaders);
		// peptideEvidenceTablePeptideView.setAutoCreateRowSorter(true);
	}

	public void loadSpectrumIdentificationList(
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller,
			GlobalStatisticsPanel globalStatisticsPanel) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		List<Peptide> peptideListNonReduntant = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyTrue = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyFalse = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListPassThresholdRankOne = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListBelowThreshold = new ArrayList<>();
		globalStatisticsPanel.getSIIListPassThreshold().clear();
		try {
			List<SpectrumIdentificationResult> sirListTemp = mzIdentMLUnmarshaller
					.unmarshal(
							SpectrumIdentificationList.class,
							globalStatisticsPanel.getSiiComboBox()
									.getSelectedItem().toString())
					.getSpectrumIdentificationResult();
			List<SpectrumIdentificationItem> siiListTemp = new ArrayList<>();

			for (int i = 0; i < sirListTemp.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = sirListTemp
						.get(i);

				for (int j = 0; j < spectrumIdentificationResult
						.getSpectrumIdentificationItem().size(); j++) {
					siiListTemp.add(spectrumIdentificationResult
							.getSpectrumIdentificationItem().get(j));
				}
			}

			if (siiListTemp.size() > 0) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(0);
				List<CvParam> cvParamList = spectrumIdentificationItem
						.getCvParam();
				for (int i = 0; i < cvParamList.size(); i++) {
					CvParam cvParam = cvParamList.get(i);
					if (cvParam.getName() != null
							&& !cvParam.getName().equals("")) {
						boolean isthere = false;
						for (int j = 0; j < globalStatisticsPanel
								.getJComboBox1().getItemCount(); j++) {
							if (globalStatisticsPanel.getJComboBox1()
									.getItemAt(j).equals(cvParam.getName())) {
								isthere = true;
								break;
							}
						}
						if (!isthere) {
							globalStatisticsPanel.getJComboBox1().addItem(
									cvParam.getName());
							globalStatisticsPanel.getCvTermMap().put(
									cvParam.getName(), cvParam.getAccession());
						}
					}
				}
			}

			for (int j = 0; j < siiListTemp.size(); j++) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(j);

				if (spectrumIdentificationItem.isPassThreshold()) {
					globalStatisticsPanel.getSIIListPassThreshold().add(
							spectrumIdentificationItem);
					String p_ref = spectrumIdentificationItem.getPeptideRef();

					// Update FG 18-03-2013 escape XML
					Peptide peptide = mzIdentMLUnmarshaller.unmarshal(
							Peptide.class, StringEscapeUtils.escapeXml(p_ref));

					if (!peptideListNonReduntant.contains(peptide)) {
						peptideListNonReduntant.add(peptide);
					}
				} else {
					sIIListBelowThreshold.add(spectrumIdentificationItem);
				}

				if (spectrumIdentificationItem.isPassThreshold()
						&& spectrumIdentificationItem.getRank() == 1) {
					sIIListPassThresholdRankOne.add(spectrumIdentificationItem);
				}

				boolean isdecoy = true;
				List<PeptideEvidenceRef> peRefLst = spectrumIdentificationItem
						.getPeptideEvidenceRef();
				for (int k = 0; k < peRefLst.size(); k++) {
					PeptideEvidenceRef peptideEvidenceRef = peRefLst.get(k);
					PeptideEvidence peptideEvidence = mzIdentMLUnmarshaller
							.unmarshal(PeptideEvidence.class,
									peptideEvidenceRef.getPeptideEvidenceRef());
					if (!peptideEvidence.isIsDecoy()) {
						sIIListIsDecoyFalse.add(spectrumIdentificationItem);
						isdecoy = false;
						break;
					}
				}
				if (isdecoy) {
					sIIListIsDecoyTrue.add(spectrumIdentificationItem);
				}
			}

			int sirSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.SpectrumIdentificationResult
							.getXpath());
			globalStatisticsPanel.getTotalSIRLabelValue().setText("" + sirSize);

			if (globalStatisticsPanel.getSIIListPassThreshold() != null) {
				globalStatisticsPanel.getTotalSIIaboveThresholdLabelValue()
						.setText(
								String.valueOf(globalStatisticsPanel
										.getSIIListPassThreshold().size()));
			}
			if (sIIListBelowThreshold != null) {
				globalStatisticsPanel.getTotalSIIbelowThresholdLabelValue()
						.setText(String.valueOf(sIIListBelowThreshold.size()));
			}
			if (sIIListPassThresholdRankOne != null) {
				globalStatisticsPanel
						.getTotalSIIaboveThresholdRankOneLabelValue().setText(
								String.valueOf(sIIListPassThresholdRankOne
										.size()));
			}
			if (sIIListPassThresholdRankOne != null && sirSize > 0) {
				double percent = IdViewerUtils
						.roundTwoDecimals((float) sIIListPassThresholdRankOne
								.size() * 100 / sirSize);
				globalStatisticsPanel.getPercentIdentifiedSpectraLabelValue()
						.setText(String.valueOf(percent) + "%");
			}
			if (peptideListNonReduntant != null) {
				globalStatisticsPanel
						.getTotalPeptidesaboveThresholdLabelValue().setText(
								String.valueOf(peptideListNonReduntant.size()));
			}
			int pagSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.ProteinAmbiguityGroup
							.getXpath());

			globalStatisticsPanel.getTotalPAGsLabelValue()
					.setText("" + pagSize);

			if (globalStatisticsPanel.getPDHListPassThreshold() != null) {
				globalStatisticsPanel.getTotalPDHsaboveThresholdLabelValue()
						.setText(
								String.valueOf(globalStatisticsPanel
										.getPDHListPassThreshold().size()));
			}
			if (sIIListIsDecoyTrue != null) {
				globalStatisticsPanel.getIsDecoySiiValue().setText(
						String.valueOf(sIIListIsDecoyTrue.size()));
			}
			if (sIIListIsDecoyFalse != null) {
				globalStatisticsPanel.getIsDecoySiiFalseValue().setText(
						String.valueOf(sIIListIsDecoyFalse.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				falsePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListIsDecoyTrue.size());
				globalStatisticsPanel.getFpSiiValue().setText(
						String.valueOf(falsePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				truePositiveSii = IdViewerUtils
						.roundThreeDecimals(globalStatisticsPanel
								.getSIIListPassThreshold().size()
								- sIIListIsDecoyTrue.size());

				globalStatisticsPanel.getTpSiiValue().setText(
						String.valueOf(truePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				if (falsePositiveSii + truePositiveSii == 0) {
					globalStatisticsPanel.getFdrSiiValue().setText("0.0");
				} else {
					fdrSii = IdViewerUtils
							.roundThreeDecimals((falsePositiveSii)
									/ (falsePositiveSii + truePositiveSii));
					globalStatisticsPanel.getFdrSiiValue().setText(
							String.valueOf(fdrSii));
				}
			}
			// thresholdValue.setText(spectrumIdentificationProtocol.get(0).getThreshold().getCvParam().get(0).getValue());
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public GlobalStatisticsPanel globalStatisticsPanel() {
		return globalStatisticsPanel;
	}

	public ProteinView getProteinView() {
		return proteinView;
	}

	public MzIdentMLUnmarshaller getMzIdentMLUnmarshaller() {
		return mzIdentMLUnmarshaller;
	}

	public void setMzIdentMLFile(MzIdentMLFile identDataFile) {
		mzIdentMLUnmarshaller = identDataFile.getUnmarshaller();
		fileName = identDataFile.getFile();

		if (!mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith("1.1.")
				&& !mzIdentMLUnmarshaller.getMzIdentMLVersion().startsWith(
						"1.2.")) {
			JOptionPane
					.showMessageDialog(
							null,
							"The file is not compatible with the Viewer: different mzIdentMl version",
							"mzIdentMl version",
							JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		jmzreader = null;
		createTables();
		clearSummaryStats();
		setSelectedIndex(0);
		secondTab = false;
		thirdTab = false;
		fourthTab = false;
		fifthTab = false;
		sixthTab = false;
		getProteinView().loadProteinAmbiguityGroupTable(
				globalStatisticsPanel(), getMzIdentMLUnmarshaller());

		if (identDataFile.getParent() != null)
		{
			try {
				File file = identDataFile.getParent().getFile();
				if (file.getAbsolutePath().toLowerCase().endsWith("mgf")) {
					jmzreader = new MgfFile(file);
					sourceFile = "mgf";
				} else if (file.getAbsolutePath().toLowerCase().endsWith("mzml")) {
					jmzreader = new MzMlWrapper(file);
					sourceFile = "mzML";
				} else {
					JOptionPane.showMessageDialog(null, file.getName()
							+ " is not supported", "Spectrum file",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (JMzReaderException ex) {
				System.out.println(ex.getMessage());
			}
		}
		if (getProteinView().getAmbiguityGroupTable().getRowCount() == 0) {
			JOptionPane.showMessageDialog(null,
					"There is no protein view for this file", "Protein View",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public <T extends MzIdentMLObject> T unmarshal(Class<T> clazz, String id) throws JAXBException {
		return mzIdentMLUnmarshaller.unmarshal(clazz, id);
	}
}