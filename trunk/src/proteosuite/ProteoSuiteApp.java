/*
 * -----------------------------------------------------------------
 * ProteoSuiteApp.java (Main class)
 * -----------------------------------------------------------------
 * Description:       SUITE FOR QUANTITATIVE PROTEOMICS ANALYSES
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * -----------------------------------------------------------------
 */

package proteosuite;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ProteoSuiteApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ProteoSuiteView(this));
    }

    /**
     * This method is to initialise the specified window by injecting resources.
     * Windows shown in our application come fully initialised from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ProteoSuiteApp
     */
    public static ProteoSuiteApp getApplication() {
        return Application.getInstance(ProteoSuiteApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ProteoSuiteApp.class, args);
    }
}
