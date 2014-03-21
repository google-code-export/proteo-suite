package org.proteosuite.gui.inspect;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.proteosuite.gui.chart.ChartSpectrum;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawMzMLFile;

import com.compomics.util.gui.spectrum.SpectrumPanel;

public class InspectRaw extends JPanel implements ListSelectionListener {
	private final InspectChartPanel chartPanel = new InspectChartPanel();
	private final JTable jTable = new JTable();
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();

	public InspectRaw() {
		setLayout(new GridLayout(1, 2));
		add(chartPanel);
		add(new JScrollPane(jTable));

		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoCreateRowSorter(true);
	}

	public JTable getTablePanel() {
		return jTable;
	}

	public InspectChartPanel getChartPanel() {
		return chartPanel;
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		if (listSelectionEvent.getValueIsAdjusting())
			return;

		ListSelectionModel listSelectionModel = (ListSelectionModel) listSelectionEvent
				.getSource();

		if (listSelectionModel.getAnchorSelectionIndex() == -1)
			return;

		if (inspectModel.isRawDataFile(InspectTab.getInstance()
				.getSelectedFile())) {
			RawMzMLFile rawMzMLFile = (RawMzMLFile) inspectModel
					.getRawDataFile(InspectTab.getInstance().getSelectedFile());
			String sID = (String) getTablePanel().getValueAt(
					listSelectionModel.getAnchorSelectionIndex(), 1);

			SpectrumPanel specPanel = (SpectrumPanel) ChartSpectrum
					.getSpectrum(rawMzMLFile.getUnmarshaller(), sID);
			chartPanel.setSpectrum(specPanel);
		}
	}
}
