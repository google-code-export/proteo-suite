package org.proteosuite.model;

import java.io.BufferedReader;

/**
 *
 * @author SPerkins
 */
public class Log {
    private BufferedReader standardOutputReader = null;
    private BufferedReader standardErrorReader = null;
    public void setStandardOutput(BufferedReader reader) {
        this.standardOutputReader = reader;
    }
    
    public BufferedReader getStandardOutput() {
        if (this.standardOutputReader == null) {
            throw new IllegalStateException("The requested value has not been set.");
        }
        
        return this.standardOutputReader;
    }
    
    public void setErrorOutput(BufferedReader reader) {
        this.standardErrorReader = reader;
    }
    
    public BufferedReader getStandardError() {
        return this.standardErrorReader;
    }
}
