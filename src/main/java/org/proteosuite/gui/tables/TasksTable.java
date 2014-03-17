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
public class TasksTable extends JTable {
    private final DefaultTableModel model;
    public TasksTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        model.addColumn("Name");
        model.addColumn("Phase");
        model.addColumn("Status");
        
        setModel(model);    
    }
    
    public void addTaskRow(String file, String phase, String status) {
        model.addRow(new Object[]{file, phase, status});
    }
    
    public void clear() {
        model.setRowCount(0);
    }
}
