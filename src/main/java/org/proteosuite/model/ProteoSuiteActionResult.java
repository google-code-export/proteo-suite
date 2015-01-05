/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite.model;

import org.proteosuite.ProteoSuiteException;

/**
 *
 * @author SPerkins
 */
public final class ProteoSuiteActionResult<T> {
    private final T result;
    private final ProteoSuiteException pex;
    private final static ProteoSuiteActionResult EMPTY_RESULT = new ProteoSuiteActionResult();
    public ProteoSuiteActionResult(T result, ProteoSuiteException exception) {
        this.result = result;
        this.pex = exception;
    }
    
    public ProteoSuiteActionResult(ProteoSuiteException exception) {
        this(null, exception);
    }
    
    public ProteoSuiteActionResult(T result) {
        this(result, null);
    }
    
    public ProteoSuiteActionResult() {
        this(null, null);
    }
    
    public T getResultObject() {
        return result;
    }
    
    public boolean hasException() {
        return pex != null;
    }
    
    public ProteoSuiteException getException() {
        return pex;
    }
    
    public static ProteoSuiteActionResult emptyResult() {
        return EMPTY_RESULT;
    }
}