
package org.proteosuite.gui.inspect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.proteosuite.gui.listener.InspectComboListener;
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
    private JComboBox dataFileComboBox;
    private InspectModel inspectModel;
    private static InspectTab instance = null;
    private InspectTablePanel tablePanel;
    private InspectChartPanel chartPanel;
    private InspectTab() {
        setLayout(layout);
        
        inspectModel = AnalyseData.getInstance().getInspectModel();
        JPanel inspectHeader = new JPanel();
        inspectHeader.setLayout(new FlowLayout());
        inspectHeader.add(new JLabel("Select data file: "));
        dataFileComboBox = new JComboBox();
        dataFileComboBox.addItemListener(new InspectComboListener());
        inspectHeader.add(dataFileComboBox);
        
        add(inspectHeader, BorderLayout.PAGE_START);
        
        tablePanel = new InspectTablePanel();        
        add(tablePanel, BorderLayout.EAST);
        
        chartPanel = new InspectChartPanel();
        add(chartPanel, BorderLayout.SOUTH);
    }
    
    public static InspectTab getInstance() {
        if (instance == null) {
            instance = new InspectTab();
        }
        
        return instance;
    }
    
    public InspectTablePanel getTablePanel() {
        return tablePanel;
    }  
    
    public InspectChartPanel getChartPanel() {
        return chartPanel;
    }
    
    public String getSelectedFile() {
        return (String)dataFileComboBox.getSelectedItem();
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
}
