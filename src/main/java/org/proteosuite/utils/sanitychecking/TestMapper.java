/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.utils.sanitychecking;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import uk.ac.liv.jmzqml.MzQuantMLElement;
import static uk.ac.liv.jmzqml.MzQuantMLElement.FeatureList;
import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.FeatureList;

import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class TestMapper {
    public static void main(String[] args) {
        Map<String, String> rawToMzidMap = new HashMap<String, String>();
        rawToMzidMap.put("e:\\data\\mam_042408o_CPTAC_study6_6B011_2400_3600.mzML", "e:\\data\\mam_042408o_CPTAC_study6_6B011_2400_3600_msgfplus.mzid");
        rawToMzidMap.put("e:\\data\\mam_042408o_CPTAC_study6_6C008_2400_3600.mzML", "e:\\data\\mam_042408o_CPTAC_study6_6C0081_2400_3600_msgfplus.mzid");
        MzQuantMLUnmarshaller umarsh = new MzQuantMLUnmarshaller("e:\\data\\unlabeled_result_FLUQT.mzq");
        Iterator<FeatureList> ftList = umarsh.unmarshalCollectionFromXpath(FeatureList);
        System.out.println(ftList.hasNext());
    
    }
}
