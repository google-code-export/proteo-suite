/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class LeftColumnAndTabbedPanel extends JPanel {
    private static BoxLayout layout;
    
    public LeftColumnAndTabbedPanel() {
        layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        //add(new ProjectAndOngoingTasksPanel());
        add(new TabbedMainPanel());
    }
}
