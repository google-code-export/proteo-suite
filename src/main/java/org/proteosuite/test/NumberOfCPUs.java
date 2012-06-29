/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.test;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author D3Y241
 */
public class NumberOfCPUs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        String osArch = osBean.getArch();
        System.out.println(osArch);

        int numOfProcessors = osBean.getAvailableProcessors();
        System.out.println(numOfProcessors);
        String[] files = new File("\\\\pnl\\projects\\msshare\\MichaelCusack\\IPC\\results").list();
        System.out.println(files.length);
        for (int i = 0; i < 30; i++) {
            System.out.println(files[i]);
            
        }


    }
}
