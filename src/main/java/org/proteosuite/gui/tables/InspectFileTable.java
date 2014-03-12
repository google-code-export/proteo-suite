/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SPerkins
 */
public class InspectFileTable extends JTable {
    private DefaultTableModel model;
    public InspectFileTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("Name");
        model.addColumn("Format");        
    }
    
    public void addFileRow(String fileName, String format) {               
        model.addRow(new Object[]{fileName, format});       
    }
    
    public void clear() {
        model.setRowCount(0);
    }
}
