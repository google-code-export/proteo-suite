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
	private static final long serialVersionUID = 1L;
        private static TabbedMainPanel INSTANCE = null;

	private TabbedMainPanel() {
		addTab("Analyse", null, AnalyseDynamicTab.getInstance(),
				"Analyse your data using our pipeline.");
		addTab("Inspect", null, InspectTab.getInstance(), "Inspect your data.");
		addTab("Tasks", null, TasksTab.getInstance(),
				"View currently running analyses.");
	}
        
        public static TabbedMainPanel getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new TabbedMainPanel();
            }
            
            return INSTANCE;
        }
}
