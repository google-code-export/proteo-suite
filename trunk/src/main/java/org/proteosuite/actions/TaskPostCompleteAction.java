package org.proteosuite.actions;

import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.BackgroundTaskSubject;

/**
 *
 * @author SPerkins
 */
public class TaskPostCompleteAction implements ProteoSuiteAction<Object, BackgroundTaskSubject> {

    @Override
    public Void act(BackgroundTaskSubject subject) {
        TasksTab.getInstance().refreshData();
        return null;
    }
}
