package uk.ac.liv.proteoidviewer.listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Fragmentation;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;

import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;
import com.compomics.util.gui.spectrum.SpectrumPanel;

public class spectrumIdentificationItemTablePeptideViewMouseClicked implements
		MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final PeptideSummary peptideSummary;
	private final List<String> filterListIon1;
	private final List<String> filterListCharge1;
	private final JPanel jGraph1;
	private final Map<String, String> siiSirMap;

	public spectrumIdentificationItemTablePeptideViewMouseClicked(
			ProteoIDViewer proteoIDViewer, PeptideSummary peptideSummary, 
			List<String> filterListIon1, 
			List<String> filterListCharge1, JPanel jGraph1, Map<String, String> siiSirMap) {
		this.proteoIDViewer = proteoIDViewer;
		this.filterListIon1 = filterListIon1;
		this.filterListCharge1 = filterListCharge1;
		this.jGraph1 = jGraph1;
		this.siiSirMap = siiSirMap;
		this.peptideSummary = peptideSummary;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = peptideSummary.getIdentificationTable().getSelectedRow();
		if (row == -1)
			return;

		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		peptideSummary.getFragmentationTable().removeAll();
		peptideSummary.getEvidenceTable().removeAll();
		try {

			// TODO: Disabled - Andrew
			// fragmentationTablePeptideView.scrollRowToVisible(0);
			SpectrumIdentificationItem spectrumIdentificationItem = proteoIDViewer.mzIdentMLUnmarshaller
					.unmarshal(SpectrumIdentificationItem.class,
							(String) peptideSummary.getIdentificationTable()
									.getValueAt(row, 0));

			if (spectrumIdentificationItem != null) {
				List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
						.getPeptideEvidenceRef();
				if (peptideEvidenceRefList != null) {
					for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
						try {
							PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
									.get(i);

							PeptideEvidence peptideEvidence = proteoIDViewer.mzIdentMLUnmarshaller
									.unmarshal(PeptideEvidence.class,
											peptideEvidenceRef
													.getPeptideEvidenceRef());

							((DefaultTableModel) peptideSummary.getEvidenceTable()
									.getModel()).addRow(new Object[] {
									peptideEvidence.getStart(),
									peptideEvidence.getEnd(),
									peptideEvidence.getPre(),
									peptideEvidence.getPost(),
									peptideEvidence.isIsDecoy(),
									peptideEvidence.getPeptideRef(),
									peptideEvidence.getDBSequenceRef()
							// "<html><a href=>"
							// +peptideEvidence.getDBSequenceRef()+"</a>"
									});
						} catch (JAXBException ex) {
							Logger.getLogger(ProteoIDViewer.class.getName())
									.log(Level.SEVERE, null, ex);
						}
					}
				}
			}

			Fragmentation fragmentation = spectrumIdentificationItem
					.getFragmentation();

			if (fragmentation != null) {
				List<IonType> ionTypeList1 = fragmentation.getIonType();
				if (ionTypeList1 != null) {
					for (int i = 0; i < ionTypeList1.size(); i++) {
						IonType ionType = ionTypeList1.get(i);
						CvParam cvParam = ionType.getCvParam();
						if (!filterListIon1.contains(cvParam.getName())) {
							filterListIon1.add(cvParam.getName());
						}
						if (!filterListCharge1.contains(String.valueOf(ionType
								.getCharge()))) {
							filterListCharge1.add(String.valueOf(ionType
									.getCharge()));
						}
						List<Float> m_mz = ionType.getFragmentArray().get(0)
								.getValues();
						List<Float> m_intensity = ionType.getFragmentArray()
								.get(1).getValues();
						List<Float> m_error = ionType.getFragmentArray().get(2)
								.getValues();
						String type = cvParam.getName();
						type = type.replaceAll(" ion", "");
						type = type.replaceAll("param: ", "");

						if (m_mz != null && !m_mz.isEmpty()) {
							for (int j = 0; j < m_mz.size(); j++) {
								((DefaultTableModel) peptideSummary.getFragmentationTable()
										.getModel()).addRow(new Object[] {
										m_mz.get(j), m_intensity.get(j),
										m_error.get(j),
										type + ionType.getIndex().get(j),
										ionType.getCharge() });

							}
						}
					}
				}
				double[] mzValuesAsDouble = new double[peptideSummary.getFragmentationTable()
						.getModel().getRowCount()];
				double[] intensityValuesAsDouble = new double[peptideSummary.getFragmentationTable()
						.getModel().getRowCount()];
				double[] m_errorValuesAsDouble = new double[peptideSummary.getFragmentationTable()
						.getModel().getRowCount()];
				List<SpectrumAnnotation> peakAnnotation1 = new ArrayList<>();
				for (int k = 0; k < peptideSummary.getFragmentationTable().getModel()
						.getRowCount(); k++) {
					mzValuesAsDouble[k] = (double) (peptideSummary.getFragmentationTable()
							.getModel().getValueAt(k, 0));

					intensityValuesAsDouble[k] = (double) (peptideSummary.getFragmentationTable()
							.getModel().getValueAt(k, 1));
					m_errorValuesAsDouble[k] = (double) (peptideSummary.getFragmentationTable()
							.getModel().getValueAt(k, 2));

					String type = (String) peptideSummary.getFragmentationTable()
							.getModel().getValueAt(k, 3);
					type = type.replaceFirst("frag:", "");
					type = type.replaceFirst("ion", "");
					type = type.replaceFirst("internal", "");

					peakAnnotation1.add(new DefaultSpectrumAnnotation(
							mzValuesAsDouble[k], m_errorValuesAsDouble[k],
							Color.blue, type));
				}

				jGraph1.removeAll();
				if (proteoIDViewer.jmzreader != null) {
					try {

						String sir_id = siiSirMap
								.get((String) peptideSummary.getIdentificationTable()
										.getValueAt(row, 0));
						SpectrumIdentificationResult spectrumIdentificationResult = proteoIDViewer.mzIdentMLUnmarshaller
								.unmarshal(SpectrumIdentificationResult.class,
										sir_id);
						Spectrum spectrum = null;
						String spectrumID = spectrumIdentificationResult
								.getSpectrumID();
						if (proteoIDViewer.sourceFile.equals("mgf")) {
							String spectrumIndex = spectrumID.substring(6);
							Integer index1 = Integer.valueOf(spectrumIndex) + 1;
							spectrum = proteoIDViewer.jmzreader.getSpectrumById(index1
									.toString());
						}
						if (proteoIDViewer.sourceFile.equals("mzML")) {
							spectrum = proteoIDViewer.jmzreader.getSpectrumById(spectrumID);
						}

						List<Double> mzValues;
						if (spectrum.getPeakList() != null) {
							mzValues = new ArrayList<Double>(spectrum
									.getPeakList().keySet());
						} else {
							mzValues = Collections.emptyList();
						}

						double[] mz = new double[mzValues.size()];
						double[] intensities = new double[mzValues.size()];

						int index = 0;
						for (double mzValue : mzValues) {
							mz[index] = mzValue;
							intensities[index] = spectrum.getPeakList().get(
									mzValue);
							index++;
						}
						SpectrumPanel spectrumPanel1 = new SpectrumPanel(mz,
								intensities,
								spectrumIdentificationItem
										.getExperimentalMassToCharge(),
								String.valueOf(spectrumIdentificationItem
										.getChargeState()),
								spectrumIdentificationItem.getName());
						spectrumPanel1.setAnnotations(peakAnnotation1);
						jGraph1.setLayout(new BorderLayout());
						jGraph1.setLayout(new BoxLayout(jGraph1,
								BoxLayout.LINE_AXIS));
						jGraph1.add(spectrumPanel1);
						jGraph1.validate();
						jGraph1.repaint();
					} catch (JMzReaderException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					} catch (JAXBException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				} else if (mzValuesAsDouble.length > 0) {
					SpectrumPanel spectrumPanel1 = new SpectrumPanel(
							mzValuesAsDouble, intensityValuesAsDouble,
							spectrumIdentificationItem
									.getExperimentalMassToCharge(),
							String.valueOf(spectrumIdentificationItem
									.getChargeState()),
							spectrumIdentificationItem.getName());

					spectrumPanel1.setAnnotations(peakAnnotation1);

					jGraph1.setLayout(new BorderLayout());
					jGraph1.setLayout(new BoxLayout(jGraph1,
							BoxLayout.LINE_AXIS));
					jGraph1.add(spectrumPanel1);
					jGraph1.validate();
					jGraph1.repaint();
					proteoIDViewer.repaint();
				}
			}
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

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
