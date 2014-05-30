package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;

public class proteinDetectionHypothesisTableMouseClicked implements
		MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final JTable proteinDetectionHypothesisTable;
	private final ProteinView proteinView;
	private final JLabel jScientificNameValueLabel;
	private final JEditorPane jProteinDescriptionEditorPane;
	private final JTextPane jProteinSequenceTextPane;

	public proteinDetectionHypothesisTableMouseClicked(
			ProteoIDViewer proteoIDViewer,
			JTable proteinDetectionHypothesisTable,
			ProteinView proteinView,
			JLabel jScientificNameValueLabel,
			JEditorPane jProteinDescriptionEditorPane,
			JTextPane jProteinSequenceTextPane) {
		this.proteoIDViewer = proteoIDViewer;
		this.proteinDetectionHypothesisTable = proteinDetectionHypothesisTable;
		this.proteinView = proteinView;
		this.jScientificNameValueLabel = jScientificNameValueLabel;
		this.jProteinDescriptionEditorPane = jProteinDescriptionEditorPane;
		this.jProteinSequenceTextPane = jProteinSequenceTextPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = proteinDetectionHypothesisTable.getSelectedRow();
		if (row == -1)
			return;

		SpectrumIdentificationItem spectrumIdentificationItem2 = null;
		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// row =
		// spectrumIdentificationItemTable.convertRowIndexToModel(row);
		try {
			proteinView.getIdentificationItemTable().removeAll();
			// TODO: Disabled - Andrew
			// spectrumIdentificationItemProteinViewTable.scrollRowToVisible(0);
			// row =
			// proteinDetectionHypothesisTable.convertRowIndexToModel(row);
			ProteinDetectionHypothesis proteinDetectionHypothesis = proteoIDViewer.mzIdentMLUnmarshaller
					.unmarshal(ProteinDetectionHypothesis.class,
							(String) proteinDetectionHypothesisTable.getModel()
									.getValueAt(row, 0));
			// System.out.println((String)
			// proteinDetectionHypothesisTable.getModel().getValueAt(row,
			// 0));
			DBSequence dBSequence = proteoIDViewer.mzIdentMLUnmarshaller.unmarshal(
					DBSequence.class,
					proteinDetectionHypothesis.getDBSequenceRef());
			// System.out.println(proteinDetectionHypothesis.getDBSequenceRef());
			// System.out.println(dBSequence.getAccession());
			List<PeptideHypothesis> peptideHypothesisList = proteinDetectionHypothesis
					.getPeptideHypothesis();
			String proteinSequence = "";
			String protein_description = "";
			if (dBSequence != null) {
				List<CvParam> cvParamListDBSequence = dBSequence.getCvParam();
				String scientific_name = null;

				for (int j = 0; j < cvParamListDBSequence.size(); j++) {
					CvParam cvParam = cvParamListDBSequence.get(j);
					if (cvParam.getName().equals("taxonomy: scientific name")) {
						scientific_name = cvParam.getValue();
					}
					if (cvParam.getName().equals("protein description")) {
						protein_description = cvParam.getValue();
					}
				}
				jScientificNameValueLabel.setText(scientific_name);
				jProteinDescriptionEditorPane.setText(protein_description);
				proteinSequence = dBSequence.getSeq();

				for (int i = 0; i < peptideHypothesisList.size(); i++) {
					PeptideHypothesis peptideHypothesis = peptideHypothesisList
							.get(i);
					for (int j = 0; j < peptideHypothesis
							.getSpectrumIdentificationItemRef().size(); j++) {
						spectrumIdentificationItem2 = proteoIDViewer.mzIdentMLUnmarshaller
								.unmarshal(
										SpectrumIdentificationItem.class,
										peptideHypothesis
												.getSpectrumIdentificationItemRef()
												.get(j)
												.getSpectrumIdentificationItemRef());
					}
					List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem2
							.getPeptideEvidenceRef();

					for (int k = 0; k < peptideEvidenceRefList.size(); k++) {
						PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
								.get(k);
						PeptideEvidence peptideEvidence = proteoIDViewer.mzIdentMLUnmarshaller
								.unmarshal(PeptideEvidence.class,
										peptideEvidenceRef
												.getPeptideEvidenceRef());
						if (peptideEvidence.getDBSequenceRef().equals(
								proteinDetectionHypothesis.getDBSequenceRef())) {
							Peptide peptide = proteoIDViewer.mzIdentMLUnmarshaller
									.unmarshal(Peptide.class,
											spectrumIdentificationItem2
													.getPeptideRef());
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
									combine = combine + " on residues: "
											+ residues;
								}
								if (location != null) {
									combine = combine + " at location: "
											+ location;
								}

								((DefaultTableModel) proteinView.getIdentificationItemTable()
										.getModel()).addRow(new String[] {
										peptide.getPeptideSequence(),
										spectrumIdentificationItem2.getId(),
										combine });
								List<CvParam> cvParamListSpectrumIdentificationItem = spectrumIdentificationItem2
										.getCvParam();

								for (int s = 0; s < cvParamListSpectrumIdentificationItem
										.size(); s++) {
									CvParam cvParam = cvParamListSpectrumIdentificationItem
											.get(s);
									String newCol = cvParam.getName();
									if (newCol.equals("mascot:score")) {
										((DefaultTableModel) proteinView.getIdentificationItemTable()
												.getModel())
												.setValueAt(
														Double.valueOf(cvParam
																.getValue()),
														((DefaultTableModel) proteinView.getIdentificationItemTable()
																.getModel())
																.getRowCount() - 1,
														3);
									}
									String accession = cvParam.getAccession();
									if (accession.equals("MS:1001330")
											|| accession.equals("MS:1001172")
											|| accession.equals("MS:1001159")
											|| accession.equals("MS:1001328")) {
										((DefaultTableModel) proteinView.getIdentificationItemTable()
												.getModel())
												.setValueAt(
														Double.valueOf(cvParam
																.getValue()),
														((DefaultTableModel) proteinView.getIdentificationItemTable()
																.getModel())
																.getRowCount() - 1,
														4);
									}

								}
								((DefaultTableModel) proteinView.getIdentificationItemTable()
										.getModel())
										.setValueAt(
												spectrumIdentificationItem2
														.isPassThreshold(),
												((DefaultTableModel) proteinView.getIdentificationItemTable()
														.getModel())
														.getRowCount() - 1, 5);
							}

						}

					}
				}
			}
			if (proteinSequence != null) {
				StringBuilder sb = new StringBuilder(proteinSequence);
				StringBuilder sb_new = new StringBuilder();
				int i = 0;

				for (int j = 0; j < sb.length(); j++) {

					if (i % 60 == 0 && i != 0) {
						sb_new.append("<BR>");
					}
					i = i + 1;
					sb_new.append(sb.charAt(j));
				}
				jProteinSequenceTextPane.setText("<FONT FACE=\"Courier New\">"
						+ sb_new.toString() + "</FONT>");

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
