
package org.proteosuite.model;

import java.util.TreeSet;

/**
 *
 * @author SPerkins
 */
public class Spectrum extends TreeSet<MzIntensityPair> {    
    public int getSpectraCount() {
        return super.size();
    }
}
