package org.proteosuite.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;

/**
 *
 * @author SPerkins
 */
public class RawDataAndMultiplexingSectionListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
        AnalyseDynamicTab.getInstance().moveToStep(AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP);
    }
}
