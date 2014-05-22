package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;

public class siiComboBoxActionPerformed implements ActionListener {
	
	private final JComboBox<String> siiComboBox;
	private final List<SpectrumIdentificationItem> sIIListPassThreshold;
	private final ProteoIDViewer proteoIDViewer;

	public siiComboBoxActionPerformed(ProteoIDViewer proteoIDViewer,
			JComboBox<String> siiComboBox,
			List<SpectrumIdentificationItem> sIIListPassThreshold) {
		this.proteoIDViewer = proteoIDViewer;
		this.siiComboBox = siiComboBox;
		this.sIIListPassThreshold = sIIListPassThreshold;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (siiComboBox.getSelectedIndex() != -1) {
			proteoIDViewer.loadSpectrumIdentificationList(sIIListPassThreshold);
		}
	}

}
