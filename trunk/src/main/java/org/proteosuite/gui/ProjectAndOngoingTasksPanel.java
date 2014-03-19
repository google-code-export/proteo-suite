/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class ProjectAndOngoingTasksPanel extends JPanel {
    private static final GridLayout layout = new GridLayout(2, 1);
    
    public ProjectAndOngoingTasksPanel() {
        setLayout(layout);
        
        add(new ProjectChoices());
    }
    
}
