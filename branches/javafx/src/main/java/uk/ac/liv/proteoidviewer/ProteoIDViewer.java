/*
 * ProteoIDViewer.java
 *
 * Created on 15-Dec-2010, 10:34:37
 * 
 * Fawaz Ghali
 * 
 */
package uk.ac.liv.proteoidviewer;

import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;
import uk.ac.liv.proteoidviewer.tabs.ProtocolPanel;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.ebi.jmzidml.model.MzIdentMLObject;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.xml.bind.JAXBException;

import org.proteosuite.model.MzIdentMLFile;

import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;
import uk.ac.ebi.pride.tools.mzml_wrapper.MzMlWrapper;

/**
 *
 * @author Fawaz Ghali
 */
public class ProteoIDViewer extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	private JMzReader jMzReader = null;
	private File fileName = null;
	private String rawType = "";

	private MzIdentMLUnmarshaller mzIdentMLUnmarshaller = null;

	private final GlobalStatisticsPanel globalStatisticsPanel = new GlobalStatisticsPanel(
			this);
	private final PeptideSummary peptideSummary = new PeptideSummary(this);
	private final ProteinDBView proteinDBView = new ProteinDBView(this);
	private final ProtocolPanel protocolPanel = new ProtocolPanel(this);
	private final SpectrumSummary spectrumSummary = new SpectrumSummary(this, proteinDBView);;
	private final ProteinView proteinView = new ProteinView(this, spectrumSummary, globalStatisticsPanel);

	/**
	 * Creates new form ProteoIDViewer
	 */
	public ProteoIDViewer() {
		// Swing init components

		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

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
				ex.printStackTrace();
			}

		}
		return result;
	}

	public void createTables() {
		globalStatisticsPanel.reset();
		proteinDBView.reset();
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
			List<SpectrumIdentificationResult> sirList = spectrumIdentificationList.getSpectrumIdentificationResult();
			for (int j = 0; j < sirList.size(); j++) {
				spectrumIdentificationResultList.add(sirList.get(j));
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
							this,
							"The file is not compatible with the Viewer: different mzIdentMl version",
							"mzIdentMl version",
							JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		jMzReader = null;
		rawType = "";

		if (identDataFile.getParent() != null)
		{
			try {
				File file = identDataFile.getParent().getFile();
				if (file.getAbsolutePath().toLowerCase().endsWith("mgf")) {
					jMzReader = new MgfFile(file);
					rawType = "mgf";
				} else if (file.getAbsolutePath().toLowerCase().endsWith("mzml")) {
					jMzReader = new MzMlWrapper(file);
					rawType = "mzML";
				} else {
					JOptionPane.showMessageDialog(null, file.getName()
							+ " is not supported", "Spectrum file",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (JMzReaderException ex) {
				ex.printStackTrace();
			}
		}

		createTables();
		setSelectedIndex(0);
	}

	public <T extends MzIdentMLObject> T unmarshal(Class<T> clazz, String id) throws JAXBException {
		return mzIdentMLUnmarshaller.unmarshal(clazz, id);
	}

	public boolean isRawAvailable() {
		if (jMzReader != null)
			return true;
		
		return false;
	}

	public Spectrum getRawSpectrum(String id) throws JMzReaderException {
		if (rawType.equals("mgf")) {
			String spectrumIndex = id.substring(6);
			Integer index1 = Integer.valueOf(spectrumIndex) + 1;
			return jMzReader.getSpectrumById(index1.toString());
		}
		
		return jMzReader.getSpectrumById(id);
	}

	public File getFileName() {
		return fileName;
	}
}