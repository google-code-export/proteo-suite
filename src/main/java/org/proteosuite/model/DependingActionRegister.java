/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.model;

import java.util.HashSet;
import org.proteosuite.gui.listener.ProteoSuiteActionListener;

/**
 *
 * @author SPerkins
 * @param <ProteoSuiteActionListener>
 * @param <T>
 */
public class DependingActionRegister<T> extends HashSet<ProteoSuiteActionListener<T>> {
    private T subject = null;
    public DependingActionRegister (T subject) {
        this.subject = subject;
    }
    
    public void fireDependingActions() {
        ProteoSuiteActionEvent<T> event = new ProteoSuiteActionEvent<>();
        event.setSubject(subject);
        for (ProteoSuiteActionListener listener : this) {
            listener.fire(event);
        }
    }
}
