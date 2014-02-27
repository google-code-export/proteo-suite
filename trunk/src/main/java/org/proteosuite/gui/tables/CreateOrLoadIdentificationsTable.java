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
public class CreateOrLoadIdentificationsTable extends JTable {
    private DefaultTableModel model;
    public CreateOrLoadIdentificationsTable() {
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("File Name");
        model.addColumn("Format");
        model.addColumn("Identifications Status");
        
        setModel(model);
    }
    
    public void addRawFileRow(RawDataFile dataFile) {
        IdentDataFile identFile = dataFile.getIdentificationDataFile();
        if (identFile == null) {
            model.addRow(new Object[]{dataFile.getFileName(), dataFile.getFormat(), "<None>"}); 
        } else {
            model.addRow(new Object[]{dataFile.getFileName(), dataFile.getFormat(), identFile.getLoadingStatus()}); 
        }              
    }
    
    public void clear() {
        model.setRowCount(0);
    }
}
