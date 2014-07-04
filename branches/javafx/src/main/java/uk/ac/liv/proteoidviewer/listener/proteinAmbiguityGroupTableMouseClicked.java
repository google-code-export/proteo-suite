package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;

public class proteinAmbiguityGroupTableMouseClicked implements MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final JTextArea jProteinSequenceTextPane;
	private final JLabel jScientificNameValueLabel;

	private final ProteinView proteinView;

	public proteinAmbiguityGroupTableMouseClicked(
			ProteoIDViewer proteoIDViewer, JTextArea jProteinSequenceTextPane,
			JLabel jScientificNameValueLabel,
			ProteinView proteinView) {
		this.proteoIDViewer = proteoIDViewer;
		this.jProteinSequenceTextPane = jProteinSequenceTextPane;
		this.jScientificNameValueLabel = jScientificNameValueLabel;
		this.proteinView = proteinView;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable proteinAmbiguityGroupTable = (JTable) e.getSource();
		jProteinSequenceTextPane.setText("");
		jScientificNameValueLabel.setText("");
		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int row = proteinAmbiguityGroupTable.getSelectedRow();

		if (row != -1) {
			row = proteinAmbiguityGroupTable.convertRowIndexToModel(row);
			try {
				((DefaultTableModel) proteinView.getDetectionHypothesisTable()
						.getModel()).setRowCount(0);
				((DefaultTableModel) proteinView.getIdentificationItemTable()
				.getModel()).setRowCount(0);
				// TODO: Disabled - Andrew
				// proteinDetectionHypothesisTable.scrollRowToVisible(0);
				String pag_id = (String) proteinAmbiguityGroupTable.getModel()
						.getValueAt(row, 0);

				ProteinAmbiguityGroup proteinAmbiguityGroup = proteoIDViewer
						.unmarshal(ProteinAmbiguityGroup.class, pag_id);

				List<ProteinDetectionHypothesis> proteinDetectionHypothesisList = proteinAmbiguityGroup
						.getProteinDetectionHypothesis();
				if (proteinDetectionHypothesisList.size() > 0) {
					for (int i = 0; i < proteinDetectionHypothesisList.size(); i++) {

						ProteinDetectionHypothesis proteinDetectionHypothesis = proteinDetectionHypothesisList
								.get(i);
						DBSequence dBSequence = proteoIDViewer.unmarshal(
								DBSequence.class,
								proteinDetectionHypothesis.getDBSequenceRef());
						boolean isDecoy = proteinView
								.checkIfProteinDetectionHypothesisIsDecoy(
										proteinDetectionHypothesis,
										proteoIDViewer
												.getMzIdentMLUnmarshaller());
						List<CvParam> cvParamList = proteinDetectionHypothesis
								.getCvParam();
						String score = " ";
						String number_peptide = " ";
						for (int j = 0; j < cvParamList.size(); j++) {
							CvParam cvParam = cvParamList.get(j);
							if (cvParam.getName().contains("score")) {
								score = cvParam.getValue();
							}

						}
						String dBSequenceAccession = "";
						if (dBSequence != null) {
							dBSequenceAccession = dBSequence.getAccession();
						}
						if (proteinDetectionHypothesis.getPeptideHypothesis() != null) {
							number_peptide = String
									.valueOf(proteinDetectionHypothesis
											.getPeptideHypothesis().size());
						}

						((DefaultTableModel) proteinView
								.getDetectionHypothesisTable().getModel())
								.addRow(new Object[] {
										proteinDetectionHypothesis.getId(),
										dBSequenceAccession,
										IdViewerUtils.roundTwoDecimals(Double
												.valueOf(score).doubleValue()),
										"",
										Integer.valueOf(number_peptide),
										isDecoy,
										proteinDetectionHypothesis
												.isPassThreshold() });
					}
				}
			} catch (JAXBException ex) {

				ex.printStackTrace();
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
