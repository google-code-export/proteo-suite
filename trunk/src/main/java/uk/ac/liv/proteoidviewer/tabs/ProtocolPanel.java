package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

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
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationProtocol;
import uk.ac.ebi.jmzidml.model.mzidml.Tolerance;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;
import uk.ac.liv.proteoidviewer.listener.LazyLoadingComponentListener;

public class ProtocolPanel extends JPanel implements LazyLoading {
	private static final long serialVersionUID = 1L;
	private final JTextPane protocolText = new JTextPane();
	private final ProteoIDViewer proteoIDViewer;

	public ProtocolPanel(ProteoIDViewer proteoIDViewer) {
		this.proteoIDViewer = proteoIDViewer;
		addComponentListener(new LazyLoadingComponentListener());
		setLayout(new BorderLayout());
		protocolText.setContentType("text/html");
		protocolText.setEditable(false);

		setBorder(BorderFactory
				.createTitledBorder("Summary"));
		add(new JScrollPane(protocolText),
				BorderLayout.CENTER);
	}

	public void setProtocolText(String string) {
		protocolText.setText(string);		
	}

	public void load() {
		setProtocolText("");
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Map<String, AnalysisSoftware> analysisSoftwareHashMap = new HashMap<>();

		Iterator<AnalysisSoftware> iterAnalysisSoftware = proteoIDViewer
				.getMzIdentMLUnmarshaller().unmarshalCollectionFromXpath(
						MzIdentMLElement.AnalysisSoftware);
		while (iterAnalysisSoftware.hasNext()) {
			AnalysisSoftware analysisSoftware = iterAnalysisSoftware.next();
			analysisSoftwareHashMap.put(analysisSoftware.getId(),
					analysisSoftware);
		}

		AnalysisProtocolCollection analysisProtocolCollection = proteoIDViewer
				.getMzIdentMLUnmarshaller().unmarshal(
						MzIdentMLElement.AnalysisProtocolCollection);

		List<SpectrumIdentificationProtocol> spectrumIdentificationProtocol = analysisProtocolCollection
				.getSpectrumIdentificationProtocol();
		ProteinDetectionProtocol proteinDetectionProtocol = analysisProtocolCollection
				.getProteinDetectionProtocol();

		String text = "";
		if (spectrumIdentificationProtocol != null) {
			text += "<html><font color=red>Spectrum Identification Protocol</font><BR>";
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
						if (searchModification.isFixedMod())
							mod = "(fixed)";
						else
							mod = "(variable)";

						text += "<B>Search Modification:</B> Type " + mod
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
							t1 += "+" + cvParam.getValue() + " Da ";
						}
						if (cvParam.getName().equals(
								"search tolerance minus value")) {
							t2 += "-" + cvParam.getValue() + " Da ";
						}
					}
					text += "<B>Fragment Tolerance: </B>" + t1 + " / " + t2
							+ "<BR>";
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
							t1 += "+" + cvParam.getValue() + " Da ";
						}
						if (cvParam.getName().equals(
								"search tolerance minus value")) {
							t2 += "-" + cvParam.getValue() + " Da ";
						}
					}
					text += "<B>Parent Fragment Tolerance: </B>" + t1 + " / "
							+ t2 + "<BR>";
				}

				String threshold = spectrumIdentificationProtocol.get(i)
						.getThreshold().getCvParam().get(0).getValue();
				if (threshold != null) {
					text += "<B>Threshold: </B>" + threshold + "<BR>";
				}
			}

		}
		if (proteinDetectionProtocol != null) {
			text = "<html><font color=red>Protein Detection Protocol</font><BR>";
			String threshold = proteinDetectionProtocol.getThreshold()
					.getCvParam().get(0).getValue();
			if (threshold != null) {
				text += "<B>Threshold: </B>" + threshold + "<BR>";
			}

		}
		setProtocolText(text);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
