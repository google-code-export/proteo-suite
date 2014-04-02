package org.proteosuite.model;

import java.util.HashSet;

/**
 *
 * @author SPerkins
 */
public class TasksModel extends HashSet<Task> {
    public TasksModel() {
        super();
    }    
    
    public synchronized boolean set(Task task) {
        this.remove(task);
        this.add(task);
        return true;
    }  
}
