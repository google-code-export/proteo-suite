/**
 * --------------------------------------------------------------------------
 * ProteoSuite.java
 * --------------------------------------------------------------------------
 * Description: ProteoSuite Graphical Unit Interface Developer: fgonzalez
 * Created: 08 February 2011 Notes: Read our documentation file under our Google
 * SVN repository SVN: http://code.google.com/p/proteo-suite/ Project Website:
 * http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.proteosuite.WorkSpace;

/**
 * This class corresponds to the main form in ProteoSuite. The form includes the
 * visualisation of data and other associated tools.
 *
 * @author fgonzalez
 */
public class ProteoSuite {

    private static final long serialVersionUID = 1L;
    // Project settings
    public static final String PROTEOSUITE_VERSION = "0.3.3 ALPHA";

    public static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
    public static final String PSI_MS_VERSION = "3.37.0";
    public static final String PSI_MOD_VERSION = "1.2";

    private static final WorkSpace WORKSPACE = WorkSpace.getInstance();
    private Stage stage = null;
    
    public ProteoSuite(Stage stage) {
        this.stage = stage;
    }   
    
    public void start() throws Exception {
        stage.setTitle("ProteoSuite");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent t) {
                stage.close();
            }
        });

        StackPane root = new StackPane();
        //root.getChildren().add(new MenuBar());
        //root.getChildren().add(TabbedMainPanel.getInstance(), BorderLayout.CENTER);
        //root.getChildren().add(new StatusPanel(), BorderLayout.PAGE_END);
        stage.setScene(new Scene(root, 1024, 768));
        stage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/icon.gif").toExternalForm()));
        stage.show();
    }
}
