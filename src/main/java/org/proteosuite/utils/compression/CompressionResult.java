/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.compression;

/**
 *
 * @author SPerkins
 */
public class CompressionResult {
    private long compressStart;
    private boolean compressStartSet = false;
    private long compressStop;
    private boolean compressStopSet = false;
    private long nonCompressedSize = -1;
    private boolean nonCompressedSizeSet = false;
    private long compressedSize = -1;
    private boolean compressedSizeSet = false;
    public void setStart(final long start) {
        this.compressStart = start;
        compressStartSet = true;
    }
    
    public void setStop(final long stop) {
        this.compressStop = stop;
        compressStopSet = true;
    }
    
    public final long getCompressionDuration() {
        if (compressStartSet && compressStopSet) {
            return compressStop - compressStart;
        }
        
        return -1;
    }
    
    public void setCompressedSize(final long compressedSize) {
        this.compressedSize = compressedSize;
        compressedSizeSet = true;
    }
    
    public long getCompressedSize() {
        return compressedSize;
    }
    
    public void setNonCompressedSize(final long nonCompressedSize) {
        this.nonCompressedSize = nonCompressedSize;
        nonCompressedSizeSet = true;
    }
    
    public long getNonCompressedSize() {
        return nonCompressedSize;
    }
    
    public double getCompressionRatio() {
        if (nonCompressedSizeSet && compressedSizeSet) {
            return ((double)compressedSize)/((double)nonCompressedSize);
        }
        
        return -1;
    }
}
