package org.proteosuite.actions;

import org.proteosuite.gui.tasks.TasksTab;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;

/**
 *
 * @author SPerkins
 */
public class TaskPostCompleteAction implements ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject> {

    @Override
    public ProteoSuiteActionResult act(ProteoSuiteActionSubject subject) {
        TasksTab.getInstance().refreshData();
        return ProteoSuiteActionResult.emptyResult();
    }
}
