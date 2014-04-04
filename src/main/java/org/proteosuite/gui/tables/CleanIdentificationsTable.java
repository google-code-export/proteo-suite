/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CleanIdentificationsTable extends JTable {
    private DefaultTableModel model;
    public CleanIdentificationsTable() {
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("File Name");       
        model.addColumn("Peptide Spectrum Matches (PSMs) Passing Threshold");
        model.addColumn("Peptide Spectrum Matches (PSMs Not Passing Threshold)");
        model.addColumn("Peptides");
        model.addColumn("Thresholding Used");
        model.addColumn("Apply Thresholding");
        
        setModel(model);
    }
    
    public void addIdentFileRow(IdentDataFile identFile) {        
        if (identFile != null) {
            model.addRow(new Object[]{
                identFile.getFileName(),
                identFile.getPSMCountPassingThreshold(),
                identFile.getPSMCountNotPassingThreshold(),
                identFile.getPeptideCountPassingThreshold(),
                identFile.getThresholdingUsed(),
                null
            }); 
        }             
    }
    
    public void clear() {
        model.setRowCount(0);
    }
}
