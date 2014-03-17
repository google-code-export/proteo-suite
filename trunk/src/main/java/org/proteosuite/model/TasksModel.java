/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
