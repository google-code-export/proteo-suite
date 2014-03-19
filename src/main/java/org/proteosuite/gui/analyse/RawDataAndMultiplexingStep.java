/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.listener.AddRawDataListener;
import org.proteosuite.gui.listener.ClearAllRawFileButtonListener;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.DeleteSelectedRawFileButtonListener;
import org.proteosuite.gui.listener.MultiplexingSelectionListener;
import org.proteosuite.gui.listener.RestartButtonListener;
import org.proteosuite.gui.tables.RawDataAndMultiplexingTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class RawDataAndMultiplexingStep extends JPanel {
    private static final BorderLayout layout = new BorderLayout();    
    private RawDataAndMultiplexingTable rawDataTable;
    private JComboBox<String> multiplexingBox;    
    public RawDataAndMultiplexingStep() {
        
        setLayout(layout);
        
        
        
        // Add the title of our step.
        JLabel stepTitle = new JLabel("Select your raw data and multiplexing:");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle.getFont().getStyle(), 72));
        add(stepTitle, BorderLayout.PAGE_START);
        
        // Add our raw data table.
        rawDataTable = new RawDataAndMultiplexingTable();
        JScrollPane rawDataTableScroller = new JScrollPane(rawDataTable);
        add(rawDataTableScroller, BorderLayout.CENTER);
       
        // Set up the buttons (panel) and their listeners.
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 3));
        
        JButton addRawDataButton = new JButton("Add Data Files...");
        addRawDataButton.addActionListener(new AddRawDataListener(this));
        buttonsPanel.add(addRawDataButton);
        JButton deleteSelectedButton = new JButton("Delete Selected");
        deleteSelectedButton.addActionListener(new DeleteSelectedRawFileButtonListener(this));
        buttonsPanel.add(deleteSelectedButton);
        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(new ClearAllRawFileButtonListener(this));
        buttonsPanel.add(clearAllButton);
        
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new RestartButtonListener(this));
        buttonsPanel.add(restartButton);
        buttonsPanel.add(new JLabel(""));
        
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ContinueButtonListener(this));
        buttonsPanel.add(continueButton);
        
        buttonsPanel.add(new JLabel("Select multiplexing:"));
        
        multiplexingBox = new JComboBox<String>(new String[]{"iTRAQ 4-plex", "None (label-free)"});
        multiplexingBox.setSelectedIndex(1);
        multiplexingBox.addActionListener(new MultiplexingSelectionListener(this));
        buttonsPanel.add(multiplexingBox);
        
        add(buttonsPanel, BorderLayout.PAGE_END);
    }
    
    public void refreshFromData() {
        AnalyseData data = AnalyseData.getInstance();
        rawDataTable.clear();
        for (int i = 0; i < data.getRawDataCount(); i++) {
            RawDataFile dataFile = data.getRawDataFile(i);
            rawDataTable.addRawFileRow(dataFile);            
        }
    }
    
    public RawDataAndMultiplexingTable getRawDataTable() {
        return rawDataTable;
    }
    
    public JComboBox<String> getMultiplexingBox() {
        return multiplexingBox;
    }
}
