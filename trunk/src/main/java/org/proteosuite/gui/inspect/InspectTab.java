package org.proteosuite.gui.inspect;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.proteosuite.gui.chart.ChartSpectrum;
import org.proteosuite.gui.listener.InspectComboListener;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.RawMzMLFile;

import com.compomics.util.gui.spectrum.SpectrumPanel;

/**
 * 
 * @author SPerkins
 */
public class InspectTab extends JPanel implements ListSelectionListener {
	private BorderLayout layout = new BorderLayout();
	private final JComboBox<String> dataFileComboBox = new JComboBox<String>();
	private final InspectModel inspectModel = AnalyseData.getInstance().getInspectModel();
	private static InspectTab instance = null;
	//private final InspectTablePanel tablePanel = new InspectTablePanel();
	private final InspectChartPanel chartPanel = new InspectChartPanel();
	private final JTable jTable = new JTable();

	private InspectTab() {
		setLayout(layout);

		dataFileComboBox.addItemListener(new InspectComboListener());
		
		add(getHeaderPanel(), BorderLayout.PAGE_START);
		add(getBodyPanel(), BorderLayout.CENTER);
	}
	
	private Component getBodyPanel() {
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1, 2));
		body.add(chartPanel);
		body.add(new JScrollPane(jTable));
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoCreateRowSorter(true);
		
		return body;
	}

	public Component getHeaderPanel()
	{
		JPanel inspectHeader = new JPanel();
		inspectHeader.setLayout(new FlowLayout());
		inspectHeader.add(new JLabel("Select data file: "));
		inspectHeader.add(dataFileComboBox);
		
		return inspectHeader;
	}

	public static InspectTab getInstance() {
		if (instance == null) {
			instance = new InspectTab();
		}

		return instance;
	}

	public JTable getTablePanel() {
		return jTable;
	}

	public InspectChartPanel getChartPanel() {
		return chartPanel;
	}

	public String getSelectedFile() {
		return (String) dataFileComboBox.getSelectedItem();
	}

	public void refreshComboBox() {
		dataFileComboBox.removeAllItems();
		for (RawDataFile dataFile : inspectModel.getRawData()) {
			dataFileComboBox.addItem(dataFile.getFileName());
		}

		for (IdentDataFile identFile : inspectModel.getIdentData()) {
			dataFileComboBox.addItem(identFile.getFileName());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		if (listSelectionEvent.getValueIsAdjusting())
			return;
		
		ListSelectionModel listSelectionModel = (ListSelectionModel) listSelectionEvent.getSource();

		if (listSelectionModel.getAnchorSelectionIndex() == -1)
			return;
		
		RawMzMLFile rawMzMLFile = (RawMzMLFile) inspectModel.getRawDataFile(getSelectedFile());		
		String sID = (String) getTablePanel().getValueAt(listSelectionModel.getAnchorSelectionIndex(), 1);

		SpectrumPanel specPanel = (SpectrumPanel) ChartSpectrum.getSpectrum(rawMzMLFile.getUnmarshaller(), sID);
		chartPanel.setSpectrum(specPanel);
	}
}
