package uk.ac.liv.proteoidviewer.listener;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableModel;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.SpectrumPanel;

public class spectrumIdentificationResultTableMouseClicked implements
		MouseListener {
	private final SpectrumSummary spectrumSummary;
	private final ProteoIDViewer proteoIDViewer;

	public spectrumIdentificationResultTableMouseClicked(
			ProteoIDViewer proteoIDViewer, SpectrumSummary spectrumSummary) {
		this.proteoIDViewer = proteoIDViewer;
		this.spectrumSummary = spectrumSummary;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = spectrumSummary.getIdentificationResultTable()
				.getSelectedRow();
		if (row == -1)
			return;

		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// row =
		// spectrumIdentificationResultTable.convertRowIndexToModel(row);
		try {
			((DefaultTableModel) spectrumSummary.getIdentificationItemTable().getModel()).setNumRows(0);
			((DefaultTableModel) spectrumSummary.getEvidenceTable().getModel()).setNumRows(0);
			((DefaultTableModel) spectrumSummary.getFragmentationTable().getModel()).setNumRows(0);
			spectrumSummary.getGraph().removeAll();

			spectrumSummary.getGraph().validate();
			spectrumSummary.getGraph().repaint();
			// TODO: Disabled - Andrew
			// spectrumIdentificationItemTable.scrollRowToVisible(0);
			String sir_id = (String) spectrumSummary
					.getIdentificationResultTable().getModel()
					.getValueAt(row, 0);
			SpectrumIdentificationResult spectrumIdentificationResult = proteoIDViewer.unmarshal(SpectrumIdentificationResult.class, sir_id);
			if (proteoIDViewer.jmzreader != null) {

				Spectrum spectrum = null;
				String spectrumID = spectrumIdentificationResult
						.getSpectrumID();
				if (proteoIDViewer.sourceFile.equals("mgf")) {
					String spectrumIndex = spectrumID.substring(6);
					Integer index1 = Integer.valueOf(spectrumIndex) + 1;
					spectrum = proteoIDViewer.jmzreader.getSpectrumById(index1.toString());
				}
				if (proteoIDViewer.sourceFile.equals("mzML")) {
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
				final List<SpectrumAnnotation> peakAnnotation = new ArrayList<>();
				for (double mzValue : mzValues) {
					mz[index] = mzValue;
					intensities[index] = spectrum.getPeakList().get(mzValue);

					// peakAnnotation.add(
					// new DefaultSpectrumAnnotation(
					// mz[index],
					// intensities[index],
					// Color.blue,
					// ""));
					index++;
				}

				SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
						intensities, 0.0, "", "");
				spectrumPanel.setAnnotations(peakAnnotation);
				spectrumSummary.getGraph().setLayout(new BorderLayout());
				spectrumSummary.getGraph().setLayout(
						new BoxLayout(spectrumSummary.getGraph(),
								BoxLayout.LINE_AXIS));
				spectrumSummary.getGraph().add(spectrumPanel);
				spectrumSummary.getGraph().validate();
				spectrumSummary.getGraph().repaint();
			}

			spectrumSummary.spectrumIdentificationItemListForSpecificResult = spectrumIdentificationResult
					.getSpectrumIdentificationItem();
			if (spectrumSummary.spectrumIdentificationItemListForSpecificResult.size() > 0) {

				for (int i = 0; i < spectrumSummary.spectrumIdentificationItemListForSpecificResult
						.size(); i++) {
					try {
						SpectrumIdentificationItem spectrumIdentificationItem = spectrumSummary.spectrumIdentificationItemListForSpecificResult
								.get(i);
						boolean isDecoy = proteoIDViewer.checkIfSpectrumIdentificationItemIsDecoy(
								spectrumIdentificationItem,
								proteoIDViewer.getMzIdentMLUnmarshaller());

						Peptide peptide = proteoIDViewer.unmarshal(Peptide.class, spectrumIdentificationItem.getPeptideRef());
						if (peptide != null) {
							List<Modification> modificationList = peptide
									.getModification();
							Modification modification;
							String residues = null;
							Integer location = null;
							String modificationName = null;
							CvParam modificationCvParam;
							String combine = null;
							if (modificationList.size() > 0) {
								modification = modificationList.get(0);
								location = modification.getLocation();
								if (modification.getResidues().size() > 0) {
									residues = modification.getResidues()
											.get(0);
								}
								List<CvParam> modificationCvParamList = modification
										.getCvParam();
								if (modificationCvParamList.size() > 0) {
									modificationCvParam = modificationCvParamList
											.get(0);
									modificationName = modificationCvParam
											.getName();
								}
							}
							if (modificationName != null) {
								combine = modificationName;
							}
							if (residues != null) {
								combine = combine + " on residues: " + residues;
							}
							if (location != null) {
								combine = combine + " at location: " + location;
							}
							double calculatedMassToCharge = 0;
							if (spectrumIdentificationItem
									.getCalculatedMassToCharge() != null) {
								calculatedMassToCharge = spectrumIdentificationItem
										.getCalculatedMassToCharge()
										.doubleValue();
							}
							((DefaultTableModel) spectrumSummary
									.getIdentificationItemTable().getModel())
									.addRow(new Object[] {
											spectrumIdentificationItem.getId(),
											peptide.getPeptideSequence(),
											combine,
											IdViewerUtils
													.roundTwoDecimals(calculatedMassToCharge),
											IdViewerUtils
													.roundTwoDecimals(spectrumIdentificationItem
															.getExperimentalMassToCharge()),
											Integer.valueOf(spectrumIdentificationItem
													.getRank()),
											isDecoy,
											spectrumIdentificationItem
													.isPassThreshold() });

							List<CvParam> cvParamListspectrumIdentificationItem = spectrumIdentificationItem
									.getCvParam();

							for (int s = 0; s < cvParamListspectrumIdentificationItem
									.size(); s++) {
								CvParam cvParam = cvParamListspectrumIdentificationItem
										.get(s);
								String accession = cvParam.getAccession();

								if (cvParam.getName().equals(
										"peptide unique to one protein")) {
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													1,
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								} else if (accession.equals("MS:1001330")
										|| accession.equals("MS:1001172")
										|| accession.equals("MS:1001159")
										|| accession.equals("MS:1001328")) {
									// ((DefaultTableModel)
									// spectrumIdentificationItemTable.getModel()).setValueAt(roundScientificNumbers(Double.valueOf(cvParam.getValue()).doubleValue()),
									// ((DefaultTableModel)
									// spectrumIdentificationItemTable.getModel()).getRowCount()
									// - 1, 8 + s);
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													cvParam.getValue(),
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								} else {
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													cvParam.getValue(),
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								}
							}

						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
