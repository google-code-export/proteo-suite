package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisProtocolCollection;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftware;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Enzyme;
import uk.ac.ebi.jmzidml.model.mzidml.Enzymes;
import uk.ac.ebi.jmzidml.model.mzidml.ModificationParams;
import uk.ac.ebi.jmzidml.model.mzidml.Param;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionProtocol;
import uk.ac.ebi.jmzidml.model.mzidml.SearchModification;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationProtocol;
import uk.ac.ebi.jmzidml.model.mzidml.Tolerance;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;
import uk.ac.liv.proteoidviewer.tabs.ProtocolPanel;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;
import uk.ac.liv.proteoidviewer.util.ProgressBarDialog;

public class mainTabbedPaneMouseClicked implements MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final ProteinDBView proteinDBView;
	private final SpectrumSummary spectrumSummary;
	private final PeptideSummary peptideSummary;
	private final GlobalStatisticsPanel globalStatisticsPanel;
	private final ProtocolPanel protocolPanel;

	public mainTabbedPaneMouseClicked(ProteoIDViewer proteoIDViewer,
			ProtocolPanel protocolPanel, ProteinDBView proteinDBView,
			SpectrumSummary spectrumSummary, PeptideSummary peptideSummary,
			GlobalStatisticsPanel globalStatisticsPanel) {
		this.proteoIDViewer = proteoIDViewer;
		this.protocolPanel = protocolPanel;
		this.proteinDBView = proteinDBView;
		this.spectrumSummary = spectrumSummary;
		this.peptideSummary = peptideSummary;
		this.globalStatisticsPanel = globalStatisticsPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// compare which tab is selected & boolean

		if (proteoIDViewer.getSelectedIndex() == 1 && !proteoIDViewer.secondTab
				&& proteoIDViewer.getMzIdentMLUnmarshaller() != null) {
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					null, true);
			final Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");

			thread.start();

			new Thread("LoadingThread") {

				@Override
				public void run() {
					spectrumSummary
							.loadSpectrumIdentificationResultTable(proteoIDViewer.getMzIdentMLUnmarshaller());
					proteoIDViewer.secondTab = true;
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
				}
			}.start();
		}

		if (proteoIDViewer.getSelectedIndex() == 2 && !proteoIDViewer.thirdTab
				&& proteoIDViewer.getMzIdentMLUnmarshaller() != null) {

			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					null, true);
			final Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");

			thread.start();

			new Thread("LoadingThread") {

				@Override
				public void run() {
					peptideSummary
							.loadPeptideTable(proteoIDViewer.getMzIdentMLUnmarshaller());
					proteoIDViewer.thirdTab = true;
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
				}
			}.start();

		}

		if (proteoIDViewer.getSelectedIndex() == 3 && !proteoIDViewer.fourthTab
				&& proteoIDViewer.getMzIdentMLUnmarshaller() != null) {
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					null, true);
			final Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");

			thread.start();

			new Thread("LoadingThread") {

				@Override
				public void run() {
					spectrumSummary
							.loadSpectrumIdentificationResultTable(proteoIDViewer.getMzIdentMLUnmarshaller());
					proteinDBView
							.loadDBSequenceTable(proteoIDViewer.getMzIdentMLUnmarshaller());
					proteoIDViewer.fourthTab = true;
					progressBarDialog.dispose();
				}
			}.start();
		}

		if (proteoIDViewer.getSelectedIndex() == 4 && !proteoIDViewer.fifthTab
				&& proteoIDViewer.getMzIdentMLUnmarshaller() != null) {
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					null, true);
			final Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");

			thread.start();

			new Thread("LoadingThread") {

				@Override
				public void run() {
					loadSummaryStats();
					proteoIDViewer.fifthTab = true;
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
				}
			}.start();

		}

		if (proteoIDViewer.getSelectedIndex() == 5 && !proteoIDViewer.sixthTab
				&& proteoIDViewer.getMzIdentMLUnmarshaller() != null) {
			final ProgressBarDialog progressBarDialog = new ProgressBarDialog(
					null, true);
			final Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
					progressBarDialog.setVisible(true);
				}
			}, "ProgressBarDialog");

			thread.start();

			new Thread("LoadingThread") {

				@Override
				public void run() {
					loadProtocolData();
					proteoIDViewer.sixthTab = true;
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
				}
			}.start();

		}
		proteoIDViewer.repaint();
	}

	private void loadSummaryStats() {
		Iterator<SpectrumIdentificationList> iterspectrumIdentificationList = proteoIDViewer.getMzIdentMLUnmarshaller()
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationList);
		while (iterspectrumIdentificationList.hasNext()) {
			SpectrumIdentificationList spectrumIdentificationList1 = iterspectrumIdentificationList
					.next();

			globalStatisticsPanel.getSiiComboBox().addItem(spectrumIdentificationList1.getId());
		}
		proteoIDViewer.loadSpectrumIdentificationList(
				proteoIDViewer.getMzIdentMLUnmarshaller(),
				globalStatisticsPanel);
	}

	private void loadProtocolData() {
		protocolPanel.setProtocolText("");
		String text = "";
		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Map<String, AnalysisSoftware> analysisSoftwareHashMap;
		AnalysisProtocolCollection analysisProtocolCollection;
		List<SpectrumIdentificationProtocol> spectrumIdentificationProtocol;
		ProteinDetectionProtocol proteinDetectionProtocol;

		analysisSoftwareHashMap = new HashMap<>();

		Iterator<AnalysisSoftware> iterAnalysisSoftware = proteoIDViewer.getMzIdentMLUnmarshaller()
				.unmarshalCollectionFromXpath(MzIdentMLElement.AnalysisSoftware);
		while (iterAnalysisSoftware.hasNext()) {
			AnalysisSoftware analysisSoftware = iterAnalysisSoftware.next();
			analysisSoftwareHashMap.put(analysisSoftware.getId(),
					analysisSoftware);

		}

		analysisProtocolCollection = proteoIDViewer.getMzIdentMLUnmarshaller()
				.unmarshal(MzIdentMLElement.AnalysisProtocolCollection);

		spectrumIdentificationProtocol = analysisProtocolCollection
				.getSpectrumIdentificationProtocol();
		proteinDetectionProtocol = analysisProtocolCollection
				.getProteinDetectionProtocol();

		if (spectrumIdentificationProtocol != null) {
			text = text
					+ "<html><font color=red>Spectrum Identification Protocol</font><BR>";
			for (int i = 0; i < spectrumIdentificationProtocol.size(); i++) {
				Param param = spectrumIdentificationProtocol.get(i)
						.getSearchType();
				if (param != null) {
					CvParam cVParam = param.getCvParam();
					if (cVParam != null) {
						text = text + "<B>Type of search:</B> "
								+ cVParam.getName() + "<BR>";

					}
				}

				Enzymes enzymes = spectrumIdentificationProtocol.get(i)
						.getEnzymes();
				if (enzymes != null) {
					List<Enzyme> enzymesList = enzymes.getEnzyme();
					for (int j = 0; j < enzymesList.size(); j++) {
						Enzyme enzyme = enzymesList.get(j);
						if (enzyme != null) {
							if (enzyme.getEnzymeName() != null) {
								List<CvParam> cvParamList = enzyme
										.getEnzymeName().getCvParam();
								for (int k = 0; k < cvParamList.size(); k++) {
									CvParam cvParam = cvParamList.get(k);
									text = text + "<B>Enzyme:</B> "
											+ cvParam.getName() + "<BR>";

								}
							}
						}

					}

				}

				ModificationParams modificationParams = spectrumIdentificationProtocol
						.get(i).getModificationParams();
				if (modificationParams != null) {
					List<SearchModification> searchModificationList = modificationParams
							.getSearchModification();
					for (int j = 0; j < searchModificationList.size(); j++) {

						SearchModification searchModification = searchModificationList
								.get(j);
						String mod = "";
						if (searchModification.isFixedMod()) {
							mod = "(fixed)";
						} else {
							mod = "(variable)";
						}
						text = text + "<B>Search Modification:</B> Type " + mod
								+ " Residues: "
								+ searchModification.getResidues()
								+ " Mass Delta: "
								+ searchModification.getMassDelta() + "<BR>";

						if (searchModification.getCvParam() != null) {
							if (searchModification.getCvParam().get(0) != null) {
							}
						}

					}
				}

				Tolerance tolerance = spectrumIdentificationProtocol.get(i)
						.getFragmentTolerance();
				if (tolerance != null) {
					String t1 = "";
					String t2 = "";
					List<CvParam> cvParamList = tolerance.getCvParam();
					for (int j = 0; j < cvParamList.size(); j++) {
						CvParam cvParam = cvParamList.get(j);
						if (cvParam.getName().equals(
								"search tolerance plus value")) {
							t1 = t1 + "+" + cvParam.getValue() + " Da ";
						}
						if (cvParam.getName().equals(
								"search tolerance minus value")) {
							t2 = t2 + "-" + cvParam.getValue() + " Da ";
						}
					}
					text = text + "<B>Fragment Tolerance: </B>" + t1 + " / "
							+ t2 + "<BR>";
				}

				Tolerance toleranceParent = spectrumIdentificationProtocol.get(
						i).getParentTolerance();
				if (toleranceParent != null) {
					String t1 = "";
					String t2 = "";
					List<CvParam> cvParamList = toleranceParent.getCvParam();
					for (int j = 0; j < cvParamList.size(); j++) {
						CvParam cvParam = cvParamList.get(j);
						if (cvParam.getName().equals(
								"search tolerance plus value")) {
							t1 = t1 + "+" + cvParam.getValue() + " Da ";
						}
						if (cvParam.getName().equals(
								"search tolerance minus value")) {
							t2 = t2 + "-" + cvParam.getValue() + " Da ";
						}
					}
					text = text + "<B>Parent Fragment Tolerance: </B>" + t1
							+ " / " + t2 + "<BR>";
				}

				String threshold = spectrumIdentificationProtocol.get(i)
						.getThreshold().getCvParam().get(0).getValue();
				if (threshold != null) {
					text = text + "<B>Threshold: </B>" + threshold + "<BR>";
				}
			}

		}
		if (proteinDetectionProtocol != null) {
			text = text
					+ "<html><font color=red>Protein Detection Protocol</font><BR>";
			String threshold = proteinDetectionProtocol.getThreshold()
					.getCvParam().get(0).getValue();
			if (threshold != null) {
				text = text + "<B>Threshold: </B>" + threshold + "<BR>";
			}

		}
		protocolPanel.setProtocolText(text);
		proteoIDViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
