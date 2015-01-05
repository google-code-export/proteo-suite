/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite;

/**
 *
 * @author SPerkins
 */
public class ProteoSuiteException extends Exception {
    private final String localMessage;
    public ProteoSuiteException(String localMessage, Exception e) {
        super(e);
        this.localMessage = localMessage;
        System.out.println("ProteoSuiteException created: " + localMessage);
    }
    
    public ProteoSuiteException(String localMessage) {
        super();
        this.localMessage = localMessage;
        System.out.println("ProteoSuiteException created: " + localMessage);
    }
    
    @Override
    public String getLocalizedMessage() {
        return localMessage != null ? localMessage : "";
    }  
}
