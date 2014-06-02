package uk.ac.liv.proteoidviewer.listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
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
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;

import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;
import com.compomics.util.gui.spectrum.SpectrumPanel;

public class spectrumIdentificationItemTableMouseClicked implements
		MouseListener {
	private static final List<String> filterListIon = new ArrayList<>();
	private static final List<String> filterListCharge = new ArrayList<>();
	
	
	private final SpectrumSummary spectrumSummary;
	private final ProteoIDViewer proteoIDViewer;

	public spectrumIdentificationItemTableMouseClicked(
			ProteoIDViewer proteoIDViewer, SpectrumSummary spectrumSummary) {
		this.proteoIDViewer = proteoIDViewer;
		this.spectrumSummary = spectrumSummary;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = spectrumSummary.getIdentificationItemTable().getSelectedRow();
		if (row == -1)
			return;

		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		spectrumSummary.getFragmentationTable().removeAll();
		spectrumSummary.getEvidenceTable().removeAll();

		// TODO: Disabled - Andrew
		// fragmentationTable.scrollRowToVisible(0);
		SpectrumIdentificationItem spectrumIdentificationItem = spectrumSummary.spectrumIdentificationItemListForSpecificResult
				.get(row);

		if (spectrumIdentificationItem != null) {
			List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
					.getPeptideEvidenceRef();
			if (peptideEvidenceRefList != null) {
				for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
					try {
						PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
								.get(i);

						PeptideEvidence peptideEvidence = proteoIDViewer.unmarshal(PeptideEvidence.class,
										peptideEvidenceRef
												.getPeptideEvidenceRef());

						((DefaultTableModel) spectrumSummary.getEvidenceTable()
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
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
			}
		}

		Fragmentation fragmentation = spectrumIdentificationItem
				.getFragmentation();

		if (fragmentation != null) {
			List<IonType> ionTypeList = fragmentation.getIonType();
			if (ionTypeList != null) {
				for (int i = 0; i < ionTypeList.size(); i++) {
					IonType ionType = ionTypeList.get(i);
					CvParam cvParam = ionType.getCvParam();
					if (!filterListIon.contains(cvParam.getName())) {
						filterListIon.add(cvParam.getName());
					}
					if (!filterListCharge.contains(String.valueOf(ionType
							.getCharge()))) {
						filterListCharge
								.add(String.valueOf(ionType.getCharge()));
					}
					List<Float> m_mz = ionType.getFragmentArray().get(0)
							.getValues();
					List<Float> m_intensity = ionType.getFragmentArray().get(1)
							.getValues();
					List<Float> m_error = ionType.getFragmentArray().get(2)
							.getValues();
					String type = cvParam.getName();
					type = type.replaceAll("param: ", "");
					type = type.replaceAll(" ion", "");

					if (m_mz != null && !m_mz.isEmpty()) {
						for (int j = 0; j < m_mz.size(); j++) {
							((DefaultTableModel) spectrumSummary
									.getFragmentationTable().getModel())
									.addRow(new Object[] { m_mz.get(j),
											m_intensity.get(j), m_error.get(j),
											type + ionType.getIndex().get(j),
											ionType.getCharge() });

						}
					}
				}
			}

			double[] mzValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			double[] intensityValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			double[] m_errorValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			final List<SpectrumAnnotation> peakAnnotation = new ArrayList<>();
			for (int k = 0; k < spectrumSummary.getFragmentationTable()
					.getModel().getRowCount(); k++) {
				mzValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 0));

				intensityValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 1));
				m_errorValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 2));

				String type = (String) spectrumSummary.getFragmentationTable()
						.getModel().getValueAt(k, 3);
				type = type.replaceFirst("frag:", "");
				type = type.replaceFirst("ion", "");
				type = type.replaceFirst("internal", "");

				peakAnnotation.add(new DefaultSpectrumAnnotation(
						mzValuesAsDouble[k], m_errorValuesAsDouble[k],
						Color.blue, type));
			}
			spectrumSummary.getGraph().removeAll();

			spectrumSummary.getGraph().validate();
			spectrumSummary.getGraph().repaint();
			if (proteoIDViewer.jmzreader != null) {
				try {
					int row1 = spectrumSummary.getIdentificationResultTable()
							.getSelectedRow();
					String sir_id = (String) spectrumSummary
							.getIdentificationResultTable().getModel()
							.getValueAt(row1, 0);
					// System.out.println(sir_id);
					SpectrumIdentificationResult spectrumIdentificationResult = proteoIDViewer.unmarshal(SpectrumIdentificationResult.class,
									sir_id);
					Spectrum spectrum = null;
					String spectrumID = spectrumIdentificationResult
							.getSpectrumID();
					if (proteoIDViewer.sourceFile.equals("mgf")) {
						String spectrumIndex = spectrumID.substring(6);
						Integer index1 = Integer.valueOf(spectrumIndex) + 1;
						spectrum = proteoIDViewer.jmzreader.getSpectrumById(index1.toString());
					} else if (proteoIDViewer.sourceFile.equals("mzML")) {
						spectrum = proteoIDViewer.jmzreader.getSpectrumById(spectrumID);
					}

					List<Double> mzValues;
					if (spectrum.getPeakList() != null) {
						mzValues = new ArrayList<Double>(spectrum.getPeakList()
								.keySet());
					} else {
						mzValues = Collections.emptyList();
					}

					double[] mz = new double[mzValues.size()];
					double[] intensities = new double[mzValues.size()];

					int index = 0;
					for (double mzValue : mzValues) {
						mz[index] = mzValue;
						intensities[index] = spectrum.getPeakList()
								.get(mzValue);
						index++;
					}
					SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
							intensities,
							spectrumIdentificationItem
									.getExperimentalMassToCharge(),
							String.valueOf(spectrumIdentificationItem
									.getChargeState()),
							spectrumIdentificationItem.getName());
					spectrumPanel.setAnnotations(peakAnnotation);
					spectrumSummary.getGraph().setLayout(new BorderLayout());
					spectrumSummary.getGraph().setLayout(
							new BoxLayout(spectrumSummary.getGraph(),
									BoxLayout.LINE_AXIS));
					spectrumSummary.getGraph().add(spectrumPanel);
					spectrumSummary.getGraph().validate();
					spectrumSummary.getGraph().repaint();
				} catch (JMzReaderException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (JAXBException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				}

			} else if (mzValuesAsDouble.length > 0) {
				SpectrumPanel spectrumPanel = new SpectrumPanel(
						mzValuesAsDouble, intensityValuesAsDouble,
						spectrumIdentificationItem
								.getExperimentalMassToCharge(),
						String.valueOf(spectrumIdentificationItem
								.getChargeState()),
						spectrumIdentificationItem.getName());

				spectrumPanel.setAnnotations(peakAnnotation);

				spectrumSummary.getGraph().setLayout(new BorderLayout());
				spectrumSummary.getGraph().setLayout(
						new BoxLayout(spectrumSummary.getGraph(),
								BoxLayout.LINE_AXIS));
				spectrumSummary.getGraph().add(spectrumPanel);
				spectrumSummary.getGraph().validate();
				spectrumSummary.getGraph().repaint();
				proteoIDViewer.repaint();
			}
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
