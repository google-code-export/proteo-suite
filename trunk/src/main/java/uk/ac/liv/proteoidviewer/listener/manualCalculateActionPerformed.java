package uk.ac.liv.proteoidviewer.listener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.xml.bind.JAXBException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.liv.mzidlib.FalseDiscoveryRate;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;
import uk.ac.liv.proteoidviewer.util.ProgressBarDialog;

public class manualCalculateActionPerformed implements ActionListener {

	private final ProteoIDViewer proteoIDViewer;
	private final JComboBox<String> siiComboBox;
	private final JTextField manualDecoyPrefixValue;
	private final JCheckBox manualDecoy;
	private final JLabel isDecoySiiValue;
	private final JLabel isDecoySiiFalseValue;
	private final JTextField manualDecoyRatioValue;
	private final JLabel fpSiiValue;
	private final List<SpectrumIdentificationItem> sIIListPassThreshold;
	private final JLabel tpSiiValue;
	private final JLabel fdrSiiValue;
	private final JPanel fdrPanel;
	private final JComboBox<String> jComboBox2;
	private final Map<String, String> cvTermMap;
	private final JComboBox<String> jComboBox1;
	private final JPanel tpEvaluePanel;
	private final JPanel tpQvaluePanel;

	public manualCalculateActionPerformed(ProteoIDViewer proteoIDViewer, JComboBox<String> siiComboBox, 
			JCheckBox manualDecoy, JTextField manualDecoyPrefixValue, JLabel isDecoySiiValue, JLabel isDecoySiiFalseValue, 
			JTextField manualDecoyRatioValue, JLabel fpSiiValue, List<SpectrumIdentificationItem> sIIListPassThreshold, JLabel tpSiiValue, 
			JLabel fdrSiiValue, JPanel fdrPanel, JComboBox<String> jComboBox2, Map<String, String> cvTermMap, JComboBox<String> jComboBox1, 
			JPanel tpEvaluePanel, JPanel tpQvaluePanel) {
		this.proteoIDViewer = proteoIDViewer;
		this.siiComboBox = siiComboBox;
		this.manualDecoy = manualDecoy;
		this.manualDecoyPrefixValue = manualDecoyPrefixValue;
		this.isDecoySiiValue = isDecoySiiValue;
		this.isDecoySiiFalseValue = isDecoySiiFalseValue;
		this.manualDecoyRatioValue = manualDecoyRatioValue;
		this.fpSiiValue = fpSiiValue;
		this.sIIListPassThreshold = sIIListPassThreshold;
		this.tpSiiValue = tpSiiValue;
		this.fdrSiiValue = fdrSiiValue;
		this.fdrPanel = fdrPanel;
		this.jComboBox2 = jComboBox2;
		this.cvTermMap = cvTermMap;
		this.jComboBox1 = jComboBox1;
		this.tpEvaluePanel = tpEvaluePanel;
		this.tpQvaluePanel = tpQvaluePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProgressBarDialog progressBarDialog = new ProgressBarDialog(null, true);
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				progressBarDialog.setTitle("Calculating FDR stats and drawing graphs. Please wait..");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");

		thread.start();

		new Thread("LoadingThread") {

			@Override
			public void run() {
				makeFDRGraphs();
				progressBarDialog.setVisible(false);
				progressBarDialog.dispose();
			}
		}.start();

	}

	private void makeFDRGraphs() {
		List<SpectrumIdentificationItem> sIIListIsDecoyTrue = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyFalse = new ArrayList<>();
		try {

			List<SpectrumIdentificationResult> sirListTemp = proteoIDViewer.unmarshal(SpectrumIdentificationList.class,
							siiComboBox.getSelectedItem().toString())
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

			boolean isdecoy = true;
			for (int j = 0; j < siiListTemp.size(); j++) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(j);
				List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
						.getPeptideEvidenceRef();

				for (int k = 0; k < peptideEvidenceRefList.size(); k++) {
					PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
							.get(k);

					PeptideEvidence peptideEvidence1 = proteoIDViewer.unmarshal(PeptideEvidence.class,
									peptideEvidenceRef.getPeptideEvidenceRef());
					DBSequence dbSeq = proteoIDViewer.unmarshal(
							DBSequence.class,
							peptideEvidence1.getDBSequenceRef());

					if (manualDecoy.isSelected()) { // Added by ARJ to use value
													// in file if manual decoy
													// is not selected
						if (!dbSeq.getAccession().startsWith(
								manualDecoyPrefixValue.getText())) {
							sIIListIsDecoyFalse.add(spectrumIdentificationItem);
							isdecoy = false;
							break;
						}
					} else {
						if (!peptideEvidence1.isIsDecoy()) {
							sIIListIsDecoyFalse.add(spectrumIdentificationItem);
							isdecoy = false;
							break;
						}
					}

				}
				if (isdecoy) {
					sIIListIsDecoyTrue.add(spectrumIdentificationItem);
				}

			}

			if (sIIListIsDecoyTrue != null) {
				isDecoySiiValue.setText(String.valueOf(sIIListIsDecoyTrue
						.size()));
			}
			if (sIIListIsDecoyFalse != null) {
				isDecoySiiFalseValue.setText(String.valueOf(sIIListIsDecoyFalse
						.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				proteoIDViewer.falsePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListIsDecoyTrue.size()
								/ Double.valueOf(manualDecoyRatioValue
										.getText().trim()));
				fpSiiValue.setText(String.valueOf(proteoIDViewer.falsePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				proteoIDViewer.truePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListPassThreshold.size()
								- sIIListIsDecoyTrue.size());
				tpSiiValue.setText(String.valueOf(proteoIDViewer.truePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				proteoIDViewer.fdrSii = IdViewerUtils.roundThreeDecimals(proteoIDViewer.falsePositiveSii
						/ (proteoIDViewer.falsePositiveSii + proteoIDViewer.truePositiveSii));
				fdrSiiValue.setText(String.valueOf(proteoIDViewer.fdrSii));
			}

			fdrPanel.removeAll();

			if (manualDecoy.isSelected()) {
				String cvTerm = "";
				boolean order = false;
				if (jComboBox2.getSelectedItem().equals(
						"Better scores are lower")) {
					order = true;
				}
				cvTerm = cvTermMap.get(jComboBox1.getSelectedItem());
				proteoIDViewer.falseDiscoveryRate = new FalseDiscoveryRate(proteoIDViewer.fileName,
						Integer.parseInt(manualDecoyRatioValue.getText()),
						manualDecoyPrefixValue.getText(), cvTerm, order);

				proteoIDViewer.falseDiscoveryRate.computeFDRusingJonesMethod();

				// FDR Graph
				XYSeriesCollection datasetFDR = new XYSeriesCollection();
				final XYSeries dataFDR = new XYSeries("FDR", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					if (proteoIDViewer.falseDiscoveryRate.getSorted_evalues().get(i) != 0) {
						dataFDR.add(Math.log10(proteoIDViewer.falseDiscoveryRate
								.getSorted_evalues().get(i)),
								proteoIDViewer.falseDiscoveryRate.getSorted_estimatedFDR()
										.get(i));
					}

				}

				final XYSeries dataFDRQvalue = new XYSeries("Q-value", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					if (proteoIDViewer.falseDiscoveryRate.getSorted_evalues().get(i) != 0) {
						dataFDRQvalue.add(Math.log10(proteoIDViewer.falseDiscoveryRate
								.getSorted_evalues().get(i)),
								proteoIDViewer.falseDiscoveryRate.getSorted_qValues().get(i));
					}
				}

				final XYSeries dataFDRSimple = new XYSeries("Simple FDR", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					if (proteoIDViewer.falseDiscoveryRate.getSorted_evalues().get(i) != 0) {
						dataFDRSimple
								.add(Math.log10(proteoIDViewer.falseDiscoveryRate
										.getSorted_evalues().get(i)),
										proteoIDViewer.falseDiscoveryRate
												.getSorted_simpleFDR().get(i));
					}

				}

				datasetFDR.addSeries(dataFDR);
				datasetFDR.addSeries(dataFDRQvalue);
				datasetFDR.addSeries(dataFDRSimple);
				final JFreeChart chartFDR = createFDRChart(datasetFDR);
				final ChartPanel chartPanelFDR = new ChartPanel(chartFDR);

				chartPanelFDR.setPreferredSize(new Dimension(257, 255));
				fdrPanel.add(chartPanelFDR);
				JScrollPane jFDRPane = new JScrollPane(chartPanelFDR);
				fdrPanel.setLayout(new BorderLayout());
				fdrPanel.add(jFDRPane);

				// TP vs Evalue Graph
				XYSeriesCollection datasetTpQvalue = new XYSeriesCollection();
				final XYSeries dataTpQvalue = new XYSeries("TP", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					if (proteoIDViewer.falseDiscoveryRate.getSorted_evalues().get(i) != 0) {
						dataTpQvalue.add(Math.log10(proteoIDViewer.falseDiscoveryRate
								.getSorted_evalues().get(i)),
								proteoIDViewer.falseDiscoveryRate.getTP().get(i));
					}
				}

				final XYSeries dataFpQvalue = new XYSeries("FP", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					if (proteoIDViewer.falseDiscoveryRate.getSorted_evalues().get(i) != 0) {
						dataFpQvalue.add(Math.log10(proteoIDViewer.falseDiscoveryRate
								.getSorted_evalues().get(i)),
								proteoIDViewer.falseDiscoveryRate.getFP().get(i));
					}
				}

				datasetTpQvalue.addSeries(dataTpQvalue);
				datasetTpQvalue.addSeries(dataFpQvalue);
				final JFreeChart chartTpQvalue = createTpQvalueChart(datasetTpQvalue);
				final ChartPanel chartPanelTpQvalue = new ChartPanel(
						chartTpQvalue);

				chartPanelTpQvalue.setPreferredSize(new Dimension(257, 255));
				tpEvaluePanel.add(chartPanelTpQvalue);
				JScrollPane jTpQvaluePane = new JScrollPane(chartPanelTpQvalue);
				tpEvaluePanel.setLayout(new BorderLayout());
				tpEvaluePanel.add(jTpQvaluePane);

				// TP vs Qvalue
				XYSeriesCollection datasetTpQvalueCollection = new XYSeriesCollection();
				final XYSeries dataTpQValueSeries = new XYSeries("data", false);

				for (int i = 0; i < proteoIDViewer.falseDiscoveryRate.getSorted_evalues()
						.size(); i++) {
					dataTpQValueSeries.add(proteoIDViewer.falseDiscoveryRate
							.getSorted_qValues().get(i), proteoIDViewer.falseDiscoveryRate
							.getTP().get(i));
					// System.out.println(falseDiscoveryRate.getSorted_qValues().get(i)
					// + " " + falseDiscoveryRate.getTP().get(i) );
				}

				datasetTpQvalueCollection.addSeries(dataTpQValueSeries);
				final JFreeChart chartTpQvalueChart = createTpQvalue(datasetTpQvalueCollection);
				final ChartPanel chartPanelTpSimpleFDR = new ChartPanel(
						chartTpQvalueChart);

				chartPanelTpSimpleFDR.setPreferredSize(new Dimension(257, 255));
				tpQvaluePanel.add(chartPanelTpSimpleFDR);
				JScrollPane jTpSimpleFDRPane = new JScrollPane(
						chartPanelTpSimpleFDR);
				tpQvaluePanel.setLayout(new BorderLayout());
				tpQvaluePanel.add(jTpSimpleFDRPane);

				proteoIDViewer.repaint();
			}
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}// GEN-LAST:event_manualCalculateActionPerformed


	private JFreeChart createFDRChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createScatterPlot("FDR", // chart
																		// title
				"log10(e-value)", // x axis label
				"", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		plot.setRenderer(renderer);
		return chart;
	}

	private JFreeChart createTpQvalueChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createScatterPlot(
				"TP vs FP vs E-value", // chart title
				"log10(e-value)", // x axis label
				"", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		plot.setRenderer(renderer);
		return chart;
	}

	private JFreeChart createTpQvalue(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createScatterPlot(
				"TP vs Q-value", // chart title
				"Q-value", // x axis label
				"TP value", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		plot.setRenderer(renderer);
		return chart;
	}
}
