package org.proteosuite.gui.listener;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.tables.CreateOrLoadIdentificationsTable;
import org.proteosuite.identification.MSGFPlusWrapper;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CreateIdentificationsForSelectedListener implements ActionListener {

    private CreateOrLoadIdentificationsStep step;

    public CreateIdentificationsForSelectedListener(CreateOrLoadIdentificationsStep step) {
        this.step = step;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // Get the selected raw files from the table to know which ones to run ident for.
        int[] selectedRawFiles = step.getIdentificationsTable().getSelectedRows();
        
        CreateOrLoadIdentificationsTable identTable = step.getIdentificationsTable();
        
        
        AnalyseData data = AnalyseData.getInstance();

        // Get the identParamsView.
        final IdentParamsView identParamsExecute = step.getIdentParamsView();

        // Create a dialog for the view, while finding the parent JFrame.
        Component parent;
        parent = step.getParent();
        while (!(parent instanceof JFrame)) {
            parent = parent.getParent();
        }

        final JDialog dialogIdentParamsExecute = new JDialog((JFrame) parent, "Set Identification Parameters", Dialog.ModalityType.APPLICATION_MODAL);

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

        // Check if we should do protein inference.
        boolean doProteinInference = identParamsExecute
                .getProteinInference();
        String sRegex = identParamsExecute.getRegex();
        String firstFile = "";

        for (int fileIndex = 0; fileIndex < selectedRawFiles.length; fileIndex++) {
            
            identTable.setValueAt("Creating...", selectedRawFiles[fileIndex], 2);            
            
            // Create an MSGFPlusWrapper from the view's params.
            MSGFPlusWrapper msgf = new MSGFPlusWrapper(identParamsExecute.getParams());

            // First get the spectrum file we want to run identifications for in this run.
            RawDataFile rawDataFile = data.getRawDataFile(selectedRawFiles[fileIndex]);          

            // Set the spectrum file as the input to MSGF+
            msgf.setInputSpectrum(rawDataFile);          

            if (fileIndex == 0) {
                firstFile = msgf.getOutputFileName();
            }

            msgf.performSearch(5);
        }
    }
}
