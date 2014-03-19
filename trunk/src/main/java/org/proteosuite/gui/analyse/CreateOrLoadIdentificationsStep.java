/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.CreateIdentificationsForSelectedListener;
import org.proteosuite.gui.listener.LoadIdentificationsForSelectedListener;
import org.proteosuite.gui.listener.PreviousButtonListener;
import org.proteosuite.gui.listener.ResetIdentificationsForSelectedListener;
import org.proteosuite.gui.tables.CreateOrLoadIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CreateOrLoadIdentificationsStep extends JPanel {
    private static final BorderLayout layout = new BorderLayout();    
    private CreateOrLoadIdentificationsTable identificationsTable;    
    private IdentParamsView identParamsView = new IdentParamsView("execute");
    
    public CreateOrLoadIdentificationsStep() {
        setLayout(layout);        
        
        JLabel stepTitle = new JLabel("Create or load your identifications:");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle.getFont().getStyle(), 72));
        add(stepTitle, BorderLayout.PAGE_START);
        identificationsTable = new CreateOrLoadIdentificationsTable();
        JScrollPane identificationsScroller = new JScrollPane(identificationsTable);
        add(identificationsScroller, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 3));
        
        JButton loadIdentificationsButton = new JButton("Load identifications for selected...");
        loadIdentificationsButton.addActionListener(new LoadIdentificationsForSelectedListener(this));
        buttonsPanel.add(loadIdentificationsButton);
        
        JButton createIdentificationsButton = new JButton("Create identifications for selected...");
        createIdentificationsButton.addActionListener(new CreateIdentificationsForSelectedListener(this));
        buttonsPanel.add(createIdentificationsButton);
        
        JButton resetIdentificationsButton = new JButton("Reset status for selected...");
        resetIdentificationsButton.addActionListener(new ResetIdentificationsForSelectedListener(this));
        buttonsPanel.add(resetIdentificationsButton);
        
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new PreviousButtonListener(this));
        buttonsPanel.add(previousButton);
        
        //JPanel supportGenomeAnnotationOption = new JPanel();
        //supportGenomeAnnotationOption.setLayout(new BoxLayout(supportGenomeAnnotationOption, BoxLayout.X_AXIS));
        //JCheckBox genomeAnnotationOption = new JCheckBox();
        //supportGenomeAnnotationOption.add(genomeAnnotationOption);
        //supportGenomeAnnotationOption.add(new JLabel("Support Genome Annotation?"));
        //buttonsPanel.add(supportGenomeAnnotationOption);
        
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ContinueButtonListener(this));
        buttonsPanel.add(continueButton);
        
        add(buttonsPanel, BorderLayout.PAGE_END);
    }
    
    public CreateOrLoadIdentificationsTable getIdentificationsTable() {
        return identificationsTable;
    }
    
    public IdentParamsView getIdentParamsView() {
        return identParamsView;
    }
    
    public synchronized void refreshFromData() {
        identificationsTable.clear();
        AnalyseData data = AnalyseData.getInstance();
        for (int i = 0; i < data.getRawDataCount(); i++) {
            RawDataFile dataFile = data.getRawDataFile(i);
            identificationsTable.addRawFileRow(dataFile);
        }
    }  
}
