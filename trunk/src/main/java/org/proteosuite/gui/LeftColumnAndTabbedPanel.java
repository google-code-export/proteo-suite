package org.proteosuite.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class LeftColumnAndTabbedPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static BoxLayout layout;
    
    public LeftColumnAndTabbedPanel() {
        layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        //add(new ProjectAndOngoingTasksPanel());
        add(new TabbedMainPanel());
    }
}
