package org.proteosuite.actions;

import org.proteosuite.gui.tasks.TasksTab;

/**
 *
 * @author SPerkins
 */
public class TaskPostCompleteAction implements ProteoSuiteAction<Void, Void> {

    @Override
    public Void act(Void argument) {
        TasksTab.getInstance().refreshData();
        return null;
    }
}
