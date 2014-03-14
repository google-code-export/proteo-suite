/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.inspect;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author SPerkins
 */
public class InspectTablePanel extends JPanel {
	private JTable jTable;
    
    public void setTable(JTable table) {
        removeAll();
        jTable = table;
        add(new JScrollPane(table));
        repaint();
    }
    
    public JTable getTable()
    {
    	return jTable;
    }
}
