package org.proteosuite.gui.inspect;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.chart.ChartPlot2D;
import org.proteosuite.gui.chart.ChartSpectrum;
import org.proteosuite.gui.tables.MzTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MzMLFile;
import org.proteosuite.model.RawDataFile;

public class InspectRaw extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private static byte MS_LEVEL = 1;
	private static double LOW_INTENSITY = 10;
	private final InspectChartPanel chartPanel = new InspectChartPanel();
	private final JTable jTable = new JTable();
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();
	private RawDataFile data;

	public InspectRaw() {
		super(new BorderLayout());
		JPanel mainContent = new JPanel(new GridLayout(1, 2));
		add(getButtonPanel(), BorderLayout.PAGE_END);
		add(mainContent, BorderLayout.CENTER);
		mainContent.add(chartPanel);
		mainContent.add(new JScrollPane(jTable));

		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoCreateRowSorter(true);
	}
	
	private JPanel getButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(new JLabel("MS Level:"));
		final JComboBox<Integer> msLevel = new JComboBox<>();
		msLevel.addItem(1);
		msLevel.addItem(2);
		msLevel.setSelectedIndex(MS_LEVEL - 1);

		panel.add(msLevel);

		final JTextField threshold = new JTextField(Double.toString(LOW_INTENSITY), 5);

		panel.add(new JLabel("Minimum Intensity:"));
		panel.add(threshold);

		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MS_LEVEL = Byte.parseByte(msLevel.getItemAt(
						msLevel.getSelectedIndex()).toString());
				try {
					LOW_INTENSITY = Double.parseDouble(threshold
							.getText());
					setData(data, false);
				} catch (NumberFormatException exception) {
					// User entered non-numeric data
					threshold.setText(String.valueOf(LOW_INTENSITY));
				}
			}
		});
                
		panel.add(refresh);

		return panel;
	}

	public JTable getTablePanel() {
		return jTable;
	}

	public InspectChartPanel getChartPanel() {
		return chartPanel;
	}

	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
//		if (listSelectionEvent.getValueIsAdjusting()){
//			return;}
                
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ListSelectionModel listSelectionModel = (ListSelectionModel) listSelectionEvent
				.getSource();

		if (listSelectionModel.getAnchorSelectionIndex() == -1)
			return;

		if (inspectModel.isRawDataFile(InspectTab.getInstance()
				.getSelectedFile())) {
			RawDataFile rawDataFile = inspectModel
					.getRawDataFile(InspectTab.getInstance().getSelectedFile());
			String sID = (String) getTablePanel().getValueAt(
					listSelectionModel.getAnchorSelectionIndex(), 1);

			SpectrumPanel specPanel = (SpectrumPanel) ChartSpectrum
					.getSpectrum(rawDataFile, sID);
			chartPanel.setSpectrum(specPanel);
		}
                
                this.setCursor(Cursor.getDefaultCursor());
	}

	public void setData(RawDataFile dataFile, boolean refreshChromatogram) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
		data = dataFile;
		
		MzTable rawData = new MzTable();
		rawData.showData(dataFile, MS_LEVEL, LOW_INTENSITY);

		JTable mzTable = getTablePanel();
		mzTable.removeAll();
		mzTable.setModel(rawData.getModel());

		rawData.getSelectionModel().addListSelectionListener(this);
                if (dataFile instanceof MzMLFile && refreshChromatogram) {
                    ChromatogramPanel cachedChromatogram = ChartChromatogram.getChromatogram(((MzMLFile)dataFile)
					.getUnmarshaller());

                    getChartPanel().setChromatogram(cachedChromatogram);
                }		
		
                JPanel cached2DView = ChartPlot2D.get2DPlot(dataFile, MS_LEVEL, LOW_INTENSITY);

		getChartPanel().set2D(cached2DView);
                
                this.setCursor(Cursor.getDefaultCursor());
	}
}
