/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.listener;

import org.proteosuite.model.ProteoSuiteActionEvent;

/**
 *
 * @author SPerkins
 * @param <T>
 */
public interface ProteoSuiteActionListener<T> {
    public void fire(ProteoSuiteActionEvent<T> event);
}
