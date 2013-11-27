/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.compression;

/**
 *
 * @author sperkins
 */
public class DecompressionResult {
    private long decompressStart;
    private boolean decompressStartSet = false;
    private long decompressStop;
    private boolean decompressStopSet = false;
    private long compressedSize = -1;
    private boolean compressedSizeSet = false;
    private long decompressedSize = -1;
    private boolean decompressedSizeSet = false;
    public void setStart(final long start) {
        this.decompressStart = start;
        decompressStartSet = true;
    }
    
    public void setStop(final long stop) {
        this.decompressStop = stop;
        decompressStopSet = true;
    }
    
    public final long getCompressionDuration() {
        if (decompressStartSet && decompressStopSet) {
            return decompressStop - decompressStart;
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
    
    public void setDecompressedSize(final long decompressedSize) {
        this.decompressedSize = decompressedSize;
        decompressedSizeSet = true;
    }
    
    public long getDecompressedSize() {
        return decompressedSize;
    }
    
    public double getDecompressionRatio() {
        if (decompressedSizeSet && compressedSizeSet) {
            return ((double)decompressedSize)/((double)compressedSize);
        }
        
        return -1;
    }
}
