/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils;

/**
 *
 * @author SPerkins
 */
public class MzMLCompressorBase {
    protected CompressionResult compressionResult;
    public MzMLCompressorBase() {
        compressionResult = new CompressionResult();
    }
    
    public void stampStart() {
        compressionResult.setStart(System.nanoTime());
    }
    
    public void stampStop() {
        compressionResult.setStop(System.nanoTime());
    }
}
