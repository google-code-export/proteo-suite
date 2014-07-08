/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.model;

/**
 *
 * @author SPerkins
 * @param <T>
 */
public class ProteoSuiteActionEvent<T> {

    private T subject = null;

    public void setSubject(T subject) {
        this.subject = subject;
    }
    
    public T getSubject() {
        return this.subject;
    }
}
