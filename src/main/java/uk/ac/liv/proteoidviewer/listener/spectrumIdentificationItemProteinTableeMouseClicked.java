package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;

public class spectrumIdentificationItemProteinTableeMouseClicked implements
		MouseListener {

	private final ProteoIDViewer proteoIDViewer;
	private final SpectrumSummary spectrumSummary;

	public spectrumIdentificationItemProteinTableeMouseClicked(
			ProteoIDViewer proteoIDViewer,
			SpectrumSummary spectrumSummary) {
		this.proteoIDViewer = proteoIDViewer;
		this.spectrumSummary = spectrumSummary;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		proteoIDViewer.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		String sii_ref = (String) spectrumSummary.getIdentificationItemTable()
				.getValueAt(spectrumSummary.getIdentificationItemTable()
						.getSelectedRow(), 1);

		for (int i = 0; i < spectrumSummary.getIdentificationResultTable()
				.getRowCount(); i++) {
			try {
				String sir_id = (String) spectrumSummary
						.getIdentificationResultTable().getValueAt(i, 0);

				SpectrumIdentificationResult sir = proteoIDViewer.unmarshal(SpectrumIdentificationResult.class, sir_id);
				List<SpectrumIdentificationItem> siiList = sir
						.getSpectrumIdentificationItem();
				for (int j = 0; j < siiList.size(); j++) {
					SpectrumIdentificationItem spectrumIdentificationItem = siiList
							.get(j);
					if (sii_ref.equals(spectrumIdentificationItem.getId())) {

						spectrumSummary.getIdentificationResultTable()
								.setRowSelectionInterval(i, i);
						new spectrumIdentificationResultTableMouseClicked(proteoIDViewer, spectrumSummary).mouseClicked(evt);

						spectrumSummary.getIdentificationItemTable()
								.setRowSelectionInterval(j, j);
						new spectrumIdentificationItemTableMouseClicked(proteoIDViewer, spectrumSummary).mouseClicked(evt);
						break;
					}
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}

		proteoIDViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if (!proteoIDViewer.secondTab) {
			spectrumSummary
					.loadSpectrumIdentificationResultTable(proteoIDViewer.getMzIdentMLUnmarshaller());
			proteoIDViewer.secondTab = true;
		}
		proteoIDViewer.setSelectedIndex(1);
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
