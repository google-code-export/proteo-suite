/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class DeleteSelectedRawFileButtonListener implements ActionListener {
    private RawDataAndMultiplexingStep step;    
    public DeleteSelectedRawFileButtonListener(RawDataAndMultiplexingStep step) {
        this.step = step;        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AnalyseData data = AnalyseData.getInstance();
        int result = JOptionPane.showConfirmDialog(step, "Are you sure you wish to delete the entries for the selected raw files?\nYour raw files will NOT be deleted, but any related data in Proteosuite will be lost.","Delete Selected Files", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            int[] selectedRows = step.getRawDataTable().getSelectedRows();
            Arrays.sort(selectedRows);
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                data.getRawDataFiles().remove(selectedRows[i]);
            }
            
            step.refreshFromData();
        }        
    }
}
