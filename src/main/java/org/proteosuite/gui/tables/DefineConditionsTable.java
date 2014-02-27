/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
public class DefineConditionsTable extends JTable {
    private DefaultTableModel model;
    public DefineConditionsTable() {
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0;
            }
        };
        
        model.addColumn("Condition");
        model.addColumn("File Name");
        model.addColumn("Multiplex Assay");
        setModel(model);        
    }
    
    public void addConditionRow(String condition, String fileName, String assay) {               
        model.addRow(new Object[]{condition, fileName, assay});       
    } 
    
    public void clear() {
        model.setRowCount(0);
    }
}
