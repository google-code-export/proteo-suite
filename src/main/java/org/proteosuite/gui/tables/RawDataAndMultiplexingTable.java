package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class RawDataAndMultiplexingTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
    

    public RawDataAndMultiplexingTable() {
        model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("File Name");
        model.addColumn("File Size (MB)");
        model.addColumn("Format");
        model.addColumn("Instrument");
        model.addColumn("MS1 Peak-picked?");
        model.addColumn("MS2 Peak-picked?");
        model.addColumn("Spectra");
        setModel(model);        

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public void addRawFileRow(RawDataFile rawDataFile) {
        boolean[] peakPicked = rawDataFile.getPeakPicking();
        boolean[] msLevelPresence = rawDataFile.getMSLevelPresence();
        String ms1PeakPicked = peakPicked[0] ? "Yes" : "No";
        ms1PeakPicked = !msLevelPresence[0] ? "No spectra" : ms1PeakPicked;
        String ms2PeakPicked = peakPicked[1] ? "Yes" : "No";
        ms2PeakPicked = !msLevelPresence[1] ? "No spectra" : ms2PeakPicked;
        model.addRow(new Object[]{rawDataFile.getFileName(), rawDataFile.getFileSizeInMegaBytes(), rawDataFile.getFormat(), "Unknown", ms1PeakPicked, ms2PeakPicked, rawDataFile.getSpectraCount()});        
    }

    public void clear() {
        model.setRowCount(0);        
    }
}