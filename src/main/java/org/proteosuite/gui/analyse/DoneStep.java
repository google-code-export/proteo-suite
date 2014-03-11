/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author SPerkins
 */
public class DoneStep extends JPanel {
    private static final BorderLayout layout = new BorderLayout();
    
    public DoneStep() {
        setLayout(layout);
        
        JLabel stepTitle = new JLabel("You're all done!");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle.getFont().getStyle(), 72));
        add(stepTitle, BorderLayout.PAGE_START);
        
        JLabel mainText = new JLabel("Your computations including any identifications and quantitations needed are running in the background\n"
                + "Visit the Tasks tab to see the progress of your analysis.\n"
                + "Visit the Inspect tab to see interrogate your raw files and result from this analysis as they become available.\n\n"
                + "But for now have a cup of tea and wait for this whole thing to blow over :)");
        
        add(mainText, BorderLayout.CENTER);
    }
}
