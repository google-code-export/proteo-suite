/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.actions;

import org.proteosuite.model.BackgroundTaskSubject;

/**
 *
 * @author SPerkins
 */
public class EmptyAction implements ProteoSuiteAction<Object, BackgroundTaskSubject> {
    private static EmptyAction INSTANCE = null;
    private EmptyAction() {}
    
    @Override
    public Void act(BackgroundTaskSubject argument) {
        return null;
    }
    
    public static EmptyAction emptyAction() {
        if (INSTANCE == null) {
            INSTANCE = new EmptyAction();
        }
        
        return INSTANCE;
    }    
}
