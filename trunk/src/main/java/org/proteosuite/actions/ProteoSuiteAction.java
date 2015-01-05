package org.proteosuite.actions;

/**
 *
 * @author SPerkins
 */
public interface ProteoSuiteAction<T, U> {
    public T act(U argument);    
}
