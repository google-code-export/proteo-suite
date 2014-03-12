
package org.proteosuite.gui.inspect;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.listener.InspectTableListener;
import org.proteosuite.gui.tables.InspectFileTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class InspectTab extends JPanel {
    private BorderLayout layout = new BorderLayout();
    private InspectFileTable fileTable;
    private InspectModel inspectModel;
    private static InspectTab instance = null;
    private InspectTab() {
        setLayout(layout);
        
        inspectModel = AnalyseData.getInstance().getInspectModel();
        fileTable = new InspectFileTable();
        fileTable.getSelectionModel().addListSelectionListener(new InspectTableListener());
        JScrollPane fileTableScroller = new JScrollPane(fileTable);
        add(fileTableScroller, BorderLayout.WEST);
        
        JScrollPane dataScroller = new JScrollPane();
        add(dataScroller, BorderLayout.EAST);
        
        JPanel chartArea = new JPanel();
        add(chartArea, BorderLayout.SOUTH);
    }
    
    public static InspectTab getInstance() {
        if (instance == null) {
            instance = new InspectTab();
        }
        
        return instance;
    }
    
    public void refreshFileTable() {
        fileTable.clear();
        for (RawDataFile dataFile : inspectModel.getRawData()) {
            fileTable.addFileRow(dataFile.getFileName(), dataFile.getFormat());
        }
        
        for (IdentDataFile identFile : inspectModel.getIdentData()) {
            fileTable.addFileRow(identFile.getFileName(), identFile.getFormat());
        }
        
    }
}
