/**
 * --------------------------------------------------------------------------
 * ProteoSuiteView.java
 * --------------------------------------------------------------------------
 * Description: ProteoSuite Graphical Unit Interface Developer: fgonzalez
 * Created: 08 February 2011 Notes: GUI generated using NetBeans IDE 7.0.1 Read
 * our documentation file under our Google SVN repository SVN:
 * http://code.google.com/p/proteo-suite/ Project Website:
 * http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.proteosuite.gui.*;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.SystemUtils;

/**
 * This class corresponds to the main form in ProteoSuite. The form includes the
 * visualisation of data and other associated tools.
 *
 * @author fgonzalez
 */
public class ProteoSuiteView extends JFrame {

    // Project settings
    public static final String PROTEOSUITE_VERSION = "1.0.0 ALPHA";
    public static String sProjectName = "";
    public static String sPreviousLocation = "user.home";    
    public static final String MZQ_XSD = "mzQuantML_1_0_0.xsd";
    public static final String mzMLVersion = "1.1";
    public static final String mzIDVersion = "1.1";
    public static final String MZQUANT_VERSION = "1.0.0";
    public static final String PSI_MS_VERSION = "3.37.0";
    public static final String PSI_MOD_VERSION = "1.2";
    public static final SystemUtils SYS_UTILS = new SystemUtils();    

    private static final WorkSpace WORKSPACE = WorkSpace.getInstance();

    private final IdentParamsView identParamsExecute;   

    public ProteoSuiteView() {             
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        System.out.println("User dir: " + System.getProperty("user.dir"));

        // Load parameter settings
        identParamsExecute = new IdentParamsView("execute");              

        setJMenuBar(new MenuBar());
        add(new LeftColumnAndTabbedPanel(), BorderLayout.CENTER);
        add(new StatusPanel(), BorderLayout.PAGE_END);
        

        // ISO 3166 country code - GB
        Locale.setDefault(new Locale("en", "GB"));   

        updateTitle();

        // Setting project icons
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
                "images/icon.gif")).getImage());

        // Check Java version and architecture
        System.out.println("****************************************");
        System.out.println("*****    P R O T E O S U I T E    ******");
        System.out.println("****************************************");
        SYS_UTILS.checkMemory("starting up");
        System.out.println("Java version: "
                + System.getProperty("java.version"));
        System.out.println("Architecture: "
                + System.getProperty("sun.arch.data.model") + "-bit");
        System.out.println("Classpath: "
                + System.getProperty("java.class.path"));
        System.out.println("****************************************");

        // Setting Window Height and Width
        setMinimumSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Configuring exit events
        pack();
        
        if (!OpenMSLabelFreeWrapper.checkIsInstalled()) {
            int result = JOptionPane.showConfirmDialog(this, "You do not appear to have openMS installed.\nYou need to install openMS in able to use the label-free quantiation feature.\n"
                    + "OpenMS is available at:\nhttp://open-ms.sourceforge.net/\nTo install now, click \"Yes\" to be directed to the openMS web site.", "openMS Not Installed!", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                OpenURL.open("http://open-ms.sourceforge.net/");
            }
        }
    }

    /**
     * Update window title main project
     */
    public void updateTitle() {
        setTitle("ProteoSuite " + PROTEOSUITE_VERSION + " - <Project: "
                + WORKSPACE.getWorkSpace() + " - " + sProjectName
                + ">      http://www.proteosuite.org");
    }

    /**
     * Main
     *
     * @param args the command line arguments (leave empty)
     * @return void
     */
    public static void main(String args[]) {
        // Setting standard look and feel
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

        // Create and display the form
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProteoSuiteView().setVisible(true);
            }
        });
    }
}
