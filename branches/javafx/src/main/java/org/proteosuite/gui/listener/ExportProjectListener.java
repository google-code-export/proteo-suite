package org.proteosuite.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author SPerkins
 */
public class ExportProjectListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.println("Export Project link clicked");
    }
}
