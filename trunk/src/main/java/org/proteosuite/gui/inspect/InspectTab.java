package org.proteosuite.gui.inspect;

import com.compomics.util.gui.spectrum.SpectrumPanel;
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
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.RawMzMLFile;

/**
 * 
 * @author SPerkins
 */
public class InspectTab extends JPanel implements ListSelectionListener {
	public static final byte PANEL_BLANK = 0;
	public static final byte PANEL_RAW = 1;
	public static final byte PANEL_IDENT = 2;
	public static final byte PANEL_QUANT = 3;

	private static final int CONTENT_PANEL_INDEX = 1;

	private BorderLayout layout = new BorderLayout();
	private final JComboBox<String> dataFileComboBox = new JComboBox<String>();
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();
	private static InspectTab instance = null;
	private final InspectChartPanel chartPanel = new InspectChartPanel();
	private final JTable jTable = new JTable();

	private InspectTab() {
		setLayout(layout);

		dataFileComboBox.addItemListener(new InspectComboListener());

		add(getHeaderPanel(), BorderLayout.PAGE_START);
		setInspectType(PANEL_BLANK);
	}

	public void setInspectType(byte panelType) {
		Component content = null;
		switch (panelType) {
		case PANEL_BLANK:
		case PANEL_IDENT:
			content = new JPanel();
			break;
		case PANEL_RAW:
			content = getRawBodyPanel();
			break;
		case PANEL_QUANT:
			content = getQuantBodyPanel();
			break;
		default:
			throw new IllegalArgumentException("Unknown panel type");
		}

		remove(CONTENT_PANEL_INDEX);
		addImpl(content, BorderLayout.CENTER, CONTENT_PANEL_INDEX);
		repaint();
		revalidate();
	}

	private Component getRawBodyPanel() {
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1, 2));
		body.add(chartPanel);
		body.add(new JScrollPane(jTable));

		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoCreateRowSorter(true);

		return body;
	}

	private Component getQuantBodyPanel() {
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(1, 2));
		body.add(chartPanel);
		body.add(new JScrollPane(jTable));

		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoCreateRowSorter(true);

		return body;
	}

	public Component getHeaderPanel() {
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

		for (QuantDataFile quantFile : inspectModel.getQuantData()) {
			dataFileComboBox.addItem(quantFile.getFileName());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		if (listSelectionEvent.getValueIsAdjusting())
			return;

		ListSelectionModel listSelectionModel = (ListSelectionModel) listSelectionEvent
				.getSource();

		if (listSelectionModel.getAnchorSelectionIndex() == -1)
			return;

		if (inspectModel.isRawDataFile(getSelectedFile())) {
			RawMzMLFile rawMzMLFile = (RawMzMLFile) inspectModel
					.getRawDataFile(getSelectedFile());
			String sID = (String) getTablePanel().getValueAt(
					listSelectionModel.getAnchorSelectionIndex(), 1);

			SpectrumPanel specPanel = (SpectrumPanel) ChartSpectrum
					.getSpectrum(rawMzMLFile.getUnmarshaller(), sID);
			chartPanel.setSpectrum(specPanel);
		}
	}
}
