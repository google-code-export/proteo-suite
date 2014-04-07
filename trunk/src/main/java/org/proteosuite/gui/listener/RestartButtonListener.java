package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class RestartButtonListener implements ActionListener {
    private RawDataAndMultiplexingStep step;
    
    public RestartButtonListener(RawDataAndMultiplexingStep step) {
        this.step = step;
       
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        int result = JOptionPane.showConfirmDialog(step, "Are you sure you wish to restart?\nAny progress you have made will be lost.","Restart Analysis", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            AnalyseData.getInstance().clear();
            step.refreshFromData();
        }
    }
}
