package uk.ac.liv.proteoidviewer.tabs;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringEscapeUtils;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;
import uk.ac.liv.proteoidviewer.listener.manualCalculateActionPerformed;
import uk.ac.liv.proteoidviewer.listener.manualDecoyActionPerformed;
import uk.ac.liv.proteoidviewer.listener.siiComboBoxActionPerformed;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

public class GlobalStatisticsPanel extends JPanel implements LazyLoading {
	private static final long serialVersionUID = 1L;
	private final ProteoIDViewer proteoIDViewer;

	private final JLabel tpSiiValue = new JLabel("0");
	private final JLabel fdrSiiValue = new JLabel("0");
	private final JLabel percentIdentifiedSpectraLabelValue = new JLabel("0");
	private final JLabel fpSiiValue = new JLabel("0");
	private final JLabel isDecoySiiFalseValue = new JLabel("0");
	private final JLabel isDecoySiiValue = new JLabel("0");
	private final JLabel totalPAGsLabelValue = new JLabel("0");
	private final JLabel totalPDHsaboveThresholdLabelValue = new JLabel("0");
	private final JLabel totalPeptidesaboveThresholdLabelValue = new JLabel("0");
	private final JLabel totalSIIaboveThresholdLabelValue = new JLabel("0");
	private final JLabel totalSIIaboveThresholdRankOneLabelValue = new JLabel(
			"0");
	private final JLabel totalSIIbelowThresholdLabelValue = new JLabel("0");
	private final JLabel totalSIRLabelValue = new JLabel("0");
	private final JPanel tpEvaluePanel = new JPanel();
	private final JPanel tpQvaluePanel = new JPanel();
	private final JComboBox<String> jComboBox1 = new JComboBox<>();
	private final JComboBox<String> siiComboBox = new JComboBox<>();
	private final JPanel fdrPanel = new JPanel();
	private final Map<String, String> cvTermMap = new HashMap<>();
	private final List<SpectrumIdentificationItem> sIIListPassThreshold = new ArrayList<>();
	private final List<ProteinDetectionHypothesis> pDHListPassThreshold = new ArrayList<>();

	public double falsePositiveSii;
	public double truePositiveSii;
	public double fdrSii;

	public GlobalStatisticsPanel(ProteoIDViewer proteoIDViewer) {
		addComponentListener(new LazyLoadingComponentListener());
		this.proteoIDViewer = proteoIDViewer;

		siiComboBox.addActionListener(new siiComboBoxActionPerformed(
				this));

		fdrPanel.setBorder(BorderFactory.createTitledBorder("FDR Graph"));
		fdrPanel.setPreferredSize(new Dimension(257, 255));

		createPanel();
	}

	private void createPanel() {
		final JTextField manualDecoyRatioValue = new JTextField("1");
		manualDecoyRatioValue.setEnabled(false);
		final JTextField manualDecoyPrefixValue = new JTextField("Rev_");
		manualDecoyPrefixValue.setEnabled(false);
		final JComboBox<String> jComboBox2 = new JComboBox<>(new String[] {
				"Better scores are lower", "Better scores are higher" });

		tpEvaluePanel.setBorder(BorderFactory
				.createTitledBorder("TP vs FP vs E-value"));
		tpEvaluePanel.setPreferredSize(new Dimension(257, 255));

		tpQvaluePanel.setBorder(BorderFactory
				.createTitledBorder("TP vs Q-value"));
		tpQvaluePanel.setPreferredSize(new Dimension(257, 255));

		final JCheckBox manualDecoy = new JCheckBox();
		manualDecoy.addActionListener(new manualDecoyActionPerformed(
				manualDecoyPrefixValue, manualDecoyRatioValue));

		final JButton manualCalculate = new JButton("Calculate / Show graphs");
		manualCalculate.addActionListener(new manualCalculateActionPerformed(
				proteoIDViewer, siiComboBox, manualDecoy,
				manualDecoyPrefixValue, isDecoySiiValue, isDecoySiiFalseValue,
				manualDecoyRatioValue, fpSiiValue, sIIListPassThreshold,
				tpSiiValue, fdrSiiValue, fdrPanel, jComboBox2, cvTermMap,
				jComboBox1, tpEvaluePanel, tpQvaluePanel, this));

		JPanel leftSummary = new JPanel(new GridLayout(11, 2));
		leftSummary.add(new JLabel("SII List:"));
		leftSummary.add(siiComboBox);
		leftSummary.add(new JLabel("Total SIR:"));
		leftSummary.add(totalSIRLabelValue);
		leftSummary.add(new JLabel("Total PSM:"));
		leftSummary.add(Box.createGlue());
		leftSummary.add(new JLabel("Total PSM below Threshold:"));
		leftSummary.add(totalSIIbelowThresholdLabelValue);
		leftSummary.add(new JLabel("Total PSM pass Threshold:"));
		leftSummary.add(totalSIIaboveThresholdLabelValue);
		leftSummary
				.add(new JLabel("Total PSM pass Threshold and where rank=1:"));
		leftSummary.add(totalSIIaboveThresholdRankOneLabelValue);
		leftSummary.add(new JLabel("Total PAGs:"));
		leftSummary.add(totalPAGsLabelValue);
		leftSummary.add(new JLabel("Total PDHs:"));
		leftSummary.add(Box.createGlue());
		leftSummary.add(new JLabel("Total PDH above Threshold:"));
		leftSummary.add(totalPDHsaboveThresholdLabelValue);
		leftSummary.add(new JLabel("Percent identified spectra:"));
		leftSummary.add(percentIdentifiedSpectraLabelValue);
		leftSummary.add(new JLabel(
				"Total non-redundant peptides above Threshold:"));
		leftSummary.add(totalPeptidesaboveThresholdLabelValue);

		JPanel rightSummary = new JPanel(new GridLayout(11, 2));
		rightSummary.add(new JLabel("PSM with Decoy = true:"));
		rightSummary.add(isDecoySiiValue);
		rightSummary.add(new JLabel("PSM with Decoy = false:"));
		rightSummary.add(isDecoySiiFalseValue);
		rightSummary.add(new JLabel("FP for PSM:"));
		rightSummary.add(fpSiiValue);
		rightSummary.add(new JLabel("TP for PSM:"));
		rightSummary.add(tpSiiValue);
		rightSummary.add(new JLabel("FDR for PSM:"));
		rightSummary.add(fdrSiiValue);
		rightSummary.add(new JLabel("FP for proteins:"));
		rightSummary.add(new JLabel("0"));
		rightSummary.add(new JLabel("TP for proteins:"));
		rightSummary.add(new JLabel("0"));
		rightSummary.add(new JLabel("FDR for proteins:"));
		rightSummary.add(new JLabel("0"));

		JPanel calculateParams = new JPanel(new FlowLayout());
		calculateParams.add(manualDecoy);
		calculateParams.add(new JLabel("Manual Decoy:"));
		calculateParams.add(new JLabel("Prefix"));
		calculateParams.add(manualDecoyPrefixValue);
		calculateParams.add(new JLabel("Ratio"));
		calculateParams.add(manualDecoyRatioValue);
		calculateParams.add(jComboBox1);
		// calculateParams.add(psmRankValue);
		calculateParams.add(jComboBox2);
		calculateParams.add(manualCalculate);

		JPanel bottomTables = new JPanel(new GridLayout(1, 3));
		bottomTables.add(fdrPanel);
		bottomTables.add(tpEvaluePanel);
		bottomTables.add(tpQvaluePanel);

		setLayout(new GridLayout(4, 1));
		setBorder(BorderFactory.createTitledBorder("Summary"));
		add(leftSummary);
		add(rightSummary);
		add(calculateParams);
		add(bottomTables);
	}

	public List<ProteinDetectionHypothesis> getPDHListPassThreshold() {
		return pDHListPassThreshold;
	}

	public JComboBox<String> getSiiComboBox() {
		return siiComboBox;
	}

	public void reset() {
		tpSiiValue.setText("0");
		fdrSiiValue.setText("0");

		totalSIRLabelValue.setText("0");
		totalSIIaboveThresholdLabelValue.setText("0");
		totalSIIbelowThresholdLabelValue.setText("0");
		totalSIIaboveThresholdRankOneLabelValue.setText("0");
		percentIdentifiedSpectraLabelValue.setText("0");
		totalPeptidesaboveThresholdLabelValue.setText("0");
		totalPAGsLabelValue.setText("0");
		totalPDHsaboveThresholdLabelValue.setText("0");
		isDecoySiiValue.setText("0");
		isDecoySiiFalseValue.setText("0");

		fpSiiValue.setText("0");

		tpEvaluePanel.removeAll();
		tpEvaluePanel.validate();
		tpEvaluePanel.repaint();

		tpQvaluePanel.removeAll();
		tpQvaluePanel.validate();
		tpQvaluePanel.repaint();
		jComboBox1.removeAllItems();

		siiComboBox.removeAllItems();

		fdrPanel.removeAll();
		fdrPanel.validate();
		fdrPanel.repaint();

	}

	@Override
	public void load() {
		Iterator<SpectrumIdentificationList> iterspectrumIdentificationList = proteoIDViewer
				.getMzIdentMLUnmarshaller().unmarshalCollectionFromXpath(
						MzIdentMLElement.SpectrumIdentificationList);
		while (iterspectrumIdentificationList.hasNext()) {
			SpectrumIdentificationList spectrumIdentificationList1 = iterspectrumIdentificationList
					.next();

			getSiiComboBox().addItem(
					spectrumIdentificationList1.getId());
		}
		
		MzIdentMLUnmarshaller mzIdentMLUnmarshaller = proteoIDViewer.getMzIdentMLUnmarshaller();
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		List<Peptide> peptideListNonReduntant = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyTrue = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyFalse = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListPassThresholdRankOne = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListBelowThreshold = new ArrayList<>();
		sIIListPassThreshold.clear();
		try {
			List<SpectrumIdentificationResult> sirListTemp = mzIdentMLUnmarshaller
					.unmarshal(SpectrumIdentificationList.class,
							getSiiComboBox().getSelectedItem().toString())
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
						for (int j = 0; j < jComboBox1.getItemCount(); j++) {
							if (jComboBox1.getItemAt(j).equals(
									cvParam.getName())) {
								isthere = true;
								break;
							}
						}
						if (!isthere) {
							jComboBox1.addItem(cvParam.getName());
							cvTermMap.put(cvParam.getName(),
									cvParam.getAccession());
						}
					}
				}
			}

			for (int j = 0; j < siiListTemp.size(); j++) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(j);

				if (spectrumIdentificationItem.isPassThreshold()) {
					sIIListPassThreshold.add(spectrumIdentificationItem);
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
			totalSIRLabelValue.setText("" + sirSize);

			if (sIIListPassThreshold != null) {
				totalSIIaboveThresholdLabelValue.setText(
						String.valueOf(sIIListPassThreshold.size()));
			}
			if (sIIListBelowThreshold != null) {
				totalSIIbelowThresholdLabelValue.setText(
						String.valueOf(sIIListBelowThreshold.size()));
			}
			if (sIIListPassThresholdRankOne != null) {
				totalSIIaboveThresholdRankOneLabelValue.setText(
						String.valueOf(sIIListPassThresholdRankOne.size()));
			}
			if (sIIListPassThresholdRankOne != null && sirSize > 0) {
				double percent = IdViewerUtils
						.roundTwoDecimals((float) sIIListPassThresholdRankOne
								.size() * 100 / sirSize);
				percentIdentifiedSpectraLabelValue.setText(
						String.valueOf(percent) + "%");
			}
			if (peptideListNonReduntant != null) {
				totalPeptidesaboveThresholdLabelValue.setText(
						String.valueOf(peptideListNonReduntant.size()));
			}
			int pagSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.ProteinAmbiguityGroup
							.getXpath());

			totalPAGsLabelValue.setText("" + pagSize);

			if (getPDHListPassThreshold() != null) {
				totalPDHsaboveThresholdLabelValue.setText(
						String.valueOf(getPDHListPassThreshold().size()));
			}
			if (sIIListIsDecoyTrue != null) {
				isDecoySiiValue.setText(
						String.valueOf(sIIListIsDecoyTrue.size()));
			}
			if (sIIListIsDecoyFalse != null) {
				isDecoySiiFalseValue.setText(
						String.valueOf(sIIListIsDecoyFalse.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				falsePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListIsDecoyTrue.size());
				fpSiiValue.setText(String.valueOf(falsePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				truePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListPassThreshold.size()
								- sIIListIsDecoyTrue.size());

				tpSiiValue.setText(String.valueOf(truePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				if (falsePositiveSii + truePositiveSii == 0) {
					fdrSiiValue.setText("0.0");
				} else {
					fdrSii = IdViewerUtils
							.roundThreeDecimals((falsePositiveSii)
									/ (falsePositiveSii + truePositiveSii));
					fdrSiiValue.setText(String.valueOf(fdrSii));
				}
			}
			// thresholdValue.setText(spectrumIdentificationProtocol.get(0).getThreshold().getCvParam().get(0).getValue());
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
	}
}
