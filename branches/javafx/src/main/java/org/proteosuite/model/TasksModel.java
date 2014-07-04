package org.proteosuite.model;

import java.util.HashSet;

/**
 *
 * @author SPerkins
 */
public class TasksModel extends HashSet<Task> {
	private static final long serialVersionUID = 1L;

	public TasksModel() {
        super();
    }    
    
    public synchronized boolean set(Task task) {
        this.remove(task);
        this.add(task);
        return true;
    }  
}
