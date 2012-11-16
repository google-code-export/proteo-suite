/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.utils;

/**
 *
 * @author faviel
 */
public class CheckMemory {
    public CheckMemory(String sTitle) {         
        int mb = 1024*1024;        
        Runtime runtime = Runtime.getRuntime();         
        System.out.println("******** Statistics [MB] " + sTitle + "*********");         
        System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        System.out.println("Free Memory:" + runtime.freeMemory() / mb);
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
    }
}
