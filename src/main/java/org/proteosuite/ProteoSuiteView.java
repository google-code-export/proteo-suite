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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import org.proteosuite.gui.*;
import org.proteosuite.utils.SystemUtils;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * This class corresponds to the main form in ProteoSuite. The form includes the
 * visualisation of data and other associated tools.
 *
 * @author fgonzalez
 */
public class ProteoSuiteView extends JFrame {

    // Project settings
    public static final String PROTEOSUITE_VERSION = "0.3.3 ALPHA";
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

    private IdentParamsView identParamsExecute;

    // List of unmarshaller objects
    private List<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
    private List<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
    private List<MzQuantMLUnmarshaller> aMzQUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();

    public ProteoSuiteView() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);        

        // Load parameter settings
        identParamsExecute = new IdentParamsView("execute");

        // Initialising all components
        final JMenuItem jmCloseProject = new JMenuItem("Close Project");
        final JMenuItem jmSaveProject = new JMenuItem("Save Project",
                new ImageIcon(getClass().getClassLoader().getResource(
                                "images/save.gif")));

        final JButton jbSaveProject = new JButton(new ImageIcon(getClass()
                .getClassLoader().getResource("images/save.gif")));
        final JComboBox<String> jcbTechnique = new JComboBox<String>();       
            
        final JLabel jlFileNameMzIDText = new JLabel();
        final JLabel jlFileNameMzMLText = new JLabel();
        final JLabel jlFileNameMzQText = new JLabel();
        final JLabel jlIdentFilesStatus = new JLabel();        

        final TabbedChartViewer jtpViewer = new TabbedChartViewer();        

        final JLabel jlFileNameMGFText = new JLabel();
        final JTable jtMzIDProtGroup = new JTable();
        final JComboBox<String> jcbPSM = new JComboBox<String>();
        jcbPSM.setModel(new DefaultComboBoxModel<String>(new String[]{"<=1",
            "<=2", "<=3", "All"}));        

        initComponents(jmCloseProject, jmSaveProject, jtpViewer,
                jbSaveProject, jcbTechnique, jlFileNameMzMLText,
                jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
                jlFileNameMGFText, jcbPSM);

        // Project default settings
        initProjectValues(jmCloseProject, jmSaveProject, jtpViewer,
                jbSaveProject, jcbTechnique, jlFileNameMzMLText,
                jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText);

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
    }

    private void initComponents(final JMenuItem jmCloseProject,
            final JMenuItem jmSaveProject, final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
            final JComboBox<String> jcbTechnique, final JLabel jlFileNameMzMLText, final JLabel jlFileNameMzIDText,
            final JLabel jlIdentFilesStatus, final JLabel jlFileNameMzQText,            
            final JLabel jlFileNameMGFText,
            final JComboBox<String> jcbPSM) {

        jcbTechnique.setModel(new DefaultComboBoxModel<>(new String[]{
            "Select technique", "iTRAQ", "TMT", "emPAI"}));
        jcbTechnique.setToolTipText("Select a technique for the analysis");
        jcbTechnique.setBorder(BorderFactory.createEtchedBorder());                

        final JComboBox<String> jcbOutputFormat = new JComboBox<String>();
        jcbOutputFormat.setModel(new DefaultComboBoxModel<String>(new String[]{
            "Select format", "mzq"}));
        jcbOutputFormat.setToolTipText("Select an output format");

        final JLabel jlRawFilesStatus = new JLabel(new ImageIcon(getClass()
                .getClassLoader().getResource("images/empty.gif")));
        jlRawFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jlRawFilesStatus.setForeground(new Color(51, 102, 0));

        jlIdentFilesStatus.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jlIdentFilesStatus.setForeground(new Color(51, 102, 0));

        

        setJMenuBar(new MenuBar(this, jcbTechnique, jmSaveProject,
                jmCloseProject, jtpViewer, jbSaveProject,
                jcbOutputFormat, jlFileNameMzMLText,
                jlFileNameMzIDText, jlIdentFilesStatus, jlFileNameMzQText,
                jcbPSM, jlRawFilesStatus, identParamsExecute, aMzMLUnmarshaller, aMzIDUnmarshaller,
                aMzQUnmarshaller));

        add(new HeaderPanel(this, jmCloseProject, jmSaveProject,
                jtpViewer, jbSaveProject, jcbTechnique,                
                jlFileNameMzMLText, jlFileNameMzIDText, jlIdentFilesStatus,
                jlFileNameMzQText, 
                jcbOutputFormat, jlFileNameMGFText, jcbPSM,
                jlRawFilesStatus, identParamsExecute, aMzMLUnmarshaller,
                aMzIDUnmarshaller, aMzQUnmarshaller), BorderLayout.PAGE_START);

        add(new LeftColumnAndTabbedPanel(), BorderLayout.CENTER);        

        pack();
    }      

    /**
     * Update save icon
     *
     * @param void
     * @return void
     */
    public void updateSaveProjectStatus(final JMenuItem jmSaveProject,
            final JButton jbSaveProject) {
        jmSaveProject.setEnabled(WORKSPACE.isProjectModified());
        jbSaveProject.setEnabled(WORKSPACE.isProjectModified());
    }

    /**
     * Update close project icon
     *
     * @param void
     * @return void
     */
    private void updateCloseProjectStatus(final JMenuItem jmCloseProject) {
        if (sProjectName.equals("New")) {
            jmCloseProject.setEnabled(false);
        } else {
            jmCloseProject.setEnabled(true);
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
     * Initialise project settings (all modules)
     *
     * @param jlFileNameMzMLText
     * @param jlFileNameMzIDText
     * @param jlIdentFilesStatus
     * @param jlFileNameMzQText
     * @param jtRawData
     * @param jtpProperties
     * @param void
     * @return void
     */
    private void initProjectValues(JMenuItem jmCloseProject,
            JMenuItem jmSaveProject, final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
            JComboBox<String> jcbTechnique, JLabel jlFileNameMzMLText, JLabel jlFileNameMzIDText,
            JLabel jlIdentFilesStatus, JLabel jlFileNameMzQText) {
		// Loading values from config file
        // From config file (workspace)
        initSettings(jmCloseProject, jmSaveProject, jbSaveProject);
        // Project pipeline
        initProjectStatusIcons(jcbTechnique, jlIdentFilesStatus);

        // Initialising components
        

        initTextAreas(jlFileNameMzMLText,
                jlFileNameMzIDText, jlFileNameMzQText);
        jtpViewer.reset();

        // ISO 3166 country code - GB
        Locale.setDefault(new Locale("en", "GB"));

        aMzMLUnmarshaller.clear();
        aMzIDUnmarshaller.clear();
        aMzQUnmarshaller.clear();
    }

    /**
     * Initialise project settings (configuration XML file)
     *
     * @param void
     * @return void
     */
    private void initSettings(JMenuItem jmCloseProject,
            JMenuItem jmSaveProject, final JButton jbSaveProject) {
        // ... Validate if config file exists ...//
        if (!WORKSPACE.isValidConfigFile()) {
            String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
            sMessage += "under your installation directory. ProteoSuite will continue launching, however \n";
            sMessage += "it is recommended that you copy the file as indicated in the readme.txt file.";
            JOptionPane.showMessageDialog(this, sMessage, "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
        sProjectName = "New";        
        WORKSPACE.setProjectModifiedTag(false);
        updateSaveProjectStatus(jmSaveProject, jbSaveProject);
        updateCloseProjectStatus(jmCloseProject);
        updateTitle();
    }

    /**
     * Initialise project status icons
     *
     * @param jlIdentFilesStatus
     * @param void
     * @return void
     */
    private void initProjectStatusIcons(JComboBox<String> jcbTechnique,
            JLabel jlIdentFilesStatus) {
        // ... Project status pipeline ...//
        Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader()
                .getResource("images/empty.gif"));
        jlIdentFilesStatus.setIcon(loadRawFilesIcon);
        jcbTechnique.setSelectedIndex(0);
    }

    /**
     * Initialise Text Areas
     *
     * @param jlFileNameMzMLText
     * @param jlFileNameMzIDText
     * @param jlFileNameMzQText
     * @param void
     * @return void
     */
    private void initTextAreas(final JLabel jlFileNameMzMLText,
            JLabel jlFileNameMzIDText, JLabel jlFileNameMzQText) {

                
        
        jlFileNameMzMLText.setText("");
        jlFileNameMzIDText.setText("");
        jlFileNameMzQText.setText("");        
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
                    // UIManager.setLookAndFeel(info.getClassName());
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
