/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.tables.CreateOrLoadIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzIdentMLFile;

/**
 *
 * @author SPerkins
 */
public class LoadIdentificationsForSelectedListener implements ActionListener {

    private CreateOrLoadIdentificationsStep step;    

    public LoadIdentificationsForSelectedListener(CreateOrLoadIdentificationsStep step) {
        this.step = step;       
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        CreateOrLoadIdentificationsTable identificationsTable = step.getIdentificationsTable();
        int[] selectedRawFiles = identificationsTable.getSelectedRows();
        if (selectedRawFiles.length == 0) {
            return;
        }

        AnalyseData data = AnalyseData.getInstance();
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        int returnVal = chooser.showOpenDialog(step);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            MzIdentMLFile mzIdentML = new MzIdentMLFile(file);
            for (int i : selectedRawFiles) {                
                data.getRawDataFiles().get(i).setIdentificationDataFile(mzIdentML);                
            }
        }

        step.refreshFromData();
    }

}
