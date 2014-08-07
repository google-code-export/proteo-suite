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
import org.proteosuite.model.BackgroundTaskSubject;
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

        if (!OpenMSLabelFreeWrapper.checkIsInstalled()) {
            int result = JOptionPane
                    .showConfirmDialog(
                            null,
                            "You do not appear to have openMS installed.\n"
                            + "You need to install openMS in able to use the label-free quantitation feature.\n"
                            + "openMS features will be disabled for now.\n"
                            + "OpenMS is available at:\nhttp://open-ms.sourceforge.net/\nTo install now, click \"Yes\" to be directed to the openMS web site.\n"
                            + "Once installed you will need to restart Proteosuite to use openMS features.",
                            "openMS Not Installed!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                OpenURL.open("http://open-ms.sourceforge.net/");
                return;
            }
        }

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
                    ExceptionCatcher.reportException(e);
                    JOptionPane.showMessageDialog(null,
                            "ProteoSuite has crashed with: " + e.getMessage(),
                            "OH NO!", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
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

    private static class UpdateWorker extends BackgroundTask {

        public UpdateWorker() {
            super(new BackgroundTaskSubject() {
                @Override
                public String getSubjectName() {
                    return "ProteoSuite";
                }
            }, "Checking For Update");

            super.addAsynchronousProcessingAction(new ProteoSuiteAction<String, Void>() {
                @Override
                public String act(Void argument) {
                    try {
                        return UpdateCheck.hasUpdate(ProteoSuite.PROTEOSUITE_VERSION);
                    } catch (IOException ex) {
                        Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return null;
                }
            });

            super.addCompletionAction(new ProteoSuiteAction<Void, Void>() {

                @Override
                public Void act(Void argument) {
                    String newVersion = UpdateWorker.super.getResultOfClass(String.class);
                    if (newVersion == null) {
                        return null;
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

                    return null;
                }
            });
        }
    }
}
