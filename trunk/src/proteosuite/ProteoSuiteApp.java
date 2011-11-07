/*
 * --------------------------------------------------------------------------
 * ProteoSuiteApp.java (Main class)
 * --------------------------------------------------------------------------
 * Description:       SOFTWARE FOR ANALYSIS OF QUANTITATIVE PROTEOMICS DATA
 * Developer:         Faviel Gonzalez
 * Created:           08 February 2011
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package proteosuite;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * Main class
 */
public class ProteoSuiteApp extends SingleFrameApplication {

    /**
     * Startup
     */
    @Override protected void startup() {
        show(new ProteoSuiteView(this));
    }

    /**
     * Window settings
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
