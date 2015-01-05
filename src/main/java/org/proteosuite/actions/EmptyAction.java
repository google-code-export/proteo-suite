/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.actions;

import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;

/**
 *
 * @author SPerkins
 */
public class EmptyAction<T extends ProteoSuiteActionSubject> implements ProteoSuiteAction<ProteoSuiteActionResult, T> {    
    protected EmptyAction() {}
    
    @Override
    public ProteoSuiteActionResult act(T argument) {
        return ProteoSuiteActionResult.emptyResult();
    }    
}
