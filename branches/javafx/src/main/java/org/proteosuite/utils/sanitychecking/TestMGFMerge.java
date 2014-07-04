/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.sanitychecking;

import java.util.HashSet;
import java.util.Set;
import org.proteosuite.utils.FileFormatUtils;

/**
 *
 * @author SPerkins
 */
public class TestMGFMerge {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.1.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.2.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.3.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.4.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.5.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.6.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.7.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.8.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.9.mgf");
        set.add("e:\\data\\puf3\\truncated\\20130123_JC.10.mgf");        
        
        FileFormatUtils.mergeMGF(set, "e:\\data\\puf3\\truncated\\merged.mgf");
    }
}
