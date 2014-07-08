package org.proteosuite;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.ProteoSuite;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.UpdateCheck;

public class Launcher extends Application {

    public static void main(String[] args) {
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

        UpdateTask checkVersion = new UpdateTask();

        AnalyseData.getInstance().getGenericExecutor().submit(checkVersion);

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        ProteoSuite application = new ProteoSuite(stage);
        application.start();
    }

    private static class UpdateTask extends Task<String> {

        @Override
        protected String call() throws IOException {
            return UpdateCheck.hasUpdate(ProteoSuite.PROTEOSUITE_VERSION);
        }

        @Override
        protected void succeeded() {
            try {
                String newVersion = get();
                if (newVersion == null) {
                    return;
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
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
