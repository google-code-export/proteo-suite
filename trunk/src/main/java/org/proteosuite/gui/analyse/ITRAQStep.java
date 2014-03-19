/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.proteosuite.gui.listener.ConfirmButtonListener;

/**
 *
 * @author SPerkins
 */
public class ITRAQStep extends JPanel {
    private static BorderLayout layout = new BorderLayout();
    
    public ITRAQStep() {
        setLayout(layout);
        
        JLabel stepTitle = new JLabel("Confirm your quantitation type as iTRAQ:");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle.getFont().getStyle(), 72));
        add(stepTitle, BorderLayout.PAGE_START);
        
        JLabel mainText = new JLabel("Your identifications will be automatically created and/or loaded in the background.\n"
                + "Additionally your raw data files are (or have) been loaded for inspection in the Inspect tab. This tab will"
                + " also become populated with quantitation data after you click confirm below.\n\n"
                + "Identifications created in Proteosuite are provided by MSGF+.\n" 
                + "iTRAQ quantitation is provided by xTracker.");
        
        mainText.setFont(new Font(mainText.getFont().getFontName(), mainText.getFont().getStyle(), 48));
        add(mainText, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(new JLabel("CLICK CONFIRM TO CONFIRM QUANTITATION TYPE AS iTRAQ AND RUN ANALYSIS"));
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ConfirmButtonListener(this));
        buttonsPanel.add(confirmButton);
        
        add(buttonsPanel, BorderLayout.PAGE_END);
    }
    
    public void refreshFromData() {}
}
