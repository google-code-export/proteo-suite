package org.proteosuite;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.actions.TaskPostCompleteAction;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.ProteoSuite;
import org.proteosuite.model.BackgroundTask;
import org.proteosuite.model.BackgroundTaskManager;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.utils.ExceptionCatcher;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.UpdateCheck;

public class Launcher {

    /**
     * Main
     *
     * @param args the command line arguments (leave empty)
     */
    public static void main(String args[]) {
        // Setting standard look and feel
        setLookAndFeel();
        installCheckOpenMS();

        // Pre-load the searchGUI modifications.
        IdentParamsView.readInPossibleMods();

        BackgroundTaskManager.getInstance().setTasksRefreshAction(new TaskPostCompleteAction());

        BackgroundTask checkVersion = new UpdateWorker();
        BackgroundTaskManager.getInstance().submit(checkVersion);

        // Create and display the form
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ProteoSuite().setVisible(true);
                } catch (Exception e) {
                    handleException(new ProteoSuiteException("ProteoSuite error.", e));
                }
            }
        });
    }
    
    public static void handleException(ProteoSuiteException pex) {
        ExceptionCatcher.reportException(pex);
        int result = JOptionPane.showConfirmDialog(null, "ProteoSuite has encountered a fatal problem: " + pex.getLocalizedMessage() + "\n"
        + "You can close the software now, in which case any work currently being done may be lost, or you"
                + " can allow tasks to complete (if possible) and close it later.\n"
                + "Do you wish to close the software immediately?", "He's dead, Jim!", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(1);
        }       
    }

    private static void installCheckOpenMS() {
        String openMSUrl = "http://open-ms.sourceforge.net";

        if (!OpenMSLabelFreeWrapper.checkIsInstalled()) {
            int result = JOptionPane
                    .showConfirmDialog(
                            null,
                            "You do not appear to have openMS installed.\n"
                            + "You need to install openMS in order to use the label-free quantitation feature.\n"
                            + "openMS features will be disabled for now.\n"
                            + "OpenMS is available at:\n" + openMSUrl + "\nTo install now, click \"Yes\" to be directed to the openMS web site.\n"
                            + "Once installed you will need to restart Proteosuite to use openMS features.",
                            "openMS Not Installed!", JOptionPane.YES_NO_OPTION);
            
            BackgroundTaskManager.getInstance().freeMoreThreadsForGenericExecution();
            if (result == JOptionPane.YES_OPTION) {
                OpenURL.open(openMSUrl);
                return;
            }
        }

    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
    }

    private static class UpdateWorker extends BackgroundTask<ProteoSuiteActionSubject> {

        public UpdateWorker() {
            super(new ProteoSuiteActionSubject() {
                @Override
                public String getSubjectName() {
                    return "ProteoSuite";
                }
            }, "Checking For Update");           

            super.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
                @Override
                public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
                    try {
                        String updateCheckString = UpdateCheck.hasUpdate(ProteoSuite.PROTEOSUITE_VERSION);
                        ProteoSuiteActionResult<String> result = new ProteoSuiteActionResult(updateCheckString, null); 
                        return result;
                    } catch (IOException ex) {
                        Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                        return new ProteoSuiteActionResult(new ProteoSuiteException("Error checking for new version.", ex));
                    }                   
                }
            });

            super.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject>() {
                @Override
                public ProteoSuiteActionResult act(ProteoSuiteActionSubject subject) {
                    String newVersion = UpdateWorker.super.getResultOfClass(String.class);
                    if (newVersion == null) {
                        return new ProteoSuiteActionResult(new ProteoSuiteException("Error getting version result from task."));                        
                    }

                    int result = JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "There is a new version of ProteoSuite available\n Click OK to visit the download page.",
                                    "Information", JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        OpenURL.open(newVersion);
                    }

                    return ProteoSuiteActionResult.emptyResult();
                }
            });
        }
    }
}
