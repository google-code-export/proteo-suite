package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;

public class siiComboBoxActionPerformed implements ActionListener {
	private final ProteoIDViewer proteoIDViewer;
	private final GlobalStatisticsPanel globalStatisticsPanel;

	public siiComboBoxActionPerformed(ProteoIDViewer proteoIDViewer,
			GlobalStatisticsPanel globalStatisticsPanel) {
		this.proteoIDViewer = proteoIDViewer;
		this.globalStatisticsPanel = globalStatisticsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> siiComboBox = (JComboBox<String>) e.getSource();
		
		if (siiComboBox.getSelectedIndex() == -1)
			return;

		globalStatisticsPanel.loadSpectrumIdentificationList(
				proteoIDViewer.getMzIdentMLUnmarshaller());

	}
}
