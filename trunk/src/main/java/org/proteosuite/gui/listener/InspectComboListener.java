
package org.proteosuite.gui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.proteosuite.gui.chart.ChartChromatogram;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tables.JTableMzML;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.model.RawMzMLFile;

/**
 *
 * @author SPerkins
 */
public class InspectComboListener implements ItemListener {
    private final InspectModel inspectModel = AnalyseData.getInstance().getInspectModel();
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        // Populate the table.
        String fileChosen = (String)e.getItem();
        if (inspectModel.isRawDataFile(fileChosen)) {
            RawDataFile dataFile = inspectModel.getRawDataFile(fileChosen);
            JTableMzML rawData = new JTableMzML();
            rawData.showData((RawMzMLFile)dataFile);
            InspectTab.getInstance().getTablePanel().setTable(rawData);
            rawData.getSelectionModel().addListSelectionListener(InspectTab.getInstance());
            InspectTab.getInstance().getChartPanel().setChromatogram(ChartChromatogram.getChromatogram((RawMzMLFile)dataFile));
        } else if (inspectModel.isIdentFile(fileChosen)) {
            // Open mzIdentMLViewer.
        } else if (inspectModel.isQuantFile(fileChosen)) {
            
        }
    }
    
}
