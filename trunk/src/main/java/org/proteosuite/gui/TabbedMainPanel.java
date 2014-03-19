/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui;

import javax.swing.JTabbedPane;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.inspect.InspectTab;
import org.proteosuite.gui.tasks.TasksTab;

/**
 *
 * @author SPerkins
 */
public class TabbedMainPanel extends JTabbedPane {
    public TabbedMainPanel() {
        addTab("Analyse", null, AnalyseDynamicTab.getInstance(), "Analyse your data using our pipeline.");
        addTab("Inspect", null, InspectTab.getInstance(), "Inspect your data.");
        addTab("Tasks", null, TasksTab.getInstance(), "View currently running analyses.");
    }
}
