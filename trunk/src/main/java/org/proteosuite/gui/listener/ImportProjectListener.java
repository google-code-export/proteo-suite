/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author SPerkins
 */
public class ImportProjectListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.println("Import Project link clicked");
    }
}
