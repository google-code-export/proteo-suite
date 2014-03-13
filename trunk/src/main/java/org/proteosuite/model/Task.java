
package org.proteosuite.model;

/**
 *
 * @author SPerkins
 */
public class Task {
    private String file = "";
    private String stage = "";
    private String status = "";
    
    public void setFile(String file) {
        this.file = file;
    }
    
    public void setStage(String stage) {
        this.stage = stage;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFile() {
        return file;
    }
    
    public String getStage() {
        return stage;
    }
    
    public String getStatus() {
        return status;
    }
}
