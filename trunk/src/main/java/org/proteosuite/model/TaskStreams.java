package org.proteosuite.model;

import java.io.InputStream;

/**
 *
 * @author SPerkins
 */
public class TaskStreams {
    private InputStream outputStream = null;
    private InputStream errorStream = null;

    public InputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getErrorStream() {
        return errorStream;
    }   
    
    public void setOutputStream(InputStream stream) {
        this.outputStream = stream;
    }   
    
    public void setErrorStream(InputStream stream) {
        this.errorStream = stream;
    }   
}
