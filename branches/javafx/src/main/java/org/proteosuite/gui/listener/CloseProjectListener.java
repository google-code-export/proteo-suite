package org.proteosuite.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author SPerkins
 */
public class CloseProjectListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.println("Close Project link clicked");
    }
}
