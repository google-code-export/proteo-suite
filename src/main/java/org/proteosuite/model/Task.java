package org.proteosuite.model;

import java.util.Objects;

/**
 *
 * @author SPerkins
 */
public class Task {

    private String file;
    private String phase;
    private String status;
    
    public Task(String file, String phase) {
        this(file, phase, "In Progress");
    }
    
    public Task(String file, String phase, String status) {
        this.file = file;
        this.phase = phase;
        this.status = status;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFile() {
        return file;
    }

    public String getPhase() {
        return phase;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Task) {
            if (((Task) that).getFile().equals(this.getFile())
                    && ((Task) that).getPhase().equals(this.getPhase())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.file);
        hash = 17 * hash + Objects.hashCode(this.phase);
        return hash;
    }
}
