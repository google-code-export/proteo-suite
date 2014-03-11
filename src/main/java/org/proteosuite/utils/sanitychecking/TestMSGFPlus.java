
package org.proteosuite.utils.sanitychecking;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.identification.MSGFPlusWrapper;

/**
 *
 * @author SPerkins
 */
public class TestMSGFPlus {
    public static void main(String[] args) {
        String directory = "e:\\data\\label_free";
        String[] rawFiles = new String[]{"mam_042408o_CPTAC_study6_6B011.mzML", "mam_042408o_CPTAC_study6_6C008.mzML",
        "mam_042408o_CPTAC_study6_6D004.mzML", "mam_042408o_CPTAC_study6_6E004.mzML"};
        
        final IdentParamsView identParamsExecute = new IdentParamsView("execute");
        
        final JDialog dialogIdentParamsExecute = new JDialog(null, "Set Identification Parameters", Dialog.ModalityType.APPLICATION_MODAL);
        // Action for when windo close is attempted.
        final Action closeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identParamsExecute.setRun(false);
                dialogIdentParamsExecute.dispose();
            }
        };

        // Add the window close action as a listener.
        dialogIdentParamsExecute.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeAction.actionPerformed(null);
            }
        });

        // Add the view to the content pane.
        dialogIdentParamsExecute.getContentPane().add(identParamsExecute);
        dialogIdentParamsExecute.pack();
        dialogIdentParamsExecute.setVisible(true);

        // Check if we're okay to run identifications..
        boolean paramsSetOkay = identParamsExecute.getRun();
        
        int executionDelay = 5;
        
        for (String rawFile : rawFiles ) {                   
            
            // Create an MSGFPlusWrapper from the view's params.
            MSGFPlusWrapper msgf = new MSGFPlusWrapper(identParamsExecute.getParams());

            // First get the spectrum file we want to run identifications for in this run.
                  

            // Set the spectrum file as the input to MSGF+
            msgf.setInputSpectrumString(directory + "\\" + rawFile);         
            
            msgf.setModificationFileName(identParamsExecute.getModificationFile().getAbsolutePath());

            msgf.performSearch(executionDelay);
            executionDelay +=5;
        }
    }
}
