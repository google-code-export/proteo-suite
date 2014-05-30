package uk.ac.liv.proteoidviewer.tabs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.listener.manualCalculateActionPerformed;
import uk.ac.liv.proteoidviewer.listener.manualDecoyActionPerformed;
import uk.ac.liv.proteoidviewer.listener.siiComboBoxActionPerformed;

public class GlobalStatisticsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final ProteoIDViewer proteoIDViewer;
	private final JTabbedPane mainTabbedPane;

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
	
	public GlobalStatisticsPanel(ProteoIDViewer proteoIDViewer,
			JTabbedPane mainTabbedPane) {
		this.proteoIDViewer = proteoIDViewer;
		this.mainTabbedPane = mainTabbedPane;

		siiComboBox.addActionListener(new siiComboBoxActionPerformed(proteoIDViewer,
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
				jComboBox1, tpEvaluePanel, tpQvaluePanel));

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
		getAccessibleContext().setAccessibleName("Global Statistics");
		getAccessibleContext().setAccessibleDescription("Global Statistics");
		getAccessibleContext().setAccessibleParent(mainTabbedPane);
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

	public JLabel getTpSiiValue() {
		return tpSiiValue;
	}

	public JLabel getFdrSiiValue() {
		return fdrSiiValue;
	}

	public JLabel getTotalSIRLabelValue() {
		return totalSIRLabelValue;
	}

	public JLabel getTotalSIIaboveThresholdLabelValue() {
		return totalSIIaboveThresholdLabelValue;
	}

	public JLabel getTotalSIIbelowThresholdLabelValue() {
		return totalSIIbelowThresholdLabelValue;
	}

	public JLabel getTotalSIIaboveThresholdRankOneLabelValue() {
		return totalSIIaboveThresholdRankOneLabelValue;
	}

	public JLabel getPercentIdentifiedSpectraLabelValue() {
		return percentIdentifiedSpectraLabelValue;
	}

	public JLabel getTotalPeptidesaboveThresholdLabelValue() {
		return totalPeptidesaboveThresholdLabelValue;
	}

	public JLabel getTotalPAGsLabelValue() {
		return totalPAGsLabelValue;
	}

	public JLabel getTotalPDHsaboveThresholdLabelValue() {
		return totalPDHsaboveThresholdLabelValue;
	}

	public JLabel getIsDecoySiiValue() {
		return isDecoySiiValue;
	}

	public JLabel getIsDecoySiiFalseValue() {
		return isDecoySiiFalseValue;
	}

	public JLabel getFpSiiValue() {
		return fpSiiValue;
	}

	public JComboBox<String> getJComboBox1() {
		return jComboBox1;
	}

	public JComboBox<String> getSiiComboBox() {
		return siiComboBox;
	}

	public Map<String, String> getCvTermMap() {
		return cvTermMap;
	}

	public List<SpectrumIdentificationItem> getSIIListPassThreshold() {
		return sIIListPassThreshold;
	}

	public List<ProteinDetectionHypothesis> getPDHListPassThreshold() {
		return pDHListPassThreshold;
	}
}
